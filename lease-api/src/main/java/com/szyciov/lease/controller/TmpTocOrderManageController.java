package com.szyciov.lease.controller;

import com.szyciov.lease.param.TocOrderManageQueryParam;
import com.szyciov.lease.service.TmpTocOrderManageService;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * toC订单管理模块控制器
 */
@Controller
public class TmpTocOrderManageController extends BaseController {

    @Resource(name = "TmpTocOrderManageService")
	public TmpTocOrderManageService tmpTocOrderManageService;

	/** 
	 * <p>分页查询网约车订单</p>
	 *
	 * @param queryParam 查询请求对象，封装需要查询的key和页码等信息
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "api/TmpOrderManage/GetNetAboutCarOrderByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getNetAboutCarOrderByQuery(@RequestBody TocOrderManageQueryParam queryParam) {
		return tmpTocOrderManageService.getNetAboutCarOrderByQuery(queryParam);
	}
	
	/** 
	 * <p>分页查询出租车订单</p>
	 *
	 * @param queryParam 查询请求对象，封装需要查询的key和页码等信息
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "api/TmpOrderManage/GetTaxiOrderByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getTaxiOrderByQuery(@RequestBody TocOrderManageQueryParam queryParam) {
		return tmpTocOrderManageService.getTaxiOrderByQuery(queryParam);
	}
}
