/**
 * 
 */
package com.szyciov.driver.param;

import com.szyciov.annotation.SzycValid;
import com.szyciov.driver.enums.OrderState;
import com.szyciov.driver.enums.PayState;
import com.szyciov.driver.enums.PurseEnum;

/**
 * @ClassName PurseParam 
 * @author Efy Shu
 * @Description 司机端钱包接口参数类
 * @date 2017年3月25日 下午5:37:18 
 */
public class PurseParam extends BaseParam {
	/**
	 * 兼容一期订单号字段
	 */
	@SuppressWarnings("unused")
	private String orderid;
	/**
	 * 订单号
	 */
	@SzycValid(rules={"checkNull","checkOrderNO"})
	private String orderno;
	/**
	 * 获取支付渠道时用(0-充值 1-订单付款)
	 */
	@SzycValid(rules={"checkNull","checkChannelType"})
	private String type;
	/**
	 * 行程费(与amount共用,冗余文档字段)
	 */
	private double rangecost;
	/**
	 * 持卡人姓名
	 */
	@SzycValid(rules={"checkNull","checkCardPerson"})
	private String name;
	/**
	 * 银行卡号
	 */
	@SzycValid(rules={"checkNull","isBankNO"})
	private String cardno;
	/**
	 * 充值|提现金额
	 */
	@SzycValid(rules="checkAmount")
	private double amount;
	/**
	 * 提现金额(冗余amount字段,校验用)
	 */
	@SzycValid(rules={"checkAmount","checkBalanceEnough","checkAmount4WithDraw"})
	private double wdamount;
	/**
	 * 充值/提现方式(1-微信  2-支付宝 3-余额 )
	 */
	@SzycValid(rules={"checkNull","checkPayMethod"})
	private String paymethod;
	/**
	 * 支付状态
	 */
	@SzycValid(rules={"checkNull","checkPayState"})
	private String paystate = PayState.PAYOVER.state;
	/**
	 * 提现密码
	 */
	@SzycValid(rules={"checkNull","checkWithDrawPwd"})
	private String password;
	/**
	 * 统计类型(0-按月统计 1-按年统计)
	 */
	@SzycValid(rules="checkStatis")
	private int statis;
	/**
	 * 明细类型(0-余额明细 1-交易明细)
	 */
	private String detailtype;
	/**
	 * 操作结果(0-成功 1-失败)
	 */
	private String operateresult = PurseEnum.OPERATERESULT_SUCCESS.code;
	/**
	 * 司机ID
	 */

