package com.szyciov.lease.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.lease.entity.LeCashManage;
import com.szyciov.lease.param.CashManageQueryParam;
import com.szyciov.lease.service.LeCashManageService;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;

@Controller
public class LeCashManageController extends BaseController {
	private static final Logger logger = Logger.getLogger(LeCashManageController.class);
	
	public LeCashManageService leCashManageService;
	
	@Resource(name = "leCashManageService")
	public void setEmployeeService(LeCashManageService leCashManageService) {
		this.leCashManageService = leCashManageService;
	}

	
	/**
	 * 查询用户拥有权限的机构下拉框数据
	 * @param organName
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "api/LeCashManage/GetAccounts")
	@ResponseBody
	public List<Map<String, Object>> getAccounts(@RequestBody Map<String,String> params, HttpServletRequest request,HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		return leCashManageService.getAccounts(params);
	}
	
	/**
	 * 查询用户拥有权限的机构下拉框数据
	 * @param organName
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "api/LeCashManage/GetNames")
	@ResponseBody
	public List<Map<String, Object>> getNames(@RequestBody Map<String,String> params, HttpServletRequest request,HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		return leCashManageService.getNames(params);
	}
	
	@RequestMapping(value = "api/LeCashManage/GetCashByQuery")
	@ResponseBody
	public PageBean getCashByQuery(@RequestBody CashManageQueryParam queryParam, HttpServletRequest request,HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		return leCashManageService.getCashByQuery(queryParam);
	}
	
	@RequestMapping(value = "api/LeCashManage/CashReject")
	@ResponseBody
	public Map<String, Object> cashReject(@RequestBody Map<String,String> params, HttpServletRequest request,HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		Map<String,Object> res = new HashMap<String,Object>();
		res.put("status", "success");
		res.put("message", "更新数据成功");
		try{
			leCashManageService.cashReject(params);
		}catch(Exception e){
			res.put("status", "fail");
			res.put("message", "更新数据库时异常");
		}
		return res;
	}
	
	@RequestMapping(value = "api/LeCashManage/CashOk")
	@ResponseBody
	public Map<String, Object> cashOk(@RequestBody Map<String,String> params, HttpServletRequest request,HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		return leCashManageService.cashOk(params);
	}
	
	@RequestMapping(value = "api/LeCashManage/GetAllUnderCashData")
	@ResponseBody
	public List<LeCashManage> getAllUnderCashData(@RequestBody Map<String,String> params, HttpServletRequest request,HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		return leCashManageService.getAllUnderCashData(params);
	}
	
	@RequestMapping(value = "api/LeCashManage/GetExportDataCount")
	@ResponseBody
	public Map<String, Object> getExportDataCount(@RequestBody Map<String,String> params, HttpServletRequest request,HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		return leCashManageService.getExportDataCount(params);
	}
	
	
}


