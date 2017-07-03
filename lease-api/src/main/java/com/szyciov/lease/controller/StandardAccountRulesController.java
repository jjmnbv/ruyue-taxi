package com.szyciov.lease.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.lease.entity.LeAccountRules;
import com.szyciov.lease.entity.LeVehiclemodels;
import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.lease.param.LeAccountRulesModiLogQueryParam;
import com.szyciov.lease.param.LeAccountRulesQueryParam;
import com.szyciov.lease.service.StandardAccountRulesService;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;

import net.sf.json.JSONObject;

/**
 * 标准计费规则模块控制器
 */
@Controller
public class StandardAccountRulesController extends BaseController {
	private static final Logger logger = Logger.getLogger(StandardAccountRulesController.class);

	public StandardAccountRulesService standardAccountRulesService;

	@Resource(name = "standardAccountRulesService")
	public void setStandardAccountRulesService(StandardAccountRulesService standardAccountRulesService) {
		this.standardAccountRulesService = standardAccountRulesService;
	}

	/** 
	 * <p>分页查询标准计费规则信息</p>
	 *
	 * @param queryParam 查询请求对象，封装需要查询的key和页码等信息
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "api/StandardAccountRules/GetStandardAccountRulesByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getStandardAccountRulesByQuery(@RequestBody LeAccountRulesQueryParam queryParam) {
		return standardAccountRulesService.getStandardAccountRulesByQuery(queryParam);
	}
	
	/** 
	 * <p>新增标准计费规则信息</p>
	 *
	 * @param leAccountRules
	 * @return
	 */
	@RequestMapping(value = "api/StandardAccountRules/CreateStandardAccountRules", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> createStandardAccountRules(@RequestBody LeAccountRules leAccountRules) {
		return standardAccountRulesService.createStandardAccountRules(leAccountRules);
	}
	
	/** 
	 * <p>修改标准计费规则信息</p>
	 *
	 * @param leAccountRules
	 * @return
	 */
	@RequestMapping(value = "api/StandardAccountRules/UpdateStandardAccountRules", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updateStandardAccountRules(@RequestBody LeAccountRules leAccountRules) {
		return standardAccountRulesService.updateStandardAccountRules(leAccountRules);
	}
	
	/** 
	 * <p>启用或禁用标准计费规则</p>
	 *
	 * @param leAccountRules
	 * @return
	 */
	@RequestMapping(value = "api/StandardAccountRules/UpdateStandardAccountRulesState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updateStandardAccountRulesState(@RequestBody LeAccountRules leAccountRules, @RequestParam(value = "modiType", required = true) String modiType) {
		logger.log(Level.INFO, "modiType:" + modiType);
		return standardAccountRulesService.updateStandardAccountRulesState(leAccountRules, modiType);
	}
	
	/** 
	 * <p>一键更换</p>
	 *
	 * @param leAccountRules
	 * @return
	 */
	@RequestMapping(value = "api/StandardAccountRules/UpdateStandardAccountRulesOneKey", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updateStandardAccountRulesOneKey(@RequestBody LeAccountRules leAccountRules) {
		return standardAccountRulesService.updateStandardAccountRulesOneKey(leAccountRules);
	}
	
	/** 
	 * <p>分页查询标准计费规则历史信息</p>
	 *
	 * @param queryParam 查询请求对象，封装需要查询的key和页码等信息
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "api/StandardAccountRules/GetStandardAccountRulesModiLogByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getStandardAccountRulesModiLogByQuery(@RequestBody LeAccountRulesModiLogQueryParam queryParam) {
		return standardAccountRulesService.getStandardAccountRulesModiLogByQuery(queryParam);
	}
	
	/** 
	 * <p>查询存在城市list</p>
	 *
	 * @param leasesCompanyId 所属租赁公司
	 * @return 
	 */
	@RequestMapping(value = "api/StandardAccountRules/GetExistCityList", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getStandardAccountRulesExistCityList(
			@RequestParam(value = "leasesCompanyId", required = true) String leasesCompanyId,
			@RequestParam(value = "cityName", required = false) String cityName) {
		logger.log(Level.INFO, "api getStandardAccountRulesExistCityList leasesCompanyId:" + leasesCompanyId);
		logger.log(Level.INFO, "api getStandardAccountRulesExistCityList cityName:" + cityName);
		return standardAccountRulesService.getStandardAccountRulesExistCityList(leasesCompanyId, cityName);
	}
	
	/** 
	 * <p>查询字典数据</p>
	 *
	 * @param type 类型
	 * @return 
	 */
	@RequestMapping(value = "api/StandardAccountRules/GetPubDictionaryByType/{type}", method = RequestMethod.GET)
	@ResponseBody
	public List<PubDictionary> getPubDictionaryByType(@PathVariable String type) {
		logger.log(Level.INFO, "api getPubDictionaryByType type:" + type);
		return standardAccountRulesService.getPubDictionaryByType(type);
	}
	
	/** 
	 * <p>查询服务车型list</p>
	 *
	 * @param leasesCompanyId 所属租赁公司
	 * @return 
	 */
	@RequestMapping(value = "api/StandardAccountRules/GetExistCarTypeList", method = RequestMethod.GET)
	@ResponseBody
	public List<LeVehiclemodels> getStandardAccountRulesExistCarTypeList(@RequestParam(value = "leasesCompanyId", required = true) String leasesCompanyId) {
		return standardAccountRulesService.getStandardAccountRulesExistCarTypeList(leasesCompanyId);
	}
	
	/** 
	 * <p>查询服务车型功能里面的数据</p>
	 *
	 * @param leasesCompanyId 所属租赁公司
	 * @return 
	 */
	@RequestMapping(value = "api/StandardAccountRules/GetCarTypeList", method = RequestMethod.GET)
	@ResponseBody
	public List<LeVehiclemodels> getCarTypeList(@RequestParam(value = "leasesCompanyId", required = true) String leasesCompanyId) {
		return standardAccountRulesService.getCarTypeList(leasesCompanyId);
	}
	
	/** 
	 * <p>查询租赁计费规则</p>
	 *
	 * @param id id
	 * @return 
	 */
	@RequestMapping(value = "api/StandardAccountRules/GetStandardAccountRulesById/{id}", method = RequestMethod.GET)
	@ResponseBody
	public LeAccountRules getStandardAccountRulesById(@PathVariable String id) {
		return standardAccountRulesService.getStandardAccountRulesById(id);
	}
	
	/** 
	 * <p>根据城市查询时间类型</p>
	 *
	 * @param leasesCompanyId 所属租赁公司
	 * @param city 城市
	 * @return 
	 */
	@RequestMapping(value = "api/StandardAccountRules/GetStandardAccountRulesByCity", method = RequestMethod.GET)
	@ResponseBody
	public List<LeAccountRules> getStandardAccountRulesByCity(
			@RequestParam(value = "leasesCompanyId", required = true) String leasesCompanyId,
			@RequestParam(value = "city", required = true) String city) {
		logger.log(Level.INFO, "api getStandardAccountRulesByCity leasesCompanyId:" + leasesCompanyId);
		logger.log(Level.INFO, "api getStandardAccountRulesByCity city:" + city);
		return standardAccountRulesService.getStandardAccountRulesByCity(leasesCompanyId, city);
	}
	
	/** 
	 * <p>根据城市查询时间类型</p>
	 *
	 * @param leasesCompanyId 所属租赁公司
	 * @param city 城市
	 * @return 
	 */
	@RequestMapping(value = "api/StandardAccountRules/GetOneRuleByCity", method = RequestMethod.GET)
	@ResponseBody
	public LeAccountRules getOneStandardAccountRulesByCity(
			@RequestParam(value = "leasesCompanyId", required = true) String leasesCompanyId,
			@RequestParam(value = "city", required = true) String city) {
		logger.log(Level.INFO, "api getOneStandardAccountRulesByCity leasesCompanyId:" + leasesCompanyId);
		logger.log(Level.INFO, "api getOneStandardAccountRulesByCity city:" + city);
		return standardAccountRulesService.getOneStandardAccountRulesByCity(leasesCompanyId, city);
	}
	
	/**
	 * 查询所有城市数据
	 * @return
	 */
	@RequestMapping(value = "api/StandardAccountRules/GetPubCityAddrList", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getPubCityAddrList() {
		return standardAccountRulesService.getPubCityAddrList();
	}

}
