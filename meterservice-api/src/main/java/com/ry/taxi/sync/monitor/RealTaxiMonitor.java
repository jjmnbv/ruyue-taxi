/**
 * 
 */
package com.ry.taxi.sync.monitor;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.xunxintech.ruyue.coach.io.network.httpclient.HttpClientUtil;

import net.sf.json.JSONObject;

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
	

	@Value("${GCI.service.http}")
	private String GPSHTTP;
	
	@Value("${spring.thread.corePoolSize:15}")
	private  int corePoolSize;
	
	@Value("${spring.thread.maxPoolSize:100}")
	private  int maxPoolSize;
	@Value("${spring.thread.queueSize:5000}")
	private  int queueSize;
	
	private static ThreadPoolExecutor gpsPool = null; 
	

	
	/*
	 * 获取GPS实时信息,15s更新一次
	 */
	@Scheduled(cron="0/15 0 0 * * ?")
	public void getRealGps(){
		initGpsPool();
		gpsPool.execute(new Runnable(){
			@Override
			public void run() {
		
				//处理过程
				
			}
		});
	}
	
    /*
     * 初始化GPS设置
     */
	private void initGpsPool(){
		if(gpsPool == null){
			synchronized (this) {
				if(gpsPool== null){
					gpsPool = new ThreadPoolExecutor(corePoolSize, maxPoolSize, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(queueSize));
				}
			}
		}
	}

}
