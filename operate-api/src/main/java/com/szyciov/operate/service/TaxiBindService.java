package com.szyciov.operate.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.szyciov.dto.driverShiftManagent.ProcessedSaveDto;
import com.szyciov.entity.PeDrivershiftPending;
import com.szyciov.entity.PeDrivershiftProcessed;
import com.szyciov.entity.PubDriverVehicleBind;
import com.szyciov.entity.PubDriverVehicleRef;
import com.szyciov.entity.Retcode;
import com.szyciov.entity.UserNews;
import com.szyciov.enums.BindingStateEnum;
import com.szyciov.enums.DriverEnum;
import com.szyciov.enums.MessageTypeEnum;
import com.szyciov.enums.PeDrivershiftEnum;
import com.szyciov.enums.WindowVisualEnum;
import com.szyciov.message.DriverVehicleBindMessage;
import com.szyciov.message.ProcessedMessage;
import com.szyciov.op.entity.OpTaxiBind;
import com.szyciov.op.param.PubDriverVehicleRefQueryParam;
import com.szyciov.op.param.TaxiBindQueryParam;
import com.szyciov.operate.dao.TaxiBindDao;
import com.szyciov.param.UserNewsParam;
import com.szyciov.passenger.util.MessageUtil;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PageBean;
import com.szyciov.util.PushObjFactory;
import com.szyciov.util.SMSTempPropertyConfigurer;
import com.szyciov.util.TemplateHelper;

import net.sf.json.JSONObject;

@Service("TaxiBindService")
public class TaxiBindService {
	private TaxiBindDao dao;
	private TemplateHelper templateHelper = new TemplateHelper();
	
	@Resource(name = "TaxiBindDao")
	public void setDao(TaxiBindDao dao) {
		this.dao = dao;
	}
	
	public PageBean getVehicleInfoByQuery(TaxiBindQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<Map<String, Object>> list = getVehicleInfoListByQuery(queryParam);
		int iTotalRecords = getVehicleInfoListCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);

