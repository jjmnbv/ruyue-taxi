package com.szyciov.lease.service;

import com.alibaba.fastjson.JSON;
import com.szyciov.enums.PayAccountStatusEnum;
import com.szyciov.lease.dao.LeLeasescompanyDao;
import com.szyciov.lease.dao.SendRulesDao;
import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.param.LeSendRulesQueryParam;
import com.szyciov.op.entity.PubSendRules;
import com.szyciov.op.entity.PubSendRulesHistory;
import com.szyciov.op.param.PubSendRulesHistoryQueryParam;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PageBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("sendRulesService")
public class SendRulesService {
	private SendRulesDao dao;

	@Resource(name = "SendRulesDao")
	public void setDao(SendRulesDao dao) {
		this.dao = dao;
	}
	
	private LeLeasescompanyDao leaseDao;
  
	@Resource(name = "LeLeasescompanyDao")
	public void setDao(LeLeasescompanyDao leaseDao) {
		this.leaseDao = leaseDao;
	}
	
	public PageBean getSendRulesByQuery(LeSendRulesQueryParam queryParam) {
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
	
	public List<PubSendRules> getSendRulesListByQuery(LeSendRulesQueryParam leSendRulesQueryParam) {
    	return dao.getSendRulesListByQuery(leSendRulesQueryParam);
    }
	
	public int getSendRulesListCountByQuery(LeSendRulesQueryParam leSendRulesQueryParam) {
		return dao.getSendRulesListCountByQuery(leSendRulesQueryParam);
	}
	
	public Map<String, String> createSendRules(PubSendRules pubSendRules) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "保存成功");
		// 添加前检查数据是否已存在
		PubCityAddr city = dao.getCityById(pubSendRules.getCity());
		if (checkIsExist(pubSendRules) > 0) {
			
			ret.put("ResultSign", "Error");
			if(pubSendRules.getUseType()!=null && pubSendRules.getUseType().equals(1))
			    ret.put("MessageKey", "该城市已有网约车即刻用车的派单规则");
			if(pubSendRules.getUseType()!=null && pubSendRules.getUseType().equals(0))
				ret.put("MessageKey", "该城市已有网约车预约用车的派单规则");
		} else {
			String uuid = GUIDGenerator.newGUID();
			pubSendRules.setId(uuid);
			pubSendRules.setShortName(city.getFullNameInitials());
			pubSendRules.setPlatformType("1");//租赁端
			pubSendRules.setVehicleType(0);//网约车
			pubSendRules.setRulesState("1");//初始为禁用
			pubSendRules.setStatus(1);
			
			PubSendRulesHistory sendRulesHistory=JSON.parseObject(JSON.toJSONString(pubSendRules), PubSendRulesHistory.class);
			sendRulesHistory.setOperateType("0");
			sendRulesHistory.setSendRulesId(uuid);
			sendRulesHistory.setOperator(pubSendRules.getCreater());
			
			try {
				dao.createSendRules(pubSendRules);
				dao.insertSendRulesHistory(sendRulesHistory);
			} catch (Exception e) {
				e.printStackTrace();
				ret.put("ResultSign", "Error");
				ret.put("MessageKey", "操作失败");
			}
		}
		return ret;
	}
	
	public int checkIsExist(PubSendRules pubSendRules){
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("leasesCompanyId", pubSendRules.getLeasesCompanyId());
		paramMap.put("city", pubSendRules.getCity());
		paramMap.put("useType", pubSendRules.getUseType().toString());
		paramMap.put("id", pubSendRules.getId());
		return dao.getSendRulesListCountByEqualQuery(paramMap);
	}
	
	public Map<String, String> updateSendRules(PubSendRules pubSendRules) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "操作成功");
		//检查账户配置
		if("0".equals(pubSendRules.getRulesState())){
		LeLeasescompany leaseCompany=leaseDao.getLeLeasescompanyById(pubSendRules.getLeasesCompanyId());
		if(!checkUserPayAccount(leaseCompany) && !checkDriverPayAccount(leaseCompany)){
			ret.put("ResultSign", "Error");
		    ret.put("MessageKey", "启用失败，乘客和司机交易账户当前没有启用的收款账户，设置后才可启用");
		   return ret;
		}
		if(checkUserPayAccount(leaseCompany) && !checkDriverPayAccount(leaseCompany)){
			ret.put("ResultSign", "Error");
		    ret.put("MessageKey", "启用失败，司机交易账户当前没有启用的收款账户，设置后才可启用");
		   return ret;
		}
		if(!checkUserPayAccount(leaseCompany) && checkDriverPayAccount(leaseCompany)){
			ret.put("ResultSign", "Error");
		    ret.put("MessageKey", "启用失败，乘客交易账户当前没有启用的收款账户，设置后才可启用");
		   return ret;
		}
		}
		
        PubSendRules oldSendRules=getSendRulesById(pubSendRules.getId(),pubSendRules.getLeasesCompanyId());
		
		PubSendRulesHistory sendRulesHistory=JSON.parseObject(JSON.toJSONString(oldSendRules), PubSendRulesHistory.class);
		if(pubSendRules.getRulesState()!=null && pubSendRules.getRulesState().equals("0"))
			sendRulesHistory.setOperateType("2");
		if(pubSendRules.getRulesState()!=null && pubSendRules.getRulesState().equals("1"))
			sendRulesHistory.setOperateType("3");
		if(pubSendRules.getRulesState()==null)
			sendRulesHistory.setOperateType("1");
		
		sendRulesHistory.setSendRulesId(pubSendRules.getId());
		sendRulesHistory.setId(GUIDGenerator.newGUID());
		sendRulesHistory.setOperator(pubSendRules.getUpdater());
		try {
			dao.updateSendRules(pubSendRules);
            dao.insertSendRulesHistory(sendRulesHistory);			
		} catch (Exception e) {
			e.printStackTrace();
			ret.put("ResultSign", "Error");
		    ret.put("MessageKey", "操作失败");
		}
		return ret;
	}
	
	private boolean checkDriverPayAccount(LeLeasescompany leaseCompany) {
		if(leaseCompany!=null && (PayAccountStatusEnum.DREDGE.code.equals(leaseCompany.getDriveralipaystatus())||PayAccountStatusEnum.DREDGE.code.equals(leaseCompany.getDriverwechatstatus())))
		    return true;
		else
			return false;
	}

	private boolean checkUserPayAccount(LeLeasescompany leaseCompany) {
		if(leaseCompany!=null && (PayAccountStatusEnum.DREDGE.code.equals(leaseCompany.getAlipaystatus())||PayAccountStatusEnum.DREDGE.code.equals(leaseCompany.getWechatstatus())))
		    return true;
		else
			return false;
	}

	public List<Map<String, Object>> getCityListById(String leasesCompanyId, String cityName) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("leasesCompanyId", leasesCompanyId);
		map.put("cityName", cityName);
		
		return dao.getCityListById(map);
	}
	
	public PubSendRules getSendRulesById(String id, String leasesCompanyId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("leasesCompanyId", leasesCompanyId);
		return dao.getSendRulesById(map);
	}
	
	public PubCityAddr getCityById(String id) {
		return dao.getCityById(id);
	}

	public PageBean getSendRulesHistoryList(PubSendRulesHistoryQueryParam queryParam) {
		PageBean pageBean=new PageBean();
		int totalRecords=dao.getSendRulesHistoryCount(queryParam);
		pageBean.setsEcho(queryParam.getsEcho());
		pageBean.setAaData(dao.getSendRulesHistoryList(queryParam));
		pageBean.setiTotalRecords(totalRecords);
		pageBean.setiTotalDisplayRecords(totalRecords);
		return pageBean;
	}
}
