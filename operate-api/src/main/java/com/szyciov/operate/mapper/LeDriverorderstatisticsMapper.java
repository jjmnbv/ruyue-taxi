package com.szyciov.operate.mapper;

import java.util.List;
import java.util.Map;


import com.szyciov.lease.param.LeDriverorderstatisticsParam;

public interface LeDriverorderstatisticsMapper {
	List<LeDriverorderstatisticsParam> getDriverCountListByQueryToC(LeDriverorderstatisticsParam leDriverorderstatisticsParam);
	int getDriverCountListCountByQueryToC(LeDriverorderstatisticsParam leDriverorderstatisticsParam);
	List<LeDriverorderstatisticsParam> getcartypeId(String leasesCompanyId);
	List<Map<String, Object>> getVehcBrand(LeDriverorderstatisticsParam leDriverorderstatisticsParam);
	List<LeDriverorderstatisticsParam> getVehcBrandAll2(LeDriverorderstatisticsParam leDriverorderstatisticsParam);
	LeDriverorderstatisticsParam getVehcBrandAllToC(LeDriverorderstatisticsParam leDriverorderstatisticsParam);
	List<Map<String, Object>> getPlateno(LeDriverorderstatisticsParam leDriverorderstatisticsParam);
	List<Map<String, Object>> getDriver(LeDriverorderstatisticsParam leDriverorderstatisticsParam);
	List<Map<String, Object>> getJobnum(LeDriverorderstatisticsParam leDriverorderstatisticsParam);
}
