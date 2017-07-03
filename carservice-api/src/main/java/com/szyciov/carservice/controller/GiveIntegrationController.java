package com.szyciov.carservice.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.carservice.service.GiveIntegrationService;

@Controller
public class GiveIntegrationController {
	@Resource(name="giveIntegrationService")
	private GiveIntegrationService giveIntegrationService;

	public void setGiveIntegrationService(GiveIntegrationService giveIntegrationService) {
		this.giveIntegrationService = giveIntegrationService;
	}
	/**
	 * 赠送积分心跳
	 */
	@RequestMapping("/api/GiveIntegration/GiveIntegration")
	@ResponseBody
	public void giveIntegration(){
		giveIntegrationService.giveIntegration();
	}


}
