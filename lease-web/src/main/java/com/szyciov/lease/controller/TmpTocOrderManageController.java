package com.szyciov.lease.controller;

import com.szyciov.lease.entity.User;
import com.szyciov.lease.param.TocOrderManageQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PageBean;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class TmpTocOrderManageController extends BaseController {

	private TemplateHelper templateHelper = new TemplateHelper();
	
	@RequestMapping(value = "/TmpOrderManage/NetAboutCarIndex")
	public String getNetAboutCarIndex(HttpServletRequest request) {
		return "resource/tmptocordermanage/index";
	}
	
	@RequestMapping(value = "/TmpOrderManage/TaxiIndex")
	public String getTaxiIndex(HttpServletRequest request) {
		return "resource/tmptocordermanage/taxiorder";
	}
	
	@RequestMapping("/TmpOrderManage/GetNetAboutCarOrderByQuery")
	@ResponseBody
	public PageBean getNetAboutCarOrderByQuery(@RequestBody TocOrderManageQueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		queryParam.setCompanyId(user.getLeasescompanyid());
        String queryTmpBelongleasecompany = SystemConfig.getSystemProperty(user.getAccount());
        if(StringUtils.isBlank(queryTmpBelongleasecompany)) {
            queryTmpBelongleasecompany = GUIDGenerator.newGUID();
        }
        queryParam.setQueryTmpBelongleasecompany(queryTmpBelongleasecompany);
		return templateHelper.dealRequestWithToken("/TmpOrderManage/GetNetAboutCarOrderByQuery", HttpMethod.POST, userToken,
				queryParam,PageBean.class);
	}
	
	@RequestMapping("/TmpOrderManage/GetTaxiOrderByQuery")
	@ResponseBody
	public PageBean getTaxiOrderByQuery(@RequestBody TocOrderManageQueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		queryParam.setCompanyId(user.getLeasescompanyid());
        String queryTmpBelongleasecompany = SystemConfig.getSystemProperty(user.getAccount());
        if(StringUtils.isBlank(queryTmpBelongleasecompany)) {
            queryTmpBelongleasecompany = GUIDGenerator.newGUID();
        }
        queryParam.setQueryTmpBelongleasecompany(queryTmpBelongleasecompany);
		return templateHelper.dealRequestWithToken("/TmpOrderManage/GetTaxiOrderByQuery", HttpMethod.POST, userToken,
				queryParam,PageBean.class);
	}

}
