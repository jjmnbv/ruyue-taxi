package com.szyciov.carservice.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.carservice.service.DriverHeartbeattimeService;

@Controller
public class DriverHeartbeattimeController {
	@Resource(name="driverHeartbeattimeService")
	private DriverHeartbeattimeService driverHeartbeattimeService;

	public void setDriverHeartbeattimeService(DriverHeartbeattimeService driverHeartbeattimeService) {
		this.driverHeartbeattimeService = driverHeartbeattimeService;
	}
	/**
	 * 司机心跳调度任务
	 */
	@RequestMapping("/api/DriverHeartbeattime/SetDriverHeartbeattime")
	@ResponseBody
	public void SetDriverHeartbeattime(){
		driverHeartbeattimeService.setDriverHeartbeattime();
	}

}
