package com.xxkj.passenger.wechat.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.szyciov.entity.Retcode;
import com.szyciov.passenger.service.PassengerService;
import com.szyciov.passenger.service.PassengerService4Fourth;

/**
 * 
 */
@RestController
public class PassengerWechatController {

    private static Logger logger = LoggerFactory.getLogger(PassengerWechatController.class);
    
    @Autowired
    private PassengerService passengerService;
	
    @Autowired
    private PassengerService4Fourth passengerService4Fourth;
	
	/**
	 * 支付接口
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "PassengerWechat/PayOder", method = {RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> payOder(@RequestBody Map<String,Object> params,HttpServletRequest req,HttpServletResponse resp){
		try{
			return passengerService4Fourth.payOder(params,req);
		}catch (Exception e){
			Map<String, Object> res = new HashMap<String, Object>();
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
			return res;
		}
	}
	
	@RequestMapping(value = "PassengerWechat/DillWXPubPayed4Op", method = RequestMethod.POST)
	public void dillWXPayed4Op(HttpServletRequest req,HttpServletResponse res){
		passengerService.dillWXPubPayed4Op(req,res);
	}
	
}
