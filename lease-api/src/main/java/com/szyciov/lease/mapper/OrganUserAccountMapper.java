package com.szyciov.lease.mapper;

import java.util.List;
import java.util.Map;

import com.szyciov.lease.entity.OrgUserExpenses;
import com.szyciov.lease.entity.OrganUserAccountInfo;
import com.szyciov.lease.param.OrganUserAccountQueryParam;

public interface OrganUserAccountMapper {
	
	List<OrganUserAccountInfo> getOrganUserAccountInfoListByQuery(OrganUserAccountQueryParam organUserAccountQueryParam);
	
	int getOrganUserAccountInfoListCountByQuery(OrganUserAccountQueryParam organUserAccountQueryParam);
	
	List<OrgUserExpenses> getUserExpensesListByQuery(OrganUserAccountQueryParam organUserAccountQueryParam);
	
	int getUserExpensesListCountByQuery(OrganUserAccountQueryParam organUserAccountQueryParam);
	
	List<Map<String, Object>> getExistUserList(Map<String, String> map);
	
	List<Map<String, Object>> getExistOrganList(Map<String, String> map);
	
	List<OrgUserExpenses> getUserExpensesListExport(OrganUserAccountQueryParam organUserAccountQueryParam);
}
