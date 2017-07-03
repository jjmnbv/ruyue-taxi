package com.szyciov.lease.mapper;

import com.szyciov.org.entity.OrgUser;

public interface OrgUserMapper {

	void createOrgUser(OrgUser orgUser);
	
	void updateOrgUser(OrgUser orgUser);
	
	void resetPassword(OrgUser orgUser);
	
	OrgUser getOrgUserId(String id);
}