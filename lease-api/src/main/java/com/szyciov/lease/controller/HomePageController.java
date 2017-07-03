package com.szyciov.lease.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.lease.service.HomePageService;
import com.szyciov.util.BaseController;

/**
 * 租赁端首页控制器
 */
@Controller
public class HomePageController extends BaseController {
	private static final Logger logger = Logger.getLogger(HomePageController.class);

	public HomePageService homepageservice;

	@Resource(name = "HomePageService")
	public void setHomePageService(HomePageService homepageservice) {
		this.homepageservice = homepageservice;
	}
	
	@RequestMapping(value = "api/Home/Info/{companyid}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getHomePageInfo(@PathVariable String companyid) {
		return homepageservice.getHomePageInfo(companyid);
	}
}
