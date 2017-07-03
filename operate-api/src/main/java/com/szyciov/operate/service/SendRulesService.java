package com.szyciov.operate.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.szyciov.enums.PayAccountStatusEnum;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.op.entity.OpPlatformInfo;
import com.szyciov.op.entity.PubSendRules;
import com.szyciov.op.entity.PubSendRulesHistory;
import com.szyciov.op.param.OpTaxiSendrulesQueryParam;
import com.szyciov.op.param.PubSendRulesHistoryQueryParam;
import com.szyciov.operate.dao.SendRulesDao;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PageBean;

@Service("sendRulesService")
public class SendRulesService {
	private SendRulesDao dao;

	@Resource(name = "SendRulesDao")
	public void setDao(SendRulesDao dao) {
		this.dao = dao;
	}
	
	public PageBean getSendRulesByQuery(OpTaxiSendrulesQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<PubSendRules> list = getSendRulesListByQuery(queryParam);
		int iTotalRecords = getSendRulesListCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);

		return pageBean;
	}
	
	public List<PubSendRules> getSendRulesListByQuery(OpTaxiSendrulesQueryParam queryParam) {
    	return dao.getSendRulesListByQuery(queryParam);
    }
	
	public int getSendRulesListCountByQuery(OpTaxiSendrulesQueryParam queryParam) {
		return dao.getSendRulesListCountByQuery(queryParam);
	}
	
	public Map<String, String> createSendRules(PubSendRules sendRules) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "保存成功");
		PubCityAddr city = dao.getCityById(sendRules.getCity());
		// 添加前检查数据是否已存在
		if (checkIsExist(sendRules) > 0) {
			
			ret.put("ResultSign", "Error");
			if(sendRules.getUseType()!=null && sendRules.getUseType().equals(1))
			    ret.put("MessageKey", "该城市已有网约车即刻用车的派单规则");
			else if(sendRules.getUseType()!=null && sendRules.getUseType().equals(0))
				ret.put("MessageKey", "该城市已有网约车预约用车的派单规则");
		} else {
			String uuid = GUIDGenerator.newGUID();
			sendRules.setId(uuid);
//			sendRules.setShortName(city.getFullNameInitials());
			sendRules.setPlatformType("0");//运管端
			sendRules.setVehicleType(0);//网约车
			sendRules.setRulesState("1");//初始为禁用
			sendRules.setStatus(1);
			
			PubSendRulesHistory sendRulesHistory=JSON.parseObject(JSON.toJSONString(sendRules), PubSendRulesHistory.class);
			sendRulesHistory.setOperateType("0");
			sendRulesHistory.setSendRulesId(uuid);
			sendRulesHistory.setOperator(sendRules.getCreater());
			dao.createSendRules(sendRules);
			dao.insertSendRulesHistory(sendRulesHistory);
		}
		return ret;
	}
	
	public int checkIsExist(PubSendRules sendRules) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("city", sendRules.getCity());
		paramMap.put("useType", sendRules.getUseType().toString());
		return dao.getSendRulesListCountByEqualQuery(paramMap);
	}
	
	public Map<String, String> updateSendRules(PubSendRules sendRules) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "操作成功");
 
		if("0".equals(sendRules.getRulesState()) ){//启用规则时判断账户
			OpPlatformInfo opPlatformInfo = dao.getOpPlatformInfo();
			   
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
		PubSendRules oldSendRules=dao.getSendRulesById(sendRules.getId());
		
		PubSendRulesHistory sendRulesHistory=JSON.parseObject(JSON.toJSONString(oldSendRules), PubSendRulesHistory.class);
		if(sendRules.getRulesState()!=null && sendRules.getRulesState().equals("0"))
			sendRulesHistory.setOperateType("2");
		if(sendRules.getRulesState()!=null && sendRules.getRulesState().equals("1"))
			sendRulesHistory.setOperateType("3");
		if(sendRules.getRulesState()==null)
			sendRulesHistory.setOperateType("1");
		
		sendRulesHistory.setSendRulesId(sendRules.getId());
		sendRulesHistory.setId(GUIDGenerator.newGUID());
		sendRulesHistory.setOperator(sendRules.getUpdater());
		
		//此处可不查重
		try {
			dao.updateSendRules(sendRules);
			dao.insertSendRulesHistory(sendRulesHistory);
		} catch (Exception e) {
			e.printStackTrace();
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "操作失败");
		}
		//}
		return ret;
	}
	
	public List<Map<String, Object>> getCityListById(String cityName) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("cityName", cityName);
		return dao.getCityListById(map);
	}
	
	public PubSendRules getSendRulesById(String id) {
		return dao.getSendRulesById(id);
	}
	
	public PubCityAddr getCityById(String id) {
		return dao.getCityById(id);
	}
	
	public List<PubCityAddr> getPubCityAddr() {
		return dao.getPubCityAddr();
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

	public PageBean getSendrulesHistoryByQuery(PubSendRulesHistoryQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		int recordCount = getSendRulesHistoryCountByQuery(queryParam);
		pageBean.setiTotalDisplayRecords(recordCount);
		pageBean.setiTotalRecords(recordCount);
		pageBean.setAaData(getSendRulesHistoryListByQuery(queryParam));
		return pageBean;
	}

	private List getSendRulesHistoryListByQuery(PubSendRulesHistoryQueryParam queryParam) {
		return dao.getSendRulesHistoryListByQuery(queryParam);
	}

	private int getSendRulesHistoryCountByQuery(PubSendRulesHistoryQueryParam queryParam) {
		return dao.getSendRulesHistoryCountByQuery(queryParam);
	}
}
