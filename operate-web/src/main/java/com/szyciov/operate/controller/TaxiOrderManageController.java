package com.szyciov.operate.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.szyciov.entity.CancelParty;
import com.szyciov.entity.PubOrderCancel;
import com.szyciov.lease.param.OrderManageQueryParam;
import com.szyciov.op.entity.OpTaxiOrder;
import com.szyciov.op.entity.OpTaxiOrderReview;
import com.szyciov.op.entity.OpTaxiordercomment;
import com.szyciov.op.entity.OpUser;
import com.szyciov.operate.service.TaxiOrderManageService;
import com.szyciov.operate.service.TaxiSendrulesService;
import com.szyciov.param.OrderApiParam;
import com.szyciov.param.OrdercommentQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TaxiOrderManageController extends BaseController {
	
	private TaxiOrderManageService orderService;
	@Resource(name = "TaxiOrderManageService")
	public void setOrderService(TaxiOrderManageService orderService) {
		this.orderService = orderService;
	}
	
	private TaxiSendrulesService sendrulesService;
	@Resource(name = "TaxiSendrulesService")
	public void setSendrulesService(TaxiSendrulesService sendrulesService) {
		this.sendrulesService = sendrulesService;
	}
	
	/**
	 * 跳转到未接订单首页
	 * @return
	 */
	@RequestMapping(value = "/TaxiOrderManage/Index")
	public String orderIndex() {
		return "resource/taxiOrdermanage/index";
	}
	
	/**
	 * 跳转到当前订单首页
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/TaxiOrderManage/CurrentOrderIndex")
	public String currentOrderIndex() {
		return "resource/taxiOrdermanage/currentorder";
	}
	
	/**
	 * 跳转到待复核订单首页
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/TaxiOrderManage/AbnormalOrderIndex")
	public String abnormalOrderIndex() {
		return "resource/taxiOrdermanage/abnormalorder";
	}
	
	/**
	 * 跳转到已复核订单首页
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/TaxiOrderManage/WasabnormalOrderIndex")
	public String wasabnormalOrderIndex() {
		return "resource/taxiOrdermanage/wasabnormalorder";
	}
	
	/**
	 * 跳转到已完成订单首页
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/TaxiOrderManage/HistoryOrderIndex")
	public String historyOrderIndex() {
		return "resource/taxiOrdermanage/historyorder";
	}
	
	/**
	 * 跳转到待收款订单首页
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/TaxiOrderManage/WaitgatheringOrderIndex")
	public String waitgatheringOrderIndex() {
		return "resource/taxiOrdermanage/waitgatheringorder";
	}

	/**
	 * 跳转到已取消订单首页
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/TaxiOrderManage/CancelOrderIndex")
	public String cancelOrderIndex() {
		return "resource/taxiOrdermanage/cancelorder";
	}
	
	/**
	 * 分页查询出租车订单数据
	 * @param queryParam
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/TaxiOrderManage/GetOpTaxiOrderByQuery")
	@ResponseBody
	public PageBean getOpOrderByQuery(@RequestBody OrderManageQueryParam queryParam, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = getUserToken(request);
		OpUser user = getLoginOpUser(request);
		queryParam.setOpUserId(user.getId());
		queryParam.setUsertype(user.getUsertype());
		return orderService.getOpTaxiOrderByQuery(queryParam, userToken);
	}
	
	/**
	 * 根据订单号查询订单详情
	 * @param orderno
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/TaxiOrderManage/GetOpTaxiOrderByOrderno")
	@ResponseBody
	public Map<String, Object> getOpTaxiOrderByOrderno(@RequestParam String orderno, HttpServletRequest request) {
		String userToken = getUserToken(request);
		return orderService.getOpTaxiOrderByOrderno(orderno, userToken);
	}
	
	/**
	 * 取消出租车订单
	 * @param orderno
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/TaxiOrderManage/CancelOpTaxiOrder")
	@ResponseBody
	public Map<String, Object> cancelOpTaxiOrder(@RequestBody OrderApiParam param, HttpServletRequest request) {
		String userToken = getUserToken(request);
		OpUser user = getLoginOpUser(request);
		param.setCanceloperator(user.getId());
		param.setOrderstate("8");
		param.setReqsrc(CancelParty.OPERATOR.code);
		return orderService.cancelOpTaxiOrder(param, userToken);
	}
	
	/**
	 * 跳转到人工派单/更换车辆页面
	 * @param orderno
	 * @param type
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/TaxiOrderManage/ManualSendTaxiOrderIndex")
	public String manualSendTaxiOrderIndex(@RequestParam String orderno, @RequestParam String type, HttpServletRequest request) {
		return "resource/taxiOrdermanage/manualsendorder";
	}
	
	/**
	 * 跳转到订单复核页面
	 * @param orderno
	 * @return
	 */
	@RequestMapping(value = "/TaxiOrderManage/OpTaxiOrderReviewIndex")
	public String opTaxiOrderReviewIndex(@RequestParam String orderno) {
		return "resource/taxiOrdermanage/orderreview";
	}
	
	/**
	 * 不受理复核订单
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/TaxiOrderManage/OpTaxiOrderReject")
	@ResponseBody
	public Map<String, Object> opTaxiOrderReject(@RequestBody Map<String, String> param, HttpServletRequest request) {
		String userToken = getUserToken(request);
		return orderService.opTaxiOrderReject(param.get("orderno"), userToken);
	}
	
	/**
	 * 申请复核
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/TaxiOrderManage/ApplyRecheckTaxiOrder")
	@ResponseBody
	public Map<String, Object> applyRecheckTaxiOrder(@RequestBody OpTaxiOrder object, HttpServletRequest request) {
		String userToken = getUserToken(request);
		return orderService.applyRecheckTaxiOrder(object, userToken);
	}
	
	/**
	 * 分页查询订单复核记录
	 * @param object
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/TaxiOrderManage/GetOpTaxiOrderReviewByQuery")
	@ResponseBody
	public PageBean getOpTaxiOrderReviewByQuery(@RequestBody OpTaxiOrderReview object, HttpServletRequest request) {
		String userToken = getUserToken(request);
		return orderService.getOpTaxiOrderReviewByQuery(object, userToken);
	}
	
	/**
	 * 订单复核
	 * @param object
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/TaxiOrderManage/OpTaxiOrderReview")
	@ResponseBody
	public Map<String, Object> opTaxiOrderReview(@RequestBody OpTaxiOrderReview object, HttpServletRequest request) {
		String userToken = getUserToken(request);
		OpUser user = getLoginOpUser(request);
		object.setOperator(user.getId());
		return orderService.opTaxiOrderReview(object, userToken);
	}
	
	/**
	 * 分页查询司机信息
	 * @param queryParam
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/TaxiOrderManage/GetDriverByQuery")
	@ResponseBody
	public PageBean getDriverByQuery(@RequestBody OrderManageQueryParam queryParam, HttpServletRequest request) {
		String userToken = getUserToken(request);
		if(StringUtils.isBlank(queryParam.getIsDriverState())) {
			queryParam.setDriverState("0");
		}
		return orderService.getDriverByQuery(queryParam, userToken);
	}
	
	/**
	 * 人工派单/更换车辆车牌联想下拉框(select)
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/TaxiOrderManage/GetTaxiPlatonoBySelect")
	@ResponseBody
	public List<Map<String, String>> getTaxiPlatonoBySelect(@RequestParam(value = "plateno", required =  false) String plateno, HttpServletRequest request) {
		String userToken = getUserToken(request);
		Map<String, String> params = new HashMap<String, String>();
		params.put("plateno", plateno);
		return orderService.getTaxiPlatonoBySelect(params, userToken);
	}
	
	/**
	 * 人工派单
	 * @param object
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/TaxiOrderManage/ManualSendOrder")
	@ResponseBody
	public Map<String, String> manualSendOrder(@RequestBody OpTaxiOrder object, HttpServletRequest request) {
		String userToken = getUserToken(request);
		OpUser user = getLoginOpUser(request);
		object.setOperator(user.getId());
		return orderService.manualSendOrder(object, userToken);
	}
	
	/**
	 * 派单失败
	 * @param orderno
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/TaxiOrderManage/SendFail/{orderno}")
	@ResponseBody
	public Map<String, String> sendFail(@PathVariable String orderno, HttpServletRequest request) {
		String userToken = getUserToken(request);
		return orderService.sendFail(orderno, userToken);
	}
	
	/**
	 * 更换车辆
	 * @param object
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/TaxiOrderManage/ChangeVehicle")
	@ResponseBody
	public Map<String, String> changeVehicle(@RequestBody OpTaxiOrder object, HttpServletRequest request) {
		String userToken = getUserToken(request);
		OpUser user = getLoginOpUser(request);
		object.setOperator(user.getId());
		return orderService.changeVehicle(object, userToken);
	}
	
	/**
	 * 跳转到订单详情页
	 * @param orderno
	 * @return
	 */
	@RequestMapping(value = "/TaxiOrderManage/OrderDetailIndex")
	public String orderDetailIndex(@RequestParam String orderno) {
		return "resource/taxiOrdermanage/orderdetail";
	}
	
	/**
	 * 查询原始订单数据(第一条复核记录)
	 * @param orderno
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/TaxiOrderManage/GetFirstTaxiOrderByOrderno")
	@ResponseBody
	public Map<String, Object> getFirstTaxiOrderByOrderno(@RequestParam String orderno, HttpServletRequest request) {
		String userToken = getUserToken(request);
		return orderService.getFirstTaxiOrderByOrderno(orderno, userToken);
	}
	
	/**
	 * 查询人工派单记录
	 * @param orderno
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/TaxiOrderManage/GetOpSendTaxiOrderRecord")
	@ResponseBody
	public Map<String, Object> getOpSendTaxiOrderRecord(@RequestParam String orderno, HttpServletRequest request) {
		String userToken = getUserToken(request);
		return orderService.getOpSendTaxiOrderRecord(orderno, userToken);
	}
	
	/**
	 * 查询更换司机记录
	 * @param queryParam
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/TaxiOrderManage/GetOpChangeDriverList")
	@ResponseBody
	public PageBean getOpChangeDriverList(@RequestBody OrderManageQueryParam queryParam, HttpServletRequest request) {
		String userToken = getUserToken(request);
		return orderService.getOpChangeDriverList(queryParam, userToken);
	}
	
	/**
	 * 查询更换车辆记录
	 * @param queryParam
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/TaxiOrderManage/GetOpChangeVehicleList")
	@ResponseBody
	public PageBean getOpChangeVehicleList(@RequestBody OrderManageQueryParam queryParam, HttpServletRequest request) {
		String userToken = getUserToken(request);
		return orderService.getOpChangeVehicleList(queryParam, userToken);
	}
	
	/**
	 * 查询客服备注列表
	 * @param queryParam
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/TaxiOrderManage/GetOpTaxiOrderCommentByQuery")
	@ResponseBody
	public PageBean getOpTaxiOrderCommentByQuery(@RequestBody OrdercommentQueryParam queryParam, HttpServletRequest request) {
		String userToken = getUserToken(request);
		return orderService.getOpTaxiOrderCommentByQuery(queryParam, userToken);
	}
	
	/**
	 * 添加客服备注
	 * @param object
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/TaxiOrderManage/AddOpTaxiOrderRemark")
	@ResponseBody
	public Map<String, String> addOpTaxiOrderRemark(@RequestBody OpTaxiordercomment object, HttpServletRequest request) {
		String userToken = getUserToken(request);
		OpUser user = getLoginOpUser(request);
		object.setOperator(user.getId());
		return orderService.addOpTaxiordercomment(object, userToken);
	}
	
	/**
	 * 调用百度地图接口查询订单轨迹
	 * @param orderno
	 * @param startDate
	 * @param endDate
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/TaxiOrderManage/GetOpTaxiOrderTraceData")
	@ResponseBody
	public Map<String, Object> getOpTaxiOrderTraceData(@RequestParam String orderno, @RequestParam String ordertype,
			@RequestParam String usetype, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = getUserToken(request);
		return orderService.getOpTaxiOrderTraceData(orderno, ordertype, usetype, userToken);
	}
	
	/**
	 * 导出订单列表数据
	 * @param queryParam
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "TaxiOrderManage/ExportOrder")
	@ResponseBody
	public void exportOrder(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = getUserToken(request);
		OrderManageQueryParam queryParam = createOrderQueryParam(request);
		OpUser user = getLoginOpUser(request);
		queryParam.setOpUserId(user.getId());
		queryParam.setUsertype(user.getUsertype());
		orderService.exportOrder(queryParam, userToken, request, response);
		//防止页面跳转
		try {
			response.sendRedirect("javascript:void(0);");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化订单查询参数
	 * @param request
	 */
	private OrderManageQueryParam createOrderQueryParam(HttpServletRequest request) {
		OrderManageQueryParam queryParam = new OrderManageQueryParam();
		queryParam.setOrderNo(request.getParameter("orderNo"));
		queryParam.setPaymentstatus(request.getParameter("paymentstatus"));
		queryParam.setReviewperson(request.getParameter("reviewperson"));
		queryParam.setPaymentmethod(request.getParameter("paymentmethod"));
		queryParam.setOrdersource(request.getParameter("ordersource"));
//		queryParam.setLeasescompanyid(request.getParameter("leasescompanyid"));

        queryParam.setBelongleasecompany(request.getParameter("belongleasecompany"));

		queryParam.setUserId(request.getParameter("userId"));
		queryParam.setDriverid(request.getParameter("driverid"));
		queryParam.setPaytype(request.getParameter("paytype"));
		queryParam.setMinUseTime(request.getParameter("minUseTime"));
		queryParam.setMaxUseTime(request.getParameter("maxUseTime"));
		queryParam.setOrderstatus(request.getParameter("orderstatus"));
		queryParam.setCancelparty(request.getParameter("cancelparty"));
		queryParam.setTradeno(request.getParameter("tradeno"));
		queryParam.setType(request.getParameter("type"));
		return queryParam;
	}
	
	/**
	 * 查询运管平台所属城市是否有系统+人工派单规则
	 * @param request
	 * @return
	 */
	private int getOpSendmodelCountByUser(HttpServletRequest request) {
		String userToken = getUserToken(request);
		OpUser user = getLoginOpUser(request);
		Map<String, Integer> result = sendrulesService.getOpSendmodelCountByUser(user, userToken);
		if(null != result && result.containsKey("taxiSendruleCount")) {
			return result.get("taxiSendruleCount");
		}
		return 0;
	}

	/**
     * 获取订单列表中的服务车企(select2)
     *  @param belongleasecompany
     * @param type
	 * @param request
	 * @param taxiOrderManageService
	 * */
    @RequestMapping(value = "TaxiOrderManage/GetBelongCompanySelect")
    @ResponseBody
    public List<Map<String, Object>> getBelongCompanySelect(
		@RequestParam(value = "belongleasecompany", required = false) String belongleasecompany,
		@RequestParam(value = "type") String type,
		HttpServletRequest request) {
        String userToken = getUserToken(request);
        OpUser user = getLoginOpUser(request);
        OrderManageQueryParam queryParam = new OrderManageQueryParam();
        queryParam.setBelongleasecompany(belongleasecompany);
        queryParam.setType(type);
        queryParam.setOpUserId(user.getId());
        queryParam.setUsertype(user.getUsertype());
        return orderService.getBelongCompanySelect(queryParam, userToken);
    }

	/**
	 * 查询订单取消规则
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "TaxiOrderManage/GetCancelPriceDetail")
	@ResponseBody
	public Map<String, Object> getCancelPriceDetail(@RequestBody Map<String, String> param, HttpServletRequest request) {
		String userToken = getUserToken(request);
		return orderService.getCancelPriceDetail(param, userToken);
	}

    /**
     * 订单免责处理
     * @param object
     * @param request
     * @return
     */
    @RequestMapping(value = "TaxiOrderManage/ExemptionOrder")
    @ResponseBody
    public Map<String, Object> exemptionOrder(@RequestBody PubOrderCancel object, HttpServletRequest request) {
        String userToken = getUserToken(request);
        OpUser user = getLoginOpUser(request);
        object.setExemptionoperator(user.getId());
        return orderService.exemptionOrder(object, userToken);
    }

}
