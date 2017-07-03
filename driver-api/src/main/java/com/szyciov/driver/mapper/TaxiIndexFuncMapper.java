package com.szyciov.driver.mapper;

import java.util.List;

import com.szyciov.entity.PeDrivershiftPending;
import com.szyciov.entity.PubDriver;
import com.szyciov.lease.entity.LeShiftRule;
import com.szyciov.op.entity.OpShiftRules;

/**
  * @ClassName TaxiIndexFuncMapper
  * @author Efy Shu
  * @Description 出租车首页功能Mapper
  * @date 2017年3月20日 09:47:20
  */ 
public interface TaxiIndexFuncMapper{
	/**
	 * 获取对班司机列表
	 * @param driver
	 * @return
	 */
	public List<PubDriver> getWorkmates(PubDriver driver);
	/**
	 * 获取交班申请
	 * @param pending
	 * @return
	 */
	public PeDrivershiftPending getShiftWorkApply(PeDrivershiftPending pending);
	/**
	 * 获取租赁公司交接班规则
	 * @param driver
	 * @return
	 */
	public LeShiftRule getLeShiftRule(PubDriver driver);
	/**
	 * 获取运管平台交接班规则
	 * @param driver
	 * @return
	 */
	public OpShiftRules getOpShiftRule(PubDriver driver);
}
