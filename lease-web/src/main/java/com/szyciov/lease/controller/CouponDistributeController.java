package com.szyciov.lease.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.szyciov.dto.coupon.PubCouponActivityDto;
import com.szyciov.entity.coupon.PubCouponActivity;
import com.szyciov.lease.entity.User;
import com.szyciov.param.PubCouponActivityQueryParam;
import com.szyciov.param.PubReceivedCouponQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.PageBean;
import com.szyciov.util.TemplateHelper;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "Couponing")
public class CouponDistributeController extends BaseController {

	private TemplateHelper templateHelper = new TemplateHelper();

	@RequestMapping(value = "Index")
	public String index() {
		return "resource/couponDistribute/index";
	}

	/**
	 * 根据条件查询抵扣券列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "QueryCouponActivityByParam")
	@ResponseBody
	public PageBean queryCouponActivityByParam(@RequestBody PubCouponActivityQueryParam queryParam,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		queryParam.setLecompanyid(user.getLeasescompanyid());
		return templateHelper.dealRequestWithToken("/couponing/QueryCouponActivityByParam", HttpMethod.POST, userToken,
				queryParam, PageBean.class);
	}

	/**
	 * 新增优惠卷
	 * 
	 * @return
	 */
	@RequestMapping(value = "add")
	@ResponseBody
	public Map<String, Object> addCouponActivity(@RequestBody PubCouponActivityDto couponActivity,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		couponActivity.setCreater(user.getId());
		couponActivity.setLecompanyid(user.getLeasescompanyid());
		return templateHelper.dealRequestWithToken("/couponing/add", HttpMethod.POST, userToken, couponActivity,
				Map.class);
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
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/couponing/edit/{id}", HttpMethod.GET, userToken, null, Map.class,
				id);
	}

	/**
	 * 修改更新优惠卷
	 * 
	 * @return
	 */
	@RequestMapping(value = "update")
	@ResponseBody
	public Map<String, Object> updateCoupon(@RequestBody PubCouponActivityDto couponActivity,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		couponActivity.setUpdater(user.getId());
		return templateHelper.dealRequestWithToken("/couponing/update", HttpMethod.POST, userToken, couponActivity,
				Map.class);
	}

	/**
	 * 作废优惠卷
	 * 
	 * @return
	 */
	@RequestMapping(value = "invalid/{id}")
	@ResponseBody
	public Map invalidCoupon(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		PubCouponActivity activy = new PubCouponActivity();
		activy.setId(id);
		User user = getLoginLeUser(request);
		activy.setUpdater(user.getId());
		return templateHelper.dealRequestWithToken("/couponing/invalid", HttpMethod.POST, userToken, activy, Map.class);
	}

	/**
	 * 删除优惠卷
	 * 
	 * @return
	 */
	@RequestMapping(value = "del/{id}")
	@ResponseBody
	public Map delCoupon(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/couponing/del/{id}", HttpMethod.GET, userToken, null, Map.class,
				id);
	}

	/**
	 * 获取该租赁公司所有优惠券名称
	 */
	@RequestMapping(value = "getCouponActivityNames")
	@ResponseBody
	public List<Object> getCouponActivityNames(@RequestParam(name = "name", required = false) String name,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		return templateHelper.dealRequestWithToken(
				"/couponing/getCouponActivityNames?lecompanyid={lecompanyid}&name={name}", HttpMethod.GET, userToken,
				null, List.class, user.getLeasescompanyid(), name);
	}

	/**
	 * 获取抵扣券活动表中存在的所有发放规则名称
	 */
	@RequestMapping(value = "getAlreadyCouponRuleNames")
	@ResponseBody
	public List<Object> getAlreadyCouponRuleNames(@RequestParam(name = "rulename", required = false) String rulename,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		return templateHelper.dealRequestWithToken(
				"/couponing/getAlreadyCouponRuleNames?lecompanyid={lecompanyid}&rulename={rulename}", HttpMethod.GET,
				userToken, null, List.class, user.getLeasescompanyid(), rulename);
	}

	/**
	 * 获取抵扣券规则中的所有规则名称
	 */
	@RequestMapping(value = "getCouponRuleNames")
	@ResponseBody
	public Map<String, Object> getCouponRuleNames(@RequestParam String sendruletarget, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		return templateHelper.dealRequestWithToken(
				"/couponing/getCouponRuleNames?lecompanyid={lecompanyid}&sendruletarget={sendruletarget}",
				HttpMethod.POST, userToken, null, Map.class, user.getLeasescompanyid(), sendruletarget);
	}

	/**
	 * 获取租赁公司业务城市
	 */
	@RequestMapping(value = "getBusinessCitys/{sendservicetype}")
	@ResponseBody
	public JSONObject getBusinessCitys(@PathVariable("sendservicetype") String sendservicetype,HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		return templateHelper.dealRequestWithToken("/couponing/getBusinessCitys?lecompanyid={lecompanyid}&sendservicetype={sendservicetype}",
				HttpMethod.GET, userToken, null, JSONObject.class, user.getLeasescompanyid(),sendservicetype);
	}

	/**
	 * 机构客户优惠卷发放记录
	 * 
	 * @return
	 */
	@RequestMapping(value = "organRecord")
	public String organRecordCoupon(@RequestParam String id, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		ModelAndView mav = new ModelAndView();
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		Map<String, Object> map = templateHelper.dealRequestWithToken("/couponing/organRecord?id={id}", HttpMethod.GET,
				userToken, null, Map.class, id);
		model.addAttribute("data", map);
		return "resource/couponDistribute/organRecord";
	}

	/**
	 * 机构用户优惠卷发放记录
	 * 
	 * @return
	 */
	@RequestMapping(value = "organUserRecord")
	public String organUserRecordCoupon(@RequestParam String id, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		ModelAndView mav = new ModelAndView();
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		Map<String, Object> map = templateHelper.dealRequestWithToken("/couponing/organUserRecord?id={id}",
				HttpMethod.GET, userToken, null, Map.class, id);
		model.addAttribute("data", map);
		return "resource/couponDistribute/organUserRecord";
	}

	/**
	 * 获取领取过优惠券的机构
	 */
	@RequestMapping(value = "GetOrganReceivedCoupon")
	@ResponseBody
	public PageBean GetOrganReceivedCoupon(@RequestBody PubReceivedCouponQueryParam queryParam,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/couponing/GetOrganReceivedCoupon", HttpMethod.POST, userToken,
				queryParam, PageBean.class);
	}

	/**
	 * 获取领取过优惠券的机构用户
	 */
	@RequestMapping(value = "GetOrganUserReceivedCoupon")
	@ResponseBody
	public PageBean GetOrganUserReceivedCoupon(@RequestBody PubReceivedCouponQueryParam queryParam,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/couponing/GetOrganUserReceivedCoupon", HttpMethod.POST, userToken,
				queryParam, PageBean.class);
	}
}
