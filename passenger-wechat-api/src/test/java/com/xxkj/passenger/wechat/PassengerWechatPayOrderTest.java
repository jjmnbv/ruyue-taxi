package com.xxkj.passenger.wechat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.szyciov.entity.Retcode;
import com.xxkj.passenger.wechat.config.SystemConfig2;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
public class PassengerWechatPayOrderTest {
	
	private static Logger logger = LoggerFactory.getLogger(PassengerWechatPayOrderTest.class);
	
	@Autowired
	private TestRestTemplate testRestTemplate;
    
    @Test
    @Transactional
    public void payOrder() {
    	logger.info("start test payOrder");
    	
    	Map<String, Object> requestMap = new HashMap<String, Object>();
    	requestMap.put("usertoken", "Nzc0NjNiYjMzYmU5ZmUzZjg3NzMzYWM0ZWU0MDZkMGUyMDE3MDgwMTExNDU1MTI5NzAxMzAyNjM1MzIwNg==");
    	requestMap.put("orderno", "CGI1708011100001");
    	requestMap.put("paytype", "4");
    	requestMap.put("ordertype", "0");
    	requestMap.put("usetype", "2");
    	requestMap.put("version", "v4.0.1");
    	
    	ResponseEntity<Map> responseEntity = testRestTemplate.postForEntity("/PassengerWechat/PayOder", requestMap, Map.class);
    	Map<String,Object> res = responseEntity.getBody();
    	
    	res.entrySet().stream().forEach(r -> logger.info(r.getKey() + "=" + r.getValue()));
    	assertEquals(res.get("status"), Retcode.OK.code);
    	assertTrue((res.get("payorderinfo") != null && res.get("payorderinfo").toString().startsWith("http")));
    	
    	logger.info("end test payOrder");
    }
    
}
