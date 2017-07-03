package com.szyciov.lease.mapper;

import java.util.List;

import com.szyciov.entity.Select2Entity;
import com.szyciov.lease.entity.LeShiftRule;
import com.szyciov.param.Select2Param;
import org.apache.ibatis.annotations.Param;

/**
  * @ClassName LeShiftRulesMapper
  * @author Efy
  * @Description 交接班规则Mapper
  * @date 2017年3月2日 14:07:02
  */ 
public interface LeShiftRulesMapper{
	
	/**
	 * 保存或更新
	 * @param rule
	 */
	public void saveOrUpdate(LeShiftRule rule);
	/**
	 * 更新全部字段
	 * @param rule
	 */
	public void updateLeShiftRule(LeShiftRule rule);
	/**
	 * 获取总数
	 * @param rule
	 * @return
	 */
	public int getTotalCount(LeShiftRule rule);
	/**
	 * 检索列表
	 * @param rule
	 * @return
	 */
	public List<LeShiftRule> search(LeShiftRule rule);
	/**
	 * 根据ID检索规则
	 * @param rule
	 * @return
	 */
	public LeShiftRule searchById(LeShiftRule rule);
	/**
	 * 根据城市检索规则
	 * @param rule
	 * @return
	 */
	public LeShiftRule searchByCity(LeShiftRule rule);



	/**
	 * 根据条件返回单个规则
	 * @param leaseId		租赁公司ID
	 * @param city			城市
	 * @return
	 */
	LeShiftRule getRules(@Param(value = "leasescompanyid") String leaseId,@Param(value = "city") String city);


	/**
	 * 城市检索
	 * @param param
	 * @return
	 */
	public List<Select2Entity> citySelect(Select2Param param);
}
