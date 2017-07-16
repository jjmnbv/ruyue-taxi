/**
 * 
 */
package com.ry.taxi.sync.monitor;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
/*
 * 这个接口做的时候要主意了，传入的参数车牌列表，中间用半角逗号分隔，为空则表示查全部车牌, 也就士说程序启动的时候应该获取所有的列表数据
 * 然后系统需要建立相应的线程池，定时，例如15s, 获取一次基础数据
 * 我看到RealTaxiMonitor类里面有类似的设计，但是请考虑一下初始化，多线程，分批的设计
 * 还有关于数据存储，在做表设计的时候要考虑到查询的效率，应该在适当的列做索引
 * 如果目前因为不必在我们系统做GPS的轨迹查询，所以，GPS的数据可以先存7天，做成可配置的参数，这样就可以节省我们的存储了
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
