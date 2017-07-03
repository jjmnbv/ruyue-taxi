package com.szyciov.operate.mapper;

import java.util.List;
import java.util.Map;

import com.szyciov.entity.PubSysVersion;
import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.param.PubSysversionQueryParam;

public interface PubSysversionMapper {
	
	List<PubDictionary> getSystemtypeByList();
	
	List<PubSysVersion> getPubSysversionList(PubSysVersion object);
	
	List<Map<String, String>> getCurversionByList(PubSysVersion object);
	
	List<PubSysVersion> getPubSysversionListByQuery(PubSysversionQueryParam queryParam);
	
	int getPubSysversionCountByQuery(PubSysversionQueryParam queryParam);
	
	List<PubSysVersion> getPubSysversionById(String id);
	
	void insertPubSysversion(PubSysVersion object);
	
	void updatePubSysversion(PubSysVersion object);

}
