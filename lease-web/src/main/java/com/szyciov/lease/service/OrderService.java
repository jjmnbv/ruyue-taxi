package com.szyciov.lease.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.szyciov.driver.enums.OrderState;
import com.szyciov.driver.param.BaseParam;
import com.szyciov.driver.param.OrderCostParam;
import com.szyciov.dto.PagingResponse;
import com.szyciov.entity.CancelParty;
import com.szyciov.entity.Retcode;
import com.szyciov.entity.Select2Entity;
import com.szyciov.enums.OrderEnum;
import com.szyciov.lease.dto.request.GetFreeDriverRequest;
import com.szyciov.lease.param.GetCarTypesParam;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.org.param.BaseOrgParam;
import com.szyciov.param.BaiduApiQueryParam;
import com.szyciov.param.OrderApiParam;
import com.szyciov.param.Select2Param;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper;

import net.sf.json.JSONObject;

@Service("OrderService")
public class OrderService {
	private TemplateHelper templateHelper = new TemplateHelper();
	
	
	public JSONObject cancelOrder(OrderApiParam param){
		param.setReqsrc(CancelParty.LEASE.code);
		param.setOrderstate(OrderState.CANCEL.state);
		return changeOrderState(param);
	}
	
	public JSONObject manticOrder(OrderApiParam param){
		param.setReqsrc(CancelParty.LEASE.code);
		param.setOrderstate(OrderState.MANTICSEND.state);
		return changeOrderState(param);
	}
	
	public JSONObject changeOrderState(OrderApiParam param){
		String carserviceApiUrl = SystemConfig.getSystemProperty("carserviceApiUrl");
		JSONObject result = templateHelper.dealRequestWithFullUrlToken(
				carserviceApiUrl+"/OrderApi/ChangeOrderState", 
				HttpMethod.POST, 
				param.getToken(),
				param, 
				JSONObject.class);
		return result;
	}
	
	public JSONObject checkOrderState(String orderno){
		JSONObject result = templateHelper.dealRequestWithToken("/Order/CheckOrderState/{orderno}", HttpMethod.GET, null, null, JSONObject.class,orderno);
		return result;
	}
	
	public JSONObject createOrgOrder(OrgOrder orderInfo){
		orderInfo.setOrdersource(OrderEnum.ORDERSOURCE_LEASE.code);
		String carserviceApiUrl = SystemConfig.getSystemProperty("carserviceApiUrl");
		JSONObject result = templateHelper.dealRequestWithFullUrlToken(
				carserviceApiUrl+"/OrderApi/CreateOrgOrder", 
				HttpMethod.POST, 
				null,
				orderInfo, 
				JSONObject.class);
		return result;
	}
	
	public Map<String,Object> initIndex(String companyid){
//		Map<String,Object> model = templateHelper.dealRequestWithToken("/Order/Index", HttpMethod.GET, null, null, Map.class);
//		return model
//		List<City> cities = templateHelper.dealRequestWithToken("/PubVehicle/GetPubCityaddr", HttpMethod.GET, null, null, List.class);
		JSONObject cities = templateHelper.dealRequestWithToken("/Order/GetCities", HttpMethod.POST, null, null, JSONObject.class);
		JSONObject organList = templateHelper.dealRequestWithToken("/Order/GetOrganList/{companyid}", HttpMethod.GET, null, null, JSONObject.class,companyid);
		JSONObject useCarReason = templateHelper.dealRequestWithToken("/Order/GetUseCarReason", HttpMethod.POST, null, null, JSONObject.class);
		JSONObject priOrganList = templateHelper.dealRequestWithToken("/Order/GetPriOrganList/{companyid}", HttpMethod.GET, null, null, JSONObject.class,companyid);
		Map<String,Object> result = new HashMap<>();
		result.put("city", cities.get("city"));
		result.put("orglist", organList.get("orglist"));
		result.put("priOrganList", priOrganList.get("priOrganList"));
		result.put("reason", useCarReason.get("reason"));
		return result;
	}
	
	public Map<String, Object> success(String orderno){
		Map<String, Object> param = new HashMap<>();
		param.put("orderno", orderno);
		JSONObject result = templateHelper.dealRequestWithToken("/Order/GetMinOrderInfo/{orderno}", HttpMethod.GET, null, null, JSONObject.class,orderno);
		if(result.getInt("status") == Retcode.OK.code){
			param.put("order", result.getJSONObject("order"));
		}
		return param;
	}
	
	public Map<String, Object> failed(String orderno){
		Map<String, Object> param = new HashMap<>();
		param.put("orderno", orderno);
		JSONObject result = templateHelper.dealRequestWithToken("/Order/CheckOrderState/{orderno}", HttpMethod.GET, null, null, JSONObject.class,orderno);
		if(result.getInt("status") == Retcode.OK.code){
			param.put("order", result.getJSONObject("order"));
			//如果是系统派单方式,则提示约车失败,否则提示约车成功
			if(OrderEnum.SENDORDERTYPE_SYSTEM.code.equals(result.getJSONObject("order").getInt("sendordertype")+"")){
				param.put("title", "约车失败");
				param.put("content", "无司机接单,请人工派单...");
			}else{
				param.put("title", "约车成功");
				param.put("content", "订单已提交成功,正在人工派单中");
			}
		}
		return param;
	}
	
