package com.szyciov.carservice.service;

import com.szyciov.carservice.dao.BaiduApiDao;
import com.szyciov.entity.Retcode;
import com.szyciov.param.BaiduApiQueryParam;
import com.szyciov.util.StringUtil;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("baiduApiService")
public class BaiduApiService {
	private TemplateHelper templateHelper = new TemplateHelper();
	private BaiduApiDao baiduApiDao;
	private Logger logger = Logger.getLogger(BaiduApiService.class);
	
	@Resource(name="baiduApiDao")
	public void setBaiduApiDao(BaiduApiDao baiduApiDao) {
		this.baiduApiDao = baiduApiDao;
	}
	

	/**
	 * 获取里程信息
	 * @param queryParam
	 * @return
	 */
	public Map<String, Object> getMileageInfo(BaiduApiQueryParam queryParam) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, String> params = new HashMap<String, String>();
		StringBuffer urlSb = new StringBuffer();
		if(queryParam.getOrderStartCityName() == null) {
			String cityName = getCityName(queryParam.getOrderStartLat(), queryParam.getOrderStartLng());
			queryParam.setOrderStartCityName(cityName);
		}
		
		if(queryParam.getOrderEndCityName() == null) {
			String cityName = getCityName(queryParam.getOrderEndLat(), queryParam.getOrderEndLng());
			queryParam.setOrderEndCityName(cityName);
		}
		
		urlSb.append("http://api.map.baidu.com/direction/v1?");
		urlSb.append("mode=");
		urlSb.append("driving");
		urlSb.append("&origin=");
		urlSb.append(queryParam.getOrderStartLat());
		urlSb.append(",");
		urlSb.append(queryParam.getOrderStartLng());
		urlSb.append("&destination=");
		urlSb.append(queryParam.getOrderEndLat());
		urlSb.append(",");
		urlSb.append(queryParam.getOrderEndLng());
		urlSb.append("&origin_region=");
		urlSb.append(queryParam.getOrderStartCityName());
		urlSb.append("&destination_region=");
		urlSb.append(queryParam.getOrderEndCityName());
		urlSb.append("&output=");
		urlSb.append("json");
		urlSb.append("&ak=");
		urlSb.append(SystemConfig.getSystemProperty("yingyan_ak"));
		
