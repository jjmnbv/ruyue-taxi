package com.szyciov.operate.mapper;

import java.util.List;
import java.util.Map;

import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.op.entity.OpPlatformInfo;
import com.szyciov.op.entity.PubSendRules;
import com.szyciov.op.entity.PubSendRulesHistory;
import com.szyciov.op.param.OpTaxiSendrulesQueryParam;
import com.szyciov.op.param.PubSendRulesHistoryQueryParam;
import com.szyciov.util.PageBean;

public interface SendRulesMapper {
    
	List<PubSendRules> getSendRulesListByQuery(OpTaxiSendrulesQueryParam queryParam);
	
	int getSendRulesListCountByQuery(OpTaxiSendrulesQueryParam queryParam);
	
	void createSendRules(PubSendRules sendRules);
	
	void updateSendRules(PubSendRules leSendRules);
	
	int getSendRulesListCountByEqualQuery(Map<String, String> paramMap);
	
	List<Map<String, Object>> getCityListById(Map<String, String> map);
	
	PubSendRules getSendRulesById(String id);
	
	PubCityAddr getCityById(String id);
	
	List<PubCityAddr> getPubCityAddr();

	OpPlatformInfo getOpPlatformInfo();

	void insertSendRulesHistory(PubSendRulesHistory sendRulesHistory);

	PageBean getSendRulesHistoryByQuery(PubSendRulesHistoryQueryParam queryParam);

	int getSendRulesHistoryCountByQuery(PubSendRulesHistoryQueryParam queryParam);

	List getSendRulesHistoryListByQuery(PubSendRulesHistoryQueryParam queryParam);
}