	public JSONObject getOrgOrderCost(OrderCostParam ocp){
//		String carserviceApiUrl = SystemConfig.getSystemProperty("carserviceApiUrl");
//		JSONObject result = templateHelper.dealRequestWithFullUrl(
//				carserviceApiUrl+"/OrderApi/GetOrderCost", 
//				HttpMethod.POST, 
//				ocp, 
//				JSONObject.class, 
//				new HashMap<String, Object>());
		JSONObject result = templateHelper.dealRequestWithToken("/Order/GetOrgOrderCost", HttpMethod.POST, ocp.getToken(), ocp, JSONObject.class);
		return result;
	}
	
	public JSONObject getCarTypes(GetCarTypesParam param){
		JSONObject result = templateHelper.dealRequestWithToken("/Order/GetCarTypes", HttpMethod.POST, param.getToken(), param, JSONObject.class);
		return result;
	}
	

	public JSONObject getOrgUserForSelect(Select2Param param){
		JSONObject result = templateHelper.dealRequestWithToken("/Order/GetOrgUserForSelect", HttpMethod.POST, param.getToken(), param, JSONObject.class);
		return result;
	}
	
	public JSONObject getFavUserForSelect(Select2Param param){
		JSONObject result = templateHelper.dealRequestWithToken("/Order/GetFavUserForSelect", HttpMethod.POST, param.getToken(), param, JSONObject.class);
		return result;
	}
	
	public JSONObject getOrgUser(Select2Param param){
		JSONObject result = templateHelper.dealRequestWithToken("/Order/GetOrgUser", HttpMethod.POST, null, param, JSONObject.class);
		return result;
	}
	
	public JSONObject getFavUser(Select2Param param){
		JSONObject result = templateHelper.dealRequestWithToken("/Order/GetFavUser", HttpMethod.POST, null, param, JSONObject.class);
		return result;
	}

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Select2Entity> getManualSelectDriverForSelect(GetFreeDriverRequest model){
        PagingResponse res = templateHelper.dealRequestWithToken("/Order/GetManualSelectDriver", HttpMethod.POST, model.getToken(), model, PagingResponse.class);
        return res.getAaData();
    }

    @SuppressWarnings("rawtypes")
	public PagingResponse getManualSelectDriver(GetFreeDriverRequest model){
        PagingResponse res = templateHelper.dealRequestWithToken("/Order/GetManualSelectDriver", HttpMethod.POST, model.getToken(), model, PagingResponse.class);
        return res;
    }



    /**
	 * 获取机构用户常用地址列表
	 * 
	 * @param param
	 * @return
	 */
	public JSONObject getMostAddress(BaseOrgParam param) {
		JSONObject result = templateHelper.dealRequestWithToken("/Order/GetMostAddress", HttpMethod.POST, param.getToken(), param, JSONObject.class);
		return result;
	}
	
	public JSONObject getAirPorts(BaseParam param){
		String carserviceApiUrl = SystemConfig.getSystemProperty("carserviceApiUrl");
		JSONObject result = templateHelper.dealRequestWithFullUrl(
				carserviceApiUrl+"/PubInfoApi/GetAirPorts", 
				HttpMethod.POST, 
				param, 
				JSONObject.class,
				new HashMap<>());
		return result;
	}
	
	public JSONObject getCities(BaseParam param){
		String carserviceApiUrl = SystemConfig.getSystemProperty("carserviceApiUrl");
		JSONObject result = templateHelper.dealRequestWithFullUrl(
				carserviceApiUrl+"/PubInfoApi/GetCities", 
				HttpMethod.POST, 
				param, 
				JSONObject.class,
				new HashMap<>());
		return result;
	}
	
	public JSONObject getLatLng(BaiduApiQueryParam param){
		String carserviceApiUrl = SystemConfig.getSystemProperty("carserviceApiUrl");
		JSONObject result = templateHelper.dealRequestWithFullUrl(
				carserviceApiUrl+"/BaiduApi/GetLatLng", 
				HttpMethod.POST, 
				param, 
				JSONObject.class,
				new HashMap<>());
		return result;
	}
	
	public JSONObject getAddress(BaiduApiQueryParam param){
		String carserviceApiUrl = SystemConfig.getSystemProperty("carserviceApiUrl");
		JSONObject result = templateHelper.dealRequestWithFullUrl(
				carserviceApiUrl+"/BaiduApi/GetAddress", 
				HttpMethod.POST, 
				param, 
				JSONObject.class,
				new HashMap<>());
		return result;
	}
	
	public JSONObject getOrgUserBusCity(Map<String, Object> param, String userToken) {
		return templateHelper.dealRequestWithToken("/Order/GetOrgUserBusCity", HttpMethod.POST, userToken, param, JSONObject.class);
	}

	/**
	 * 获取机构列表
	 * @param param
	 * @return
	 */
	public Map<String,Object> getOrgans(Map<String, Object> param) {
		Map<String,Object> result = templateHelper.dealRequestWithToken("/Order/GetOrgans", HttpMethod.POST, null, param, Map.class);
		return result;
	}

    /**
     * 获取机构列表
     * @param param
     * @return
     */
    public Map<String,Object> getCompanyidByruyue(Map<String, Object> param) {
        Map<String,Object> result = templateHelper.dealRequestWithToken("/Order/GetCompanyidByruyue", HttpMethod.POST, null, param, Map.class);
        return result;
    }



	/**
	 * 获取机构下的员工列表
	 * @param param
	 * @return
	 */
	public Map<String,Object> getOrganUsers(Map<String, Object> param) {
		Map<String,Object> result = templateHelper.dealRequestWithToken("/Order/GetOrganUsers", HttpMethod.POST, null, param, Map.class);
		return result;
	}
}
