package com.szyciov.operate.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.szyciov.operate.vo.request.GetManualSelectDriverRequest;
import com.szyciov.operate.vo.request.GetTaxiManualSelectDriverRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.driver.param.OrderCostParam;
import com.szyciov.dto.PagingResponse;
import com.szyciov.entity.Retcode;
import com.szyciov.entity.Select2Entity;
import com.szyciov.enums.OrderEnum;
import com.szyciov.enums.OrderVarietyEnum;
import com.szyciov.lease.param.GetCarTypesParam;
import com.szyciov.op.dto.request.GetManualDriverRequest;
import com.szyciov.op.dto.request.GetTaxiManualDriverRequest;
import com.szyciov.op.entity.OpOrder;
import com.szyciov.op.entity.OpTaxiOrder;
import com.szyciov.op.entity.OpUser;
import com.szyciov.op.param.BaseOpParam;
import com.szyciov.operate.service.OrderService;
import com.szyciov.operate.vo.request.GetManualSelectDriverRequest;
import com.szyciov.operate.vo.request.GetTaxiManualSelectDriverRequest;
import com.szyciov.org.entity.OrgMostContact;
import com.szyciov.param.BaiduApiQueryParam;
import com.szyciov.param.OrderApiParam;
import com.szyciov.param.Select2Param;
import com.szyciov.util.WebExceptionHandle;

import net.sf.json.JSONObject;

/**
 * @ClassName OrderController 
 * @author Efy Shu
 * @Description 个人订单控制类
 * @date 2016年10月17日 下午2:20:10 
 */
@Controller
public class OrderController extends WebExceptionHandle {
	private OrderService os;
	
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
		starttime.set(System.currentTimeMillis());
		String page = "";
		Map<String,Object> model = null;
		try {
			String usertoken = getUserToken(request);
			OpUser user = getLoginOpUser(request);
			
			model = os.initIndex(user, usertoken);
			if(orderType.equals("jieji")){
				page = "resource/order/jieji";
			}else if(orderType.equals("songji")){
				page = "resource/order/songji";
			}else if(orderType.equals("yueche")){
				page = "resource/order/yueche";
			}else if(orderType.equals("Index")){
				page = "resource/order/yueche";
			}else{
				page = "error";
			}
		} catch (Exception e) {
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
		starttime.set(System.currentTimeMillis());
		Map<String,Object> model = os.success(orderno);
		String page = "resource/order/success";
		return new ModelAndView(page, model);
	}
	
	/**
	 * 约车失败
	 * @param orderno
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/Order/Failed/{orderno}")
	public ModelAndView failed(@PathVariable String orderno,HttpServletRequest request){
		starttime.set(System.currentTimeMillis());
		Map<String,Object> model = os.failed(orderno);
		String page = "resource/order/failed";
		return new ModelAndView(page, model);
	}
	
	/**
	 * 获取加入toC业务的租赁公司列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/Order/GetCompanyList")
	public JSONObject getCompanyList(HttpServletRequest request){
		starttime.set(System.currentTimeMillis());
		BaseOpParam param = new BaseOpParam();
		param.setOpUser(getLoginOpUser(request));
		param.setToken(getUserToken(request));
		JSONObject result = os.getCompanyList(param);
		releaseResource(os);
		return checkResult(result);
	}

    /**
     * 获取服务车企列表
     * @param request r
     * @return r
     */
    @ResponseBody
    @RequestMapping(value="/Order/GetBelongCompanyList")
    public JSONObject getBelongCompanyList(HttpServletRequest request){
        BaseOpParam param = new BaseOpParam();
        param.setOpUser(getLoginOpUser(request));
        param.setToken(getUserToken(request));
        JSONObject result = os.getBelongCompanyList(param);
        return checkResult(result);
    }

