package com.szyciov.lease.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.lease.entity.OrgOrganCompanyRef;
import com.szyciov.lease.mapper.OrgOrganCompanyRefMapper;

@Repository("OrgOrganCompanyRefDao")
public class OrgOrganCompanyRefDao {
	public OrgOrganCompanyRefDao() {
	}

	private OrgOrganCompanyRefMapper mapper;

	@Resource
	public void setMapper(OrgOrganCompanyRefMapper mapper) {
		this.mapper = mapper;
	}
	
	public void createOrgOrganCompanyRef(OrgOrganCompanyRef orgOrganCompanyRef) {
		mapper.createOrgOrganCompanyRef(orgOrganCompanyRef);
	}
	
	public void reassignProducts(OrgOrganCompanyRef orgOrganCompanyRef) {
		mapper.reassignProducts(orgOrganCompanyRef);
	}
}
