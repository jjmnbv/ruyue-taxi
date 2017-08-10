package com.szyciov.organ.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.lease.entity.OrgOrganBill;
import com.szyciov.lease.entity.OrgOrganCompanyRef;
import com.szyciov.lease.entity.OrgOrganExpenses;
import com.szyciov.lease.param.OrganAccountQueryParam;
import com.szyciov.lease.param.OrganBillQueryParam;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.org.param.FinancialManagementQueryParam;
import com.szyciov.organ.service.FinancialManagementService;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;

/**
 * 财务管理模块控制器
 */
@Controller
public class FinancialManagementController extends BaseController {
	private static final Logger logger = Logger.getLogger(FinancialManagementController.class);

	public FinancialManagementService financialManagementService;

	@Resource(name = "FinancialManagementService")
	public void setFinancialManagementService(FinancialManagementService financialManagementService) {
		this.financialManagementService = financialManagementService;
	}
	
	/** 
	 * <p>账户管理  账户列表页</p>
	 *
	 * @param organId 机构Id
	 * @return
	 */
	@RequestMapping(value = "api/FinancialManagement/GetAccountListByOrganId/{organId}", method = RequestMethod.GET)
	@ResponseBody
	public List<OrgOrganCompanyRef> getAccountListByOrganId(@PathVariable String organId) {
		logger.log(Level.INFO, "api getAccountListByOrganId organId:" + organId);
		return financialManagementService.getAccountListByOrganId(organId);
	}
	
	/** 
	 * <p>分页查询  交易明细</p>
	 *
	 * @param queryParam 查询请求对象，封装需要查询的key和页码等信息
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "api/FinancialManagement/GetOrganExpensesByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getOrganExpensesByQuery(@RequestBody OrganAccountQueryParam queryParam) {
		return financialManagementService.getOrganExpensesByQuery(queryParam);
	}
	
	/** 
	 * <p>分页查询  账单列表</p>
	 *
	 * @param queryParam 查询请求对象，封装需要查询的key和页码等信息
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "api/FinancialManagement/GetOrganBillByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getOrganBillByQuery(@RequestBody FinancialManagementQueryParam queryParam) {
		return financialManagementService.getOrganBillByQuery(queryParam);
	}
	
	/** 
	 * <p>账单明细中账单信息</p>
	 *
	 * @param id 账单Id
	 * @return
	 */
	@RequestMapping(value = "api/FinancialManagement/GetOrganBillById/{id}", method = RequestMethod.GET)
	@ResponseBody
	public OrgOrganBill getOrganBillById(@PathVariable String id) {
		logger.log(Level.INFO, "api getOrganBillById id:" + id);
		return financialManagementService.getOrganBillById(id);
	}
	
	/** 
	 * <p>分页查询  账单明细</p>
	 *
	 * @param queryParam 查询请求对象，封装需要查询的key和页码等信息
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "api/FinancialManagement/GetOrgOrderByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getOrgOrderByQuery(@RequestBody OrganBillQueryParam queryParam) {
		return financialManagementService.getOrgOrderByQuery(queryParam);
	}
	
	/** 
	 * <p>查询导出数据</p>
	 *
	 * @param billsId 账单Id  
	 * @param organId 机构Id      
	 * @return
	 */
	@RequestMapping(value = "api/FinancialManagement/GetOrgOrderListExport", method = RequestMethod.POST)
	@ResponseBody
	public List<OrgOrder> getOrgOrderListExport(@RequestParam(value = "billsId", required = true) String billsId,
			@RequestParam(value = "organId", required = true) String organId) {
		logger.log(Level.INFO, "api getOrgOrderListExport billsId:" + billsId);
		logger.log(Level.INFO, "api getOrgOrderListExport organId:" + organId);
		return financialManagementService.getOrgOrderListExport(billsId, organId);
	}
	
	/** 
	 * <p>查询租赁公司</p>
	 *
	 * @param organId 机构Id    
	 * @return
	 */
	@RequestMapping(value = "api/FinancialManagement/GetLeasesCompanyListByOrgan", method = RequestMethod.GET)
	@ResponseBody
	public List<LeLeasescompany> getLeasesCompanyListByOrgan(@RequestParam(value = "organId", required = true) String organId) {
		logger.log(Level.INFO, "api getLeasesCompanyListByOrgan organId:" + organId);
		return financialManagementService.getLeasesCompanyListByOrgan(organId);
	}
	
	/** 
	 * <p>通过账单或者退回账单</p>
	 *
	 * @param leAccountRules
	 * @return
	 */
	@RequestMapping(value = "api/FinancialManagement/UpdateOrganBillState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updateOrganBillState(
			@RequestParam(value = "billsId", required = true) String billsId,
			@RequestParam(value = "billValue", required = true) String billValue,
			@RequestParam(value = "comment", required = false) String comment) {
		logger.log(Level.INFO, "billsId:" + billsId);
		logger.log(Level.INFO, "billValue:" + billValue);
		logger.log(Level.INFO, "comment:" + comment);
		return financialManagementService.updateOrganBillState(billsId, billValue, comment);
	}
	
