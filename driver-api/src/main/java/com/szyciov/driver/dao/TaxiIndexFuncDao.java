package com.szyciov.driver.dao;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.driver.mapper.TaxiIndexFuncMapper;
import com.szyciov.entity.PeDrivershiftPending;
import com.szyciov.entity.PubDriver;
import com.szyciov.lease.entity.LeShiftRule;
import com.szyciov.op.entity.OpShiftRules;



/**
  * @ClassName TaxiIndexFuncDao
  * @author Efy Shu
  * @Description 出租车首页功能Dao
  * @date 2017年3月20日 09:47:20
  */ 
@Repository("TaxiIndexFuncDao")
public class TaxiIndexFuncDao{

	/**
	  *依赖
	  */
	private TaxiIndexFuncMapper taxiindexfuncmapper;

	/**
	  *依赖注入
	  */
	@Resource
	public void setTaxiIndexFuncMapper(TaxiIndexFuncMapper taxiindexfuncmapper){
		this.taxiindexfuncmapper=taxiindexfuncmapper;
	}

	/**
	 * 获取对班司机列表
	 * @param param
	 * @return
	 */
	public List<PubDriver> getWorkmates(PubDriver driver){
		List<PubDriver> list = taxiindexfuncmapper.getWorkmates(driver);
		return list;
	}
	
	/**
	 * 获取当前活动的交接班
	 * @param pending
	 * @return
	 */
	public PeDrivershiftPending getShiftWorkApply(PeDrivershiftPending pending){
		return taxiindexfuncmapper.getShiftWorkApply(pending);
	}
	
	/**
	 * 获取Le交接班规则
	 * @param driver
	 * @return
	 */
	public LeShiftRule getLeShiftWorkRule(PubDriver driver){
		return taxiindexfuncmapper.getLeShiftRule(driver);
	}
	
	/**
	 * 获取Op交接班规则
	 * @param driver
	 * @return
	 */
	public OpShiftRules getOpShiftWorkRule(PubDriver driver){
		return taxiindexfuncmapper.getOpShiftRule(driver);
	}
}
