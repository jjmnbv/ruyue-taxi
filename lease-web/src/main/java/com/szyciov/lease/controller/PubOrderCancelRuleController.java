package com.szyciov.lease.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.dto.pubOrderCancelRule.PubOrderCancelRule;
import com.szyciov.dto.pubOrderCancelRule.PubOrderCancelRuleHistory;
import com.szyciov.dto.pubPremiumRule.PubPremiumParam;
import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.lease.entity.User;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.PageBean;
import com.szyciov.util.TemplateHelper;
@Controller
public class PubOrderCancelRuleController extends BaseController{
	private TemplateHelper templateHelper = new TemplateHelper();
	ModelAndView mav = new ModelAndView();

	@RequestMapping(value = "/PubOrderCancelRule/Index")
	@SuppressWarnings("unchecked")
	public ModelAndView getPubPremiumRuleIndex(HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		List<PubDictionary> list = templateHelper.dealRequestWithToken(
				"/PubOrderCancelRule/getCity", HttpMethod.GET, userToken, null,
				List.class);
		mav.addObject("list", list);
		mav.setViewName("resource/pubOrderCancelRule/index");
		return mav;
	}
	@RequestMapping(value = "PubOrderCancelRule/GetPubOrderCancelRule")
	@ResponseBody
    public PageBean getPubPremiumRule(
		    @RequestBody PubOrderCancelRule pubOrderCancelRule,
		    HttpServletRequest request, HttpServletResponse response) throws IOException {
    	response.setContentType("application/json;charset=utf-8");
    	User user = getLoginLeUser(request);
    	pubOrderCancelRule.setLeasescompanyid(user.getLeasescompanyid());
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
    	return templateHelper.dealRequestWithToken("/PubOrderCancelRule/GetPubOrderCancelRule",
				HttpMethod.POST, userToken,pubOrderCancelRule,PageBean.class);
    }
	//新增,修改
	@RequestMapping("/PubOrderCancelRule/PubOrderCancelRuleAdd")
	@ResponseBody
	public Map<String, String> pubPremiumAdd(@RequestBody PubOrderCancelRule pubOrderCancelRule,
			HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		pubOrderCancelRule.setCreater(user.getId());
		pubOrderCancelRule.setLeasescompanyid(user.getLeasescompanyid());
		pubOrderCancelRule.setUpdater(user.getId());
		return templateHelper.dealRequestWithToken("/PubOrderCancelRule/PubOrderCancelRuleAdd", HttpMethod.POST, userToken,
				pubOrderCancelRule,Map.class);
	}
	/**
	 * 禁用规则
	 */
	@RequestMapping(value = "/PubOrderCancelRule/ruleConflictOk")
	@ResponseBody
	public int ruleConflictOk( @RequestBody PubOrderCancelRule pubOrderCancelRule, HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		pubOrderCancelRule.setCreater(user.getId());
		return templateHelper.dealRequestWithToken("/PubOrderCancelRule/ruleConflictOk", HttpMethod.POST, userToken,
				pubOrderCancelRule, int.class);
	}
	/**
	 * 启用规则
	 */
	@RequestMapping(value = "/PubOrderCancelRule/ruleConflict")
	@ResponseBody
	public int ruleConflict( @RequestBody PubOrderCancelRule pubOrderCancelRule, HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		pubOrderCancelRule.setCreater(user.getId());
		return templateHelper.dealRequestWithToken("/PubOrderCancelRule/ruleConflict", HttpMethod.POST, userToken,
				pubOrderCancelRule, int.class);
	}
	//转跳历史记录
	@RequestMapping("PubOrderCancelRule/HistoryIndex")
	@ResponseBody
public ModelAndView historyIndex(HttpServletRequest request,HttpServletResponse response) throws IOException {
		   ModelAndView mav = new ModelAndView();
		    mav.addObject("aaid",request.getParameter("id"));
		    String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		    PubOrderCancelRule pubOrderCancelRule = templateHelper.dealRequestWithToken(
					"/PubOrderCancelRule/getRulename/{aaid}", HttpMethod.GET, userToken, null,
					PubOrderCancelRule.class,request.getParameter("id"));
		    mav.addObject("pubOrderCancelRule", pubOrderCancelRule);
		    mav.setViewName("resource/pubOrderCancelRule/history");
		return mav;
	}
	@RequestMapping(value = "PubOrderCancelRule/GetHistoryData")
	@ResponseBody
    public PageBean getHistoryData(
		    @RequestBody PubOrderCancelRuleHistory history,
		    @RequestParam (value = "aaid", required = false) String aaid,
		    HttpServletRequest request, HttpServletResponse response) throws IOException {
    	response.setContentType("application/json;charset=utf-8");
    	history.setId(aaid);
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
    	return templateHelper.dealRequestWithToken("/PubOrderCancelRule/GetHistoryData",
				HttpMethod.POST, userToken,history,PageBean.class);
    }

}
