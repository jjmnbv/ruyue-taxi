package com.szyciov.lease.controller;

import com.szyciov.lease.param.OrderManageQueryParam;
import com.szyciov.lease.service.TmpOrderManageService;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class TmpOrderManageController extends BaseController {

    @Resource(name = "TmpOrderManageService")
	private TmpOrderManageService tmpOrderManageService;

	/**
	 * 查询订单列表
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "api/TmpOrderManage/GetOrgOrderByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getOrgOrderByQuery(@RequestBody OrderManageQueryParam queryParam) {
		return this.tmpOrderManageService.getOrgOrderByQuery(queryParam);
	}

}
