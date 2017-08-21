package com.szyciov.lease.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.lease.entity.OrgUserExpenses;
import com.szyciov.lease.entity.OrganUserAccountInfo;
import com.szyciov.lease.entity.OrganUserCouponInfo;
import com.szyciov.lease.mapper.OrganUserAccountMapper;
import com.szyciov.lease.param.OrganUserAccountQueryParam;
import com.szyciov.lease.param.OrganUserCouponQueryParam;

@Repository("OrganUserAccountDao")
public class OrganUserAccountDao {
	public OrganUserAccountDao() {
	}

	private OrganUserAccountMapper mapper;

	@Resource
	public void setMapper(OrganUserAccountMapper mapper) {
		this.mapper = mapper;
	}
	
    public List<OrganUserAccountInfo> getOrganUserAccountInfoListByQuery(OrganUserAccountQueryParam organUserAccountQueryParam) {
    	return mapper.getOrganUserAccountInfoListByQuery(organUserAccountQueryParam);
    }
	
	public int getOrganUserAccountInfoListCountByQuery(OrganUserAccountQueryParam organUserAccountQueryParam) {
		return mapper.getOrganUserAccountInfoListCountByQuery(organUserAccountQueryParam);
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
	
	public List<Map<String, Object>> getExistOrganList(Map<String, String> map) {
		return mapper.getExistOrganList(map);
	}
	
	public List<OrgUserExpenses> getUserExpensesListExport(OrganUserAccountQueryParam organUserAccountQueryParam) {
		return mapper.getUserExpensesListExport(organUserAccountQueryParam);
	}

	public int getOrganUserCouponInfoListCount(OrganUserCouponQueryParam queryParam) {
		return mapper.getOrganUserCouponInfoListCount(queryParam);
	}

	public List<OrganUserCouponInfo> getOrganUserCouponInfoList(OrganUserCouponQueryParam queryParam) {
		return mapper.getOrganUserCouponInfoList(queryParam);
	}

	public String getBusinessCitys(String lecompanyid) {
		return mapper.getBusinessCitys(lecompanyid);
	}

	public List<String> getActivitysInBusinessCity(List<OrganUserCouponInfo> coupons) {
		return mapper.getActivitysInBusinessCity(coupons);
	}

	public List<OrganUserCouponInfo> exportCouponData(OrganUserCouponQueryParam queryParam) {
		return mapper.exportCouponData(queryParam);
	}
}
