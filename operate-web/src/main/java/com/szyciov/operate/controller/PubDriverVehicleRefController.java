package com.szyciov.operate.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.entity.PubDriverVehicleBind;
import com.szyciov.op.entity.OpUser;
import com.szyciov.op.param.PubDriverVehicleBindQueryParam;
import com.szyciov.op.param.PubDriverVehicleRefQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.PageBean;
import com.szyciov.util.TemplateHelper;

@Controller
public class PubDriverVehicleRefController extends BaseController {
private static final Logger logger = Logger.getLogger(PubDriverVehicleRefController.class);
	
	private TemplateHelper templateHelper = new TemplateHelper();
	
	@RequestMapping(value = "/PubDriverVehicleRef/Index")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public ModelAndView getPubDriverVehicleRefIndex(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		ModelAndView view = new ModelAndView();
		
		//服务车型
		List<Map<String, Object>> vehiclemodelList = templateHelper.dealRequestWithToken(
				"/PubDriverVehicleRef/GetVehiclemodels", HttpMethod.GET, userToken, null, List.class);
		
		//登记城市
		List<Map<String, Object>> cityList = templateHelper.dealRequestWithToken(
				"/PubDriverVehicleRef/GetCityaddr", HttpMethod.GET, userToken, null, List.class);
		
		//品牌车系
		/*List<Map<String, Object>> vehclineidList = templateHelper.dealRequestWithToken("/PubDriverVehicleRef/GetVehcbrand",
				HttpMethod.GET, userToken, null, List.class);*/
		
		view.addObject("vehiclemodelList", vehiclemodelList);
		view.addObject("cityList", cityList);
		/*view.addObject("vehclineidList", vehclineidList);*/
		view.setViewName("resource/pubDriverVehicleRef/index");
		return view;
	}
	