		return pageBean;
	}
	
	public List<Map<String, Object>> getVehicleInfoListByQuery(TaxiBindQueryParam queryParam) {
    	return dao.getVehicleInfoListByQuery(queryParam);
    }
	
    public int getVehicleInfoListCountByQuery(TaxiBindQueryParam queryParam) {
    	return dao.getVehicleInfoListCountByQuery(queryParam);
    }
	
    public List<Map<String, Object>> getCityaddr() {
    	return dao.getCityaddr();
    }
	
    public List<Map<String, Object>> getOndutyDriver(String driver) {
    	return dao.getOndutyDriver(driver);
    }
	
    public List<Map<String, Object>> getVehcbrandVehcline(String vehcbrandname) {
    	return dao.getVehcbrandVehcline(vehcbrandname);
    }

    public List<Map<String, Object>> getDriverByNameOrPhone(String city, String driver) {
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("city", city);
    	map.put("driver", driver);
    	return dao.getDriverByNameOrPhone(map);
    }
	
	public List<Map<String, Object>> getDriverByJobnum(String city, String jobnum) {
		Map<String, String> map = new HashMap<String, String>();
    	map.put("city", city);
    	map.put("jobnum", jobnum);
		return dao.getDriverByJobnum(map);
	}
	
	public PageBean getUnbindDriverByQuery(PubDriverVehicleRefQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<Map<String, Object>> list = getUnbindDriverListByQuery(queryParam);
		int iTotalRecords = getUnbindDriverListCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);

		return pageBean;
	}
	
	public List<Map<String, Object>> getUnbindDriverListByQuery(PubDriverVehicleRefQueryParam queryParam) {
		return dao.getUnbindDriverListByQuery(queryParam);
	}
	
	public int getUnbindDriverListCountByQuery(PubDriverVehicleRefQueryParam queryParam) {
		return dao.getUnbindDriverListCountByQuery(queryParam);
	}
    
	@Transactional
	public Map<String, String> createPubDriverVehicleRef(OpTaxiBind opTaxiBind) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "绑定成功");
		try {
			
			List<String> driverids = opTaxiBind.getDriverids();
			for (String driverid : driverids) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("vehicleid", opTaxiBind.getVehicleid());
				map.put("driverid", driverid);
				
				Map<String, Object> vehicleinfo = dao.getVehicleByVehicleId(opTaxiBind.getVehicleid());
				if ("1".equals(String.valueOf(vehicleinfo.get("vehiclestatus")))) {//0-营运中,1-维修中
					ret.put("ResultSign", "Error");
					ret.put("MessageKey", "当前车辆不可用，请选择其他车辆");
				} else {
					
					// 执行绑定时，判断该司机是否已被绑定
					//int count = dao.checkBindstate(map);
					Map<String, Object> driverinfo = dao.getDriverByDriverId(driverid);	
					
					// 判断该司机是否在职
					if ("0".equals(String.valueOf(driverinfo.get("jobstatus")))) {//0-在职
						
						if (driverinfo.get("boundstate") != null && "0".equals(String.valueOf(driverinfo.get("boundstate")))) {
							
							if (vehicleinfo.get("city").equals(driverinfo.get("city"))) {
								
								PubDriverVehicleBind driverVehicleBind = new PubDriverVehicleBind();
								driverVehicleBind.setVehicleid(opTaxiBind.getVehicleid());
								driverVehicleBind.setDriverid(driverid);
								// 0-网约车，1-出租车
								driverVehicleBind.setDrivertype("1");
								// 0-未绑定（解绑），1-已绑定（绑定）
								driverVehicleBind.setBindstate("1");
								//driverVehicleBind.setBindpersonnum(1);

								driverVehicleBind.setOperator(opTaxiBind.getUpdater());
								driverVehicleBind.setCreater(opTaxiBind.getUpdater());
								driverVehicleBind.setUpdater(opTaxiBind.getUpdater());

								// 修改车辆的绑定状态
								updateVehicleBindState(driverVehicleBind, 1);

								// 修改司机的绑定状态   --如果无当班司机 给0，如果有对班司机，给2（'0-无对班,1-当班,2-歇班,3-交班中，4-接班中'）
								updateDriverWorkStatus(driverVehicleBind, "2", "1", "0");

								// 添加记录到司机车辆绑定关系表
								createDriverVehicleRef(driverVehicleBind);
								
								// 查找司机信息
								String driverInfo = getDriverInfo(opTaxiBind.getVehicleid());
								driverVehicleBind.setBinddirverinfo(driverInfo);

								// 添加记录到司机车辆绑定记录表
								createPubDriverVehicleBind(driverVehicleBind);
								
								// 第一个绑定的司机id置为当班司机id
								List<Map<String, Object>> driversList = dao.getDriverByVehicleid(opTaxiBind.getVehicleid());
								if (driversList != null && driversList.size() == 1) {
									updateDriverid(driverVehicleBind, driverid);
								}
								
								// 改变绑定司机的交接班状态
								bindUpdateDriverWorkStatus(driversList, opTaxiBind, driverVehicleBind, driverid);
								
								// 向司机发送消息
								sendBindNews(opTaxiBind.getPlateno(), driverid);
								
								// 向司机推送消息
								sendBindMessage(driverinfo, opTaxiBind.getPlateno());
								
							} else {
								ret.put("ResultSign", "Error");
								ret.put("MessageKey", "司机与车辆登记城市不一致，不可绑定");
							}
						
						} else {
							ret.put("ResultSign", "Error");
							ret.put("MessageKey", "当前资源不可用，请选择其他司机");
						}
						
					} else {
						ret.put("ResultSign", "Error");
						ret.put("MessageKey", "该司机已离职");
					}
				}
			}
			
		} catch (Exception e) {
			ret.put("ResultSign", String.valueOf(Retcode.EXCEPTION.code));
			ret.put("MessageKey", "系统繁忙请稍后再试！");
        }
		
		return ret;
    }
	
	public void bindUpdateDriverWorkStatus(List<Map<String, Object>> driversList, OpTaxiBind opTaxiBind, PubDriverVehicleBind driverVehicleBind, String driverid) {
		DriverEnum driverEnum =  DriverEnum.PASS_WORK_STATUS_NO;
		// 如果仅有一条绑定关系，司机状态改为无对班
		if (driversList.size() == 1) {
			driverEnum =  DriverEnum.PASS_WORK_STATUS_NO;
		} else if (driversList.size() > 1) {
			Map<String, Object> driverinfo = getDriverStateByVehicleid(opTaxiBind.getVehicleid());
			// 如果司机状态为当班，则后续绑定司机状态为歇班
			if(DriverEnum.PASS_WORK_STATUS_ON.code.equals(driverinfo.get("passworkstatus")) || DriverEnum.PASS_WORK_STATUS_PENDING.code.equals(driverinfo.get("passworkstatus"))){
                driverEnum =  DriverEnum.PASS_WORK_STATUS_OFF;
            } else {// 未排班
            	// 如果不为当班，则后续绑定状态为未排班
                driverEnum =  DriverEnum.PASS_WORK_STATUS_NOPLAN;
                // 如果车辆对应司机状态不为未排班，则将该司机状态改为未排班
                if(!DriverEnum.PASS_WORK_STATUS_NOPLAN.code.equals(driverinfo.get("passworkstatus"))) {
                	driverVehicleBind.setDriverid(driverinfo.get("driverid").toString());
                	//修改司机相关状态
                    updateDriverWorkStatus(driverVehicleBind, null, null, driverEnum.code);
                }
            }
		}
		
		driverVehicleBind.setDriverid(driverid);
		updateDriverWorkStatus(driverVehicleBind, null, null, driverEnum.code);
	}
	
	public Map<String, Object> getDriverStateByVehicleid(String vehicleId) {
		// 获取车辆绑定的司机的状态
		Map<String, String> checkmap = new HashMap<String, String>();
		checkmap.put("vehicleid", vehicleId);
		
		Map<String, Object> bindStateMap = dao.getBindDriverStateByVehicleid(checkmap);
		return bindStateMap;
	}

	public String getDriverInfo(String vehicleid) {
		StringBuffer driverInfo = new StringBuffer();
		// 查找已绑定司机信息
		List<Map<String, Object>> driversList = dao.getDriverByVehicleid(vehicleid);
		if (driversList != null && driversList.size() > 0) {
			for (int i = 0;i < driversList.size();i++) {
				if (i == 0) {
					if (driversList.get(i).get("name") == null || driversList.get(i).get("name") == "") {
						driverInfo.append(driversList.get(i).get("phone"));
					} else {
						driverInfo.append(driversList.get(i).get("name"));
						driverInfo.append(" ");
						driverInfo.append(driversList.get(i).get("phone"));
					}
				} else {
					if (driversList.get(i).get("name") == null || driversList.get(i).get("name") == "") {
						driverInfo.append("、");
						driverInfo.append(driversList.get(i).get("phone"));
					} else {
						driverInfo.append("、");
						driverInfo.append(driversList.get(i).get("name"));
						driverInfo.append(" ");
						driverInfo.append(driversList.get(i).get("phone"));
					}
				}
			}
		} else {
			return null;
		}
		return driverInfo.toString();
	}
	
	public void sendBindNews(String plateno, String userId) {
		Map<String, Object> opPlatformInfo = dao.getOpPlatformInfo();
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
    
    public void updateVehicleBindState(PubDriverVehicleBind pubDriverVehicleBind, int count) {
    	Map<String, Object> vehicleMap = new HashMap<String, Object>();
    	vehicleMap.put("id", pubDriverVehicleBind.getVehicleid());
    	// 0-未绑定,1-已绑定
    	vehicleMap.put("boundstate", pubDriverVehicleBind.getBindstate());
    	//vehicleMap.put("bindpersonnum", pubDriverVehicleBind.getBindpersonnum());
    	vehicleMap.put("updater", pubDriverVehicleBind.getUpdater());
    	vehicleMap.put("count", count);
    	dao.updateVehicleBindState(vehicleMap);
    }
    
    public void updateDriverWorkStatus(PubDriverVehicleBind pubDriverVehicleBind, String workStatus, String boundState, String passWorkStatus) {
    	Map<String, Object> driverMap = new HashMap<String, Object>();
    	driverMap.put("id", pubDriverVehicleBind.getDriverid());
    	// 0-空闲，1-服务中，2-下线,3-未绑定
    	driverMap.put("workstatus", workStatus);
    	// 0-未绑定,1-已绑定
    	driverMap.put("boundstate", boundState);
    	driverMap.put("updater", pubDriverVehicleBind.getUpdater());
    	// 0-无对班,1-当班,2-歇班,3-交班中，4-接班中
    	driverMap.put("passworkstatus", passWorkStatus);
    	dao.updateDriverWorkStatus(driverMap);
    }
    
    public void createDriverVehicleRef(PubDriverVehicleBind pubDriverVehicleBind) {
    	PubDriverVehicleRef pubDriverVehicleRef = new PubDriverVehicleRef();
    	pubDriverVehicleRef.setId(GUIDGenerator.newGUID());
    	pubDriverVehicleRef.setVehicleid(pubDriverVehicleBind.getVehicleid());
    	pubDriverVehicleRef.setDriverid(pubDriverVehicleBind.getDriverid());
    	pubDriverVehicleRef.setCreater(pubDriverVehicleBind.getCreater());
    	pubDriverVehicleRef.setUpdater(pubDriverVehicleBind.getUpdater());
    	dao.createPubDriverVehicleRef(pubDriverVehicleRef);
    }
    
    public void createPubDriverVehicleBind(PubDriverVehicleBind pubDriverVehicleBind) {
    	PubDriverVehicleBind driverVehicleBind = new PubDriverVehicleBind();
    	driverVehicleBind.setId(GUIDGenerator.newGUID());
    	driverVehicleBind.setVehicleid(pubDriverVehicleBind.getVehicleid());
    	driverVehicleBind.setDriverid(pubDriverVehicleBind.getDriverid());
    	driverVehicleBind.setDrivertype(pubDriverVehicleBind.getDrivertype());
    	driverVehicleBind.setBindstate(pubDriverVehicleBind.getBindstate());
    	driverVehicleBind.setUnbindreason(pubDriverVehicleBind.getUnbindreason());
    	//driverVehicleBind.setBindpersonnum(pubDriverVehicleBind.getBindpersonnum());
    	driverVehicleBind.setBinddirverinfo(pubDriverVehicleBind.getBinddirverinfo());
    	driverVehicleBind.setOperator(pubDriverVehicleBind.getUpdater());
    	driverVehicleBind.setCreater(pubDriverVehicleBind.getUpdater());
    	driverVehicleBind.setUpdater(pubDriverVehicleBind.getUpdater());
    	driverVehicleBind.setUnbinddirverinfo(pubDriverVehicleBind.getUnbinddirverinfo());
    	driverVehicleBind.setUnbindpersonnum(pubDriverVehicleBind.getUnbindpersonnum());
    	dao.createPubDriverVehicleBind(driverVehicleBind);
    }
    
    public void updateDriverid(PubDriverVehicleBind pubDriverVehicleBind, String driverId) {
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("id", pubDriverVehicleBind.getVehicleid());
    	map.put("driverid", driverId);
    	map.put("updater", pubDriverVehicleBind.getUpdater());
    	dao.updateDriverid(map);
    }

  
    public PageBean getBindDriverByVehicleid(String vehicleid) {
    	PageBean pageBean = new PageBean();
		List<Map<String, Object>> list = dao.getBindDriverByVehicleid(vehicleid);
		int iTotalRecords = list.size();
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
    	
    	return pageBean;
    	//return dao.getBindDriverByVehicleid(vehicleid);
    }
    
    @Transactional
    public Map<String, String> updatePubDriverVehicleRef(OpTaxiBind opTaxiBind) {
    	Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "解绑成功");
		
        try {
        	
        	List<String> driverids = opTaxiBind.getDriverids();
			Map<String, Object> driverinfo = new HashMap<String, Object>();
			
			int bindcount = 0;
			String unbindDrivers = "";
			String uncompleteDrivers = "";
			// 判断所选某司机是否已被其他客服解绑   所选司机是否存在未完成订单
			for (String checkdriverid : driverids) {
				driverinfo = dao.getDriverByDriverId(checkdriverid);
				
				bindcount = checkBindStatus(opTaxiBind.getVehicleid(), checkdriverid);
				if (bindcount == 0) {
					unbindDrivers = unbindDrivers + driverinfo.get("name") + "、";
				}
				
				bindcount = getUncompleteCountByDriverId(opTaxiBind.getVehicleid(), checkdriverid);
				if (bindcount > 0) {
					uncompleteDrivers = uncompleteDrivers + driverinfo.get("name") + "、";
				}
			}
			
			if (StringUtils.isNotBlank(unbindDrivers)) {
				unbindDrivers = unbindDrivers.substring(0, unbindDrivers.length() - 1);
				ret.put("ResultSign", "Error");
				ret.put("MessageKey", "【" + unbindDrivers + "】已被其他客服解绑");
				return ret;
			}
        	
			if (StringUtils.isNotBlank(uncompleteDrivers)) {
				uncompleteDrivers = uncompleteDrivers.substring(0, uncompleteDrivers.length() - 1);
				ret.put("ResultSign", "Error");
				ret.put("MessageKey", "【" + uncompleteDrivers + "】存在未完成订单，无法解绑");
				return ret;
			}

        	Map<String, String> map = new HashMap<String, String>();
			map.put("vehicleid", opTaxiBind.getVehicleid());
			
			// 解绑人数
			int unbindcount = 0;

			// 查看绑定数
			int boundnum = dao.checkBindstate(map);
			//int boundnum = opTaxiBind.getDriverids().size();
			if (boundnum == 1) {
				List<Map<String, Object>> driversList = dao.getDriverByVehicleid(opTaxiBind.getVehicleid());
				if (driversList != null && driversList.size() > 0) {
					for (String driverid : driverids) {
						if (driverid.equals(driversList.get(0).get("id"))) {
							// 解绑时，需判断当前司机是否存在未完成订单。此处未完成订单包括：待出发、已出发、已抵达、服务中、待确费等状态的订单。
							Map<String, String> checkmap = new HashMap<String, String>();
							checkmap.put("vehicleid", opTaxiBind.getVehicleid());
							checkmap.put("driverid", driverid);
							// 执行解绑时，判断该司机是否已被绑定
							int count = dao.checkBindstate(checkmap);
							if (count > 0) {
								int uncompletecount = dao.getUncompleteCountByDriverId(checkmap);
								if (uncompletecount > 0) {
									ret.put("ResultSign", "Error");
									ret.put("MessageKey", "存在未完成订单，不可执行解绑操作");
								} else {
									// 获取司机信息
									driverinfo = dao.getDriverByDriverId(driverid);
									unbind(opTaxiBind, driverid);
									unbindcount++;
									unbindDrivers = unbindDrivers + driverinfo.get("name") + " " + driverinfo.get("phone") + "、";
									
									// 向司机发送消息
									sendUnBindNews(opTaxiBind.getPlateno(), driverid);
									// 向司机推送消息
									sendUnbindMessage(driverinfo, opTaxiBind.getPlateno());
									
									// 添加司机车辆解绑记录
									createUnbindInfo(opTaxiBind, driverid, unbindcount, unbindDrivers.substring(0, unbindDrivers.length() - 1));
								}
							}
							
							
							// 修改车辆绑定状态
						}
					}
				}
			} else if (boundnum >= 2) {
				boolean exist = false;
				boolean inSuccession = false;
				
				for (String driverid : driverids) {
					Map<String, String> checkmap = new HashMap<String, String>();
					checkmap.put("vehicleid", opTaxiBind.getVehicleid());
					checkmap.put("driverid", driverid);
					// 判断是否为当班司机
					Map<String, Object> bindStateMap = dao.getBindDriverStateByVehicleid(checkmap);
					if (bindStateMap != null && bindStateMap.get("passworkstatus") != null && ("1".equals(String.valueOf(bindStateMap.get("passworkstatus"))) || "3".equals(String.valueOf(bindStateMap.get("passworkstatus"))))) {
						// 当班司机
						exist = true;
						// 获取司机信息
						driverinfo = dao.getDriverByDriverId(driverid);
						break;
					}
				}
				
				if (exist) {
					ret.put("ResultSign", "Error");
					ret.put("MessageKey", "【" + driverinfo.get("name") + "】处于当班中，不能解绑，如需解绑，则需先执行交班或回收车辆");
				} else {
					for (String driverid : driverids) {
						Map<String, String> checkmap2 = new HashMap<String, String>();
						checkmap2.put("vehicleid", opTaxiBind.getVehicleid());
						checkmap2.put("driverid", driverid);
						// 执行解绑时，判断该司机是否已被绑定
						int count = dao.checkBindstate(checkmap2);
						if (count > 0) {
							// 获取司机信息
							driverinfo = dao.getDriverByDriverId(driverid);
							unbind(opTaxiBind, driverid);
							unbindcount++;
							unbindDrivers = unbindDrivers + driverinfo.get("name") + " " + driverinfo.get("phone") + "、";
							
							// 向司机发送消息
							sendUnBindNews(opTaxiBind.getPlateno(), driverid);
							// 向司机推送消息
							sendUnbindMessage(driverinfo, opTaxiBind.getPlateno());

							if (DriverEnum.PASS_WORK_STATUS_PROCESSIND.code.equals(driverinfo.get("passworkstatus").toString())) {
								inSuccession = true;
							}
						} 
					}
					
					if (unbindcount > 0) {
						// 添加司机车辆解绑记录
						createUnbindInfo(opTaxiBind, driverids.get(0), unbindcount, unbindDrivers.substring(0, unbindDrivers.length() - 1));
					}
				}
				
				// 解绑完后判断绑定司机数量是否大于1，如果只剩一个了，状态改为无对班
				int boundnum2 = dao.checkBindstate(map);
				
				Map<String, Object> bindStateMap = getDriverStateByVehicleid(opTaxiBind.getVehicleid());
				if (inSuccession) {// 解绑司机中存在“接班中”
					sendShiftFailedMessage(boundnum2, bindStateMap, opTaxiBind.getUpdater());
				}
				if (boundnum2 == 1) {
					//Map<String, String> checkmap3 = new HashMap<String, String>();
					//checkmap3.put("vehicleid", opTaxiBind.getVehicleid());
					// 判断是否为当班司机
					//Map<String, Object> bindStateMap = dao.getBindDriverStateByVehicleid(checkmap3);
					if (bindStateMap != null && bindStateMap.get("passworkstatus") != null && !"0".equals(String.valueOf(bindStateMap.get("passworkstatus")))) {
						//修改接班司机
				        PubDriverVehicleBind pubDriverVehicleBind = new PubDriverVehicleBind();
				        pubDriverVehicleBind.setDriverid(bindStateMap.get("driverid").toString());
				        pubDriverVehicleBind.setUpdater(opTaxiBind.getUpdater());
				        // 修改司机的绑定状态   0-无对班
				     	updateDriverWorkStatus(pubDriverVehicleBind, null, null, "0");
					}
				}

			}
			
			// 查看绑定数
			int boundnum3 = dao.checkBindstate(map);
			// 如果全部都解绑了，修改车辆绑定状态
			if (boundnum3 == 0) {
				Map<String, String> bindstatemap = new HashMap<String, String>();
				bindstatemap.put("id", opTaxiBind.getVehicleid());
				bindstatemap.put("boundstate", "0");
				bindstatemap.put("updater", opTaxiBind.getUpdater());
				
				dao.updateVehicleBindStateAllUnbind(bindstatemap);
			}
        } catch (Exception e) {
			ret.put("ResultSign", String.valueOf(Retcode.EXCEPTION.code));
			ret.put("MessageKey", "系统繁忙请稍后再试！");
        }

		return ret;
    }
    
    public int checkBindStatus(String vehicleid, String driverid) {
    	Map<String, String> checkmap = new HashMap<String, String>();
		checkmap.put("vehicleid", vehicleid);
		checkmap.put("driverid", driverid);
		// 判断该司机是否已被绑定
		int count = dao.checkBindstate(checkmap);
		return count;
    }
    
    public void unbind(OpTaxiBind opTaxiBind, String driverid) {
    	PubDriverVehicleBind driverVehicleBind = new PubDriverVehicleBind();
		driverVehicleBind.setVehicleid(opTaxiBind.getVehicleid());
		driverVehicleBind.setDriverid(driverid);
		// 0-网约车，1-出租车
		driverVehicleBind.setDrivertype("1");
		
		driverVehicleBind.setUnbindreason(opTaxiBind.getUnbindreason());
		//driverVehicleBind.setBindpersonnum(0);
		//driverVehicleBind.setBinddirverinfo(null);
		driverVehicleBind.setOperator(opTaxiBind.getUpdater());
		driverVehicleBind.setCreater(opTaxiBind.getUpdater());
		driverVehicleBind.setUpdater(opTaxiBind.getUpdater());

		// 修改车辆的绑定状态
		updateVehicleBindState(driverVehicleBind, -1);
		
		// 0-未绑定（解绑），1-已绑定（绑定）
		driverVehicleBind.setBindstate("0");
		// 修改司机的绑定状态
		updateDriverWorkStatus(driverVehicleBind, "3", "0", null);
		
		// 修改司机车辆绑定关系表
		updateDriverVehicleRef(driverVehicleBind);
		
		// 查找司机信息
		String driverInfo = getDriverInfo(opTaxiBind.getVehicleid());
		driverVehicleBind.setBinddirverinfo(driverInfo);
		
		// 添加记录到司机车辆绑定记录表
		//createPubDriverVehicleBind(driverVehicleBind);
		
		// 判断是否需要更改当班司机id
		List<Map<String, Object>> driversList = dao.getDriverByVehicleid(opTaxiBind.getVehicleid());
		if (driversList != null && driversList.size() > 0) {
			// 查找车辆表中当前的driverid
			Map<String, String> checkmap = new HashMap<String, String>();
			checkmap.put("vehicleid", opTaxiBind.getVehicleid());
			Map<String, Object> bindStateMap = dao.getBindDriverStateByVehicleid(checkmap);
			if (bindStateMap != null && bindStateMap.size() > 0) {
				if (bindStateMap.get("driverid").equals(driverid)) {
					updateDriverid(driverVehicleBind, driversList.get(0).get("id").toString());
				}
			}
		} else {
			updateDriverid(driverVehicleBind, null);
		}
		
    }
    
    public void createUnbindInfo(OpTaxiBind opTaxiBind, String driverid, int unbindcount, String unbindDrivers) {
    	PubDriverVehicleBind driverVehicleBind = new PubDriverVehicleBind();
		driverVehicleBind.setVehicleid(opTaxiBind.getVehicleid());
		driverVehicleBind.setDriverid(driverid);
		// 0-网约车，1-出租车
		driverVehicleBind.setDrivertype("1");
		
		driverVehicleBind.setUnbindreason(opTaxiBind.getUnbindreason());
		driverVehicleBind.setOperator(opTaxiBind.getUpdater());
		driverVehicleBind.setCreater(opTaxiBind.getUpdater());
		driverVehicleBind.setUpdater(opTaxiBind.getUpdater());

		// 0-未绑定（解绑），1-已绑定（绑定）
		driverVehicleBind.setBindstate("0");

		// 查找司机信息
		String driverInfo = getDriverInfo(opTaxiBind.getVehicleid());
		driverVehicleBind.setBinddirverinfo(driverInfo);
		
		driverVehicleBind.setUnbindpersonnum(unbindcount);
		driverVehicleBind.setUnbinddirverinfo(unbindDrivers);
		
		// 添加记录到司机车辆绑定记录表
		createPubDriverVehicleBind(driverVehicleBind);
    }
    
    public void sendUnBindNews(String plateno, String userId) {
		Map<String, Object> opPlatformInfo = dao.getOpPlatformInfo();
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
	
	/**
     * 向交班中司机发送接班失败消息
     * @param driverinfo
     * @param plateno
	 * @throws Exception 
     */
	private void sendShiftFailedMessage(int boundnum, Map<String, Object> bindStateMap, String updater) throws Exception {
		String content = "";
		String operation = "";
		if (boundnum >= 2) {
			content = "交班失败";
			operation = "field";
		} else {
			content = "您当前没有对班司机，请收工";
			operation = "off";
		}
		
		// 删除交班申请
		removeShfitApplyByDriver(bindStateMap.get("driverid").toString(), updater);
		
		// 发送短信及推送
		//UserMessage um = new UserMessage(userids, content, UserMessage.SHIFTON);
		DriverVehicleBindMessage um = new DriverVehicleBindMessage(bindStateMap.get("phone").toString(), content,
				PushObjFactory.HINT_SHIFT_DRIVER_UNBIND, operation);
		MessageUtil.sendMessage(um);
	}
	
	/**
	 * 根据司机ID删除交班申请
	 * @param driverId		交班司机ID
	 * @throws Exception
	 */
	@Transactional
	public void removeShfitApplyByDriver(String driverId, String updater) throws Exception {
		PeDrivershiftPending paramPend = new PeDrivershiftPending();
		paramPend.setDriverid(driverId);
		PeDrivershiftPending pending = dao.getPendingInfo(paramPend);
		this.removeShfitApply(pending, updater);
	}
	
	/**
	 * 删除待交接班申请 并更改司机状态
	 * @param pending
	 */
	private void removeShfitApply(PeDrivershiftPending pending, String updater) {
		// 删除待交班数据
		dao.removeById(pending.getId());
		// 重新将司机状态改为当班中
        PubDriverVehicleBind pubDriverVehicleBind = new PubDriverVehicleBind();
        pubDriverVehicleBind.setDriverid(pending.getDriverid());
        pubDriverVehicleBind.setUpdater(updater);
		updateDriverWorkStatus(pubDriverVehicleBind, null, null, DriverEnum.PASS_WORK_STATUS_ON.code);
	}

    public void updateDriverVehicleRef(PubDriverVehicleBind pubDriverVehicleBind) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("vehicleid", pubDriverVehicleBind.getVehicleid());
		map.put("driverid", pubDriverVehicleBind.getDriverid());
		map.put("updater", pubDriverVehicleBind.getUpdater());
		dao.updatePubDriverVehicleRef(map);
	}   
    
    public PageBean getOperateRecordByVehicleid(TaxiBindQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<Map<String, Object>> list = getOperateRecordListByVehicleid(queryParam);
		int iTotalRecords = getOperateRecordListCountByVehicleid(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);

		return pageBean;
	}
    
    public List<Map<String, Object>> getOperateRecordListByVehicleid(TaxiBindQueryParam queryParam) {
    	return dao.getOperateRecordListByVehicleid(queryParam);
    }
	
    public int getOperateRecordListCountByVehicleid(TaxiBindQueryParam queryParam) {
    	return dao.getOperateRecordListCountByVehicleid(queryParam);
    }
    
    
    public List<Map<String, Object>> listTaxiBindDriver(String vehicleid) {
    	// 查找处于上班状态的司机
    	List<Map<String, Object>> list = dao.listVehicleBindInfoOfOnline(vehicleid);
    	if (list == null || list.size() == 0) {
    		// 查找绑定的所有司机
    		list = dao.getDriverByVehicleid(vehicleid);
    	}
    	
    	return list;
    }
    
    /**
     * 保存指派当班
     * @param
     * @return
     */
    @Transactional
    public boolean saveAssign(ProcessedSaveDto saveDto) {

        PeDrivershiftProcessed processed = new PeDrivershiftProcessed();
        
        processed.setId(GUIDGenerator.newGUID());
        processed.setVehicleid(saveDto.getVehicleId());
        processed.setRelieveddriverid(saveDto.getRelieveddriverid());
        processed.setRelieveddriverinfo(saveDto.getRelieveddriverInfo());
        processed.setShifttype(PeDrivershiftEnum.SHIFT_TYPE_ASSIGN.code);
        processed.setRelievedtype(PeDrivershiftEnum.RELIVED_TYPE_MANPOWER.code);
        processed.setProcessperson(saveDto.getProcessperson());
        processed.setProcesspersonname(saveDto.getProcesspersonname());
        processed.setCreater(saveDto.getProcessperson());
        processed.setUpdater(saveDto.getProcessperson());
        
        //保存数据至 已处理表
        dao.createDrivershiftProcessed(processed);

        //修改接班司机 为当班状态
        PubDriverVehicleBind pubDriverVehicleBind = new PubDriverVehicleBind();
        pubDriverVehicleBind.setDriverid(saveDto.getRelieveddriverid());
        pubDriverVehicleBind.setUpdater(saveDto.getProcessperson());
        // 修改司机的绑定状态
     	updateDriverWorkStatus(pubDriverVehicleBind, null, null, "1");

     	pubDriverVehicleBind.setVehicleid(saveDto.getVehicleId());
        // 将其他的司机状态改为歇班
     	updateOtherDriverWorkStatus(pubDriverVehicleBind);
        //修改车辆 当班司机ID 为接班司机ID
     	updateDriverid(pubDriverVehicleBind, saveDto.getRelieveddriverid());
     	
     	// 给当班司机发送短信
     	sendProcessedMessage(saveDto.getRelieveddriverid());

        return true;

    }
    
    public void updateOtherDriverWorkStatus(PubDriverVehicleBind pubDriverVehicleBind) {
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("vehicleid", pubDriverVehicleBind.getVehicleid());
    	map.put("driverid", pubDriverVehicleBind.getDriverid());
    	map.put("updater", pubDriverVehicleBind.getUpdater());
    	// 其他的改为歇班 2-歇班
    	map.put("passworkstatus", "2");
    	dao.updateOtherDriverWorkStatus(map);
    }
    
    /**
     * 指定当班司机成功，发送消息
     * @param driverId
     */
    private void sendProcessedMessage(String driverId) {

    	// 获取被指派当班司机信息
		Map<String, Object> driverinfo = dao.getDriverByDriverId(driverId);
		// 获取平台信息
		Map<String, Object> opPlatformInfo = dao.getOpPlatformInfo();
		String tel = "";
		String companyshortname = "";
		if (opPlatformInfo != null && opPlatformInfo.get("servcietel") != null) {
			tel = opPlatformInfo.get("servcietel").toString();
		}
		if (opPlatformInfo != null && opPlatformInfo.get("companyshortname") != null) {
			companyshortname = opPlatformInfo.get("companyshortname").toString();
		}
		// 客服指派由您当班，请尽快打卡上班，努力加油哦！如有疑问，请联系{0}（平台公司简称）
		// 客服指派由您当班，请尽快打卡上班，努力加油哦！如有疑问，请联系${0}（${1}）
		String content = SMSTempPropertyConfigurer
				.getSMSTemplate("com.szyciov.operate.service.taxibindservice.processed", tel);
		// 发送短信
		ProcessedMessage um = new ProcessedMessage(driverinfo.get("phone").toString(), content,
				PeDrivershiftEnum.SHIFT_TYPE_PROCESSED.code, null);
		MessageUtil.sendMessage(um);
    }
    
    /**
	 * 判断是否需要指定当班
	 * @param vehicleId	车辆ID
	 * @return
	 */
	public boolean isAssign(String vehicleId) {
		
		Map<String, String> checkmap = new HashMap<String, String>();
		checkmap.put("vehicleid", vehicleId);
		// 判断是否为当班司机
		Map<String, Object> bindStateMap = dao.getBindDriverStateByVehicleid(checkmap);
		if (bindStateMap != null && (int)bindStateMap.get("bindpersonnum") >= 2) {
			if (StringUtils.isBlank(bindStateMap.get("passworkstatus").toString()) || (!"1".equals(String.valueOf(bindStateMap.get("passworkstatus"))) && !"3".equals(String.valueOf(bindStateMap.get("passworkstatus"))))) {
				return true;
			}
		}
		
		return false;
	}
    
	public List<Map<String, Object>> getBindDriversByVehicleid(String vehicleid) {
		return dao.getDriverByVehicleid(vehicleid);
	}
	
	public int getUncompleteCountByDriverId(String vehicleid, String driverid) {
		Map<String, String> checkmap = new HashMap<String, String>();
		checkmap.put("vehicleid", vehicleid);
		checkmap.put("driverid", driverid);
		int uncompletecount = dao.getUncompleteCountByDriverId(checkmap);
		return uncompletecount;
	}
	
}