	/** 
	 * <p>查询账户余额</p>
	 *
	 * @param organId 机构  
	 * @param leasesCompanyId 租赁公司 
	 * @return
	 */
	@RequestMapping(value = "api/FinancialManagement/GetActualBalance", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getActualBalance(@RequestParam(value = "organId", required = true) String organId,
			@RequestParam(value = "leasesCompanyId", required = true) String leasesCompanyId) {
		logger.log(Level.INFO, "organId:" + organId);
		logger.log(Level.INFO, "leasesCompanyId:" + leasesCompanyId);
		return financialManagementService.getActualBalance(organId, leasesCompanyId);
	}
	
	/** 
	 * <p>去支付</p>
	 *
	 * @param organId 机构
	 * @param leasesCompanyId 租赁公司
	 * @param money 账单金额
	 * @return
	 */
	@RequestMapping(value = "api/FinancialManagement/ConfirmAccount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> confirmAccount(@RequestBody OrgOrganBill orgOrganBill) {
		return financialManagementService.confirmAccount(orgOrganBill);
	}
	
	/** 
	 * <p>查询账单状态</p>
	 *
	 * @param billsId 账单Id
	 * @return
	 */
	@RequestMapping(value = "api/FinancialManagement/GetOrganBillStateById/{billsId}", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getOrganBillStateById(@PathVariable String billsId) {
		logger.log(Level.INFO, "billsId:" + billsId);
		return financialManagementService.getOrganBillStateById(billsId);
	}
	
	/** 
	 * <p>查询租赁公司信息</p>
	 *
	 * @param leasesCompanyId 租赁公司Id
	 * @return
	 */
	@RequestMapping(value = "api/FinancialManagement/GetLeasesCompanyById/{leasesCompanyId}", method = RequestMethod.GET)
	@ResponseBody
	public LeLeasescompany getLeasesCompanyById(@PathVariable String leasesCompanyId) {
		logger.log(Level.INFO, "leasesCompanyId:" + leasesCompanyId);
		return financialManagementService.getLeasesCompanyById(leasesCompanyId);
	}
	
	/**
	 * <p>充值支付</p>
	 * 
	 */
	@RequestMapping(value = "api/FinancialManagement/UpdatePaytype", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePaytype(@RequestBody Map<String,Object> payparam) {
		return financialManagementService.updatePaytype(payparam);
	}
	
	/** 
	 * <p>微信支付</p>
	 */
	@RequestMapping(value = "FinancialManagement/DillWXPayed", method = RequestMethod.POST)
	public void dillWXPayed(HttpServletRequest req,HttpServletResponse res){
		financialManagementService.dillWXPayed(req,res);
	}
	
	/** 
	 * <p>支付宝支付</p>
	 */
	@RequestMapping(value = "FinancialManagement/DillZFBPayed", method = RequestMethod.POST)
	public void dillZFBPayed(HttpServletRequest req,HttpServletResponse res){
		financialManagementService.dillZFBPayed(req,res);
	}
	
	/**
	 * CheckOrderState
	 * @param loginparam
	 * @return
	 */
	@RequestMapping(value = "api/FinancialManagement/CheckPayState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkPayState(@RequestBody Map<String,Object> params,HttpServletRequest request,HttpServletResponse response){
		return financialManagementService.checkPayState(params);
	}
	
	
	/** 
	 * <p>查询机构账户信息</p>
	 *
	 * @param organId 机构  
	 * @param leasesCompanyId 租赁公司 
	 * @return
	 */
	@RequestMapping(value = "api/FinancialManagement/GetWithdrawInfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getWithdrawInfo(@RequestParam(value = "organId", required = true) String organId,
			@RequestParam(value = "leasesCompanyId", required = true) String leasesCompanyId) {
		logger.log(Level.INFO, "organId:" + organId);
		logger.log(Level.INFO, "leasesCompanyId:" + leasesCompanyId);
		return financialManagementService.getWithdrawInfo(organId, leasesCompanyId);
	}
	
	/** 
	 * <p>提现处理</p>
	 *
	 * @param org_organexpenses
	 * @return
	 */
	@RequestMapping(value = "api/FinancialManagement/WithdrawOrganAccount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> withdrawOrganAccount(@RequestBody OrgOrganExpenses org_organexpenses) {
		return financialManagementService.withdrawOrganAccount(org_organexpenses);
	}
	
	/** 
	 * <p>查询提现申请信息</p>
	 *
	 * @param id 提现信息id  
	 * @return
	 */
	@RequestMapping(value = "api/FinancialManagement/GetPubWithdraw/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getPubWithdraw(@PathVariable String id) {
		logger.log(Level.INFO, "id:" + id);
		return financialManagementService.getPubWithdraw(id);
	}
	/** 
	 * <p>分页查询  抵用券</p>
	 *
	 * @param queryParam 查询请求对象，封装需要查询的key和页码等信息
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "api/FinancialManagement/GetPubCouponDetailByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getPubCouponDetailByQuery(@RequestBody OrganAccountQueryParam queryParam) {
		return financialManagementService.getPubCouponDetailByQuery(queryParam);
	}
}
