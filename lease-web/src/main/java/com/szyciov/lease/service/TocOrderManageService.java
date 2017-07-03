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

@Service("tocOrderManageService")
public class TocOrderManageService {
	private TemplateHelper templateHelper = new TemplateHelper();
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getOpOrderByOrderno(String orderno, String userToken) {
		return templateHelper.dealRequestWithToken("/OrderManage/GetOpOrderByOrderno/{orderno}", HttpMethod.GET, userToken, null, Map.class, orderno);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getOpOrderTraceData(String orderno, String ordertype, String usetype, String userToken) {
		return templateHelper.dealRequestWithFullUrlToken(
				SystemConfig.getSystemProperty("carserviceApiUrl")
						+ "/BaiduApi/GetTraceData/?orderno={orderno}&ordertype={ordertype}&usetype={usetype}",
				HttpMethod.GET, userToken, null, Map.class, orderno, ordertype, usetype);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getFirstOpOrderByOrderno(String orderno, String userToken) {
		return templateHelper.dealRequestWithToken("/OrderManage/GetFirstOpOrderByOrderno/{orderno}", HttpMethod.GET, userToken, null, Map.class, orderno);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getOpSendOrderRecord(String orderno, String userToken) {
		return templateHelper.dealRequestWithToken("/OrderManage/GetOpSendOrderRecord/{orderno}", HttpMethod.GET, userToken, null, Map.class, orderno);
	}
	
	public PageBean getOpChangeDriverByQuery(OrderManageQueryParam queryParam, String userToken) {
		return templateHelper.dealRequestWithToken("/OrderManage/GetOpChangeDriverByQuery", HttpMethod.POST, userToken, queryParam, PageBean.class);
	}
	
	public PageBean getOpOrderReviewByQuery(OrderManageQueryParam queryParam, String userToken) {
		return templateHelper.dealRequestWithToken("/OrderManage/GetOpOrderReviewByQuery", HttpMethod.POST, userToken, queryParam, PageBean.class);
	}
	
	public PageBean getOpOrderCommentByQuery(OrdercommentQueryParam queryParam, String userToken) {
		return templateHelper.dealRequestWithToken("/OrderManage/GetOpOrderCommentByQuery", HttpMethod.POST, userToken, queryParam,PageBean.class);
	}
	
	
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getOpTaxiOrderByOrderno(String orderno, String userToken) {
		return templateHelper.dealRequestWithToken("/OrderManage/GetOpTaxiOrderByOrderno/{orderno}", HttpMethod.GET, userToken, null, Map.class, orderno);
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
		return templateHelper.dealRequestWithToken("/OrderManage/GetFirstTaxiOrderByOrderno/{orderno}", HttpMethod.GET, userToken, null, Map.class, orderno);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getOpSendTaxiOrderRecord(String orderno, String userToken) {
		return templateHelper.dealRequestWithToken("/OrderManage/GetOpSendTaxiOrderRecord/{orderno}", HttpMethod.GET, userToken, null, Map.class, orderno);
	}
	
	public PageBean getOpChangeDriverList(OrderManageQueryParam queryParam, String userToken) {
		return templateHelper.dealRequestWithToken("/OrderManage/GetOpChangeDriverList", HttpMethod.POST, userToken, queryParam, PageBean.class);
	}
	
	public PageBean getOpChangeVehicleList(OrderManageQueryParam queryParam, String userToken) {
		return templateHelper.dealRequestWithToken("/OrderManage/GetOpChangeVehicleList", HttpMethod.POST, userToken, queryParam, PageBean.class);
	}
	
	public PageBean getOpTaxiOrderReviewByQuery(OpTaxiOrderReview object, String userToken) {
		return templateHelper.dealRequestWithToken("/OrderManage/GetOpTaxiOrderReviewByQuery", HttpMethod.POST, userToken, object, PageBean.class);
	}
	
	public PageBean getOpTaxiOrderCommentByQuery(OrdercommentQueryParam queryParam, String userToken) {
		return templateHelper.dealRequestWithToken("/OrderManage/GetOpTaxiOrderCommentByQuery", HttpMethod.POST, userToken, queryParam, PageBean.class);
	}
	
}
