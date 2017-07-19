package com.ry.taxi.Util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestBody;

import com.szyciov.entity.Retcode;
import com.szyciov.param.BaiduApiQueryParam;
import com.szyciov.util.InvokeUtil;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper;

import net.sf.json.JSONObject;

public class AddressUitl {
	
	
	public static final TemplateHelper templateHelper = new TemplateHelper();
	
	public static final String carserviceApiUrl = SystemConfig.getSystemProperty("carserviceApiUrl");
	
		
	@Value(value="${yingyan_ak}")
	private static  String parame;
	
	
	public static JSONObject getAddress(@RequestBody BaiduApiQueryParam param){
		JSONObject result =getAddressTwo(param);
		long starttime=System.currentTimeMillis();
		return checkResult(result,starttime);
	}
	
	

	private static JSONObject getAddressTwo(BaiduApiQueryParam param){
		String url = "http://api.map.baidu.com/geocoder/v2/?location={lat},{lng}&output={output}&pois=1&ak={ak}";
		Map<String, Object> uriParam = new HashMap<>();
		uriParam.put("ak", SystemConfig.getSystemProperty(parame));
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
	
	
	private static JSONObject checkResult(JSONObject result,long starttime){
		if (result == null) {
			result = new JSONObject();
			result.put("status", Retcode.FAILED.code);
			result.put("message", Retcode.FAILED.msg);
			result.put("servertime", new Date());
		}else if(result.isEmpty()){
			result.put("status", Retcode.OK.code);
			result.put("message", Retcode.OK.msg);
			result.put("servertime", new Date());
		}else{
			if (!result.has("status")) {
				result.put("status", Retcode.OK.code);
			}
			if(!result.has("message")){
				result.put("message", Retcode.OK.msg);
			}
			if(!result.has("servertime")){
				 result.put("servertime", new Date());
			}
		}
		long endtime = System.currentTimeMillis();
		double accesstime = ((double)(endtime - (starttime == 0 ? endtime : starttime)))/1000;
		result.put("accesstime", accesstime+"s");
		InvokeUtil.removeNullObejct(result,true,"yyyy-MM-dd HH:mm:ss");
		return result;
	}

}
