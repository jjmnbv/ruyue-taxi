package com.szyciov.lease.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.lease.entity.LeVehclineModelsRef;
import com.szyciov.lease.entity.PubVehcline;
import com.szyciov.lease.entity.PubVehicle;
import com.szyciov.lease.mapper.LeVehclineModelsRefMapper;

@Repository("LeVehclineModelsRefDao")
public class LeVehclineModelsRefDao {
	public LeVehclineModelsRefDao() {
	}

	private LeVehclineModelsRefMapper mapper;

	@Resource
	public void setMapper(LeVehclineModelsRefMapper mapper) {
		this.mapper = mapper;
	}
	
	public void createLeVehclineModelsRef(LeVehclineModelsRef leVehclineModelsRef) {
		mapper.createLeVehclineModelsRef(leVehclineModelsRef);
	}
	
	public void deleteLeVehclineModelsRef(LeVehclineModelsRef leVehclineModelsRef) {
		mapper.deleteLeVehclineModelsRef(leVehclineModelsRef);
	}
	
	public int checkVelMod(PubVehicle pubVehicle){
		return mapper.checkVelMod(pubVehicle);
	};
	
	public List<String> genVehiclineId(String id){
		return mapper.genVehiclineId(id);
	};
	
	public void deleteLeVehclineModelsRefAll(String id){
		mapper.deleteLeVehclineModelsRefAll(id);
	};
	
	public List<PubVehcline> getBindVehclines(List<String> tempvehclinesid) {
		return mapper.getBindVehclines(tempvehclinesid);
	}
}
