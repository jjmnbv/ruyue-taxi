package com.szyciov.lease.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.dto.pubOrderCancelRule.PubOrderCancelRule;
import com.szyciov.dto.pubOrderCancelRule.PubOrderCancelRuleHistory;
import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.lease.mapper.PubOrderCancelRuleMapper;
import com.szyciov.lease.service.PubOrderCancelRuleService;

@Repository("PubOrderCancelRuleDao")
public class PubOrderCancelRuleDao {
	 public PubOrderCancelRuleDao(){
		}
	     private PubOrderCancelRuleMapper mapper;
	     @Resource
		 	public void setMapper(PubOrderCancelRuleMapper mapper) {
		 		this.mapper = mapper;
		 	}
	     public List<PubDictionary> getCity()  {
	 		return mapper.getCity();
	 	}
	     public List<PubOrderCancelRule> getPubOrderCancelRuleList(PubOrderCancelRule pubOrderCancelRule){
	 		return mapper.getPubOrderCancelRuleList(pubOrderCancelRule);
	 	}
	     public int getPubOrderCancelRuleCount(PubOrderCancelRule pubOrderCancelRule){
	 		return mapper.getPubOrderCancelRuleCount(pubOrderCancelRule);
	 	}
	     public void aadPubOrderCancelRule(PubOrderCancelRule pubOrderCancelRule){
	 		      mapper.aadPubOrderCancelRule(pubOrderCancelRule);
	 	}
	     public void modifyPubOrderCancelRule(PubOrderCancelRule pubOrderCancelRule){
		      mapper.modifyPubOrderCancelRule(pubOrderCancelRule);
	}
	     public void addHistory(PubOrderCancelRuleHistory history){
		      mapper.addHistory(history);
	}
	     public void ruleConflictOk(PubOrderCancelRule pubOrderCancelRule){
	 		  mapper.ruleConflictOk(pubOrderCancelRule);
	 	}
	     public void ruleConflict(PubOrderCancelRule pubOrderCancelRule){
	 		  mapper.ruleConflict(pubOrderCancelRule);
	 	}
	     public PubOrderCancelRule searchRule(PubOrderCancelRule pubOrderCancelRule){
		 		return mapper.searchRule(pubOrderCancelRule);
		 	}
	     public List<PubOrderCancelRuleHistory> getHistoryDataList(PubOrderCancelRuleHistory history){
	 		return mapper.getHistoryDataList(history);
	 	}
	     public int getHistoryDataCount(PubOrderCancelRuleHistory history){
	 		return mapper.getHistoryDataCount(history);
	 	}
	     public String getCityId(String cityName){
	    	 return mapper.getCityId(cityName);
	     }
	     public PubOrderCancelRule getRulename(String id)  {
	 		return mapper.getRulename(id);
	 	}
	     public PubOrderCancelRule getRuleById(String id)  {
		 		return mapper.getRuleById(id);
		 	}
	     public int ifHaveRule(PubOrderCancelRule pubOrderCancelRule){
		 		return mapper.ifHaveRule(pubOrderCancelRule);
		 	}

}
