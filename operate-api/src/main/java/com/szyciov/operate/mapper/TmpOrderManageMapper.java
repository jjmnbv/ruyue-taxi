package com.szyciov.operate.mapper;

import com.szyciov.lease.param.OrderManageQueryParam;

import java.util.List;
import java.util.Map;

public interface TmpOrderManageMapper {
	
	List<Map<String, Object>> getOpCurrentOrderListByQuery(OrderManageQueryParam queryParam);
	
	int getOpCurrentOrderCountByQuery(OrderManageQueryParam queryParam);
	
	List<Map<String, Object>> getOpAbnormalOrderListByQuery(OrderManageQueryParam queryParam);
	
	int getOpAbnormalOrderCountByQuery(OrderManageQueryParam queryParam);
	
	List<Map<String, Object>> getOpWasabnormalOrderListByQuery(OrderManageQueryParam queryParam);
	
	int getOpWasabnormalOrderCountByQuery(OrderManageQueryParam queryParam);
	
	List<Map<String, Object>> getOpCompleteOrderListByQuery(OrderManageQueryParam queryParam);
	
	int getOpCompleteOrderCountByQuery(OrderManageQueryParam queryParam);
	
	List<Map<String, Object>> getOpWaitgatheringOrderListByQuery(OrderManageQueryParam queryParam);
	
	int getOpWaitgatheringOrderCountByQuery(OrderManageQueryParam queryParam);

}
