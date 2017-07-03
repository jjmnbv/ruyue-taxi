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
import com.szyciov.lease.param.OrganUserAccountQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.ExcelExport;
import com.szyciov.util.ExcelExport2;
import com.szyciov.util.PageBean;
import com.szyciov.util.TemplateHelper;

@Controller
public class OpUserAccountController extends BaseController {
    private static final Logger logger = Logger.getLogger(OpUserAccountController.class);
	
	private TemplateHelper templateHelper = new TemplateHelper();
	
	@RequestMapping(value = "/OpUserAccount/Index")
	public String getOpUserAccountIndex(HttpServletRequest request) {
		return "resource/opUserAccount/index";
	}
	
	@RequestMapping("/OpUserAccount/GetOpUserAccountByQuery")
	@ResponseBody
	public PageBean getOpUserAccountByQuery(@RequestBody OrganUserAccountQueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		return templateHelper.dealRequestWithToken("/OpUserAccount/GetOpUserAccountByQuery", HttpMethod.POST, userToken,
				queryParam,PageBean.class);
	}
	
	@RequestMapping("/OpUserAccount/GetUserExpensesByQuery")
	@ResponseBody
	public PageBean getUserExpensesByQuery(@RequestParam(value = "userId", required = true) String userId, 
			@RequestParam(value = "detaills", required = true) String detaills,
			@RequestBody OrganUserAccountQueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		queryParam.setUserId(userId);
		if (queryParam.getStartTime() == null && StringUtils.isBlank(queryParam.getKey())) {
			queryParam.setStartTime(request.getParameter("startTime"));
		}
		if (queryParam.getEndTime() == null && StringUtils.isBlank(queryParam.getKey())) {
			queryParam.setEndTime(request.getParameter("endTime"));
		}
		if(detaills.equals("1")){
			queryParam.setDetailType("");
		}else{
			queryParam.setDetailType(detaills);
		}
		return templateHelper.dealRequestWithToken("/OpUserAccount/GetUserExpensesByQuery", HttpMethod.POST, userToken,
				queryParam,PageBean.class);
	}
	
	@RequestMapping("/OpUserAccount/GetUserExpensesCount")
	@ResponseBody
	public int getUserExpensesCount(@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "detaills", required = true) String detaills,
			@RequestBody OrganUserAccountQueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		queryParam.setUserId(userId);
		if(detaills.equals("1")){
			queryParam.setDetailType("");
		}else{
			queryParam.setDetailType(detaills);
		}
		return templateHelper.dealRequestWithToken("/OpUserAccount/GetUserExpensesCount", HttpMethod.POST, userToken,
				queryParam,Integer.class);
	}
	
	@RequestMapping("/OpUserAccount/GetExistUserList")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getExistUserList(@RequestParam(value = "nameAccount", required = false) String nameAccount, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		return templateHelper.dealRequestWithToken("/OpUserAccount/GetExistUserList?nameAccount={nameAccount}", HttpMethod.GET, userToken, null,
				List.class, nameAccount);
	}
	
	@RequestMapping(value = "/OpUserAccount/AccountDetail")
	public ModelAndView getOrganUserAccountDetail(@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "account", required = true) String account,
			@RequestParam(value = "nickName", required = true) String nickName, 
			@RequestParam(value = "detaills", required = true) String detaills,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("userId", userId);
		mav.addObject("account", account);
		mav.addObject("nickName", nickName);
		if(detaills.equals("jiaoyi")){
			mav.setViewName("resource/opUserAccount/accountDetail");
		}else if(detaills.equals("yue")){
			mav.setViewName("resource/opUserAccount/balanceDetail");
		}
		
		return mav;
	}
	
	@RequestMapping("/OpUserAccount/ExportData")
	@SuppressWarnings("unchecked")
	public void exportData(@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "expenseType", required = false) String expenseType,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime, 
			@RequestParam(value = "detaills", required = true) String detaills,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		Map<String, List<Object>> colData = new HashMap<String, List<Object>>();
		List<Object> colData1 = new ArrayList<Object>();
		List<Object> colData2 = new ArrayList<Object>();
		List<Object> colData3 = new ArrayList<Object>();
		List<Object> colData4 = new ArrayList<Object>();
		List<Object> colData5 = new ArrayList<Object>();
		List<Object> colData6 = new ArrayList<Object>();
		OrganUserAccountQueryParam queryParam = new OrganUserAccountQueryParam();
		queryParam.setUserId(userId);
		queryParam.setExpenseType(expenseType);
		queryParam.setStartTime(startTime);
		queryParam.setEndTime(endTime);
		if(detaills.equals("交易明细")){
			queryParam.setDetailType("");
		}else if(detaills.equals("余额明细")){
			queryParam.setDetailType("0");			
		}
		List<Map> orgUserExpenses = templateHelper.dealRequestWithToken("/OpUserAccount/GetUserExpensesListExport",
				HttpMethod.POST, userToken, queryParam, List.class);
		for (int i = 0; i < orgUserExpenses.size(); i++) {
			colData1.add((String) orgUserExpenses.get(i).get("expenseTime"));
			colData2.add((String) orgUserExpenses.get(i).get("tradeType"));
			colData3.add(String.valueOf(orgUserExpenses.get(i).get("amount")));
			if(orgUserExpenses.get(i).get("tradeType").toString().equals("提现")){
				colData4.add("/");
			}else{
				colData4.add(String.valueOf(orgUserExpenses.get(i).get("expenseType")));
			}
			colData5.add((String) orgUserExpenses.get(i).get("remark"));
			//balance
			if(detaills.equals("余额明细")){
				colData6.add((String) orgUserExpenses.get(i).get("balance"));
			}
		}
		Excel excel = new Excel();
		// excel文件
		File tempFile = new File("用户账户-【"+request.getParameter("account")+"】"+detaills+".xls");
		
		List<String> colName = new ArrayList<String>();
		colName.add("时间");
		colName.add("交易类型");
		colName.add("交易渠道");
		colName.add("金额(元)");
		if(detaills.equals("余额明细")){
			colName.add("账户余额（元）");
		}
		colName.add("备注");
		excel.setColName(colName);
		colData.put("时间", colData1);
		colData.put("交易类型", colData2);
		colData.put("交易渠道", colData4);
		colData.put("金额(元)", colData3);
		if(detaills.equals("余额明细")){
			colData.put("账户余额（元）", colData6);
		}
		colData.put("备注", colData5);
		excel.setColData(colData);
		
//		List<String> rowData = new ArrayList<String>();
//		rowData.add("【" + request.getParameter("account") + " " + request.getParameter("nickName") + "】账户往来明细");
//		excel.setRowData(rowData);
		
		ExcelExport ee = new ExcelExport(request,response,excel);
//		ee.setTitleRowCount(2);
		//ee.setSheetMaxRow(6);
		ee.setSheetName("个人账户");
		ee.createExcel(tempFile);
	}
}
