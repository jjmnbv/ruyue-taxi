package com.szyciov.lease.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szyciov.dto.pubAlarmprocess.PubAlarmprocessSaveDto;
import com.szyciov.dto.pubAlarmprocess.SavePubAlarmprocessDto;
import com.szyciov.enums.RedisKeyEnum;
import com.szyciov.lease.dao.PubAlarmprocessDao;
import com.szyciov.lease.dao.RoleManagementDao;
import com.szyciov.lease.dto.alarmprocess.AlarmprocessPopDto;
import com.szyciov.lease.entity.PubRoleId;
import com.szyciov.lease.param.PubAlarmprocessParam;
import com.szyciov.message.redis.RedisMessage;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.GsonUtil;
import com.szyciov.util.PageBean;
import com.szyciov.util.message.RedisListMessage;

@Service("pubAlarmprocessService")
public class PubAlarmprocessService {
	private PubAlarmprocessDao dao;
	@Resource(name = "PubAlarmprocessDao")
	public void setDao(PubAlarmprocessDao dao) {
		this.dao = dao;
	}
	public PageBean getPubAlarmprocessByQuery(PubAlarmprocessParam pubAlarmprocessParam){
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(pubAlarmprocessParam.getsEcho());
		List<PubAlarmprocessParam> list = getPubAlarmprocessByQueryList(pubAlarmprocessParam);
		int iTotalRecords = getPubAlarmprocessByQueryCount(pubAlarmprocessParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	@Autowired
	private RoleManagementDao roleDao;
	private Logger logger = LoggerFactory.getLogger(PubAlarmprocessService.class);
	public List<PubAlarmprocessParam> getPubAlarmprocessByQueryList(PubAlarmprocessParam pubAlarmprocessParam){
		return dao.getPubAlarmprocessByQueryList(pubAlarmprocessParam);
	}
	public int getPubAlarmprocessByQueryCount(PubAlarmprocessParam pubAlarmprocessParam){
		return dao.getPubAlarmprocessByQueryCount(pubAlarmprocessParam);
	}
	public PubAlarmprocessParam getPubAlarmprocessDetail(String id){
		return dao.getPubAlarmprocessDetail(id);
	}
	public int updataDetail(PubAlarmprocessParam pubAlarmprocessParam)  {
		 return dao.updataDetail(pubAlarmprocessParam);
	}
	public List<Map<String, Object>> getPubAlarmprocessDriver(PubAlarmprocessParam pubAlarmprocessParam)  {
		return dao.getPubAlarmprocessDriver(pubAlarmprocessParam);
	}
	public List<Map<String, Object>> getPubAlarmprocessPassenger(PubAlarmprocessParam pubAlarmprocessParam)  {
		return dao.getPubAlarmprocessPassenger(pubAlarmprocessParam);
	}
	public List<Map<String, Object>> getPubAlarmprocessPlateno(PubAlarmprocessParam pubAlarmprocessParam)  {
		return dao.getPubAlarmprocessPlateno(pubAlarmprocessParam);
	}
	public int ordernoOK(PubRoleId pubRoleId)  {
		return  dao.ordernoOK(pubRoleId);
	}
	public int handleOK(PubAlarmprocessParam pubAlarmprocessParam)  {
		return dao.handleOK(pubAlarmprocessParam);
	}
	/**
	 * 创建报警
	 * @param 
	 * @return
	 */
	public PubAlarmprocessSaveDto save(SavePubAlarmprocessDto saveDto)throws Exception{
		//保存报警信息
		PubAlarmprocessSaveDto alarmprocessPopDto  = createPubAlarmprocess(saveDto);
		dao.save(alarmprocessPopDto);
		long time = 30000;
		//发送报警
		sendRedisMessage(alarmprocessPopDto,time);
		return alarmprocessPopDto;
		}
	/**
	 * 创建实体对象
	 * @param
	 * @return
	 */
	@SuppressWarnings("unused")
	private PubAlarmprocessSaveDto createPubAlarmprocess(SavePubAlarmprocessDto saveDto){
		PubAlarmprocessSaveDto pubAlarmprocessSaveDto = new PubAlarmprocessSaveDto();
		String id = GUIDGenerator.newGUID();
		//设置id
		pubAlarmprocessSaveDto.setId(id);
		//创建时间
		pubAlarmprocessSaveDto.setCreatetime(new Date());
		pubAlarmprocessSaveDto.setUpdatetime(new Date());
		pubAlarmprocessSaveDto.setPlatformtype(saveDto.getPlatformtype());
		pubAlarmprocessSaveDto.setLeasecompanyid(saveDto.getLeasecompanyid());
		pubAlarmprocessSaveDto.setUsertype(saveDto.getUsertype());
		pubAlarmprocessSaveDto.setOrdertype(saveDto.getOrdertype());
		pubAlarmprocessSaveDto.setUserid(saveDto.getUserid());
		pubAlarmprocessSaveDto.setAlarmsource(saveDto.getAlarmsource());
		pubAlarmprocessSaveDto.setAlarmtype(saveDto.getAlarmtype());
		pubAlarmprocessSaveDto.setAlarmtime(saveDto.getAlarmtime());
		pubAlarmprocessSaveDto.setOrderno(saveDto.getOrderno());
		pubAlarmprocessSaveDto.setDriverid(saveDto.getDriverid());
		pubAlarmprocessSaveDto.setLng(saveDto.getLng());
		pubAlarmprocessSaveDto.setLat(saveDto.getLat());
		pubAlarmprocessSaveDto.setProcessstatus("0");
		pubAlarmprocessSaveDto.setStatus("1");
		pubAlarmprocessSaveDto.setCreater(saveDto.getCreater());
		return pubAlarmprocessSaveDto;
	}
	/**
	 * 发送redis消息
	 * @param 	
	 * @param extime	超时时间
	 */
	@SuppressWarnings("unused")
	private void sendRedisMessage(PubAlarmprocessSaveDto alarmprocessPopDto,long extime){

		List<String> list = roleDao.AllListRoleId(alarmprocessPopDto.getLeasecompanyid());
		RedisMessage redisMessage = this.createRedisMessage(alarmprocessPopDto,extime);
		try{
			if(list==null||list.size()==0){
				redisMessage.setKey(RedisKeyEnum.MESSAGE_LEASE_ROLE_ADMIN.code+alarmprocessPopDto.getLeasecompanyid());
				RedisListMessage.getInstance().pushMessage(redisMessage);
			}else{
				for(String roleId:list){
					redisMessage.setKey(roleId);
					RedisListMessage.getInstance().pushMessage(redisMessage);
				}
			}
		}catch (Exception e){
			logger.error("生成报警弹窗失败：alarmprocessPopDto-{}", GsonUtil.toJson(alarmprocessPopDto),e);
		}

	}

	/**
	 * 创建redis消息对象
	 * @param alarmprocessPopDto 报警对象
	 * @param extime	超时时间
	 * @return
	 */
	private RedisMessage createRedisMessage(PubAlarmprocessSaveDto alarmprocessPop,long extime){
		AlarmprocessPopDto alarmprocessPopDto = new AlarmprocessPopDto();
		alarmprocessPopDto.setId(alarmprocessPop.getId());
		if(alarmprocessPop.getAlarmsource() != null){
			if(alarmprocessPop.getAlarmsource().equals("0")){
				alarmprocessPopDto.setAlarmsource("乘客");	
			}
			if(alarmprocessPop.getAlarmsource().equals("1"))
				alarmprocessPopDto.setAlarmsource("司机");	
		}else{
			alarmprocessPopDto.setAlarmsource(alarmprocessPop.getAlarmsource());
		}
		if(alarmprocessPop.getAlarmtype() != null){
			if(alarmprocessPop.getAlarmtype().equals("0")){
				alarmprocessPopDto.setAlarmtype("侯客报警");	
			}
			if(alarmprocessPop.getAlarmtype().equals("1"))
				alarmprocessPopDto.setAlarmtype("行程报警");	
		}else{
			alarmprocessPopDto.setAlarmtype(alarmprocessPop.getAlarmtype());
		}
		alarmprocessPopDto.setTitle("待处理报警");

		RedisMessage redisMessage = new RedisMessage();
		redisMessage.setBusiness(RedisKeyEnum.MESSAGE_ALARMPROCESS.code);
		redisMessage.setOperation(RedisKeyEnum.MESSAGE_TYPE_MAKE.code);
		redisMessage.setNowTime(System.currentTimeMillis());
		redisMessage.setExTime(extime);
		redisMessage.setMessage(alarmprocessPopDto);
		redisMessage.setFunction(RedisKeyEnum.MESSAGE_FUNCTION_POPUP.code);

		return redisMessage;
	}

}
