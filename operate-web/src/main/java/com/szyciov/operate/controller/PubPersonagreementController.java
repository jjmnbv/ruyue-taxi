package com.szyciov.operate.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.entity.PubPersonagreement;
import com.szyciov.op.entity.OpUser;
import com.szyciov.operate.service.PubPersonagreementService;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;

/**
 * 个人用户协议管理
 */
@Controller
public class PubPersonagreementController extends BaseController {
	
	private PubPersonagreementService personagreementService;
	@Resource(name = "PubPersonagreementService")
	public void setPersonagreementService(PubPersonagreementService personagreementService) {
		this.personagreementService = personagreementService;
	}
	
	/**
	 * 跳转到个人用户协议主页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "PubPersonagreement/Index")
	@ResponseBody
	public ModelAndView getPubPersonagreementIndex(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OpUser user = getLoginOpUser(request);
		ModelAndView view = new ModelAndView();
		view.setViewName("resource/personagreement/index");
		//查询个人用户协议详情
		PubPersonagreement personagreement = personagreementService.getPubPersonagreement(userToken);
		view.addObject("personagreement", personagreement);
		view.addObject("usertype", user.getUsertype());
		return view;
	}
	
	/**
	 * 修改个人用户协议信息
	 * @param object
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "PubPersonagreement/EditPubPersonagreement")
	@ResponseBody
	public Map<String, String> editPubPersonagreement(@RequestBody PubPersonagreement object, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		
		OpUser user = getLoginOpUser(request);
		object.setUpdater(user.getId());
		return personagreementService.editPubPersonagreement(object, userToken);
	}

}
