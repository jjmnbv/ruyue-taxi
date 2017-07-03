package com.szyciov.carservice.controller;

import com.szyciov.carservice.service.DriverNewsService;
import com.szyciov.driver.entity.PubDriverNews;
import com.szyciov.entity.Retcode;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 司机消息接口服务
 * @author LC
 */
@RestController
@RequestMapping("api/DriverNews")
public class DriverNewsApiController {

	private Logger logger = LoggerFactory.getLogger(DriverNewsApiController.class);

	@Resource
	private DriverNewsService driverNewsService;


	@RequestMapping( value = "/addDriverNews",method = RequestMethod.POST,produces = "application/json")
	public JSONObject addUserNews(@RequestBody PubDriverNews driverNews) throws IOException {

		boolean isSuccess =  driverNewsService.saveDriverNews(driverNews);
		JSONObject result = new JSONObject();
		if(isSuccess){
			result.put("status", Retcode.OK.code);
			result.put("message", Retcode.OK.msg);
		}else{
			result.put("status", Retcode.EXCEPTION.code);
			result.put("message", Retcode.EXCEPTION.msg);
		}
		return result;
	}



}
