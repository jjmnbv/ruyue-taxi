package com.szyciov.operate.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.lease.entity.OrgUserExpenses;
import com.szyciov.lease.param.OrganUserAccountQueryParam;
import com.szyciov.op.entity.PeUser;
import com.szyciov.op.entity.PeUserExpenses;
import com.szyciov.op.entity.PeUseraccount;
import com.szyciov.operate.mapper.OpUserAccountMapper;

@Repository("OpUserAccountDao")
public class OpUserAccountDao {
	public OpUserAccountDao() {
	}

	private OpUserAccountMapper mapper;

	@Resource
	public void setMapper(OpUserAccountMapper mapper) {
		this.mapper = mapper;
	}
	
    public List<PeUser> getOpUserAccountListByQuery(OrganUserAccountQueryParam organUserAccountQueryParam) {
    	return mapper.getOpUserAccountListByQuery(organUserAccountQueryParam);
    }
	
    public int getOpUserAccountListCountByQuery(OrganUserAccountQueryParam organUserAccountQueryParam) {
    	return mapper.getOpUserAccountListCountByQuery(organUserAccountQueryParam);
    }
	
    public List<OrgUserExpenses> getUserExpensesListByQuery(OrganUserAccountQueryParam organUserAccountQueryParam) {
    	return mapper.getUserExpensesListByQuery(organUserAccountQueryParam);
    }
	
    public int getUserExpensesListCountByQuery(OrganUserAccountQueryParam organUserAccountQueryParam) {
    	return mapper.getUserExpensesListCountByQuery(organUserAccountQueryParam);
    }
	
    public List<Map<String, Object>> getExistUserList(Map<String, String> map) {
    	return mapper.getExistUserList(map);
    }
	
    public List<OrgUserExpenses> getUserExpensesListExport(OrganUserAccountQueryParam organUserAccountQueryParam) {
    	return mapper.getUserExpensesListExport(organUserAccountQueryParam);
    }
    public PeUser admoney(PeUser peUser) {
		return mapper.admoney(peUser);
	}
    public void admoneyOk(PeUser peUser) {
		 mapper.admoneyOk(peUser);
	}
    public void addPeuserexpenses(PeUserExpenses peUserExpenses) {
		 mapper.addPeuserexpenses(peUserExpenses);
	}
    public void insertAccount(PeUseraccount peUseraccount) {
		 mapper.insertAccount(peUseraccount);
	}
}
