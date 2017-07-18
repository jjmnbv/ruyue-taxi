/**
 * 
 */
package com.ry.taxi.order.web;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ry.taxi.base.constant.ErrorEnum;
import com.ry.taxi.base.constant.UrlRequestConstant;
import com.ry.taxi.base.query.BaseResult;
import com.ry.taxi.order.request.DriverTakeParam;
import com.xunxintech.ruyue.coach.io.file.PropertiesUtil;
import com.xunxintech.ruyue.coach.io.json.JSONUtil;


/**
 * @Title:MainOrderController.java
 * @Package com.ry.taxi.order.web
 * @Description
 * @author zhangdd
 * @date 2017年7月17日 上午10:27:06
 * @version 
 *
 * @Copyrigth  版权所有 (C) 2017 广州讯心信息科技有限公司.
 */
@RestController
@RequestMapping(value = "/RyTaxi/Management/func", produces = "application/json; charset=utf-8")
public class BaseOrderController {
	
	private static Logger logger = LoggerFactory.getLogger(BaseOrderController.class);
	

	
	private static final Integer ERROR_RESPONSE = 2;
	
	
	/*
	 * 请求分发
	 */
	@PostMapping
	public String cmdRoute(@RequestBody String jsonParam,HttpServletRequest request) throws JsonProcessingException{
		String cmdName = request.getParameter(UrlRequestConstant.CMD);
		switch(cmdName){
			case UrlRequestConstant.CMD_DRIVERTAKEORDER : return driverTakeOrder(jsonParam);//司机应邀
			case UrlRequestConstant.CMD_DRIVERSTARTORDER : return driverStartOrder(jsonParam);//司机执行订单
			case UrlRequestConstant.CMD_DRIVERARRIVAL : return driverArrival(jsonParam); //司机到达乘客起点
			case UrlRequestConstant.CMD_DRIVERCANCELORDER : return driverCancelOrder(jsonParam);//司机取消
			case UrlRequestConstant.CMD_STARTCALCULATION : return startCalculation(jsonParam);//压表
			case UrlRequestConstant.CMD_ENDCALCULATION : return endCalculation(jsonParam);//起表
			case UrlRequestConstant.CMD_PAYMENTCONFIRMATION: return paymentConfirmation(jsonParam);//支付确认
		}
		BaseResult<String> result = new BaseResult<String>();
		result.setCmd(cmdName);
		result.setRemark(PropertiesUtil.getStringByKey(String.valueOf(ErrorEnum.e1004.getValue()), ""));
		result.setResult(ERROR_RESPONSE);
	    return JSONUtil.toJackson(result);
	}
	
	/*
	 * 司机应邀通知
	 */
	public String driverTakeOrder(String jsonParam) throws JsonProcessingException{
		DriverTakeParam driverTakeParam= null;
		BaseResult<String> result = new BaseResult<String>();
		try {
			driverTakeParam = JSONUtil.objectMapper.readValue(jsonParam, DriverTakeParam.class);
		} catch (IOException e) {
			logger.error("司机应邀通知,json参数转换失败:{}",e.getMessage());
			result.setCmd(UrlRequestConstant.CMD_DRIVERTAKEORDER);
			result.setRemark(PropertiesUtil.getStringByKey(String.valueOf(ErrorEnum.e1005.getValue()), ""));
			result.setResult(ERROR_RESPONSE);
		    return JSONUtil.toJackson(result);
		}
		
		return null;
	}
	
	/*
	 * 司机执行订单通知
	 */
	public String driverStartOrder(String jsonParam){
		
		return null;
		
	}
	
	/*
	 * 司机到达乘客起点位置消息
	 */
	public String driverArrival(String jsonParam){
		
		return null;
		
	}
	
	/*
	 * 司机取消通知
	 */
	public String driverCancelOrder(String jsonParam){
		
		return null;
		
	}
	
	/*
	 * 压表通知
	 */
	public String startCalculation(String jsonParam){
		
		return null;
		
	}
	
	/*
	 * 起表通知
	 */
	public String endCalculation(String jsonParam){
		
		return null;
		
	}
	
	/*
	 * 支付确认通知
	 */
	public String paymentConfirmation(String jsonParam){
		
		return null;
		
	}
	
	

}
