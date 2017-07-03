package com.szyciov.lease.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.lease.entity.OrgUseraccount;
import com.szyciov.lease.mapper.OrgUseraccountMapper;

@Repository("OrgUseraccountDao")
public class OrgUseraccountDao {
	
	private OrgUseraccountMapper mapper;
	@Resource
	public void setMapper(OrgUseraccountMapper mapper) {
		this.mapper = mapper;
	}
	
	public List<OrgUseraccount> getOrgUseraccountList(OrgUseraccount object) {
		return mapper.getOrgUseraccountList(object);
	}

}
