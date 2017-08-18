package com.szyciov.operate.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.dto.pubPremiumRule.PubPremiumAdd;
import com.szyciov.dto.pubPremiumRule.PubPremiumDetail;
import com.szyciov.dto.pubPremiumRule.PubPremiumHistory;
import com.szyciov.dto.pubPremiumRule.PubPremiumModify;
import com.szyciov.dto.pubPremiumRule.PubPremiumParam;
import com.szyciov.entity.PubPremiumRule;
import com.szyciov.entity.PubPremiumRuleDatedetail;
import com.szyciov.entity.PubPremiumRuleWeekdetail;
import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.operate.mapper.PubPremiumRuleMapper;

@Repository("PubPremiumRuleDao")
public class PubPremiumRuleDao {
	 public PubPremiumRuleDao(){
		}
	     private PubPremiumRuleMapper mapper;
	     @Resource
		 	public void setMapper(PubPremiumRuleMapper mapper) {
		 		this.mapper = mapper;
		 	}
	     public List<PubPremiumParam> getPubPremiumRuleByQueryList(PubPremiumParam pubPremiumParam){
	    	 return mapper.getPubPremiumRuleByQueryList(pubPremiumParam);
	     }
	     public int getPubPremiumRuleByQueryCount(PubPremiumParam pubPremiumParam){
	    	 return mapper.getPubPremiumRuleByQueryCount(pubPremiumParam);
	     }
	     public List<PubDictionary> getCity(){
	    	 return mapper.getCity();
	     }
	     public List<PubDictionary> getWeeks(String id){
		 		return mapper.getWeeks(id);
		 	}
	     public PubPremiumParam getRulename(String id)  {
	 		return mapper.getRulename(id);
	 	}
	     public List<PubPremiumParam> ruleConflict(PubPremiumParam pubPremiumParam){
	    	 return mapper.ruleConflict(pubPremiumParam);
	     }
	     public PubPremiumParam getById(PubPremiumParam pubPremiumParam){
	    	 return mapper.getById(pubPremiumParam);
	     }
	     public void updateStatus(PubPremiumParam pubPremiumParam){
	    	  mapper.updateStatus(pubPremiumParam);
	     }
	     public void ruleConflictOk(PubPremiumParam pubPremiumParam){
	    	  mapper.ruleConflictOk(pubPremiumParam);
	     }
	     public void insertIntoHistory( PubPremiumHistory pubPremiumHistory){
	    	 mapper.insertIntoHistory(pubPremiumHistory);
	     }
	     public String getCityId(String cityName){
	    	 return mapper.getCityId(cityName);
	     }
	     public void insertPubPremiumRule(PubPremiumRule pubPremiumRule){
	    	  mapper.insertPubPremiumRule(pubPremiumRule);
	     }
	     public void inserWeek(PubPremiumRuleWeekdetail weekdetail){
	    	 mapper.inserWeek(weekdetail);
	     }
	     public void insertDate(PubPremiumRuleDatedetail datedetail){
	    	 mapper.insertDate(datedetail);
	     }
	     public List<PubPremiumDetail> getPubPremiumRuleDetail(PubPremiumDetail pubPremiumDetail){
	 		return mapper.getPubPremiumRuleDetail(pubPremiumDetail);
	 	}
	 	public int getPubPremiumRuleDetailCount(PubPremiumDetail pubPremiumDetail){
	 		return mapper.getPubPremiumRuleDetailCount(pubPremiumDetail);
	 	}
	 	public List<PubPremiumDetail> getDetailDateDataList(PubPremiumDetail pubPremiumDetail){
	 		return mapper.getDetailDateDataList(pubPremiumDetail);
	 	}
	 	public int getDetailDateDataCount(PubPremiumDetail pubPremiumDetail){
	 		return mapper.getDetailDateDataCount(pubPremiumDetail);
	 	}
	 	public int ifHavePremiumRule(PubPremiumAdd pubPremiumAdd){
	 		 return mapper.ifHavePremiumRule(pubPremiumAdd);
	 	}
	 	public List<PubPremiumHistory> getHistoryDataList(PubPremiumHistory pubPremiumHistory){
			return mapper.getHistoryDataList(pubPremiumHistory);
		}
		public int getHistoryDataCount(PubPremiumHistory pubPremiumHistory){
			return mapper.getHistoryDataCount(pubPremiumHistory);
		}
		public PubPremiumModify modify(PubPremiumParam pubPremiumParam)  {
			return mapper.modify(pubPremiumParam);
		}
		public List<PubPremiumDetail> getWeek(String id){
			return mapper.getWeek(id);
		}
		public List<PubPremiumDetail> getDate(String id){
			return mapper.getDate(id);
		}
		public void insertWeekHistory(PubPremiumDetail pubPremiumDetail){
			mapper.insertWeekHistory(pubPremiumDetail);
		}
		public void insertDateHistory(PubPremiumDetail pubPremiumDetail){
			mapper.insertDateHistory(pubPremiumDetail);
		}
		public int getDateSame(PubPremiumAdd pubPremiumAdd){
	 		return mapper.getDateSame(pubPremiumAdd);
	 	}
		public void deleteDate(String id){
			mapper.deleteDate(id);
		}
		public void deletDateDetail(String id){
			mapper.deletDateDetail(id);
		}
		public List<PubPremiumHistory> getHistorydetailList(PubPremiumHistory pubPremiumHistory){
			return mapper.getHistorydetailList(pubPremiumHistory);
		}
		public List<PubPremiumHistory> getHistorydetailDateList(PubPremiumHistory pubPremiumHistory){
			return mapper.getHistorydetailDateList(pubPremiumHistory);
		}
		public int getHistorydetailCount(PubPremiumHistory pubPremiumHistory){
			return mapper.getHistorydetailCount(pubPremiumHistory);
		}
		public int getHistorydetaiDatelCount(PubPremiumHistory pubPremiumHistory){
			return mapper.getHistorydetaiDatelCount(pubPremiumHistory);
		}
		public void deleteWeek(String id){
			mapper.deleteWeek(id);
		}
		public void deletWeekDetail(String id){
			mapper.deletWeekDetail(id);
		}
		public PubPremiumRule getRule(String id){
			return mapper.getRule(id);
		}
		public List<PubPremiumRuleWeekdetail> getWeekRule(String id){
			return mapper.getWeekRule(id);
		}
		public List<PubPremiumRuleDatedetail> getDateRule(String id){
			return mapper.getDateRule(id);
		}
		public List<PubPremiumDetail> getdetailList(PubPremiumHistory pubPremiumHistory){
			return mapper.getdetailList(pubPremiumHistory);
		}
		public int getdetailCount(PubPremiumHistory pubPremiumHistory){
			return mapper.getdetailCount(pubPremiumHistory);
		}
		public List<PubPremiumDetail> getdetailDateList(PubPremiumHistory pubPremiumHistory){
			return mapper.getdetailDateList(pubPremiumHistory);
		}
		public int getdetaiDatelCount(PubPremiumHistory pubPremiumHistory){
			return mapper.getdetaiDatelCount(pubPremiumHistory);
		}
}
