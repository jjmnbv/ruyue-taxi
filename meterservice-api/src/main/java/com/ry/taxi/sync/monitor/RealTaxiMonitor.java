/**
 * 
 */
package com.ry.taxi.sync.monitor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ry.taxi.base.exception.RyTaxiException;
import com.ry.taxi.sync.domain.GciSyncLog;
import com.ry.taxi.sync.mapper.GciVehicleMapper;
import com.ry.taxi.sync.query.RealTimeGps;
import com.xunxintech.ruyue.coach.io.date.DateUtil;
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
	
	@Value("${spring.thread.corePoolSize:1}")
	private  int corePoolSize;
	
	@Value("${spring.thread.maxPoolSize:1}")
	private  int maxPoolSize;
	
	@Value("${spring.thread.queueSize:5000}")
	private  int queueSize;
	
	@Autowired
	private GciVehicleMapper gciVehicleMapper;
	
	private static ThreadPoolExecutor gpsPool = null; 

	private static final int VERHICLE_COUNT = 200; //每次处理的车辆
	
	private static final String REP_STATUS = "status";
	
	private static final String REP_DATA = "data";
	
	private	static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	
	private static final int hour = 6;

	
	/*
	 * 获取GPS实时信息,15s更新一次
	 */
	@Scheduled(cron="0/15 0 0 * * ?")
	public void realGps(){
		initSet();
		GciSyncLog lastTrace = gciVehicleMapper.getLastTrace();
		String updatetime = "";
		//如果首次加载,则加载n个小时前的数据,保证全量更新
		if(lastTrace == null || StringUtils.isNotBlank(lastTrace.getSynctime())){
			Long time = System.currentTimeMillis()/1000 -  hour * 3600;
			updatetime = DateUtil.formatUnixTime(String.valueOf(time),YYYY_MM_DD_HH_MM_SS);
		}
		gpsPool.execute(new RealTimeRunnable("",updatetime));
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
	}
    

	/**
	 * 
	 * @Title:RealTaxiMonitor.java
	 * @Package com.ry.taxi.sync.monitor
	 * @Description 处理GPS同步线程
	 * @author zhangdd
	 * @date 2017年7月20日 上午9:59:36
	 * @version 
	 *
	 * @Copyrigth  版权所有 (C) 2017 广州讯心信息科技有限公司.
	 */
	private class RealTimeRunnable implements Runnable{
		
		private String platnos; //车辆列表
		
		private  String updateTime; //增量更新时间
		
		GciSyncLog synLog;//日志记录表
		
		public RealTimeRunnable(String platnos, String updateTime){
			this.platnos = platnos;
			this.updateTime = updateTime;
			synLog = new GciSyncLog();
		}

		@Override
		public void run() {	
			List<RealTimeGps> realtimeList = null;
			Long startTime = System.currentTimeMillis();
			try{	
				realtimeList = getRealGps();
				if (realtimeList != null && realtimeList.size() > 0){
					//批量插入GPS记录表
					int size = realtimeList.size();
					int time = size/VERHICLE_COUNT;
					for(int i = 0; i < time; i ++){
						gciVehicleMapper.insertBathGps(realtimeList.subList(i*VERHICLE_COUNT,(i+1)*VERHICLE_COUNT));
					}
					if (time * VERHICLE_COUNT < size){
						gciVehicleMapper.insertBathGps(realtimeList.subList(time *VERHICLE_COUNT,size));
					}
					synLog.setRefreshcount(size);
					synLog.setSynctime(realtimeList.get(size-1).getServiceTime());
				}
			}catch(Exception e){
				synLog.setStatus(1);
				synLog.setErrormsg(e.getMessage());
			}
			finally{
				Long endTime = System.currentTimeMillis();
				synLog.setRequesttime(DateUtil.formatUnixTime(String.valueOf(startTime/1000),YYYY_MM_DD_HH_MM_SS));
				synLog.setStatus(0);
				synLog.setProcesstime(endTime - startTime);
				gciVehicleMapper.insertTraceLog(synLog);
				
				if (realtimeList != null && realtimeList.size() > 0)
					realtimeList.clear();
			}
		}
		
		/*
		 * 获取实时GPS数据
		 */
		public List<RealTimeGps> getRealGps(){	
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("PlateNoList", platnos);
			paramMap.put("UpdatTime", updateTime);
			
			String response = HttpClientUtil.sendHttpPost(gpsUrl, paramMap, ContentType.APPLICATION_JSON);
			JSONObject jsonObject = JSONObject.fromObject(response);
			int status =  jsonObject.getInt(REP_STATUS);
			if (status < 200  || status > 299){
				throw new RyTaxiException(status,"请求GPS数据错误:" + response);
			}
			JSONArray datas = jsonObject.getJSONArray(REP_DATA);
			List<RealTimeGps> realtimeList = JSONArray.toList(datas, RealTimeGps.class, new JsonConfig());
			return realtimeList;
		}
	}

}
