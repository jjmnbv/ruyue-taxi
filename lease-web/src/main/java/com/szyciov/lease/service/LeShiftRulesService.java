package com.szyciov.lease.service;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.szyciov.entity.Retcode;
import com.szyciov.entity.Select2Entity;
import com.szyciov.lease.entity.LeShiftRule;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.param.Select2Param;
import com.szyciov.util.PageBean;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper;
import com.szyciov.util.ValidateUtil;

import net.sf.json.JSONObject;



/**
  * @ClassName LeShiftRulesService
  * @author Efy
  * @Description 交接班规则Service
  * @date 2017年3月3日 10:47:03
  */ 
@Service("LeShiftRulesService")
public class LeShiftRulesService{
	private static final Logger logger = Logger.getLogger(LeShiftRulesService.class);
	private static final String apiUrl = SystemConfig.getSystemProperty("webApiUrl");
	private static final String carserviceApiUrl = SystemConfig.getSystemProperty("carserviceApiUrl");
	private TemplateHelper templateHelper = new TemplateHelper();
	
	
	@SuppressWarnings("unchecked")
	public List<Select2Entity> getCityListForSelect(Select2Param param){
		//这里应该检查参数
		List<Select2Entity> list =  templateHelper.dealRequestWithFullUrlToken(
				apiUrl+"/LeShiftRules/GetCityListForSelect", 
				HttpMethod.POST, 
				param.getToken(),
				param,
				List.class);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<PubCityAddr> getCityList(String token){
		//这里应该检查参数
		JSONObject result =  templateHelper.dealRequestWithFullUrlToken(
				carserviceApiUrl+"/PubInfoApi/GetCities", 
				HttpMethod.POST, 
				token,
				null,
				JSONObject.class);
		List<PubCityAddr> list = new ArrayList<>();
		if(result.getInt("status") == Retcode.OK.code){
			list = result.getJSONArray("cities");
		}
		return list;
	}
	
	public JSONObject duplicateCheck(LeShiftRule rule){
		//这里应该检查参数
		JSONObject result =  templateHelper.dealRequestWithFullUrlToken(
				apiUrl+"/LeShiftRules/DuplicateCheck", 
				HttpMethod.POST, 
				rule.getToken(),
				rule,
				JSONObject.class);
		return result;
	}
	
	public PageBean getRules(LeShiftRule rule){
		//这里应该检查参数
		PageBean result =  templateHelper.dealRequestWithFullUrlToken(
				apiUrl+"/LeShiftRules/GetLeShiftRules", 
				HttpMethod.POST, 
				rule.getToken(),
				rule,
				PageBean.class);
		return result;
	}
	
	public JSONObject insert(LeShiftRule rules){
		//检查参数
		JSONObject result = new JSONObject();
		String error = checkParam(rules);
		if(error != null){
			result.put("status", Retcode.FAILED.code);
			result.put("message", error);
			return result;
		}
		result = templateHelper.dealRequestWithFullUrlToken(
				apiUrl+"/LeShiftRules/AddRules", 
				HttpMethod.POST, 
				rules.getToken(),
				rules, 
				JSONObject.class);
		return result;
	}
	
	public JSONObject update(LeShiftRule rules){
		//检查参数
		JSONObject result = new JSONObject();
		String error = checkParam(rules);
		if(error != null){
			result.put("status", Retcode.FAILED.code);
			result.put("message", error);
			return result;
		}
		result = templateHelper.dealRequestWithFullUrlToken(
				apiUrl+"/LeShiftRules/ModifyRules", 
				HttpMethod.POST, 
				rules.getToken(),
				rules, 
				JSONObject.class);
		return result;
	}
	
	public String checkParam(LeShiftRule rules){
		ValidateUtil util = new ValidateUtil();
		util.loadRules();
		if(!util.valid(rules)){
			for(String field : util.getErrorFields()){
				logger.info("出租车交接班规则参数校验失败:" + util.getError(field));
			}
			return util.getError(util.getErrorFields()[0]);
		}
		return null;
	}
}
