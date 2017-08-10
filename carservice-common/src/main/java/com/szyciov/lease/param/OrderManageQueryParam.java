package com.szyciov.lease.param;

import java.util.Date;

public class OrderManageQueryParam extends LeBaseQueryParam{
	
	/**
	 * 订单分类(0 - 未接单，1 - 待人工派单， 2- 当前订单， 3 - 异常（待复核）订单， 4 - 异常（已复核）订单， 5 - 已完成订单， 6 - 待收款订单)
	 */
	private String type;

	private String orderType;
	
	private String userId;
	
	private String organId;
	
	private String startOrderTime;
	
	private String endOrderTime;
	
	private String orderNo;
	
	private String reviewPerson;
	
	private String selectedmodel;
	
	private String minUseTime;
	
	private String maxUseTime;
	
	private String cancelparty;
	
	private String reviewperson;
	
	/**
	 * 复核状态：0-复核中，1-复核完成
	 */
	private String reviewstatus;
	
	/**
	 * 司机数据是否分页: 1 - 分页
	 */
	private String driverPaging;
	
	private String driverState;
	
	private String driverName;
	
	private String orderOncityName;
	
	private String orderOffcityName;
	
	private int distance; 
	
	private double orderLat;
	
	private double orderLon;
	
	private double minLat;
	
	private double maxLat;
	
	private double minLon;
	
	private double maxLon;
	
	private String usetype;
	
	/**
	 * 排序方式
	 */
	private String orderby;
	
	/**
	 * 上车城市
	 */
	private String oncity;
	
	/**
	 * 车型派单规则
	 */
	private String models;
	
	/**
	 * 订单车型级别
	 */
	private String orderSelectedmodel;
	
	/**
	 * 运营端用户id
	 */
	private String opUserId;
	
	/**
	 * 司机id
	 */
	private String driverid;
	
	/**
	 * 下车城市
	 */
	private String offcity;
	
	private String isDriverState;
	
	/**
	 * 用户类型(0-普通员工，1-超级管理员)
	 */
	private String usertype;
	
	/**
	 * 下单来源(乘客端（因公：BC，因私：CJ，个人：CG）；机构端：BJ；租赁端（因公：BZ，因私：CZ）；运管端：CY)
	 */
	private String ordersource;
	
	/**
	 * 订单状态(0-待接单，1-待人工派单，2-待出发，3-已出发，4-已抵达，5-接到乘客，6-服务中，7-行程结束，8-已取消，9-待确费)
	 */
	private String orderstatus;
	
	/**
	 * 支付状态(0-未支付，1-已支付，2-结算中，3-已结算，4-未结算)
	 */
	private String paymentstatus;
	
	/**
	 * 支付方式(0-个人支付，1-个人垫付，2-机构支付)
	 */
	private String paymethod;
	
	/**
	 * 交易流水号
	 */
	private String tradeno;
	
	/**
	 * 付款方式(0-线上,1-线下)
	 */
	private String paymentmethod;
	
	/**
	 * 支付渠道(1-余额支付，2-微信支付，3-支付宝支付)
	 */
	private String paytype;
	
	/**
	 * 是否是即刻用车，1-是，0-不是
	 */
	private Integer isusenow;
	
	/**
	 * 订单预估结束时间
	 */
	private Date estimatedEndtime;
	
	/**
	 * 订单用车时间
	 */
	private Date usetime;
	
	/**
	 * 车辆id
	 */
	private String vehicleid;

    /**
     * 服务车企
     */
	private String belongleasecompany;

    /**
     * 账号对应服务车企权限
     */
    private String queryTmpBelongleasecompany;

    /**
     * 订单性质(0-自营单，1-联盟单)
     */
    private String ordernature;

    /**
     * 费用类型(1-行程服务,2-取消处罚)
     */
    private Integer expensetype;

    /**
     * 取消性质(1-有责,2-免责)
     */
    private Integer cancelnature;

    /**
     * 选择的司机
     */
    private String selectdriverid;

    public String getSelectdriverid() {
        return selectdriverid;
    }

    public void setSelectdriverid(String selectdriverid) {
        this.selectdriverid = selectdriverid;
    }

    public String getBelongleasecompany() {
        return belongleasecompany;
    }

    public void setBelongleasecompany(String belongleasecompany) {
        this.belongleasecompany = belongleasecompany;
    }

    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getStartOrderTime() {
		return startOrderTime;
	}

	public void setStartOrderTime(String startOrderTime) {
		this.startOrderTime = startOrderTime;
	}

	public String getEndOrderTime() {
		return endOrderTime;
	}

