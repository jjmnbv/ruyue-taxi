package com.szyciov.lease.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.lease.entity.LeCashManage;
import com.szyciov.lease.mapper.LeCashManageMapper;
import com.szyciov.lease.param.CashManageQueryParam;
import com.szyciov.org.entity.OrgUser;

@Repository("leCashManageDao")
public class LeCashManageDao {
	
	private LeCashManageMapper mapper;
	
	@Resource
	public void setMapper(LeCashManageMapper mapper) {
		this.mapper = mapper;
	}

	public List<Map<String, Object>> getAccounts(Map<String, String> params) {
		return mapper.getAccounts(params);
	}

	public List<Map<String, Object>> getNames(Map<String, String> params) {
		return mapper.getNames(params);
	}

	public int getCashListCountByQuery(CashManageQueryParam queryParam) {
		return mapper.getCashListCountByQuery(queryParam);
	}

	public List<LeCashManage> getCashListByQuery(CashManageQueryParam queryParam) {
		return mapper.getCashListByQuery(queryParam);
	}

	public void cashReject(Map<String, String> params) {
		mapper.cashReject(params);
	}
	
	public void cashOk(Map<String, String> params) {
		mapper.cashOk(params);
	}

	public List<LeCashManage> getAllUnderCashData(Map<String, String> params) {
		return mapper.getAllUnderCashData(params);
	}

	public int getExportDataCount(Map<String, String> params) {
		return mapper.getExportDataCount(params);
	}
	
	public Map<String,Object> getUserInfoByProcessId(String processid){
		return mapper.getUserInfoByProcessId(processid);
	}
	
	public List<String> getUsersBySuperId(String superid){
		return mapper.getUsersBySuperId(superid);
	}

	public void recoverMoney4OrgUser(Map<String, Object> userinfo) {
		mapper.recoverMoney4OrgUser(userinfo);
	}

	public void addRecord4OrgUser(Map<String, Object> userinfo) {
		mapper.addRecord4OrgUser(userinfo);
	}

	public void recoverMoney4Driver(Map<String, Object> userinfo) {
		mapper.recoverMoney4Driver(userinfo);
	}

	public void addRecord4Driver(Map<String, Object> userinfo) {
		mapper.addRecord4Driver(userinfo);
	}

	public void recoverMoney4Org(Map<String, Object> userinfo) {
		mapper.recoverMoney4Org(userinfo);
	}

	public void addRecord4Org(Map<String, Object> userinfo) {
		mapper.addRecord4Org(userinfo);
	}

	public Map<String, Object> getUserBlance4OrgUser(Map<String, Object> userinfo) {
		return mapper.getUserBlance4OrgUser(userinfo);
	}

	public Map<String, Object> getUserBlance4Driver(Map<String, Object> userinfo) {
		return mapper.getUserBlance4Driver(userinfo);
	}

	public Map<String, Object> getUserBlance4Org(Map<String, Object> userinfo) {
		return mapper.getUserBlance4Org(userinfo);
	}

	public Map<String, Object> getCompanyById(String companyid) {
		return mapper.getCompanyById(companyid);
	}

	public void addNews4OrgUser(Map<String,Object> newsparams) {
		mapper.addNews4OrgUser(newsparams);
	}

	public void addNews4Driver(Map<String, Object> newsparams) {
		mapper.addNews4Driver(newsparams);
	}

	public Map<String, Object> getOrgan4Company(Map<String, Object> userinfo) {
		return mapper.getOrgan4Company(userinfo);
	}

	public List<OrgUser> getSAC(String organid) {
		return mapper.getSAC(organid);
	}

	public void addNews2Org(Map<String, Object> param) {
		mapper.addNews2Org(param);
	}

}
