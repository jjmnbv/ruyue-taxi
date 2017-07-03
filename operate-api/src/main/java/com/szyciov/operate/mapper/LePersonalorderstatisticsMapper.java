package com.szyciov.operate.mapper;

import java.util.List;
import java.util.Map;

import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.lease.param.LePersonalorderstatisticsParam;

public interface LePersonalorderstatisticsMapper {
	List<LePersonalorderstatisticsParam> getPersonalCountListByQuery(LePersonalorderstatisticsParam lePersonalorderstatisticsParam);
	int getPersonalCountListCountByQuery(LePersonalorderstatisticsParam lePersonalorderstatisticsParam);
	List<LePersonalorderstatisticsParam> getPersonalAll(LePersonalorderstatisticsParam lePersonalorderstatisticsParam);
	List<LePersonalorderstatisticsParam> getPersonalAll1(LePersonalorderstatisticsParam lePersonalorderstatisticsParam);
	List<LePersonalorderstatisticsParam> getPersonalAll2(LePersonalorderstatisticsParam lePersonalorderstatisticsParam);
	List<PubDictionary> getordertype();
	List<PubDictionary> getPaymentstatus();
	List<PubDictionary> getCustomer();
	List<LePersonalorderstatisticsParam> getPersonalCountListByQuery1(LePersonalorderstatisticsParam lePersonalorderstatisticsParam);
	int getPersonalCountListCountByQuery1(LePersonalorderstatisticsParam lePersonalorderstatisticsParam);
	List<Map<String, Object>> getCityListById(LePersonalorderstatisticsParam lePersonalorderstatisticsParam);

}
