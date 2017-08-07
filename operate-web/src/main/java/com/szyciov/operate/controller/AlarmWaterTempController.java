package com.szyciov.operate.controller;

import com.szyciov.entity.Dictionary;
import com.szyciov.entity.TextAndValue;
import com.szyciov.op.param.QueryWaterTemp;
import com.szyciov.op.param.QueryWaterTempParam;
import com.szyciov.operate.util.TextValueUtil;
import com.szyciov.util.*;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
 * 驾驶行为  低电压
 * Created by liubangwei_lc on 2017/7/10
 *
 */
@Controller
public class AlarmWaterTempController extends BaseController {

	private TemplateHelper templateHelper = new TemplateHelper();
	private String baseApiUrl = SystemConfig.getSystemProperty("vmsBaseApiUrl");
	private String vmsApiUrl = SystemConfig.getSystemProperty("vmsApiUrl");
	private String vmsApikey = SystemConfig.getSystemProperty("vmsApikey");


	@RequestMapping(value = "/AlarmWaterTemp/Index")
	public ModelAndView getAlarmWaterTempIndex(HttpServletRequest request, HttpServletResponse response) throws NoSuchAlgorithmException, ParseException {
		Map<String, Object> model = new HashMap<>();
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		List<Dictionary> opUserCompany = getOpUserCompany(request, userToken, true);
		model.put("opUserCompany", opUserCompany);
		model.put("durationList", templateHelper.dealRequestWithFullUrlToken(baseApiUrl +"/Dictionary/GetDictionaryByType?type=时长范围", HttpMethod.GET,
				userToken, null, List.class));
		model.put("mileageList", templateHelper.dealRequestWithFullUrlToken(baseApiUrl +"/Dictionary/GetDictionaryByType?type=里程范围", HttpMethod.GET,
				userToken, null, List.class));
	
		ModelAndView mv = new ModelAndView("resource/alarmWaterTemp/index",model);
		return mv;
	}

	/**
	 * 列表
	 * @param queryWaterTempParam
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/AlarmWaterTemp/queryAlarmWaterTempList")
	@ResponseBody
	public PageBean getqueryAlarmWaterTempList(@RequestBody QueryWaterTempParam queryWaterTempParam, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PageBean pageBean=new PageBean();
		queryWaterTempParam.setApikey(vmsApikey);
		String userToken = getUserToken(); //调通父类方法获取userToken
		List<Dictionary> dictionary = getOpUserCompany(request, userToken, false);//获取用户的所属单位

		// 转换字典值
		List<TextAndValue> listDictionary = TextValueUtil.convert(dictionary);
		queryWaterTempParam.setOrganizationId(
				(!listDictionary.isEmpty() && listDictionary.size() > 0) ? listDictionary.get(0).getValue() : "");

		//调用查询车辆时间违规信息接口
		Map<String,Object>	map=templateHelper.dealRequestWithFullUrlToken(vmsApiUrl+"/Monitor/QueryWaterTemp?"
				+ ReflectClassField.getMoreFieldsValue(queryWaterTempParam),
				HttpMethod.GET, userToken, null, Map.class);
		List<QueryWaterTemp> list=(List<QueryWaterTemp>) map.get("waterTemp");
		int i=(int)map.get("iTotalRecords");
		pageBean.setiTotalRecords(i);
		pageBean.setiTotalDisplayRecords(i);
		pageBean.setAaData(list);
		return pageBean;
	}

	/**
	 * 水温趋势跳转
	 * @param id
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/AlarmWaterTemp/TempTrend/{id}")
	public ModelAndView getTempTrend(@PathVariable("id")String id, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("id", id);	
		//跳转到水温详情页面
		return new ModelAndView("resource/alarmWaterTemp/showDetail",model);
	}


	/**
	 * 水温趋势
	 * @param id
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/AlarmWaterTemp/QueryWaterTempTrend")
	@ResponseBody
	public Map<String,Object> getQueryWaterTempTrend(String id, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = getUserToken(); //调通父类方法获取userToken

		//调用查询水温趋势信息接口
		Map<String,Object>	map=templateHelper.dealRequestWithFullUrlToken(vmsApiUrl+"/Monitor/QueryWaterTempTrend?apikey="+vmsApikey+"&"
				+"id="+id, HttpMethod.GET, userToken, null, Map.class);
		
		return map;
	}
}
