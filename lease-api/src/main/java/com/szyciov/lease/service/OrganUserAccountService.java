package com.szyciov.lease.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szyciov.lease.dao.OrganUserAccountDao;
import com.szyciov.lease.entity.OrgUserExpenses;
import com.szyciov.lease.entity.OrganUserAccountInfo;
import com.szyciov.lease.param.OrganUserAccountQueryParam;
import com.szyciov.util.PageBean;

@Service("organUserAccountService")
public class OrganUserAccountService {
	private OrganUserAccountDao dao;

	@Resource(name = "OrganUserAccountDao")
	public void setDao(OrganUserAccountDao dao) {
		this.dao = dao;
	}
	
	public PageBean getOrganUserAccountInfoByQuery(OrganUserAccountQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<OrganUserAccountInfo> list = getOrganUserAccountInfoListByQuery(queryParam);
		int iTotalRecords = getOrganUserAccountInfoListCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);

		return pageBean;
	}

	public List<OrganUserAccountInfo> getOrganUserAccountInfoListByQuery(OrganUserAccountQueryParam organUserAccountQueryParam) {
    	return dao.getOrganUserAccountInfoListByQuery(organUserAccountQueryParam);
    }
	
	public int getOrganUserAccountInfoListCountByQuery(OrganUserAccountQueryParam organUserAccountQueryParam) {
		return dao.getOrganUserAccountInfoListCountByQuery(organUserAccountQueryParam);
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
	
	public List<Map<String, Object>> getExistUserList(String leasesCompanyId, String nameAccount, String specialState, String account) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("leasesCompanyId", leasesCompanyId);
		map.put("nameAccount", nameAccount);
		map.put("specialState", specialState);
    	map.put("account", account);
		return dao.getExistUserList(map);
	}
	
	public List<Map<String, Object>> getExistOrganList(String leasesCompanyId, String shortName, String specialState, String account) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("leasesCompanyId", leasesCompanyId);
		map.put("shortName", shortName);
		map.put("specialState", specialState);
    	map.put("account", account);
		return dao.getExistOrganList(map);
	}
	
	public List<OrgUserExpenses> getUserExpensesListExport(OrganUserAccountQueryParam organUserAccountQueryParam) {
		return dao.getUserExpensesListExport(organUserAccountQueryParam);
	}

}
