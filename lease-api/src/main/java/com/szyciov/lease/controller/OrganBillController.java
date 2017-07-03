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

import com.szyciov.lease.entity.OrgOrgan;
import com.szyciov.lease.entity.OrgOrganBill;
import com.szyciov.lease.entity.OrgOrganBillState;
import com.szyciov.lease.param.OrganBillQueryParam;
import com.szyciov.lease.service.OrganBillService;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;

/**
 * 机构账单模块控制器
 */
@Controller
public class OrganBillController extends BaseController {
	private static final Logger logger = Logger.getLogger(StandardAccountRulesController.class);

	public OrganBillService organBillService;

	@Resource(name = "organBillService")
	public void setOrganBillService(OrganBillService organBillService) {
		this.organBillService = organBillService;
	}
	
	/** 
	 * <p>分页查询机构当前账单</p>
	 *
	 * @param queryParam 查询请求对象，封装需要查询的key和页码等信息
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "api/OrganBill/GetCurOrganBillByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getCurOrganBillByQuery(@RequestBody OrganBillQueryParam queryParam) {
		return organBillService.getCurOrganBillByQuery(queryParam);
	}
	
	/** 
	 * <p>根据账单Id查询账单日志</p>
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "api/OrganBill/GetOrganBillStateById/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<OrgOrganBillState> getOrganBillStateById(@PathVariable String id) {
		logger.log(Level.INFO, "getOrganBillStateById id:" + id);
		return organBillService.getOrganBillStateById(id);
	}
	
	/** 
	 * <p>分页查询机构账单详情</p>
	 *
	 * @param queryParam 查询请求对象，封装需要查询的key和页码等信息
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "api/OrganBill/GetOrgOrderByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getOrgOrderByQuery(@RequestBody OrganBillQueryParam queryParam) {
		return organBillService.getOrgOrderByQuery(queryParam);
	}
	
	/** 
	 * <p>分页查询手动生成机构账单详情</p>
	 *
	 * @param queryParam 查询请求对象，封装需要查询的key和页码等信息
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "api/OrganBill/GetManualOrgOrderByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getManualOrgOrderByQuery(@RequestBody OrganBillQueryParam queryParam) {
		return organBillService.getManualOrgOrderByQuery(queryParam);
	}
	
	/** 
	 * <p>手动生成账单</p>
	 *
	 * @param orgOrganBill
	 * @return
	 */
	@RequestMapping(value = "api/OrganBill/CreateOrganbill", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> createOrganbill(@RequestBody OrgOrganBill orgOrganBill) {
		return organBillService.createOrganbill(orgOrganBill);
	}
	
	/** 
	 * <p>作废账单</p>
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "api/OrganBill/DeleteOrganBill/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> deleteOrganbill(@PathVariable String id, @RequestParam(value = "remark", required = true) String remark) {
		return organBillService.deleteOrganbill(id, remark);
	}
	
	/** 
	 * <p>确认收款</p>
	 *
	 * @param organId 机构
	 * @param leasesCompanyId 租赁公司
	 * @param money 账单金额
	 * @return
	 */
	@RequestMapping(value = "api/OrganBill/ConfirmAccount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> confirmAccount(@RequestBody OrgOrganBill orgOrganBill) {
		return organBillService.confirmAccount(orgOrganBill);
	}
	
	/** 
	 * <p>查询账户余额</p>
	 *
	 * @param organId 机构  
	 * @param leasesCompanyId 租赁公司 
	 * @return
	 */
	@RequestMapping(value = "api/OrganBill/GetActualBalance", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getActualBalance(@RequestParam(value = "organId", required = true) String organId,
			@RequestParam(value = "leasesCompanyId", required = true) String leasesCompanyId) {
		return organBillService.getActualBalance(organId, leasesCompanyId);
	}
	
	/** 
	 * <p>根据账单Id获取账单信息</p>
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "api/OrganBill/GetOrganBillById/{id}", method = RequestMethod.GET)
	@ResponseBody
	public OrgOrganBill getOrganBillById(@PathVariable String id) {
		return organBillService.getOrganBillById(id);
	}
	
	/** 
	 * <p>增加核对完成状态</p>
	 *
	 * @param billsId 账单Id
	 * @return
	 */
	@RequestMapping(value = "api/OrganBill/CheckComplete/{billsId}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> createCheckOrganBillState(@PathVariable String billsId) {
		return organBillService.createCheckOrganBillState(billsId);
	}
	
	/** 
	 * <p>查询机构客户信息</p>
	 *
	 * @param leasesCompanyId 所属租赁公司
	 * @return
	 */
	@RequestMapping(value = "api/OrganBill/GetOrganList", method = RequestMethod.GET)
	@ResponseBody
	public List<OrgOrgan> getOrganList(@RequestParam(value = "leasesCompanyId", required = true) String leasesCompanyId) {
		return organBillService.getOrganList(leasesCompanyId);
	}
	
	/** 
	 * <p>重新生成账单时，根据订单ID分页查询订单详情</p>
	 *
	 * @param queryParam 查询请求对象，封装需要查询的key和页码等信息
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "api/OrganBill/GetOrgOrderById", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getOrgOrderById(@RequestBody OrganBillQueryParam queryParam) {
		return organBillService.getOrgOrderById(queryParam);
	}
	
	/** 
	 * <p>重新生成账单</p>
	 *
	 * @param orgOrganBill
	 * @return
	 */
	@RequestMapping(value = "api/OrganBill/ReCreateOrganbill", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> reCreateOrganbill(@RequestBody OrgOrganBill orgOrganBill) {
		return organBillService.reCreateOrganbill(orgOrganBill);
	}
	
	/** 
	 * <p>查询导出数据</p>
	 *
	 * @param OrganAccountQueryParam 
	 * @return
	 */
	@RequestMapping(value = "api/OrganBill/GetOrganBillListExport", method = RequestMethod.POST)
	@ResponseBody
	public List<OrgOrganBill> getCurOrganBillListExport(@RequestBody OrganBillQueryParam queryParam) {
		return organBillService.getCurOrganBillListExport(queryParam);
	}

	/** 
	 * <p>自动生成账单</p>
	 *
	 * @param orgOrganBill
	 * @return
	 */
	@RequestMapping(value = "api/OrganBill/CreateOrganBillAuto", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> createOrganBillAuto(@RequestBody OrgOrganBill orgOrganBill) {
		return organBillService.createOrganBillAuto(orgOrganBill);
	}
	
	/** 
	 * <p>查询机构客户分组信息</p>
	 *
	 * @param leasesCompanyId 所属租赁公司
	 * @param fullName 机构名称
	 * @return
	 */
	@RequestMapping(value = "api/OrganBill/SelectOrganList", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> selectOrganList(
			@RequestParam(value = "leasesCompanyId", required = true) String leasesCompanyId,
			@RequestParam(value = "fullName", required = false) String fullName,
			@RequestParam(value = "specialState", required = true) String specialState,
			@RequestParam(value = "account", required = true) String account) {
		logger.log(Level.INFO, "leasesCompanyId:" + leasesCompanyId);
		logger.log(Level.INFO, "fullName:" + fullName);
		logger.log(Level.INFO, "specialState:" + specialState);
		logger.log(Level.INFO, "account:" + account);
		return organBillService.selectOrganList(leasesCompanyId, fullName, specialState, account);
	}
	
	/** 
	 * <p>查询账单详情导出数据</p>
	 *
	 * @param OrganBillQueryParam 
	 * @return
	 */
	@RequestMapping(value = "api/OrganBill/GetOrgOrderListExport", method = RequestMethod.POST)
	@ResponseBody
	public List<OrgOrder> getOrgOrderListExport(@RequestBody OrganBillQueryParam organBillQueryParam) {
		return organBillService.getOrgOrderListExport(organBillQueryParam);
	}
}
