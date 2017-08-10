package com.szyciov.lease.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.entity.coupon.PubCouponRule;
import com.szyciov.lease.param.PubCouponRuleQueryParam;
import com.szyciov.lease.service.PubCouponRuleService;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;

/**
 * 运营管理-规则管理 控制器
 * */
@Controller
public class PubCouponRuleController extends BaseController {
	private static final Logger logger = Logger.getLogger(PubCouponRuleController.class);

	public PubCouponRuleService pubCouponRuleService;

	@Resource(name = "pubCouponRuleService")
	public void setPubCouponRuleService(PubCouponRuleService pubCouponRuleService) {
		this.pubCouponRuleService = pubCouponRuleService;
	}
	
	/** 
	 * <p>分页查询发放规则信息</p>
	 *
	 * @param queryParam 查询请求对象，封装需要查询的key和页码等信息
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "api/PubCouponRule/GetPubCouponRuleByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getPubCouponRuleByQuery(@RequestBody PubCouponRuleQueryParam queryParam) {
		return pubCouponRuleService.getPubCouponRuleByQuery(queryParam);
	}
	
	/** 
	 * <p>新增发放规则</p>
	 *
	 * @param pubCouponRule
	 * @return
	 */
	@RequestMapping(value = "api/PubCouponRule/CreatePubCouponRule", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> createPubCouponRule(@RequestBody PubCouponRule pubCouponRule) {
		return pubCouponRuleService.createPubCouponRule(pubCouponRule);
	}
	
	/** 
	 * <p>修改发放规则</p>
	 *
	 * @param leAccountRules
	 * @return
	 */
	@RequestMapping(value = "api/PubCouponRule/UpdatePubCouponRule", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updatePubCouponRule(@RequestBody PubCouponRule pubCouponRule) {
		return pubCouponRuleService.updatePubCouponRule(pubCouponRule);
	}
	
	/** 
	 * <p>分页查询历史规则记录</p>
	 *
	 * @param queryParam 查询请求对象，封装需要查询的key和页码等信息
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "api/PubCouponRule/GetPubCouponRuleHistoryByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getPubCouponRuleHistoryByQuery(@RequestBody PubCouponRuleQueryParam queryParam) {
		return pubCouponRuleService.getPubCouponRuleHistoryByQuery(queryParam);
	}
	
	/** 
	 * <p>修改时，根据id查找优惠券规则</p>
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "api/PubCouponRule/GetPubCouponRuleById/{id}", method = RequestMethod.GET)
	@ResponseBody
	public PubCouponRule getPubCouponRuleById(@PathVariable String id) {
		logger.log(Level.INFO, "id：" + id);
		return pubCouponRuleService.getPubCouponRuleById(id);
    }
}
