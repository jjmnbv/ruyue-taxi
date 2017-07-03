package com.szyciov.operate.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.op.entity.OpAccountrules;
import com.szyciov.op.entity.OpVehiclemodels;
import com.szyciov.op.param.OpAccountruleQueryParam;
import com.szyciov.operate.mapper.OpAccountrulesMapper;

@Repository("OpAccountrulesDao")
public class OpAccountrulesDao {
	
	private OpAccountrulesMapper mapper;
	@Resource
	public void setMapper(OpAccountrulesMapper mapper) {
		this.mapper = mapper;
	}
	
	public List<OpVehiclemodels> getModelsByList() {
		return mapper.getModelsByList();
	}
	
	public List<Map<String, String>> getCityByList(PubCityAddr object) {
		return mapper.getCityByList(object);
	}
	
	public List<OpAccountrules> getOpAccountRulesListByQuery(OpAccountruleQueryParam queryParam) {
		return mapper.getOpAccountRulesListByQuery(queryParam);
	}
	
	public int getOpAccountRulesCountByQuery(OpAccountruleQueryParam queryParam) {
		return mapper.getOpAccountRulesCountByQuery(queryParam);
	}
	
	public OpAccountrules getOpAccountrulesById(String id) {
		return mapper.getOpAccountrulesById(id);
	}
	
	public List<OpAccountrules> getOpAccountrulesByList(OpAccountrules object) {
		return mapper.getOpAccountrulesByList(object);
	}
	
	public void insertOpAccountrules(OpAccountrules object) {
		mapper.insertOpAccountrules(object);
	}
	
	public void updateOpAccountrules(OpAccountrules object) {
		mapper.updateOpAccountrules(object);
	}
	
	public void updateOpAccountRulesByCity(OpAccountrules object) {
		mapper.updateOpAccountRulesByCity(object);
	}

}
