package com.szyciov.lease.mapper;

import java.util.List;
import java.util.Map;

import com.szyciov.lease.entity.LeVehiclemodels;
import com.szyciov.lease.entity.PubVehcbrand;
import com.szyciov.lease.entity.PubVehicle;
import com.szyciov.param.QueryParam;

public interface LeVehiclemodelsMapper {

	void createLeVehiclemodels(LeVehiclemodels leVehiclemodels);
	
	void updateLeVehiclemodels(LeVehiclemodels leVehiclemodels);
	
	int checkDelete(String id);
	
	void deleteLeVehiclemodels(String id);
	
	List<LeVehiclemodels> getLeVehiclemodelsListByQuery(QueryParam queryParam);
	
	int getLeVehiclemodelsListCountByQuery(QueryParam queryParam);
	
	LeVehiclemodels getBrandCars(String id);
	
	int checkLeVehiclemodels(LeVehiclemodels leVehiclemodels);
	
	LeVehiclemodels getById(String id);
	
	List<Map<String, Object>> getPubVehcbrand(String leasesCompanyId);
	
	LeVehiclemodels getVehicleModelsName(String id);
	
	int getVehclineBindstate(PubVehicle pubVehicle);
	
	int checkDisable(String id);
	
	void updateEnableOrDisable(LeVehiclemodels leVehiclemodels);
	
	int checkAllocationVehcline(String id);
	
	LeVehiclemodels getBrandcar(String id);
}