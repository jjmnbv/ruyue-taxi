package com.szyciov.operate.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.op.entity.OpVehclineModelsRef;
import com.szyciov.op.entity.OpVehiclemodels;
import com.szyciov.op.entity.OpVehiclemodelsVehicleRef;
import com.szyciov.op.entity.PubVehcline;
import com.szyciov.operate.mapper.OpVehiclemodelsMapper;
import com.szyciov.param.QueryParam;

@Repository("OpVehiclemodelsDao")
public class OpVehiclemodelsDao {

	private OpVehiclemodelsMapper mapper;
	@Resource
	public void setMapper(OpVehiclemodelsMapper mapper) {
		this.mapper = mapper;
	}
	
	public List<OpVehiclemodels> getOpVehiclemodelsListByQuery(QueryParam queryParam) {
		return mapper.getOpVehiclemodelsListByQuery(queryParam);
	}
	
	public int getOpVehiclemodelsCountByQuery(QueryParam queryParam) {
		return mapper.getOpVehiclemodelsCountByQuery(queryParam);
	}
	
	public OpVehiclemodels getBrandCars(String id) {
		return mapper.getBrandCars(id);
	}
	
	public List<Map<String, Object>> getPubVehcbrand() {
		return mapper.getPubVehcbrand();
	}
	
	public OpVehiclemodels getVehicleModelsByLineId(String id) {
		return mapper.getVehicleModelsByLineId(id);
	}
	
	public List<OpVehiclemodels> getOpVehiclemodelsByList(OpVehiclemodels opVehiclemodels) {
		return mapper.getOpVehiclemodelsByList(opVehiclemodels);
	}
	
	public void insertOpVehiclemodels(OpVehiclemodels opVehiclemodels) {
		mapper.insertOpVehiclemodels(opVehiclemodels);
	}
	
	public void updateOpVehiclemodels(OpVehiclemodels opVehiclemodels) {
		mapper.updateOpVehiclemodels(opVehiclemodels);
	}
	
	public OpVehiclemodels getOpVehiclemodelsById(String id) {
		return mapper.getOpVehiclemodelsById(id);
	}
	
	public List<OpVehiclemodelsVehicleRef> getModelsVehicl6eRefByModel(OpVehiclemodelsVehicleRef object) {
		return mapper.getModelsVehicl6eRefByModel(object);
	}

	public void createLeVehclineModelsRef(OpVehclineModelsRef opVehclineModelsRef) {
		mapper.createLeVehclineModelsRef(opVehclineModelsRef);
	}

	public void deleteLeVehclineModelsRef(String id) {
		mapper.deleteLeVehclineModelsRef(id);
	}

	public List<String> getVehclinesIdByModelId(String modelid) {
		return mapper.getVehclinesIdByModelId(modelid);
	}

	public List<PubVehcline> getVehclines(String modelid) {
		return mapper.getVehclines(modelid);
	}

	public List<PubVehcline> getBindVehclines(List<String> tempvehclinesid) {
		return mapper.getBindVehclines(tempvehclinesid);
	}

	public void createLeVehclineModelsRef4Batch(List<OpVehclineModelsRef> opVehclineModelsRefs) {
		mapper.createLeVehclineModelsRef4Batch(opVehclineModelsRefs);
	}

	public void changeState(Map<String, Object> params) {
		mapper.changeState(params);
	}

	public boolean hasBindLeaseCars(String modelid) {
		return mapper.hasBindLeaseCars(modelid);
	}

	public void deleteVehicleByVehiclemodel(Map<String,Object> params) {
		 mapper.deleteVehicleByVehiclemodel(params);
	}
	
}
