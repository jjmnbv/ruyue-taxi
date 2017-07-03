package com.szyciov.operate.service;

import com.szyciov.driver.enums.OrderState;
import com.szyciov.driver.param.OrderCostParam;
import com.szyciov.dto.PagingResponse;
import com.szyciov.entity.CancelParty;
import com.szyciov.entity.Retcode;
import com.szyciov.entity.Select2Entity;
import com.szyciov.enums.OrderEnum;
import com.szyciov.lease.param.GetCarTypesParam;
import com.szyciov.op.dto.request.GetManualDriverRequest;
import com.szyciov.op.dto.request.GetTaxiManualDriverRequest;
import com.szyciov.op.entity.OpOrder;
import com.szyciov.op.entity.OpTaxiOrder;
import com.szyciov.op.entity.OpUser;
import com.szyciov.op.param.BaseOpParam;
import com.szyciov.org.entity.OrgMostContact;
import com.szyciov.param.BaiduApiQueryParam;
import com.szyciov.param.OrderApiParam;
import com.szyciov.param.Select2Param;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper;
import net.sf.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName OrderService 
 * @author Efy Shu
 * @Description 机构WEB端订单Service
 * @date 2016年10月17日 下午2:22:40 
 */
@Service("OrderService")
public class OrderService {
	private TemplateHelper templateHelper = new TemplateHelper();
	
	public Map<String,Object> initIndex(OpUser user,String usertoken){
		Map<String, Object> model = new HashMap<String, Object>();
//		List<Map<String,Object>> navinfo = templateHelper.dealRequestWithToken("/User/GetNavInfo", HttpMethod.POST, usertoken, user, List.class);
		model.put("username", user.getNickname());
		model.put("user", user);
//		model.put("navinfo", navinfo);
		return model;
	}
	
