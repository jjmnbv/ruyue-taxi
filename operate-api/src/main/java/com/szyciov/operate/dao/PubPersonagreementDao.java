package com.szyciov.operate.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.entity.PubPersonagreement;
import com.szyciov.operate.mapper.PubPersonagreementMapper;

@Repository("PubPersonagreementDao")
public class PubPersonagreementDao {
	
	private PubPersonagreementMapper mapper;
	@Resource
	public void setMapper(PubPersonagreementMapper mapper) {
		this.mapper = mapper;
	}
	
	public void insertPubPersonagreement(PubPersonagreement object) {
		mapper.insertPubPersonagreement(object);
	}
	
	public void updatePubPersonagreement(PubPersonagreement object) {
		mapper.updatePubPersonagreement(object);
	}
	
	public List<PubPersonagreement> getPubPersonagreementList() {
		return mapper.getPubPersonagreementList();
	}

}
