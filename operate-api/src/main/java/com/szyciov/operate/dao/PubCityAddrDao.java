package com.szyciov.operate.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.operate.mapper.PubCityAddrMapper;

@Repository("PubCityAddrDao")
public class PubCityAddrDao {
	
	private PubCityAddrMapper mapper;
	@Resource
	public void setMapper(PubCityAddrMapper mapper) {
		this.mapper = mapper;
	}
	
	public List<PubCityAddr> getPubCityAddrByList() {
		return mapper.getPubCityAddrByList();
	}

}
