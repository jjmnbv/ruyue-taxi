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

import com.szyciov.entity.Excel;
import com.szyciov.lease.entity.OrgOrganExpenses;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.entity.User;
import com.szyciov.lease.param.OrganAccountQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.ExcelExport;
import com.szyciov.util.PageBean;
import com.szyciov.util.TemplateHelper;

@Controller
public class OrganAccountController extends BaseController {
    private static final Logger logger = Logger.getLogger(OrganAccountController.class);
	
	private TemplateHelper templateHelper = new TemplateHelper();
	
	@RequestMapping(value = "/OrganAccount/Index")
	@SuppressWarnings("unchecked")
	public ModelAndView getOrganAccountIndex(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		String leasesCompanyId = user.getLeasescompanyid();
		String specialState = user.getSpecialstate();
		String account = user.getAccount();
		List<PubCityAddr> pubCityAddr = templateHelper.dealRequestWithToken("/OrganAccount/GetExistCityList?leasesCompanyId={leasesCompanyId}&specialState={specialState}&account={account}", HttpMethod.GET,
				userToken, null, List.class, leasesCompanyId, specialState, account);
		mav.addObject("pubCityAddr", pubCityAddr);
		mav.setViewName("resource/organAccount/index");
		return mav;
	}
	
	@RequestMapping("/OrganAccount/GetOrganAccountInfoByQuery")
	@ResponseBody
	public PageBean getOrganAccountInfoByQuery(@RequestBody OrganAccountQueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		queryParam.setLeasesCompanyId(user.getLeasescompanyid());
		queryParam.setSpecialState(user.getSpecialstate());
		queryParam.setAccount(user.getAccount());
		return templateHelper.dealRequestWithToken("/OrganAccount/GetOrganAccountInfoByQuery", HttpMethod.POST, userToken,
				queryParam,PageBean.class);
	}
	
	@RequestMapping("/OrganAccount/GetOrganExpensesByQuery")
	@ResponseBody
	public PageBean getOrganExpensesByQuery(@RequestParam(value = "organId", required = true) String organId, @RequestBody OrganAccountQueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		queryParam.setLeasesCompanyId(user.getLeasescompanyid());
		queryParam.setOrganId(organId);
		return templateHelper.dealRequestWithToken("/OrganAccount/GetOrganExpensesByQuery", HttpMethod.POST, userToken,
				queryParam,PageBean.class);
	}

	@RequestMapping("/OrganAccount/RechargeOrganAccount")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String, String> rechargeOrganAccount(@RequestBody OrgOrganExpenses orgOrganExpenses, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		orgOrganExpenses.setLeasesCompanyId(user.getLeasescompanyid());
		return templateHelper.dealRequestWithToken("/OrganAccount/RechargeOrganAccount", HttpMethod.POST, userToken, orgOrganExpenses,
				Map.class);
	}
	
	@RequestMapping("/OrganAccount/ReduceOrganAccount")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String, String> reduceOrganAccount(@RequestBody OrgOrganExpenses orgOrganExpenses, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		orgOrganExpenses.setLeasesCompanyId(user.getLeasescompanyid());
		return templateHelper.dealRequestWithToken("/OrganAccount/ReduceOrganAccount", HttpMethod.POST, userToken, orgOrganExpenses,
				Map.class);
	}
	
	@RequestMapping("/OrganAccount/GetExistOrganList")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> reduceOrganAccount(@RequestParam(value = "shortName", required = false) String shortName, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		String leasesCompanyId = user.getLeasescompanyid();
		String specialState = user.getSpecialstate();
		String account = user.getAccount();
		
		return templateHelper.dealRequestWithToken("/OrganAccount/GetExistOrganList?leasesCompanyId={leasesCompanyId}&shortName={shortName}&specialState={specialState}&account={account}", HttpMethod.GET, userToken, null,
				List.class, leasesCompanyId, shortName, specialState, account);
	}
	
	@RequestMapping(value = "/OrganAccount/AccountDetail")
	@SuppressWarnings("unchecked")
	public ModelAndView getOrganAccountDetail(@RequestParam(value = "organId", required = true) String organId,
			@RequestParam(value = "fullName", required = true) String fullName, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("organId", organId);
		mav.addObject("fullName", fullName);
		mav.setViewName("resource/organAccount/accountDetail");
		return mav;
	}
	
	@RequestMapping("/OrganAccount/ExportData")
	@SuppressWarnings("unchecked")
	public void exportData(@RequestParam(value = "organId", required = true) String organId,
			@RequestParam(value = "type", required = false) String type,
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
		OrganAccountQueryParam queryParam = new OrganAccountQueryParam();
		queryParam.setLeasesCompanyId(getLoginLeUser(request).getLeasescompanyid());
		queryParam.setOrganId(organId);
		queryParam.setType(type);
		queryParam.setStartTime(startTime);
		queryParam.setEndTime(endTime);
		List<Map> orgOrganExpenses = templateHelper.dealRequestWithToken("/OrganAccount/GetOrganExpensesListExport",
				HttpMethod.POST, userToken, queryParam, List.class);
		for (int i = 0; i < orgOrganExpenses.size(); i++) {
			colData1.add((String) orgOrganExpenses.get(i).get("expenseTime"));
			colData2.add((String) orgOrganExpenses.get(i).get("typeName"));
			colData3.add(String.valueOf(orgOrganExpenses.get(i).get("amountVisual")));
			colData4.add(String.valueOf(orgOrganExpenses.get(i).get("balance")));
			colData5.add((String) orgOrganExpenses.get(i).get("remarkVisual"));
		}
		Excel excel = new Excel();
		// excel文件
		File tempFile = new File("机构账户-【" + request.getParameter("fullName") + "】账户往来明细.xls");
		
		List<String> colName = new ArrayList<String>();
		colName.add("时间");
		colName.add("类型");
		colName.add("金额(元)");
		colName.add("账户余额(元)");
		colName.add("备注");
		excel.setColName(colName);
		colData.put("时间", colData1);
		colData.put("类型", colData2);
		colData.put("金额(元)", colData3);
		colData.put("账户余额(元)", colData4);
		colData.put("备注", colData5);
		excel.setColData(colData);
		
		ExcelExport ee = new ExcelExport(request,response,excel);
		//ee.setSheetMaxRow(6);
		ee.setSheetName("账户往来明细");
		ee.createExcel(tempFile);
	}
	
}
