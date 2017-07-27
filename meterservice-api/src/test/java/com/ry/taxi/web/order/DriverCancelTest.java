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

public class DriverCancelTest extends BaseWebTest{
	
	public String takeArgs() throws JSONException{
		JSONObject jsonparam = new JSONObject();
		jsonparam.put("OrderNum", "CGT1707251731");
		jsonparam.put("CancelTime", DateUtil.date2String(new Date()));
		jsonparam.put("CancelType", 1);
		
		return jsonparam.toString();
		
	}
	
	/**
	 * 取消订单
	 * @throws JSONException
	 */
	@Test
	public void testDriverCancel() throws JSONException{
		
		String argJson = takeArgs();
		String sign =  MD5.MD5Encode(DESUtils.encode(Key,  argJson, Key), "utf-8");
		String url ="/RyTaxi/Management/func?Cmd=DriverCancelOrder&Key=" + Key + "&UserId=CGI"+"&Args={json}"+"&Sign="+sign;
		
		HttpEntity<String> request = new HttpEntity<String>(argJson, null);
		ResponseEntity<String> responseEntity = testRest.postForEntity(url, request, String.class,argJson);		
		String data = responseEntity.getBody();
		System.out.println(data);
		
	}

}
