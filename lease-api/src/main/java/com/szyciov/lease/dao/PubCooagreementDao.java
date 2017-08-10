package com.szyciov.lease.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.szyciov.entity.PubCooagreement;
import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.lease.mapper.PubCooagreementMapper;
import com.szyciov.op.param.PubCooagreementQueryParam;

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
	
	public List<Map<String, Object>> getLeLeasescompanyList(Map<String, String> map){
		return mapper.getLeLeasescompanyList(map);
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
	
	public int checkCreatePubCooagreement(PubCooagreement pubCooagreement){
		return mapper.checkCreatePubCooagreement(pubCooagreement);
	};
	
	public LeLeasescompany getLeLeasescompany(Map<String, String> map){
		return mapper.getLeLeasescompany(map);
	};
}
