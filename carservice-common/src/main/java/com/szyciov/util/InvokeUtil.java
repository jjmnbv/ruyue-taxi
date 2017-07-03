package com.szyciov.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class InvokeUtil {
	public static boolean isTest = SystemConfig.getSystemProperty("runmode","dev").equals("product");
	public static boolean isTimestamp = SystemConfig.getSystemProperty("dateformat", "timestamp").equals("timestamp");
	
	/**
	 * 清空null值为""
	 * @param json
	 * @param transTime
	 * @param timeformat
	 */
	public static void removeNullObejct(Object json,boolean transTime,String timeformat){
		if(json instanceof JSONObject){
			JSONObject temp = (JSONObject) json;
			for(Object key :temp.keySet()){
				Object o = temp.get(key);
				if(o instanceof JSONArray){
					for(Object arrO : ((JSONArray)o)){
						removeNullObejct(arrO,transTime,timeformat);
					}
				}else if(o instanceof JSONObject){
					JSONObject tempO = (JSONObject)o;
					if(tempO.isNullObject()){
						//将null字段替换为空字符串
						temp.put(key,"");
					}else if (tempO.has("time") && transTime) {
						if(isTimestamp){
							temp.put(key,tempO.getLong("time"));
						}else{
							Date d = new Date(tempO.getLong("time"));
							String datetime = StringUtil.formatDate(d, timeformat);
							temp.put(key,datetime);
						}
					}else{
						removeNullObejct(tempO,transTime,timeformat);
					}
				}
			}
		}else if(json instanceof JSONArray){
			for(Object arrO : ((JSONArray)json)){
				removeNullObejct(arrO,transTime,timeformat);
			}
		}
	}
	
	/**
	 * 删除null值
	 * @param json
	 */
	public static void removeNullObejct(Object json){
		List<String> rmKeys = new ArrayList<>();
		if(json instanceof JSONObject){
			JSONObject temp = (JSONObject) json;
			for(Object key :temp.keySet()){
				Object o = temp.get(key);
				if(o instanceof JSONArray){
					for(Object arrO : ((JSONArray)o)){
						removeNullObejct(arrO);
					}
				}else if(o instanceof JSONObject){
					JSONObject tempO = (JSONObject)o;
					if(tempO.isNullObject()){
						//保存key,删除空字段
						rmKeys.add((String)key);
					}else if (((String)key).toLowerCase().contains("time")) {
						Date d = new Date(tempO.getLong("time"));
						String datetime = StringUtil.formatDate(d,"yyyy-MM-dd HH:mm");
						temp.put(key,datetime);
					}else{
						removeNullObejct(tempO);
					}
				}
			}
			//删除空字段
			for(String key : rmKeys){
				temp.remove(key);
			}
		}else if(json instanceof JSONArray){
			for(Object arrO : ((JSONArray)json)){
				removeNullObejct(arrO);
			}
		}
	}
}