	/**
	 * 获取城市列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/Order/GetCities")
	public JSONObject getCities(HttpServletRequest request) {
		starttime.set(System.currentTimeMillis());
		BaseOpParam param = new BaseOpParam();
		param.setOpUser(getLoginOpUser(request));
		param.setToken(getUserToken(request));
		JSONObject result =  os.getCities(param);
		releaseResource(os);
		return checkResult(result);
	}

	/**
	 * 获取全国机场列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/Order/GetAirPorts")
	public JSONObject getAirPorts(HttpServletRequest request){
		starttime.set(System.currentTimeMillis());
		BaseOpParam param = new BaseOpParam();
		param.setOpUser(getLoginOpUser(request));
		param.setToken(getUserToken(request));
		JSONObject result =  os.getAirPorts(param);
		releaseResource(os);
		return checkResult(result);
	}
	
	/**
	 * 获取用车事由列表
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/Order/GetUseCarReason")
	public JSONObject getUseCarReason(HttpServletRequest request) {
		starttime.set(System.currentTimeMillis());
		BaseOpParam param = new BaseOpParam();
		param.setOpUser(getLoginOpUser(request));
		param.setToken(getUserToken(request));
		JSONObject result =  os.getUseCarReason(param);
		releaseResource(os);
		return checkResult(result);
	}
	
	/**
	 * 获取租赁公司列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/Order/GetLeaseList")
	public JSONObject getLeaseList(HttpServletRequest request) {
		starttime.set(System.currentTimeMillis());
		BaseOpParam param = new BaseOpParam();
		param.setOpUser(getLoginOpUser(request));
		param.setToken(getUserToken(request));
		JSONObject result =  os.getLeaseList(param);
		releaseResource(os);
		return checkResult(result);
	}

	/**
	 * 获取订单状态
	 * 
	 * @param orderno
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/Order/CheckOrderState")
	public JSONObject checkOrderState(@RequestBody Map<String,Object> param) {
		starttime.set(System.currentTimeMillis());
		JSONObject result = os.checkOrderState((String)param.get("orderno"));
		releaseResource(os);
		return checkResult(result);
	}

	/**
	 * 取消订单
	 * 
	 * @param orderno
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/Order/CancelOrder")
	public JSONObject cancelOrder(@RequestBody OrderApiParam param) {
		starttime.set(System.currentTimeMillis());
		JSONObject result = os.cancelOrder(param);
		releaseResource(os);
		return checkResult(result);
	}
	
	
	/**
	 * 将订单变更为人工派单
	 * 
	 * @param orderno
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/Order/ManticOrder")
	public JSONObject manticOrder(@RequestBody OrderApiParam param) {
		starttime.set(System.currentTimeMillis());
		JSONObject result = os.manticOrder(param);
		releaseResource(os);
		return checkResult(result);
	}
	
	/**
	 * 获取个人用户常用联系人列表
	 * 
	 * @param param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/Order/GetMostContactForSelect")
	public List<Select2Entity> getMostContactForSelect(@RequestBody Select2Param param,HttpServletRequest request) {
		starttime.set(System.currentTimeMillis());
		param.setToken(getUserToken(request));
		JSONObject result = os.getMostContactForSelect(param);
		if(result.getInt("status") == Retcode.OK.code){
			return result.getJSONArray("list");
		}else{
			return new ArrayList<Select2Entity>();
		}
	}
	
	/**
	 * 获取个人用户列表
	 * 
	 * @param param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/Order/GetPeUserForSelect")
	public List<Select2Entity> getPeUserForSelect(@RequestBody Select2Param param,HttpServletRequest request) {
		starttime.set(System.currentTimeMillis());
		param.setToken(getUserToken(request));
		JSONObject result = os.getPeUserForSelect(param);
		if(result.getInt("status") == Retcode.OK.code){
			return result.getJSONArray("list");
		}else{
			return new ArrayList<Select2Entity>();
		}
	}
	/**
	 * 获取个人用户常用联系人列表
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/Order/GetMostContact")
	public JSONObject getMostContact(@RequestBody Select2Param param,HttpServletRequest request) {
		starttime.set(System.currentTimeMillis());
		param.setToken(getUserToken(request));
		JSONObject result = os.getMostContact(param);
		releaseResource(os);
		return checkResult(result);
	}

	/**
	 * 添加机构用户常用联系人
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/Order/AddMostContact")
	public JSONObject addMostContact(@RequestBody OrgMostContact param ,HttpServletRequest request) {
		starttime.set(System.currentTimeMillis());
		param.setUserid(getLoginOpUser(request).getId());
		JSONObject result =  os.addMostContact(param);
		releaseResource(os);
		return checkResult(result);
	}
	
	/**
	 * 删除机构用户常用联系人
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/Order/DelMostContact")
	public JSONObject delMostContact(@RequestBody OrgMostContact param , HttpServletRequest request) {
		starttime.set(System.currentTimeMillis());
		param.setUserid(getLoginOpUser(request).getId());
		JSONObject result =  os.delMostContact(param);
		releaseResource(os);
		return checkResult(result);
	}
	
	/**
	 * 获取机构用户常用地址列表
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/Order/GetMostAddress")
	public JSONObject getMostAddress(@RequestBody BaseOpParam param,HttpServletRequest request) {
		starttime.set(System.currentTimeMillis());
		param.setOpUser(getLoginOpUser(request));
		param.setToken(getUserToken(request));
		JSONObject result =  os.getMostAddress(param);
		releaseResource(os);
		return checkResult(result);
	}
	
	/**
	 * 创建机构订单
	 * 
	 * @param orderInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/Order/CreateOpOrder")
	public JSONObject createOpOrder(@RequestBody OpOrder orderInfo,HttpServletRequest request) {
		starttime.set(System.currentTimeMillis());
		JSONObject result =  os.createOpOrder(orderInfo);
		releaseResource(os);
		return checkResult(result);
	}

	/**
	 * 获取机构订单费用
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/Order/GetOpOrderCost")
	public JSONObject getOpOrderCost(@RequestBody OrderCostParam param,HttpServletRequest request) {
		starttime.set(System.currentTimeMillis());
		param.setToken(getUserToken(request));
		param.setOrderprop(OrderVarietyEnum.OPERATING_NET.icode);
		JSONObject result =  os.getOpOrderCost(param);
		releaseResource(os);
		return checkResult(result);
	}

	/**
	 * 获取机构车型
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/Order/GetCarTypes")
	public JSONObject getCarTypes(@RequestBody GetCarTypesParam param,HttpServletRequest request) {
		starttime.set(System.currentTimeMillis());
		param.setToken(getUserToken(request));
		JSONObject result =  os.getCarTypes(param);
		releaseResource(os);
		return checkResult(result);
	}
	
	/**
	 * 根据地址获取经纬度
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/Order/GetLatLng")
	public JSONObject getLatLng(@RequestBody BaiduApiQueryParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result =  os.getLatLng(param);
		releaseResource(os);
		return checkResult(result);
	}
	
	/**
	 * 根据经纬度获取地址
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/Order/GetAddress")
	public JSONObject getAddress(@RequestBody BaiduApiQueryParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result =  os.getAddress(param);
		releaseResource(os);
		return checkResult(result);
	}
	
	/**
	 * 出租车下单
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/Order/TaxiIndex")
	public ModelAndView taxiOrderIndex(HttpServletRequest request) {
		starttime.set(System.currentTimeMillis());
		ModelAndView view = new ModelAndView();
		view.setViewName("resource/taxiOrder/order");
		//查询toC租赁公司
		BaseOpParam baseOpParam = new BaseOpParam();
		baseOpParam.setToken(getUserToken(request));
//		JSONObject companyJson = os.getCompanyList(baseOpParam);
        JSONObject companyJson = os.getBelongCompanyList(baseOpParam);
		view.addObject("companyList", companyJson.get("lease"));
		return view;
	}
	
	/**
	 * 获取出租车订单预估费用
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/Order/GetOpTaxiOrderCost")
	@ResponseBody
	public JSONObject getOpTaxiOrderCost(@RequestBody OrderCostParam param, HttpServletRequest request) {
		starttime.set(System.currentTimeMillis());
		String userToken = getUserToken(request);
		param.setToken(userToken);
		return os.getOpTaxiOrderCost(param);
	}
	
	/**
	 * 获取出租车订单预估费用
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/Order/CheckOpTaxiAccountRules")
	@ResponseBody
	public Map<String, Object> checkOpTaxiAccountRules(@RequestBody OrderCostParam param, HttpServletRequest request) {
		starttime.set(System.currentTimeMillis());
		String userToken = getUserToken(request);
		param.setToken(userToken);
		return os.checkOpTaxiAccountRules(param);
	}
	
	/**
	 * 创建出租车订单
	 * @param object
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/Order/CreateOpTaxiOrder")
	@ResponseBody
	public JSONObject createOpTaxiOrder(@RequestBody OpTaxiOrder object, HttpServletRequest request) {
		starttime.set(System.currentTimeMillis());
		object.setOrdersource(OrderEnum.ORDERSOURCE_OPERATE.code);
		return os.createOpTaxiOrder(object);
	}
	
	/**
	 * 查询网约车业务城市
	 * @param param
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/Order/GetNetBusCity")
	@ResponseBody
	public JSONObject getNetBusCity(@RequestBody Map<String, Object> param, HttpServletRequest request) {
		starttime.set(System.currentTimeMillis());
		String userToken = getUserToken(request);
		JSONObject result =  os.getNetBusCity(param, userToken);
		releaseResource(os);
		return checkResult(result);
	}
	
	/**
	 * 查询出租车业务城市
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/Order/GetTaxiBusCity")
	@ResponseBody
	public JSONObject getTaxiBusCity(HttpServletRequest request) {
		starttime.set(System.currentTimeMillis());
		Map<String, String> param = new HashMap<String, String>();
		String userToken = getUserToken(request);
		return os.getTaxiBusCity(param, userToken);
	}
	
	/**
	 * 验证城市是否开启业务
	 * @param city
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/Order/CheckTaxiBusCity")
	@ResponseBody
	public JSONObject checkTaxiBusCity(@RequestParam(value = "city", required = false) String city, HttpServletRequest request) {
		starttime.set(System.currentTimeMillis());
		Map<String, String> param = new HashMap<String, String>();
		param.put("city", city);
		String userToken = getUserToken(request);
		JSONObject json = os.getTaxiBusCity(param, userToken);
		if(null == json || json.isEmpty()) {
			json = new JSONObject();
			json.put("status", "-1");
		}
		return json;
	}


    /**
     * 获取指派司机下拉数据
     * @param model 查询条件
     * @return 指派司机
     */
    @ResponseBody
    @RequestMapping(value="/Order/getManualSelectDriverForSelect")
    public List<Select2Entity> getManualSelectDriverForSelect(@RequestBody GetManualSelectDriverRequest model){
        GetManualDriverRequest condition = new GetManualDriverRequest();
        condition.setKeyword(model.getKeyword());
        condition.setToken(getUserToken());
        condition.setLeaseCompanyId(model.getLeaseCompanyId());
        List<Select2Entity> result = os.getManualSelectDriverForSelect(condition);
        return result;
    }


