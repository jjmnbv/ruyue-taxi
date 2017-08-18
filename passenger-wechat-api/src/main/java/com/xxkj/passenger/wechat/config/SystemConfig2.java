package com.xxkj.passenger.wechat.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:web.properties")
public class SystemConfig2 implements ApplicationContextAware {
	
	private static ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext _applicationContext) throws BeansException {
		applicationContext = _applicationContext;
	}
	
	public static String getSystemProperty(String key) {
		return applicationContext.getEnvironment().getProperty(key);
	}

	public static String getSystemProperty(String key, String defaultValue) {
		return applicationContext.getEnvironment().getProperty(key, defaultValue);
	}

}
