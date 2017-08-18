package com.szyciov.carservice.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.szyciov.carservice.service.OpOrderApiService;
import com.szyciov.carservice.service.OpTaxiOrderManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.carservice.service.OrderApiService;
import com.szyciov.driver.param.OrderCostParam;
import com.szyciov.driver.param.OrderStatisticsParam;
import com.szyciov.entity.OrderReviewParam;
import com.szyciov.lease.param.GetSendInfoParam;
import com.szyciov.op.entity.OpOrder;
import com.szyciov.op.entity.OpTaxiOrder;
import com.szyciov.op.entity.OpUser;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.param.OrderApiParam;
import com.szyciov.param.PubPushLogParam;
import com.szyciov.util.ApiExceptionHandle;

import net.sf.json.JSONObject;

@Controller
public class OrderApiController extends ApiExceptionHandle{
	
	private OrderApiService service;
	@Autowired
    private OpOrderApiService opOrderApiService;
	@Autowired
    private OpTaxiOrderManageService opTaxiOrderManageService;


	@Resource(name="OrderApiService")
	public void setOrderApiService(OrderApiService service) {
		this.service = service;
	}

	/**
	 * 更新最新订单号
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/api/OrderApi/UpdateNewestOrderNO")
	public JSONObject updateNewestOrderNO(){
		starttime.set(System.currentTimeMillis());
		JSONObject result = service.updateNewestOrderNO();
		releaseResource(service);
		return checkResult(result);
	}
	
	/**
	 * 获取订单费用
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/api/OrderApi/GetOrderCost")
	public JSONObject getOrderCost(@RequestBody OrderCostParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = service.getOrderCost(param);
		releaseResource(service);
		return checkResult(result);
	}
	
	/**
	 * 获取派单规则
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/api/OrderApi/GetSendRule")
	public JSONObject getSendRule(@RequestBody GetSendInfoParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = service.getSendRule(param);
		releaseResource(service);
		return checkResult(result);
	}
	
	/**
	 * 获取派单时限
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/api/OrderApi/GetSendTime")
	public JSONObject getSendTime(@RequestBody OrderApiParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = service.getSendTime(param);
		releaseResource(service);
		return checkResult(result);
	}
	
	/**
	 * 获取订单信息(OrderInfoDetail)
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/api/OrderApi/GetOrderInfo")
	public JSONObject getOrderInfo(@RequestBody OrderApiParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = service.getOrderInfo(param);
		releaseResource(service);
		return checkResult(result);
	}
	
	/**
	 * 订单统计
	 * @param param
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@ResponseBody
	@RequestMapping(value="/api/OrderApi/GetOrderStatistics")
	public  JSONObject getOrderStatistics(@RequestBody OrderStatisticsParam param) {
		starttime.set(System.currentTimeMillis());
		JSONObject result = service.getOrderStatistics(param);
		releaseResource(service);
		return checkResult(result);
	}
	
	/**
	 * 订单复议
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/api/OrderApi/OrderReview")
	public JSONObject orderReview(@RequestBody OrderReviewParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = service.orderReview(param);
		releaseResource(service);
		return checkResult(result);
	}
	
	/**
	 * 变更订单状态(只能变更为取消,完成和待人工派单,其他状态都由司机变更)
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/api/OrderApi/ChangeOrderState")
	public JSONObject changeOrderState(@RequestBody OrderApiParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = service.changeOrderState(param);
		releaseResource(service);
		return checkResult(result);
	}
	
	/**
	 * 创建机构订单
	 * @param order
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/api/OrderApi/CreateOrgOrder")
	public JSONObject createOrgOrder(@RequestBody OrgOrder order){
		starttime.set(System.currentTimeMillis());
		JSONObject result = service.createOrgOrder(order);
		releaseResource(service);
		return checkResult(result);
	}


    /**
     * 人工派单
     * @param object o
     * @return r
     */
    @RequestMapping(value = "api/TaxiOrderManage/ManualSendOrder")
    @ResponseBody
    public Map<String, String> manualSendOrder(@RequestBody OpTaxiOrder object) {
        return opTaxiOrderManageService.manualSendOrder(object);
    }

