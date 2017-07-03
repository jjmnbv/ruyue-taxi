package com.szyciov.carservice.controller;

import com.szyciov.carservice.service.UserNewsService;
import com.szyciov.entity.Retcode;
import com.szyciov.param.UserNewsParam;
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
 * 用户消息接口服务
 * @author LC
 */
@RestController
@RequestMapping("api/UserNews")
public class UserNewsApiController {

	private Logger logger = LoggerFactory.getLogger(UserNewsApiController.class);

	@Resource
	private UserNewsService userNewsService;


	@RequestMapping( value = "/addUserNews",method = RequestMethod.POST,produces = "application/json")
	public JSONObject addUserNews(@RequestBody UserNewsParam userNewsParam) throws IOException {

		boolean isSuccess =  userNewsService.saveUserNews(userNewsParam);
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
