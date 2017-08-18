package com.szyciov.operate.mapper;

import java.util.List;
import java.util.Map;

import com.szyciov.driver.entity.OrderInfoDetail;
import com.szyciov.driver.entity.PubDriverNews;
import com.szyciov.lease.entity.PubCityAddr;
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
import com.szyciov.param.OrdercommentQueryParam;
import org.apache.ibatis.annotations.Param;

public interface OrderManageMapper {
	
	List<Map<String, String>> getSendRulesByName(String cityName);
	
	List<Map<String, Object>> getOpLabourOrderListByQuery(OrderManageQueryParam queryParam);
	
	int getOpLabourOrderCountByQuery(OrderManageQueryParam queryParam);
	
	List<Map<String, Object>> getOpCurrentOrderListByQuery(OrderManageQueryParam queryParam);
	
	int getOpCurrentOrderCountByQuery(OrderManageQueryParam queryParam);
	
	List<Map<String, Object>> getOpAbnormalOrderListByQuery(OrderManageQueryParam queryParam);
	
	int getOpAbnormalOrderCountByQuery(OrderManageQueryParam queryParam);
	
	List<Map<String, Object>> getOpWasabnormalOrderListByQuery(OrderManageQueryParam queryParam);
	
	int getOpWasabnormalOrderCountByQuery(OrderManageQueryParam queryParam);

    List<Map<String, Object>> getOpWaitgatheringOrderListByQuery(OrderManageQueryParam queryParam);

    int getOpWaitgatheringOrderCountByQuery(OrderManageQueryParam queryParam);
	
	List<Map<String, Object>> getOpCompleteOrderListByQuery(OrderManageQueryParam queryParam);
	
	int getOpCompleteOrderCountByQuery(OrderManageQueryParam queryParam);
	
    List<Map<String, Object>> getOpCancelOrderListByQuery(OrderManageQueryParam queryParam);

    int getOpCancelOrderCountByQuery(OrderManageQueryParam queryParam);

	List<Map<String, Object>> getPeUser(String userName);
	
	Map<String, Object> getOpOrderByOrderno(String orderno);
	
	int cancelOpOrder(String orderno);
	
	OpOrder getOpOrder(String orderno);
	
	List<Map<String, Object>> getCompanyVehicleModel(String orderno);
	
	List<PubSendRules> getSendRulesList(PubSendRules object);
	
	List<Map<String, Object>> getDriverListByQuery(OrderManageQueryParam queryParam);
	
	int getDriverCountByQuery(OrderManageQueryParam queryParam);
	
	List<OpAccountrules> findModelPriceByModels(Map<String, Object> params);
	
	int manualSendOrder(OpOrder object);
	
	int insertOpSendrecord(OpSendrecord object);
	
	int changeOpDriver(Map<String, Object> params);
	
	int insertOpDriverchanges(OpDriverchanges object);
	
	int applyRecheckOrder(Map<String, Object> params);
	
	Map<String, Object> getOpSendOrderRecord(String orderno);
	
	List<Map<String, Object>> getOpChangeDriverListByQuery(OrderManageQueryParam queryParam);
	
	int getOpChangeDriverCountByQuery(OrderManageQueryParam queryParam);
	
	List<Map<String, Object>> getOpOrderReviewListByQuery(OrderManageQueryParam queryParam);
	
	int getOpOrderReviewCountByQuery(OrderManageQueryParam queryParam);
	
	int updateOrderState(OpOrder object);
	
	int applyOpOrderReview(OpOrderReview object);
	
	int insertPeUserRefund(PeUserRefund object);
	
	int updatePubDriverWorkstatus(PubDriver object);
	
	Map<String, Object> getFirstOrderByOrderno(String orderno);
	
	int insertPubDriverNews(PubDriverNews object);
	
	List<Map<String, Object>> getOrdernoBySelect(OrderManageQueryParam queryParam);
	
	void insertOpOrdercomment(OpOrdercomment opOrdercomment);
	
	List<Map<String, Object>> getOpOrderCommentListByQuery(OrdercommentQueryParam queryParam);
	
	int getOpOrderCommentCountByQuery(OrdercommentQueryParam queryParam);
	
	List<Map<String, Object>> getToCCompanySelect(Map<String, Object> params);
	
	Map<String, String> getOpOrderVehicleByOrder(String orderno);
	
	void updateOpOrderVehicleByOrderno(OpOrder object);
	
	OpOrderReview getOpOrderreviewLastByOrderno(String orderno);
	
	void orderReject(OpOrder object);
	
	PubDriver getPubDriver(@Param("id") String id);

    void updatePubDriverLeisure(String driverid);

    List<Map<String, Object>> getBelongCompanySelect(OrderManageQueryParam queryParam);

    OrderInfoDetail getOpOrderInfoByOrderno(String orderno);

    void updateOpOrderInfo(OrderInfoDetail infoDetail);

    PubCityAddr getPubCityByName(String cityname);

    void updatePubDriver(PubDriver pubDriver);
	
}
