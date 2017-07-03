package com.szyciov.operate.controller;

import com.szyciov.entity.Excel;
import com.szyciov.enums.PlatformTypeByDb;
import com.szyciov.enums.WithdrawEnum;
import com.szyciov.op.entity.OpUser;
import com.szyciov.op.param.cashManager.CashManageQueryParam;
import com.szyciov.operate.service.OpCashManageService;
import com.szyciov.util.BaseController;
import com.szyciov.util.ExcelExport;
import com.szyciov.util.PageBean;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class OpCashManageController extends BaseController {
	private static final Logger logger = Logger.getLogger(OpCashManageController.class);

	@Autowired
	public OpCashManageService opCashManageService;


	@RequestMapping(value = "/CashManage/Index")
	public String LeCashManageIndex() {
		return "resource/cashmanage/index";
	}
	
	@RequestMapping(value = "/CashManage/AlIndex")
	public String LeCashManageAlIndex() {
		return "resource/cashmanage/alindex";
	}
	
	/**
	 * 查询用户拥有权限的机构下拉框数据
	 * @param
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "CashManage/GetAccounts")
	@ResponseBody
	public List<Map<String, Object>> getAccounts(@RequestParam Map<String,Object> params, HttpServletRequest request,HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String usertoken = getUserToken(request);
		params.put("platformtype", PlatformTypeByDb.OPERATING.code);
		params.put("processstatus", params.get("processstatus"));
		return opCashManageService.getAccounts(params, usertoken);
	}
	
	/**
	 * 查询用户拥有权限的机构下拉框数据
	 * @param
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "CashManage/GetNames")
	@ResponseBody
	public List<Map<String, Object>> getNames(@RequestParam Map<String,Object> params, HttpServletRequest request,HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String usertoken = getUserToken(request);
		params.put("platformtype", PlatformTypeByDb.OPERATING.code);
		params.put("processstatus", params.get("processstatus"));
		return opCashManageService.getNames(params, usertoken);
	}
	
	
	@RequestMapping("CashManage/GetCashByQuery")
	@ResponseBody
	public PageBean getCashByQuery(@RequestBody CashManageQueryParam queryParam, HttpServletRequest request,
								   HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String usertoken = getUserToken(request);
		queryParam.setPlatformtype(PlatformTypeByDb.OPERATING.code);
		return opCashManageService.getCashByQuery(queryParam, usertoken);
	}
	
	@RequestMapping("CashManage/CashReject")
	@ResponseBody
	public Map<String, Object> cashReject(@RequestBody Map<String,Object> params, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String usertoken = getUserToken(request);
		OpUser opUser = getLoginOpUser(request);
		params.put("processuserid", opUser.getId());
		return opCashManageService.cashReject(params, usertoken);
	}
	
	@RequestMapping("CashManage/CashOk")
	@ResponseBody
	public Map<String, Object> cashOk(@RequestBody Map<String,Object> params, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String usertoken = getUserToken(request);
		OpUser opUser = getLoginOpUser(request);
		params.put("processuserid", opUser.getId());
		return opCashManageService.cashOk(params, usertoken);
	}
	
	@RequestMapping("CashManage/GetExportDataCount")
	@ResponseBody
	public Map<String, Object> getExportDataCount(@RequestBody CashManageQueryParam params, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String usertoken = getUserToken(request);
		params.setPlatformtype(PlatformTypeByDb.OPERATING.code);
		params.setProcessstatus(WithdrawEnum.PROCESS_STATUS_PENDING.code);
		return opCashManageService.getListExportDataCount(params, usertoken);
	}
	
	@RequestMapping("/CashManage/ExportData")
	public void exportData(@RequestParam Map<String,String> parm,HttpServletRequest request, HttpServletResponse response){
		String usertoken = getUserToken(request);



		CashManageQueryParam queryParam = new CashManageQueryParam();
		queryParam.setUsertype(parm.get("usertype"));
		queryParam.setUserid(parm.get("userid"));
		queryParam.setCreditcardname(parm.get("creditcardname"));
		queryParam.setMinUseTime(parm.get("minUseTime"));
		queryParam.setMaxUseTime(parm.get("maxUseTime"));
		queryParam.setPlatformtype(PlatformTypeByDb.OPERATING.code);
		queryParam.setProcessstatus(WithdrawEnum.PROCESS_STATUS_PENDING.code);
		List<Map<String,Object>> alldata = opCashManageService.listExportData(queryParam, usertoken);
		if(alldata==null||alldata.size()<=0){
			return ;
		}
		List<String> colNames = new ArrayList<String>();
		Map<String, List<Object>> colData = new HashMap<String, List<Object>>();
		String[] colnames = new String[]{"申请账户","用户类型","提现金额(元)","银行账号","账户名称","开户银行","申请时间"};
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

		for(int i=0;i<alldata.size();i++){
			Map<String,Object> lecashmanage = alldata.get(i);
			colData.get(colnames[0]).add(lecashmanage.get("login"));
			colData.get(colnames[1]).add(lecashmanage.get("usertype"));
			colData.get(colnames[2]).add(lecashmanage.get("amount"));
			colData.get(colnames[3]).add(lecashmanage.get("creditcardnum"));
			colData.get(colnames[4]).add(lecashmanage.get("creditcardname"));
			colData.get(colnames[5]).add(lecashmanage.get("bankname"));
			colData.get(colnames[6]).add(lecashmanage.get("applytimeStr"));
		}
		
		Excel excel = new Excel();
		File tempFile = new File("提现管理-待处理.xls");
		excel.setColName(colNames);
		excel.setColData(colData);
		ExcelExport ee = new ExcelExport(request,response,excel);
		ee.createExcel(tempFile);
	}
	
}


