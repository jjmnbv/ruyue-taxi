package com.szyciov.operate.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.op.param.TourFeeManagementQueryParam;
import com.szyciov.operate.mapper.TourFeeManagementMapper;

@Repository("TourFeeManagementDao")
public class TourFeeManagementDao {
	public TourFeeManagementDao() {
	}

	private TourFeeManagementMapper mapper;

	@Resource
	public void setMapper(TourFeeManagementMapper mapper) {
		this.mapper = mapper;
	}
	
    public List<Map<String, Object>> getTourFeeListByQuery(TourFeeManagementQueryParam queryParam) {
    	return mapper.getTourFeeListByQuery(queryParam);
    }
	
    public int getTourFeeListCountByQuery(TourFeeManagementQueryParam queryParam) {
    	return mapper.getTourFeeListCountByQuery(queryParam);
    }
	
    public List<Map<String, Object>> getOrderNo(String orderno) {
    	return mapper.getOrderNo(orderno);
    }
	
    public List<Map<String, Object>> getDriverByNameOrPhone(String driver) {
    	return mapper.getDriverByNameOrPhone(driver);
    }
	
    public List<Map<String, Object>> getCompanyNameById() {
    	return mapper.getCompanyNameById();
    }
    
    public List<Map<String, Object>> getJobnumByJobnum(String jobnum) {
    	return mapper.getJobnumByJobnum(jobnum);
    }
    
    public List<Map<String, Object>> getTourFeeListExport(TourFeeManagementQueryParam queryParam) {
    	return mapper.getTourFeeListExport(queryParam);
    }
	
}
