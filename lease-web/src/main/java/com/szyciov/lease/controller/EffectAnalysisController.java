package com.szyciov.lease.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.lease.entity.User;
import com.szyciov.param.CouponUsageQueryParam;
import com.szyciov.param.UserRechargeQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.PageBean;
import com.szyciov.util.TemplateHelper;

@Controller
@RequestMapping(value="EffectAnalysis")
public class EffectAnalysisController extends BaseController{

	private TemplateHelper templateHelper = new TemplateHelper();
	
	@RequestMapping(value="Index")
	public String Index(HttpServletRequest request,HttpServletResponse response,Model model){
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return "resource/effectAnalysis/index";
	}
	
	@RequestMapping(value="QueryUserRechargePercent")
	@ResponseBody
	public PageBean QueryUserRechargePercent(@RequestBody UserRechargeQueryParam queryParam,HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		queryParam.setLecompanyid(user.getLeasescompanyid());
		//查询注册使用率
		return templateHelper.dealRequestWithToken("/EffectAnalysis/QueryUserRechargePercent", HttpMethod.POST, userToken,
				queryParam, PageBean.class);
	}
	
	@RequestMapping(value="QueryCouponUsageByParam")
	@ResponseBody
	public PageBean QueryCouponUsageByParam(@RequestBody CouponUsageQueryParam queryParam,HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		queryParam.setLecompanyid(user.getLeasescompanyid());
		return templateHelper.dealRequestWithToken("/EffectAnalysis/QueryCouponUsageByParam", HttpMethod.POST, userToken,
				queryParam, PageBean.class);
	}
	
	@RequestMapping(value="GetCouponUsageSendCitys")
	@ResponseBody
	public List<Object> GetCouponUsageSendCitys(@RequestParam(required=false) String city,HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		return templateHelper.dealRequestWithToken("/EffectAnalysis/GetCouponUsageSendCitys?city={city}&lecompanyid={lecompanyid}", HttpMethod.GET, userToken,
				null, List.class,city,user.getLeasescompanyid());
	}
	
}
