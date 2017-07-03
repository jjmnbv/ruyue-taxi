package com.szyciov.operate.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.entity.Dictionary;
import com.szyciov.entity.EqpTrajectory;
import com.szyciov.entity.Excel;
import com.szyciov.entity.OneTrafficData;
import com.szyciov.entity.TextAndValue;
import com.szyciov.entity.VehcLocation;
import com.szyciov.op.param.QueryTrackRecordParam;
import com.szyciov.op.param.QueryTrajectoryByEqpParam;
import com.szyciov.op.param.QueryVehcAndEqpParam;
import com.szyciov.operate.service.VehcSevice;
import com.szyciov.operate.util.BaiduMap;
import com.szyciov.operate.util.TextValueUtil;
import com.szyciov.param.VehcParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.ExcelExport;
import com.szyciov.util.ExcelExportCwt;
import com.szyciov.util.JSONUtil;
import com.szyciov.util.ReflectClassField;
import com.szyciov.util.StringUtil;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 车辆监控/车辆轨迹 <一句话功能简述> <功能详细描述>
 * 
 * @author huangyanan
 * @version [版本号, 2017年5月22日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
public class VehicleTrajectoryController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(VehicleTrajectoryController.class);
	private TemplateHelper templateHelper = new TemplateHelper();
	private String vmsApiUrl = SystemConfig.getSystemProperty("vmsApiUrl");
	private String vmsApikey = SystemConfig.getSystemProperty("vmsApikey");

	@Autowired
	private VehcSevice vehcSevice;

	/**
	 * 车辆轨迹 界面<一句话功能简述> <功能详细描述>
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "/VehicleTrajectory/Index")
	public ModelAndView vehicleTrajectoryIndex(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("resource/vehicleTrajectory/index", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/VehicleTrajectory/Index/{imei}")
	public ModelAndView vehicleTrajectoryIndex(@PathVariable("imei") String imei, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		String apikey = vmsApikey;
		String userToken = getUserToken(request);
		// initUserQueryParam(request, queryVehcAndEqpParam);
		Map<String, Object> map = templateHelper.dealRequestWithFullUrlToken(
				vmsApiUrl + "/Common/QueryVehcAndEqp?apikey=" + apikey + "&imei=" + imei, HttpMethod.GET, userToken,
				null, Map.class);
		ArrayList<Map<String, String>> list = (ArrayList) map.get("vhecEqpList");
		String plate = (String) list.get(0).get("plate");// 回显车牌
		String eqpId = (String) list.get(0).get("eqpId");// 回显设备Id
		Date date = new Date();// 得到当前时间
		SimpleDateFormat smdate = new SimpleDateFormat("yyyy-MM-dd");// 日期的格式
		String endTime = smdate.format(date);// 回显时显示的结束时间

		Calendar calendar = Calendar.getInstance();// 得到日历
		calendar.setTime(date); // 把当前时间设置给日历
		calendar.add(Calendar.DAY_OF_MONTH, -2);// 把日历的时间调成前两天
		date = calendar.getTime();// 把调整后的日历时间给date
		String startTime = smdate.format(date);// 回显时显示的开始时间

		model.put("plate", plate);
		model.put("eqpId", eqpId);
		model.put("startTime", startTime);
		model.put("endTime", endTime);
		return new ModelAndView("resource/vehicleTrajectory/index", model);
	}

	/**
	 * 查询车辆设备列表<一句话功能简述> <功能详细描述>
	 * 
	 * @param queryVehcAndEqpParam
	 * @param request
	 * @param response
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/VehicleTrajectory/getVehclineByQuery")
	@ResponseBody
	public Map<String, Object> getVehclineByQuery(QueryVehcAndEqpParam queryVehcAndEqpParam, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		queryVehcAndEqpParam.setiDisplayLength(10);
		String userToken = getUserToken(request);
		List<Dictionary> dictionary = getOpUserCompany(request, userToken, false);
		// 转换字典值
		List<TextAndValue> listDictionary = TextValueUtil.convert(dictionary);
		queryVehcAndEqpParam.setOrganizationId(
				(!listDictionary.isEmpty() && listDictionary.size() > 0) ? listDictionary.get(0).getValue() : "");

		Map<String, Object> map = templateHelper.dealRequestWithFullUrlToken(
				vmsApiUrl + "/Common/QueryVehcAndEqp?" + ReflectClassField.getMoreFieldsValue(queryVehcAndEqpParam),
				HttpMethod.GET, userToken, null, Map.class);
		return map;
	}

	/**
	 * 根据设备查询绑定行程信息
	 * 
	 * @author 袁金林
	 * @param queryParam
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/VehicleTrajectory/getTrackRecord")
	@ResponseBody
	public Map<String, Object> getTrackRecord(QueryTrackRecordParam queryParam, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("application/json; charset=utf-8");
		Map<String, Object> map = new HashMap<String, Object>();

		queryParam.setiDisplayLength(10);
		String userToken = getUserToken(request);

		List<Dictionary> dictionary = getOpUserCompany(request, userToken, false);
		// 转换字典值
		List<TextAndValue> listDictionary = TextValueUtil.convert(dictionary);
		queryParam.setOrganizationId(
				(!listDictionary.isEmpty() && listDictionary.size() > 0) ? listDictionary.get(0).getValue() : "");
		// 行程记录
		Map<String, Object> trackRecordMap = templateHelper.dealRequestWithFullUrlToken(
				vmsApiUrl + "/Monitor/QueryTrackRecord?" + ReflectClassField.getMoreFieldsValue(queryParam),
				HttpMethod.GET, userToken, null, Map.class);

		map.put("trackRecord", trackRecordMap);
		return map;
	}

	/**
	 * 根据设备查询轨迹 和 行程记录
	 * 
	 * @author 袁金林
	 * @param queryParam
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/VehicleTrajectory/getTrajectoryByEqp")
	@ResponseBody
	public Map<String, Object> getTrajectoryByEqp(QueryTrajectoryByEqpParam queryTrajectoryByEqpParam,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json; charset=utf-8");
		Map<String, Object> map = new HashMap<String, Object>();
		String userToken = getUserToken(request);
		List<Dictionary> dictionary = getOpUserCompany(request, userToken, false);
		// 转换字典值
		List<TextAndValue> listDictionary = TextValueUtil.convert(dictionary);
		queryTrajectoryByEqpParam.setOrganizationId(
				(!listDictionary.isEmpty() && listDictionary.size() > 0) ? listDictionary.get(0).getValue() : "");
		// 行程轨迹
		Map<String, Object> trajectoryMap = templateHelper.dealRequestWithFullUrlToken(
				vmsApiUrl + "/Monitor/QueryTrajectoryByEqp?"
						+ ReflectClassField.getMoreFieldsValue(queryTrajectoryByEqpParam),
				HttpMethod.GET, userToken, null, Map.class);

		map.put("trajectory", trajectoryMap);
		return map;
	}

	/**
	 * 查询轨迹
	 * 
	 * @author 袁金林
	 * @param request
	 * @return
	 * @throws ParseException
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/VehicleTrajectory/GetGpsListTrack")
	@ResponseBody
	public Map<String, Object> getGpsListTrack(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("application/json; charset=utf-8");
		Map<String, Object> map = new HashMap<String, Object>();

		JSONArray jsonArray = JSONArray.fromObject("[" + request.getParameter("trajectory") + "]");// 转化为json数组-------JSONArray对象得到数组
		if (jsonArray.toArray() == null) {
			map.put("vehcTrajectory", null);
			map.put("alarmList", null);
			return map;
		}
		// map.put("trajectory", jsonArray);
		String trackId = (String) JSONObject.fromObject(jsonArray.get(0)).get("trackId");
		String startTime = (String) JSONObject.fromObject(jsonArray.get(0)).get("startTime");
		String endTime = (String) JSONObject.fromObject(jsonArray.get(0)).get("endTime");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 创建日期转换对象HH:mm:ss为时分秒，年月日为yyyy-MM-dd
		Date stTime = null;// 将字符串转换为date类型
		Date edTime = null;
		if (!startTime.equals("")) {
			try {
				stTime = df.parse(startTime);
			} catch (ParseException e) {
				stTime = null;
			} // 将字符串转换为date类型
		}
		if (!endTime.equals("")) {
			try {
				edTime = df.parse(endTime);
			} catch (ParseException e) {
				edTime = null;
			} // 将字符串转换为date类型
		} else {
			edTime = stTime;
		}

		JSONArray jsonArrayTrajectory = JSONArray
				.fromObject("[" + JSONObject.fromObject(jsonArray.get(0)).get("trajectory") + "]");

		String key = "";
		String value = "";
		String vehcTrajectory = "";
		String alarmList = "";
		for (int i = 0; i < jsonArrayTrajectory.size(); i++) { // 遍历json数组
			JSONObject jsonObject = JSONObject.fromObject(jsonArrayTrajectory.get(i));
			Iterator iterator = jsonObject.keys();
			while (iterator.hasNext()) {
				key = (String) iterator.next();
				value = jsonObject.getString(key);
				if (key.equals("vehcTrajectory")) {
					vehcTrajectory = value;
				}
				if (key.equals("alarmList")) {
					alarmList = value;
				}
			}
		}
		JSONArray vehcTrajectoryArray = JSONArray.fromObject(vehcTrajectory);// 转化为json数组-------JSONArray对象得到数组
		if (!vehcTrajectoryArray.toString().equals("[]")) {
			for (int i = 0; i < vehcTrajectoryArray.size(); i++) { // 遍历json数组
				JSONObject obj = JSONObject.fromObject(vehcTrajectoryArray.get(i)); // 转化为json对象---------json字符串解析
				Date timeTemp;
				try {
					timeTemp = df.parse(obj.getString("locTime"));
				} catch (ParseException e) {
					timeTemp = null;
				} // 将字符串转换为date类型
				if (stTime.getTime() < timeTemp.getTime() && timeTemp.getTime() < edTime.getTime()
						&& trackId.equals(obj.get("trackId").toString()))// 比较时间大小,dt1小于dt2
				{

				} else {
					vehcTrajectoryArray.remove(i);
					i = -1;
				}
				// if(!obj.getString("trackId").equals(trackId)){
				// vehcTrajectoryArray.remove(i);
				// i=-1;
				// }
			}
		}
		JSONArray alarmListArray = JSONArray.fromObject(alarmList);// 转化为json数组-------JSONArray对象得到数组
		for (int i = 0; i < alarmListArray.size(); i++) { // 遍历json数组
			JSONObject obj = JSONObject.fromObject(alarmListArray.get(i)); // 转化为json对象---------json字符串解析
			Date sttimeTemp = null;
			Date edtimeTemp = null;
			int num = 0;
			if (!obj.getString("startTime").equals("")) {
				try {
					sttimeTemp = df.parse(obj.getString("startTime"));
				} catch (ParseException e) {
					sttimeTemp = null;
				} // 将字符串转换为date类型
				if (stTime.getTime() < sttimeTemp.getTime() && sttimeTemp.getTime() < edTime.getTime())// 比较时间大小,dt1小于dt2
				{
					continue;// 在范围内
				} else {
					num++;// 不在范围内，不显示
				}
			} else {
				num++;
				;// 开始时间为空，无法比较
			}
			if (!obj.getString("endTime").equals("")) {
				try {
					edtimeTemp = df.parse(obj.getString("endTime"));
				} catch (ParseException e) {
					edtimeTemp = null;
				} // 将字符串转换为date类型
				if (stTime.getTime() < edtimeTemp.getTime() && edtimeTemp.getTime() < edTime.getTime())// 比较时间大小,dt1小于dt2
				{
					continue;// 在范围内
				} else {
					num++;// 不在范围内，不显示
				}
			} else {
				num++;// 结束时间为空，无法比较，不显示
			}
			if (num == 2) {
				alarmListArray.remove(i);
				i = -1;
			}
		}

		map.put("vehcTrajectory", vehcTrajectoryArray);
		map.put("alarmList", alarmListArray);
		// if(vehcTrajectoryArray.size()>0){
		// result = parseJSON2Map(vehcTrajectoryArray.toString());
		// }

		return map;
	}

	/**
	 * 导出行程 <功能详细描述>
	 * 
	 * @author 袁金林
	 * @param queryParam
	 * @param request
	 * @param session
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/VehicleTrajectory/ExportTrackRecord")
	@ResponseBody
	public void exportTrackRecord(QueryTrackRecordParam queryParam, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		String fileName = "";
		String title = "导出行程";
		// 表头
		List<String> titleList = new ArrayList<String>();
		Map<String, String> header = this.getTrackRecordTitle(titleList);
		if (null == header || header.isEmpty()) {
			return;
		}
		// 查询数据
		String userToken = getUserToken(request);
		queryParam.setiDisplayStart(0);
		queryParam.setiDisplayLength(9999);
		Map<String, Object> trackRecordMap = templateHelper.dealRequestWithFullUrlToken(
				vmsApiUrl + "/Monitor/QueryTrackRecord?" + ReflectClassField.getMoreFieldsValue(queryParam),
				HttpMethod.GET, userToken, null, Map.class);
		// 处理查询结果
		List<Map<String, Object>> list = new ArrayList<>();
		JSONArray vehcTrajectoryArray = JSONArray.fromObject(trackRecordMap.get("oneTrafficData"));// 转化为json数组-------JSONArray对象得到数组
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
				// 行程开始时间
				if ("startTime".equals(key) || "endTime".equals(key)) {
					if (StringUtils.isBlank(data)) {
						dataList.add("/");
					} else {
						dataList.add(data);
					}
				}
				if ("mileage".equals(key)) {
					dataList.add(StringUtil.formatNum(Double.valueOf(data), 1));
				}
				if ("cumulativeOil".equals(key)) {
					dataList.add(StringUtil.formatNum(Double.valueOf(data), 1));
				}
				if ("fuelConspt".equals(key)) {
					dataList.add(StringUtil.formatNum(Double.valueOf(data), 1));
				}
				if ("idleFuel".equals(key)) {
					dataList.add(StringUtil.formatNum(Double.valueOf(data), 1));
				}
				if ("runLength".equals(key)) {
					if (StringUtils.isBlank(data)) {
						dataList.add("/");
					} else {
						dataList.add(data);
					}
				}
				if ("totalIdleTime".equals(key)) {
					dataList.add(StringUtil.formatNum(Double.valueOf(data), 1));
				}
				if ("avgSpeed".equals(key)) {
					dataList.add(StringUtil.formatNum(Double.valueOf(data), 1));
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
	 * 导出轨迹 <功能详细描述>
	 * 
	 * @author 袁金林
	 * @param queryParam
	 * @param request
	 * @param session
	 * @return
	 * @throws IOException
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/VehicleTrajectory/ExportTrajectory")
	@ResponseBody
	public void exportTrajectory(QueryTrajectoryByEqpParam queryTrajectoryByEqpParam, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws IOException {

		String userToken = getUserToken(request);

		// 获取轨迹
		queryTrajectoryByEqpParam.setiDisplayStart(0);
		queryTrajectoryByEqpParam.setiDisplayLength(9999);
		queryTrajectoryByEqpParam.setProcessOption(2);// 纠偏选项 1_不纠偏;2_百度纠偏;默认不纠偏

		Map<String, Object> trajectoryMap = templateHelper.dealRequestWithFullUrlToken(
				vmsApiUrl + "/Monitor/QueryTrajectoryByEqp?"
						+ ReflectClassField.getMoreFieldsValue(queryTrajectoryByEqpParam),
				HttpMethod.GET, userToken, null, Map.class);
		List<Map<String, Object>> list = new ArrayList<>();
		JSONArray vehcTrajectoryArray = JSONArray.fromObject(trajectoryMap.get("vehcTrajectory"));// 转化为json数组-------JSONArray对象得到数组
		list = JSONUtil.parseJSON2Map(vehcTrajectoryArray);
		String address = "";

		// 获取车辆信息
		VehcParam vehc = vehcSevice.getVehcById(queryTrajectoryByEqpParam.getVehcId(), userToken);
		// todo 去掉vehc为空的判断
		String vehcInfo = "";
		if (vehc != null) {
			vehcInfo = "车辆: " + vehc.getPlates() + " 所属部门: " + vehc.getDeptName();
		} else {
			vehcInfo = "车辆信息不存在，导出错误数据";
		}

		// 获取行程
		QueryTrackRecordParam queryParam = new QueryTrackRecordParam();
		queryParam.setApikey(vmsApikey);
		queryParam.setEqpId(queryTrajectoryByEqpParam.getEqpId());
		queryParam.setOrganizationId(queryTrajectoryByEqpParam.getOrganizationId());
		queryParam.setStartTime(queryTrajectoryByEqpParam.getStartTime());
		queryParam.setEndTime(queryTrajectoryByEqpParam.getEndTime());
		queryParam.setDepartmentId(queryTrajectoryByEqpParam.getOrganizationId());
		queryParam.setiDisplayStart(0);
		queryParam.setiDisplayLength(10);

		Map<String, Object> trackRecordMap = templateHelper.dealRequestWithFullUrlToken(
				vmsApiUrl + "/Monitor/QueryTrackRecord?" + ReflectClassField.getMoreFieldsValue(queryParam),
				HttpMethod.GET, userToken, null, Map.class);
		List<VehcLocation> vehcLocationList = new ArrayList<>();
		List<OneTrafficData> oneTrafficData = (List<OneTrafficData>) trackRecordMap.get("oneTrafficData");

		// 转换接口查询数据
		List<OneTrafficData> tempTrafficList = new ArrayList<>();
		for (Object oneTrafficDatum : oneTrafficData) {
			LinkedHashMap<String, Object> traffivDataList = (LinkedHashMap<String, Object>) oneTrafficDatum;
			OneTrafficData trafficData = new OneTrafficData();
			trafficData.setStartTime(String.valueOf(traffivDataList.get("startTime")));
			trafficData.setEndTime(String.valueOf(traffivDataList.get("endTime")));
			tempTrafficList.add(trafficData);
		}

		// 收集行程信息
		for (OneTrafficData oneTrafficDatum : tempTrafficList) {
			if (oneTrafficDatum.getStartTime() != null && !oneTrafficDatum.getStartTime().equals("")) {
				VehcLocation startVehcLocation = new VehcLocation();
				startVehcLocation.setLocTime(oneTrafficDatum.getStartTime());
				startVehcLocation.setWorkStatusText("点火");
				startVehcLocation.setSpeed(null);
				startVehcLocation.setDirection("");
				for (Map<String, Object> m : list) {
					BigDecimal longitude = new BigDecimal(0);
					BigDecimal latitude = new BigDecimal(0);
					for (String k : m.keySet()) {
						if (k.equals("longitudeOffSet")) { // 纠偏后的经度
							longitude = new BigDecimal(m.get(k).toString());
						}
						if (k.equals("latitudeOffSet")) {// 纠偏后的纬度
							latitude = new BigDecimal(m.get(k).toString());
						}
					}
					try {
						address = BaiduMap.getAdress(latitude, longitude);// 根据纠偏后的经纬度获取地址
					} catch (Exception ex) {
						address = "";
					}
				}
				startVehcLocation.setAddress(address);
				vehcLocationList.add(startVehcLocation);
			}

			// 收集GPS列表
			List<EqpTrajectory> trajectoryList = (List<EqpTrajectory>) trajectoryMap.get("vehcTrajectory");
			// 转换接口查询数据
			List<EqpTrajectory> tempTrajectoryList = new ArrayList<>();
			if (trajectoryList != null && trajectoryList.size() > 0) {
				for (Object eqpTrajectory : trajectoryList) {
					LinkedHashMap<String, Object> traffivDataList = (LinkedHashMap<String, Object>) eqpTrajectory;
					EqpTrajectory trajectory = new EqpTrajectory();
					trajectory.setLocTime(String.valueOf(traffivDataList.get("locTime")));
					trajectory.setLongitude(new BigDecimal(String.valueOf(traffivDataList.get("longitude"))));
					trajectory.setLatitude(new BigDecimal(String.valueOf(traffivDataList.get("latitude"))));
					trajectory.setSpeed(new BigDecimal(String.valueOf(traffivDataList.get("speed"))));
					trajectory.setDirection(String.valueOf(traffivDataList.get("direction")));

					tempTrajectoryList.add(trajectory);
				}
			}

			if (tempTrajectoryList != null && tempTrajectoryList.size() > 0) {
				for (int i = 0; i <= tempTrajectoryList.size(); i += 18) {
					if (tempTrajectoryList.get(i).getLongitude() != null
							&& ((tempTrajectoryList.get(i).getLongitude()).compareTo(new BigDecimal(0))) > 0
							&& tempTrajectoryList.get(i).getLatitude() != null
							&& ((tempTrajectoryList.get(i).getLatitude()).compareTo(new BigDecimal(0))) > 0
							&& tempTrajectoryList.get(i).getLocTime() != null) {
						VehcLocation vehcLocation = new VehcLocation();
						vehcLocation.setLocTime(tempTrajectoryList.get(i).getLocTime());
						vehcLocation.setSpeed(tempTrajectoryList.get(i).getSpeed() != null
								? (tempTrajectoryList.get(i).getSpeed()) : null);
						vehcLocation.setDirection(tempTrajectoryList.get(i).getDirection());
						// 如果位置在行程内，则表示车辆运行中，否则是车辆停止
						if (tempTrajectoryList.get(i).getSpeed() != null
								&& ((tempTrajectoryList.get(i).getSpeed()).compareTo(new BigDecimal(0))) > 0) {
							vehcLocation.setWorkStatusText("行驶");
						} else {
							vehcLocation.setWorkStatusText("怠速");
						}
						address = BaiduMap.getAdress(tempTrajectoryList.get(i).getLatitude(),
								tempTrajectoryList.get(i).getLongitude());
						vehcLocation.setAddress(address);
						vehcLocationList.add(vehcLocation);
					}
				}
			}

			if (oneTrafficDatum.getEndTime() != null && !oneTrafficDatum.getEndTime().equals("")) {
				VehcLocation endVehcLocation = new VehcLocation();
				endVehcLocation.setLocTime(oneTrafficDatum.getEndTime());
				endVehcLocation.setWorkStatusText("熄火");
				endVehcLocation.setSpeed(null);
				endVehcLocation.setDirection("");
				for (Map<String, Object> m : list) {
					BigDecimal longitude = new BigDecimal(0);
					BigDecimal latitude = new BigDecimal(0);
					for (String k : m.keySet()) {
						if (k.equals("longitudeOffSet")) { // 纠偏后的经度
							longitude = new BigDecimal(m.get(k).toString());
						}
						if (k.equals("latitudeOffSet")) {// 纠偏后的纬度
							latitude = new BigDecimal(m.get(k).toString());
						}
					}
					try {
						address = BaiduMap.getAdress(latitude, longitude);// 根据纠偏后的经纬度获取地址
					} catch (Exception ex) {
						address = "";
					}
				}
				endVehcLocation.setAddress(address);
				vehcLocationList.add(endVehcLocation);
			}

		}

		// 构建需要Excel表格数据
		File demoFile = new File(session.getServletContext().getRealPath("/") + "content/template/历史轨迹导出模板.xls");
		POIFSFileSystem poifsFileSystem = new POIFSFileSystem(new FileInputStream(demoFile));
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMddHHmmss");
		// todo 去掉vehc为空的判断
		String plates = "";
		if (vehc != null) {
			plates = vehc.getPlates();
		}
		String fileName = "导出轨迹 " + plates + sdfDate.format(new Date()) + ".xls";
		File file = new File(fileName);
		HSSFWorkbook workbook = new HSSFWorkbook(poifsFileSystem);
		HSSFSheet sheet = workbook.getSheetAt(0);
		HSSFRow firstRow = sheet.getRow(0);
		firstRow.createCell(0).setCellValue(vehcInfo);

		// 写入数据，Excel索引从1开始。
		for (int i = 0; i < vehcLocationList.size(); i++) {
			//
			int row = 2 + i;
			HSSFRow sheetRow = sheet.createRow(row);
			// 序号
			sheetRow.createCell(0).setCellValue(i + 1);
			// 时间
			sheetRow.createCell(1).setCellValue(
					(vehcLocationList.get(i).getLocTime() != null && vehcLocationList.get(i).getLocTime() != "")
							? vehcLocationList.get(i).getLocTime() : "--");
			// 发动机状态
			sheetRow.createCell(2)
					.setCellValue((vehcLocationList.get(i).getWorkStatusText() != null
							&& vehcLocationList.get(i).getWorkStatusText() != "")
									? vehcLocationList.get(i).getWorkStatusText() : "--");
			// 时速
			sheetRow.createCell(3)
					.setCellValue(((String.valueOf(vehcLocationList.get(i).getSpeed())) != null
							&& (String.valueOf(vehcLocationList.get(i).getSpeed())) != ""
							&& (String.valueOf(vehcLocationList.get(i).getSpeed())) != "null")
									? String.valueOf(vehcLocationList.get(i).getSpeed()) : "--");
			// 方向
			sheetRow.createCell(4)
					.setCellValue((vehcLocationList.get(i).getDirection() != null
							&& vehcLocationList.get(i).getDirection() != "") ? vehcLocationList.get(i).getDirection()
									: "--");
			// 位置
			sheetRow.createCell(5).setCellValue((vehcLocationList.get(i).getAddress() != null
					&& vehcLocationList.get(i).getAddress() != "" && vehcLocationList.get(i).getAddress() != "null")
							? vehcLocationList.get(i).getAddress() : "--");

		}
		String mileage = String.valueOf(trackRecordMap.get("mileage"));
		String totalFuel = String.valueOf(trackRecordMap.get("totalFuel"));

		HSSFRow lastrow = sheet.createRow(vehcLocationList.size() + 3);
		lastrow.createCell(0).setCellValue("");
		lastrow.createCell(1).setCellValue("合计里程数 km");
		lastrow.createCell(2).setCellValue((mileage != null && mileage != "null") ? mileage : "--");
		lastrow.createCell(3).setCellValue("");
		lastrow.createCell(4).setCellValue("");
		lastrow.createCell(5).setCellValue("");

		HSSFRow lastrow2 = sheet.createRow(vehcLocationList.size() + 4);
		lastrow2.createCell(0).setCellValue("");
		lastrow2.createCell(1).setCellValue("合计耗油量 L");
		lastrow2.createCell(2).setCellValue((totalFuel != null && totalFuel != "null") ? totalFuel : "--");
		lastrow2.createCell(3).setCellValue("");
		lastrow2.createCell(4).setCellValue("");
		lastrow2.createCell(5).setCellValue("");
		sheet.setForceFormulaRecalculation(true);

		OutputStream outputStream = null;
		try {
			if (request == null || response == null) {
				outputStream = new FileOutputStream(file);
			} else {
				ExcelExportCwt ee = new ExcelExportCwt(request, response);
				String decodeName = ee.decodeFileName(file.getName());
				response.reset();
				response.addHeader("Content-Disposition", "attachment;filename=" + decodeName);
				response.addHeader("Transfer-Encoding", "Chunked"); // 改为Chunked则不需要声明文件长度,由http协议自动判断
				response.setContentType("application/octet-stream");
				outputStream = response.getOutputStream();
				workbook.write(outputStream);

			}
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			logger.error("写入Excel失败:");
			e.printStackTrace();
		}

	}

	/**
	 * <一句话功能简述> <功能详细描述>
	 * 
	 * @param headerList
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	private Map<String, String> getTrackRecordTitle(List<String> headerList) {
		Map<String, String> title = new HashMap<String, String>();
		title.put("startTime", "行程开始时间");
		headerList.add("行程开始时间");

		title.put("endTime", "行程结束时间");
		headerList.add("行程结束时间");

		title.put("mileage", "当前设备里程(km)");
		headerList.add("当前设备里程(km)");

		title.put("cumulativeOil", "行程百公里油耗(L/100km)");
		headerList.add("行程百公里油耗(L/100km)");

		title.put("fuelConspt", "行程累计耗油(L)");
		headerList.add("行程累计耗油(L)");

		title.put("idleFuel", "行程怠速耗油(L)");
		headerList.add("行程怠速耗油(L)");

		title.put("runLength", "行程总行驶时间(分钟)");
		headerList.add("行程总行驶时间(分钟)");

		title.put("totalIdleTime", "怠速时长(分钟)");
		headerList.add("怠速时长(分钟)");

		title.put("avgSpeed", "行程平均车速(km/h)");
		headerList.add("行程平均车速(km/h)");

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
