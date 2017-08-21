package com.szyciov.lease.mapper;

import java.util.List;
import java.util.Map;

import com.szyciov.lease.entity.OrgUserExpenses;
import com.szyciov.lease.entity.OrganUserAccountInfo;
import com.szyciov.lease.entity.OrganUserCouponInfo;
import com.szyciov.lease.param.OrganUserAccountQueryParam;
import com.szyciov.lease.param.OrganUserCouponQueryParam;

public interface OrganUserAccountMapper {
	
	List<OrganUserAccountInfo> getOrganUserAccountInfoListByQuery(OrganUserAccountQueryParam organUserAccountQueryParam);
	
	int getOrganUserAccountInfoListCountByQuery(OrganUserAccountQueryParam organUserAccountQueryParam);
	
	List<OrgUserExpenses> getUserExpensesListByQuery(OrganUserAccountQueryParam organUserAccountQueryParam);
	
	int getUserExpensesListCountByQuery(OrganUserAccountQueryParam organUserAccountQueryParam);
	
	List<Map<String, Object>> getExistUserList(Map<String, String> map);
	
	List<Map<String, Object>> getExistOrganList(Map<String, String> map);
	
	List<OrgUserExpenses> getUserExpensesListExport(OrganUserAccountQueryParam organUserAccountQueryParam);

	int getOrganUserCouponInfoListCount(OrganUserCouponQueryParam queryParam);

	List<OrganUserCouponInfo> getOrganUserCouponInfoList(OrganUserCouponQueryParam queryParam);

	String getBusinessCitys(String lecompanyid);

	List<String> getActivitysInBusinessCity(List<OrganUserCouponInfo> coupons);

	List<OrganUserCouponInfo> exportCouponData(OrganUserCouponQueryParam queryParam);
}
