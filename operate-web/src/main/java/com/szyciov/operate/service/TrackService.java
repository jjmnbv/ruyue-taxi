package com.szyciov.operate.service;

import com.szyciov.entity.Excel;
import com.szyciov.op.param.QueryTrackDataParam;
import com.szyciov.op.param.QueryTrackRecordParam;
import com.szyciov.operate.util.ExcelUtil;
import com.szyciov.util.ReflectClassField;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper;
import net.sf.json.JSONArray;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class TrackService {
	
	private TemplateHelper templateHelper = new TemplateHelper();
	private String vmsApiUrl = SystemConfig.getSystemProperty("vmsApiUrl");
	
	/**
	 * 导出Excel
	 * @param param
	 * @param token
	 * @return
	 * @throws IntrospectionException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	public Excel exportExcel(QueryTrackDataParam queryParam,String token) throws IntrospectionException, InvocationTargetException, IllegalAccessException {

		//显示的表头及对应的实体类属性名
		List<String> cloumnList = new ArrayList<>(11);
		cloumnList.add("plate");
//		cloumnList.add("eqpId");
		cloumnList.add("imei");
		cloumnList.add("totalMileage");
		cloumnList.add("totalFuel");
		cloumnList.add("numberOfDays");
		cloumnList.add("totalTrackTime");
		cloumnList.add("totalIdleTime");
		cloumnList.add("finalTrackTime");
		List<String> titleList = new ArrayList<>(11);
		titleList.add("车牌");
//		titleList.add("设备ID");
		titleList.add("设备IMEI");
		titleList.add("总里程 (km)");
		titleList.add("总耗油量(L)");
		titleList.add("总行程数 (次)");
		titleList.add("总行程时长");
		titleList.add("总怠速时长");
		titleList.add("最后行驶时间");

		Map<String, Object> param =new HashMap<>();
		Map<String, Object> map = templateHelper.dealRequestWithFullUrl(vmsApiUrl + "/Monitor/QueryTrackData?"
				+ ReflectClassField.getMoreFieldsValue(queryParam), HttpMethod.GET,queryParam, Map.class,param);
		//遍历要显示的列
		JSONArray jsonArray =   JSONArray.fromObject(map.get("vehcTrack"));
		
		return  ExcelUtil.deal(cloumnList,titleList,jsonArray);

	}
	
	
	/**
	 * 导出Excel
	 * @param param
	 * @param token
	 * @return
	 * @throws IntrospectionException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	public Excel exportExcelTrackDataRecord(QueryTrackRecordParam queryParam,String token) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
		queryParam.setiDisplayLength(Integer.MAX_VALUE);
		//显示的表头及对应的实体类属性名
		List<String> cloumnList = new ArrayList<>();
		cloumnList.add("startTime");
		cloumnList.add("endTime");
		cloumnList.add("runLength");
		cloumnList.add("mileage");
		cloumnList.add("fuelConspt");
		cloumnList.add("idleTime");
		cloumnList.add("idleFuel");
		cloumnList.add("cumulativeOil");
		cloumnList.add("avgSpeed");
		List<String> titleList = new ArrayList<>();
		titleList.add("开始时间");
		titleList.add("结束时间");
		titleList.add("行程时长");
		titleList.add("里程(km)");
		titleList.add("耗油量 (L)");
		titleList.add("怠速时长");
		titleList.add("怠速耗油量(L)");
		titleList.add("油耗(L/100km)");
		titleList.add("平均车速(km/h)");
		List<String> colName = new ArrayList<String>();
		Map<String, List<Object>> colData = new HashMap<String, List<Object>>();
		Map<String, Object> param =new HashMap<>();
		Map<String, Object> map = templateHelper.dealRequestWithFullUrl(vmsApiUrl + "/Monitor/QueryTrackRecord?"
				+ ReflectClassField.getMoreFieldsValue(queryParam), HttpMethod.GET,queryParam, Map.class,param);
		//遍历要显示的列
		JSONArray jsonArray =   JSONArray.fromObject(map.get("oneTrafficData"));

		return ExcelUtil.deal(cloumnList,titleList,jsonArray);
	}



}
