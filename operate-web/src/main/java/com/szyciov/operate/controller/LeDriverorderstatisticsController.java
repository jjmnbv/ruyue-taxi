package com.szyciov.operate.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.szyciov.lease.param.LeDriverorderstatisticsParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.ExcelExport2;
import com.szyciov.util.PageBean;
import com.szyciov.util.TemplateHelper;

@Controller
public class LeDriverorderstatisticsController extends BaseController {
	private TemplateHelper templateHelper = new TemplateHelper();
	ModelAndView mav = new ModelAndView();

	@RequestMapping(value = "/LeDriverorderstatistics/Index")
	@SuppressWarnings("unchecked")
	public ModelAndView getPubVehicleIndex(HttpServletRequest request) {
		mav.setViewName("resource/leDriverorderstatistics/index");
		return mav;
	}
	/**
	 *  toC订单统计
	 * @param leDriverorderstatisticsParam
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "LeDriverorderstatistics/GetOragnCountByQueryToC")
	@ResponseBody
	public PageBean getOragnCountByQueryToC(
	@RequestParam(value = "name", required = false) String name,
	@RequestParam(value = "plateno", required = false) String plateno,
	@RequestParam(value = "vehcBrand", required = false) String vehcBrand,
	@RequestParam(value = "startTime", required = false) String startTime,
	@RequestParam(value = "endTime", required = false) String endTime,
			@RequestBody LeDriverorderstatisticsParam leDriverorderstatisticsParam1,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		LeDriverorderstatisticsParam leDriverorderstatisticsParam = new LeDriverorderstatisticsParam();
		if(leDriverorderstatisticsParam1.getKey() == null){
			leDriverorderstatisticsParam = leDriverorderstatisticsParam1;
			leDriverorderstatisticsParam.setName(name);
			leDriverorderstatisticsParam.setPlateno(plateno);
			leDriverorderstatisticsParam.setVehcBrand(vehcBrand);
			leDriverorderstatisticsParam.setStartTime(startTime);
			leDriverorderstatisticsParam.setEndTime(endTime);
			//leDriverorderstatisticsParam.setCartype(cartype);
		}else{
			leDriverorderstatisticsParam = leDriverorderstatisticsParam1;
		}
		return templateHelper.dealRequestWithToken("/LeDriverorderstatistics/LeDriverorderstatisticsParamToC",
				HttpMethod.POST, userToken, leDriverorderstatisticsParam, PageBean.class);
	}
	
	/**
	 *  品牌车系
	 * @param vehcBrand
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/LeDriverorderstatistics/GetVehcBrand")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getVehcBrand(@RequestParam String vehcBrand, HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		LeDriverorderstatisticsParam leDriverorderstatisticsParam = new LeDriverorderstatisticsParam();
		leDriverorderstatisticsParam.setVehcBrand(vehcBrand);
		return templateHelper.dealRequestWithToken("/LeOrgorderstatistics/GetVehcBrand", HttpMethod.POST, userToken,
				leDriverorderstatisticsParam, List.class);
	}
	/**
	 * 车牌号
	 * @param plateno
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/LeDriverorderstatistics/GetPlateno")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getPlateno(@RequestParam String plateno, HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		LeDriverorderstatisticsParam leDriverorderstatisticsParam = new LeDriverorderstatisticsParam();
//		User user = getLoginLeUser(request);
		leDriverorderstatisticsParam.setPlateno(plateno);
		return templateHelper.dealRequestWithToken("/LeDriverorderstatistics/GetPlateno", HttpMethod.POST, userToken,
				leDriverorderstatisticsParam, List.class);
	}
	/**
	 * 司机
	 * @param driver
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/LeDriverorderstatistics/GetDriver")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getDriver(@RequestParam String driver, HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		LeDriverorderstatisticsParam leDriverorderstatisticsParam = new LeDriverorderstatisticsParam();
//		User user = getLoginLeUser(request);
		leDriverorderstatisticsParam.setName(driver);
		return templateHelper.dealRequestWithToken("/LeDriverorderstatistics/GetDriver", HttpMethod.POST, userToken,
				leDriverorderstatisticsParam, List.class);
	}
	/**
	 * 资格证号
	 * @param driver
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/LeDriverorderstatistics/GetJobnum")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getJobnum(@RequestParam String jobnum, HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		LeDriverorderstatisticsParam leDriverorderstatisticsParam = new LeDriverorderstatisticsParam();
//		User user = getLoginLeUser(request);
		leDriverorderstatisticsParam.setJobnum(jobnum);
		return templateHelper.dealRequestWithToken("/LeDriverorderstatistics/GetJobnum", HttpMethod.POST, userToken,
				leDriverorderstatisticsParam, List.class);
	}
	/**
	 * 订单统计
	 * @param leDriverorderstatisticsParam
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/LeDriverorderstatistics/DriverAll")
	@ResponseBody
	public LeDriverorderstatisticsParam getVehcBrandAll(
			@RequestBody LeDriverorderstatisticsParam leDriverorderstatisticsParam, HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		LeDriverorderstatisticsParam driverAllTo = templateHelper.dealRequestWithToken("/LeDriverorderstatistics/DriverAllTo", HttpMethod.POST,
				userToken, leDriverorderstatisticsParam, LeDriverorderstatisticsParam.class);
             return driverAllTo;
	}
/**
 * 导出
 * @param cartype
 * @param name
 * @param plateno
 * @param vehcBrand
 * @param startTime
 * @param endTime
 * @param request
 * @param response
 * @throws Exception
 */
@RequestMapping(value = "LeDriverorderstatistics/ExportDrivetoC")
@ResponseBody
@SuppressWarnings("unchecked")
public void exportDataToC(//@RequestParam(value = "cartype", required = false) String cartype,
		@RequestParam(value = "name", required = false) String name,
		@RequestParam(value = "nametext", required = false) String nametext,
		@RequestParam(value = "plateno", required = false) String plateno,
		@RequestParam(value = "platenotext", required = false) String platenotext,
		@RequestParam(value = "vehcBrand", required = false) String vehcBrand,
		@RequestParam(value = "vehcBrandtext", required = false) String vehcBrandtext,
		@RequestParam(value = "jobnum", required = false) String jobnum,
		@RequestParam(value = "startTime", required = false) String startTime,
		@RequestParam(value = "endTime", required = false) String endTime, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
	String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
	Map<String, List<Object>> colData = new HashMap<String, List<Object>>();
	List<Object> colData1 = new ArrayList<Object>();
	List<Object> colData2 = new ArrayList<Object>();
	List<Object> colData3 = new ArrayList<Object>();
	List<Object> colData4 = new ArrayList<Object>();
//	List<Object> colData5 = new ArrayList<Object>();
	List<Object> colData6 = new ArrayList<Object>();
	List<Object> colData7 = new ArrayList<Object>();
	List<Object> colData8 = new ArrayList<Object>();
	List<Object> colData9 = new ArrayList<Object>();
	List<Object> colData10 = new ArrayList<Object>();
	List<Object> colData11 = new ArrayList<Object>();
	List<Object> colData12 = new ArrayList<Object>();
//	List<Object> colData13 = new ArrayList<Object>();
	List<Object> colData14 = new ArrayList<Object>();
	LeDriverorderstatisticsParam queryParam = new LeDriverorderstatisticsParam();
	queryParam.setLeasesCompanyId("");
	//queryParam.setCartype(cartype);
	queryParam.setName(name);
	queryParam.setPlateno(plateno);
	queryParam.setVehcBrand(vehcBrand);
	queryParam.setStartTime(startTime);
	queryParam.setEndTime(endTime);
	queryParam.setJobnum(jobnum);
	List<Map> orgOrganExpenses = templateHelper.dealRequestWithToken("/LeDriverorderstatistics/DriverAll2",
			HttpMethod.POST, userToken, queryParam, List.class);
	for (int i = 0; i < orgOrganExpenses.size(); i++) {
		colData1.add((String) orgOrganExpenses.get(i).get("jobnum"));
		colData2.add((String) orgOrganExpenses.get(i).get("name"));
		colData3.add((String) orgOrganExpenses.get(i).get("plateno"));
		String vehcBrandA = (String) orgOrganExpenses.get(i).get("vehcBrand");
		if(vehcBrandA == null){
			vehcBrandA = "";
		}
		colData4.add(vehcBrandA);
		colData6.add((String) orgOrganExpenses.get(i).get("cityName"));
		colData7.add((String) orgOrganExpenses.get(i).get("ordermoney"));
		colData8.add((String) orgOrganExpenses.get(i).get("allOrders"));
		colData9.add((String) orgOrganExpenses.get(i).get("carorders"));
		colData10.add((String) orgOrganExpenses.get(i).get("pickuporders"));
		colData11.add((String) orgOrganExpenses.get(i).get("dropofforders"));
		colData12.add((String) orgOrganExpenses.get(i).get("taxiOrders"));
		String userrateA = (String) orgOrganExpenses.get(i).get("userrate");
		if(userrateA == null){
			userrateA = "";
		}
		colData14.add(userrateA);
	}
	Excel excel = new Excel();
	// excel文件
	File tempFile = new File("司机订单统计.xls");

	List<String> colName = new ArrayList<String>();
	colName.add("工号");
	colName.add("姓名/手机号码");
	colName.add("车牌号");
	colName.add("品牌车系");
	colName.add("城市名称");
	colName.add("总金额");
	colName.add("总订单数");
	colName.add("约车");
	colName.add("接机");
	colName.add("送机");
	colName.add("出租车");
	colName.add("星评(平均分)");
	excel.setColName(colName);
	colData.put("工号", colData1);
	colData.put("姓名/手机号码", colData2);
	colData.put("车牌号", colData3);
	colData.put("品牌车系", colData4);
	colData.put("城市名称", colData6);
	colData.put("总金额", colData7);
	colData.put("总订单数", colData8);
	colData.put("约车", colData9);
	colData.put("接机", colData10);
	colData.put("送机", colData11);
	colData.put("出租车", colData12);
	colData.put("星评(平均分)", colData14);
	excel.setColData(colData);
	List<String> rowData = new ArrayList<String>();
	if(nametext==null ||nametext ==""){
		nametext = "全部";
	}
	if(platenotext==null ||platenotext ==""){
		platenotext = "全部";
	}
	if(vehcBrandtext==null ||vehcBrandtext ==""){
		vehcBrandtext = "全部";
	}
	rowData.add("司机:"+nametext+"   "+"时间:"+switchTime(startTime)+"-"+switchTime(endTime)+"   "+"车牌:"+platenotext+"    "+"品牌车系:"+vehcBrandtext);
	excel.setRowData(rowData);
	ExcelExport2 ee = new ExcelExport2(request, response, excel);
	ee.setTitleRowCount(2);
	ee.setSheetMaxRow(5000);
	ee.setSheetName("司机订单统计");
	ee.createExcel(tempFile);
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
