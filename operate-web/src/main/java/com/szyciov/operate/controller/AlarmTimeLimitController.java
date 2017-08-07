package com.szyciov.operate.controller;

import com.szyciov.entity.Dictionary;
import com.szyciov.entity.TextAndValue;
import com.szyciov.op.param.QueryTimeViolation;
import com.szyciov.op.param.QueryTimeViolationParam;
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
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 时间栅栏控制层
 * Created by liubangwei_lc on 2017/7/5
 *
 */
@Controller
public class AlarmTimeLimitController extends BaseController{

	private TemplateHelper templateHelper = new TemplateHelper();
	private String baseApiUrl = SystemConfig.getSystemProperty("vmsBaseApiUrl");
	private String vmsApiUrl = SystemConfig.getSystemProperty("vmsApiUrl");
	private String vmsApikey = SystemConfig.getSystemProperty("vmsApikey");


	@RequestMapping(value = "/AlarmTimeLimit/Index")
	public ModelAndView getAlarmTimeLimitIndex(HttpServletRequest request) throws NoSuchAlgorithmException, ParseException {
		Map<String, Object> model = new HashMap<>();
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		List<Dictionary> opUserCompany = getOpUserCompany(request, userToken, true);
		model.put("opUserCompany", opUserCompany);
		model.put("durationList", templateHelper.dealRequestWithFullUrlToken(baseApiUrl +"/Dictionary/GetDictionaryByType?type=时长范围", HttpMethod.GET,
				userToken, null, List.class));
		model.put("mileageList", templateHelper.dealRequestWithFullUrlToken(baseApiUrl +"/Dictionary/GetDictionaryByType?type=里程范围", HttpMethod.GET,
				userToken, null, List.class));
	

		ModelAndView mv = new ModelAndView("resource/alarmTimeLimit/index",model);
		return mv;
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/AlarmTimeLimit/queryAlarmTimeLimitList")
	@ResponseBody
	public PageBean getqueryAlarmTimeLimitList(@RequestBody QueryTimeViolationParam queryTimeViolationParam, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");

		queryTimeViolationParam.setApikey(vmsApikey);
		PageBean pageBean=new PageBean();
		String userToken = getUserToken(); //调通父类方法获取userToken
		List<Dictionary> dictionary = getOpUserCompany(request, userToken, false);
		// 转换字典值
		List<TextAndValue> listDictionary = TextValueUtil.convert(dictionary);
		queryTimeViolationParam.setOrganizationId(
				(!listDictionary.isEmpty() && listDictionary.size() > 0) ? listDictionary.get(0).getValue() : "");

		//调用接口
		Map<String, Object> map = templateHelper.dealRequestWithFullUrlToken(
				vmsApiUrl + "/Monitor/QueryTimeViolation?" + ReflectClassField.getMoreFieldsValue(queryTimeViolationParam),
				HttpMethod.GET, userToken, null, Map.class);

		List<QueryTimeViolation> list=(List<QueryTimeViolation>) map.get("timeViolation");
		pageBean.setsEcho(queryTimeViolationParam.getsEcho());
		int i=(int)map.get("iTotalRecords");
		pageBean.setiTotalRecords(i);
		pageBean.setiTotalDisplayRecords(i);
		pageBean.setAaData(list);
		return pageBean;
	}

}
