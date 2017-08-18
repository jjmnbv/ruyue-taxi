package com.szyciov.lease.controller;

import java.io.File;
import java.io.IOException;
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

import com.alibaba.fastjson.JSON;
import com.szyciov.entity.Excel;
import com.szyciov.lease.entity.OrganUserCouponInfo;
import com.szyciov.lease.entity.User;
import com.szyciov.lease.param.OrganUserAccountQueryParam;
import com.szyciov.lease.param.OrganUserCouponQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.ExcelExport;
import com.szyciov.util.PageBean;
import com.szyciov.util.TemplateHelper;

@Controller
public class OrganUserAccountController extends BaseController {
    private static final Logger logger = Logger.getLogger(OrganUserAccountController.class);
	
	private TemplateHelper templateHelper = new TemplateHelper();
	
	@RequestMapping(value = "/OrganUserAccount/Index")
	public String getOrganAccountIndex(HttpServletRequest request) {
		return "resource/organUserAccount/index";
	}
	
	@RequestMapping("/OrganUserAccount/GetOrganUserAccountInfoByQuery")
	@ResponseBody
	public PageBean getOrganUserAccountInfoByQuery(@RequestBody OrganUserAccountQueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		queryParam.setLeasesCompanyId(user.getLeasescompanyid());
		queryParam.setSpecialState(user.getSpecialstate());
		queryParam.setAccount(user.getAccount());
		return templateHelper.dealRequestWithToken("/OrganUserAccount/GetOrganUserAccountInfoByQuery", HttpMethod.POST, userToken,
				queryParam,PageBean.class);
	}
	
