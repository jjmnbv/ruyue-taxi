package com.szyciov.driver.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.driver.enums.OrderState;
import com.szyciov.driver.param.ChangeOrderStateParam;
import com.szyciov.driver.param.DriverMessageParam;
import com.szyciov.driver.param.NewsParam;
import com.szyciov.driver.param.OrderCostParam;
import com.szyciov.driver.param.OrderLineParam;
import com.szyciov.driver.param.OrderListParam;
import com.szyciov.driver.param.OrderStatisticsParam;
import com.szyciov.driver.service.OrderService;
import com.szyciov.entity.OrderReviewParam;
import com.szyciov.param.OrderApiParam;
import com.szyciov.util.ApiExceptionHandle;

import net.sf.json.JSONObject;

/**
 * 订单控制器
 * @ClassName OrderController 
 * @author Efy Shu
 * @Description 处理订单相关的请求
 * @date 2016年8月24日 上午9:18:54
 */
@Controller
public class OrderController extends ApiExceptionHandle {
	private OrderService os;
	
	public OrderController() {
	}
	
	@Resource(name="OrderService")
	public void setOs(OrderService os) {
		this.os = os;
	}
	
	//=====================================================================//
	/**
	 * 获取新消息
	 * @param param
	 * @return
	 * @see {@link PollMessageParam}
	 */
	@ResponseBody
	@RequestMapping(value="Driver/PollMessage")
	public JSONObject pollMessage(@RequestBody NewsParam param) {
		starttime.set(System.currentTimeMillis());
		JSONObject result = os.pollMessage(param);
		releaseResource(os);
		return checkResult(result);
	}
	
	/**
	 * 获取订单列表接口
	 * @param param
	 * @return
	 * @see {@link OrderListParam}
	 */
	@ResponseBody
	@RequestMapping(value="Driver/GetOrderList")
	public JSONObject getOrderList(@RequestBody OrderListParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = os.getOrderList(param);
		releaseResource(os);
		return checkResult(result);
	}
	
	/**
	 * 获取今日订单
	 * @param param
	 * @return
	 * @see {@link OrderListParam}
	 */
	@ResponseBody
	@RequestMapping(value="Driver/GetTodayOrders")
	public JSONObject getTodayOrders(@RequestBody OrderListParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = os.getTodayOrders(param);
		releaseResource(os);
		return checkResult(result);
	}
	
	/**
	 * 变更订单状态
	 * @param param
	 * @return
	 * @see {@link OrderService.changeOrderState()}
	 */
	@ResponseBody
	@RequestMapping(value="Driver/ChangeOrderState")
	public JSONObject changeOrderState(@RequestBody ChangeOrderStateParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = os.changeOrderState(param);
		releaseResource(os);
		return checkResult(result);
	}
	
	/**
	 * 获取订单状态
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="Driver/GetOrderState")
	public JSONObject getOrderState(@RequestBody OrderApiParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = os.getOrderState(param);
		releaseResource(os);
		return checkResult(result);
	}
	
	/**
	 * 获取轨迹线
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="Driver/GetOrderLine")
	public JSONObject getOrderLine(@RequestBody OrderLineParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = os.getOrderLine(param);
		releaseResource(os);
		return checkResult(result);
	}
	
	/**
	 * 抢单
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="Driver/TakingOrder")
	public JSONObject takingOrder(@RequestBody OrderApiParam param){
		starttime.set(System.currentTimeMillis());
		ChangeOrderStateParam cosp = new ChangeOrderStateParam();
		cosp.setToken(param.getToken());
		cosp.setOrderno(param.getOrderno());
		cosp.setOrderstate(OrderState.WAITSTART.state);
		cosp.setUsetype(param.getUsetype());
		cosp.setOrdertype(param.getOrdertype());
		JSONObject result = os.changeOrderState(cosp);
		releaseResource(os);
		return checkResult(result);
	}
	
	/**
	 * 我的贡献
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="Driver/OrderStatistics")
	public JSONObject orderStatistics(@RequestBody OrderStatisticsParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = os.orderStatistics(param);
		releaseResource(os);
		return checkResult(result);
	}
	
	/**
	 * 申请复核
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="Driver/ApplyForReview")
	public JSONObject applyForReview(@RequestBody OrderReviewParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = os.applyForReview(param);
		releaseResource(os);
		return checkResult(result);
	}
	
	/**
	 * 获取实时车费
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="Driver/GetCurrentCost")
	public JSONObject getCurrentCost(@RequestBody OrderCostParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = os.getCurrentCost(param);
		releaseResource(os);
		return checkResult(result);
	}
	
	/**
	 * 获取当前订单
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="Driver/GetCurrentOrder")
	public JSONObject getCurrentOrder(@RequestBody OrderApiParam param){
		starttime.set(System.currentTimeMillis());
		OrderListParam olp = new OrderListParam();
		olp.setToken(param.getToken());
		JSONObject result = os.getCurrentOrder(olp);
		releaseResource(os);
		return checkResult(result);
	}
	
	/**
	 * 司机阅读消息
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/Driver/ReadNews")
	public JSONObject readNews(@RequestBody NewsParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result =   os.readNews(param);
		releaseResource(os);
		return checkResult(result);
	}
	
	/**
	 * 司机删除消息
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/Driver/DelNews")
	public JSONObject delNews(@RequestBody NewsParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result =   os.delNews(param);
		releaseResource(os);
		return checkResult(result);
	}
	
	/**
	 * 司机订单提醒
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/Driver/OrderRemind")
	public JSONObject orderRemind(@RequestBody OrderApiParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result =   os.orderRemind(param);
		releaseResource(os);
		return checkResult(result);
	}
	
	/**
	 * 司机订单提醒
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/Driver/GetOrderMessageList")
	public JSONObject getOrderMessageList(@RequestBody DriverMessageParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result =   os.getOrderMessageList(param);
		releaseResource(os);
		return checkResult(result);
	}
}
