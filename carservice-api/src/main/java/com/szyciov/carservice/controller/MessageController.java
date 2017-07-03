package com.szyciov.carservice.controller;

import java.util.HashMap;
import java.util.Map;

import com.szyciov.message.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.carservice.util.MessageConnection;
import com.szyciov.util.BaseController;

/**
 * 添加消息的接口
 * 接受消息并且插入队列
 * @ClassName MessageController 
 */
@Controller
public class MessageController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(MessageController.class);
	
	/**
	 * 添加一个账单类消息
	 * @param billmessage
	 */
	@RequestMapping(value="api/Message/SendBillMessage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> sendBillMessage(@RequestBody BillMessage billmessage){
		Map<String,String> res = new HashMap<String,String>();
		MessageConnection.add(billmessage);
		res.put("status", "success");
		res.put("message", "已放入队列");
		return res;
	}
	
	/**
	 * 添加一个订单类消息
	 * @param ordermessage
	 */
	@RequestMapping(value="api/Message/SendOrderMessage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> sendOrderMessage(@RequestBody OrderMessage ordermessage){
		Map<String,String> res = new HashMap<String,String>();
		MessageConnection.add(ordermessage);
		res.put("status", "success");
		res.put("message", "已放入队列");
		return res;
	}
	
	/**
	 * 添加一个出租车类消息
	 * @param taxiOrderMessage
	 * @return
	 */
	@RequestMapping(value = "api/Message/SendTaxiOrderMessage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> sendTaxiOrderMessage(@RequestBody TaxiOrderMessage taxiOrderMessage) {
		Map<String,String> res = new HashMap<String,String>();
		MessageConnection.add(taxiOrderMessage);
		res.put("status", "success");
		res.put("message", "已放入队列");
		return res;
	}
	
	/**
	 * 添加toc类消息
	 * @param tocmessage
	 */
	@RequestMapping(value="api/Message/SendToCMessage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> sendToCMessage(@RequestBody ToCMessage tocmessage){
		Map<String,String> res = new HashMap<String,String>();
		MessageConnection.add(tocmessage);
		res.put("status", "success");
		res.put("message", "已放入队列");
		return res;
	}
	
	/**
	 * 添加用户类消息
	 * @param usermessage
	 */
	@RequestMapping(value="api/Message/SendUserMessage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> sendUserMessage(@RequestBody UserMessage usermessage){
		Map<String,String> res = new HashMap<String,String>();
		MessageConnection.add(usermessage);
		res.put("status", "success");
		res.put("message", "已放入队列");
		return res;
	}

	/**
	 * 添加交接班类消息
	 * @param usermessage
	 */
	@RequestMapping(value="api/Message/sendProcessedMessage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> sendPendingMessage(@RequestBody ProcessedMessage usermessage){
		Map<String,String> res = new HashMap<String,String>();
		MessageConnection.add(usermessage);
		res.put("status", "success");
		res.put("message", "已放入队列");
		return res;
	}


	/**
	 * 添加绑定类信息
	 * @param usermessage
	 */
	@RequestMapping(value="api/Message/sendBindMessage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> sendPendingMessage(@RequestBody DriverVehicleBindMessage usermessage){
		Map<String,String> res = new HashMap<String,String>();
		MessageConnection.add(usermessage);
		res.put("status", "success");
		res.put("message", "已放入队列");
		return res;
	}


	/**
	 * 添加一个提现类消息
	 * @param cashMessage
	 */
	@RequestMapping(value="api/Message/sendCashMessage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> sendCashMessage(@RequestBody CashMessage cashMessage){
		Map<String,String> res = new HashMap<String,String>();
		MessageConnection.add(cashMessage);
		res.put("status", "success");
		res.put("message", "提现消息已放入队列");
		return res;
	}
}
