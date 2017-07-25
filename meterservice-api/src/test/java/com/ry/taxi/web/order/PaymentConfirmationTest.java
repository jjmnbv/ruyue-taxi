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

public class PaymentConfirmationTest extends BaseWebTest {
	
	 private String  Key = "12345678";
	 
	 public String takeArgs() throws JSONException{
		 
		 JSONObject jsonparame = new JSONObject();
		 jsonparame.put("OrderNum", "CGT1707251423");
		 jsonparame.put("TransId", 0);
		 jsonparame.put("TotalPayable", 4);
		 jsonparame.put("RewardFeePayable", 0);
		 jsonparame.put("KmFeePayable", 4);
		 jsonparame.put("TotalFee", 4);
		 jsonparame.put("RewardFee", 0);
		 jsonparame.put("KmFee", 4);
		 jsonparame.put("TransTime",DateUtil.date2String(new Date()));
		 jsonparame.put("TransType", 4);
		 jsonparame.put("TransMode", 0);
		 
		return jsonparame.toString();
		 
	 }
	 
	 
	 /**
	  * 支付确认
	 * @throws JSONException 
	  */
		@Test
		public void testEndCalculation() throws JSONException{
			String argJson = takeArgs();
			String sign =  MD5.MD5Encode(DESUtils.encode(Key,  argJson, Key), "utf-8");
			String url ="/RyTaxi/Management/func?Cmd=PaymentConfirmation&Key=" + Key + "&UserId=CGI"+"&Args={json}"+"&Sign="+sign;
			
			HttpEntity<String> request = new HttpEntity<String>(argJson, null);
			ResponseEntity<String> responseEntity = testRest.postForEntity(url, request, String.class,argJson);		
			String data = responseEntity.getBody();
			System.out.println(data);
		}

}
