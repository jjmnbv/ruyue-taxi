package com.szyciov.operate.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.op.entity.OpPlatformInfo;
import com.szyciov.op.entity.PubSendRules;
import com.szyciov.op.entity.PubSendRulesHistory;
import com.szyciov.op.param.OpTaxiSendrulesQueryParam;
import com.szyciov.op.param.PubSendRulesHistoryQueryParam;
import com.szyciov.operate.mapper.SendRulesMapper;
import com.szyciov.util.PageBean;

@Repository("SendRulesDao")
public class SendRulesDao {
	public SendRulesDao() {
	}

	private SendRulesMapper mapper;

	@Resource
	public void setMapper(SendRulesMapper mapper) {
		this.mapper = mapper;
	}
	
    public List<PubSendRules> getSendRulesListByQuery(OpTaxiSendrulesQueryParam queryParam) {
    	return mapper.getSendRulesListByQuery(queryParam);
    }
	
    public int getSendRulesListCountByQuery(OpTaxiSendrulesQueryParam queryParam) {
    	return mapper.getSendRulesListCountByQuery(queryParam);
    }
	
    public void createSendRules(PubSendRules sendRules) {
    	mapper.createSendRules(sendRules);
    }
	
    public void updateSendRules(PubSendRules leSendRules) {
    	mapper.updateSendRules(leSendRules);
    }
	
    public int getSendRulesListCountByEqualQuery(Map<String, String> paramMap) {
    	return mapper.getSendRulesListCountByEqualQuery(paramMap);
    }
	
    public List<Map<String, Object>> getCityListById(Map<String, String> map) {
    	return mapper.getCityListById(map);
    }
	
    public PubSendRules getSendRulesById(String id) {
    	return mapper.getSendRulesById(id);
    }
	
    public PubCityAddr getCityById(String id) {
    	return mapper.getCityById(id);
    }
    
    public List<PubCityAddr> getPubCityAddr() {
		return mapper.getPubCityAddr();
	}

	public OpPlatformInfo getOpPlatformInfo() {
		return mapper.getOpPlatformInfo();
	}

	public void insertSendRulesHistory(PubSendRulesHistory sendRulesHistory) {
        mapper.insertSendRulesHistory(sendRulesHistory);		
	}

	public PageBean getSendRulesHistoryByQuery(PubSendRulesHistoryQueryParam queryParam) {
		return mapper.getSendRulesHistoryByQuery(queryParam);
	}

	public int getSendRulesHistoryCountByQuery(PubSendRulesHistoryQueryParam queryParam) {
		return mapper.getSendRulesHistoryCountByQuery(queryParam);
	}

	public List getSendRulesHistoryListByQuery(PubSendRulesHistoryQueryParam queryParam) {
		return mapper.getSendRulesHistoryListByQuery(queryParam);
	}
}
