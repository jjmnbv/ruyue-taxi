package com.szyciov.operate.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.szyciov.dto.driverVehicleBindManage.BindDto;
import com.szyciov.dto.driverVehicleBindManage.VehicleBindInfoDto;
import com.szyciov.entity.PubDriverVehicleBind;
import com.szyciov.entity.PubDriverVehicleRef;
import com.szyciov.entity.UserNews;
import com.szyciov.enums.BindingStateEnum;
import com.szyciov.enums.DriverEnum;
import com.szyciov.enums.MessageTypeEnum;
import com.szyciov.enums.WindowVisualEnum;
import com.szyciov.message.DriverVehicleBindMessage;
import com.szyciov.op.param.PubDriverVehicleBindQueryParam;
import com.szyciov.op.param.PubDriverVehicleRefQueryParam;
import com.szyciov.operate.dao.PubDriverVehicleRefDao;
import com.szyciov.param.UserNewsParam;
import com.szyciov.passenger.util.MessageUtil;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PageBean;
import com.szyciov.util.TemplateHelper;

import net.sf.json.JSONObject;

/**
 * 人车绑定service
 */
@Service
public class PubDriverVehicleRefService {

    Logger logger = LoggerFactory.getLogger(PubDriverVehicleBindService.class);
    private TemplateHelper templateHelper = new TemplateHelper();

    @Autowired
    private PubDriverVehicleRefDao refDao;

    @Autowired
    private PubDriverVehicleBindService bindService;


    @Transactional
    public boolean bind(BindDto bindDto){

        PubDriverVehicleRef ref = new PubDriverVehicleRef();

        ref.setId(GUIDGenerator.newGUID());

        ref.setVehicleid(bindDto.getVehicleid());

        ref.setDriverid(bindDto.getDriverID());

        ref.setCreatetime(new Date());

        ref.setCreater(bindDto.getCreater());

        refDao.save(ref);

        //获取车辆绑定信息
        List<VehicleBindInfoDto> listBindInfo = this.listAllVehicleBindInfo(bindDto.getVehicleid());

        int count = 0;
        String infos = "/";
        if(listBindInfo==null || listBindInfo.size()==0){
            logger.warn("------------未获取到车辆绑定司机信息-----------bindDto：{}", JSONObject.fromObject(bindDto));
        }else{
            count = listBindInfo.size();
            infos = this.getVehicleBindInfos(listBindInfo);
        }

        //添加至绑定信息表
        bindService.saveBind(bindDto, count, infos);

        //更新车辆信息



        return true;
    }



    /**
     * 返回车辆待接班司机信息
     * @param vehicleid     车辆ID
     * @param driverId      当班司机ID
     * @return
     */
    public String getVehicleBindInfos(String vehicleid,String driverId){

        List<VehicleBindInfoDto> listBindInfo = this.listVehicleBindInfo(vehicleid,driverId);

        if(listBindInfo !=null && listBindInfo.size()>0){

            return  this.getVehicleBindInfos(listBindInfo);

        }

        return "/";
    }

    /**
     * 返回车辆待接班信息集合
     * @param vehicleid   车辆ID
     * @param driverId    当班司机ID
     * @return
     */
    public List<VehicleBindInfoDto> listVehicleBindInfo(String vehicleid,String driverId){
        return refDao.listVehicleBindInfo(vehicleid,driverId);
    }


    /**
     * 返回车辆所有绑定司机信息
     * @param vehicleid
     * @return
     */
    public String getAllVehicleBindInfos(String vehicleid){

        List<VehicleBindInfoDto> listBindInfo = this.listAllVehicleBindInfo(vehicleid);

        if(listBindInfo !=null && listBindInfo.size()>0){

            return  this.getVehicleBindInfos(listBindInfo);

        }

        return "/";
    }




    /**
     * 拼接车辆绑定的司机信息
     * @param listDto
     * @return
     */
    private String getVehicleBindInfos(List<VehicleBindInfoDto> listDto){

        StringBuffer sb = new StringBuffer();
        int i =0;
        for(VehicleBindInfoDto dto:listDto){
            sb.append(dto.toString());
            if(++i<listDto.size()){
                sb.append("、");
            }
        }
        return sb.toString();
    }



