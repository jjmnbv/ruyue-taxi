package com.szyciov.organ.mapper;

import com.szyciov.org.entity.OrgInformation;

public interface OrganInformationMapper {
	OrgInformation getorgInformation(String organId);
	void insertOrgInformation(OrgInformation orgInformation);
	void updateOrgInformation(OrgInformation orgInformation);
}