		String resultStr = null;
		String estimate_count = SystemConfig.getSystemProperty("estimate_count");
		int count = 5;
		if(!StringUtils.isBlank(estimate_count) && !StringUtils.isNumeric(estimate_count)) {
			count = Integer.valueOf(estimate_count);
		}
		JSONObject resultJson = null;
		for(int m = 0;m < count;m++) {
			resultStr = templateHelper.dealRequestWithFullUrl(urlSb.toString(), HttpMethod.GET, null, String.class, params);
			if(null != resultStr) {
				resultJson = JSONObject.fromObject(resultStr);
				if(resultJson.get("status").equals(0)) {
					break;
				}
			}
		}
		try {
			JSONObject milesJson = resultJson.getJSONObject("result").getJSONArray("routes").getJSONObject(0);
			resultMap.put("status", Retcode.OK.code);
			resultMap.put("message", Retcode.OK.msg);
			resultMap.put("distance", milesJson.get("distance"));
			resultMap.put("duration", milesJson.get("duration"));
		} catch (Exception e) {
			logger.error("获取预估里程失败", e);
			resultMap.put("status", Retcode.EXCEPTION.code);
			resultMap.put("message", Retcode.EXCEPTION.msg);
		}
		return resultMap;
	}

	/**
	 * 根据经纬度获取城市名称
	 * @param lat
	 * @param lng
	 * @return
	 */
	private String getCityName(double lat, double lng) {
		try {
			Map<String, String> params = new HashMap<String, String>();
			StringBuffer urlSb = new StringBuffer();
			
			urlSb.append("http://api.map.baidu.com/geocoder/v2/?");
			urlSb.append("location=");
			urlSb.append(lat);
			urlSb.append(",");
			urlSb.append(lng);
			urlSb.append("&pois=0");
			urlSb.append("&output=");
			urlSb.append("json");
			urlSb.append("&ak=");
			urlSb.append(SystemConfig.getSystemProperty("yingyan_ak"));
			String resultStr = templateHelper.dealRequestWithFullUrl(urlSb.toString(), HttpMethod.GET, null, String.class, params);
			
			JSONObject resultJson = JSONObject.fromObject(resultStr);
			String cityName = resultJson.getJSONObject("result").getJSONObject("addressComponent").getString("city");
			
			if(cityName.endsWith("市")) {
				cityName = cityName.substring(0, cityName.length() - 1);
			}
			
			return cityName;
		} catch (Exception e) {
			logger.error("获取城市名称失败", e);
		}
		
		return null;
	}

	public JSONObject getOrderMileage(String orderno, String startDateStr, String endDateStr, String userToken) {
		Map<String, Object> traceParams = new HashMap<String, Object>();
		traceParams.put("orderno", orderno);
		traceParams.put("fullReturn", false);

		JSONObject json = new JSONObject();
        JSONObject resultJson = getBaiduTraceData(orderno, startDateStr, endDateStr, userToken, true, 2);
        if(!resultJson.has("distance")) {
            json.put("distance", 0.0);
            return json;
        }
		json.put("distance", resultJson.get("distance"));

		return json;
	}

	/**
	 * 调用百度鹰眼接口获取轨迹数据
	 * @param orderno
	 * @param startDateStr
	 * @param endDateStr
	 * @param userToken
	 * @return
	 */
	private JSONObject getBaiduTraceData(String orderno, String startDateStr, String endDateStr, String userToken, boolean isProcessed, int simple_return) {
		JSONObject resultJson = new JSONObject();
		Map<String, String> params = new HashMap<String, String>();
		StringBuffer urlSb = new StringBuffer();
		
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date startDate = dateFormat.parse(startDateStr);
			Date endDate = new Date();
			
			if(endDateStr != null) {
				endDate = dateFormat.parse(endDateStr);
			}
			
			urlSb.append("http://api.map.baidu.com/trace/v2/track/gethistory?");
			urlSb.append("ak=");
			urlSb.append(SystemConfig.getSystemProperty("yingyan_ak"));
			urlSb.append("&service_id=");
			urlSb.append(SystemConfig.getSystemProperty("yingyan_serviceid"));
			urlSb.append("&entity_name=");
			urlSb.append(orderno);
			urlSb.append("&simple_return=");
			urlSb.append(simple_return);
			urlSb.append("&start_time=");
			urlSb.append(Long.toString(startDate.getTime() / 1000));
			urlSb.append("&end_time=");
			urlSb.append(Long.toString(endDate.getTime() / 1000));
			if(isProcessed) {
				urlSb.append("&is_processed=1");
				urlSb.append("&process_option=need_denoise=1,need_vacuate=1,need_mapmatch=1,transport_mode=1");
			} else {
				urlSb.append("&is_processed=0");
			}
			urlSb.append("&supplement_mode=driving");
			urlSb.append("&page_size=5000");
			String resultStr = templateHelper.dealRequestWithFullUrl(urlSb.toString(), HttpMethod.GET, null, String.class, params);
			
			resultJson = JSONObject.fromObject(resultStr);
			
			if(resultJson.getInt("status") != Retcode.OK.code) {
				resultJson.put("status", Retcode.FAILED.code);
			}
			
			if(resultJson.containsKey("points") && !resultJson.getJSONArray("points").isEmpty()) {
				JSONArray pointArr = resultJson.getJSONArray("points");
				//返回的轨迹数据需要倒序
				if(pointArr.getJSONObject(0).getLong("loc_time") > pointArr.getJSONObject(pointArr.size() - 1).getLong("loc_time")) {
					JSONArray json = new JSONArray();
					for (int i = pointArr.size() - 1; i >= 0; i--) {
						json.add(pointArr.get(i));
					}
					resultJson.put("points", json.toString());
				}
			}
			
			if(!resultJson.containsKey("duration")) {
				resultJson.put("duration", 0);
			}
		} catch (Exception e) {
			logger.error("获取轨迹数据失败", e);
			resultJson.put("status", Retcode.FAILED.code);
		}
		
		return resultJson;
	}
	
	/**
	 * 根据轨迹数据创建低速时长
	 * @param orderno
	 * @param traceJson
	 * @param lowSpeed
	 */
	private void createLowSpeedTimes(String orderno, JSONObject traceJson, double lowSpeed) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			double lowSpeedCost = 0;
			JSONArray pointJsonArr = traceJson.getJSONArray("points");
			
			for(int i = 0; i < pointJsonArr.size(); i++) {
				JSONObject pointJson = pointJsonArr.getJSONObject(i);
				if(pointJson.containsKey("speed")) {
					double speed = pointJson.getDouble("speed");	
					String createTimeStr = pointJson.getString("create_time");
					Date createTime = dateFormat.parse(createTimeStr);
					
					if(speed < lowSpeed && (i + 1) < pointJsonArr.size()) {
						JSONObject nextPointJson = pointJsonArr.getJSONObject(i + 1);
						Date nextCreateTime = dateFormat.parse(nextPointJson.getString("create_time"));
						
						lowSpeedCost += (createTime.getTime() - nextCreateTime.getTime()) / 1000;
					}
				}
				
			}
			
			if(lowSpeedCost <= 0) {
				traceJson.put("lowspeedcost", 0);
			} else {
				traceJson.put("lowspeedcost", lowSpeedCost);
			}
			
			
		} catch (Exception e) {
			logger.error(orderno + "的订单创建订单低速用时异常：", e);
		}
		
	}
	
	
	public void createEntity(String orderno, String userToken) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("ak", "AROf12zX6qSnsPrTzHOPSywY2Yh01jNF");
		params.add("service_id", "125696");
		params.add("entity_name", orderno);
		
		templateHelper.dealRequestWithFullUrlToken("http://api.map.baidu.com/trace/v2/entity/add", 
				HttpMethod.POST, userToken, params, String.class);
	}
	
	public void addPoint(String orderno, String userToken) {
		double[][] ponitArr = {{119.78736528643,31.979624614742,1413538256},{119.7873622858,31.979628190208,1413538250}};
		
		for(int i = 0; i < ponitArr.length; i++) {
			MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
			params.add("ak", SystemConfig.getSystemProperty("yingyan_ak"));
			params.add("service_id", SystemConfig.getSystemProperty("yingyan_serviceid"));
			params.add("latitude", ponitArr[i][1] + "");
			params.add("longitude", ponitArr[i][0] + "");
			params.add("loc_time", Long.toString(new Date().getTime() / 1000));
			params.add("coord_type", "1");
			params.add("entity_name", orderno);
			
			String result = templateHelper.dealRequestWithFullUrlToken("http://api.map.baidu.com/trace/v2/track/addpoint", 
					HttpMethod.POST, userToken, params, String.class);
			
			System.out.println(result);
		}
	}

	public Map<String, Object> createTraceData(String orderno, String userToken) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		createEntity(orderno, userToken);
		addPoint(orderno, userToken);
		return resultMap;
	}
	
	public JSONObject getLatLng(BaiduApiQueryParam param){
		String url = "http://api.map.baidu.com/geocoder/v2/?ak={ak}&output={output}&address={address}&city={city}";
		Map<String, Object> uriParam = new HashMap<>();
		uriParam.put("ak", SystemConfig.getSystemProperty("yingyan_ak"));
		uriParam.put("output", "json");
		uriParam.put("address", param.getAddress());
		uriParam.put("city", param.getCity());
		String str = templateHelper.dealRequestWithFullUrl(url, HttpMethod.POST, null, String.class, uriParam);
		JSONObject result = JSONObject.fromObject(str);
		if(Retcode.OK.code == result.getInt("status")){
			double lat,lng;
			int precise,confidence;
			String level;
			lat = result.getJSONObject("result").getJSONObject("location").getDouble("lat");
			lng = result.getJSONObject("result").getJSONObject("location").getDouble("lng");
			precise = result.getJSONObject("result").getInt("precise");
			confidence = result.getJSONObject("result").getInt("confidence");
			level = result.getJSONObject("result").getString("level");
			result.clear();
			result.put("lat", lat);
			result.put("lng", lng);
			result.put("precise", precise);
			result.put("confidence", confidence);
			result.put("level", level);
		}else{
			result.clear();
			result.put("status", Retcode.FAILED.code);
			result.put("message", Retcode.FAILED.msg);
		}
		return result; 
	}
	
	public JSONObject getAddress(BaiduApiQueryParam param){
		String url = "http://api.map.baidu.com/geocoder/v2/?location={lat},{lng}&output={output}&pois=1&ak={ak}";
		Map<String, Object> uriParam = new HashMap<>();
		uriParam.put("ak", SystemConfig.getSystemProperty("yingyan_ak"));
		uriParam.put("output", "json");
		uriParam.put("lng", param.getOrderStartLng());
		uriParam.put("lat", param.getOrderStartLat());
		String str = templateHelper.dealRequestWithFullUrl(url, HttpMethod.POST, null, String.class, uriParam);
		JSONObject result = JSONObject.fromObject(str);
		if(Retcode.OK.code == result.getInt("status")){
			double lat,lng;
			String address,description,business,city;
			JSONObject jsonAddr = result.getJSONObject("result").getJSONObject("addressComponent");
			lat = result.getJSONObject("result").getJSONObject("location").getDouble("lat");
			lng = result.getJSONObject("result").getJSONObject("location").getDouble("lng");
			address = jsonAddr.getString("district");
			address += jsonAddr.getString("street");
			address += jsonAddr.getString("street_number");
			description = result.getJSONObject("result").getString("sematic_description");
			business = result.getJSONObject("result").getString("business");
			city = result.getJSONObject("result").getJSONObject("addressComponent").getString("city");
			result.clear();
			result.put("lat", lat);
			result.put("lng", lng);
			result.put("city", city);
			result.put("address", address);
			result.put("business", business);
			result.put("description", description);
		}else{
			result.clear();
			result.put("status", Retcode.FAILED.code);
			result.put("message", Retcode.FAILED.msg);
		}
		return result; 
	}
	
	/**
	 * 根据上车地点，组合轨迹数据
	 * @param params
	 * @return
	 */
	public JSONObject getDefaultOrderTrace(Map<String, Object> params) {
		Date starttime = (Date) params.get("starttime");
		long loc_time = 0;
		String create_time = null;
		if(null != starttime) {
			loc_time = starttime.getTime()/1000;
			create_time = StringUtil.formatDate(starttime, "yyyy-MM-dd HH:mm:ss");
		}
		
		//开始坐标
		JSONObject startPoint = new JSONObject();
		startPoint.put("longitude", params.get("onaddrlng"));
		startPoint.put("latitude", params.get("onaddrlat"));
		startPoint.put("coord_type", 3);
		startPoint.put("loc_time", loc_time);
		
		//位置点
		JSONArray location = new JSONArray();
		location.add(params.get("onaddrlng"));
		location.add(params.get("onaddrlat"));
		
		//轨迹数据点
		JSONObject point = new JSONObject();
		point.put("loc_time", loc_time);
		point.put("location", location);
		point.put("create_time", create_time);
		point.put("direction", 0);
		point.put("height", 0);
		point.put("radius", 0);
		point.put("speed", 0);
		
		JSONArray points = new JSONArray();
		points.add(point);
		
		//轨迹数据
		JSONObject json = new JSONObject();
		json.put("status", 0);
		json.put("size", 1);
		json.put("entity_name", params.get("orderno"));
		json.put("distance", 0);
		json.put("toll_distance", 0);
		json.put("start_point", startPoint);
		json.put("points", points);
		json.put("isInterface", 1);
		return json;
	}

}
