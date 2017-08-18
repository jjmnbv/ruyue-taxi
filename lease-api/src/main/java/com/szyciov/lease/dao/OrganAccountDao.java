package com.szyciov.lease.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.entity.PubCouponDetail;
import com.szyciov.lease.entity.OrgOrganExpenses;
import com.szyciov.lease.entity.OrganAccountInfo;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.mapper.OrganAccountMapper;
import com.szyciov.lease.param.OrganAccountQueryParam;

@Repository("OrganAccountDao")
public class OrganAccountDao {
	public OrganAccountDao() {
	}

	private OrganAccountMapper mapper;

	@Resource
	public void setMapper(OrganAccountMapper mapper) {
		this.mapper = mapper;
	}
	
    public List<OrganAccountInfo> getOrganAccountInfoListByQuery(OrganAccountQueryParam organAccountQueryParam) {
    	return mapper.getOrganAccountInfoListByQuery(organAccountQueryParam);
    }
	
	public int getOrganAccountInfoListCountByQuery(OrganAccountQueryParam organAccountQueryParam) {
		return mapper.getOrganAccountInfoListCountByQuery(organAccountQueryParam);
	}
	
	public List<OrgOrganExpenses> getOrganExpensesListByQuery(OrganAccountQueryParam organAccountQueryParam) {
		return mapper.getOrganExpensesListByQuery(organAccountQueryParam);
	}
	
	public int getOrganExpensesListCountByQuery(OrganAccountQueryParam organAccountQueryParam) {
		return mapper.getOrganExpensesListCountByQuery(organAccountQueryParam);
	}
	
    public void rechargeOrganAccount(Map<String, Object> map) {
    	mapper.rechargeOrganAccount(map);
    }
	
	public void reduceOrganAccount(Map<String, Object> map) {
		mapper.reduceOrganAccount(map);
	}
	
	public void createOrganExpenses(OrgOrganExpenses orgOrganExpenses) {
		mapper.createOrganExpenses(orgOrganExpenses);
	}
	
    public List<PubCityAddr> getExistCityList(Map<String, String> map) {
    	return mapper.getExistCityList(map);
    }
	
	public List<Map<String, Object>> getExistOrganList(Map<String, String> map) {
		return mapper.getExistOrganList(map);
	}
	
	public List<OrgOrganExpenses> getOrganExpensesListExport(OrganAccountQueryParam organAccountQueryParam) {
		return mapper.getOrganExpensesListExport(organAccountQueryParam);
	}
	
    public List<PubCouponDetail> getPubCouponDetailListByQuery(OrganAccountQueryParam organAccountQueryParam) {
    	return mapper.getPubCouponDetailListByQuery(organAccountQueryParam);
    }
	
	public int getPubCouponDetailListCountByQuery(OrganAccountQueryParam organAccountQueryParam) {
		return mapper.getPubCouponDetailListCountByQuery(organAccountQueryParam);
	}
	
	public List<PubCouponDetail> getPubCouponDetailExport(OrganAccountQueryParam organAccountQueryParam) {
		return mapper.getPubCouponDetailExport(organAccountQueryParam);
	}
	
	public void addOrganCouponValue(Map<String, Object> map) {
		mapper.addOrganCouponValue(map);
	}
	
    public void addCouponDetail(Map<String, Object> map) {
    	mapper.addCouponDetail(map);
    }
	
    public void clearOrganCouponValue(Map<String, Object> map) {
    	mapper.clearOrganCouponValue(map);
    }
	
    public double getOrganCouponValue(Map<String, Object> map) {
    	return mapper.getOrganCouponValue(map);
    }
	
    public void clearCouponDetail(Map<String, Object> map) {
    	mapper.clearCouponDetail(map);
    }
    
    public String getCityByOrganid(String id) {
    	return mapper.getCityByOrganid(id);
    }
}
