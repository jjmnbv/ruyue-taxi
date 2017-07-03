package com.szyciov.operate.controller;

import java.io.IOException;
import java.util.HashMap;
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
import com.szyciov.op.entity.OpUser;
import com.szyciov.op.entity.PeUserRefund;
import com.szyciov.param.QueryParam;
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
	
	@RequestMapping("/UserRefund/GetPeUserRefundByQuery")
	@ResponseBody
	public PageBean getPeUserRefundByQuery(@RequestBody QueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		// 0-待处理,1-已处理
		if ("0".equals(request.getParameter("refundstatus"))) {
			queryParam.setKey("0");
		} else if ("1".equals(request.getParameter("refundstatus"))) {
			queryParam.setKey("1");
		}
		
		return templateHelper.dealRequestWithToken("/UserRefund/GetPeUserRefundByQuery", HttpMethod.POST, userToken,
				queryParam,PageBean.class);
	}
	
	@RequestMapping("/UserRefund/ConfirmRefund")
	@ResponseBody
	public Map<String, String> confirmRefund(@RequestBody PeUserRefund peUserRefund, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OpUser opUser = getLoginOpUser(request);
		peUserRefund.setUpdater(opUser.getId());
		
		PeUserRefund peUserRefund2 = templateHelper.dealRequestWithToken("/UserRefund/GetPeUserRefundById/{id}", HttpMethod.GET, userToken, null,
				PeUserRefund.class, peUserRefund.getId());
		Map<String, String> ret = new HashMap<String, String>();
		if ("0".equals(peUserRefund2.getRefundStatus())) {//0-待处理,1-已处理
			// 所属用户
			peUserRefund.setUserId(peUserRefund2.getUserId());
			// 退款金额
			peUserRefund.setAmount(peUserRefund2.getAmount());
			templateHelper.dealRequestWithToken("/UserRefund/ConfirmRefund", HttpMethod.POST, userToken, peUserRefund,
					null);
			ret.put("ResultSign", "Successful");
			ret.put("MessageKey", "退款成功");			
		} else if ("1".equals(peUserRefund2.getRefundStatus())) {
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "退款失败");
		}

		return ret;
	}
	
	@RequestMapping("/UserRefund/GetPeUserRefundById/{id}")
	@ResponseBody
	public PeUserRefund getPeUserRefundById(@PathVariable String id, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);

		return templateHelper.dealRequestWithToken("/UserRefund/GetPeUserRefundById/{id}", HttpMethod.GET, userToken, null,
				PeUserRefund.class, id);
	
	}
}
