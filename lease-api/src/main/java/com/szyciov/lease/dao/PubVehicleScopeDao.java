package com.szyciov.lease.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.entity.City;
import com.szyciov.entity.Dictionary;
import com.szyciov.lease.entity.PubVehicleScope;
import com.szyciov.lease.mapper.PubVehicleScopeMapper;

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
