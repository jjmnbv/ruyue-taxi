package com.ry.taxi.web.order;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import com.ry.taxi.web.BaseWebTest;
import com.xunxintech.ruyue.coach.encryption.algorithm.DESUtils;
import com.xunxintech.ruyue.coach.encryption.algorithm.MD5;

public class DriverStartTest extends BaseWebTest{
	
	private String  Key = "12345678";
	
	
	public String takeArgs() throws JSONException{
		
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("CertNum", "100804");
		jsonParam.put("Mobile", "18620291125");
		jsonParam.put("PlateNum", "4YC46");
		jsonParam.put("OrderNum", "CGT1707211721");
		jsonParam.put("DepartureTime", "2017-07-21 17:30:00");
		jsonParam.put("Longitude", 113.3490850000);
		jsonParam.put("Latitude", 23.1764890000);
		jsonParam.put("MapType", 1);
		
		return jsonParam.toString();
		
	}
	
	
	/**
	 * 司机执行订单通知（预约单）
	 * @throws JSONException
	 */
	
	@Test
	public void testDriverStart() throws JSONException{
		
		String jsonparam = takeArgs();
		
		String sign =  MD5.MD5Encode(DESUtils.encode(Key,  jsonparam, Key), "utf-8");
		
		String url ="/RyTaxi/Management/func?Cmd=DriverCancelOrder&Key=" + Key + "&UserId=CGI"+"&Args={json}"+"&Sign="+sign;
		
		HttpEntity<String> request = new HttpEntity<String>(jsonparam, null);
		
		ResponseEntity<String> responseEntity = testRest.postForEntity(url, request, String.class,jsonparam);		
		
		String data = responseEntity.getBody();
		
		System.out.println(data);
			
	}

}
