package com.szyciov.lease.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.lease.mapper.OrgUserMapper;
import com.szyciov.org.entity.OrgUser;

@Repository("OrgUserDao")
public class OrgUserDao {
	public OrgUserDao() {
	}

	private OrgUserMapper mapper;

	@Resource
	public void setMapper(OrgUserMapper mapper) {
		this.mapper = mapper;
	}
	
	public void createOrgUser(OrgUser orgUser) {
		mapper.createOrgUser(orgUser);
	}
	
	public void resetPassword(OrgUser orgUser) {
		mapper.resetPassword(orgUser);
	}
	
	public OrgUser getOrgUserId(String id){
		return mapper.getOrgUserId(id);
	};
	
	public void updateOrgUser(OrgUser orgUser){
		mapper.updateOrgUser(orgUser);
	};
}
