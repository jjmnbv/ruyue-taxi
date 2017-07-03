package com.szyciov.operate.controller;

import java.io.IOException;
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

import com.szyciov.op.entity.OpShiftRules;
import com.szyciov.op.param.OpShiftRulesQueryParam;
import com.szyciov.operate.service.OpShiftRulesService;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;

@Controller
public class OpShiftRulesController extends BaseController{
	
	private static final Logger logger = Logger.getLogger(OpShiftRulesController.class);

	public OpShiftRulesService opShiftRulesService;
	
	@Resource(name = "opShiftRulesService")
	public void setOpShiftRulesService(OpShiftRulesService opShiftRulesService) {
		this.opShiftRulesService = opShiftRulesService;
	}
	
	
	@RequestMapping(value = "api/OpShiftRules/GetVailableCitys")
	@ResponseBody
	public List<Map<String,String>> getVailableCitys(@RequestBody Map<String,Object> params,HttpServletRequest request, HttpServletResponse response) {
		return opShiftRulesService.getVailableCitys(params);
	}
	
	@RequestMapping(value = "api/OpShiftRules/GetCitys")
	@ResponseBody
	public List<Map<String,String>> getCitys(@RequestBody Map<String,Object> params,HttpServletRequest request, HttpServletResponse response) {
		return opShiftRulesService.getCitys(params);
	}
	
	@RequestMapping("api/OpShiftRules/GetShiftRulesByQuery")
	@ResponseBody
	public PageBean getShiftRulesByQuery(@RequestBody OpShiftRulesQueryParam queryParam, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		return opShiftRulesService.getShiftRulesByQuery(queryParam);
	}
	
	@RequestMapping(value = "api/OpShiftRules/GetShiftRules")
	@ResponseBody
	public OpShiftRules getShiftRules(@RequestBody Map<String,Object> params,HttpServletRequest request, HttpServletResponse response) {
		return opShiftRulesService.getShiftRules(params);
	}
	
	@RequestMapping("api/OpShiftRules/CreateShiftRules")
	@ResponseBody
	public Map<String,Object> createShiftRules(@RequestBody Map<String,Object> params, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		return opShiftRulesService.createShiftRules(params);
	}
	
	@RequestMapping("api/OpShiftRules/UpdateShiftRules")
	@ResponseBody
	public Map<String,Object> updateShiftRules(@RequestBody Map<String,Object> params, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String usertoken = getUserToken(request);
		return opShiftRulesService.updateShiftRules(params);
	}
}