    /**
     * 返回车辆所有绑定信息集合
     * @param vehicleid   车辆ID
     * @return
     */
    public List<VehicleBindInfoDto> listAllVehicleBindInfo(String vehicleid){
        return refDao.listVehicleBindInfo(vehicleid,null);
    }

    /**
     * 判断是否绑定
     * @param driverId          司机ID
     * @param vehicleId         车辆ID
     * @return
     */
    public boolean isBind(String driverId,String vehicleId){
        //返回绑定数量
        int count = refDao.getRefCount(driverId,vehicleId);
        //如果为1  则为已绑定，否则为未绑定
        if(count==1){
            return true;
        }else{
            return false;
        }
    }
    
    
    
    
    public List<Map<String, Object>> getDriverByNameOrPhone(String vehicletype, String driver, String jobstatus) {
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("vehicletype", vehicletype);
    	map.put("driver", driver);
    	map.put("jobstatus", jobstatus);
    	return refDao.getDriverByNameOrPhone(map);
    }
    
    public List<Map<String, Object>> getDriverByJobnum(String jobnum, String jobstatus) {
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("jobnum", jobnum);
    	map.put("jobstatus", jobstatus);
    	return refDao.getDriverByJobnum(map);
    }
    
    public PageBean getDriverInfoByQuery(PubDriverVehicleRefQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<Map<String, Object>> list = getDriverInfoListByQuery(queryParam);
		int iTotalRecords = getDriverInfoListCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);

