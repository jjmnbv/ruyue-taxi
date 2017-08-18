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
import com.szyciov.lease.param.TobOrderManageQueryParam;
import com.szyciov.lease.service.TobOrderManageService;
import com.szyciov.op.entity.OpTaxiOrderReview;
import com.szyciov.param.OrdercommentQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;

/**
 * toC订单管理模块控制器
 */
@Controller
public class TobOrderManageController extends BaseController {
	private static final Logger logger = Logger.getLogger(TobOrderManageController.class);

	public TobOrderManageService tobOrderManageService;

	@Resource(name = "tobOrderManageService")
	public void setTobOrderManageService(TobOrderManageService tobOrderManageService) {
		this.tobOrderManageService = tobOrderManageService;
	}
	
	/** 
	 * <p>分页查询网约车订单</p>
	 *
	 * @param queryParam 查询请求对象，封装需要查询的key和页码等信息
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "api/OrderManage2b/GetNetAboutCarOrderByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getNetAboutCarOrderByQuery(@RequestBody TobOrderManageQueryParam queryParam) {
		return tobOrderManageService.getNetAboutCarOrderByQuery(queryParam);
	}
	
	/** 
	 * <p>分页查询出租车订单</p>
	 *
	 * @param queryParam 查询请求对象，封装需要查询的key和页码等信息
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "api/OrderManage2b/GetTaxiOrderByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getTaxiOrderByQuery(@RequestBody TobOrderManageQueryParam queryParam) {
		return tobOrderManageService.getTaxiOrderByQuery(queryParam);
	}
	
	/** 
	 * <p>根据网约车订单号联想查找订单号</p>
	 *
	 * @param companyId 所属租赁公司   
	 * @param orderNo 订单号
	 * @return 
	 */
	@RequestMapping(value = "api/OrderManage2b/GetNetAboutCarOrderNOByQuery", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getNetAboutCarOrderNOByQuery(
			@RequestParam(value = "companyId", required = true) String companyId,
			@RequestParam(value = "orderNo", required = false) String orderNo) {
		logger.log(Level.INFO, "companyId:" + companyId);
		logger.log(Level.INFO, "orderNo:" + orderNo);
		return tobOrderManageService.getNetAboutCarOrderNOByQuery(companyId, orderNo);
	}
	
	/** 
	 * <p>根据下单人姓名或手机号联想查找下单人</p>
	 *
	 * @param orderPerson 下单人
	 * @return 
	 */
	@RequestMapping(value = "api/OrderManage2b/GetNetAboutCarOrderUserByQuery", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getNetAboutCarOrderUserByQuery(
			@RequestParam(value = "orderPerson", required = false) String orderPerson) {
		logger.log(Level.INFO, "orderPerson:" + orderPerson);
		return tobOrderManageService.getNetAboutCarOrderUserByQuery(orderPerson);
	}
	
	/** 
	 * <p>根据司机姓名或手机号联想查找司机</p>
	 *
	 * @param companyId 所属租赁公司   
	 * @param driver 司机
	 * @param vehicleType 司机类型
	 * @return 
	 */
	@RequestMapping(value = "api/OrderManage2b/GetNetAboutCarOrderDriverByQuery", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getNetAboutCarOrderDriverByQuery(
			@RequestParam(value = "companyId", required = true) String companyId,
			@RequestParam(value = "driver", required = false) String driver,
			@RequestParam(value = "vehicleType", required = true) String vehicleType) {
		logger.log(Level.INFO, "companyId:" + companyId);
		logger.log(Level.INFO, "driver:" + driver);
		logger.log(Level.INFO, "vehicleType:" + vehicleType);
		return tobOrderManageService.getNetAboutCarOrderDriverByQuery(companyId, driver, vehicleType);
	}
	
	/** 
	 * <p>查询网约车订单导出数据</p>
	 *
	 * @param TobOrderManageQueryParam
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage2b/GetNetAboutCarOrderExport", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getNetAboutCarOrderExport(@RequestBody TobOrderManageQueryParam queryParam) {
		return tobOrderManageService.getNetAboutCarOrderExport(queryParam);
	}
	
	/**
	 * 获取租赁合作方
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage2b/GetPartnerCompanySelect", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getPartnerCompanySelect(@RequestBody Map<String,Object> params) {
		return tobOrderManageService.getPartnerCompanySelect(params);
	}
	
	/** 
	 * <p>查询出租车订单导出数据</p>
	 *
	 * @param TobOrderManageQueryParam
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage2b/GetTaxiOrderExport", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getTaxiOrderExport(@RequestBody TobOrderManageQueryParam queryParam) {
		return tobOrderManageService.getTaxiOrderExport(queryParam);
	}

	//网约车详情
	/**
	 * 查询订单详情
	 * @param orderno
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage2b/GetOrgOrderByOrderno/{orderno}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getOrgOrderByOrderno(@PathVariable String orderno) {
		return tobOrderManageService.getOrgOrderByOrderno(orderno);
	}
	
	/**
	 * 查询订单最原始订单记录
	 * @param orderno
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage2b/GetFirstOrgOrderByOrderno/{orderno}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getFirstOrgOrderByOrderno(@PathVariable String orderno) {
		return tobOrderManageService.getFirstOrgOrderByOrderno(orderno);
	}
	
	/**
	 * 查询订单的人工派单记录
	 * @param orderno
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage2b/GetOrgSendOrderRecord/{orderno}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getOrgSendOrderRecord(@PathVariable String orderno) {
		return tobOrderManageService.getOrgSendOrderRecord(orderno);
	}
	
	/**
	 * 查询订单更换司机记录 
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage2b/GetOrgChangeDriverByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getOrgChangeDriverByQuery(@RequestBody OrderManageQueryParam queryParam) {
		return tobOrderManageService.getOrgChangeDriverByQuery(queryParam);
	}
	
	/**
	 * 查询订单复核记录
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage2b/GetOpOrderReviewByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getOpOrderReviewByQuery(@RequestBody OrderManageQueryParam queryParam) {
		return tobOrderManageService.getOpOrderReviewByQuery(queryParam);
	}
	
	/**
	 * 查询客服备注列表
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage2b/GetOrgOrderCommentByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getOrgOrderCommentByQuery(@RequestBody OrdercommentQueryParam queryParam) {
		return tobOrderManageService.getOrgOrderCommentByQuery(queryParam);
	}
	
	// 出租车详情
	/**
	 * 根据订单号查询订单详情
	 * @param orderno
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage2b/GetOpTaxiOrderByOrderno/{orderno}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getOpTaxiOrderByOrderno(@PathVariable String orderno) {
		return tobOrderManageService.getOpTaxiOrderByOrderno(orderno);
	}
	
	/**
	 * 查询原始订单数据(第一条复核记录)
	 * @param orderno
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage2b/GetFirstTaxiOrderByOrderno/{orderno}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getFirstTaxiOrderByOrderno(@PathVariable String orderno) {
		return tobOrderManageService.getFirstTaxiOrderByOrderno(orderno);
	}
	
	/**
	 * 查询人工派单记录
	 * @param orderno
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage2b/GetOpSendTaxiOrderRecord/{orderno}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getOpSendTaxiOrderRecord(@PathVariable String orderno) {
		return tobOrderManageService.getOpSendTaxiOrderRecord(orderno);
	}
	
	/**
	 * 查询更换司机记录
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage2b/GetOpChangeDriverList", method =  RequestMethod.POST)
	@ResponseBody
	public PageBean getOpChangeDriverList(@RequestBody OrderManageQueryParam queryParam) {
		return tobOrderManageService.getOpChangeDriverList(queryParam);
	}
	
	/**
	 * 查询更换车辆记录
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage2b/GetOpChangeVehicleList", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getOpChangeVehicleList(@RequestBody OrderManageQueryParam queryParam) {
		return tobOrderManageService.getOpChangeVehicleList(queryParam);
	}
	
	/**
	 * 分页查询订单复核记录
	 * @param object
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage2b/GetOpTaxiOrderReviewByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getOpTaxiOrderReviewByQuery(@RequestBody OpTaxiOrderReview object) {
		return tobOrderManageService.getOpTaxiOrderReviewByQuery(object);
	}
	
	/**
	 * 查询客服备注列表
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "api/OrderManage2b/GetOpTaxiOrderCommentByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getOpTaxiOrderCommentByQuery(@RequestBody OrdercommentQueryParam queryParam) {
		return tobOrderManageService.getOpTaxiOrderCommentByQuery(queryParam);
	}
	
	
	
}
