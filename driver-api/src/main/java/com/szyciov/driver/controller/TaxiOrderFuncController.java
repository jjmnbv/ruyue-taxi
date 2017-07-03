package com.szyciov.driver.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.driver.enums.OrderState;
import com.szyciov.driver.param.OrderLineParam;
import com.szyciov.driver.param.OrderListParam;
import com.szyciov.driver.param.PurseParam;
import com.szyciov.driver.service.TaxiOrderFuncService;
import com.szyciov.entity.OrderReviewParam;
import com.szyciov.entity.Retcode;
import com.szyciov.param.OrderApiParam;
import com.szyciov.util.ApiExceptionHandle;

import net.sf.json.JSONObject;



/**
  * @ClassName TaxiOrderFuncController
  * @author Efy Shu
  * @Description 出租车我的订单功能Controller
  * @date 2017年3月27日 14:45:27
  */ 
@Controller("TaxiOrderFuncController")
public class TaxiOrderFuncController extends ApiExceptionHandle{

	/**
	  *依赖
	  */
	private TaxiOrderFuncService taxiorderfuncservice;

	/**
	  *依赖注入
	  */
	@Resource(name="TaxiOrderFuncService")
	public void setTaxiOrderFuncService(TaxiOrderFuncService taxiorderfuncservice){
		this.taxiorderfuncservice=taxiorderfuncservice;
	}

	/**
	 * 我的订单
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="Driver/TaxiGetOrderList")
	public JSONObject taxiGetOrderList(@RequestBody OrderListParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = taxiorderfuncservice.taxiGetOrderList(param);
		releaseResource(taxiorderfuncservice);
		return checkResult(result);
	}
	
	/**
	 * 今日订单
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="Driver/TaxiGetTodayOrderList")
	public JSONObject taxiGetTodayOrderList(@RequestBody OrderListParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = taxiorderfuncservice.taxiGetTodayOrderList(param);
		releaseResource(taxiorderfuncservice);
		return checkResult(result);
	}
	
	/**
	 * 服务中订单
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="Driver/TaxiGetCurrentOrder")
	public JSONObject taxiGetCurrentOrder(@RequestBody OrderListParam param){
		starttime.set(System.currentTimeMillis());
		param.setType(0);
		JSONObject result = taxiorderfuncservice.taxiGetCurrentOrder(param);
		releaseResource(taxiorderfuncservice);
		return checkResult(result);
	}
	
	/**
	 * 获取订单详情
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="Driver/TaxiGetOrderDetail")
	public JSONObject taxiGetOrderDetail(@RequestBody OrderApiParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = taxiorderfuncservice.taxiGetOrderDetail(param);
		releaseResource(taxiorderfuncservice);
		return checkResult(result);
	}
	
	/**
	 * 出租车改变订单状态
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="Driver/TaxiChangeOrderState")
	public JSONObject taxiChangeOrderState(@RequestBody OrderApiParam param){
		starttime.set(System.currentTimeMillis());
		//改变订单状态接口不受理服务完成状态
		if(OrderState.SERVICEDONE.state.equals(param.getOrderstatus())){
			JSONObject result = new JSONObject();
			result.put("status", Retcode.FAILED.code);
			result.put("message", "订单状态不正确");
			return checkResult(result);
		}
		JSONObject result = taxiorderfuncservice.taxiChangeOrderState(param);
		releaseResource(taxiorderfuncservice);
		return checkResult(result);
	}
	
	/**
	 * 出租车抢单
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="Driver/TaxiTakingOrder")
	public JSONObject taxiTakingOrder(@RequestBody OrderApiParam param){
		starttime.set(System.currentTimeMillis());
		//抢单表示订单需要改变为待出发状态,用于校验订单状态是否正确
		param.setOrderstate(OrderState.WAITSTART.state);
		JSONObject result = taxiorderfuncservice.taxiTakingOrder(param);
		releaseResource(taxiorderfuncservice);
		return checkResult(result);
	}
	
	/**
	 * 出租车付结订单
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="Driver/PayOrder")
	public JSONObject payOrder(@RequestBody PurseParam param,HttpServletRequest request){
		starttime.set(System.currentTimeMillis());
		//保存ip地址
		param.setIpaddr(request.getRemoteAddr());
		JSONObject result = taxiorderfuncservice.payOrder(param);
		releaseResource(taxiorderfuncservice);
		return checkResult(result);
	}
	
	/**
	 * 出租车转为线上支付
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="Driver/ChangeToOnlinePay")
	public JSONObject changeToOnlinePay(@RequestBody OrderApiParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = taxiorderfuncservice.changeToOnlinePay(param);
		releaseResource(taxiorderfuncservice);
		return checkResult(result);
	}
	
	/**
	 * 出租车订单确费
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="Driver/ConfirmCost")
	public JSONObject confirmCost(@RequestBody OrderApiParam param){
		starttime.set(System.currentTimeMillis());
		//订单确费,等于参数传入订单完成
		param.setOrderstate(OrderState.SERVICEDONE.state);
		JSONObject result = taxiorderfuncservice.confirmCost(param);
		releaseResource(taxiorderfuncservice);
		return checkResult(result);
	}
	
	/**
	 * 出租车行程提醒
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="Driver/TaxiOrderRemind")
	public JSONObject taxiOrderRemind(@RequestBody OrderApiParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = taxiorderfuncservice.taxiOrderRemind(param);
		releaseResource(taxiorderfuncservice);
		return checkResult(result);
	}
	
	/**
	 * 出租车获取订单轨迹
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="Driver/TaxiGetOrderLine")
	public JSONObject taxiGetOrderLine(@RequestBody OrderLineParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = taxiorderfuncservice.taxiGetOrderLine(param);
		releaseResource(taxiorderfuncservice);
		return checkResult(result);
	}
	
	/**
	 * 出租车订单复核
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="Driver/TaxiApplyForReview")
	public JSONObject taxiApplyForReview(@RequestBody OrderReviewParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = taxiorderfuncservice.taxiApplyForReview(param);
		releaseResource(taxiorderfuncservice);
		return checkResult(result);
	}
}
