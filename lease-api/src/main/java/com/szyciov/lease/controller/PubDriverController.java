package com.szyciov.lease.controller;

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

import com.szyciov.lease.entity.PubDriver;
import com.szyciov.lease.param.PubDriverQueryParam;
import com.szyciov.lease.param.ServiceOrgQueryParam;
import com.szyciov.lease.service.PubDriverService;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;

/**
 * 控制器
 */
@Controller
public class PubDriverController extends BaseController {
	private static final Logger logger = Logger.getLogger(PubDriverController.class);

	public PubDriverService pubDriverService;

	@Resource(name = "PubDriverService")
	public void setPubDriverService(PubDriverService pubDriverService) {
		this.pubDriverService = pubDriverService;
	}

	/**
	 * <p>
	 * 增加一条记录
	 * </p>
	 *
	 * @param PubDriver
	 * @return
	 */
	@RequestMapping(value = "api/PubDriver/CreatePubDriver", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> createPubDriver(@RequestBody PubDriver pubDriver) {
		return pubDriverService.createPubDriver(pubDriver);
	}

	/**
	 * <p>
	 * 查询
	 * </p>
	 *
	 * @param PubDriver
	 * @return
	 */
	@RequestMapping(value = "api/PubDriver/GetPubDriverByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getPubDriverByQuery(@RequestBody PubDriverQueryParam pubDriverQueryParam){
		return pubDriverService.getPubDriverByQuery(pubDriverQueryParam);
	}
	
	/**
	 * 获取范围内的司机
	 * @return
	 */
	@RequestMapping(value="api/PubDriver/GetPubDriverByBound")
	@ResponseBody
	public List<PubDriver> getPubDriverByBound(@RequestBody PubDriverQueryParam pubDriverQueryParam){
		return pubDriverService.getPubDriverByBound(pubDriverQueryParam);
	}
	
	/**
	 * <p>
	 * 查询
	 * </p>
	 *
	 * @param 
	 * @return orgOrganQueryParam
	 */
	@RequestMapping(value = "api/PubDriver/GetServiceOrgByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getServiceOrgByQuery(@RequestBody ServiceOrgQueryParam serviceOrgQueryParam){
		return pubDriverService.getServiceOrgByQuery(serviceOrgQueryParam);
	}
	
	/**
	 * <p>
	 * 得到城市
	 * </p>
	 *
	 * @param QueryParam
	 * @return
	 */
	@RequestMapping(value = "api/PubDriver/GetCity", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getCity(@RequestBody PubDriverQueryParam pubDriverQueryParam) {
		return pubDriverService.getCity(pubDriverQueryParam);
	}
	
	/**
	 * <p>
	 * 根据id查询
	 * </p>
	 *
	 * @param PubVehcbrand
	 * @return
	 */
	@RequestMapping(value = "api/PubDriver/GetById/{id}", method = RequestMethod.GET)
	@ResponseBody
	public PubDriver getById(@PathVariable String id) {
		return pubDriverService.getById(id);
	}
	
//	/**
//	 * <p>
//	 * 解绑车辆
//	 * </p>
//	 *
//	 * @param 
//	 * @return
//	 */
//	@RequestMapping(value = "api/PubDriver/UnwrapVel/{id}", method = RequestMethod.GET)
//	@ResponseBody
//	public PubDriver unwrapVel(@PathVariable String id) {
//		return pubDriverService.unwrapVel(id);
//	}
	
	/**
	 * <p>
	 * 根据id查询   查解绑
	 * </p>
	 *
	 * @param PubVehcbrand
	 * @return
	 */
	@RequestMapping(value = "api/PubDriver/CheckDelete/{id}", method = RequestMethod.GET)
	@ResponseBody
	public int checkDelete(@PathVariable String id) {
		return pubDriverService.judgeBinding(id);
	}
	
	/**
	 * <p>
	 * 根据id查询   查解绑
	 * </p>
	 *
	 * @param PubVehcbrand
	 * @return
	 */
	@RequestMapping(value = "api/PubDriver/UpdatePubDriver", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updatePubDriver(@RequestBody PubDriver pubDriver) {
		return pubDriverService.updatePubDriver(pubDriver);
	}
	
	/** 
	 * <p>删除一条记录</p>
	 *
	 * @param id 待删除记录对应的序列号
	 */
	@RequestMapping(value = "api/PubDriver/DeletePubDriver/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Map<String, String> deletePubDriver(@PathVariable String id) {
		return pubDriverService.deletePubDriver(id);
	}
	
	/** 
	 * 
	 *
	 * @param id 待删除记录对应的序列号
	 */
	@RequestMapping(value = "api/PubDriver/GetSpecialDri", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getSpecialDri(@RequestBody PubDriverQueryParam pubDriverQueryParam) {
		return pubDriverService.getSpecialDri(pubDriverQueryParam);
	}
	
	/** 
	 * 
	 *
	 * @param
	 */
	@RequestMapping(value = "api/PubDriver/ResetPassword/{id}/{leasesCompanyId}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> resetPassword(@PathVariable String id,@PathVariable String leasesCompanyId) {
		return pubDriverService.resetPassword(id,leasesCompanyId);
	}
	
	/** 
	 * 
	 *导出 司机
	 * @param
	 */
	@RequestMapping(value = "api/PubDriver/ExportData", method = RequestMethod.POST)
	@ResponseBody
	public List<PubDriver> exportData(@RequestBody PubDriverQueryParam pubDriverQueryParam) {
		return pubDriverService.exportData(pubDriverQueryParam);
	}
	
	/** 
	 * 
	 *
	 * @param
	 */
	@RequestMapping(value = "api/PubDriver/CheckPubDriver", method = RequestMethod.POST)
	@ResponseBody
	public int checkPubDriver(@RequestBody PubDriver pubDriver) {
		return pubDriverService.checkPubDriver(pubDriver);
	}
	
	/** 
	 * 检查  司机的唯一性，在平台上唯一 
	 * @param
	 */
	@RequestMapping(value = "api/PubDriver/CheckPubDriverPhone", method = RequestMethod.POST)
	@ResponseBody
	public int checkPubDriverPhone(@RequestBody PubDriver pubDriver) {
		return pubDriverService.checkPubDriverPhone(pubDriver);
	}
	
	/** 
	 * 检查  司机的解绑、 在服务中、有预约订单的不能解绑
	 * @param
	 */
	@RequestMapping(value = "api/PubDriver/CheckUnbundling/{driverId}", method = RequestMethod.GET)
	@ResponseBody
	public int checkUnbundling(@PathVariable String driverId) {
		return pubDriverService.checkUnbundling(driverId);
	}
	/** 
	 * 
	 *
	 * @param 
	 */
	@RequestMapping(value = "api/PubDriver/GetQueryJobNum", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getQueryJobNum(@RequestBody PubDriverQueryParam pubDriverQueryParam) {
		return pubDriverService.getQueryJobNum(pubDriverQueryParam);
	}
}
