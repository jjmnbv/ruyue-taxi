package com.szyciov.lease.dao;

import com.szyciov.driver.entity.PubDriverNews;
import com.szyciov.lease.entity.LeAccountRules;
import com.szyciov.lease.entity.OrgDriverchanges;
import com.szyciov.lease.entity.OrgOrdercomment;
import com.szyciov.lease.entity.OrgOrganCompanyRef;
import com.szyciov.lease.entity.OrgSendrecord;
import com.szyciov.lease.entity.OrgUserRefund;
import com.szyciov.lease.entity.PubDriver;
import com.szyciov.lease.mapper.OrderManageMapper;
import com.szyciov.lease.param.OrderManageQueryParam;
import com.szyciov.op.entity.PubSendRules;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.org.entity.OrgOrderReview;
import com.szyciov.param.OrdercommentQueryParam;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Repository("orderManageDao")
public class OrderManageDao {
	private OrderManageMapper mapper;

	@Resource
	public void setMapper(OrderManageMapper mapper) {
		this.mapper = mapper;
	}
	
	public List<Map<String, Object>> getOrgLabourOrderListByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOrgLabourOrderListByQuery(queryParam);
	}
	
	public int getOrgLabourOrderCountByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOrgLabourOrderCountByQuery(queryParam);
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
	
	public Map<String, Object> getOrgOrderByOrderno(String orderno) {
		return mapper.getOrgOrderByOrderno(orderno);
	}
	
	public OrgOrder getOrgOrder(String orderno) {
		return mapper.getOrgOrder(orderno);
	}
	
	public List<Map<String, Object>> getOrgUser(Map<String, Object> params) {
		return mapper.getOrgUser(params);
	}
	
	public List<Map<String, Object>> getCompanyVehicleModel(Map<String, String> params) {
		return mapper.getCompanyVehicleModel(params);
	}
	
	public Map<String, Object> getOrgWaitingOrderByOrderno(String orderno) {
		return mapper.getOrgWaitingOrderByOrderno(orderno);
	}
	
	public List<Map<String, Object>> getOrgDriverByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOrgDriverByQuery(queryParam);
	}
	
	public int getOrgDriverCountByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOrgDriverCountByQuery(queryParam);
	}
	
	public int manualSendOrder(OrgOrder orgOrder) {
		return mapper.manualSendOrder(orgOrder);
	}
	
	public int cancelOrgOrder(String orderno) {
		return mapper.cancelOrgOrder(orderno);
	}
	
	public Map<String, Object> getOrgSendOrderRecord(String orderno) {
		return mapper.getOrgSendOrderRecord(orderno);
	}
	
	public List<Map<String, Object>> getOrgChangeDriverListByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOrgChangeDriverListByQuery(queryParam);
	}
	
	public int getOrgChangeDriverListCountByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOrgChangeDriverListCountByQuery(queryParam);
	}
	
	public List<Map<String, Object>> getOrgOrderReviewListRecord(OrderManageQueryParam queryParam) {
		return mapper.getOrgOrderReviewListRecord(queryParam);
	}
	
	public int getOrgOrderReviewListCountRecord(OrderManageQueryParam queryParam) {
		return mapper.getOrgOrderReviewListCountRecord(queryParam);
	}
	
	public int applyOrgOrderReview(OrgOrderReview orderReview) {
		return mapper.applyOrgOrderReview(orderReview);
	}
	
	public int orgOrderRemark(Map<String, Object> params) {
		return mapper.orgOrderRemark(params);
	}
	
	public int updateOrderState(OrgOrder orgOrder) {
		return mapper.updateOrderState(orgOrder);
	}
	
	public int changeOrgDriver(Map<String, Object> params) {
		return mapper.changeOrgDriver(params);
	}
	
	public int applyRecheckOrder(Map<String, String> params) {
		return mapper.applyRecheckOrder(params);
	}
	
	public int insertOrgSendrecord(OrgSendrecord orgSendrecord) {
		return mapper.insertOrgSendrecord(orgSendrecord);
	}
	
	public int insertOrgDriverchanges(OrgDriverchanges orgDriverchanges) {
		return mapper.insertOrgDriverchanges(orgDriverchanges);
	}
	
	public void insertOrgOrdercomment(OrgOrdercomment object) {
		mapper.insertOrgOrdercomment(object);
	}
	
	public List<Map<String, String>> getSendRulesByName(PubSendRules object) {
		return mapper.getSendRulesByName(object);
	}
	
	public List<LeAccountRules> findModelPriceByModels(Map<String, Object> params) {
		return mapper.findModelPriceByModels(params);
	}
	
	public int insertOrgUserrefund(OrgUserRefund object) {
		return mapper.insertOrgUserrefund(object);
	}
	
	public OrgOrganCompanyRef getOrgOrganCompanyRefByOrgCom(OrgOrganCompanyRef object) {
		return mapper.getOrgOrganCompanyRefByOrgCom(object);
	}
	
	public int updateOrgOrganCompanyRef(OrgOrganCompanyRef object) {
		return mapper.updateOrgOrganCompanyRef(object);
	}
	
	public List<PubSendRules> getLeSendrulesList(PubSendRules object) {
		return mapper.getLeSendrulesList(object);
	}
	
	public List<Map<String, Object>> getOrganByName(Map<String, Object> params) {
		return mapper.getOrganByName(params);
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
	
	public Map<String, String> getOrgOrderVehicleByOrder(String orderno) {
		return mapper.getOrgOrderVehicleByOrder(orderno);
	}
	
	public void updateOrgOrderVehicleByOrderno(OrgOrder object) {
		mapper.updateOrgOrderVehicleByOrderno(object);
	}
	
	public List<Map<String, Object>> getOrgOrderCommentListByQuery(OrdercommentQueryParam queryParam) {
		return mapper.getOrgOrderCommentListByQuery(queryParam);
	}
	
	public int getOrgOrderCommentCountByQuery(OrdercommentQueryParam queryParam) {
		return mapper.getOrgOrderCommentCountByQuery(queryParam);
	}
	
	public List<Map<String, Object>> getOrdernoBySelect(OrderManageQueryParam queryParam) {
		return mapper.getOrdernoBySelect(queryParam);
	}
	
	public OrgOrderReview getOrgOrderreviewLastByOrderno(String orderno) {
		return mapper.getOrgOrderreviewLastByOrderno(orderno);
	}
	
	public void orderReject(OrgOrder object) {
		mapper.orderReject(object);
	}
	
	public PubDriver getPubDriver(String id){
		return mapper.getPubDriver(id);
	}

    public List<Map<String, Object>> getBelongLeaseCompanySelect(Map<String, Object> params) {
        return mapper.getBelongLeaseCompanySelect(params);
    }

    public void updatePubDriverLeisure(String driverid) {
        mapper.updatePubDriverLeisure(driverid);
    }

}
