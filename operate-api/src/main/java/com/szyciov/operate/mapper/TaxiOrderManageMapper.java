package com.szyciov.operate.mapper;

import java.util.List;
import java.util.Map;

import com.szyciov.lease.param.OrderManageQueryParam;
import com.szyciov.op.entity.OpTaxiOrder;
import com.szyciov.op.entity.OpTaxiOrderReview;
import com.szyciov.op.entity.OpTaxidriverchanges;
import com.szyciov.op.entity.OpTaxiordercomment;
import com.szyciov.op.entity.OpTaxisendrecord;
import com.szyciov.op.entity.OpTaxivehiclechanges;
import com.szyciov.op.entity.PeUserRefund;
import com.szyciov.op.entity.PubDriver;
import com.szyciov.param.OrdercommentQueryParam;

public interface TaxiOrderManageMapper {
	
	List<Map<String, Object>> getOpMissTaxiOrderListByQuery(OrderManageQueryParam queryParam);
	
	int getOpMissTaxiOrderCountByQuery(OrderManageQueryParam queryParam);
	
	List<Map<String, Object>> getOpLabourTaxiOrderListByQuery(OrderManageQueryParam queryParam);
	
	int getOpLabourTaxiOrderCountByQuery(OrderManageQueryParam queryParam);
	
	List<Map<String, Object>> getOpCurrentTaxiOrderListByQuery(OrderManageQueryParam queryParam);
	
	int getOpCurrentTaxiOrderCountByQuery(OrderManageQueryParam queryParam);
	
	List<Map<String, Object>> getOpAbnormalTaxiOrderListByQuery(OrderManageQueryParam queryParam);
	
	int getOpAbnormalTaxiOrderCountByQuery(OrderManageQueryParam queryParam);
	
	List<Map<String, Object>> getOpWasabnormalTaxiOrderListByQuery(OrderManageQueryParam queryParam);
	
	int getOpWasabnormalTaxiOrderCountByQuery(OrderManageQueryParam queryParam);
	
	List<Map<String, Object>> getOpCompleteTaxiOrderListByQuery(OrderManageQueryParam queryParam);
	
	int getOpCompleteTaxiOrderCountByQuery(OrderManageQueryParam queryParam);
	
	List<Map<String, Object>> getOpWaitgatheringTaxiOrderListByQuery(OrderManageQueryParam queryParam);
	
	int getOpWaitgatheringTaxiOrderCountByQuery(OrderManageQueryParam queryParam);
	
	Map<String, Object> getOpTaxiOrderByOrderno(String orderno);
	
	void updateOpTaxiOrderByOrderno(OpTaxiOrder object);
	
	OpTaxiOrder getOpTaxiOrder(String orderno);
	
	List<Map<String, Object>> getOpTaxiOrderReviewListByQuery(OpTaxiOrderReview object);
	
	int getOpTaxiOrderReviewCountByQuery(OpTaxiOrderReview object);
	
	void insertOpTaxiOrderReview(OpTaxiOrderReview object);
	
	void insertPeUserRefund(PeUserRefund object);
	
	List<Map<String, Object>> getDriverListByQuery(OrderManageQueryParam queryParam);
	
	int getDriverCountByQuery(OrderManageQueryParam queryParam);
	
	List<Map<String, String>> getTaxiPlatonoBySelect(Map<String, String> params);
	
	void insertOpTaxisendrecord(OpTaxisendrecord object);
	
	void insertOpTaxivehiclechanges(OpTaxivehiclechanges object);
	
	Map<String, Object> getFirstTaxiOrderByOrderno(String orderno);
	
	Map<String, Object> getOpSendTaxiOrderRecord(String orderno);
	
	List<Map<String, Object>> getOpChangeDriverList(OrderManageQueryParam queryParam);
	
	List<Map<String, Object>> getOpChangeVehicleList(OrderManageQueryParam queryParam);
	
	List<Map<String, Object>> getOpTaxiOrderCommentListByQuery(OrdercommentQueryParam queryParam);
	
	int getOpTaxiOrderCommentCountByQuery(OrdercommentQueryParam queryParam);
	
	void insertOpTaxiordercomment(OpTaxiordercomment object);
	
	void insertOpTaxidriverchanges(OpTaxidriverchanges object);
	
	Map<String, Object> getInServiceOrderByDriver(String driverid);
	
	OpTaxiOrderReview getOpTaxiOrderreviewLastByOrderno(String orderno);
	
	void updateOpTaxiorderReview(OpTaxiOrder object);
	
	void orderReject(OpTaxiOrder object);

    PubDriver getPubDriverById(String driverid);
	
}
