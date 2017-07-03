package com.szyciov.carservice.controller;

import com.szyciov.carservice.service.PhoneAuthenticationService;
import com.szyciov.entity.Retcode;
import com.szyciov.param.PhoneAuthenticationParam;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * 寻程数据相关接口controller
 */
@Controller
public class XunChengApiController {

	private Logger logger = LoggerFactory.getLogger(XunChengApiController.class);


	@Autowired
	private PhoneAuthenticationService phoneAuthenticationService;


	
	/**
	 * 手机号，身份证，姓名 实名制认证
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("api/XunChengApi/realNameAuthentication")
	@ResponseBody
	public JSONObject realNameAuthentication(@RequestBody PhoneAuthenticationParam param) throws IOException {
		JSONObject jsonObject = new JSONObject();
		try {
			boolean success = phoneAuthenticationService.realNameAuthentication(param);
			jsonObject.put("status",Retcode.OK.code);
			jsonObject.put("data",success);
		}catch (Exception e){
			logger.error("手机、身份证、姓名实名制认证失败！",e);
			jsonObject.put("status", Retcode.FAILED.code);
			jsonObject.put("message", "认证失败，请稍候再试！");
		}

		return jsonObject;
	}


	/**
	 * 手机号是否实名认证
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("api/XunChengApi/phoneAuthentication")
	@ResponseBody
	public JSONObject phoneAuthentication(@RequestBody PhoneAuthenticationParam param) throws IOException {
		JSONObject jsonObject = new JSONObject();

		try {
			boolean success = phoneAuthenticationService.phoneAuthentication(param);
			jsonObject.put("status",Retcode.OK.code);
			jsonObject.put("data",success);
		}catch (Exception e){
			logger.error("手机号是否实名认证！",e);
			jsonObject.put("status", Retcode.FAILED.code);
			jsonObject.put("message", "认证失败，请稍候再试！");
		}
		return jsonObject;
	}
	

}
