package com.szyciov.operate.dao;

import com.szyciov.op.entity.PeUserExpenses;
import com.szyciov.op.entity.PubDriverAccount;
import com.szyciov.op.entity.PubDriverExpenses;
import com.szyciov.op.param.PubDriverAccountQueryParam;
import com.szyciov.op.param.PubDriverExpensesQuerryParam;
import com.szyciov.operate.mapper.PubDriverAccountMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
	
	public List<PubDriverAccount> getOrgDriverAccountListByQuery(PubDriverAccountQueryParam orgDriverAccountQueryParam) {
		return mapper.getOrgDriverAccountListByQuery(orgDriverAccountQueryParam);
	};
	
	public int getOrgDriverAccountListCountByQuery(PubDriverAccountQueryParam orgDriverAccountQueryParam) {
		return mapper.getOrgDriverAccountListCountByQuery(orgDriverAccountQueryParam);
	};
	
	public List<Map<String, Object>> getQueryDriver(String queryDriver){
		return mapper.getQueryDriver(queryDriver);
	}; 
	
	public List<PubDriverExpenses> getDetailedByList(PubDriverExpensesQuerryParam peDriverExpensesQuerryParam) {
		return mapper.getDetailedByList(peDriverExpensesQuerryParam);
	};
	
	public int getDetailedByListCount(PubDriverExpensesQuerryParam peDriverExpensesQuerryParam) {
		return mapper.getDetailedByListCount(peDriverExpensesQuerryParam);
	};
	
	public List<PubDriverExpenses> exportData(PubDriverExpensesQuerryParam peDriverExpensesQuerryParam) {
		return mapper.exportData(peDriverExpensesQuerryParam);
	};
	
	public int getIsNull(String id) {
		return mapper.getIsNull(id);
	};

	/**
	 * 保存司机余额明细
	 * @param pubDriverExpenses
	 */
	public void savePubDriverExpenses(PubDriverExpenses pubDriverExpenses){
		this.mapper.savePubDriverExpenses(pubDriverExpenses);
	}

	/**
	 * 保存乘客余额明细
	 * @param peUserExpenses
	 */
	public void savePeUserExpenses(PeUserExpenses peUserExpenses){
		this.mapper.savePeUserExpenses(peUserExpenses);
	}


	/**
	 * 返回用户余额明细
	 * @param userid
	 * @return
	 */
	public PeUserExpenses  getPeUserExpenses(String userid){
		return this.mapper.getPeUserExpenses(userid);
	}
}
