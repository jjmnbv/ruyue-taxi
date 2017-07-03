package com.szyciov.lease.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.lease.entity.PubDriverAccount;
import com.szyciov.lease.mapper.PubDriverAccountMapper;
import com.szyciov.lease.param.PubDriverAccountQueryParam;
import com.szyciov.op.entity.PubDriverExpenses;
import com.szyciov.op.param.PubDriverExpensesQuerryParam;

@Repository("PubDriverAccountDao")
public class PubDriverAccountDao {
	
	private PubDriverAccountMapper mapper;

	@Resource
	public void setMapper(PubDriverAccountMapper mapper) {
		this.mapper = mapper;
	}

	public void createOrgDriverAccount(PubDriverAccount orgDriverAccount) {
		mapper.createOrgDriverAccount(orgDriverAccount);
	}
	
	public List<PubDriverAccount> getOrgDriverAccountListByQuery(PubDriverAccountQueryParam orgDriverAccountQueryParam){
		return mapper.getOrgDriverAccountListByQuery(orgDriverAccountQueryParam);
	};
	
	public int getOrgDriverAccountListCountByQuery(PubDriverAccountQueryParam orgDriverAccountQueryParam){
		return mapper.getOrgDriverAccountListCountByQuery(orgDriverAccountQueryParam);
	};
	
	public List<Map<String, Object>> getQueryDriver(PubDriverAccountQueryParam orgDriverAccountQueryParam){
		return mapper.getQueryDriver(orgDriverAccountQueryParam);
	}; 
	
	public List<PubDriverExpenses> getDetailedByList(PubDriverExpensesQuerryParam peDriverExpensesQuerryParam){
		return mapper.getDetailedByList(peDriverExpensesQuerryParam);
	};
	
	public int getDetailedByListCount(PubDriverExpensesQuerryParam peDriverExpensesQuerryParam){
		return mapper.getDetailedByListCount(peDriverExpensesQuerryParam);
	};
	
	public List<PubDriverExpenses> exportData(PubDriverExpensesQuerryParam peDriverExpensesQuerryParam){
		return mapper.exportData(peDriverExpensesQuerryParam);
	};
	
	public int getIsNull(String id){
		return mapper.getIsNull(id);
	};
}
