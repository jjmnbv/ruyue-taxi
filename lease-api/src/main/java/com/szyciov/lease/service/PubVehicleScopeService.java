package com.szyciov.lease.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szyciov.entity.City;
import com.szyciov.entity.Dictionary;
import com.szyciov.lease.dao.PubVehicleScopeDao;
import com.szyciov.lease.entity.PubVehicleScope;

@Service("PubVehicleScopeService")
public class PubVehicleScopeService {
	private PubVehicleScopeDao dao;
	@Resource(name = "PubVehicleScopeDao")
	public void setDao(PubVehicleScopeDao dao) {
		this.dao = dao;
	}
	public void createPubVehicleScope(PubVehicleScope pubVehicleScope){
		dao.createPubVehicleScope(pubVehicleScope);
	};
	public void updatePubVehicleScope(PubVehicleScope pubVehicleScope){
		dao.updatePubVehicleScope(pubVehicleScope);
	};
	
	public void setAsDefault(Dictionary dictionary){
		dao.setAsDefault(dictionary);
	};
	
	public List<City> loadAsDefault(Dictionary dictionary){
		return dao.loadAsDefault(dictionary);
	};
	
	public void deleteSetAsDefault(Dictionary dictionary){
		dao.deleteSetAsDefault(dictionary);
	};
	
	public int checkLoadAsDefault(Dictionary dictionary){
		return dao.checkLoadAsDefault(dictionary);
	};
	
	public List<City> getByVelId(String id){
		return dao.getByVelId(id);
	};
	
	public void deletePubVehicleScope(String id){
		dao.deletePubVehicleScope(id);
	};
}
