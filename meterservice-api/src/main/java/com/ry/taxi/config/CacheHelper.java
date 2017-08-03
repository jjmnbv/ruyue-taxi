/**
 * 
 */
package com.ry.taxi.config;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ry.taxi.order.domain.PubDriver;
import com.ry.taxi.order.mapper.DriverMapper;
import com.xunxintech.ruyue.coach.io.network.httpclient.HttpClientUtil;

/**
 * @Title:CacheHelper.java
 * @Package com.ry.taxi.config
 * @Description
 * @author zhangdd
 * @date 2017年7月10日 下午4:46:58
 * @version 
 *
 * @Copyrigth  版权所有 (C) 2017 广州讯心信息科技有限公司.
 */
@Component
public class CacheHelper implements InitializingBean {

    private@Value("${spring.http.timeout.connect:60000}") int connectTimeout;

    private@Value("${spring.http.timeout.socket:60000}") int socketTimeout;

    private@Value("${spring.http.timeout.write:60000}") int writeTimeout;

    private@Value("${spring.http.dispatcher.maxRequests:500}") int maxRequests;

    private@Value("${spring.http.dispatcher.maxRequestsPerHost:50}") int maxRequestsPerHost;
    
    private @Autowired DriverMapper driverMapper;
    
	public static Map<String, PubDriver> driverMap = new ConcurrentHashMap<String, PubDriver>();


	@Override
	public void afterPropertiesSet() throws Exception {
		HttpClientUtil.initHttpClient(socketTimeout,connectTimeout,writeTimeout,null,maxRequests, maxRequestsPerHost);
		
	}
	
	@Scheduled(cron="0 0/2 * * * ?")
	public void reloadConfig() throws JsonParseException, JsonMappingException, IOException{	
		driverMap = driverMapper.selectAllDriver().stream().collect(Collectors.toMap(PubDriver::getJobnum, Function.identity()));
	}

}
