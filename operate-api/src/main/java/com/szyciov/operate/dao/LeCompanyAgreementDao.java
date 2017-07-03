package com.szyciov.operate.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.op.entity.LeCompanyAgreement;
import com.szyciov.op.param.LeCompanyAgreementQueryParam;
import com.szyciov.operate.mapper.LeCompanyAgreementMapper;

@Repository("LeCompanyAgreementDao")
public class LeCompanyAgreementDao {
	
	private LeCompanyAgreementMapper mapper;
	@Resource
	public void setMapper(LeCompanyAgreementMapper mapper) {
		this.mapper = mapper;
	}
	
	public List<LeCompanyAgreement> getCompanyAgreementListByQuery(LeCompanyAgreementQueryParam queryParam) {
		return mapper.getCompanyAgreementListByQuery(queryParam);
	}
	
	public int getCompanyAgreementCountByQuery(LeCompanyAgreementQueryParam queryParam) {
		return mapper.getCompanyAgreementCountByQuery(queryParam);
	}
	
	public List<LeLeasescompany> getCompanyLisyByEdit() {
		return mapper.getCompanyLisyByEdit();
	}
	
	public List<LeLeasescompany> getCompanyLisyByAdd() {
		return mapper.getCompanyLisyByAdd();
	}
	
	public LeCompanyAgreement getCompanyAgreementById(String id) {
		return mapper.getCompanyAgreementById(id);
	}
	
	public void insertCompanyAgreement(LeCompanyAgreement object) {
		mapper.insertCompanyAgreement(object);
	}
	
	public void updateCompanyAgreement(LeCompanyAgreement object) {
		mapper.updateCompanyAgreement(object);
	}
	
	public List<LeCompanyAgreement> getCompanyAgreementList(LeCompanyAgreement object) {
		return mapper.getCompanyAgreementList(object);
	}

}
