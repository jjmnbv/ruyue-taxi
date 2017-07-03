package com.szyciov.organ.controller;
import java.io.File;
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
import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.org.param.ReportStatisticsParam;
import com.szyciov.param.OrderStatisticsQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.ExcelExport;
import com.szyciov.util.ExcelExport2;
import com.szyciov.util.PageBean;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper;
@Controller
public class ReportStatisticsController extends BaseController{
	private static final Logger logger = Logger.getLogger(OrgUsecarrulesController.class);
	private TemplateHelper templateHelper = new TemplateHelper();
	@RequestMapping("ReportStatistics/Index")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView();
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		List<PubDictionary> ordertype = templateHelper.dealRequestWithToken(
				"/ReportStatistics/Getordertype", HttpMethod.GET, userToken, null,
				List.class);
		mav.addObject("ordertype", ordertype);
		mav.setViewName("resource/reportStatistics/index");
		return mav;
	}
	/**
	 * 查询公司统计
	 * @param reportStatisticsParam
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("ReportStatistics/GetCompayByQuery")
	@ResponseBody
	public PageBean getCompayByQuery(
	@RequestParam(value = "starttime", required = false) String starttime,
	@RequestParam(value = "endtime", required = false) String endtime,
	@RequestParam(value = "ordertype", required = false) String ordertype,
	@RequestBody ReportStatisticsParam reportStatisticsParam1,HttpServletRequest request,HttpServletResponse response){
		String organId = getLoginOrgUser(request).getOrganId();
		reportStatisticsParam1.setOrganid(organId);
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		orgCompanystatistics(userToken);
		ReportStatisticsParam reportStatisticsParam = new ReportStatisticsParam();
		if(reportStatisticsParam1.getKey()==null){
			reportStatisticsParam = reportStatisticsParam1;
			reportStatisticsParam.setStarttime(starttime);
			reportStatisticsParam.setEndtime(endtime);
			reportStatisticsParam.setOrdertype(ordertype);
		}else{
			reportStatisticsParam = reportStatisticsParam1;
		}
		reportStatisticsParam.setOrganid(organId);
		return templateHelper.dealRequestWithToken("/ReportStatistics/GetCompayByQuery", HttpMethod.POST, userToken,reportStatisticsParam,
				PageBean.class);
	}
	/**
	 * 查询部门统计
	 * @param reportStatisticsParam
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("ReportStatistics/GetDeptByQuery")
	@ResponseBody
	public PageBean getDeptByQuery(
			@RequestParam(value = "starttime", required = false) String starttime,
			@RequestParam(value = "endtime", required = false) String endtime,
			@RequestParam(value = "ordertype", required = false) String ordertype,
			@RequestBody ReportStatisticsParam reportStatisticsParam1,HttpServletRequest request,HttpServletResponse response){
		String organId = getLoginOrgUser(request).getOrganId();
		reportStatisticsParam1.setOrganid(organId);
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		orgDeptstatistics(userToken);
		ReportStatisticsParam reportStatisticsParam = new ReportStatisticsParam();
		if(reportStatisticsParam1.getKey()==null){
			reportStatisticsParam = reportStatisticsParam1;
			reportStatisticsParam.setStarttime(starttime);
			reportStatisticsParam.setEndtime(endtime);
			reportStatisticsParam.setOrdertype(ordertype);
		}else{
			reportStatisticsParam = reportStatisticsParam1;
		}
		reportStatisticsParam.setOrganid(organId);
		return templateHelper.dealRequestWithToken("/ReportStatistics/GetDeptByQuery", HttpMethod.POST, userToken,reportStatisticsParam,
				PageBean.class);
	} 
	/**
	 * 公司统计导出
	 * @param starttime
	 * @param endtime
	 * @param ordertype
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "ReportStatistics/exportExcel")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public void exportData(@RequestParam(value = "starttime", required = false) String starttime,
			@RequestParam(value = "endtime", required = false) String endtime,
			@RequestParam(value = "ordertypeText", required = false) String ordertypeText,
			@RequestParam(value = "ordertype", required = false) String ordertype,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		Map<String, List<Object>> colData = new HashMap<String, List<Object>>();
		List<Object> colData1 = new ArrayList<Object>();
		List<Object> colData2 = new ArrayList<Object>();
		List<Object> colData3 = new ArrayList<Object>();
		List<Object> colData4 = new ArrayList<Object>();
		ReportStatisticsParam queryParam = new ReportStatisticsParam();
		String organId = getLoginOrgUser(request).getOrganId();
		queryParam.setOrganid(organId);
		queryParam.setStarttime(starttime);
		queryParam.setEndtime(endtime);
		queryParam.setOrdertype(ordertype);
		List<Map> orgOrganExpenses = templateHelper.dealRequestWithToken("/ReportStatistics/GetExportExcal",
				HttpMethod.POST, userToken, queryParam, List.class);
		for (int i = 0; i < orgOrganExpenses.size(); i++) {
			colData1.add((String) orgOrganExpenses.get(i).get("rownum"));
			colData2.add((String) orgOrganExpenses.get(i).get("leasescompanyName"));
			colData3.add((String) orgOrganExpenses.get(i).get("ordernum"));
			colData4.add((String) orgOrganExpenses.get(i).get("ordermoney"));
		}
		Excel excel = new Excel();
		// excel文件
		File tempFile = new File("机构用车统计.xls");

		List<String> colName = new ArrayList<String>();
		colName.add("序号");
		colName.add("服务车企");
		colName.add("总订单数");
		colName.add("总金额(元)");
		excel.setColName(colName);
		colData.put("序号", colData1);
		colData.put("服务车企", colData2);
		colData.put("总订单数", colData3);
		colData.put("总金额(元)", colData4);
		excel.setColData(colData);

		List<String> rowData = new ArrayList<String>();
		if(ordertypeText==null ||ordertypeText ==""){
			ordertypeText = "全部";
		}
		rowData.add("时间:"+switchTime(starttime)+"-"+switchTime(endtime)+"   "+"用车方式:"+ordertypeText);
		excel.setRowData(rowData);
		ExcelExport2 ee = new ExcelExport2(request, response, excel);
		ee.setTitleRowCount(2);
		ee.setSheetMaxRow(5000);
		ee.setSheetName("机构用车统计");
		ee.createExcel(tempFile);
	}
	/**
	 * 订单统计导出
	 * @param starttime
	 * @param endtime
	 * @param ordertype
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "ReportStatistics/exportExcel1")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public void exportData1(@RequestParam(value = "starttime", required = true) String starttime,
			@RequestParam(value = "endtime", required = false) String endtime,
			@RequestParam(value = "ordertypeText", required = false) String ordertypeText,
			@RequestParam(value = "ordertype", required = false) String ordertype,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		Map<String, List<Object>> colData = new HashMap<String, List<Object>>();
		List<Object> colData1 = new ArrayList<Object>();
		List<Object> colData2 = new ArrayList<Object>();
		List<Object> colData3 = new ArrayList<Object>();
		List<Object> colData4 = new ArrayList<Object>();
		ReportStatisticsParam queryParam = new ReportStatisticsParam();
		String organId = getLoginOrgUser(request).getOrganId();
		queryParam.setOrganid(organId);
		queryParam.setStarttime(starttime);
		queryParam.setEndtime(endtime);
		queryParam.setOrdertype(ordertype);
		List<Map> orgOrganExpenses = templateHelper.dealRequestWithToken("/ReportStatistics/GetExportExcal1",
				HttpMethod.POST, userToken, queryParam, List.class);
		for (int i = 0; i < orgOrganExpenses.size(); i++) {
			colData1.add((String) orgOrganExpenses.get(i).get("rownum"));
			colData2.add((String) orgOrganExpenses.get(i).get("deptName"));
			colData3.add((String) orgOrganExpenses.get(i).get("ordernum"));
			colData4.add((String) orgOrganExpenses.get(i).get("ordermoney"));
		}
		Excel excel = new Excel();
		// excel文件
		File tempFile = new File("部门用车统计.xls");

		List<String> colName = new ArrayList<String>();
		colName.add("序号");
		colName.add("部门");
		colName.add("总订单数");
		colName.add("总金额(元)");
		excel.setColName(colName);
		colData.put("序号", colData1);
		colData.put("部门", colData2);
		colData.put("总订单数", colData3);
		colData.put("总金额(元)", colData4);
		excel.setColData(colData);

		List<String> rowData = new ArrayList<String>();
		if(ordertypeText==null ||ordertypeText ==""){
			ordertypeText = "全部";
		}
		rowData.add("时间:"+switchTime(starttime)+"-"+switchTime(endtime)+"   "+"用车方式:"+ordertypeText);
		excel.setRowData(rowData);
		ExcelExport2 ee = new ExcelExport2(request, response, excel);
		ee.setTitleRowCount(2);
		ee.setSheetMaxRow(5000);
		ee.setSheetName("部门用车统计");
		ee.createExcel(tempFile);
	}
	/**
	 * 机构端机构公司用车统计
	 * @param userToken
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> orgCompanystatistics(String userToken) {
		OrderStatisticsQueryParam queryParam = new OrderStatisticsQueryParam();
		String carserviceApiUrl = SystemConfig.getSystemProperty("carserviceApiUrl");
		return templateHelper.dealRequestWithFullUrlToken(carserviceApiUrl + "/OrderStatistics/OrgCompanystatistics", HttpMethod.POST, userToken, 
				queryParam, Map.class);
	}
	/**
	 * 机构端部门用车统计
	 * @param userToken
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> orgDeptstatistics(String userToken) {
		OrderStatisticsQueryParam queryParam = new OrderStatisticsQueryParam();
		String carserviceApiUrl = SystemConfig.getSystemProperty("carserviceApiUrl");
		return templateHelper.dealRequestWithFullUrlToken(carserviceApiUrl + "/OrderStatistics/OrgDeptstatistics", HttpMethod.POST, userToken, 
				queryParam, Map.class);
	}
	/**
	 * 替换字符
	 * 
	 */
	 public String switchTime(String time) {
		 String alltimeStr = time+"-";
		 String aa = alltimeStr.replaceFirst("-", "年").replaceFirst("-","月").replaceFirst("-", "日");
		return aa;
		 
	 }

}
