package com.szyciov.lease.mapper;

import java.util.List;
import java.util.Map;

import com.szyciov.lease.entity.PubDriverAccount;
import com.szyciov.lease.param.PubDriverAccountQueryParam;
import com.szyciov.op.entity.PubDriverExpenses;
import com.szyciov.op.param.PubDriverExpensesQuerryParam;

public interface PubDriverAccountMapper {
	void createOrgDriverAccount(PubDriverAccount orgDriverAccount);
	
	List<PubDriverAccount> getOrgDriverAccountListByQuery(PubDriverAccountQueryParam orgDriverAccountQueryParam);
	
	int getOrgDriverAccountListCountByQuery(PubDriverAccountQueryParam orgDriverAccountQueryParam);
	
	List<Map<String, Object>> getQueryDriver(PubDriverAccountQueryParam orgDriverAccountQueryParam); 
	
	List<PubDriverExpenses> getDetailedByList(PubDriverExpensesQuerryParam peDriverExpensesQuerryParam);
	
	int getDetailedByListCount(PubDriverExpensesQuerryParam peDriverExpensesQuerryParam);
	
	List<PubDriverExpenses> exportData(PubDriverExpensesQuerryParam peDriverExpensesQuerryParam);
	
	int getIsNull(String id);
}