    /**
     * 获取指派司机列表数据
     * @param model 查询条件
     * @return 指派司机
     */
    @ResponseBody
    @RequestMapping(value="/Order/GetManualSelectDriver")
    public PagingResponse getManualSelectDriver(@RequestBody GetManualSelectDriverRequest model){
        ModelMapper mapper = new ModelMapper();
        GetManualDriverRequest condition = mapper.map(model, GetManualDriverRequest.class);
        condition.setKeyword(model.getKeyword());
        condition.setToken(getUserToken());
        PagingResponse result = os.getManualSelectDriver(condition);
        return result;
    }


    /**
     * 获取出租车指派司机下拉数据
     * @param model 查询条件
     * @return 指派司机
     */
    @ResponseBody
    @RequestMapping(value="/Order/getTaxiManualSelectDriverForSelect")
    public List<Select2Entity> getTaxiManualSelectDriverForSelect(@RequestBody GetTaxiManualSelectDriverRequest model){
        GetTaxiManualDriverRequest condition = new GetTaxiManualDriverRequest();
        condition.setKeyword(model.getKeyword());
        condition.setToken(getUserToken());
        condition.setLeaseCompanyId(model.getLeaseCompanyId());
        List<Select2Entity> result = os.getTaxiManualSelectDriverForSelect(condition);
        return result;
    }


    /**
     * 获取出租车指派司机列表数据
     * @param model 查询条件
     * @return 指派司机
     */
    @ResponseBody
    @RequestMapping(value="/Order/GetTaxiManualSelectDriver")
    public PagingResponse getTaxiManualSelectDriver(@RequestBody GetTaxiManualSelectDriverRequest model){
        ModelMapper mapper = new ModelMapper();
        GetTaxiManualDriverRequest condition = mapper.map(model, GetTaxiManualDriverRequest.class);
        condition.setKeyword(model.getKeyword());
        condition.setToken(getUserToken());
        PagingResponse result = os.getTaxiManualSelectDriver(condition);
        return result;
    }

 }
