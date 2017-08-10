package com.szyciov.lease.mapper;

import java.util.List;
import java.util.Map;

import com.szyciov.entity.PubCooagreement;
import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.op.param.PubCooagreementQueryParam;

public interface PubCooagreementMapper {
	
	List<PubCooagreement> getPubCooagreementList(PubCooagreementQueryParam queryParam);
	
	int getPubCooagreementListCount(PubCooagreementQueryParam queryParam);
	
	List<Map<String, Object>> getLeLeasescompanyList(Map<String, String> map);
	
	void createPubCooagreement(PubCooagreement pubCooagreement);
	
	PubCooagreement getById(String id);
	
	void updatePubCooagreement(PubCooagreement pubCooagreement);
	
	void deletePubCooagreement(String id);
	
	int checkCreatePubCooagreement(PubCooagreement pubCooagreement);
	
	LeLeasescompany getLeLeasescompany(Map<String, String> map);
}