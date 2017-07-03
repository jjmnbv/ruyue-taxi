package com.szyciov.operate.mapper;

import java.util.List;

import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.op.entity.LeCompanyAgreement;
import com.szyciov.op.param.LeCompanyAgreementQueryParam;

public interface LeCompanyAgreementMapper {
	
	List<LeCompanyAgreement> getCompanyAgreementListByQuery(LeCompanyAgreementQueryParam queryParam);
	
	int getCompanyAgreementCountByQuery(LeCompanyAgreementQueryParam queryParam);
	
	List<LeLeasescompany> getCompanyLisyByEdit();
	
	List<LeLeasescompany> getCompanyLisyByAdd();
	
	LeCompanyAgreement getCompanyAgreementById(String id);
	
	void insertCompanyAgreement(LeCompanyAgreement object);
	
	void updateCompanyAgreement(LeCompanyAgreement object);
	
	List<LeCompanyAgreement> getCompanyAgreementList(LeCompanyAgreement object);

}
