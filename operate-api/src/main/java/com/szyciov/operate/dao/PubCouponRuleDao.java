package com.szyciov.operate.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.entity.coupon.PubCouponRule;
import com.szyciov.entity.coupon.PubCouponRuleHistory;
import com.szyciov.op.param.PubCouponRuleQueryParam;
import com.szyciov.operate.mapper.PubCouponRuleMapper;

@Repository("PubCouponRuleDao")
public class PubCouponRuleDao {
	public PubCouponRuleDao() {
	}

	private PubCouponRuleMapper mapper;

	@Resource
	public void setMapper(PubCouponRuleMapper mapper) {
		this.mapper = mapper;
	}
	
    public List<PubCouponRule> getPubCouponRuleListByQuery(PubCouponRuleQueryParam queryParam) {
    	return mapper.getPubCouponRuleListByQuery(queryParam);
    }
	
    public int getPubCouponRuleListCountByQuery(PubCouponRuleQueryParam queryParam) {
    	return mapper.getPubCouponRuleListCountByQuery(queryParam);
    }
	
    public void createPubCouponRule(PubCouponRule pubCouponRule) {
    	mapper.createPubCouponRule(pubCouponRule);
    }
	
    public void updatePubCouponRule(PubCouponRule pubCouponRule) {
    	mapper.updatePubCouponRule(pubCouponRule);
    }
	
    public void createPubCouponRuleHistory(PubCouponRuleHistory pubCouponRuleHistory) {
    	mapper.createPubCouponRuleHistory(pubCouponRuleHistory);
    }
	
    public List<PubCouponRuleHistory> getPubCouponRuleHistoryListByQuery(PubCouponRuleQueryParam queryParam) {
    	return mapper.getPubCouponRuleHistoryListByQuery(queryParam);
    }
	
    public int getPubCouponRuleHistoryListCountByQuery(PubCouponRuleQueryParam queryParam) {
    	return mapper.getPubCouponRuleHistoryListCountByQuery(queryParam);
    }
    
    public int checkRuleNameExist(String name) {
    	return mapper.checkRuleNameExist(name);
    }
    
    public PubCouponRule getPubCouponRuleById(String id) {
    	return mapper.getPubCouponRuleById(id);
    }
}
