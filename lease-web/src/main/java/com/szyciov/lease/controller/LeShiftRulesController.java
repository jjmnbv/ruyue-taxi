package com.szyciov.lease.controller;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.entity.Retcode;
import com.szyciov.entity.Select2Entity;
import com.szyciov.lease.entity.LeShiftRule;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.entity.User;
import com.szyciov.lease.service.LeShiftRulesService;
import com.szyciov.param.Select2Param;
import com.szyciov.util.PageBean;
import com.szyciov.util.WebExceptionHandle;

import net.sf.json.JSONObject;



/**
  * @ClassName LeShiftRulesController
  * @author Efy
  * @Description 交接班规则Controller
  * @date 2017年3月3日 10:47:03
  */ 
@Controller("LeShiftRulesController")
public class LeShiftRulesController extends WebExceptionHandle{

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
	
	@RequestMapping(value="LeShiftRules/Index")
	public ModelAndView index(HttpServletRequest request){
		String page = "resource/shiftRules/index";
		return new ModelAndView(page);
	}
	
	/**
	 * select2城市控件
	 * @param param
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="LeShiftRules/GetCityListForSelect")
	public JSONObject getCityListForSelect(@RequestBody Select2Param param,HttpServletRequest request){
		JSONObject result = new JSONObject();
		result.put("status", Retcode.OK.code);
		result.put("message", Retcode.OK.msg);
		param.setToken(getUserToken(request));
		param.setCompanyid(getLoginLeUser(request).getLeasescompanyid());
		List<Select2Entity> list = leshiftrulesservice.getCityListForSelect(param);
		result.put("list", list);
		return result;
	}
	
	/**
	 * 获取城市列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="LeShiftRules/GetCityList")
	public List<PubCityAddr> getCityList(HttpServletRequest request){
		List<PubCityAddr> list = leshiftrulesservice.getCityList(getUserToken(request));
		return list;
	}
	

	/**
	 * 获取规则列表
	 * @param rule
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="LeShiftRules/GetRules")
	public PageBean getRules(@RequestBody LeShiftRule rule,HttpServletRequest request){
		User user = getLoginLeUser(request);
		rule.setToken(getUserToken(request));
		rule.setLeasescompanyid(user.getLeasescompanyid());
		PageBean result = leshiftrulesservice.getRules(rule);
		return result;
	}
	
	
	/**
	 * 检查重复规则
	 * @param rule
	 * @param request
	 * @return
	 * <p>status - int</p>
	 * <p>message - String  </p>
	 * <p>exists - boolean 表示是否存在重复规则</p>
	 */
	@ResponseBody
	@RequestMapping(value="api/LeShiftRules/DuplicateCheck")
	public JSONObject duplicateCheck(@RequestBody LeShiftRule rule,HttpServletRequest request){
		User user = getLoginLeUser(request);
		rule.setToken(getUserToken(request));
		rule.setLeasescompanyid(user.getLeasescompanyid());
		rule.setCreater(user.getId());
		rule.setUpdater(user.getId());
		JSONObject result = leshiftrulesservice.duplicateCheck(rule);
		return result;
	}	
	
	/**
	 * 添加交接班规则
	 * @return 
	 * @see {@linkplain RetCode.SAMECITY}
	 */
	@ResponseBody
	@RequestMapping(value="LeShiftRules/AddRules")
	public JSONObject addRules(@RequestBody LeShiftRule rule,HttpServletRequest request){
		User user = getLoginLeUser(request);
		rule.setToken(getUserToken(request));
		rule.setLeasescompanyid(user.getLeasescompanyid());
		rule.setCreater(user.getId());
		rule.setUpdater(user.getId());
		JSONObject result = leshiftrulesservice.insert(rule);
		return result;
	}
	
	/**
	 * 修改交接班规则
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="LeShiftRules/ModifyRules")
	public JSONObject modifyRules(@RequestBody LeShiftRule rule,HttpServletRequest request){
		User user = getLoginLeUser(request);
		rule.setToken(getUserToken(request));
		rule.setLeasescompanyid(user.getLeasescompanyid());
		rule.setUpdater(user.getId());
		JSONObject result = leshiftrulesservice.update(rule);
		return result;
	}
}
