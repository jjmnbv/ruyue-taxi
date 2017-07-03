package com.szyciov.operate.dao;

import com.szyciov.driver.entity.PubDriverNews;
import com.szyciov.lease.param.OrderManageQueryParam;
import com.szyciov.op.entity.OpAccountrules;
import com.szyciov.op.entity.OpDriverchanges;
import com.szyciov.op.entity.OpOrder;
import com.szyciov.op.entity.OpOrderReview;
import com.szyciov.op.entity.OpOrdercomment;
import com.szyciov.op.entity.OpSendrecord;
import com.szyciov.op.entity.PeUserRefund;
import com.szyciov.op.entity.PubDriver;
import com.szyciov.op.entity.PubSendRules;
import com.szyciov.operate.mapper.OrderManageMapper;
import com.szyciov.param.OrdercommentQueryParam;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Repository("OrderManageDao")
public class OrderManageDao {
	
	private OrderManageMapper mapper;
	@Resource
	public void setMapper(OrderManageMapper mapper) {
		this.mapper = mapper;
	}
	
	public List<Map<String, String>> getSendRulesByName(String cityName) {
		return mapper.getSendRulesByName(cityName);
	}
	
	public List<Map<String, Object>> getOpLabourOrderListByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOpLabourOrderListByQuery(queryParam);
	}
	
	public int getOpLabourOrderCountByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOpLabourOrderCountByQuery(queryParam);
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
	
	public List<Map<String, Object>> getPeUser(String userName) {
		return mapper.getPeUser(userName);
	}
	
	public Map<String, Object> getOpOrderByOrderno(String orderno) {
		return mapper.getOpOrderByOrderno(orderno);
	}
	
	public int cancelOpOrder(String orderno) {
		return mapper.cancelOpOrder(orderno);
	}
	
	public OpOrder getOpOrder(String orderno) {
		return mapper.getOpOrder(orderno);
	}
	
	public List<Map<String, Object>> getCompanyVehicleModel(String orderno) {
		return mapper.getCompanyVehicleModel(orderno);
	}
	
	public List<PubSendRules> getSendRulesList(PubSendRules object) {
		return mapper.getSendRulesList(object);
	}
	
	public List<Map<String, Object>> getDriverListByQuery(OrderManageQueryParam queryParam) {
		return mapper.getDriverListByQuery(queryParam);
	}
	
	public int getDriverCountByQuery(OrderManageQueryParam queryParam) {
		return mapper.getDriverCountByQuery(queryParam);
	}
	
	public List<OpAccountrules> findModelPriceByModels(Map<String, Object> params) {
		return mapper.findModelPriceByModels(params);
	}
	
	public int manualSendOrder(OpOrder object) {
		return mapper.manualSendOrder(object);
	}
	
	public int insertOpSendrecord(OpSendrecord object) {
		return mapper.insertOpSendrecord(object);
	}
	
	public int changeOpDriver(Map<String, Object> params) {
		return mapper.changeOpDriver(params);
	}
	
	public int insertOpDriverchanges(OpDriverchanges object) {
		return mapper.insertOpDriverchanges(object);
	}
	
	public int applyRecheckOrder(Map<String, Object> params) {
		return mapper.applyRecheckOrder(params);
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
	
	public int updateOrderState(OpOrder object) {
		return mapper.updateOrderState(object);
	}
	
	public int applyOpOrderReview(OpOrderReview object) {
		return mapper.applyOpOrderReview(object);
	}
	
	public int insertPeUserRefund(PeUserRefund object) {
		return mapper.insertPeUserRefund(object);
	}
	
	public int updatePubDriverWorkstatus(PubDriver object) {
		return mapper.updatePubDriverWorkstatus(object);
	}
	
	public Map<String, Object> getFirstOrderByOrderno(String orderno) {
		return mapper.getFirstOrderByOrderno(orderno);
	}
	
	public int insertPubDriverNews(PubDriverNews object) {
		return mapper.insertPubDriverNews(object);
	}
	
	public List<Map<String, Object>> getOrdernoBySelect(OrderManageQueryParam queryParam) {
		return mapper.getOrdernoBySelect(queryParam);
	}
	
	public void insertOpOrdercomment(OpOrdercomment opOrdercomment) {
		mapper.insertOpOrdercomment(opOrdercomment);
	}
	
	public List<Map<String, Object>> getOpOrderCommentListByQuery(OrdercommentQueryParam queryParam) {
		return mapper.getOpOrderCommentListByQuery(queryParam);
	}
	
	public int getOpOrderCommentCountByQuery(OrdercommentQueryParam queryParam) {
		return mapper.getOpOrderCommentCountByQuery(queryParam);
	}
	
	public List<Map<String, Object>> getToCCompanySelect(Map<String, Object> params) {
		return mapper.getToCCompanySelect(params);
	}
	
	public Map<String, String> getOpOrderVehicleByOrder(String orderno) {
		return mapper.getOpOrderVehicleByOrder(orderno);
	}
	
	public void updateOpOrderVehicleByOrderno(OpOrder object) {
		mapper.updateOpOrderVehicleByOrderno(object);
	}
	
	public OpOrderReview getOpOrderreviewLastByOrderno(String orderno) {
		return mapper.getOpOrderreviewLastByOrderno(orderno);
	}
	
	public void orderReject(OpOrder object) {
		mapper.orderReject(object);
	}
	
	public PubDriver getPubDriver(String id){
		return mapper.getPubDriver(id);
		
	}
}
