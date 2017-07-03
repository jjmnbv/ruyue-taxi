package com.szyciov.operate.controller;

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
import com.szyciov.operate.service.OpUserAccountService;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;

/**
 * 个人账户模块控制器
 */
@Controller
public class OpUserAccountController extends BaseController {
	private static final Logger logger = Logger.getLogger(OpUserAccountController.class);

	public OpUserAccountService opUserAccountService;

	@Resource(name = "opUserAccountService")
	public void setOpUserAccountService(OpUserAccountService opUserAccountService) {
		this.opUserAccountService = opUserAccountService;
	}
	
	/** 
	 * <p>分页查询个人客户账户信息</p>
	 *
	 * @param queryParam 查询请求对象，封装需要查询的key和页码等信息
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "api/OpUserAccount/GetOpUserAccountByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getOpUserAccountByQuery(@RequestBody OrganUserAccountQueryParam queryParam) {
		return opUserAccountService.getOpUserAccountByQuery(queryParam);
	}
	
	/** 
	 * <p>分页查询账户往来明细</p>
	 *
	 * @param queryParam 查询请求对象，封装需要查询的key和页码等信息
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "api/OpUserAccount/GetUserExpensesByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getUserExpensesByQuery(@RequestBody OrganUserAccountQueryParam queryParam) {
		return opUserAccountService.getUserExpensesByQuery(queryParam);
	}
	
	/** 
	 * <p>根据关键字查找用户</p>
	 *   
	 * @param nameAccount 关键字
	 * @return 
	 */
	@RequestMapping(value = "api/OpUserAccount/GetExistUserList", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getExistUserList(@RequestParam(value = "nameAccount", required = false) String nameAccount) {
		logger.log(Level.INFO, "nameAccount:" + nameAccount);
		return opUserAccountService.getExistUserList(nameAccount);
	}
	
	/** 
	 * <p>查询导出数据</p>
	 *
	 * @param OrganUserAccountQueryParam
	 * @return
	 */
	@RequestMapping(value = "api/OpUserAccount/GetUserExpensesListExport", method = RequestMethod.POST)
	@ResponseBody
	public List<OrgUserExpenses> getUserExpensesListExport(@RequestBody OrganUserAccountQueryParam queryParam) {
		return opUserAccountService.getUserExpensesListExport(queryParam);
	}
	
	/** 
	 * <p>查询账户往来明细数量</p>
	 *
	 * @param OrganUserAccountQueryParam
	 * @return
	 */
	@RequestMapping(value = "api/OpUserAccount/GetUserExpensesCount", method = RequestMethod.POST)
	@ResponseBody
	public int getUserExpensesCount(@RequestBody OrganUserAccountQueryParam queryParam) {
		return opUserAccountService.getUserExpensesListCountByQuery(queryParam);
	}
	
}
