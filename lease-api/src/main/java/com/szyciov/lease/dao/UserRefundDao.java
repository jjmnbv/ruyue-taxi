package com.szyciov.lease.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.lease.entity.OrgUserExpenses;
import com.szyciov.lease.entity.OrgUserRefund;
import com.szyciov.lease.mapper.UserRefundMapper;
import com.szyciov.lease.param.UserRefundQueryParam;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.org.entity.OrgUserNews;

@Repository("UserRefundDao")
public class UserRefundDao {
	public UserRefundDao() {
	}

	private UserRefundMapper mapper;

	@Resource
	public void setMapper(UserRefundMapper mapper) {
		this.mapper = mapper;
	}
	
    public List<OrgUserRefund> getOrgUserRefundListByQuery(UserRefundQueryParam queryParam) {
    	return mapper.getOrgUserRefundListByQuery(queryParam);
    }
	
	public int getOrgUserRefundListCountByQuery(UserRefundQueryParam queryParam) {
		return mapper.getOrgUserRefundListCountByQuery(queryParam);
	}
	
	public void updateOrgUserRefund(Map<String, String> map) {
		mapper.updateOrgUserRefund(map);
	}
	
	public void updateOrgUserAccount(Map<String, Object> map) {
		mapper.updateOrgUserAccount(map);
	}
	
	public void createOrgUserExpenses(OrgUserExpenses orgUserExpenses) {
		mapper.createOrgUserExpenses(orgUserExpenses);
	}
	
	public OrgUserRefund getOrgUserRefundById(String id) {
		return mapper.getOrgUserRefundById(id);
	}
	
	public BigDecimal getBalanceByUserId(Map<String, String> map) {
		return mapper.getBalanceByUserId(map);
	}
	
	public void createOrgUserNews(OrgUserNews orgUserNews) {
		mapper.createOrgUserNews(orgUserNews);
	}
	
    public int checkOrgUserAccountExist(Map<String, String> map) {
    	return mapper.checkOrgUserAccountExist(map);
    }
	
	public void createOrgUserAccount(Map<String, Object> map) {
		mapper.createOrgUserAccount(map);
	}
	
	public OrgOrder getOrgOrderById(String orderNo) {
		return mapper.getOrgOrderById(orderNo);
	}
	
	public String getUserIdByUserId(String userId) {
		return mapper.getUserIdByUserId(userId);
	}
	
    public Map<String, Object> getLeasesCompanyInfo(String leasesCompanyId) {
    	return mapper.getLeasesCompanyInfo(leasesCompanyId);
    }
	
	public Map<String, Object> getOrgUserInfo(String id) {
		return mapper.getOrgUserInfo(id);
	}
}
