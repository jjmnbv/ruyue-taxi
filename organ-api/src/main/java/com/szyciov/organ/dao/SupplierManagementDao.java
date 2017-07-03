package com.szyciov.organ.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.lease.entity.OrgOrgan;
import com.szyciov.lease.entity.OrgOrganCompanyRef;
import com.szyciov.lease.entity.User;
import com.szyciov.org.entity.OrgUser;
import com.szyciov.organ.mapper.SupplierManagementMapper;

@Repository("SupplierManagementDao")
public class SupplierManagementDao {
	public SupplierManagementDao() {
	}

	private SupplierManagementMapper mapper;

	@Resource
	public void setMapper(SupplierManagementMapper mapper) {
		this.mapper = mapper;
	}
	
	public OrgUser getById(Map<String,String> map){
		return mapper.getById(map);
	};
	
	public List<OrgUser> getByList(Map<String,String> map){
		return mapper.getByList(map);
	};
	
	public void addLink(String id){
		mapper.addLink(id);
	};
	
	public void removeLink(String id){
		mapper.removeLink(id);
	};
	
	public List<OrgUser> getChildCccount(Map<String,String> map){
		return mapper.getChildCccount(map);
	};
	
	public int checkPassword(OrgUser orgUser){
		return mapper.checkPassword(orgUser);
	};
	
	public void updatePassword(OrgUser orgUser){
		mapper.updatePassword(orgUser);
	};
	public void updatePePassword(OrgUser orgUser){
		mapper.updatePePassword(orgUser);
	};
	
	public void updateMainaccount(String id){
		mapper.updateMainaccount(id);
	};
	
	public void updateChildMainaccount(String id){
		mapper.updateChildMainaccount(id);
	};
	
	public void addLogontimes(OrgUser orgUser){
		mapper.addLogontimes(orgUser);
	};
	
	public void resetLogontimes(OrgUser orgUser){
		mapper.resetLogontimes(orgUser);
	};
	
	public OrgOrgan getOrgorganPhone(String id){
		return mapper.getOrgorganPhone(id);
	};
	
	public void updateOrg(OrgOrgan orgOrgan){
		mapper.updateOrg(orgOrgan);
	};
	
	public OrgOrganCompanyRef getCmpId(OrgOrganCompanyRef orgOrganCompanyRef){
		return mapper.getCmpId(orgOrganCompanyRef);
	};
	
	public void updateCmp(LeLeasescompany leLeasescompany){
		mapper.updateCmp(leLeasescompany);
	};
	
	public OrgOrgan getLeuser(String id){
		return mapper.getLeuser(id);
	};
	
	public void deleteRuleByLeasesCompanyId(String id){
		mapper.deleteRuleByLeasesCompanyId(id);
	}
	
	public OrgOrganCompanyRef getLeasesCompanyId(String id){
		return mapper.getLeasesCompanyId(id);
	};
}
