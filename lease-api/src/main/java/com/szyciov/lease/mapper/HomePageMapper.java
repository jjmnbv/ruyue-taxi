package com.szyciov.lease.mapper;

import java.util.Map;

public interface HomePageMapper {

	Map<String, Object> getTotalPayedInfo(String companyid);

	Map<String, Object> getTotalUnPayedInfo(String companyid);

	Object getTotalMoney(String companyid);

	Object getCYTotalMoney(String companyid);

	Map<String, Object> getTodayInfo(Map<String, String> todayparam);

	Map<String, Object> getTomorrowInfo(Map<String, String> tomorrowparam);

	Map<String, Object> getTheMonthInfo(Map<String, String> themonthparam);

	Map<String, Object> getTotalInfoByUseType(Map<String, String> jjparam);

	Map<String, Object> getInfoByTime(Map<String, String> param);

}
