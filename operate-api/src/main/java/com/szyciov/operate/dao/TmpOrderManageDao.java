package com.szyciov.operate.dao;

import com.szyciov.lease.param.OrderManageQueryParam;
import com.szyciov.operate.mapper.TmpOrderManageMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Repository("TmpOrderManageDao")
public class TmpOrderManageDao {
	
	private TmpOrderManageMapper mapper;
	@Resource
	public void setMapper(TmpOrderManageMapper mapper) {
		this.mapper = mapper;
	}
	
	public List<Map<String, Object>> getOpCurrentOrderListByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOpCurrentOrderListByQuery(queryParam);
	}
	
	public int getOpCurrentOrderCountByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOpCurrentOrderCountByQuery(queryParam);
	}
	
	public List<Map<String, Object>> getOpAbnormalOrderListByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOpAbnormalOrderListByQuery(queryParam);
	}
	
	public int getOpAbnormalOrderCountByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOpAbnormalOrderCountByQuery(queryParam);
	}
	
	public List<Map<String, Object>> getOpWasabnormalOrderListByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOpWasabnormalOrderListByQuery(queryParam);
	}
	
	public int getOpWasabnormalOrderCountByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOpWasabnormalOrderCountByQuery(queryParam);
	}
	
	public List<Map<String, Object>> getOpCompleteOrderListByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOpCompleteOrderListByQuery(queryParam);
	}
	
	public int getOpCompleteOrderCountByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOpCompleteOrderCountByQuery(queryParam);
	}
	
	public List<Map<String, Object>> getOpWaitgatheringOrderListByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOpWaitgatheringOrderListByQuery(queryParam);
	}
	
	public int getOpWaitgatheringOrderCountByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOpWaitgatheringOrderCountByQuery(queryParam);
	}
	
}
