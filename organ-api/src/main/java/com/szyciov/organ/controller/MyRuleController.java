package com.szyciov.organ.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.organ.service.MyRuleService;
import com.szyciov.util.BaseController;

/**
 * 控制器
 */
@Controller
public class MyRuleController extends BaseController {
	private static final Logger logger = Logger.getLogger(MyRuleController.class);

	public MyRuleService myRuleService;

	@Resource(name = "MyRuleService")
	public void setOrgOrganService(MyRuleService myRuleService) {
		this.myRuleService = myRuleService;
	}
	/**
	 * <p>
	 * 
	 * </p>
	 *
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "api/MyRule/GetOrgUsecarrules", method = RequestMethod.POST)
	@ResponseBody
	public Map getOrgUsecarrules(@RequestBody Map<String, String> map) {
		return myRuleService.getOrgUsecarrules(map);
	}
	
}
