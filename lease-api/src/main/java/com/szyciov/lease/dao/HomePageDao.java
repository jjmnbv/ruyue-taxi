package com.szyciov.lease.dao;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.lease.mapper.HomePageMapper;

@Repository("HomePageDao")
public class HomePageDao {
	
	private HomePageMapper mapper;

	@Resource
	public void setMapper(HomePageMapper mapper) {
		this.mapper = mapper;
	}

	public Map<String, Object> getTotalPayedInfo(String companyid) {
		return mapper.getTotalPayedInfo(companyid);
	}

	public Map<String,Object> getTotalUnPayedInfo(String companyid) {
		return mapper.getTotalUnPayedInfo(companyid);
	}

	public Object getTotalMoney(String companyid) {
		return mapper.getTotalMoney(companyid);
	}

	public Object getCYTotalMoney(String companyid) {
		return mapper.getCYTotalMoney(companyid);
	}

	public Map<String,Object> getTotalInfoByUseType(Map<String, String> jjparam) {
		return mapper.getTotalInfoByUseType(jjparam);
	}

	public Map<String,Object> getInfoByTime(Map<String, String> param) {
		return mapper.getInfoByTime(param);
	}

}
