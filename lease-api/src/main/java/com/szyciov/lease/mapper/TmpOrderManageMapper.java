package com.szyciov.lease.mapper;

import com.szyciov.lease.param.OrderManageQueryParam;

import java.util.List;
import java.util.Map;

public interface TmpOrderManageMapper {

	List<Map<String, Object>> getOrgCurrentOrderListByQuery(OrderManageQueryParam queryParam);
	
	int getOrgCurrentOrderCountByQuery(OrderManageQueryParam queryParam);
	
	List<Map<String, Object>> getOrgAbnormalOrderListByQuery(OrderManageQueryParam queryParam);
	
	int getOrgAbnormalOrderCountByQuery(OrderManageQueryParam queryParam);
	
	List<Map<String, Object>> getOrgWasabnormalOrderListByQuery(OrderManageQueryParam queryParam);
	
	int getOrgWasabnormalOrderCountByQuery(OrderManageQueryParam queryParam);
	
	List<Map<String, Object>> getOrgCompleteOrderListByQuery(OrderManageQueryParam queryParam);
	
	int getOrgCompleteOrderCountByQuery(OrderManageQueryParam queryParam);
	
	List<Map<String, Object>> getOrgWaitgatheringOrderListByQuery(OrderManageQueryParam queryParam);
	
	int getOrgWaitgatheringOrderCountByQuery(OrderManageQueryParam queryParam);
	
}
