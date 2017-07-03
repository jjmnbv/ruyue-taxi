package com.szyciov.lease.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.lease.entity.OrgOrganExpenses;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.param.OrganAccountQueryParam;
import com.szyciov.lease.service.OrganAccountService;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;

/**
 * 机构账户模块控制器
 */
@Controller
public class OrganAccountController extends BaseController {
	private static final Logger logger = Logger.getLogger(StandardAccountRulesController.class);

	public OrganAccountService organAccountService;

	@Resource(name = "organAccountService")
	public void setOrganAccountService(OrganAccountService organAccountService) {
		this.organAccountService = organAccountService;
	}
	
	/** 
	 * <p>分页查询机构客户账户信息</p>
	 *
	 * @param queryParam 查询请求对象，封装需要查询的key和页码等信息
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "api/OrganAccount/GetOrganAccountInfoByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getOrganAccountInfoByQuery(@RequestBody OrganAccountQueryParam queryParam) {
		return organAccountService.getOrganAccountInfoByQuery(queryParam);
	}
	
	/** 
	 * <p>分页查询账户往来明细</p>
	 *
	 * @param queryParam 查询请求对象，封装需要查询的key和页码等信息
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "api/OrganAccount/GetOrganExpensesByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getOrganExpensesByQuery(@RequestBody OrganAccountQueryParam queryParam) {
		return organAccountService.getOrganExpensesByQuery(queryParam);
	}
	
	/** 
	 * <p>机构账户充值</p>
	 *
	 * @param orgOrganExpenses 
	 * @return
	 */
	@RequestMapping(value = "api/OrganAccount/RechargeOrganAccount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> rechargeOrganAccount(@RequestBody OrgOrganExpenses orgOrganExpenses) {
		return organAccountService.rechargeOrganAccount(orgOrganExpenses);
	}
	
	/** 
	 * <p>机构账户提现</p>
	 *
	 * @param orgOrganExpenses    
	 * @return
	 */
	@RequestMapping(value = "api/OrganAccount/ReduceOrganAccount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> reduceOrganAccount(@RequestBody OrgOrganExpenses orgOrganExpenses) {
		return organAccountService.reduceOrganAccount(orgOrganExpenses);
	}
	
	/** 
	 * <p>查询所属城市列表</p>
	 *
	 * @param leasesCompanyId 所属租赁公司    
	 * @return
	 */
	@RequestMapping(value = "api/OrganAccount/GetExistCityList", method = RequestMethod.GET)
	@ResponseBody
	public List<PubCityAddr> getExistCityList(
			@RequestParam(value = "leasesCompanyId", required = true) String leasesCompanyId,
			@RequestParam(value = "specialState", required = true) String specialState,
			@RequestParam(value = "account", required = true) String account) {
		logger.log(Level.INFO, "leasesCompanyId:" + leasesCompanyId);
		logger.log(Level.INFO, "specialState:" + specialState);
		logger.log(Level.INFO, "account:" + account);
		return organAccountService.getExistCityList(leasesCompanyId, specialState, account);
	}
	
	/** 
	 * <p>查询机构名称列表</p>
	 *
	 * @param leasesCompanyId 所属租赁公司    
	 * @return
	 */
	@RequestMapping(value = "api/OrganAccount/GetExistOrganList", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getExistOrganList(
			@RequestParam(value = "leasesCompanyId", required = true) String leasesCompanyId,
			@RequestParam(value = "shortName", required = false) String shortName,
			@RequestParam(value = "specialState", required = true) String specialState,
			@RequestParam(value = "account", required = true) String account) {
		logger.log(Level.INFO, "leasesCompanyId:" + leasesCompanyId);
		logger.log(Level.INFO, "shortName:" + shortName);
		logger.log(Level.INFO, "specialState:" + specialState);
		logger.log(Level.INFO, "account:" + account);
		return organAccountService.getExistOrganList(leasesCompanyId, shortName, specialState, account);
	}
	
	/** 
	 * <p>查询导出数据</p>
	 *
	 * @param leasesCompanyId 所属租赁公司    
	 * @return
	 */
	@RequestMapping(value = "api/OrganAccount/GetOrganExpensesListExport", method = RequestMethod.POST)
	@ResponseBody
	public List<OrgOrganExpenses> getOrganExpensesListExport(@RequestBody OrganAccountQueryParam queryParam) {
		return organAccountService.getOrganExpensesListExport(queryParam);
	}
}
