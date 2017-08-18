package com.szyciov.passenger.mapper;

import java.util.List;
import java.util.Map;

import com.szyciov.lease.entity.OrgOrgan;
import com.szyciov.passenger.entity.AccountRules;
import com.szyciov.passenger.entity.LeasesCompany;
import com.szyciov.passenger.entity.VehicleModels;

public interface OrgMapper {

	List<Map<String, Object>> getCompanyCityBusiness(Map<String, Object> param);

	List<Map<String, Object>> getCCBusiness4UserRules(Map<String, Object> ruleparam);

	List<AccountRules> getAccountRules(Map<String, Object> params);

	LeasesCompany getLeasesCompanyById(String companyid);

	List<String> getValidCity(String companyid);

	List<Map<String,Object>> getCity(Map<String, Object> sp);

	List<Map<String, Object>> getUseCarRules(Map<String, Object> carserviceparams);

	List<VehicleModels> getCarMoudels(Map<String, Object> sparam);

	List<String> getOrgUsercar4Rules(Map<String, Object> param);

	Map<String, Object> getCostInfo4Org(String orderid);

	OrgOrgan getOrgInfo(String account);

	boolean hasThisTradeNo(Map<String, Object> params);

	List<Map<String, Object>> getGetOnCitys(Map<String, Object> params);

	List<Map<String, Object>> getSelfCompanyCity(Map<String, Object> sp);

	Map<String, Object> getSuperTelPhone(String organid);

	List<Map<String, Object>> getSuperAndCaiWu(String companyid);

	void addNews4LeaseUser(Map<String, Object> news);

	List<String> getSendRuleCitys(Map<String, Object> params);

    List<Map<String,Object>> getOldestServiceCompanys();

    List<String> getAccountRuleCitys(Map<String, Object> params);

    Map<String,Object> getSendRuleByCity4ReverceNetCar(Map<String, Object> params);
}