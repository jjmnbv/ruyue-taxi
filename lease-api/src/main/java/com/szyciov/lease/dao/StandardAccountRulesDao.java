package com.szyciov.lease.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.lease.entity.LeAccountRules;
import com.szyciov.lease.entity.LeAccountRulesModiLog;
import com.szyciov.lease.entity.LeVehiclemodels;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.lease.mapper.StandardAccountRulesMapper;
import com.szyciov.lease.param.LeAccountRulesModiLogQueryParam;
import com.szyciov.lease.param.LeAccountRulesQueryParam;

@Repository("StandardAccountRulesDao")
public class StandardAccountRulesDao {
	public StandardAccountRulesDao() {
	}

	private StandardAccountRulesMapper mapper;

	@Resource
	public void setMapper(StandardAccountRulesMapper mapper) {
		this.mapper = mapper;
	}
	
	public List<LeAccountRules> getStandardAccountRulesListByQuery(LeAccountRulesQueryParam queryParam) {
		return mapper.getStandardAccountRulesListByQuery(queryParam);
	}
	
	public int getStandardAccountRulesListCountByQuery(LeAccountRulesQueryParam queryParam) {
		return mapper.getStandardAccountRulesListCountByQuery(queryParam);
	}
	
	public int getStandardAccountRulesListCountByEqualQuery(LeAccountRules leAccountRules) {
		return mapper.getStandardAccountRulesListCountByEqualQuery(leAccountRules);
	}
	
	public int getStandardAccountRulesListCountByEqualQuery2(LeAccountRules leAccountRules) {
		return mapper.getStandardAccountRulesListCountByEqualQuery2(leAccountRules);
	}
	
	public LeAccountRules getMessageInfoByEqualQuery(LeAccountRules leAccountRules) {
		return mapper.getMessageInfoByEqualQuery(leAccountRules);
	}
	
	public void createStandardAccountRules(LeAccountRules leAccountRules) {
		mapper.createStandardAccountRules(leAccountRules);
	}

	public void updateStandardAccountRules(LeAccountRules leAccountRules) {
		mapper.updateStandardAccountRules(leAccountRules);
	}
	
	public LeAccountRules getStandardAccountRulesById(String id) {
		return mapper.getStandardAccountRulesById(id);
	}
	
	public void createStandardAccountRulesModiLog(LeAccountRulesModiLog leAccountRulesModiLog) {
		mapper.createStandardAccountRulesModiLog(leAccountRulesModiLog);
	}
	
	public void updateStandardAccountRulesState(LeAccountRules leAccountRules) {
		mapper.updateStandardAccountRulesState(leAccountRules);
	}
	
	public void updateStandardAccountRulesOneKey(LeAccountRules leAccountRules) {
		mapper.updateStandardAccountRulesOneKey(leAccountRules);
	}
	
	public List<LeAccountRulesModiLog> getStandardAccountRulesModiLogListByQuery(LeAccountRulesModiLogQueryParam queryParam) {
		return mapper.getStandardAccountRulesModiLogListByQuery(queryParam);
	}
	
	public int getStandardAccountRulesModiLogListCountByQuery(LeAccountRulesModiLogQueryParam queryParam) {
		return mapper.getStandardAccountRulesModiLogListCountByQuery(queryParam);
	}

	public List<LeAccountRules> getStandardAccountRulesByCity(Map<String, String> map) {
		return mapper.getStandardAccountRulesByCity(map);
	}
	
	public List<Map<String, Object>> getStandardAccountRulesExistCityList(Map<String, String> map) {
		return mapper.getStandardAccountRulesExistCityList(map);
	}
	
	public List<PubDictionary> getPubDictionaryByType(String type) {
		return mapper.getPubDictionaryByType(type);
	}
	
	public List<LeVehiclemodels> getStandardAccountRulesExistCarTypeList(String leasesCompanyId) {
		return mapper.getStandardAccountRulesExistCarTypeList(leasesCompanyId);
	}
	
	public List<LeVehiclemodels> getCarTypeList(String leasesCompanyId) {
		return mapper.getCarTypeList(leasesCompanyId);
	}
	
	public int getLeAccountRulesCountById(Map<String, String> map) {
		return mapper.getLeAccountRulesCountById(map);
	}
	
	public LeAccountRules getOneStandardAccountRulesByCity(Map<String, String> map) {
		return mapper.getOneStandardAccountRulesByCity(map);
	}
	
	public List<PubCityAddr> getPubCityAddrList() {
		return mapper.getPubCityAddrList();
	}
	
	public Map<String, String> getAccountStatusByLeasesCompanyId(String leasesCompanyId) {
		return mapper.getAccountStatusByLeasesCompanyId(leasesCompanyId);
	}

}