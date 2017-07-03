package com.szyciov.lease.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.lease.entity.OrgUserRefund;
import com.szyciov.lease.entity.User;
import com.szyciov.lease.param.UserRefundQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.PageBean;
import com.szyciov.util.TemplateHelper;

@Controller
public class UserRefundController extends BaseController {
    private static final Logger logger = Logger.getLogger(UserRefundController.class);
	
	private TemplateHelper templateHelper = new TemplateHelper();
	
	@RequestMapping(value = "/UserRefund/Index")
	public String getUserRefundIndex() {
		return "resource/userRefund/index";
	}
	
	@RequestMapping(value = "/UserRefund/RefundIndex")
	public String getRefundIndex() {
		return "resource/userRefund/refund";
	}
	
	@RequestMapping("/UserRefund/GetOrgUserRefundByQuery")
	@ResponseBody
	public PageBean getOrgUserRefundByQuery(@RequestBody UserRefundQueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		queryParam.setLeasesCompanyId(user.getLeasescompanyid());
		queryParam.setSpecialState(user.getSpecialstate());
		queryParam.setAccount(user.getAccount());
		
		// 0-待处理,1-已处理
		if ("0".equals(request.getParameter("refundstatus"))) {
			queryParam.setRefundStatus("0");
		} else if ("1".equals(request.getParameter("refundstatus"))) {
			queryParam.setRefundStatus("1");
		}
		
		return templateHelper.dealRequestWithToken("/UserRefund/GetOrgUserRefundByQuery", HttpMethod.POST, userToken,
				queryParam,PageBean.class);
	}
	
	@RequestMapping("/UserRefund/ConfirmRefund")
	@ResponseBody
	public Map<String, String> confirmRefund(@RequestBody OrgUserRefund orgUserRefund, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		orgUserRefund.setLeasesCompanyId(user.getLeasescompanyid());
		orgUserRefund.setUpdater(user.getId());
		
		OrgUserRefund orgUserRefund2 = templateHelper.dealRequestWithToken("/UserRefund/GetOrgUserRefundById/{id}", HttpMethod.GET, userToken, null,
				OrgUserRefund.class, orgUserRefund.getId());
		Map<String, String> ret = new HashMap<String, String>();
		if ("0".equals(orgUserRefund2.getRefundStatus())) {//0-待处理,1-已处理
			// 所属用户
			orgUserRefund.setUserId(orgUserRefund2.getUserId());
			// 退款金额
			orgUserRefund.setAmount(orgUserRefund2.getAmount());
			templateHelper.dealRequestWithToken("/UserRefund/ConfirmRefund", HttpMethod.POST, userToken, orgUserRefund,
					null);
			ret.put("ResultSign", "Successful");
			ret.put("MessageKey", "退款成功");
		} else if ("1".equals(orgUserRefund2.getRefundStatus())) {
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "退款失败");
		}

		return ret;
	}
	
	@RequestMapping("/UserRefund/GetOrgUserRefundById/{id}")
	@ResponseBody
	public OrgUserRefund getOrgUserRefundById(@PathVariable String id, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);

		return templateHelper.dealRequestWithToken("/UserRefund/GetOrgUserRefundById/{id}", HttpMethod.GET, userToken, null,
				OrgUserRefund.class, id);
	
	}
}
