package com.szyciov.operate.mapper;

import java.util.List;
import java.util.Map;

import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.lease.param.LeDriverorderstatisticsParam;
import com.szyciov.op.entity.OpTaxiSchedulefeestatistics;

public interface OpTaxiSchedulefeestatisticsMapper {
	 List<PubDictionary> getCustomer();
	 List<OpTaxiSchedulefeestatistics> getDateQuery(OpTaxiSchedulefeestatistics opTaxiSchedulefeestatistics);
	 int getDateQueryCount(OpTaxiSchedulefeestatistics opTaxiSchedulefeestatistics);
	 List<Map<String, Object>> getDriver(LeDriverorderstatisticsParam leDriverorderstatisticsParam);
	 List<OpTaxiSchedulefeestatistics> getDateDriverQuery(OpTaxiSchedulefeestatistics opTaxiSchedulefeestatistics);
	 int getDateDriverQueryCount(OpTaxiSchedulefeestatistics opTaxiSchedulefeestatistics);
	 List<OpTaxiSchedulefeestatistics> opTaxiSchedulefeesExport(OpTaxiSchedulefeestatistics opTaxiSchedulefeestatistics);
	 List<OpTaxiSchedulefeestatistics> opTaxiSchedulefeesExport1(OpTaxiSchedulefeestatistics opTaxiSchedulefeestatistics);
}
