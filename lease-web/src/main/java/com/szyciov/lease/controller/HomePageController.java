package com.szyciov.lease.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.lease.service.HomePageService;
import com.szyciov.util.BaseController;

@Controller
public class HomePageController extends BaseController {
	private static final Logger logger = Logger.getLogger(HomePageController.class);

	public HomePageService homepageservice;
	
	@Resource(name = "HomePageService")
	public void setHomePageService(HomePageService homepageservice) {
		this.homepageservice = homepageservice;
	}

	@RequestMapping(value = "/Home/Index")
	public ModelAndView getDictionaryManagementIndex(HttpServletRequest request, HttpServletResponse response) {
		String userToken = getUserToken(request);
		String companyid = getLoginLeUser(request).getLeasescompanyid();
		Map<String,Object> extrainfo = homepageservice.getHomeInfo(companyid,userToken);
		return new ModelAndView("resource/home/index", extrainfo);
	}
	
}


