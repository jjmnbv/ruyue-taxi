package com.szyciov.operate.mapper;

import java.util.List;
import java.util.Map;

import com.szyciov.op.entity.OpTaxisendrules;
import com.szyciov.op.entity.OpTaxisendrulesHistory;
import com.szyciov.op.entity.OpUser;
import com.szyciov.op.param.OpTaxiSendrulesHistoryQueryParam;
import com.szyciov.op.param.OpTaxiSendrulesQueryParam;

public interface TaxiSendrulesMapper {
	
	List<Map<String, Object>> getTaxiSendrulesListByQuery(OpTaxiSendrulesQueryParam queryParam);
	
	int getTaxiSendrulesCountByQuery(OpTaxiSendrulesQueryParam queryParam);
	
	List<Map<String, String>> getTaxiSendrulesCityBySelect(Map<String, String> params);
	
	OpTaxisendrules getTaxiSendrulesById(String id);
	
	int getTaxiDriverCount();
	
	void insertOpTaxiSendrules(OpTaxisendrules object);
	
	void updateOpTaxiSendrules(OpTaxisendrules object);
	
	Map<String, String> getCityShortname(String cityid);
	
	void insertOpTaxisendrulesHistory(OpTaxisendrulesHistory object);
	
	List<OpTaxisendrules> getOpTaxiSendrulesList(OpTaxisendrules object);
	
	List<Map<String, Object>> getTaxiSendrulesHistoryListByQuery(OpTaxiSendrulesHistoryQueryParam queryParam);
	
	int getTaxiSendrulesHistoryCountByQuery(OpTaxiSendrulesHistoryQueryParam queryParam);
	
	int getOpSendmodelCountByUser(OpUser object);

}
