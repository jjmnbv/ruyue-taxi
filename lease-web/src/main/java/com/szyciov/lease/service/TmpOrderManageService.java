package com.szyciov.lease.service;

import com.szyciov.lease.param.OrderManageQueryParam;
import com.szyciov.util.PageBean;
import com.szyciov.util.TemplateHelper;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service("TmpOrderManageService")
public class TmpOrderManageService {
	private TemplateHelper templateHelper = new TemplateHelper();

	public PageBean getOrgOrderByQuery(OrderManageQueryParam queryParam, String userToken) {
		return templateHelper.dealRequestWithToken("/TmpOrderManage/GetOrgOrderByQuery", HttpMethod.POST, userToken,
				queryParam, PageBean.class);
	}
}
