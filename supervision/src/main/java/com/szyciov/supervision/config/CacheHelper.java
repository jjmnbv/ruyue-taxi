package com.szyciov.supervision.config;

import com.xunxintech.ruyue.coach.io.network.httpclient.HttpClientUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sun.net.www.http.HttpClient;

/**
 * Created by admin on 2017/7/6.
 */
@Component
public class CacheHelper implements InitializingBean{

    private @Value("${service.url}") String serverUrl;
    /**
     *公司标识
     */
    private@Value("${service.company.code}") String companyId;

    private@Value("${service.company.key}") String key;

    private@Value("${spring.http.timeout.connect:60000}") int connectTimeout;

    private@Value("${spring.http.timeout.socket:60000}") int socketTimeout;

    private@Value("${spring.http.timeout.write:60000}") int writeTimeout;

    private@Value("${spring.http.dispatcher.maxRequests:500}") int maxRequests;

    private@Value("${spring.http.dispatcher.maxRequestsPerHost:50}") int maxRequestsPerHost;


    @Override
    public void afterPropertiesSet() throws Exception {
        p_serviceUrl = serverUrl;
        p_companyId = companyId;
        p_key = key;
        HttpClientUtil.initHttpClient(socketTimeout,connectTimeout,writeTimeout,null,maxRequests, maxRequestsPerHost, false);
    }


    private static String p_serviceUrl;
    private static String p_companyId;
    private static String p_key;
    public static String getServiceUrl(){
        return p_serviceUrl;
    }
    public static String getCompanyId(){
        return p_companyId;
    }
    public static String getKey(){
        return p_key;
    }

}
