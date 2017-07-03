package com.szyciov.operate.controller;

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
import com.szyciov.op.entity.OpUser;
import com.szyciov.op.param.OpOrderstatisticsParam;
import com.szyciov.param.OrderStatisticsQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.ExcelExport;
import com.szyciov.util.PageBean;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper;
@Controller
public class OpOrderstatisticsController extends BaseController{
	private static final Logger logger = Logger.getLogger(HomePageController.class);
	private TemplateHelper templateHelper = new TemplateHelper();
	ModelAndView mav = new ModelAndView();
	/**
	 * 进入界面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/OpOrderstatistics/Index")
	public ModelAndView getOpOrderstatistics(HttpServletRequest request, HttpServletResponse response) {
		mav.addObject("custom",getCustom(request));
		mav.setViewName("resource/opOrderstatistics/month");
		return mav;
	}
	/**
	 * 按月统计进入界面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/OpOrderstatistics/month")
	public ModelAndView monthIndex(HttpServletRequest request, HttpServletResponse response){
		mav.addObject("custom",getCustom(request));
		mav.setViewName("resource/opOrderstatistics/month");
		return mav;
	}
	/**
	 * 按季度统计进入界面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/OpOrderstatistics/quarter")
	public ModelAndView quarterIndex(HttpServletRequest request, HttpServletResponse response){
		mav.addObject("custom",getCustom(request));
		mav.setViewName("resource/opOrderstatistics/quarter");
		return mav;
	}
	/**
	 * 按年统计进入界面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/OpOrderstatistics/year")
	public ModelAndView yearIndex(HttpServletRequest request, HttpServletResponse response){
		mav.addObject("custom",getCustom(request));
		mav.setViewName("resource/opOrderstatistics/year");
		return mav;
	}
	/**
	 * 客户下拉列表
	 */
	@SuppressWarnings("unchecked")
	public List<OpOrderstatisticsParam> getCustom(HttpServletRequest request){
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OpUser user = getLoginOpUser(request);
		String userid = user.getId();
		List<OpOrderstatisticsParam> custom = templateHelper.dealRequestWithToken("/OpOrderstatistics/GetCustom/{userid}", 
				HttpMethod.GET, userToken, null, List.class,userid);
		return custom;
	}
	/**
	 * 按月统计数据, 按季度统计数据,按年统计数据
	 * @param time
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "OpOrderstatistics/monthData")
	@ResponseBody
	public PageBean getMonthData(
			@RequestParam(value = "starttime", required = false) String starttime,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "endtime", required = false) String endtime1,
			@RequestBody OpOrderstatisticsParam opOrderstatisticsParam1 ,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		response.setContentType("application/json;charset=utf-8");
		String endtime = opOrderstatisticsParam1.getEndtime();
		String endtimeOK = "";
		if(endtime !=null){
			endtime = opOrderstatisticsParam1.getStarttime()+"_"+endtime;
			opOrderstatisticsParam1.setEndtime(endtime);
		}
		if(endtime1 !=null){
			endtimeOK = starttime+"_"+endtime1;
		}
		OpOrderstatisticsParam opOrderstatisticsParam = new  OpOrderstatisticsParam();
		if(opOrderstatisticsParam1.getKey() == null){
			opOrderstatisticsParam = opOrderstatisticsParam1;
			opOrderstatisticsParam.setStarttime(starttime);
			opOrderstatisticsParam.setType(type);
			opOrderstatisticsParam.setEndtime(endtimeOK);
		}else{
			opOrderstatisticsParam = opOrderstatisticsParam1;
		}
		OpUser user = getLoginOpUser(request);
		String userid = user.getId();
		String usertype = user.getUsertype();
		if(usertype.equals("1")){
		}else{
			opOrderstatisticsParam.setUserid(userid);
		}
		PageBean  opOrderstatistics = templateHelper.dealRequestWithToken(
				"/OpOrderstatistics/month", HttpMethod.POST, userToken, opOrderstatisticsParam, PageBean.class);
		return opOrderstatistics;
	}
	/**
	 * 获取统计数据
	 * @param opOrderstatisticsParam
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/OpOrderstatistics/AllData")
	@ResponseBody
	public OpOrderstatisticsParam getAllData(@RequestBody OpOrderstatisticsParam opOrderstatisticsParam,
			HttpServletRequest request, HttpServletResponse response) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		opOrderstatistics(userToken);
		String endtime = opOrderstatisticsParam.getEndtime();
		if(endtime != null){
			endtime = opOrderstatisticsParam.getStarttime()+"_"+endtime;
			opOrderstatisticsParam.setEndtime(endtime);
		}
		OpOrderstatisticsParam OpOrderstatistics = templateHelper.dealRequestWithToken(
				"/OpOrderstatistics/AllData", HttpMethod.POST, userToken, opOrderstatisticsParam, 
				OpOrderstatisticsParam.class);
		return OpOrderstatistics;
	}
	
	@RequestMapping(value = "OpOrderstatistics/exportExcel")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public void exportData
	     (
			@RequestParam(value = "starttime", required = false) String starttime,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "endtime", required = false) String endtime,
			@RequestParam(value = "leasescompanyid", required = false) String leasescompanyid,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		Map<String, List<Object>> colData = new HashMap<String, List<Object>>();
		List<Object> colData1 = new ArrayList<Object>();
		List<Object> colData2 = new ArrayList<Object>();
		List<Object> colData3 = new ArrayList<Object>();
		List<Object> colData4 = new ArrayList<Object>();
		List<Object> colData5 = new ArrayList<Object>();
		List<Object> colData6 = new ArrayList<Object>();
		List<Object> colData7 = new ArrayList<Object>();
		OpOrderstatisticsParam queryParam = new OpOrderstatisticsParam();
		queryParam.setStarttime(starttime);
		queryParam.setType(type);
		queryParam.setLeasescompanyid(leasescompanyid);
		OpUser user = getLoginOpUser(request);
		String userid = user.getId();
		String usertype = user.getUsertype();
		if(usertype.equals("1")){
		}else{
			queryParam.setUserid(userid);
		}
		if(endtime != "null"){
			endtime = queryParam.getStarttime()+"_"+endtime;
			queryParam.setEndtime(endtime);
		}
		List<Map> orgOrganExpenses = templateHelper.dealRequestWithToken("/OpOrderstatistics/exportExcel",
				HttpMethod.POST, userToken, queryParam, List.class);
		for (int i = 0; i < orgOrganExpenses.size(); i++) {
			colData1.add((String) orgOrganExpenses.get(i).get("starttime"));
			colData2.add((String) orgOrganExpenses.get(i).get("compayName"));
			colData3.add((String) orgOrganExpenses.get(i).get("orders"));
			colData4.add((String) orgOrganExpenses.get(i).get("carorders"));
			colData5.add((String) orgOrganExpenses.get(i).get("pickuporders"));
			colData6.add((String) orgOrganExpenses.get(i).get("dropofforders"));
			colData7.add((String) orgOrganExpenses.get(i).get("ordermoney"));
		}
		Excel excel = new Excel();
		// excel文件
		File tempFile = new File("销售管理统计.xls");

		List<String> colName = new ArrayList<String>();
		if(type.equals("0")){
		       colName.add("月份");
		}
		if(type.equals("1")){
			   colName.add("季度");
			}
		if(type.equals("2")){
			   colName.add("年度");
			}
		colName.add("客户名称");
		colName.add("总订单量");
		colName.add("约车订单");
		colName.add("接机订单");
		colName.add("送机订单");
		colName.add("订单收入(元)");
		excel.setColName(colName);
		if(type.equals("0")){
		      colData.put("月份", colData1);
		}
		if(type.equals("1")){
		      colData.put("季度", colData1);
		}
		if(type.equals("2")){
		      colData.put("年度", colData1);
		}
		colData.put("客户名称", colData2);
		colData.put("总订单量", colData3);
		colData.put("约车订单", colData4);
		colData.put("接机订单", colData5);
		colData.put("送机订单", colData6);
		colData.put("订单收入(元)", colData7);
		excel.setColData(colData);

		ExcelExport ee = new ExcelExport(request, response, excel);
		ee.setSheetMaxRow(5000);
		ee.createExcel(tempFile);
		
	}
	/**
	 * 订单数据汇总
	 * @param userToken
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> opOrderstatistics(String userToken) {
		OrderStatisticsQueryParam queryParam = new OrderStatisticsQueryParam();
		String carserviceApiUrl = SystemConfig.getSystemProperty("carserviceApiUrl");
		return templateHelper.dealRequestWithFullUrlToken(carserviceApiUrl + "/OrderStatistics/OpOrderstatistics", HttpMethod.POST, userToken, 
				queryParam, Map.class);
	}

}
