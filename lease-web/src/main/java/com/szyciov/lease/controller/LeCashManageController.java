package com.szyciov.lease.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.entity.Excel;
import com.szyciov.lease.entity.LeCashManage;
import com.szyciov.lease.entity.User;
import com.szyciov.lease.param.CashManageQueryParam;
import com.szyciov.lease.service.LeCashManageService;
import com.szyciov.util.BaseController;
import com.szyciov.util.ExcelExport;
import com.szyciov.util.PageBean;

@Controller
public class LeCashManageController extends BaseController {
	private static final Logger logger = Logger.getLogger(LeCashManageController.class);
	
	public LeCashManageService leCashManageService;
	
	@Resource(name = "leCashManageService")
	public void setEmployeeService(LeCashManageService leCashManageService) {
		this.leCashManageService = leCashManageService;
	}

	@RequestMapping(value = "/LeCashManage/Index")
	public String LeCashManageIndex() {
		return "resource/cashmanage/index";
	}
	
	@RequestMapping(value = "/LeCashManage/AlIndex")
	public String LeCashManageAlIndex() {
		return "resource/cashmanage/alindex";
	}
	
	/**
	 * 查询用户拥有权限的机构下拉框数据
	 * @param organName
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "LeCashManage/GetAccounts")
	@ResponseBody
	public List<Map<String, Object>> getAccounts(@RequestParam Map<String,Object> params, HttpServletRequest request,HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String usertoken = getUserToken(request);
		User user = getLoginLeUser(request);
		params.put("leasescompanyid", user.getLeasescompanyid());
		params.put("platformtype", "1");
		return leCashManageService.getAccounts(params, usertoken);
	}
	
	/**
	 * 查询用户拥有权限的机构下拉框数据
	 * @param organName
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "LeCashManage/GetNames")
	@ResponseBody
	public List<Map<String, Object>> getNames(@RequestParam Map<String,Object> params, HttpServletRequest request,HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String usertoken = getUserToken(request);
		User user = getLoginLeUser(request);
		params.put("leasescompanyid", user.getLeasescompanyid());
		params.put("platformtype", "1");
		return leCashManageService.getNames(params, usertoken);
	}
	
	
	@RequestMapping("LeCashManage/GetCashByQuery")
	@ResponseBody
	public PageBean getCashByQuery(@RequestBody CashManageQueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String usertoken = getUserToken(request);
		User user = getLoginLeUser(request);
		queryParam.setLeasescompanyid(user.getLeasescompanyid());
		return leCashManageService.getCashByQuery(queryParam, usertoken);
	}
	
	@RequestMapping("LeCashManage/CashReject")
	@ResponseBody
	public Map<String, Object> cashReject(@RequestBody Map<String,Object> params, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String usertoken = getUserToken(request);
		User user = getLoginLeUser(request);
		params.put("processuserid", user.getId());
		return leCashManageService.cashReject(params, usertoken);
	}
	
	@RequestMapping("LeCashManage/CashOk")
	@ResponseBody
	public Map<String, Object> cashOk(@RequestBody Map<String,Object> params, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String usertoken = getUserToken(request);
		User user = getLoginLeUser(request);
		params.put("processuserid", user.getId());
		return leCashManageService.cashOk(params, usertoken);
	}
	
	@RequestMapping("LeCashManage/GetExportDataCount")
	@ResponseBody
	public Map<String, Object> getExportDataCount(@RequestBody Map<String,Object> params, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String usertoken = getUserToken(request);
		User user = getLoginLeUser(request);
		params.put("leasescompanyid", user.getLeasescompanyid());
		params.put("platformtype", "1");
		params.put("processstatus", "0");
		return leCashManageService.getExportDataCount(params, usertoken);
	}
	
	@RequestMapping("/LeCashManage/ExportData")
	public void exportData(@RequestParam Map<String,Object> params,HttpServletRequest request, HttpServletResponse response){
		String usertoken = getUserToken(request);
		User user = getLoginLeUser(request);
		params.put("leasescompanyid", user.getLeasescompanyid());
		params.put("platformtype", "1");
		params.put("processstatus", "0");
		List<Map<String,Object>> alldata = leCashManageService.getAllUnderCashData(params, usertoken);
		if(alldata==null||alldata.size()<=0){
			return ;
		}
		List<String> colNames = new ArrayList<String>();
		Map<String, List<Object>> colData = new HashMap<String, List<Object>>();
		String[] colnames = new String[]{"序号","申请账户","用户类型","提现金额(元)","银行账号","账户名称","开户银行","申请时间"};
		colNames.add(colnames[0]);
		colData.put(colnames[0], new ArrayList<Object>());
		colNames.add(colnames[1]);
		colData.put(colnames[1], new ArrayList<Object>());
		colNames.add(colnames[2]);
		colData.put(colnames[2], new ArrayList<Object>());
		colNames.add(colnames[3]);
		colData.put(colnames[3], new ArrayList<Object>());
		colNames.add(colnames[4]);
		colData.put(colnames[4], new ArrayList<Object>());
		colNames.add(colnames[5]);
		colData.put(colnames[5], new ArrayList<Object>());
		colNames.add(colnames[6]);
		colData.put(colnames[6], new ArrayList<Object>());
		colNames.add(colnames[7]);
		colData.put(colnames[7], new ArrayList<Object>());
		for(int i=0;i<alldata.size();i++){
			Map<String,Object> lecashmanage = alldata.get(i);
			colData.get(colnames[0]).add(lecashmanage.get("rownum"));
			colData.get(colnames[1]).add(lecashmanage.get("account"));
			String usertype = (String) lecashmanage.get("usertype");
			switch (usertype) {
			case "0":
				colData.get(colnames[2]).add("乘客");
				break;
			case "2":
				colData.get(colnames[2]).add("司机");
				break;
			case "3":
				colData.get(colnames[2]).add("机构");
				break;
			default:
				break;
			}
			colData.get(colnames[3]).add(lecashmanage.get("amount"));
			colData.get(colnames[4]).add(lecashmanage.get("creditcardnum"));
			colData.get(colnames[5]).add(lecashmanage.get("creditcardname"));
			colData.get(colnames[6]).add(lecashmanage.get("bankname"));
			colData.get(colnames[7]).add(lecashmanage.get("applytime"));
		}
		
		Excel excel = new Excel();
		File tempFile = new File("提现管理-待处理.xls");
		excel.setColName(colNames);
		excel.setColData(colData);
		ExcelExport ee = new ExcelExport(request,response,excel);
		ee.createExcel(tempFile);
	}
	
}


