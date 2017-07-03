package com.szyciov.lease.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.szyciov.lease.entity.LeCashManage;
import com.szyciov.lease.param.CashManageQueryParam;
import com.szyciov.util.PageBean;
import com.szyciov.util.TemplateHelper;

@Service("leCashManageService")
public class LeCashManageService {
	
	private TemplateHelper templateHelper = new TemplateHelper();

	public List<Map<String, Object>> getAccounts(Map<String, Object> params, String userToken) {
		return templateHelper.dealRequestWithToken("/LeCashManage/GetAccounts", HttpMethod.POST, userToken, params, List.class);
	}

	public List<Map<String, Object>> getNames(Map<String, Object> params, String userToken) {
		return templateHelper.dealRequestWithToken("/LeCashManage/GetNames", HttpMethod.POST, userToken, params, List.class);
	}

	public PageBean getCashByQuery(CashManageQueryParam queryParam, String usertoken) {
		return templateHelper.dealRequestWithToken("/LeCashManage/GetCashByQuery", HttpMethod.POST, usertoken, queryParam, PageBean.class);
	}

	public Map<String, Object> cashReject(Map<String, Object> params, String usertoken) {
		return templateHelper.dealRequestWithToken("/LeCashManage/CashReject", HttpMethod.POST, usertoken, params, Map.class);
	}

	public Map<String, Object> cashOk(Map<String, Object> params, String usertoken) {
		return templateHelper.dealRequestWithToken("/LeCashManage/CashOk", HttpMethod.POST, usertoken, params, Map.class);
	}

	public List<Map<String,Object>> getAllUnderCashData(Map<String, Object> params, String usertoken) {
		return templateHelper.dealRequestWithToken("/LeCashManage/GetAllUnderCashData", HttpMethod.POST, usertoken, params, List.class);
	}

	public Map<String, Object> getExportDataCount(Map<String, Object> params, String usertoken) {
		return templateHelper.dealRequestWithToken("/LeCashManage/GetExportDataCount", HttpMethod.POST, usertoken, params, Map.class);
	}
}
