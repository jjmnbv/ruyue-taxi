package com.szyciov.organ.controller;

import java.io.File;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.driver.param.OrderCostParam;
import com.szyciov.entity.Excel;
import com.szyciov.entity.OrderCost;
import com.szyciov.entity.Retcode;
import com.szyciov.enums.OrderEnum;
import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.org.entity.OrgOrderDetails;
import com.szyciov.org.entity.OrgUser;
import com.szyciov.org.param.OrgOrderQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.ExcelExport;
import com.szyciov.util.PageBean;
import com.szyciov.util.StringUtil;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class OrgIndexController extends BaseController {
	private static final Logger logger = Logger.getLogger(OrgIndexController.class);
	private TemplateHelper templateHelper = new TemplateHelper();
	@RequestMapping("OrgIndex/Index")
	@ResponseBody
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView();
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OrgUser user = getLoginOrgUser(request);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("organid", user.getOrganId());
		params.put("userid", user.getId());
		List<LeLeasescompany> leLeasecompany = templateHelper.dealRequestWithToken("/OrgIndex/GetQueryCompany", HttpMethod.POST, userToken,params,
				List.class);
		mav.addObject("leLeasecompany", leLeasecompany);
		mav.setViewName("resource/orgIndex/index");
		return mav;
	}
	
	@RequestMapping("OrgIndex/GetOrgderByQuery")
	@ResponseBody
	public PageBean getOrgderByQuery(@RequestBody OrgOrderQueryParam orgOrderQueryParam,HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView();
		String organId = getLoginOrgUser(request).getOrganId();
		orgOrderQueryParam.setKey(organId);
		orgOrderQueryParam.setUserid(this.getLoginOrgUser(request).getId());
		orgOrderQueryParam.setUsertype(getLoginOrgUser(request).getUserType());
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/OrgIndex/GetOrgderByQuery", HttpMethod.POST, userToken,orgOrderQueryParam,
				PageBean.class);
	}
	
	@RequestMapping("OrgIndex/Details")
	@ResponseBody
	public ModelAndView details(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView();
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OrgOrderDetails orgOrderDetails1 = templateHelper.dealRequestWithToken("/OrgIndex/GetById/{id}", HttpMethod.GET, userToken,null,
				OrgOrderDetails.class,request.getParameter("id"));
//		if(orgOrderDetails.getPricecopy()!=null){
//			OrderCost orderCost = StringUtil.parseJSONToBean(orgOrderDetails1.getPricecopy(), OrderCost.class);
//			mav.addObject("orderCost", orderCost);
//		}
		orgOrderDetails1.setEstimatedcost(changeDouble(Double.valueOf(orgOrderDetails1.getEstimatedcost())).toString());
		orgOrderDetails1.setOrderamount(changeDouble(Double.valueOf(orgOrderDetails1.getOrderamount())).toString());
		mav.addObject("orgOrderDetails", orgOrderDetails1);
//		mav.setViewName("resource/orgIndex/details");
		OrderCostParam ocp = new OrderCostParam();
		ocp.setOrderno(request.getParameter("id"));
		ocp.setUsetype(orgOrderDetails1.getUsetype());
		ocp.setOrdertype(orgOrderDetails1.getOrdertype());
		if(orgOrderDetails1.getOrderstatus().equals("0") || orgOrderDetails1.getOrderstatus().equals("1") || orgOrderDetails1.getOrderstatus().equals("2") || orgOrderDetails1.getOrderstatus().equals("3") || orgOrderDetails1.getOrderstatus().equals("4") || orgOrderDetails1.getOrderstatus().equals("5") || orgOrderDetails1.getOrderstatus().equals("8")){
			String pricecopy = orgOrderDetails1.getPricecopy();
			JSONObject json = JSONObject.fromObject(pricecopy);
			OrderCost oc = StringUtil.parseJSONToBean(json.toString(), OrderCost.class);
			Map<String, String> orgOrderDetails = new HashMap<>();
	//		{"orderno":"B674A989-7D81-4308-B18A-C693B45DCA2D","cost":"10.0元","mileage":"0.0公里","startprice":"10.0元","rangeprice":"2.0元/公里","timeprice":"3.0元/分钟","rangecost":"0.0元","timecost":"0.0元","times":"0分钟","status":0,"message":"请求成功,无异常"}
			orgOrderDetails.put("orderno", oc.getOrderno());
//			orgOrderDetails.put("cost", oc.getCost()+"元");
//			orgOrderDetails.put("mileage", oc.getMileage()/1000+"公里");
//			orgOrderDetails.put("startprice", oc.getStartprice()+"元");
//			orgOrderDetails.put("rangeprice", oc.getRangeprice()+"元");
//			orgOrderDetails.put("timeprice", oc.getTimeprice()+"元/分钟");
//			orgOrderDetails.put("rangecost", oc.getRangecost()+"元");
//			orgOrderDetails.put("timecost", oc.getTimecost()+"元");
//			orgOrderDetails.put("times", StringUtil.formatCostTime(oc.getTimes()/60));
//			//空驶公里
//			orgOrderDetails.put("deadheadmileage",oc.getMileage()/1000-oc.getDeadheadmileage()+"公里");
//			//回空费价
//			orgOrderDetails.put("deadheadcost",(oc.getMileage()/1000-oc.getDeadheadmileage())*oc.getDeadheadprice()+"元");
//			//夜间费价  xx公里/元
//			orgOrderDetails.put("nightcost",oc.getNighteprice()*oc.getMileage()/1000+"元");
//			if(oc.getMileage()/1000-oc.getDeadheadmileage() > 0){
//				orgOrderDetails.put("res", "resY");
//			}
			//起步价
			orgOrderDetails.put("startprice", changeDouble(oc.getStartprice())+"元");
			//每公里单价
			orgOrderDetails.put("rangeprice", oc.getRangeprice()+"元/公里");
			//每分钟单价
			orgOrderDetails.put("timeprice", oc.getTimeprice()+"元/分钟");
			//预估时长（分钟）
			orgOrderDetails.put("times", orgOrderDetails1.getEstimatedtime()+"分钟");
			//预估时长（分钟）  费用
			orgOrderDetails.put("timecost", changeDouble(Double.valueOf(oc.getTimecost()))+"元");
			//预估里程(公里)
			orgOrderDetails.put("mileage", changeDouble(Double.valueOf(orgOrderDetails1.getEstimatedmileage()))+"公里");
			//预估里程(公里) 费用
			orgOrderDetails.put("rangecost", changeDouble(Double.valueOf(orgOrderDetails1.getEstimatedmileage())*oc.getRangeprice())+"元");
			//预估费用(元)
			orgOrderDetails.put("cost", changeDouble(Double.valueOf(orgOrderDetails1.getEstimatedcost()))+"元");
			//空驶公里
			orgOrderDetails.put("deadheadmileage",changeDouble(oc.getMileage()/1000-oc.getDeadheadmileage())+"公里");
			//回空费价
			orgOrderDetails.put("deadheadcost",changeDouble(changeDouble(oc.getMileage()/1000-oc.getDeadheadmileage())*oc.getDeadheadprice())+"元");
			//夜间费价  xx公里/元  给个默认
			orgOrderDetails.put("nightcost","0.0元");
			if(oc.getMileage()/1000-oc.getDeadheadmileage() > 0){
				orgOrderDetails.put("res", "resY");
			}
			mav.addObject("orderCost", orgOrderDetails);
		}else{
			JSONObject json = templateHelper.dealRequestWithFullUrlToken(SystemConfig.getSystemProperty("carserviceApiUrl")+"/OrderApi/GetOrderCost", HttpMethod.POST, userToken,ocp,
					JSONObject.class);
			Map<String, String> orgOrderDetails = new HashMap<>();
	//		{"orderno":"B674A989-7D81-4308-B18A-C693B45DCA2D","cost":"10.0元","mileage":"0.0公里","startprice":"10.0元","rangeprice":"2.0元/公里","timeprice":"3.0元/分钟","rangecost":"0.0元","timecost":"0.0元","times":"0分钟","status":0,"message":"请求成功,无异常"}
			orgOrderDetails.put("orderno", json.getString("orderno"));
			orgOrderDetails.put("cost", json.getString("cost").substring(0,json.getString("cost").length()-1));
			orgOrderDetails.put("mileage", json.getString("mileage"));
			orgOrderDetails.put("startprice", json.getString("startprice"));
			orgOrderDetails.put("rangeprice", json.getString("rangeprice").substring(0, json.getString("rangeprice").length()-3));
			orgOrderDetails.put("timeprice", json.getString("timeprice"));
			orgOrderDetails.put("rangecost", json.getString("rangecost"));
			orgOrderDetails.put("timecost", json.getString("timecost"));
			orgOrderDetails.put("times", json.getString("times"));
			//空驶公里
			orgOrderDetails.put("deadheadmileage",json.getString("realdeadheadmileage"));
			//回空费价
			orgOrderDetails.put("deadheadcost",json.getString("deadheadcost"));
			//夜间费价  xx公里/元
			orgOrderDetails.put("nightcost",json.getString("nightcost"));
			orgOrderDetails.put("res", "resY");
			mav.addObject("orderCost", orgOrderDetails);
		}
		if(request.getParameter("bill")!=null){
			mav.addObject("bill",request.getParameter("bill"));
			mav.addObject("billsId",request.getParameter("billsId"));
		}
		mav.setViewName("resource/orgIndex/details");
		return mav;
	}
	public Double changeDouble(Double dou){
		DecimalFormat df = new DecimalFormat("0.0");
		return Double.valueOf(df.format(dou));
    }
	@RequestMapping("/OrgIndex/ExportExcel")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
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
		List<Object> colData27 = new ArrayList<Object>();
		List<Object> colData28 = new ArrayList<Object>();
		List<Object> colData29 = new ArrayList<Object>();
		List<Object> colData30 = new ArrayList<Object>();
		List<Object> colData31 = new ArrayList<Object>();
		List<Object> colData32 = new ArrayList<Object>();
		List<Object> colData33 = new ArrayList<Object>();
		List<Object> colData34 = new ArrayList<Object>();
		List<Object> colData35 = new ArrayList<Object>();
		List<Object> colData36 = new ArrayList<Object>();
		List<Object> colData37 = new ArrayList<Object>();
		List<Object> colData38 = new ArrayList<Object>();
		List<Object> colData39 = new ArrayList<Object>();
		OrgOrderQueryParam oo = new OrgOrderQueryParam();
		String organId = getLoginOrgUser(request).getOrganId();
		String queryCompany = request.getParameter("queryCompany");
		String queryOrderNo = request.getParameter("queryOrderNo");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String queryUserMessage = request.getParameter("queryUserMessage");
		String queryOrderTemp = request.getParameter("queryOrderTemp");
		String queryVehicleMode = request.getParameter("queryVehicleMode");
		String queryPaymentMethod = request.getParameter("queryPaymentMethod");
		oo.setKey(organId);
		oo.setQueryCompany(queryCompany);
		oo.setQueryOrderNo(queryOrderNo);
		oo.setStartTime(startTime);
		oo.setEndTime(endTime);
		oo.setQueryUserMessage(queryUserMessage);
		oo.setQueryOrderTemp(queryOrderTemp);
		oo.setQueryVehicleMode(queryVehicleMode);
		oo.setQueryPaymentMethod(queryPaymentMethod);
		oo.setUserid(this.getLoginOrgUser(request).getId());
		oo.setUsertype(getLoginOrgUser(request).getUserType());
		List<Map> orgOrder = templateHelper.dealRequestWithToken("/OrgIndex/ExportExcel", HttpMethod.POST,
				userToken, oo, List.class);
		for(int i=0;i<orgOrder.size();i++){
			if(orgOrder.get(i).get("orderno") != null){
				colData1.add(orgOrder.get(i).get("orderno"));
			}else{
				colData1.add("");
			}
			if(orgOrder.get(i).get("nickname") != null){
				colData2.add(orgOrder.get(i).get("nickname"));
			}else{
				colData2.add("");
			}
			if(orgOrder.get(i).get("account") != null){
				colData3.add(orgOrder.get(i).get("account"));
			}else{
				colData3.add("");
			}
			if(orgOrder.get(i).get("deptid") != null){
				colData4.add(orgOrder.get(i).get("deptid"));
			}else{
				colData4.add("");
			}
			if(orgOrder.get(i).get("passengers") != null){
				colData5.add(orgOrder.get(i).get("passengers"));
			}else{
				colData5.add("");
			}
			if(orgOrder.get(i).get("passengerphone") != null){
				colData6.add(orgOrder.get(i).get("passengerphone"));
			}else{
				colData6.add("");
			}
			if(orgOrder.get(i).get("usetime") != null){
				colData7.add(getStringDate(Long.valueOf(orgOrder.get(i).get("usetime").toString())));
			}else{
				colData7.add("");
			}
			if(orgOrder.get(i).get("onaddress") != null){
				colData8.add(orgOrder.get(i).get("onaddress"));
			}else{
				colData8.add("");
			}
			if(orgOrder.get(i).get("offaddress") != null){
				colData9.add(orgOrder.get(i).get("offaddress"));
			}else{
				colData9.add("");
			}
			if(orgOrder.get(i).get("paymethod") != null){
				colData10.add(orgOrder.get(i).get("paymethod"));
			}else{
				colData10.add("");
			}
			if(orgOrder.get(i).get("ordersource") != null){
				colData11.add(orgOrder.get(i).get("ordersource"));
			}else{
				colData11.add("");
			}
			if(orgOrder.get(i).get("ordertype") != null){
				colData12.add(orgOrder.get(i).get("ordertype"));
			}else{
				colData12.add("");
			}
			if(orgOrder.get(i).get("modelName") != null){
				colData13.add(orgOrder.get(i).get("modelName"));
			}else{
				colData13.add("");
			}
			if(orgOrder.get(i).get("vehiclessubjecttype") != null){
				colData14.add(orgOrder.get(i).get("vehiclessubjecttype"));
			}else{
				colData14.add("");
			}
			if(orgOrder.get(i).get("vehiclessubject") != null){
				colData15.add(orgOrder.get(i).get("vehiclessubject"));
			}else{
				colData15.add("");
			}
			if(orgOrder.get(i).get("tripremark") != null){
				colData16.add(orgOrder.get(i).get("tripremark"));
			}else{
				colData16.add("");
			}
			if(orgOrder.get(i).get("companyName") != null){
				colData17.add("如约的士");
			}else{
				colData17.add("");
			}
			if(orgOrder.get(i).get("starttime") != null){
				colData18.add(orgOrder.get(i).get("starttime"));
			}else{
				colData18.add("");
			}
			if(orgOrder.get(i).get("endtime") != null){
				colData19.add(orgOrder.get(i).get("endtime"));
			}else{
				colData19.add("");
			}
			JSONObject json = getJson((String)orgOrder.get(i).get("orderno"),request);
			if(!json.getString("cost").equals("")){
				colData20.add("¥"+json.getString("cost").substring(0, json.getString("cost").length()-1));
			}else{
				colData20.add("");
			}
			if(!json.getString("startprice").equals("")){
				colData21.add(json.getString("startprice").substring(0, json.getString("startprice").length()-1));
			}else{
				colData21.add("");
			}
			if(!json.getString("mileage").equals("")){
				colData22.add(json.getString("mileage").substring(0, json.getString("mileage").length()-2));
			}else{
				colData22.add("");
			}
			if(!json.getString("rangecost").equals("")){
				colData23.add(json.getString("rangecost").substring(0, json.getString("rangecost").length()-1));
			}else{
				colData23.add("");
			}
			if(!json.getString("times").equals("")){
				colData24.add(json.getString("times").substring(0, json.getString("times").length()-2));
			}else{
				colData24.add("");
			}
			if(!json.getString("timecost").equals("")){
				colData25.add(json.getString("timecost").substring(0, json.getString("timecost").length()-1));
			}else{
				colData25.add("");
			}
			if(!json.getString("deadheadcost").equals("")){
				colData38.add(json.getString("deadheadcost").substring(0, json.getString("deadheadcost").length()-1));
			}else{
				colData38.add("");
			}
			if(!json.getString("nightcost").equals("")){
				colData39.add(json.getString("nightcost").substring(0, json.getString("nightcost").length()-1));
			}else{
				colData39.add("");
			}
			if(orgOrder.get(i).get("orderamount") != null){
				colData26.add("¥"+orgOrder.get(i).get("orderamount"));
			}else{
				colData26.add("");
			}
			if(orgOrder.get(i).get("oncity") != null){
				colData27.add(orgOrder.get(i).get("oncity"));
			}else{
				colData27.add("");
			}
			if(orgOrder.get(i).get("estimatedtime") != null){
				colData28.add(orgOrder.get(i).get("estimatedtime"));
			}else{
				colData28.add("");
			}
			if(orgOrder.get(i).get("estimatedmileage") != null){
				colData29.add(orgOrder.get(i).get("estimatedmileage"));
			}else{
				colData29.add("");
			}
			if(orgOrder.get(i).get("undertime") != null){
				colData30.add(orgOrder.get(i).get("undertime"));
			}else{
				colData30.add("");
			}
			if(orgOrder.get(i).get("ordertime") != null){
				colData31.add(orgOrder.get(i).get("ordertime"));
			}else{
				colData31.add("");
			}
			if(orgOrder.get(i).get("orderStatusShow") != null){
				colData32.add(orgOrder.get(i).get("orderStatusShow"));
			}else{
				colData32.add("");
			}
			if(orgOrder.get(i).get("paymentstatus") != null){
				colData33.add(orgOrder.get(i).get("paymentstatus"));
			}else{
				colData33.add("");
			}
			if(orgOrder.get(i).get("paytype") != null){
				colData34.add(orgOrder.get(i).get("paytype"));
			}else{
				colData34.add("");
			}
			if(orgOrder.get(i).get("ordertype") != null){
				colData35.add(orgOrder.get(i).get("ordertype"));
			}else{
				colData35.add("");
			}
			if(orgOrder.get(i).get("driverName") != null){
				colData36.add(orgOrder.get(i).get("driverName"));
			}else{
				colData36.add("");
			}
			if(orgOrder.get(i).get("jobnum") != null){
				colData37.add(orgOrder.get(i).get("jobnum"));
			}else{
				colData37.add("");
			}
		}
		Excel excel = new Excel();
		// excel文件
		File tempFile = new File("订单管理.xls");
		//订单号	下单人姓名	下单人电话	下单人部门	乘车人姓名	乘车人电话	上车地址	下车地址	预估行驶时长（分钟）	预估行驶里程（公里）	下单时间	用车时间	
		//接单时间	开始时间	结束时间	订单状态	支付方式	支付状态	支付渠道	订单来源	用车方式	订单类型	服务车型	用车事由	事由说明	行程备注	
		//总金额（元）	起步价（元）	行驶里程（公里）	行驶里程费（元）	用车时长（分钟）	用车时长费（元）	实付金额（元）	司机信息	资格证号	服务车企	用车城市
		List<String> colName = new ArrayList<String>();
		colName.add("订单号");
		colName.add("下单人姓名");
		colName.add("下单人电话");
		colName.add("下单人部门");
		colName.add("乘车人姓名");
		colName.add("乘车人电话");
		colName.add("上车地址");
		colName.add("下车地址");
		colName.add("预估行驶时长（分钟）");
		colName.add("预估行驶里程（公里）");
		colName.add("下单时间");
		colName.add("用车时间");
		colName.add("接单时间");
		colName.add("开始时间");
		colName.add("结束时间");
		colName.add("订单状态");
		colName.add("支付方式");
		colName.add("支付状态");
		colName.add("支付渠道");
		colName.add("订单来源");
		colName.add("用车方式");
		colName.add("订单类型");
		colName.add("服务车型");
		colName.add("用车事由");
		colName.add("事由说明");
		colName.add("行程备注");
		colName.add("总金额（元）");
		colName.add("起步价（元）");
		colName.add("行驶里程（公里）");
		colName.add("行驶里程费（元）");
		colName.add("用车时长（分钟）");
		colName.add("用车时长费（元）");
		colName.add("空驶费（元）");
		colName.add("夜间费（元）");
		colName.add("实付金额（元）");
		colName.add("司机信息");
		colName.add("资格证号");
		colName.add("服务车企");
		colName.add("用车城市");
		excel.setColName(colName);
		colData.put("订单号", colData1);
		colData.put("下单人姓名", colData2);
		colData.put("下单人电话", colData3);
		colData.put("下单人部门", colData4);
		colData.put("乘车人姓名", colData5);
		colData.put("乘车人电话", colData6);
		colData.put("上车地址", colData8);
		colData.put("下车地址", colData9);
		colData.put("预估行驶时长（分钟）",colData28);
		colData.put("预估行驶里程（公里）",colData29);
		colData.put("下单时间",colData30);
		colData.put("用车时间", colData7);
		colData.put("接单时间",colData31);
		colData.put("开始时间",colData18);
		colData.put("结束时间",colData19);
		colData.put("订单状态",colData32);
		colData.put("支付方式", colData10);
		colData.put("支付状态",colData33);
		colData.put("支付渠道",colData34);
		colData.put("订单来源", colData11);
		colData.put("用车方式", colData12);
		colData.put("订单类型",colData35);
		colData.put("服务车型", colData13);
		colData.put("用车事由", colData14);
		colData.put("事由说明", colData15);
		colData.put("行程备注", colData16);
		colData.put("总金额（元）", colData20);
		colData.put("起步价（元）", colData21);
		colData.put("行驶里程（公里）", colData22);
		colData.put("行驶里程费（元）", colData23);
		colData.put("用车时长（分钟）", colData24);
		colData.put("用车时长费（元）", colData25);
		colData.put("空驶费（元）", colData38);
		colData.put("夜间费（元）", colData39);
		colData.put("实付金额（元）", colData26);
		colData.put("司机信息",colData36);
		colData.put("资格证号",colData37);
		colData.put("服务车企", colData17);
		colData.put("用车城市", colData27);
		excel.setColData(colData);
		
		ExcelExport ee = new ExcelExport(request,response,excel);
