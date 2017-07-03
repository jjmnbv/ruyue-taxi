package com.szyciov.lease.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.szyciov.lease.entity.OrgUserExpenses;
import com.szyciov.lease.entity.OrgUserRefund;
import com.szyciov.lease.param.UserRefundQueryParam;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.org.entity.OrgUserNews;

public interface UserRefundMapper {
	
	List<OrgUserRefund> getOrgUserRefundListByQuery(UserRefundQueryParam queryParam);
	
	int getOrgUserRefundListCountByQuery(UserRefundQueryParam queryParam);
	
	void updateOrgUserRefund(Map<String, String> map);
	
	void updateOrgUserAccount(Map<String, Object> map);
	
	void createOrgUserExpenses(OrgUserExpenses orgUserExpenses);
	
	OrgUserRefund getOrgUserRefundById(String id);
	
	BigDecimal getBalanceByUserId(Map<String, String> map);
	
	void createOrgUserNews(OrgUserNews orgUserNews);
	
	int checkOrgUserAccountExist(Map<String, String> map);
	
	void createOrgUserAccount(Map<String, Object> map);
	
	OrgOrder getOrgOrderById(String orderNo);
	
	String getUserIdByUserId(String userId);
	
	Map<String, Object> getLeasesCompanyInfo(String leasesCompanyId);
	
	Map<String, Object> getOrgUserInfo(String id);
}
