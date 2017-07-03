package com.szyciov.organ.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.org.entity.OrgInformation;
import com.szyciov.organ.mapper.OrganInformationMapper;

@Repository("OrganInformationDao")
public class OrganInformationDao {
	public OrganInformationDao() {
	}

	private OrganInformationMapper mapper;

	@Resource
	public void setMapper(OrganInformationMapper mapper) {
		this.mapper = mapper;
	}
	public OrgInformation getorgInformation(String organId)  {
		return mapper.getorgInformation(organId);
	}
	public void insertOrgInformation(OrgInformation orgInformation){
		   mapper.insertOrgInformation(orgInformation);
	}
	public void updateOrgInformation(OrgInformation orgInformation){
		   mapper.updateOrgInformation(orgInformation);
	}
}
