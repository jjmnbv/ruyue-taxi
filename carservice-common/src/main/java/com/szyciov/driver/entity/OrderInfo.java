package com.szyciov.driver.entity;

import java.util.Date;

public class OrderInfo{
	/**
	 * 订单号
	 */
	private String orderno;
	/**
	 * 订单类型(1-约车 2-接机 3-送机)
	 */
	private String type;

	/**
	 * 上车地址
	 */
	private String onaddress;
	
	/**
	 * 下车地址
	 */
	private String offaddress;
	
	/**
	 * 上车地址经度
	 */
	private double onlng;

	/**
	 * 上车地址纬度
	 */
	private double onlat;

	/**
	 * 下车地址经度
	 */
	private double offlng;

	/**
	 * 下车地址纬度
	 */
	private double offlat;
	
	/**
	 * 预估时长（分钟）
	 */
	private double estimatedtime;

	/**
	 * 预估里程
	 */
	private double estimatedmileage;

	/**
	 * 预估费用
	 */
	private double estimatedcost;
	
	/**
	 * 剩余时间
	 */
	private String lasttime;
	
	/**
	 * 出发时间
	 */
	private Date usetime;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 订单状态
	 */
	private String status;
	/**
	 * 订单属性(0-机构 1-个人)
	 */
	private int orderprop;

	/**
	 * 司机抢单时限
	 */
	private int grabtime;
	
	/**
	 * 是否即刻用车
	 */
	private boolean isusenow;
	
	/**
	 * 0-网约车，1-出租车
	 */
	private String orderstyle;
	
	/**
	 * 订单类型(约车、接机、送机、出租车)
	 */
	private String usetype;
	
	/**
	 * 打表来接里程
	 */
	private Integer meterrange;
	
	/**
	 * 行程备注
	 */
	private String tripremark;

    /**
     * 订单里程计算方式
     */
    private Integer costtype;

    /**
     * 订单开始经度
     */
    private Double startlng;

    /**
     * 订单开始纬度
     */
    private Double startllat;

    /**
     * 订单结束经度
     */
    private Double endlng;

    /**
     * 订单结束纬度
     */
    private Double endllat;

	/**
	 * 是否总是弹窗
	 * （true有弹窗也弹，false有弹窗不弹）
	 */
	private boolean alwayshowdialog;

	public String getOrderstyle() {
		return orderstyle;
	}

	public void setOrderstyle(String orderstyle) {
		this.orderstyle = orderstyle;
	}

	/**  
	 * 获取剩余时间  
	 * @return lasttime 剩余时间  
	 */
	public String getLasttime() {
		return lasttime;
	}

	/**  
	 * 设置剩余时间  
	 * @param lasttime 剩余时间  
	 */
	public void setLasttime(String lasttime) {
		this.lasttime = lasttime;
	}
	
	/**  
	 * 获取预估时长（分钟）  
	 * @return estimatedtime 预估时长（分钟）  
	 */
	public double getEstimatedtime() {
		return estimatedtime;
	}
	
	/**  
	 * 设置预估时长（分钟）  
	 * @param estimatedtime 预估时长（分钟）  
	 */
	public void setEstimatedtime(double estimatedtime) {
		this.estimatedtime = estimatedtime;
	}
	
	/**  
	 * 获取预估里程  
	 * @return estimatedmileage 预估里程  
	 */
	public double getEstimatedmileage() {
		return estimatedmileage;
	}
	
	/**  
	 * 设置预估里程  
	 * @param estimatedmileage 预估里程  
	 */
	public void setEstimatedmileage(double estimatedmileage) {
		this.estimatedmileage = estimatedmileage;
	}

	/**  
	 * 获取预估费用  
	 * @return estimatedcost 预估费用  
	 */
	public double getEstimatedcost() {
		return estimatedcost;
	}
	
	/**  
	 * 设置预估费用  
	 * @param estimatedcost 预估费用  
	 */
	public void setEstimatedcost(double estimatedcost) {
		this.estimatedcost = estimatedcost;
	}

	/**  
	 * 获取司机抢单时限  
	 * @return grabtime 司机抢单时限  
	 */
	public int getGrabtime() {
		return grabtime;
	}

	/**  
	 * 设置司机抢单时限  
	 * @param grabtime 司机抢单时限  
	 */
	public void setGrabtime(int grabtime) {
		this.grabtime = grabtime;
	}

	/**  
	 * 获取是否即刻用车  
	 * @return isusenow 是否即刻用车  
	 */
	public boolean isIsusenow() {
		return isusenow;
	}
	

