package com.szyciov.lease.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.lease.entity.LeAccountRules;
import com.szyciov.lease.entity.LeCompanyRulesRef;
import com.szyciov.lease.entity.OrgOrgan;
import com.szyciov.lease.entity.User;
import com.szyciov.lease.param.IndividualAccountRulesQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.PageBean;
import com.szyciov.util.TemplateHelper;

@Controller
public class IndividualAccountRulesController extends BaseController {
    private static final Logger logger = Logger.getLogger(IndividualAccountRulesController.class);
	
	private TemplateHelper templateHelper = new TemplateHelper();
	
	@RequestMapping(value = "/IndividualAccountRules/Index")
	@SuppressWarnings("unchecked")
	public ModelAndView getIndividualAccountRulesIndex(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		String leasesCompanyId = user.getLeasescompanyid();
		String specialState = user.getSpecialstate();
		String account = user.getAccount();
		// 机构名称
		List<OrgOrgan> orgOrgan = templateHelper.dealRequestWithToken("/IndividualAccountRules/GetOrganList?leasesCompanyId={leasesCompanyId}&specialState={specialState}&account={account}", HttpMethod.GET,
				userToken, null, List.class, leasesCompanyId, specialState, account);

		mav.addObject("orgOrgan", orgOrgan);
		mav.setViewName("resource/individualAccountRules/index");
		return mav;
	}
	
	@RequestMapping("/IndividualAccountRules/GetIndividualAccountRulesByQuery")
	@ResponseBody
	public PageBean getIndividualAccountRulesByQuery(@RequestBody IndividualAccountRulesQueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		queryParam.setLeasesCompanyId(user.getLeasescompanyid());
		queryParam.setSpecialState(user.getSpecialstate());
		queryParam.setAccount(user.getAccount());
		
		return templateHelper.dealRequestWithToken("/IndividualAccountRules/GetIndividualAccountRulesByQuery", HttpMethod.POST, userToken,
				queryParam,PageBean.class);
	}
	
	@RequestMapping("/IndividualAccountRules/DisableIndividualAccountRules")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String, String> disableIndividualAccountRules(@RequestBody LeCompanyRulesRef leCompanyRulesRef, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		leCompanyRulesRef.setLeasesCompanyId(user.getLeasescompanyid());
		leCompanyRulesRef.setUpdater(user.getId());
		
		return templateHelper.dealRequestWithToken("/IndividualAccountRules/DisableIndividualAccountRules", HttpMethod.POST, userToken, leCompanyRulesRef,
				Map.class);
	}
	
	@RequestMapping(value = "/IndividualAccountRules/CreateRules")
	@SuppressWarnings("unchecked")
	public ModelAndView getIndividualAccountRulesCreateRules(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		String leasesCompanyId = user.getLeasescompanyid();
		String specialState = user.getSpecialstate();
		String account = user.getAccount();
		// 机构名称
		List<OrgOrgan> orgOrgan = templateHelper.dealRequestWithToken("/IndividualAccountRules/GetInsertOrganList?leasesCompanyId={leasesCompanyId}&specialState={specialState}&account={account}", HttpMethod.GET,
				userToken, null, List.class, leasesCompanyId, specialState, account);
		
		mav.addObject("orgOrgan", orgOrgan);
		mav.setViewName("resource/individualAccountRules/createRules");
		return mav;
	}
	
	@RequestMapping("/IndividualAccountRules/GetInsertCityList")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getInsertCityList(@RequestParam(value = "cityName", required = false) String cityName, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		String leasesCompanyId = user.getLeasescompanyid();
		
		return templateHelper.dealRequestWithToken("/IndividualAccountRules/GetInsertCityList?leasesCompanyId={leasesCompanyId}&cityName={cityName}", HttpMethod.GET, userToken, null,
				List.class, leasesCompanyId, cityName);
	}
	
