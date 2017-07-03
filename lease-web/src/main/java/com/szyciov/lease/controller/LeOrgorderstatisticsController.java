package com.szyciov.lease.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.entity.Excel;
import com.szyciov.lease.entity.LeOrgorderstatisticsParamAll;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.entity.User;
import com.szyciov.lease.param.OrgOrganQueryParam;
import com.szyciov.param.OrderStatisticsQueryParam;
import com.szyciov.lease.param.LeOrgorderstatisticsParam;
import com.szyciov.lease.param.LePersonalorderstatisticsParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.ExcelExport;
import com.szyciov.util.PageBean;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper;

@Controller
public class LeOrgorderstatisticsController extends BaseController {
	private static final Logger logger = Logger.getLogger(LeOrgorderstatisticsController.class);
	private TemplateHelper templateHelper = new TemplateHelper();
	ModelAndView mav = new ModelAndView();

	@RequestMapping(value = "/LeOrgorderstatistics/Index")
	@SuppressWarnings("unchecked")
	public ModelAndView getPubVehicleIndex(HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		String leasesCompanyId = user.getLeasescompanyid();
		List<PubCityAddr> pubCityAddr = templateHelper.dealRequestWithToken(
				"/LeOrgorderstatistics/GetCityListById/{leasesCompanyId}", HttpMethod.GET, userToken, null, List.class,
				leasesCompanyId);
		mav.addObject("pubCityAddr", pubCityAddr);
		mav.setViewName("resource/leOrgorderstatistics/index");
		return mav;
	}

