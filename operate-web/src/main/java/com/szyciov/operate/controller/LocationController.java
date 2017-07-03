package com.szyciov.operate.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.entity.Dictionary;
import com.szyciov.entity.TextAndValue;
import com.szyciov.lease.entity.User;
import com.szyciov.op.param.QueryStatusParam;
import com.szyciov.op.param.QueryTrackRecordParam;
import com.szyciov.op.param.QueryTrajectoryByEqpParam;
import com.szyciov.op.param.QueryVehcAndEqpParam;
import com.szyciov.op.param.VehicleMonitorParam;
import com.szyciov.operate.util.TextValueUtil;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.Property;
import com.szyciov.util.ReflectClassField;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper;
import com.szyciov.util.UserTokenManager;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class LocationController extends BaseController {

	private TemplateHelper templateHelper = new TemplateHelper();
	private String baseApiUrl = SystemConfig.getSystemProperty("vmsBaseApiUrl");
	private String vmsApiUrl = SystemConfig.getSystemProperty("vmsApiUrl");

	/******************* 车辆位置主页面 *********************/
	/**
	 * 默认
	 * 
	 * @author 陈建辉
	 * @param vehicleMonitorParam
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "/Location/Index")
	public ModelAndView getLocationIndex(HttpServletRequest request, HttpServletResponse response)
			throws NoSuchAlgorithmException, ParseException {
		Map<String, Object> model = new HashMap<String, Object>();
		String usertoken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		if (usertoken == null || "".equalsIgnoreCase(usertoken)) {
			HttpSession session = request.getSession();
			if (session != null) {
				usertoken = (String) session.getAttribute(Constants.REQUEST_USER_TOKEN);
			}
		}
		List<Dictionary> companyList = getOpUserCompany(request, usertoken, true);
		model.put("companyList", companyList);
		ModelAndView mv = new ModelAndView("resource/location/index", model);
		return mv;
	}

	/**
	 * 查询车辆位置
	 * 
	 * @author 陈建辉
	 * @param vehicleMonitorParam
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "/Location/QueryLocation", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryLocation(VehicleMonitorParam queryParam, HttpServletRequest request) {

		String usertoken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		if (usertoken == null || "".equalsIgnoreCase(usertoken)) {
			HttpSession session = request.getSession();
			if (session != null) {
				usertoken = (String) session.getAttribute(Constants.REQUEST_USER_TOKEN);
			}
		}
		List<Dictionary> dictionary = getOpUserCompany(request, usertoken, false);
		// 转换字典值
		List<TextAndValue> listDictionary = TextValueUtil.convert(dictionary);
		queryParam.setOrganizationId(
				(!listDictionary.isEmpty() && listDictionary.size() > 0) ? listDictionary.get(0).getValue() : "");
		return templateHelper.dealRequestWithFullUrlToken(
				vmsApiUrl + "/Monitor/QueryLocation?" + ReflectClassField.getMoreFieldsValue(queryParam),
				HttpMethod.GET, usertoken, null, Map.class);

	}

	/******************** 实时追踪 ***********************/
	/**
	 * 实时追踪主页面
	 * 
	 * @author 袁金林
	 * @param trackPlayback
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "/Location/realimeTracking")
	public ModelAndView getRealimeTracking(HttpServletRequest request, HttpServletResponse response)
			throws NoSuchAlgorithmException, ParseException {
		Map<String, Object> model = new HashMap<String, Object>();

		ModelAndView mv = new ModelAndView("resource/location/realimeTracking", model);
		return mv;
	}

	/**
	 * 实时追踪
	 * 
	 * @author 袁金林
	 * @param vehicleMonitorParam
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "/Location/QueryEqpLocation")
	@ResponseBody
	public Map<String, Object> queryLocation(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json; charset=utf-8");
		JSONArray jsonArray = JSONArray.fromObject("[" + request.getParameter("eqpIdList") + "]");// 转化为json数组-------JSONArray对象得到数组
		String apikey = (String) JSONObject.fromObject(jsonArray.get(0)).get("apikey");
		int processOption = (int) JSONObject.fromObject(jsonArray.get(0)).get("processOption");
		int relationType = (int) JSONObject.fromObject(jsonArray.get(0)).get("relationType");
		String eqpIdList = (String) JSONObject.fromObject(jsonArray.get(0)).get("eqpIdList");

		QueryStatusParam queryStatusParam = new QueryStatusParam();
		queryStatusParam.setApikey(apikey);
		queryStatusParam.setEqpId(eqpIdList);
		queryStatusParam.setProcessOption(processOption);
		queryStatusParam.setRelationType(relationType);
		if (queryStatusParam.getEqpId().length() > 0) {
			queryStatusParam.setEqpId("'" + queryStatusParam.getEqpId().replace(",", "','") + "'");
			queryStatusParam.setTypeArray(java.util.Arrays.asList(queryStatusParam.getEqpId()));
		}
		// JSONObject queryParam=JSONObject.fromObject(queryStatusParam);
		String usertoken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		if (usertoken == null || "".equalsIgnoreCase(usertoken)) {
			HttpSession session = request.getSession();
			if (session != null) {
				usertoken = (String) session.getAttribute(Constants.REQUEST_USER_TOKEN);
			}
		}
		return templateHelper.dealRequestWithFullUrlToken(vmsApiUrl + "/Monitor/QueryRealtimeLocation", HttpMethod.POST,
				usertoken, queryStatusParam, Map.class);
	}

	/**
	 * 根据行程查询轨迹
	 * 
	 * @author 袁金林
	 * @param queryParam
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/Location/getTrajectoryByTrack")
	@ResponseBody
	public Map<String, Object> getTrajectoryByTrack(QueryTrajectoryByEqpParam queryParam, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("application/json; charset=utf-8");
		String usertoken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		if (usertoken == null || "".equalsIgnoreCase(usertoken)) {
			HttpSession session = request.getSession();
			if (session != null) {
				usertoken = (String) session.getAttribute(Constants.REQUEST_USER_TOKEN);
			}
		}
		Map<String, Object> trackRecordMap = templateHelper.dealRequestWithFullUrlToken(
				vmsApiUrl + "/Monitor/QueryTrajectoryByTrack?" + ReflectClassField.getMoreFieldsValue(queryParam),
				HttpMethod.GET, usertoken, null, Map.class);

		return trackRecordMap;
	}

	/******************** 车辆轨迹主页面 ********************/

	/**
	 * 查询车牌及IMEI
	 * 
	 * @author 袁金林
	 * @param queryVehcAndEqpParam
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/Location/getVehclineByQuery")
	@ResponseBody
	public Map<String, Object> getVehclineByQuery(QueryVehcAndEqpParam queryVehcAndEqpParam, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		queryVehcAndEqpParam.setiDisplayLength(10);
		String usertoken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		if (usertoken == null || "".equalsIgnoreCase(usertoken)) {
			HttpSession session = request.getSession();
			if (session != null) {
				usertoken = (String) session.getAttribute(Constants.REQUEST_USER_TOKEN);
			}
		}
		List<Dictionary> dictionary = getOpUserCompany(request, usertoken, false);
		// 转换字典值
		List<TextAndValue> listDictionary = TextValueUtil.convert(dictionary);
		queryVehcAndEqpParam.setOrganizationId(
				(!listDictionary.isEmpty() && listDictionary.size() > 0) ? listDictionary.get(0).getValue() : "");
		Map<String, Object> map = templateHelper.dealRequestWithFullUrlToken(
				vmsApiUrl + "/Common/QueryVehcAndEqp?" + ReflectClassField.getMoreFieldsValue(queryVehcAndEqpParam),
				HttpMethod.GET, usertoken, null, Map.class);
		return map;
	}

}
