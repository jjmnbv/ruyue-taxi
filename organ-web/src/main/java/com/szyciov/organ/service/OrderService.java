package com.szyciov.organ.service;

import com.szyciov.driver.enums.OrderState;
import com.szyciov.driver.param.OrderCostParam;
import com.szyciov.entity.CancelParty;
import com.szyciov.entity.Retcode;
import com.szyciov.enums.OrderEnum;
import com.szyciov.lease.param.GetCarTypesParam;
import com.szyciov.org.entity.OrgMostContact;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.org.entity.OrgUser;
import com.szyciov.org.param.BaseOrgParam;
import com.szyciov.org.param.OrgUserParam;
import com.szyciov.param.BaiduApiQueryParam;
import com.szyciov.param.OrderApiParam;
import com.szyciov.util.StringUtil;
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
	
	@SuppressWarnings("unchecked")
	public Map<String,Object> initIndex(OrgUser user,String orderno,String usertoken){
		OrgUserParam oup = new OrgUserParam();
		oup.setUserid(user.getId());
		oup.setToken(usertoken);
		Map<String, Object> model = new HashMap<String, Object>();
		List<Map<String,Object>> navinfo = templateHelper.dealRequestWithToken("/User/GetNavInfo", HttpMethod.POST, usertoken, user, List.class);
		JSONObject result = templateHelper.dealRequestWithToken("/Order/GetOrgUserById", HttpMethod.POST, usertoken, oup, JSONObject.class);
		if(result.getInt("status") == Retcode.OK.code){ //重新查询是否有未支付订单
			user = StringUtil.parseJSONToBean(result.getJSONObject("user").toString(), OrgUser.class);
		}
		if(orderno != null){
			result = checkOrderState(orderno);
			//重新查询是否有未支付订单
			if(result.getInt("status") == Retcode.OK.code && user.getId().equals(result.getJSONObject("order").getString("userid"))){
				model.put("order", result.getJSONObject("order"));
				model.put("orderno", orderno);
				model.put("ordertype", result.getJSONObject("order").getString("ordertype"));
			}
		}
		
		// 未读消息数量
		int unReadNum = templateHelper.dealRequestWithToken("/Message/GetUnReadNewsCountByUserId?userId={userId}",
				HttpMethod.GET, usertoken, null, Integer.class, user.getId());
		
		model.put("unReadNum", unReadNum);
		model.put("username", user.getAccount());
		model.put("user", user);
		model.put("navinfo", navinfo);
		return model;
	}
	
	/**
	 * 获取城市列表
	 * @param param
	 * @return
	 */
	public JSONObject getCities(BaseOrgParam param){
		String carserviceApiUrl = SystemConfig.getSystemProperty("carserviceApiUrl");
		JSONObject result = templateHelper.dealRequestWithFullUrlToken(carserviceApiUrl+"/PubInfoApi/GetCities", HttpMethod.POST, param.getToken(), null, JSONObject.class);
		return result;
	}
	
	/**
	 * 获取全国机场列表
	 * @param param
	 * @return
	 */
	public JSONObject getAirPorts(BaseOrgParam param){
		String carserviceApiUrl = SystemConfig.getSystemProperty("carserviceApiUrl");
		JSONObject result = templateHelper.dealRequestWithFullUrlToken(carserviceApiUrl+"/PubInfoApi/GetAirPorts", HttpMethod.POST, param.getToken(), null, JSONObject.class);
		return result;
	}
	
	/**
	 * 获取租赁公司列表
	 * 
	 * @return
	 */
	public JSONObject getLeaseList(BaseOrgParam param) {
		JSONObject result = templateHelper.dealRequestWithToken("/Order/GetLeaseList", HttpMethod.POST, param.getToken(), param, JSONObject.class);
		return result;
	}

	/**
	 * 获取用车事由列表
	 * 
	 * @return
	 */
	public JSONObject getUseCarReason(BaseOrgParam param) {
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
	public JSONObject cancelOrder(String orderno, String ordertype) {
		OrderApiParam oap = new OrderApiParam();
		oap.setOrderid(orderno);
		oap.setOrdertype(ordertype);
		oap.setUsetype(OrderEnum.USETYPE_PUBLIC.code);
		oap.setOrderstate(OrderState.CANCEL.state);
		oap.setReqsrc(CancelParty.ORGAN.code);
		String carserviceApiUrl = SystemConfig.getSystemProperty("carserviceApiUrl");
		JSONObject result = templateHelper.dealRequestWithFullUrlToken(carserviceApiUrl+"/OrderApi/ChangeOrderState", HttpMethod.POST, oap.getToken(), oap, JSONObject.class);
		return result;
	}
	
	/**
	 * 获取机构用户常用联系人列表
	 * 
	 * @param param
	 * @return
	 */
	public JSONObject getMostContact(BaseOrgParam param) {
		JSONObject result = templateHelper.dealRequestWithToken("/Order/GetMostContact", HttpMethod.POST, param.getToken(), param, JSONObject.class);
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
	public JSONObject getMostAddress(BaseOrgParam param) {
		JSONObject result = templateHelper.dealRequestWithToken("/Order/GetMostAddress", HttpMethod.POST, param.getToken(), param, JSONObject.class);
		return result;
	}
	
	/**
	 * 创建机构订单
	 * 
	 * @param orderInfo
	 * @return
	 */
	public JSONObject createOrgOrder(OrgOrder orderInfo) {
		//下单来源为机构端
		orderInfo.setOrdersource(OrderEnum.ORDERSOURCE_ORGAN.code);
		JSONObject result = templateHelper.dealRequestWithToken("/Order/CreateOrgOrder", HttpMethod.POST, null, orderInfo, JSONObject.class);
		return result;
	}

	/**
	 * 获取机构订单费用
	 * 
	 * @param param
	 * @return
	 */
	public JSONObject getOrgOrderCost(OrderCostParam param) {
		param.setUsetype("0");
		param.setRulestype("1");
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
		JSONObject result = templateHelper.dealRequestWithFullUrl(
				carserviceApiUrl+"/BaiduApi/GetLatLng", 
				HttpMethod.POST, 
				param, 
				JSONObject.class,
				new HashMap<>());
		return result;
	}
	
	/**
	 * 根据经纬度获取地址(逆解析)
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
	
	public JSONObject getOrgUserPubBusCity(Map<String, Object> param, String userToken) {
		return templateHelper.dealRequestWithToken("/Order/GetOrgUserPubBusCity", HttpMethod.POST, userToken, param, JSONObject.class);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getRuyueCompany(String userToken) {
        return templateHelper.dealRequestWithToken("/Order/GetRuyueCompany", HttpMethod.POST, userToken, null, Map.class);
    }
}
