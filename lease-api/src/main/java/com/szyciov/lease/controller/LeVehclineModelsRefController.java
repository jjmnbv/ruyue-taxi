package com.szyciov.lease.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.lease.entity.LeVehclineModelsRef;
import com.szyciov.lease.service.LeVehclineModelsRefService;
import com.szyciov.util.BaseController;

/**
 * 控制器
 */
@Controller
public class LeVehclineModelsRefController extends BaseController {
	private static final Logger logger = Logger.getLogger(LeVehclineModelsRefController.class);

	public LeVehclineModelsRefService leVehclineModelsRefService;

	@Resource(name = "LeVehclineModelsRefService")
	public void setLeVehclineModelsRefService(LeVehclineModelsRefService leVehclineModelsRefService) {
		this.leVehclineModelsRefService = leVehclineModelsRefService;
	}

	/**
	 * <p>
	 * 增加一条记录
	 * </p>
	 *
	 * @param LeVehclineModelsRef
	 * @return
	 */
	@RequestMapping(value = "api/LeVehclineModelsRef/CreateLeVehclineModelsRef", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> createLeVehclineModelsRef(@RequestBody Map map) {
		return leVehclineModelsRefService.createLeVehclineModelsRef(map);
	}

}
