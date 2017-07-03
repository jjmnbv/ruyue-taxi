package com.szyciov.lease.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
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
import com.szyciov.lease.entity.LeOrgorderstatisticsParamAll;
import com.szyciov.lease.entity.User;
import com.szyciov.lease.param.LeDriverorderstatisticsParam;
import com.szyciov.lease.param.LeOrgorderstatisticsParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.ExcelExport;
import com.szyciov.util.ExcelExport2;
import com.szyciov.util.PageBean;
import com.szyciov.util.TemplateHelper;

@Controller
public class LeDriverorderstatisticsController extends BaseController {
	private static final Logger logger = Logger.getLogger(LeDriverorderstatisticsController.class);
	private TemplateHelper templateHelper = new TemplateHelper();
	ModelAndView mav = new ModelAndView();

	@RequestMapping(value = "/LeDriverorderstatistics/Index")
	@SuppressWarnings("unchecked")
	public ModelAndView getPubVehicleIndex(HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		String leasesCompanyId = user.getLeasescompanyid();
		List<LeDriverorderstatisticsParam> cartype = templateHelper.dealRequestWithToken(
				"/LeDriverorderstatistics/GetcartypeId/{leasesCompanyId}", HttpMethod.GET, userToken, null, List.class,
				leasesCompanyId);
		mav.addObject("cartype", cartype);
		mav.setViewName("resource/leDriverorderstatistics/index");
		return mav;
	}

