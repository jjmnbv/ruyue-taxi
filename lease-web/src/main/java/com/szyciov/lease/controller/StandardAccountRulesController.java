package com.szyciov.lease.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.lease.entity.LeAccountRules;
import com.szyciov.lease.entity.LeVehiclemodels;
import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.lease.entity.User;
import com.szyciov.lease.param.LeAccountRulesModiLogQueryParam;
import com.szyciov.lease.param.LeAccountRulesQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.PageBean;
import com.szyciov.util.TemplateHelper;

import net.sf.json.JSONObject;

@Controller
public class StandardAccountRulesController extends BaseController {
	private static final Logger logger = Logger.getLogger(StandardAccountRulesController.class);
	
	private TemplateHelper templateHelper = new TemplateHelper();

	@RequestMapping(value = "/StandardAccountRules/Index")
	@SuppressWarnings("unchecked")
	public ModelAndView getStandardAccountRulesIndex(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		String leasesCompanyId = user.getLeasescompanyid();
		//规则类型
		String type = "订单类型";
		List<PubDictionary> rulesTypeDictionary = templateHelper.dealRequestWithToken("/StandardAccountRules/GetPubDictionaryByType/{type}", HttpMethod.GET,
				userToken, null, List.class, type);
		
		//城市
		List<Map<String, Object>> pubCityAddr = templateHelper.dealRequestWithToken(
				"/StandardAccountRules/GetExistCityList?leasesCompanyId={leasesCompanyId}", HttpMethod.GET, userToken,
				null, List.class, leasesCompanyId);
		
		//服务车型
		List<LeVehiclemodels> leVehiclemodels = templateHelper.dealRequestWithToken("/StandardAccountRules/GetExistCarTypeList?leasesCompanyId={leasesCompanyId}", HttpMethod.GET,
				userToken, null, List.class, leasesCompanyId);
		
		mav.addObject("rulesTypeDictionary", rulesTypeDictionary);
		mav.addObject("pubCityAddr", pubCityAddr);
		mav.addObject("leVehiclemodels", leVehiclemodels);
		mav.setViewName("resource/standardAccountRules/index");
		return mav;
	}

	@RequestMapping("/StandardAccountRules/GetStandardAccountRulesByQuery")
	@ResponseBody
	public PageBean getStandardAccountRulesByQuery(@RequestBody LeAccountRulesQueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		queryParam.setLeasesCompanyId(user.getLeasescompanyid());
		
		return templateHelper.dealRequestWithToken("/StandardAccountRules/GetStandardAccountRulesByQuery", HttpMethod.POST, userToken,
				queryParam,PageBean.class);
	}
	
	@RequestMapping("/StandardAccountRules/CreateStandardAccountRules")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String, String> createStandardAccountRules(@RequestBody LeAccountRules leAccountRules, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		leAccountRules.setLeasesCompanyId(user.getLeasescompanyid());
		leAccountRules.setCreater(user.getId());
		leAccountRules.setUpdater(user.getId());
		
		return templateHelper.dealRequestWithToken("/StandardAccountRules/CreateStandardAccountRules", HttpMethod.POST, userToken, leAccountRules,
				Map.class);
	}
	
	@RequestMapping("/StandardAccountRules/UpdateStandardAccountRules")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String, String> updateStandardAccountRules(@RequestBody LeAccountRules leAccountRules, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		leAccountRules.setLeasesCompanyId(user.getLeasescompanyid());
		leAccountRules.setUpdater(user.getId());
		
		return templateHelper.dealRequestWithToken("/StandardAccountRules/UpdateStandardAccountRules", HttpMethod.POST, userToken, leAccountRules,
				Map.class);
	}
	
	@RequestMapping("/StandardAccountRules/UpdateStandardAccountRulesState")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String, String> updateStandardAccountRulesState(@RequestParam(value = "modiType", required = true) String modiType, @RequestBody LeAccountRules leAccountRules, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		logger.log(Level.INFO, "web updateStandardAccountRulesState modiType:" + modiType);
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		leAccountRules.setLeasesCompanyId(user.getLeasescompanyid());
		leAccountRules.setUpdater(user.getId());

		return templateHelper.dealRequestWithToken("/StandardAccountRules/UpdateStandardAccountRulesState?modiType={modiType}", HttpMethod.POST, userToken, leAccountRules,
				Map.class, modiType);
	}
	
