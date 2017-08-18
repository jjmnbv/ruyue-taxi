package com.szyciov.operate.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.szyciov.lease.entity.PubCityAddr;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.operate.service.PubInfoService;
import com.szyciov.util.BaseController;

import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

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
	/**
	 * 城市控件2
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/PubInfoApi/GetCitySelect2")
	@ResponseBody
	public JSONObject getCitySelect2(HttpServletRequest request) {
		String userToken = getUserToken(request);
		return pubInfoService.getCitySelect2(userToken);
	}
	/**
	 * 城市查询控件
	 */
	@RequestMapping(value = "/PubInfoApi/GetSearchCitySelect")
	@ResponseBody
	public List<Map<String,String>> getSearchCitySelect(HttpServletRequest request, @RequestParam String cityName,HttpServletResponse response) {
		String userToken = getUserToken(request);
		response.setContentType("application/json; charset=utf-8");
		PubCityAddr pubCityAddr=new PubCityAddr();
		pubCityAddr.setCity(cityName);
		return pubInfoService.getSearchCitySelect(userToken,pubCityAddr);
	}
	
}
