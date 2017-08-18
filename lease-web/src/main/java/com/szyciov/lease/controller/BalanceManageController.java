package com.szyciov.lease.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.szyciov.entity.Excel;
import com.szyciov.lease.dto.balance.BalanceDto;
import com.szyciov.lease.entity.User;
import com.szyciov.lease.param.BalanceManageQueryParam;
import com.szyciov.op.param.TourFeeManagementQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.ExcelExport;
import com.szyciov.util.PageBean;
import com.szyciov.util.TemplateHelper;

@Controller
@RequestMapping(value="BalanceManage")
public class BalanceManageController extends BaseController{

	private TemplateHelper templateHelper = new TemplateHelper();
	
	@RequestMapping(value = "Index")
	@ResponseBody
	public ModelAndView Index(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = new ModelAndView();
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		//所属企业
		List<Map<String, Object>> companyList = templateHelper.dealRequestWithToken("/BalanceManage/GetCompanyNameById?lecompanyid={lecompanyid}",
				HttpMethod.GET, userToken, null, List.class,user.getLeasescompanyid());

		view.addObject("companyList", companyList);
		view.setViewName("resource/balanceManage/index");
		return view;
	}
	
	@RequestMapping(value="QueryBalanceByParam")
	@ResponseBody
	public PageBean queryBalanceByParam(@RequestBody BalanceManageQueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		queryParam.setLecompanyid(user.getLeasescompanyid());
		return templateHelper.dealRequestWithToken("/BalanceManage/QueryBalanceByParam", HttpMethod.POST, userToken,
				queryParam,PageBean.class);
	}
	
	@RequestMapping(value="GetOrderNo")
	@ResponseBody
	public List<Map<String, Object>> getOrderNo(@RequestParam(value = "orderno", required = false) String orderno,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		return templateHelper.dealRequestWithToken("/BalanceManage/GetOrderNo?orderno={orderno}&lecompanyid={lecompanyid}",
				HttpMethod.GET, userToken, null, List.class, orderno,user.getLeasescompanyid());
	}
	
	@RequestMapping(value="GetDriverByNameOrPhone")
	@ResponseBody
	public List<Map<String, Object>> getDriverByNameOrPhone(@RequestParam(value = "driver", required = false) String driver,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		return templateHelper.dealRequestWithToken("/BalanceManage/GetDriverByNameOrPhone?driver={driver}&lecompanyid={lecompanyid}",
				HttpMethod.GET, userToken, null, List.class, driver,user.getLeasescompanyid());
	}
	
	@RequestMapping(value="GetJobNum")
	@ResponseBody
	public List<Map<String, Object>> getJobnum(@RequestParam(value = "jobnum", required = false) String jobnum,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		return templateHelper.dealRequestWithToken("/BalanceManage/GetJobNum?jobnum={jobnum}&lecompanyid={lecompanyid}",
				HttpMethod.GET, userToken, null, List.class, jobnum,user.getLeasescompanyid());
	}
	
	@RequestMapping(value="GetFullPlateno")
	@ResponseBody
	public List<Map<String, Object>> getFullPlateno(@RequestParam(value = "fullplateno", required = false) String fullplateno,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		return templateHelper.dealRequestWithToken("/BalanceManage/GetFullPlateno?fullplateno={fullplateno}&lecompanyid={lecompanyid}",
				HttpMethod.GET, userToken, null, List.class, fullplateno,user.getLeasescompanyid());
	}
	
