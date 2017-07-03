package com.szyciov.organ.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.lease.entity.LeAccountRules;
import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.lease.entity.OrgOrganCompanyRef;
import com.szyciov.org.entity.OrgUsecarrules;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.TemplateHelper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;




@Controller
public class OrgUsecarrulesController extends BaseController {
	private static final Logger logger = Logger.getLogger(OrgUsecarrulesController.class);
	private TemplateHelper templateHelper = new TemplateHelper();
	@RequestMapping("OrgUsecarrules/Index")
	@ResponseBody
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView();
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OrgUsecarrules orgUsecarrules = new OrgUsecarrules();
		if(request.getParameter("queryCompany") != null && !request.getParameter("queryCompany").equals("")){
			orgUsecarrules.setName(request.getParameter("queryCompany"));
		}
		orgUsecarrules.setOrganId(getLoginOrgUser(request).getOrganId());
		Map map = templateHelper.dealRequestWithToken("/OrgUsecarrules/GetOrgUsecarrules", HttpMethod.POST, userToken, orgUsecarrules,
				Map.class);
		if(map.size() <= 0 && request.getParameter("search") != null && !request.getParameter("search").equals("") ){
			mav.addObject("search", "没有查询到相关规则信息");
		}else{
			mav.addObject("map", map);
		}
		mav.addObject("size",map.size());
		mav.addObject("queryCompany",request.getParameter("queryCompany"));
		mav.setViewName("resource/orgUsecarrules/index");
		return mav;
	}
	
	@RequestMapping("OrgUsecarrules/AddIndex")
	@ResponseBody
	public ModelAndView addIndex(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView();
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		String organid = getLoginOrgUser(request).getOrganId();
		if(request.getParameter("ruleName") != null){
			OrgUsecarrules orgUsecarrules = new OrgUsecarrules();
			orgUsecarrules.setName(request.getParameter("ruleName"));
			orgUsecarrules.setOrganId(organid);
			Map map = templateHelper.dealRequestWithToken("/OrgUsecarrules/GetOrgUsecarrules", HttpMethod.POST, userToken, orgUsecarrules,
					Map.class);	
			mav.addObject("map", map);
			mav.addObject("ruleName",request.getParameter("ruleName"));
			List<LeAccountRules> list = templateHelper.dealRequestWithToken("/OrgUsecarrules/GetRulestype/{organid}", HttpMethod.GET, userToken, null,
					List.class,organid);
			mav.addObject("list", list);
			mav.setViewName("resource/orgUsecarrules/edit");
		}else{
			List<LeAccountRules> list = templateHelper.dealRequestWithToken("/OrgUsecarrules/GetRulestype/{organid}", HttpMethod.GET, userToken, null,
					List.class,organid);
			mav.addObject("list", list);
			mav.setViewName("resource/orgUsecarrules/add");
		}
		return mav;
	}
	
	@RequestMapping("OrgUsecarrules/GetRulesCount")
	@ResponseBody
	public List<LeAccountRules> GetRulesCount(HttpServletRequest request,HttpServletResponse response){
		String organid = getLoginOrgUser(request).getOrganId();
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return templateHelper.dealRequestWithToken("/OrgUsecarrules/GetRulestype/{organid}", HttpMethod.GET, userToken, null,
				List.class,organid);
	}
	
	@RequestMapping("OrgUsecarrules/Add")
	@ResponseBody
	public Map<String, String> add(@RequestBody Map<String, Object> params,HttpServletRequest request,HttpServletResponse response){
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
//		JSONObject json = JSONObject.fromObject(params);
//		Iterator<String> ite = json.keys();
//		boolean flag = false;
//		while(ite.hasNext()) {
//			String key = (String)ite.next();
//			JSONArray arr = json.getJSONArray(key);
//			String[] s = new String[arr.size()];
//			for(int i = 0; i < arr.size(); i++) {
//				JSONObject info = arr.getJSONObject(i);
//				System.out.println(info.get("leasesCompanyId"));
//				s[i] = (String) info.get("leasesCompanyId");
//			}
//			for(int i=0;i<s.length;i++){
//				for(int j=i+1;j<s.length;j++){
//					if(s[i].equals(s[j])){
//						flag=true;
//					}
//				}
//			}
//		}
//		if(flag){
//			Map<String, String> map = new HashMap<String, String>();
//			map.put("ResultSign", "Error");
//			map.put("MessageKey", "存在重复的租赁公司");
//			return map;
//		}else{
			return templateHelper.dealRequestWithToken("/OrgUsecarrules/Add/{ruleName}/{userId}/{add}", HttpMethod.POST, userToken,params,
				Map.class,params.get("ruleName"),getLoginOrgUser(request).getOrganId(),"add");
//		}
	}
	
	@RequestMapping("OrgUsecarrules/Update")
	@ResponseBody
	public Map<String, String> update(@RequestBody Map<String, Object> params,HttpServletRequest request,HttpServletResponse response){
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
//		JSONObject json = JSONObject.fromObject(params);
//		Iterator<String> ite = json.keys();
//		boolean flag = false;
//		while(ite.hasNext()) {
//			String key = (String)ite.next();
//			JSONArray arr = json.getJSONArray(key);
//			for(int i = 0; i < arr.size(); i++) {
//				JSONObject info = arr.getJSONObject(i);
//				System.out.println(info.get("leasesCompanyId"));
//				if(info.get("leasesCompanyId").equals(info.get("leasesCompanyId"))){
//					flag = true;
//				}
//			}
//		}
//		if(flag){
//			Map<String, String> map = new HashMap<String, String>();
//			map.put("ResultSign", "Error");
//			map.put("MessageKey", "存在重复的租赁公司");
//			return map;
//		}else{
			return templateHelper.dealRequestWithToken("/OrgUsecarrules/Update/{ruleName}/{userId}/{update}/{ruleYName}", HttpMethod.POST, userToken,params,
				Map.class,params.get("ruleName"),getLoginOrgUser(request).getOrganId(),"update",params.get("ruleYName"));
//		}
	}
	
	@RequestMapping("OrgUsecarrules/Edit")
	@ResponseBody
	public Map edit(@RequestParam String ruleName,HttpServletRequest request,HttpServletResponse response){
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OrgUsecarrules orgUsecarrules = new OrgUsecarrules();
		orgUsecarrules.setName(ruleName);
		orgUsecarrules.setId(getLoginOrgUser(request).getId());
		return templateHelper.dealRequestWithToken("/OrgUsecarrules/GetOrgUsecarrules", HttpMethod.POST, userToken, orgUsecarrules,
					Map.class);	
	}
	
	@RequestMapping("OrgUsecarrules/DeleteOrgUsecarrules")
	@ResponseBody
	public Map<String, String> deleteOrgUsecarrules(@RequestParam String ruleName,HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OrgUsecarrules orgUsecarrules = new OrgUsecarrules();
		orgUsecarrules.setId(getLoginOrgUser(request).getId());
		orgUsecarrules.setName(ruleName);
		return templateHelper.dealRequestWithToken("/OrgUsecarrules/Delete", HttpMethod.POST, userToken,orgUsecarrules,
				Map.class);
//		 return "redirect:/OrgUsecarrules/Index";
	}
	
