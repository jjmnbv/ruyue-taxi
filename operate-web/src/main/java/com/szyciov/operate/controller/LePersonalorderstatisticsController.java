package com.szyciov.operate.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.lease.entity.User;
import com.szyciov.lease.param.LePersonalorderstatisticsParam;
import com.szyciov.lease.param.PubAlarmprocessParam;
import com.szyciov.op.entity.OpUser;
import com.szyciov.param.OrderStatisticsQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.ExcelExport;
import com.szyciov.util.PageBean;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper;

@Controller
public class LePersonalorderstatisticsController extends BaseController {
	private TemplateHelper templateHelper = new TemplateHelper();
	ModelAndView mav = new ModelAndView();

	@RequestMapping(value = "/LePersonalorderstatistics/Index")
	@SuppressWarnings("unchecked")
	public ModelAndView getPubVehicleIndex(HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		List<PubDictionary> ordertype = templateHelper.dealRequestWithToken(
				"/LePersonalorderstatistics/Getordertype", HttpMethod.GET, userToken, null,
				List.class);
		mav.addObject("ordertype", ordertype);
		List<PubDictionary> paymentstatus = templateHelper.dealRequestWithToken(
				"/LePersonalorderstatistics/GetPaymentstatus", HttpMethod.GET, userToken, null,
				List.class);
		mav.addObject("paymentstatus", paymentstatus);
		List<PubDictionary> customer = templateHelper.dealRequestWithToken(
				"/LePersonalorderstatistics/GetCustomer", HttpMethod.GET, userToken, null,
				List.class);
		mav.addObject("customer", customer);
		mav.setViewName("resource/lePersonalorderstatistics/index");
		return mav;
	}
	/**
	 * 城市联想输入下拉框
	 */
	@RequestMapping(value = "/LePersonalorderstatistics/GetCityListById")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getPubAlarmprocessDriver(@RequestParam String city, HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		LePersonalorderstatisticsParam lePersonalorderstatisticsParam = new LePersonalorderstatisticsParam();
		OpUser user = getLoginOpUser(request);
		String userid = user.getId();
		String usertype = user.getUsertype();
		if(usertype.equals("1")){
			lePersonalorderstatisticsParam.setId("");
		}else{
			lePersonalorderstatisticsParam.setId(userid);
		}
		lePersonalorderstatisticsParam.setCity(city);
		return templateHelper.dealRequestWithToken("/LePersonalorderstatistics/GetCityListById", HttpMethod.POST, userToken,
				lePersonalorderstatisticsParam, List.class);
	}
	/**
	 * 数据表格统计按时间
	 * @param lePersonalorderstatisticsParam
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "LePersonalorderstatistics/GetPersonalByQuery")
	@ResponseBody
	public PageBean getPersonalByQuery(			@RequestParam(value = "ordertype", required = false) String ordertype,
			@RequestParam(value = "paymentstatus", required = false) String paymentstatus,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "leasescompanyid", required = false) String leasescompanyid,
			@RequestParam(value = "endTime", required = false) String endTime, 
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "key", required = false) String key,
			@RequestBody LePersonalorderstatisticsParam lePersonalorderstatisticsParam1,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json;charset=utf-8");
		LePersonalorderstatisticsParam lePersonalorderstatisticsParam = new LePersonalorderstatisticsParam();
		if(lePersonalorderstatisticsParam1.getKey() == null){
			lePersonalorderstatisticsParam = lePersonalorderstatisticsParam1;
			lePersonalorderstatisticsParam.setOrdertype(ordertype);
			lePersonalorderstatisticsParam.setPaymentstatus(paymentstatus);
			lePersonalorderstatisticsParam.setStartTime(startTime);
			lePersonalorderstatisticsParam.setEndTime(endTime);
			lePersonalorderstatisticsParam.setLeasescompanyid(leasescompanyid);
			lePersonalorderstatisticsParam.setType(type);
		}else{
			lePersonalorderstatisticsParam = lePersonalorderstatisticsParam1;
		}
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/LePersonalorderstatistics/GetPersonalByQuery", HttpMethod.POST,
				userToken, lePersonalorderstatisticsParam, PageBean.class);
	}

	/**
	 * 数据表格统计按城市
	 * @param lePersonalorderstatisticsParam
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "LePersonalorderstatistics/GetPersonalByQuery1")
	@ResponseBody
	public PageBean getPersonalByQuery1(
			@RequestParam(value = "ordertype", required = false) String ordertype,
			@RequestParam(value = "leasescompanyid", required = false) String leasescompanyid,
			@RequestParam(value = "paymentstatus", required = false) String paymentstatus,
			@RequestParam(value = "cityid", required = false) String cityid,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime, 
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "key", required = false) String key,
			@RequestBody LePersonalorderstatisticsParam lePersonalorderstatisticsParam1,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json;charset=utf-8");
		LePersonalorderstatisticsParam lePersonalorderstatisticsParam = new LePersonalorderstatisticsParam();
		if(lePersonalorderstatisticsParam1.getKey() == null){
			lePersonalorderstatisticsParam = lePersonalorderstatisticsParam1;
			lePersonalorderstatisticsParam.setOrdertype(ordertype);
			lePersonalorderstatisticsParam.setCityid(cityid);
			lePersonalorderstatisticsParam.setPaymentstatus(paymentstatus);
			lePersonalorderstatisticsParam.setLeasescompanyid(leasescompanyid);
			lePersonalorderstatisticsParam.setStartTime(startTime);
			lePersonalorderstatisticsParam.setEndTime(endTime);
			lePersonalorderstatisticsParam.setType(type);
		}else{
			lePersonalorderstatisticsParam = lePersonalorderstatisticsParam1;
		}
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/LePersonalorderstatistics/GetPersonalByQuery1", HttpMethod.POST,
				userToken, lePersonalorderstatisticsParam, PageBean.class);
	}

	/**
	 * 订单统计
	 * @param lePersonalorderstatisticsParam
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/LePersonalorderstatistics/PersonalAll")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public LeOrgorderstatisticsParamAll getPersonalAll(
			@RequestBody LePersonalorderstatisticsParam lePersonalorderstatisticsParam, HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		lePersonalOrderStatistics(userToken);
		lePersonalorderstatisticsParam.setType("1");
		List<Map> driverAll = templateHelper.dealRequestWithToken("/LePersonalorderstatistics/PersonalAll",
				HttpMethod.POST, userToken, lePersonalorderstatisticsParam, List.class);
		double sumOrdermoney = 0;
		int sumCarorders = 0;
		int sumPickporders = 0;
		int sumDropofforders = 0;
		int sumAllOrders = 0;
		double sumIncomePrice = 0;
		int sumAlluporders = 0;
		int sumConfirmedorders = 0;
		int sumProcessedorders = 0;
		double sumDiffmoney = 0;
		int sumOrderreview = 0;
		for (int i = 0; i < driverAll.size(); i++) {
			// 订单金额
			String a = (String) driverAll.get(i).get("ordermoney");
			// 业务类型查询
			String m = (String) driverAll.get(i).get("ordertype");
			String b = "";
			String c = "";
			String d = "";
			if (m.equals("1")) {
				// 预约订单
				b = (String) driverAll.get(i).get("ordernum");
			}
			if (m.equals("2")) {
				// 接机订单
				c = (String) driverAll.get(i).get("ordernum");
			}
			if (m.equals("3")) {
				// 送机订单
				d = (String) driverAll.get(i).get("ordernum");
			}
			// 差异订单
			String e = (String) driverAll.get(i).get("difforders");

			// 差异金额
			String f = (String) driverAll.get(i).get("diffmoney");
			// 异常已处理订单
			String g = (String) driverAll.get(i).get("confirmedorders");
			// 异常未处理订单
			String h = (String) driverAll.get(i).get("processedorders");
			// 订单数
			String k = (String) driverAll.get(i).get("ordernum");

			double ordermoney = 0;

			int carorders = 0;
			int pickporders = 0;
			int dropofforders = 0;
			int orderreview = 0;

			double diffmoney = 0;
			int confirmedorders = 0;
			int processedorders = 0;
			int ordernum = 0;
			// 订单金额
			if (a != "" && a != null) {
				ordermoney = Double.parseDouble(a);
			}
			// 约车订单数
			if (b != "" && b != null) {
				carorders = Integer.parseInt(b);
			}
			// 接机订单数
			if (c != "" && c != null) {
				pickporders = Integer.parseInt(c);
			}
			// 送机订单数
			if (d != "" && d != null) {
				dropofforders = Integer.parseInt(d);
			}
			// 差异订单
			if (e != "" && e != null) {
				orderreview = Integer.parseInt(e);
			}
			// 差异金额
			if (f != "" && f != null) {
				diffmoney = Double.parseDouble(f);
			}
			// 异常已处理订单
			if (g != "" && g != null) {
				confirmedorders = Integer.parseInt(g);
			}
			// 异常未处理订单
			if (h != "" && h != null) {
				processedorders = Integer.parseInt(h);
			}
			// 订单数
			if (k != "" && k != null) {
				ordernum = Integer.parseInt(k);
			}
			// 总订单数
			int allOrders = ordernum;
			// 收益金额
			double incomePrice = ordermoney - diffmoney;
			// 异常订单
			int alluporders = confirmedorders + processedorders;

			sumOrdermoney = sumOrdermoney + ordermoney;

			sumCarorders = sumCarorders + carorders;
			sumPickporders = sumPickporders + pickporders;
			sumDropofforders = sumDropofforders + dropofforders;

			sumAllOrders = sumAllOrders + allOrders;

			sumIncomePrice = sumIncomePrice + incomePrice;

			sumAlluporders = sumAlluporders + alluporders;

			sumConfirmedorders = sumConfirmedorders + confirmedorders;

			sumProcessedorders = sumProcessedorders + processedorders;

			sumDiffmoney = sumDiffmoney + diffmoney;

			sumOrderreview = sumOrderreview + orderreview;

		}
		LeOrgorderstatisticsParamAll leFunctionmanagementParamAll = new LeOrgorderstatisticsParamAll();
		leFunctionmanagementParamAll.setSumCarorders(sumCarorders);
		leFunctionmanagementParamAll.setSumDropofforders(sumDropofforders);
		leFunctionmanagementParamAll.setSumPickporders(sumPickporders);
		leFunctionmanagementParamAll.setSumAllOrders(sumAllOrders);
		leFunctionmanagementParamAll.setSumOrderreview(sumOrderreview);

		leFunctionmanagementParamAll.setSumOrdermoney(doubleRetainTow(sumOrdermoney));
		leFunctionmanagementParamAll.setSumIncomePrice(doubleRetainTow(sumIncomePrice));
		leFunctionmanagementParamAll.setSumDiffmoney(doubleRetainTow(sumDiffmoney));

		leFunctionmanagementParamAll.setSumAlluporders(sumAlluporders);
		leFunctionmanagementParamAll.setSumConfirmedorders(sumConfirmedorders);
		leFunctionmanagementParamAll.setSumProcessedorders(sumProcessedorders);
		return leFunctionmanagementParamAll;
	}
	public double doubleRetainTow(double ok){
		 BigDecimal bg = new BigDecimal(ok);  
	     double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
     return f1;
	}


	/**
	 * 导出
	 * @param ordertype
	 * @param paymethod
	 * @param startTime
	 * @param endTime
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "LePersonalorderstatistics/ExportPersonal2")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public void exportData(@RequestParam(value = "ordertype", required = false) String ordertype,
			@RequestParam(value = "paymentstatus", required = false) String paymentstatus,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "leasescompanyid", required = false) String leasescompanyid,
			@RequestParam(value = "endTime", required = false) String endTime, 
			@RequestParam(value = "type", required = false) String type,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		Map<String, List<Object>> colData = new HashMap<String, List<Object>>();
		List<Object> colData1 = new ArrayList<Object>();
		List<Object> colData2 = new ArrayList<Object>();
		List<Object> colData3 = new ArrayList<Object>();
		List<Object> colData4 = new ArrayList<Object>();
		List<Object> colData5 = new ArrayList<Object>();
		LePersonalorderstatisticsParam queryParam = new LePersonalorderstatisticsParam();
		queryParam.setLeasescompanyid(leasescompanyid);
		queryParam.setOrdertype(ordertype);
		queryParam.setPaymentstatus(paymentstatus);
		queryParam.setPaymethod(paymentstatus);
		queryParam.setStartTime(startTime);
		queryParam.setEndTime(endTime);
		queryParam.setType(type);
		List<Map> orgOrganExpenses = templateHelper.dealRequestWithToken("/LePersonalorderstatistics/PersonalAll1",
				HttpMethod.POST, userToken, queryParam, List.class);
		for (int i = 0; i < orgOrganExpenses.size(); i++) {
			colData1.add((String) orgOrganExpenses.get(i).get("time"));
			colData2.add((String) orgOrganExpenses.get(i).get("ordernum"));
			colData3.add((String) orgOrganExpenses.get(i).get("ordermoney"));
			colData4.add((String) orgOrganExpenses.get(i).get("alluporders"));
			colData5.add((String) orgOrganExpenses.get(i).get("diffmoney"));
		}
		Excel excel = new Excel();
		// excel文件
		File tempFile = new File("个人订单月度统计.xls");

		List<String> colName = new ArrayList<String>();
		colName.add("时间");
		colName.add("订单数");
		colName.add("订单额(元)");
		colName.add("异常订单");
		colName.add("异常金额(元)");
		excel.setColName(colName);
		colData.put("时间", colData1);
		colData.put("订单数", colData2);
		colData.put("订单额(元)", colData3);
		colData.put("异常订单", colData4);
		colData.put("异常金额(元)", colData5);
		excel.setColData(colData);

		ExcelExport ee = new ExcelExport(request, response, excel);
		ee.setSheetMaxRow(5000);
		ee.createExcel(tempFile);
	}

	/**
	 * 导出
	 * @param ordertype
	 * @param paymethod
	 * @param startTime
	 * @param endTime
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "LePersonalorderstatistics/ExportPersonal3")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public void exportData1(@RequestParam(value = "ordertype", required = false) String ordertype,
			@RequestParam(value = "leasescompanyid", required = false) String leasescompanyid,
			@RequestParam(value = "paymentstatus", required = false) String paymentstatus,
			@RequestParam(value = "cityid", required = false) String cityid,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime, 
			@RequestParam(value = "type", required = false) String type,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		Map<String, List<Object>> colData = new HashMap<String, List<Object>>();
		List<Object> colData1 = new ArrayList<Object>();
		List<Object> colData2 = new ArrayList<Object>();
		List<Object> colData3 = new ArrayList<Object>();
		LePersonalorderstatisticsParam queryParam = new LePersonalorderstatisticsParam();
		queryParam.setLeasescompanyid(leasescompanyid);
		queryParam.setOrdertype(ordertype);
		queryParam.setPaymentstatus(paymentstatus);
		queryParam.setCityid(cityid);
		queryParam.setStartTime(startTime);
		queryParam.setEndTime(endTime);
		queryParam.setType(type);
		List<Map> orgOrganExpenses = templateHelper.dealRequestWithToken("/LePersonalorderstatistics/PersonalAll2",
				HttpMethod.POST, userToken, queryParam, List.class);
		for (int i = 0; i < orgOrganExpenses.size(); i++) {
			colData1.add((String) orgOrganExpenses.get(i).get("city"));
			colData2.add((String) orgOrganExpenses.get(i).get("ordernum"));
			colData3.add((String) orgOrganExpenses.get(i).get("ordermoney"));
		}
		Excel excel = new Excel();
		// excel文件
		File tempFile = new File("个人订单城市统计.xls");

		List<String> colName = new ArrayList<String>();
		colName.add("城市");
		colName.add("订单数");
		colName.add("订单额(元)");
		excel.setColName(colName);
		colData.put("城市", colData1);
		colData.put("订单数", colData2);
		colData.put("订单额(元)", colData3);
		excel.setColData(colData);

		ExcelExport ee = new ExcelExport(request, response, excel);
		ee.setSheetMaxRow(5000);
		ee.createExcel(tempFile);
	}
	/**
	 * 订单数据汇总
	 * @param userToken
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> lePersonalOrderStatistics(String userToken) {
		OrderStatisticsQueryParam queryParam = new OrderStatisticsQueryParam();
		String carserviceApiUrl = SystemConfig.getSystemProperty("carserviceApiUrl");
		return templateHelper.dealRequestWithFullUrlToken(carserviceApiUrl + "/OrderStatistics/LePersonalOrderStatistics", HttpMethod.POST, userToken, 
				queryParam, Map.class);
	}
}
