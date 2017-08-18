package com.szyciov.lease.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.szyciov.entity.CancelParty;
import com.szyciov.entity.OrderCost;
import com.szyciov.entity.PubOrderCancel;
import com.szyciov.lease.entity.LeAccountRules;
import com.szyciov.lease.entity.OrgOrdercomment;
import com.szyciov.lease.entity.User;
import com.szyciov.lease.param.OrderManageQueryParam;
import com.szyciov.lease.service.OrderManageService;
import com.szyciov.op.entity.PubSendRules;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.org.entity.OrgOrderReview;
import com.szyciov.param.OrderApiParam;
import com.szyciov.param.OrdercommentQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.PageBean;
import com.szyciov.util.StringUtil;
import com.szyciov.util.SystemConfig;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OrderManageController extends BaseController{
	
	public static final Logger logger = Logger.getLogger(DictionaryController.class);
	
	private OrderManageService orderManageService;
	
	@Resource(name="orderManageService")
	public void setOrderManageService(OrderManageService orderManageService) {
		this.orderManageService = orderManageService;
	}
	
	/**
	 * 机构订单首页展示
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/OrgOrderIndex")
	public String getOrgOrderIndex() {
		return "resource/orgordermanage/index";
	}
	
	/**
	 * 个人订单首页展示
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/PersonOrderIndex")
	public String getPersonOrderIndex() {
		return "resource/personordermanage/index";
	}
	
	/**
	 * 机构订单当前订单页
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/OrgCurrentOrderIndex")
	public String getOrgCurrentOrderIndex() {
		return "resource/orgordermanage/currentorder";
	}
	
	/**
	 * 个人订单当前订单页
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/PersonCurrentOrderIndex")
	public String getPersonCurrentOrderIndex() {
		return "resource/personordermanage/currentorder";
	}
	
	/**
	 * 机构异常订单页 - 待复核
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/OrgAbnormalOrderIndex")
	public String getOrgAbnormalOrderIndex() {
		return "resource/orgordermanage/abnormalorder";
	}
	
	/**
	 * 机构异常订单页 - 已复核
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/OrgWasAbnormalOrderIndex")
	public String getOrgWasAbnormalOrderIndex() {
		return "resource/orgordermanage/wasabnormalorder";
	}
	
	/**
	 * 个人异常订单页 - 待复核
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/PersonAbnormalOrderIndex")
	public String getPersonAbnormalOrderIndex() {
		return "resource/personordermanage/abnormalorder";
	}
	
	/**
	 * 个人异常订单页 - 已复核
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/PersonWasAbnormalOrderIndex")
	public String getPersonWasAbnormalOrderIndex() {
		return "resource/personordermanage/wasabnormalorder";
	}
	
	/**
	 * 机构历史订单页
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/OrgHistoryOrderIndex")
	public String getOrgHistoryOrderIndex() {
		return "resource/orgordermanage/historyorder";
	}
	
	/**
	 * 个人历史订单页
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/PersonHistoryOrderIndex")
	public String getPersonHistoryOrderIndex() {
		return "resource/personordermanage/historyorder";
	}
	
	/**
	 * 机构待收款订单页
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/OrgWaitgatheringOrderIndex")
	public String getOrgWaitgatheringOrderIndex() {
		return "resource/orgordermanage/waitgatheringorder";
	}
	
	/**
	 * 个人待收款订单页
	 * @return
	 */
	@RequestMapping(value = "OrderManage/PersonWaitgatheringOrderIndex")
	public String getPersonWaitgatheringOrderIndex() {
		return "resource/personordermanage/waitgatheringorder";
	}

    /**
     * 因公已取消订单页
     * @return
     */
	@RequestMapping(value = "/OrderManage/OrgCancelOrderIndex")
	public String getOrgCancelOrderIndex() {
        return "resource/orgordermanage/cancelorder";
    }

    /**
     * 因私已取消订单页
     * @return
     */
    @RequestMapping(value = "/OrderManage/PersonCancelOrderIndex")
    public String getPersonCancelOrderIndex() {
        return "resource/personordermanage/cancelorder";
    }
	
	/**
	 * 机构订单详情首页
	 * @param orderno
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/OrgOrderDetailIndex")
	public ModelAndView orgOrderDetailIndex(@RequestParam String orderno, @RequestParam(required = false) String tmp, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = new ModelAndView();
        view.setViewName("resource/orgordermanage/orderdetail");
        view.addObject("tmp", tmp);
        return view;
	}
	
	/**
	 * 个人订单详情页
	 * @param orderno
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/PersonOrderDetailIndex")
	public ModelAndView personOrderDetailIndex(@RequestParam String orderno, @RequestParam(required = false) String tmp, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView view = new ModelAndView();
        view.setViewName("resource/personordermanage/orderdetail");
        view.addObject("tmp", tmp);
        return view;
	}
	
	/**
	 * 机构订单待处理订单详情页
	 * @param orderno
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/OrgOrderPendingDetailIndex")
	public String orgOrderPendingDetailIndex(@RequestParam String orderno, HttpServletRequest request, HttpServletResponse response) {
		return "resource/orgordermanage/pendingorderdetail";
	}
	
	/**
	 * 个人订单待处理订单详情页
	 * @param orderno
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/PersonOrderPendingDetailIndex")
	public String personOrderPendingDetailIndex(@RequestParam String orderno, HttpServletRequest request, HttpServletResponse response) {
		return "resource/personordermanage/pendingorderdetail";
	}
	
	@RequestMapping(value = "/OrderManage/OrgOrderReviewIndex")
	public String orgOrderReview(@RequestParam String orderno, @RequestParam String usetype, HttpServletRequest request, HttpServletResponse response) {
		if("0".equals(usetype)) {
			return "resource/orgordermanage/orderreview";
		} else if("1".equals(usetype)) {
			return "resource/personordermanage/orderreview";
		}
		return null;
	}
	
	
	/**
	 * 根据用户名、机构ID获取机构用户列表（用户id，用户昵称+用户手机）
	 * @param userName
	 * @param organId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("OrderManage/GetOrgUser")
	@ResponseBody
	public List<Map<String, Object>> getOrgUser(@RequestParam(value = "userName", required = false) String userName,
			@RequestParam(value = "organId", required = false) String organId,
			HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		
		return orderManageService.getOrgUser(userName, organId, user.getLeasescompanyid(), userToken);
	}
	

	/**
	 * 获取机构订单数据
	 * @param queryParam
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/OrderManage/GetOrgOrderByQuery")
	@ResponseBody
	public PageBean getOrgOrderByQuery(@RequestBody OrderManageQueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		initLeUserInfo(request, queryParam);
		
		return orderManageService.getOrgOrderByQuery(queryParam, userToken);
	}
	
	/**
	 * 人工派单页面
	 * @param orderno
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/OrderManage/ManualSendOrderIndex")
	public ModelAndView getManualSendOrderIndex(@RequestParam String orderno,
			@RequestParam String type, 
			@RequestParam String usetype,
            @RequestParam(required = false) String ordertype,
            @RequestParam(required = false) String tmp,
			HttpServletRequest request, HttpServletResponse response) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		Map<String, Object> param = new HashMap<String, Object>();
		
		User user = getLoginLeUser(request);
		List<Map<String, Object>> vehicleModelList = orderManageService.getCompanyVehicleModel(orderno, user.getLeasescompanyid(), userToken);
		
		param.put("vehicleModelList", vehicleModelList);
        param.put("ordertype", ordertype);
        param.put("tmp", tmp);
		
		//0表示机构订单，1表示个人订单
		if("0".equals(usetype)) {
			return new ModelAndView("resource/orgordermanage/manualsendorder", param);
		} else if("1".equals(usetype)) {
			return new ModelAndView("resource/personordermanage/manualsendorder", param);
		}
		return null;
		
	}
	
	/**
	 * 根据订单获取附近司机
	 * @param orderno
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("OrderManage/GetOrgDriverByQuery")
	@ResponseBody
	public PageBean getOrgDriverByQuery(@RequestBody OrderManageQueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		queryParam.setLeasescompanyid(user.getLeasescompanyid());
        queryParam.setQueryTmpBelongleasecompany(SystemConfig.getSystemProperty(user.getAccount()));
		if(StringUtils.isBlank(queryParam.getIsDriverState())) {
			queryParam.setDriverState("0");
		}
		return orderManageService.getOrgDriverByQuery(queryParam, userToken);
	}
	
	/**
	 * 人工派单
	 * @param orgOrder
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("OrderManage/ManualSendOrder")
	@ResponseBody
	public Map<String, Object> manualSendOrder(@RequestBody OrgOrder orgOrder, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		orgOrder.setOperator(user.getId());
		orgOrder.setCompanyid(user.getLeasescompanyid());
		return orderManageService.manualSendOrder(orgOrder, userToken);
	}
	
	/**
	 * 根据订单号获取已接单订单信息
	 * @param orderno
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("OrderManage/GetOrgOrderByOrderno")
	@ResponseBody
	public Map<String, Object> getOrgOrderByOrderno(@RequestParam String orderno, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return orderManageService.getOrgOrderByOrderno(orderno, userToken);
	}
	
	/**
	 * 根据订单号获取待接单订单信息
	 * @param orderno
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("OrderManage/GetOrgWaitingOrderByOrderno")
	@ResponseBody
	public Map<String, Object> getOrgWaitingOrderByOrderno(@RequestParam String orderno, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return orderManageService.getOrgWaitingOrderByOrderno(orderno, userToken);
	}
	
	/**
	 * 根据订单号取消订单
	 * @param orderno
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("OrderManage/CancelOrgOrder")
	@ResponseBody
	public Map<String, Object> cancelOrgOrder(@RequestBody OrderApiParam param, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = getUserToken(request);
        User user = getLoginLeUser(request);
        param.setCanceloperator(user.getId());
		param.setOrderstate("8");
		param.setReqsrc(CancelParty.LEASE.code);
		return orderManageService.cancelOrgOrder(param, userToken);
	}
	
	/**
	 * 获取人工派单记录
	 * @param orderno
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/OrderManage/GetOrgSendOrderRecord")
	@ResponseBody
	public Map<String, Object> getOrgSendOrderRecord(@RequestParam String orderno, HttpServletRequest request, HttpServletResponse response) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return orderManageService.getOrgSendOrderRecord(orderno, userToken);
	}
	
	/**
	 * 根据订单号获取司机更换记录
	 * @param orderno
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/OrderManage/GetOrgChangeDriverByQuery")
	@ResponseBody
	public PageBean getOrgChangeDriverByQuery(@RequestBody OrderManageQueryParam queryParam, HttpServletRequest request, HttpServletResponse response) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return orderManageService.getOrgChangeDriverByQuery(queryParam, userToken);
	}
	
	/**
	 * 根据订单号获取订单复议记录
	 * @param orderno
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/OrderManage/GetOrgOrderReviewByQuery")
	@ResponseBody
	public PageBean getOrgOrderReviewByQuery(@RequestBody OrderManageQueryParam queryParam, HttpServletRequest request, HttpServletResponse response) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return orderManageService.getOrgOrderReviewByQuery(queryParam, userToken);
	}
	
	/**
	 * 根据车型名称及租赁公司获取服务车型信息
	 * @param modelId
	 * @param organId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("OrderManage/GetCompanyVehicleModel")
	@ResponseBody
	public List<Map<String, Object>> getCompanyVehicleModel(@RequestParam(value = "modelName", required = false) String modelName,
			HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		
		return orderManageService.getCompanyVehicleModel(modelName, user.getLeasescompanyid(), userToken);
	}
	
	/**
	 * 订单申请复核
	 * @param params
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "OrderManage/ApplyOrgOrderReview")
	@ResponseBody
	public Map<String, Object> applyOrgOrderReview(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		
		params.put("operator", user.getId());
		params.put("leasescompanyid", user.getLeasescompanyid());
		
		return orderManageService.applyOrgOrderReview(params, userToken);
	}
	
	/**
	 * 添加订单客服备注
	 * @param params
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "OrderManage/AddOrgOrderRemark")
	@ResponseBody
	public Map<String, Object> addOrgOrderRemark(@RequestBody OrgOrdercomment object, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		
		object.setOperator(user.getId());
		return orderManageService.addOrgOrderRemark(object, userToken);
	}
	
	/**
	 * 订单复核
	 * @param params
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "OrderManage/OrgOrderReview")
	@ResponseBody
	public Map<String, Object> orgOrderReview(@RequestBody OrgOrderReview review, HttpServletRequest request,
                                              HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);

        review.setOperator(user.getId());
        review.setLeasescompanyid(user.getLeasescompanyid());
		return orderManageService.orgOrderReview(review, userToken);
	}
	
	/**
	 * 拒绝复核
	 * @param params
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "OrderManage/OrgOrderReject")
	@ResponseBody
	public Map<String, Object> orgOrderReject(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		
		params.put("operator", user.getId());
		params.put("leasescompanyid", user.getLeasescompanyid());
		
		return orderManageService.orgOrderReject(params, userToken);
	}
	
	@RequestMapping(value = "OrderManage/GetOrgOrderTraceData")
	@ResponseBody
	public Map<String, Object> getOrgOrderTraceData(@RequestParam String orderno,
			@RequestParam String ordertype,
			@RequestParam String usetype,
			HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		return orderManageService.getOrgOrderTraceData(orderno, ordertype, usetype, userToken);
	}
	
	
	/**
	 * 拒绝复核
	 * @param params
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "OrderManage/ChangeOrgDriver")
	@ResponseBody
	public Map<String, Object> changeOrgDriver(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		params.put("leasescompanyid", user.getLeasescompanyid());
		params.put("operator", user.getId());
		return orderManageService.changeOrgDriver(params, userToken);
	}
	
	/**
	 * 申请复核
	 * @param params
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "OrderManage/ApplyRecheckOrder")
	@ResponseBody
	public Map<String, String> applyRecheckOrder(@RequestBody Map<String, String> params, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		params.put("leasescompanyid", user.getLeasescompanyid());
		params.put("reviewstatus", "1");
		return orderManageService.applyRecheckOrder(params, userToken);
	}
	
	/**
	 * 查询订单客服备注
	 * @param orderno
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "OrderManage/GetOrgOrdercommentByOrderno")
	@ResponseBody
	public OrgOrdercomment getOrgOrdercomment(@RequestParam String orderno, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return orderManageService.getOrgOrdercommentByOrderno(orderno, userToken);
	}
	
	/**
	 * 查询当前用户所在租赁公司的派单规则城市
	 * @param cityName
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "OrderManage/GetSendRulesByName")
	@ResponseBody
	public List<Map<String, String>> getSendRulesByName(@RequestParam(value = "cityName", required = false) String cityName, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		PubSendRules object = new PubSendRules();
		object.setCityName(cityName);
		object.setLeasesCompanyId(user.getLeasescompanyid());
		return orderManageService.getSendRulesByName(object, userToken);
	}
	
	/**
	 * 查询订单的计费规则
	 * @param params
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "OrderManage/GetOrderAccountRulesByOrderno")
	@ResponseBody
	public LeAccountRules getOrderAccountRulesByOrderno(@RequestBody Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return orderManageService.getOrderAccountRulesByOrderno(params, userToken);
	}
	
	/**
	 * 查询用户拥有权限的机构下拉框数据
	 * @param organName
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "OrderManage/GetOrganByName")
	@ResponseBody
	public List<Map<String, Object>> getOrganByName(
			@RequestParam(value = "organName", required = false) String organName, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("organName", organName);
		params.put("leasescompanyid", user.getLeasescompanyid());
		params.put("account", user.getAccount());
		params.put("specialstate", user.getSpecialstate());
		return orderManageService.getOrganByName(params, userToken);
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
	 * 分页查询订单客服备注
	 * @param queryParam
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "OrderManage/GetOrgOrderCommentByQuery")
	@ResponseBody
	public PageBean getOrgOrderCommentByQuery(@RequestBody OrdercommentQueryParam queryParam, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return orderManageService.getOrgOrderCommentByQuery(queryParam, userToken);
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
			@RequestParam String usetype, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OrderManageQueryParam queryParam = new OrderManageQueryParam();
		queryParam.setOrderNo(orderNo);
		queryParam.setType(type);
		queryParam.setUsetype(usetype);
		initLeUserInfo(request, queryParam);
		return orderManageService.getOrdernoBySelect(queryParam, userToken);
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
		initLeUserInfo(request, queryParam);
		orderManageService.exportOrder(queryParam, userToken, request, response);
		//防止页面跳转
		try {
			response.sendRedirect("javascript:void(0);");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    /**
     * OBDGPS和APPGPS坐标点展示页面
     * @param orderno
     * @return
     */
	@RequestMapping(value = "OrderManage/GpsMap")
	public String gpsMap(@RequestParam String orderno) {
        return "resource/orgordermanage/gps";
    }

    /**
     * 查询OBDGPS和APPGPS轨迹
     * @param orderno
     * @param ordertype
     * @param usetype
     * @param request
     * @return
     */
    @RequestMapping(value = "OrderManage/GetGpsTraceData")
    @ResponseBody
    public Map<String, Object> getGpsTraceData(@RequestParam String orderno, @RequestParam String ordertype, @RequestParam String usetype, HttpServletRequest request) {
        return orderManageService.getGpsTraceData(orderno, ordertype, usetype, getUserToken(request));
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
		queryParam.setOrganId(request.getParameter("organId"));
		queryParam.setReviewperson(request.getParameter("reviewperson"));
		queryParam.setMinUseTime(request.getParameter("minUseTime"));
		queryParam.setMaxUseTime(request.getParameter("maxUseTime"));
		queryParam.setOrdersource(request.getParameter("ordersource"));
		queryParam.setType(request.getParameter("type"));
		queryParam.setUsetype(request.getParameter("usetype"));
		queryParam.setOrderstatus(request.getParameter("orderstatus"));
		queryParam.setCancelparty(request.getParameter("cancelparty"));
		queryParam.setTradeno(request.getParameter("tradeno"));
		queryParam.setPaymentstatus(request.getParameter("paymentstatus"));
		queryParam.setPaymethod(request.getParameter("paymethod"));
        queryParam.setBelongleasecompany(request.getParameter("belongleasecompany"));
		return queryParam;
	}


    /**
     * 获取服务车企
     * @param shortname 车企名字
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "OrderManage/GetBelongCompanySelect")
    @ResponseBody
    public List<Map<String, Object>> getBelongCompanySelect(
            @RequestParam(value = "belongleasecompany", required = false) String belongleasecompany,
            @RequestParam(value = "type") String type,
            @RequestParam(value = "usetype") String usetype,
            HttpServletRequest request,
            HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        String userToken = getUserToken(request);
        OrderManageQueryParam queryParam = new OrderManageQueryParam();
        queryParam.setBelongleasecompany(belongleasecompany);
        queryParam.setType(type);
        queryParam.setUsetype(usetype);
        initLeUserInfo(request, queryParam);
        return orderManageService.getBelongCompanySelect(queryParam, userToken);
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
     * 查询订单取消规则
     * @param param
     * @return
     */
    @RequestMapping(value = "OrderManage/GetCancelPriceDetail")
    @ResponseBody
    public Map<String, Object> getCancelPriceDetail(@RequestBody Map<String, String> param, HttpServletRequest request) {
        String userToken = getUserToken(request);
        return orderManageService.getCancelPriceDetail(param, userToken);
    }

    /**
     * 订单免责处理
     * @param object
     * @param request
     * @return
     */
    @RequestMapping(value = "OrderManage/ExemptionOrder")
    @ResponseBody
    public Map<String, Object> exemptionOrder(@RequestBody PubOrderCancel object, HttpServletRequest request) {
        String userToken = getUserToken(request);
        User user = getLoginLeUser(request);
        object.setExemptionoperator(user.getId());
        return orderManageService.exemptionOrder(object, userToken);
    }

    /**
     * 结束订单
     * @param orderno
     * @param request
     * @return
     */
    @RequestMapping(value = "OrderManage/EndOrder/{orderno}")
    @ResponseBody
    public Map<String, Object> endOrder(@PathVariable String orderno, HttpServletRequest request) {
        String usertoken = getUserToken(request);
        return orderManageService.endOrder(orderno, usertoken);
    }

}
