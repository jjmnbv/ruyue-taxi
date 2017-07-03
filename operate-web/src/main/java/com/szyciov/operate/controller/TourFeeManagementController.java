package com.szyciov.operate.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.entity.Excel;
import com.szyciov.op.param.TourFeeManagementQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.ExcelExport;
import com.szyciov.util.PageBean;
import com.szyciov.util.TemplateHelper;

@Controller
public class TourFeeManagementController extends BaseController {
    private static final Logger logger = Logger.getLogger(TourFeeManagementController.class);
	
    private TemplateHelper templateHelper = new TemplateHelper();
	
	@RequestMapping(value = "/TourFeeManagement/Index")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public ModelAndView getTourFeeManagementIndex(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		ModelAndView view = new ModelAndView();

		//所属企业
		List<Map<String, Object>> companyidList = templateHelper.dealRequestWithToken("/TourFeeManagement/GetCompanyNameById",
				HttpMethod.GET, userToken, null, List.class);

		view.addObject("companyidList", companyidList);
		view.setViewName("resource/tourFeeManagement/index");
		return view;
	}
	
	@RequestMapping("/TourFeeManagement/GetTourFeeByQuery")
	@ResponseBody
	public PageBean getTourFeeByQuery(@RequestBody TourFeeManagementQueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		if (StringUtils.isNotBlank(queryParam.getStarttime())) {
			queryParam.setStarttime(queryParam.getStarttime().replace("/", "-"));
		}
		
		if (StringUtils.isNotBlank(queryParam.getEndtime())) {
			queryParam.setEndtime(queryParam.getEndtime().replace("/", "-"));
		}
		
		return templateHelper.dealRequestWithToken("/TourFeeManagement/GetTourFeeByQuery", HttpMethod.POST, userToken,
				queryParam,PageBean.class);
	}
	
	@RequestMapping("/TourFeeManagement/GetOrderNo")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getOrderNo(@RequestParam(value = "orderno", required = false) String orderno,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		return templateHelper.dealRequestWithToken("/TourFeeManagement/GetOrderNo?orderno={orderno}",
				HttpMethod.GET, userToken, null, List.class, orderno);
	}
	
	@RequestMapping("/TourFeeManagement/GetDriverByNameOrPhone")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getDriverByNameOrPhone(@RequestParam(value = "driver", required = false) String driver,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		return templateHelper.dealRequestWithToken("/TourFeeManagement/GetDriverByNameOrPhone?driver={driver}",
				HttpMethod.GET, userToken, null, List.class, driver);
	}
	
	@RequestMapping("/TourFeeManagement/GetJobnumByJobnum")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getJobnumByJobnum(@RequestParam(value = "jobnum", required = false) String jobnum,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		return templateHelper.dealRequestWithToken("/TourFeeManagement/GetJobnumByJobnum?jobnum={jobnum}",
				HttpMethod.GET, userToken, null, List.class, jobnum);
	}
	
