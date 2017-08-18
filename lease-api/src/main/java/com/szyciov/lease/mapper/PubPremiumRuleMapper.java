package com.szyciov.lease.mapper;

import java.util.List;

import com.szyciov.dto.pubPremiumRule.PubPremiumAdd;
import com.szyciov.dto.pubPremiumRule.PubPremiumDetail;
import com.szyciov.dto.pubPremiumRule.PubPremiumHistory;
import com.szyciov.dto.pubPremiumRule.PubPremiumModify;
import com.szyciov.dto.pubPremiumRule.PubPremiumParam;
import com.szyciov.entity.PubPremiumRule;
import com.szyciov.entity.PubPremiumRuleDatedetail;
import com.szyciov.entity.PubPremiumRuleWeekdetail;
import com.szyciov.lease.entity.PubDictionary;

public interface PubPremiumRuleMapper {
	 List<PubPremiumParam> getPubPremiumRuleByQueryList(PubPremiumParam pubPremiumParam);
	 int getPubPremiumRuleByQueryCount(PubPremiumParam pubPremiumParam);
	 List<PubDictionary> getCity();
	 List<PubDictionary> getWeeks(String id);
	 PubPremiumParam getRulename(String id);
	 List<PubPremiumParam> ruleConflict(PubPremiumParam pubPremiumParam);
	 PubPremiumParam getById(PubPremiumParam pubPremiumParam);
	 void updateStatus(PubPremiumParam pubPremiumParam);
	 void ruleConflictOk(PubPremiumParam pubPremiumParam);
	 void insertIntoHistory(PubPremiumHistory pubPremiumHistory);
	 String getCityId(String cityName);
	 void insertPubPremiumRule(PubPremiumRule pubPremiumRule);
	 void inserWeek(PubPremiumRuleWeekdetail weekdetail);
	 void insertDate(PubPremiumRuleDatedetail datedetail);
	 List<PubPremiumDetail> getPubPremiumRuleDetail(PubPremiumDetail pubPremiumDetail);
	 int getPubPremiumRuleDetailCount(PubPremiumDetail pubPremiumDetail);
	 List<PubPremiumDetail> getDetailDateDataList(PubPremiumDetail pubPremiumDetail);
	 int getDetailDateDataCount(PubPremiumDetail pubPremiumDetail);
	 int ifHavePremiumRule(PubPremiumAdd pubPremiumAdd);
	 List<PubPremiumHistory> getHistoryDataList(PubPremiumHistory pubPremiumHistory);
	 int getHistoryDataCount(PubPremiumHistory pubPremiumHistory);
	 PubPremiumModify modify(PubPremiumParam pubPremiumParam);
	 List<PubPremiumDetail> getWeek(String id);
	 public List<PubPremiumDetail> getDate(String id);
	 void insertWeekHistory(PubPremiumDetail pubPremiumDetail);
	 void insertDateHistory(PubPremiumDetail pubPremiumDetail);
	 int getDateSame(PubPremiumAdd pubPremiumAdd);
	 void deleteDate(String id);
	 void deletDateDetail(String id);
	 List<PubPremiumHistory> getHistorydetailList(PubPremiumHistory pubPremiumHistory);
	 List<PubPremiumHistory> getHistorydetailDateList(PubPremiumHistory pubPremiumHistory);
	 int getHistorydetailCount(PubPremiumHistory pubPremiumHistory);
	 int getHistorydetaiDatelCount(PubPremiumHistory pubPremiumHistory);
	 void deleteWeek(String id);
	 void deletWeekDetail(String id);
	 PubPremiumRule getRule(String id);
	 List<PubPremiumRuleWeekdetail> getWeekRule(String id);
	 List<PubPremiumRuleDatedetail> getDateRule(String id);
	 List<PubPremiumDetail> getdetailList(PubPremiumHistory pubPremiumHistory);
	 int getdetailCount(PubPremiumHistory pubPremiumHistory);
	 List<PubPremiumDetail> getdetailDateList(PubPremiumHistory pubPremiumHistory);
	 int getdetaiDatelCount(PubPremiumHistory pubPremiumHistory);
}
