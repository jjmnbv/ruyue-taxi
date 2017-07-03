package com.szyciov.operate.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.lease.entity.OrgUserExpenses;
import com.szyciov.op.entity.OpOrder;
import com.szyciov.op.entity.PeUserRefund;
import com.szyciov.operate.mapper.UserRefundMapper;
import com.szyciov.org.entity.OrgUserNews;
import com.szyciov.param.QueryParam;

@Repository("UserRefundDao")
public class UserRefundDao {
	public UserRefundDao() {
	}

	private UserRefundMapper mapper;

	@Resource
	public void setMapper(UserRefundMapper mapper) {
		this.mapper = mapper;
	}
	
    public List<PeUserRefund> getPeUserRefundListByQuery(QueryParam queryParam) {
    	return mapper.getPeUserRefundListByQuery(queryParam);
    }
	
	public int getPeUserRefundListCountByQuery(String key) {
		return mapper.getPeUserRefundListCountByQuery(key);
	}
	
	public void updatePeUserRefund(Map<String, String> map) {
		mapper.updatePeUserRefund(map);
	}
	
	public void updateOpUserAccount(Map<String, Object> map) {
		mapper.updateOpUserAccount(map);
	}
	
    public void createOpUserExpenses(OrgUserExpenses orgUserExpenses) {
    	mapper.createOpUserExpenses(orgUserExpenses);
    }
	
	public PeUserRefund getPeUserRefundById(String id) {
		return mapper.getPeUserRefundById(id);
	}
	
	public BigDecimal getBalanceByUserId(String userId) {
		return mapper.getBalanceByUserId(userId);
	}
	
	public void createPeUserNews(OrgUserNews orgUserNews) {
		mapper.createPeUserNews(orgUserNews);
	}
	
    public int checkPeUserAccountExist(String userId) {
    	return mapper.checkPeUserAccountExist(userId);
    }
	
	public void createPeUserAccount(Map<String, Object> map) {
		mapper.createPeUserAccount(map);
	}
	
	public OpOrder getOpOrderById(String orderNo) {
		return mapper.getOpOrderById(orderNo);
	}
	
    public Map<String, Object> getOpPlatformInfo() {
    	return mapper.getOpPlatformInfo();
    }
	
	public Map<String, Object> getPeUserInfo(String id) {
		return mapper.getPeUserInfo(id);
	}
}
