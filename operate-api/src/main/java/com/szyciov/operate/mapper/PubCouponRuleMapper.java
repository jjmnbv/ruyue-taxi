package com.szyciov.operate.mapper;

import java.util.List;

import com.szyciov.entity.coupon.PubCouponRule;
import com.szyciov.entity.coupon.PubCouponRuleHistory;
import com.szyciov.op.param.PubCouponRuleQueryParam;

public interface PubCouponRuleMapper {
	
	List<PubCouponRule> getPubCouponRuleListByQuery(PubCouponRuleQueryParam queryParam);
	
	int getPubCouponRuleListCountByQuery(PubCouponRuleQueryParam queryParam);
	
	void createPubCouponRule(PubCouponRule pubCouponRule);
	
	void updatePubCouponRule(PubCouponRule pubCouponRule);
	
	void createPubCouponRuleHistory(PubCouponRuleHistory pubCouponRuleHistory);
	
	List<PubCouponRuleHistory> getPubCouponRuleHistoryListByQuery(PubCouponRuleQueryParam queryParam);
	
	int getPubCouponRuleHistoryListCountByQuery(PubCouponRuleQueryParam queryParam);
	
	int checkRuleNameExist(String name);
	
	PubCouponRule getPubCouponRuleById(String id);
}
