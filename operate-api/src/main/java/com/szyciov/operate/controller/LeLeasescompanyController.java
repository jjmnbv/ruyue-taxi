package com.szyciov.operate.controller;

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

import com.szyciov.entity.City;
import com.szyciov.entity.Dictionary;
import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.op.entity.PubDriver;
import com.szyciov.lease.param.PubDriverQueryParam;
import com.szyciov.op.entity.OpVehiclemodels;
import com.szyciov.op.entity.OpVehiclemodelsVehicleRef;
import com.szyciov.op.param.LeLeasescompanyQueryParam;
import com.szyciov.operate.service.LeLeasescompanyService;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;

/**
 * 客户管理
 */
@Controller
public class LeLeasescompanyController extends BaseController {
	private static final Logger logger = Logger.getLogger(LeLeasescompanyController.class);

	public LeLeasescompanyService leLeasescompanyService;

	@Resource(name = "LeLeasescompanyService")
	public void setLeLeasescompanyService(LeLeasescompanyService leLeasescompanyService) {
		this.leLeasescompanyService = leLeasescompanyService;
	}
	
	/** 
	 * <p></p>
	 *
	 * @param
	 * @return
	 */
	@RequestMapping(value = "api/LeLeasescompany/GetLeLeasescompanyByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getLeLeasescompanyByQuery(@RequestBody LeLeasescompanyQueryParam queryParam) {
		return leLeasescompanyService.getLeLeasescompanyByQuery(queryParam);
	}
	
	/** 
	 * <p></p>
	 *
	 * @param
	 * @return
	 */
	@RequestMapping(value = "api/LeLeasescompany/GetNameOrCity", method = RequestMethod.POST)
	@ResponseBody
	public List<LeLeasescompany> getNameOrCity(@RequestBody LeLeasescompanyQueryParam queryParam) {
		return leLeasescompanyService.getNameOrCity(queryParam);
	}
	
	/** 
	 * 
	 *
	 * @param
	 * @return
	 */
	@RequestMapping(value = "api/LeLeasescompany/GetCityOrName", method = RequestMethod.POST)
	@ResponseBody
	public List<LeLeasescompany> getCityOrName(@RequestBody LeLeasescompanyQueryParam queryParam) {
		return leLeasescompanyService.getCityOrName(queryParam);
	}
	
	/** 
	 * 
	 *
	 * @param
	 * @return
	 */
	@RequestMapping(value = "api/LeLeasescompany/Enable/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> enable(@PathVariable String id) {
		return leLeasescompanyService.enable(id);
	}
	
	/** 
	 * 
	 *
	 * @param
	 * @return
	 */
	@RequestMapping(value = "api/LeLeasescompany/Disable/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> disable(@PathVariable String id) {
		return leLeasescompanyService.disable(id);
	}
	
	/** 
	 * 
	 *
	 * @param
	 * @return
	 */
	@RequestMapping(value = "api/LeLeasescompany/GetById/{id}", method = RequestMethod.GET)
	@ResponseBody
	public LeLeasescompany getById(@PathVariable String id) {
		return leLeasescompanyService.getById(id);
	}
	
	/** 
	 * 
	 *审核不通过
	 * @param
	 * @return
	 */
	@RequestMapping(value = "api/LeLeasescompany/DisableToc/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> disableToc(@PathVariable String id) {
		return leLeasescompanyService.disableToc(id);
	}
	/** 
	 * 
	 *禁用
	 * @param
	 * @return
	 */
	@RequestMapping(value = "api/LeLeasescompany/DisableTocs/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> disableTocs(@PathVariable String id) {
		return leLeasescompanyService.disableTocs(id);
	}
	/** 
	 * 
	 *
	 * @param
	 * @return
	 */
	@RequestMapping(value = "api/LeLeasescompany/ExamineToc/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> examineToc(@PathVariable String id) {
		return leLeasescompanyService.examineToc(id);
	}
	

	/** 
	 * 
	 *
	 * @param
	 * @return
	 */
	@RequestMapping(value = "api/LeLeasescompany/ResetPassword/{id}/{account}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> resetPassword(@PathVariable String id,@PathVariable String account) {
		return leLeasescompanyService.resetPassword(id,account);
	}
	
	/**
	 * <p>
	 * 得到pub_cityaddr
	 * </p>
	 *
	 * @param QueryParam
	 * @return
	 */
	@RequestMapping(value = "api/LeLeasescompany/GetPubCityaddr", method = RequestMethod.GET)
	@ResponseBody
	public List<City> getPubCityaddr() {
		return leLeasescompanyService.getPubCityaddr();
	}
	
