package com.szyciov.operate.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.lease.entity.OrgUserRefund;
import com.szyciov.op.entity.PeUserRefund;
import com.szyciov.operate.service.UserRefundService;
import com.szyciov.param.QueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;

/**
 * 退款管理模块控制器
 */
@Controller
public class UserRefundController extends BaseController {
	private static final Logger logger = Logger.getLogger(UserRefundController.class);

	public UserRefundService userRefundService;

	@Resource(name = "userRefundService")
	public void setUserRefundService(UserRefundService userRefundService) {
		this.userRefundService = userRefundService;
	}
	
	/** 
	 * <p>分页查询待退款/已退款</p>
	 *
	 * @param queryParam 查询请求对象，封装需要查询的key和页码等信息
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "api/UserRefund/GetPeUserRefundByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getPeUserRefundByQuery(@RequestBody QueryParam queryParam) {
		return userRefundService.getPeUserRefundByQuery(queryParam);
	}
	
	/** 
	 * <p>确认退款</p>
	 *
	 * @param peUserRefund
	 * @return
	 */
	@RequestMapping(value = "api/UserRefund/ConfirmRefund", method = RequestMethod.POST)
	@ResponseBody
	public void confirmRefund(@RequestBody PeUserRefund peUserRefund) {
		userRefundService.confirmRefund(peUserRefund);
	}
	
	/** 
	 * <p>查看退款信息</p>
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "api/UserRefund/GetPeUserRefundById/{id}", method = RequestMethod.GET)
	@ResponseBody
	public PeUserRefund getPeUserRefundById(@PathVariable String id) {
		return userRefundService.getPeUserRefundById(id);
	}
}
