package com.szyciov.operate.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.lease.entity.User;
import com.szyciov.param.OrderStatisticsQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper;
@Controller
public class HomePageController extends BaseController{
	private static final Logger logger = Logger.getLogger(HomePageController.class);
	private TemplateHelper templateHelper = new TemplateHelper();
	@RequestMapping(value = "/Home/Index")
	@SuppressWarnings("unchecked")
	public ModelAndView getHomeAllData(HttpServletRequest request, HttpServletResponse response) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		opIndexorderstatistics(userToken);
		Map<String,Object> homeAllData = templateHelper.dealRequestWithToken(
				"/Home/GetHomeAllData", HttpMethod.GET, userToken, null, Map.class);
		return new ModelAndView("resource/home/index", homeAllData);
	}
	/**
	 * 订单数据汇总
	 * @param userToken
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> opIndexorderstatistics(String userToken) {
		OrderStatisticsQueryParam queryParam = new OrderStatisticsQueryParam();
		String carserviceApiUrl = SystemConfig.getSystemProperty("carserviceApiUrl");
		return templateHelper.dealRequestWithFullUrlToken(carserviceApiUrl + "/OrderStatistics/OpIndexorderstatistics", HttpMethod.POST, userToken, 
				queryParam, Map.class);
	}

}
