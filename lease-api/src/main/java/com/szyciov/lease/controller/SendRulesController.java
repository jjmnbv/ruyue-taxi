package com.szyciov.lease.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.param.LeSendRulesQueryParam;
import com.szyciov.lease.service.SendRulesService;
import com.szyciov.op.entity.PubSendRules;
import com.szyciov.op.param.PubSendRulesHistoryQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;

/**
 * 派单规则模块控制器
 */
@Controller
public class SendRulesController extends BaseController {
	private static final Logger logger = Logger.getLogger(SendRulesController.class);

	public SendRulesService sendRulesService;

	@Resource(name = "sendRulesService")
	public void setSendRulesService(SendRulesService sendRulesService) {
		this.sendRulesService = sendRulesService;
	}
	
	/** 
	 * <p>分页查询派单规则</p>
	 *
	 * @param queryParam 查询请求对象，封装需要查询的key和页码等信息
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "api/SendRules/GetSendRulesByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getSendRulesByQuery(@RequestBody LeSendRulesQueryParam queryParam) {
		return sendRulesService.getSendRulesByQuery(queryParam);
	}
	
	/** 
	 * <p>新增派单规则</p>
	 *
	 * @param pubSendRules
	 * @return
	 */
	@RequestMapping(value = "api/SendRules/CreateSendRules", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> createSendRules(@RequestBody PubSendRules pubSendRules) {
		return sendRulesService.createSendRules(pubSendRules);
	}
	
	/** 
	 * <p>修改派单规则</p>
	 *
	 * @param leAccountRules
	 * @return
	 */
	@RequestMapping(value = "api/SendRules/UpdateSendRules", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updateSendRules(@RequestBody PubSendRules pubSendRules) {
		return sendRulesService.updateSendRules(pubSendRules);
	}
	
	/** 
	 * <p>所属城市</p>
	 *
	 * @param leasesCompanyId 所属租赁公司
	 * @return
	 */
	@RequestMapping(value = "api/SendRules/GetCityListById", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getCityListById(
			@RequestParam(value = "leasesCompanyId", required = true) String leasesCompanyId,
			@RequestParam(value = "cityName", required = false) String cityName) {
		logger.log(Level.INFO, "api getCityListById leasesCompanyId:" + leasesCompanyId);
		logger.log(Level.INFO, "api getCityListById cityName:" + cityName);
		return sendRulesService.getCityListById(leasesCompanyId, cityName);
	}
	
	/** 
	 * <p>租赁派单规则</p>
	 *
	 * @param id id
	 * @param leasesCompanyId 所属租赁公司
	 * @return
	 */
	@RequestMapping(value = "api/SendRules/GetSendRulesListById/{id}", method = RequestMethod.GET)
	@ResponseBody
	public PubSendRules getSendRulesListById(@PathVariable String id, @RequestParam(value = "leasesCompanyId", required = true) String leasesCompanyId) {
		logger.log(Level.INFO, "api getCityListById id:" + id);
		logger.log(Level.INFO, "api getCityListById leasesCompanyId:" + leasesCompanyId);
		return sendRulesService.getSendRulesById(id,leasesCompanyId);
	}
	
	/**
	 * 获取派单规则历史记录
	 */
	@RequestMapping(value="api/SendRules/GetSendRulesHistory",method=RequestMethod.POST)
	@ResponseBody
	public PageBean getSenRulesHistory(@RequestBody PubSendRulesHistoryQueryParam queryParam){
         return sendRulesService.getSendRulesHistoryList(queryParam);		
	}
	
	/** 
	 * <p>查找城市信息</p>
	 *
	 * @param id id
	 * @return
	 */
	@RequestMapping(value = "api/SendRules/GetCityById/{city}", method = RequestMethod.GET)
	@ResponseBody
	public PubCityAddr getCityById(@PathVariable String city) {
		logger.log(Level.INFO, "api getCityListById city:" + city);
		return sendRulesService.getCityById(city);
	}
}
