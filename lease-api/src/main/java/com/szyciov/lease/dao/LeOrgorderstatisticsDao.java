package com.szyciov.lease.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.mapper.LeOrgorderstatisticsMapper;
import com.szyciov.lease.param.LeOrgorderstatisticsParam;
import com.szyciov.lease.param.OrgOrganQueryParam;

@Repository("LeOrgorderstatisticsDao")
public class LeOrgorderstatisticsDao {
	
     public LeOrgorderstatisticsDao(){
	}
     private LeOrgorderstatisticsMapper mapper;
 	@Resource
 	public void setMapper(LeOrgorderstatisticsMapper mapper) {
 		this.mapper = mapper;
 	}
 	public List<LeOrgorderstatisticsParam> getOrganCountListByQuery(LeOrgorderstatisticsParam leOrgorderstatisticsParam) {
 		return mapper.getOrganCountListByQuery(leOrgorderstatisticsParam);
 	}

 	public int getOrganCountListCountByQuery(LeOrgorderstatisticsParam leOrgorderstatisticsParam) {
 		return mapper.getOrganCountListCountByQuery(leOrgorderstatisticsParam);
 	}
 	public List<LeOrgorderstatisticsParam> getOrganCountAll(LeOrgorderstatisticsParam leOrgorderstatisticsParam) {
 		return mapper.getOrganCountAll(leOrgorderstatisticsParam);
 	}
 	public List<LeOrgorderstatisticsParam> getOrganCountAll1(LeOrgorderstatisticsParam leOrgorderstatisticsParam) {
 		return mapper.getOrganCountAll1(leOrgorderstatisticsParam);
 	}
 	public List<LeOrgorderstatisticsParam> getOrganCityCountAll(LeOrgorderstatisticsParam leOrgorderstatisticsParam) {
 		return mapper.getOrganCityCountAll(leOrgorderstatisticsParam);
 	}
 	public List<LeOrgorderstatisticsParam> getCityCountListByQuery(LeOrgorderstatisticsParam leOrgorderstatisticsParam) {
 		return mapper.getCityCountListByQuery(leOrgorderstatisticsParam);
 	}

 	public int getCityCountListCountByQuery(LeOrgorderstatisticsParam leOrgorderstatisticsParam) {
 		return mapper.getCityCountListCountByQuery(leOrgorderstatisticsParam);
 	}
 	public List<Map<String, Object>> getOrganCity(OrgOrganQueryParam orgOrganQueryParam){
		return mapper.getOrganCity(orgOrganQueryParam);
	};
	public List<Map<String, Object>> getOrganShortName(OrgOrganQueryParam orgOrganQueryParam){
		return mapper.getOrganShortName(orgOrganQueryParam);
	};
	public List<PubCityAddr> getCityListById(String leasesCompanyId) {
		return mapper.getCityListById(leasesCompanyId);
	}
     
}
