package com.szyciov.lease.mapper;

import java.util.List;

import com.szyciov.dto.pubOrderCancelRule.PubOrderCancelRule;
import com.szyciov.dto.pubOrderCancelRule.PubOrderCancelRuleHistory;
import com.szyciov.lease.entity.PubDictionary;

public interface PubOrderCancelRuleMapper {
	List<PubDictionary> getCity();
	List<PubOrderCancelRule> getPubOrderCancelRuleList(PubOrderCancelRule pubOrderCancelRule);
	int getPubOrderCancelRuleCount(PubOrderCancelRule pubOrderCancelRule);
	void aadPubOrderCancelRule(PubOrderCancelRule pubOrderCancelRule);
	void modifyPubOrderCancelRule(PubOrderCancelRule pubOrderCancelRule);
	void addHistory(PubOrderCancelRuleHistory history);
	void ruleConflictOk(PubOrderCancelRule pubOrderCancelRule);
	void ruleConflict(PubOrderCancelRule pubOrderCancelRule);
	PubOrderCancelRule searchRule(PubOrderCancelRule pubOrderCancelRule);
	List<PubOrderCancelRuleHistory> getHistoryDataList(PubOrderCancelRuleHistory history);
	int getHistoryDataCount(PubOrderCancelRuleHistory history);
	String getCityId(String cityName);
	PubOrderCancelRule getRulename(String id);
	PubOrderCancelRule getRuleById(String id);
	int ifHaveRule(PubOrderCancelRule pubOrderCancelRule);
}
