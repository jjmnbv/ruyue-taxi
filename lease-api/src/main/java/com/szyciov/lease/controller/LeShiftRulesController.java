package com.szyciov.lease.controller;


import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.entity.Retcode;
import com.szyciov.entity.Select2Entity;
import com.szyciov.lease.entity.LeShiftRule;
import com.szyciov.lease.service.LeShiftRulesService;
import com.szyciov.param.Select2Param;
import com.szyciov.util.ApiExceptionHandle;
import com.szyciov.util.PageBean;

import net.sf.json.JSONObject;



/**
  * @ClassName LeShiftRulesController
  * @author Efy
  * @Description 交接班规则Controller
  * @date 2017年3月2日 14:07:02
  * 
  */
@Controller("LeShiftRulesController")
public class LeShiftRulesController extends ApiExceptionHandle{
	private static final Logger logger = Logger.getLogger(LeShiftRulesController.class);
	
	/**
	  *依赖
	  */
	private LeShiftRulesService leshiftrulesservice;
	
	/**
	  *依赖注入
	  */
	@Resource(name="LeShiftRulesService")
	public void setLeShiftRulesService(LeShiftRulesService leshiftrulesservice){
		this.leshiftrulesservice=leshiftrulesservice;
	}

	/**
	 * select2城市控件
	 * @param param
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="api/LeShiftRules/GetCityListForSelect")
	public List<Select2Entity> getCityListForSelect(@RequestBody Select2Param param){
		return leshiftrulesservice.getCityListForSelect(param);
	}
	
	/**
	 * 查询交接班规则
	 * @param type
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="api/LeShiftRules/GetLeShiftRules")
	public PageBean getLeShiftRules(@RequestBody LeShiftRule simple){
		if(null == simple){
			logger.info("查询条件不能为空!");
			throw new NullPointerException();
		}else{
			return leshiftrulesservice.getLeShiftRules(simple);
		}
	}
	
	/**
	 * 检查重复规则
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="api/LeShiftRules/DuplicateCheck")
	public JSONObject duplicateCheck(@RequestBody LeShiftRule rule){
		JSONObject result = new JSONObject();
		result.put("status", Retcode.OK.code);
		result.put("message", Retcode.OK.msg);
		result.put("exists", leshiftrulesservice.duplicateCheck(rule));
		return result;
	}
	
	/**
	 * 添加交接班规则
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="api/LeShiftRules/AddRules")
	public JSONObject addRules(@RequestBody LeShiftRule rule){
		JSONObject result = new JSONObject();
		if(leshiftrulesservice.addRules(rule)){
			result.put("status", Retcode.OK.code);
			result.put("message", Retcode.OK.msg);
		}else{
			result.put("status", Retcode.SAMECITY.code);
			result.put("message", Retcode.SAMECITY.msg);
		}
		return result;
	}
	
	/**
	 * 修改交接班规则
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="api/LeShiftRules/ModifyRules")
	public JSONObject modifyRules(@RequestBody LeShiftRule rule){
		JSONObject result = new JSONObject();
		if(leshiftrulesservice.modifyRules(rule)){
			result.put("status", Retcode.OK.code);
			result.put("message", Retcode.OK.msg);
		}else{
			result.put("status", Retcode.FAILED.code);
			result.put("message", Retcode.FAILED.msg);
		}
		return result;
	}
}
