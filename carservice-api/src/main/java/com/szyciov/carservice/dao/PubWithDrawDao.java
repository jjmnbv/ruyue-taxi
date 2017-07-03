package com.szyciov.carservice.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.carservice.mapper.PubWithDrawMapper;

@Repository("pubWithDrawDao")
public class PubWithDrawDao {
	public PubWithDrawMapper mapper;

	@Resource
	public void setMapper(PubWithDrawMapper mapper) {
		this.mapper = mapper;
	}
	
	public String getMaxPubWithDrawNo() {
		return mapper.getMaxPubWithDrawNo();
	}
}