	@RequestMapping("/StandardAccountRules/UpdateStandardAccountRulesOneKey")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String, String> updateStandardAccountRulesOneKey(@RequestBody LeAccountRules leAccountRules, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		leAccountRules.setLeasesCompanyId(user.getLeasescompanyid());
		leAccountRules.setUpdater(user.getId());
		
		return templateHelper.dealRequestWithToken("/StandardAccountRules/UpdateStandardAccountRulesOneKey", HttpMethod.POST, userToken, leAccountRules,
				Map.class);
	}
	
	@RequestMapping("/StandardAccountRules/GetStandardAccountRulesModiLogByQuery")
	@ResponseBody
	public PageBean getStandardAccountRulesModiLogByQuery(@RequestBody LeAccountRulesModiLogQueryParam queryParam, @RequestParam(value = "accountRulesId", required = true) String accountRulesId, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		queryParam.setAccountRulesId(accountRulesId);
		
		return templateHelper.dealRequestWithToken("/StandardAccountRules/GetStandardAccountRulesModiLogByQuery", HttpMethod.POST, userToken,
				queryParam,PageBean.class);
	}
	
	@RequestMapping(value = "/StandardAccountRules/EditRules")
	@SuppressWarnings("unchecked")
	public ModelAndView getSendRulesEditRules(@RequestParam(value = "id", required = false) String id,
			HttpServletRequest request)
					throws IOException {
		ModelAndView mav = new ModelAndView();
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		String leasesCompanyId = user.getLeasescompanyid();
		
		//规则类型
		String type = "订单类型";
		List<PubDictionary> rulesTypeDictionary = templateHelper.dealRequestWithToken(
				"/StandardAccountRules/GetPubDictionaryByType/{type}", HttpMethod.GET, userToken, null, List.class,
				type);
		
		// 服务车型
		List<LeVehiclemodels> leVehiclemodels = templateHelper.dealRequestWithToken(
				"/StandardAccountRules/GetCarTypeList?leasesCompanyId={leasesCompanyId}", HttpMethod.GET,
				userToken, null, List.class, leasesCompanyId);
		
		LeAccountRules leAccountRules = null;
		if (id != null && !StringUtils.isBlank(id)) {
			leAccountRules = templateHelper.dealRequestWithToken("/StandardAccountRules/GetStandardAccountRulesById/{id}", HttpMethod.GET,
					userToken, null, LeAccountRules.class, id);
		}
		
		mav.setViewName("resource/standardAccountRules/editRules");
		
		mav.addObject("rulesTypeDictionary", rulesTypeDictionary);
		mav.addObject("leVehiclemodels", leVehiclemodels);
		mav.addObject("leAccountRules", leAccountRules);
		return mav;
	}
	
	@RequestMapping(value = "/StandardAccountRules/SearchHistory")
	public ModelAndView getSendRulesEditRulesHistory(@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "cityName", required = true) String cityName,
			@RequestParam(value = "rulesTypeName", required = true) String rulesTypeName,
			@RequestParam(value = "carTypeName", required = true) String carTypeName,
			HttpServletRequest request)
					throws IOException {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("resource/standardAccountRules/history");
		
		mav.addObject("accountRulesId", id);
		mav.addObject("cityName", cityName);
		mav.addObject("rulesTypeName", rulesTypeName);
		mav.addObject("carTypeName", carTypeName);
		return mav;
	}
	
	@RequestMapping("/StandardAccountRules/GetStandardAccountRulesByCity")
	@ResponseBody
	public LeAccountRules getStandardAccountRulesByCity(@RequestParam(value = "city", required = true) String city, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		logger.log(Level.INFO, "web getStandardAccountRulesByCity city:" + city);
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		String leasesCompanyId = user.getLeasescompanyid();
		
		LeAccountRules leAccountRules = templateHelper.dealRequestWithToken("/StandardAccountRules/GetOneRuleByCity?leasesCompanyId={leasesCompanyId}&city={city}", HttpMethod.GET, userToken, null,
				LeAccountRules.class, leasesCompanyId, city);
		
		return leAccountRules;
	}
	
	@RequestMapping("/StandardAccountRules/GetExistCityList")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getExistCityList(@RequestParam(value = "cityName", required = false) String cityName, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		String leasesCompanyId = user.getLeasescompanyid();
		
		return templateHelper.dealRequestWithToken("/StandardAccountRules/GetExistCityList?leasesCompanyId={leasesCompanyId}&cityName={cityName}", HttpMethod.GET, userToken, null,
				List.class, leasesCompanyId, cityName);
	}
	
	/**
	 * 查询所有城市按照字母分类
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/StandardAccountRules/GetPubCityAddrList")
	@ResponseBody
	public JSONObject getPubCityAddrList(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/StandardAccountRules/GetPubCityAddrList", HttpMethod.POST,
				userToken, null, JSONObject.class);
	}

}
