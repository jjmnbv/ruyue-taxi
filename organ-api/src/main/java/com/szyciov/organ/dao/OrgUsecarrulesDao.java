package com.szyciov.organ.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.lease.entity.LeAccountRules;
import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.lease.entity.OrgOrganCompanyRef;
import com.szyciov.org.entity.OrgUsecarrules;
import com.szyciov.org.entity.OrgUserRulesRef;
import com.szyciov.organ.mapper.OrgUsecarrulesMapper;

@Repository("OrgUsecarrulesDao")
public class OrgUsecarrulesDao {
	public OrgUsecarrulesDao() {
	}

	private OrgUsecarrulesMapper mapper;

	@Resource
	public void setMapper(OrgUsecarrulesMapper mapper) {
		this.mapper = mapper;
	}
	
//	public List<Map<String, Object>> getLeLeasescompany(OrgOrganCompanyRef o){
//		return mapper.getLeLeasescompany(o);
//	};
	
	
//	public List<Map<String, Object>> getLeVehiclemodels(OrgOrganCompanyRef o){
//		return mapper.getLeVehiclemodels(o);
//	};
	
	public List<Map<String, Object>> getOrgUsecarrules(OrgUsecarrules orgUsecarrules){
		return mapper.getOrgUsecarrules(orgUsecarrules);
	};
	
	public List<OrgUsecarrules> getByName(OrgUsecarrules orgUsecarrules){
		return mapper.getByName(orgUsecarrules);
	};
	
//	public void deleteOrgUserRulesRef(String id){
//		mapper.deleteOrgUserRulesRef(id);
//	};
	
	public void deleteOrgUsecarrules(String id){
		mapper.deleteOrgUsecarrules(id);
	};
	
//	public List<Map<String, Object>> getById(OrgUsecarrules orgUsecarrules){
//		return mapper.getById(orgUsecarrules);
//	};
	
	public void add(OrgUsecarrules orgUsecarrules){
		mapper.add(orgUsecarrules);
	};
	
	public void update(OrgUsecarrules orgUsecarrules){
		mapper.update(orgUsecarrules);
	};
	
//	public void addourr(OrgUserRulesRef orgUserRulesRef){
//		mapper.addourr(orgUserRulesRef);
//	};
	
	public List<LeAccountRules> getRulestype(String organid){
		return mapper.getRulestype(organid);
	};
	
	public int checkRulesname(OrgUsecarrules orgUsecarrules){
		return mapper.checkRulesname(orgUsecarrules);
	}; 
	
	public List<Map<String, Object>> getAllRules(String organid){
		return mapper.getAllRules(organid);
	};
	
	public int checkRulesUpdate(String name){
		return mapper.checkRulesUpdate(name);
	};
}
