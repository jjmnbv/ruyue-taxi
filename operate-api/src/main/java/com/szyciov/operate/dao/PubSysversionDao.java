package com.szyciov.operate.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.entity.PubSysVersion;
import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.operate.mapper.PubSysversionMapper;
import com.szyciov.param.PubSysversionQueryParam;

@Repository("PubSysversionDao")
public class PubSysversionDao {
	
	private PubSysversionMapper mapper;
	@Resource
	public void setMapper(PubSysversionMapper mapper) {
		this.mapper = mapper;
	}
	
	public List<PubDictionary> getSystemtypeByList() {
		return mapper.getSystemtypeByList();
	}
	
	public List<PubSysVersion> getPubSysversionList(PubSysVersion object) {
		return mapper.getPubSysversionList(object);
	}
	
	public List<Map<String, String>> getCurversionByList(PubSysVersion object) {
		return mapper.getCurversionByList(object);
	}
	
	public List<PubSysVersion> getPubSysversionListByQuery(PubSysversionQueryParam queryParam) {
		return mapper.getPubSysversionListByQuery(queryParam);
	}
	
	public int getPubSysversionCountByQuery(PubSysversionQueryParam queryParam) {
		return mapper.getPubSysversionCountByQuery(queryParam);
	}
	
	public List<PubSysVersion> getPubSysversionById(String id) {
		return mapper.getPubSysversionById(id);
	}
	
	public void insertPubSysversion(PubSysVersion object) {
		mapper.insertPubSysversion(object);
	}
	
	public void updatePubSysversion(PubSysVersion object) {
		mapper.updatePubSysversion(object);
	}

}
