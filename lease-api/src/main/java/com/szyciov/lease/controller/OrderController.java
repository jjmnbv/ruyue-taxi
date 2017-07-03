package com.szyciov.lease.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.szyciov.lease.dto.request.GetFreeDriverRequest;
import com.szyciov.dto.PagingResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.driver.param.OrderCostParam;
import com.szyciov.exception.EmptyException;
import com.szyciov.lease.param.GetCarTypesParam;
import com.szyciov.lease.param.GetSendInfoParam;
import com.szyciov.lease.param.PubDriverInBoundParam;
import com.szyciov.lease.service.OrderService;
import com.szyciov.op.entity.OpOrder;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.org.param.BaseOrgParam;
import com.szyciov.param.Select2Param;
import com.szyciov.util.ApiExceptionHandle;

import net.sf.json.JSONObject;

/**
 * @ClassName MessageController
 * @author Efy Shu
 * @Description 订单处理类
 * @date 2016年8月24日 下午5:24:28
 */
@Controller
public class OrderController extends ApiExceptionHandle {
	private OrderService os;

	public OrderController() {

	}

	@Resource(name = "OrderService")
	public void setMs(OrderService os) {
		this.os = os;
	}

	// =====================================================================//
	/**
	 * 获取城市列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws EmptyException 
	 */
	@ResponseBody
	@RequestMapping(value = "api/Order/GetCities")
	public JSONObject getCities(HttpServletRequest request, HttpServletResponse response){
		starttime.set(System.currentTimeMillis());
		JSONObject result =  os.getCities();
		releaseResource(os);
		return checkResult(result);
	}

	/**
	 * 获取机构列表(因公)
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "api/Order/GetOrganList/{companyid}")
	public JSONObject getOrganList(@PathVariable String companyid){
		starttime.set(System.currentTimeMillis());
		JSONObject result =  os.getOrganList(companyid);
		releaseResource(os);
		return checkResult(result);
	}
	
	/**
	 * 获取机构列表(因私)
	 * @param companyid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "api/Order/GetPriOrganList/{companyid}")
	public JSONObject getPriOrganList(@PathVariable String companyid) {
		starttime.set(System.currentTimeMillis());
		JSONObject result =  os.getPriOrganList(companyid);
		return checkResult(result);
	}

	/**
	 * 获取用车事由列表
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "api/Order/GetUseCarReason")
	public JSONObject getUseCarReason(){
		starttime.set(System.currentTimeMillis());
		JSONObject result =  os.getUseCarReason();
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
	@RequestMapping(value = "api/Order/CheckOrderState/{orderno}")
	public JSONObject checkOrderState(@PathVariable String orderno){
		starttime.set(System.currentTimeMillis());
		JSONObject result = os.checkOrderState(orderno);
		releaseResource(os);
		return checkResult(result);
	}
	
	/**
	 * 获取订单简要信息
	 * 
	 * @param orderno
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "api/Order/GetMinOrderInfo/{orderno}")
	public JSONObject getMinOrderInfo(@PathVariable String orderno){
		starttime.set(System.currentTimeMillis());
		JSONObject result = os.getMinOrderInfo(orderno);
		releaseResource(os);
		return checkResult(result);
	}
	
	/**
	 * 下拉搜索机构用户
	 * 
	 * @param param
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "api/Order/GetOrgUserForSelect")
	public JSONObject getOrgUserForSelect(@RequestBody Select2Param param,HttpServletRequest request){
		starttime.set(System.currentTimeMillis());
		JSONObject result = os.getOrgUserForSelect(param);
		releaseResource(os);
		return checkResult(result);
	}

	/**
	 * 下拉搜索机构用户常用联系人
	 * 
	 * @param param
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "api/Order/GetFavUserForSelect")
	public JSONObject getFavUserForSelect(@RequestBody Select2Param param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = os.getFavUserForSelect(param);
		releaseResource(os);
		return checkResult(result);
	}

	/**
	 * 获取机构用户列表
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "api/Order/GetOrgUser", method = { RequestMethod.POST })
	public JSONObject getOrgUser(@RequestBody Select2Param param){
		starttime.set(System.currentTimeMillis());
		JSONObject result =  os.getOrgUser(param);
		releaseResource(os);
		return checkResult(result);
	}

	/**
	 * 获取机构用户常用联系人列表
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "api/Order/GetFavUser", method = { RequestMethod.POST })
	public JSONObject getFavUser(@RequestBody Select2Param param){
		starttime.set(System.currentTimeMillis());
		JSONObject result =  os.getFavUser(param);
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
	@RequestMapping(value = "api/Order/GetMostAddress")
	public JSONObject getMostAddress(@RequestBody BaseOrgParam param){
		starttime.set(System.currentTimeMillis());
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
	@RequestMapping(value = "api/Order/CreateOrgOrder")
	public JSONObject createOrgOrder(@RequestBody OrgOrder orderInfo){
		starttime.set(System.currentTimeMillis());
		JSONObject result =  os.createOrgOrder(orderInfo);
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
	@RequestMapping(value = "api/Order/GetOrgOrderCost")
	public JSONObject getOrgOrderCost(@RequestBody OrderCostParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result =  os.getOrgOrderCost(param);
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
	@RequestMapping(value = "api/Order/GetCarTypes")
	public JSONObject getCarTypes(@RequestBody GetCarTypesParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result =  os.getCarTypes(param);
		releaseResource(os);
		return checkResult(result);
	}

	/**
	 * 创建个人订单
	 * 
	 * @param orderInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "api/Order/CreateOpOrder")
	public JSONObject createOpOrder(@RequestBody OpOrder orderInfo){
		starttime.set(System.currentTimeMillis());
		JSONObject result =  os.createOpOrder(orderInfo);
		releaseResource(os);
		return checkResult(result);
	}
	
	/**
	 * 获取派单时限
	 * 
	 * @param orderInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "api/Order/GetSendRuleInfo")
	public JSONObject getSendRuleInfo(@RequestBody GetSendInfoParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result =  os.getSendRuleInfo(param);
		releaseResource(os);
		return checkResult(result);
	}
	
	/**
	 * 获取特殊司机
	 * 
	 * @param orderInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "api/Order/GetSpecialDrivers")
	public JSONObject getSpecialDrivers(@RequestBody PubDriverInBoundParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result =  os.getSpecialDrivers(param);
		releaseResource(os);
		return checkResult(result);
	}
	
	/**
	 * 获取范围内司机
	 * 
	 * @param orderInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "api/Order/GetDriversInBound")
	public JSONObject getDriversInBound(@RequestBody PubDriverInBoundParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result =  os.getDriversInBound(param);
		releaseResource(os);
		return checkResult(result);
	}
	
	/**
	 * 查询机构用户下单业务城市
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "api/Order/GetOrgUserBusCity")
	public JSONObject getOrgUserBusCity(@RequestBody Map<String, Object> param) {
		starttime.set(System.currentTimeMillis());
		JSONObject result =  os.getOrgUserBusCity(param);
		releaseResource(os);
		return checkResult(result);
	}


    /**
     * 获取指派司机列表
     * @param model 查询条件
     * @return 司机列表
     */
    @ResponseBody
    @RequestMapping(value = "api/Order/GetManualSelectDriver")
    public PagingResponse getManualSelectDriver(@RequestBody GetFreeDriverRequest model) {
        starttime.set(System.currentTimeMillis());
        PagingResponse result =  os.getManualSelectDriver(model);
        return result;
    }
}
