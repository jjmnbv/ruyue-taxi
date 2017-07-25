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
public class OrderTakingTest extends BaseWebTest {
	

	private String  Key = "12345678";
	
	public String takeArgs() throws JSONException{
		 JSONObject argJson = new JSONObject();
		 argJson.put("CertNum", "STR334624");
		 argJson.put("Mobile", "18620291125");
		 argJson.put("PlateNum", "京A23455");
		 argJson.put("OrderNum", "CGT1707251731");
		 argJson.put("ResponseType", 1);
		 return argJson.toString();
	}
    
	
    /*
     * 司机应邀通知
     */
	@Test
	public void OrderTakingTest() throws JSONException{
		String argJson = takeArgs();
		String sign =  MD5.MD5Encode(DESUtils.encode(Key,  argJson, Key), "utf-8");
		String url ="/RyTaxi/Management/func?Cmd=DriverTakeOrder&Key=" + Key + "&UserId=CGI"+"&Args={json}"+"&Sign="+sign;
		
		HttpEntity<String> request = new HttpEntity<String>(argJson, null);
		ResponseEntity<String> responseEntity = testRest.postForEntity(url, request, String.class,argJson);		
		String data = responseEntity.getBody();
		System.out.println(data);
        
	}


}
