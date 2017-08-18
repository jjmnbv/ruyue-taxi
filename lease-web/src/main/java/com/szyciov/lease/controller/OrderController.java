package com.szyciov.lease.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.szyciov.driver.param.BaseParam;
import com.szyciov.driver.param.OrderCostParam;
import com.szyciov.dto.PagingResponse;
import com.szyciov.entity.Retcode;
import com.szyciov.entity.Select2Entity;
import com.szyciov.enums.OrderEnum;
import com.szyciov.enums.OrderVarietyEnum;
import com.szyciov.lease.dto.request.GetFreeDriverRequest;
import com.szyciov.lease.entity.User;
import com.szyciov.lease.param.GetCarTypesParam;
import com.szyciov.lease.service.OrderService;
import com.szyciov.lease.vo.request.GetManualSelectDriverRequest;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.org.param.BaseOrgParam;
import com.szyciov.param.BaiduApiQueryParam;
import com.szyciov.param.OrderApiParam;
import com.szyciov.param.Select2Param;
import com.szyciov.util.WebExceptionHandle;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 订单控制器
 * @ClassName MessageController 
 * @author Efy Shu
 * @Description 处理订单相关请求
 * @date 2016年8月24日 下午5:24:28
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
	
	//=====================================================================//
	@RequestMapping(value="/Order/{orderType}")
	public ModelAndView index(@PathVariable String orderType,HttpServletRequest request){
		User user = getLoginLeUser(request);
		Map<String,Object> model = os.initIndex(user.getLeasescompanyid());
		String page = "";
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
		return new ModelAndView(page, model);
	}
	
	@ResponseBody
	@RequestMapping(value="/Order/Success/{orderno}")
	public ModelAndView success(@PathVariable String orderno,HttpServletRequest request){
		Map<String,Object> model = os.success(orderno);
		String page = "resource/order/success";
		return new ModelAndView(page, model);
	}
	
	@ResponseBody
	@RequestMapping(value="/Order/Failed/{orderno}")
	public ModelAndView failed(@PathVariable String orderno,HttpServletRequest request){
		Map<String,Object> model = os.failed(orderno);
		String page = "resource/order/failed";
		return new ModelAndView(page, model);
	}
	
	@ResponseBody
	@RequestMapping(value="/Order/GetOrgOrderCost")
	public JSONObject getOrgOrderCost(@RequestBody OrderCostParam param,HttpServletRequest request){
		starttime.set(System.currentTimeMillis());
		User user = getLoginLeUser(request);
		param.setToken(getUserToken(request));
		param.setOrderprop(OrderVarietyEnum.LEASE_NET.icode);
		param.setCompanyid(user.getLeasescompanyid());
		JSONObject result = os.getOrgOrderCost(param);
		releaseResource(os);
		return checkResult(result);
	}
	
	@ResponseBody
	@RequestMapping(value="/Order/GetCarTypes")
	public JSONObject getCarTypes(@RequestBody GetCarTypesParam param,HttpServletRequest request){
		starttime.set(System.currentTimeMillis());

        Object o = request.getSession().getAttribute("user");
        if(o==null){
            Map<String, Object> res = os.getCompanyidByruyue(new HashedMap());
            String companyid = (String)res.get("companyid");
            param.setCompanyid(companyid);
            param.setToken("");
        }else{
            User user = getLoginLeUser(request);
            param.setToken(getUserToken(request));
            param.setCompanyid(user.getLeasescompanyid());
        }

		JSONObject result = os.getCarTypes(param);
		releaseResource(os);
		return checkResult(result);
	}
	
	@ResponseBody
	@RequestMapping(value="/Order/CheckOrderState", method = {RequestMethod.POST })
	public JSONObject checkOrderState(@RequestBody Map<String, Object> param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = os.checkOrderState((String)param.get("orderno"));
		releaseResource(os);
		return checkResult(result);
	}
	
	@ResponseBody
	@RequestMapping(value="/Order/CancelOrder", method = {RequestMethod.POST })
	public JSONObject cancelOrder(@RequestBody OrderApiParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = os.cancelOrder(param);
		releaseResource(os);
		return checkResult(result);
	}
	
	@ResponseBody
	@RequestMapping(value="/Order/ManticOrder", method = {RequestMethod.POST })
	public JSONObject manticOrder(@RequestBody OrderApiParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = os.manticOrder(param);
		releaseResource(os);
		return checkResult(result);
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/Order/GetOrgUserForSelect")
	public List<Select2Entity> getOrgUserForSelect(@RequestBody Select2Param param,HttpServletRequest request){
		param.setToken(getUserToken(request));
		param.setCompanyid(getLoginLeUser(request).getLeasescompanyid());
		JSONObject result = os.getOrgUserForSelect(param);
		if(result.getInt("status") == Retcode.OK.code){
			return result.getJSONArray("list");
		}else{
			return new ArrayList<Select2Entity>();
		}
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/Order/GetFavUserForSelect")
	public List<Select2Entity> getFavUserForSelect(@RequestBody Select2Param param,HttpServletRequest request){
		param.setToken(getUserToken(request));
		JSONObject result = os.getFavUserForSelect(param);
		if(result.getInt("status") == Retcode.OK.code){
			return result.getJSONArray("list");
		}else{
			return new ArrayList<Select2Entity>();
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/Order/GetOrgUser", method = {RequestMethod.POST })
	public JSONObject getOrgUser(@RequestBody Select2Param param,HttpServletRequest request){
		starttime.set(System.currentTimeMillis());
		if(StringUtils.isBlank(param.getUsetype())) {
			param.setUsetype(OrderEnum.USETYPE_PUBLIC.code);
		}
		if(StringUtils.isBlank(param.getOrdertype())) {
			param.setOrdertype(OrderEnum.ORDERTYPE_RESERVE.code);
		}
		param.setToken(getUserToken(request));
		param.setCompanyid(getLoginLeUser(request).getLeasescompanyid());
		JSONObject result = os.getOrgUser(param);
		releaseResource(os);
		return checkResult(result);
	}
	
	@ResponseBody
	@RequestMapping(value="/Order/GetFavUser", method = {RequestMethod.POST })
	public JSONObject getFavUser(@RequestBody Select2Param param,HttpServletRequest request){
		starttime.set(System.currentTimeMillis());
		param.setToken(getUserToken(request));
		JSONObject result = os.getFavUser(param);
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
	public JSONObject getMostAddress(@RequestBody BaseOrgParam param) {
		starttime.set(System.currentTimeMillis());
		JSONObject result =  os.getMostAddress(param);
		releaseResource(os);
		return checkResult(result);
	}
	
	@ResponseBody
	@RequestMapping(value="/Order/CreateOrgOrder")
	public JSONObject createOrgOrder(@RequestBody OrgOrder orderInfo,HttpServletRequest request){
		starttime.set(System.currentTimeMillis());

        Object o = request.getSession().getAttribute("user");
        if(o==null){
            orderInfo.setOperator(orderInfo.getUserid());
            Map<String, Object> param = new HashedMap();
            Map<String, Object> res = os.getCompanyidByruyue(param);
            String companyid = (String)res.get("companyid");
            orderInfo.setCompanyid(companyid);
            //验证乘车人数据
            if(null == orderInfo.getPassengers()) {
                orderInfo.setPassengers("");
            }
            if(null == orderInfo.getPassengerphone()) {
                orderInfo.setPassengerphone("");
            }
        }else{
            User u = getLoginLeUser(request);
            orderInfo.setCompanyid(u.getLeasescompanyid());
            orderInfo.setOperator(u.getId());
        }

		JSONObject result = os.createOrgOrder(orderInfo);
		releaseResource(os);
		return checkResult(result);
	}
	
	@ResponseBody
	@RequestMapping(value="/Order/GetAirPorts")
	public JSONObject getAirPorts(HttpServletRequest request){
		starttime.set(System.currentTimeMillis());
		BaseParam param = new BaseParam();
		param.setToken(getUserToken(request));
		JSONObject result =  os.getAirPorts(param);
		releaseResource(os);
		return checkResult(result);
	}
	
	/**
	 * 获取城市列表
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/Order/GetCities")
	public JSONObject getCities(HttpServletRequest request, HttpServletResponse response) {
		starttime.set(System.currentTimeMillis());

        Object o = request.getSession().getAttribute("user");
        BaseParam param = new BaseParam();
        if(o==null){
            param.setToken("");
        }else{
            param.setToken(getUserToken(request));
        }

		JSONObject result =  os.getCities(param);
		releaseResource(os);
		return checkResult(result);
	}
	
	/**
	 * 获取经纬度
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
	 * 获取地址
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
	 * 查询机构用户下单业务城市
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/Order/GetOrgUserBusCity")
	public JSONObject getOrgUserBusCity(@RequestBody Map<String, Object> param, HttpServletRequest request) {
		starttime.set(System.currentTimeMillis());
        String userToken = "";

        Object o = request.getSession().getAttribute("user");
        if(o==null){
            Map<String, Object> res = os.getCompanyidByruyue(new HashedMap());
            String companyid = (String)res.get("companyid");
            param.put("companyid", companyid);
        }else{
            userToken = getUserToken(request);
            User user = getLoginLeUser(request);
            param.put("companyid", user.getLeasescompanyid());
        }

		JSONObject result = os.getOrgUserBusCity(param, userToken);
		releaseResource(os);
		return checkResult(result);
	}


    /**
     * 获取指派司机下拉数据
     * @param model 查询条件
     * @return 指派司机
     */
    @ResponseBody
    @RequestMapping(value="/Order/getManualSelectDriverForSelect")
    public List<Select2Entity> getManualSelectDriverForSelect(@RequestBody GetManualSelectDriverRequest model){
        GetFreeDriverRequest condition = new GetFreeDriverRequest();
        condition.setKeyword(model.getKeyword());
        condition.setToken(getUserToken());
        condition.setLeasesCompanyId(getLoginLeUser().getLeasescompanyid());
        List<Select2Entity> result = os.getManualSelectDriverForSelect(condition);
        return result;
    }

    /**
     * 获取指派司机列表数据
     * @param model 查询条件
     * @return 指派司机
     */
    @SuppressWarnings("rawtypes")
	@ResponseBody
    @RequestMapping(value="/Order/GetManualSelectDriver")
    public PagingResponse getManualSelectDriver(@RequestBody GetManualSelectDriverRequest model, HttpServletRequest request){
        ModelMapper mapper = new ModelMapper();
        GetFreeDriverRequest condition = mapper.map(model, GetFreeDriverRequest.class);
        condition.setKeyword(model.getKeyword());

        Object o = request.getSession().getAttribute("user");
        if(o==null){
            Map<String, Object> res = os.getCompanyidByruyue(new HashedMap());
            String companyid = (String)res.get("companyid");
            condition.setLeasesCompanyId(companyid);
            condition.setToken("");
        }else{
            condition.setToken(getUserToken());
            condition.setLeasesCompanyId(getLoginLeUser().getLeasescompanyid());
        }

		PagingResponse result = os.getManualSelectDriver(condition);
        return result;
    }


	/**
	 * 获取如约签约的机构信息列表
	 * @param param
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/Order/GetOrgans")
	public Map<String,Object> getOrgans(@RequestParam Map<String, Object> param) {
		try{
			return os.getOrgans(param);
		}catch (Exception e){
			Map<String,Object> res = new HashMap<String,Object>();
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message",Retcode.EXCEPTION.msg);
			return res;
		}
	}

	/**
	 * 获取机构下的用户信息列表
	 * @param param
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/Order/GetOrganUsers")
	public Map<String,Object> getOrganUsers(@RequestParam Map<String, Object> param) {
		try{
			return os.getOrganUsers(param);
		}catch (Exception e){
			Map<String,Object> res = new HashMap<String,Object>();
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message",Retcode.EXCEPTION.msg);
			return res;
		}
	}
}
