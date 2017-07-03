package com.szyciov.operate.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.szyciov.lease.entity.OrgUserExpenses;
import com.szyciov.op.entity.OpOrder;
import com.szyciov.op.entity.PeUserRefund;
import com.szyciov.org.entity.OrgUserNews;
import com.szyciov.param.QueryParam;

public interface UserRefundMapper {
	
	List<PeUserRefund> getPeUserRefundListByQuery(QueryParam queryParam);
	
	int getPeUserRefundListCountByQuery(String key);
	
	void updatePeUserRefund(Map<String, String> map);
	
	void updateOpUserAccount(Map<String, Object> map);
	
	void createOpUserExpenses(OrgUserExpenses orgUserExpenses);
	
	PeUserRefund getPeUserRefundById(String id);
	
	BigDecimal getBalanceByUserId(String userId);
	
	void createPeUserNews(OrgUserNews orgUserNews);
	
	int checkPeUserAccountExist(String userId);
	
	void createPeUserAccount(Map<String, Object> map);
	
	OpOrder getOpOrderById(String orderNo);
	
	Map<String, Object> getOpPlatformInfo();
	
	Map<String, Object> getPeUserInfo(String id);
}