	private String driverid;
	/**
	 * 订单状态(我的贡献,只查订单状态为X的)
	 */
	private String orderstatus = OrderState.SERVICEDONE.state;
	/**
	 * 检查未付结订单是否超限(金额>=200或数量>=10)
	 */
	@SzycValid(rules={"checkNotPayOrderLimit"})
	private boolean notpayorderlimit = false;
    /**
     * 检查是否有未付结订单(参数检测用,无实际值)
     */
    @SzycValid(rules={"checkNotPayOrder"})
    private boolean hasnotpayorder = false;
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
        sb.append(OrderState.SERVICEDONE.state).append("'"); // 行程结束
//        sb.append(OrderState.CANCEL.state).append("'"); // 订单取消
        orderstatuslist = sb.toString();
        return orderstatuslist;
    }

    public String getPaystatuslist() {
		StringBuffer sb = new StringBuffer();
		sb.append("'");
		//已完成
        sb.append(PayState.NOTPAY.state).append("','"); // 未支付 线上付款
        sb.append(PayState.PAYED.state).append("','"); // 已支付 线上付款
        sb.append(PayState.STATEMENTED.state).append("','"); // 已结算
        sb.append(PayState.PASSENGERNOPAY.state).append("','"); // 未付结
        sb.append(PayState.PAYOVER.state).append("'"); // 已付结
		paystatuslist = sb.toString();
		return paystatuslist;
	}

	/**
	 * 获取持卡人姓名  
	 * @return name 持卡人姓名  
	 */
	public String getName() {
		return name;
	}
	
	/**  
	 * 设置持卡人姓名  
	 * @param name 持卡人姓名  
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**  
	 * 获取银行卡号  
	 * @return cardno 银行卡号  
	 */
	public String getCardno() {
		return cardno;
	}
	
	/**  
	 * 设置银行卡号  
	 * @param cardno 银行卡号  
	 */
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	
	/**  
	 * 获取充值金额  
	 * @return amount 充值金额  
	 */
	public double getAmount() {
		return amount;
	}
	
	/**  
	 * 设置充值金额  
	 * @param amount 充值金额  
	 */
	public void setAmount(double amount) {
		this.amount = amount;
		this.wdamount = amount;
	}
	
	/**  
	 * 获取充值/提现方式(1-微信 2-支付宝 3-余额)  
	 * @return paymethod 充值/提现方式(1-微信 2-支付宝 3-余额)  
	 */
	public String getPaymethod() {
		return paymethod;
	}
	
	/**  
	 * 设置充值/提现方式(1-微信 2-支付宝 3-余额)  
	 * @param paymethod 充值/提现方式(1-微信 2-支付宝 3-余额)  
	 */
	public void setPaymethod(String paymethod) {
		this.paymethod = paymethod;
	}
	
	/**  
	 * 获取提现密码  
	 * @return password 提现密码  
	 */
	public String getPassword() {
		return password;
	}
	
	/**  
	 * 设置提现密码  
	 * @param password 提现密码  
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**  
	 * 获取明细类型()  
	 * @return detailtype 明细类型()  
	 * @see {@linkplain PurseEnum}
	 */
	public String getDetailtype() {
		return detailtype;
	}

	/**  
	 * 设置明细类型()  
	 * @param detailtype 明细类型()  
	 * @see {@linkplain PurseEnum}
	 */
	public void setDetailtype(String detailtype) {
		this.detailtype = detailtype;
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
	 * 获取操作结果(0-成功1-失败)  
	 * @return operateresult 操作结果(0-成功1-失败)  
	 */
	public String getOperateresult() {
		return operateresult;
	}
	
	/**  
	 * 设置操作结果(0-成功1-失败)  
	 * @param operateresult 操作结果(0-成功1-失败)  
	 */
	public void setOperateresult(String operateresult) {
		this.operateresult = operateresult;
	}

	/**  
	 * 获取订单状态(我的贡献只查订单状态为X的)  
	 * @return orderstatus 订单状态(我的贡献只查订单状态为X的)  
	 */
	public String getOrderstatus() {
		return orderstatus;
	}
	
	/**  
	 * 设置订单状态(我的贡献只查订单状态为X的)  
	 * @param orderstatus 订单状态(我的贡献只查订单状态为X的)  
	 */
	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}

	/**  
	 * 获取统计类型(0-按月统计1-按年统计)  
	 * @return statis 统计类型(0-按月统计1-按年统计)  
	 */
	public int getStatis() {
		return statis;
	}

	/**  
	 * 设置统计类型(0-按月统计1-按年统计)  
	 * @param statis 统计类型(0-按月统计1-按年统计)  
	 */
	public void setStatis(int statis) {
		this.statis = statis;
	}

	/**  
	 * 获取订单号  
	 * @return orderno 订单号  
	 */
	public String getOrderno() {
		return orderno;
	}

	/**  
	 * 设置订单号  
	 * @param orderno 订单号  
	 */
	public void setOrderno(String orderno) {
		this.orderid = orderno;
		this.orderno = orderno;
	}

	/**  
	 * 设置兼容一期订单号字段  
	 * @param orderid 兼容一期订单号字段  
	 */
	public void setOrderid(String orderid) {
		this.orderid = orderid;
		this.orderno = orderid;
	}

	/**  
	 * 获取行程费(与amount共用冗余文档字段)  
	 * @return rangecost 行程费(与amount共用冗余文档字段)  
	 */
	public double getRangecost() {
		return rangecost;
	}
	
	/**  
	 * 设置行程费(与amount共用冗余文档字段)  
	 * @param rangecost 行程费(与amount共用冗余文档字段)  
	 */
	public void setRangecost(double rangecost) {
		this.amount = rangecost;
		this.rangecost = rangecost;
	}

	/**  
	 * 获取提现金额(冗余amount字段校验用)  
	 * @return wdamount 提现金额(冗余amount字段校验用)  
	 */
	public double getWdamount() {
		return wdamount;
	}

	/**  
	 * 设置提现金额(冗余amount字段校验用)  
	 * @param wdamount 提现金额(冗余amount字段校验用)  
	 */
	public void setWdamount(double wdamount) {
		this.amount = wdamount;
		this.wdamount = wdamount;
	}

	/**  
	 * 获取支付状态  
	 * @return paystate 支付状态  
	 */
	public String getPaystate() {
		return paystate;
	}

	/**  
	 * 设置支付状态  
	 * @param paystate 支付状态  
	 */
	public void setPaystate(String paystate) {
		this.paystate = paystate;
	}

	/**  
	 * 获取获取支付渠道时用(0-充值1-订单付款)  
	 * @return type 获取支付渠道时用(0-充值1-订单付款)  
	 */
	public String getType() {
		return type;
	}
	
	/**  
	 * 设置获取支付渠道时用(0-充值1-订单付款)  
	 * @param type 获取支付渠道时用(0-充值1-订单付款)  
	 */
	public void setType(String type) {
		this.type = type;
	}
	
}
