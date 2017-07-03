package com.szyciov.lease.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.lease.entity.User;
import com.szyciov.param.OrderStatisticsQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper;
@Controller
public class LeToCorderstatisticsController extends BaseController{
	private static final Logger logger = Logger.getLogger(LeToCorderstatisticsController.class);
	private TemplateHelper templateHelper = new TemplateHelper();
	ModelAndView mav = new ModelAndView();

	@RequestMapping(value = "/leToCorderstatistics/Index")
	@SuppressWarnings("unchecked")
	public ModelAndView getToCIndex(HttpServletRequest request) {
		User user = getLoginLeUser(request);
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		String leasesCompanyId = user.getLeasescompanyid();
		List<PubDictionary> ordertype = templateHelper.dealRequestWithToken(
				"/LePersonalorderstatistics/Getordertype/{leasesCompanyId}", HttpMethod.GET, userToken, null,
				List.class, leasesCompanyId);
		mav.addObject("ordertype", ordertype);
		List<PubCityAddr> pubCityAddr = templateHelper.dealRequestWithToken(
				"/LeOrgorderstatistics/GetCityListById/{leasesCompanyId}", HttpMethod.GET, userToken, null, List.class,
				leasesCompanyId);
		mav.addObject("pubCityAddr", pubCityAddr);
		mav.setViewName("resource/leToCorderstatistics/index");
		return mav;
	}
}
