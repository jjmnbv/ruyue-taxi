package com.szyciov.lease.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.lease.entity.LeVehiclemodels;
import com.szyciov.lease.entity.PubVehcbrand;
import com.szyciov.lease.entity.PubVehcline;
import com.szyciov.lease.entity.PubVehicle;
import com.szyciov.lease.mapper.LeVehiclemodelsMapper;
import com.szyciov.param.QueryParam;

@Repository("LeVehiclemodelsDao")
public class LeVehiclemodelsDao {
	public LeVehiclemodelsDao() {
	}

	private LeVehiclemodelsMapper mapper;

	@Resource
	public void setMapper(LeVehiclemodelsMapper mapper) {
		this.mapper = mapper;
	}
	
	public void createLeVehiclemodels(LeVehiclemodels leVehiclemodels) {
		mapper.createLeVehiclemodels(leVehiclemodels);
	}
	
	public void updateLeVehiclemodels(LeVehiclemodels leVehiclemodels){
		mapper.updateLeVehiclemodels(leVehiclemodels);
	};
	
	public int checkDelete(String id){
		return mapper.checkDelete(id);
	};
	
	public void deleteLeVehiclemodels(String id){
		mapper.deleteLeVehiclemodels(id);
	};
	
	public List<LeVehiclemodels> getLeVehiclemodelsListByQuery(QueryParam queryParam){
		return mapper.getLeVehiclemodelsListByQuery(queryParam);
	};
	
	public int getLeVehiclemodelsListCountByQuery(QueryParam queryParam){
		return mapper.getLeVehiclemodelsListCountByQuery(queryParam);
	};
	
	public LeVehiclemodels getBrandCars(String id){
		return mapper.getBrandCars(id);
	};
	
	public int checkLeVehiclemodels(LeVehiclemodels leVehiclemodels){
		return mapper.checkLeVehiclemodels(leVehiclemodels);
	};
	
	public LeVehiclemodels getById(String id){
		return mapper.getById(id);
	};
	
	public List<Map<String, Object>> getPubVehcbrand(String leasesCompanyId){
		return mapper.getPubVehcbrand(leasesCompanyId);
	};
	
	public LeVehiclemodels getVehicleModelsName(String id){
		return mapper.getVehicleModelsName(id);
	};
	
	public int getVehclineBindstate(PubVehicle pubVehicle){
		return mapper.getVehclineBindstate(pubVehicle);
	};
	
	public int checkDisable(String id){
		return mapper.checkDisable(id);
	};
	
	public void updateEnableOrDisable(LeVehiclemodels leVehiclemodels){
		mapper.updateEnableOrDisable(leVehiclemodels);
	};
	
	public int checkAllocationVehcline(String id){
		return mapper.checkAllocationVehcline(id);
	};
	
	public LeVehiclemodels getBrandcar(String id){
		return mapper.getBrandcar(id);
	};
}
