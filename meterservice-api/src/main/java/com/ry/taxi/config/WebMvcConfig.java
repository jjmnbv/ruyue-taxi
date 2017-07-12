/**
 * 
 */
package com.ry.taxi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Title:WebMvcConfig.java
 * @Package com.ry.taxi.config
 * @Description
 * @author zhangdd
 * @date 2017年7月12日 上午11:06:47
 * @version 
 *
 * @Copyrigth  版权所有 (C) 2017 广州讯心信息科技有限公司.
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter  {
	
	@Autowired
	private ConfigFilter configFilter;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) { 
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
   
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	registry.addInterceptor(configFilter).addPathPatterns("/RyTaxi/Management/*");
        super.addInterceptors(registry);
    }

}
