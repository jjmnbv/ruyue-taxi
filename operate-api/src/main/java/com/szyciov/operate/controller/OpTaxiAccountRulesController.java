package com.szyciov.operate.controller;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.entity.Retcode;
import com.szyciov.entity.Select2Entity;
import com.szyciov.op.entity.OpTaxiAccountRule;
import com.szyciov.operate.service.OpTaxiAccountRulesService;
import com.szyciov.param.Select2Param;
import com.szyciov.util.ApiExceptionHandle;
import com.szyciov.util.PageBean;

import net.sf.json.JSONObject;



/**
  * @ClassName OpTaxiAccountRulesController
  * @author Efy Shu
  * @Description 出租车计费规则Controller
  * @date 2017年3月9日 11:43:09
  */ 
@Controller("OpTaxiAccountRulesController")
public class OpTaxiAccountRulesController extends ApiExceptionHandle{
	private static final Logger logger = Logger.getLogger(OpTaxiAccountRulesController.class);
	
	/**
	  *依赖
	  */
	private OpTaxiAccountRulesService optaxiaccountrulesservice;

	/**
	  *依赖注入
	  */
	@Resource(name="OpTaxiAccountRulesService")
	public void setOpTaxiAccountRulesService(OpTaxiAccountRulesService optaxiaccountrulesservice){
		this.optaxiaccountrulesservice=optaxiaccountrulesservice;
	}
	
	/**
	 * select2城市控件
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="api/OpTaxiAccountRules/GetCityListForSelect")
	public List<Select2Entity> getCityListForSelect(@RequestBody Select2Param param){
		return optaxiaccountrulesservice.getCityListForSelect(param);
	}
	
	/**
	 * 条件查询规则列表
	 * @param rule
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="api/OpTaxiAccountRules/Search",method=RequestMethod.POST)
	public PageBean search(@RequestBody OpTaxiAccountRule rule){
		if(null == rule){
			logger.info("查询条件不能为空!");
			throw new NullPointerException();
		}else{
			return optaxiaccountrulesservice.search(rule);
		}
	}
	
	/**
	 * ID查询规则
	 * @param rule
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="api/OpTaxiAccountRules/SearchById",method=RequestMethod.POST)
	public JSONObject searchById(@RequestBody OpTaxiAccountRule rule){
		if(null == rule){
			logger.info("查询条件不能为空!");
			throw new NullPointerException();
		}else{
			OpTaxiAccountRule r = optaxiaccountrulesservice.searchById(rule);
			JSONObject result = new JSONObject();
			if(r != null){
				result.put("status", Retcode.OK.code);
				result.put("message", Retcode.OK.msg);
				result.put("rule", r);
			}else{
				result.put("status", Retcode.FAILED.code);
				result.put("message", Retcode.FAILED.msg);
			}
			return result;
		}
	}
	
	/**
	 * 新增出租车计费规则
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="api/OpTaxiAccountRules/AddRule",method=RequestMethod.POST)
	public JSONObject addRule(@RequestBody OpTaxiAccountRule rule,HttpServletRequest request){
		String token = getUserToken(request);
		rule.setToken(token);
		JSONObject result = new JSONObject();
		if(!checkDuplicateByCity(rule)){
			optaxiaccountrulesservice.save(rule);
			result.put("status", Retcode.OK.code);
			result.put("message", Retcode.OK.msg);
		}else{
			result.put("status", Retcode.FAILED.code);
			result.put("message", "该城市已有计费规则，无法新增");
		}
		return result;
	}
	
	/**
	 * 修改出租车计费规则
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="api/OpTaxiAccountRules/EditRule",method=RequestMethod.POST)
	public JSONObject editRule(@RequestBody OpTaxiAccountRule rule){
		JSONObject result = new JSONObject();
		if(optaxiaccountrulesservice.edit(rule)){
			result.put("status", Retcode.OK.code);
			result.put("message", Retcode.OK.msg);
		}else{
			result.put("status", Retcode.FAILED.code);
			result.put("message", Retcode.FAILED.msg);
		}
		return result;
	}
	
	/**
	 * 禁用规则
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="api/OpTaxiAccountRules/DisableRule",method=RequestMethod.POST)
	public JSONObject disableRule(@RequestBody OpTaxiAccountRule rule){
		JSONObject result = new JSONObject();
		if(optaxiaccountrulesservice.disable(rule)){
			result.put("status", Retcode.OK.code);
			result.put("message", Retcode.OK.msg);
		}else{
			result.put("status", Retcode.FAILED.code);
			result.put("message", Retcode.FAILED.msg);
		}
		return result;
	}
	
	/**
	 * 启用规则
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="api/OpTaxiAccountRules/EnableRule",method=RequestMethod.POST)
	public JSONObject enableRule(@RequestBody OpTaxiAccountRule rule){
		JSONObject result = new JSONObject();
		if(optaxiaccountrulesservice.enable(rule)){
			result.put("status", Retcode.OK.code);
			result.put("message", Retcode.OK.msg);
		}else{
			result.put("status", Retcode.FAILED.code);
			result.put("message", Retcode.FAILED.msg);
		}
		return result;
	}
	
	/**
	 * 查询操作历史
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="api/OpTaxiAccountRules/SearchHistory",method=RequestMethod.POST)
	public PageBean searchHistory(@RequestBody OpTaxiAccountRule rule){
		return optaxiaccountrulesservice.searchHistory(rule);
	}
	
	/**
	 * 检查是否有重复城市的规则
	 * @param rule
	 * @return
	 */
	public boolean checkDuplicateByCity(OpTaxiAccountRule rule){
		PageBean list = optaxiaccountrulesservice.search(rule);
		return list.iTotalDisplayRecords > 0;
	}
}