	/**
	 * <p>
	 * 
	 * </p>
	 *
	 * @param QueryParam
	 * @return
	 */
	@RequestMapping(value = "api/LeLeasescompany/CheckNameOrShortName", method = RequestMethod.POST)
	@ResponseBody
	public int checkNameOrShortName(@RequestBody LeLeasescompany leLeasescompany) {
		return leLeasescompanyService.checkNameOrShortName(leLeasescompany);
	}
	
	/**
	 * <p>
	 * 
	 * </p>
	 *
	 * @param QueryParam
	 * @return
	 */
	@RequestMapping(value = "api/LeLeasescompany/CreateLeLeasescompany", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> createLeLeasescompany(@RequestBody LeLeasescompany leLeasescompany) {
		return leLeasescompanyService.createLeLeasescompany(leLeasescompany);
	}
	
	/**
	 * <p>
	 * 
	 * </p>
	 *
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "api/LeLeasescompany/UpdateLeLeasescompany", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updateLeLeasescompany(@RequestBody LeLeasescompany leLeasescompany) {
		return leLeasescompanyService.updateLeLeasescompany(leLeasescompany);
	}
	
	/**
	 * <p>
	 * 
	 * </p>
	 *
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "api/LeLeasescompany/GetPubDriverByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getPubDriverByQuery(@RequestBody PubDriverQueryParam pubDriverQueryParam) {
		return leLeasescompanyService.getPubDriverByQuery(pubDriverQueryParam);
	}
	/** 
	 * <p>根据字典类型，查询对应的字典项</p>
	 *
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "api/LeLeasescompany/GetDictionaryByType/{type}", method = RequestMethod.GET)
	@ResponseBody
	public List<Dictionary> getDictionaryByType(@PathVariable String type) {
		return leLeasescompanyService.getDictionaryByType(type);
	}
	
	/** 
	 * <p>根据字典类型，查询对应的字典项</p>
	 *
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "api/LeLeasescompany/GetCity/{leasesCompanyId}", method = RequestMethod.GET)
	@ResponseBody
	public List<PubDriver> getCity(@PathVariable String leasesCompanyId) {
		return leLeasescompanyService.getCity(leasesCompanyId);
	}
	
	/** 
	 * 
	 *
	 * @param
	 * @return
	 */
	@RequestMapping(value = "api/LeLeasescompany/GetByIdPubDriver/{id}", method = RequestMethod.GET)
	@ResponseBody
	public PubDriver getByIdPubDriver(@PathVariable String id) {
		return leLeasescompanyService.getByIdPubDriver(id);
	}
	
	/** 
	 * 
	 *
	 * @param
	 * @return
	 */
	@RequestMapping(value = "api/LeLeasescompany/GetOpVehiclemodels", method = RequestMethod.POST)
	@ResponseBody
	public List<OpVehiclemodels> getOpVehiclemodels() {
		return leLeasescompanyService.getOpVehiclemodels();
	}
	
	/** 
	 * 
	 *
	 * @param
	 * @return
	 */
	@RequestMapping(value = "api/LeLeasescompany/createOpVehclineModelsRef", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> createOpVehclineModelsRef(@RequestBody OpVehiclemodelsVehicleRef opVehclineModelsRef) {
		return leLeasescompanyService.createOpVehclineModelsRef(opVehclineModelsRef);
	}
	
	/** 
	 * 
	 *
	 * @param
	 * @return
	 */
	@RequestMapping(value = "api/LeLeasescompany/ExportData", method = RequestMethod.POST)
	@ResponseBody
	public List<LeLeasescompany> exportData(@RequestBody LeLeasescompanyQueryParam leLeasescompanyQueryParam) {
		return leLeasescompanyService.exportData(leLeasescompanyQueryParam);
	}
	
	/** 
	 * 
	 *城市名字 反向 查城市id
	 * @param
	 */
	@RequestMapping(value = "api/LeLeasescompany/GetCityId/{cityName}", method = RequestMethod.GET)
	@ResponseBody
	public PubCityAddr getCityId(@PathVariable String cityName) {
		return leLeasescompanyService.getCityId(cityName);
	}
	
	/** 
	 * 
	 *
	 * @param
	 */
	@RequestMapping(value = "api/LeLeasescompany/GetQueryKeyword", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getQueryKeyword(@RequestBody PubDriverQueryParam pubDriverQueryParam) {
		return leLeasescompanyService.getQueryKeyword(pubDriverQueryParam);
	}
}
