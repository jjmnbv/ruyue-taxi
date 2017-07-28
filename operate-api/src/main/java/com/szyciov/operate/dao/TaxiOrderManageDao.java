package com.szyciov.operate.dao;

import com.szyciov.lease.param.OrderManageQueryParam;
import com.szyciov.op.entity.OpTaxiOrder;
import com.szyciov.op.entity.OpTaxiOrderReview;
import com.szyciov.op.entity.OpTaxidriverchanges;
import com.szyciov.op.entity.OpTaxiordercomment;
import com.szyciov.op.entity.OpTaxisendrecord;
import com.szyciov.op.entity.OpTaxivehiclechanges;
import com.szyciov.op.entity.PeUserRefund;
import com.szyciov.op.entity.PubDriver;
import com.szyciov.operate.mapper.TaxiOrderManageMapper;
import com.szyciov.param.OrdercommentQueryParam;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Repository("TaxiOrderManageDao")
public class TaxiOrderManageDao {
	
	private TaxiOrderManageMapper mapper;
	@Resource
	public void setMapper(TaxiOrderManageMapper mapper) {
		this.mapper = mapper;
	}
	
	public List<Map<String, Object>> getOpMissTaxiOrderListByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOpMissTaxiOrderListByQuery(queryParam);
	}
	
	public int getOpMissTaxiOrderCountByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOpMissTaxiOrderCountByQuery(queryParam);
	}
	
	public List<Map<String, Object>> getOpLabourTaxiOrderListByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOpLabourTaxiOrderListByQuery(queryParam);
	}
	
	public int getOpLabourTaxiOrderCountByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOpLabourTaxiOrderCountByQuery(queryParam);
	}
	
	public List<Map<String, Object>> getOpCurrentTaxiOrderListByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOpCurrentTaxiOrderListByQuery(queryParam);
	}
	
	public int getOpCurrentTaxiOrderCountByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOpCurrentTaxiOrderCountByQuery(queryParam);
	}
	
	public List<Map<String, Object>> getOpAbnormalTaxiOrderListByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOpAbnormalTaxiOrderListByQuery(queryParam);
	}
	
	public int getOpAbnormalTaxiOrderCountByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOpAbnormalTaxiOrderCountByQuery(queryParam);
	}
	
	public List<Map<String, Object>> getOpWasabnormalTaxiOrderListByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOpWasabnormalTaxiOrderListByQuery(queryParam);
	}
	
	public int getOpWasabnormalTaxiOrderCountByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOpWasabnormalTaxiOrderCountByQuery(queryParam);
	}
	
	public List<Map<String, Object>> getOpCompleteTaxiOrderListByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOpCompleteTaxiOrderListByQuery(queryParam);
	}
	
	public int getOpCompleteTaxiOrderCountByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOpCompleteTaxiOrderCountByQuery(queryParam);
	}
	
	public List<Map<String, Object>> getOpWaitgatheringTaxiOrderListByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOpWaitgatheringTaxiOrderListByQuery(queryParam);
	}
	
	public int getOpWaitgatheringTaxiOrderCountByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOpWaitgatheringTaxiOrderCountByQuery(queryParam);
	}
	
	public Map<String, Object> getOpTaxiOrderByOrderno(String orderno) {
		return mapper.getOpTaxiOrderByOrderno(orderno);
	}
	
	public void updateOpTaxiOrderByOrderno(OpTaxiOrder object) {
		mapper.updateOpTaxiOrderByOrderno(object);
	}
	
	public OpTaxiOrder getOpTaxiOrder(String orderno) {
		return mapper.getOpTaxiOrder(orderno);
	}
	
	public List<Map<String, Object>> getOpTaxiOrderReviewListByQuery(OpTaxiOrderReview object) {
		return mapper.getOpTaxiOrderReviewListByQuery(object);
	}
	
	public int getOpTaxiOrderReviewCountByQuery(OpTaxiOrderReview object) {
		return mapper.getOpTaxiOrderReviewCountByQuery(object);
	}
	
	public void insertOpTaxiOrderReview(OpTaxiOrderReview object) {
		mapper.insertOpTaxiOrderReview(object);
	}
	
	public void insertPeUserRefund(PeUserRefund object) {
		mapper.insertPeUserRefund(object);
	}
	
	public List<Map<String, Object>> getDriverListByQuery(OrderManageQueryParam queryParam) {
		return mapper.getDriverListByQuery(queryParam);
	}
	
	public int getDriverCountByQuery(OrderManageQueryParam queryParam) {
		return mapper.getDriverCountByQuery(queryParam);
	}
	
	public List<Map<String, String>> getTaxiPlatonoBySelect(Map<String, String> params) {
		return mapper.getTaxiPlatonoBySelect(params);
	}
	
	public void insertOpTaxisendrecord(OpTaxisendrecord object) {
		mapper.insertOpTaxisendrecord(object);
	}
	
	public void insertOpTaxivehiclechanges(OpTaxivehiclechanges object) {
		mapper.insertOpTaxivehiclechanges(object);
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
	
	public List<Map<String, Object>> getOpTaxiOrderCommentListByQuery(OrdercommentQueryParam queryParam) {
		return mapper.getOpTaxiOrderCommentListByQuery(queryParam);
	}
	
	public int getOpTaxiOrderCommentCountByQuery(OrdercommentQueryParam queryParam) {
		return mapper.getOpTaxiOrderCommentCountByQuery(queryParam);
	}
	
	public void insertOpTaxiordercomment(OpTaxiordercomment object) {
		mapper.insertOpTaxiordercomment(object);
	}
	
	public void insertOpTaxidriverchanges(OpTaxidriverchanges object) {
		mapper.insertOpTaxidriverchanges(object);
	}
	
	public Map<String, Object> getInServiceOrderByDriver(String driverid) {
		return mapper.getInServiceOrderByDriver(driverid);
	}
	
	public OpTaxiOrderReview getOpTaxiOrderreviewLastByOrderno(String orderno) {
		return mapper.getOpTaxiOrderreviewLastByOrderno(orderno);
	}
	
	public void updateOpTaxiorderReview(OpTaxiOrder object) {
		mapper.updateOpTaxiorderReview(object);
	}
	
	public void orderReject(OpTaxiOrder object) {
		mapper.orderReject(object);
	}

	public PubDriver getPubDriverById(String driverid) {
        return mapper.getPubDriverById(driverid);
    }
	
}
