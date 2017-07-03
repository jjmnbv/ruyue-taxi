package com.szyciov.operate.controller;

import java.io.File;
import java.io.IOException;
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
import com.szyciov.op.entity.OrderExportEntity;
import com.szyciov.util.Constants;
import com.szyciov.util.ExcelExport;
import com.szyciov.util.PageBean;
import com.szyciov.util.TemplateHelper;
@Controller
public class OrderExportController {
	private TemplateHelper templateHelper = new TemplateHelper();
	/**
	 * 进入界面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("OrderExport/Index")
	@ResponseBody
	public ModelAndView getIndex(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = new ModelAndView();
		response.setContentType("text/html;charset=utf-8");
		view.setViewName("resource/orderExport/index");
		return view;
	}
	/**
	 * 查询数据
	 */
	@RequestMapping(value = "OrderExport/GetOrderExportData")
	@ResponseBody
	public PageBean getOrderExportData(
			@RequestBody OrderExportEntity orderExportEntity,
			HttpServletRequest request, HttpServletResponse response) throws IOException{ 
		response.setContentType("application/json;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/OrderExport/GetOrderExportData",
				HttpMethod.POST, userToken, orderExportEntity, PageBean.class);
	}
	/**
	 * 下单人
	 */
	@RequestMapping(value = "/OrderExport/GetOrderperson")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getPassage(@RequestParam String passengers, HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OrderExportEntity orderExportEntity = new OrderExportEntity();
//		User user = getLoginLeUser(request);
		orderExportEntity.setPassengers(passengers);
		return templateHelper.dealRequestWithToken("/OrderExport/GetOrderperson", HttpMethod.POST, userToken,
				orderExportEntity, List.class);
	}
	/**
	 * 司机
	 * @param driver
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/OrderExport/GetAllDriver")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getDriver(@RequestParam String driver, HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OrderExportEntity orderExportEntity = new OrderExportEntity();
//		User user = getLoginLeUser(request);
		orderExportEntity.setDriver(driver);
		return templateHelper.dealRequestWithToken("/OrderExport/GetAllDriver", HttpMethod.POST, userToken,
				orderExportEntity, List.class);
	}
	/**
	 * 所属机构
	 */
	@RequestMapping(value = "/OrderExport/GetAllOrganid")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getAllOrganid(@RequestParam String organid, HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OrderExportEntity orderExportEntity = new OrderExportEntity();
		orderExportEntity.setOrganid(organid);
		return templateHelper.dealRequestWithToken("/OrderExport/GetAllOrganid", HttpMethod.POST, userToken,
				orderExportEntity, List.class);
	}
	/**
	 * 所属机构
	 */
	@RequestMapping(value = "/OrderExport/GetAllOrganid1")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getAllOrganid1(@RequestParam String organid1, HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OrderExportEntity orderExportEntity = new OrderExportEntity();
		orderExportEntity.setOrganid(organid1);
		return templateHelper.dealRequestWithToken("/OrderExport/GetAllOrganid", HttpMethod.POST, userToken,
				orderExportEntity, List.class);
	}
	
