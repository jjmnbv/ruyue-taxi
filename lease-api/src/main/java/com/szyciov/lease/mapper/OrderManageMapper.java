package com.szyciov.lease.mapper;

import com.szyciov.driver.entity.PubDriverNews;
import com.szyciov.lease.entity.LeAccountRules;
import com.szyciov.lease.entity.OrgDriverchanges;
import com.szyciov.lease.entity.OrgOrdercomment;
import com.szyciov.lease.entity.OrgOrganCompanyRef;
import com.szyciov.lease.entity.OrgSendrecord;
import com.szyciov.lease.entity.OrgUserRefund;
import com.szyciov.lease.entity.PubDriver;
import com.szyciov.lease.param.OrderManageQueryParam;
import com.szyciov.op.entity.PubSendRules;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.org.entity.OrgOrderReview;
import com.szyciov.param.OrdercommentQueryParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrderManageMapper {
	List<Map<String, Object>> getOrgLabourOrderListByQuery(OrderManageQueryParam queryParam);
	
	int getOrgLabourOrderCountByQuery(OrderManageQueryParam queryParam);
	
	List<Map<String, Object>> getOrgCurrentOrderListByQuery(OrderManageQueryParam queryParam);
	
	int getOrgCurrentOrderCountByQuery(OrderManageQueryParam queryParam);
	
	List<Map<String, Object>> getOrgAbnormalOrderListByQuery(OrderManageQueryParam queryParam);
	
	int getOrgAbnormalOrderCountByQuery(OrderManageQueryParam queryParam);
	
	List<Map<String, Object>> getOrgWasabnormalOrderListByQuery(OrderManageQueryParam queryParam);
	
	int getOrgWasabnormalOrderCountByQuery(OrderManageQueryParam queryParam);
	
	List<Map<String, Object>> getOrgCompleteOrderListByQuery(OrderManageQueryParam queryParam);
	
	int getOrgCompleteOrderCountByQuery(OrderManageQueryParam queryParam);
	
	List<Map<String, Object>> getOrgWaitgatheringOrderListByQuery(OrderManageQueryParam queryParam);
	
	int getOrgWaitgatheringOrderCountByQuery(OrderManageQueryParam queryParam);
	
	Map<String, Object> getOrgOrderByOrderno(String orderno);
	
	OrgOrder getOrgOrder(String orderno);
	
	List<Map<String, Object>> getOrgUser(Map<String, Object> params);
	
	List<Map<String, Object>> getCompanyVehicleModel(Map<String, String> params);
	
	Map<String, Object> getOrgWaitingOrderByOrderno(String orderno);
	
	List<Map<String, Object>> getOrgDriverByQuery(OrderManageQueryParam queryParam);
	
	int getOrgDriverCountByQuery(OrderManageQueryParam queryParam);
	
	int manualSendOrder(OrgOrder orgOrder);
	
	int cancelOrgOrder(String orderno);
	
	Map<String, Object> getOrgSendOrderRecord(String orderno);
	
	List<Map<String, Object>> getOrgChangeDriverListByQuery(OrderManageQueryParam queryParam);
	
	int getOrgChangeDriverListCountByQuery(OrderManageQueryParam queryParam);
	
	List<Map<String, Object>> getOrgOrderReviewListRecord(OrderManageQueryParam queryParam);
	
	int getOrgOrderReviewListCountRecord(OrderManageQueryParam queryParam);
	
	int applyOrgOrderReview(OrgOrderReview orgOrderReview);
	
	int orgOrderRemark(Map<String, Object> params);
	
	int updateOrderState(OrgOrder orgOrder);
	
	int changeOrgDriver(Map<String, Object> params);
	
	int applyRecheckOrder(Map<String, String> params);
	
	int insertOrgSendrecord(OrgSendrecord orgSendrecord);
	
	int insertOrgDriverchanges(OrgDriverchanges orgDriverchanges);
	
	void insertOrgOrdercomment(OrgOrdercomment orgOrdercomment);
	
	List<Map<String, String>> getSendRulesByName(PubSendRules pubSendRules);
	
	List<LeAccountRules> findModelPriceByModels(Map<String, Object> params);
	
	int insertOrgUserrefund(OrgUserRefund orgUserRefund);
	
	OrgOrganCompanyRef getOrgOrganCompanyRefByOrgCom(OrgOrganCompanyRef orgOrganCompanyRef);
	
	int updateOrgOrganCompanyRef(OrgOrganCompanyRef orgOrganCompanyRef);
	
	List<PubSendRules> getLeSendrulesList(PubSendRules pubSendRules);
	
	List<Map<String, Object>> getOrganByName(Map<String, Object> params);
	
	int updatePubDriverWorkstatus(PubDriver pubDriver);
	
	Map<String, Object> getFirstOrderByOrderno(String orderno);
	
	int insertPubDriverNews(PubDriverNews pubDriverNews);
	
	Map<String, String> getOrgOrderVehicleByOrder(String orderno);
	
	void updateOrgOrderVehicleByOrderno(OrgOrder object);
	
	List<Map<String, Object>> getOrgOrderCommentListByQuery(OrdercommentQueryParam queryParam);
	
	int getOrgOrderCommentCountByQuery(OrdercommentQueryParam queryParam);
	
	List<Map<String, Object>> getOrdernoBySelect(OrderManageQueryParam queryParam);
	
	OrgOrderReview getOrgOrderreviewLastByOrderno(String orderno);
	
	void orderReject(OrgOrder object);
	public PubDriver getPubDriver(@Param("id") String id);

    List<Map<String, Object>> getBelongLeaseCompanySelect(Map<String, Object> params);

    void updatePubDriverLeisure(String driverid);

}
