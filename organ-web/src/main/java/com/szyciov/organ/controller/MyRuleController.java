package com.szyciov.organ.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.org.entity.OrgUsecarrules;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.TemplateHelper;
/**
 * 我的规则
 * */
@Controller
public class MyRuleController extends BaseController {
	private static final Logger logger = Logger.getLogger(MyRuleController.class);
	private TemplateHelper templateHelper = new TemplateHelper();
	@RequestMapping("MyRule/Index")
	@ResponseBody
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView();
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		Map<String, String> map1=new HashMap<>();
		map1.put("id", getLoginOrgUser(request).getId());
		map1.put("organId", getLoginOrgUser(request).getOrganId());
		Map map = templateHelper.dealRequestWithToken("/MyRule/GetOrgUsecarrules", HttpMethod.POST, userToken, map1,
				Map.class);				
		mav.addObject("map", map);
		mav.addObject("size",map.size());
		mav.setViewName("resource/myRule/index");
		return mav;
	}
}
