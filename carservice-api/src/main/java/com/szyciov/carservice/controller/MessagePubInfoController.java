package com.szyciov.carservice.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.carservice.service.MessagePubInfoService;
import com.szyciov.passenger.entity.LeasesCompany;
import com.szyciov.passenger.entity.PushMessageLog;
import com.szyciov.util.AppMessageUtil;
import com.szyciov.util.PushObjFactory;

import cn.jpush.api.push.model.PushPayload;

@Controller
public class MessagePubInfoController {
	
	private MessagePubInfoService service;

	@Resource(name="MessagePubInfoService")
	public void setMessagePubInfoService(MessagePubInfoService service) {
		this.service = service;
	}

	@ResponseBody
	@RequestMapping(value="api/MessagePubInfo/GetDriverPhoneById/{driverid}")
	public String getDriverPhoneById(@PathVariable String driverid){
		return service.getDriverPhoneById(driverid);
	}
	
	@ResponseBody
	@RequestMapping(value="api/MessagePubInfo/GetLeaseCompanyById/{companyid}")
	public LeasesCompany getLeaseCompanyById(@PathVariable String companyid){
		return service.getLeaseCompanyById(companyid);
	}
	
	@ResponseBody
	@RequestMapping(value="api/MessagePubInfo/GetOrgUserInfo/{userid}")
	public Map<String,Object> getOrgUserInfo(@PathVariable String userid){
		return service.getOrgUserInfo(userid);
	}
	
	@ResponseBody
	@RequestMapping(value="api/MessagePubInfo/GetOpUserInfo/{userid}")
	public Map<String,Object> getOpUserInfo(@PathVariable String userid){
		return service.getOpUserInfo(userid);
	}
	
	@ResponseBody
	@RequestMapping(value="api/MessagePubInfo/GetDriverInfo/{driverid}")
	public Map<String,Object> getDriverInfo(@PathVariable String driverid){
		return service.getDriverInfo(driverid);
	}
	
	
	@ResponseBody
	@RequestMapping(value="api/MessagePubInfo/GetCarInfo/{vehicleid}")
	public Map<String,Object> getCarInfo(@PathVariable String vehicleid){
		return service.getCarInfo(vehicleid);
	}
	
	@ResponseBody
	@RequestMapping(value="api/MessagePubInfo/GetOpInfo")
	public Map<String,Object> getOpInfo(){
		return service.getOpInfo();
	}
	
	
	/**
	 * 保存手机推送消息
	 * 
	 * @author xuxxtr
	 */
	@RequestMapping(value="api/Message/savePushMessage",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,String> savePushMessage(@RequestBody PushMessageLog pushLog){
		return service.savePushMessage(pushLog);
	}
	 
	/*@RequestMapping(value="api/Message/test/{type}",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,String> test(@PathVariable String type){
		List<String> phones4driver = new ArrayList<String>();
     	phones4driver.add("15821188307");
     	
     	if(type.equals("1")){
    	PushPayload pushload_android = PushObjFactory.creatHintObj4Android("测试android司机端", "测试", phones4driver, null);
		PushPayload pushload_ios = PushObjFactory.creatHintObj4IOS("测试ios司机端", "测试", null, phones4driver, null);
    	AppMessageUtil.send(pushload_ios, pushload_android, "1");
     	}
     	if(type.equals("2")){
        	PushPayload pushload = PushObjFactory.creatHintObj4Android("测试android乘客端", "测试", phones4driver, null);
        	PushPayload pushload_ios = PushObjFactory.creatHintObj4IOS("测试ios乘客端", "测试", null, phones4driver, null);
    		AppMessageUtil.send(pushload_ios, pushload, "0");
         	}
     	if(type.equals("3")){
        	PushPayload pushload = PushObjFactory.creatHintObj4IOS("测试ios司机端", "测试", phones4driver, null);
    		AppMessageUtil.send(pushload, null, "1");
         	}
     	if(type.equals("4")){
        	PushPayload pushload = PushObjFactory.creatHintObj4IOS("测试ios乘客端", "测试", phones4driver, null);
    		AppMessageUtil.send(pushload, null, "0");
         	}
		Map<String,String> map=new HashMap<>();
		map.put("result", "success");
		return map;
	}*/
}
