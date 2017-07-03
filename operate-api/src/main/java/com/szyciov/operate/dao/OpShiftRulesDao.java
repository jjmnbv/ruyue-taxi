package com.szyciov.operate.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.op.entity.OpShiftRules;
import com.szyciov.op.param.OpShiftRulesQueryParam;
import com.szyciov.operate.mapper.OpShiftRulesMapper;

@Repository("opShiftRulesDao")
public class OpShiftRulesDao {
	
	private OpShiftRulesMapper mapper;

	@Resource
	public void setMapper(OpShiftRulesMapper mapper) {
		this.mapper = mapper;
	}

	public List<Map<String, String>> getVailableCitys(Map<String, Object> params) {
		return mapper.getVailableCitys(params);
	}

	public int getShiftRulesListCountByQuery(OpShiftRulesQueryParam queryParam) {
		return mapper.getShiftRulesListCountByQuery(queryParam);
	}

	public List<OpShiftRules> getShiftRulesListByQuery(OpShiftRulesQueryParam queryParam) {
		return mapper.getShiftRulesListByQuery(queryParam);
	}

	public OpShiftRules getShiftRules(Map<String, Object> params) {
		return mapper.getShiftRules(params);
	}

	public boolean hasCity(Map<String, Object> params) {
		return mapper.hasCity(params);
	}

	public void createShiftRules(Map<String, Object> params) {
		mapper.createShiftRules(params);
	}

	public void updateShiftRules(Map<String, Object> params) {
		mapper.updateShiftRules(params);
	}

	public List<Map<String, String>> getCitys(Map<String, Object> params) {
		return mapper.getCitys(params);
	}

	public OpShiftRules getRules(String city){
		return mapper.getRules(city);
	}

}
