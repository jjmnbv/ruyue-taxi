package com.szyciov.operate.mapper;

import java.util.List;
import java.util.Map;

import com.szyciov.lease.entity.OrgUserExpenses;
import com.szyciov.lease.param.OrganUserAccountQueryParam;
import com.szyciov.op.entity.PeUser;
import com.szyciov.op.entity.PeUserExpenses;
import com.szyciov.op.entity.PeUseraccount;

public interface OpUserAccountMapper {
	
	List<PeUser> getOpUserAccountListByQuery(OrganUserAccountQueryParam organUserAccountQueryParam);
	
	int getOpUserAccountListCountByQuery(OrganUserAccountQueryParam organUserAccountQueryParam);
	
	List<OrgUserExpenses> getUserExpensesListByQuery(OrganUserAccountQueryParam organUserAccountQueryParam);
	
	int getUserExpensesListCountByQuery(OrganUserAccountQueryParam organUserAccountQueryParam);
	
	List<Map<String, Object>> getExistUserList(Map<String, String> map);
	
	List<OrgUserExpenses> getUserExpensesListExport(OrganUserAccountQueryParam organUserAccountQueryParam);
	
	PeUser admoney(PeUser peUser);
	
	void admoneyOk(PeUser peUser);
	void addPeuserexpenses(PeUserExpenses peUserExpenses);
     void insertAccount(PeUseraccount peUseraccount);
}
