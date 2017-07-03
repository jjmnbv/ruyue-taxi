package com.szyciov.operate.controller;

import com.szyciov.entity.CancelParty;
import com.szyciov.entity.OrderCost;
import com.szyciov.enums.OrderEnum;
import com.szyciov.lease.param.OrderManageQueryParam;
import com.szyciov.op.entity.OpAccountrules;
import com.szyciov.op.entity.OpOrder;
import com.szyciov.op.entity.OpOrdercomment;
import com.szyciov.op.entity.OpUser;
import com.szyciov.operate.service.OrderManageService;
import com.szyciov.param.OrderApiParam;
import com.szyciov.param.OrdercommentQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.PageBean;
import com.szyciov.util.StringUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	 * 待人工派单首页
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/OrderIndex")
	public String orderIndex() {
		return "resource/ordermanage/index";
	}
	
	/**
	 * 当前订单首页
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/CurrentOrderIndex")
	public String currentOrderIndex() {
		return "resource/ordermanage/currentorder";
	}
	
	/**
	 * 异常订单首页(待复核)
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/AbnormalOrderIndex")
	public String abnormalOrderIndex() {
		return "resource/ordermanage/abnormalorder";
	}
	
	/**
	 * 异常订单首页(已复核)
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/WasAbnormalOrderIndex")
	public String getWasAbnormalOrderIndex() {
		return "resource/ordermanage/wasabnormalorder";
	}
	
	/**
	 * 个人历史订单首页
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/HistoryOrderIndex")
	public String getHistoryOrderIndex() {
		return "resource/ordermanage/historyorder";
	}
	
	/**
	 * 个人待收款订单页
	 * @return
	 */
	@RequestMapping(value = "OrderManage/WaitgatheringOrderIndex")
	public String getWaitgatheringOrderIndex() {
		return "resource/ordermanage/waitgatheringorder";
	}
	
	/**
	 * 跳转到待人工派单详情界面
	 * @param orderno
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/OrderPendingDetailIndex")
	public String orderPendingDetailIndex(@RequestParam String orderno) {
		return "resource/ordermanage/pendingorderdetail";
	}
	
	/**
	 * 当前订单/异常订单/历史订单详情
	 * @param orderno
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/OrderDetailIndex")
	public String orderDetailIndex(@RequestParam String orderno) {
		return "resource/ordermanage/orderdetail";
	}
	
	/**
	 * 查询派单规则中所有城市
	 * @param cityName
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/GetSendRulesByName")
	@ResponseBody
	public List<Map<String, String>> getSendRulesByName(@RequestParam(value = "cityName", required = false) String cityName,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return orderManageService.getSendRulesByName(cityName, userToken);
	}
	
	/**
	 * 分页查询个人订单数据
	 * @param queryParam
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/GetOpOrderByQuery")
	@ResponseBody
	public PageBean getOpOrderByQuery(@RequestBody OrderManageQueryParam queryParam, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OpUser user = getLoginOpUser(request);
		queryParam.setOpUserId(user.getId());
		queryParam.setUsertype(user.getUsertype());
		return orderManageService.getOpOrderByQuery(queryParam, userToken);
	}
	
	/**
	 * 查询下单列表信息
	 * @param params
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/GetPeUser")
	@ResponseBody
	public List<Map<String, Object>> getPeUser(@RequestParam(value = "userName", required = false) String userName, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return orderManageService.getPeUser(userName, userToken);
	}
	
	/**
	 * 查询订单详情
	 * @param orderno
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/GetOpOrderByOrderno")
	@ResponseBody
	public Map<String, Object> getOpOrderByOrderno(@RequestParam String orderno, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return orderManageService.getOpOrderByOrderno(orderno, userToken);
	}
	
	/**
	 * 取消订单
	 * @param orderno
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/CancelOpOrder")
	@ResponseBody
	public Map<String, Object> cancelOpOrder(@RequestParam String orderno, @RequestParam String ordertype, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OrderApiParam param = new OrderApiParam();
		param.setOrderno(orderno);
		param.setOrdertype(ordertype);
		param.setUsetype(OrderEnum.USETYPE_PERSONAL.code);
		param.setOrderstate("8");
		param.setReqsrc(CancelParty.OPERATOR.code);
		return orderManageService.cancelOpOrder(param, userToken);
	}
	
	/**
	 * 跳转到人工派单/更换司机界面
	 * @param orderno
	 * @param type
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/ManualSendOrderIndex")
	public ModelAndView manualSendOrderIndex(@RequestParam String orderno, @RequestParam String type,
            @RequestParam(required = false) String ordertype,
			HttpServletRequest request, HttpServletResponse response) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		List<Map<String, Object>> vehicleModelList = orderManageService.getCompanyVehicleModel(orderno, userToken);
		
		ModelAndView view = new ModelAndView();
		view.addObject("vehicleModelList", vehicleModelList);
        view.addObject("ordertype", ordertype);
		view.setViewName("resource/ordermanage/manualsendorder");
		return view;
	}
	
	/**
	 * 跳转到订单复核界面
	 * @param orderno
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/OpOrderReviewIndex")
	public String opOrderReviewIndex(@RequestParam String orderno) {
		return "resource/ordermanage/orderreview";
	}
	
	/**
	 * 分页查询司机信息
	 * @param queryParam
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/GetDriverByQuery")
	@ResponseBody
	public PageBean getDriverByQuery(@RequestBody OrderManageQueryParam queryParam, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		if(StringUtils.isBlank(queryParam.getIsDriverState())) {
			queryParam.setDriverState("0");
		}
		return orderManageService.getDriverByQuery(queryParam, userToken);
	}
	
	/**
	 * 人工派单
	 * @param object
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/ManualSendOrder")
	@ResponseBody
	public Map<String, Object> manualSendOrder(@RequestBody OpOrder object, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OpUser user = getLoginOpUser(request);
		object.setOperator(user.getId());
		return orderManageService.manualSendOrder(object, userToken);
	}
	
	/**
	 * 更换司机
	 * @param params
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/ChangeOpDriver")
	@ResponseBody
	public Map<String, Object> changeOpDriver(@RequestBody Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OpUser user = getLoginOpUser(request);
		params.put("operator", user.getId());
		return orderManageService.changeOpDriver(params, userToken);
	}
	
	/**
	 * 申请复核
	 * @param params
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/ApplyRecheckOrder")
	@ResponseBody
	public Map<String, Object> applyRecheckOrder(@RequestBody Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		params.put("reviewstatus", "1");
		return orderManageService.applyRecheckOrder(params, userToken);
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
	@RequestMapping(value = "/OrderManage/GetOpOrderTraceData")
	@ResponseBody
	public Map<String, Object> getOpOrderTraceData(@RequestParam String orderno, @RequestParam String ordertype, @RequestParam String usetype, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return orderManageService.getOpOrderTraceData(orderno, ordertype, usetype, userToken);
	}
	
	/**
	 * 查询订单的人工派单记录
	 * @param orderno
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/GetOpSendOrderRecord")
	@ResponseBody
	public Map<String, Object> getOpSendOrderRecord(@RequestParam String orderno, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return orderManageService.getOpSendOrderRecord(orderno, userToken);
	}
	
	/**
	 * 查询订单的更换司机记录
	 * @param queryParam
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/GetOpChangeDriverByQuery")
	@ResponseBody
	public PageBean getOpChangeDriverByQuery(@RequestBody OrderManageQueryParam queryParam, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return orderManageService.getOpChangeDriverByQuery(queryParam, userToken);
	}
	
	/**
	 * 查询订单复核记录
	 * @param queryParam
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/GetOpOrderReviewByQuery")
	@ResponseBody
	public PageBean getOpOrderReviewByQuery(@RequestBody OrderManageQueryParam queryParam, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return orderManageService.getOpOrderReviewByQuery(queryParam, userToken);
	}
	
	/**
	 * 不受理复核订单
	 * @param params
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/OpOrderReject")
	@ResponseBody
	public Map<String, Object> opOrderReject(@RequestBody Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OpUser user = getLoginOpUser(request);
		
		params.put("operator", user.getId());
		return orderManageService.opOrderReject(params, userToken);
	}
	
	/**
	 * 根据车型查询计费规则
	 * @param params
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/GetOrderAccountRulesByOrderno")
	@ResponseBody
	public OpAccountrules getOrderAccountRulesByOrderno(@RequestBody Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return orderManageService.getOrderAccountRulesByOrderno(params, userToken);
	}
	
	/**
	 * 订单复核
	 * @param params
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/OpOrderReview")
	@ResponseBody
	public Map<String, Object> opOrderReview(@RequestBody Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OpUser user = getLoginOpUser(request);
		params.put("operator", user.getId());
		return orderManageService.opOrderReview(params, userToken);
	}
	
	/**
	 * 查询订单最原始订单记录
	 * @param orderno
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "OrderManage/GetFirstOrderByOrderno")
	@ResponseBody
	public Map<String, Object> getFirstOrderByOrderno(@RequestParam String orderno, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return orderManageService.getFirstOrderByOrderno(orderno, userToken);
	}
	
	/**
	 * 查询订单号(select)
	 * @param queryParam
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "OrderManage/GetOrdernoBySelect")
	@ResponseBody
	public List<Map<String, Object>> getOrdernoBySelect(
			@RequestParam(value = "orderNo", required = false) String orderNo, @RequestParam String type,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OpUser opUser = getLoginOpUser(request);
		OrderManageQueryParam queryParam = new OrderManageQueryParam();
		queryParam.setOrderNo(orderNo);
		queryParam.setType(type);
		queryParam.setOpUserId(opUser.getId());
		return orderManageService.getOrdernoBySelect(queryParam, userToken);
	}
	
	/**
	 * 添加订单客服备注
	 * @param params
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "OrderManage/AddOpOrderRemark")
	@ResponseBody
	public Map<String, Object> addOpOrderRemark(@RequestBody OpOrdercomment object, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OpUser user = getLoginOpUser(request);
		
		object.setOperator(user.getId());
		return orderManageService.addOpOrderRemark(object, userToken);
	}
	
	/**
	 * 分页查询订单客服备注
	 * @param queryParam
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "OrderManage/GetOpOrderCommentByQuery")
	@ResponseBody
	public PageBean getOpOrderCommentByQuery(@RequestBody OrdercommentQueryParam queryParam, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return orderManageService.getOpOrderCommentByQuery(queryParam, userToken);
	}
	
	/**
	 * 查询加入toC的租赁公司和运管端平台(select2)
	 * @param shortname
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "OrderManage/GetToCCompanySelect")
	@ResponseBody
	public List<Map<String, Object>> getToCCompanySelect(
			@RequestParam(value = "shortname", required = false) String shortname, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("shortname", shortname);
		return orderManageService.getToCCompanySelect(params, userToken);
	}
	
	/**
	 * 导出订单列表数据
	 * @param queryParam
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "OrderManage/ExportOrder")
	@ResponseBody
	public void exportOrder(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OrderManageQueryParam queryParam = createOrderQueryParam(request);
		OpUser user = getLoginOpUser(request);
		queryParam.setOpUserId(user.getId());
		queryParam.setUsertype(user.getUsertype());
		orderManageService.exportOrder(queryParam, userToken, request, response);
		//防止页面跳转
		try {
			response.sendRedirect("javascript:void(0);");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    /**
     * 根据计价副本计算价格
     * @param params
     * @return
     */
    @RequestMapping(value = "OrderManage/GetOrdercost")
    @ResponseBody
    public Map<String, Object> getOrdercost(@RequestBody Map<String, Object> params) {
        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put("cost", 0);
        try {
            Date starttime = StringUtil.parseDate((String) params.get("starttime"), StringUtil.NOMAL_DATE_TIME_);
            OrderCost orderCost = StringUtil.countCost(StringUtil.parseJSONToBean(JSONObject.fromObject(params).toString(), OrderCost.class), starttime, false);
            ret.put("cost", orderCost.getCost());
        } catch (Exception e) {

        }
        return ret;
    }
	
	/**
	 * 初始化订单查询参数
	 * @param request
	 */
	private OrderManageQueryParam createOrderQueryParam(HttpServletRequest request) {
		OrderManageQueryParam queryParam = new OrderManageQueryParam();
		queryParam.setOrderNo(request.getParameter("orderNo"));
		queryParam.setOrderType(request.getParameter("orderType"));
		queryParam.setDriverid(request.getParameter("driverid"));
		queryParam.setUserId(request.getParameter("userId"));
		queryParam.setReviewperson(request.getParameter("reviewperson"));
		queryParam.setMinUseTime(request.getParameter("minUseTime"));
		queryParam.setMaxUseTime(request.getParameter("maxUseTime"));
		queryParam.setOrdersource(request.getParameter("ordersource"));
		queryParam.setType(request.getParameter("type"));
		queryParam.setOrderstatus(request.getParameter("orderstatus"));
		queryParam.setCancelparty(request.getParameter("cancelparty"));
		queryParam.setTradeno(request.getParameter("tradeno"));
		queryParam.setPaymentstatus(request.getParameter("paymentstatus"));
//		queryParam.setLeasescompanyid(request.getParameter("leasescompanyid"));
        queryParam.setBelongleasecompany(request.getParameter("belongleasecompany"));
		return queryParam;
	}
	
}
