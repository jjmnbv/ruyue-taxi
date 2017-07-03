package com.szyciov.lease.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szyciov.lease.dao.OrganAccountDao;
import com.szyciov.lease.entity.OrgOrganExpenses;
import com.szyciov.lease.entity.OrganAccountInfo;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.param.OrganAccountQueryParam;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PageBean;

@Service("organAccountService")
public class OrganAccountService {
	private OrganAccountDao dao;

	@Resource(name = "OrganAccountDao")
	public void setDao(OrganAccountDao dao) {
		this.dao = dao;
	}
	
	public PageBean getOrganAccountInfoByQuery(OrganAccountQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<OrganAccountInfo> list = getOrganAccountInfoListByQuery(queryParam);
		int iTotalRecords = getOrganAccountInfoListCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);

		return pageBean;
	}
	
	public List<OrganAccountInfo> getOrganAccountInfoListByQuery(OrganAccountQueryParam organAccountQueryParam) {
    	return dao.getOrganAccountInfoListByQuery(organAccountQueryParam);
    }
	
	public int getOrganAccountInfoListCountByQuery(OrganAccountQueryParam organAccountQueryParam) {
		return dao.getOrganAccountInfoListCountByQuery(organAccountQueryParam);
	}
	
	public PageBean getOrganExpensesByQuery(OrganAccountQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<OrgOrganExpenses> list = getOrganExpensesListByQuery(queryParam);
		int iTotalRecords = getOrganExpensesListCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);

		return pageBean;
	}
	
	public List<OrgOrganExpenses> getOrganExpensesListByQuery(OrganAccountQueryParam organAccountQueryParam) {
		return dao.getOrganExpensesListByQuery(organAccountQueryParam);
	}
	
	public int getOrganExpensesListCountByQuery(OrganAccountQueryParam organAccountQueryParam) {
		return dao.getOrganExpensesListCountByQuery(organAccountQueryParam);
	}
	
	public Map<String, String> rechargeOrganAccount(OrgOrganExpenses orgOrganExpenses) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "保存成功");

		Map<String, Object> map = new HashMap<String, Object>();
		// 机构、人员、租赁公司关联 id
		map.put("id", orgOrganExpenses.getId());
		// 充值金额
		map.put("value", orgOrganExpenses.getAmount());
		// 机构、人员、租赁公司关联
		dao.rechargeOrganAccount(map);
		
		orgOrganExpenses.setType("0");
		String uuid = GUIDGenerator.newGUID();
		orgOrganExpenses.setId(uuid);
		createOrganExpenses(orgOrganExpenses);
		
		return ret;
    }
	
	public Map<String, String> reduceOrganAccount(OrgOrganExpenses orgOrganExpenses) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "保存成功");

		Map<String, Object> map = new HashMap<String, Object>();
		// 机构、人员、租赁公司关联 id
		map.put("id", orgOrganExpenses.getId());
		// 提现金额
		map.put("value", orgOrganExpenses.getAmount());
		// 机构、人员、租赁公司关联
		dao.reduceOrganAccount(map);
		
		orgOrganExpenses.setType("1");
		String uuid = GUIDGenerator.newGUID();
		orgOrganExpenses.setId(uuid);
		// 提现成功
		orgOrganExpenses.setOperateResult("0");
		orgOrganExpenses.setRemark("申请提现成功，扣减余额");
		createOrganExpenses(orgOrganExpenses);
		
		return ret;
	}

	public void createOrganExpenses(OrgOrganExpenses orgOrganExpenses) {
		dao.createOrganExpenses(orgOrganExpenses);
	}
	
	public List<PubCityAddr> getExistCityList(String leasesCompanyId, String specialState, String account) {
		Map<String, String> map = new HashMap<String, String>();
    	map.put("leasesCompanyId", leasesCompanyId);
    	map.put("specialState", specialState);
    	map.put("account", account);
    	return dao.getExistCityList(map);
    }
	
	public List<Map<String, Object>> getExistOrganList(String leasesCompanyId, String shortName, String specialState, String account) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("leasesCompanyId", leasesCompanyId);
		map.put("shortName", shortName);
		map.put("specialState", specialState);
    	map.put("account", account);
		return dao.getExistOrganList(map);
	}
	
	public List<OrgOrganExpenses> getOrganExpensesListExport(OrganAccountQueryParam organAccountQueryParam) {
		return dao.getOrganExpensesListExport(organAccountQueryParam);
	}
}
