package com.szyciov.lease.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.lease.mapper.TobOrderManageMapper;
import com.szyciov.lease.param.OrderManageQueryParam;
import com.szyciov.lease.param.TobOrderManageQueryParam;
import com.szyciov.op.entity.OpTaxiOrderReview;
import com.szyciov.param.OrdercommentQueryParam;

@Repository("TobOrderManageDao")
public class TobOrderManageDao {
	public TobOrderManageDao() {
	}

	private TobOrderManageMapper mapper;

	@Resource
	public void setMapper(TobOrderManageMapper mapper) {
		this.mapper = mapper;
	}
	
	public List<Map<String, Object>> getNetAboutCarOrderListByQuery(TobOrderManageQueryParam queryParam) {
    	return mapper.getNetAboutCarOrderListByQuery(queryParam);
    }
	
	public int getNetAboutCarOrderListCountByQuery(TobOrderManageQueryParam queryParam) {
		return mapper.getNetAboutCarOrderListCountByQuery(queryParam);
	}
	
    public List<Map<String, Object>> getTaxiOrderListByQuery(TobOrderManageQueryParam queryParam) {
    	return mapper.getTaxiOrderListByQuery(queryParam);
    }
	
	public int getTaxiOrderListCountByQuery(TobOrderManageQueryParam queryParam) {
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
	
    public List<Map<String, Object>> getNetAboutCarOrderExport(TobOrderManageQueryParam queryParam) {
    	return mapper.getNetAboutCarOrderExport(queryParam);
    }
	
	public List<Map<String, Object>> getTaxiOrderExport(TobOrderManageQueryParam queryParam) {
		return mapper.getTaxiOrderExport(queryParam);
	}
	
	//网约车详情
	public Map<String, Object> getOrgOrderByOrderno(String orderno) {
		return mapper.getOrgOrderByOrderno(orderno);
	}
	
	public Map<String, Object> getFirstOrgOrderByOrderno(String orderno) {
		return mapper.getFirstOrgOrderByOrderno(orderno);
	}
	
	public Map<String, Object> getOrgSendOrderRecord(String orderno) {
		return mapper.getOrgSendOrderRecord(orderno);
	}
	
	public List<Map<String, Object>> getOrgChangeDriverListByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOrgChangeDriverListByQuery(queryParam);
	}
	
	public int getOrgChangeDriverCountByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOrgChangeDriverCountByQuery(queryParam);
	}
	
	public List<Map<String, Object>> getOpOrderReviewListByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOpOrderReviewListByQuery(queryParam);
	}
	
	public int getOpOrderReviewCountByQuery(OrderManageQueryParam queryParam) {
		return mapper.getOpOrderReviewCountByQuery(queryParam);
	}
	
	public List<Map<String, Object>> getOrgOrderCommentListByQuery(OrdercommentQueryParam queryParam) {
		return mapper.getOrgOrderCommentListByQuery(queryParam);
	}
	
	public int getOrgOrderCommentCountByQuery(OrdercommentQueryParam queryParam) {
		return mapper.getOrgOrderCommentCountByQuery(queryParam);
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

	public List<Map<String, Object>> getPartnerCompanySelect(Map<String, Object> params) {
		return mapper.getPartnerCompanySelect(params);
	}
	
}
