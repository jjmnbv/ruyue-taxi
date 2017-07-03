package com.szyciov.operate.mapper;



import com.szyciov.op.entity.PubVehcbrand;
import com.szyciov.op.entity.PubVehcline;
import com.szyciov.op.param.PubVehclineQueryParam;

import java.util.List;
import java.util.Map;

public interface PubVehclineMapper {

	void createPubVehcline(PubVehcline pubVehcline);
	
	void updatePubVehcline(PubVehcline pubVehcline);
	
	int checkDelete(String id);
	
	void deletePubVehcline(String id);
	
	List<PubVehcline> getPubVehclineListByQuery(PubVehclineQueryParam queryParam);
	
	int getPubVehclineListCountByQuery(PubVehclineQueryParam queryParam);
	
	List<Map<String, Object>> getBrandCars(PubVehcline pubVehcline);
	
	PubVehcline getById(String id);
	
	int checkLine(PubVehcline pubVehcline);
	
	int checkImprot(PubVehcline pubVehcline);
	
	List<PubVehcbrand> getBrand();
}