package com.szyciov.operate.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.op.entity.PubDriverExpenses;
import com.szyciov.op.param.PubDriverAccountQueryParam;
import com.szyciov.op.param.PubDriverExpensesQuerryParam;
import com.szyciov.operate.service.PubDriverAccountService;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;
/**
 * 司机管理 控制器
 * */
@Controller
public class PubDriverAccountController extends BaseController {
	private static final Logger logger = Logger.getLogger(PubDriverAccountController.class);

	public PubDriverAccountService service;

	@Resource(name = "PubDriverAccountService")
	public void setOrgOrganService(PubDriverAccountService service) {
		this.service = service;
	}
	
	/**
	 * @param PubDriverAccountQueryParam
	 * @return PageBean
	 */
	@RequestMapping(value = "api/PubDriverAccount/GetOrgDriverAccountByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getOrgDriverAccountByQuery(@RequestBody PubDriverAccountQueryParam orgDriverAccountQueryParam) {
		return service.getOrgDriverAccountByQuery(orgDriverAccountQueryParam);
	}
	
	/**
	 * <p>
	 * 首页加载  司机
	 * </p>
	 * @param queryDriver
	 * @return
	 */
	@RequestMapping(value = "api/PubDriverAccount/GetQueryDriver", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getQueryDriver(@RequestParam(value = "queryDriver", required = false) String queryDriver) {
		return service.getQueryDriver(queryDriver);
	}
	
	/**
	 * @param PubDriverExpensesQuerryParam
	 * @return PageBean
	 */
	@RequestMapping(value = "api/PubDriverAccount/GetDetailedByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getDetailedByQuery(@RequestBody PubDriverExpensesQuerryParam peDriverExpensesQuerryParam) {
		return service.getDetailedByQuery(peDriverExpensesQuerryParam);
	}
	
	/**
	 * @param PubDriverExpensesQuerryParam
	 * @return PageBean
	 */
	@RequestMapping(value = "api/PubDriverAccount/ExportData", method = RequestMethod.POST)
	@ResponseBody
	public List<PubDriverExpenses> exportData(@RequestBody PubDriverExpensesQuerryParam peDriverExpensesQuerryParam) {
		return service.exportData(peDriverExpensesQuerryParam);
	}
}
