package com.szyciov.operate.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.lease.param.TobOrderManageQueryParam;
import com.szyciov.op.param.PlatformOrderManagementParam;
import com.szyciov.operate.service.PlatformOrderManagementService;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;
@Controller
public class PlatformOrderManagementController extends BaseController{
	private static final Logger logger = Logger.getLogger(PlatformOrderManagementService.class);

	public PlatformOrderManagementService platformOrderManagementService;

	@Resource(name = "PlatformOrderManagementService")
	public void setPlatformOrderManagement(PlatformOrderManagementService platformOrderManagementService) {
		this.platformOrderManagementService = platformOrderManagementService;
	}
	/** 
	 * <p>根据下单人姓名或手机号联想查找下单人</p>
	 *
	 * @param orderPerson 下单人
	 * @return 
	 */
	@RequestMapping(value = "api/PlatformOrderManagement/GetNetAboutCarOrderUserByQuery", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getNetAboutCarOrderUserByQuery(
			@RequestParam(value = "orderPerson", required = false) String orderPerson) {
		logger.log(Level.INFO, "orderPerson:" + orderPerson);
		return platformOrderManagementService.getNetAboutCarOrderUserByQuery(orderPerson);
	}
	/** 
	 * <p>根据司机姓名或手机号联想查找司机</p>
	 *
	 * @param driver 司机
	 * @param vehicleType 司机类型
	 * @return 
	 */
	@RequestMapping(value = "api/PlatformOrderManagement/GetNetAboutCarOrderDriverByQuery", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getNetAboutCarOrderDriverByQuery(
			@RequestParam(value = "driver", required = false) String driver,
			@RequestParam(value = "vehicleType", required = true) String vehicleType) {
		logger.log(Level.INFO, "driver:" + driver);
		logger.log(Level.INFO, "vehicleType:" + vehicleType);
		return platformOrderManagementService.getNetAboutCarOrderDriverByQuery(driver, vehicleType);
	}
	/** 
	 * <p>分页查询网约车订单</p>
	 *
	 * @param queryParam 查询请求对象，封装需要查询的key和页码等信息
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "api/PlatformOrderManagement/GetNetAboutCarOrderByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getNetAboutCarOrderByQuery(@RequestBody PlatformOrderManagementParam queryParam) {
		return platformOrderManagementService.getNetAboutCarOrderByQuery(queryParam);
	}
	/**
	 * 获取租赁合作方
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "api/PlatformOrderManagement/GetPartnerCompanySelect", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getPartnerCompanySelect(@RequestBody Map<String,Object> params) {
		return platformOrderManagementService.getPartnerCompanySelect(params);
	}
	/** 
	 * <p>查询网约车订单导出数据</p>
	 *
	 * @param TobOrderManageQueryParam
	 * @return
	 */
	@RequestMapping(value = "api/PlatformOrderManagement/GetNetAboutCarOrderExport", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getNetAboutCarOrderExport(@RequestBody PlatformOrderManagementParam queryParam) {
		return platformOrderManagementService.getNetAboutCarOrderExport(queryParam);
	}

}
