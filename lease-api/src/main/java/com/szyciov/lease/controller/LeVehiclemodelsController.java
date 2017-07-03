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

import com.szyciov.lease.entity.LeVehiclemodels;
import com.szyciov.lease.entity.PubVehcbrand;
import com.szyciov.lease.entity.PubVehcline;
import com.szyciov.lease.service.LeVehiclemodelsService;
import com.szyciov.param.QueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;

import net.sf.json.JSONObject;

/**
 * 控制器
 */
@Controller
public class LeVehiclemodelsController extends BaseController {
	private static final Logger logger = Logger.getLogger(LeVehiclemodelsController.class);

	public LeVehiclemodelsService leVehiclemodelsService;

	@Resource(name = "LeVehiclemodelsService")
	public void setLeVehiclemodelsService(LeVehiclemodelsService leVehiclemodelsService) {
		this.leVehiclemodelsService = leVehiclemodelsService;
	}

	/**
	 * <p>
	 * 增加一条记录
	 * </p>
	 *
	 * @param LeVehiclemodels
	 * @return
	 */
	@RequestMapping(value = "api/LeVehiclemodels/CreateLeVehiclemodels", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> createLeVehiclemodels(@RequestBody LeVehiclemodels leVehiclemodels) {
		return leVehiclemodelsService.createLeVehiclemodels(leVehiclemodels);
	}

	/**
	 * <p>
	 * 初始化、查询
	 * </p>
	 *
	 * @param QueryParam
	 * @return
	 */
	@RequestMapping(value = "api/LeVehiclemodels/GetLeVehiclemodelsByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getLeVehiclemodelsByQuery(@RequestBody QueryParam queryParam) {
		return leVehiclemodelsService.getLeVehiclemodelsByQuery(queryParam);
	}
	
	/**
	 * <p>
	 * 修改一条记录
	 * </p>
	 *
	 * @param PubVehcbrand
	 * @return
	 */
	@RequestMapping(value = "api/LeVehiclemodels/UpdateLeVehiclemodels", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updateLeVehiclemodels(@RequestBody LeVehiclemodels leVehiclemodels) {
		return leVehiclemodelsService.updateLeVehiclemodels(leVehiclemodels);
	}
	
	/**
	 * <p>
	 * 根据id查询
	 * </p>
	 *
	 * @param PubVehcbrand
	 * @return
	 */
	@RequestMapping(value = "api/LeVehiclemodels/GetById/{id}", method = RequestMethod.GET)
	@ResponseBody
	public LeVehiclemodels getById(@PathVariable String id) {
		return leVehiclemodelsService.getById(id);
	}
	
	/** 
	 * <p>删除一条记录</p>
	 *
	 * @param id 待删除记录对应的序列号
	 */
	@RequestMapping(value = "api/LeVehiclemodels/DeleteLeVehiclemodels/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Map<String, String> deleteLeVehiclemodels(@PathVariable String id) {
		return leVehiclemodelsService.deleteLeVehiclemodels(id);
	}
	
	/** 
	 * 
	 *
	 * @param 
	 */
	@RequestMapping(value = "api/LeVehiclemodels/CheckLeVehiclemodels", method = RequestMethod.POST)
	@ResponseBody
	public int getPubVehcbrand(@RequestBody LeVehiclemodels leVehiclemodels) {
		return leVehiclemodelsService.checkLeVehiclemodels(leVehiclemodels);
	}
	
	/** 
	 * 
	 *
	 * @param 
	 */
	@RequestMapping(value = "api/LeVehiclemodels/GetPubVehcline/{leasesCompanyId}", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getPubVehcline(@PathVariable String leasesCompanyId) {
		return leVehiclemodelsService.getPubVehcline(leasesCompanyId);
	}
	
	/** 
	 * 
	 *
	 * @param 
	 */
	@RequestMapping(value = "api/LeVehiclemodels/CheckDisable/{id}", method = RequestMethod.GET)
	@ResponseBody
	public int checkDisable(@PathVariable String id) {
		return leVehiclemodelsService.checkDisable(id);
	}
	
	/**
	 * <p>
	 * 
	 * </p>
	 *
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "api/LeVehiclemodels/UpdateEnableOrDisable", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updateEnableOrDisable(@RequestBody LeVehiclemodels leVehiclemodels) {
		return leVehiclemodelsService.updateEnableOrDisable(leVehiclemodels);
	}
}
