package com.szyciov.organ.controller;

import com.szyciov.driver.param.OrderCostParam;
import com.szyciov.lease.param.GetCarTypesParam;
import com.szyciov.org.entity.OrgMostContact;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.org.entity.OrgUser;
import com.szyciov.org.param.BaseOrgParam;
import com.szyciov.organ.service.OrderService;
import com.szyciov.param.BaiduApiQueryParam;
import com.szyciov.util.TemplateHelper;
import com.szyciov.util.WebExceptionHandle;
import net.sf.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName OrderController 
 * @author Efy Shu
 * @date 2016年10月17日 下午2:20:10 
 */
@Controller
public class OrderController extends WebExceptionHandle {
	
	private OrderService os;
	private TemplateHelper templateHelper = new TemplateHelper();
	public OrderController() {
		
	}
	
	@Resource(name="OrderService")
	public void setMs(OrderService os) {
		this.os = os;
	}
	
	/**
	 * 加载约车首页
	 * @param orderType
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/Order/{orderType}")
	public ModelAndView index(@PathVariable String orderType,HttpServletRequest request){
		return index(orderType,null,request);
	}
	
	/**
	 * 加载约车首页
	 * @param orderType
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/Order/{orderType}/{orderno}")
	public ModelAndView index(@PathVariable String orderType,@PathVariable String orderno,HttpServletRequest request){
		OrgUser user = getLoginOrgUser(request);
		String usertoken = getUserToken(request);
		Map<String,Object> model = os.initIndex(user, orderno,usertoken);
		String page = "";
		if(orderType.equals("jieji")){
			page = "resource/order/jieji";
		}else if(orderType.equals("songji")){
			page = "resource/order/songji";
		}else if(orderType.equals("yueche")){
			page = "resource/order/yueche";
		}else if(orderType.equals("index")){
			page = "resource/order/index";
		}else{
			page = "error";
		}
		return new ModelAndView(page, model);
	}
	
	/**
	 * 约车成功
	 * @param orderno
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/Order/Success/{orderno}")
	public ModelAndView success(@PathVariable String orderno,HttpServletRequest request){
		OrgUser user = getLoginOrgUser(request);
		String usertoken = getUserToken(request);
		Map<String,Object> model = os.initIndex(user, orderno,usertoken);
		String page = "resource/order/success";
//		return goToResourceByUserType(user,usertoken,orderno);
		return new ModelAndView(page, model);
	}
	
	@RequestMapping(value="/Order/Details")
	public ModelAndView go2Details(@RequestParam String id,HttpServletRequest request){
		OrgUser user = getLoginOrgUser(request);
		String usertoken = getUserToken(request);
		return goToResourceByUserType(user,usertoken,id);
	}
	
	/**
	 * 跳转到首页
	 * @param user
	 * @return
	 */
	public ModelAndView goToResourceByUserType(OrgUser user,String usertoken,String orderno){
		Map<String, Object> model = new HashMap<String, Object>();
		if(user==null){
			model.put("message", "登录失败，暂时无法获取用户信息");
			return new ModelAndView("login", model);
		}
//		String remember = (String) request.getParameter("remember");
//		boolean needremember = "1".equalsIgnoreCase(remember)?true:false;
		List<Map<String,Object>> menuinfo  = templateHelper.dealRequestWithToken("/User/GetMenuInfo", HttpMethod.POST, usertoken, user, List.class);
		model.put("menuinfo", menuinfo);
		List<Map<String,Object>> navinfo = templateHelper.dealRequestWithToken("/User/GetNavInfo", HttpMethod.POST, usertoken, user, List.class);
		model.put("navinfo", navinfo);
		model.put("username", user.getNickName());
		model.put("orderno", orderno);
//		boolean differentip = checkLastLogin(request,user.getAccount(),usertoken);
//		if(differentip){
//			model.put("differentip", differentip);
//			model.put("different4account",user.getAccount());
//		}
//		if(needremember){
//			model.put("account", user.getAccount());
//		}
//		Map<String,Object> logininfo = getPubLoginfo(request);
//		logininfo.put("loginstatus", "0");
//		logininfo.put("userid", user.getId());
//		addUserLoginLog(logininfo,usertoken);
		return new ModelAndView("resource/index", model);
	}
	/**
	 * 获取城市列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "Order/GetCities")
	public JSONObject getCities(HttpServletRequest request, HttpServletResponse response) {
		BaseOrgParam param = new BaseOrgParam();
		param.setOrgUser(getLoginOrgUser(request));
		param.setToken(getUserToken(request));
		JSONObject result =  os.getCities(param);
		return checkResult(result);
	}

	/**
	 * 获取全国机场列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "Order/GetAirPorts")
	public JSONObject getAirPorts(HttpServletRequest request){
		BaseOrgParam param = new BaseOrgParam();
		param.setOrgUser(getLoginOrgUser(request));
		param.setToken(getUserToken(request));
		JSONObject result =  os.getAirPorts(param);
		return checkResult(result);
	}
	
	/**
	 * 获取租赁公司列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "Order/GetLeaseList")
	public JSONObject getLeaseList(HttpServletRequest request) {
		BaseOrgParam param = new BaseOrgParam();
		param.setOrgUser(getLoginOrgUser(request));
		param.setToken(getUserToken(request));
		JSONObject result =  os.getLeaseList(param);
		return checkResult(result);
	}

	/**
	 * 获取用车事由列表
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "Order/GetUseCarReason")
	public JSONObject getUseCarReason(HttpServletRequest request) {
		BaseOrgParam param = new BaseOrgParam();
		param.setOrgUser(getLoginOrgUser(request));
		param.setToken(getUserToken(request));
		JSONObject result =  os.getUseCarReason(param);
		return checkResult(result);
	}

	/**
	 * 获取订单状态
	 * 
	 * @param orderno
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "Order/CheckOrderState/{orderno}")
	public JSONObject checkOrderState(@PathVariable String orderno) {
		JSONObject result = os.checkOrderState(orderno);
		return checkResult(result);
	}

	/**
	 * 取消订单
	 * 
	 * @param orderno
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "Order/CancelOrder/{orderno}")
	public JSONObject cancelOrder(@PathVariable String orderno, @RequestParam String ordertype) {
		JSONObject result = os.cancelOrder(orderno, ordertype);
		return checkResult(result);
	}

	/**
	 * 获取机构用户常用联系人列表
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "Order/GetMostContact")
	public JSONObject getMostContact(@RequestBody BaseOrgParam param,HttpServletRequest request) {
//		BaseOrgParam param = new BaseOrgParam();
//		param.setsSearch(sSearch);
		param.setiDisplayLength(5);
		param.setOrgUser(getLoginOrgUser(request));
		param.setToken(getUserToken(request));
		JSONObject result =  os.getMostContact(param);
		return checkResult(result);
	}

	/**
	 * 添加机构用户常用联系人
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "Order/AddMostContact")
	public JSONObject addMostContact(@RequestBody OrgMostContact param ,HttpServletRequest request) {
		param.setUserid(getLoginOrgUser(request).getId());
		JSONObject result =  os.addMostContact(param);
		return checkResult(result);
	}
	
	/**
	 * 删除机构用户常用联系人
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "Order/DelMostContact")
	public JSONObject delMostContact(@RequestBody OrgMostContact param , HttpServletRequest request) {
		param.setUserid(getLoginOrgUser(request).getId());
		JSONObject result =  os.delMostContact(param);
		return checkResult(result);
	}
	
	/**
	 * 获取机构用户常用地址列表
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "Order/GetMostAddress")
	public JSONObject getMostAddress(HttpServletRequest request) {
		BaseOrgParam param = new BaseOrgParam();
		param.setOrgUser(getLoginOrgUser(request));
		param.setToken(getUserToken(request));
		JSONObject result =  os.getMostAddress(param);
		return checkResult(result);
	}
	
	/**
	 * 创建机构订单
	 * 
	 * @param orderInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "Order/CreateOrgOrder")
	public JSONObject createOrgOrder(@RequestBody OrgOrder orderInfo,HttpServletRequest request) {
		OrgUser user = getLoginOrgUser(request);
		orderInfo.setOrganid(user.getOrganId());
		orderInfo.setUserid(user.getId());
		JSONObject result =  os.createOrgOrder(orderInfo);
		return checkResult(result);
	}

	/**
	 * 获取机构订单费用
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "Order/GetOrgOrderCost")
	public JSONObject getOrgOrderCost(@RequestBody OrderCostParam param,HttpServletRequest request) {
		param.setUserid(getLoginOrgUser(request).getId());
		param.setToken(getUserToken(request));
		JSONObject result =  os.getOrgOrderCost(param);
		return checkResult(result);
	}

    /**
     * 获取机构订单费用(第三方接口)
     * @param param
     * @param request
     * @return
     */
	@ResponseBody
    @RequestMapping(value = "ThirdOrder/GetOrgOrderCost")
	public JSONObject getThirdOrgOrderCost(@RequestBody OrderCostParam param, HttpServletRequest request) {
        String userToken = getUserToken(request);
        Map<String, Object> company = os.getRuyueCompany(userToken);
        if(null != company && !company.isEmpty()) {
            param.setCompanyid(company.get("id").toString());
        }
        param.setHasunit(false);
        return os.getOrgOrderCost(param);
    }

