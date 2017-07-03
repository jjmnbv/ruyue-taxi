package com.szyciov.carservice.util;

import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;

import com.szyciov.message.BaseMessage;

/**
 * 队列添加获取接口
 * @author zhu
 *
 */
public class MessageConnection {
	private static final Logger logger = Logger.getLogger(MessageConnection.class);
	
	private static ConcurrentLinkedQueue<BaseMessage> queue;
	
	//5个扫描线程
	private static int threadcount = 10;
	
	private MessageConnection(){}
	
	static{
		queue = new ConcurrentLinkedQueue<BaseMessage>();
		//初始化 threadcount个线程扫描队列消费消息
		Poll poll = new Poll(3);
		for(int i=0;i<threadcount;i++){
			new Thread(poll).start();
		}
		logger.info(threadcount+"个队列初始化完成");
	}
	
	public static void add(BaseMessage message){
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
            		BaseMessage message = queue.poll();
            		if(message!=null){
            			//没有到指定时间点，将消息放回到线程中
            			long date = new Date().getTime();
                		if(null != message.getRunTime() && message.getRunTime() > date) {
                			queue.offer(message);
                			continue;
                		}
	            		new Thread(new  Runnable(){
	        				public void run() {
	    						message.send();
	        					logger.info("消息已发送+++++++++++++++++++++++++++++++++++++");
	        				}
	        			}).start();
            		}
            	}else{
//            		logger.info("队列现在是空的+++++++++++++++++++++++++++++++++++++");
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
