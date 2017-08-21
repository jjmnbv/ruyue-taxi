package com.szyciov.lease.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szyciov.lease.dao.OrganUserAccountDao;
import com.szyciov.lease.entity.OrgUserExpenses;
import com.szyciov.lease.entity.OrganUserAccountInfo;
import com.szyciov.lease.entity.OrganUserCouponInfo;
import com.szyciov.lease.param.OrganUserAccountQueryParam;
import com.szyciov.lease.param.OrganUserCouponQueryParam;
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

	public PageBean getOrganUserCouponInfoByQuery(OrganUserCouponQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<OrganUserCouponInfo> list = getOrganUserCouponInfoList(queryParam);
		int iTotalRecords = getOrganUserCouponInfoListCount(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);

		return pageBean;
	}

	private int getOrganUserCouponInfoListCount(OrganUserCouponQueryParam queryParam) {
		return dao.getOrganUserCouponInfoListCount(queryParam);
	}

	private List<OrganUserCouponInfo> getOrganUserCouponInfoList(OrganUserCouponQueryParam queryParam) {
		List<OrganUserCouponInfo> coupons=dao.getOrganUserCouponInfoList(queryParam);
		//对于抵扣券活动使用区域在业务城市的记录，需要填入
		//因为只是统计机构客户，所以只查询网约车业务城市即可
		if(coupons.size()>0){
		List<String> ids=dao.getActivitysInBusinessCity(coupons);//查询是否有抵扣券活动使用区域在业务城市
		if(ids.size()>0){
		String citys=dao.getBusinessCitys(queryParam.getLecompanyid());//查询租赁公司业务城市
		  for(OrganUserCouponInfo c:coupons){
			  if(ids.contains(c.getId()))
				  c.setUsecity(citys);
		  }
		}
		}
		return coupons;
	}

	public List<OrganUserCouponInfo> exportCouponData(OrganUserCouponQueryParam queryParam) {
		List<OrganUserCouponInfo> coupons=dao.exportCouponData(queryParam);
		//对于抵扣券活动使用区域在业务城市的记录，需要填入
		//因为只是统计机构客户，所以只查询网约车业务城市即可
		if(coupons.size()>0){
		List<String> ids=dao.getActivitysInBusinessCity(coupons);//查询是否有抵扣券活动使用区域在业务城市
		if(ids.size()>0){
		String citys=dao.getBusinessCitys(queryParam.getLecompanyid());//查询租赁公司业务城市
		  for(OrganUserCouponInfo c:coupons){
			  if(ids.contains(c.getId()))
				  c.setUsecity(citys);
		  }
		}
		}
		return coupons;
	}

}
