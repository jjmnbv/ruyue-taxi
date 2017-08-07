package com.szyciov.operate.controller;

import com.szyciov.lease.param.OrderManageQueryParam;
import com.szyciov.operate.service.TmpOrderManageService;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 运营端订单管理
 */
@Controller
public class TmpOrderManageController extends BaseController {

    @Resource(name = "TmpOrderManageService")
	private TmpOrderManageService orderManageService;

	/**
	 * 分页查询订单数据
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "api/TmpOrderManage/GetOpOrderByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getOpOrderByQuery(@RequestBody OrderManageQueryParam queryParam) {
		return orderManageService.getOpOrderByQuery(queryParam);
	}
	
}
