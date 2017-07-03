package com.szyciov.organ.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.lease.entity.LeAccountRules;
import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.lease.entity.OrgOrganCompanyRef;
import com.szyciov.org.entity.OrgUsecarrules;
import com.szyciov.organ.service.OrgUsecarrulesService;
import com.szyciov.util.BaseController;

/**
 * 控制器
 */
@Controller
public class OrgUsecarrulesController extends BaseController {
	private static final Logger logger = Logger.getLogger(OrgUsecarrulesController.class);

	public OrgUsecarrulesService orgUsecarrulesService;

	@Resource(name = "OrgUsecarrulesService")
	public void setOrgOrganService(OrgUsecarrulesService orgUsecarrulesService) {
		this.orgUsecarrulesService = orgUsecarrulesService;
	}
	
//	/**
//	 * <p>
//	 * 
//	 * </p>
//	 *
//	 * @param 
//	 * @return
//	 */
//	@RequestMapping(value = "api/OrgUsecarrules/GetLeLeasescompany/{id}", method = RequestMethod.GET)
//	@ResponseBody
//	public List<Map<String, Object>> getLeLeasescompany(@PathVariable String id) {
//		return orgUsecarrulesService.getLeLeasescompany(id);
//	}
	
//	/**
//	 * <p>
//	 * 
//	 * </p>
//	 *
//	 * @param 
//	 * @return
//	 */
//	@RequestMapping(value = "api/OrgUsecarrules/GetLeLeasescompany", method = RequestMethod.POST)
//	@ResponseBody
//	public List<Map<String, Object>> getLeLeasescompany(@RequestBody OrgOrganCompanyRef o) {
//		return orgUsecarrulesService.getLeLeasescompany(o);
//	}
//	/**
//	 * <p>
//	 * 
//	 * </p>
//	 *
//	 * @param 
//	 * @return
//	 */
//	@RequestMapping(value = "api/OrgUsecarrules/GetLeVehiclemodels", method = RequestMethod.POST)
//	@ResponseBody
//	public List<Map<String, Object>> getLeVehiclemodels(@RequestBody OrgOrganCompanyRef o) {
//		return orgUsecarrulesService.getLeVehiclemodels(o);
//	}
	
	/**
	 * <p>
	 * 
	 * </p>
	 *
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "api/OrgUsecarrules/GetOrgUsecarrules", method = RequestMethod.POST)
	@ResponseBody
	public Map getOrgUsecarrules(@RequestBody OrgUsecarrules orgUsecarrules) {
		return orgUsecarrulesService.getOrgUsecarrules(orgUsecarrules);
	}
	
	/**
	 * <p>
	 * 
	 * </p>
	 *
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "api/OrgUsecarrules/Delete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> delete(@RequestBody OrgUsecarrules orgUsecarrules) {
		return orgUsecarrulesService.delete(orgUsecarrules);
	}
	
	/**
	 * <p>
	 * 
	 * </p>
	 *
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "api/OrgUsecarrules/Add/{ruleName}/{organId}/{add}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(@RequestBody Map map,@PathVariable String ruleName,@PathVariable String organId,@PathVariable String add) {
		return orgUsecarrulesService.add(map,ruleName,organId,add);
	}
	
	/**
	 * <p>
	 * 
	 * </p>
	 *
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "api/OrgUsecarrules/Update/{ruleName}/{organId}/{update}/{ruleYName}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> update(@RequestBody Map map,@PathVariable String ruleName,@PathVariable String organId,@PathVariable String update,@PathVariable String ruleYName) {
		return orgUsecarrulesService.update(map,ruleName,organId,update,ruleYName);
	}
	
	/**
	 * <p>
	 * 
	 * </p>
	 *
	 * @param 
	 * @return 
	 * @return
	 */
	@RequestMapping(value = "api/OrgUsecarrules/GetRulestype/{organid}", method = RequestMethod.GET)
	@ResponseBody
	public  List<LeAccountRules> getRulestype(@PathVariable String organid) {
		return orgUsecarrulesService.getRulestype(organid);
	}
	
	/**
	 * <p>
	 * 
	 * </p>
	 *
	 * @param 
	 * @return 
	 * @return
	 */
	@RequestMapping(value = "api/OrgUsecarrules/GetAllRules/{organid}", method = RequestMethod.GET)
	@ResponseBody
	public  Map getAllRules(@PathVariable String organid) {
		return orgUsecarrulesService.getAllRules(organid);
	}
	
	/**
	 * <p>
	 * 根据用车规则的名字查询 是否能 （编辑）
	 * </p>
	 *
	 * @param 
	 * @return 
	 * @return
	 */
	@RequestMapping(value = "api/OrgUsecarrules/CheckRulesUpdate/{name}", method = RequestMethod.GET)
	@ResponseBody
	public  int checkRulesUpdate(@PathVariable String name) {
		return orgUsecarrulesService.checkRulesUpdate(name);
	}
}
