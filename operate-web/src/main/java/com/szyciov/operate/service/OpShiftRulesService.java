package com.szyciov.operate.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.szyciov.op.entity.OpShiftRules;
import com.szyciov.op.param.OpShiftRulesQueryParam;
import com.szyciov.util.PageBean;
import com.szyciov.util.TemplateHelper;

@Service("opShiftRulesService")
public class OpShiftRulesService {
	
	private TemplateHelper templateHelper = new TemplateHelper();

//	public List<Map<String, String>> getVailableCitys(Map<String, Object> params, String usertoken) {
//		return templateHelper.dealRequestWithToken("/OpShiftRules/GetVailableCitys",HttpMethod.POST, usertoken,params,List.class);
//	}

	public PageBean getShiftRulesByQuery(OpShiftRulesQueryParam queryParam, String usertoken) {
		return templateHelper.dealRequestWithToken("/OpShiftRules/GetShiftRulesByQuery",HttpMethod.POST, usertoken,queryParam,PageBean.class);
	}

	public OpShiftRules getShiftRules(Map<String, Object> params, String usertoken) {
		return templateHelper.dealRequestWithToken("/OpShiftRules/GetShiftRules",HttpMethod.POST, usertoken,params,OpShiftRules.class);
	}

	public Map<String, Object> createShiftRules(Map<String, Object> params, String usertoken) {
		return templateHelper.dealRequestWithToken("/OpShiftRules/CreateShiftRules",HttpMethod.POST, usertoken,params,Map.class);
	}

	public Map<String, Object> updateShiftRules(Map<String, Object> params, String usertoken) {
		return templateHelper.dealRequestWithToken("/OpShiftRules/UpdateShiftRules",HttpMethod.POST, usertoken,params,Map.class);
	}

	public List<Map<String, String>> getCitys(Map<String, Object> params, String usertoken) {
		return templateHelper.dealRequestWithToken("/OpShiftRules/GetCitys",HttpMethod.POST, usertoken,params,List.class);
	}
}
