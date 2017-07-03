package com.szyciov.organ.mapper;

import java.util.List;
import java.util.Map;

import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.org.entity.OrgUsecarrules;
import com.szyciov.org.entity.OrgUserRulesRef;

public interface MyRuleMapper {
	
	List<Map<String, Object>> getOrgUsecarrules(Map<String, String> map);
	
}