package com.szyciov.lease.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.entity.User;
import com.szyciov.lease.param.LeSendRulesQueryParam;
import com.szyciov.op.entity.PubSendRules;
import com.szyciov.op.param.PubSendRulesHistoryQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.PageBean;
import com.szyciov.util.TemplateHelper;

import net.sf.json.JSONObject;

@Controller
public class SendRulesController extends BaseController {
	private static final Logger logger = Logger.getLogger(SendRulesController.class);
	
	private TemplateHelper templateHelper = new TemplateHelper();
	
	@RequestMapping(value = "/SendRules/Index")
	public ModelAndView getSendRulesIndex(HttpServletResponse response,HttpServletRequest request) {
		response.setContentType("text/html; charset=utf-8");
		User user = getLoginLeUser(request);
		ModelAndView view = new ModelAndView();
		view.addObject("usertype", user.getSpecialstate());
		view.setViewName("resource/sendRules/index");
		return view;
	}

	@RequestMapping("/SendRules/GetSendRulesByQuery")
	@ResponseBody
	public PageBean getSendRulesByQuery(@RequestBody LeSendRulesQueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		queryParam.setLeasesCompanyId(user.getLeasescompanyid());
		return templateHelper.dealRequestWithToken("/SendRules/GetSendRulesByQuery", HttpMethod.POST, userToken,
				queryParam,PageBean.class);
	}
	
	@RequestMapping("/SendRules/CreateSendRules")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String, String> createSendRules(@RequestBody PubSendRules pubSendRules, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		if(!"1".equals(user.getSpecialstate())) {
			Map<String, String> ret = new HashMap<String, String>();
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "您没有执行该操作的权限");
			return ret;
		}
		pubSendRules.setCreater(user.getId());
		pubSendRules.setUpdater(user.getId());
		pubSendRules.setLeasesCompanyId(user.getLeasescompanyid());

		return templateHelper.dealRequestWithToken("/SendRules/CreateSendRules", HttpMethod.POST, userToken, pubSendRules,
				Map.class);
	}
	
	@RequestMapping("/SendRules/UpdateSendRules")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String, String> updateSendRules(@RequestBody PubSendRules pubSendRules, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		if(!"1".equals(user.getSpecialstate())) {
			Map<String, String> ret = new HashMap<String, String>();
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "您没有执行该操作的权限");
			return ret;
		}
		pubSendRules.setUpdater(user.getId());
		pubSendRules.setLeasesCompanyId(user.getLeasescompanyid());

		return templateHelper.dealRequestWithToken("/SendRules/UpdateSendRules", HttpMethod.POST, userToken, pubSendRules,
				Map.class);
	}
	
	@RequestMapping(value = "/SendRules/EditRules")
	public ModelAndView getSendRulesEditRules(@RequestParam(value = "id", required = false) String id,
			HttpServletRequest request) throws IOException {
		ModelAndView mav = new ModelAndView();
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		String leasesCompanyId = user.getLeasescompanyid();
		PubSendRules pubSendRules = null;
		if (id != null && !StringUtils.isBlank(id)) {
            pubSendRules = templateHelper.dealRequestWithToken("/SendRules/GetSendRulesListById/{id}?leasesCompanyId={leasesCompanyId}", HttpMethod.GET,
					userToken, null, PubSendRules.class, id, leasesCompanyId);
		}

		mav.setViewName("resource/sendRules/editRules");		
		mav.addObject("sendrules", pubSendRules);

		return mav;
	}
	
	/**
	 * 派单规则历史记录界面
	 * @param id
	 * @param request
	 * @param model
	 * @return
	 */
    @RequestMapping("/SendRules/SendRulesHistoryIndex/{id}")
    public String SendRulesHistotyIndex(@PathVariable String id,HttpServletRequest request,Model model){
    	
    	String userToken=getUserToken();
    	User user = getLoginLeUser(request);
    	PubSendRules sendRules=templateHelper.dealRequestWithToken("/SendRules/GetSendRulesListById/{id}?leasesCompanyId={leasesCompanyId}", HttpMethod.GET, userToken, null, PubSendRules.class, id,user.getLeasescompanyid());
    	model.addAttribute("sendrules", sendRules);
    	return "resource/sendRules/history";
    }
    
    /**
     * 获取对应派单规则的历史记录
     * @param request
     * @return
     */
    @RequestMapping("/SendRules/GetSendRulesHistoryByQuery")
    @ResponseBody
    public PageBean GetSendrulesHistoryByQuery(@RequestBody PubSendRulesHistoryQueryParam queryParam){
    	String userToken=getUserToken();
    	return templateHelper.dealRequestWithToken("/SendRules/GetSendRulesHistory", HttpMethod.POST, userToken, queryParam, PageBean.class);
    }
	
	@RequestMapping("/SendRules/GetCityById")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public PubCityAddr updateSendRules(@RequestParam(value = "city", required = true) String city, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		return templateHelper.dealRequestWithToken("/SendRules/GetCityById/{city}", HttpMethod.GET, userToken, null,
				PubCityAddr.class, city);
	}
	
	@RequestMapping("/SendRules/GetCityListById")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getCityListById(@RequestParam(value = "cityName", required = false) String cityName, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		String leasesCompanyId = user.getLeasescompanyid();
		
		return templateHelper.dealRequestWithToken("/SendRules/GetCityListById?leasesCompanyId={leasesCompanyId}&cityName={cityName}", HttpMethod.GET, userToken, null,
				List.class, leasesCompanyId, cityName);
	}
	
	/**
	 * 查询所有城市按照字母分类
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/SendRules/GetPubCityAddrList")
	@ResponseBody
	public JSONObject getPubCityAddrList(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/StandardAccountRules/GetPubCityAddrList", HttpMethod.POST,
				userToken, null, JSONObject.class);
	}
	
}
