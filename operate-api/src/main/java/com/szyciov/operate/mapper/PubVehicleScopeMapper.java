package com.szyciov.operate.mapper;

import com.szyciov.entity.City;
import com.szyciov.entity.Dictionary;
import com.szyciov.op.entity.PubVehicleScope;

import java.util.List;

public interface PubVehicleScopeMapper {

	void createPubVehicleScope(PubVehicleScope pubVehicleScope);
	
	void updatePubVehicleScope(PubVehicleScope pubVehicleScope);
	
	void setAsDefault(Dictionary dictionary);
	
	List<City> loadAsDefault(Dictionary dictionary);
	
	void deleteSetAsDefault(Dictionary dictionary);
	
	int checkLoadAsDefault(Dictionary dictionary);
	
	List<City> getByVelId(String id);
	
	void deletePubVehicleScope(String id);
	
}