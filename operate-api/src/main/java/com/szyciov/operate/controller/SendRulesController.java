package com.szyciov.operate.controller;

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
import com.szyciov.op.entity.PubSendRules;
import com.szyciov.op.param.PubSendRulesHistoryQueryParam;
import com.szyciov.op.param.OpTaxiSendrulesQueryParam;
import com.szyciov.operate.service.SendRulesService;
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
	public PageBean getSendRulesByQuery(@RequestBody OpTaxiSendrulesQueryParam queryParam) {
		return sendRulesService.getSendRulesByQuery(queryParam);
	}
	
	/** 
	 * <p>新增派单规则</p>
	 *
	 * @param leSendRules
	 * @return
	 */
	@RequestMapping(value = "api/SendRules/CreateSendRules", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> createSendRules(@RequestBody PubSendRules sendRules) {
		return sendRulesService.createSendRules(sendRules);
	}
	
	/** 
	 * <p>修改派单规则</p>
	 *
	 * @param leSendRules
	 * @return
	 */
	@RequestMapping(value = "api/SendRules/UpdateSendRules", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updateSendRules(@RequestBody PubSendRules sendRules) {
		return sendRulesService.updateSendRules(sendRules);
	}
	
	/** 
	 * <p>所属城市</p>
	 *
	 * @param cityName 城市名称
	 * @return
	 */
	@RequestMapping(value = "api/SendRules/GetCityListById", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getCityListById(@RequestParam(value = "cityName", required = false) String cityName) {
		logger.log(Level.INFO, "api getCityListById cityName:" + cityName);
		return sendRulesService.getCityListById(cityName);
	}
	
	/** 
	 * <p>派单规则</p>
	 *
	 * @param id id
	 * @return
	 */
	@RequestMapping(value = "api/SendRules/GetSendRulesById/{id}", method = RequestMethod.GET)
	@ResponseBody
	public PubSendRules getSendRulesById(@PathVariable String id) {
		logger.log(Level.INFO, "api getSendRulesById id:" + id);
		return sendRulesService.getSendRulesById(id);
	}
	
	/**
	 * 分页查询网约车派单规则历史记录
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "api/SendRules/GetSendRulesHistoryByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getTaxiSendrulesHistoryByQuery(@RequestBody PubSendRulesHistoryQueryParam queryParam) {
		return sendRulesService.getSendrulesHistoryByQuery(queryParam);
	}
	
	
	/** 
	 * <p>查找城市信息</p>
	 *
	 * @param city city
	 * @return
	 */
	@RequestMapping(value = "api/SendRules/GetCityById/{city}", method = RequestMethod.GET)
	@ResponseBody
	public PubCityAddr getCityById(@PathVariable String city) {
		logger.log(Level.INFO, "api getCityById city:" + city);
		return sendRulesService.getCityById(city);
	}
	
	/** 
	 * <p>查找城市list</p>
	 *
	 * @param city city
	 * @return
	 */
	@RequestMapping(value = "api/SendRules/GetPubCityAddr", method = RequestMethod.GET)
	@ResponseBody
	public List<PubCityAddr> getPubCityAddr() {
		return sendRulesService.getPubCityAddr();
	}
}
