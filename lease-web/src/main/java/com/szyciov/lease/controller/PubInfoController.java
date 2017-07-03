package com.szyciov.lease.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.lease.service.PubInfoService;
import com.szyciov.util.BaseController;

import net.sf.json.JSONObject;

@Controller
public class PubInfoController extends BaseController {
	
	private PubInfoService pubInfoService;
	@Resource(name = "PubInfoService")
	public void setPubInfoService(PubInfoService pubInfoService) {
		this.pubInfoService = pubInfoService;
	}
	
	/**
	 * 城市控件1
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/PubInfoApi/GetCitySelect1")
	@ResponseBody
	public JSONObject getCitySelect1(HttpServletRequest request) {
		String userToken = getUserToken(request);
		return pubInfoService.getCitySelect1(userToken);
	}
	
}
