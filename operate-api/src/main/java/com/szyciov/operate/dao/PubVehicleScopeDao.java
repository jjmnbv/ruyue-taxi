package com.szyciov.operate.dao;

import com.szyciov.entity.City;
import com.szyciov.entity.Dictionary;
import com.szyciov.op.entity.PubVehicleScope;
import com.szyciov.operate.mapper.PubVehicleScopeMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository("PubVehicleScopeDao")
public class PubVehicleScopeDao {
	public PubVehicleScopeDao() {
	}

	private PubVehicleScopeMapper mapper;

	@Resource
	public void setMapper(PubVehicleScopeMapper mapper) {
		this.mapper = mapper;
	}
	
	public void createPubVehicleScope(PubVehicleScope pubVehicleScope){
		mapper.createPubVehicleScope(pubVehicleScope);
	};
	
	public void updatePubVehicleScope(PubVehicleScope pubVehicleScope){
		mapper.updatePubVehicleScope(pubVehicleScope);
	};
	
	public void setAsDefault(Dictionary dictionary){
		mapper.setAsDefault(dictionary);
	};
	
	public List<City> loadAsDefault(Dictionary dictionary){
		return mapper.loadAsDefault(dictionary);
	};
	
	public void deleteSetAsDefault(Dictionary dictionary){
		mapper.deleteSetAsDefault(dictionary);
	};
	
	public int checkLoadAsDefault(Dictionary dictionary){
		return mapper.checkLoadAsDefault(dictionary);
	};
	
	public List<City> getByVelId(String id){
		return mapper.getByVelId(id);
	};
	
	public void deletePubVehicleScope(String id){
		mapper.deletePubVehicleScope(id);
	};
}
