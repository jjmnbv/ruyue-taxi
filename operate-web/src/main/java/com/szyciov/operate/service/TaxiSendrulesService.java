package com.szyciov.operate.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.szyciov.op.entity.OpTaxisendrules;
import com.szyciov.op.entity.OpUser;
import com.szyciov.op.param.OpTaxiSendrulesHistoryQueryParam;
import com.szyciov.op.param.OpTaxiSendrulesQueryParam;
import com.szyciov.util.PageBean;
import com.szyciov.util.TemplateHelper;

@Service("TaxiSendrulesService")
public class TaxiSendrulesService {
	
	private TemplateHelper templateHelper = new TemplateHelper();
	
	public PageBean getTaxiSendrulesByQuery(OpTaxiSendrulesQueryParam queryParam, String userToken) {
		return templateHelper.dealRequestWithToken("/TaxiSendrules/GetTaxiSendrulesByQuery", HttpMethod.POST, userToken, queryParam, PageBean.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> getTaxiSendrulesCityBySelect(Map<String, String> params, String userToken) {
		return templateHelper.dealRequestWithToken("/TaxiSendrules/GetTaxiSendrulesCityBySelect", HttpMethod.POST, userToken, params, List.class);
	}
	
	public OpTaxisendrules getTaxiSendrulesById(String id, String userToken) {
		return templateHelper.dealRequestWithToken("/TaxiSendrules/GetTaxiSendrulesById/{id}", HttpMethod.GET, userToken, null, OpTaxisendrules.class, id);
	}
	
	public int getTaxiDriverCount(String userToken) {
		return templateHelper.dealRequestWithToken("/TaxiSendrules/GetTaxiDriverCount", HttpMethod.POST, userToken, null, Integer.class);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> addOpTaxiSendrules(OpTaxisendrules object, String userToken) {
		return templateHelper.dealRequestWithToken("/TaxiSendrules/AddOpTaxiSendrules", HttpMethod.POST, userToken, object, Map.class);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> editOpTaxiSendrules(OpTaxisendrules object, String userToken) {
		return templateHelper.dealRequestWithToken("/TaxiSendrules/EditOpTaxiSendrules", HttpMethod.POST, userToken, object, Map.class);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> updateTaxiSendrulesState(OpTaxisendrules object, String userToken) {
		return templateHelper.dealRequestWithToken("/TaxiSendrules/UpdateTaxiSendrulesState", HttpMethod.POST, userToken, object, Map.class);
	}
	
	public PageBean getTaxiSendrulesHistoryByQuery(OpTaxiSendrulesHistoryQueryParam queryParam, String userToken) {
		return templateHelper.dealRequestWithToken("/TaxiSendrules/GetTaxiSendrulesHistoryByQuery", HttpMethod.POST, userToken, queryParam, PageBean.class);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Integer> getOpSendmodelCountByUser(OpUser user, String userToken) {
		return templateHelper.dealRequestWithToken("/TaxiSendrules/GetOpSendmodelCountByUser", HttpMethod.POST, userToken, user, Map.class);
	}
	
}
