package com.szyciov.operate.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.op.entity.LeCompanyAgreement;
import com.szyciov.op.entity.OpUser;
import com.szyciov.op.param.LeCompanyAgreementQueryParam;
import com.szyciov.operate.service.LeCompanyAgreementService;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
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
	 * 跳转到租赁公司协议主页面
	 * @return
	 */
	@RequestMapping(value = "CompanyAgreement/Index")
	@ResponseBody
	public ModelAndView getCompanyAgreementIndex(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		OpUser user = getLoginOpUser(request);
		ModelAndView view = new ModelAndView();
		view.addObject("usertype", user.getUsertype());
		view.setViewName("resource/leCompanyAgreement/index");
		return view;
	}
	
	/**
	 * 分页查询租赁公司协议数据
	 * @param queryParam
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "CompanyAgreement/GetCompanyAgreementByQuery")
	@ResponseBody
	public PageBean getCompanyAgreementByQuery(@RequestBody LeCompanyAgreementQueryParam queryParam,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return agreementService.getCompanyAgreementByQuery(queryParam, userToken);
	}
	
	/**
	 * 跳转到租赁公司协议维护页面
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "CompanyAgreement/GetEditCompanyAgreementPage")
	@ResponseBody
	public ModelAndView getEditCompanyAgreementPage(@RequestParam(value = "id", required = false) String id, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		ModelAndView view = new ModelAndView();
		view.setViewName("resource/leCompanyAgreement/editCompanyAgreement");
		//id为空时，查询所有没有签订协议的公司列表；id不为空时，查询所有签订了协议的公司列表和协议详情
		List<LeLeasescompany> leasesCompanyList = null;
		LeCompanyAgreement companyAgreement = null;
		if(StringUtils.isBlank(id)) {
			leasesCompanyList = agreementService.getCompanyLisyByAdd(userToken);
		} else {
			leasesCompanyList = agreementService.getCompanyLisyByEdit(userToken);
			companyAgreement = agreementService.getCompanyAgreementById(id, userToken);
		}
		view.addObject("leasesCompanyList", leasesCompanyList);
		view.addObject("companyAgreement", companyAgreement);
		return view;
	}
	
	/**
	 * 添加租赁公司协议数据
	 * @param object
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "CompanyAgreement/CreateCompanyAgreement")
	@ResponseBody
	public Map<String, String> createCompanyAgreement(@RequestBody LeCompanyAgreement object, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OpUser user = getLoginOpUser(request);
		object.setCreater(user.getId());
		object.setUpdater(user.getId());
		return agreementService.createCompanyAgreement(object, userToken);
	}
	
	/**
	 * 修改租赁公司协议数据
	 * @param object
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "CompanyAgreement/EditCompanyAgreement")
	@ResponseBody
	public Map<String, String> editCompanyAgreement(@RequestBody LeCompanyAgreement object, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OpUser user = getLoginOpUser(request);
		object.setUpdater(user.getId());
		return agreementService.editCompanyAgreement(object, userToken);
	}
	
	/**
	 * 删除租赁公司协议数据
	 * @param object
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "CompanyAgreement/DeleteCompanyAgreement")
	@ResponseBody
	public Map<String, String> deleteCompanyAgreement(@RequestParam String id, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OpUser user = getLoginOpUser(request);
		LeCompanyAgreement object = new LeCompanyAgreement();
		object.setId(id);
		object.setUpdater(user.getId());
		return agreementService.deleteCompanyAgreement(object, userToken);
	}
	
	/**
	 * 跳转到租赁公司协议详情页面
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "CompanyAgreement/GetCompanyAgreementByDetail/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView getCompanyAgreementByDetail(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		ModelAndView view = new ModelAndView();
		view.setViewName("resource/leCompanyAgreement/detailCompanyAgreement");
		LeCompanyAgreement companyAgreement = agreementService.getCompanyAgreementById(id, userToken);
		view.addObject("companyAgreement", companyAgreement);
		return view;
	}
	
}
