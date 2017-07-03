package com.szyciov.lease.controller;

import com.szyciov.lease.entity.LeAccountRules;
import com.szyciov.lease.entity.OrgOrdercomment;
import com.szyciov.lease.param.OrderManageQueryParam;
import com.szyciov.lease.service.OrderManageService;
import com.szyciov.op.entity.PubSendRules;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.param.OrdercommentQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class OrderManageController extends BaseController {
	
	private OrderManageService orderManageService;
	@Resource(name = "orderManageService")
	public void setOrderService(OrderManageService orderManageService) {
		this.orderManageService = orderManageService;
	}
	
	/**
	 * 查询订单列表
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/GetOrgOrderByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getOrgOrderByQuery(@RequestBody OrderManageQueryParam queryParam) {
		return this.orderManageService.getOrgOrderByQuery(queryParam);
	}
	
	/**
	 * 根据订单号查询订单详情
	 * @param orderno
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/GetOrgOrderByOrderno/{orderno}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getOrgOrderByOrderno(@PathVariable String orderno) {
		return this.orderManageService.getOrgOrderByOrderno(orderno);
	}
	
	/**
	 * 查询机构下单人(select2)
	 * @param userName
	 * @param organId
	 * @param leasescompanyid
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/GetOrgUser", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getOrgUser(@RequestParam(value = "userName", required = false) String userName,
			@RequestParam(value = "organId", required = false) String organId, @RequestParam String leasescompanyid) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userName", userName);
		params.put("organId", organId);
		params.put("leasescompanyid", leasescompanyid);
		return this.orderManageService.getOrgUser(params);
	}
	
	/**
	 * 查询车型列表
	 * @param modelName
	 * @param leasescompanyid
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/GetCompanyVehicleModel", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getCompanyVehicleModel(
			@RequestParam String orderno,
			@RequestParam String leasescompanyid) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("orderno", orderno);
		params.put("leasescompanyid", leasescompanyid);
		return this.orderManageService.getCompanyVehicleModel(params);
	}
	
	/**
	 * 根据订单号获取订单信息
	 * @param orderno
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/GetOrgWaitingOrderByOrderno/{orderno}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getOrgWaitingOrderByOrderno(@PathVariable String orderno) {
		return this.orderManageService.getOrgOrderByOrderno(orderno);
	}
	
	/**
	 * 取消订单
	 * @param orderno
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/CancelOrgOrder/{orderno}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> cancelOrgOrder(@PathVariable String orderno) {
		return this.orderManageService.cancelOrgOrder(orderno);
	}
	
	/**
	 * 根据订单获取附近司机
	 * @param orderno
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "api/OrderManage/GetOrgDriverByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getOrgDriverByQuery(@RequestBody OrderManageQueryParam queryParam) throws IOException {
		return this.orderManageService.getOrgDriverByQuery(queryParam);
	}
	
	/**
	 * 查询人工派单记录
	 * @param orderno
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/GetOrgSendOrderRecord/{orderno}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getOrgSendOrderRecord(@PathVariable String orderno) {
		return this.orderManageService.getOrgSendOrderRecord(orderno);
	}
	
	/**
	 * 查询更换司机记录
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/GetOrgChangeDriverByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getOrgChangeDriverByQuery(@RequestBody OrderManageQueryParam queryParam) {
		return this.orderManageService.getOrgChangeDriverByQuery(queryParam);
	}
	
	/**
	 * 查询订单复核记录
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/GetOrgOrderReviewByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getOrgOrderReviewByQuery(@RequestBody OrderManageQueryParam queryParam) {
		return this.orderManageService.getOrgOrderReviewByQuery(queryParam);
	}
	
	/**
	 * 人工派单
     *
     * 此方法于2017/05/25搬迁至 carservice-api 中
     * 具体访问地址： api/OrderManage/ManualSendOrder
     * 后请求请直接走carservice-api
     *
	 * @param orgOrder
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/ManualSendOrder", method = RequestMethod.POST)
	@ResponseBody
    @Deprecated
	public Map<String, Object> manualSendOrder(@RequestBody OrgOrder orgOrder) {
		return this.orderManageService.manualSendOrder(orgOrder);
	}
	
	/**
	 * 更换司机
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/ChangeOrgDriver", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> changeOrgDriver(@RequestBody Map<String, Object> params) {
		return this.orderManageService.changeOrgDriver(params);
	}
	
	/**
	 * 订单复核
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/OrgOrderReview", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> orgOrderReview(@RequestBody Map<String, Object> params) {
		return this.orderManageService.orgOrderReview(params);
	}
	
	/**
	 * 拒绝复核
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/OrgOrderReject", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> orgOrderReject(@RequestBody Map<String, Object> params) {
		return this.orderManageService.orgOrderReject(params);
	}
	
	/**
	 * 申请复核
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/ApplyRecheckOrder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> applyRecheckOrder(@RequestBody Map<String, String> params) {
		return this.orderManageService.applyRecheckOrder(params);
	}
	
	/**
	 * 查询当前用户所在租赁公司的派单规则城市
	 * @param object
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/GetSendRulesByName", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, String>> getSendRulesByName(@RequestBody PubSendRules object) {
		return this.orderManageService.getSendRulesByName(object);
	}
	
	/**
	 * 查询订单的计费规则
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/GetOrderAccountRulesByOrderno", method = RequestMethod.POST)
	@ResponseBody
	public LeAccountRules getOrderAccountRulesByOrderno(@RequestBody Map<String, Object> params) {
		return this.orderManageService.getOrderAccountRulesByOrderno(params);
	}
	
	/**
	 * 查询机构列表(select2)
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/GetOrganByName", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getOrganByName(@RequestBody Map<String, Object> params) {
		return this.orderManageService.getOrganByName(params);
	}
	
	/**
	 * 查询订单最原始订单记录
	 * @param orderno
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/GetFirstOrderByOrderno/{orderno}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getFirstOrderByOrderno(@PathVariable String orderno) {
		return this.orderManageService.getFirstOrderByOrderno(orderno);
	}
	
	/**
	 * 查询客服备注列表
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/GetOrgOrderCommentByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getOrgOrderCommentByQuery(@RequestBody OrdercommentQueryParam queryParam) {
		return this.orderManageService.getOrgOrderCommentByQuery(queryParam);
	}
	
	/**
	 * 添加客服备注
	 * @param object
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/AddOrgOrderRemark", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> addOrgOrderRemark(@RequestBody OrgOrdercomment object) {
		return this.orderManageService.addOrgOrdercomment(object);
	}
	
	/**
	 * 订单号联想框(select2)
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/GetOrdernoBySelect", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getOrdernoBySelect(@RequestBody OrderManageQueryParam queryParam) {
		return orderManageService.getOrdernoBySelect(queryParam);
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



    /**
     * 获取服务车企
     * @param params 查询参数
     * @return 车企list
     */
    @RequestMapping(value = "api/OrderManage/GetBelongLeaseCompanySelect", method = RequestMethod.POST)
    @ResponseBody
    public List<Map<String, Object>> getBelongLeaseCompanySelect(@RequestBody Map<String, Object> params) {
        return orderManageService.getBelongLeaseCompanySelect(params);
    }

}
