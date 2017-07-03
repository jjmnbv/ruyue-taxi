package com.szyciov.operate.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.op.entity.OpAccountrules;
import com.szyciov.op.entity.OpVehiclemodels;
import com.szyciov.op.param.OpAccountruleQueryParam;
import com.szyciov.op.param.OpAccountrulesModilogQueryParam;
import com.szyciov.util.PageBean;
import com.szyciov.util.TemplateHelper;

import net.sf.json.JSONObject;

@Service("OpAccountrulesService")
public class OpAccountrulesService {
	
	private TemplateHelper templateHelper = new TemplateHelper();
	
	@SuppressWarnings("unchecked")
	public List<OpVehiclemodels> getModelsByList(String userToken) {
		return templateHelper.dealRequestWithToken("/OpAccountrules/GetModelsByList", HttpMethod.POST, userToken, null, List.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> getCityByList(PubCityAddr object, String userToken) {
		return templateHelper.dealRequestWithToken("/OpAccountrules/GetCityByList", HttpMethod.POST, userToken, object, List.class);
	}
	
	public PageBean getOpAccountRulesByQuery(OpAccountruleQueryParam queryParam, String userToken) {
		return templateHelper.dealRequestWithToken("/OpAccountrules/GetOpAccountRulesByQuery", HttpMethod.POST, userToken, queryParam, PageBean.class);
	}
	
	public JSONObject getPubCityAddrByList(String userToken) {
		return templateHelper.dealRequestWithToken("/PubInfo/GetPubCityAddrByList", HttpMethod.POST, userToken, null, JSONObject.class);
	}
	
	public OpAccountrules getOpAccountrulesById(String id, String userToken) {
		return templateHelper.dealRequestWithToken("/OpAccountrules/GetOpAccountrulesById/{id}", HttpMethod.GET, userToken, null, OpAccountrules.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> createOpAccountRules(OpAccountrules object, String userToken) {
		return templateHelper.dealRequestWithToken("/OpAccountrules/CreateOpAccountRules", HttpMethod.POST, userToken, object, Map.class);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> editOpAccountRules(OpAccountrules object, String userToken) {
		return templateHelper.dealRequestWithToken("/OpAccountrules/EditOpAccountRules", HttpMethod.POST, userToken, object, Map.class);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> editOpAccountRulesState(OpAccountrules object, String userToken) {
		return templateHelper.dealRequestWithToken("/OpAccountrules/EditOpAccountRulesState", HttpMethod.POST, userToken, object, Map.class);
	}
	
	public PageBean getOpAccountRulesModiLogByQuery(OpAccountrulesModilogQueryParam queryParam, String userToken) {
		return templateHelper.dealRequestWithToken("/OpAccountrules/GetOpAccountRulesModiLogByQuery", HttpMethod.POST, userToken, queryParam, PageBean.class);
	}
	
	public OpAccountrules getOpAccountRulesByCity(OpAccountrules object, String userToken) {
		return templateHelper.dealRequestWithToken("/OpAccountrules/GetOpAccountRulesByCity", HttpMethod.POST, userToken, object, OpAccountrules.class);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> editOpAccountRulesByCity(OpAccountrules object, String userToken) {
		return templateHelper.dealRequestWithToken("/OpAccountrules/EditOpAccountRulesByCity", HttpMethod.POST, userToken, object, Map.class);
	}

}
