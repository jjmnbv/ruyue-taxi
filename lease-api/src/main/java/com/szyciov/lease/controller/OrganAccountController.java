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

import com.szyciov.entity.PubCouponDetail;
import com.szyciov.entity.Retcode;
import com.szyciov.lease.entity.OrgOrganExpenses;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.param.OrganAccountQueryParam;
import com.szyciov.lease.service.OrganAccountService;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;

import net.sf.json.JSONObject;

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
	 * <p>查询导出数据-账户明细</p>
	 *
	 * @param leasesCompanyId 所属租赁公司    
	 * @return
	 */
	@RequestMapping(value = "api/OrganAccount/GetOrganExpensesListExport", method = RequestMethod.POST)
	@ResponseBody
	public List<OrgOrganExpenses> getOrganExpensesListExport(@RequestBody OrganAccountQueryParam queryParam) {
		return organAccountService.getOrganExpensesListExport(queryParam);
	}
	
	/** 
	 * <p>分页查询抵用券明细</p>
	 *
	 * @param queryParam 查询请求对象，封装需要查询的key和页码等信息
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "api/OrganAccount/GetPubCouponDetailByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getPubCouponDetailByQuery(@RequestBody OrganAccountQueryParam queryParam) {
		return organAccountService.getPubCouponDetailByQuery(queryParam);
	}
	
	/** 
	 * <p>查询导出数据-抵用券明细</p>
	 *
	 * @param leasesCompanyId 所属租赁公司    
	 * @return
	 */
	@RequestMapping(value = "api/OrganAccount/GetPubCouponDetailExport", method = RequestMethod.POST)
	@ResponseBody
	public List<PubCouponDetail> getPubCouponDetailExport(@RequestBody OrganAccountQueryParam queryParam) {
		return organAccountService.getPubCouponDetailExport(queryParam);
	}
	
	/** 
	 * <p>给机构返券时，机构账户的可用余额加上相应的额度</p>
	 *
	 * @param organid 所属租赁公司    
	 * @param companyid 所属租赁公司    
	 * @param money 抵用券金额    
	 * @param remark 备注  
	 * @param usetype 类型 
	 * @return
	 */
	@RequestMapping(value = "api/OrganAccount/AddOrganCouponValue", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject addOrganCouponValue(@RequestParam(value = "organid", required = true) String organid,
			@RequestParam(value = "companyid", required = true) String companyid,
			@RequestParam(value = "money", required = true) double money,
			@RequestParam(value = "remark", required = true) String remark,
			@RequestParam(value = "usetype", required = true) String usetype) {
		logger.log(Level.INFO, "organid:" + organid);
		logger.log(Level.INFO, "companyid:" + companyid);
		logger.log(Level.INFO, "money:" + money);
		logger.log(Level.INFO, "remark:" + remark);
		logger.log(Level.INFO, "usetype:" + usetype);
		JSONObject jsonObject = new JSONObject();
		try {
			organAccountService.addOrganCouponValue(organid, companyid, money, remark, usetype);
			jsonObject.put("status", Retcode.OK.code);
		} catch (Exception e) {
			jsonObject.put("status", Retcode.EXCEPTION.code);
			jsonObject.put("message", "系统繁忙请稍后再试！");
			logger.error("抵用券返券时机构账户可用余额充值失败！", e);
		}
		return jsonObject;
	}

}
