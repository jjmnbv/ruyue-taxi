package com.szyciov.operate.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.szyciov.op.param.TourFeeManagementQueryParam;

public interface TourFeeManagementMapper {
	
	List<Map<String, Object>> getTourFeeListByQuery(TourFeeManagementQueryParam queryParam);
	
	int getTourFeeListCountByQuery(TourFeeManagementQueryParam queryParam);
	
	List<Map<String, Object>> getOrderNo(@Param(value = "orderno") String orderno);
	
	List<Map<String, Object>> getDriverByNameOrPhone(@Param(value = "driver") String driver);
	
	List<Map<String, Object>> getCompanyNameById();
	
	List<Map<String, Object>> getJobnumByJobnum(@Param(value = "jobnum") String jobnum);
	
	List<Map<String, Object>> getTourFeeListExport(TourFeeManagementQueryParam queryParam);
}
