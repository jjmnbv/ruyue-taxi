package com.szyciov.operate.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szyciov.lease.entity.OrgUserExpenses;
import com.szyciov.lease.param.OrganUserAccountQueryParam;
import com.szyciov.op.entity.PeUser;
import com.szyciov.operate.dao.OpUserAccountDao;
import com.szyciov.util.PageBean;

@Service("opUserAccountService")
public class OpUserAccountService {
	private OpUserAccountDao dao;

	@Resource(name = "OpUserAccountDao")
	public void setDao(OpUserAccountDao dao) {
		this.dao = dao;
	}
	
	public PageBean getOpUserAccountByQuery(OrganUserAccountQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<PeUser> list = getOpUserAccountListByQuery(queryParam);
		int iTotalRecords = getOpUserAccountListCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);

		return pageBean;
	}
	
	public List<PeUser> getOpUserAccountListByQuery(OrganUserAccountQueryParam organUserAccountQueryParam) {
    	return dao.getOpUserAccountListByQuery(organUserAccountQueryParam);
    }
	
    public int getOpUserAccountListCountByQuery(OrganUserAccountQueryParam organUserAccountQueryParam) {
    	return dao.getOpUserAccountListCountByQuery(organUserAccountQueryParam);
    }
    
    public PageBean getUserExpensesByQuery(OrganUserAccountQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<OrgUserExpenses> list = getUserExpensesListByQuery(queryParam);
		int iTotalRecords = getUserExpensesListCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);

		return pageBean;
	}
    
    public List<OrgUserExpenses> getUserExpensesListByQuery(OrganUserAccountQueryParam organUserAccountQueryParam) {
    	return dao.getUserExpensesListByQuery(organUserAccountQueryParam);
    }
	
    public int getUserExpensesListCountByQuery(OrganUserAccountQueryParam organUserAccountQueryParam) {
    	return dao.getUserExpensesListCountByQuery(organUserAccountQueryParam);
    }
    
    public List<Map<String, Object>> getExistUserList(String nameAccount) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("nameAccount", nameAccount);
		return dao.getExistUserList(map);
	}
	
    public List<OrgUserExpenses> getUserExpensesListExport(OrganUserAccountQueryParam organUserAccountQueryParam) {
		return dao.getUserExpensesListExport(organUserAccountQueryParam);
	}
}
