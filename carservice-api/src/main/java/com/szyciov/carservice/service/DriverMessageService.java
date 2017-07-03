package com.szyciov.carservice.service;


import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.szyciov.carservice.dao.DriverMessageDao;
import com.szyciov.driver.entity.PubDriverNews;

import net.sf.json.JSONObject;



/**
  * @ClassName DriverMessageService
  * @author Efy Shu
  * @Description 出租车消息功能Service
  * @date 2017年4月11日 20:50:11
  */ 
@Service("DriverMessageService")
public class DriverMessageService{
	private Logger logger = LoggerFactory.getLogger(DriverMessageService.class);
	/**
	  *依赖
	  */
	private DriverMessageDao drivermessagedao;

	/**
	  *依赖注入
	  */
	@Resource(name="DriverMessageDao")
	public void setDriverMessageDao(DriverMessageDao drivermessagedao){
		this.drivermessagedao=drivermessagedao;
	}
	
	/**
	 * 添加司机消息
	 * @param news
	 * @return
	 */
	public JSONObject addDriverMessage(PubDriverNews news){
		logger.info("添加司机消息...");
		logger.info("使用参数:"+JSONObject.fromObject(news));
		drivermessagedao.addDriverMessage(news);
		JSONObject result = new JSONObject();
		logger.info("添加司机消息完成");
		return result;
	}
	
	/*********************************************************内部方法****************************************************************/
	
	
	
	/*********************************************************校验方法****************************************************************/
}
