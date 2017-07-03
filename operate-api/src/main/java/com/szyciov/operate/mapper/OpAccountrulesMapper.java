package com.szyciov.operate.mapper;

import java.util.List;
import java.util.Map;

import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.op.entity.OpAccountrules;
import com.szyciov.op.entity.OpVehiclemodels;
import com.szyciov.op.param.OpAccountruleQueryParam;

public interface OpAccountrulesMapper {
	
	List<OpVehiclemodels> getModelsByList();
	
	List<Map<String, String>> getCityByList(PubCityAddr object);
	
	List<OpAccountrules> getOpAccountRulesListByQuery(OpAccountruleQueryParam queryParam);
	
	int getOpAccountRulesCountByQuery(OpAccountruleQueryParam queryParam);
	
	OpAccountrules getOpAccountrulesById(String id);
	
	List<OpAccountrules> getOpAccountrulesByList(OpAccountrules object);
	
	void insertOpAccountrules(OpAccountrules object);
	
	void updateOpAccountrules(OpAccountrules object);
	
	void updateOpAccountRulesByCity(OpAccountrules object);
	
}