	/**
	 * 获取机构车型
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "Order/GetCarTypes")
	public JSONObject getCarTypes(@RequestBody GetCarTypesParam param,HttpServletRequest request) {
		param.setUserid(getLoginOrgUser(request).getId());
		param.setToken(getUserToken(request));
		JSONObject result =  os.getCarTypes(param);
		return checkResult(result);
	}
	
	/**
	 * 获取经纬度
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/Order/GetLatLng")
	public JSONObject getLatLng(@RequestBody BaiduApiQueryParam param){
		JSONObject result =  os.getLatLng(param);
		return checkResult(result);
	}
	
	/**
	 * 获取地址
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/Order/GetAddress")
	public JSONObject getAddress(@RequestBody BaiduApiQueryParam param){
		JSONObject result =  os.getAddress(param);
		return checkResult(result);
	}
	
	/**
	 * 查询因公用车业务城市
	 * @param param
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/Order/GetOrgUserPubBusCity")
	public JSONObject getOrgUserPubBusCity(@RequestBody Map<String, Object> param, HttpServletRequest request) {
		String userToken = getUserToken(request);
		OrgUser user = getLoginOrgUser(request);
		param.put("userid", user.getId());
		JSONObject result =  os.getOrgUserPubBusCity(param, userToken);
		return checkResult(result);
	}
}
