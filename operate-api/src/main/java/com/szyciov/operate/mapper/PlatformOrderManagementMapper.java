package com.szyciov.operate.mapper;

import java.util.List;
import java.util.Map;

import com.szyciov.op.param.PlatformOrderManagementParam;

public interface PlatformOrderManagementMapper {
	List<Map<String, Object>> getNetAboutCarOrderUserByQuery(Map<String, String> map);
	List<Map<String, Object>> getNetAboutCarOrderDriverByQuery(Map<String, String> map);
	List<Map<String, Object>> getNetAboutCarOrderListByQuery(PlatformOrderManagementParam queryParam);
	int getNetAboutCarOrderListCountByQuery(PlatformOrderManagementParam queryParam);
	List<Map<String, Object>> getPartnerCompanySelect(Map<String,Object> params);
	List<Map<String, Object>> getNetAboutCarOrderExport(PlatformOrderManagementParam queryParam);
}