	public Map<String, Object> failed(String orderno){
		Map<String, Object> param = new HashMap<>();
		param.put("orderno", orderno);
		JSONObject result = templateHelper.dealRequestWithToken("/Order/CheckOrderState/{orderno}", HttpMethod.GET, null, null, JSONObject.class,orderno);
		if(result.getInt("status") == Retcode.OK.code){
			param.put("order", result.getJSONObject("order"));
		}
		return param;
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
	
	public JSONObject getCompanyList(BaseOpParam param){
		JSONObject result = templateHelper.dealRequestWithToken("/Order/GetCompanyList", HttpMethod.POST, param.getToken(), null, JSONObject.class);
		return result;
	}

    public JSONObject getBelongCompanyList(BaseOpParam param){
        JSONObject result = templateHelper.dealRequestWithToken("/Order/GetBelongCompanyList", HttpMethod.POST, param.getToken(), null, JSONObject.class);
        return result;
    }
	
	/**
	 * 获取城市列表
	 * @param param
	 * @return
	 */
	public JSONObject getCities(BaseOpParam param){
		String carserviceApiUrl = SystemConfig.getSystemProperty("carserviceApiUrl");
		JSONObject result = templateHelper.dealRequestWithFullUrlToken(carserviceApiUrl+"/PubInfoApi/GetCities", HttpMethod.POST, param.getToken(), null, JSONObject.class);
		return result;
	}
	
	/**
	 * 获取全国机场列表
	 * @param param
	 * @return
	 */
	public JSONObject getAirPorts(BaseOpParam param){
		String carserviceApiUrl = SystemConfig.getSystemProperty("carserviceApiUrl");
		JSONObject result = templateHelper.dealRequestWithFullUrlToken(carserviceApiUrl+"/PubInfoApi/GetAirPorts", HttpMethod.POST, param.getToken(), null, JSONObject.class);
		return result;
	}
	
	/**
	 * 获取租赁公司列表
	 * 
	 * @return
	 */
	public JSONObject getLeaseList(BaseOpParam param) {
		JSONObject result = templateHelper.dealRequestWithToken("/Order/GetLeaseList", HttpMethod.POST, param.getToken(), param, JSONObject.class);
		return result;
	}

	/**
	 * 获取用车事由列表
	 * 
	 * @return
	 */
	public JSONObject getUseCarReason(BaseOpParam param) {
		String carserviceApiUrl = SystemConfig.getSystemProperty("carserviceApiUrl");
		JSONObject result = templateHelper.dealRequestWithFullUrlToken(carserviceApiUrl+"/PubInfoApi/GetUseCarReason", HttpMethod.POST, param.getToken(), param, JSONObject.class);
		return result;
	}

	/**
	 * 获取订单状态
	 * 
	 * @param orderno
	 * @return
	 */
	public JSONObject checkOrderState(String orderno) {
		JSONObject result = templateHelper.dealRequestWithToken("/Order/CheckOrderState/{orderno}", HttpMethod.GET, null, null, JSONObject.class,orderno);
		return result;
	}

	/**
	 * 取消订单
	 * 
	 * @param orderno
	 * @return
	 */
	public JSONObject cancelOrder(OrderApiParam param) {
		String carserviceApiUrl = SystemConfig.getSystemProperty("carserviceApiUrl");
		param.setReqsrc(CancelParty.OPERATOR.code);
		param.setOrderstate(OrderState.CANCEL.state);
		param.setUsetype(OrderEnum.USETYPE_PERSONAL.code);
		JSONObject result = templateHelper.dealRequestWithFullUrlToken(carserviceApiUrl + "/OrderApi/ChangeOrderState", HttpMethod.POST, null, param, JSONObject.class);
		return result;
	}
	
	/**
	 * 将订单变更为人工派单
	 * 
	 * @param orderno
	 * @return
	 */
	public JSONObject manticOrder(OrderApiParam param) {
		String carserviceApiUrl = SystemConfig.getSystemProperty("carserviceApiUrl");
		param.setReqsrc(CancelParty.OPERATOR.code);
		param.setOrderstate(OrderState.MANTICSEND.state);
		param.setUsetype(OrderEnum.USETYPE_PERSONAL.code);
		JSONObject result = templateHelper.dealRequestWithFullUrlToken(carserviceApiUrl + "/OrderApi/ChangeOrderState", HttpMethod.POST, null, param, JSONObject.class);
		return result;
	}
	
	/**
	 * 获取机构用户常用联系人列表
	 * 
	 * @param param
	 * @return
	 */
	public JSONObject getMostContact(Select2Param param) {
		JSONObject result = templateHelper.dealRequestWithToken("/Order/GetMostContact", HttpMethod.POST, param.getToken(), param, JSONObject.class);
		return result;
	}

	/**
	 * 获取个人用户常用联系人列表
	 * 
	 * @param param
	 * @return
	 */
	public JSONObject getMostContactForSelect(Select2Param param){
		JSONObject result = templateHelper.dealRequestWithToken("/Order/GetMostContactForSelect", HttpMethod.POST, param.getToken(), param, JSONObject.class);
		return result;
	}
	
	/**
	 * 获取个人用户列表
	 * 
	 * @param param
	 * @return
	 */
	public JSONObject getPeUserForSelect(Select2Param param){
		JSONObject result = templateHelper.dealRequestWithToken("/Order/GetPeUserForSelect", HttpMethod.POST, param.getToken(), param, JSONObject.class);
		return result;
	}
	
	/**
	 * 添加机构用户常用联系人
	 * 
	 * @param param
	 * @return
	 */
	public JSONObject addMostContact(OrgMostContact param) {
		JSONObject result = templateHelper.dealRequestWithToken("/Order/AddMostContact", HttpMethod.POST, null, param, JSONObject.class);
		return result;
	}
	
	/**
	 * 删除机构用户常用联系人
	 * 
	 * @param param
	 * @return
	 */
	public JSONObject delMostContact(OrgMostContact param) {
		JSONObject result = templateHelper.dealRequestWithToken("/Order/DelMostContact", HttpMethod.POST, null, param, JSONObject.class);
		return result;
	}
	
	/**
	 * 获取机构用户常用地址列表
	 * 
	 * @param param
	 * @return
	 */
	public JSONObject getMostAddress(BaseOpParam param) {
		JSONObject result = templateHelper.dealRequestWithToken("/Order/GetMostAddress", HttpMethod.POST, param.getToken(), param, JSONObject.class);
		return result;
	}
	
	/**
	 * 创建机构订单
	 * 
	 * @param orderInfo
	 * @return
	 */
	public JSONObject createOpOrder(OpOrder orderInfo) {
		orderInfo.setOrdersource(OrderEnum.ORDERSOURCE_OPERATE.code);
		String carserviceApiUrl = SystemConfig.getSystemProperty("carserviceApiUrl");
		JSONObject result = templateHelper.dealRequestWithFullUrlToken(
				carserviceApiUrl+"/OrderApi/CreateOpOrder", 
				HttpMethod.POST, 
				null,
				orderInfo, 
				JSONObject.class);
		return result;
	}

	/**
	 * 获取个人订单费用
	 * 
	 * @param param
	 * @return
	 */
	public JSONObject getOpOrderCost(OrderCostParam param) {
		JSONObject result = templateHelper.dealRequestWithToken("/Order/GetOrderCost", HttpMethod.POST, param.getToken(), param, JSONObject.class);
		return result;
//		String carserviceApiUrl = SystemConfig.getSystemProperty("carserviceApiUrl");
//		JSONObject result = templateHelper.dealRequestWithFullUrlToken(carserviceApiUrl+"/OrderApi/GetOrderCost", HttpMethod.POST, param.getToken(), param, JSONObject.class);
//		return result;
	}

	/**
	 * 获取机构车型
	 * 
	 * @param param
	 * @return
	 */
	public JSONObject getCarTypes(GetCarTypesParam param) {
		JSONObject result = templateHelper.dealRequestWithToken("/Order/GetCarTypes", HttpMethod.POST, param.getToken(), param, JSONObject.class);
		return result;
	}
	
	/**
	 * 根据地址获取经纬度
	 * @param param
	 * @return
	 */
	public JSONObject getLatLng(BaiduApiQueryParam param){
		String carserviceApiUrl = SystemConfig.getSystemProperty("carserviceApiUrl");
		JSONObject result = templateHelper.dealRequestWithFullUrlToken(
				carserviceApiUrl+"/BaiduApi/GetLatLng", 
				HttpMethod.POST, 
				null,
				param, 
				JSONObject.class);
		return result;
	}
	
	/**
	 * 根据经纬度获取地址
	 * @param param
	 * @return
	 */
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
	
	public JSONObject getOpTaxiOrderCost(OrderCostParam param) {
		JSONObject result = templateHelper.dealRequestWithToken("/Order/GetOpTaxiOrderCost", HttpMethod.POST, param.getToken(), param, JSONObject.class);
		return result;
	}
	
	/**
	 * 获取出租车订单费用
	 * @param param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> checkOpTaxiAccountRules(OrderCostParam param){
		String carserviceApiUrl = SystemConfig.getSystemProperty("carserviceApiUrl");
		return templateHelper.dealRequestWithFullUrlToken(
				carserviceApiUrl+"/OrderApi/CheckOpTaxiAccountRules", 
				HttpMethod.POST, 
				param.getToken(),
				param, 
				Map.class);
	}
	
	/**
	 * 创建出租车订单
	 * 
	 * @param orderInfo
	 * @return
	 */
	public JSONObject createOpTaxiOrder(OpTaxiOrder object) {
		String carserviceApiUrl = SystemConfig.getSystemProperty("carserviceApiUrl");
		JSONObject result = templateHelper.dealRequestWithFullUrlToken(
				carserviceApiUrl+"/OrderApi/CreateOpTaxiOrder", 
				HttpMethod.POST, 
				null,
				object, 
				JSONObject.class);
		return result;
	}
	
	public JSONObject getNetBusCity(Map<String, Object> param, String userToken) {
		return templateHelper.dealRequestWithToken("/Order/GetNetBusCity", HttpMethod.POST, userToken, param, JSONObject.class);
	}
	
	public JSONObject getTaxiBusCity(Map<String, String> param, String userToken) {
		return templateHelper.dealRequestWithToken("/Order/GetTaxiBusCity", HttpMethod.POST, userToken, param, JSONObject.class);
	}

    public List<Select2Entity> getManualSelectDriverForSelect(GetManualDriverRequest model){
        PagingResponse res = templateHelper.dealRequestWithToken("/Order/GetManualSelectDriver", HttpMethod.POST, model.getToken(), model, PagingResponse.class);
        return res.getAaData();
    }

    public PagingResponse getManualSelectDriver(GetManualDriverRequest model){
        PagingResponse res = templateHelper.dealRequestWithToken("/Order/GetManualSelectDriver", HttpMethod.POST, model.getToken(), model, PagingResponse.class);
        return res;
    }

    public List<Select2Entity> getTaxiManualSelectDriverForSelect(GetTaxiManualDriverRequest model){
        PagingResponse res = templateHelper.dealRequestWithToken("/Order/GetTaxiManualSelectDriver", HttpMethod.POST, model.getToken(), model, PagingResponse.class);
        return res.getAaData();
    }

    public PagingResponse getTaxiManualSelectDriver(GetTaxiManualDriverRequest model){
        PagingResponse res = templateHelper.dealRequestWithToken("/Order/GetTaxiManualSelectDriver", HttpMethod.POST, model.getToken(), model, PagingResponse.class);
        return res;
    }

}
