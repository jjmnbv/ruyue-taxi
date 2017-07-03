package com.szyciov.operate.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.op.entity.OpShiftRules;
import com.szyciov.op.entity.OpUser;
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
	
	@RequestMapping(value = "/OpShiftRules/Index")
	public ModelAndView getOpShiftRulesIndex(HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> model = new HashMap<String,Object>();
//		String usertoken = getUserToken(request);
//		OpUser cuser = getLoginOpUser(request);
//		Map<String,Object> params = new HashMap<String,Object>();
//		params.put("cuserid", cuser.getId());
//		model.put("cityinfo", opShiftRulesService.getVailableCitys(params,usertoken));
		return new ModelAndView("resource/opshiftrules/index", model);
	}
	
//	@RequestMapping(value = "/OpShiftRules/GetVailableCitys")
//	@ResponseBody
//	public List<Map<String,String>> getVailableCitys(HttpServletRequest request, HttpServletResponse response) {
//		String usertoken = getUserToken(request);
//		OpUser cuser = getLoginOpUser(request);
//		Map<String,Object> params = new HashMap<String,Object>();
//		params.put("cuserid", cuser.getId());
//		return opShiftRulesService.getVailableCitys(params,usertoken);
//	}
	
	
	@RequestMapping(value = "/OpShiftRules/GetCitys")
	@ResponseBody
	public List<Map<String,String>> getCitys(@RequestParam Map<String,Object> params,HttpServletRequest request, HttpServletResponse response) {
		String usertoken = getUserToken(request);
		OpUser cuser = getLoginOpUser(request);
		params.put("cuserid", cuser.getId());
		return opShiftRulesService.getCitys(params,usertoken);
	}
	
	@RequestMapping("/OpShiftRules/GetShiftRulesByQuery")
	@ResponseBody
	public PageBean getShiftRulesByQuery(@RequestBody OpShiftRulesQueryParam queryParam, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String usertoken = getUserToken(request);
		return opShiftRulesService.getShiftRulesByQuery(queryParam,usertoken);
	}
	
	
	@RequestMapping("/OpShiftRules/GetShiftRules")
	@ResponseBody
	public OpShiftRules getShiftRules(@RequestParam Map<String,Object> params, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String usertoken = getUserToken(request);
		return opShiftRulesService.getShiftRules(params,usertoken);
	}
	
	@RequestMapping("/OpShiftRules/CreateShiftRules")
	@ResponseBody
	public Map<String,Object> createShiftRules(@RequestBody Map<String,Object> params, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String usertoken = getUserToken(request);
		OpUser opuser = getLoginOpUser(request);
		params.put("creater", opuser.getId());
		params.put("updater", opuser.getId());
		return opShiftRulesService.createShiftRules(params,usertoken);
	}
	
	@RequestMapping("/OpShiftRules/UpdateShiftRules")
	@ResponseBody
	public Map<String,Object> updateShiftRules(@RequestBody Map<String,Object> params, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String usertoken = getUserToken(request);
		OpUser opuser = getLoginOpUser(request);
		params.put("creater", opuser.getId());
		params.put("updater", opuser.getId());
		return opShiftRulesService.updateShiftRules(params,usertoken);
	}
}
