package com.szyciov.organ.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.org.entity.OrgUser;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.TemplateHelper;

@Controller
public class UpdatePasswordController extends BaseController {
	private static final Logger logger = Logger.getLogger(UpdatePasswordController.class);
	private TemplateHelper templateHelper = new TemplateHelper();
	@RequestMapping("UpdatePassword/Index")
	@ResponseBody
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView();
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
//		mav.addObject("childCccount", childCccount);
		mav.setViewName("resource/updatePassword/index");
		return mav;
	}

	@RequestMapping("UpdatePassword/CheckPasswords")
	@ResponseBody
	public Map<String, Object> checkPasswords(@RequestParam String userpassword,HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OrgUser orgUser = new OrgUser();
		orgUser.setId(getLoginOrgUser(request).getId());
		orgUser.setUserPassword(userpassword);
		return templateHelper.dealRequestWithToken("/SupplierManagement/CheckPasswords", HttpMethod.POST, userToken, orgUser,
				Map.class);
	}
	
	@RequestMapping("UpdatePassword/updatePassword")
	@ResponseBody
	public Map<String, String> updatePassword(@RequestParam String userpassword,HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OrgUser orgUser = new OrgUser();
		orgUser.setId(getLoginOrgUser(request).getId());
		orgUser.setUserPassword(userpassword);
		return templateHelper.dealRequestWithToken("/SupplierManagement/UpdatePassword", HttpMethod.POST, userToken, orgUser,
				Map.class);
	}
}