	/**  
	 * 设置是否即刻用车  
	 * @param isusenow 是否即刻用车  
	 */
	public void setIsusenow(boolean isusenow) {
		this.isusenow = isusenow;
	}
	


	/**
	 * 获取订单类型(1-约车 2-接机 3-送机)
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置订单类型(1-约车 2-接机 3-送机)
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 获取用车时间
	 * @return
	 */
	public Date getUsetime() {
		return usetime;
	}

	/**
	 * 设置用车时间
	 * @param usetime
	 */
	public void setUsetime(Date usetime) {
		this.usetime = usetime;
	}

	/**
	 * 获取行程备注
	 * @return
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置行程备注
	 * @param remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 获取订单状态
	 * @return
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 设置订单状态
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**  
	 * 获取订单属性(0-机构1-个人)  
	 * @return orderprop 订单属性(0-机构1-个人)  
	 */
	public int getOrderprop() {
		return orderprop;
	}
	

	/**  
	 * 设置订单属性(0-机构1-个人)  
	 * @param orderprop 订单属性(0-机构1-个人)  
	 */
	public void setOrderprop(int orderprop) {
		this.orderprop = orderprop;
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
		this.orderno = orderno;
	}
	

	/**  
	 * 获取上车地址  
	 * @return onaddress 上车地址  
	 */
	public String getOnaddress() {
		return onaddress;
	}
	

	/**  
	 * 设置上车地址  
	 * @param onaddress 上车地址  
	 */
	public void setOnaddress(String onaddress) {
		this.onaddress = onaddress;
	}
	

	/**  
	 * 获取下车地址  
	 * @return offaddress 下车地址  
	 */
	public String getOffaddress() {
		return offaddress;
	}
	

	/**  
	 * 设置下车地址  
	 * @param offaddress 下车地址  
	 */
	public void setOffaddress(String offaddress) {
		this.offaddress = offaddress;
	}
	

	/**  
	 * 获取上车地址经度  
	 * @return onlng 上车地址经度  
	 */
	public double getOnlng() {
		return onlng;
	}
	

	/**  
	 * 设置上车地址经度  
	 * @param onlng 上车地址经度  
	 */
	public void setOnlng(double onlng) {
		this.onlng = onlng;
	}
	

	/**  
	 * 获取上车地址纬度  
	 * @return onlat 上车地址纬度  
	 */
	public double getOnlat() {
		return onlat;
	}
	

	/**  
	 * 设置上车地址纬度  
	 * @param onlat 上车地址纬度  
	 */
	public void setOnlat(double onlat) {
		this.onlat = onlat;
	}
	

	/**  
	 * 获取下车地址经度  
	 * @return offlng 下车地址经度  
	 */
	public double getOfflng() {
		return offlng;
	}
	

	/**  
	 * 设置下车地址经度  
	 * @param offlng 下车地址经度  
	 */
	public void setOfflng(double offlng) {
		this.offlng = offlng;
	}
	

	/**  
	 * 获取下车地址纬度  
	 * @return offlat 下车地址纬度  
	 */
	public double getOfflat() {
		return offlat;
	}
	

	/**  
	 * 设置下车地址纬度  
	 * @param offlat 下车地址纬度  
	 */
	public void setOfflat(double offlat) {
		this.offlat = offlat;
	}

	public String getUsetype() {
		return usetype;
	}

	public void setUsetype(String usetype) {
		this.usetype = usetype;
	}

	public Integer getMeterrange() {
		return meterrange;
	}

	public void setMeterrange(Integer meterrange) {
		this.meterrange = meterrange;
	}

	public String getTripremark() {
		return tripremark;
	}

	public void setTripremark(String tripremark) {
		this.tripremark = tripremark;
	}

    public Integer getCosttype() {
        return costtype;
    }

    public void setCosttype(Integer costtype) {
        this.costtype = costtype;
    }

    public boolean isusenow() {
        return isusenow;
    }

    public Double getStartlng() {
        return startlng;
    }

    public void setStartlng(Double startlng) {
        this.startlng = startlng;
    }

    public Double getStartllat() {
        return startllat;
    }

    public void setStartllat(Double startllat) {
        this.startllat = startllat;
    }

    public Double getEndlng() {
        return endlng;
    }

    public void setEndlng(Double endlng) {
        this.endlng = endlng;
    }

    public Double getEndllat() {
        return endllat;
    }

    public void setEndllat(Double endllat) {
        this.endllat = endllat;
    }

	public boolean isAlwayshowdialog() {
		return alwayshowdialog;
	}

	public void setAlwayshowdialog(boolean alwayshowdialog) {
		this.alwayshowdialog = alwayshowdialog;
	}
}
