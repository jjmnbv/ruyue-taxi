package com.szyciov.lease.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.lease.entity.PubVehcbrand;
import com.szyciov.lease.entity.PubVehcline;
import com.szyciov.lease.mapper.PubVehclineMapper;
import com.szyciov.lease.param.PubVehclineQueryParam;

@Repository("PubVehclineDao")
public class PubVehclineDao {
	public PubVehclineDao() {
	}

	private PubVehclineMapper mapper;

	@Resource
	public void setMapper(PubVehclineMapper mapper) {
		this.mapper = mapper;
	}
	
	public void createPubVehcline(PubVehcline pubVehcline) {
		mapper.createPubVehcline(pubVehcline);
	}
	
	public void updatePubVehcline(PubVehcline pubVehcline){
		mapper.updatePubVehcline(pubVehcline);
	};
	
	public int checkDelete(String id){
		return mapper.checkDelete(id);
	};
	
	public void deletePubVehcline(String id){
		mapper.deletePubVehcline(id);
	};
	
	public List<PubVehcline> getPubVehclineListByQuery(PubVehclineQueryParam queryParam){
		return mapper.getPubVehclineListByQuery(queryParam);
	};
	
	public int getPubVehclineListCountByQuery(PubVehclineQueryParam queryParam){
		return mapper.getPubVehclineListCountByQuery(queryParam);
	};
	
	public List<Map<String, Object>> getBrandCars(PubVehcline pubVehcline){
		return mapper.getBrandCars(pubVehcline);
	};
	
	public PubVehcline getById(String id){
		return mapper.getById(id);
	};
	
	public int checkLine(PubVehcline pubVehcline){
		return mapper.checkLine(pubVehcline);
	};
	
	public int checkImprot(PubVehcline pubVehcline){
		return mapper.checkImprot(pubVehcline);
	};
	
	public List<PubVehcbrand> getBrand(String id){
		return mapper.getBrand(id);
	};
}
