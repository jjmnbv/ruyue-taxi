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

import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.org.entity.OrgOrderDetails;
import com.szyciov.org.param.OrgOrderQueryParam;
import com.szyciov.organ.service.OrgIndexService;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;

/**
 * 控制器
 */
@Controller
public class OrgIndexController extends BaseController {
	private static final Logger logger = Logger.getLogger(OrgIndexController.class);

	public OrgIndexService orgIndexService;

	@Resource(name = "OrgIndexService")
	public void setOrgOrganService(OrgIndexService orgIndexService) {
		this.orgIndexService = orgIndexService;
	}
	/**
	 * <p>
	 * 初始化、查询
	 * </p>
	 *
	 * @param QueryParam
	 * @return
	 */
	@RequestMapping(value = "api/OrgIndex/GetOrgderByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getOrgderByQuery(@RequestBody OrgOrderQueryParam orgOrderQueryParam) {
		return orgIndexService.getOrgderByQuery(orgOrderQueryParam);
	}
	
	/**
	 * <p>
	 * 
	 * </p>
	 *
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "api/OrgIndex/GetQueryCompany", method = RequestMethod.POST)
	@ResponseBody
	public List<LeLeasescompany> getQueryCompany(@RequestBody Map<String, Object> params) {
		return orgIndexService.getQueryCompany(params);
	}
	/**
	 * <p>
	 * 
	 * </p>
	 *
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "api/OrgIndex/GetById/{id}", method = RequestMethod.GET)
	@ResponseBody
	public OrgOrderDetails getById(@PathVariable String id) {
		return orgIndexService.getById(id);
	}
	
	/**
	 * <p>
	 * 
	 * </p>
	 *
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "api/OrgIndex/ExportExcel", method = RequestMethod.POST)
	@ResponseBody
	public List<OrgOrder> exportExcel(@RequestBody OrgOrderQueryParam orgOrderQueryParam) {
		return orgIndexService.exportExcel(orgOrderQueryParam);
	}
	
	
	/**
	 * 根据订单号获取订单信息
	 * @param orderno
	 * @return
	 */
	@RequestMapping(value = "api/OrgIndex/GetOrgOrderByOrderno/{orderno}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getOrgOrderByOrderno(@PathVariable String orderno) {
		return orgIndexService.getOrgOrderByOrderno(orderno);
	}
}
