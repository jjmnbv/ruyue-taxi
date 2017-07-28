package com.szyciov.lease.mapper;

import com.szyciov.lease.param.TocOrderManageQueryParam;

import java.util.List;
import java.util.Map;

public interface TmpTocOrderManageMapper {
	List<Map<String, Object>> getNetAboutCarOrderListByQuery(TocOrderManageQueryParam queryParam);
	
	int getNetAboutCarOrderListCountByQuery(TocOrderManageQueryParam queryParam);
	
	List<Map<String, Object>> getTaxiOrderListByQuery(TocOrderManageQueryParam queryParam);
	
	int getTaxiOrderListCountByQuery(TocOrderManageQueryParam queryParam);
	
}
	