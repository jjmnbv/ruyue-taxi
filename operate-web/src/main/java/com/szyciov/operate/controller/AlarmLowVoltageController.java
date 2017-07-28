package com.szyciov.operate.controller;

import com.szyciov.entity.Dictionary;
import com.szyciov.entity.TextAndValue;
import com.szyciov.op.param.QueryLowVoltage;
import com.szyciov.op.param.QueryLowVoltageParam;
import com.szyciov.operate.util.TextValueUtil;
import com.szyciov.util.*;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 驾驶行为  低电压
 * @author liubangwei_lc
 *
 */
@Controller
@RequestMapping("/AlarmLowVoltage")
public class AlarmLowVoltageController extends  BaseController {

	private TemplateHelper templateHelper = new TemplateHelper();
	private String baseApiUrl = SystemConfig.getSystemProperty("vmsBaseApiUrl");
	private String vmsApiUrl = SystemConfig.getSystemProperty("vmsApiUrl");
	private String vmsApikey = SystemConfig.getSystemProperty("vmsApikey");
	

	
	@RequestMapping("/Index")
	public ModelAndView getAlarmLowVoltageIndex(HttpServletRequest request) throws NoSuchAlgorithmException, ParseException {

		Map<String, Object> model = new HashMap<>();
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		List<Dictionary> opUserCompany = getOpUserCompany(request, userToken, true);
		model.put("opUserCompany", opUserCompany);
		return new ModelAndView("resource/alarmLowVoltage/index", model);
	}


	@SuppressWarnings("unchecked")
	@RequestMapping("/getAlarmPowerFailureByPage")
	@ResponseBody
	public PageBean getAlarmLowVoltageByPage(@RequestBody QueryLowVoltageParam queryLowVoltageParam, HttpServletRequest request,
											 HttpServletResponse response) throws ParseException {
		response.setContentType("text/html;charset=utf-8");
		PageBean pageBean = new PageBean();
		queryLowVoltageParam.setApikey(vmsApikey);
		String userToken = getUserToken(); //调通父类方法获取userToken
		List<Dictionary> dictionary = getOpUserCompany(request, userToken, false);//获取用户的所属单位

		// 转换字典值
		List<TextAndValue> listDictionary = TextValueUtil.convert(dictionary);
		queryLowVoltageParam.setOrganizationId(
				(!listDictionary.isEmpty() && listDictionary.size() > 0) ? listDictionary.get(0).getValue() : "");

		Map<String, Object> map = templateHelper.dealRequestWithFullUrlToken(vmsApiUrl + "/Monitor/QueryLowVoltage?" + ReflectClassField.getMoreFieldsValue(queryLowVoltageParam), HttpMethod.GET, userToken,
				queryLowVoltageParam, Map.class);
		List<QueryLowVoltage> list = (List<QueryLowVoltage>) map.get("lowVoltage");

		pageBean.setsEcho(queryLowVoltageParam.getsEcho());
		int i =  (int) map.get("iTotalRecords");
		pageBean.setiTotalDisplayRecords(i);
		pageBean.setiTotalRecords(i);
		pageBean.setAaData(list);

		return pageBean;
	}
}