	@RequestMapping(value = "/IndividualAccountRules/EditRules")
	public ModelAndView getIndividualAccountRulesEditRules(@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "shortName", required = true) String shortName) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("rulesRefId", id);
		mav.addObject("shortName", shortName);
		mav.setViewName("resource/individualAccountRules/editRules");
		return mav;
	}
	
	@RequestMapping("/IndividualAccountRules/GetIndividualAccountRulesByOrgan")
	@ResponseBody
	public PageBean getIndividualAccountRulesByOrgan(@RequestParam(value = "id", required = true) String id,
			@RequestBody IndividualAccountRulesQueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		queryParam.setLeasesCompanyId(user.getLeasescompanyid());
		queryParam.setId(id);
		
		return templateHelper.dealRequestWithToken("/IndividualAccountRules/GetIndividualAccountRulesByOrgan",
				HttpMethod.POST, userToken, queryParam, PageBean.class);
	}
	
	@RequestMapping("/IndividualAccountRules/UpdateIndividualAccountRules")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String, String> updateIndividualAccountRules(@RequestBody LeAccountRules leAccountRules, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		leAccountRules.setLeasesCompanyId(user.getLeasescompanyid());
		leAccountRules.setUpdater(user.getId());
		
		return templateHelper.dealRequestWithToken("/IndividualAccountRules/UpdateIndividualAccountRules", HttpMethod.POST, userToken, leAccountRules,
				Map.class);
	}
	
	@RequestMapping("/IndividualAccountRules/DeleteIndividualAccountRules/{id}")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String, String> deleteIndividualAccountRules(@PathVariable String id, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		String leasesCompanyId = user.getLeasescompanyid();

		return templateHelper.dealRequestWithToken("/IndividualAccountRules/DeleteIndividualAccountRules/{id}?leasesCompanyId={leasesCompanyId}", HttpMethod.DELETE, userToken, null,
				Map.class, id, leasesCompanyId);
	}
	
	@RequestMapping(value = "/IndividualAccountRules/RulesDetail")
	public ModelAndView getIndividualAccountRulesRulesDetail(@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "shortName", required = true) String shortName) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("rulesRefId", id);
		mav.addObject("shortName", shortName);
		mav.setViewName("resource/individualAccountRules/rulesDetail");
		return mav;
	}
	
	@RequestMapping("/IndividualAccountRules/GetRulesDateByOrgan/{organId}")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getRulesDateByOrgan(@PathVariable String organId, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		String leasesCompanyId = user.getLeasescompanyid();
		
		return templateHelper.dealRequestWithToken("/IndividualAccountRules/GetRulesDateByOrgan/{organId}?leasesCompanyId={leasesCompanyId}", HttpMethod.GET, userToken, null,
				List.class, organId, leasesCompanyId);
	}
	
	@RequestMapping("/IndividualAccountRules/GetStandardRulesByCity/{city}")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getStandardRulesByCity(@PathVariable String city, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		String leasesCompanyId = user.getLeasescompanyid();
		
		return templateHelper.dealRequestWithToken("/IndividualAccountRules/GetStandardRulesByCity/{city}?leasesCompanyId={leasesCompanyId}", HttpMethod.GET, userToken, null,
				List.class, city, leasesCompanyId);
	}
	
	@RequestMapping("/IndividualAccountRules/CreateIndividualAccountRules")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String, String> createIndividualAccountRules(@RequestBody LeCompanyRulesRef leCompanyRulesRef, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		leCompanyRulesRef.setLeasesCompanyId(user.getLeasescompanyid());
		leCompanyRulesRef.setCreater(user.getId());
		leCompanyRulesRef.setUpdater(user.getId());

		return templateHelper.dealRequestWithToken("/IndividualAccountRules/CreateIndividualAccountRules", HttpMethod.POST, userToken, leCompanyRulesRef,
				Map.class);
	}
	
	@RequestMapping("/IndividualAccountRules/EnableIndividualAccountRules")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String, String> enableIndividualAccountRules(@RequestBody LeCompanyRulesRef leCompanyRulesRef, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		leCompanyRulesRef.setLeasesCompanyId(user.getLeasescompanyid());
		leCompanyRulesRef.setUpdater(user.getId());

		return templateHelper.dealRequestWithToken("/IndividualAccountRules/EnableIndividualAccountRules", HttpMethod.POST, userToken, leCompanyRulesRef,
				Map.class);
	}

}
