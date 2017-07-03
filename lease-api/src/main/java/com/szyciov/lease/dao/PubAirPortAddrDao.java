package com.szyciov.lease.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.entity.PubAirPortAddr;
import com.szyciov.lease.mapper.PubAirPortAddrMapper;

@Repository("PubAirPortAddrDao")
public class PubAirPortAddrDao {
	private PubAirPortAddrMapper mapper;

	@Resource
	public void setMapper(PubAirPortAddrMapper mapper) {
		this.mapper = mapper;
	}
	
	public List<PubAirPortAddr> getPubAirPortAddrList(String city){
		return mapper.getPubAirPortAddrList(city);
	}
}
