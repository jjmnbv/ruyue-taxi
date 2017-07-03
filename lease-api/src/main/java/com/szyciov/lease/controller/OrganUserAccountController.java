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

import com.szyciov.lease.entity.OrgUserExpenses;
import com.szyciov.lease.param.OrganUserAccountQueryParam;
import com.szyciov.lease.service.OrganUserAccountService;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;

/**
 * 个人账户模块控制器
 */
@Controller
public class OrganUserAccountController extends BaseController {
	private static final Logger logger = Logger.getLogger(OrganUserAccountController.class);

	public OrganUserAccountService organUserAccountService;

	@Resource(name = "organUserAccountService")
	public void setOrganUserAccountService(OrganUserAccountService organUserAccountService) {
		this.organUserAccountService = organUserAccountService;
	}
	
	/** 
	 * <p>分页查询个人客户账户信息</p>
	 *
	 * @param queryParam 查询请求对象，封装需要查询的key和页码等信息
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "api/OrganUserAccount/GetOrganUserAccountInfoByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getOrganUserAccountInfoByQuery(@RequestBody OrganUserAccountQueryParam queryParam) {
		return organUserAccountService.getOrganUserAccountInfoByQuery(queryParam);
	}
	
	/** 
	 * <p>分页查询账户往来明细</p>
	 *
	 * @param queryParam 查询请求对象，封装需要查询的key和页码等信息
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "api/OrganUserAccount/GetUserExpensesByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getUserExpensesByQuery(@RequestBody OrganUserAccountQueryParam queryParam) {
		return organUserAccountService.getUserExpensesByQuery(queryParam);
	}
	
	/** 
	 * <p>根据关键字查找用户</p>
	 *
	 * @param leasesCompanyId 所属租赁公司   
	 * @param nameAccount 关键字
	 * @return 
	 */
	@RequestMapping(value = "api/OrganUserAccount/GetExistUserList", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getExistUserList(
			@RequestParam(value = "leasesCompanyId", required = true) String leasesCompanyId,
			@RequestParam(value = "nameAccount", required = false) String nameAccount,
			@RequestParam(value = "specialState", required = true) String specialState,
			@RequestParam(value = "account", required = true) String account) {
		logger.log(Level.INFO, "leasesCompanyId:" + leasesCompanyId);
		logger.log(Level.INFO, "nameAccount:" + nameAccount);
		logger.log(Level.INFO, "specialState:" + specialState);
		logger.log(Level.INFO, "account:" + account);
		return organUserAccountService.getExistUserList(leasesCompanyId, nameAccount, specialState, account);
	}
	
	/** 
	 * <p>查询机构名称列表</p>
	 *
	 * @param leasesCompanyId 所属租赁公司   
	 * @param shortName 机构名称 
	 * @return
	 */
	@RequestMapping(value = "api/OrganUserAccount/GetExistOrganList", method = RequestMethod.GET)
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
		return organUserAccountService.getExistOrganList(leasesCompanyId, shortName, specialState, account);
	}
	
	/** 
	 * <p>查询导出数据</p>
	 *
	 * @param OrganUserAccountQueryParam
	 * @return
	 */
	@RequestMapping(value = "api/OrganAccount/GetUserExpensesListExport", method = RequestMethod.POST)
	@ResponseBody
	public List<OrgUserExpenses> getUserExpensesListExport(@RequestBody OrganUserAccountQueryParam queryParam) {
		return organUserAccountService.getUserExpensesListExport(queryParam);
	}
	
}
