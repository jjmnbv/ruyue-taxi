/**
 * 
 */
package com.ry.taxi.sync.monitor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * @Title:RealTaxiMonitor.java
 * @Package com.ry.taxi.sync.monitor
 * @Description
 * @author zhangdd
 * @date 2017年7月10日 下午4:17:37
 * @version 
 *
 * @Copyrigth  版权所有 (C) 2017 广州讯心信息科技有限公司.
 */
@Component
public class RealTaxiMonitor {
	
	/*
	 * 获取实时GPS数据 15s更新一次
	 */
	@Value("${GCI.service.http}")
	private static String GPSHTTP;
	
	@Scheduled(cron="0/15 0 0 * * ?")
	public void getRealGps(){
		
	}

}
