package com.szyciov.lease.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.dto.pubPremiumRule.PubPremiumAdd;
import com.szyciov.dto.pubPremiumRule.PubPremiumDetail;
import com.szyciov.dto.pubPremiumRule.PubPremiumHistory;
import com.szyciov.dto.pubPremiumRule.PubPremiumModify;
import com.szyciov.dto.pubPremiumRule.PubPremiumParam;
import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.lease.entity.User;
import com.szyciov.op.entity.OpUser;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.PageBean;
import com.szyciov.util.TemplateHelper;
@Controller
public class PubPremiumRuleController extends BaseController {
	private TemplateHelper templateHelper = new TemplateHelper();
	ModelAndView mav = new ModelAndView();

	@RequestMapping(value = "/PubPremiumRule/Index")
	@SuppressWarnings("unchecked")
	public ModelAndView getPubPremiumRuleIndex(HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		List<PubDictionary> list = templateHelper.dealRequestWithToken(
				"/PubPremiumRule/getCity", HttpMethod.GET, userToken, null,
				List.class);
		mav.addObject("list", list);
		mav.setViewName("resource/pubPremiumRule/index");
		return mav;
	}
	@RequestMapping(value = "PubPremiumRule/GetPubPremiumRule")
	@ResponseBody
    public PageBean getPubPremiumRule(
    		@RequestParam (value = "cityName", required = false) String alarmsource,
	        @RequestParam(value = "cartype", required = false) String cartype,
		    @RequestParam(value = "ruletype", required = false) String ruletype,
		    @RequestParam(value = "rulestatus", required = false) String rulestatus,
		    @RequestParam(value = "startdt", required = false) String startdt,
		    @RequestParam(value = "enddt", required = false) String enddt,
		    @RequestParam(value = "rulename", required = false) String rulename,
		    @RequestBody PubPremiumParam pubPremiumParam,
		    HttpServletRequest request, HttpServletResponse response) throws IOException {
    	response.setContentType("application/json;charset=utf-8");
    	User user = getLoginLeUser(request);
    	pubPremiumParam.setLeasescompanyid(user.getLeasescompanyid());
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
    	return templateHelper.dealRequestWithToken("/PubPremiumRule/PubPremiumParam",
				HttpMethod.POST, userToken,pubPremiumParam,PageBean.class);
    }
	/**
	 * 判断规则能否被启用
	 */
	@RequestMapping(value = "/PubPremiumRule/ruleConflict")
	@ResponseBody
	public int ruleConflict( @RequestBody PubPremiumParam pubPremiumParam, HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OpUser user = getLoginOpUser(request);
		pubPremiumParam.setCreater(user.getId());
		return templateHelper.dealRequestWithToken("/PubPremiumRule/ruleConflict", HttpMethod.POST, userToken,
				pubPremiumParam, int.class);
	}
	/**
	 * 禁用规则
	 */
	@RequestMapping(value = "/PubPremiumRule/ruleConflictOk")
	@ResponseBody
	public int ruleConflictOk( @RequestBody PubPremiumParam pubPremiumParam, HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OpUser user = getLoginOpUser(request);
		pubPremiumParam.setCreater(user.getId());
		return templateHelper.dealRequestWithToken("/PubPremiumRule/ruleConflictOk", HttpMethod.POST, userToken,
				pubPremiumParam, int.class);
	}
	//新增，修改
	@RequestMapping("PubPremiumRule/AddIndex")
	@ResponseBody
	public ModelAndView addIndex(HttpServletRequest request,HttpServletResponse response) throws IOException {
		ModelAndView mav = new ModelAndView();
		response.setContentType("text/html;charset=utf-8");
		if(request.getParameter("id") != null && !request.getParameter("id").equals("")){
			PubPremiumParam pubPremiumParam = new PubPremiumParam();
			pubPremiumParam.setId(request.getParameter("id"));
			pubPremiumParam.setRuletype(request.getParameter("ruletype"));
			mav.addObject("pubPremiumParam", pubPremiumParam);
			mav.addObject("addOrModify", "修改溢价规则");
			mav.setViewName("resource/pubPremiumRule/add");
		}else{
			mav.addObject("addOrModify", "新增溢价规则");
			mav.setViewName("resource/pubPremiumRule/add");
		}
		return mav;
	}
	/**
	 * 修改
	 */
	@RequestMapping(value = "/PubPremiumRule/modifyDate")
	@ResponseBody
	public PubPremiumModify getModify( @RequestBody PubPremiumParam pubPremiumParam, HttpServletRequest request) {
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/pubPremiumRule/Modify", HttpMethod.POST, userToken,
				 pubPremiumParam,PubPremiumModify.class);
	}
	//新增，修改
		@RequestMapping("/PubPremiumRule/PubPremiumAdd")
		@ResponseBody
		public Map<String, String> pubPremiumAdd(@RequestBody PubPremiumAdd pubPremiumAdd,
				HttpServletRequest request,HttpServletResponse response) throws IOException {
			response.setContentType("text/html;charset=utf-8");
			String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
			OpUser user = getLoginOpUser(request);
			pubPremiumAdd.setCreater(user.getId());
			return templateHelper.dealRequestWithToken("/PubPremiumRule/PubPremiumAdd", HttpMethod.POST, userToken,
					pubPremiumAdd,Map.class);
		}
	//页面详情转跳
		@RequestMapping("PubPremiumRule/DetailIndex")
		@ResponseBody
		public ModelAndView detailIndex(HttpServletRequest request,HttpServletResponse response) throws IOException {
			ModelAndView mav = new ModelAndView();
			    mav.addObject("aaid",request.getParameter("id"));
				mav.setViewName("resource/pubPremiumRule/detail");
			return mav;
		}
	//页面详情数据初始化
		@RequestMapping(value = "PubPremiumRule/GetDetailData")
		@ResponseBody
		public PageBean getDetailData(
				@RequestParam (value = "aaid", required = false) String aaid,
				@RequestBody PubPremiumDetail pubPremiumDetail,
				HttpServletRequest request,HttpServletResponse response) throws IOException {
			response.setContentType("application/json;charset=utf-8");
			String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
			pubPremiumDetail.setId(aaid);
			return templateHelper.dealRequestWithToken("/pubPremiumRule/DetailData", HttpMethod.POST, userToken,
					pubPremiumDetail,PageBean.class);
		}
		//页面详情转跳
				@RequestMapping("PubPremiumRule/DetailIndexDate")
				@ResponseBody
		public ModelAndView detailIndexDate(HttpServletRequest request,HttpServletResponse response) throws IOException {
					ModelAndView mav = new ModelAndView();
					    mav.addObject("aaid",request.getParameter("id"));
						mav.setViewName("resource/pubPremiumRule/detailDate");
					return mav;
				}
				//页面详情数据初始化
				@RequestMapping(value = "PubPremiumRule/GetDetailDateData")
				@ResponseBody
		public PageBean getDetailDateData(
						@RequestParam (value = "aaid", required = false) String aaid,
						@RequestBody PubPremiumDetail pubPremiumDetail,
						HttpServletRequest request,HttpServletResponse response) throws IOException {
					response.setContentType("application/json;charset=utf-8");
					String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
					pubPremiumDetail.setId(aaid);
					return templateHelper.dealRequestWithToken("/pubPremiumRule/DetailDateData", HttpMethod.POST, userToken,
							pubPremiumDetail,PageBean.class);
				}
		//转跳历史记录
		//页面详情转跳
		@RequestMapping("PubPremiumRule/HistoryIndex")
		@ResponseBody
		public ModelAndView historyIndex(HttpServletRequest request,HttpServletResponse response) throws IOException {
			    ModelAndView mav = new ModelAndView();
			    mav.addObject("aaid",request.getParameter("id"));
				mav.setViewName("resource/pubPremiumRule/history");
			return mav;
		}
		@RequestMapping(value = "PubPremiumRule/GetHistoryData")
		@ResponseBody
		public PageBean getHistoryData(
						@RequestParam (value = "aaid", required = false) String aaid,
						@RequestBody PubPremiumHistory pubPremiumHistory,
						HttpServletRequest request,HttpServletResponse response) throws IOException {
					response.setContentType("application/json;charset=utf-8");
					String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
					pubPremiumHistory.setId(aaid);
					return templateHelper.dealRequestWithToken("/pubPremiumRule/GetHistoryData", HttpMethod.POST, userToken,
							pubPremiumHistory,PageBean.class);
				}
		//页面详情转跳历史记录详情
				@RequestMapping("PubPremiumRule/Historydetail")
				@ResponseBody
				public ModelAndView historydetailIndex(HttpServletRequest request,HttpServletResponse response) throws IOException {
					ModelAndView mav = new ModelAndView();
					    mav.addObject("aaid",request.getParameter("id"));
					    mav.addObject("aaruletype",request.getParameter("ruletype"));
						mav.setViewName("resource/pubPremiumRule/historydetail");
					return mav;
				}
				@RequestMapping(value = "PubPremiumRule/GetHistorydetail")
				@ResponseBody
				public PageBean getHistorydetail(
								@RequestParam (value = "aaid", required = false) String aaid,
								@RequestParam (value = "aaruletype", required = false) String aaruletype,
								@RequestBody PubPremiumHistory pubPremiumHistory,
								HttpServletRequest request,HttpServletResponse response) throws IOException {
							response.setContentType("application/json;charset=utf-8");
							String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
							pubPremiumHistory.setId(aaid);
							pubPremiumHistory.setRuletype(aaruletype);
							return templateHelper.dealRequestWithToken("/pubPremiumRule/GetHistorydetail", HttpMethod.POST, userToken,
									pubPremiumHistory,PageBean.class);
						}
}
