package com.szyciov.operate.service;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.szyciov.annotation.ValidateRule;
import com.szyciov.entity.Retcode;
import com.szyciov.entity.Select2Entity;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.op.entity.OpTaxiAccountRule;
import com.szyciov.param.Select2Param;
import com.szyciov.util.PageBean;
import com.szyciov.util.StringUtil;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper;
import com.szyciov.util.ValidateUtil;

import net.sf.json.JSONObject;



/**
  * @ClassName OpTaxiAccountRulesService
  * @author Efy Shu
  * @Description 出租车计费规则Service
  * @date 2017年3月9日 11:43:09
  */ 
@Service("OpTaxiAccountRulesService")
public class OpTaxiAccountRulesService{
	private static final Logger logger = Logger.getLogger(OpTaxiAccountRulesService.class);
	private static final String apiUrl = SystemConfig.getSystemProperty("webApiUrl");
	private static final String carserviceApiUrl = SystemConfig.getSystemProperty("carserviceApiUrl");
	private TemplateHelper templateHelper = new TemplateHelper();
	
	@SuppressWarnings("unchecked")
	public List<Select2Entity> getCityListForSelect(Select2Param param){
		//这里应该检查参数
		List<Select2Entity> list =  templateHelper.dealRequestWithFullUrlToken(
				apiUrl+"/OpTaxiAccountRules/GetCityListForSelect", 
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
	
	public PageBean search(OpTaxiAccountRule rule){
		//检查参数
		PageBean result =  templateHelper.dealRequestWithFullUrlToken(
				apiUrl+"/OpTaxiAccountRules/Search", 
				HttpMethod.POST, 
				rule.getToken(),
				rule,
				PageBean.class);
		return result;
	}
	
	public OpTaxiAccountRule searchById(OpTaxiAccountRule rule){
		//检查参数
		JSONObject result =  templateHelper.dealRequestWithFullUrlToken(
				apiUrl+"/OpTaxiAccountRules/SearchById", 
				HttpMethod.POST, 
				rule.getToken(),
				rule,
				JSONObject.class);
		if(result.getInt("status") == Retcode.OK.code){
			return StringUtil.parseJSONToBean(result.getJSONObject("rule").toString(), OpTaxiAccountRule.class);
		}
		return null;
	}
	
	public JSONObject save(OpTaxiAccountRule rule){
		//检查参数
		String error = checkParam(rule);
		JSONObject result = new JSONObject();
		if(error != null){
			result.put("status", Retcode.FAILED.code);
			result.put("message", error);
			return result;
		}
		//校验标准里程是否大于起租里程
		if(rule.getStandardrange() <= rule.getStartrange()){
			result.put("status", Retcode.FAILED.code);
			result.put("message", "标准里程应大于起租里程");
			return result;
		}
		result =  templateHelper.dealRequestWithFullUrlToken(
				apiUrl+"/OpTaxiAccountRules/AddRule", 
				HttpMethod.POST, 
				rule.getToken(),
				rule,
				JSONObject.class);
		return result;
	}
	
	public JSONObject edit(OpTaxiAccountRule rule){
		//检查参数
		String error = checkParam(rule);
		JSONObject result = new JSONObject();
		if(error != null){
			result.put("status", Retcode.FAILED.code);
			result.put("message", error);
			return result;
		}
		//校验标准里程是否大于起租里程 standardrange
		if(rule.getStartrange() >= rule.getStandardrange()){
			result.put("status", Retcode.FAILED.code);
			result.put("message", "标准里程应大于起租里程");
			return result;
		}
		result =  templateHelper.dealRequestWithFullUrlToken(
				apiUrl+"/OpTaxiAccountRules/EditRule", 
				HttpMethod.POST, 
				rule.getToken(),
				rule,
				JSONObject.class);
		return result;
	}
	
	/**
	 * 禁用规则
	 * @return
	 */
	public JSONObject disableRule(OpTaxiAccountRule rule){
		//检查参数
		JSONObject result =  templateHelper.dealRequestWithFullUrlToken(
				apiUrl+"/OpTaxiAccountRules/DisableRule", 
				HttpMethod.POST, 
				rule.getToken(),
				rule,
				JSONObject.class);
		return result;
	}
	
	/**
	 * 启用规则
	 * @return
	 */
	public JSONObject enableRule(OpTaxiAccountRule rule){
		//检查参数
		JSONObject result =  templateHelper.dealRequestWithFullUrlToken(
				apiUrl+"/OpTaxiAccountRules/EnableRule", 
				HttpMethod.POST, 
				rule.getToken(),
				rule,
				JSONObject.class);
		return result;
	}
	
	/**
	 * 启用规则
	 * @return
	 */
	public PageBean searchHistory(OpTaxiAccountRule rule){
		//检查参数
		PageBean result =  templateHelper.dealRequestWithFullUrlToken(
				apiUrl+"/OpTaxiAccountRules/SearchHistory", 
				HttpMethod.POST, 
				rule.getToken(),
				rule,
				PageBean.class);
		return result;
	}
	
	/**
	 * 检查输入项是否完整
	 * @param num
	 * @return
	 */
	@ValidateRule(msg="请输入完整信息")
	public boolean checkNumNotZero(double num){
		return num >= 0;
	}
	
	/**
	 * 检查输入项是否符合规则
	 * @param num
	 * @return
	 */
	@ValidateRule(msg="请输入正确数字,最大4位整数,1位小数")
	public boolean checkNumLimit(double num){
		return (num+"").matches("\\d{1,4}\\.\\d{0,1}");
	}
	
	/**
	 * 检查城市项
	 * @return
	 */
	@ValidateRule(msg="请输入完整信息")
	public boolean checkCity(String city){
		return null != city && !city.trim().isEmpty();
	}
	
	public String checkParam(OpTaxiAccountRule rule){
		ValidateUtil util = new ValidateUtil();
		util.addRuleClass(this);
		util.loadRules();
		util.addErrorMessage("checkNull", "请输入完整信息");
		if(!util.valid(rule)){
			for(String field : util.getErrorFields()){
				logger.info("出租车计费规则参数校验失败:" + util.getError(field));
			}
			return util.getError(util.getErrorFields()[0]);
		}
		return null;
	}
}
