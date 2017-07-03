package com.szyciov.touch.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.szyciov.touch.enums.ResultStateEnum;

import net.sf.json.JSONObject;

import static com.szyciov.touch.enums.ResultStateEnum.OK;

/**
 * 处理结果工具类
 * @author zhu
 *
 */
public class ResultUtil {

	/**
	 * 初始化返回结果的公用信息
	 * 初始设置result状态都为ok
	 * @param result
	 */
	private static void initResultInfo(Map<String,Object> result){
		if(result!=null){
			result.put("result", OK.state);
		}
	}
	
	/**
	 * 获取一个返回结果的数据对象
	 * @param resultenum
	 * @return
	 */
	public static Map<String,Object> getResultMapInfo(ResultStateEnum resultenum){
		Map<String,Object> result = new HashMap<String,Object>();
		initResultInfo(result);
		result.put("result", resultenum.state);
		result.put("data", null);
		if(!OK.state.equals(resultenum.state)){
			result.put("errmsg", resultenum.message);
		}
		return result;
	}
	
	/**
	 * map转json
	 * @param result
	 * @return
	 */
	public static JSONObject convertMap2Json(Map<String,Object> result){
		JSONObject json = new JSONObject();
		if(result!=null&&result.size()>0){
			Iterator<String> keyit = result.keySet().iterator();
			while(keyit.hasNext()){
				String key = keyit.next();
				json.put(key, result.get(key));
			}
		}
		return json;
	}
}
