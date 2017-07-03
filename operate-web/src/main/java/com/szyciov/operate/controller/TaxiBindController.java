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

import com.alipay.api.internal.util.StringUtils;
import com.szyciov.dto.driverShiftManagent.ProcessedSaveDto;
import com.szyciov.op.entity.OpTaxiBind;
import com.szyciov.op.entity.OpUser;
import com.szyciov.op.param.PubDriverVehicleRefQueryParam;
import com.szyciov.op.param.TaxiBindQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.PageBean;
import com.szyciov.util.TemplateHelper;

import net.sf.json.JSONObject;

@Controller
public class TaxiBindController extends BaseController {
	private static final Logger logger = Logger.getLogger(TaxiBindController.class);
	
    private TemplateHelper templateHelper = new TemplateHelper();
	
	@RequestMapping(value = "/TaxiBind/Index")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public ModelAndView getTaxiBindIndex(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		ModelAndView view = new ModelAndView();

		//登记城市
		List<Map<String, Object>> cityList = templateHelper.dealRequestWithToken("/TaxiBind/GetCityaddr",
				HttpMethod.GET, userToken, null, List.class);

		view.addObject("cityList", cityList);
		view.setViewName("resource/taxiBind/index");
		return view;
	}
	
	@RequestMapping("/TaxiBind/GetVehicleInfoByQuery")
	@ResponseBody
	public PageBean getVehicleInfoByQuery(@RequestBody TaxiBindQueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		return templateHelper.dealRequestWithToken("/TaxiBind/GetVehicleInfoByQuery", HttpMethod.POST, userToken,
				queryParam,PageBean.class);
	}
	
	@RequestMapping("/TaxiBind/GetOndutyDriver")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getOndutyDriver(@RequestParam(value = "driver", required = false) String driver,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		return templateHelper.dealRequestWithToken("/TaxiBind/GetOndutyDriver?driver={driver}",
				HttpMethod.GET, userToken, null, List.class, driver);
	}
	
	@RequestMapping("/TaxiBind/GetVehcbrandVehcline")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getVehcbrandVehcline(@RequestParam(value = "vehcbrandname", required = false) String vehcbrandname,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		return templateHelper.dealRequestWithToken("/TaxiBind/GetVehcbrandVehcline?vehcbrandname={vehcbrandname}",
				HttpMethod.GET, userToken, null, List.class, vehcbrandname);
	}
	
	@RequestMapping("/TaxiBind/GetDriverByJobnum")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getDriverByJobnum(@RequestParam(value = "city", required = true) String city,
			@RequestParam(value = "jobnum", required = false) String jobnum, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		return templateHelper.dealRequestWithToken("/TaxiBind/GetDriverByJobnum?city={city}&jobnum={jobnum}",
				HttpMethod.GET, userToken, null, List.class, city, jobnum);
	}
	
	@RequestMapping("/TaxiBind/GetDriverByNameOrPhone")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getDriverByNameOrPhone(@RequestParam(value = "city", required = true) String city,
			@RequestParam(value = "driver", required = false) String driver, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		return templateHelper.dealRequestWithToken("/TaxiBind/GetDriverByNameOrPhone?city={city}&driver={driver}",
				HttpMethod.GET, userToken, null, List.class, city, driver);
	}
	
	@RequestMapping("/TaxiBind/GetUnbindDriverByQuery")
	@ResponseBody
	public PageBean getUnbindDriverByQuery(@RequestBody PubDriverVehicleRefQueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		if (StringUtils.isEmpty(queryParam.getJobnum()) && StringUtils.isEmpty(queryParam.getDriver())) {
			queryParam.setKey("12");
		} else {
			if (StringUtils.isEmpty(queryParam.getKey())) {
				queryParam.setKey("12");
			} else {
				queryParam.setKey("");
			}
		}
		
		return templateHelper.dealRequestWithToken("/TaxiBind/GetUnbindDriverByQuery", HttpMethod.POST, userToken,
				queryParam,PageBean.class);
	}
	
