package com.szyciov.operate.mapper;

import java.util.List;

import com.szyciov.entity.PubPersonagreement;

public interface PubPersonagreementMapper {
	
	void insertPubPersonagreement(PubPersonagreement object);
	
	void updatePubPersonagreement(PubPersonagreement object);
	
	List<PubPersonagreement> getPubPersonagreementList();

}
