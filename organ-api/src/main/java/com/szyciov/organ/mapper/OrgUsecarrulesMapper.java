package com.szyciov.organ.mapper;

import java.util.List;
import java.util.Map;

import com.szyciov.lease.entity.LeAccountRules;
import com.szyciov.org.entity.OrgUsecarrules;

public interface OrgUsecarrulesMapper {

//	List<Map<String, Object>> getLeLeasescompany(OrgOrganCompanyRef o);
	
	
//	List<Map<String, Object>> getLeVehiclemodels(OrgOrganCompanyRef o);
	
	List<Map<String, Object>> getOrgUsecarrules(OrgUsecarrules orgUsecarrules);
	
	List<OrgUsecarrules> getByName(OrgUsecarrules orgUsecarrules);
	
//	void deleteOrgUserRulesRef(String id);
	
	void deleteOrgUsecarrules(String id);
	
//	List<Map<String, Object>> getById(OrgUsecarrules orgUsecarrules);
	
	void add(OrgUsecarrules orgUsecarrules);
	
//	void addourr(OrgUserRulesRef orgUserRulesRef);
	
	void update(OrgUsecarrules orgUsecarrules);
	
	List<LeAccountRules> getRulestype(String organid);
	
	int checkRulesname(OrgUsecarrules orgUsecarrules); 
	
	List<Map<String, Object>> getAllRules(String organid);
	
	int checkRulesUpdate(String name);
}