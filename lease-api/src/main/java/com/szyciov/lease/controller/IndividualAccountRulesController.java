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
import com.szyciov.lease.entity.LeCompanyRulesRef;
import com.szyciov.lease.entity.OrgOrgan;
import com.szyciov.lease.param.IndividualAccountRulesQueryParam;
import com.szyciov.lease.service.IndividualAccountRulesService;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;

/**
 * 个性化计费规则模块控制器
 */
@Controller
public class IndividualAccountRulesController extends BaseController {
	private static final Logger logger = Logger.getLogger(IndividualAccountRulesController.class);

	public IndividualAccountRulesService individualAccountRulesService;

	@Resource(name = "individualAccountRulesService")
	public void setIndividualAccountRulesService(IndividualAccountRulesService individualAccountRulesService) {
		this.individualAccountRulesService = individualAccountRulesService;
	}
	
	/** 
	 * <p>分页查询个性化计费规则信息</p>
	 *
	 * @param queryParam 查询请求对象，封装需要查询的key和页码等信息
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "api/IndividualAccountRules/GetIndividualAccountRulesByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getIndividualAccountRulesByQuery(@RequestBody IndividualAccountRulesQueryParam queryParam) {
		return individualAccountRulesService.getIndividualAccountRulesByQuery(queryParam);
	}
	
	/** 
	 * <p>个性化计费规则   首页  机构名称</p>
	 *
	 * @param leasesCompanyId 所属租赁公司
	 * @return
	 */
	@RequestMapping(value = "api/IndividualAccountRules/GetOrganList", method = RequestMethod.GET)
	@ResponseBody
	public List<OrgOrgan> getOrganList(@RequestParam(value = "leasesCompanyId", required = true) String leasesCompanyId,
			@RequestParam(value = "specialState", required = true) String specialState,
			@RequestParam(value = "account", required = true) String account) {
		logger.log(Level.INFO, "api getOrganList leasesCompanyId:" + leasesCompanyId);
		logger.log(Level.INFO, "api getOrganList specialState:" + specialState);
		logger.log(Level.INFO, "api getOrganList account:" + account);
		return individualAccountRulesService.getOrganList(leasesCompanyId, specialState, account);
	}
	
