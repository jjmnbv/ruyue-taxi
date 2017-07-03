package com.szyciov.lease.controller;


import java.io.IOException;
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

import com.szyciov.lease.entity.PubRoleId;
import com.szyciov.lease.entity.User;
import com.szyciov.lease.param.PubAlarmprocessParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.PageBean;
import com.szyciov.util.TemplateHelper;
@Controller
public class PubAlarmprocessController extends BaseController{
	private static final Logger logger = Logger.getLogger(LeToCorderstatisticsController.class);
	private TemplateHelper templateHelper = new TemplateHelper();
	ModelAndView mav = new ModelAndView();
/*
 *  进入已处理界面
 */
	@RequestMapping(value = "/PubAlarmprocess/Index")
	public String getPubAlarmprocessIndex(HttpServletRequest request) {
		return "resource/pubAlarmprocess/daichuli";
	}
/*
 *  进入待处理界面
 */
	@RequestMapping(value = "/PubAlarmprocessDaichuli/Index")
	public String getDaichuliIndex(HttpServletRequest request) {
		return "resource/pubAlarmprocess/daichuli";
	}
	/*
	 *  进入已处理界面
	 */
		@RequestMapping(value = "/PubAlarmprocessYichuli/Index")
		public String getDaichuYichuli(HttpServletRequest request) {
			return "resource/pubAlarmprocess/yichuli";
		}
	/*
	 *  进入待处理界面
	 */
		@RequestMapping(value = "/PubAlarmprocessDaichuliOK/Index")
		public ModelAndView getDaichuliIndexGaoliang(
				@RequestParam (value = "id", required = false) String id,
				HttpServletRequest request,HttpServletResponse response) {
			ModelAndView mav = new ModelAndView();
			PubAlarmprocessParam getId = new PubAlarmprocessParam();
			getId.setId(id);
			mav.addObject("getId", getId);
			mav.setViewName("resource/pubAlarmprocess/daichuli");
			response.setContentType("application/json;charset=utf-8");
			return mav;
		}
	/**
	 * 获取报警数据
	 * @param leDriverorderstatisticsParam
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "PubAlarmprocess/GetPubAlarmprocessData")
	@ResponseBody
	public PageBean getDriverCountByQuery(
			@RequestParam (value = "alarmsource", required = false) String alarmsource,
	        @RequestParam(value = "alarmtype", required = false) String alarmtype,
		    @RequestParam(value = "passenger", required = false) String passenger,
		    @RequestParam(value = "driverid", required = false) String driverid,
		    @RequestParam(value = "plateno", required = false) String plateno,
		    @RequestParam(value = "startTime", required = false) String startTime,
		    @RequestParam(value = "endTime", required = false) String endTime,
		    @RequestParam(value = "processstatus", required = false) String processstatus,
		    @RequestParam(value = "key", required = false) String key,
            @RequestBody PubAlarmprocessParam pubAlarmprocessParam,
            HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		response.setContentType("application/json;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		String leasesCompanyId = user.getLeasescompanyid();
		PubAlarmprocessParam pubAlarmprocessParamEnd = new PubAlarmprocessParam();
		if(pubAlarmprocessParam.getKey() == null){
			pubAlarmprocessParamEnd = pubAlarmprocessParam;
			pubAlarmprocessParamEnd.setAlarmsource(alarmsource);
			pubAlarmprocessParamEnd.setAlarmtype(alarmtype);
			pubAlarmprocessParamEnd.setPassenger(passenger);
			pubAlarmprocessParamEnd.setDriverid(driverid);
			pubAlarmprocessParamEnd.setPlateno(plateno);
			pubAlarmprocessParamEnd.setStartTime(startTime);
			pubAlarmprocessParamEnd.setEndTime(endTime);
			pubAlarmprocessParamEnd.setProcessstatus(processstatus);
		}else{
			pubAlarmprocessParamEnd = pubAlarmprocessParam;
		}
		pubAlarmprocessParamEnd.setPlatformtype("1");
		pubAlarmprocessParamEnd.setLeasecompanyid(leasesCompanyId);
		return templateHelper.dealRequestWithToken("/PubAlarmprocess/PubAlarmprocessParam",
				HttpMethod.POST, userToken, pubAlarmprocessParamEnd, PageBean.class);
	}
	@RequestMapping(value = "PubAlarmprocess/GetPubAlarmprocessDetail")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public PubAlarmprocessParam getPubAlarmprocessDetail(
			@RequestParam (value = "id", required = false) String id,
            HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		response.setContentType("application/json;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
//		User user = getLoginLeUser(request);
		return templateHelper.dealRequestWithToken("/PubAlarmprocess/GetPubAlarmprocessDetail/{id}",
				HttpMethod.GET, userToken, null, PubAlarmprocessParam.class,id);
	}
	@RequestMapping(value = "PubAlarmprocess/UpdataDetail")
	@ResponseBody
	public int updataDetail(
			 @RequestBody PubAlarmprocessParam pubAlarmprocessParam,
            HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		response.setContentType("application/json;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		pubAlarmprocessParam.setUpdater(user.getId());
		pubAlarmprocessParam.setProcessstatus("1");
		return templateHelper.dealRequestWithToken("/PubAlarmprocess/UpdataDetail",
				HttpMethod.POST, userToken,pubAlarmprocessParam,int.class);
	}
	/**
	 * 司机联想输入框
	 * @param driver
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/PubAlarmprocess/GetPubAlarmprocessDriver")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getPubAlarmprocessDriver(@RequestParam String driver, HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		PubAlarmprocessParam pubAlarmprocessParam = new PubAlarmprocessParam();
//		User user = getLoginLeUser(request);
		pubAlarmprocessParam.setDriver(driver);
		return templateHelper.dealRequestWithToken("/PubAlarmprocess/GetPubAlarmprocessDriver", HttpMethod.POST, userToken,
				pubAlarmprocessParam, List.class);
	}
	/**
	 * 乘客
	 * @param passenger
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/PubAlarmprocess/GetPubAlarmprocessPassenger")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getPubAlarmprocessPassenger(@RequestParam String passenger, HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		PubAlarmprocessParam pubAlarmprocessParam = new PubAlarmprocessParam();
//		User user = getLoginLeUser(request);
		pubAlarmprocessParam.setPassenger(passenger);
		return templateHelper.dealRequestWithToken("/PubAlarmprocess/GetPubAlarmprocessPassenger", HttpMethod.POST, userToken,
				pubAlarmprocessParam, List.class);
	}
	/**
	 * 车牌号
	 * @param plateno
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/PubAlarmprocess/GetPubAlarmprocessPlateno")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getPubAlarmprocessPlateno(@RequestParam String plateno, HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		PubAlarmprocessParam pubAlarmprocessParam = new PubAlarmprocessParam();
//		User user = getLoginLeUser(request);
		pubAlarmprocessParam.setPlateno(plateno);
		return templateHelper.dealRequestWithToken("/PubAlarmprocess/GetPubAlarmprocessPlateno", HttpMethod.POST, userToken,
				pubAlarmprocessParam, List.class);
	}
	/**
	 * 订单过滤判断
	 */
	@RequestMapping(value = "/PubAlarmprocess/OrdernoOK")
	@ResponseBody
	public int ordernoOK( @RequestBody PubRoleId pubRoleId, HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		String roleId = user.getRoleid();
		if(roleId == null){
			return 1;
		}
		else{
			pubRoleId.setRoleId(roleId);
			return templateHelper.dealRequestWithToken("/PubAlarmprocess/OrdernoOK", HttpMethod.POST, userToken,
					pubRoleId, int.class);
		}
	}
	/**
	 * 判断报警单是否被处理
	 */
	@RequestMapping(value = "/PubAlarmprocess/HandleOK")
	@ResponseBody
	public boolean handleOK( @RequestBody PubAlarmprocessParam pubAlarmprocessParam,HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		pubAlarmprocessParam.setPlatformtype("1");
		pubAlarmprocessParam.setProcessstatus("0");
		int aa  = templateHelper.dealRequestWithToken("/PubAlarmprocess/HandleOK", HttpMethod.POST, userToken,
				pubAlarmprocessParam, int.class);
		boolean ok;
		if(aa == 0){
			ok = false;
		}else{
			ok = true;
		}
		return ok;
	}

}
