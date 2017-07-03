package com.szyciov.operate.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.lease.param.OrderManageQueryParam;
import com.szyciov.op.entity.OpAccountrules;
import com.szyciov.op.entity.OpOrder;
import com.szyciov.op.entity.OpOrdercomment;
import com.szyciov.operate.service.OrderManageService;
import com.szyciov.param.OrdercommentQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;

/**
 * 运营端订单管理
 */
@Controller
public class OrderManageController extends BaseController {
	
	private OrderManageService orderManageService;
	@Resource(name = "OrderManageService")
	public void setOrderManageService(OrderManageService orderManageService) {
		this.orderManageService = orderManageService;
	}
	
	/**
	 * 查询派单规则中所有城市
	 * @param cityName
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/GetSendRulesByName", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, String>> getSendRulesByName(@RequestParam(value = "cityName", required =  false) String cityName) {
		return orderManageService.getSendRulesByName(cityName);
	}
	
	/**
	 * 分页查询订单数据
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/GetOpOrderByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getOpOrderByQuery(@RequestBody OrderManageQueryParam queryParam) {
		return orderManageService.getOpOrderByQuery(queryParam);
	}
	
	/**
	 * 查询下单列表信息
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/GetPeUser", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getPeUser(@RequestParam(value = "userName", required = false) String userName) {
		return orderManageService.getPeUser(userName);
	}
	
	/**
	 * 查询订单详情
	 * @param orderno
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/GetOpOrderByOrderno/{orderno}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getOpOrderByOrderno(@PathVariable String orderno) {
		return orderManageService.getOpOrderByOrderno(orderno);
	}
	
	/**
	 * 取消订单
	 * @param orderno
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/CancelOpOrder/{orderno}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> cancelOpOrder(@PathVariable String orderno) {
		return orderManageService.cancelOpOrder(orderno);
	}
	
	/**
	 * 根据上车城市查询车型信息
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/GetCompanyVehicleModel/{orderno}", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getCompanyVehicleModel(@PathVariable String orderno) {
		return orderManageService.getCompanyVehicleModel(orderno);
	}
	
	/**
	 * 分页查询司机信息
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/GetDriverByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getDriverByQuery(@RequestBody OrderManageQueryParam queryParam) {
		return orderManageService.getDriverByQuery(queryParam);
	}

    /**
     * 人工派单
     * 已于2017/05/26移动到carservice-api中
     * 访问路径api/OrderManage/ManualSendOpOrder
     * @param object o
     * @return r
     */
	@RequestMapping(value = "api/OrderManage/ManualSendOrder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> manualSendOrder(@RequestBody OpOrder object) {
		return orderManageService.manualSendOrder(object);
	}
	
	/**
	 * 更换司机
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/ChangeOpDriver", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> changeOpDriver(@RequestBody Map<String, Object> params) {
		return orderManageService.changeOpDriver(params);
	}
	
	/**
	 * 申请复核
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/ApplyRecheckOrder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> applyRecheckOrder(@RequestBody Map<String, Object> params) {
		return orderManageService.applyRecheckOrder(params);
	}
	
	/**
	 * 查询订单的人工派单记录
	 * @param orderno
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/GetOpSendOrderRecord/{orderno}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getOpSendOrderRecord(@PathVariable String orderno) {
		return orderManageService.getOpSendOrderRecord(orderno);
	}
	
	/**
	 * 查询订单更换司机记录 
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/GetOpChangeDriverByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getOpChangeDriverByQuery(@RequestBody OrderManageQueryParam queryParam) {
		return orderManageService.getOpChangeDriverByQuery(queryParam);
	}
	
	/**
	 * 查询订单复核记录
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/GetOpOrderReviewByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getOpOrderReviewByQuery(@RequestBody OrderManageQueryParam queryParam) {
		return orderManageService.getOpOrderReviewByQuery(queryParam);
	}
	
	/**
	 * 不受理复核订单
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/OpOrderReject", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> opOrderReject(@RequestBody Map<String, Object> params) {
		return orderManageService.opOrderReject(params);
	}
	
	/**
	 * 根据车型查询计费规则
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/GetOrderAccountRulesByOrderno", method = RequestMethod.POST)
	@ResponseBody
	public OpAccountrules getOrderAccountRulesByOrderno(@RequestBody Map<String, Object> params) {
		return orderManageService.getOrderAccountRulesByOrderno(params);
	}
	
	/**
	 * 订单复核
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/OpOrderReview", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> opOrderReview(@RequestBody Map<String, Object> params) {
		return orderManageService.opOrderReview(params);
	}
	
	/**
	 * 查询订单最原始订单记录
	 * @param orderno
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/GetFirstOrderByOrderno/{orderno}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getFirstOrderByOrderno(@PathVariable String orderno) {
		return orderManageService.getFirstOrderByOrderno(orderno);
	}
	
	/**
	 * 查询订单号(select)
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/GetOrdernoBySelect", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getOrdernoBySelect(@RequestBody OrderManageQueryParam queryParam) {
		return orderManageService.getOrdernoBySelect(queryParam);
	}
	
	/**
	 * 查询客服备注列表
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/GetOpOrderCommentByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getOpOrderCommentByQuery(@RequestBody OrdercommentQueryParam queryParam) {
		return this.orderManageService.getOpOrderCommentByQuery(queryParam);
	}
	
	/**
	 * 添加客服备注
	 * @param object
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/AddOpOrderRemark", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> addOpOrderRemark(@RequestBody OpOrdercomment object) {
		return this.orderManageService.addOpOrdercomment(object);
	}
	
	/**
	 * 查询加入toC的租赁公司和运管平台(select2)
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/GetToCCompanySelect", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getToCCompanySelect(@RequestBody Map<String, Object> params) {
		return orderManageService.getToCCompanySelect(params);
	}
	
	/**
	 * 导出订单列表数据
	 * @param queryParam
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "api/OrderManage/ExportOrder", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> exportOrder(@RequestBody OrderManageQueryParam queryParam) {
		return orderManageService.exportOrder(queryParam);
	}
	
}
