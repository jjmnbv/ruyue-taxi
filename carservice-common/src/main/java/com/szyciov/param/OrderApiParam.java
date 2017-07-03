package com.szyciov.param;

import com.szyciov.annotation.SzycValid;
import com.szyciov.driver.param.BaseParam;

/**
 * @ClassName OrderApiParam 
 * @author Efy Shu
 * @Description 订单请求基础参数类
 * @date 2016年9月21日 下午8:44:06
 */
public class OrderApiParam extends BaseParam{
	/**
	 * 订单号  (数据库用这个)
	 */
	@SzycValid(rules={"checkNull","checkOrderNO"})
	private String orderno;
	
	/**
	 * 订单号 (冗余orderno字段,校验条件不同)
	 */
	@SzycValid(rules={"checkNull","checkOrder4Taking"})
	private String orderid;
	/**
	 * 司机ID(订单需保证是改司机的)
	 */
	private String driverid;
	/**
	 * 订单状态
	 * @see OrderState
	 */
	@SzycValid(rules={"checkNull","checkOrderStatus"})
	private String orderstate;
	/**
	 * 订单状态(与orderstate共用,冗余文档字段)
	 * @see {@link com.szyciov.driver.enums.OrderState OrderState}
	 */
	@SzycValid(rules={"checkNull","checkOrderStatus"})
	private String orderstatus;
	
	/**
	 * 请求来源(参考CancelParty枚举类)
	 * @see CancelParty
	 */
	private String reqsrc;
	
	/**
	 * 下单来源(参考OrderSource枚举类)
	 * @see {@linkplain OrderSource}
	 */
	private String ordersource;
	
	/**
	 * 订单行程费(用于订单确费)
	 */
	@SzycValid(rules={"checkAmount"})
	private double rangecost;
	
	/**
	 * 订单支付方式(0-线下 1-线上)
	 */
	@SzycValid(rules={"checkNull","checkOrderPayMethod"})
	private String paymethod;
	/**
	 * 订单属性(0-机构 1-个人 2-个人出租车)
	 */
	private int orderprop;
	
	/**
	 * 用车类型
	 */
	private String usetype;
	
	/**
	 * 订单类型
	 */
	private String ordertype;

	/**
	 * 是否提醒 true - 继续提醒 false - 不再提醒
	 */
	private boolean remind = true;
	
	/**
	 * 检查是否有更早的未出行订单(参数检测用,无实际值)
	 */
	@SzycValid(rules={"checkHasEarlierOrder"})
	private boolean hasearlierorder = false;
	/**
	 * 检查是否有未付结订单(参数检测用,无实际值)
	 */
	@SzycValid(rules={"checkNotPayOrder"})
	private boolean hasnotpayorder = false;
	/**
	 * 检查未付结订单是否超限(金额>=200或数量>=10)
	 */
	@SzycValid(rules={"checkNotPayOrderLimit"})
	private boolean notpayorderlimit = false;
	/**
	 * 检查司机是否能抢该订单 
	 */
	@SzycValid(rules={"checkDriverCanTakeOrder"})
	private boolean cantakesign = false;

    /**
     * 里程计算是否保存日志
     */
    private Boolean isLog;
	
	/**  
	 * 获取是否提醒true-继续提醒false-不再提醒  
	 * @return remind 是否提醒true-继续提醒false-不再提醒  
	 */
	public boolean isRemind() {
		return remind;
	}

	/**  
	 * 设置是否提醒true-继续提醒false-不再提醒  
	 * @param remind 是否提醒true-继续提醒false-不再提醒  
	 */
	public void setRemind(boolean remind) {
		this.remind = remind;
	}
	

	/**  
	 * 获取请求来源(参考CancelParty枚举类)  
	 * @return reqsrc 请求来源(参考CancelParty枚举类)  
	 */
	public String getReqsrc() {
		return reqsrc;
	}

	/**  
	 * 设置请求来源(参考CancelParty枚举类)  
	 * @param reqsrc 请求来源(参考CancelParty枚举类)  
	 */
	public void setReqsrc(String reqsrc) {
		this.reqsrc = reqsrc;
	}

	/**  
	 * 获取订单号(数据库用这个)  
	 * @return orderno 订单号(数据库用这个)  
	 */
	public String getOrderno() {
		return orderno;
	}

	/**  
	 * 设置订单号(数据库用这个)  
	 * @param orderno 订单号(数据库用这个)  
	 */
	public void setOrderno(String orderno) {
		this.orderid = orderno;
		this.orderno = orderno;
	}

