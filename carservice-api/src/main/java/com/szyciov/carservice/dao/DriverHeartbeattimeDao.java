package com.szyciov.carservice.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.carservice.mapper.DriverHeartbeattimeMapper;

@Repository("driverHeartbeattimeDao")
public class DriverHeartbeattimeDao {
	private DriverHeartbeattimeMapper mapper;

	@Resource
	public void setMapper(DriverHeartbeattimeMapper mapper) {
		this.mapper = mapper;
	}
	public void setDriverHeartbeattime(){
		mapper.setDriverHeartbeattime();
	}

}
