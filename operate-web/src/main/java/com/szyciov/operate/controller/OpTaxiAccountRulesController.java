package com.szyciov.operate.controller;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.entity.Retcode;
import com.szyciov.entity.Select2Entity;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.op.entity.OpTaxiAccountRule;
import com.szyciov.op.entity.OpUser;
import com.szyciov.operate.service.OpTaxiAccountRulesService;
import com.szyciov.param.Select2Param;
import com.szyciov.util.PageBean;
import com.szyciov.util.WebExceptionHandle;

import net.sf.json.JSONObject;



/**
  * @ClassName OpTaxiAccountRulesController
  * @author Efy Shu
  * @Description 出租车计费规则Controller
  * @date 2017年3月9日 11:43:09
  */ 
@Controller("OpTaxiAccountRulesController")
public class OpTaxiAccountRulesController extends WebExceptionHandle{

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
	 * 出租车计费规则首页
	 * @return
	 */
	@RequestMapping(value="OpTaxiAccountRules/Index")
	public ModelAndView index(HttpServletRequest request){
		OpUser user = getLoginOpUser(request);
		String page = "resource/opTaxiAccountRules/index";
		ModelAndView model = new ModelAndView();
		model.setViewName(page);
		model.addObject("usertype", user.getUsertype());
		return model;
	}
	
	/**
	 * 出租车计费规则操作历史记录页
	 * @return
	 */
	@RequestMapping(value="OpTaxiAccountRules/History")
	public ModelAndView historyPage(
			@RequestParam(value = "id") String id,
			@RequestParam(value = "cityname") String cityname
		){
		String page = "resource/opTaxiAccountRules/history";
		ModelAndView model = new ModelAndView();
		model.setViewName(page);
		model.addObject("id", id);
		model.addObject("cityname", cityname);
		return model;
		
	}
	
	/**
	 * 出租车计费规则编辑页
	 * @return
	 */
	@RequestMapping(value="OpTaxiAccountRules/Add")
	public ModelAndView addPage(HttpServletRequest request){
		return editPage(null,request);
	}
	
	/**
	 * 出租车计费规则编辑页
	 * @return
	 */
	@RequestMapping(value="OpTaxiAccountRules/Edit")
	public ModelAndView editPage(@RequestParam String id,HttpServletRequest request){
		String page = "resource/opTaxiAccountRules/edit";
		ModelAndView model = new ModelAndView();
		model.setViewName(page);
		if(id != null && !id.trim().isEmpty()){
			OpTaxiAccountRule rule = new OpTaxiAccountRule();
			rule.setId(id);
			rule = optaxiaccountrulesservice.searchById(rule);
			model.addObject("rule", rule);
		}
		return model;
	}
	
	/**
	 * select2城市控件
	 * @param param
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="OpTaxiAccountRules/GetCityListForSelect")
	public JSONObject getCityListForSelect(@RequestBody Select2Param param,HttpServletRequest request){
		JSONObject result = new JSONObject();
		result.put("status", Retcode.OK.code);
		result.put("message", Retcode.OK.msg);
		param.setToken(getUserToken(request));
		List<Select2Entity> list = optaxiaccountrulesservice.getCityListForSelect(param);
		result.put("list", list);
		return result;
	}
	
	/**
	 * 获取城市列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="OpTaxiAccountRules/GetCityList")
	public List<PubCityAddr> getCityList(HttpServletRequest request){
		List<PubCityAddr> list = optaxiaccountrulesservice.getCityList(getUserToken(request));
		return list;
	}
	
	/**
	 * 条件查询规则列表
	 * @param rule
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="OpTaxiAccountRules/Search",method=RequestMethod.POST)
	public PageBean search(@RequestBody OpTaxiAccountRule rule,HttpServletRequest request){
		String token = getUserToken(request);
		rule.setToken(token);
		return optaxiaccountrulesservice.search(rule);
	}
	
	/**
	 * 新增出租车计费规则
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="OpTaxiAccountRules/AddRule",method=RequestMethod.POST)
	public JSONObject addRule(@RequestBody OpTaxiAccountRule rule,HttpServletRequest request){
		String token = getUserToken(request);
		OpUser user = getLoginOpUser(request);
		rule.setToken(token);
		rule.setCreater(user.getId());
		rule.setUpdater(user.getId());
		return optaxiaccountrulesservice.save(rule);
	}
	
	/**
	 * 修改出租车计费规则
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="OpTaxiAccountRules/EditRule",method=RequestMethod.POST)
	public JSONObject editRule(@RequestBody OpTaxiAccountRule rule,HttpServletRequest request){
		String token = getUserToken(request);
		OpUser user = getLoginOpUser(request);
		rule.setToken(token);
		rule.setUpdater(user.getId());
		return optaxiaccountrulesservice.edit(rule);
	}
	
	/**
	 * 禁用规则
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="OpTaxiAccountRules/DisableRule",method=RequestMethod.POST)
	public JSONObject disableRule(@RequestBody OpTaxiAccountRule rule,HttpServletRequest request){
		String token = getUserToken(request);
		OpUser user = getLoginOpUser(request);
		rule.setToken(token);
		rule.setUpdater(user.getId());
		return optaxiaccountrulesservice.disableRule(rule);
	}
	
	/**
	 * 启用规则
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="OpTaxiAccountRules/EnableRule",method=RequestMethod.POST)
	public JSONObject enableRule(@RequestBody OpTaxiAccountRule rule,HttpServletRequest request){
		String token = getUserToken(request);
		OpUser user = getLoginOpUser(request);
		rule.setToken(token);
		rule.setUpdater(user.getId());
		return optaxiaccountrulesservice.enableRule(rule);
	}
	
	/**
	 * 查询操作历史
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="OpTaxiAccountRules/SearchHistory",method=RequestMethod.POST)
	public PageBean searchHistory(@RequestBody OpTaxiAccountRule rule,HttpServletRequest request){
		String token = getUserToken(request);
		OpUser user = getLoginOpUser(request);
		rule.setToken(token);
		rule.setUpdater(user.getId());
		return optaxiaccountrulesservice.searchHistory(rule);
	}
}
