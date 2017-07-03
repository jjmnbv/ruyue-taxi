package com.szyciov.operate.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.op.entity.OpAccountrules;
import com.szyciov.op.entity.OpUser;
import com.szyciov.op.entity.OpVehiclemodels;
import com.szyciov.op.param.OpAccountruleQueryParam;
import com.szyciov.op.param.OpAccountrulesModilogQueryParam;
import com.szyciov.operate.service.OpAccountrulesService;
import com.szyciov.operate.service.OpVehiclemodelsService;
import com.szyciov.operate.service.PubDictionaryService;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.PageBean;

import net.sf.json.JSONObject;

/**
 * 计费规则管理
 */
@Controller
public class OpAccountrulesController extends BaseController {

	private OpAccountrulesService accountrulesService;

	@Resource(name = "OpAccountrulesService")
	public void setService(OpAccountrulesService accountrulesService) {
		this.accountrulesService = accountrulesService;
	}

	private PubDictionaryService dictionaryService;

	@Resource(name = "PubDictionaryService")
	public void setDictionaryService(PubDictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

	private OpVehiclemodelsService vehiclemodelsService;

	@Resource(name = "OpVehiclemodelsService")
	public void setVehiclemodelsService(OpVehiclemodelsService vehiclemodelsService) {
		this.vehiclemodelsService = vehiclemodelsService;
	}

	/**
	 * 跳转到计费规则主页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "OpAccountrules/Index")
	@ResponseBody
	public ModelAndView getPoAccountrulesIndex(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		// 获取用户数据
		OpUser user = getLoginOpUser(request);
		// 从字典表中获取规则类型
		List<PubDictionary> ruleTypeList = dictionaryService.getPubDictionary("规则类型", userToken);
		// 从字典表中获取规则状态
		List<PubDictionary> ruleStatusList = dictionaryService.getPubDictionary("规则状态", userToken);
		// 查询计费规则中已存在的城市
		PubCityAddr cityAddr = new PubCityAddr();
		List<Map<String, String>> cityList = accountrulesService.getCityByList(cityAddr, userToken);
		// 查询计费规则中已存在的服务车型
		OpVehiclemodels vehiclemodels = new OpVehiclemodels();
		vehiclemodels.setStatus(1);
		List<OpVehiclemodels> vehiclemodelList = accountrulesService.getModelsByList(userToken);

		ModelAndView view = new ModelAndView();
		view.addObject("usertype", user.getUsertype());
		view.addObject("ruleTypeList", ruleTypeList);
		view.addObject("ruleStatusList", ruleStatusList);
		view.addObject("cityList", cityList);
		view.addObject("vehiclemodelList", vehiclemodelList);
		view.setViewName("resource/opAccountRules/index");
		return view;
	}

	/**
	 * 查询计费规则中已存在的城市
	 * 
	 * @param cityName
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "OpAccountrules/GetCityByList")
	@ResponseBody
	public List<Map<String, String>> getCityByList(@RequestParam String cityName, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("application/json; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		PubCityAddr cityAddr = new PubCityAddr();
		cityAddr.setCity(cityName);
		return accountrulesService.getCityByList(cityAddr, userToken);
	}

	/**
	 * 分页查询计费规则
	 * 
	 * @param queryParam
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "OpAccountrules/GetOpAccountRulesByQuery")
	@ResponseBody
	public PageBean getOpAccountRulesByQuery(@RequestBody OpAccountruleQueryParam queryParam,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return accountrulesService.getOpAccountRulesByQuery(queryParam, userToken);
	}

	/**
	 * 跳转到计费规则详情页面
	 * 
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "OpAccountrules/EditAccountRulesPage")
	public ModelAndView editAccountRulesPage(@RequestParam(value = "id", required = false) String id,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = new ModelAndView();
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		String companyId = null;
		// 从字典表中获取规则类型
		List<PubDictionary> ruleTypeList = dictionaryService.getPubDictionary("规则类型", userToken);
		// 获取所有服务车型
		OpVehiclemodels vehiclemodels = new OpVehiclemodels();
		vehiclemodels.setLeasescompanyid(companyId);
		vehiclemodels.setStatus(1);
		vehiclemodels.setModelstatus("0");
		List<OpVehiclemodels> vehiclemodelsList = vehiclemodelsService.getOpVehiclemodelsByList(vehiclemodels,
				userToken);
		// 查询计费规则详情
		OpAccountrules opAccountrules = null;
		if (!StringUtils.isBlank(id)) {
			opAccountrules = new OpAccountrules();
			opAccountrules.setId(id);
			opAccountrules = accountrulesService.getOpAccountrulesById(id, userToken);
		}
		view.addObject("opAccountrules", opAccountrules);
		view.addObject("ruleTypeList", ruleTypeList);
		view.addObject("vehiclemodelsList", vehiclemodelsList);
		view.setViewName("resource/opAccountRules/editAccountRules");
		return view;
	}

	/**
	 * 添加计费规则
	 * 
	 * @param object
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "OpAccountrules/CreateOpAccountRules")
	@ResponseBody
	public Map<String, String> createOpAccountRules(@RequestBody OpAccountrules object, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("application/json; charsetutf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OpUser user = getLoginOpUser(request);
		if(!"1".equals(user.getUsertype())) {
			Map<String, String> ret = new HashMap<String, String>();
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "您没有执行该操作的权限");
			return ret;
		}
		object.setCreater(user.getId());
		object.setUpdater(user.getId());
		return accountrulesService.createOpAccountRules(object, userToken);
	}

	/**
	 * 修改计费规则
	 * 
	 * @param object
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "OpAccountrules/EditOpAccountRules")
	@ResponseBody
	public Map<String, String> editOpAccountRules(@RequestBody OpAccountrules object, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("application/json; charsetutf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OpUser user = getLoginOpUser(request);
		if(!"1".equals(user.getUsertype())) {
			Map<String, String> ret = new HashMap<String, String>();
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "您没有执行该操作的权限");
			return ret;
		}
		object.setUpdater(user.getId());
		return accountrulesService.editOpAccountRules(object, userToken);
	}

	/**
	 * 修改计费规则状态
	 * 
	 * @param object
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "OpAccountrules/EditOpAccountRulesState")
	@ResponseBody
	public Map<String, String> editOpAccountRulesState(@RequestBody OpAccountrules object, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("application/json; charsetutf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OpUser user = getLoginOpUser(request);
		if(!"1".equals(user.getUsertype())) {
			Map<String, String> ret = new HashMap<String, String>();
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "您没有执行该操作的权限");
			return ret;
		}
		object.setUpdater(user.getId());
		return accountrulesService.editOpAccountRulesState(object, userToken);
	}

	/**
	 * 跳转到计费规则历史操作记录页面
	 * 
	 * @param id
	 * @param cityName
	 * @param rulestypeName
	 * @param vehiclemodelsName
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "OpAccountrules/SearchHistory")
	@ResponseBody
	public ModelAndView searchHistory(@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "cityName", required = true) String cityName,
			@RequestParam(value = "rulestypeName", required = true) String rulestypeName,
			@RequestParam(value = "vehiclemodelsName", required = true) String vehiclemodelsName,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = new ModelAndView();
		view.setViewName("resource/opAccountRules/history");
		view.addObject("accountrulesid", id);
		view.addObject("cityName", cityName);
		view.addObject("rulestypeName", rulestypeName);
		view.addObject("vehiclemodelsName", vehiclemodelsName);
		return view;
	}
	
	/**
	 * 分页查询计费规则历史数据
	 * @param accountrulesid
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "OpAccountrules/GetOpAccountRulesModiLogByQuery")
	@ResponseBody
	public PageBean getOpAccountRulesModiLogByQuery(
			@RequestBody OpAccountrulesModilogQueryParam queryParam,
			@RequestParam(value = "accountrulesid", required = true) String accountrulesid, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		queryParam.setAccountrulesid(accountrulesid);
		return accountrulesService.getOpAccountRulesModiLogByQuery(queryParam, userToken);
	}
	
	/**
	 * 根据城市查询时间补贴类型
	 * @param object
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "OpAccountrules/GetOpAccountRulesByCity")
	@ResponseBody
	public OpAccountrules getOpAccountRulesByCity(@RequestBody OpAccountrules object, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return accountrulesService.getOpAccountRulesByCity(object, userToken);
	}
	
	/**
	 * 一键更换时间补贴类型
	 * @param object
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "OpAccountrules/EditOpAccountRulesByCity")
	@ResponseBody
	public Map<String, String> editOpAccountRulesByCity(@RequestBody OpAccountrules object, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OpUser user = getLoginOpUser(request);
		if(!"1".equals(user.getUsertype())) {
			Map<String, String> ret = new HashMap<String, String>();
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "您没有执行该操作的权限");
			return ret;
		}
		object.setUpdater(user.getId());
		return accountrulesService.editOpAccountRulesByCity(object, userToken);
	}
	
	/**
	 * 查询所有城市按照字母分类
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "OpAccountrules/GetPubCityAddrByList")
	@ResponseBody
	public JSONObject getPubCityAddrByList(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return accountrulesService.getPubCityAddrByList(userToken);
	}

	/**
	 * 查询所有城市按照字母分类
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "OpAccountrules/OpAccountRulesHistory")
	public ModelAndView opAccountRulesHistory(){
		String page = "";
		ModelAndView view = new ModelAndView();
		view.setViewName(page);
		return view;
	}
}