//		ee.setSheetMaxRow(colData.size());
		ee.createExcel(tempFile);
		
//		ExcelExportController excelExportController = new ExcelExportController();
//		excelExportController.exportExcel(request, response, excel);
	}
	/*@RequestMapping("/OrgIndex/ExportExcel")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
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
		List<Object> colData11 = new ArrayList<Object>();
		List<Object> colData13 = new ArrayList<Object>();
		List<Object> colData17 = new ArrayList<Object>();
		List<Object> colData18 = new ArrayList<Object>();
		List<Object> colData19 = new ArrayList<Object>();
		List<Object> colData20 = new ArrayList<Object>();
		List<Object> colData22 = new ArrayList<Object>();
		List<Object> colData24 = new ArrayList<Object>();
		List<Object> colData28 = new ArrayList<Object>();
		List<Object> colData29 = new ArrayList<Object>();
		List<Object> colData30 = new ArrayList<Object>();
		List<Object> colData31 = new ArrayList<Object>();
		List<Object> colData32 = new ArrayList<Object>();
		List<Object> colData33 = new ArrayList<Object>();
		List<Object> colData35 = new ArrayList<Object>();
		List<Object> colData37 = new ArrayList<Object>();
		OrgOrderQueryParam oo = new OrgOrderQueryParam();
		String organId = getLoginOrgUser(request).getOrganId();
		String queryCompany = request.getParameter("queryCompany");
		String queryOrderNo = request.getParameter("queryOrderNo");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String queryUserMessage = request.getParameter("queryUserMessage");
		String queryOrderTemp = request.getParameter("queryOrderTemp");
		String queryVehicleMode = request.getParameter("queryVehicleMode");
		String queryPaymentMethod = request.getParameter("queryPaymentMethod");
		oo.setKey(organId);
		oo.setQueryCompany(queryCompany);
		oo.setQueryOrderNo(queryOrderNo);
		oo.setStartTime(startTime);
		oo.setEndTime(endTime);
		oo.setQueryUserMessage(queryUserMessage);
		oo.setQueryOrderTemp(queryOrderTemp);
		oo.setQueryVehicleMode(queryVehicleMode);
		oo.setQueryPaymentMethod(queryPaymentMethod);
		oo.setUserid(this.getLoginOrgUser(request).getId());
		oo.setUsertype(getLoginOrgUser(request).getUserType());
		List<Map> orgOrder = templateHelper.dealRequestWithToken("/OrgIndex/ExportExcel", HttpMethod.POST,
				userToken, oo, List.class);
		for(int i=0;i<orgOrder.size();i++){
			if(orgOrder.get(i).get("orderno") != null){
				colData1.add(orgOrder.get(i).get("orderno"));
			}else{
				colData1.add("");
			}
			if(orgOrder.get(i).get("name") != null){
				colData2.add(orgOrder.get(i).get("name"));
			}else{
				colData2.add("");
			}
			if(orgOrder.get(i).get("phone") != null){
				colData3.add(orgOrder.get(i).get("phone"));
			}else{
				colData3.add("");
			}
			if(orgOrder.get(i).get("plateno") != null){
				colData4.add(orgOrder.get(i).get("plateno"));
			}else{
				colData4.add("");
			}
			if(orgOrder.get(i).get("passengers") != null){
				colData5.add(orgOrder.get(i).get("passengers"));
			}else{
				colData5.add("");
			}
			if(orgOrder.get(i).get("passengerphone") != null){
				colData6.add(orgOrder.get(i).get("passengerphone"));
			}else{
				colData6.add("");
			}
			if(orgOrder.get(i).get("usetime") != null){
				colData7.add(getStringDate(Long.valueOf(orgOrder.get(i).get("usetime").toString())));
			}else{
				colData7.add("");
			}
			if(orgOrder.get(i).get("onaddress") != null){
				colData8.add(orgOrder.get(i).get("onaddress"));
			}else{
				colData8.add("");
			}
			if(orgOrder.get(i).get("offaddress") != null){
				colData9.add(orgOrder.get(i).get("offaddress"));
			}else{
				colData9.add("");
			}
			if(orgOrder.get(i).get("ordersource") != null){
				colData11.add(orgOrder.get(i).get("ordersource"));
			}else{
				colData11.add("");
			}
			if(orgOrder.get(i).get("modelName") != null){
				colData13.add(orgOrder.get(i).get("modelName"));
			}else{
				colData13.add("");
			}
			if(orgOrder.get(i).get("companyName") != null){
				colData17.add(orgOrder.get(i).get("companyName"));
			}else{
				colData17.add("");
			}
			if(orgOrder.get(i).get("starttime") != null){
				colData18.add(orgOrder.get(i).get("starttime"));
			}else{
				colData18.add("");
			}
			if(orgOrder.get(i).get("endtime") != null){
				colData19.add(orgOrder.get(i).get("endtime"));
			}else{
				colData19.add("");
			}
			JSONObject json = getJson((String)orgOrder.get(i).get("orderno"),request);
			if(!json.getString("cost").equals("")){
				colData20.add("¥"+json.getString("cost").substring(0, json.getString("cost").length()-1));
			}else{
				colData20.add("");
			}
			if(!json.getString("mileage").equals("")){
				colData22.add(json.getString("mileage").substring(0, json.getString("mileage").length()-2));
			}else{
				colData22.add("");
			}
			if(!json.getString("times").equals("")){
				colData24.add(json.getString("times").substring(0, json.getString("times").length()-2));
			}else{
				colData24.add("");
			}
			if(orgOrder.get(i).get("estimatedtime") != null){
				colData28.add(orgOrder.get(i).get("estimatedtime"));
			}else{
				colData28.add("");
			}
			if(orgOrder.get(i).get("estimatedmileage") != null){
				colData29.add(orgOrder.get(i).get("estimatedmileage"));
			}else{
				colData29.add("");
			}
			if(orgOrder.get(i).get("undertime") != null){
				colData30.add(orgOrder.get(i).get("undertime"));
			}else{
				colData30.add("");
			}
			if(orgOrder.get(i).get("ordertime") != null){
				colData31.add(orgOrder.get(i).get("ordertime"));
			}else{
				colData31.add("");
			}
			if(orgOrder.get(i).get("orderStatusShow") != null){
				colData32.add(orgOrder.get(i).get("orderStatusShow"));
			}else{
				colData32.add("");
			}
			if(orgOrder.get(i).get("paymentstatus") != null){
				colData33.add(orgOrder.get(i).get("paymentstatus"));
			}else{
				colData33.add("");
			}
			if(orgOrder.get(i).get("ordertype") != null){
				colData35.add(orgOrder.get(i).get("ordertype"));
			}else{
				colData35.add("");
			}
			if(orgOrder.get(i).get("jobnum") != null){
				colData37.add(orgOrder.get(i).get("jobnum"));
			}else{
				colData37.add("");
			}
		}
		Excel excel = new Excel();
		// excel文件
		File tempFile = new File("订单管理.xls");
		//订单号	下单人姓名	下单人电话	下单人部门	乘车人姓名	乘车人电话	上车地址	下车地址	预估行驶时长（分钟）	预估行驶里程（公里）	下单时间	用车时间	
		//接单时间	开始时间	结束时间	订单状态	支付方式	支付状态	支付渠道	订单来源	用车方式	订单类型	服务车型	用车事由	事由说明	行程备注	
		//总金额（元）	起步价（元）	行驶里程（公里）	行驶里程费（元）	用车时长（分钟）	用车时长费（元）	实付金额（元）	司机信息	资格证号	服务车企	用车城市
		List<String> colName = new ArrayList<String>();
		colName.add("订单号");
		colName.add("上车地点");
		colName.add("下车地点");
		colName.add("预估行驶时长（分钟）");
		colName.add("预估行驶里程（公里）");
		colName.add("下单时间");
		colName.add("用车时间");
		colName.add("接单时间");
		colName.add("服务开始时间");
		colName.add("服务结束时间");
		colName.add("行驶时长");
		colName.add("行驶里程");
		colName.add("司机资格证号");
		colName.add("司机姓名");
		colName.add("司机电话");
		colName.add("车牌号");
		colName.add("车企名称");
		colName.add("服务车型");
		colName.add("乘客账号");
		colName.add("乘客姓名");
		colName.add("订单状态");
		colName.add("订单金额");
		colName.add("支付状态");
		colName.add("订单来源");
		colName.add("订单类型");
		excel.setColName(colName);
		colData.put("订单号", colData1);
		colData.put("上车地点", colData8);
		colData.put("下车地点", colData9);
		colData.put("预估行驶时长（分钟）",colData28);
		colData.put("预估行驶里程（公里）",colData29);
		colData.put("下单时间",colData30);
		colData.put("用车时间", colData7);
		colData.put("接单时间",colData31);
		colData.put("服务开始时间",colData18);
		colData.put("服务结束时间",colData19);
		colData.put("行驶时长", colData24);
		colData.put("行驶里程", colData22);
		colData.put("司机资格证号",colData37);
		colData.put("司机姓名",colData2);
		colData.put("司机电话",colData3);
		colData.put("车牌号",colData4);
		colData.put("车企名称", colData17);
		colData.put("服务车型", colData13);
		colData.put("乘客账号", colData6);
		colData.put("乘客姓名", colData5);
		colData.put("订单状态",colData32);
		colData.put("订单金额", colData20);
		colData.put("支付状态",colData33);
		colData.put("订单来源", colData11);
		colData.put("订单类型",colData35);
		excel.setColData(colData);
		
		ExcelExport ee = new ExcelExport(request,response,excel);
		ee.createExcel(tempFile);
	}*/
	
	@RequestMapping(value = "OrgIndex/GetOrgOrderTraceData")
	@ResponseBody
	public Map<String, Object> getOrgOrderTraceData(@RequestParam String orderno,
			@RequestParam String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		if(endDate == null || "".equalsIgnoreCase(endDate)) {
			endDate = StringUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
		}
		OrgOrderDetails o = templateHelper.dealRequestWithToken("/OrgIndex/GetById/{id}", HttpMethod.GET, userToken,null,
				OrgOrderDetails.class,orderno);
		if (o.getOrdertype() != null && !o.getOrdertype().equals("")) {
			if (o.getOrdertype().equals("约车")) {
				o.setOrdertype(OrderEnum.ORDERTYPE_RESERVE.code);
			}
		}
		if (o.getOrdertype() != null && !o.getOrdertype().equals("")) {
			if (o.getOrdertype().equals("接机")) {
				o.setOrdertype(OrderEnum.ORDERTYPE_PICKUP.code);
			}
		}
		if (o.getOrdertype() != null && !o.getOrdertype().equals("")) {
			if (o.getOrdertype().equals("送机")) {
				o.setOrdertype(OrderEnum.ORDERTYPE_DROPOFF.code);
			}
		}
		if (o.getOrdertype() != null && !o.getOrdertype().equals("")) {
			if (o.getOrdertype().equals("出租车")) {
				o.setOrdertype(OrderEnum.ORDERTYPE_TAXI.code);
			}
		}
		return templateHelper.dealRequestWithFullUrlToken(SystemConfig.getSystemProperty("carserviceApiUrl") + 
				"/BaiduApi/GetTraceData/?orderno={orderno}&ordertype={ordertype}&usetype={usetype}", 
				HttpMethod.GET, userToken, 
				null, Map.class, orderno, o.getOrdertype(), o.getUsetype());
	}
	//得到  费用说明
	public JSONObject getJson(String id,HttpServletRequest request){
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OrgOrderDetails orgOrderDetails1 = templateHelper.dealRequestWithToken("/OrgIndex/GetById/{id}", HttpMethod.GET, userToken,null,
				OrgOrderDetails.class,id);
		OrderCostParam ocp = new OrderCostParam();
		ocp.setOrderno(id);
		ocp.setUsetype(orgOrderDetails1.getUsetype());
		ocp.setOrdertype(orgOrderDetails1.getOrdertype());
		JSONObject json = templateHelper.dealRequestWithFullUrlToken(SystemConfig.getSystemProperty("carserviceApiUrl")+"/OrderApi/GetOrderCost", HttpMethod.POST, userToken,ocp,
				JSONObject.class);
		return json;
	}
	/**
	 * 根据订单号获取已接单订单信息
	 * @param orderno
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("OrgIndex/GetOrgOrderByOrderno")
	@ResponseBody
	public Map<String, Object> getOrgOrderByOrderno(@RequestParam String orderno, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/OrgIndex/GetOrgOrderByOrderno/{orderno}", HttpMethod.GET, userToken, 
				null, Map.class, orderno);
	}
	/**
	   * 
	   * 
	   * @return返回字符串格式 yyyy-MM-dd HH mm
	   */
	public static String getStringDate(Date date) {
	   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	   String dateString = formatter.format(date);
	   return dateString;
	}
	
	public static String getStringDate(long now) {
		Date d = new Date(now);
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTimeInMillis(now);
//		return formatter.format(calendar.getTime());
		return formatter.format(d);
	}
	
	@RequestMapping("OrgIndex/GetServiceOrder")
	@ResponseBody
	public JSONObject getServiceOrder(HttpServletRequest request,HttpServletResponse response,@RequestParam String userId,@RequestParam String organId,
			@RequestParam String usertype){
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
//		String userId = "3D5B0245-87F3-46AD-B062-61D6F8B89CE5";
//		String organId = "F9FD2C47-CEF5-4AE0-93C5-A07BDEA4E4A8";
//		String usertype = "1";
		OrgOrderQueryParam orgOrderQueryParam = new OrgOrderQueryParam();
		orgOrderQueryParam.setKey(organId);
		orgOrderQueryParam.setUserid(userId);
		orgOrderQueryParam.setUsertype(usertype);
		if(request.getParameter("iDisplayStart") != null && !request.getParameter("iDisplayStart").equals("")){
			orgOrderQueryParam.setiDisplayStart(Integer.valueOf(request.getParameter("iDisplayStart")));
		}else{
			orgOrderQueryParam.setiDisplayStart(0);
		}
		if(request.getParameter("iDisplayLength") != null && !request.getParameter("iDisplayLength").equals("")){
			orgOrderQueryParam.setiDisplayLength(Integer.valueOf(request.getParameter("iDisplayLength")));
		}else{
			orgOrderQueryParam.setiDisplayLength(10);
		}
		response.setContentType("text/html;charset=utf-8");
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		try {
			json = templateHelper.dealRequestWithToken("/OrgIndex/GetServiceOrder", HttpMethod.POST, userToken,orgOrderQueryParam,
					JSONArray.class);
			jo.put("data", json);
		} catch (Exception e) {
			jo.put("status", Retcode.EXCEPTION.code);
			jo.put("message", Retcode.EXCEPTION.msg);
			json.add(jo);
		}
		if(jo.get("status") == null && jo.get("message") == null){
			jo.put("status", Retcode.OK.code);
			jo.put("message", Retcode.OK.msg);
			json.add(jo);
		}
		return jo;
	}
}
