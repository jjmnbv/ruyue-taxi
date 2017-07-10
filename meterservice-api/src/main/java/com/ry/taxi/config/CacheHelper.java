/**
 * 
 */
package com.ry.taxi.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

import com.xunxintech.ruyue.coach.io.network.httpclient.HttpClientUtil;

/**
 * @Title:CacheHelper.java
 * @Package com.ry.taxi.config
 * @Description
 * @author zhangdd
 * @date 2017��7��10�� ����4:46:58
 * @version 
 *
 * @Copyrigth  ��Ȩ���� (C) 2017 ����Ѷ����Ϣ�Ƽ����޹�˾.
 */
public class CacheHelper implements InitializingBean {

    private@Value("${spring.http.timeout.connect:60000}") int connectTimeout;

    private@Value("${spring.http.timeout.socket:60000}") int socketTimeout;

    private@Value("${spring.http.timeout.write:60000}") int writeTimeout;

    private@Value("${spring.http.dispatcher.maxRequests:500}") int maxRequests;

    private@Value("${spring.http.dispatcher.maxRequestsPerHost:50}") int maxRequestsPerHost;


	@Override
	public void afterPropertiesSet() throws Exception {
		 HttpClientUtil.initHttpClient(socketTimeout,connectTimeout,writeTimeout,null,maxRequests, maxRequestsPerHost);
	}
	

}
