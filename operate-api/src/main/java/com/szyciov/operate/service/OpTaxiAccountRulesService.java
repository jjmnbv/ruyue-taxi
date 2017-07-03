package com.szyciov.operate.service;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.szyciov.entity.DataStatus;
import com.szyciov.entity.Select2Entity;
import com.szyciov.enums.OperateTypeEnum;
import com.szyciov.enums.RuleStateEnum;
import com.szyciov.op.entity.OpTaxiAccountRule;
import com.szyciov.op.entity.OpTaxiAccountRuleModilog;
import com.szyciov.operate.dao.OpTaxiAccountRulesDao;
import com.szyciov.param.Select2Param;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PageBean;



/**
  * @ClassName OpTaxiAccountRulesService
  * @author Efy Shu
  * @Description 出租车计费规则Service
  * @date 2017年3月9日 11:43:09
  */ 
@Service("OpTaxiAccountRulesService")
public class OpTaxiAccountRulesService{
	private static final Logger logger = Logger.getLogger(OpTaxiAccountRulesService.class);
	
	/**
	  *依赖
	  */
	private OpTaxiAccountRulesDao optaxiaccountrulesdao;

	/**
	  *依赖注入
	  */
	@Resource(name="OpTaxiAccountRulesDao")
	public void setOpTaxiAccountRulesDao(OpTaxiAccountRulesDao optaxiaccountrulesdao){
		this.optaxiaccountrulesdao=optaxiaccountrulesdao;
	}

	public List<Select2Entity> getCityListForSelect(Select2Param param){
		List<Select2Entity> list = optaxiaccountrulesdao.citySelect(param);
		return list;
	}
	
	public PageBean search(OpTaxiAccountRule rule){
		logger.info("检索出租车计费规则:" + rule.getCityname()==null?"全部":rule.getCityname());
		int totalCount = optaxiaccountrulesdao.getTotalCount(rule);
		logger.info("检索到的总条目:" + totalCount);
		List<OpTaxiAccountRule> list = optaxiaccountrulesdao.search(rule);
		logger.info("当前页个数:" + list.size());
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(rule.getsEcho()+"");
		pageBean.setiTotalDisplayRecords(totalCount);
		pageBean.setiTotalRecords(totalCount);
		pageBean.setAaData(list);
		return pageBean;
	}
	
	public OpTaxiAccountRule searchById(OpTaxiAccountRule rule){
		OpTaxiAccountRule result = optaxiaccountrulesdao.searchById(rule);
		return result;
	}
	
	public boolean save(OpTaxiAccountRule rule){
		rule.setId(GUIDGenerator.newGUID());
		rule.setRulesstate(RuleStateEnum.ENABLE.code);
		rule.setCreatetime(new Date());
		rule.setUpdatetime(new Date());
		rule.setStatus(DataStatus.OK.code);
		optaxiaccountrulesdao.save(rule);
		//出租车新增计费规则不记录日志2017-4-18 09:19:18,对应bug8690
//		saveModiLog(rule,OperateTypeEnum.ADD);
		return true;
	}
	
	public boolean edit(OpTaxiAccountRule rule){
		rule.setUpdatetime(new Date());
		saveModiLog(rule,OperateTypeEnum.MODIFY);
		optaxiaccountrulesdao.edit(rule);
		return true;
	}
	
	public boolean disable(OpTaxiAccountRule rule){
		rule.setUpdatetime(new Date());
		optaxiaccountrulesdao.disable(rule);
		saveModiLog(rule,OperateTypeEnum.DISABLE);
		return true;
	}
	
	public boolean enable(OpTaxiAccountRule rule){
		rule.setUpdatetime(new Date());
		optaxiaccountrulesdao.enable(rule);
		saveModiLog(rule,OperateTypeEnum.ENABLE);
		return true;
	}
	
	public void saveModiLog(OpTaxiAccountRule rule,OperateTypeEnum type){
		OpTaxiAccountRule oldRule = searchById(rule);
		OpTaxiAccountRuleModilog ruleLog = new OpTaxiAccountRuleModilog();
		ruleLog.setId(GUIDGenerator.newGUID());
		ruleLog.setTaxiaccountrulesid(oldRule.getId());
		ruleLog.setCity(oldRule.getCity());
		ruleLog.setOperatetype(type.code);
		ruleLog.setRenewalprice(oldRule.getRenewalprice());
		ruleLog.setRulesstate(oldRule.getRulesstate());
		ruleLog.setStandardrange(oldRule.getStandardrange());
		ruleLog.setEmptytravelrate(oldRule.getEmptytravelrate());
		ruleLog.setStartprice(oldRule.getStartprice());
		ruleLog.setStartrange(oldRule.getStartrange());
		ruleLog.setSurcharge(oldRule.getSurcharge());
		ruleLog.setCreatetime(new Date());
		ruleLog.setUpdatetime(new Date());
		ruleLog.setCreater(rule.getUpdater());
		ruleLog.setUpdater(rule.getUpdater());
		ruleLog.setStatus(DataStatus.OK.code);
		optaxiaccountrulesdao.saveModiLog(ruleLog);
	}
	
	public PageBean searchHistory(OpTaxiAccountRule rule){
		logger.info("检索出租车计费规则操作记录:" + rule.getCityname()==null?"全部":rule.getCityname());
		int totalCount = optaxiaccountrulesdao.getHistoryTotalCount(rule);
		logger.info("检索到的总条目:" + totalCount);
		List<OpTaxiAccountRuleModilog> list = optaxiaccountrulesdao.searchHistory(rule);
		logger.info("当前页个数:" + list.size());
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(rule.getsEcho()+"");
		pageBean.setiTotalDisplayRecords(totalCount);
		pageBean.setiTotalRecords(totalCount);
		pageBean.setAaData(list);
		return pageBean;
	}
}
