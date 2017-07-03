package com.szyciov.operate.mapper;

import java.util.List;
import java.util.Map;

import com.szyciov.op.entity.PubVehcbrand;
import com.szyciov.op.param.PubVehcbrandQueryParam;

public interface PubVehcbrandMapper {

	void createPubVehcbrand(PubVehcbrand pubVehcbrand);
	
	void updatePubVehcbrand(PubVehcbrand pubVehcbrand);
	
	int checkDelete(String id);
	
	void deletePubVehcbrand(String id);
	
	List<PubVehcbrand> getPubVehcbrandListByQuery(PubVehcbrandQueryParam queryParam);
	
	int getPubVehcbrandListCountByQuery(PubVehcbrandQueryParam queryParam);
	
	List<Map<String, Object>> getBrand(PubVehcbrand pubVehcbrand);
	
	PubVehcbrand getById(String id);
	
	List<PubVehcbrand> exportData(Map<String, String> map);
	
	int checkBrand(PubVehcbrand pubVehcbrand);
	
	PubVehcbrand getByName(PubVehcbrand pubVehcbrand);
}