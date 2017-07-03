package com.szyciov.operate.controller;

import java.beans.IntrospectionException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.entity.Dictionary;
import com.szyciov.entity.Excel;
import com.szyciov.entity.TextAndValue;
import com.szyciov.op.entity.QueryTreeNode;
import com.szyciov.op.param.QueryTrackData;
import com.szyciov.op.param.QueryTrackDataParam;
import com.szyciov.op.param.QueryTrackDetailParam;
import com.szyciov.op.param.QueryTrackRecordParam;
import com.szyciov.op.param.QueryTrajectoryByEqpParam;
import com.szyciov.op.param.QueryTreeParam;
import com.szyciov.op.param.QueryVehcAndEqpParam;
import com.szyciov.operate.service.TrackService;
import com.szyciov.operate.util.PageBean;
import com.szyciov.operate.util.TextValueUtil;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.ExcelExport;
import com.szyciov.util.ReflectClassField;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class TrackController extends BaseController {

	private TemplateHelper templateHelper = new TemplateHelper();
	private String baseApiUrl = SystemConfig.getSystemProperty("vmsBaseApiUrl");
	private String vmsApiUrl = SystemConfig.getSystemProperty("vmsApiUrl");
	private String apikey = SystemConfig.getSystemProperty("vmsApikey");

	@Autowired
	private TrackService trackService;

	/****************** 行程数据 *********************/
	@RequestMapping(value = "/Track/Index")
	public ModelAndView getTrackIndex(HttpServletRequest request, HttpServletResponse response)
			throws NoSuchAlgorithmException, ParseException {
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<>();
		model.put("propertyList", templateHelper.dealRequestWithFullUrl(
				baseApiUrl + "/Dictionary/GetDictionaryByType?type=车辆属性", HttpMethod.GET, null, List.class, map));
		model.put("displacementList", templateHelper.dealRequestWithFullUrl(
				baseApiUrl + "/Dictionary/GetDictionaryByType?type=汽车排量", HttpMethod.GET, null, List.class, map));
		String usertoken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		if (usertoken == null || "".equalsIgnoreCase(usertoken)) {
			HttpSession session = request.getSession();
			if (session != null) {
				usertoken = (String) session.getAttribute(Constants.REQUEST_USER_TOKEN);
			}
		}
		List<Dictionary> companyList = getOpUserCompany(request, usertoken, true);
		model.put("companyList", companyList);
		ModelAndView mv = new ModelAndView("resource/track/index", model);
		return mv;
	}

	/**
	 * 分页显示行程数据
	 * 
	 * @param queryParam
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/Track/getTrackData")
	@ResponseBody
	public PageBean getTrackData(@RequestBody QueryTrackDataParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws ParseException {
		response.setContentType("text/html;charset=utf-8");
		PageBean pageBean = new PageBean();

		Map<String, Object> param = new HashMap<>();
		queryParam.setApikey(apikey);
		queryParam.setRelationType(1);
		String userToken = getUserToken(request);
		List<Dictionary> dictionary = getOpUserCompany(request, userToken, false);
		// 转换字典值
		List<TextAndValue> listDictionary = TextValueUtil.convert(dictionary);
		queryParam.setOrganizationId(
				(!listDictionary.isEmpty() && listDictionary.size() > 0) ? listDictionary.get(0).getValue() : "");
		// 调用接口查询
		Map<String, Object> map = templateHelper.dealRequestWithFullUrl(
				vmsApiUrl + "/Monitor/QueryTrackData?" + ReflectClassField.getMoreFieldsValue(queryParam),
				HttpMethod.GET, queryParam, Map.class, param);

		// 获取list数据
		List<QueryTrackData> list = (List<QueryTrackData>) map.get("vehcTrack");

		// 分页处理
		pageBean.setsEcho(queryParam.getsEcho());
		pageBean.setAaData(list);

		int i = (int) map.get("iTotalRecords");
		pageBean.setiTotalDisplayRecords(i);
		pageBean.setiTotalRecords(list.size());

		return pageBean;
	}

	/**
	 * 查询部门下拉树数据
	 * 
	 * @param treeQueryparam
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("Track/GetAllDeptTree")
	@ResponseBody
	public List<QueryTreeNode> getAllDeptTree(QueryTreeParam treeQueryparam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map<String, Object> map = new HashMap<>();
		List<QueryTreeNode> queryTreeNode = templateHelper.dealRequestWithFullUrl(
				baseApiUrl + "/Dept/getAllCompanyDeptTree", HttpMethod.POST, treeQueryparam, List.class, map);
		return queryTreeNode;
	}

	/**
	 * 查询车系
	 * 
	 * @param brandId
	 * @param lineName
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/Vehcmodel/GetVehcLineByName")
	@ResponseBody
	public List<Map<String, Object>> getVehcLineByName(
			@RequestParam(value = "brandId", required = false) String brandId,
			@RequestParam(value = "lineName", required = false) String lineName, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("application/json; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithFullUrlToken(
				baseApiUrl + "/Vehcmodel/GetVehcLineByName?brandId={brandId}&lineName={lineName}", HttpMethod.GET,
				userToken, null, List.class, brandId, lineName);
	}

	/**
	 * 导出行程数据 <功能详细描述>
	 * 
	 * @author 袁金林
	 * @param queryParam
	 * @param request
	 * @param session
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "/Track/ExportTrackData")
	@ResponseBody
	public void exportTrackData(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws Exception {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);

		Integer relationType = Integer.parseInt(request.getParameter("relationType"));
		String departmentId = request.getParameter("departmentId");
		String keyword = request.getParameter("keyword");

		QueryTrackDataParam queryParam = new QueryTrackDataParam();
		queryParam.setApikey(apikey);
		queryParam.setRelationType(relationType);
		queryParam.setDepartmentId(departmentId);
		queryParam.setKeyword(keyword);

		Excel excel = trackService.exportExcel(queryParam, userToken);
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMddHHmmss");
		String title = "行程数据" + "(" + sdfDate.format(new Date()) + ")" + ".xls";
		File tempFile = new File(title);

		ExcelExport ee = new ExcelExport(request, response, excel);
		ee.createExcel(tempFile);
	}

	/****************** 行程记录 *********************/
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/Track/TrackRecord")
	public ModelAndView getTrackRecordIndex(HttpServletRequest request, HttpServletResponse response)
			throws NoSuchAlgorithmException, ParseException {
		Map<String, Object> model = new HashMap<String, Object>();
		String imei = request.getParameter("imei");
		String eqpId = "";
		String plate = "";
		if (imei != null) {
			QueryVehcAndEqpParam queryVehcAndEqpParam = new QueryVehcAndEqpParam();
			queryVehcAndEqpParam.setApikey(apikey);
			queryVehcAndEqpParam.setImei(imei);
			queryVehcAndEqpParam.setiDisplayStart(0);
			queryVehcAndEqpParam.setiDisplayLength(10);
			Map<String, Object> map = templateHelper.dealRequestWithFullUrl(
					vmsApiUrl + "/Common/QueryVehcAndEqp?" + ReflectClassField.getMoreFieldsValue(queryVehcAndEqpParam),
					HttpMethod.GET, null, Map.class, model);
			JSONArray vhecEqpArray = JSONArray.fromObject(map.get("vhecEqpList"));
			eqpId = (String) JSONObject.fromObject(vhecEqpArray.get(0)).get("eqpId");
			plate = (String) JSONObject.fromObject(vhecEqpArray.get(0)).get("plate");
		}

		model.put("imei", imei);
		model.put("eqpId", eqpId);
		model.put("plate", plate);
		ModelAndView mv = new ModelAndView("resource/track/trackRecord", model);
		return mv;
	}

	/**
	 * 分页显示行程记录
	 * 
	 * @param queryParam
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/Track/getTrackRecord")
	@ResponseBody
	public PageBean getTrackRecord(QueryTrackRecordParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws ParseException {
		response.setContentType("text/html;charset=utf-8");
		PageBean pageBean = new PageBean();
		List<QueryTrackDataParam> lt = new ArrayList<QueryTrackDataParam>();
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
		// 调用接口查询
		Map<String, Object> map = templateHelper.dealRequestWithFullUrlToken(
				vmsApiUrl + "/Monitor/QueryTrackRecord?apikey=" + apikey + "&"
						+ ReflectClassField.getMoreFieldsValue(queryParam),
				HttpMethod.GET, usertoken, queryParam, Map.class);

		// 获取list数据
		List<QueryTrackData> list = (List<QueryTrackData>) map.get("oneTrafficData");
		// 分页处理
		pageBean.setsEcho(queryParam.getsEcho());
		int i = 0;
		if (list != null && list.size() != 0) {
			i = (int) map.get("iTotalRecords");
			pageBean.setiTotalRecords(list.size());
			pageBean.setAaData(list);
		} else {
			pageBean.setiTotalRecords(i);
			pageBean.setAaData(lt);
		}
		pageBean.setAData(map);
		pageBean.setiTotalDisplayRecords(i);

		return pageBean;
	}

	/**
	 * 查询车牌及IMEI
	 * 
	 * @author 袁金林
	 * @param queryVehcAndEqpParam
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/Track/getVehclineByQuery")
	@ResponseBody
	public Map<String, Object> getVehclineByQuery(QueryVehcAndEqpParam queryVehcAndEqpParam, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		Map<String, Object> model = new HashMap<String, Object>();
		queryVehcAndEqpParam.setiDisplayStart(0);
		queryVehcAndEqpParam.setiDisplayLength(10);
		String userToken = getUserToken(request);
		List<Dictionary> dictionary = getOpUserCompany(request, userToken, false);
		// 转换字典值
		List<TextAndValue> listDictionary = TextValueUtil.convert(dictionary);
		queryVehcAndEqpParam.setOrganizationId(
				(!listDictionary.isEmpty() && listDictionary.size() > 0) ? listDictionary.get(0).getValue() : "");
		Map<String, Object> map = templateHelper.dealRequestWithFullUrl(
				vmsApiUrl + "/Common/QueryVehcAndEqp?" + ReflectClassField.getMoreFieldsValue(queryVehcAndEqpParam),
				HttpMethod.GET, null, Map.class, model);
		return map;
	}

	/**
	 * 导出行程记录 <功能详细描述>
	 * 
	 * @author 袁金林
	 * @param queryParam
	 * @param request
	 * @param session
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "/Track/ExportTrackRecord")
	@ResponseBody
	public void exportTrackRecord(QueryTrackRecordParam queryParam, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		Excel excel = trackService.exportExcelTrackDataRecord(queryParam, userToken);
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMddHHmmss");
		String title = "行程记录" + sdfDate.format(new Date()) + ".xls";
		File tempFile = new File(title);

		ExcelExport ee = new ExcelExport(request, response, excel);
		ee.createExcel(tempFile);
	}

	/****************** 行程详情 *********************/
	@RequestMapping(value = "/Track/trackRecordDetail")
	public ModelAndView getTrackRecordDetailIndex(HttpServletRequest request, HttpServletResponse response)
			throws NoSuchAlgorithmException, ParseException {
		Map<String, Object> model = new HashMap<String, Object>();
		String trackId = "";
		if (request.getParameter("trackId") != null && !request.getParameter("trackId").equals("")) {
			trackId = request.getParameter("trackId");
		}
		String eqpId = request.getParameter("eqpId");
		model.put("trackId", trackId);
		model.put("eqpId", eqpId);
		ModelAndView mv = new ModelAndView("resource/track/trackRecordDetail", model);
		return mv;
	}

	/**
	 * 查看行程详情
	 * 
	 * @author 袁金林
	 * @param queryParam
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/Track/getTrackDetails")
	@ResponseBody
	public Map<String, Object> getTrackDetailsByTrackId(QueryTrackDetailParam queryParam, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String usertoken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		if (usertoken == null || "".equalsIgnoreCase(usertoken)) {
			HttpSession session = request.getSession();
			if (session != null) {
				usertoken = (String) session.getAttribute(Constants.REQUEST_USER_TOKEN);
			}
		}
		Map<String, Object> map = templateHelper.dealRequestWithFullUrlToken(
				vmsApiUrl + "/Monitor/QueryTrackDetails?" + ReflectClassField.getMoreFieldsValue(queryParam),
				HttpMethod.GET, usertoken, queryParam, Map.class);

		return map;
	}

	/********************************************/
	/**
	 * 行程数据详情里的行程轨迹
	 * 
	 * @author
	 * @param queryParam
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/Track/getVehcLocation")
	@ResponseBody
	public Map<String, Object> getVehcLocation(QueryTrajectoryByEqpParam queryParam, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		queryParam.setProcessOption(2); // 纠偏选项 1_不纠偏;2_百度纠偏;默认不纠偏
		queryParam.setReturnResult(1); // 返回结果 1_轨迹;2_轨迹+报警;默认轨迹
		String usertoken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		if (usertoken == null || "".equalsIgnoreCase(usertoken)) {
			HttpSession session = request.getSession();
			if (session != null) {
				usertoken = (String) session.getAttribute(Constants.REQUEST_USER_TOKEN);
			}
		}
		Map<String, Object> map = templateHelper.dealRequestWithFullUrlToken(
				vmsApiUrl + "/Monitor/QueryTrajectoryByTrack?" + ReflectClassField.getMoreFieldsValue(queryParam),
				HttpMethod.GET, usertoken, queryParam, Map.class);

		return map;
	}
}
