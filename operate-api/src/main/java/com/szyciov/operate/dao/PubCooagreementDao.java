package com.szyciov.operate.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.szyciov.entity.PubCooagreement;
import com.szyciov.op.entity.LeLeasescompany;
import com.szyciov.op.param.PubCooagreementQueryParam;
import com.szyciov.operate.mapper.PubCooagreementMapper;

@Repository("PubCooagreementDao")
public class PubCooagreementDao {
	public PubCooagreementDao() {
	}

	@Autowired
	private PubCooagreementMapper mapper;
	
	public List<PubCooagreement> getPubCooagreementList(PubCooagreementQueryParam queryParam){
		return mapper.getPubCooagreementList(queryParam);
	};
	
	public int getPubCooagreementListCount(PubCooagreementQueryParam queryParam){
		return mapper.getPubCooagreementListCount(queryParam);
	};
	
	public List<LeLeasescompany> getLeLeasescompanyList(){
		return mapper.getLeLeasescompanyList();
	};
	
	public void createPubCooagreement(PubCooagreement pubCooagreement){
		mapper.createPubCooagreement(pubCooagreement);
	};
	
	public PubCooagreement getById(String id){
		return mapper.getById(id);
	};
	
	public void updatePubCooagreement(PubCooagreement pubCooagreement){
		mapper.updatePubCooagreement(pubCooagreement);
	};
	
	public void deletePubCooagreement(String id){
		mapper.deletePubCooagreement(id);
	};
}
