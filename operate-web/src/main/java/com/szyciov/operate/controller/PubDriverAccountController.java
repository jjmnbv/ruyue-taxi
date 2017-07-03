package com.szyciov.operate.controller;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.szyciov.op.param.PubDriverAccountQueryParam;
import com.szyciov.op.param.PubDriverExpensesQuerryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.ExcelExport;
import com.szyciov.util.PageBean;
import com.szyciov.util.TemplateHelper;

@Controller
public class PubDriverAccountController extends BaseController {
	private static final Logger logger = Logger.getLogger(PubDriverAccountController.class);
	
	private TemplateHelper templateHelper = new TemplateHelper();
	
	@RequestMapping(value = "/PubDriverAccount/Index")
	public String getOrgDriverAccountIndex() {
		return "resource/pubDriverAccount/index";
	}
	
	@RequestMapping(value = "/PubDriverAccount/GetOrgDriverAccountByQuery")
	@ResponseBody
	public PageBean getOrgDriverAccountByQuery(@RequestBody PubDriverAccountQueryParam orgDriverAccountQueryParam,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		return templateHelper.dealRequestWithToken("/PubDriverAccount/GetOrgDriverAccountByQuery", HttpMethod.POST,
				userToken, orgDriverAccountQueryParam, PageBean.class);
	}
	
	@RequestMapping(value = "/PubDriverAccount/GetQueryDriver")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getQueryDriver(
			@RequestParam(value = "queryDriver", required = false) String queryDriver, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		return templateHelper.dealRequestWithToken("/PubDriverAccount/GetQueryDriver?queryDriver={queryDriver}", HttpMethod.POST, userToken, null,
				List.class, queryDriver);
	}
	
	@RequestMapping(value = "/PubDriverAccount/TransactionDetailedIndex")
	public ModelAndView getTransactionDetailedIndex(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("driverid", request.getParameter("driverid"));
		mav.addObject("driverName", request.getParameter("driverName"));
		mav.addObject("driverAccount", request.getParameter("driverAccount"));
		mav.setViewName("resource/pubDriverAccount/transactionDetailed");
		return mav;
	}
	
	@RequestMapping(value = "/PubDriverAccount/BalanceDetailedIndex")
	public ModelAndView getBalanceDetailedIndex(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("driverid", request.getParameter("driverid"));
		mav.addObject("driverName", request.getParameter("driverName"));
		mav.addObject("driverAccount", request.getParameter("driverAccount"));
		mav.setViewName("resource/pubDriverAccount/balanceDetailed");
		return mav;
	}
	
	@RequestMapping(value = "/PubDriverAccount/GetDetailedByQuery")
	@ResponseBody
	public PageBean getDetailedByQuery(@RequestBody PubDriverExpensesQuerryParam peDriverExpensesQuerryParam,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		peDriverExpensesQuerryParam.setDriverid(request.getParameter("driverid"));
		peDriverExpensesQuerryParam.setDetailtype(request.getParameter("detailtype"));
		
		return templateHelper.dealRequestWithToken("/PubDriverAccount/GetDetailedByQuery", HttpMethod.POST, userToken,
				peDriverExpensesQuerryParam, PageBean.class);
	}
	
	@RequestMapping("/PubDriverAccount/ExportData")
	@SuppressWarnings("unchecked")
	public void exportData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		Map<String, List<Object>> colData = new HashMap<String, List<Object>>();
		List<Object> colData1 = new ArrayList<Object>();
		List<Object> colData2 = new ArrayList<Object>();
		List<Object> colData3 = new ArrayList<Object>();
		List<Object> colData4 = new ArrayList<Object>();
		List<Object> colData5 = new ArrayList<Object>();
		List<Object> colData6 = new ArrayList<Object>();
		
		String driverid = request.getParameter("driverid");
		String queryTradetype = request.getParameter("queryTradetype");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String detailType = request.getParameter("detailtype");
		PubDriverExpensesQuerryParam pv = new PubDriverExpensesQuerryParam();
		pv.setDriverid(driverid);
		pv.setQueryTradetype(queryTradetype);
		pv.setStartTime(startTime);
		pv.setEndTime(endTime);
		pv.setDetailtype(detailType);
		
