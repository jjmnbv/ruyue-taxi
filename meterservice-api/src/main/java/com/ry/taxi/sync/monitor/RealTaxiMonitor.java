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
 * @date 2017��7��10�� ����4:17:37
 * @version 
 *
 * @Copyrigth  ��Ȩ���� (C) 2017 ����Ѷ����Ϣ�Ƽ����޹�˾.
 */
@Component
public class RealTaxiMonitor {
	
	/*
	 * ��ȡʵʱGPS���� 15s����һ��
	 */
	@Value("${GCI.service.http}")
	private static String GPSHTTP;
	
	@Scheduled(cron="0/15 0 0 * * ?")
	public void getRealGps(){
		
	}

}