	public void setEndOrderTime(String endOrderTime) {
		this.endOrderTime = endOrderTime;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getReviewPerson() {
		return reviewPerson;
	}

	public void setReviewPerson(String reviewPerson) {
		this.reviewPerson = reviewPerson;
	}

	public String getSelectedmodel() {
		return selectedmodel;
	}

	public void setSelectedmodel(String selectedmodel) {
		this.selectedmodel = selectedmodel;
	}

	public String getDriverState() {
		return driverState;
	}

	public void setDriverState(String driverState) {
		this.driverState = driverState;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public double getMinLat() {
		return minLat;
	}

	public void setMinLat(double minLat) {
		this.minLat = minLat;
	}

	public double getMaxLat() {
		return maxLat;
	}

	public void setMaxLat(double maxLat) {
		this.maxLat = maxLat;
	}

	public double getMinLon() {
		return minLon;
	}

	public void setMinLon(double minLon) {
		this.minLon = minLon;
	}

	public double getMaxLon() {
		return maxLon;
	}

	public void setMaxLon(double maxLon) {
		this.maxLon = maxLon;
	}

	public double getOrderLat() {
		return orderLat;
	}

	public void setOrderLat(double orderLat) {
		this.orderLat = orderLat;
	}

	public double getOrderLon() {
		return orderLon;
	}

	public void setOrderLon(double orderLon) {
		this.orderLon = orderLon;
	}

	public String getOrderOncityName() {
		return orderOncityName;
	}

	public void setOrderOncityName(String orderOncityName) {
		this.orderOncityName = orderOncityName;
	}

	public String getOrderOffcityName() {
		return orderOffcityName;
	}

	public void setOrderOffcityName(String orderOffcityName) {
		this.orderOffcityName = orderOffcityName;
	}

	public String getDriverPaging() {
		return driverPaging;
	}

	public void setDriverPaging(String driverPaging) {
		this.driverPaging = driverPaging;
	}

	public String getMinUseTime() {
		return minUseTime;
	}

	public void setMinUseTime(String minUseTime) {
		this.minUseTime = minUseTime;
	}

	public String getMaxUseTime() {
		return maxUseTime;
	}

	public void setMaxUseTime(String maxUseTime) {
		this.maxUseTime = maxUseTime;
	}

	public String getCancelparty() {
		return cancelparty;
	}

	public void setCancelparty(String cancelparty) {
		this.cancelparty = cancelparty;
	}

	public String getReviewperson() {
		return reviewperson;
	}

	public void setReviewperson(String reviewperson) {
		this.reviewperson = reviewperson;
	}

	public String getReviewstatus() {
		return reviewstatus;
	}

	public void setReviewstatus(String reviewstatus) {
		this.reviewstatus = reviewstatus;
	}

	public String getUsetype() {
		return usetype;
	}

	public void setUsetype(String usetype) {
		this.usetype = usetype;
	}

	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}

	public String getOncity() {
		return oncity;
	}

	public void setOncity(String oncity) {
		this.oncity = oncity;
	}

	public String getModels() {
		return models;
	}

	public void setModels(String models) {
		this.models = models;
	}

	public String getOrderSelectedmodel() {
		return orderSelectedmodel;
	}

	public void setOrderSelectedmodel(String orderSelectedmodel) {
		this.orderSelectedmodel = orderSelectedmodel;
	}

	public String getOpUserId() {
		return opUserId;
	}

	public void setOpUserId(String opUserId) {
		this.opUserId = opUserId;
	}

	public String getDriverid() {
		return driverid;
	}

	public void setDriverid(String driverid) {
		this.driverid = driverid;
	}

	public String getOffcity() {
		return offcity;
	}

	public void setOffcity(String offcity) {
		this.offcity = offcity;
	}

	public String getIsDriverState() {
		return isDriverState;
	}

	public void setIsDriverState(String isDriverState) {
		this.isDriverState = isDriverState;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getOrdersource() {
		return ordersource;
	}

	public void setOrdersource(String ordersource) {
		this.ordersource = ordersource;
	}

	public String getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}

	public String getPaymentstatus() {
		return paymentstatus;
	}

	public void setPaymentstatus(String paymentstatus) {
		this.paymentstatus = paymentstatus;
	}

	public String getPaymethod() {
		return paymethod;
	}

	public void setPaymethod(String paymethod) {
		this.paymethod = paymethod;
	}

	public String getTradeno() {
		return tradeno;
	}

	public void setTradeno(String tradeno) {
		this.tradeno = tradeno;
	}

	public String getPaymentmethod() {
		return paymentmethod;
	}

	public void setPaymentmethod(String paymentmethod) {
		this.paymentmethod = paymentmethod;
	}

	public String getPaytype() {
		return paytype;
	}

	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}

	public Integer getIsusenow() {
		return isusenow;
	}

	public void setIsusenow(Integer isusenow) {
		this.isusenow = isusenow;
	}

	public Date getEstimatedEndtime() {
		return estimatedEndtime;
	}

	public void setEstimatedEndtime(Date estimatedEndtime) {
		this.estimatedEndtime = estimatedEndtime;
	}

	public Date getUsetime() {
		return usetime;
	}

	public void setUsetime(Date usetime) {
		this.usetime = usetime;
	}

	public String getVehicleid() {
		return vehicleid;
	}

	public void setVehicleid(String vehicleid) {
		this.vehicleid = vehicleid;
	}

    public String getQueryTmpBelongleasecompany() {
        return queryTmpBelongleasecompany;
    }

    public void setQueryTmpBelongleasecompany(String queryTmpBelongleasecompany) {
        this.queryTmpBelongleasecompany = queryTmpBelongleasecompany;
    }

    public String getOrdernature() {
        return ordernature;
    }

    public void setOrdernature(String ordernature) {
        this.ordernature = ordernature;
    }

    public Integer getExpensetype() {
        return expensetype;
    }

    public void setExpensetype(Integer expensetype) {
        this.expensetype = expensetype;
    }

    public Integer getCancelnature() {
        return cancelnature;
    }

    public void setCancelnature(Integer cancelnature) {
        this.cancelnature = cancelnature;
    }
}
