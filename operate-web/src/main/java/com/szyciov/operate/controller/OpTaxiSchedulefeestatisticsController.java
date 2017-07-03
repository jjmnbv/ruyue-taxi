package com.szyciov.operate.controller;

import java.io.File;
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
import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.lease.param.LeDriverorderstatisticsParam;
import com.szyciov.op.entity.OpTaxiSchedulefeestatistics;
import com.szyciov.util.Constants;
import com.szyciov.util.ExcelExport2;
import com.szyciov.util.PageBean;
import com.szyciov.util.TemplateHelper;
@Controller
public class OpTaxiSchedulefeestatisticsController {
	private TemplateHelper templateHelper = new TemplateHelper();
	ModelAndView mav = new ModelAndView();
	@RequestMapping(value = "/OpTaxiSchedulefeestatistics/Index")
	@SuppressWarnings("unchecked")
	public ModelAndView getOpTaxiSchedulefeeIndex(HttpServletRequest request) {
		 String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		 List<PubDictionary> customer = templateHelper.dealRequestWithToken("/OpTaxiSchedulefeestatistics/GetCustomer",
					HttpMethod.POST, userToken,null, List.class);
		mav.addObject("customer", customer);
		mav.setViewName("resource/opTaxiSchedulefeestatistics/index");
		return mav;
	}
	/**
	 * 司机
	 * @param driver
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/OpTaxiSchedulefeestatistics/GetDriver")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getDriver(@RequestParam String driver, 
			@RequestParam String leasesCompanyId,
			HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		LeDriverorderstatisticsParam leDriverorderstatisticsParam = new LeDriverorderstatisticsParam();
//		User user = getLoginLeUser(request);
		leDriverorderstatisticsParam.setName(driver);
		leDriverorderstatisticsParam.setLeasesCompanyId(leasesCompanyId);
		return templateHelper.dealRequestWithToken("/OpTaxiSchedulefeestatistics/GetDriver", HttpMethod.POST, userToken,
				leDriverorderstatisticsParam, List.class);
	}
	/*
	 * 企业调度费用统计
	 */
	@RequestMapping(value = "OpTaxiSchedulefeestatistics/GetDate")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public PageBean getDate(
			@RequestParam(value = "timeType", required = false) String timeType,
			@RequestParam(value = "startTime1", required = false) String startTime1,
			@RequestParam(value = "endTime1", required = false) String endTime1,
			@RequestBody OpTaxiSchedulefeestatistics opTaxiSchedulefeestatistics,
			HttpServletRequest request,HttpServletResponse response) {
		 String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		 String key = opTaxiSchedulefeestatistics.getKey();
		 OpTaxiSchedulefeestatistics theFee = new OpTaxiSchedulefeestatistics();
		 if(key == null){
			 theFee = opTaxiSchedulefeestatistics;
			 theFee.setTimeType(timeType);
			 theFee.setStartTime1(startTime1);
			 theFee.setEndTime1(endTime1);
		 }else{
			 theFee = opTaxiSchedulefeestatistics;
		 }
		 theFee.setFeetype("0");
		 PageBean allData = templateHelper.dealRequestWithToken("/OpTaxiSchedulefeestatistics/GetDate",
					HttpMethod.POST, userToken,theFee, PageBean.class);
		return allData;
	}
	/*
	 * 司机调度费用统计
	 */
	@RequestMapping(value = "OpTaxiSchedulefeestatistics/GetDateDriver")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public PageBean getDateDriver(
			@RequestParam(value = "timeType", required = false) String timeType,
			@RequestParam(value = "startTime1", required = false) String startTime1,
			@RequestParam(value = "endTime1", required = false) String endTime1,
			@RequestBody OpTaxiSchedulefeestatistics opTaxiSchedulefeestatistics,
			HttpServletRequest request,HttpServletResponse response) {
		 String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		 String key = opTaxiSchedulefeestatistics.getKey();
		 OpTaxiSchedulefeestatistics theFee = new OpTaxiSchedulefeestatistics();
		 if(key == null){
			 theFee = opTaxiSchedulefeestatistics;
			 theFee.setTimeType(timeType);
			 theFee.setStartTime1(startTime1);
			 theFee.setEndTime1(endTime1);
		 }else{
			 theFee = opTaxiSchedulefeestatistics;
		 }
		 theFee.setFeetype("0");
		 PageBean allData = templateHelper.dealRequestWithToken("/OpTaxiSchedulefeestatistics/GetDateDriver",
					HttpMethod.POST, userToken,theFee, PageBean.class);
		return allData;
	}
	/**
	 * 企业调度费统计导出
	 */
	@RequestMapping(value = "OpTaxiSchedulefeestatistics/Export")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public void exportData1(
			@RequestParam(value = "timeType", required = false) String timeType,
			@RequestParam(value = "startTime1", required = false) String startTime1,
			@RequestParam(value = "endTime1", required = false) String endTime1,
			@RequestParam(value = "customer", required = false) String customer,
			@RequestParam(value = "customertext", required = false) String customertext,
			@RequestParam(value = "accounttype", required = false) String accounttype,
			@RequestParam(value = "accounttypetext", required = false) String accounttypetext,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		 String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		 OpTaxiSchedulefeestatistics theFee = new OpTaxiSchedulefeestatistics();
			 theFee.setTimeType(timeType);
			 theFee.setStartTime1(startTime1);
			 theFee.setEndTime1(endTime1);
			 theFee.setCustomer(customer);
			 theFee.setStartTime(startTime);
			 theFee.setEndTime(endTime);
			 theFee.setAccounttype(accounttype);
			 theFee.setFeetype("0");
		Map<String, List<Object>> colData = new HashMap<String, List<Object>>();
		List<Object> colData1 = new ArrayList<Object>();
		List<Object> colData2 = new ArrayList<Object>();
		List<Object> colData3 = new ArrayList<Object>();
		List<Object> colData4 = new ArrayList<Object>();
		List<Map> orgOrganExpenses = templateHelper.dealRequestWithToken("/OpTaxiSchedulefeestatistics/Export",
				HttpMethod.POST, userToken, theFee, List.class);
		for (int i = 0; i < orgOrganExpenses.size(); i++) {
			colData1.add((String) orgOrganExpenses.get(i).get("time"));
			colData2.add((String) orgOrganExpenses.get(i).get("customer"));
			colData3.add((String) orgOrganExpenses.get(i).get("orders"));
			colData4.add((String) orgOrganExpenses.get(i).get("money"));
		}
		Excel excel = new Excel();
		// excel文件
		File tempFile = new File("出租车调度费统计-按车企统计.xls");

		List<String> colName = new ArrayList<String>();
		colName.add("时间");
		colName.add("客户名称");
		colName.add("订单数");
		colName.add("金额(元)");
		excel.setColName(colName);
		colData.put("时间", colData1);
		colData.put("客户名称", colData2);
		colData.put("订单数", colData3);
		colData.put("金额(元)", colData4);
		excel.setColData(colData);
		List<String> rowData = new ArrayList<String>();
		LeDriverorderstatisticsController swichTime = new LeDriverorderstatisticsController();
		if(customertext==null || customertext == ""){
			customertext = "全部";
		}
		if(accounttypetext==null || accounttypetext == ""){
			accounttypetext = "全部";
		}
		if(timeType.equals("0")){
		  rowData.add("时间:"+swichTime.switchTime(startTime1)+"-"+swichTime.switchTime(endTime1)+"   "+"客户名称:"+customertext+"    "+"入账状态:"+accounttypetext);
		}else{
		  rowData.add("时间:"+swichTime.switchTime(startTime)+"-"+swichTime.switchTime(endTime)+"   "+"客户名称:"+customertext+"    "+"入账状态:"+accounttypetext);
		}
		excel.setRowData(rowData);
		ExcelExport2 ee = new ExcelExport2(request, response, excel);
		ee.setTitleRowCount(2);
		ee.setSheetMaxRow(5000);
		ee.setSheetName("出租车调度费统计-按车企统计");
		ee.createExcel(tempFile);
	}
	/**
	 * 司机调度费用统计导出
	 * 
	 */
	@RequestMapping(value = "OpTaxiSchedulefeestatistics/Export1")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public void exportData(
			@RequestParam(value = "timeType", required = false) String timeType,
			@RequestParam(value = "startTime1", required = false) String startTime1,
			@RequestParam(value = "endTime1", required = false) String endTime1,
			@RequestParam(value = "customer", required = false) String customer,
			@RequestParam(value = "accounttype", required = false) String accounttype,
			@RequestParam(value = "customertext", required = false) String customertext,
			@RequestParam(value = "accounttypetext", required = false) String accounttypetext,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime,
			@RequestParam(value = "driver", required = false) String driver,
			@RequestParam(value = "drivertext", required = false) String drivertext,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		 String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		 OpTaxiSchedulefeestatistics theFee = new OpTaxiSchedulefeestatistics();
			 theFee.setTimeType(timeType);
			 theFee.setStartTime1(startTime1);
			 theFee.setEndTime1(endTime1);
			 theFee.setCustomer(customer);
			 theFee.setStartTime(startTime);
			 theFee.setEndTime(endTime);
			 theFee.setDriver(driver);
			 theFee.setAccounttype(accounttype);
			 theFee.setFeetype("0");
		Map<String, List<Object>> colData = new HashMap<String, List<Object>>();
		List<Object> colData1 = new ArrayList<Object>();
		List<Object> colData2 = new ArrayList<Object>();
		List<Object> colData3 = new ArrayList<Object>();
		List<Object> colData4 = new ArrayList<Object>();
		List<Object> colData5 = new ArrayList<Object>();
		List<Map> orgOrganExpenses = templateHelper.dealRequestWithToken("/OpTaxiSchedulefeestatistics/Export1",
				HttpMethod.POST, userToken, theFee, List.class);
		for (int i = 0; i < orgOrganExpenses.size(); i++) {
			colData1.add((String) orgOrganExpenses.get(i).get("time"));
			colData2.add((String) orgOrganExpenses.get(i).get("driver"));
			colData3.add((String) orgOrganExpenses.get(i).get("customer"));
			colData4.add((String) orgOrganExpenses.get(i).get("orders"));
			colData5.add((String) orgOrganExpenses.get(i).get("money"));
		}
		Excel excel = new Excel();
		// excel文件
		File tempFile = new File("出租车调度费统计-按司机统计.xls");

		List<String> colName = new ArrayList<String>();
		colName.add("时间");
		colName.add("司机信息");
		colName.add("客户名称");
		colName.add("订单数");
		colName.add("金额(元)");
		excel.setColName(colName);
		colData.put("时间", colData1);
		colData.put("司机信息", colData2);
		colData.put("客户名称", colData3);
		colData.put("订单数", colData4);
		colData.put("金额(元)", colData5);
		excel.setColData(colData);
		List<String> rowData = new ArrayList<String>();
		LeDriverorderstatisticsController swichTime = new LeDriverorderstatisticsController();
		if(customertext==null || customertext == ""){
			customertext = "全部";
		}
		if(drivertext==null || drivertext == ""){
			drivertext = "全部";
		}
		if(accounttypetext==null || accounttypetext == ""){
			accounttypetext = "全部";
		}
		if(timeType.equals("0")){
		  rowData.add("时间:"+swichTime.switchTime(startTime1)+"-"+swichTime.switchTime(endTime1)+"   "+"客户名称:"+customertext+"    "+"司机:"+drivertext+"    "+"入账状态:"+accounttypetext);
		}else{
		  rowData.add("时间:"+swichTime.switchTime(startTime)+"-"+swichTime.switchTime(endTime)+"   "+"客户名称:"+customertext+"    "+"司机:"+drivertext+"    "+"入账状态:"+accounttypetext);
		}
		excel.setRowData(rowData);
		ExcelExport2 ee = new ExcelExport2(request, response, excel);
		ee.setTitleRowCount(2);
		ee.setSheetMaxRow(5000);
		ee.setSheetName("出租车调度费统计-按司机统计");
		ee.createExcel(tempFile);
	}

}
