/**
 * 
 */
package com.szyciov.carservice.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.carservice.service.PubInfoService;
import com.szyciov.entity.PubAdImage;
import com.szyciov.entity.PubSysVersion;
import com.szyciov.util.ApiExceptionHandle;

import net.sf.json.JSONObject;

/**
 * @ClassName PubInfoController 
 * @author Efy Shu
 * @Description 获取公共信息控制器
 * @date 2016年9月27日 下午8:21:19 
 */
@Controller
public class PubInfoController extends ApiExceptionHandle{
	
	private PubInfoService service;

	@Resource(name="PubInfoService")
	public void setPubInfoService(PubInfoService service) {
		this.service = service;
	}
	
	
	/**
	 * 获取机场列表
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="api/PubInfoApi/GetAirPorts")
	public JSONObject getAirPorts(){
		starttime.set(System.currentTimeMillis());
		JSONObject result = service.getAirPorts();
		releaseResource(service);
		return checkResult(result);
	}
	
	/**
	 * 获取城市列表
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="api/PubInfoApi/GetCities")
	public JSONObject getCities(){
		starttime.set(System.currentTimeMillis());
		JSONObject result = service.getCities();
		releaseResource(service);
		return checkResult(result);
	}
	
	/**
	 * 获取用车事由列表
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "api/PubInfoApi/GetUseCarReason")
	public JSONObject getUseCarReason() {
		starttime.set(System.currentTimeMillis());
		JSONObject result = service.getUseCarReason();
		releaseResource(service);
		return checkResult(result);
	}
	
	/**
	 * 获取广告图片
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="api/PubInfoApi/GetPubAdImage")
	public JSONObject getPubAdImage(@RequestBody PubAdImage param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = service.getPubAdImage(param);
		releaseResource(service);
		return checkResult(result);
	}
	
	/**
	 * 获取系统版本
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="api/PubInfoApi/GetPubSysVersion")
	public JSONObject getPubSysVersion(@RequestBody PubSysVersion param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = service.getPubSysVersion(param);
		releaseResource(service);
		return checkResult(result);
	}
	
	/**
	 * 创建系统版本信息
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="api/PubInfoApi/CreatePubSysVersion")
	public JSONObject createPubSysVersion(@RequestBody PubSysVersion param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = service.createPubSysVersion(param);
		releaseResource(service);
		return checkResult(result);
	}
	
	
	@ResponseBody
	@RequestMapping(value="api/PubInfoApi/CheckTokenValid")
	public Map<String,Object> checkTokenValid(@RequestBody Map<String,Object> param){
		Map<String,Object> res = new HashMap<String,Object>();
		boolean flag = service.checkTokenValid(param);
		res.put("flag", flag);
	    return res;
	}
	
	/**
	 * 城市控件1
	 * @return
	 */
	@RequestMapping(value = "api/PubInfoApi/GetCitySelect1")
	@ResponseBody
	public JSONObject getCitySelect1() {
		return service.getCitySelect1();
	}
	
}
