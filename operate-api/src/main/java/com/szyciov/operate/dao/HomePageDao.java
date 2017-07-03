package com.szyciov.operate.dao;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.operate.mapper.HomePageMapper;


@Repository("HomePageDao")
public class HomePageDao {
	private HomePageMapper mapper;

	@Resource
	public void setMapper(HomePageMapper mapper) {
		this.mapper = mapper;
	}
	public Map<String,Object> getCompayData(Map<String,Object> homeDataParam){
		return mapper.getCompayData(homeDataParam);
	}
	public Map<String,Object> getDataByTime(Map<String,Object> homeDataParam){
		return mapper.getDataByTime(homeDataParam);
	}

}
