package com.szyciov.operate.service;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szyciov.op.entity.PubDriverAccount;
import com.szyciov.op.entity.PubDriverExpenses;
import com.szyciov.op.param.PubDriverAccountQueryParam;
import com.szyciov.op.param.PubDriverExpensesQuerryParam;
import com.szyciov.operate.dao.PubDriverAccountDao;
import com.szyciov.util.PageBean;

@Service("PubDriverAccountService")
public class PubDriverAccountService {
	private PubDriverAccountDao dao;

	@Resource(name = "PubDriverAccountDao")
	public void setDao(PubDriverAccountDao dao) {
		this.dao = dao;
	}

	public void createOrgDriverAccount(PubDriverAccount orgDriverAccount) {
		dao.createOrgDriverAccount(orgDriverAccount);
	}
	
	public List<PubDriverAccount> getOrgDriverAccountListByQuery(PubDriverAccountQueryParam orgDriverAccountQueryParam) {
		return dao.getOrgDriverAccountListByQuery(orgDriverAccountQueryParam);
	};
	
	public int getOrgDriverAccountListCountByQuery(PubDriverAccountQueryParam orgDriverAccountQueryParam) {
		return dao.getOrgDriverAccountListCountByQuery(orgDriverAccountQueryParam);
	};
	
	public PageBean getOrgDriverAccountByQuery(PubDriverAccountQueryParam orgDriverAccountQueryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(orgDriverAccountQueryParam.getsEcho());
		List<PubDriverAccount> list = getOrgDriverAccountListByQuery(orgDriverAccountQueryParam);
		int iTotalRecords = getOrgDriverAccountListCountByQuery(orgDriverAccountQueryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	
	public List<Map<String, Object>> getQueryDriver(String queryDriver) {
		return dao.getQueryDriver(queryDriver);
	};
	
	public List<PubDriverExpenses> getDetailedByList(PubDriverExpensesQuerryParam peDriverExpensesQuerryParam) {
		return dao.getDetailedByList(peDriverExpensesQuerryParam);
	};
	
	public int getDetailedByListCount(PubDriverExpensesQuerryParam peDriverExpensesQuerryParam) {
		return dao.getDetailedByListCount(peDriverExpensesQuerryParam);
	};
	
	public PageBean getDetailedByQuery(PubDriverExpensesQuerryParam peDriverExpensesQuerryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(peDriverExpensesQuerryParam.getsEcho());
		List<PubDriverExpenses> list = getDetailedByList(peDriverExpensesQuerryParam);
		int iTotalRecords = getDetailedByListCount(peDriverExpensesQuerryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	
	public List<PubDriverExpenses> exportData(PubDriverExpensesQuerryParam peDriverExpensesQuerryParam) {
		return dao.exportData(peDriverExpensesQuerryParam);
	};
	
	public int getIsNull(String id) {
		return dao.getIsNull(id);
	};
}
