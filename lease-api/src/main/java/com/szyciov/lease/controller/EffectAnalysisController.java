package com.szyciov.lease.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.lease.service.EffectAnalysisService;
import com.szyciov.param.CouponUsageQueryParam;
import com.szyciov.param.UserRechargeQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;
@Controller
@RequestMapping(value="api/EffectAnalysis")
public class EffectAnalysisController extends BaseController{

	@Resource(name="effectAnalysisService")
	private EffectAnalysisService service;

	public EffectAnalysisService getService() {
		return service;
	}

	public void setService(EffectAnalysisService service) {
		this.service = service;
	}
	
	@RequestMapping(value="QueryUserRechargePercent")
	@ResponseBody
	public PageBean queryUserRechargePercent(@RequestBody UserRechargeQueryParam queryParam){
		return service.queryUserRechargePercent(queryParam);
	}
	
	@RequestMapping(value="QueryCouponUsageByParam")
	@ResponseBody
	public PageBean QueryCouponUsageByParam(@RequestBody CouponUsageQueryParam queryParam){
		return service.queryCouponUsageByParam(queryParam);
	}
	
	@RequestMapping(value="GetCouponUsageSendCitys")
	@ResponseBody
	public List<Object> GetCouponUsageSendCitys(@RequestParam(required=false) String city,@RequestParam String lecompanyid,HttpServletRequest request,HttpServletResponse response){
		return service.getCouponUsageSendCitys(city,lecompanyid);
	}
}
