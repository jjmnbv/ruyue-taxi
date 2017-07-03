package com.szyciov.lease.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.lease.service.AccountReceivableService;
import com.szyciov.util.BaseController;

/**
 * 控制器
 */
@Controller
public class AccountReceivableController extends BaseController {
	private static final Logger logger = Logger.getLogger(AccountReceivableController.class);

	public AccountReceivableService service;

	@Resource(name = "AccountReceivableService")
	public void setService(AccountReceivableService service) {
		this.service = service;
	}

	/**
	 * <p>
	 * 
	 * </p>
	 *
	 * @param LeLeasescompany
	 * @return
	 */
	@RequestMapping(value = "api/AccountReceivable/UpdateWechat", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updateWechat(@RequestBody LeLeasescompany leLeasescompany) {
		return service.updateWechat(leLeasescompany);
	}

	/**
	 * <p>
	 *	司机收账账户微信 更新
	 * </p>
	 *
	 * @param leLeasescompany
	 * @return
	 */
	@RequestMapping(value = "api/AccountReceivable/driver/UpdateWechat", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updateDriverWechat(@RequestBody LeLeasescompany leLeasescompany) {
		return service.updateDriverWechat(leLeasescompany);
	}

	/**
	 * <p>
	 * 
	 * </p>
	 *
	 * @param LeLeasescompany
	 * @return
	 */
	@RequestMapping(value = "api/AccountReceivable/UpdateAlipay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updateAlipay(@RequestBody LeLeasescompany leLeasescompany) {
		return service.updateAlipay(leLeasescompany);
	}

	/**
	 * <p>
	 *	司机收账账户Alipay 更新
	 * </p>
	 *
	 * @param leLeasescompany
	 * @return
	 */
	@RequestMapping(value = "api/AccountReceivable/driver/UpdateAlipay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updateDriverAlipay(@RequestBody LeLeasescompany leLeasescompany) {
		return service.updateDriverAlipay(leLeasescompany);
	}


	/**
	 * <p>
	 * 
	 * </p>
	 *
	 * @param LeLeasescompany
	 * @return
	 */
	@RequestMapping(value = "api/AccountReceivable/OpenOrCloseAlipay", method = RequestMethod.POST)
	@ResponseBody
	public void openOrCloseAlipay(@RequestBody LeLeasescompany leLeasescompany) {
		service.openOrCloseAlipay(leLeasescompany);
	}
	
	/**
	 * <p>
	 * 
	 * </p>
	 *
	 * @param LeLeasescompany
	 * @return
	 */
	@RequestMapping(value = "api/AccountReceivable/OpenOrCloseWechat", method = RequestMethod.POST)
	@ResponseBody
	public void openOrCloseWechat(@RequestBody LeLeasescompany leLeasescompany) {
		service.openOrCloseWechat(leLeasescompany);
	}


	/**
	 * <p>
	 *
	 * </p>
	 *
	 * @param leLeasescompany
	 * @return
	 */
	@RequestMapping(value = "api/AccountReceivable/driver/OpenOrCloseAlipay", method = RequestMethod.POST)
	@ResponseBody
	public void openOrCloseDriverAlipay(@RequestBody LeLeasescompany leLeasescompany) {
		service.openOrCloseDriverAlipay(leLeasescompany);
	}

	/**
	 * <p>
	 *
	 * </p>
	 *
	 * @param leLeasescompany
	 * @return
	 */
	@RequestMapping(value = "api/AccountReceivable/driver/OpenOrCloseWechat", method = RequestMethod.POST)
	@ResponseBody
	public void openOrCloseDriverWechat(@RequestBody LeLeasescompany leLeasescompany) {
		service.openOrCloseDriverWechat(leLeasescompany);
	}
}



