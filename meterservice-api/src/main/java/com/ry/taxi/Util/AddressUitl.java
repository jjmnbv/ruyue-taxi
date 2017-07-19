package com.ry.taxi.Util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestBody;

import com.ry.taxi.base.constant.PropertyConstant;
import com.szyciov.entity.Retcode;
import com.szyciov.util.InvokeUtil;
import com.xunxintech.ruyue.coach.io.network.httpclient.HttpClientUtil;

import net.sf.json.JSONObject;

public class AddressUitl {
	
	
	public static JSONObject getAddress(double lat, double lng){
		Long starttime = System.currentTimeMillis();
		JSONObject result =getAddressTwo( lat,  lng);
		return checkResult(result,starttime);
	}

	
	
	private static JSONObject getAddressTwo(double lat, double lng){
		Map<String, String> uriParam = new HashMap<>();
		uriParam.put("ak", PropertyConstant.BAIDU_AK);
		uriParam.put("output", "json");
		uriParam.put("location",String.valueOf(lat)+"," +String.valueOf(lng));
		uriParam.put("pois","1");
		String str = HttpClientUtil.sendHttpPost(PropertyConstant.BAIDU_URL, uriParam, ContentType.APPLICATION_JSON);
		JSONObject result = JSONObject.fromObject(str);
		if(Retcode.OK.code == result.getInt("status")){
			double bdLat,bdLng;
			String address,description,business,city;
			JSONObject jsonAddr = result.getJSONObject("result").getJSONObject("addressComponent");
			bdLat = result.getJSONObject("result").getJSONObject("location").getDouble("lat");
			bdLng = result.getJSONObject("result").getJSONObject("location").getDouble("lng");
			address = jsonAddr.getString("district");
			address += jsonAddr.getString("street");
			address += jsonAddr.getString("street_number");
			description = result.getJSONObject("result").getString("sematic_description");
			business = result.getJSONObject("result").getString("business");
			city = result.getJSONObject("result").getJSONObject("addressComponent").getString("city");
			result.clear();
			result.put("lat", String.valueOf(bdLat));
			result.put("lng", String.valueOf(bdLng));
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
