package com.szyciov.lease.mapper;

import java.util.List;

import com.szyciov.entity.City;
import com.szyciov.entity.Dictionary;
import com.szyciov.lease.entity.PubVehicleScope;

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