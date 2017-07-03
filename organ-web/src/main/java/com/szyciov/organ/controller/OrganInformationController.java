package com.szyciov.organ.controller;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.org.entity.OrgInformation;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.TemplateHelper;

@Controller
public class OrganInformationController extends BaseController {
	private TemplateHelper templateHelper = new TemplateHelper();
	@RequestMapping("OrganInformation/Index")
	@ResponseBody
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView();
		response.setContentType("text/html;charset=utf-8");
		String organId = getLoginOrgUser(request).getOrganId();
//		String organId = "0090D0DD-F1D0-4FC8-B53E-DFD2DDBF8D7D";
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OrgInformation orgInformation =  templateHelper.dealRequestWithToken("/OrganInformation/GetorgInformation/{organId}", HttpMethod.GET, userToken,null,
				OrgInformation.class,organId);
		mav.addObject("orgInformation", orgInformation);
		mav.setViewName("resource/organInformation/index");
		return mav;
	}
	@RequestMapping("OrganInformation/InsertOrgInformation")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String,String> insertOrgInformation(@RequestBody OrgInformation orgInformation,
			           HttpServletRequest request,HttpServletResponse response){
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return  templateHelper.dealRequestWithToken("/OrganInformation/InsertOrgInformation", HttpMethod.POST, userToken,orgInformation,
				Map.class);
	}

}
