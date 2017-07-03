package com.szyciov.operate.service;

import com.szyciov.enums.PayAccountStatusEnum;
import com.szyciov.op.entity.OpPlatformInfo;
import com.szyciov.op.entity.OpTaxisendrules;
import com.szyciov.op.entity.OpTaxisendrulesHistory;
import com.szyciov.op.entity.OpUser;
import com.szyciov.op.param.OpTaxiSendrulesHistoryQueryParam;
import com.szyciov.op.param.OpTaxiSendrulesQueryParam;
import com.szyciov.operate.dao.OpInformationSetDao;
import com.szyciov.operate.dao.TaxiSendrulesDao;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PageBean;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("TaxiSendrulesService")
public class TaxiSendrulesService {
	
	private TaxiSendrulesDao dao;
	@Resource(name = "TaxiSendrulesDao")
	public void setDao(TaxiSendrulesDao dao) {
		this.dao = dao;
	}
	
	private OpInformationSetDao informationSetDao;
	@Resource(name = "OpInformationSetDao")
	public void setInformationSetDao(OpInformationSetDao informationSetDao) {
		this.informationSetDao = informationSetDao;
	}
	
	/**
	 * 分页查询出租车派单规则
	 * @param queryParam
	 * @return
	 */
	public PageBean getTaxiSendrulesByQuery(OpTaxiSendrulesQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		int recordCount = getTaxiSendrulesCountByQuery(queryParam);
		pageBean.setiTotalDisplayRecords(recordCount);
		pageBean.setiTotalRecords(recordCount);
		pageBean.setAaData(getTaxiSendrulesListByQuery(queryParam));
		return pageBean;
	}
	
	/**
	 * 添加出租车派单规则
	 * @param object
	 * @return
	 */
	public Map<String, String> addOpTaxiSendrules(OpTaxisendrules object) {
		Map<String, String> ret = new HashMap<String, String>();
		
		//判断城市、用车类型是否唯一
		OpTaxisendrules taxisendrules = new OpTaxisendrules();
		taxisendrules.setCity(object.getCity());
		taxisendrules.setUsetype(object.getUsetype()); //0-预约用车,1-即刻用车
		List<OpTaxisendrules> taxisendrulesList = getOpTaxiSendrulesList(taxisendrules);
		if(null != taxisendrulesList && !taxisendrulesList.isEmpty()) {
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "保存失败");
			if("0".equals(object.getUsetype())) {
				ret.put("MessageKey", "该城市已有预约用车的派单规则");
			} else if("1".equals(object.getUsetype())) {
				ret.put("MessageKey", "该城市已有即刻用车的派单规则");
			}
			return ret;
		}
		
		//司机推送数量不大于当前司机数
		if(("1".equals(object.getSendtype()) || "2".equals(object.getSendtype())) && "1".equals(object.getPushnumlimit())) {
			int driverCount = getTaxiDriverCount();
			if(object.getPushnum() > driverCount) {
				ret.put("ResultSign", "Error");
				ret.put("MessageKey", "推送数量不可超过" + driverCount + "人次");
				return ret;
			}
		}
		
		//检验平台是否有已启用收款账户
		OpPlatformInfo platformInfo = informationSetDao.getOpPlatformInfo();
		if(getPayAccountStatusUser(platformInfo) && getPayAccountStatusDriver(platformInfo)) {
			object.setRulesstate("0");
			ret.put("MessageKey", "保存成功");
		} else{
			object.setRulesstate("1");
			if(!getPayAccountStatusUser(platformInfo) && getPayAccountStatusDriver(platformInfo)){
				ret.put("MessageKey", "启用失败，乘客交易账户当前没有启用的收款账户，设置后才可启用");
			}else if(getPayAccountStatusUser(platformInfo) && !getPayAccountStatusDriver(platformInfo)){
				ret.put("MessageKey", "启用失败，司机交易账户当前没有启用的收款账户，设置后才可启用");
			}else{
				ret.put("MessageKey", "启用失败，乘客和司机交易账户当前没有启用的收款账户，设置后才可启用");
			}
		}
		
