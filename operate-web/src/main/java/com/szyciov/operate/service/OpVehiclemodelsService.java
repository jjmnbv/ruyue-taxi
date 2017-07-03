package com.szyciov.operate.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.szyciov.op.entity.OpVehiclemodels;
import com.szyciov.param.QueryParam;
import com.szyciov.util.PageBean;
import com.szyciov.util.TemplateHelper;

import net.sf.json.JSONObject;

@Service("OpVehiclemodelsService")
public class OpVehiclemodelsService {
	
	private TemplateHelper templateHelper = new TemplateHelper();
	
	public PageBean getOpVehiclemodelsByQuery(QueryParam queryParam, String userToken) {
		return templateHelper.dealRequestWithToken("/OpVehiclemodels/GetOpVehiclemodelsByQuery", HttpMethod.POST, userToken, queryParam, PageBean.class);
	}
	
	public JSONObject getPubVehcline(String userToken) {
		return templateHelper.dealRequestWithToken("/OpVehiclemodels/GetPubVehcline", HttpMethod.POST, userToken, null, JSONObject.class);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> createOpVehiclemodels(OpVehiclemodels opVehiclemodels, String userToken) {
		return templateHelper.dealRequestWithToken("/OpVehiclemodels/CreateOpVehiclemodels", HttpMethod.POST, userToken, opVehiclemodels, Map.class);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> updateOpVehiclemodels(OpVehiclemodels opVehiclemodels, String userToken) {
		return templateHelper.dealRequestWithToken("/OpVehiclemodels/UpdateOpVehiclemodels", HttpMethod.POST, userToken, opVehiclemodels, Map.class);
	}
	
	public OpVehiclemodels getOpVehiclemodelsById(String id, String userToken) {
		return templateHelper.dealRequestWithToken("/OpVehiclemodels/GetOpVehiclemodelsById/{id}", HttpMethod.GET, userToken, null, OpVehiclemodels.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> deleteOpVehiclemodels(OpVehiclemodels object, String userToken) {
		return templateHelper.dealRequestWithToken("/OpVehiclemodels/DeleteOpVehiclemodels", HttpMethod.POST, userToken, object, Map.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<OpVehiclemodels> getOpVehiclemodelsByList(OpVehiclemodels object, String userToken) {
		return templateHelper.dealRequestWithToken("/OpVehiclemodels/getOpVehiclemodelsByList", HttpMethod.POST, userToken, object, List.class);
	}

	public Map<String, String> saveLeVehclineModelsRef(Map<String, Object> params, String userToken) {
		return templateHelper.dealRequestWithToken("/OpVehiclemodels/SaveLeVehclineModelsRef", HttpMethod.POST, userToken, params, Map.class);
	}

	public Map<String, String> changeState(Map<String, Object> params, String userToken) {
		return templateHelper.dealRequestWithToken("/OpVehiclemodels/ChangeState", HttpMethod.POST, userToken, params, Map.class);
	}

	public Map<String, String> hasBindLeaseCars(Map<String, Object> params, String userToken) {
		return templateHelper.dealRequestWithToken("/OpVehiclemodels/HasBindLeaseCars", HttpMethod.POST, userToken, params, Map.class);
	}
	
}