		List<Map> peDriverExpenses = templateHelper.dealRequestWithToken("/PubDriverAccount/ExportData",
				HttpMethod.POST, userToken, pv, List.class);
		for (int i = 0; i < peDriverExpenses.size(); i++) {
			if (peDriverExpenses.get(i).get("expensetimevisual") != null) {
				colData1.add(peDriverExpenses.get(i).get("expensetimevisual").toString());
			} else {
				colData1.add("");
			}
			
			if (peDriverExpenses.get(i).get("tradetype") != null) {
				if (("0").equals(peDriverExpenses.get(i).get("tradetype").toString())) {
					colData2.add("充值");
				} else if (("1").equals(peDriverExpenses.get(i).get("tradetype").toString())) {
					colData2.add("订单结算");
				} else if (("2").equals(peDriverExpenses.get(i).get("tradetype").toString())
						|| ("3").equals(peDriverExpenses.get(i).get("tradetype").toString())) {
					colData2.add("提现");
				} else if (("4").equals(peDriverExpenses.get(i).get("tradetype").toString())) {
					colData2.add("订单收入");
				}
			} else {
				colData2.add("");
			}
			
			if (peDriverExpenses.get(i).get("expensetype") != null) {
				if (("2").equals(peDriverExpenses.get(i).get("tradetype").toString())
						|| ("3").equals(peDriverExpenses.get(i).get("tradetype").toString())) {
					colData3.add("/");
				} else {
					if (("1").equals(peDriverExpenses.get(i).get("expensetype").toString())) {
						colData3.add("微信支付");
					} else if (("2").equals(peDriverExpenses.get(i).get("expensetype").toString())) {
						colData3.add("支付宝支付");
					} else if (("3").equals(peDriverExpenses.get(i).get("expensetype").toString())) {
						colData3.add("余额支付");
					} else if (("5").equals(peDriverExpenses.get(i).get("expensetype").toString())) {
						if ("0".equals(detailType)) {
							colData3.add("平台转入");
						} else {
							colData3.add("余额");
						}
					}
				}
			} else {
				colData3.add("");
			}
			
			if (peDriverExpenses.get(i).get("amount") != null) {
				if (peDriverExpenses.get(i).get("tradetype") != null) {
					if (("0").equals(peDriverExpenses.get(i).get("tradetype").toString())
							|| ("2").equals(peDriverExpenses.get(i).get("tradetype").toString())
							|| ("4").equals(peDriverExpenses.get(i).get("tradetype").toString())) {
						colData4.add("+" + peDriverExpenses.get(i).get("amount"));
					} else if (("1").equals(peDriverExpenses.get(i).get("tradetype").toString())
							|| ("3").equals(peDriverExpenses.get(i).get("tradetype").toString())) {
						colData4.add("-" + peDriverExpenses.get(i).get("amount"));
					}
				}
			} else {
				colData4.add("");
			}
			
			if (peDriverExpenses.get(i).get("balance") != null) {
				colData5.add(peDriverExpenses.get(i).get("balance"));
			} else {
				colData5.add("");
			}
			
			if (peDriverExpenses.get(i).get("remark") != null) {
				colData6.add(peDriverExpenses.get(i).get("remark"));
			} else {
				colData6.add("");
			}
		}
		Excel excel = new Excel();
		// excel文件
		String driverName = request.getParameter("driverName");
		String driverAccount = request.getParameter("driverAccount");
		String detailed = "";
		if ("0".equals(detailType)) {
			detailed = "余额明细";
		} else {
			detailed = "交易明细";
		}
		File tempFile = new File("司机账户-【" + driverName + driverAccount + "】" + detailed + ".xls");
		
		List<String> colName = new ArrayList<String>();
		colName.add("时间");
		colName.add("交易类型");
		colName.add("交易渠道");
		colName.add("金额（元）");
		if ("0".equals(detailType)) {
			colName.add("账户余额（元）");
		}
		colName.add("备注");
		excel.setColName(colName);
		colData.put("时间", colData1);
		colData.put("交易类型", colData2);
		colData.put("交易渠道", colData3);
		colData.put("金额（元）", colData4);
		if ("0".equals(detailType)) {
			colData.put("账户余额（元）", colData5);
		}
		colData.put("备注", colData6);
		excel.setColData(colData);
		
		ExcelExport ee = new ExcelExport(request, response, excel);
		ee.createExcel(tempFile);
	}
	
	public static String getStringDate(String now) {
		Date d = new Date(Long.valueOf(now));
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTimeInMillis(now);
//		return formatter.format(calendar.getTime());
		return formatter.format(d);
	}
}