		return pageBean;
	}

    public List<Map<String, Object>> getDriverInfoListByQuery(PubDriverVehicleRefQueryParam queryParam) {
    	return refDao.getDriverInfoListByQuery(queryParam);
    }
    
    public int getDriverInfoListCountByQuery(PubDriverVehicleRefQueryParam queryParam) {
    	return refDao.getDriverInfoListCountByQuery(queryParam);
    }
    
    public List<Map<String, Object>> getVehiclemodels() {
    	return refDao.getVehiclemodels();
    }
    
    public List<Map<String, Object>> getCityaddr() {
    	return refDao.getCityaddr();
    }
    
    public List<Map<String, Object>> getVehcbrand(String vehcbrandname) {
    	return refDao.getVehcbrand(vehcbrandname);
    }

    public List<Map<String, Object>> getVehcbrandByCity(String city, String vehcbrandname) {
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("city", city);
    	map.put("vehcbrandname", vehcbrandname);
    	return refDao.getVehcbrandByCity(map);
    }
    
    public PageBean getUnbandCarsByCity(PubDriverVehicleRefQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<Map<String, Object>> list = getUnbandCarsListByCity(queryParam);
		int iTotalRecords = getUnbandCarsListCountByCity(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);

		return pageBean;
	}

    public List<Map<String, Object>> getUnbandCarsListByCity(PubDriverVehicleRefQueryParam queryParam) {
    	return refDao.getUnbandCarsListByCity(queryParam);
    }
    
    public int getUnbandCarsListCountByCity(PubDriverVehicleRefQueryParam queryParam) {
    	return refDao.getUnbandCarsListCountByCity(queryParam);
    }
    
    @Transactional
    public Map<String, String> createPubDriverVehicleRef(PubDriverVehicleBind pubDriverVehicleBind) {
    	Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "绑定成功");
		
		// 查找司机信息
		Map<String, Object> driver = refDao.getDriverByDriverId(pubDriverVehicleBind.getDriverid());
		// 判断该司机是否在职
		if ("0".equals(String.valueOf(driver.get("jobstatus")))) {
			if ("0".equals(String.valueOf(driver.get("boundstate")))) {// 司机未绑定
				// 查找该车辆是否已被绑定，是否已具有服务车型
				Map<String, String> bind = refDao.getBoundStateByVehicleId(pubDriverVehicleBind.getVehicleid());
				if (bind == null) {
					ret.put("ResultSign", "Error");
					ret.put("MessageKey", "该车不存在");
				} else {
					// 执行绑定操作时，需检测车辆是否已具有服务车型
					if ("0".equals(bind.get("boundstate"))) {// 未绑定
						if (bind.get("Id") != null) {// 已具有服务车型
							if ("0".equals(bind.get("vehiclestatus"))) {// 营运中
								if (driver.get("city").equals(bind.get("city"))) {// 车辆和司机的登记城市相同
									
									PubDriverVehicleBind driverVehicleBind = new PubDriverVehicleBind();
									driverVehicleBind.setVehicleid(pubDriverVehicleBind.getVehicleid());
									driverVehicleBind.setDriverid(pubDriverVehicleBind.getDriverid());
									// 0-网约车，1-出租车
									driverVehicleBind.setDrivertype("0");
									// 0-未绑定（解绑），1-已绑定（绑定）
									driverVehicleBind.setBindstate("1");
									driverVehicleBind.setBindpersonnum(1);
									// 查找司机信息
									//Map<String, Object> driver = refDao.getDriverByDriverId(pubDriverVehicleBind.getDriverid());
									if (StringUtils.isEmpty((String)driver.get("name"))) {
										driverVehicleBind.setBinddirverinfo(driver.get("phone").toString());
									} else {
										driverVehicleBind.setBinddirverinfo(driver.get("name").toString() + " " + driver.get("phone").toString());
									}
									driverVehicleBind.setOperator(pubDriverVehicleBind.getUpdater());
									driverVehicleBind.setCreater(pubDriverVehicleBind.getUpdater());
									driverVehicleBind.setUpdater(pubDriverVehicleBind.getUpdater());

									// 修改车辆的绑定状态
									updateVehicleBindState(driverVehicleBind, pubDriverVehicleBind.getDriverid());

									// 修改司机的绑定状态
									updateDriverWorkStatus(driverVehicleBind, "2", "1");

									// 添加记录到司机车辆绑定关系表
									createDriverVehicleRef(driverVehicleBind);

									// 添加记录到司机车辆绑定记录表
									createPubDriverVehicleBind(driverVehicleBind);
									
									// 向司机发送消息
									sendBindNews(bind.get("plateno").toString(), pubDriverVehicleBind.getDriverid());
									
									// 向司机推送消息
									sendBindMessage(driver, bind.get("plateno").toString());
								} else {
									ret.put("ResultSign", "Error");
									ret.put("MessageKey", "车辆与司机登记城市不一致，不可绑定");
								}
							} else {// 维修中
								ret.put("ResultSign", "Error");
								ret.put("MessageKey", "当前车辆不可用，请选择其他车辆");
							}
						} else {
							ret.put("ResultSign", "Error");
							ret.put("MessageKey", "车辆所属车系没有对应服务车型，请分配后再绑定");
						}
					} else {// 已绑定
						ret.put("ResultSign", "Error");
						ret.put("MessageKey", "当前车辆不可用，请选择其他车辆");
					}
				}
			} else {
				ret.put("ResultSign", "Error");
				ret.put("MessageKey", "该司机已被绑定");
			}
		} else {
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "该司机已离职");
		}		

		return ret;
    }
    
    public void sendBindNews(String plateno, String userId) {
		Map<String, Object> opPlatformInfo = refDao.getOpPlatformInfo();
		String tel = "";
		if (opPlatformInfo != null && opPlatformInfo.get("servcietel") != null) {
			tel = opPlatformInfo.get("servcietel").toString();
		}
		StringBuffer content = new StringBuffer();
		content.append("客服已为您绑定车辆");
		content.append(plateno);
		content.append("。如有疑问，请联系");
		content.append(tel);
		content.append("。");

		UserNewsParam userNewsParam = new UserNewsParam();
		userNewsParam.setUserNewsTbName(UserNewsParam.DRIVER_USERNEWS_TABNAME);
		UserNews userNews = new UserNews();
		String uuid = GUIDGenerator.newGUID();
		userNews.setId(uuid);
		userNews.setUserid(userId);
		userNews.setType(userNews.USER_NEWS_TYPE_SYSTEM);
		JSONObject json = new JSONObject();
		json.put("type", MessageTypeEnum.BIND_SUCCESS.code);
		json.put("title", "绑定成功");
		json.put("content", content.toString());
		userNews.setContent(json.toString());
		userNews.setNewsstate(userNews.USER_NEWS_STATE_UNREAD);
		userNewsParam.setUserNews(userNews);
		createNews(userNewsParam);
	}
	
	/**
     * 推送绑定消息
     * @param driverinfo
     * @param plateno
     */
	private void sendBindMessage(Map<String, Object> driverinfo, String plateno) {
		String content = "您已和【" + plateno + "】绑定";
		// 发送短信及推送
		DriverVehicleBindMessage um = new DriverVehicleBindMessage(driverinfo.get("phone").toString(), content,
				BindingStateEnum.BINDING, WindowVisualEnum.TANCHUANG.msg);
		MessageUtil.sendMessage(um);
	}
	
	public void createNews(UserNewsParam userNewsParam) {
		templateHelper.dealRequestWithTokenCarserviceApiUrl("/UserNews/addUserNews", HttpMethod.POST, null,
				userNewsParam, JSONObject.class);
	}
    
    public void updateVehicleBindState(PubDriverVehicleBind pubDriverVehicleBind, String driverid) {
    	Map<String, Object> vehicleMap = new HashMap<String, Object>();
    	vehicleMap.put("id", pubDriverVehicleBind.getVehicleid());
    	// 0-未绑定,1-已绑定
    	vehicleMap.put("boundstate", pubDriverVehicleBind.getBindstate());
    	vehicleMap.put("bindpersonnum", pubDriverVehicleBind.getBindpersonnum());
    	vehicleMap.put("updater", pubDriverVehicleBind.getUpdater());
    	vehicleMap.put("driverid", driverid);
    	refDao.updateVehicleBindState(vehicleMap);
    }
    
    public void updateDriverWorkStatus(PubDriverVehicleBind pubDriverVehicleBind, String workStatus, String boundState) {
    	Map<String, String> driverMap = new HashMap<String, String>();
    	driverMap.put("id", pubDriverVehicleBind.getDriverid());
    	// 0-空闲，1-服务中，2-下线,3-未绑定
    	driverMap.put("workstatus", workStatus);
    	// 0-未绑定,1-已绑定
    	driverMap.put("boundstate", boundState);
    	driverMap.put("updater", pubDriverVehicleBind.getUpdater());
    	refDao.updateDriverWorkStatus(driverMap);
    }
    
    public void createDriverVehicleRef(PubDriverVehicleBind pubDriverVehicleBind) {
    	PubDriverVehicleRef pubDriverVehicleRef = new PubDriverVehicleRef();
    	pubDriverVehicleRef.setId(GUIDGenerator.newGUID());
    	pubDriverVehicleRef.setVehicleid(pubDriverVehicleBind.getVehicleid());
    	pubDriverVehicleRef.setDriverid(pubDriverVehicleBind.getDriverid());
    	pubDriverVehicleRef.setCreater(pubDriverVehicleBind.getCreater());
    	pubDriverVehicleRef.setUpdater(pubDriverVehicleBind.getUpdater());
    	refDao.createPubDriverVehicleRef(pubDriverVehicleRef);
    }
    
    public void createPubDriverVehicleBind(PubDriverVehicleBind pubDriverVehicleBind) {
    	PubDriverVehicleBind driverVehicleBind = new PubDriverVehicleBind();
    	driverVehicleBind.setId(GUIDGenerator.newGUID());
    	driverVehicleBind.setVehicleid(pubDriverVehicleBind.getVehicleid());
    	driverVehicleBind.setDriverid(pubDriverVehicleBind.getDriverid());
    	driverVehicleBind.setDrivertype(pubDriverVehicleBind.getDrivertype());
    	driverVehicleBind.setBindstate(pubDriverVehicleBind.getBindstate());
    	driverVehicleBind.setUnbindreason(pubDriverVehicleBind.getUnbindreason());
    	driverVehicleBind.setBindpersonnum(pubDriverVehicleBind.getBindpersonnum());
    	driverVehicleBind.setBinddirverinfo(pubDriverVehicleBind.getBinddirverinfo());
    	driverVehicleBind.setOperator(pubDriverVehicleBind.getUpdater());
    	driverVehicleBind.setCreater(pubDriverVehicleBind.getUpdater());
    	driverVehicleBind.setUpdater(pubDriverVehicleBind.getUpdater());
    	refDao.createPubDriverVehicleBind(driverVehicleBind);
    }
    
    public Map<String, String> judgeUncompleteOrder(String driverid, String vehicleid) {
    	Map<String, String> ret = new HashMap<String, String>();

		Map<String, String> map = new HashMap<String, String>();
		map.put("driverid", driverid);
		map.put("vehicleid", vehicleid);
		// 解绑时，需判断当前司机是否存在未完成订单。此处未完成订单包括：待出发、已出发、已抵达、服务中等状态的订单。
		int count = refDao.getUncompleteCountByDriverId(map);
		if (count == 0) {
			ret.put("ResultSign", "Successful");
			ret.put("MessageKey", "");
		} else {
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "该司机存在未完成订单，请在其完成订单后或将订单更换其他司机后再进行解绑");
		}
		return ret;
    }
    
    @Transactional
    public Map<String, String> updatePubDriverVehicleRef(PubDriverVehicleBind pubDriverVehicleBind) {
    	Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "解绑成功");
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("driverid", pubDriverVehicleBind.getDriverid());
		map.put("vehicleid", pubDriverVehicleBind.getVehicleid());
		// 解绑时，需判断当前司机是否存在未完成订单。此处未完成订单包括：待出发、已出发、已抵达、服务中等状态的订单。
		int count = refDao.getUncompleteCountByDriverId(map);
		if (count == 0) {
			
			PubDriverVehicleBind driverVehicleBind = new PubDriverVehicleBind();
			driverVehicleBind.setVehicleid(pubDriverVehicleBind.getVehicleid());
			driverVehicleBind.setDriverid(pubDriverVehicleBind.getDriverid());
			// 0-网约车，1-出租车
			driverVehicleBind.setDrivertype("0");
			// 0-未绑定（解绑），1-已绑定（绑定）
			driverVehicleBind.setBindstate("0");
			driverVehicleBind.setUnbindreason(pubDriverVehicleBind.getUnbindreason());
			driverVehicleBind.setBindpersonnum(0);
			driverVehicleBind.setBinddirverinfo(null);
			driverVehicleBind.setOperator(pubDriverVehicleBind.getUpdater());
			driverVehicleBind.setCreater(pubDriverVehicleBind.getUpdater());
			driverVehicleBind.setUpdater(pubDriverVehicleBind.getUpdater());

			// 查找司机信息
			Map<String, Object> driver = refDao.getDriverByDriverId(pubDriverVehicleBind.getDriverid());
			
			// 修改车辆的绑定状态
			updateVehicleBindState(driverVehicleBind, null);
			
			// 修改司机的绑定状态
			updateDriverWorkStatus(driverVehicleBind, "3", "0");
			
			// 修改司机车辆绑定关系表
			updateDriverVehicleRef(driverVehicleBind);
			
			// 添加记录到司机车辆绑定记录表
			createPubDriverVehicleBind(driverVehicleBind);
			
			// 获取车牌号
			Map<String, String> bind = refDao.getBoundStateByVehicleId(pubDriverVehicleBind.getVehicleid());
			// 向司机发送消息
			sendUnBindNews(bind.get("plateno").toString(), pubDriverVehicleBind.getDriverid());
			
			// 向司机推送消息
			sendUnbindMessage(driver, bind.get("plateno").toString());
		} else {
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "该司机存在未完成订单，请在其完成订单后或将订单更换其他司机后再进行解绑");
		}

		return ret;
    }
    
    public void sendUnBindNews(String plateno, String userId) {
		Map<String, Object> opPlatformInfo = refDao.getOpPlatformInfo();
		String tel = "";
		if (opPlatformInfo != null && opPlatformInfo.get("servcietel") != null) {
			tel = opPlatformInfo.get("servcietel").toString();
		}
		StringBuffer content = new StringBuffer();
		content.append("客服已解除您和车辆");
		content.append(plateno);
		content.append("的绑定。如有疑问，请联系");
		content.append(tel);
		content.append("。");

		UserNewsParam userNewsParam = new UserNewsParam();
		userNewsParam.setUserNewsTbName(UserNewsParam.DRIVER_USERNEWS_TABNAME);
		UserNews userNews = new UserNews();
		String uuid = GUIDGenerator.newGUID();
		userNews.setId(uuid);
		userNews.setUserid(userId);
		userNews.setType(userNews.USER_NEWS_TYPE_SYSTEM);
		JSONObject json = new JSONObject();
		json.put("type", MessageTypeEnum.UNBIND_SUCCESS.code);
		json.put("title", "解绑成功");
		json.put("content", content.toString());
		userNews.setContent(json.toString());
		userNews.setNewsstate(userNews.USER_NEWS_STATE_UNREAD);
		userNewsParam.setUserNews(userNews);
		createNews(userNewsParam);
	}
    
    /**
     * 发送解除绑定消息
     * @param driverinfo
     * @param plateno
     */
	private void sendUnbindMessage(Map<String, Object> driverinfo, String plateno) {
		String content = "";
		String type = "";
		// 如果为空闲状态
		if (DriverEnum.WORK_STATUS_LEISURE.code.equals(driverinfo.get("workstatus").toString())) {
			content = "您已和【" + plateno + "】解绑，请尽快完成车辆交接";
			type = WindowVisualEnum.FUCHUANG.code;
		} else if (DriverEnum.WORK_STATUS_OFFLINE.code.equals(driverinfo.get("workstatus").toString())) {
			content = "您已和【" + plateno + "】解绑";
			type = WindowVisualEnum.TANCHUANG.code;
		}
		// 发送短信及推送
		DriverVehicleBindMessage um = new DriverVehicleBindMessage(driverinfo.get("phone").toString(), content,
				BindingStateEnum.UN_BINDING, type);
		MessageUtil.sendMessage(um);
	}
    	
	public void updateDriverVehicleRef(PubDriverVehicleBind pubDriverVehicleBind) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("vehicleid", pubDriverVehicleBind.getVehicleid());
		map.put("driverid", pubDriverVehicleBind.getDriverid());
		map.put("updater", pubDriverVehicleBind.getUpdater());
		refDao.updatePubDriverVehicleRef(map);
	}
	
	
	public PageBean getDriverOpRecordByQuery(PubDriverVehicleBindQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<Map<String, Object>> list = getDriverOpRecordListByQuery(queryParam);
		int iTotalRecords = getDriverOpRecordListCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);

		return pageBean;
	}
	
	public List<Map<String, Object>> getDriverOpRecordListByQuery(PubDriverVehicleBindQueryParam queryParam) {
    	return refDao.getDriverOpRecordListByQuery(queryParam);
    }
    
    public int getDriverOpRecordListCountByQuery(PubDriverVehicleBindQueryParam queryParam) {
    	return refDao.getDriverOpRecordListCountByQuery(queryParam);
    }
    
    
    public PageBean getVehicleOpRecordByQuery(PubDriverVehicleBindQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<Map<String, Object>> list = getVehicleOpRecordListByQuery(queryParam);
		int iTotalRecords = getVehicleOpRecordListCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);

		return pageBean;
	}
    
    public List<Map<String, Object>> getVehicleOpRecordListByQuery(PubDriverVehicleBindQueryParam queryParam) {
    	return refDao.getVehicleOpRecordListByQuery(queryParam);
    }
    
    public int getVehicleOpRecordListCountByQuery(PubDriverVehicleBindQueryParam queryParam) {
    	return refDao.getVehicleOpRecordListCountByQuery(queryParam);
    }
    
    public List<Map<String, Object>> getPlatenoByPlateno(String plateno) {
    	return refDao.getPlatenoByPlateno(plateno);
    }
    
    public List<Map<String, Object>> getVehicleVinByVin(String vin) {
    	return refDao.getVehicleVinByVin(vin);
    }
		
    
}