	/** 
	 * <p>禁用个性化计费规则</p>
	 *
	 * @param leCompanyRulesRef
	 * @return
	 */
	@RequestMapping(value = "api/IndividualAccountRules/DisableIndividualAccountRules", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> disableIndividualAccountRules(@RequestBody LeCompanyRulesRef leCompanyRulesRef) {
		return individualAccountRulesService.disableIndividualAccountRules(leCompanyRulesRef);
	}
	
	/** 
	 * <p>新增计费规则  机构名称</p>
	 *
	 * @param leasesCompanyId 所属租赁公司
	 * @return
	 */
	@RequestMapping(value = "api/IndividualAccountRules/GetInsertOrganList", method = RequestMethod.GET)
	@ResponseBody
	public List<OrgOrgan> getInsertOrganList(
			@RequestParam(value = "leasesCompanyId", required = true) String leasesCompanyId,
			@RequestParam(value = "specialState", required = true) String specialState,
			@RequestParam(value = "account", required = true) String account) {
		logger.log(Level.INFO, "api getInsertOrganList leasesCompanyId:" + leasesCompanyId);
		logger.log(Level.INFO, "api getInsertOrganList specialState:" + specialState);
		logger.log(Level.INFO, "api getInsertOrganList account:" + account);
		return individualAccountRulesService.getInsertOrganList(leasesCompanyId, specialState, account);
	}
	
	/** 
	 * <p>新增计费规则  城市名称</p>
	 *
	 * @param leasesCompanyId 所属租赁公司
	 * @return
	 */
	@RequestMapping(value = "api/IndividualAccountRules/GetInsertCityList", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getInsertCityList(
			@RequestParam(value = "leasesCompanyId", required = true) String leasesCompanyId,
			@RequestParam(value = "cityName", required = false) String cityName) {
		logger.log(Level.INFO, "api getInsertCityList leasesCompanyId:" + leasesCompanyId);
		logger.log(Level.INFO, "api getInsertCityList cityName:" + cityName);
		return individualAccountRulesService.getInsertCityList(leasesCompanyId, cityName);
	}
	
	/** 
	 * <p>分页查询个性化计费规则详情</p>
	 *
	 * @param queryParam 查询请求对象，封装需要查询的key和页码等信息
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "api/IndividualAccountRules/GetIndividualAccountRulesByOrgan", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getIndividualAccountRulesByOrgan(@RequestBody IndividualAccountRulesQueryParam queryParam) {
		return individualAccountRulesService.getIndividualAccountRulesByOrgan(queryParam);
	}
	
	/** 
	 * <p>更新一条记录</p>
	 *
	 * @param leAccountRules
	 * @return
	 */
	@RequestMapping(value = "api/IndividualAccountRules/UpdateIndividualAccountRules", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updateIndividualAccountRules(@RequestBody LeAccountRules leAccountRules) {
		return individualAccountRulesService.updateIndividualAccountRules(leAccountRules);
	}
	
	/** 
	 * <p>删除一条记录</p>
	 *
	 * @param id 待删除记录对应的序列号
	 */
	@RequestMapping(value = "api/IndividualAccountRules/DeleteIndividualAccountRules/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Map<String, String> deleteIndividualAccountRules(@PathVariable String id,
			@RequestParam(value = "leasesCompanyId", required = true) String leasesCompanyId) {
		logger.log(Level.INFO, "deleteIndividualAccountRules id:" + id);
		logger.log(Level.INFO, "deleteIndividualAccountRules leasesCompanyId:" + leasesCompanyId);
		return individualAccountRulesService.deleteIndividualAccountRules(id, leasesCompanyId);
	}
	
	/** 
	 * <p>查询机构当前是否存在已启用的规则及有效期</p>
	 *
	 * @param organId 机构Id
	 * @param leasesCompanyId 所属租赁公司
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "api/IndividualAccountRules/GetRulesDateByOrgan/{organId}", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String,Object>> getRulesDateByOrgan(@PathVariable String organId, @RequestParam(value = "leasesCompanyId", required = true) String leasesCompanyId) {
		logger.log(Level.INFO, "organId:" + organId);
		logger.log(Level.INFO, "leasesCompanyId:" +leasesCompanyId);
		return individualAccountRulesService.getRulesDateByOrgan(organId, leasesCompanyId);
	}
	
	/** 
	 * <p>根据城市查询它的产品服务</p>
	 *
	 * @param city 城市
	 * @param leasesCompanyId 所属租赁公司
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "api/IndividualAccountRules/GetStandardRulesByCity/{city}", method = RequestMethod.GET)
	@ResponseBody
	public List<LeAccountRules> getIndividualAccountRulesListByCity(@PathVariable String city, @RequestParam(value = "leasesCompanyId", required = true) String leasesCompanyId) {
		logger.log(Level.INFO, "city:" + city);
		logger.log(Level.INFO, "leasesCompanyId:" +leasesCompanyId);
		return individualAccountRulesService.getIndividualAccountRulesListByCity(city, leasesCompanyId);
	}
	
	/** 
	 * <p>新增个性化计费规则</p>
	 *
	 * @param leCompanyRulesRef 个性化计费规则
	 * @return
	 */
	@RequestMapping(value = "api/IndividualAccountRules/CreateIndividualAccountRules", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> createIndividualAccountRules(@RequestBody LeCompanyRulesRef leCompanyRulesRef) {
		return individualAccountRulesService.createIndividualAccountRules(leCompanyRulesRef);
	}
	
	/** 
	 * <p>启用个性化计费规则</p>
	 *
	 * @param leCompanyRulesRef 个性化计费规则
	 * @return
	 */
	@RequestMapping(value = "api/IndividualAccountRules/EnableIndividualAccountRules", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> enableIndividualAccountRules(@RequestBody LeCompanyRulesRef leCompanyRulesRef) {
		return individualAccountRulesService.enableIndividualAccountRules(leCompanyRulesRef);
	}
	
}
