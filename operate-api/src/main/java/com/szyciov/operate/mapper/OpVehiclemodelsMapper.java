package com.szyciov.operate.mapper;

import java.util.List;
import java.util.Map;

import com.szyciov.op.entity.OpVehclineModelsRef;
import com.szyciov.op.entity.OpVehiclemodels;
import com.szyciov.op.entity.OpVehiclemodelsVehicleRef;
import com.szyciov.op.entity.PubVehcline;
import com.szyciov.param.QueryParam;

public interface OpVehiclemodelsMapper {
	
	List<OpVehiclemodels> getOpVehiclemodelsListByQuery(QueryParam queryParam);
	
	int getOpVehiclemodelsCountByQuery(QueryParam queryParam);
	
	OpVehiclemodels getBrandCars(String id);
	
	List<Map<String, Object>> getPubVehcbrand();
	
	OpVehiclemodels getVehicleModelsByLineId(String id);
	
	List<OpVehiclemodels> getOpVehiclemodelsByList(OpVehiclemodels opVehiclemodels);
	
	void insertOpVehiclemodels(OpVehiclemodels opVehiclemodels);
	
	void updateOpVehiclemodels(OpVehiclemodels opVehiclemodels);
	
	OpVehiclemodels getOpVehiclemodelsById(String id);
	
	List<OpVehiclemodelsVehicleRef> getModelsVehicl6eRefByModel(OpVehiclemodelsVehicleRef object);

	void createLeVehclineModelsRef(OpVehclineModelsRef opVehclineModelsRef);

	void deleteLeVehclineModelsRef(String id);

	List<String> getVehclinesIdByModelId(String modelid);

	List<PubVehcline> getVehclines(String modelid);

	List<PubVehcline> getBindVehclines(List<String> tempvehclinesid);

	void createLeVehclineModelsRef4Batch(List<OpVehclineModelsRef> opVehclineModelsRefs);

	void changeState(Map<String, Object> params);

	boolean hasBindLeaseCars(String modelid);

	void deleteVehicleByVehiclemodel(Map<String,Object> params);
	
}
