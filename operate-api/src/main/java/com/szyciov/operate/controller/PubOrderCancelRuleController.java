package com.szyciov.operate.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.dto.pubOrderCancelRule.PubOrderCancelRule;
import com.szyciov.dto.pubOrderCancelRule.PubOrderCancelRuleHistory;
import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.operate.service.PubOrderCancelRuleService;
import com.szyciov.util.PageBean;
@Controller
public class PubOrderCancelRuleController {
	public PubOrderCancelRuleService pubOrderCancelRuleService;
	@Resource(name = "pubOrderCancelRuleService")
	public void setPubOrderCancelRuleService(PubOrderCancelRuleService pubOrderCancelRuleService) {
		this.pubOrderCancelRuleService = pubOrderCancelRuleService;
	}
	@RequestMapping(value = "api/PubOrderCancelRule/getCity", method = RequestMethod.GET)
	@ResponseBody
	public List<PubDictionary> getCity()  {
		return pubOrderCancelRuleService.getCity();
	}
	@RequestMapping(value = "api/PubOrderCancelRule/GetPubOrderCancelRule", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getPubOrderCancelRule(@RequestBody PubOrderCancelRule pubOrderCancelRule)  {
		return pubOrderCancelRuleService.getPubOrderCancelRule(pubOrderCancelRule);
	}
	//增加规则
	@RequestMapping(value = "api/PubOrderCancelRule/PubOrderCancelRuleAdd",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> aadPubOrderCancelRule(@RequestBody PubOrderCancelRule pubOrderCancelRule){
		return pubOrderCancelRuleService.aadPubOrderCancelRule(pubOrderCancelRule);
	}
	//禁用规则
	@RequestMapping(value = "api/PubOrderCancelRule/ruleConflictOk",method = RequestMethod.POST)
	@ResponseBody
	public int ruleConflictOk(@RequestBody PubOrderCancelRule pubOrderCancelRule){
		return pubOrderCancelRuleService.ruleConflictOk(pubOrderCancelRule);
	}
	//启用规则
	@RequestMapping(value = "api/PubOrderCancelRule/ruleConflict",method = RequestMethod.POST)
	@ResponseBody
	public int ruleConflict(@RequestBody PubOrderCancelRule pubOrderCancelRule){
		return pubOrderCancelRuleService.ruleConflict(pubOrderCancelRule);
	}
	@RequestMapping(value = "api/PubOrderCancelRule/GetHistoryData", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getHistoryData(@RequestBody  PubOrderCancelRuleHistory history)  {
		return pubOrderCancelRuleService.getHistoryData(history);
	}
	@RequestMapping(value = "api/PubOrderCancelRule/getRulename/{id}", method = RequestMethod.GET)
	@ResponseBody
	public PubOrderCancelRule getRulename(@PathVariable String id)  {
		return pubOrderCancelRuleService.getRulename(id);
	}

}
