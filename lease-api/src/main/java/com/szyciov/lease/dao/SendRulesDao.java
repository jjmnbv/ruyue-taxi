package com.szyciov.lease.dao;

import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.mapper.SendRulesMapper;
import com.szyciov.lease.param.LeSendRulesQueryParam;
import com.szyciov.op.entity.PubSendRules;
import com.szyciov.op.entity.PubSendRulesHistory;
import com.szyciov.op.param.PubSendRulesHistoryQueryParam;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Repository("SendRulesDao")
public class SendRulesDao {
	public SendRulesDao() {
	}

	private SendRulesMapper mapper;

	@Resource
	public void setMapper(SendRulesMapper mapper) {
		this.mapper = mapper;
	}
	
    public List<PubSendRules> getSendRulesListByQuery(LeSendRulesQueryParam leSendRulesQueryParam) {
    	return mapper.getSendRulesListByQuery(leSendRulesQueryParam);
    }
	
	public int getSendRulesListCountByQuery(LeSendRulesQueryParam leSendRulesQueryParam) {
		return mapper.getSendRulesListCountByQuery(leSendRulesQueryParam);
	}
	
	public void createSendRules(PubSendRules pubSendRules) {
		mapper.createSendRules(pubSendRules);
	}
	
	public void updateSendRules(PubSendRules pubSendRules) {
		mapper.updateSendRules(pubSendRules);
	}
	
	public int getSendRulesListCountByEqualQuery(Map<String, String> paramMap) {
		return mapper.getSendRulesListCountByEqualQuery(paramMap);
	}
	
	public List<Map<String, Object>> getCityListById(Map<String, String> map) {
		return mapper.getCityListById(map);
	}
	
	public PubSendRules getSendRulesById(Map<String, String> map) {
		return mapper.getSendRulesById(map);
	}
	
	public PubCityAddr getCityById(String id) {
		return mapper.getCityById(id);
	}
	
	public List<PubSendRules> getSendRulesList(PubSendRules leSendRules) {
		return mapper.getSendRulesList(leSendRules);
	}

	public void insertSendRulesHistory(PubSendRulesHistory sendRulesHistory) {
		mapper.insertSendRulesHistory(sendRulesHistory);
	}

	public int getSendRulesHistoryCount(PubSendRulesHistoryQueryParam queryParam) {
		return mapper.getSendRulesHistoryCount(queryParam);
	}

	public List getSendRulesHistoryList(PubSendRulesHistoryQueryParam queryParam) {
		return mapper.getSednRulesHistoryList(queryParam);
	}
}
