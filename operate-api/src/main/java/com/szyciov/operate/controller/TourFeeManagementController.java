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

import com.szyciov.op.param.TourFeeManagementQueryParam;
import com.szyciov.operate.service.TourFeeManagementService;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;

/**
 * 行程费用结算管理模块控制器
 * @author chenjunfeng_pc
 *
 */
@Controller
public class TourFeeManagementController extends BaseController {
	private static final Logger logger = Logger.getLogger(TourFeeManagementController.class);

	public TourFeeManagementService tourFeeManagementService;

	@Resource(name = "TourFeeManagementService")
	public void setTourFeeManagementService(TourFeeManagementService tourFeeManagementService) {
		this.tourFeeManagementService = tourFeeManagementService;
	}
	
	/** 
	 * <p>分页查询行程费用结算信息</p>
	 *
	 * @param queryParam 查询请求对象，封装需要查询的key和页码等信息
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "api/TourFeeManagement/GetTourFeeByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getTourFeeByQuery(@RequestBody TourFeeManagementQueryParam queryParam) {
		return tourFeeManagementService.getTourFeeByQuery(queryParam);
	}
	
	/** 
	 * <p>订单号</p>
	 *
	 * @param orderno 订单号
	 * @return
	 */
	@RequestMapping(value = "api/TourFeeManagement/GetOrderNo", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getOrderNo(@RequestParam(value = "orderno", required = false) String orderno) {
		logger.log(Level.INFO, "orderno:" + orderno);		
		return tourFeeManagementService.getOrderNo(orderno);
	}
	
	/** 
	 * <p>司机</p>
	 *
	 * @param driver 姓名/手机号
	 * @return
	 */
	@RequestMapping(value = "api/TourFeeManagement/GetDriverByNameOrPhone", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getDriverByNameOrPhone(@RequestParam(value = "driver", required = false) String driver) {
		logger.log(Level.INFO, "driver:" + driver);		
		return tourFeeManagementService.getDriverByNameOrPhone(driver);
	}
	
	/** 
	 * <p>所属企业</p>
	 *
	 * @return
	 */
	@RequestMapping(value = "api/TourFeeManagement/GetCompanyNameById", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getCompanyNameById() {
		return tourFeeManagementService.getCompanyNameById();
	}
	
	/** 
	 * <p>资格证号</p>
	 *
	 * @param jobnum 资格证号
	 * @return
	 */
	@RequestMapping(value = "api/TourFeeManagement/GetJobnumByJobnum", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getJobnumByJobnum(@RequestParam(value = "jobnum", required = false) String jobnum) {
		logger.log(Level.INFO, "jobnum:" + jobnum);		
		return tourFeeManagementService.getJobnumByJobnum(jobnum);
	}
	
	/** 
	 * <p>查询导出数据</p>
	 *
	 * @param TourFeeManagementQueryParam
	 * @return
	 */
	@RequestMapping(value = "api/TourFeeManagement/GetTourFeeListExport", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getTourFeeListExport(@RequestBody TourFeeManagementQueryParam queryParam) {
		return tourFeeManagementService.getTourFeeListExport(queryParam);
	}
	
}
