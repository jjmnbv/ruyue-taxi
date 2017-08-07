package com.szyciov.operate.controller;

import com.szyciov.lease.param.OrderManageQueryParam;
import com.szyciov.op.entity.OpUser;
import com.szyciov.operate.service.TmpOrderManageService;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PageBean;
import com.szyciov.util.SystemConfig;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 运营端订单管理
 */
@Controller
public class TmpOrderManageController extends BaseController {

    @Resource(name = "TmpOrderManageService")
	private TmpOrderManageService orderManageService;

	/**
	 * 当前订单首页
	 * @return
	 */
	@RequestMapping(value = "/TmpOrderManage/CurrentOrderIndex")
	public String currentOrderIndex() {
		return "resource/tmpordermanage/currentorder";
	}
	
	/**
	 * 异常订单首页(待复核)
	 * @return
	 */
	@RequestMapping(value = "/TmpOrderManage/AbnormalOrderIndex")
	public String abnormalOrderIndex() {
		return "resource/tmpordermanage/abnormalorder";
	}
	
	/**
	 * 异常订单首页(已复核)
	 * @return
	 */
	@RequestMapping(value = "/TmpOrderManage/WasAbnormalOrderIndex")
	public String getWasAbnormalOrderIndex() {
		return "resource/tmpordermanage/wasabnormalorder";
	}
	
	/**
	 * 个人历史订单首页
	 * @return
	 */
	@RequestMapping(value = "/TmpOrderManage/HistoryOrderIndex")
	public String getHistoryOrderIndex() {
		return "resource/tmpordermanage/historyorder";
	}
	
	/**
	 * 个人待收款订单页
	 * @return
	 */
	@RequestMapping(value = "TmpOrderManage/WaitgatheringOrderIndex")
	public String getWaitgatheringOrderIndex() {
		return "resource/tmpordermanage/waitgatheringorder";
	}

	/**
	 * 分页查询个人订单数据
	 * @param queryParam
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/TmpOrderManage/GetOpOrderByQuery")
	@ResponseBody
	public PageBean getOpOrderByQuery(@RequestBody OrderManageQueryParam queryParam, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OpUser user = getLoginOpUser(request);
		queryParam.setOpUserId(user.getId());
		queryParam.setUsertype(user.getUsertype());
        String queryTmpBelongleasecompany = SystemConfig.getSystemProperty(user.getAccount());
        if(StringUtils.isBlank(queryTmpBelongleasecompany)) {
            queryTmpBelongleasecompany = GUIDGenerator.newGUID();
        }
        queryParam.setQueryTmpBelongleasecompany(queryTmpBelongleasecompany);
		return orderManageService.getOpOrderByQuery(queryParam, userToken);
	}
}
