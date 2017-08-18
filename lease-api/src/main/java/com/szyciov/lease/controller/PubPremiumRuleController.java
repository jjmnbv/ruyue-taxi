package com.szyciov.lease.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.dto.pubPremiumRule.PubPremiumAdd;
import com.szyciov.dto.pubPremiumRule.PubPremiumDetail;
import com.szyciov.dto.pubPremiumRule.PubPremiumHistory;
import com.szyciov.dto.pubPremiumRule.PubPremiumModify;
import com.szyciov.dto.pubPremiumRule.PubPremiumParam;
import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.lease.service.PubPremiumRuleService;
import com.szyciov.util.PageBean;

@Controller
public class PubPremiumRuleController {
	Logger logger = LoggerFactory.getLogger(PubPremiumRuleController.class);
	public PubPremiumRuleService pubPremiumRuleService;
	@Resource(name = "pubPremiumRuleService")
	public void setPubPremiumRuleService(PubPremiumRuleService pubPremiumRuleService) {
		this.pubPremiumRuleService = pubPremiumRuleService;
	}
	@RequestMapping(value = "api/PubPremiumRule/PubPremiumParam", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getPubPremiumRuleByQuery(@RequestBody PubPremiumParam pubPremiumParam)  {
		return pubPremiumRuleService.getPubPremiumRuleByQuery(pubPremiumParam);
	}
	@RequestMapping(value = "api/PubPremiumRule/getCity", method = RequestMethod.GET)
	@ResponseBody
	public List<PubDictionary> getCity()  {
		return pubPremiumRuleService.getCity();
	}
	@RequestMapping(value = "api/PubPremiumRule/ruleConflict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> ruleConflict(@RequestBody PubPremiumParam pubPremiumParam)  {
		return pubPremiumRuleService.ruleConflict(pubPremiumParam);
	}
	@RequestMapping(value = "api/PubPremiumRule/ruleConflictOk", method = RequestMethod.POST)
	@ResponseBody
	public int ruleConflictOk(@RequestBody PubPremiumParam pubPremiumParam)  {
		 pubPremiumRuleService.ruleConflictOk(pubPremiumParam);
		 return 1;
	}
	//增加溢价规则
	@RequestMapping(value = "api/PubPremiumRule/PubPremiumAdd",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> aadPubPremium(@RequestBody PubPremiumAdd pubPremiumAdd){
		return pubPremiumRuleService.addPuPremiumRule(pubPremiumAdd);
	}
	//详情界面转跳
	@RequestMapping(value = "api/pubPremiumRule/DetailData",method = RequestMethod.POST)
	@ResponseBody
	public PageBean detailIndex(@RequestBody PubPremiumDetail pubPremiumDetail){
		return pubPremiumRuleService.detailIndex(pubPremiumDetail);
	}
	//详情界面转跳
		@RequestMapping(value = "api/pubPremiumRule/DetailDateData",method = RequestMethod.POST)
		@ResponseBody
	public PageBean getDetailDateData(@RequestBody PubPremiumDetail pubPremiumDetail){
			return pubPremiumRuleService.getDetailDateData(pubPremiumDetail);
		}
		@RequestMapping(value = "api/pubPremiumRule/GetHistoryData",method = RequestMethod.POST)
		@ResponseBody
	public PageBean getHistoryData(@RequestBody PubPremiumHistory pubPremiumHistory){
			return pubPremiumRuleService.getHistoryData(pubPremiumHistory);
		}
		@RequestMapping(value = "api/pubPremiumRule/Modify", method = RequestMethod.POST)
		@ResponseBody
		public PubPremiumModify modify(@RequestBody PubPremiumParam pubPremiumParam)  {
			 return pubPremiumRuleService.modify(pubPremiumParam);
		}
		@RequestMapping(value = "api/pubPremiumRule/GetHistorydetail",method = RequestMethod.POST)
		@ResponseBody
	public PageBean getHistorydetail(@RequestBody PubPremiumHistory pubPremiumHistory){
			return pubPremiumRuleService.getHistorydetail(pubPremiumHistory);
		}
		@RequestMapping(value = "api/PubPremiumRule/getWeeks/{id}", method = RequestMethod.GET)
		@ResponseBody
	public List<PubDictionary> getWeeks(@PathVariable String id)  {
			return pubPremiumRuleService.getWeeks(id);
		}
		@RequestMapping(value = "api/PubPremiumRule/getRulename/{id}", method = RequestMethod.GET)
		@ResponseBody
	public PubPremiumParam getRulename(@PathVariable String id)  {
			return pubPremiumRuleService.getRulename(id);
		}
}
