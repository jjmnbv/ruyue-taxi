package com.szyciov.lease.mapper;

import java.util.List;
import java.util.Map;

import com.szyciov.lease.entity.LeAccountRules;
import com.szyciov.lease.entity.LeCompanyRulesRef;
import com.szyciov.lease.entity.OrgOrgan;
import com.szyciov.lease.param.IndividualAccountRulesQueryParam;

public interface IndividualAccountRulesMapper {
	
	List<LeCompanyRulesRef> getIndividualAccountRulesListByQuery(IndividualAccountRulesQueryParam queryParam);
	
	int getIndividualAccountRulesListCountByQuery(IndividualAccountRulesQueryParam queryParam);
	
	List<OrgOrgan> getOrganList(Map<String, String> map);
	
	void disableIndividualLeCompanyRulesRef(LeCompanyRulesRef leCompanyRulesRef);
	
	int getIndividualRulesCountById(String id);
	
	List<OrgOrgan> getInsertOrganList(Map<String, String> map);
	
	List<Map<String, Object>> getInsertCityList(Map<String, String> map);
	
    List<LeAccountRules> getIndividualAccountRulesListByOrgan(IndividualAccountRulesQueryParam queryParam);
	
	int getIndividualAccountRulesListCountByOrgan(IndividualAccountRulesQueryParam queryParam);
	
	void updateIndividualAccountRules(LeAccountRules leAccountRules);
	
	int getAccountRulesCountById(Map<String, String> map);
	
	void deleteIndividualAccountRules(Map<String, String> map);
	
	List<Map<String,Object>> getRulesDateByOrgan(Map<String,String> map);
	
	List<LeAccountRules> getIndividualAccountRulesListByCity(Map<String,String> map);
	
	String getRulesIdByDate(LeCompanyRulesRef rulesRef);
	
	int getRulesCountById(LeAccountRules leAccountRules);
	
    void createIndividualLeCompanyRulesRef(LeCompanyRulesRef leCompanyRulesRef);
	
	void createIndividualLeAccountRules(LeAccountRules leAccountRules);
	
	int getRulesCountByOrgan(LeCompanyRulesRef leCompanyRulesRef);
	
	int getRulesCountByQuery(LeCompanyRulesRef leCompanyRulesRef);
	
	int getRulesCountByRulesRefId(LeCompanyRulesRef leCompanyRulesRef);
	
	Map<String, String> getAccountStatusByLeasesCompanyId(String leasesCompanyId);
	
}
