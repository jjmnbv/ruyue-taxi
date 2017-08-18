package com.szyciov.lease.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szyciov.lease.dao.BalanceManageDao;
import com.szyciov.lease.dto.balance.BalanceDto;
import com.szyciov.lease.param.BalanceManageQueryParam;
import com.szyciov.op.param.TourFeeManagementQueryParam;
import com.szyciov.util.PageBean;

@Service("BalanceManageService")
public class BalanceManageService {

	@Resource(name="BalanceManageDao")
	private BalanceManageDao dao;

	public BalanceManageDao getDao() {
		return dao;
	}

	public void setDao(BalanceManageDao dao) {
		this.dao = dao;
	}
	
	public PageBean queryBalanceByParam(BalanceManageQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<BalanceDto> list = queryBalanceList(queryParam);
		int iTotalRecords = queryBalanceListCount(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	
	public List<BalanceDto> queryBalanceList(BalanceManageQueryParam queryParam) {
    	return dao.queryBalanceList(queryParam);
    }
	
    public int queryBalanceListCount(BalanceManageQueryParam queryParam) {
    	return dao.queryBalanceListCount(queryParam);
    }
	
    public List<Map<String, Object>> getOrderNo(String orderno, String lecompanyid) {
    	return dao.getOrderNo(orderno,lecompanyid);
    }
	
    public List<Map<String, Object>> getDriverByNameOrPhone(String driver, String lecompanyid) {
    	return dao.getDriverByNameOrPhone(driver,lecompanyid);
    }
	
    public List<Map<String, Object>> getCompanyNameById(String lecompanyid) {
    	return dao.getCompanyNameById(lecompanyid);
    }
    
    public List<Map<String, Object>> getJobnumByJobnum(String jobnum, String lecompanyid) {
    	return dao.getJobnumByJobnum(jobnum,lecompanyid);
    }
    
    public List<BalanceDto> getBalanceListExport(BalanceManageQueryParam queryParam) {
    	return dao.getBalanceListExport(queryParam);
    }

	public List<Map<String, Object>> getFullPlateno(String fullplateno, String lecompanyid) {
		return dao.getFullPlateno(fullplateno,lecompanyid);
	}
}
