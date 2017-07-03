package com.szyciov.carservice.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szyciov.carservice.dao.DriverHeartbeattimeDao;

@Service("driverHeartbeattimeService")
public class DriverHeartbeattimeService {
	private DriverHeartbeattimeDao driverHeartbeattimeDao;

	@Resource(name="driverHeartbeattimeDao")
	public void setDriverHeartbeattimeDao(DriverHeartbeattimeDao driverHeartbeattimeDao) {
		this.driverHeartbeattimeDao = driverHeartbeattimeDao;
	}
	public void setDriverHeartbeattime(){
		driverHeartbeattimeDao.setDriverHeartbeattime();
	}

}