	@RequestMapping(value="ExportData")
	public void exportData(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		Map<String, List<Object>> colData = new HashMap<String, List<Object>>();
		List<Object> colData1 = new ArrayList<Object>();
		List<Object> colData2 = new ArrayList<Object>();
		List<Object> colData3 = new ArrayList<Object>();
		List<Object> colData4 = new ArrayList<Object>();
		List<Object> colData5 = new ArrayList<Object>();
		List<Object> colData6 = new ArrayList<Object>();
		List<Object> colData7 = new ArrayList<Object>();
		List<Object> colData8 = new ArrayList<Object>();
		List<Object> colData9 = new ArrayList<Object>();
		List<Object> colData10 = new ArrayList<Object>();
		List<Object> colData11 = new ArrayList<Object>();
		BalanceManageQueryParam queryParam = new BalanceManageQueryParam();
		queryParam.setOrderno(request.getParameter("orderno"));
		queryParam.setFullplateno(request.getParameter("plateno"));
		queryParam.setJobnum(request.getParameter("jobnum"));
		queryParam.setDriverid(request.getParameter("driverid"));
		queryParam.setPaymentstatus(request.getParameter("paymentstatus"));
		queryParam.setCompanyid(request.getParameter("companyid"));
		queryParam.setTradeno(request.getParameter("tradeno"));
		queryParam.setStarttime(request.getParameter("starttime"));
		queryParam.setEndtime(request.getParameter("endtime"));
		queryParam.setLecompanyid(user.getLeasescompanyid());
		
		List<BalanceDto> tourFeeList = templateHelper.dealRequestWithToken("/BalanceManage/GetBalanceListExport",
				HttpMethod.POST, userToken, queryParam, List.class);
		for (int i = 0; i < tourFeeList.size(); i++) {
			BalanceDto dto=JSON.parseObject(JSON.toJSONString(tourFeeList.get(i)), BalanceDto.class);
			colData1.add(dto.getOrderno());
			colData2.add(dto.getFullplateno());
			colData3.add(dto.getJobnum());
			colData4.add(dto.getDrivername() +" "+ dto.getDriverphone());
			/*if (tourFeeList.get(i).get("drivername")!=null) {
				colData4.add((String) tourFeeList.get(i).get("driverphone"));
			} else {
				colData4.add((String) tourFeeList.get(i).get("drivername") + " " + (String) tourFeeList.get(i).get("driverphone"));
			}*/
			colData5.add(dto.getShortname());
			colData6.add(dto.getOrderamount());
			colData7.add(dto.getPaymentstatus());
			colData8.add(dto.getPaymenttime());
			
			//colData5.add((String) tourFeeList.get(i).get("shortname"));
			//colData6.add(String.valueOf(tourFeeList.get(i).get("orderamount")));
			//colData7.add((String) tourFeeList.get(i).get("paymentstatus"));
			//colData8.add((String) tourFeeList.get(i).get("paymenttime"));
			// 支付渠道
			if ("未结算".equals(dto.getPaymentstatus())) {
				colData9.add("/");
			} else {
				if (dto.getPaymenttype() == null || StringUtils.isBlank(dto.getPaymenttype())) {
					colData9.add("余额支付");
				} else if ("1".equals(dto.getPaymenttype())) {
					colData9.add("微信支付");
				} else if ("2".equals(dto.getPaymenttype())) {
					colData9.add("支付宝支付");
				}
			}
			//colData10.add((String) tourFeeList.get(i).get("settlementtime"));
			colData10.add(dto.getSettlementtime());
			// 交易流水号
			if ("未结算".equals(dto.getPaymentstatus())) {
				colData11.add("/");
			} else {
				if (dto.getPaymenttype() == null || StringUtils.isBlank(dto.getPaymenttype())) {
					colData11.add("/");
				} else if ("1".equals(dto.getPaymenttype()) || "2".equals(dto.getPaymenttype())) {
					colData11.add(dto.getTradeno());
				}
			}
		}
		Excel excel = new Excel();
		// excel文件
		File tempFile = new File("结算管理.xls");
		
		List<String> colName = new ArrayList<String>();
		colName.add("订单号");
		colName.add("车牌号");
		colName.add("资格证号");
		colName.add("司机信息");
		colName.add("服务车企");
		colName.add("行程费用（元）");
		colName.add("结算状态");
		colName.add("收款时间");
		colName.add("支付渠道");
		colName.add("结算时间");
		colName.add("交易流水号");
		excel.setColName(colName);
		colData.put("订单号", colData1);
		colData.put("车牌号", colData2);
		colData.put("资格证号", colData3);
		colData.put("司机信息", colData4);
		colData.put("服务车企", colData5);
		colData.put("行程费用（元）", colData6);
		colData.put("结算状态", colData7);
		colData.put("收款时间", colData8);
		colData.put("支付渠道", colData9);
		colData.put("结算时间", colData10);
		colData.put("交易流水号", colData11);
		excel.setColData(colData);
		
		ExcelExport ee = new ExcelExport(request,response,excel);
		//ee.setSheetName("结算管理");
		ee.createExcel(tempFile);
	}
}
