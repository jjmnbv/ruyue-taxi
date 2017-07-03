package com.szyciov.organ.mapper;

import java.util.List;
import java.util.Map;

import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.lease.entity.OrgOrgan;
import com.szyciov.lease.entity.OrgOrganCompanyRef;
import com.szyciov.lease.entity.User;
import com.szyciov.org.entity.OrgUser;

public interface SupplierManagementMapper {

	OrgUser getById(Map<String,String> map);
	
	List<OrgUser> getByList(Map<String,String> map);
	
	void addLink(String id);
	
	void removeLink(String id);
	
	List<OrgUser> getChildCccount(Map<String,String> map);
	
	int checkPassword(OrgUser orgUser);
	
	void updatePassword(OrgUser orgUser);
	
	void updatePePassword(OrgUser orgUser);
	
	void updateMainaccount(String id);
	
	void updateChildMainaccount(String id);
	
	void addLogontimes(OrgUser orgUser);
	
	void resetLogontimes(OrgUser orgUser);
	
	OrgOrgan getOrgorganPhone(String id);
	
	void updateOrg(OrgOrgan orgOrgan);
	
	OrgOrganCompanyRef getCmpId(OrgOrganCompanyRef orgOrganCompanyRef);
	
	void updateCmp(LeLeasescompany leLeasescompany);
	
	OrgOrgan getLeuser(String id);
	
	void deleteRuleByLeasesCompanyId(String id);
	
	OrgOrganCompanyRef getLeasesCompanyId(String id);
}