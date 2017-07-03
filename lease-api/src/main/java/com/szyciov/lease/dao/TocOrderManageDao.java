package com.szyciov.lease.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.lease.mapper.TocOrderManageMapper;
import com.szyciov.lease.param.OrderManageQueryParam;
import com.szyciov.lease.param.TocOrderManageQueryParam;
import com.szyciov.op.entity.OpTaxiOrderReview;
import com.szyciov.param.OrdercommentQueryParam;

@Repository("TocOrderManageDao")
public class TocOrderManageDao {
	public TocOrderManageDao() {
	}

	private TocOrderManageMapper mapper;

	@Resource
	public void setMapper(TocOrderManageMapper mapper) {
		this.mapper = mapper;
	}
	
	public List<Map<String, Object>> getNetAboutCarOrderListByQuery(TocOrderManageQueryParam queryParam) {
    	return mapper.getNetAboutCarOrderListByQuery(queryParam);
    }
	
	public int getNetAboutCarOrderListCountByQuery(TocOrderManageQueryParam queryParam) {
		return mapper.getNetAboutCarOrderListCountByQuery(queryParam);
	}
	
    public List<Map<String, Object>> getTaxiOrderListByQuery(TocOrderManageQueryParam queryParam) {
    	return mapper.getTaxiOrderListByQuery(queryParam);
    }
	
	public int getTaxiOrderListCountByQuery(TocOrderManageQueryParam queryParam) {
		return mapper.getTaxiOrderListCountByQuery(queryParam);
	}
	
    public List<Map<String, Object>> getNetAboutCarOrderNOByQuery(Map<String, String> map) {
    	return mapper.getNetAboutCarOrderNOByQuery(map);
    }
	
	public List<Map<String, Object>> getNetAboutCarOrderUserByQuery(Map<String, String> map) {
		return mapper.getNetAboutCarOrderUserByQuery(map);
	}
	
	public List<Map<String, Object>> getNetAboutCarOrderDriverByQuery(Map<String, String> map) {
		return mapper.getNetAboutCarOrderDriverByQuery(map);
	}
	
    public List<Map<String, Object>> getNetAboutCarOrderExport(TocOrderManageQueryParam queryParam) {
    	return mapper.getNetAboutCarOrderExport(queryParam);
    }
	
	public List<Map<String, Object>> getTaxiOrderExport(TocOrderManageQueryParam queryParam) {
		return mapper.getTaxiOrderExport(queryParam);
	}
	
	//网约车详情
	public Map<String, Object> getOpOrderByOrderno(String orderno) {
		return mapper.getOpOrderByOrderno(orderno);
	}
	
	public Map<String, Object> getFirstOpOrderByOrderno(String orderno) {
		return mapper.getFirstOpOrderByOrderno(orderno);
	}
	
	public Map<String, Object> getOpSendOrderRecord(String orderno) {
		return mapper.getOpSendOrderRecord(orderno);
	}
	
	public List<Map<String, Object>> getOpChangeDriverListByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOpChangeDriverListByQuery(queryParam);
	}
	
	public int getOpChangeDriverCountByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOpChangeDriverCountByQuery(queryParam);
	}
	
	public List<Map<String, Object>> getOpOrderReviewListByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOpOrderReviewListByQuery(queryParam);
	}
	
	public int getOpOrderReviewCountByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOpOrderReviewCountByQuery(queryParam);
	}
	
	public List<Map<String, Object>> getOpOrderCommentListByQuery(OrdercommentQueryParam queryParam) {
		return mapper.getOpOrderCommentListByQuery(queryParam);
	}
	
	public int getOpOrderCommentCountByQuery(OrdercommentQueryParam queryParam) {
		return mapper.getOpOrderCommentCountByQuery(queryParam);
	}
	
	//出租车详情
	public Map<String, Object> getOpTaxiOrderByOrderno(String orderno) {
		return mapper.getOpTaxiOrderByOrderno(orderno);
	}
	
	public Map<String, Object> getFirstTaxiOrderByOrderno(String orderno) {
		return mapper.getFirstTaxiOrderByOrderno(orderno);
	}
	
	public Map<String, Object> getOpSendTaxiOrderRecord(String orderno) {
		return mapper.getOpSendTaxiOrderRecord(orderno);
	}
	
	public List<Map<String, Object>> getOpChangeDriverList(OrderManageQueryParam queryParam) {
		return mapper.getOpChangeDriverList(queryParam);
	}
	
	public List<Map<String, Object>> getOpChangeVehicleList(OrderManageQueryParam queryParam) {
		return mapper.getOpChangeVehicleList(queryParam);
	}
	
	public List<Map<String, Object>> getOpTaxiOrderReviewListByQuery(OpTaxiOrderReview object) {
		return mapper.getOpTaxiOrderReviewListByQuery(object);
	}
	
	public int getOpTaxiOrderReviewCountByQuery(OpTaxiOrderReview object) {
		return mapper.getOpTaxiOrderReviewCountByQuery(object);
	}
	
	public List<Map<String, Object>> getOpTaxiOrderCommentListByQuery(OrdercommentQueryParam queryParam) {
		return mapper.getOpTaxiOrderCommentListByQuery(queryParam);
	}
	
	public int getOpTaxiOrderCommentCountByQuery(OrdercommentQueryParam queryParam) {
		return mapper.getOpTaxiOrderCommentCountByQuery(queryParam);
	}
	
}
