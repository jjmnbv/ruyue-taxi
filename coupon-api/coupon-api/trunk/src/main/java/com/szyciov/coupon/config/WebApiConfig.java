package com.szyciov.coupon.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * webapi配置文件实体类
 * @author LC
 * @date 2017/8/16
 */
@Component
@PropertySource("classpath:test/webapi.properties")
@ConfigurationProperties
public class WebApiConfig {

    private static String carserviceApi;

    public static String getCarserviceApi() {
        return carserviceApi;
    }

    public static void setCarserviceApi(String carserviceApi) {
        WebApiConfig.carserviceApi = carserviceApi;
    }
}
 