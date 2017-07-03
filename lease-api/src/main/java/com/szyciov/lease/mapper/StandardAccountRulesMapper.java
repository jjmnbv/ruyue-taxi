package com.szyciov.lease.mapper;

import java.util.List;
import java.util.Map;

import com.szyciov.lease.entity.LeAccountRules;
import com.szyciov.lease.entity.LeAccountRulesModiLog;
import com.szyciov.lease.entity.LeVehiclemodels;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.lease.param.LeAccountRulesModiLogQueryParam;
import com.szyciov.lease.param.LeAccountRulesQueryParam;

public interface StandardAccountRulesMapper {
	
	List<LeAccountRules> getStandardAccountRulesListByQuery(LeAccountRulesQueryParam queryParam);
	
	int getStandardAccountRulesListCountByQuery(LeAccountRulesQueryParam queryParam);
	
	int getStandardAccountRulesListCountByEqualQuery(LeAccountRules leAccountRules);
	
	int getStandardAccountRulesListCountByEqualQuery2(LeAccountRules leAccountRules);
	
	LeAccountRules getMessageInfoByEqualQuery(LeAccountRules leAccountRules);
	
	void createStandardAccountRules(LeAccountRules leAccountRules);
	
	void updateStandardAccountRules(LeAccountRules leAccountRules);
	
	LeAccountRules getStandardAccountRulesById(String id);
	
	void createStandardAccountRulesModiLog(LeAccountRulesModiLog leAccountRulesModiLog);
	
	void updateStandardAccountRulesState(LeAccountRules leAccountRules);
	
	void updateStandardAccountRulesOneKey(LeAccountRules leAccountRules);
	
	List<LeAccountRulesModiLog> getStandardAccountRulesModiLogListByQuery(LeAccountRulesModiLogQueryParam queryParam);
	
	int getStandardAccountRulesModiLogListCountByQuery(LeAccountRulesModiLogQueryParam queryParam);
	
	List<LeAccountRules> getStandardAccountRulesByCity(Map<String, String> map);
	
	List<Map<String, Object>> getStandardAccountRulesExistCityList(Map<String, String> map);
	
	List<PubDictionary> getPubDictionaryByType(String type);
	
	List<LeVehiclemodels> getStandardAccountRulesExistCarTypeList(String leasesCompanyId);
	
	List<LeVehiclemodels> getCarTypeList(String leasesCompanyId);
	
	int getLeAccountRulesCountById(Map<String, String> map);
	
	LeAccountRules getOneStandardAccountRulesByCity(Map<String, String> map);
	
	List<PubCityAddr> getPubCityAddrList();
	
	Map<String, String> getAccountStatusByLeasesCompanyId(String leasesCompanyId);
}