		//添加出租车派单规则数据
		object.setId(GUIDGenerator.newGUID());
		Map<String, String> cityMap = getCityShortname(object.getCity());
		if(null != cityMap && cityMap.containsKey("shortname")) {
			object.setShortname(cityMap.get("shortname"));
		}
		insertOpTaxiSendrules(object);

		ret.put("ResultSign", "Successful");
		return ret;
	}
	
	/**
	 * 修改出租车派单规则
	 * @param object
	 * @return
	 */
	public Map<String, String> editOpTaxiSendrules(OpTaxisendrules object) {
		Map<String, String> ret = new HashMap<String, String>();
		
		//判断城市、用车类型是否唯一
		OpTaxisendrules taxisendrules = new OpTaxisendrules();
		taxisendrules.setCity(object.getCity());
		taxisendrules.setUsetype(object.getUsetype()); //0-预约用车,1-即刻用车
		List<OpTaxisendrules> taxisendrulesList = getOpTaxiSendrulesList(taxisendrules);
		if(null != taxisendrulesList && !taxisendrulesList.isEmpty() && !taxisendrulesList.get(0).getId().equals(object.getId())) {
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "保存失败");
			if("0".equals(object.getUsetype())) {
				ret.put("MessageKey", "该城市已有预约用车的派单规则");
			} else if("1".equals(object.getUsetype())) {
				ret.put("MessageKey", "该城市已有即刻用车的派单规则");
			}
			return ret;
		}
		
		//司机推送数量不大于当前司机数
		if(("1".equals(object.getSendtype()) || "2".equals(object.getSendtype())) && "1".equals(object.getPushnumlimit())) {
			int driverCount = getTaxiDriverCount();
			if(object.getPushnum() > driverCount) {
				ret.put("ResultSign", "Error");
				ret.put("MessageKey", "推送数量不可超过" + driverCount + "人次");
				return ret;
			}
		}
		
		//添加出租车派单规则历史数据
		addTaxiSendrulesHistory(object.getId(), "1");
		
		//修改出租车派单规则数据
		updateOpTaxiSendrules(object);
		
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "保存成功");
		return ret;
	}
	
	/**
	 * 出租车派单规则启用、禁用(0-启用，1-禁用)
	 * @param object
	 * @return
	 */
	public Map<String, String> updateTaxiSendrulesState(OpTaxisendrules object) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		
		String rulestate = object.getRulesstate();
		//启用状态需要判断运管平台是否有启用的收款账户
		if("0".equals(rulestate) ){
			OpPlatformInfo opPlatformInfo = informationSetDao.getOpPlatformInfo();
			if(!getPayAccountStatusUser(opPlatformInfo) && !getPayAccountStatusDriver(opPlatformInfo)) {
				ret.put("ResultSign", "Error");
				ret.put("MessageKey", "启用失败，乘客和司机交易账户当前没有启用的收款账户，设置后才可启用");
				return ret;
			}
			if(!getPayAccountStatusUser(opPlatformInfo) && getPayAccountStatusDriver(opPlatformInfo)) {
				ret.put("ResultSign", "Error");
				ret.put("MessageKey", "启用失败，乘客交易账户当前没有启用的收款账户，设置后才可启用");
				return ret;
			}
			if(getPayAccountStatusUser(opPlatformInfo) && !getPayAccountStatusDriver(opPlatformInfo)) {
				ret.put("ResultSign", "Error");
				ret.put("MessageKey", "启用失败，司机交易账户当前没有启用的收款账户，设置后才可启用");
				return ret;
			}
		}
		//添加出租车派单规则历史记录
		if("0".equals(rulestate)) {
			addTaxiSendrulesHistory(object.getId(), "2");
			ret.put("MessageKey", "启用成功");
		} else if("1".equals(rulestate)) {
			addTaxiSendrulesHistory(object.getId(), "3");
			ret.put("MessageKey", "禁用成功");
		}
		
		//修改出租车派单规则
		updateOpTaxiSendrules(object);
		
		return ret;
	}
	
	/**
	 * 添加出租车派单规则历史记录
	 * @param id
	 * @param operatetype
	 * @param operatetype(0-新增，1-修改，2-启用，3-禁用)
	 */
	public void addTaxiSendrulesHistory(String id, String operatetype) {
		OpTaxisendrules object = getTaxiSendrulesById(id);
		if(null != object) {
			OpTaxisendrulesHistory taxisendrulesHistory = new OpTaxisendrulesHistory();
			BeanUtils.copyProperties(object, taxisendrulesHistory);
			taxisendrulesHistory.setId(GUIDGenerator.newGUID());
			taxisendrulesHistory.setCreater(object.getUpdater());
			taxisendrulesHistory.setOperatetype(operatetype);
			taxisendrulesHistory.setOperator(object.getUpdater());
			taxisendrulesHistory.setTaxisendrulesid(object.getId());
			insertOpTaxisendrulesHistory(taxisendrulesHistory);
		}
	}
	
	/**
	 * 分页查询出租车派单规则
	 * @param queryParam
	 * @return
	 */
	public PageBean getTaxiSendrulesHistoryByQuery(OpTaxiSendrulesHistoryQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		int recordCount = getTaxiSendrulesHistoryCountByQuery(queryParam);
		pageBean.setiTotalDisplayRecords(recordCount);
		pageBean.setiTotalRecords(recordCount);
		pageBean.setAaData(getTaxiSendrulesHistoryListByQuery(queryParam));
		return pageBean;
	}
	
	/**
	 * 查询运管端乘客收款账户状态
	 * @return
	 */
	private boolean getPayAccountStatusUser(OpPlatformInfo platformInfo) {
		if (null != platformInfo && (PayAccountStatusEnum.DREDGE.code.equals(platformInfo.getAlipaystatus())
				|| PayAccountStatusEnum.DREDGE.code.equals(platformInfo.getWechatstatus()))) {
			return true;
		}
		return false;
	}
	/**
	 * 查询运管端司机收款账户状态
	 * @return
	 */
	private boolean getPayAccountStatusDriver(OpPlatformInfo platformInfo) {
		if (null != platformInfo && (PayAccountStatusEnum.DREDGE.code.equals(platformInfo.getDriveralipaystatus())
				|| PayAccountStatusEnum.DREDGE.code.equals(platformInfo.getDriverwechatstatus()))) {
			return true;
		}
		return false;
	}
	public List<Map<String, Object>> getTaxiSendrulesListByQuery(OpTaxiSendrulesQueryParam queryParam) {
		return dao.getTaxiSendrulesListByQuery(queryParam);
	}
	
	public int getTaxiSendrulesCountByQuery(OpTaxiSendrulesQueryParam queryParam) {
		return dao.getTaxiSendrulesCountByQuery(queryParam);
	}
	
	public List<Map<String, String>> getTaxiSendrulesCityBySelect(Map<String, String> params) {
		return dao.getTaxiSendrulesCityBySelect(params);
	}
	
	public OpTaxisendrules getTaxiSendrulesById(String id) {
		return dao.getTaxiSendrulesById(id);
	}
	
	public int getTaxiDriverCount() {
		return dao.getTaxiDriverCount();
	}
	
	public void insertOpTaxiSendrules(OpTaxisendrules object) {
		dao.insertOpTaxiSendrules(object);
	}
	
	public void updateOpTaxiSendrules(OpTaxisendrules object) {
		dao.updateOpTaxiSendrules(object);
	}
	
	public Map<String, String> getCityShortname(String cityid) {
		return dao.getCityShortname(cityid);
	}
	
	public void insertOpTaxisendrulesHistory(OpTaxisendrulesHistory object) {
		dao.insertOpTaxisendrulesHistory(object);
	}
	
	public List<OpTaxisendrules> getOpTaxiSendrulesList(OpTaxisendrules object) {
		return dao.getOpTaxiSendrulesList(object);
	}
	
	public List<Map<String, Object>> getTaxiSendrulesHistoryListByQuery(OpTaxiSendrulesHistoryQueryParam queryParam) {
		return dao.getTaxiSendrulesHistoryListByQuery(queryParam);
	}
	
	public int getTaxiSendrulesHistoryCountByQuery(OpTaxiSendrulesHistoryQueryParam queryParam) {
		return dao.getTaxiSendrulesHistoryCountByQuery(queryParam);
	}
	
	public int getOpSendmodelCountByUser(OpUser object) {
		return dao.getOpSendmodelCountByUser(object);
	}
}
