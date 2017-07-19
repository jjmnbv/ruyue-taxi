/**
 * 
 */
package com.ry.taxi.sync.monitor;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ry.taxi.order.web.BaseOrderController;
import com.ry.taxi.sync.domain.GciVehicle;
import com.ry.taxi.sync.mapper.GciVehicleMapper;
import com.ry.taxi.sync.query.RealTimeGps;
import com.xunxintech.ruyue.coach.io.json.JSONUtil;
import com.xunxintech.ruyue.coach.io.network.httpclient.HttpClientUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

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
	
	private static Logger logger = LoggerFactory.getLogger(RealTaxiMonitor.class);
  
	@Value("${GCI.service.http}")
	private String gpsUrl;
	
	@Value("${spring.thread.corePoolSize:15}")
	private  int corePoolSize;
	
	@Value("${spring.thread.maxPoolSize:100}")
	private  int maxPoolSize;
	
	@Value("${spring.thread.queueSize:5000}")
	private  int queueSize;
	
	@Autowired
	private GciVehicleMapper gciVehicleMapper;
	
	private static ThreadPoolExecutor gpsPool = null; 
	
	private static boolean initStart = true;//首次启动
	
	private static final String REP_STATUS = "status";
	
	private static final String REP_DATA = "data";
	
	private static final String REP_TEXT = "statustext";
	
	private static List<String> gpsList ;

	private static final int verhicle_count = 200;

	
	/*
	 * 获取GPS实时信息,15s更新一次
	 */
	@Scheduled(cron="0/15 0 0 * * ?")
	public void realGps(){
		initSet();
		if((!initStart) && verhicleMap != null && verhicleMap.size() > 0){
			gpsPool.execute(new RealTimeRunnalbe(""));
		}
		else if (gpsList.size() > 0){
			int size = gpsList.size();
			int time = size/verhicle_count;
			for(int i = 0; i < time; i ++){
				String platenos = gpsList.subList(i*verhicle_count,(i+1)*verhicle_count).stream()
						.collect(Collectors.joining(","));
				gpsPool.execute(new RealTimeRunnalbe(platenos));
			}
			if (time * verhicle_count < size){
				String platenoleft = gpsList.subList(time *verhicle_count,size).stream()
						.collect(Collectors.joining(","));
				gpsPool.execute(new RealTimeRunnalbe(platenoleft));
			}
			changeInitStart();
		}
	}
	

	
    /*
     * 初始化GPS设置
     */
	private void initSet(){
		//线程池初始化
		if(gpsPool == null){
			synchronized (this) {
				if(gpsPool== null){
					gpsPool = new ThreadPoolExecutor(corePoolSize, maxPoolSize, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(queueSize));
				}
			}
		}
		//从数据库中获取所有的车辆
		if(gpsList == null){
			synchronized (this) {
				if(gpsList== null){
					synchronized (gpsList = Collections) {
						gciVehicleMapper.getAllVehicleList();
					}
					if (gpsList == null)
						synchronized (gpsList = Collections) {
							new ArrayList<String>();
						}
				}
			}
		}
	}
    
	/*
	 * 修改首次启动标志
	 */
	private void changeInitStart(){
		if (initStart){
			synchronized (this) {
				if (initStart){
					initStart = false;
				}
			}
		}
	}
	
	private class RealTimeRunnalbe implements Runnable{
		
		private String platnos;
		
		public RealTimeRunnalbe(String platnos){
			this.platnos = platnos;
		}

		@Override
		public void run() {
			 List<RealTimeGps> gpsList = getRealGps();
			 if (gpsList.size() > 0){

			 }
		}
		
		/*
		 * 获取实时GPS数据
		 */
		public List<RealTimeGps> getRealGps(){	
			String response = HttpClientUtil.sendHttpPost(gpsUrl, platnos, ContentType.APPLICATION_JSON);
			JSONObject jsonObject = JSONObject.fromObject(response);
			int status =  jsonObject.getInt(REP_STATUS);
			String statusText = jsonObject.getString(REP_TEXT);
			if (status < 200  || status > 299){
				logger.error("请求GPS数据错误:{}",response);
				return null;
			}
			JSONArray datas = jsonObject.getJSONArray(REP_DATA);
			List<RealTimeGps> gpsList = JSONArray.toList(datas, RealTimeGps.class, new JsonConfig());
			return gpsList;
		}
		
		
	}

}
