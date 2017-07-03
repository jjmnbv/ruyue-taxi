package com.szyciov.driver.param;

import com.szyciov.driver.enums.OrderState;
import com.szyciov.driver.enums.PayState;
import com.szyciov.entity.PayMethod;

/**
 * 订单统计参数类
 * @ClassName OrderStatisticsParam 
 * @author Efy Shu
 * @Description TODO(这里用一句话描述这个类的作用) 
 * @date 2016年9月22日 下午6:02:44
 */
public class OrderStatisticsParam extends BaseParam {
	/**
	 * 订单类型 0-机构订单  1-个人订单
	 */
	private int type;
	/**
	 * 统计类型 0-按月统计  1-按年统计
	 */
	private int statis;
	/**
	 * 司机ID
	 */
	private String driverid;
	
	/**
	 * 订单状态
	 */
	private String status;
	
	/**
	 * 支付方式(0-个人垫付 1-个人支付 2-机构支付)
	 */
	private String paymethod = PayMethod.ORGAN.code;
	
	/**
	 * 订单支付状态列表
	 */
	private String paystatuslist;

    /**
     * 订单状态集合
     *      -- 我的贡献
     */
    private String orderstatuslist;

    public String getOrderstatuslist() {
        StringBuffer sb = new StringBuffer();
        sb.append("'");
        //我的贡献 查询所需要的状态
        sb.append(OrderState.SERVICEDONE.state);             // 行程结束
//        sb.append(OrderState.CANCEL.state).append("'"); // 订单取消
        sb.append("'");
        orderstatuslist = sb.toString();
        return orderstatuslist;
    }

    public String getPaystatuslist() {
		StringBuffer sb = new StringBuffer();
		sb.append("'");
		//已完成
        sb.append(PayState.PAYED.state).append("','");          // 已支付 线上付款
        sb.append(PayState.MENTED.state).append("','");      // 未结算(目前没有结算环节,兼容已有流程)
        sb.append(PayState.STATEMENTED.state);                 // 已结算
        sb.append("'");
		paystatuslist = sb.toString();
		return paystatuslist;
	}
	
	/**  
	 * 获取支付方式(0-个人垫付1-个人支付2-机构支付)  
	 * @return paymethod 支付方式(0-个人垫付1-个人支付2-机构支付)  
	 */
	public String getPaymethod() {
		return paymethod;
	}

	/**  
	 * 设置支付方式(0-个人垫付1-个人支付2-机构支付)  
	 * @param paymethod 支付方式(0-个人垫付1-个人支付2-机构支付)  
	 */
	public void setPaymethod(String paymethod) {
		this.paymethod = paymethod;
	}

	/**  
	 * 获取订单状态  
	 * @return status 订单状态  
	 */
	public String getStatus() {
		return status;
	}
	

	/**  
	 * 设置订单状态  
	 * @param status 订单状态  
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	

	/**  
	 * 获取司机ID  
	 * @return driverid 司机ID  
	 */
	public String getDriverid() {
		return driverid;
	}

	/**  
	 * 设置司机ID  
	 * @param driverid 司机ID  
	 */
	public void setDriverid(String driverid) {
		this.driverid = driverid;
	}

	/**  
	 * 获取订单类型0-机构订单1-个人订单  
	 * @return type 订单类型0-机构订单1-个人订单  
	 */
	public int getType() {
		return type;
	}
	
	/**  
	 * 设置订单类型0-机构订单1-个人订单  
	 * @param type 订单类型0-机构订单1-个人订单  
	 */
	public void setType(int type) {
		this.type = type;
	}
	
	/**  
	 * 获取统计类型0-按月统计1-按年统计  
	 * @return statis 统计类型0-按月统计1-按年统计  
	 */
	public int getStatis() {
		return statis;
	}
	
	/**  
	 * 设置统计类型0-按月统计1-按年统计  
	 * @param statis 统计类型0-按月统计1-按年统计  
	 */
	public void setStatis(int statis) {
		this.statis = statis;
	}
}