	/**  
	 * 获取订单号(司机端用这个)  
	 * @return orderid 订单号(司机端用这个)  
	 */
	public String getOrderid() {
		return orderid;
	}
	
	/**  
	 * 设置订单号(司机端用这个)  
	 * @param orderid 订单号(司机端用这个)  
	 */
	public void setOrderid(String orderid) {
		this.orderid = orderid;
		this.orderno = orderid;
	}

	/**  
	 * 获取订单状态  
	 * @return orderstate 订单状态  
	 */
	public String getOrderstate() {
		return orderstate;
	}
	
	/**  
	 * 设置订单状态  
	 * @param orderstate 订单状态  
	 */
	public void setOrderstate(String orderstate) {
		this.orderstate = orderstate;
		this.orderstatus = orderstate;
	}

	/**  
	 * 获取订单属性(0-机构1-个人)  
	 * @return orderprop 订单属性(0-机构1-个人)  
	 */
	public int getOrderprop() {
		return orderprop;
	}

	/**  
	 * 设置订单属性(0-机构1-个人2-个人出租车)  
	 * @param orderprop 订单属性(0-机构1-个人2-个人出租车)  
	 */
	public void setOrderprop(int orderprop) {
		this.orderprop = orderprop;
	}

	/**  
	 * 获取下单来源(参考OrderSource枚举类)@see{@linkplainOrderSource}  
	 * @return ordersource 下单来源(参考OrderSource枚举类)@see{@linkplainOrderSource}  
	 */
	public String getOrdersource() {
		return ordersource;
	}

	/**  
	 * 设置下单来源(参考OrderSource枚举类)@see{@linkplainOrderSource}  
	 * @param ordersource 下单来源(参考OrderSource枚举类)@see{@linkplainOrderSource}  
	 */
	public void setOrdersource(String ordersource) {
		this.ordersource = ordersource;
	}

	/**  
	 * 获取订单行程费(用于订单确费)  
	 * @return rangecost 订单行程费(用于订单确费)  
	 */
	public double getRangecost() {
		return rangecost;
	}

	/**  
	 * 设置订单行程费(用于订单确费)  
	 * @param rangecost 订单行程费(用于订单确费)  
	 */
	public void setRangecost(double rangecost) {
		this.rangecost = rangecost;
	}

	/**  
	 * 获取订单支付方式(0-线上1-线下)  
	 * @return paymethod 订单支付方式(0-线上1-线下)  
	 */
	public String getPaymethod() {
		return paymethod;
	}

	/**  
	 * 设置订单支付方式(0-线上 1-线下)  
	 * @param paymethod 订单支付方式(0-线上 1-线下)  
	 */
	public void setPaymethod(String paymethod) {
		this.paymethod = paymethod;
	}

	/**  
	 * 获取司机ID(订单需保证是改司机的)  
	 * @return driverid 司机ID(订单需保证是改司机的)  
	 */
	public String getDriverid() {
		return driverid;
	}

	/**  
	 * 设置司机ID(订单需保证是改司机的)  
	 * @param driverid 司机ID(订单需保证是改司机的)  
	 */
	public void setDriverid(String driverid) {
		this.driverid = driverid;
	}

	/**  
	 * 获取订单状态(与orderstate共用冗余文档字段)@see{@linkcom.szyciov.driver.enums.OrderStateOrderState}  
	 * @return orderstatus 订单状态(与orderstate共用冗余文档字段)@see{@linkcom.szyciov.driver.enums.OrderStateOrderState}  
	 */
	public String getOrderstatus() {
		return orderstatus;
	}

	/**  
	 * 设置订单状态(与orderstate共用冗余文档字段)@see{@linkcom.szyciov.driver.enums.OrderStateOrderState}  
	 * @param orderstatus 订单状态(与orderstate共用冗余文档字段)@see{@linkcom.szyciov.driver.enums.OrderStateOrderState}  
	 */
	public void setOrderstatus(String orderstatus) {
		this.orderstate = orderstatus;
		this.orderstatus = orderstatus;
	}

	public String getUsetype() {
		return usetype;
	}

	public void setUsetype(String usetype) {
		this.usetype = usetype;
	}

	public String getOrdertype() {
		return ordertype;
	}

	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}

    public Boolean getLog() {
        return isLog;
    }

    public void setLog(Boolean log) {
        isLog = log;
    }
}
