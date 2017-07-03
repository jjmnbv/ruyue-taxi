package com.szyciov.carservice.controller;


import javax.annotation.Resource;
import com.szyciov.carservice.service.DriverMessageService;
import com.szyciov.driver.entity.PubDriverNews;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.util.ApiExceptionHandle;

import net.sf.json.JSONObject;



/**
  * @ClassName DriverMessageController
  * @author Efy Shu
  * @Description 出租车消息功能Controller
  * @date 2017年4月11日 20:50:11
  */ 
@Controller("DriverMessageController")
public class DriverMessageController extends ApiExceptionHandle{

	/**
	  *依赖
	  */
	private DriverMessageService drivermessageservice;

	/**
	  *依赖注入
	  */
	@Resource(name="DriverMessageService")
	public void setDriverMessageService(DriverMessageService drivermessageservice){
		this.drivermessageservice=drivermessageservice;
	}
	
	/**
	 * 添加司机消息
	 * @param news
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="api/DriverMessage/AddDriverMessage")
	public JSONObject addDriverMessage(@RequestBody PubDriverNews news){
		starttime.set(System.currentTimeMillis());
		JSONObject result = drivermessageservice.addDriverMessage(news);
		return checkResult(result);
	}
}