	@RequestMapping("/TourFeeManagement/ExportData")
	@SuppressWarnings("unchecked")
	public void exportData(HttpServletRequest request,HttpServletResponse response) throws Exception {
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
		List<Object> colData9 = new ArrayList<Object>();
		List<Object> colData10 = new ArrayList<Object>();
		List<Object> colData11 = new ArrayList<Object>();
		TourFeeManagementQueryParam queryParam = new TourFeeManagementQueryParam();
		queryParam.setOrderno(request.getParameter("orderno"));
		queryParam.setPlateno(request.getParameter("plateno"));
		queryParam.setJobnum(request.getParameter("jobnum"));
		queryParam.setDriverid(request.getParameter("driverid"));
		queryParam.setPaymentstatus(request.getParameter("paymentstatus"));
		queryParam.setCompanyid(request.getParameter("companyid"));
		queryParam.setTradeno(request.getParameter("tradeno"));
		if (StringUtils.isNotBlank(request.getParameter("starttime"))) {
			queryParam.setStarttime(request.getParameter("starttime").replace("/", "-"));
		}
		if (StringUtils.isNotBlank(request.getParameter("endtime"))) {
			queryParam.setEndtime(request.getParameter("endtime").replace("/", "-"));
		}
		
		List<Map> tourFeeList = templateHelper.dealRequestWithToken("/TourFeeManagement/GetTourFeeListExport",
				HttpMethod.POST, userToken, queryParam, List.class);
		for (int i = 0; i < tourFeeList.size(); i++) {
			colData1.add((String) tourFeeList.get(i).get("orderno"));
			colData2.add((String) tourFeeList.get(i).get("plateno"));
			colData3.add((String) tourFeeList.get(i).get("jobnum"));
			if (StringUtils.isBlank(tourFeeList.get(i).get("name").toString())) {
				colData4.add((String) tourFeeList.get(i).get("phone"));
			} else {
				colData4.add((String) tourFeeList.get(i).get("name") + " " + (String) tourFeeList.get(i).get("phone"));
			}
			colData5.add((String) tourFeeList.get(i).get("shortname"));
			colData6.add(String.valueOf(tourFeeList.get(i).get("orderamount")));
			colData7.add((String) tourFeeList.get(i).get("paymentstatusvisual"));
			colData8.add((String) tourFeeList.get(i).get("paymenttimevisual"));
			// 支付渠道
			if ("未结算".equals((String) tourFeeList.get(i).get("paymentstatusvisual"))) {
				colData9.add("/");
			} else {
				if (tourFeeList.get(i).get("paymenttype") == null || StringUtils.isBlank(String.valueOf(tourFeeList.get(i).get("paymenttype")))) {
					colData9.add("余额支付");
				} else if ("1".equals(String.valueOf(tourFeeList.get(i).get("paymenttype")))) {
					colData9.add("微信支付");
				} else if ("2".equals(String.valueOf(tourFeeList.get(i).get("paymenttype")))) {
					colData9.add("支付宝支付");
				}
			}
			colData10.add((String) tourFeeList.get(i).get("settlementtimevisual"));
			// 交易流水号
			if ("未结算".equals((String) tourFeeList.get(i).get("paymentstatusvisual"))) {
				colData11.add("/");
			} else {
				if (tourFeeList.get(i).get("paymenttype") == null || StringUtils.isBlank(String.valueOf(tourFeeList.get(i).get("paymenttype")))) {
					colData11.add("/");
				} else if ("1".equals(String.valueOf(tourFeeList.get(i).get("paymenttype"))) || "2".equals(String.valueOf(tourFeeList.get(i).get("paymenttype")))) {
					colData11.add((String) tourFeeList.get(i).get("tradeno"));
				}
			}
		}
		Excel excel = new Excel();
		// excel文件
		File tempFile = new File("结算管理.xls");
		
		List<String> colName = new ArrayList<String>();
		colName.add("订单号");
		colName.add("车牌号");
		colName.add("资格证号");
		colName.add("司机信息");
		colName.add("服务车企");
		colName.add("行程费用（元）");
		colName.add("结算状态");
		colName.add("收款时间");
		colName.add("支付渠道");
		colName.add("结算时间");
		colName.add("交易流水号");
		excel.setColName(colName);
		colData.put("订单号", colData1);
		colData.put("车牌号", colData2);
		colData.put("资格证号", colData3);
		colData.put("司机信息", colData4);
		colData.put("服务车企", colData5);
		colData.put("行程费用（元）", colData6);
		colData.put("结算状态", colData7);
		colData.put("收款时间", colData8);
		colData.put("支付渠道", colData9);
		colData.put("结算时间", colData10);
		colData.put("交易流水号", colData11);
		excel.setColData(colData);
		
		ExcelExport ee = new ExcelExport(request,response,excel);
		//ee.setSheetName("结算管理");
		ee.createExcel(tempFile);
	}
}