//	@RequestMapping("OrgUsecarrules/GetLeLeasescompany")
//	@ResponseBody
//	public List<Map<String, Object>> getLeLeasescompany(HttpServletRequest request,HttpServletResponse response){
//		response.setContentType("text/html;charset=utf-8");
//		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
//		if(request.getParameter("id")!=null && !request.getParameter("id").equals("")){
//			return templateHelper.dealRequestWithToken("/OrgUsecarrules/GetLeLeasescompany/{id}", HttpMethod.GET, userToken,null,
//					List.class,request.getParameter("id"));
//		}else{
//		OrgOrganCompanyRef o = new OrgOrganCompanyRef();
//		o.setOrganId(getLoginOrgUser(request).getOrganId());
//		o.setRulesType(request.getParameter("rulesType"));
//		return templateHelper.dealRequestWithToken("/OrgUsecarrules/GetLeLeasescompany", HttpMethod.POST, userToken,o,
//				List.class);
//		}
//	}
	
//	@RequestMapping("OrgUsecarrules/GetLeVehiclemodels")
//	@ResponseBody
//	public List<Map<String, Object>> getLeVehiclemodels(HttpServletRequest request,HttpServletResponse response){
//		response.setContentType("text/html;charset=utf-8");
//		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
//		if(request.getParameter("id")!=null && !request.getParameter("id").equals("")){
//			OrgOrganCompanyRef o = new OrgOrganCompanyRef();
//			o.setCompanyId(request.getParameter("id"));
//			o.setOrganId(getLoginOrgUser(request).getOrganId());
//			o.setRulesType(request.getParameter("rulesType"));
//			return templateHelper.dealRequestWithToken("/OrgUsecarrules/GetLeVehiclemodels", HttpMethod.POST, userToken,o,
//					List.class);
//		}else{
//			return null;
//		}
//	}
	
	@RequestMapping("OrgUsecarrules/GetEditJson")
	@ResponseBody
	public Map getEditJson(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OrgUsecarrules orgUsecarrules = new OrgUsecarrules();
		orgUsecarrules.setName(request.getParameter("ruleName"));
		orgUsecarrules.setOrganId(getLoginOrgUser(request).getOrganId());
		return templateHelper.dealRequestWithToken("/OrgUsecarrules/GetOrgUsecarrules", HttpMethod.POST, userToken, orgUsecarrules,
				Map.class);	
	}
	
	@RequestMapping("OrgUsecarrules/GetALLJson")
	@ResponseBody
	public Map getAllJson(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		String organid = getLoginOrgUser(request).getOrganId();
		return templateHelper.dealRequestWithToken("/OrgUsecarrules/GetAllRules/{organid}", HttpMethod.GET, userToken, null,
				Map.class,organid);
	}
	
	
	@RequestMapping("OrgUsecarrules/CheckRulesUpdate")
	@ResponseBody
	public int checkRulesUpdate(@RequestParam String name,HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
//		String organid = getLoginOrgUser(request).getOrganId();
		return templateHelper.dealRequestWithToken("/OrgUsecarrules/CheckRulesUpdate/{name}", HttpMethod.GET, userToken, null,
				int.class,name);
	}
	
}
