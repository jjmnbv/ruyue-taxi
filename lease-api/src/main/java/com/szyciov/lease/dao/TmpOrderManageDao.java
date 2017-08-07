package com.szyciov.lease.dao;

import com.szyciov.lease.mapper.TmpOrderManageMapper;
import com.szyciov.lease.param.OrderManageQueryParam;
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

	public List<Map<String, Object>> getOrgCurrentOrderListByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOrgCurrentOrderListByQuery(queryParam);
	}
	
	public int getOrgCurrentOrderCountByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOrgCurrentOrderCountByQuery(queryParam);
	}
	
	public List<Map<String, Object>> getOrgAbnormalOrderListByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOrgAbnormalOrderListByQuery(queryParam);
	}
	
	public int getOrgAbnormalOrderCountByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOrgAbnormalOrderCountByQuery(queryParam);
	}
	
	public List<Map<String, Object>> getOrgWasabnormalOrderListByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOrgWasabnormalOrderListByQuery(queryParam);
	}
	
	public int getOrgWasabnormalOrderCountByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOrgWasabnormalOrderCountByQuery(queryParam);
	}
	
	public List<Map<String, Object>> getOrgCompleteOrderListByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOrgCompleteOrderListByQuery(queryParam);
	}
	
	public int getOrgCompleteOrderCountByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOrgCompleteOrderCountByQuery(queryParam);
	}
	
	public List<Map<String, Object>> getOrgWaitgatheringOrderListByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOrgWaitgatheringOrderListByQuery(queryParam);
	}
	
	public int getOrgWaitgatheringOrderCountByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOrgWaitgatheringOrderCountByQuery(queryParam);
	}
	
}
