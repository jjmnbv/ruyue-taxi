package com.szyciov.carservice.controller;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
 
import com.szyciov.carservice.service.OrderStatisticsService;
import com.szyciov.param.OrderStatisticsQueryParam;
import com.szyciov.util.Constants;

@Controller
public class OrderStatisticsController {
	
	@Resource(name="orderStatisticsService")
	private OrderStatisticsService orderStatisticsService;

	public void setOrderStatisticsService(OrderStatisticsService orderStatisticsService) {
		this.orderStatisticsService = orderStatisticsService;
	}
	
	/**
	 * 租赁端首页统计
	 * @param queryParam
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/api/OrderStatistics/LeIndexOrderStatistics")
	@ResponseBody
	public Map<String, Object> leIndexOrderStatistics(@RequestBody OrderStatisticsQueryParam queryParam, 
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		return orderStatisticsService.leIndexOrderStatistics(queryParam, userToken);
	}
	
	/**
	 * 租赁端机构订单统计
	 * @param queryParam
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/api/OrderStatistics/LeOrgOrderStatistics")
	@ResponseBody
	public Map<String, Object> leOrgOrderStatistics(@RequestBody OrderStatisticsQueryParam queryParam, 
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		return orderStatisticsService.leOrgOrderStatistics(queryParam, userToken);
	}
	
	
	/**
	 * 租赁端个人订单统计
	 * @param queryParam
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/api/OrderStatistics/LePersonalOrderStatistics")
	@ResponseBody
	public Map<String, Object> lePersonalOrderStatistics(@RequestBody OrderStatisticsQueryParam queryParam, 
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		return orderStatisticsService.lePersonalOrderStatistics(queryParam, userToken);
	}
	
	/**
	 * 运管端首页统计
	 * @param queryParam
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/api/OrderStatistics/OpIndexorderstatistics")
	@ResponseBody
	public Map<String, Object> opIndexorderstatistics(@RequestBody OrderStatisticsQueryParam queryParam, 
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		return orderStatisticsService.opIndexorderstatistics(queryParam, userToken);
	}
	/**
	 * 运管端销售统计
	 * @param queryParam
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/api/OrderStatistics/OpOrderstatistics")
	@ResponseBody
	public Map<String, Object> opOrderstatistics(@RequestBody OrderStatisticsQueryParam queryParam, 
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		return orderStatisticsService.opOrderstatistics(queryParam, userToken);
	}
	/**
	 * 机构端报表管理公司
	 * @param queryParam
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/api/OrderStatistics/OrgCompanystatistics")
	@ResponseBody
	public Map<String, Object> orgCompanystatistics(@RequestBody OrderStatisticsQueryParam queryParam, 
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		return orderStatisticsService.orgCompanystatistics(queryParam, userToken);
	}
	/**
	 * 机构端报表管理部门
	 * @param queryParam
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/api/OrderStatistics/OrgDeptstatistics")
	@ResponseBody
	public Map<String, Object> orgDeptstatistics(@RequestBody OrderStatisticsQueryParam queryParam, 
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		return orderStatisticsService.orgDeptstatistics(queryParam, userToken);
	}
	/**
	 * 对所有数据进行统计
	 * @param queryParam
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/api/OrderStatistics/GetAllDatas")
	@ResponseBody
	public void getAllDatas( 
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
	     orderStatisticsService.getAllDatas();
	}

}
