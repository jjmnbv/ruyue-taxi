package com.szyciov.operate.service;

import com.szyciov.op.param.cashManager.CashManageQueryParam;
import com.szyciov.util.PageBean;
import com.szyciov.util.TemplateHelper;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("leCashManageService")
public class OpCashManageService {
	
	private TemplateHelper templateHelper = new TemplateHelper();

	public List<Map<String, Object>> getAccounts(Map<String, Object> params, String userToken) {
		return templateHelper.dealRequestWithToken("/OpCashManage/GetAccounts", HttpMethod.POST, userToken, params, List.class);
	}

	public List<Map<String, Object>> getNames(Map<String, Object> params, String userToken) {
		return templateHelper.dealRequestWithToken("/OpCashManage/GetNames", HttpMethod.POST, userToken, params, List.class);
	}

	public PageBean getCashByQuery(CashManageQueryParam queryParam, String usertoken) {
		return templateHelper.dealRequestWithToken("/OpCashManage/GetCashByQuery", HttpMethod.POST, usertoken, queryParam, PageBean.class);
	}

	public Map<String, Object> cashReject(Map<String, Object> params, String usertoken) {
		return templateHelper.dealRequestWithToken("/OpCashManage/CashReject", HttpMethod.POST, usertoken, params, Map.class);
	}

	public Map<String, Object> cashOk(Map<String, Object> params, String usertoken) {
		return templateHelper.dealRequestWithToken("/OpCashManage/CashOk", HttpMethod.POST, usertoken, params, Map.class);
	}

	public List<Map<String,Object>> listExportData(CashManageQueryParam queryParam, String usertoken) {
		return templateHelper.dealRequestWithToken("/OpCashManage/listExportData", HttpMethod.POST, usertoken, queryParam, List.class);
	}

	public Map<String, Object> getListExportDataCount(CashManageQueryParam queryParam, String usertoken) {
		return templateHelper.dealRequestWithToken("/OpCashManage/getListExportDataCount", HttpMethod.POST, usertoken, queryParam, Map.class);
	}
}
