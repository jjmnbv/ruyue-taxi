package com.szyciov.lease.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.entity.coupon.PubCouponRule;
import com.szyciov.lease.entity.User;
import com.szyciov.lease.param.PubCouponRuleQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.PageBean;
import com.szyciov.util.TemplateHelper;

@Controller
public class PubCouponRuleController extends BaseController {
    private static final Logger logger = Logger.getLogger(PubCouponRuleController.class);
	
    private TemplateHelper templateHelper = new TemplateHelper();
    
	@RequestMapping(value = "/PubCouponRule/Index")
	public String getPubCouponRuleIndex() {
		return "resource/pubCouponRule/index";
	}
	
	@RequestMapping(value = "/PubCouponRule/GetPubCouponRuleByQuery")
	@ResponseBody
	public PageBean getPubCouponRuleByQuery(@RequestBody PubCouponRuleQueryParam pubCouponRuleQueryParam,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		pubCouponRuleQueryParam.setLecompanyid(user.getLeasescompanyid());
		
		return templateHelper.dealRequestWithToken("/PubCouponRule/GetPubCouponRuleByQuery", HttpMethod.POST,
				userToken, pubCouponRuleQueryParam, PageBean.class);
	}
	
	@RequestMapping("/PubCouponRule/CreatePubCouponRule")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String, String> createPubCouponRule(@RequestBody PubCouponRule pubCouponRule, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		pubCouponRule.setLecompanyid(user.getLeasescompanyid());
		pubCouponRule.setCreater(user.getId());
		pubCouponRule.setUpdater(user.getId());

		return templateHelper.dealRequestWithToken("/PubCouponRule/CreatePubCouponRule", HttpMethod.POST, userToken, pubCouponRule,
				Map.class);
	}
	
	@RequestMapping("/PubCouponRule/UpdatePubCouponRule")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String, String> updatePubCouponRule(@RequestBody PubCouponRule pubCouponRule, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		pubCouponRule.setLecompanyid(user.getLeasescompanyid());
		pubCouponRule.setUpdater(user.getId());

		return templateHelper.dealRequestWithToken("/PubCouponRule/UpdatePubCouponRule", HttpMethod.POST, userToken, pubCouponRule,
				Map.class);
	}
	
	@RequestMapping(value = "/PubCouponRule/HistoryIndex")
	public ModelAndView getPubCouponRuleHistoryIndex(@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "name", required = true) String name, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("id", id);
		mav.addObject("name", name);
		mav.setViewName("resource/pubCouponRule/historyindex");
		return mav;
	}
	
	@RequestMapping(value = "/PubCouponRule/GetPubCouponRuleHistoryByQuery")
	@ResponseBody
	public PageBean getPubCouponRuleHistoryByQuery(@RequestParam(value = "id", required = true) String id,
			@RequestBody PubCouponRuleQueryParam pubCouponRuleQueryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		pubCouponRuleQueryParam.setId(id);
		
		return templateHelper.dealRequestWithToken("/PubCouponRule/GetPubCouponRuleHistoryByQuery", HttpMethod.POST,
				userToken, pubCouponRuleQueryParam, PageBean.class);
	}
	
	@RequestMapping(value = "/PubCouponRule/GetPubCouponRuleById/{id}")
	@ResponseBody
	public PubCouponRule getPubCouponRuleById(@PathVariable String id, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		return templateHelper.dealRequestWithToken("/PubCouponRule/GetPubCouponRuleById/{id}", HttpMethod.GET,
				userToken, null, PubCouponRule.class, id);
	}
}
