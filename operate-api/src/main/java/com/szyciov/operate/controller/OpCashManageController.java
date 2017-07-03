package com.szyciov.operate.controller;

import com.szyciov.op.param.cashManager.CashManageQueryParam;
import com.szyciov.op.vo.cashManager.CashManagerExcelVo;
import com.szyciov.operate.service.OpCashManageService;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class OpCashManageController extends BaseController {
	private static final Logger logger = Logger.getLogger(OpCashManageController.class);

	@Autowired
	public OpCashManageService opCashManageService;

	
	/**
	 * 查询用户拥有权限的机构下拉框数据
	 * @param params
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "api/OpCashManage/GetAccounts")
	@ResponseBody
	public List<Map<String, Object>> getAccounts(@RequestBody Map<String,String> params, HttpServletRequest request,HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		return opCashManageService.getAccounts(params);
	}
	
	/**
	 * 查询用户拥有权限的机构下拉框数据
	 * @param params
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "api/OpCashManage/GetNames")
	@ResponseBody
	public List<Map<String, Object>> getNames(@RequestBody Map<String,String> params, HttpServletRequest request,HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		return opCashManageService.getNames(params);
	}
	
	@RequestMapping(value = "api/OpCashManage/GetCashByQuery")
	@ResponseBody
	public PageBean getCashByQuery(@RequestBody CashManageQueryParam queryParam, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		return opCashManageService.getCashByQuery(queryParam);
	}
	
	@RequestMapping(value = "api/OpCashManage/CashReject")
	@ResponseBody
	public Map<String, Object> cashReject(@RequestBody Map<String,String> params, HttpServletRequest request,HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		Map<String, Object> res = new HashMap<>();
		try{
			res = opCashManageService.cashReject(params);
		}catch (Exception e){
			res.put("status", "fail");
			res.put("message", "系统繁忙，请稍后再试！");
			logger.error("提现拒绝异常!",e);
		}
		return res;
	}
	
	@RequestMapping(value = "api/OpCashManage/CashOk")
	@ResponseBody
	public Map<String, Object> cashOk(@RequestBody Map<String,String> params, HttpServletRequest request,HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		return opCashManageService.cashOk(params);
	}
	
	@RequestMapping(value = "api/OpCashManage/listExportData")
	@ResponseBody
	public List<CashManagerExcelVo> listExportData(@RequestBody CashManageQueryParam queryParam, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		return opCashManageService.listExportData(queryParam);
	}
	
	@RequestMapping(value = "api/OpCashManage/getListExportDataCount")
	@ResponseBody
	public Map<String, Object> getListExportDataCount(@RequestBody CashManageQueryParam queryParam, HttpServletRequest request,HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		return opCashManageService.getListExportDataCount(queryParam);
	}
	
	
}


