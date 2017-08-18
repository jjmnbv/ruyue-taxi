package com.szyciov.lease.service;


import com.szyciov.dto.driverShiftManagent.PendingDto;
import com.szyciov.dto.driverShiftManagent.PendingSaveDto;
import com.szyciov.dto.driverVehicleBindManage.VehicleBindInfoDto;
import com.szyciov.entity.PeDrivershiftPending;
import com.szyciov.enums.DriverEnum;
import com.szyciov.enums.PeDrivershiftEnum;
import com.szyciov.enums.PendInfoEnum;
import com.szyciov.enums.RedisKeyEnum;
import com.szyciov.lease.dao.PeDrivershiftPendingDao;
import com.szyciov.lease.dao.PubDriverDao;
import com.szyciov.lease.dao.RoleManagementDao;
import com.szyciov.lease.dto.drivershift.PendInfoDto;
import com.szyciov.lease.dto.pending.PendingPopDto;
import com.szyciov.lease.entity.LeShiftRule;
import com.szyciov.lease.entity.PubDriver;
import com.szyciov.lease.entity.PubVehicle;
import com.szyciov.message.ProcessedMessage;
import com.szyciov.message.redis.RedisMessage;
import com.szyciov.param.PeDrivershiftPendingParam;
import com.szyciov.passenger.util.MessageUtil;
import com.szyciov.util.*;
import com.szyciov.util.message.RedisListMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class PeDrivershiftPendingService {

	private Logger logger = LoggerFactory.getLogger(PeDrivershiftPendingService.class);

	@Autowired
	private PeDrivershiftPendingDao pendingDao;

	@Autowired
	private PeDrivershiftProcessedService processedService;

	@Autowired
	private PubDriverVehicleRefService refService;

	@Autowired
	private LeShiftRulesService rulesService;

	@Autowired
	private PubVehicleService vehicleService;

	@Autowired
	private PubDriverDao driverDao;

	@Autowired
	private RoleManagementDao roleDao;
	/**
	 * 创建交接班待处理
	 * @param savePending		待交班对象
	 * @return
	 */
	@Transactional(rollbackFor = {Exception.class})
	public PendInfoDto save(PendingSaveDto savePending)throws Exception{

		//判断车辆与司机是否在绑定状态
		boolean isBind = refService.isBind(savePending.getDriverid(),savePending.getVehicleid());

		PendInfoDto infoDto = new PendInfoDto();

		//如果绑定，则进行交接班操作
		if(isBind) {
            //获取交接班规则
            LeShiftRule rule =  this.getShiftRule(savePending.getVehicleid(),savePending.getLeasescompanyid());
			if(rule!=null) {
				PeDrivershiftPending pending = this.createPending(savePending);
				//获取司机信息
				PubDriver driver = driverDao.getById(savePending.getDriverid());

				Date overTime = null;

				//自主交接班 超时时间取 自主交接班时限
				if(PeDrivershiftEnum.RELIVED_TYPE_AUTONOMOUSLY.code.equals(pending.getRelievedtype())){
					overTime = DateUtil.getNowDateAppend(0,0,0,0,rule.getAutoshifttime(),0);
				//人工交接班 超时时间取 人工交接班时限
				}else if(PeDrivershiftEnum.RELIVED_TYPE_MANPOWER.code.equals(pending.getRelievedtype())){
					overTime = DateUtil.getNowDateAppend(0,0,0,0,rule.getManualshifttime(),0);
				}
				//上线时间不为空
				if(driver.getUptime()!=null) {
					//最近一次在线时长
					long nowTime = DateUtil.hasMinute(driver.getUptime(), new Date());
					//历史累计在线时长
					long oldTime = Long.valueOf((driver.getOnlinetime() / 60));
					pending.setOnlinetime(nowTime + oldTime);
					savePending.setOnlinetime(nowTime + oldTime);
				}
				//设置超时时间
				pending.setOvertimetime(overTime);

				//保存待交班
				pendingDao.save(pending);

				savePending.setId(pending.getId());

				//获取人工派单超时时限(秒)
				int	time = rule.getManualshifttime() * 60;

				infoDto.setPendId(pending.getId());
				infoDto.setAutotime(time);
				infoDto.setState(PendInfoEnum.DEFAULT.code);
				//修改交班司机状态为交班中
				this.updateDriverPassWork(pending.getDriverid(), pending.getCreater(),DriverEnum.PASS_WORK_STATUS_PENDING);
				//修改接班司机状态为接班中
				this.updateDriverPassWork(savePending.getRelieveddriverid(),pending.getCreater(),DriverEnum.PASS_WORK_STATUS_PROCESSIND);

				//如果为人工交接班，则发送redis消息
				if(PeDrivershiftEnum.RELIVED_TYPE_MANPOWER.code.equals(pending.getRelievedtype())){
					this.sendRedisMessage(savePending,time*1000);
				}
			}else{
				infoDto.setState(PendInfoEnum.NO_RULE.code);
			}
		}else{
			infoDto.setState(PendInfoEnum.NO_BIND.code);
		}
		return infoDto;
	}


	/**
	 * 创建待交接班实体对象
	 * @param savePending	交接班保存DTO
	 * @return
	 */
	private PeDrivershiftPending createPending(PendingSaveDto savePending){

		String id = GUIDGenerator.newGUID();
		PeDrivershiftPending pending = new PeDrivershiftPending();
		//设置id
		pending.setId(id);
		pending.setLeasescompanyid(savePending.getLeasescompanyid());
		pending.setOnlinetime(savePending.getOnlinetime());
		pending.setPlatformtype(savePending.getPlatformtype());
		pending.setRelievedtype(savePending.getRelievedtype());
		pending.setShifttype(PeDrivershiftEnum.SHIFT_TYPE_PENDING.code);
		pending.setVehicleid(savePending.getVehicleid());
		pending.setApplytime(new Date());
		pending.setCreater(savePending.getCreater());
		pending.setCreatetime(new Date());
		pending.setDriverid(savePending.getDriverid());
		pending.setDriverinfo(savePending.getDriverinfo());
		return pending;
	}

	/**
	 * 发送redis消息
	 * @param pending	待交接班对象
	 * @param extime	超时时间
	 */
	private void sendRedisMessage(PendingSaveDto pending,long extime){

		List<String> list = roleDao.listRoleId(pending.getLeasescompanyid(),"交接班管理");
		RedisMessage redisMessage = this.createRedisMessage(pending,extime);
		try{
			if(list==null||list.size()==0){
				redisMessage.setKey(RedisKeyEnum.MESSAGE_LEASE_ROLE_ADMIN.code+pending.getLeasescompanyid());
				RedisListMessage.getInstance().pushMessage(redisMessage);
			}else{
				for(String roleId:list){
					redisMessage.setKey(roleId);
					RedisListMessage.getInstance().pushMessage(redisMessage);
				}
			}
		}catch (Exception e){
			logger.error("生成待人工处理交接班失败：pending-{}", GsonUtil.toJson(pending),e);
		}

	}

	/**
	 * 创建redis消息对象
	 * @param pending	待交接班对象
	 * @param extime	超时时间
	 * @return
	 */
	private RedisMessage createRedisMessage(PendingSaveDto pending,long extime){

		PendingPopDto popDto = new PendingPopDto();
		popDto.setId(pending.getId());
		popDto.setOnlinetimeStr(pending.getOnlinetime()/60+"小时"+pending.getOnlinetime()%60+"分");
		popDto.setDriverInfo(pending.getDriverinfo());
		popDto.setTitle("待人工指派交接班");
		popDto.setPlatenoStr(pending.getPlatenoStr());
		popDto.setExtime(extime);

		RedisMessage redisMessage = new RedisMessage();
		redisMessage.setBusiness(RedisKeyEnum.MESSAGE_PENDING.code);
		redisMessage.setOperation(RedisKeyEnum.MESSAGE_TYPE_MAKE.code);
		redisMessage.setNowTime(System.currentTimeMillis());
		redisMessage.setExTime(extime);
		redisMessage.setMessage(popDto);
		redisMessage.setFunction(RedisKeyEnum.MESSAGE_FUNCTION_POPUP.code);

		return redisMessage;
	}



	/**
	 * 根据司机ID 返回交班申请信息
	 * @param driverId		司机ID
	 * @return
	 */
	public PendInfoDto getPendInfo(String driverId){

		PeDrivershiftPending pending = this.getPendingByDriverId(driverId);

		//默认交班状态正常
		int state = PendInfoEnum.DEFAULT.code;

		long time = 0L;

		PendInfoDto infoDto = new PendInfoDto();

		if(pending!=null){

			//判断车辆与司机是否在绑定状态
			boolean isBind = refService.isBind(pending.getDriverid(),pending.getVehicleid());

			if(isBind) {
				//设置ID
				infoDto.setPendId(pending.getId());

				//如果当前时间毫秒数-超时时间的毫秒数>=0 则为超时
				if(DateUtil.contrastDate(new Date(), pending.getOvertimetime())>=0){
					//交班失败 超时
					state = PendInfoEnum.OUT_TIME.code;
				}
			}else{
				//交班失败 没有绑定信息
				state  = PendInfoEnum.NO_BIND.code;
			}

			//如果交接班为失败，则删除该交班申请数据
			if(state != PendInfoEnum.DEFAULT.code){
				//删除交接班申请数据
				this.removeShfitApply(pending);
			}
		}else{
			//交班失败 没找到交班申请
			state  = PendInfoEnum.NO_PENDING.code;
		}


		//设置剩余时限
		infoDto.setAutotime(time);
		//设置状态
		infoDto.setState(state);

		return infoDto;
	}

	/**
	 * 更新司机状态 为交班中
	 * @param id                记录ID
	 * @param personId          处理人ID
	 */
	private void updateDriverPassWork(String id,String personId,DriverEnum driverEnum){
		PubDriver driver = new PubDriver();
		driver.setPassworkStatus(driverEnum.code);
		driver.setId(id);
		driver.setUpdater(personId);
		driver.setUpdateTime(new Date());
		driverDao.updatePubDriverStatus(driver);
	}


	/**
	 * 根据车辆ID 返回对应交接班规则 对象
	 * @param vehicleId
	 * @param leaseId
	 * @return
	 */
	private LeShiftRule getShiftRule(String vehicleId,String leaseId){

		//获取车辆信息
		PubVehicle pubVehicle = vehicleService.getVehicleById(vehicleId);

		//获取交接班规则
		return rulesService.getRules(leaseId,pubVehicle.getCity());
	}


	/**
	 * 是否有未完成订单
	 * @param pendingId    车辆ID
	 * @return
	 */
	public boolean isUnfinishedOrder(String pendingId){
		PeDrivershiftPending paramPend = new PeDrivershiftPending();
		paramPend.setId(pendingId);
		PeDrivershiftPending pending = pendingDao.getPendingInfo(paramPend);

		int count = pendingDao.getUnfinishedOrderCount(pending.getVehicleid());
		if(count>0){
			return true;
		}
		return false;
	}


	/**
	 * 取消交接班申请
	 * @param pendingId   待处理交班ID
	 * @return
	 */
	@Transactional(rollbackFor = {Exception.class})
	public void removeShfitApply(String pendingId)throws Exception{
		PeDrivershiftPending paramPend = new PeDrivershiftPending();
		paramPend.setId(pendingId);
		PeDrivershiftPending pending = pendingDao.getPendingInfo(paramPend);

		this.removeShfitApply(pending);
	}


	/**
	 * 根据司机ID删除交班申请
	 * @param driverId		交班司机ID
	 * @throws Exception
	 */
	@Transactional(rollbackFor = {Exception.class})
	public void removeShfitApplyByDriver(String driverId)throws Exception{
		PeDrivershiftPending paramPend = new PeDrivershiftPending();
		paramPend.setDriverid(driverId);
		PeDrivershiftPending pending = pendingDao.getPendingInfo(paramPend);

		this.removeShfitApply(pending);
	}




	/**
	 * 删除待交接班申请 并更改司机状态
	 * @param pending
	 */
	private void removeShfitApply(PeDrivershiftPending pending){
		//删除待交班数据
		pendingDao.removeById(pending.getId());
		//重新将司机状态改为当班中
		processedService.updateDriverPassWork(pending.getDriverid(),pending.getCreater(),DriverEnum.PASS_WORK_STATUS_ON);

	}


	/**
	 * 查询待处理交班列表
	 * @param pendingParam
	 * @return
	 */
	public PageBean listPending(PeDrivershiftPendingParam pendingParam){

		List<PendingDto> list = pendingDao.listPending(pendingParam);

		for(PendingDto dto:list){
			dto.setSucceedDrivers(refService.getVehicleBindDetails(dto.getVehicleid(),dto.getDriverid()));
		}

		PageBean pageBean = new PageBean();
		pageBean.setsEcho(pendingParam.getsEcho());

		int iTotalRecords = pendingDao.getlistPendingCount(pendingParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}


	/**
	 * 删除待交班记录
	 * @param id
	 * @return
	 */
	public int removeById(String id){
		return  pendingDao.removeById(id);
	}


	/**
	 * 获取完整待接班信息记录
	 * @param paramPend	待接班信息参数对象
	 * @return
	 */
	public PeDrivershiftPending getAllPending(PeDrivershiftPending paramPend){
		return pendingDao.getPendingInfo(paramPend);
	}

	/**
	 * 获取完整待接班信息记录
	 * @param driverId	司机ID
	 * @return
	 */
	public PeDrivershiftPending getPendingByDriverId(String driverId){
		PeDrivershiftPending pending = new PeDrivershiftPending();
		pending.setDriverid(driverId);
		return pendingDao.getPendingInfo(pending);
	}

	/**
	 * 判断是否需要指定当班
	 * @param vehicleId	车辆ID
	 * @return
	 */
	public boolean isAssign(String vehicleId){
		//获取车辆信息
		PubVehicle vehicle= vehicleService.getById(vehicleId);
		//获取车辆对应绑定司机
		List<VehicleBindInfoDto> list = refService.listAllVehicleBindInfo(vehicleId);
		if(list!=null && vehicle!=null){
			PubDriver pubDriver = driverDao.getById(vehicle.getDriverId());
			//如果司机>=2
			if (list.size() > 1) {
				if(pubDriver==null){
					return true;
				}
				//如果司机状态为当班或交班中，则不需要指定交班，否则需要提示指定交班
				if(DriverEnum.PASS_WORK_STATUS_ON.code.equals(pubDriver.getPassworkStatus())
						||DriverEnum.PASS_WORK_STATUS_PENDING.code.equals(pubDriver.getPassworkStatus())){
					return false;
				}else{
					return true;
				}
			}

		}
		return false;
	}

}