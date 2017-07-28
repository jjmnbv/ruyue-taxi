package com.szyciov.lease.controller;

import com.szyciov.lease.param.OrderManageQueryParam;
import com.szyciov.lease.service.TmpOrderManageService;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PageBean;
import com.szyciov.util.SystemConfig;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class TmpOrderManageController extends BaseController{
	
	public static final Logger logger = Logger.getLogger(DictionaryController.class);

    @Resource(name="TmpOrderManageService")
	private TmpOrderManageService tmpOrderManageService;

	/**
	 * 机构订单当前订单页
	 * @return
	 */
	@RequestMapping(value = "/TmpOrderManage/OrgCurrentOrderIndex")
	public String getOrgCurrentOrderIndex() {
		return "resource/tmporgordermanage/currentorder";
	}
	
	/**
	 * 个人订单当前订单页
	 * @return
	 */
	@RequestMapping(value = "/TmpOrderManage/PersonCurrentOrderIndex")
	public String getPersonCurrentOrderIndex() {
		return "resource/tmppersonordermanage/currentorder";
	}
	
	/**
	 * 机构异常订单页 - 待复核
	 * @return
	 */
	@RequestMapping(value = "/TmpOrderManage/OrgAbnormalOrderIndex")
	public String getOrgAbnormalOrderIndex() {
		return "resource/tmporgordermanage/abnormalorder";
	}
	
	/**
	 * 机构异常订单页 - 已复核
	 * @return
	 */
	@RequestMapping(value = "/TmpOrderManage/OrgWasAbnormalOrderIndex")
	public String getOrgWasAbnormalOrderIndex() {
		return "resource/tmporgordermanage/wasabnormalorder";
	}
	
	/**
	 * 个人异常订单页 - 待复核
	 * @return
	 */
	@RequestMapping(value = "/TmpOrderManage/PersonAbnormalOrderIndex")
	public String getPersonAbnormalOrderIndex() {
		return "resource/tmppersonordermanage/abnormalorder";
	}
	
	/**
	 * 个人异常订单页 - 已复核
	 * @return
	 */
	@RequestMapping(value = "/TmpOrderManage/PersonWasAbnormalOrderIndex")
	public String getPersonWasAbnormalOrderIndex() {
		return "resource/tmppersonordermanage/wasabnormalorder";
	}
	
	/**
	 * 机构历史订单页
	 * @return
	 */
	@RequestMapping(value = "/TmpOrderManage/OrgHistoryOrderIndex")
	public String getOrgHistoryOrderIndex() {
		return "resource/tmporgordermanage/historyorder";
	}
	
	/**
	 * 个人历史订单页
	 * @return
	 */
	@RequestMapping(value = "/TmpOrderManage/PersonHistoryOrderIndex")
	public String getPersonHistoryOrderIndex() {
		return "resource/tmppersonordermanage/historyorder";
	}
	
	/**
	 * 机构待收款订单页
	 * @return
	 */
	@RequestMapping(value = "/TmpOrderManage/OrgWaitgatheringOrderIndex")
	public String getOrgWaitgatheringOrderIndex() {
		return "resource/tmporgordermanage/waitgatheringorder";
	}
	
	/**
	 * 个人待收款订单页
	 * @return
	 */
	@RequestMapping(value = "TmpOrderManage/PersonWaitgatheringOrderIndex")
	public String getPersonWaitgatheringOrderIndex() {
		return "resource/tmppersonordermanage/waitgatheringorder";
	}

	/**
	 * 获取机构订单数据
	 * @param queryParam
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/TmpOrderManage/GetOrgOrderByQuery")
	@ResponseBody
	public PageBean getOrgOrderByQuery(@RequestBody OrderManageQueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		initLeUserInfo(request, queryParam);
        String queryTmpBelongleasecompany = SystemConfig.getSystemProperty(queryParam.getAccount());
        if(StringUtils.isBlank(queryTmpBelongleasecompany)) {
            queryTmpBelongleasecompany = GUIDGenerator.newGUID();
        }
        queryParam.setQueryTmpBelongleasecompany(queryTmpBelongleasecompany);
		
		return tmpOrderManageService.getOrgOrderByQuery(queryParam, userToken);
	}


}
