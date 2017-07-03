package com.szyciov.driver.dao;


import java.util.List;

import javax.annotation.Resource;
import com.szyciov.driver.mapper.TaxiOrderFuncMapper;
import com.szyciov.driver.param.OrderListParam;
import com.szyciov.op.entity.OpTaxiOrder;
import com.szyciov.op.entity.PeUser;
import com.szyciov.param.OrderApiParam;

import org.springframework.stereotype.Repository;



/**
  * @ClassName TaxiOrderFuncDao
  * @author Efy Shu
  * @Description 出租车我的订单功能Dao
  * @date 2017年3月27日 14:45:27
  */ 
@Repository("TaxiOrderFuncDao")
public class TaxiOrderFuncDao{

	/**
	  *依赖
	  */
	private TaxiOrderFuncMapper taxiorderfuncmapper;

	/**
	  *依赖注入
	  */
	@Resource
	public void setTaxiOrderFuncMapper(TaxiOrderFuncMapper taxiorderfuncmapper){
		this.taxiorderfuncmapper=taxiorderfuncmapper;
	}

	/**
	 * 获取出租车订单列表
	 * @param param
	 * @return
	 */
	public List<OpTaxiOrder> getTaxiOrderList(OrderListParam param){
		return taxiorderfuncmapper.getTaxiOrderList(param);
	}
	
	/**
	 * 获取出租车订单总数
	 * @param param
	 * @return
	 */
	public int getTaxiOrderListTotalCount(OrderListParam param){
		return taxiorderfuncmapper.getTaxiOrderListTotalCount(param);
	}
	
	/**
	 * 更新出租车订单信息
	 * @param param
	 * @return
	 */
	public boolean updateTaxiOrder(OpTaxiOrder param){
		taxiorderfuncmapper.updateTaxiOrder(param);
		return true;
	}
	
	/**
	 * 获取出租车运管端订单
	 * @param param
	 * @return
	 */
	public OpTaxiOrder getOpTaxiOrder(OrderApiParam param){
		return taxiorderfuncmapper.getOpTaxiOrder(param);
	}
	
	/**
	 * 获取出租车运管端订单
	 * @param param
	 * @return
	 */
	public OpTaxiOrder getOpTaxiOrder(String orderno){
		OrderApiParam param = new OrderApiParam();
		param.setOrderno(orderno);
		return taxiorderfuncmapper.getOpTaxiOrder(param);
	}
	
	/**
	 * 出租车运管端抢单
	 * @param param
	 * @return
	 */
	public boolean taxiTakingOrder(OpTaxiOrder param){
		taxiorderfuncmapper.taxiTakingOrder(param);
		return true;
	}
	
	/**
	 * 出租车运管端订单付结
	 * @param param
	 * @return
	 */
	public boolean taxiPayOrder(OpTaxiOrder param){
		taxiorderfuncmapper.taxiPayOrder(param);
		return true;
	}

    /**
     * 获取今日预约订单
     * @param driverId ss
     * @return
     */
    public List<OpTaxiOrder> taxiTodayYuyueOrder(String driverId){
        List<OpTaxiOrder> list = taxiorderfuncmapper.taxiTodayYuyueOrder(driverId);
        return list;
    }

    /**
     * 获取出租车乘客信息
     * @param userId u
     * @return
     */
    public PeUser getPeUserInfoById(String userId){
        PeUser p = this.taxiorderfuncmapper.getPeUserInfoById(userId);
        return p;
    }

}