	/**
	 * 导出
	 */
	@RequestMapping(value = "OrderExport/ExportOrders")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public void exportOrders(//@RequestParam(value = "cartype", required = false) String cartype,
			@RequestParam(value = "usetype", required = false) String usetype,
			@RequestParam(value = "paymentstatus", required = false) String paymentstatus,
			@RequestParam(value = "passengers", required = false) String passengers,
			@RequestParam(value = "driver", required = false) String driver,
			@RequestParam(value = "ordertype", required = false) String ordertype,
			@RequestParam(value = "organid", required = false) String organid,
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
		List<Object> colData7 = new ArrayList<Object>();
		List<Object> colData8 = new ArrayList<Object>();
		List<Object> colData9 = new ArrayList<Object>();
		List<Object> colData10 = new ArrayList<Object>();
		List<Object> colData11 = new ArrayList<Object>();
		List<Object> colData12 = new ArrayList<Object>();
		List<Object> colData13 = new ArrayList<Object>();
		List<Object> colData14 = new ArrayList<Object>();
		List<Object> colData15 = new ArrayList<Object>();
		List<Object> colData16 = new ArrayList<Object>();
		List<Object> colData17 = new ArrayList<Object>();
		List<Object> colData18 = new ArrayList<Object>();
		List<Object> colData19 = new ArrayList<Object>();
		List<Object> colData20 = new ArrayList<Object>();
		List<Object> colData21 = new ArrayList<Object>();
		List<Object> colData22 = new ArrayList<Object>();
		List<Object> colData23 = new ArrayList<Object>();
		List<Object> colData24 = new ArrayList<Object>();
		List<Object> colData25 = new ArrayList<Object>();
		List<Object> colData26 = new ArrayList<Object>();
		OrderExportEntity queryParam = new OrderExportEntity();
		queryParam.setDriver(driver);
		queryParam.setEndtime(endTime);
		queryParam.setOrdertype(ordertype);
		queryParam.setOrganid(organid);
		queryParam.setPassengers(passengers);
		queryParam.setPaymentstatus(paymentstatus);
		queryParam.setUsetype(usetype);
		queryParam.setStarttime(startTime);
		List<Map> orgOrganExpenses = templateHelper.dealRequestWithToken("/OrderExport/ExportOrders",
				HttpMethod.POST, userToken, queryParam, List.class);
		for (int i = 0; i < orgOrganExpenses.size(); i++) {
			colData1.add((String) orgOrganExpenses.get(i).get("orderno"));
			colData2.add((String) orgOrganExpenses.get(i).get("onaddress"));
			colData3.add((String) orgOrganExpenses.get(i).get("offaddress"));
			colData4.add((String) orgOrganExpenses.get(i).get("estimatedtime"));
			colData5.add((String) orgOrganExpenses.get(i).get("estimatedmileage"));
			colData6.add((String) orgOrganExpenses.get(i).get("undertime"));
			colData7.add((String) orgOrganExpenses.get(i).get("usetime"));
			colData8.add((String) orgOrganExpenses.get(i).get("ordertime"));
			colData9.add((String) orgOrganExpenses.get(i).get("starttime"));
			colData10.add((String) orgOrganExpenses.get(i).get("endtime"));
			/*colData11.add((String) orgOrganExpenses.get(i).get("ordertype"));*/
			colData12.add((String) orgOrganExpenses.get(i).get("runtime"));
			colData13.add((String) orgOrganExpenses.get(i).get("mileage"));
			colData14.add((String) orgOrganExpenses.get(i).get("jobnum"));
			colData15.add((String) orgOrganExpenses.get(i).get("name"));
			colData16.add((String) orgOrganExpenses.get(i).get("phone"));
			colData17.add((String) orgOrganExpenses.get(i).get("plateno"));
			colData18.add((String) orgOrganExpenses.get(i).get("companyid"));
			colData19.add((String) orgOrganExpenses.get(i).get("cartype"));
			colData20.add((String) orgOrganExpenses.get(i).get("passengerphone"));
			colData21.add((String) orgOrganExpenses.get(i).get("passengers"));
			colData22.add((String) orgOrganExpenses.get(i).get("orderstatus"));
			colData23.add((String) orgOrganExpenses.get(i).get("orderamount"));
			colData24.add((String) orgOrganExpenses.get(i).get("paymentstatus"));
			colData25.add((String) orgOrganExpenses.get(i).get("ordersource"));
			colData26.add((String) orgOrganExpenses.get(i).get("usetype"));
		}
		Excel excel = new Excel();
		// excel文件
		File tempFile = new File("订单导出.xls");

		List<String> colName = new ArrayList<String>();
		colName.add("订单号");
		colName.add("上车地点");
		colName.add("下车地点");
		colName.add("预估行程时长(分钟)");
		colName.add("预估行程里程(公里)");
		colName.add("下单时间");
		colName.add("用车时间");
		colName.add("接单时间");
		colName.add("服务开始时间");
		colName.add("服务结束时间");
		/*colName.add("订单类型");*/
		colName.add("行驶时长(分钟)");
		colName.add("行驶里程(公里)");
		colName.add("司机资格证号");
		colName.add("司机姓名");
		colName.add("司机电话");
		colName.add("车牌号");
		colName.add("车企名称");
		colName.add("服务车型");
		colName.add("乘客账号");
		colName.add("乘客名称");
		colName.add("订单状态");
		colName.add("订单金额");
		colName.add("支付状态");
		colName.add("订单来源");
		colName.add("订单类型");
		excel.setColName(colName);
		colData.put("订单号", colData1);
		colData.put("上车地点", colData2);
		colData.put("下车地点", colData3);
		colData.put("预估行程时长(分钟)", colData4);
		colData.put("预估行程里程(公里)", colData5);
		colData.put("下单时间", colData6);
		colData.put("用车时间", colData7);
		colData.put("接单时间", colData8);
		colData.put("服务开始时间", colData9);
		colData.put("服务结束时间", colData10);
		/*colData.put("订单类型", colData11);*/
		colData.put("行驶时长(分钟)", colData12);
		colData.put("行驶里程(公里)", colData13);
		colData.put("司机资格证号", colData14);
		colData.put("司机姓名", colData15);
		colData.put("司机电话", colData16);
		colData.put("车牌号", colData17);
		colData.put("车企名称", colData18);
		colData.put("服务车型", colData19);
		colData.put("乘客账号", colData20);
		colData.put("乘客名称", colData21);
		colData.put("订单状态", colData22);
		colData.put("订单金额", colData23);
		colData.put("支付状态", colData24);
		colData.put("订单来源", colData25);
		colData.put("订单类型", colData26);
		excel.setColData(colData);
		ExcelExport ee = new ExcelExport(request, response, excel);
		ee.setSheetMaxRow(10000);
		ee.createExcel(tempFile);
	}

}
