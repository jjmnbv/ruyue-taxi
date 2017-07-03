package com.szyciov.lease.mapper;

import java.util.List;

import com.szyciov.lease.entity.LeVehclineModelsRef;
import com.szyciov.lease.entity.PubVehcline;
import com.szyciov.lease.entity.PubVehicle;

public interface LeVehclineModelsRefMapper {

	void createLeVehclineModelsRef(LeVehclineModelsRef leVehclineModelsRef);
	
	void deleteLeVehclineModelsRef(LeVehclineModelsRef leVehclineModelsRef);
	
	int checkVelMod(PubVehicle pubVehicle);
	
	List<String> genVehiclineId(String id);
	
	void deleteLeVehclineModelsRefAll(String id);
	
	List<PubVehcline> getBindVehclines(List<String> tempvehclinesid);
}