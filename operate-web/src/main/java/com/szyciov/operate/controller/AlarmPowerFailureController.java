package com.szyciov.operate.controller;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.entity.Dictionary;
import com.szyciov.entity.Excel;
import com.szyciov.entity.TextAndValue;
import com.szyciov.op.param.HandleOutageParam;
import com.szyciov.op.param.QueryIdleParam;
import com.szyciov.op.param.QueryOutage;
import com.szyciov.op.param.QueryOutageParam;
import com.szyciov.operate.util.TextValueUtil;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.ExcelExport;
import com.szyciov.util.JSONUtil;
import com.szyciov.util.PageBean;
import com.szyciov.util.ReflectClassField;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper;

import net.sf.json.JSONArray;

/**
 * 驾驶行为断电处理
 * 
 * @author liubangwei_lc
 *
 */
@Controller
@RequestMapping("/AlarmPowerFailure")
public class AlarmPowerFailureController extends BaseController {
	private TemplateHelper templateHelper = new TemplateHelper();
	private String baseApiUrl = SystemConfig.getSystemProperty("vmsBaseApiUrl");
	private String vmsApiUrl = SystemConfig.getSystemProperty("vmsApiUrl");
	private String vmsApikey = SystemConfig.getSystemProperty("vmsApikey");

	@RequestMapping("/Index")
	public ModelAndView getAlarmPowerFailureIndex(HttpServletRequest request)
			throws NoSuchAlgorithmException, ParseException {
		Map<String, Object> model = new HashMap<String, Object>();
		String usertoken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		List<Dictionary> opUserCompany = getOpUserCompany(request, usertoken, true);
		model.put("opUserCompany", opUserCompany);
		model.put("stateList", templateHelper.dealRequestWithFullUrlToken(
				baseApiUrl + "/Dictionary/GetDictionaryByType?type=报警状态", HttpMethod.GET, usertoken, null, List.class));
		model.put("serviceStatus", templateHelper.dealRequestWithFullUrlToken(
				baseApiUrl + "/Dictionary/GetDictionaryByType?type=服役状态", HttpMethod.GET, usertoken, null, List.class));
		model.put("outageReasons", templateHelper.dealRequestWithFullUrlToken(
				baseApiUrl + "/Dictionary/GetDictionaryByType?type=断电原因", HttpMethod.GET, usertoken, null, List.class));
		// model.put("pSwitch", pSwitch);
		return new ModelAndView("resource/alarmPowerFailure/index", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/getAlarmPowerFailureByPage")
	@ResponseBody
	public PageBean getAlarmPowerFailureByPage(@RequestBody QueryOutageParam qaueryOutageParam,
			HttpServletRequest request, HttpServletResponse response) throws ParseException {
		response.setContentType("text/html;charset=utf-8");
		qaueryOutageParam.setApikey(vmsApikey);
		PageBean pageBean = new PageBean();
		String usertoken = getUserToken(request);
		List<Dictionary> dictionary = getOpUserCompany(request, usertoken, true);
		// 转换字典值
		List<TextAndValue> listDictionary = TextValueUtil.convert(dictionary);
		qaueryOutageParam.setOrganizationId(
				(!listDictionary.isEmpty() && listDictionary.size() > 0) ? listDictionary.get(0).getValue() : "");
		Map<String, Object> map = templateHelper.dealRequestWithFullUrlToken(
				vmsApiUrl + "/Monitor/QueryOutage?" + ReflectClassField.getMoreFieldsValue(qaueryOutageParam),
				HttpMethod.GET, usertoken, null, Map.class);
		List<QueryOutage> list = (List<QueryOutage>) map.get("outage");
		pageBean.setsEcho(qaueryOutageParam.getsEcho());
		int i = (int) map.get("iTotalRecords");
		pageBean.setiTotalDisplayRecords(i);
		pageBean.setiTotalRecords(list.size());
		pageBean.setAaData(list);

		return pageBean;
	}

	/**
	 * 处理断电
	 * 
	 * @param handleOutageParam
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getOutage")
	@ResponseBody
	public Map<String, Object> getOutage(@RequestBody HandleOutageParam handleOutageParam, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		handleOutageParam.setApikey(vmsApikey);
		String usertoken = getUserToken(request);
		Map<String, Object> map = templateHelper.dealRequestWithFullUrlToken(vmsApiUrl + "/Monitor/HandleOutage",
				HttpMethod.POST, usertoken, handleOutageParam, Map.class);
		return map;
	}

	// 导出
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/Export")
	@ResponseBody
	public void export(QueryIdleParam queryIdleParam, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String fileName = "";
		String title = "断电报警";
		// 表头
		List<String> titleList = new ArrayList<String>();
		Map<String, String> header = this.getTrackTrajectoryTitle(titleList);
		if (null == header || header.isEmpty()) {
			return;
		}
		// 查询数据
		String userToken = getUserToken(request);
		queryIdleParam.setApikey(vmsApikey);
		queryIdleParam.setiDisplayStart(0);
		queryIdleParam.setiDisplayLength(9999);
		Map<String, Object> trackRecordMap = templateHelper.dealRequestWithFullUrlToken(
				vmsApiUrl + "/Monitor/QueryOutage?" + ReflectClassField.getMoreFieldsValue(queryIdleParam),
				HttpMethod.GET, userToken, null, Map.class);
		List<Map<String, Object>> list = new ArrayList<>();
		JSONArray vehcTrajectoryArray = JSONArray.fromObject(trackRecordMap.get("outage"));// 转化为json数组-------JSONArray对象得到数组
		list = JSONUtil.parseJSON2Map(vehcTrajectoryArray);
		// 构建需要Excel表格数据
		Map<String, List<Object>> colData = new HashMap<String, List<Object>>();
		for (Map.Entry<String, String> entry : header.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			List<Object> dataList = new ArrayList<Object>();
			for (Map<String, Object> map : list) {
				Object obj = map.get(key);
				String data = null;
				if (null != obj) {
					data = obj.toString();
				}
				// 车牌
				if ("plate".equals(key)) {
					if (StringUtils.isBlank(data)) {
						dataList.add("/");
					} else {
						dataList.add(data);
					}
				}
				if ("imei".equals(key)) {
					if (StringUtils.isBlank(data)) {
						dataList.add("/");
					} else {
						dataList.add(data);
					}
				}
				if ("department".equals(key)) {
					if (StringUtils.isBlank(data)) {
						dataList.add("/");
					} else {
						dataList.add(data);
					}
				}
				if ("alarmTime".equals(key)) {
					if (StringUtils.isBlank(data)) {
						dataList.add("/");
					} else {
						dataList.add(data);
					}
				}
				if ("processingTime".equals(key)) {
					if (StringUtils.isBlank(data)) {
						dataList.add("/");
					} else {
						dataList.add(data);
					}
				}
				if ("alarmAddress".equals(key)) {
					if (StringUtils.isBlank(data)) {
						dataList.add("/");
					} else {
						dataList.add(data);
					}
				}
				if ("processingState".equals(key)) {
					if (StringUtils.isBlank(data)) {
						dataList.add("/");
					} else {
						dataList.add(data);
					}
				}
				if ("processingPeople".equals(key)) {
					if (StringUtils.isBlank(data)) {
						dataList.add("/");
					} else {
						dataList.add(data);
					}
				}
				if ("updateTime".equals(key)) {
					if (StringUtils.isBlank(data)) {
						dataList.add("/");
					} else {
						dataList.add(data);
					}
				}
			}
			colData.put(value, dataList);
		}
		// 创建 Excel
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMddHHmmss");
		fileName += title + sdfDate.format(new Date()) + ".xls";
		Excel excel = new Excel();
		File tempFile = new File(fileName);
		excel.setColName(titleList);
		excel.setColData(colData);
		ExcelExport ee = new ExcelExport(request, response, excel);
		ee.createExcel(tempFile);
		// 防止页面跳转
		try {
			response.sendRedirect("javascript:void(0);");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 导出断电报警表头构建<一句话功能简述> <功能详细描述>
	 * 
	 * @param titleList
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	private Map<String, String> getTrackTrajectoryTitle(List<String> headerList) {
		Map<String, String> title = new HashMap<String, String>();

		title.put("plate", "车牌");
		headerList.add("车牌");

		title.put("imei", "车辆IMEI");
		headerList.add("车辆IMEI");
		title.put("department", "车辆所属");
		headerList.add("车辆所属");
		title.put("alarmTime", "报警时间");
		headerList.add("报警时间");
		title.put("processingTime", "处理时间");
		headerList.add("处理时间");
		title.put("alarmAddress", "报警地址");
		headerList.add("报警地址");
		title.put("processingState", "处理状态");
		headerList.add("处理状态");

		title.put("processingPeopleId", "处理人ID");
		headerList.add("处理人ID");

		title.put("processingPeople", "处理人");
		headerList.add("处理人");
		title.put("updateTime", "更新时间");
		headerList.add("更新时间");

		return title;
	}

	/**
	 * 获取usertoken的通用方法 app端用户主要是用户请求中传递usertoken web端用户主要是从session中获取
	 * 
	 * @param request
	 * @return
	 */
	public String getUserToken(HttpServletRequest request) {
		String usertoken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		if (usertoken == null || "".equalsIgnoreCase(usertoken)) {
			HttpSession session = request.getSession();
			if (session == null) {
				return usertoken;
			}
			usertoken = (String) session.getAttribute(Constants.REQUEST_USER_TOKEN);
		}
		return usertoken;
	}
}
