package com.szyciov.operate.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.op.entity.OpUser;
import com.szyciov.op.entity.PubSendRules;
import com.szyciov.op.param.PubSendRulesHistoryQueryParam;
import com.szyciov.op.param.OpTaxiSendrulesQueryParam;
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
	@ResponseBody
	public ModelAndView getSendRulesIndex(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		OpUser user = getLoginOpUser(request);
		ModelAndView view = new ModelAndView();
		view.addObject("usertype", user.getUsertype());
		view.setViewName("resource/sendRules/index");
		return view;
	}
	
	@RequestMapping("/SendRules/GetSendRulesByQuery")
	@ResponseBody
	public PageBean getSendRulesByQuery(@RequestBody OpTaxiSendrulesQueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/SendRules/GetSendRulesByQuery", HttpMethod.POST, userToken,
				queryParam,PageBean.class);
	}

	@RequestMapping("/SendRules/GetCityListById")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getCityListById(@RequestParam(value = "cityName", required = false) String cityName, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		return templateHelper.dealRequestWithToken("/SendRules/GetCityListById?cityName={cityName}", HttpMethod.GET, userToken, null,
				List.class, cityName);
	}
	
	@RequestMapping(value = "/SendRules/EditRules")
	public ModelAndView getSendRulesEditRules(HttpServletRequest request)
					throws IOException {
		ModelAndView mav = new ModelAndView();
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		PubSendRules sendRules = null;
		String id = request.getParameter("id");
		if (id != null && !StringUtils.isBlank(id)) {
			sendRules = templateHelper.dealRequestWithToken("/SendRules/GetSendRulesById/{id}", HttpMethod.GET,
					userToken, null, PubSendRules.class, id);
		}

		mav.addObject("sendrules", sendRules);
		mav.setViewName("resource/sendRules/editRules");

		return mav;
	}
	
	@RequestMapping("/SendRules/GetCityById")
	@ResponseBody
	public PubCityAddr getCityById(@RequestParam(value = "city", required = true) String city, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		return templateHelper.dealRequestWithToken("/SendRules/GetCityById/{city}", HttpMethod.GET, userToken, null,
				PubCityAddr.class, city);
	}
	
	@RequestMapping("/SendRules/CreateSendRules")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String, String> createSendRules(@RequestBody PubSendRules sendRules, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OpUser opUser = getLoginOpUser(request);
		if(!"1".equals(opUser.getUsertype())) {
			Map<String, String> ret = new HashMap<String, String>();
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "您没有执行该操作的权限");
			return ret;
		}
		sendRules.setCreater(opUser.getId());
		sendRules.setUpdater(opUser.getId());

		return templateHelper.dealRequestWithToken("/SendRules/CreateSendRules", HttpMethod.POST, userToken, sendRules,
				Map.class);
	}
	
	@RequestMapping("/SendRules/UpdateSendRules")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String, String> updateSendRules(@RequestBody PubSendRules sendRules, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OpUser opUser = getLoginOpUser(request);
		if(!"1".equals(opUser.getUsertype())) {
			Map<String, String> ret = new HashMap<String, String>();
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "您没有执行该操作的权限");
			return ret;
		}
		sendRules.setUpdater(opUser.getId());

		return templateHelper.dealRequestWithToken("/SendRules/UpdateSendRules", HttpMethod.POST, userToken, sendRules,
				Map.class);
	}
	
	/**
	 * 网约车派单规则历史记录页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/SendRules/SendRulesHistoryIndex/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView taxiSendrulesHistoryIndex(@PathVariable String id, HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		String userToken = getUserToken(request);
		PubSendRules sendRules=templateHelper.dealRequestWithToken("/SendRules/GetSendRulesById/{id}", HttpMethod.GET,
				userToken, null, PubSendRules.class, id);
		view.addObject("sendrules", sendRules);
		view.setViewName("resource/sendRules/history");
		return view;
	}
	
	
	/**
	 * 查询网约车派单规则历史记录
	 * @param queryParam
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/SendRules/GetSendrulesHistoryByQuery")
	@ResponseBody
	public PageBean getTaxiSendrulesHistoryByQuery(@RequestBody PubSendRulesHistoryQueryParam queryParam, HttpServletRequest request, HttpServletResponse response) {
		String userToken = getUserToken(request);
		return templateHelper.dealRequestWithToken("/SendRules/GetSendRulesHistoryByQuery", HttpMethod.POST, userToken, queryParam, PageBean.class);
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
		return templateHelper.dealRequestWithToken("/PubInfo/GetPubCityAddrByList", HttpMethod.POST, userToken, null,
				JSONObject.class);
	}
	
}
