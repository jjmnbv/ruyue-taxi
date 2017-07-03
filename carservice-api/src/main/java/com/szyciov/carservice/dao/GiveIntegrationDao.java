package com.szyciov.carservice.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.carservice.mapper.GiveIntegrationMapper;
import com.szyciov.lease.entity.PubDictionary;

@Repository("giveIntegrationDao")
public class GiveIntegrationDao {
	private GiveIntegrationMapper mapper;

	@Resource
	public void setMapper(GiveIntegrationMapper mapper) {
		this.mapper = mapper;
	}
	public PubDictionary findKeyValue(){
		return mapper.findKeyValue();
	}
	public void updateKeyText(){
		mapper.updateKeyText();
	}
	public List<String> findAllUsers(){
		return mapper.findAllUsers();
	}

}
