package com.szyciov.operate.mapper;

import com.szyciov.op.entity.PeUserExpenses;
import com.szyciov.op.entity.PubDriverAccount;
import com.szyciov.op.entity.PubDriverExpenses;
import com.szyciov.op.param.PubDriverAccountQueryParam;
import com.szyciov.op.param.PubDriverExpensesQuerryParam;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface PubDriverAccountMapper {
	void createOrgDriverAccount(PubDriverAccount orgDriverAccount);
	
	List<PubDriverAccount> getOrgDriverAccountListByQuery(PubDriverAccountQueryParam orgDriverAccountQueryParam);
	
	int getOrgDriverAccountListCountByQuery(PubDriverAccountQueryParam orgDriverAccountQueryParam);
	
	List<Map<String, Object>> getQueryDriver(@Param(value = "queryDriver") String queryDriver); 
	
	List<PubDriverExpenses> getDetailedByList(PubDriverExpensesQuerryParam peDriverExpensesQuerryParam);
	
	int getDetailedByListCount(PubDriverExpensesQuerryParam peDriverExpensesQuerryParam);
	
	List<PubDriverExpenses> exportData(PubDriverExpensesQuerryParam peDriverExpensesQuerryParam);
	
	int getIsNull(String id);


	void savePubDriverExpenses(PubDriverExpenses pubDriverExpenses);

	void savePeUserExpenses(PeUserExpenses peUserExpenses);

	PeUserExpenses getPeUserExpenses(String userid);
}