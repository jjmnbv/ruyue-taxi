package com.szyciov.lease.controller;

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

import com.szyciov.lease.dto.balance.BalanceDto;
import com.szyciov.lease.param.BalanceManageQueryParam;
import com.szyciov.lease.service.BalanceManageService;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;

/**
 * 出租车行程费用结算管理
 * @author chenjunfeng_pc
 *
 */
@Controller
@RequestMapping(value="api/BalanceManage")
public class BalanceManageController extends BaseController {
	private static final Logger logger = Logger.getLogger(BalanceManageController.class);

	@Resource(name = "BalanceManageService")
	public BalanceManageService service;

	 
	
	/** 
	 * 分页查询行程费用结算信息
	 *
	 * @param queryParam 查询请求对象，封装需要查询的key和页码等信息
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "QueryBalanceByParam", method = RequestMethod.POST)
	@ResponseBody
	public PageBean queryBalanceByParam(@RequestBody BalanceManageQueryParam queryParam) {
		return service.queryBalanceByParam(queryParam);
	}
	
	/** 
	 * <p>订单号</p>
	 *
	 * @param orderno 订单号
	 * @return
	 */
	@RequestMapping(value = "GetOrderNo", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getOrderNo(@RequestParam(value = "orderno", required = false) String orderno,@RequestParam String lecompanyid) {
		logger.log(Level.INFO, "orderno:" + orderno);		
		return service.getOrderNo(orderno,lecompanyid);
	}
	
	/** 
	 * <p>司机</p>
	 *
	 * @param driver 姓名/手机号
	 * @return
	 */
	@RequestMapping(value = "GetDriverByNameOrPhone", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getDriverByNameOrPhone(@RequestParam(value = "driver", required = false) String driver,@RequestParam String lecompanyid) {
		return service.getDriverByNameOrPhone(driver,lecompanyid);
	}
	
	/** 
	 * <p>所属企业</p>
	 *
	 * @return
	 */
	@RequestMapping(value = "GetCompanyNameById", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getCompanyNameById(@RequestParam String lecompanyid) {
		return service.getCompanyNameById(lecompanyid);
	}
	
	/** 
	 * <p>资格证号</p>
	 *
	 * @param jobnum 资格证号
	 * @return
	 */
	@RequestMapping(value = "GetJobNum", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getJobnum(@RequestParam(value = "jobnum", required = false) String jobnum,@RequestParam String lecompanyid) {
		return service.getJobnumByJobnum(jobnum,lecompanyid);
	}
	
	/** 
	 * <p>资格证号</p>
	 *
	 * @param jobnum 资格证号
	 * @return
	 */
	@RequestMapping(value = "GetFullPlateno", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getFullPlateno(@RequestParam(value = "fullplateno", required = false) String fullplateno,@RequestParam String lecompanyid) {
		return service.getFullPlateno(fullplateno,lecompanyid);
	}
	
	/** 
	 * <p>查询导出数据</p>
	 *
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "GetBalanceListExport", method = RequestMethod.POST)
	@ResponseBody
	public List<BalanceDto> getBalanceListExport(@RequestBody BalanceManageQueryParam queryParam) {
		return service.getBalanceListExport(queryParam);
	}
	
}
