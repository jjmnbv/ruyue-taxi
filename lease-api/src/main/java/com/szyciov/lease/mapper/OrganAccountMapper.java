package com.szyciov.lease.mapper;

import java.util.List;
import java.util.Map;

import com.szyciov.entity.PubCouponDetail;
import com.szyciov.lease.entity.OrgOrganExpenses;
import com.szyciov.lease.entity.OrganAccountInfo;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.param.OrganAccountQueryParam;

public interface OrganAccountMapper {
	
	List<OrganAccountInfo> getOrganAccountInfoListByQuery(OrganAccountQueryParam organAccountQueryParam);
	
	int getOrganAccountInfoListCountByQuery(OrganAccountQueryParam organAccountQueryParam);
	
	List<OrgOrganExpenses> getOrganExpensesListByQuery(OrganAccountQueryParam organAccountQueryParam);
	
	int getOrganExpensesListCountByQuery(OrganAccountQueryParam organAccountQueryParam);
	
	void rechargeOrganAccount(Map<String, Object> map);
	
	void reduceOrganAccount(Map<String, Object> map);
	
	void createOrganExpenses(OrgOrganExpenses orgOrganExpenses);
	
	List<PubCityAddr> getExistCityList(Map<String, String> map);
	
	List<Map<String, Object>> getExistOrganList(Map<String, String> map);
	
	List<OrgOrganExpenses> getOrganExpensesListExport(OrganAccountQueryParam organAccountQueryParam);
	
	List<PubCouponDetail> getPubCouponDetailListByQuery(OrganAccountQueryParam organAccountQueryParam);
	
	int getPubCouponDetailListCountByQuery(OrganAccountQueryParam organAccountQueryParam);
	
	List<PubCouponDetail> getPubCouponDetailExport(OrganAccountQueryParam organAccountQueryParam);
	
	void addOrganCouponValue(Map<String, Object> map);
	
	void addCouponDetail(Map<String, Object> map);
	
	void clearOrganCouponValue(Map<String, Object> map);
	
	double getOrganCouponValue(Map<String, Object> map);
	
	void clearCouponDetail(Map<String, Object> map);
	
	String getCityByOrganid(String id);
}
