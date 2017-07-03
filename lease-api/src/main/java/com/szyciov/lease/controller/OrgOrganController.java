package com.szyciov.lease.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.lease.entity.OrgOrgan;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.param.OrgOrganQueryParam;
import com.szyciov.lease.service.OrgOrganService;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;

/**
 * 控制器
 */
@Controller
public class OrgOrganController extends BaseController {
	private static final Logger logger = Logger.getLogger(OrgOrganController.class);

	public OrgOrganService orgOrganService;

	@Resource(name = "OrgOrganService")
	public void setOrgOrganService(OrgOrganService orgOrganService) {
		this.orgOrganService = orgOrganService;
	}

	/**
	 * <p>
	 * 增加一条记录
	 * </p>
	 *
	 * @param orgOrgan
	 * @return
	 */
	@RequestMapping(value = "api/OrgOrgan/CreateOrgOrgan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> createOrgOrgan(@RequestBody OrgOrgan orgOrgan) {
		return orgOrganService.createOrgOrgan(orgOrgan);
	}

	/**
	 * <p>
	 * 更新一条记录
	 * </p>
	 *
	 * @param orgOrgan
	 * @return
	 */
	@RequestMapping(value = "api/OrgOrgan/UpdateOrgOrgan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updateOrgOrgan(@RequestBody OrgOrgan orgOrgan) {
		return orgOrganService.updateOrgOrgan(orgOrgan);
	}
	
	/**
	 * <p>
	 * 
	 * </p>
	 *
	 * @param orgOrgan
	 * @return
	 */
	@RequestMapping(value = "api/OrgOrgan/GetOrgOrganShortName", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getOrgOrganShortName(@RequestBody OrgOrganQueryParam orgOrganQueryParam) {
		return orgOrganService.getOrgOrganShortName(orgOrganQueryParam);
	}
	
	/**
	 * <p>
	 * 
	 * </p>
	 *
	 * @param orgOrgan
	 * @return
	 */
	@RequestMapping(value = "api/OrgOrgan/GetOrgOrganCity", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getOrgOrganCity(@RequestBody OrgOrganQueryParam orgOrganQueryParam) {
		return orgOrganService.getOrgOrganCity(orgOrganQueryParam);
	}
	
	/**
	 * <p>
	 * 
	 * </p>
	 *
	 * @param orgOrgan
	 * @return
	 */
	@RequestMapping(value = "api/OrgOrgan/GetOrgOrganByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getOrgOrganByQuery(@RequestBody OrgOrganQueryParam orgOrganQueryParam) {
		return orgOrganService.getOrgOrganByQuery(orgOrganQueryParam);
	}
	
	/**
	 * <p>
	 * 
	 * </p>
	 *
	 * @param orgOrgan
	 * @return
	 */
	@RequestMapping(value = "api/OrgOrgan/GetByOrgOrganId", method = RequestMethod.POST)
	@ResponseBody
	public OrgOrgan getByOrgOrganId(@RequestBody OrgOrgan orgOrgan) {
		return orgOrganService.getByOrgOrganId(orgOrgan);
	}
	/**
	 * <p>
	 * 
	 * </p>
	 *
	 * @param orgOrgan
	 * @return
	 */
	@RequestMapping(value = "api/OrgOrgan/ResetPassword/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> resetPassword(@PathVariable String id) {
		return orgOrganService.resetPassword(id);
	}
	
	/** 
	 * 
	 *机构的导出
	 * @param
	 */
	@RequestMapping(value = "api/OrgOrgan/ExportData", method = RequestMethod.POST)
	@ResponseBody
	public List<OrgOrgan> exportData(@RequestBody OrgOrganQueryParam orgOrganQueryParam) {
		return orgOrganService.exportData(orgOrganQueryParam);
	}
	
	/** 
	 * 
	 *
	 * @param
	 */
	@RequestMapping(value = "api/OrgOrgan/GetFristTime", method = RequestMethod.POST)
	@ResponseBody
	public int getFristTime(@RequestBody OrgOrgan orgOrgan) {
		return orgOrganService.getFristTime(orgOrgan);
	}
	
	/** 
	 * 
	 *
	 * @param
	 */
	@RequestMapping(value = "api/OrgOrgan/CheckOrgOrgan", method = RequestMethod.POST)
	@ResponseBody
	public int checkOrgOrgan(@RequestBody OrgOrgan orgOrgan) {
		return orgOrganService.checkOrgOrgan(orgOrgan);
	}
	
	/** 
	 * 
	 *
	 * @param
	 */
	@RequestMapping(value = "api/OrgOrgan/CheckAccount", method = RequestMethod.POST)
	@ResponseBody
	public int checkAccount(@RequestBody OrgOrgan orgOrgan) {
		return orgOrganService.checkAccount(orgOrgan);
	}
	
	@RequestMapping(value = "api/OrgOrgan/GetOrgOrganByName", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getOrgOrganByName(
			@RequestParam(value = "organName", required = false) String organName, 
			@RequestParam String leasescompanyid) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("organName", organName);
		params.put("leasescompanyid", leasescompanyid);
		
		return orgOrganService.getOrgOrganByName(params);
	}
	
	/** 
	 * 
	 *城市名字 反向 查城市id
	 * @param
	 */
	@RequestMapping(value = "api/OrgOrgan/GetCityId/{cityName}", method = RequestMethod.GET)
	@ResponseBody
	public PubCityAddr getCityId(@PathVariable String cityName) {
		return orgOrganService.getCityId(cityName);
	}
	
	/** 
	 * <p>分页查询信用额度变更记录</p>
	 *
	 * @param queryParam 查询请求对象，封装需要查询的key和页码等信息
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "api/OrgOrgan/GetOrganCreditRecord", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getOrganCreditRecord(@RequestBody OrgOrganQueryParam orgOrganQueryParam) {
		return orgOrganService.getOrganCreditRecord(orgOrganQueryParam);
	}
	
	/** 
	 * 
	 *
	 * @param
	 */
	@RequestMapping(value = "api/OrgOrgan/CheckOrgOrganAccout", method = RequestMethod.POST)
	@ResponseBody
	public int checkOrgOrganAccout(@RequestBody OrgOrganQueryParam orgOrganQueryParam) {
		return orgOrganService.checkOrgOrganAccout(orgOrganQueryParam);
	}
}