	/**
	 * 机构支付统计
	 * @param leFunctionmanagementParam
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "LeOrgorderstatistics/GetOragnCountByQuery")
	@ResponseBody
	public PageBean getOragnCountByQuery(
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime,
			@RequestBody LeOrgorderstatisticsParam leFunctionmanagementParam1,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		LeOrgorderstatisticsParam leFunctionmanagementParam = new LeOrgorderstatisticsParam();
		if(leFunctionmanagementParam1.getKey() == null){
			leFunctionmanagementParam = leFunctionmanagementParam1;
			leFunctionmanagementParam.setStartTime(startTime);
			leFunctionmanagementParam.setEndTime(endTime);
		}else{
			leFunctionmanagementParam = leFunctionmanagementParam1;
		}
		leFunctionmanagementParam.setLeasesCompanyId(user.getLeasescompanyid());
		return templateHelper.dealRequestWithToken("/LeOrgorderstatistics/OrganCountParam", HttpMethod.POST, userToken,
				leFunctionmanagementParam, PageBean.class);
	}

	/**
	 * 机构支付导出
	 * @param organId
	 * @param cityid
	 * @param startTime
	 * @param endTime
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "LeOrgorderstatistics/ExportOrgan")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public void exportData(@RequestParam(value = "organId", required = false) String organId,
			@RequestParam(value = "cityid", required = false) String cityid,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		Map<String, List<Object>> colData = new HashMap<String, List<Object>>();
		List<Object> colData1 = new ArrayList<Object>();
		List<Object> colData2 = new ArrayList<Object>();
		List<Object> colData3 = new ArrayList<Object>();
		List<Object> colData4 = new ArrayList<Object>();
		List<Object> colData5 = new ArrayList<Object>();
		List<Object> colData6 = new ArrayList<Object>();
		List<Object> colData7 = new ArrayList<Object>();
		List<Object> colData8 = new ArrayList<Object>();
		LeOrgorderstatisticsParam queryParam = new LeOrgorderstatisticsParam();
		User user = getLoginLeUser(request);
		queryParam.setLeasesCompanyId(user.getLeasescompanyid());
		queryParam.setOrganId(organId);
		queryParam.setCityid(cityid);
		queryParam.setStartTime(startTime);
		queryParam.setEndTime(endTime);
		List<Map> orgOrganExpenses = templateHelper.dealRequestWithToken("/LeOrgorderstatistics/OrganCountAll1",
				HttpMethod.POST, userToken, queryParam, List.class);
		for (int i = 0; i < orgOrganExpenses.size(); i++) {
			colData1.add((String) orgOrganExpenses.get(i).get("shortname"));
			colData2.add((String) orgOrganExpenses.get(i).get("ordermoney"));
			// 约车订单数
			int carorders = Integer.parseInt((String) orgOrganExpenses.get(i).get("carorders"));
			// 接机订单数
			int pickporders = Integer.parseInt((String) orgOrganExpenses.get(i).get("pickuporders"));
			// 送机订单数
			int dropofforders = Integer.parseInt((String) orgOrganExpenses.get(i).get("dropofforders"));
			colData3.add(String.valueOf(carorders + pickporders + dropofforders));
			colData4.add(String.valueOf(orgOrganExpenses.get(i).get("carorders")));
			colData5.add((String) orgOrganExpenses.get(i).get("pickuporders"));
			colData6.add((String) orgOrganExpenses.get(i).get("dropofforders"));
//			// 异常已处理订单数
//			int confirmedorders = Integer.parseInt((String) orgOrganExpenses.get(i).get("confirmedorders"));
//			// 异常未处理订单数
//			int processedorders = Integer.parseInt((String) orgOrganExpenses.get(i).get("processedorders"));
			colData7.add((String) orgOrganExpenses.get(i).get("alluporders"));
			colData8.add((String) orgOrganExpenses.get(i).get("ordermoney"));
		}
		Excel excel = new Excel();
		// excel文件
		File tempFile = new File("机构支付统计.xls");

		List<String> colName = new ArrayList<String>();
		colName.add("机构");
		colName.add("总金额(元)");
		colName.add("总订单数");
		colName.add("预约订单");
		colName.add("接机订单");
		colName.add("送机订单");
		colName.add("异常订单");
		colName.add("差异金额(元)");
		excel.setColName(colName);
		colData.put("机构", colData1);
		colData.put("总金额(元)", colData2);
		colData.put("总订单数", colData3);
		colData.put("预约订单", colData4);
		colData.put("接机订单", colData5);
		colData.put("送机订单", colData6);
		colData.put("异常订单", colData7);
		colData.put("差异金额(元)", colData8);
		excel.setColData(colData);

		ExcelExport ee = new ExcelExport(request, response, excel);
		ee.setSheetMaxRow(5000);
		ee.createExcel(tempFile);
	}

	/**
	 * 城市支付统计
	 * @param leFunctionmanagementParam
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "LeOrgorderstatistics/GetCityCountByQuery")
	@ResponseBody
	public PageBean getCityCountByQuery(
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime,
			@RequestBody LeOrgorderstatisticsParam leFunctionmanagementParam1,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		LeOrgorderstatisticsParam leFunctionmanagementParam = new LeOrgorderstatisticsParam();
		if(leFunctionmanagementParam1.key == null){
			leFunctionmanagementParam = leFunctionmanagementParam1;
			leFunctionmanagementParam.setStartTime(startTime);
			leFunctionmanagementParam.setEndTime(endTime);
		}else{
			leFunctionmanagementParam = leFunctionmanagementParam1;
		}
		leFunctionmanagementParam.setLeasesCompanyId(user.getLeasescompanyid());
		return templateHelper.dealRequestWithToken("/LeOrgorderstatistics/OrganCountCityParam", HttpMethod.POST,
				userToken, leFunctionmanagementParam, PageBean.class);
	}

	/**
	 * 城市支付导出
	 * @param organId
	 * @param cityid
	 * @param startTime
	 * @param endTime
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "LeOrgorderstatistics/ExportCity")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public void exportData1(@RequestParam(value = "organId", required = true) String organId,
			@RequestParam(value = "cityid", required = false) String cityid,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		Map<String, List<Object>> colData = new HashMap<String, List<Object>>();
		List<Object> colData1 = new ArrayList<Object>();
		List<Object> colData2 = new ArrayList<Object>();
		List<Object> colData3 = new ArrayList<Object>();
		List<Object> colData4 = new ArrayList<Object>();
		List<Object> colData5 = new ArrayList<Object>();
		List<Object> colData6 = new ArrayList<Object>();
		List<Object> colData7 = new ArrayList<Object>();
		List<Object> colData8 = new ArrayList<Object>();
		LeOrgorderstatisticsParam queryParam = new LeOrgorderstatisticsParam();
		User user = getLoginLeUser(request);
		queryParam.setLeasesCompanyId(user.getLeasescompanyid());
		queryParam.setOrganId(organId);
		queryParam.setCityid(cityid);
		queryParam.setStartTime(startTime);
		queryParam.setEndTime(endTime);
		List<Map> orgOrganExpenses = templateHelper.dealRequestWithToken("/LeOrgorderstatistics/OrganCityCountAll",
				HttpMethod.POST, userToken, queryParam, List.class);
		for (int i = 0; i < orgOrganExpenses.size(); i++) {
			colData1.add((String) orgOrganExpenses.get(i).get("city"));
			colData2.add((String) orgOrganExpenses.get(i).get("ordermoney"));
			// 约车订单数
			int carorders = Integer.parseInt((String) orgOrganExpenses.get(i).get("carorders"));
			// 接机订单数
			int pickporders = Integer.parseInt((String) orgOrganExpenses.get(i).get("pickuporders"));
			// 送机订单数
			int dropofforders = Integer.parseInt((String) orgOrganExpenses.get(i).get("dropofforders"));
			colData3.add(String.valueOf(carorders + pickporders + dropofforders));
			colData4.add(String.valueOf(orgOrganExpenses.get(i).get("carorders")));
			colData5.add((String) orgOrganExpenses.get(i).get("pickuporders"));
			colData6.add((String) orgOrganExpenses.get(i).get("dropofforders"));
//			// 异常已处理订单数
//			int confirmedorders = Integer.parseInt((String) orgOrganExpenses.get(i).get("confirmedorders"));
//			// 异常未处理订单数
//			int processedorders = Integer.parseInt((String) orgOrganExpenses.get(i).get("processedorders"));
			colData7.add((String) orgOrganExpenses.get(i).get("alluporders"));
			colData8.add((String) orgOrganExpenses.get(i).get("ordermoney"));
		}
		Excel excel = new Excel();
		// excel文件
		File tempFile = new File("城市支付统计.xls");

		List<String> colName = new ArrayList<String>();
		colName.add("城市");
		colName.add("总金额(元)");
		colName.add("总订单数");
		colName.add("预约订单");
		colName.add("接机订单");
		colName.add("送机订单");
		colName.add("异常订单");
		colName.add("差异金额(元)");
		excel.setColName(colName);
		colData.put("城市", colData1);
		colData.put("总金额(元)", colData2);
		colData.put("总订单数", colData3);
		colData.put("预约订单", colData4);
		colData.put("接机订单", colData5);
		colData.put("送机订单", colData6);
		colData.put("异常订单", colData7);
		colData.put("差异金额(元)", colData8);
		excel.setColData(colData);

		ExcelExport ee = new ExcelExport(request, response, excel);
		ee.setSheetMaxRow(5000);
		ee.createExcel(tempFile);
	}

	/**
	 * 订单查询里面的数据
	 * @param leFunctionmanagementParam
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "LeOrgorderstatistics/OrganCountAll")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public LeOrgorderstatisticsParamAll organCountAll(@RequestBody LeOrgorderstatisticsParam leFunctionmanagementParam,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		leFunctionmanagementParam.setLeasesCompanyId(user.getLeasescompanyid());
		leOrgOrderStatistics(userToken);
		List<Map> organCountAll = templateHelper.dealRequestWithToken("/LeOrgorderstatistics/OrganCountAll",
				HttpMethod.POST, userToken, leFunctionmanagementParam, List.class);
		double sumOrdermoney = 0;
		int sumCarorders = 0;
		int sumPickporders = 0;
		int sumDropofforders = 0;
		int sumConfirmedorders = 0;
		int sumPickuporders = 0;
		int sumAlluporders = 0;
		int sumAllOrders = 0;
		for (int i = 0; i < organCountAll.size(); i++) {
			// 订单金额
			double ordermoney = Double.parseDouble((String) organCountAll.get(i).get("ordermoney"));
			// 约车订单数
			int carorders = Integer.parseInt((String) organCountAll.get(i).get("carorders"));
			// 接机订单数
			int pickporders = Integer.parseInt((String) organCountAll.get(i).get("pickuporders"));
			// 送机订单数
			int dropofforders = Integer.parseInt((String) organCountAll.get(i).get("dropofforders"));
			// 异常已处理订单数
			int confirmedorders = Integer.parseInt((String) organCountAll.get(i).get("confirmedorders"));
			// 异常未处理订单数
			int processedorders = Integer.parseInt((String) organCountAll.get(i).get("processedorders"));
			// 总异常数
			int alluporders = confirmedorders + processedorders;
			// 总订单数
			int allOrders = carorders + pickporders + dropofforders;
			sumOrdermoney = sumOrdermoney + ordermoney;
			sumCarorders = sumCarorders + carorders;
			sumPickporders = sumPickporders + pickporders;
			sumDropofforders = sumDropofforders + dropofforders;
			sumConfirmedorders = sumConfirmedorders + confirmedorders;
			sumPickuporders = sumPickuporders + processedorders;
			sumAlluporders = sumAlluporders + alluporders;
			sumAllOrders = sumAllOrders + allOrders;
		}
		LeOrgorderstatisticsParamAll leFunctionmanagementParamAll = new LeOrgorderstatisticsParamAll();
		leFunctionmanagementParamAll.setSumAlluporders(sumAlluporders);
		leFunctionmanagementParamAll.setSumCarorders(sumCarorders);
		leFunctionmanagementParamAll.setSumConfirmedorders(sumConfirmedorders);
		leFunctionmanagementParamAll.setSumDropofforders(sumDropofforders);
		leFunctionmanagementParamAll.setSumOrdermoney(doubleRetainTow(sumOrdermoney));
		leFunctionmanagementParamAll.setSumPickporders(sumPickporders);
		leFunctionmanagementParamAll.setSumPickuporders(sumPickuporders);
		leFunctionmanagementParamAll.setSumAllOrders(sumAllOrders);
		return leFunctionmanagementParamAll;

	}
	public double doubleRetainTow(double ok){
		 BigDecimal bg = new BigDecimal(ok);  
	     double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    return f1;
	}

	/**
	 * 城市查询
	 * @param queryCity
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/LeOrgorderstatistics/GetOrganCity")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getOrganCity(@RequestParam String queryCity, HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OrgOrganQueryParam orgOrganQueryParam = new OrgOrganQueryParam();
		User user = getLoginLeUser(request);
		orgOrganQueryParam.setLeasesCompanyId(user.getLeasescompanyid());
		orgOrganQueryParam.setQueryCity(queryCity);
		return templateHelper.dealRequestWithToken("/LeOrgorderstatistics/GetOrganCity", HttpMethod.POST, userToken,
				orgOrganQueryParam, List.class);
	}

	/**
	 * 机构查询
	 * @param queryShortName
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/LeOrgorderstatistics/GetOrganShortName")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getOrganShortName(@RequestParam String queryShortName,
			HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OrgOrganQueryParam orgOrganQueryParam = new OrgOrganQueryParam();
		User user = getLoginLeUser(request);
		orgOrganQueryParam.setLeasesCompanyId(user.getLeasescompanyid());
		orgOrganQueryParam.setQueryShortName(queryShortName);
		;
		;
		return templateHelper.dealRequestWithToken("/LeOrgorderstatistics/GetOrganShortName", HttpMethod.POST,
				userToken, orgOrganQueryParam, List.class);
	}
	
	/**
	 * 订单数据汇总
	 * @param userToken
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> leOrgOrderStatistics(String userToken) {
		OrderStatisticsQueryParam queryParam = new OrderStatisticsQueryParam();
		String carserviceApiUrl = SystemConfig.getSystemProperty("carserviceApiUrl");
		return templateHelper.dealRequestWithFullUrlToken(carserviceApiUrl + "/OrderStatistics/LeOrgOrderStatistics", HttpMethod.POST, userToken, 
				queryParam, Map.class);
	}
}
