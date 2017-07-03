package com.szyciov.operate.mapper;

import com.szyciov.lease.entity.LeCashManage;
import com.szyciov.op.param.cashManager.CashManageQueryParam;
import com.szyciov.op.vo.cashManager.CashManagerExcelVo;
import com.szyciov.op.vo.cashManager.CashManagerIndexVo;

import java.util.List;
import java.util.Map;

public interface OpCashManageMapper {

	List<Map<String, Object>> getAccounts(Map<String, String> params);

	List<Map<String, Object>> getNames(Map<String, String> params);

	int getListCashCountByQuery(CashManageQueryParam queryParam);

	List<CashManagerIndexVo> listCashByQuery(CashManageQueryParam queryParam);

	void cashReject(Map<String, String> params);

	void cashOk(Map<String, String> params);

	List<CashManagerExcelVo> listExportData(CashManageQueryParam queryParam);

	int getListExportDataCount(CashManageQueryParam queryParam);
	

	Map<String, Object> getUserInfoByProcessId(String processid);

	void recoverMoney4PeUser(Map<String, Object> userinfo);

	void addRecord4PeUser(Map<String, Object> userinfo);

	void recoverMoney4Driver(Map<String, Object> userinfo);

	void addRecord4Driver(Map<String, Object> userinfo);

}
