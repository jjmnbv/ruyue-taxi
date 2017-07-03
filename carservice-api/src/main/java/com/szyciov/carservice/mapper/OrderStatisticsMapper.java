package com.szyciov.carservice.mapper;

import com.szyciov.param.OrderStatisticsQueryParam;

public interface OrderStatisticsMapper {
	
	public int leIndexOrderStatistics(OrderStatisticsQueryParam queryParam);
	public int leIndexOrderStatisticsok(OrderStatisticsQueryParam queryParam);
	
	public int leOrgOrderStatistics(OrderStatisticsQueryParam queryParam);
	
	public int lePersonalOrderStatistics(OrderStatisticsQueryParam queryParam);
	
	public int lePersonalOrderStatisticsToC(OrderStatisticsQueryParam queryParam);
	public int lePersonalOrderStatisticsTaxi(OrderStatisticsQueryParam queryParam);
	
	public int opIndexorderstatistics(OrderStatisticsQueryParam queryParam);
	public int opIndexorderstatisticsP(OrderStatisticsQueryParam queryParam);
	public int opIndexorderstatisticsPB(OrderStatisticsQueryParam queryParam);
	public int opIndexorderstatisticsTaxi(OrderStatisticsQueryParam queryParam);
	
	public int opOrderstatistics(OrderStatisticsQueryParam queryParam);
	public int opOrderstatisticsP(OrderStatisticsQueryParam queryParam);
	
	public int orgCompanystatistics(OrderStatisticsQueryParam queryParam);
	
	public int orgDeptstatistics(OrderStatisticsQueryParam queryParam);
	
	public int validateDataExists(OrderStatisticsQueryParam queryParam);
	
	public int deleteRepeatData(OrderStatisticsQueryParam queryParam);
	public int deleteData(OrderStatisticsQueryParam queryParam);
}
