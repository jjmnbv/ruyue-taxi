package com.szyciov.lease.mapper;

import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.param.LeSendRulesQueryParam;
import com.szyciov.op.entity.PubSendRules;
import com.szyciov.op.entity.PubSendRulesHistory;
import com.szyciov.op.param.PubSendRulesHistoryQueryParam;

import java.util.List;
import java.util.Map;

public interface SendRulesMapper {
	
	List<PubSendRules> getSendRulesListByQuery(LeSendRulesQueryParam leSendRulesQueryParam);
	
	int getSendRulesListCountByQuery(LeSendRulesQueryParam leSendRulesQueryParam);
	
	void createSendRules(PubSendRules pubSendRules);
	
	void updateSendRules(PubSendRules pubSendRules);
	
	int getSendRulesListCountByEqualQuery(Map<String, String> paramMap);
	
	List<Map<String, Object>> getCityListById(Map<String, String> map);
	
	PubSendRules getSendRulesById(Map<String, String> map);
	
	PubCityAddr getCityById(String id);
	
	List<PubSendRules> getSendRulesList(PubSendRules pubSendRules);

	void insertSendRulesHistory(PubSendRulesHistory sendRulesHistory);

	int getSendRulesHistoryCount(PubSendRulesHistoryQueryParam queryParam);

	List getSednRulesHistoryList(PubSendRulesHistoryQueryParam queryParam);
}
