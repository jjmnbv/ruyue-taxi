/**
 * 
 */
package com.ry.taxi.sync.monitor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ry.taxi.config.CacheHelper;
import com.ry.taxi.order.domain.PubDriver;
import com.ry.taxi.order.mapper.DriverMapper;
import com.ry.taxi.sync.query.RealTimeGps;
import com.szyciov.util.GUIDGenerator;

/**
 * @Title:AddDriverQueue.java
 * @Package com.ry.taxi.sync.monitor
 * @Description
 * @author zhangdd
 * @date 2017年8月3日 下午4:47:47
 * @version 
 *
 * @Copyrigth  版权所有 (C) 2017 广州讯心信息科技有限公司.
 */
@Component
public class AddDriverQueue {
	
	private static Logger logger =  LoggerFactory.getLogger(AddDriverQueue.class);
	
	private static ConcurrentLinkedQueue<List<RealTimeGps>> queue;
	
	
	private static DriverMapper driverMapper;
	
	//5个扫描线程
    private static int threadcount = 5;
  	
  	private AddDriverQueue(){}
  	
  	
  	static{
  		queue = new ConcurrentLinkedQueue<List<RealTimeGps>>();
  		//初始化 threadcount个线程扫描队列消费消息
  		Poll poll = new Poll(3);
  		for(int i=0;i<threadcount;i++){
  			new Thread(poll).start();
  		}
  		logger.info(threadcount+"个队列初始化完成");
  	}
  	
  	
  	/**
	 * 注入
	 */
	@Autowired(required = true)
	public  void setDriverMapper(DriverMapper driverMapper) {
		AddDriverQueue.driverMapper = driverMapper;
	}

	
	public static void add(List<RealTimeGps> message){
  		//根据消息类型区分,是否即时发送
  		//message.send();
  		queue.offer(message);
  		logger.info("+++++++++++++++++++++++++++++++++++消息已加入队列+++++++++++++++++++++++++++++++++++");
  	}
  	
  	/**
  	 * 对象消息消费线程
  	 * @author zhu
  	 *
  	 */
  	static class Poll implements Runnable{
  		
  	//队列循环的间隔
  	private int intervalTime;
  		
  	private Poll(int intervalTime){
  			this.intervalTime = intervalTime;
  	}
  		
    public void run() {
      	//线程一直存在扫描队列
      while(true){
      	if(!queue.isEmpty()){
      		List<RealTimeGps> message = queue.poll();
      		if(message!=null){
      			ArrayList<PubDriver> driverList = new ArrayList<PubDriver>();
      			int count = message.size();
      			for (int i = 0; i< count; i++){
      				RealTimeGps realtime= message.get(count);
      				if(CacheHelper.driverMap.get(realtime.getDriverno())==null){
      					PubDriver driver = new PubDriver();
      					driver.setId(GUIDGenerator.newGUID());
      					driver.setJobnum(realtime.getDriverno());
      					driver.setName(realtime.getDrivername());
      					driver.setWorkstatus("1");
      					driver.setVehicletype("1");
      					driver.setBelongleasecompany(realtime.getCompany());
      					driverList.add(driver);
      				}
      			}
      			if (driverList.size() > 0){
      				driverMapper.insertBatch(driverList);
      			}
      		}
      	}else{
      		try {
				Thread.sleep(intervalTime*1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
      	}
      }
    }
    }
  }