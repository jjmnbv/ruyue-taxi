package com.szyciov.operate.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.szyciov.entity.PubOrderCancel;
import com.szyciov.lease.param.OrderManageQueryParam;
import com.szyciov.op.entity.OpTaxiOrder;
import com.szyciov.op.entity.OpTaxiOrderReview;
import com.szyciov.op.entity.OpTaxiordercomment;
import com.szyciov.operate.service.TaxiOrderManageService;
import com.szyciov.param.OrdercommentQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TaxiOrderManageController extends BaseController {
	
	private TaxiOrderManageService orderService;
	@Resource(name = "TaxiOrderManageService")
	public void setOrderService(TaxiOrderManageService orderService) {
		this.orderService = orderService;
	}
	
	/**
	 * 分页查询订单数据
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "api/TaxiOrderManage/GetOpTaxiOrderByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getOpTaxiOrderByQuery(@RequestBody OrderManageQueryParam queryParam) {
		return orderService.getOpTaxiOrderByQuery(queryParam);
	}
	
	/**
	 * 根据订单号查询订单详情
	 * @param orderno
	 * @return
	 */
	@RequestMapping(value = "api/TaxiOrderManage/GetOpTaxiOrderByOrderno/{orderno}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getOpTaxiOrderByOrderno(@PathVariable String orderno) {
		return orderService.getOpTaxiOrderByOrderno(orderno);
	}
	
	/**
	 * 拒绝复核
	 * @param orderno
	 * @return
	 */
	@RequestMapping(value = "api/TaxiOrderManage/OpTaxiOrderReject/{orderno}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> opTaxiOrderReject(@PathVariable String orderno) {
		return orderService.opTaxiOrderReject(orderno);
	}
	
	/**
	 * 申请复核
	 * @param object
	 * @return
	 */
	@RequestMapping(value = "api/TaxiOrderManage/ApplyRecheckTaxiOrder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> applyRecheckTaxiOrder(@RequestBody OpTaxiOrder object) {
		return orderService.applyRecheckTaxiOrder(object);
	}
	
	/**
	 * 分页查询订单复核记录
	 * @param object
	 * @return
	 */
	@RequestMapping(value = "api/TaxiOrderManage/GetOpTaxiOrderReviewByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getOpTaxiOrderReviewByQuery(@RequestBody OpTaxiOrderReview object) {
		return orderService.getOpTaxiOrderReviewByQuery(object);
	}
	
	/**
	 * 订单复核
	 * @param object
	 * @return
	 */
	@RequestMapping(value = "api/TaxiOrderManage/OpTaxiOrderReview", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> opTaxiOrderReview(@RequestBody OpTaxiOrderReview object) {
		return orderService.opTaxiOrderReview(object);
	}
	
	/**
	 * 查询可派单司机
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "api/TaxiOrderManage/GetDriverByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getDriverByQuery(@RequestBody OrderManageQueryParam queryParam) {
		return orderService.getDriverByQuery(queryParam);
	}
	
	/**
	 * 人工派单/更换车辆车牌联想下拉框(select)
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "api/TaxiOrderManage/GetTaxiPlatonoBySelect", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, String>> getTaxiPlatonoBySelect(@RequestBody Map<String, String> params) {
		return orderService.getTaxiPlatonoBySelect(params);
	}
	
	/**
	 * 人工派单
	 * @param object
	 * @return
	 */
	@RequestMapping(value = "api/TaxiOrderManage/ManualSendOrder")
	@ResponseBody
	public Map<String, String> manualSendOrder(@RequestBody OpTaxiOrder object) {
		return orderService.manualSendOrder(object);
	}
	
	/**
	 * 派单失败
	 * @param orderno
	 * @return
	 */
	@RequestMapping(value = "api/TaxiOrderManage/SendFail/{orderno}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> sendFail(@PathVariable String orderno) {
		return orderService.sendFail(orderno);
	}
	
	/**
	 * 更换车辆
	 * @param object
	 * @return
	 */
	@RequestMapping(value = "api/TaxiOrderManage/ChangeVehicle", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> changeVehicle(@RequestBody OpTaxiOrder object) {
		return orderService.changeVehicle(object);
	}
	
	/**
	 * 查询原始订单数据(第一条复核记录)
	 * @param orderno
	 * @return
	 */
	@RequestMapping(value = "api/TaxiOrderManage/GetFirstTaxiOrderByOrderno/{orderno}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getFirstTaxiOrderByOrderno(@PathVariable String orderno) {
		return orderService.getFirstTaxiOrderByOrderno(orderno);
	}
	
	/**
	 * 查询人工派单记录
	 * @param orderno
	 * @return
	 */
	@RequestMapping(value = "api/TaxiOrderManage/GetOpSendTaxiOrderRecord/{orderno}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getOpSendTaxiOrderRecord(@PathVariable String orderno) {
		return orderService.getOpSendTaxiOrderRecord(orderno);
	}
	
	/**
	 * 查询更换司机记录
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "api/TaxiOrderManage/GetOpChangeDriverList", method =  RequestMethod.POST)
	@ResponseBody
	public PageBean getOpChangeDriverList(@RequestBody OrderManageQueryParam queryParam) {
		return orderService.getOpChangeDriverList(queryParam);
	}
	
	/**
	 * 查询更换车辆记录
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "api/TaxiOrderManage/GetOpChangeVehicleList", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getOpChangeVehicleList(@RequestBody OrderManageQueryParam queryParam) {
		return orderService.getOpChangeVehicleList(queryParam);
	}
	
	/**
	 * 查询客服备注列表
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "api/TaxiOrderManage/GetOpTaxiOrderCommentByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getOpTaxiOrderCommentByQuery(@RequestBody OrdercommentQueryParam queryParam) {
		return orderService.getOpTaxiOrderCommentByQuery(queryParam);
	}
	
	/**
	 * 添加客服备注
	 * @param object
	 * @return
	 */
	@RequestMapping(value = "api/TaxiOrderManage/AddOpTaxiordercomment", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> addOpTaxiordercomment(@RequestBody OpTaxiordercomment object) {
		return orderService.addOpTaxiordercomment(object);
	}
	
	/**
	 * 订单导出
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "api/TaxiOrderManage/ExportOrder", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> exportOrder(@RequestBody OrderManageQueryParam queryParam) {
		return orderService.exportOrder(queryParam);
	}

	/**
	 * 获取订单列表中的服务车企(select2)
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "api/TaxiOrderManage/GetBelongCompanySelect", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getBelongCompanySelect(@RequestBody OrderManageQueryParam queryParam) {
		return orderService.getBelongCompanySelect(queryParam);
	}

    /**
     * 查询订单取消规则明细
     * @param param
     * @return
     */
    @RequestMapping(value = "api/TaxiOrderManage/GetCancelPriceDetail")
    @ResponseBody
    public Map<String, Object> getCancelPriceDetail(@RequestBody Map<String, String> param) {
        return orderService.getCancelPriceDetail(param);
    }

    /**
     * 免责处理
     * @param object
     * @return
     */
    @RequestMapping(value = "api/TaxiOrderManage/ExemptionOrder")
    @ResponseBody
    public Map<String, Object> exemptionOrder(@RequestBody PubOrderCancel object) {
        return orderService.exemptionOrder(object);
    }
	
}