	@RequestMapping("/TaxiBind/CreatePubDriverVehicleRef")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String, String> createPubDriverVehicleRef(@RequestBody OpTaxiBind opTaxiBind, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OpUser opUser = getLoginOpUser(request);
		
		opTaxiBind.setCreater(opUser.getId());
		opTaxiBind.setUpdater(opUser.getId());

		return templateHelper.dealRequestWithToken("/TaxiBind/CreatePubDriverVehicleRef", HttpMethod.POST, userToken, opTaxiBind,
				Map.class);
	}
	
	@RequestMapping("/TaxiBind/GetBindDriverByVehicleid")
	@ResponseBody
	public PageBean getBindDriverByVehicleid(@RequestBody TaxiBindQueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		String vehicleid = queryParam.getVehicleid();
		
		return templateHelper.dealRequestWithToken("/TaxiBind/GetBindDriverByVehicleid?vehicleid={vehicleid}", HttpMethod.POST, userToken,
				null,PageBean.class, vehicleid);
	}
	
	@RequestMapping("/TaxiBind/UpdatePubDriverVehicleRef")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String, String> updatePubDriverVehicleRef(@RequestBody OpTaxiBind opTaxiBind, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OpUser opUser = getLoginOpUser(request);
		
		opTaxiBind.setCreater(opUser.getId());
		opTaxiBind.setUpdater(opUser.getId());

		return templateHelper.dealRequestWithToken("/TaxiBind/UpdatePubDriverVehicleRef", HttpMethod.POST, userToken, opTaxiBind,
				Map.class);
	}
	
	@RequestMapping("/TaxiBind/ListTaxiBindDriver/{vehicleid}")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> listTaxiBindDriver(@PathVariable String vehicleid, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		return templateHelper.dealRequestWithToken("/TaxiBind/ListTaxiBindDriver/{vehicleid}", HttpMethod.POST,
				userToken, null, List.class, vehicleid);
	}
	
	@RequestMapping("/TaxiBind/SaveAssign")
	@ResponseBody
	public JSONObject saveAssign(@RequestBody ProcessedSaveDto processedSaveDto,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OpUser opUser = getLoginOpUser(request);
		
		processedSaveDto.setProcessperson(opUser.getId());
		processedSaveDto.setProcesspersonname(opUser.getNickname());
		return templateHelper.dealRequestWithToken("/TaxiBind/SaveAssign", HttpMethod.POST, userToken, processedSaveDto,
				JSONObject.class);
	}
	
	@RequestMapping("/TaxiBind/IsAssign/{vehicleId}")
	@ResponseBody
	public JSONObject isAssign(@PathVariable String vehicleId, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		return templateHelper.dealRequestWithToken("/TaxiBind/IsAssign/{vehicleId}", HttpMethod.POST, userToken, null,
				JSONObject.class, vehicleId);
	}
	
	
	
	@RequestMapping(value = "/TaxiBind/OperateDetail")
	@ResponseBody
	public ModelAndView getOperateDetail(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		ModelAndView view = new ModelAndView();

		view.addObject("vehicleid", request.getParameter("vehicleid"));
		view.addObject("plateno", request.getParameter("plateno"));
		view.setViewName("resource/taxiBind/operateDetail");
		return view;
	}
	
	@RequestMapping("/TaxiBind/GetOperateRecordByVehicleid")
	@ResponseBody
	public PageBean getOperateRecordByVehicleid(@RequestBody TaxiBindQueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		return templateHelper.dealRequestWithToken("/TaxiBind/GetOperateRecordByVehicleid", HttpMethod.POST, userToken,
				queryParam,PageBean.class);
	}
	
	@RequestMapping("/TaxiBind/GetBindDriversByVehicleid/{vehicleid}")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getBindDriversByVehicleid(@PathVariable String vehicleid, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		return templateHelper.dealRequestWithToken("/TaxiBind/GetBindDriversByVehicleid/{vehicleid}", HttpMethod.POST,
				userToken, null, List.class, vehicleid);
	}
	
}
