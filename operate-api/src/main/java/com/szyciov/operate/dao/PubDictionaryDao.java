package com.szyciov.operate.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.operate.mapper.PubDictionaryMapper;

@Repository("PubDictionaryDao")
public class PubDictionaryDao {
	
	private PubDictionaryMapper mapper;
	@Resource
	public void setMapper(PubDictionaryMapper mapper) {
		this.mapper = mapper;
	}
	
	public List<PubDictionary> getPubDictionaryByList(PubDictionary object) {
		return mapper.getPubDictionaryByList(object);
	}

}
