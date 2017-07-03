package com.szyciov.lease.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.lease.entity.User;
import com.szyciov.lease.service.LeLeasescompanyService;
import com.szyciov.op.entity.LeCompanyAgreement;
import com.szyciov.op.entity.OpUser;
import com.szyciov.util.BaseController;

/**
 * 控制器
 */
@Controller
public class LeLeasescompanyController extends BaseController {
	private static final Logger logger = Logger.getLogger(LeLeasescompanyController.class);

	public LeLeasescompanyService leLeasescompanyService;

	@Resource(name = "LeLeasescompanyService")
	public void setOrgOrganService(LeLeasescompanyService leLeasescompanyService) {
		this.leLeasescompanyService = leLeasescompanyService;
	}

	/**
	 * <p>
	 * 增加一条记录
	 * </p>
	 *
	 * @param orgOrgan
	 * @return
	 */
	@RequestMapping(value = "api/LeLeasescompany/UpdateLeLeasescompany", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updateLeLeasescompany(@RequestBody LeLeasescompany leLeasescompany) {
		return leLeasescompanyService.updateLeLeasescompany(leLeasescompany);
	}
	
	/**
	 * <p>
	 * 根据 登陆的userid 查LeLeasescompany
	 * </p>
	 *
	 * @param orgOrgan
	 * @return
	 */
	@RequestMapping(value = "api/LeLeasescompany/GetLeLeasescompany/{id}", method = RequestMethod.GET)
	@ResponseBody
	public LeLeasescompany getLeLeasescompany(@PathVariable String id) {
		return leLeasescompanyService.getLeLeasescompany(id);
	}
	
	/**
	 * <p>
	 * 
	 * </p>
	 *
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "api/LeLeasescompany/UpdatePassword", method = RequestMethod.POST)
	@ResponseBody
	public void updatePassword(@RequestBody User user) {
		leLeasescompanyService.updatePassword(user);
	}
	
	/**
	 * <p>
	 * 
	 * </p>
	 *
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "api/LeLeasescompany/UpdateToC", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updateToC(@RequestBody LeLeasescompany leLeasescompany) {
		return leLeasescompanyService.updateToC(leLeasescompany);
	}

	/**
	 * <p>
	 * 
	 * </p>
	 *
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "api/LeLeasescompany/OutToC", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> outToC(@RequestBody LeLeasescompany leLeasescompany) {
		return leLeasescompanyService.outToC(leLeasescompany);
	}
	
	/**
	 * <p>
	 * 根据 LeLeasescompany id 查 租赁公司的协议
	 * </p>
	 *
	 * @param orgOrgan
	 * @return
	 */
	@RequestMapping(value = "api/LeLeasescompany/GetLeCompanyAgreement/{id}", method = RequestMethod.GET)
	@ResponseBody
	public LeCompanyAgreement getLeCompanyAgreement(@PathVariable String id) {
		return leLeasescompanyService.getLeCompanyAgreement(id);
	}
	
	/**
	 * <p>
	 * 修改le_user fristtime 
	 * </p>
	 *
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "api/LeLeasescompany/updataLeUserFristtime/{id}", method = RequestMethod.GET)
	@ResponseBody
	public void updataLeUserFristtime(@PathVariable String id) {
		leLeasescompanyService.updataLeUserFristtime(id);
	}
	
	/**
	 * <p>
	 * 查询  运管超管表
	 * </p>
	 *
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "api/LeLeasescompany/GetOpUser", method = RequestMethod.POST)
	@ResponseBody
	public OpUser getOpUser() {
		return leLeasescompanyService.getOpUser();
	}
}
