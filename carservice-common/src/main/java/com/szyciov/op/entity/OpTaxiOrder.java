package com.szyciov.op.entity;

import com.szyciov.entity.AbstractOrder;

import java.util.Date;

public class OpTaxiOrder extends AbstractOrder {
	
	/**
	 * 调度费
	 */
	private Integer schedulefee;
	
	/**
	 * 打表来接里程数
	 */
	private Integer meterrange;
	
	/**
	 * 收款方式(0-线上,1-线下)
	 */
	private String paymentmethod;
	
	/**
	 * 收款时间
	 */
	private Date paymenttime;
	
	/**
	 * 结算时间
	 */
	private Date settlementtime;
	
	/**
	 * 订单排序字段(1-待接单,2-待出发,3-已出发,4-已抵达,5-服务中,6-待确费,7-未支付,8-已支付,9-未结算,10-结算中,11-已结算,12-未付结,13-已付结,14-已取消)
	 */
	private Integer ordersortcolumn;
	
	/**
	 * 下单人头像(小)
	 */
	private String passengericonmin	;
	
	/**
	 * 下单人头像(大)
	 */
	private String passengericonmax;

	/**
	 * 客服电话
	 */
	private String contact;
	/**
	 * 开始服务地址城市
	 */
	private String startcity;
	/**
	 * 开始服务地址
	 */
	private String startaddress;
	/**
	 * 开始服务地址经度
	 */
//	private String startlng;
	/**
	 * 开始服务地址纬度
	 */
//	private String startllat;
	/**
	 * 结束服务地址城市
	 */
	private String endcity;
	/**
	 * 结束服务地址
	 */
	private String endaddress;
	/**
	 * 结束服务地址经度
	 */
//	private String endlng;
	/**
	 * 结束服务地址纬度
	 */
//	private String endllat;

    private String manualDriver; // 手动选择司机 0: 否    1:是

    /**
     * "0"表示机构订单，"1"表示个人订单
     */
    private String type;

    public String getManualDriver() {
        return manualDriver;
    }

    public void setManualDriver(String manualDriver) {
        this.manualDriver = manualDriver;
    }

    public Integer getSchedulefee() {
		return schedulefee == null ? 0 : schedulefee;
	}

	public void setSchedulefee(Integer schedulefee) {
		this.schedulefee = schedulefee;
	}

	public Integer getMeterrange() {
		return meterrange == null ? 0 : meterrange;
	}

	public void setMeterrange(Integer meterrange) {
		this.meterrange = meterrange;
	}

	public String getPaymentmethod() {
		return paymentmethod;
	}

	public void setPaymentmethod(String paymentmethod) {
		this.paymentmethod = paymentmethod;
	}

	public Date getPaymenttime() {
		return paymenttime;
	}

	public void setPaymenttime(Date paymenttime) {
		this.paymenttime = paymenttime;
	}

	public Date getSettlementtime() {
		return settlementtime;
	}

	public void setSettlementtime(Date settlementtime) {
		this.settlementtime = settlementtime;
	}

	public Integer getOrdersortcolumn() {
		return ordersortcolumn;
	}

	public void setOrdersortcolumn(Integer ordersortcolumn) {
		this.ordersortcolumn = ordersortcolumn;
	}

	/**  
	 * 获取下单人头像(小)  
	 * @return passengericonmin 下单人头像(小)  
	 */
	public String getPassengericonmin() {
		return passengericonmin;
	}

	/**  
	 * 设置下单人头像(小)  
	 * @param passengericonmin 下单人头像(小)  
	 */
	public void setPassengericonmin(String passengericonmin) {
		this.passengericonmin = passengericonmin;
	}
	
	/**  
	 * 获取下单人头像(大)  
	 * @return passengericonmax 下单人头像(大)  
	 */
	public String getPassengericonmax() {
		return passengericonmax;
	}
	
	/**  
	 * 设置下单人头像(大)  
	 * @param passengericonmax 下单人头像(大)  
	 */
	public void setPassengericonmax(String passengericonmax) {
		this.passengericonmax = passengericonmax;
	}

	/**  
	 * 获取客服电话  
	 * @return contact 客服电话  
	 */
	public String getContact() {
		return contact;
	}
	
	/**  
	 * 设置客服电话  
	 * @param contact 客服电话  
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getStartcity() {
		return startcity;
	}

	public void setStartcity(String startcity) {
		this.startcity = startcity;
	}

	public String getStartaddress() {
		return startaddress;
	}

	public void setStartaddress(String startaddress) {
		this.startaddress = startaddress;
	}

	public String getEndcity() {
		return endcity;
	}

	public void setEndcity(String endcity) {
		this.endcity = endcity;
	}

	public String getEndaddress() {
		return endaddress;
	}

	public void setEndaddress(String endaddress) {
		this.endaddress = endaddress;
	}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
