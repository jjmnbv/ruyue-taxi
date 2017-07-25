package com.ry.taxi.web.order;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import com.ry.taxi.web.BaseWebTest;
import com.xunxintech.ruyue.coach.encryption.algorithm.DESUtils;
import com.xunxintech.ruyue.coach.encryption.algorithm.MD5;
import com.xunxintech.ruyue.coach.io.date.DateUtil;

public class EndCalculationTest extends BaseWebTest{
	
	
	private String  Key = "12345678";
	
	public String takeArgs() throws JSONException{
		
		JSONObject jsonparame = new JSONObject();
		jsonparame.put("OrderNum", "CGT1707251423");
		jsonparame.put("CertNum", "STR334624");
		jsonparame.put("Mobile", "18620291125");
		jsonparame.put("PlateNum", "京A23455");
		jsonparame.put("InputLon", 113.3490850000);
		jsonparame.put("InputLat", 23.1764890000);
		jsonparame.put("MapType", 1);
		jsonparame.put("TransId", 123456);
		jsonparame.put("AmountPayable", 4);
		jsonparame.put("OrderEndTime", DateUtil.date2String(new Date()));
				
		return jsonparame.toString();
		
	}
	
	/**
	 * 起表通知
	 * @throws JSONException 
	 */
	@Test
	public void testEndCalculation() throws JSONException{
		String argJson = takeArgs();
		String sign =  MD5.MD5Encode(DESUtils.encode(Key,  argJson, Key), "utf-8");
		String url ="/RyTaxi/Management/func?Cmd=EndCalculation&Key=" + Key + "&UserId=CGI"+"&Args={json}"+"&Sign="+sign;
		
		HttpEntity<String> request = new HttpEntity<String>(argJson, null);
		ResponseEntity<String> responseEntity = testRest.postForEntity(url, request, String.class,argJson);		
		String data = responseEntity.getBody();
		System.out.println(data);
	}

}
