package com.szyciov.lease.mapper;

import java.util.List;
import java.util.Map;

import com.szyciov.lease.param.OrderManageQueryParam;
import com.szyciov.lease.param.TocOrderManageQueryParam;
import com.szyciov.op.entity.OpTaxiOrderReview;
import com.szyciov.param.OrdercommentQueryParam;

public interface TocOrderManageMapper {
	List<Map<String, Object>> getNetAboutCarOrderListByQuery(TocOrderManageQueryParam queryParam);
	
	int getNetAboutCarOrderListCountByQuery(TocOrderManageQueryParam queryParam);
	
	List<Map<String, Object>> getTaxiOrderListByQuery(TocOrderManageQueryParam queryParam);
	
	int getTaxiOrderListCountByQuery(TocOrderManageQueryParam queryParam);
	
	List<Map<String, Object>> getNetAboutCarOrderNOByQuery(Map<String, String> map);
	
	List<Map<String, Object>> getNetAboutCarOrderUserByQuery(Map<String, String> map);
	
	List<Map<String, Object>> getNetAboutCarOrderDriverByQuery(Map<String, String> map);
	
	List<Map<String, Object>> getNetAboutCarOrderExport(TocOrderManageQueryParam queryParam);
	
	List<Map<String, Object>> getTaxiOrderExport(TocOrderManageQueryParam queryParam);

	//网约车详情
	Map<String, Object> getOpOrderByOrderno(String orderno);
	
	Map<String, Object> getFirstOpOrderByOrderno(String orderno);
	
	Map<String, Object> getOpSendOrderRecord(String orderno);
	
	List<Map<String, Object>> getOpChangeDriverListByQuery(OrderManageQueryParam queryParam);
	
	int getOpChangeDriverCountByQuery(OrderManageQueryParam queryParam);
	
	List<Map<String, Object>> getOpOrderReviewListByQuery(OrderManageQueryParam queryParam);
	
	int getOpOrderReviewCountByQuery(OrderManageQueryParam queryParam);
	
    List<Map<String, Object>> getOpOrderCommentListByQuery(OrdercommentQueryParam queryParam);
	
	int getOpOrderCommentCountByQuery(OrdercommentQueryParam queryParam);
	
	//出租车详情
	Map<String, Object> getOpTaxiOrderByOrderno(String orderno);
	
	Map<String, Object> getFirstTaxiOrderByOrderno(String orderno);
	
	Map<String, Object> getOpSendTaxiOrderRecord(String orderno);
	
	List<Map<String, Object>> getOpChangeDriverList(OrderManageQueryParam queryParam);
	
	List<Map<String, Object>> getOpChangeVehicleList(OrderManageQueryParam queryParam);
	
	List<Map<String, Object>> getOpTaxiOrderReviewListByQuery(OpTaxiOrderReview object);
	
	int getOpTaxiOrderReviewCountByQuery(OpTaxiOrderReview object);
	
	List<Map<String, Object>> getOpTaxiOrderCommentListByQuery(OrdercommentQueryParam queryParam);
	
	int getOpTaxiOrderCommentCountByQuery(OrdercommentQueryParam queryParam);
	
}
	