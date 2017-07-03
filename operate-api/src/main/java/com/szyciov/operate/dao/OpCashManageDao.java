package com.szyciov.operate.dao;

import com.szyciov.op.param.cashManager.CashManageQueryParam;
import com.szyciov.op.vo.cashManager.CashManagerExcelVo;
import com.szyciov.op.vo.cashManager.CashManagerIndexVo;
import com.szyciov.operate.mapper.OpCashManageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class OpCashManageDao {

	@Autowired
	private OpCashManageMapper mapper;
	

	public List<Map<String, Object>> getAccounts(Map<String, String> params) {
		return mapper.getAccounts(params);
	}

	public List<Map<String, Object>> getNames(Map<String, String> params) {
		return mapper.getNames(params);
	}

	public int getListCashCountByQuery(CashManageQueryParam queryParam) {
		return mapper.getListCashCountByQuery(queryParam);
	}

	public List<CashManagerIndexVo> listCashByQuery(CashManageQueryParam queryParam) {
		return mapper.listCashByQuery(queryParam);
	}

	public void cashReject(Map<String, String> params) {
		mapper.cashReject(params);
	}
	
	public void cashOk(Map<String, String> params) {
		mapper.cashOk(params);
	}

	public List<CashManagerExcelVo> listExportData(CashManageQueryParam queryParam) {
		return mapper.listExportData(queryParam);
	}

	public int getListExportDataCount(CashManageQueryParam queryParam) {
		return mapper.getListExportDataCount(queryParam);
	}
	
	public Map<String,Object> getUserInfoByProcessId(String processid){
		return mapper.getUserInfoByProcessId(processid);
	}
	
	public void recoverMoney4PeUser(Map<String, Object> userinfo) {
		mapper.recoverMoney4PeUser(userinfo);
	}

	public void addRecord4PeUser(Map<String, Object> userinfo) {
		mapper.addRecord4PeUser(userinfo);
	}

	public void recoverMoney4Driver(Map<String, Object> userinfo) {
		mapper.recoverMoney4Driver(userinfo);
	}

	public void addRecord4Driver(Map<String, Object> userinfo) {
		mapper.addRecord4Driver(userinfo);
	}


}
