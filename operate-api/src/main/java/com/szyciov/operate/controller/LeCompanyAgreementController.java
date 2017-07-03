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

import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.op.entity.LeCompanyAgreement;
import com.szyciov.op.param.LeCompanyAgreementQueryParam;
import com.szyciov.operate.service.LeCompanyAgreementService;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;

/**
 * 租赁公司协议管理
 */
@Controller
public class LeCompanyAgreementController extends BaseController {
	
	private LeCompanyAgreementService agreementService;
	@Resource(name = "LeCompanyAgreementService")
	public void setAgreementService(LeCompanyAgreementService agreementService) {
		this.agreementService = agreementService;
	}
	
	/**
	 * 分页查询租赁公司协议数据
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "api/CompanyAgreement/GetCompanyAgreementByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getCompanyAgreementByQuery(@RequestBody LeCompanyAgreementQueryParam queryParam) {
		return agreementService.getCompanyAgreementByQuery(queryParam);
	}
	
	/**
	 * 查询租赁公司协议修改页面的公司列表信息
	 * @return
	 */
	@RequestMapping(value = "api/CompanyAgreement/GetCompanyLisyByEdit", method = RequestMethod.POST)
	@ResponseBody
	public List<LeLeasescompany> getCompanyLisyByEdit() {
		return agreementService.getCompanyLisyByEdit();
	}
	
	/**
	 * 查询租赁公司协议添加页面的公司列表信息
	 * @return
	 */
	@RequestMapping(value = "api/CompanyAgreement/GetCompanyLisyByAdd", method = RequestMethod.POST)
	@ResponseBody
	public List<LeLeasescompany> getCompanyLisyByAdd() {
		return agreementService.getCompanyLisyByAdd();
	}
	
	/**
	 * 根据协议id查询租赁公司协议详细信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "api/CompanyAgreement/GetCompanyAgreementById/{id}", method = RequestMethod.GET)
	@ResponseBody
	public LeCompanyAgreement getCompanyAgreementById(@PathVariable String id) {
		return agreementService.getCompanyAgreementById(id);
	}
	
	/**
	 * 添加租赁公司协议
	 * @param object
	 * @return
	 */
	@RequestMapping(value = "api/CompanyAgreement/CreateCompanyAgreement", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> createCompanyAgreement(@RequestBody LeCompanyAgreement object) {
		return agreementService.createCompanyAgreement(object);
	}
	
	/**
	 * 修改租赁公司协议
	 * @param object
	 * @return
	 */
	@RequestMapping(value = "api/CompanyAgreement/EditCompanyAgreement", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> editCompanyAgreement(@RequestBody LeCompanyAgreement object) {
		return agreementService.editCompanyAgreement(object);
	}
	
	/**
	 * 删除租赁公司协议
	 * @param object
	 * @return
	 */
	@RequestMapping(value = "api/CompanyAgreement/DeleteCompanyAgreement", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> deleteCompanyAgreement(@RequestBody LeCompanyAgreement object) {
		return agreementService.deleteCompanyAgreement(object);
	}

}
