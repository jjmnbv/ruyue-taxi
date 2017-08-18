package com.szyciov.lease.mapper;

import java.util.List;
import java.util.Map;

import com.szyciov.lease.param.OrderManageQueryParam;
import com.szyciov.lease.param.TobOrderManageQueryParam;
import com.szyciov.op.entity.OpTaxiOrderReview;
import com.szyciov.param.OrdercommentQueryParam;

public interface TobOrderManageMapper {
	List<Map<String, Object>> getNetAboutCarOrderListByQuery(TobOrderManageQueryParam queryParam);
	
	int getNetAboutCarOrderListCountByQuery(TobOrderManageQueryParam queryParam);
	
	List<Map<String, Object>> getTaxiOrderListByQuery(TobOrderManageQueryParam queryParam);
	
	int getTaxiOrderListCountByQuery(TobOrderManageQueryParam queryParam);
	
	List<Map<String, Object>> getNetAboutCarOrderNOByQuery(Map<String, String> map);
	
	List<Map<String, Object>> getNetAboutCarOrderUserByQuery(Map<String, String> map);
	
	List<Map<String, Object>> getNetAboutCarOrderDriverByQuery(Map<String, String> map);
	
	List<Map<String, Object>> getNetAboutCarOrderExport(TobOrderManageQueryParam queryParam);
	
	List<Map<String, Object>> getTaxiOrderExport(TobOrderManageQueryParam queryParam);

	//网约车详情
	Map<String, Object> getOrgOrderByOrderno(String orderno);
	
	Map<String, Object> getFirstOrgOrderByOrderno(String orderno);
	
	Map<String, Object> getOrgSendOrderRecord(String orderno);
	
	List<Map<String, Object>> getOrgChangeDriverListByQuery(OrderManageQueryParam queryParam);
	
	int getOrgChangeDriverCountByQuery(OrderManageQueryParam queryParam);
	
	List<Map<String, Object>> getOpOrderReviewListByQuery(OrderManageQueryParam queryParam);
	
	int getOpOrderReviewCountByQuery(OrderManageQueryParam queryParam);
	
    List<Map<String, Object>> getOrgOrderCommentListByQuery(OrdercommentQueryParam queryParam);
	
	int getOrgOrderCommentCountByQuery(OrdercommentQueryParam queryParam);
	
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

	List<Map<String, Object>> getPartnerCompanySelect(Map<String, Object> params);
	
}
	