	/**
	 * 创建个人订单
	 * @param order
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/api/OrderApi/CreateOpOrder")
	public JSONObject createOpOrder(@RequestBody OpOrder order){
		starttime.set(System.currentTimeMillis());
		JSONObject result = service.createOpOrder(order);
		releaseResource(service);
		return checkResult(result);
	}
	
	/**
	 * 获取待司机出发的机构网约车订单信息
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/api/OrderApi/GetOrgBeDepartureOrder")
	public List<OrgOrder> getOrgBeDepartureOrder(@RequestBody OrderApiParam param){
		starttime.set(System.currentTimeMillis());
		return service.getOrgBeDepartureOrder(param);
	}
	
	
	/**
	 * 获取待司机出发的个人网约车订单信息
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/api/OrderApi/GetOpBeDepartureOrder")
	public List<OpOrder> getOpBeDepartureOrder(@RequestBody OrderApiParam param){
		starttime.set(System.currentTimeMillis());
		return service.getOpBeDepartureOrder(param);
	}

	/**
	 * 获取待司机出发的个人出租车订单信息
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/api/OrderApi/GetOpTaxiBeDepartureOrder")
	public List<OpTaxiOrder> getOpTaxiBeDepartureOrder(@RequestBody OrderApiParam param){
		starttime.set(System.currentTimeMillis());
		return service.getOpTaxiBeDepartureOrder(param);
	}

	
	/**
	 * 取消司机端接客提醒，需要输入orderno
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/api/OrderApi/CancelOrderRemind")
	public Map<String, Object> cancelOrderRemind(@RequestBody OrderApiParam param){
		starttime.set(System.currentTimeMillis());
		return service.cancelOrderRemind(param);
	}
	
	/**
	 * 将超时待人工派单的订单置为取消
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/api/OrderApi/CancelOverTimeOrder")
	public Map<String, Object> cancelOverTimeOrder(@RequestBody OrderApiParam param){
		starttime.set(System.currentTimeMillis());
		return service.cancelOverTimeOrder(param);
	}
	
	
	/**
	 * 获取待人工派单机构订单数据
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/api/OrderApi/getBeArtificialOrgOrder")
	public OrgOrder getBeArtificialOrgOrder(@RequestBody Map<String, Object> params){
		starttime.set(System.currentTimeMillis());
		return service.getBeArtificialOrgOrder(params);
	}
	
	
	/**
	 * 获取待人工派单运管订单数据
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/api/OrderApi/getBeArtificialOpOrder")
	public OpOrder getBeArtificialOpOrder(@RequestBody Map<String, Object> params){
		starttime.set(System.currentTimeMillis());
		return service.getBeArtificialOpOrder(params);
	}
	
	/**
	 * 获取订单费用
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/api/OrderApi/GetOpTaxiOrderCost")
	@ResponseBody
	public JSONObject getOpTaxiOrderCost(@RequestBody OrderCostParam param) {
		return service.getOpTaxiOrderCost(param);
	}
	
	/**
	 * 验证当前城市的出租车计费规则
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/api/OrderApi/CheckOpTaxiAccountRules")
	@ResponseBody
	public Map<String, Object> checkOpTaxiAccountRules(@RequestBody OrderCostParam param) {
		return service.checkOpTaxiAccountRules(param);
	}
	
	/**
	 * 创建运管端出租车订单
	 * @param object
	 * @return
	 */
	@RequestMapping(value = "/api/OrderApi/CreateOpTaxiOrder")
	@ResponseBody
	public JSONObject createOpTaxiOrder(@RequestBody OpTaxiOrder object) {
        starttime.set(System.currentTimeMillis());
        JSONObject result = service.createOpTaxiOrder(object);
        releaseResource(service);
        return checkResult(result);
	}


    /**
     * 机构人工派单
     * @param orgOrder 订单信息
     * @return r
             */
    @RequestMapping(value = "api/OrderManage/ManualSendOrgOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> manualSendOrder(@RequestBody OrgOrder orgOrder) {
        return this.service.manualSendOrgOrder(orgOrder);
    }

    /**
     * 运管人工派单
     * @param orgOrder 订单信息
     * @return r
     */
    @RequestMapping(value = "api/OrderManage/ManualSendOpOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> manualSendOrder(@RequestBody OpOrder orgOrder) {
        return this.opOrderApiService.manualSendOrder(orgOrder);
    }

    /**
     * 保存司机抢单推送
     * @return
     */
    @RequestMapping(value = "api/OrderApi/SavePushLog", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject savePushLog(@RequestBody PubPushLogParam param){
    	starttime.set(System.currentTimeMillis());
		JSONObject result = service.savePushLog(param);
		releaseResource(service);
		return checkResult(result);
    }
}
