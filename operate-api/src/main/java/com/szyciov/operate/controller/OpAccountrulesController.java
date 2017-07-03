package com.szyciov.operate.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.op.entity.OpAccountrules;
import com.szyciov.op.entity.OpVehiclemodels;
import com.szyciov.op.param.OpAccountruleQueryParam;
import com.szyciov.op.param.OpAccountrulesModilogQueryParam;
import com.szyciov.operate.service.OpAccountrulesService;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;

/**
 * 计费规则管理
 */
@Controller
public class OpAccountrulesController extends BaseController {
	
	private OpAccountrulesService accountrulesService;
	@Resource(name = "OpAccountrulesService")
	public void setAccountrulesService(OpAccountrulesService accountrulesService) {
		this.accountrulesService = accountrulesService;
	}
	
	/**
	 * 查询计费规则中的所有服务车型
	 * @return
	 */
	@RequestMapping(value = "api/OpAccountrules/GetModelsByList", method = RequestMethod.POST)
	@ResponseBody
	public List<OpVehiclemodels> getModelsByList() {
		return accountrulesService.getModelsByList();
	}
	
	/**
	 * 查询计费规则中的所有城市
	 * @param cityName
	 * @return
	 */
	@RequestMapping(value = "api/OpAccountrules/GetCityByList", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, String>> getCityByList(@RequestBody PubCityAddr object) {
		return accountrulesService.getCityByList(object);
	}

	/**
	 * 分页查询计费规则
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "api/OpAccountrules/GetOpAccountRulesByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getOpAccountRulesByQuery(@RequestBody OpAccountruleQueryParam queryParam) {
		return accountrulesService.getOpAccountRulesByQuery(queryParam);
	}
	
	/**
	 * 根据主键查询计费规则详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "api/OpAccountrules/GetOpAccountrulesById/{id}", method = RequestMethod.GET)
	@ResponseBody
	public OpAccountrules getOpAccountrulesById(@PathVariable String id) {
		return accountrulesService.getOpAccountrulesById(id);
	}
	
	/**
	 * 添加计费规则
	 * @param object
	 * @return
	 */
	@RequestMapping(value = "api/OpAccountrules/CreateOpAccountRules", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> createOpAccountRules(@RequestBody OpAccountrules object) {
		return accountrulesService.createOpAccountRules(object);
	}
	
	/**
	 * 修改计费规则
	 * @param object
	 * @return
	 */
	@RequestMapping(value = "api/OpAccountrules/EditOpAccountRules", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> editOpAccountRules(@RequestBody OpAccountrules object) {
		return accountrulesService.editOpAccountRules(object);
	}
	
	/**
	 * 修改计费规则状态
	 * @param object
	 * @return
	 */
	@RequestMapping(value = "api/OpAccountrules/EditOpAccountRulesState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> editOpAccountRulesState(@RequestBody OpAccountrules object) {
		return accountrulesService.editOpAccountRulesState(object);
	}
	
	/**
	 * 分页查询计费规则历史数据
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "api/OpAccountrules/GetOpAccountRulesModiLogByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getOpAccountRulesModiLogByQuery(@RequestBody OpAccountrulesModilogQueryParam queryParam) {
		return accountrulesService.getOpAccountRulesModiLogByQuery(queryParam);
	}
	
	/**
	 * 条件查询计费规则列表
	 * @param object
	 * @return
	 */
	@RequestMapping(value = "api/OpAccountrules/GetOpAccountrulesByList", method = RequestMethod.POST)
	public List<OpAccountrules> getOpAccountrulesByList(@RequestBody OpAccountrules object) {
		return accountrulesService.getOpAccountrulesByList(object);
	}
	
	/**
	 * 一键更换时间补贴类型
	 * @param object
	 * @return
	 */
	@RequestMapping(value = "api/OpAccountrules/EditOpAccountRulesByCity", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> editOpAccountRulesByCity(@RequestBody OpAccountrules object) {
		return accountrulesService.editOpAccountRulesByCity(object);
	}
	
	/**
	 * 根据城市查询计费规则
	 * @param object
	 * @return
	 */
	@RequestMapping(value = "api/OpAccountrules/GetOpAccountRulesByCity", method = RequestMethod.POST)
	@ResponseBody
	public OpAccountrules getOpAccountRulesByCity(@RequestBody OpAccountrules object) {
		List<OpAccountrules> accountrulesList = accountrulesService.getOpAccountrulesByList(object);
		if(null != accountrulesList && !accountrulesList.isEmpty()) {
			return accountrulesList.get(0);
		}
		return null;
	}
	
}
