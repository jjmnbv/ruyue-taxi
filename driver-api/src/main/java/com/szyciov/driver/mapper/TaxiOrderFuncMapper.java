package com.szyciov.driver.mapper;

import java.util.List;

import com.szyciov.driver.param.OrderListParam;
import com.szyciov.op.entity.OpTaxiOrder;
import com.szyciov.op.entity.PeUser;
import com.szyciov.param.OrderApiParam;

/**
  * @ClassName TaxiOrderFuncMapper
  * @author Efy Shu
  * @Description 出租车我的订单功能Mapper
  * @date 2017年3月27日 14:45:27
  */ 
public interface TaxiOrderFuncMapper{
	public List<OpTaxiOrder> getTaxiOrderList(OrderListParam param);
	
	public int getTaxiOrderListTotalCount(OrderListParam param);
	
	public int updateTaxiOrder(OpTaxiOrder param);
	
	public OpTaxiOrder getOpTaxiOrder(OrderApiParam param);
	
	public int taxiTakingOrder(OpTaxiOrder param);
	
	public int taxiPayOrder(OpTaxiOrder param);

    /**
     * 获取今日预约订单
     * @param driverId ss
     * @return
     */
    List<OpTaxiOrder> taxiTodayYuyueOrder(String driverId);

    /**
     * 获取出租车乘客信息
     * @param userId u
     * @return
     */
    PeUser getPeUserInfoById(String userId);
}