	/**
	 *  toB订单统计
	 * @param leDriverorderstatisticsParam
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "LeDriverorderstatistics/GetOragnCountByQuery")
	@ResponseBody
	public PageBean getDriverCountByQuery(//@RequestParam (value = "cartype", required = false) String cartype,
	@RequestParam(value = "name", required = false) String name,
	@RequestParam(value = "plateno", required = false) String plateno,
	@RequestParam(value = "vehcBrand", required = false) String vehcBrand,
	@RequestParam(value = "jobnum", required = false) String jobnum,
	@RequestParam(value = "startTime", required = false) String startTime,
	@RequestParam(value = "endTime", required = false) String endTime,
			@RequestBody LeDriverorderstatisticsParam leDriverorderstatisticsParam1,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		LeDriverorderstatisticsParam leDriverorderstatisticsParam = new LeDriverorderstatisticsParam();
		if(leDriverorderstatisticsParam1.getKey() == null){
			leDriverorderstatisticsParam = leDriverorderstatisticsParam1;
			leDriverorderstatisticsParam.setName(name);
			leDriverorderstatisticsParam.setPlateno(plateno);
			leDriverorderstatisticsParam.setVehcBrand(vehcBrand);
			leDriverorderstatisticsParam.setStartTime(startTime);
			leDriverorderstatisticsParam.setEndTime(endTime);
			leDriverorderstatisticsParam.setJobnum(jobnum);
			//leDriverorderstatisticsParam.setCartype(cartype);
		}else{
			leDriverorderstatisticsParam = leDriverorderstatisticsParam1;
		}
		leDriverorderstatisticsParam.setLeasesCompanyId(user.getLeasescompanyid());
		return templateHelper.dealRequestWithToken("/LeDriverorderstatistics/LeDriverorderstatisticsParam",
				HttpMethod.POST, userToken, leDriverorderstatisticsParam, PageBean.class);
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
	public PageBean getOragnCountByQueryToC(//@RequestParam (value = "cartype", required = false) String cartype,
	@RequestParam(value = "name", required = false) String name,
	@RequestParam(value = "plateno", required = false) String plateno,
	@RequestParam(value = "vehcBrand", required = false) String vehcBrand,
	@RequestParam(value = "startTime", required = false) String startTime,
	@RequestParam(value = "endTime", required = false) String endTime,
			@RequestBody LeDriverorderstatisticsParam leDriverorderstatisticsParam1,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
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
		leDriverorderstatisticsParam.setLeasesCompanyId(user.getLeasescompanyid());
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
		User user = getLoginLeUser(request);
		leDriverorderstatisticsParam.setLeasesCompanyId(user.getLeasescompanyid());
		leDriverorderstatisticsParam.setVehcBrand(vehcBrand);
		return templateHelper.dealRequestWithToken("/LeOrgorderstatistics/GetVehcBrand", HttpMethod.POST, userToken,
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
	@SuppressWarnings("unchecked")
	public LeOrgorderstatisticsParamAll getVehcBrandAll(
			@RequestBody LeDriverorderstatisticsParam leDriverorderstatisticsParam, HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		leDriverorderstatisticsParam.setLeasesCompanyId(user.getLeasescompanyid());
		List<Map> driverAll = templateHelper.dealRequestWithToken("/LeDriverorderstatistics/DriverAll", HttpMethod.POST,
				userToken, leDriverorderstatisticsParam, List.class);
		List<Map> driverAllTo = templateHelper.dealRequestWithToken("/LeDriverorderstatistics/DriverAllTo", HttpMethod.POST,
				userToken, leDriverorderstatisticsParam, List.class);
		double ordermoney = 0;
		int carorders = 0;
		int pickporders = 0;
		int dropofforders = 0;
		int orderreview = 0;
		int allOrders = 0;
		double orderreviewPrice = 0;
		int oragnAgency = 0;
		int personOrders = 0;
		for (int i = 0; i < driverAll.size(); i++) {
			String a = (String) driverAll.get(i).get("ordermoney");
			String b = (String) driverAll.get(i).get("carorders");
			String c = (String) driverAll.get(i).get("pickuporders");
			String d = (String) driverAll.get(i).get("dropofforders");
			// 差异订单
			String e = (String) driverAll.get(i).get("orderreview");
			// 差异金额
			String f = (String) driverAll.get(i).get("orderreviewPrice");
			// 服务机构订单
			String g = (String) driverAll.get(i).get("oragnAgency");
			// 服务机构订单
			String h = (String) driverAll.get(i).get("personOrders");
			String m = (String) driverAll.get(i).get("allOrders");
			// 订单金额
			if (a != "" && a != null) {
				ordermoney = Double.parseDouble(a);
			}
			// 约车订单数
			if (b != "" && b != null) {
				carorders = Integer.parseInt(b);
			}
			// 接机订单数
			if (c != "" && c != null) {
				pickporders = Integer.parseInt(c);
			}
			// 送机订单数
			if (d != "" && d != null) {
				dropofforders = Integer.parseInt(d);
			}
			// 差异订单
			if (e != "" && e != null) {
				orderreview = Integer.parseInt(e);
			}
			// 差异金额
			if (f != "" && f != null) {
				orderreviewPrice = Double.parseDouble(f);
			}
			// 服务机构订单
			if (g != "" && g != null) {
				oragnAgency = Integer.parseInt(g);
			}
			// 服务个人
			if (g != "" && g != null) {
				personOrders = Integer.parseInt(h);
			}
			// 总订单
			if (m != "" && m != null) {
				allOrders = Integer.parseInt(m);
			}

		}
		double ordermoneyToC = 0;
		int carordersToC = 0;
		int pickpordersToC = 0;
		int dropoffordersToC = 0;
		int allOrdersToC = 0;
		int orderreviewToC = 0;
		double orderreviewPriceToC = 0;
		int personOrdersToC = 0;
		int taxiOrders = 0;
		for (int i = 0; i < driverAllTo.size(); i++) {
			String a = (String) driverAllTo.get(i).get("ordermoney");
			String b = (String) driverAllTo.get(i).get("carorders");
			String c = (String) driverAllTo.get(i).get("pickuporders");
			String d = (String) driverAllTo.get(i).get("dropofforders");
			// 差异订单
			String e = (String) driverAllTo.get(i).get("orderreview");
			// 差异金额
			String f = (String) driverAllTo.get(i).get("orderreviewPrice");
			// 服务机构订单
			String g = (String) driverAllTo.get(i).get("personOrders");
			String m = (String) driverAllTo.get(i).get("allOrders");
			String n = (String) driverAllTo.get(i).get("taxiOrders");
			// 订单金额
			if (a != "" && a != null) {
				ordermoneyToC = Double.parseDouble(a);
			}
			// 约车订单数
			if (b != "" && b != null) {
				carordersToC = Integer.parseInt(b);
			}
			// 接机订单数
			if (c != "" && c != null) {
				pickpordersToC = Integer.parseInt(c);
			}
			// 送机订单数
			if (d != "" && d != null) {
				dropoffordersToC = Integer.parseInt(d);
			}
			// 差异订单
			if (e != "" && e != null) {
				orderreviewToC = Integer.parseInt(e);
			}
			// 差异金额
			if (f != "" && f != null) {
				orderreviewPriceToC = Double.parseDouble(f);
			}
			// 服务个人
			if (g != "" && g != null) {
				personOrdersToC = Integer.parseInt(g);
			}
			// 总订单
			if (m != "" && m != null) {
				allOrdersToC = Integer.parseInt(m);
			}
			//出租车
			if (n != "" && n != null) {
				taxiOrders = Integer.parseInt(n);
			}

		}
		LeOrgorderstatisticsParamAll leFunctionmanagementParamAll = new LeOrgorderstatisticsParamAll();
		leFunctionmanagementParamAll.setSumCarorders(carorders+carordersToC);
		leFunctionmanagementParamAll.setSumDropofforders(dropofforders+dropoffordersToC);
		leFunctionmanagementParamAll.setSumOrdermoney(doubleRetainTow(ordermoney+ordermoneyToC));
		leFunctionmanagementParamAll.setSumPickporders(pickporders+pickpordersToC);
		leFunctionmanagementParamAll.setSumAllOrders(allOrders+allOrdersToC);
		leFunctionmanagementParamAll.setSumOrderreview(orderreview+orderreviewToC);
		leFunctionmanagementParamAll.setSumOrderreviewPrice(doubleRetainTow(orderreviewPrice+orderreviewPriceToC));
		leFunctionmanagementParamAll.setSumOragnAgency(oragnAgency);
		leFunctionmanagementParamAll.setSumPersonOrders(personOrders+personOrdersToC);
		leFunctionmanagementParamAll.setSumIncomePrice(doubleRetainTow(ordermoney+ordermoneyToC-orderreviewPrice-orderreviewPriceToC));
		leFunctionmanagementParamAll.setSumTaxiOrders(taxiOrders);
		return leFunctionmanagementParamAll;
	}
	public double doubleRetainTow(double ok){
		 BigDecimal bg = new BigDecimal(ok);  
	     double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
   return f1;
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
	@RequestMapping(value = "LeDriverorderstatistics/ExportDrive")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public void exportData(//@RequestParam(value = "cartype", required = false) String cartype,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "plateno", required = false) String plateno,
			@RequestParam(value = "vehcBrand", required = false) String vehcBrand,
			@RequestParam(value = "vehcBrandtext", required = false) String vehcBrandtext,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		Map<String, List<Object>> colData = new HashMap<String, List<Object>>();
		List<Object> colData1 = new ArrayList<Object>();
		List<Object> colData2 = new ArrayList<Object>();
		List<Object> colData3 = new ArrayList<Object>();
		List<Object> colData4 = new ArrayList<Object>();
//		List<Object> colData5 = new ArrayList<Object>();
		List<Object> colData6 = new ArrayList<Object>();
		List<Object> colData7 = new ArrayList<Object>();
		List<Object> colData8 = new ArrayList<Object>();
		List<Object> colData9 = new ArrayList<Object>();
		List<Object> colData10 = new ArrayList<Object>();
		List<Object> colData11 = new ArrayList<Object>();
		List<Object> colData12 = new ArrayList<Object>();
//		List<Object> colData13 = new ArrayList<Object>();
		List<Object> colData14 = new ArrayList<Object>();
		LeDriverorderstatisticsParam queryParam = new LeDriverorderstatisticsParam();
		User user = getLoginLeUser(request);
		queryParam.setLeasesCompanyId(user.getLeasescompanyid());
		//queryParam.setCartype(cartype);
		queryParam.setName(name);
		queryParam.setPlateno(plateno);
		queryParam.setVehcBrand(vehcBrand);
		queryParam.setStartTime(startTime);
		queryParam.setEndTime(endTime);
		List<Map> orgOrganExpenses = templateHelper.dealRequestWithToken("/LeDriverorderstatistics/DriverAll1",
				HttpMethod.POST, userToken, queryParam, List.class);
		for (int i = 0; i < orgOrganExpenses.size(); i++) {
			colData1.add((String) orgOrganExpenses.get(i).get("jobnum"));
			colData2.add((String) orgOrganExpenses.get(i).get("name"));
			String platenoA = (String) orgOrganExpenses.get(i).get("plateno");
			if(platenoA == null){
				platenoA = "";
			}
			colData3.add(platenoA);
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
			colData12.add((String) orgOrganExpenses.get(i).get("reviewstatus"));
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
		colName.add("异常订单");
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
		colData.put("异常订单", colData12);
		colData.put("星评(平均分)", colData14);
		excel.setColData(colData);

		List<String> rowData = new ArrayList<String>();
		if(vehcBrandtext == null || vehcBrandtext == ""){
			vehcBrandtext = "全部";
		}
		rowData.add("司机:"+name+"   "+"时间:"+switchTime(startTime)+"-"+switchTime(endTime)+"   "+"车牌:"+plateno+"    "+"品牌车系:"+vehcBrandtext);
		excel.setRowData(rowData);
		ExcelExport2 ee = new ExcelExport2(request, response, excel);
		ee.setTitleRowCount(2);
		ee.setSheetMaxRow(5000);
		ee.setSheetName("司机订单统计");
		ee.createExcel(tempFile);
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
		@RequestParam(value = "plateno", required = false) String plateno,
		@RequestParam(value = "vehcBrand", required = false) String vehcBrand,
		@RequestParam(value = "vehcBrandtext", required = false) String vehcBrandtext,
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
	User user = getLoginLeUser(request);
	queryParam.setLeasesCompanyId(user.getLeasescompanyid());
	//queryParam.setCartype(cartype);
	queryParam.setName(name);
	queryParam.setPlateno(plateno);
	queryParam.setVehcBrand(vehcBrand);
	queryParam.setStartTime(startTime);
	queryParam.setEndTime(endTime);
	List<Map> orgOrganExpenses = templateHelper.dealRequestWithToken("/LeDriverorderstatistics/DriverAll2",
			HttpMethod.POST, userToken, queryParam, List.class);
	for (int i = 0; i < orgOrganExpenses.size(); i++) {
		colData1.add((String) orgOrganExpenses.get(i).get("jobnum"));
		colData2.add((String) orgOrganExpenses.get(i).get("name"));
		String platenoA = (String) orgOrganExpenses.get(i).get("plateno");
		if(platenoA == null){
			platenoA = "";
		}
		colData3.add(platenoA);
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
		colData12.add((String) orgOrganExpenses.get(i).get("texiOrders"));
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
	if(vehcBrandtext == null || vehcBrandtext == ""){
		vehcBrandtext = "全部";
	}
	rowData.add("司机:"+name+"   "+"时间:"+switchTime(startTime)+"-"+switchTime(endTime)+"   "+"车牌:"+plateno+"    "+"品牌车系:"+vehcBrandtext);
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
