package com.szyciov.lease.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.szyciov.lease.dto.balance.BalanceDto;
import com.szyciov.lease.mapper.BalanceManageMapper;
import com.szyciov.lease.param.BalanceManageQueryParam;

@Repository("BalanceManageDao")
public class BalanceManageDao {

	@Resource
	private BalanceManageMapper mapper;

	public BalanceManageMapper getMapper() {
		return mapper;
	}

	public void setMapper(BalanceManageMapper mapper) {
		this.mapper = mapper;
	}
	
	   public List<BalanceDto> queryBalanceList(BalanceManageQueryParam queryParam) {
	    	return mapper.queryBalanceList(queryParam);
	    }
		
	    public int queryBalanceListCount(BalanceManageQueryParam queryParam) {
	    	return mapper.queryBalanceListCount(queryParam);
	    }
		
	    public List<Map<String, Object>> getOrderNo(String orderno,String lecompanyid) {
	    	return mapper.getOrderNo(orderno,lecompanyid);
	    }
		
	    public List<Map<String, Object>> getDriverByNameOrPhone(String driver,String lecompanyid) {
	    	return mapper.getDriverByNameOrPhone(driver,lecompanyid);
	    }
		
	    public List<Map<String, Object>> getCompanyNameById(String lecompanyid) {
	    	return mapper.getCompanyNameById(lecompanyid);
	    }
	    
	    public List<Map<String, Object>> getJobnumByJobnum(String jobnum,String lecompanyid) {
	    	return mapper.getJobNum(jobnum,lecompanyid);
	    }
	    
	    public List<BalanceDto> getBalanceListExport(BalanceManageQueryParam queryParam) {
	    	return mapper.getBalanceListExport(queryParam);
	    }

		public List<Map<String, Object>> getFullPlateno(String fullplateno,String lecompanyid) {
			return mapper.getFullPlateno(fullplateno,lecompanyid);
		}
}
