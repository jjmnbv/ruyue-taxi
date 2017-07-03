package com.szyciov.carservice.dao;


import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.carservice.mapper.OrderStatisticsMapper;
import com.szyciov.param.OrderStatisticsQueryParam;

@Repository("orderStatisticsDao")
public class OrderStatisticsDao {
	
	private OrderStatisticsMapper mapper;

	@Resource
	public void setMapper(OrderStatisticsMapper mapper) {
		this.mapper = mapper;
	}
	
	public int leIndexOrderStatistics(OrderStatisticsQueryParam queryParam) {
		return mapper.leIndexOrderStatistics(queryParam);
	}
	public int leIndexOrderStatisticsok(OrderStatisticsQueryParam queryParam) {
		return mapper.leIndexOrderStatisticsok(queryParam);
	}
	
	public int leOrgOrderStatistics(OrderStatisticsQueryParam queryParam) {
		return mapper.leOrgOrderStatistics(queryParam);
	}
	
	public int lePersonalOrderStatistics(OrderStatisticsQueryParam queryParam) {
		return mapper.lePersonalOrderStatistics(queryParam);
	}
	public int lePersonalOrderStatisticsToC(OrderStatisticsQueryParam queryParam) {
		return mapper.lePersonalOrderStatisticsToC(queryParam);
	}
	public int lePersonalOrderStatisticsTaxi(OrderStatisticsQueryParam queryParam) {
		return mapper.lePersonalOrderStatisticsTaxi(queryParam);
	}
	public int opIndexorderstatistics(OrderStatisticsQueryParam queryParam) {
		return mapper.opIndexorderstatistics(queryParam);
	}
	public int opIndexorderstatisticsP(OrderStatisticsQueryParam queryParam) {
		return mapper.opIndexorderstatisticsP(queryParam);
	}
	public int opIndexorderstatisticsPB(OrderStatisticsQueryParam queryParam) {
		return mapper.opIndexorderstatisticsPB(queryParam);
	}
	public int opIndexorderstatisticsTaxi(OrderStatisticsQueryParam queryParam) {
		return mapper.opIndexorderstatisticsTaxi(queryParam);
	}
	public int opOrderstatistics(OrderStatisticsQueryParam queryParam) {
		return mapper.opOrderstatistics(queryParam);
	}
	public int opOrderstatisticsP(OrderStatisticsQueryParam queryParam) {
		return mapper.opOrderstatisticsP(queryParam);
	}
	public int orgCompanystatistics(OrderStatisticsQueryParam queryParam) {
		return mapper.orgCompanystatistics(queryParam);
	}
	public int orgDeptstatistics(OrderStatisticsQueryParam queryParam) {
		return mapper.orgDeptstatistics(queryParam);
	}
	
	public int validateDataExists(OrderStatisticsQueryParam queryParam) {
		return mapper.validateDataExists(queryParam);
	}
	
	public int deleteRepeatData(OrderStatisticsQueryParam queryParam) {
		return mapper.deleteRepeatData(queryParam);
	}
	public int deleteData(OrderStatisticsQueryParam queryParam) {
		return mapper.deleteData(queryParam);
	}
}
