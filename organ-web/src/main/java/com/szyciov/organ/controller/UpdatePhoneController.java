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

import com.szyciov.lease.entity.OrgOrgan;
import com.szyciov.org.entity.OrgUser;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.TemplateHelper;

@Controller
public class UpdatePhoneController extends BaseController {
	private static final Logger logger = Logger.getLogger(UpdatePhoneController.class);
	private TemplateHelper templateHelper = new TemplateHelper();
	@RequestMapping("UpdatePhone/Index")
	@ResponseBody
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView();
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		String id = getLoginOrgUser(request).getId();
		OrgOrgan orgOrgan = templateHelper.dealRequestWithToken("/SupplierManagement/GetOrgorganPhone/{id}", HttpMethod.GET, userToken, null,
				OrgOrgan.class,id);
		mav.addObject("orgOrgan", orgOrgan);
		mav.setViewName("resource/updatePhone/index");
		return mav;
	}
	
	@RequestMapping("UpdatePhone/UpdatePhone")
	@ResponseBody
	public Map<String, String> updatePhone(@RequestParam String phone,HttpServletRequest request,HttpServletResponse response){
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OrgOrgan orgOrgan  = new OrgOrgan();
		OrgUser o = getLoginOrgUser(request);
		orgOrgan.setId(o.getOrganId());
		orgOrgan.setUserId(o.getId());
		orgOrgan.setPhone(phone);
		return templateHelper.dealRequestWithToken("/SupplierManagement/updateAll", HttpMethod.POST, userToken, orgOrgan,
				Map.class);
	}
}
