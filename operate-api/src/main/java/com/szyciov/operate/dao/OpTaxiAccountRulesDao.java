package com.szyciov.operate.dao;


import java.util.List;

import javax.annotation.Resource;

import com.szyciov.entity.Select2Entity;
import com.szyciov.op.entity.OpTaxiAccountRule;
import com.szyciov.op.entity.OpTaxiAccountRuleModilog;
import com.szyciov.operate.mapper.OpTaxiAccountRulesMapper;
import com.szyciov.param.Select2Param;

import org.springframework.stereotype.Repository;



/**
  * @ClassName OpTaxiAccountRulesDao
  * @author Efy Shu
  * @Description 出租车计费规则Dao
  * @date 2017年3月9日 11:43:09
  */ 
@Repository("OpTaxiAccountRulesDao")
public class OpTaxiAccountRulesDao{

	/**
	  *依赖
	  */
	private OpTaxiAccountRulesMapper optaxiaccountrulesmapper;

	/**
	  *依赖注入
	  */
	@Resource
	public void setOpTaxiAccountRulesMapper(OpTaxiAccountRulesMapper optaxiaccountrulesmapper){
		this.optaxiaccountrulesmapper=optaxiaccountrulesmapper;
	}

	public List<Select2Entity> citySelect(Select2Param param){
		List<Select2Entity> list = optaxiaccountrulesmapper.citySelect(param);
		return list;
	}
	
	public List<OpTaxiAccountRule> search(OpTaxiAccountRule rule){
		List<OpTaxiAccountRule> list = optaxiaccountrulesmapper.search(rule);
		return list;
	}
	
	public OpTaxiAccountRule searchById(OpTaxiAccountRule rule){
		List<OpTaxiAccountRule> list = optaxiaccountrulesmapper.search(rule);
		OpTaxiAccountRule result = null;
		if(list != null && !list.isEmpty()){
			result = list.get(0);
		}
		return result;
	}
	
	public int getTotalCount(OpTaxiAccountRule rule){
		return optaxiaccountrulesmapper.getTotalCount(rule);
	}
	
	public boolean saveModiLog(OpTaxiAccountRuleModilog ruleLog){
		optaxiaccountrulesmapper.saveModiLog(ruleLog);
		return true;
	}
	
	public boolean save(OpTaxiAccountRule rule){
		optaxiaccountrulesmapper.saveOrUpdate(rule);
		return true;
	}
	
	public boolean edit(OpTaxiAccountRule rule){
		optaxiaccountrulesmapper.saveOrUpdate(rule);
		return true;
	}
	
	public boolean enable(OpTaxiAccountRule rule){
		optaxiaccountrulesmapper.updateRuleState(rule);
		return true;
	}
	
	public boolean disable(OpTaxiAccountRule rule){
		optaxiaccountrulesmapper.updateRuleState(rule);
		return true;
	}
	
	public int getHistoryTotalCount(OpTaxiAccountRule rule){
		return optaxiaccountrulesmapper.getHistoryTotalCount(rule);
	}
	
	public List<OpTaxiAccountRuleModilog> searchHistory(OpTaxiAccountRule rule){
		return optaxiaccountrulesmapper.searchHistory(rule);
	}
}
