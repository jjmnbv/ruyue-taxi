package com.ry.taxi.web;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.xunxintech.ruyue.coach.encryption.algorithm.DESUtils;
import com.xunxintech.ruyue.coach.encryption.algorithm.MD5;



/**
 * 
  * @Title: BaseWebTest.java 
  * @Package com.xunxintech.ruyue.coach.web 
  * @Description  TODO
  * @author  XZF
  * @date 2017年1月3日 下午3:56:55 
  * @version   
  *
  * @Copyrigth  版权所有 (C) 2016 广州讯心信息科技有限公司.
  *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
public class BaseWebTest {
	
	@Autowired
	protected TestRestTemplate testRest;

	private String  Key = "12345678";
	
	public String takeArgs() throws JSONException{
		 JSONObject argJson = new JSONObject();
		 argJson.put("CertNum", "123456");
		 argJson.put("Mobile", "13607428657");
		 argJson.put("PlateNum", "A30523");
		 argJson.put("OrderNum", "2017070630000");
		 argJson.put("ResponseType", 1);
		 return argJson.toString();
	}

    /*
     * 司机应邀通知
     */
	@Test
	public void TakeWebTest() throws JSONException{
		String  argJson = takeArgs();
		String sign =  MD5.MD5Encode(DESUtils.encode(Key,  argJson, Key), "utf-8");
		String url ="/RyTaxi/Management/func?Cmd=DriverTakeOrder&Key=" + Key + "&UserId=CGI"+"&Args={json}"+"&Sign="+sign;
		
		HttpEntity<String> request = new HttpEntity<String>(argJson, null);
		ResponseEntity<String> responseEntity = this.testRest.postForEntity(url, request, String.class,argJson);		
		String data = responseEntity.getBody();
        
	}

}