	@RequestMapping("/OrganUserAccount/GetUserExpensesByQuery")
	@ResponseBody
	public PageBean getUserExpensesByQuery(@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "detailType", required = false) String detailType,
			@RequestBody OrganUserAccountQueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		queryParam.setUserId(userId);
		queryParam.setLeasesCompanyId(user.getLeasescompanyid());
		queryParam.setDetailType(detailType);
		return templateHelper.dealRequestWithToken("/OrganUserAccount/GetUserExpensesByQuery", HttpMethod.POST,
				userToken, queryParam, PageBean.class);
	}
	
	@RequestMapping("/OrganUserAccount/GetExistOrganList")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getExistOrganList(@RequestParam(value = "shortName", required = false) String shortName, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		String leasesCompanyId = user.getLeasescompanyid();
		String specialState = user.getSpecialstate();
		String account = user.getAccount();
		
		return templateHelper.dealRequestWithToken("/OrganUserAccount/GetExistOrganList?leasesCompanyId={leasesCompanyId}&shortName={shortName}&specialState={specialState}&account={account}", HttpMethod.GET, userToken, null,
				List.class, leasesCompanyId, shortName, specialState, account);
	}
	
	@RequestMapping("/OrganUserAccount/GetExistUserList")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getExistUserList(@RequestParam(value = "nameAccount", required = false) String nameAccount, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		String leasesCompanyId = user.getLeasescompanyid();
		String specialState = user.getSpecialstate();
		String account = user.getAccount();
		
		return templateHelper.dealRequestWithToken("/OrganUserAccount/GetExistUserList?leasesCompanyId={leasesCompanyId}&nameAccount={nameAccount}&specialState={specialState}&account={account}", HttpMethod.GET, userToken, null,
				List.class, leasesCompanyId, nameAccount, specialState, account);
	}
	
	@RequestMapping(value = "/OrganUserAccount/AccountDetail")
	public ModelAndView getOrganUserAccountDetail(@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "account", required = true) String account, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("userId", userId);
		mav.addObject("account", account);
		mav.setViewName("resource/organUserAccount/accountDetail");
		return mav;
	}
	
	/**
	 * 抵用券明细界面
	 * @param userId
	 * @param account
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/OrganUserAccount/CouponDetail")
	public ModelAndView getOrganUserCouponDetail(@RequestParam String userid,
			@RequestParam String account, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("userid", userid);
		mav.addObject("account", account);
		mav.setViewName("resource/organUserAccount/couponDetail");
		return mav;
	}
	
	/**
	 * 查询个人抵用券明细
	 * @param userId
	 * @param account
	 * @param request
	 * @return
	 */
	@RequestMapping("/OrganUserAccount/GetOrganUserCouponInfoByQuery")
	@ResponseBody
	public PageBean getOrganUserCouponInfoByQuery(@RequestBody OrganUserCouponQueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		queryParam.setLecompanyid(user.getLeasescompanyid());
		return templateHelper.dealRequestWithToken("/OrganUserAccount/GetOrganUserCouponInfoByQuery", HttpMethod.POST, userToken,
				queryParam,PageBean.class);
	}
	
	@RequestMapping(value = "/OrganUserAccount/BalanceDetail")
	public ModelAndView getOrganUserBalanceDetail(@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "account", required = true) String account, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("userId", userId);
		mav.addObject("account", account);
		mav.setViewName("resource/organUserAccount/balanceDetail");
		return mav;
	}
	
	@RequestMapping("/OrganUserAccount/ExportData")
	@SuppressWarnings("unchecked")
	public void exportData(@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "expenseType", required = false) String expenseType,
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
		OrganUserAccountQueryParam queryParam = new OrganUserAccountQueryParam();
		queryParam.setLeasesCompanyId(getLoginLeUser(request).getLeasescompanyid());
		queryParam.setUserId(userId);
		queryParam.setExpenseType(expenseType);
		queryParam.setStartTime(startTime);
		queryParam.setEndTime(endTime);
		queryParam.setDetailType(request.getParameter("detailType"));
		List<Map> orgUserExpenses = templateHelper.dealRequestWithToken("/OrganAccount/GetUserExpensesListExport",
				HttpMethod.POST, userToken, queryParam, List.class);
		for (int i = 0; i < orgUserExpenses.size(); i++) {
			colData1.add((String) orgUserExpenses.get(i).get("expenseTime"));
			colData2.add((String) orgUserExpenses.get(i).get("tradeType"));
			colData3.add((String) orgUserExpenses.get(i).get("expenseType"));
			colData4.add(String.valueOf(orgUserExpenses.get(i).get("amount")));
			colData5.add(String.valueOf(orgUserExpenses.get(i).get("balance")));
			colData6.add((String) orgUserExpenses.get(i).get("remark"));
		}
		Excel excel = new Excel();
		// excel文件
		String fineName = "";
		if (request.getParameter("detailType") != null && "0".equals(request.getParameter("detailType"))) {
			fineName = "个人账户-【" + request.getParameter("account") + "】余额明细.xls";
		} else {
			fineName = "个人账户-【" + request.getParameter("account") + "】交易明细.xls";
		}
		File tempFile = new File(fineName);
		
		List<String> colName = new ArrayList<String>();
		colName.add("时间");
		colName.add("交易类型");
		colName.add("交易渠道");
		colName.add("金额(元)");
		if (request.getParameter("detailType") != null && "0".equals(request.getParameter("detailType"))) {
			colName.add("账户余额(元)");
		}
		colName.add("备注");
		excel.setColName(colName);
		colData.put("时间", colData1);
		colData.put("交易类型", colData2);
		colData.put("交易渠道", colData3);
		colData.put("金额(元)", colData4);
		if (request.getParameter("detailType") != null && "0".equals(request.getParameter("detailType"))) {
			colData.put("账户余额(元)", colData5);
		}
		colData.put("备注", colData6);
		excel.setColData(colData);
		
		ExcelExport ee = new ExcelExport(request,response,excel);
		//ee.setSheetMaxRow(6);
		if (request.getParameter("detailType") != null && "0".equals(request.getParameter("detailType"))) {
			ee.setSheetName("余额明细");
		} else {
			ee.setSheetName("交易明细");
		}
		ee.createExcel(tempFile);
	}
	
	@RequestMapping("/OrganUserAccount/ExportCouponData")
	@SuppressWarnings("unchecked")
	public void exportCouponData(@RequestParam(value = "userid", required = true) String userid,
			@RequestParam(value = "couponstatus", required = false) Integer couponstatus,
			@RequestParam(value = "starttime", required = false) String startTime,
			@RequestParam(value = "account", required = false) String account,
			@RequestParam(value = "endtime", required = false) String endTime, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		Map<String, List<Object>> colData = new HashMap<String, List<Object>>();
		List<Object> colData1 = new ArrayList<Object>();
		List<Object> colData2 = new ArrayList<Object>();
		List<Object> colData3 = new ArrayList<Object>();
		List<Object> colData4 = new ArrayList<Object>();
		List<Object> colData5 = new ArrayList<Object>();
		List<Object> colData6 = new ArrayList<Object>();
		OrganUserCouponQueryParam queryParam = new OrganUserCouponQueryParam();
		queryParam.setLecompanyid(getLoginLeUser(request).getLeasescompanyid());
		queryParam.setUserid(userid);
		queryParam.setCouponstatus(couponstatus);
		queryParam.setStarttime(startTime);
		queryParam.setEndtime(endTime);
		 
		List<Map> orgUserExpenses = templateHelper.dealRequestWithToken("/OrganAccount/ExportCouponData",
				HttpMethod.POST, userToken, queryParam, List.class);
		for (Map m:orgUserExpenses) {
			OrganUserCouponInfo info=JSON.parseObject(JSON.toJSONString(m), OrganUserCouponInfo.class);
			colData1.add(info.getSendtime());
			colData2.add(info.getName());
			colData3.add(info.getCouponstatus()==0?"未使用":(info.getCouponstatus()==1?"已使用":"已过期"));
			colData4.add(info.getAmount());
			colData5.add(info.getUseendtime().equals(info.getUsestarttime())?info.getUsestarttime():info.getUsestarttime()+"-"+info.getUseendtime());
			colData6.add(info.getUsecity());
		}
		Excel excel = new Excel();
		// excel文件
		String fineName = "个人账户-【" + account + "】抵用券明细.xls";
		File tempFile = new File(fineName);
		
		List<String> colName = new ArrayList<String>();
		colName.add("时间");
		colName.add("抵用券名称");
		colName.add("状态");
		colName.add("金额(元)");
		colName.add("有效期");
		colName.add("限用地点");
		excel.setColName(colName);
		colData.put("时间", colData1);
		colData.put("抵用券名称", colData2);
		colData.put("状态", colData3);
		colData.put("金额(元)", colData4);
		colData.put("有效期", colData5);
		colData.put("限用地点", colData6);
		excel.setColData(colData);
		
		ExcelExport ee = new ExcelExport(request,response,excel);
		//ee.setSheetName("抵用券明细");
		ee.createExcel(tempFile);
	}
}
