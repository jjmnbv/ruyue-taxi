package com.szyciov.lease.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.lease.entity.PubVehcbrand;
import com.szyciov.lease.mapper.PubVehcbrandMapper;
import com.szyciov.lease.param.PubVehcbrandQueryParam;

@Repository("PubVehcbrandDao")
public class PubVehcbrandDao {
	public PubVehcbrandDao() {
	}

	private PubVehcbrandMapper mapper;

	@Resource
	public void setMapper(PubVehcbrandMapper mapper) {
		this.mapper = mapper;
	}
	
	public void createPubVehcbrand(PubVehcbrand pubVehcbrand) {
		mapper.createPubVehcbrand(pubVehcbrand);
	}
	
	public void updatePubVehcbrand(PubVehcbrand pubVehcbrand){
		mapper.updatePubVehcbrand(pubVehcbrand);
	};
	
	public int checkDelete(String id){
		return mapper.checkDelete(id);
	};
	
	public void deletePubVehcbrand(String id){
		mapper.deletePubVehcbrand(id);
	};
	
	public List<PubVehcbrand> getPubVehcbrandListByQuery(PubVehcbrandQueryParam queryParam){
		return mapper.getPubVehcbrandListByQuery(queryParam);
	};
	
	public int getPubVehcbrandListCountByQuery(PubVehcbrandQueryParam queryParam){
		return mapper.getPubVehcbrandListCountByQuery(queryParam);
	};
	
	public List<Map<String, Object>> getBrand(PubVehcbrand pubVehcbrand){
		return mapper.getBrand(pubVehcbrand);
	};
	
	public PubVehcbrand getById(String id){
		return mapper.getById(id);
	};
	
	public List<PubVehcbrand> exportData(Map<String, String> map){
		return mapper.exportData(map);
	};
	
	public int checkBrand(PubVehcbrand pubVehcbrand){
		return mapper.checkBrand(pubVehcbrand);
	};
	
	public PubVehcbrand getByName(PubVehcbrand pubVehcbrand){
		return mapper.getByName(pubVehcbrand);
	};
}
