/**
 * 
 */
package com.ry.taxi.order.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ry.taxi.base.constant.ErrorEnum;
import com.ry.taxi.base.constant.UrlRequestConstant;
import com.ry.taxi.base.query.BaseResult;
import com.ry.taxi.order.request.DriverArrivalParam;
import com.ry.taxi.order.request.DriverCancelParam;
import com.ry.taxi.order.request.DriverStartParam;
import com.ry.taxi.order.request.DriverTakeParam;
import com.ry.taxi.order.service.OrderService;
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
	
	@Autowired
    private OrderService orderService;
	
	private static final Integer SUCESS_RESPONSE = 1;
	
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
		result.setCmd(UrlRequestConstant.CMD_DRIVERTAKEORDER);
		try {
			driverTakeParam = JSONUtil.objectMapper.readValue(jsonParam, DriverTakeParam.class);
		} catch (IOException e) {
			logger.error("司机应邀通知,json参数{}转换失败:{}",jsonParam,e.getMessage());
			result.setRemark(PropertiesUtil.getStringByKey(String.valueOf(ErrorEnum.e1005.getValue()), ""));
			result.setResult(ERROR_RESPONSE);
		    return JSONUtil.toJackson(result);
		}
		int resultCode = orderService.doTakingOrder(driverTakeParam);
		if (resultCode > 0){
			result.setRemark(PropertiesUtil.getStringByKey(String.valueOf(resultCode), ""));
			result.setResult(ERROR_RESPONSE);
			return JSONUtil.toJackson(result);
			
		}
		result.setResult(SUCESS_RESPONSE);	
		return JSONUtil.toJackson(result);
	}
	
	/*
	 * 司机执行订单通知
	 */
	public String driverStartOrder(String jsonParam) throws JsonProcessingException {
		BaseResult<String> result = new BaseResult<String>();
		DriverStartParam driverStartParam = null;
		result.setCmd(UrlRequestConstant.CMD_DRIVERSTARTORDER);
		try {
			driverStartParam = JSONUtil.objectMapper.readValue(jsonParam, DriverStartParam.class);
		} catch (IOException e) {
			
			logger.error("司机执行订单通知,json参数转换失败:{}",e.getMessage());
			result.setRemark(PropertiesUtil.getStringByKey(String.valueOf(ErrorEnum.e1005.getValue()), ""));
			result.setResult(ERROR_RESPONSE);	
			return JSONUtil.toJackson(result);
		}

		String resultinfo = orderService.doStartOrder(driverStartParam);
		
		
		return resultinfo;
		
	}
	
	/*
	 * 司机到达乘客起点位置消息
	 */
	public String driverArrival(String jsonParam) throws JsonProcessingException{
		DriverArrivalParam driverArrivalParam= null;
		BaseResult<String> result = new BaseResult<String>();
		result.setCmd(UrlRequestConstant.CMD_DRIVERTAKEORDER);
		try {
			driverArrivalParam = JSONUtil.objectMapper.readValue(jsonParam, DriverArrivalParam.class);
		} catch (IOException e) {
			logger.error("司机到达乘客起点位置通知,json参数{}转换失败:{}",jsonParam,e.getMessage());
			result.setRemark(PropertiesUtil.getStringByKey(String.valueOf(ErrorEnum.e1005.getValue()), ""));
			result.setResult(ERROR_RESPONSE);
		    return JSONUtil.toJackson(result);
		}	
		
		int resultCode = orderService.doDriverArrival(driverArrivalParam);
		if (resultCode > 0){
			result.setRemark(PropertiesUtil.getStringByKey(String.valueOf(resultCode), ""));
			result.setResult(ERROR_RESPONSE);
			return JSONUtil.toJackson(result);
			
		}
		result.setResult(SUCESS_RESPONSE);	
		return JSONUtil.toJackson(result);
		
	}
	
	/*
	 * 司机取消通知
	 */
	public String driverCancelOrder(String jsonParam) throws JsonProcessingException{
		BaseResult<String> result = new BaseResult<String>();
		DriverCancelParam drivercancel = null;
		result.setCmd(UrlRequestConstant.CMD_DRIVERSTARTORDER);
		try {
			drivercancel = JSONUtil.objectMapper.readValue(jsonParam, DriverCancelParam.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("司机取消通知,json参数转换失败:{}",e.getMessage());
		
			result.setRemark(PropertiesUtil.getStringByKey(String.valueOf(ErrorEnum.e1005.getValue()), ""));
			result.setResult(ERROR_RESPONSE);	
			return JSONUtil.toJackson(result);
		}
		
		
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
