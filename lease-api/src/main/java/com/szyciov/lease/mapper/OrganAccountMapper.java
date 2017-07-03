package com.szyciov.lease.mapper;

import java.util.List;
import java.util.Map;

import com.szyciov.lease.entity.OrgOrganExpenses;
import com.szyciov.lease.entity.OrganAccountInfo;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.param.OrganAccountQueryParam;

public interface OrganAccountMapper {
	
	List<OrganAccountInfo> getOrganAccountInfoListByQuery(OrganAccountQueryParam organAccountQueryParam);
	
	int getOrganAccountInfoListCountByQuery(OrganAccountQueryParam organAccountQueryParam);
	
	List<OrgOrganExpenses> getOrganExpensesListByQuery(OrganAccountQueryParam organAccountQueryParam);
	
	int getOrganExpensesListCountByQuery(OrganAccountQueryParam organAccountQueryParam);
	
	void rechargeOrganAccount(Map<String, Object> map);
	
	void reduceOrganAccount(Map<String, Object> map);
	
	void createOrganExpenses(OrgOrganExpenses orgOrganExpenses);
	
	List<PubCityAddr> getExistCityList(Map<String, String> map);
	
	List<Map<String, Object>> getExistOrganList(Map<String, String> map);
	
	List<OrgOrganExpenses> getOrganExpensesListExport(OrganAccountQueryParam organAccountQueryParam);
}
