package com.szyciov.lease.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.dto.coupon.PubCouponActivityDto;
import com.szyciov.entity.coupon.PubCouponActivity;
import com.szyciov.lease.service.CouponDistritubeService;
import com.szyciov.param.PubCouponActivityQueryParam;
import com.szyciov.param.PubReceivedCouponQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "api/couponing")
public class CouponDistributeController extends BaseController {

	@Resource(name = "couponDistributeService")
	private CouponDistritubeService service;

	public CouponDistritubeService getService() {
		return service;
	}

	public void setService(CouponDistritubeService service) {
		this.service = service;
	}

	/**
	 * 新增优惠券
	 * 
	 * @param activity
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "add")
	public Map<String, Object> add(@RequestBody PubCouponActivityDto activity) {
		return service.addCouponActivity(activity);
	}

	/**
	 * 查询优惠券列表
	 */
	@ResponseBody
	@RequestMapping(value = "QueryCouponActivityByParam")
	public PageBean getPubCouponActivityByParam(@RequestBody PubCouponActivityQueryParam queryParam) {
		return service.getCouponActivityByParam(queryParam);
	}

	/**
	 * 作废优惠卷
	 * 
	 * @return
	 */
	@RequestMapping(value = "invalid")
	@ResponseBody
	public Map invalidCoupon(@RequestBody PubCouponActivity activy) {
		return service.invalidCouponActivity(activy);
	}

	/**
	 * 弹出修改窗口，获取抵扣券信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "edit/{id}")
	@ResponseBody
	public Map<String, Object> editCoupon(@PathVariable String id, HttpServletRequest request,
			HttpServletResponse response) {
		return service.editCoupon(id);
	}

	/**
	 * 修改更新优惠卷
	 * 
	 * @return
	 */
	@RequestMapping(value = "update")
	@ResponseBody
	public Map<String, Object> updateCoupon(@RequestBody PubCouponActivityDto couponActivity) {
		return service.updateCouponActivity(couponActivity);
	}

	/**
	 * 删除优惠卷
	 * 
	 * @return
	 */
	@RequestMapping(value = "del/{id}")
	@ResponseBody
	public Map delCoupon(@PathVariable String id) {
		return service.delCouponActivity(id);
	}

	/**
	 * 获取所有优惠券名称
	 */
	@RequestMapping(value = "getCouponActivityNames")
	@ResponseBody
	public List<Object> getCouponActivityNames(@RequestParam String lecompanyid,
			@RequestParam(required = false) String name) {
		return service.getCouponActivityNames(lecompanyid, name);
	}

	/**
	 * 获取抵扣券活动表中存在的所有发放规则名称
	 */
	@RequestMapping(value = "getAlreadyCouponRuleNames")
	@ResponseBody
	public List<Object> getAlreadyCouponRuleNames(@RequestParam String lecompanyid,
			@RequestParam(required = false) String rulename) {
		return service.getAlreadyCouponRuleNames(lecompanyid, rulename);
	}

	/**
	 * 获取抵扣券规则中的所有发放规则名称
	 */
	@RequestMapping(value = "getCouponRuleNames")
	@ResponseBody
	public Map<String, Object> getCouponRuleNames(@RequestParam String lecompanyid,
			@RequestParam String sendruletarget) {
		return service.getCouponRuleNames(lecompanyid, sendruletarget);
	}

	/**
	 * 获取运管端业务城市
	 */
	@RequestMapping(value = "getBusinessCitys")
	@ResponseBody
	public JSONObject getBusinessCitys(@RequestParam String lecompanyid,@RequestParam String sendservicetype) {
		return service.getBusinessCitys(lecompanyid,sendservicetype);
	}

	/**
	 * 机构客户优惠卷发放记录
	 * 
	 * @return
	 */
	@RequestMapping(value = "organRecord")
	@ResponseBody
	public Map<String, Object> organRecordCoupon(@RequestParam String id, HttpServletRequest request,
			HttpServletResponse response) {
		return service.organRecordCoupon(id);
	}

	/**
	 * 机构优惠券优惠卷发放记录
	 * 
	 * @return
	 */
	@RequestMapping(value = "organUserRecord")
	@ResponseBody
	public Map<String, Object> organUserRecord(@RequestParam String id, HttpServletRequest request,
			HttpServletResponse response) {
		return service.organUserRecordCoupon(id);
	}

	/**
	 * 查询机构客户领取过优惠券的记录
	 */
	@RequestMapping(value = "GetOrganReceivedCoupon")
	@ResponseBody
	public PageBean GetOrganReceivedCoupon(@RequestBody PubReceivedCouponQueryParam queryParam,
			HttpServletRequest request, HttpServletResponse response) {
		return service.GetOrganReceivedCoupon(queryParam);
	}

	/**
	 * 查询机构用户领取过优惠券的记录
	 */
	@RequestMapping(value = "GetOrganUserReceivedCoupon")
	@ResponseBody
	public PageBean GetOrganUserReceivedCoupon(@RequestBody PubReceivedCouponQueryParam queryParam,
			HttpServletRequest request, HttpServletResponse response) {
		return service.GetOrganUserReceivedCoupon(queryParam);
	}
}
