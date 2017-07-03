package com.szyciov.operate.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.operate.service.HomePageService;
/**
 * 运管端首页
 * @author Administrator
 *
 */
@Controller
public class HomePageController {
	private static final Logger logger = Logger.getLogger(HomePageController.class);

	public HomePageService homepageservice;

	@Resource(name = "HomePageService")
	public void setHomePageService(HomePageService homepageservice) {
		this.homepageservice = homepageservice;
	}
	
	@RequestMapping(value = "api/Home/GetHomeAllData", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getHomeAllData() {
		return homepageservice.getHomeAllData();
	}

}
