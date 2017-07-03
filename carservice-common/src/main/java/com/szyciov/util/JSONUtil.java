package com.szyciov.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author 袁金林
 *
 */
public class JSONUtil {
	public static List<Map<String, Object>> parseJSON2Map(JSONArray jsonArray){ 
		try{
	        Map<String, Object> map = null;
	        List<Map<String, Object>> ltMap = new ArrayList<Map<String,Object>>(); 
	        //最外层解析 
	        //JSONObject json = JSONObject.fromObject(jsonStr); 
	        //JSONArray jsonArray = JSONArray.fromObject(jsonStr);
	        for ( int   i=0;i<jsonArray.size();i++){   //遍历json数组
				JSONObject json = JSONObject.fromObject(jsonArray.get(i));
				map = new HashMap<String, Object>();
		        for(Object k : json.keySet()){ 
		            Object v = json.get(k);  
		            
		            map.put(k.toString(), v); 
		            //如果内层还是数组的话，继续解析 
//		            if(v instanceof JSONArray){ 
//		                List<Map<String, Object>> list = new ArrayList<Map<String,Object>>(); 
//		                Iterator<JSONObject> it = ((JSONArray)v).iterator(); 
//		                while(it.hasNext()){ 
//		                    JSONObject json2 = it.next(); 
//		                    list.add(parseJSON2Map(json2.toString())); 
//		                }
//		                map.put(k.toString(), list); 
//		            } else { 
//		                map.put(k.toString(), v); 
//		            } 
		        }
		        ltMap.add(map);
	        } 
	        return ltMap;
		}catch (Exception e) {
			return null;
		}
    }
}
