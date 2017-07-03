package com.szyciov.operate.mapper;

import java.util.List;

import com.szyciov.entity.Select2Entity;
import com.szyciov.op.entity.OpTaxiAccountRule;
import com.szyciov.op.entity.OpTaxiAccountRuleModilog;
import com.szyciov.param.Select2Param;

/**
  * @ClassName OpTaxiAccountRulesMapper
  * @author Efy Shu
  * @Description 出租车计费规则Mapper
  * @date 2017年3月9日 11:43:09
  */ 
public interface OpTaxiAccountRulesMapper{
	
	public List<Select2Entity> citySelect(Select2Param param);
	
	/**
	 * 保存或更新
	 * @param rule
	 */
	public void saveOrUpdate(OpTaxiAccountRule rule);
	/**
	 * 更新全部字段
	 * @param rule
	 */
	public void updateAll(OpTaxiAccountRule rule);
	/**
	 * 获取总数
	 * @param rule
	 * @return
	 */
	public int getTotalCount(OpTaxiAccountRule rule);
	/**
	 * 检索列表
	 * 有效参数:
	 * city,rulesstate
	 * @param rule
	 * @return
	 */
	public List<OpTaxiAccountRule> search(OpTaxiAccountRule rule);
	/**
	 * 根据ID检索规则
	 * @param rule
	 * @return
	 */
	public OpTaxiAccountRule searchById(OpTaxiAccountRule rule);
	/**
	 * 操作历史总数
	 * @param rule
	 * @return
	 */
	public int getHistoryTotalCount(OpTaxiAccountRule rule);
	/**
	 * 查询操作历史
	 * @param rule
	 * @return
	 */
	public List<OpTaxiAccountRuleModilog> searchHistory(OpTaxiAccountRule rule);
	/**
	 * 保存操作记录
	 * @param ruleLog
	 */
	public void saveModiLog(OpTaxiAccountRuleModilog ruleLog); 
	/**
	 * 改变规则状态
	 * @param rule
	 */
	public void updateRuleState(OpTaxiAccountRule rule);
}
