package com.szyciov.lease.mapper;

import java.util.List;
import java.util.Map;

import com.szyciov.lease.param.OrgOrganQueryParam;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.param.LeOrgorderstatisticsParam;


public interface LeOrgorderstatisticsMapper {
	List<LeOrgorderstatisticsParam> getOrganCountListByQuery(LeOrgorderstatisticsParam leOrgorderstatisticsParam);
	List<LeOrgorderstatisticsParam> getOrganCountAll(LeOrgorderstatisticsParam leOrgorderstatisticsParam);
	List<LeOrgorderstatisticsParam> getOrganCountAll1(LeOrgorderstatisticsParam leOrgorderstatisticsParam);
	List<LeOrgorderstatisticsParam> getOrganCityCountAll(LeOrgorderstatisticsParam leOrgorderstatisticsParam);

	int getOrganCountListCountByQuery(LeOrgorderstatisticsParam leOrgorderstatisticsParam);
	List<LeOrgorderstatisticsParam> getCityCountListByQuery(LeOrgorderstatisticsParam leOrgorderstatisticsParam);

	int getCityCountListCountByQuery(LeOrgorderstatisticsParam leOrgorderstatisticsParam);
	List<Map<String, Object>> getOrganCity(OrgOrganQueryParam orgOrganQueryParam);
	List<Map<String, Object>> getOrganShortName(OrgOrganQueryParam orgOrganQueryParam);
	List<PubCityAddr> getCityListById(String leasesCompanyId);
}
