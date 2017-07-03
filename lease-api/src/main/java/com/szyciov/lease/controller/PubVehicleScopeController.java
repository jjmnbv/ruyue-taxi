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

import com.szyciov.entity.City;
import com.szyciov.entity.Dictionary;
import com.szyciov.lease.entity.PubVehcbrand;
import com.szyciov.lease.entity.PubVehicle;
import com.szyciov.lease.service.PubVehicleScopeService;
import com.szyciov.util.BaseController;

/**
 * 控制器
 */
@Controller
public class PubVehicleScopeController extends BaseController {
	private static final Logger logger = Logger.getLogger(PubVehicleScopeController.class);

	public PubVehicleScopeService pubVehicleScopeService;

	@Resource(name = "PubVehicleScopeService")
	public void setPubVehicleService(PubVehicleScopeService pubVehicleScopeService) {
		this.pubVehicleScopeService = pubVehicleScopeService;
	}
	/**
	 * <p>
	 * 检查  载入 经营范围
	 * </p>
	 *
	 * @param PubVehcbrand
	 * @return
	 */
	@RequestMapping(value = "api/PubVehicleScope/CheckLoadAsDefault", method = RequestMethod.POST)
	@ResponseBody
	public int checkLoadAsDefault(@RequestBody Dictionary dictionary) {
		return pubVehicleScopeService.checkLoadAsDefault(dictionary);
	}
	
	/**
	 * <p>
	 * 载入常用数据
	 * </p>
	 *
	 * @param QueryParam
	 * @return
	 */
	@RequestMapping(value = "api/PubVehicleScope/LoadAsDefault", method = RequestMethod.POST)
	@ResponseBody
	public List<City> loadAsDefault(@RequestBody Dictionary dictionary) {
		return pubVehicleScopeService.loadAsDefault(dictionary);
	}
	
}
