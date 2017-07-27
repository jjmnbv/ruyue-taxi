/**
 * 
 */
package com.ry.taxi.web.order;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import com.ry.taxi.web.BaseWebTest;
import com.xunxintech.ruyue.coach.encryption.algorithm.DESUtils;
import com.xunxintech.ruyue.coach.encryption.algorithm.MD5;

/**
 * @Title:StartCalculationTest.java
 * @Package com.ry.taxi.web.order
 * @Description
 * @author zhangdd
 * @date 2017年7月21日 下午5:20:15
 * @version 
 *
 * @Copyrigth  版权所有 (C) 2017 广州讯心信息科技有限公司.
 */
public class StartCalculationTest extends BaseWebTest {
	
	
	public String takeArgs() throws JSONException{
		 JSONObject argJson = new JSONObject();
		 argJson.put("CertNum", "STR334624");
		 argJson.put("Mobile", "18620291125");
		 argJson.put("PlateNum", "京A23455");
		 argJson.put("OrderNum", "CGT1707251423");
		 argJson.put("Longitude", 113.3490850000);
		 argJson.put("Latitude", 23.1764890000);
		 argJson.put("MapType",1);
		 argJson.put("PassengerGetOnTime", "2017-07-21 17:03:00");
		 return argJson.toString();
	}
    
	
    /*
     * 压表
     */
	@Test
	public void StartCalculationTest() throws JSONException{
		String argJson = takeArgs();
		String sign =  MD5.MD5Encode(DESUtils.encode(Key,  argJson, Key), "utf-8");
		String url ="/RyTaxi/Management/func?Cmd=StartCalculation&Key=" + Key + "&UserId=CGI"+"&Args={json}"+"&Sign="+sign;
		
		HttpEntity<String> request = new HttpEntity<String>(argJson, null);
		ResponseEntity<String> responseEntity = testRest.postForEntity(url, request, String.class,argJson);		
		String data = responseEntity.getBody();
		System.out.println(data);
        
	}


}