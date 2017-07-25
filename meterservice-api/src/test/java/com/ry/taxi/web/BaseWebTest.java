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

}