	@RequestMapping("/PubDriverVehicleRef/GetDriverInfoByQuery")
	@ResponseBody
	public PageBean getDriverInfoByQuery(@RequestBody PubDriverVehicleRefQueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/PubDriverVehicleRef/GetDriverInfoByQuery", HttpMethod.POST, userToken,
				queryParam,PageBean.class);
	}
	
	@RequestMapping("/PubDriverVehicleRef/GetDriverByNameOrPhone")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getDriverByNameOrPhone(
			@RequestParam(value = "driver", required = false) String driver,
			@RequestParam(value = "vehicletype", required = true) String vehicletype,
			@RequestParam(value = "jobstatus", required = false) String jobstatus, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		return templateHelper.dealRequestWithToken(
				"/PubDriverVehicleRef/GetDriverByNameOrPhone?driver={driver}&vehicletype={vehicletype}&jobstatus={jobstatus}",
				HttpMethod.GET, userToken, null, List.class, driver, vehicletype, jobstatus);
	}
	
	@RequestMapping("/PubDriverVehicleRef/GetDriverByJobnum")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getDriverByJobnum(@RequestParam(value = "jobnum", required = false) String jobnum,
			@RequestParam(value = "jobstatus", required = false) String jobstatus, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		return templateHelper.dealRequestWithToken(
				"/PubDriverVehicleRef/GetDriverByJobnum?jobnum={jobnum}&jobstatus={jobstatus}", HttpMethod.GET,
				userToken, null, List.class, jobnum, jobstatus);
	}
	
	@RequestMapping("/PubDriverVehicleRef/GetVehcbrand")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getVehcbrand(@RequestParam(value = "vehcbrandname", required = false) String vehcbrandname,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		return templateHelper.dealRequestWithToken("/PubDriverVehicleRef/GetVehcbrand?vehcbrandname={vehcbrandname}",
				HttpMethod.GET, userToken, null, List.class, vehcbrandname);
	}
	
	@RequestMapping("/PubDriverVehicleRef/GetVehcbrandByCity/{city}")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getVehcbrandByCity(@PathVariable String city,@RequestParam(value = "vehcbrandname", required = false) String vehcbrandname,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		return templateHelper.dealRequestWithToken("/PubDriverVehicleRef/GetVehcbrandByCity/{city}?vehcbrandname={vehcbrandname}",
				HttpMethod.GET, userToken, null, List.class, city, vehcbrandname);
	}
	
	@RequestMapping("/PubDriverVehicleRef/GetUnbandCarsByCity")
	@ResponseBody
	public PageBean getUnbandCarsByCity(@RequestBody PubDriverVehicleRefQueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		if (queryParam.getKey() != null && !"".equals(queryParam.getKey())) {
			queryParam.setCity(queryParam.getKey());
		} else {
			queryParam.setCity(request.getParameter("cityId"));
		}

		return templateHelper.dealRequestWithToken("/PubDriverVehicleRef/GetUnbandCarsByCity", HttpMethod.POST, userToken,
				queryParam,PageBean.class);
	}
	
	@RequestMapping("/PubDriverVehicleRef/CreatePubDriverVehicleRef")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String, String> createSendRules(@RequestBody PubDriverVehicleBind pubDriverVehicleBind, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OpUser opUser = getLoginOpUser(request);
		
		pubDriverVehicleBind.setCreater(opUser.getId());
		pubDriverVehicleBind.setUpdater(opUser.getId());

		return templateHelper.dealRequestWithToken("/PubDriverVehicleRef/CreatePubDriverVehicleRef", HttpMethod.POST, userToken, pubDriverVehicleBind,
				Map.class);
	}
	
	@RequestMapping("/PubDriverVehicleRef/JudgeUncompleteOrder")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String, String> createSendRules(@RequestParam(value = "driverid", required = true) String driverid, @RequestParam(value = "vehicleid", required = true) String vehicleid, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);

		return templateHelper.dealRequestWithToken("/PubDriverVehicleRef/JudgeUncompleteOrder?driverid={driverid}&vehicleid={vehicleid}", HttpMethod.POST, userToken, null,
				Map.class, driverid, vehicleid);
	}
	
	@RequestMapping("/PubDriverVehicleRef/UpdatePubDriverVehicleRef")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String, String> updatePubDriverVehicleRef(@RequestBody PubDriverVehicleBind pubDriverVehicleBind, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OpUser opUser = getLoginOpUser(request);

		pubDriverVehicleBind.setUpdater(opUser.getId());

		return templateHelper.dealRequestWithToken("/PubDriverVehicleRef/UpdatePubDriverVehicleRef", HttpMethod.POST, userToken, pubDriverVehicleBind,
				Map.class);
	}
	
	@RequestMapping(value = "/PubDriverVehicleRef/DriverOperateDetail")
	@ResponseBody
	public ModelAndView getDriverOperateDetail(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		ModelAndView view = new ModelAndView();

		view.setViewName("resource/pubDriverVehicleRef/driveroperatedetail");
		return view;
	}

	@RequestMapping("/PubDriverVehicleRef/GetDriverOpRecordByQuery")
	@ResponseBody
	public PageBean getDriverOpRecordByQuery(@RequestBody PubDriverVehicleBindQueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		// 初始化数据为空
		if (queryParam.getKey() == null || "".equals(queryParam.getKey())) {
			queryParam.setBindstate("A");
		}
		
		return templateHelper.dealRequestWithToken("/PubDriverVehicleRef/GetDriverOpRecordByQuery", HttpMethod.POST, userToken,
				queryParam,PageBean.class);
	}
	
	
	@RequestMapping(value = "/PubDriverVehicleRef/VehicleOperateDetail")
	@ResponseBody
	public ModelAndView getVehicleOperateDetail(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		ModelAndView view = new ModelAndView();

		view.setViewName("resource/pubDriverVehicleRef/vehicleoperatedetail");
		return view;
	}
	
	@RequestMapping("/PubDriverVehicleRef/GetVehicleOpRecordByQuery")
	@ResponseBody
	public PageBean getVehicleOpRecordByQuery(@RequestBody PubDriverVehicleBindQueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		// 初始化数据为空
		if (queryParam.getKey() == null || "".equals(queryParam.getKey())) {
			queryParam.setBindstate("A");
		}
		
		return templateHelper.dealRequestWithToken("/PubDriverVehicleRef/GetVehicleOpRecordByQuery", HttpMethod.POST, userToken,
				queryParam,PageBean.class);
	}
	
	@RequestMapping("/PubDriverVehicleRef/GetPlatenoByPlateno")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getPlatenoByPlateno(@RequestParam(value = "plateno", required = false) String plateno,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		return templateHelper.dealRequestWithToken("/PubDriverVehicleRef/GetPlatenoByPlateno?plateno={plateno}",
				HttpMethod.GET, userToken, null, List.class, plateno);
	}
	
	@RequestMapping("/PubDriverVehicleRef/GetVehicleVinByVin")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getVehicleVinByVin(@RequestParam(value = "vin", required = false) String vin,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		return templateHelper.dealRequestWithToken("/PubDriverVehicleRef/GetVehicleVinByVin?vin={vin}",
				HttpMethod.GET, userToken, null, List.class, vin);
	}
	
}
