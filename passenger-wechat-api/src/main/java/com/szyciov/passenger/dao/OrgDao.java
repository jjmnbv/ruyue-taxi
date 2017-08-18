package com.szyciov.passenger.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.lease.entity.OrgOrgan;
import com.szyciov.passenger.entity.AccountRules;
import com.szyciov.passenger.entity.LeasesCompany;
import com.szyciov.passenger.entity.VehicleModels;
import com.szyciov.passenger.mapper.OrgMapper;

@Repository("OrgDao")
public class OrgDao {

	private OrgMapper mapper;

	@Resource
	public void setMapper(OrgMapper mapper) {
		this.mapper = mapper;
	}

	public OrgOrgan getOrgInfo(String account) {
		return mapper.getOrgInfo(account);
	}

	public List<VehicleModels> getCarMoudels(Map<String, Object> sparam) {
		return mapper.getCarMoudels(sparam);
	}

	public List<AccountRules> getAccountRules(Map<String, Object> params) {
		return mapper.getAccountRules(params);
	}

	public LeasesCompany getLeasesCompanyById(String companyid) {
		return mapper.getLeasesCompanyById(companyid);
	}

	public List<String> getValidCity(String companyid) {
		return mapper.getValidCity(companyid);
	}

	public List<Map<String,Object>> getCity(Map<String, Object> sp) {
		return mapper.getCity(sp);
	}

	public List<Map<String, Object>> getUseCarRules(Map<String, Object> carserviceparams) {
		return mapper.getUseCarRules(carserviceparams);
	}

	public List<Map<String, Object>> getCompanyCityBusiness(Map<String, Object> sparam) {
		return mapper.getCompanyCityBusiness(sparam);
	}

	public List<Map<String, Object>> getCCBusiness4UserRules(Map<String, Object> ruleparam) {
		return mapper.getCCBusiness4UserRules(ruleparam);
	}

	public List<String> getOrgUsercar4Rules(Map<String, Object> param) {
		return mapper.getOrgUsercar4Rules(param);
	}

	public Map<String, Object> getCostInfo4Org(String orderid) {
		return mapper.getCostInfo4Org(orderid);
	}

	public boolean hasThisTradeNo(Map<String, Object> params) {
		return mapper.hasThisTradeNo(params);
	}

	public List<Map<String, Object>> getGetOnCitys(Map<String, Object> params) {
		return mapper.getGetOnCitys(params);
	}

	public List<Map<String, Object>> getSelfCompanyCity(Map<String, Object> sp) {
		return mapper.getSelfCompanyCity(sp);
	}

	public Map<String, Object> getSuperTelPhone(String organid) {
		return mapper.getSuperTelPhone(organid);
	}

	public List<Map<String, Object>> getSuperAndCaiWu(String companyid) {
		return mapper.getSuperAndCaiWu(companyid);
	}

	public void addNews4LeaseUser(Map<String, Object> news) {
		mapper.addNews4LeaseUser(news);
	}

	public List<String> getSendRuleCitys(Map<String, Object> params) {
		return mapper.getSendRuleCitys(params);
	}

    public List<Map<String,Object>> getOldestServiceCompanys() {
		return mapper.getOldestServiceCompanys();
    }

    public List<String> getAccountRuleCitys(Map<String, Object> params) {
		return mapper.getAccountRuleCitys(params);
    }

	public Map<String,Object> getSendRuleByCity4ReverceNetCar(Map<String, Object> params) {
		return mapper.getSendRuleByCity4ReverceNetCar(params);
	}
}
