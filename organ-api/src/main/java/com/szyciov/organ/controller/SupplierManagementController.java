package com.szyciov.organ.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.lease.entity.OrgOrgan;
import com.szyciov.org.entity.OrgUser;
import com.szyciov.organ.service.SupplierManagementService;
import com.szyciov.util.BaseController;

/**
 * 控制器
 */
@Controller
public class SupplierManagementController extends BaseController {
	private static final Logger logger = Logger.getLogger(SupplierManagementController.class);

	public SupplierManagementService supplierManagementService;

	@Resource(name = "SupplierManagementService")
	public void setOrgOrganService(SupplierManagementService supplierManagementService) {
		this.supplierManagementService = supplierManagementService;
	}
	/**
	 * <p>
	 * 
	 * </p>
	 *
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "api/SupplierManagement/GetById/{id}", method = RequestMethod.GET)
	@ResponseBody
	public OrgUser getById(@PathVariable String id) {
		return supplierManagementService.getById(id);
	}
	
	/**
	 * <p>
	 * 
	 * </p>
	 *
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "api/SupplierManagement/GetByList/{id}/{organid}", method = RequestMethod.GET)
	@ResponseBody
	public List<OrgUser> getByList(@PathVariable String id,@PathVariable String organid) {
		return supplierManagementService.getByList(id,organid);
	}
	
	/**
	 * <p>
	 * 
	 * </p>
	 *
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "api/SupplierManagement/AddLink", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> addLink(@RequestBody OrgUser orgUser) {
		return supplierManagementService.addLink(orgUser);
	}
	
	/**
	 * <p>
	 * 
	 * </p>
	 *
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "api/SupplierManagement/RemoveLink/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> removeLink(@PathVariable String id) {
		return supplierManagementService.removeLink(id);
	}
	
	/**
	 * <p>
	 * 
	 * </p>
	 *
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "api/SupplierManagement/GetChildCccount/{id}/{organid}", method = RequestMethod.GET)
	@ResponseBody
	public List<OrgUser> getChildCccount(@PathVariable String id,@PathVariable String organid) {
		return supplierManagementService.getChildCccount(id,organid);
	}
	
	/**
	 * <p>
	 * 
	 * </p>
	 *
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "api/SupplierManagement/CheckPassword", method = RequestMethod.POST)
	@ResponseBody
	public int checkPassword(@RequestBody OrgUser orgUser) {
		return supplierManagementService.checkPassword(orgUser);
	}
	/**
	 * <p>
	 * 
	 * </p>
	 *
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "api/SupplierManagement/CheckPasswords", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkPasswords(@RequestBody OrgUser orgUser) {
		return supplierManagementService.checkPasswords(orgUser);
	}
	/**
	 * <p>
	 * 
	 * </p>
	 *
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "api/SupplierManagement/RemoveLink/{id}/{orgOrganCompanyRefId}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> removeLink(@PathVariable String id,@PathVariable String orgOrganCompanyRefId) {
		return supplierManagementService.removeLink(id,orgOrganCompanyRefId);
	}
	
	/**
	 * <p>
	 * 
	 * </p>
	 *
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "api/SupplierManagement/UpdatePassword", method = RequestMethod.POST)
	@ResponseBody
	public void updatePassword(@RequestBody OrgUser orgUser) {
		supplierManagementService.updatePassword(orgUser);
	}
	
	/**
	 * <p>
	 * 
	 * </p>
	 *
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "api/SupplierManagement/GetOrgorganPhone/{id}", method = RequestMethod.GET)
	@ResponseBody
	public OrgOrgan getOrgorganPhone(@PathVariable String id) {
		return supplierManagementService.getOrgorganPhone(id);
	}
	
	/**
	 * <p>
	 * 
	 * </p>
	 *
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "api/SupplierManagement/updateAll", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updateAll(@RequestBody OrgOrgan orgOrgan) {
		return supplierManagementService.updateAll(orgOrgan);
	}
}
