/**
 * 
 */
package com.szyciov.organ.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.driver.param.OrderCostParam;
import com.szyciov.lease.param.GetCarTypesParam;
import com.szyciov.org.entity.OrgMostContact;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.org.param.BaseOrgParam;
import com.szyciov.org.param.OrgUserParam;
import com.szyciov.organ.service.OrderService;
import com.szyciov.util.ApiExceptionHandle;

import net.sf.json.JSONObject;

/**
 * @ClassName OrderController 
 * @author Efy Shu
 * @Description 机构端订单控制类
 * @date 2016年10月17日 下午4:04:08 
 */
@Controller
public class OrderController extends ApiExceptionHandle {
	private OrderService os;
	
	@Resource(name="OrderService")
	public void setMs(OrderService os) {
		this.os = os;
	}
	
	/**
	 * 根据ID查机构用户
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "api/Order/GetOrgUserById")
	public JSONObject getOrgUserById(@RequestBody OrgUserParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = os.getOrgUserById(param);
		return checkResult(result);
	}

	/**
	 * 获取租赁公司列表
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "api/Order/GetLeaseList")
	public JSONObject getOrganList(@RequestBody BaseOrgParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = os.getLeaseList(param);
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
		return checkResult(result);
	}

	/**
	 * 取消订单
	 * 
	 * @param orderno
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "api/Order/CancelOrder/{orderno}")
	public JSONObject cancelOrder(@PathVariable String orderno){
		starttime.set(System.currentTimeMillis());
		JSONObject result = os.cancelOrder(orderno);
		return checkResult(result);
	}
	
	/**
	 * 添加机构用户常用联系人
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "api/Order/AddMostContact")
	public JSONObject addMostContact(@RequestBody OrgMostContact param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = os.addMostContact(param);
		return checkResult(result);
	}
	
	/**
	 * 删除机构用户常用联系人
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "api/Order/DelMostContact")
	public JSONObject delMostContact(@RequestBody OrgMostContact param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = os.delMostContact(param);
		return checkResult(result);
	}
	
	/**
	 * 获取机构用户常用联系人列表
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "api/Order/GetMostContact")
	public JSONObject getMostContact(@RequestBody BaseOrgParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = os.getMostContact(param);
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
		JSONObject result = os.getMostAddress(param);
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
		JSONObject result = os.createOrgOrder(orderInfo);
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
		JSONObject result = os.getCarTypes(param);
		return checkResult(result);
	}
	
	/**
	 * 获取预估费用
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "api/Order/GetOrderCost")
	public JSONObject getOrderCost(@RequestBody OrderCostParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = os.getOrderCost(param);
		return checkResult(result);
	}
	
	/**
	 * 查询因公用车业务城市
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "api/Order/GetOrgUserPubBusCity")
	public JSONObject getOrgUserPubBusCity(@RequestBody Map<String, Object> param) {
		starttime.set(System.currentTimeMillis());
		JSONObject result =  os.getOrgUserPubBusCity(param);
		releaseResource(os);
		return checkResult(result);
	}
	
}
