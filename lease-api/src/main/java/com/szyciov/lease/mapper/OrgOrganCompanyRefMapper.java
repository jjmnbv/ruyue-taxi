package com.szyciov.lease.mapper;

import com.szyciov.lease.entity.OrgOrganCompanyRef;

public interface OrgOrganCompanyRefMapper {

	void createOrgOrganCompanyRef(OrgOrganCompanyRef orgOrganCompanyRef);
	
	void reassignProducts(OrgOrganCompanyRef orgOrganCompanyRef);
}