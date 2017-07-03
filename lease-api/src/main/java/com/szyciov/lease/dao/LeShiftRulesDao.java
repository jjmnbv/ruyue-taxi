package com.szyciov.lease.dao;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.entity.Select2Entity;
import com.szyciov.lease.entity.LeShiftRule;
import com.szyciov.lease.mapper.LeShiftRulesMapper;
import com.szyciov.param.Select2Param;
/**
  * @ClassName LeShiftRulesDao
  * @author Efy
  * @Description 交接班规则Dao
  * @date 2017年3月2日 14:07:02
  */ 
@Repository("LeShiftRulesDao")
public class LeShiftRulesDao{
	/**
	  *依赖
	  */
	private LeShiftRulesMapper leshiftrulesmapper;

	/**
	  *依赖注入
	  */
	@Resource
	public void setLeShiftRulesMapper(LeShiftRulesMapper leshiftrulesmapper){
		this.leshiftrulesmapper=leshiftrulesmapper;
	}
	
	/**
	 * 保存或更新
	 * @param rule
	 * @return
	 */
	public boolean saveOrUpdate(LeShiftRule rule){
		leshiftrulesmapper.saveOrUpdate(rule);
		return true;
	}

	/**
	 * 更新全部字段
	 * @param rule
	 * @return
	 */
	public boolean update(LeShiftRule rule){
		leshiftrulesmapper.updateLeShiftRule(rule);
		return true;
	}
	
	/**
	 * 获取规则总数
	 * @param rule
	 * @return
	 */
	public int getTotalCount(LeShiftRule rule){
		return leshiftrulesmapper.getTotalCount(rule);
	}
	
	/**
	 * 检索
	 * @param rule
	 * @return
	 */
	public List<LeShiftRule> search(LeShiftRule rule){
		return leshiftrulesmapper.search(rule);
	}
	/**
	 * 检索
	 * @param rule
	 * @return
	 */
	public LeShiftRule searchById(LeShiftRule rule){
		return leshiftrulesmapper.searchById(rule);
	}
	/**
	 * 检索
	 * @param rule
	 * @return
	 */
	public LeShiftRule searchByCity(LeShiftRule rule){
		return leshiftrulesmapper.searchByCity(rule);
	}
	
	/**
	 * 城市检索
	 * @param param
	 * @return
	 */
	public List<Select2Entity> citySelect(Select2Param param){
		return leshiftrulesmapper.citySelect(param);
	}



	public LeShiftRule getRules(String leaseId,String city){
		return leshiftrulesmapper.getRules(leaseId,city);
	}
}
