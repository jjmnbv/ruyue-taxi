package com.szyciov.operate.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.op.param.PlatformOrderManagementParam;
import com.szyciov.operate.mapper.PlatformOrderManagementMapper;

@Repository("PlatformOrderManagementDao")
public class PlatformOrderManagementDao {
	public PlatformOrderManagementDao() {
	}

	private PlatformOrderManagementMapper mapper;

	@Resource
	public void setMapper(PlatformOrderManagementMapper mapper) {
		this.mapper = mapper;
	}
	public List<Map<String, Object>> getNetAboutCarOrderUserByQuery(Map<String, String> map) {
		return mapper.getNetAboutCarOrderUserByQuery(map);
	}
	public List<Map<String, Object>> getNetAboutCarOrderDriverByQuery(Map<String, String> map) {
		return mapper.getNetAboutCarOrderDriverByQuery(map);
	}
	public List<Map<String, Object>> getNetAboutCarOrderListByQuery(PlatformOrderManagementParam queryParam) {
    	return mapper.getNetAboutCarOrderListByQuery(queryParam);
    }
	
	public int getNetAboutCarOrderListCountByQuery(PlatformOrderManagementParam queryParam) {
		return mapper.getNetAboutCarOrderListCountByQuery(queryParam);
	}
	public List<Map<String, Object>> getPartnerCompanySelect(Map<String,Object> params) {
		return mapper.getPartnerCompanySelect(params);
	}
	public List<Map<String, Object>> getNetAboutCarOrderExport(PlatformOrderManagementParam queryParam) {
		return mapper.getNetAboutCarOrderExport(queryParam);
	}

}
