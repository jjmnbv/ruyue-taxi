package com.szyciov.lease.mapper;

import java.util.List;
import java.util.Map;

import com.szyciov.lease.entity.LeCashManage;
import com.szyciov.lease.param.CashManageQueryParam;
import com.szyciov.org.entity.OrgUser;

public interface LeCashManageMapper {

	List<Map<String, Object>> getAccounts(Map<String, String> params);

	List<Map<String, Object>> getNames(Map<String, String> params);

	int getCashListCountByQuery(CashManageQueryParam queryParam);

	List<LeCashManage> getCashListByQuery(CashManageQueryParam queryParam);

	void cashReject(Map<String, String> params);

	void cashOk(Map<String, String> params);

	List<LeCashManage> getAllUnderCashData(Map<String, String> params);

	int getExportDataCount(Map<String, String> params);
	
	List<String> getUsersBySuperId(String superid);

	Map<String, Object> getUserInfoByProcessId(String processid);

	void recoverMoney4OrgUser(Map<String, Object> userinfo);

	void addRecord4OrgUser(Map<String, Object> userinfo);

	void recoverMoney4Driver(Map<String, Object> userinfo);

	void addRecord4Driver(Map<String, Object> userinfo);

	void recoverMoney4Org(Map<String, Object> userinfo);

	void addRecord4Org(Map<String, Object> userinfo);

	Map<String, Object> getUserBlance4OrgUser(Map<String, Object> userinfo);

	Map<String, Object> getUserBlance4Driver(Map<String, Object> userinfo);

	Map<String, Object> getUserBlance4Org(Map<String, Object> userinfo);

	Map<String, Object> getCompanyById(String companyid);

	void addNews4OrgUser(Map<String, Object> newsparams);

	void addNews4Driver(Map<String, Object> newsparams);

	Map<String, Object> getOrgan4Company(Map<String, Object> userinfo);

	List<OrgUser> getSAC(String organid);

	void addNews2Org(Map<String, Object> param);
	
}
