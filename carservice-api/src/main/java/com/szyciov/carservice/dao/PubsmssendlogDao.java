package com.szyciov.carservice.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.carservice.mapper.PubsmssendlogMapper;
import com.szyciov.entity.Pubsmssendlog;

@Repository("pubsmssendlogDao")
public class PubsmssendlogDao {
	private PubsmssendlogMapper mapper;

	@Resource
	public void setMapper(PubsmssendlogMapper mapper) {
		this.mapper = mapper;
	}
	public void insertPubsmssendlog(Pubsmssendlog pubsmssendlog){
		mapper.insertPubsmssendlog(pubsmssendlog);
	}

}
