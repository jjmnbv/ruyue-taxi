package com.szyciov.supervision.config;

import com.xunxintech.ruyue.coach.io.network.httpclient.HttpClientUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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

    public @Value("${service.company.open-token}") String openToken;
    public @Value("${service.map-encrypt}") String mapEncrypt;

    private@Value("${spring.http.timeout.connect:60000}") int connectTimeout;

    private@Value("${spring.http.timeout.socket:60000}") int socketTimeout;

    private@Value("${spring.http.timeout.write:60000}") int writeTimeout;

    private@Value("${spring.http.dispatcher.maxRequests:500}") int maxRequests;

    private@Value("${spring.http.dispatcher.maxRequestsPerHost:50}") int maxRequestsPerHost;

    private @Value("${spring.http.compression:false}")boolean compression;
    
    public static String MAPTYPE; //地图类型 1：GCJ-02 测绘局标准 2：WGS84 GPS标准 3：BD-09 百度标准 4：CGCS2000 北斗标准 0：其他 目前使用百度


    @Override
    public void afterPropertiesSet() throws Exception {
        p_serviceUrl = serverUrl;
        p_companyId = companyId;
        p_key = key;
        p_openToken=openToken;
        p_mapEncrypt=mapEncrypt;
        HttpClientUtil.initHttpClient(socketTimeout,connectTimeout,writeTimeout,null,maxRequests, maxRequestsPerHost, compression);
    }


    private static String p_serviceUrl;
    private static String p_companyId;
    private static String p_key;
    private static String p_openToken;
    private static String p_mapEncrypt;
    public static String getServiceUrl(){
        return p_serviceUrl;
    }
    public static String getCompanyId(){
        return p_companyId;
    }
    public static String getKey(){
        return p_key;
    }

    public static String getOpenToken(){
        return p_openToken;
    }

    public static String getMapEncrypt(){
        return p_mapEncrypt;
    }
    
    @Value("${maptype:3}")
    public void setMaptype(String type){
    	MAPTYPE = type;
    }
}
