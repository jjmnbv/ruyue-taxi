package com.szyciov.lease.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.lease.param.OrderManageQueryParam;
import com.szyciov.lease.param.TocOrderManageQueryParam;
import com.szyciov.lease.service.TocOrderManageService;
import com.szyciov.op.entity.OpTaxiOrderReview;
import com.szyciov.param.OrdercommentQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;

/**
 * toC订单管理模块控制器
 */
@Controller
public class TocOrderManageController extends BaseController {
	private static final Logger logger = Logger.getLogger(TocOrderManageController.class);

	public TocOrderManageService tocOrderManageService;

	@Resource(name = "tocOrderManageService")
	public void setTocOrderManageService(TocOrderManageService tocOrderManageService) {
		this.tocOrderManageService = tocOrderManageService;
	}
	
	/** 
	 * <p>分页查询网约车订单</p>
	 *
	 * @param queryParam 查询请求对象，封装需要查询的key和页码等信息
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "api/OrderManage/GetNetAboutCarOrderByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getNetAboutCarOrderByQuery(@RequestBody TocOrderManageQueryParam queryParam) {
		return tocOrderManageService.getNetAboutCarOrderByQuery(queryParam);
	}
	
	/** 
	 * <p>分页查询出租车订单</p>
	 *
	 * @param queryParam 查询请求对象，封装需要查询的key和页码等信息
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "api/OrderManage/GetTaxiOrderByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getTaxiOrderByQuery(@RequestBody TocOrderManageQueryParam queryParam) {
		return tocOrderManageService.getTaxiOrderByQuery(queryParam);
	}
	
	/** 
	 * <p>根据网约车订单号联想查找订单号</p>
	 *
	 * @param companyId 所属租赁公司   
	 * @param orderNo 订单号
	 * @return 
	 */
	@RequestMapping(value = "api/OrderManage/GetNetAboutCarOrderNOByQuery", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getNetAboutCarOrderNOByQuery(
			@RequestParam(value = "companyId", required = true) String companyId,
			@RequestParam(value = "orderNo", required = false) String orderNo) {
		logger.log(Level.INFO, "companyId:" + companyId);
		logger.log(Level.INFO, "orderNo:" + orderNo);
		return tocOrderManageService.getNetAboutCarOrderNOByQuery(companyId, orderNo);
	}
	
	/** 
	 * <p>根据下单人姓名或手机号联想查找下单人</p>
	 *
	 * @param orderPerson 下单人
	 * @return 
	 */
	@RequestMapping(value = "api/OrderManage/GetNetAboutCarOrderUserByQuery", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getNetAboutCarOrderUserByQuery(
			@RequestParam(value = "orderPerson", required = false) String orderPerson) {
		logger.log(Level.INFO, "orderPerson:" + orderPerson);
		return tocOrderManageService.getNetAboutCarOrderUserByQuery(orderPerson);
	}
	
	/** 
	 * <p>根据司机姓名或手机号联想查找司机</p>
	 *
	 * @param companyId 所属租赁公司   
	 * @param driver 司机
	 * @param vehicleType 司机类型
	 * @return 
	 */
	@RequestMapping(value = "api/OrderManage/GetNetAboutCarOrderDriverByQuery", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getNetAboutCarOrderDriverByQuery(
			@RequestParam(value = "companyId", required = true) String companyId,
			@RequestParam(value = "driver", required = false) String driver,
			@RequestParam(value = "vehicleType", required = true) String vehicleType) {
		logger.log(Level.INFO, "companyId:" + companyId);
		logger.log(Level.INFO, "driver:" + driver);
		logger.log(Level.INFO, "vehicleType:" + vehicleType);
		return tocOrderManageService.getNetAboutCarOrderDriverByQuery(companyId, driver, vehicleType);
	}
	
	/** 
	 * <p>查询网约车订单导出数据</p>
	 *
	 * @param TocOrderManageQueryParam
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/GetNetAboutCarOrderExport", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getNetAboutCarOrderExport(@RequestBody TocOrderManageQueryParam queryParam) {
		return tocOrderManageService.getNetAboutCarOrderExport(queryParam);
	}
	
	/** 
	 * <p>查询出租车订单导出数据</p>
	 *
	 * @param TocOrderManageQueryParam
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/GetTaxiOrderExport", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getTaxiOrderExport(@RequestBody TocOrderManageQueryParam queryParam) {
		return tocOrderManageService.getTaxiOrderExport(queryParam);
	}

	//网约车详情
	/**
	 * 查询订单详情
	 * @param orderno
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/GetOpOrderByOrderno/{orderno}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getOpOrderByOrderno(@PathVariable String orderno) {
		return tocOrderManageService.getOpOrderByOrderno(orderno);
	}
	
	/**
	 * 查询订单最原始订单记录
	 * @param orderno
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/GetFirstOpOrderByOrderno/{orderno}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getFirstOpOrderByOrderno(@PathVariable String orderno) {
		return tocOrderManageService.getFirstOpOrderByOrderno(orderno);
	}
	
	/**
	 * 查询订单的人工派单记录
	 * @param orderno
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/GetOpSendOrderRecord/{orderno}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getOpSendOrderRecord(@PathVariable String orderno) {
		return tocOrderManageService.getOpSendOrderRecord(orderno);
	}
	
	/**
	 * 查询订单更换司机记录 
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/GetOpChangeDriverByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getOpChangeDriverByQuery(@RequestBody OrderManageQueryParam queryParam) {
		return tocOrderManageService.getOpChangeDriverByQuery(queryParam);
	}
	
	/**
	 * 查询订单复核记录
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/GetOpOrderReviewByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getOpOrderReviewByQuery(@RequestBody OrderManageQueryParam queryParam) {
		return tocOrderManageService.getOpOrderReviewByQuery(queryParam);
	}
	
	/**
	 * 查询客服备注列表
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/GetOpOrderCommentByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getOpOrderCommentByQuery(@RequestBody OrdercommentQueryParam queryParam) {
		return tocOrderManageService.getOpOrderCommentByQuery(queryParam);
	}
	
	// 出租车详情
	/**
	 * 根据订单号查询订单详情
	 * @param orderno
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/GetOpTaxiOrderByOrderno/{orderno}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getOpTaxiOrderByOrderno(@PathVariable String orderno) {
		return tocOrderManageService.getOpTaxiOrderByOrderno(orderno);
	}
	
	/**
	 * 查询原始订单数据(第一条复核记录)
	 * @param orderno
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/GetFirstTaxiOrderByOrderno/{orderno}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getFirstTaxiOrderByOrderno(@PathVariable String orderno) {
		return tocOrderManageService.getFirstTaxiOrderByOrderno(orderno);
	}
	
	/**
	 * 查询人工派单记录
	 * @param orderno
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/GetOpSendTaxiOrderRecord/{orderno}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getOpSendTaxiOrderRecord(@PathVariable String orderno) {
		return tocOrderManageService.getOpSendTaxiOrderRecord(orderno);
	}
	
	/**
	 * 查询更换司机记录
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/GetOpChangeDriverList", method =  RequestMethod.POST)
	@ResponseBody
	public PageBean getOpChangeDriverList(@RequestBody OrderManageQueryParam queryParam) {
		return tocOrderManageService.getOpChangeDriverList(queryParam);
	}
	
	/**
	 * 查询更换车辆记录
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/GetOpChangeVehicleList", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getOpChangeVehicleList(@RequestBody OrderManageQueryParam queryParam) {
		return tocOrderManageService.getOpChangeVehicleList(queryParam);
	}
	
	/**
	 * 分页查询订单复核记录
	 * @param object
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/GetOpTaxiOrderReviewByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getOpTaxiOrderReviewByQuery(@RequestBody OpTaxiOrderReview object) {
		return tocOrderManageService.getOpTaxiOrderReviewByQuery(object);
	}
	
	/**
	 * 查询客服备注列表
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage/GetOpTaxiOrderCommentByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getOpTaxiOrderCommentByQuery(@RequestBody OrdercommentQueryParam queryParam) {
		return tocOrderManageService.getOpTaxiOrderCommentByQuery(queryParam);
	}
	
	
	
}
