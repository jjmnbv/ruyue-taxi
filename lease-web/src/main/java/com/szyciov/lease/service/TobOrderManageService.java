package com.szyciov.lease.service;

import com.szyciov.lease.param.OrderManageQueryParam;
import com.szyciov.op.entity.OpTaxiOrderReview;
import com.szyciov.param.OrdercommentQueryParam;
import com.szyciov.util.PageBean;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("tobOrderManageService")
public class TobOrderManageService {
	private TemplateHelper templateHelper = new TemplateHelper();
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getOrgOrderByOrderno(String orderno, String userToken) {
		return templateHelper.dealRequestWithToken("/OrderManage2b/GetOrgOrderByOrderno/{orderno}", HttpMethod.GET, userToken, null, Map.class, orderno);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getOrgOrderTraceData(String orderno, String ordertype, String usetype, String userToken) {
		return templateHelper.dealRequestWithFullUrlToken(
				SystemConfig.getSystemProperty("carserviceApiUrl")
						+ "/BaiduApi/GetTraceData/?orderno={orderno}&ordertype={ordertype}&usetype={usetype}",
				HttpMethod.GET, userToken, null, Map.class, orderno, ordertype, usetype);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getFirstOrgOrderByOrderno(String orderno, String userToken) {
		return templateHelper.dealRequestWithToken("/OrderManage2b/GetFirstOrgOrderByOrderno/{orderno}", HttpMethod.GET, userToken, null, Map.class, orderno);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getOrgSendOrderRecord(String orderno, String userToken) {
		return templateHelper.dealRequestWithToken("/OrderManage2b/GetOrgSendOrderRecord/{orderno}", HttpMethod.GET, userToken, null, Map.class, orderno);
	}
	
	public PageBean getOrgChangeDriverByQuery(OrderManageQueryParam queryParam, String userToken) {
		return templateHelper.dealRequestWithToken("/OrderManage2b/GetOrgChangeDriverByQuery", HttpMethod.POST, userToken, queryParam, PageBean.class);
	}
	
	public PageBean getOpOrderReviewByQuery(OrderManageQueryParam queryParam, String userToken) {
		return templateHelper.dealRequestWithToken("/OrderManage2b/GetOpOrderReviewByQuery", HttpMethod.POST, userToken, queryParam, PageBean.class);
	}
	
	public PageBean getOrgOrderCommentByQuery(OrdercommentQueryParam queryParam, String userToken) {
		return templateHelper.dealRequestWithToken("/OrderManage2b/GetOrgOrderCommentByQuery", HttpMethod.POST, userToken, queryParam,PageBean.class);
	}
	
	
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getOpTaxiOrderByOrderno(String orderno, String userToken) {
		return templateHelper.dealRequestWithToken("/OrderManage2b/GetOpTaxiOrderByOrderno/{orderno}", HttpMethod.GET, userToken, null, Map.class, orderno);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getOpTaxiOrderTraceData(String orderno, String ordertype, String usetype, String userToken) {
		return templateHelper.dealRequestWithFullUrlToken(
				SystemConfig.getSystemProperty("carserviceApiUrl")
						+ "/BaiduApi/GetTraceData/?orderno={orderno}&ordertype={ordertype}&usetype={usetype}",
				HttpMethod.GET, userToken, null, Map.class, orderno, ordertype, usetype);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getFirstTaxiOrderByOrderno(String orderno, String userToken) {
		return templateHelper.dealRequestWithToken("/OrderManage2b/GetFirstTaxiOrderByOrderno/{orderno}", HttpMethod.GET, userToken, null, Map.class, orderno);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getOpSendTaxiOrderRecord(String orderno, String userToken) {
		return templateHelper.dealRequestWithToken("/OrderManage2b/GetOpSendTaxiOrderRecord/{orderno}", HttpMethod.GET, userToken, null, Map.class, orderno);
	}
	
	public PageBean getOpChangeDriverList(OrderManageQueryParam queryParam, String userToken) {
		return templateHelper.dealRequestWithToken("/OrderManage2b/GetOpChangeDriverList", HttpMethod.POST, userToken, queryParam, PageBean.class);
	}
	
	public PageBean getOpChangeVehicleList(OrderManageQueryParam queryParam, String userToken) {
		return templateHelper.dealRequestWithToken("/OrderManage2b/GetOpChangeVehicleList", HttpMethod.POST, userToken, queryParam, PageBean.class);
	}
	
	public PageBean getOpTaxiOrderReviewByQuery(OpTaxiOrderReview object, String userToken) {
		return templateHelper.dealRequestWithToken("/OrderManage2b/GetOpTaxiOrderReviewByQuery", HttpMethod.POST, userToken, object, PageBean.class);
	}
	
	public PageBean getOpTaxiOrderCommentByQuery(OrdercommentQueryParam queryParam, String userToken) {
		return templateHelper.dealRequestWithToken("/OrderManage2b/GetOpTaxiOrderCommentByQuery", HttpMethod.POST, userToken, queryParam, PageBean.class);
	}
	
}
