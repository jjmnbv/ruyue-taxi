package com.szyciov.operate.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.op.entity.OpTaxisendrules;
import com.szyciov.op.entity.OpTaxisendrulesHistory;
import com.szyciov.op.entity.OpUser;
import com.szyciov.op.param.OpTaxiSendrulesHistoryQueryParam;
import com.szyciov.op.param.OpTaxiSendrulesQueryParam;
import com.szyciov.operate.mapper.TaxiSendrulesMapper;

@Repository("TaxiSendrulesDao")
public class TaxiSendrulesDao {
	
	private TaxiSendrulesMapper mapper;
	@Resource
	public void setMapper(TaxiSendrulesMapper mapper) {
		this.mapper = mapper;
	}
	
	public List<Map<String, Object>> getTaxiSendrulesListByQuery(OpTaxiSendrulesQueryParam queryParam) {
		return mapper.getTaxiSendrulesListByQuery(queryParam);
	}
	
	public int getTaxiSendrulesCountByQuery(OpTaxiSendrulesQueryParam queryParam) {
		return mapper.getTaxiSendrulesCountByQuery(queryParam);
	}
	
	public List<Map<String, String>> getTaxiSendrulesCityBySelect(Map<String, String> params) {
		return mapper.getTaxiSendrulesCityBySelect(params);
	}
	
	public OpTaxisendrules getTaxiSendrulesById(String id) {
		return mapper.getTaxiSendrulesById(id);
	}
	
	public int getTaxiDriverCount() {
		return mapper.getTaxiDriverCount();
	}
	
	public void insertOpTaxiSendrules(OpTaxisendrules object) {
		mapper.insertOpTaxiSendrules(object);
	}
	
	public void updateOpTaxiSendrules(OpTaxisendrules object) {
		mapper.updateOpTaxiSendrules(object);
	}
	
	public Map<String, String> getCityShortname(String cityid) {
		return mapper.getCityShortname(cityid);
	}
	
	public void insertOpTaxisendrulesHistory(OpTaxisendrulesHistory object) {
		mapper.insertOpTaxisendrulesHistory(object);
	}
	
	public List<OpTaxisendrules> getOpTaxiSendrulesList(OpTaxisendrules object) {
		return mapper.getOpTaxiSendrulesList(object);
	}
	
	public List<Map<String, Object>> getTaxiSendrulesHistoryListByQuery(OpTaxiSendrulesHistoryQueryParam queryParam) {
		return mapper.getTaxiSendrulesHistoryListByQuery(queryParam);
	}
	
	public int getTaxiSendrulesHistoryCountByQuery(OpTaxiSendrulesHistoryQueryParam queryParam) {
		return mapper.getTaxiSendrulesHistoryCountByQuery(queryParam);
	}
	
	public int getOpSendmodelCountByUser(OpUser object) {
		return mapper.getOpSendmodelCountByUser(object);
	}
	
}
