package com.szyciov.operate.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.entity.PubPersonagreement;
import com.szyciov.operate.service.PubPersonagreementService;
import com.szyciov.util.BaseController;

/**
 * 个人协议管理
 */
@Controller
public class PubPersonagreementController extends BaseController {
	
	private PubPersonagreementService personagreementService;
	@Resource(name = "PubPersonagreementService")
	public void setPersonagreementService(PubPersonagreementService personagreementService) {
		this.personagreementService = personagreementService;
	}
	
	/**
	 * 修改个人协议管理
	 * @param object
	 * @return
	 */
	@RequestMapping(value = "api/PubPersonagreement/EditPubPersonagreement", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> editPubPersonagreement(@RequestBody PubPersonagreement object) {
		return personagreementService.editPubPersonagreement(object);
	}
	
	/**
	 * 获取个人协议数据
	 * @return
	 */
	@RequestMapping(value = "api/PubPersonagreement/GetPubPersonagreement", method = RequestMethod.POST)
	@ResponseBody
	public PubPersonagreement getPubPersonagreement() {
		return personagreementService.getPubPersonagreement();
	}

}
