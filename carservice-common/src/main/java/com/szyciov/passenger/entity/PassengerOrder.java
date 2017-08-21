package com.szyciov.passenger.entity;

import com.szyciov.entity.AbstractOrder;

import net.sf.json.JSONObject;

import java.util.Map;

public class PassengerOrder extends AbstractOrder {
	/**
	 * 用车事由类型
	 */
	private String vehiclessubjecttype;
	
	/**
	 * 用车事由
	 */
	private String vehiclessubject;
	
	/**
	 * 结算方式
	 */
	private String paymethod;
	
	/**
	 * 机构id
	 */
	private String organid;
	
	/**
	 * 订单状态显示值
	 */
	private String orderstatuscaption;
	
	/**
	 * 所选车型的显示值
	 */
	private String selectedmodelcaption;
	
	/**
	 * 是否评论
	 */
	private boolean hasComment;
	
	/**
	 * 订单相关信息
	 */
	private String startprice;
	
	private String rangeprice;
	
	private String timeprice;
	
	private String rangecost;
	
	private String timecost;
	
	private String times;
	
	private String timetype;
	
	private String mileagestr;
	
	/**
	 * 客服电话
	 */
	private String servicephone;
	
	/**
	 * 取消人
	 */
	private String cancelname;
	
	/**
	 * 最大派单时限
	 */
	private int sendinterval;
	
	/**
	 * 车型的logo
	 */
	private String selectedmodellogo;
	
	/**
	 * 距离出发时间
	 */
	private String remaintime;
	
	private Map<String,Object> driverposition;
	
	/**
	 * 0-网约车，1-出租车
	 */
	private String orderstyle;
	
	private JSONObject costinfo;
	
	/**
	 * 取消费
	 */
	private double cancelfee;
	
	/**
	 * 是否有取消规则
	 */
	private boolean hascancelrule;

    /**
     * "0"表示机构订单，"1"表示个人订单
     */
    private String type;

	public String getVehiclessubjecttype() {
		return vehiclessubjecttype;
	}

	public void setVehiclessubjecttype(String vehiclessubjecttype) {
		this.vehiclessubjecttype = vehiclessubjecttype;
	}

	public String getVehiclessubject() {
		return vehiclessubject;
	}

	public void setVehiclessubject(String vehiclessubject) {
		this.vehiclessubject = vehiclessubject;
	}

	public String getPaymethod() {
		return paymethod;
	}

	public void setPaymethod(String paymethod) {
		this.paymethod = paymethod;
	}

	public String getOrganid() {
		return organid;
	}

	public void setOrganid(String organid) {
		this.organid = organid;
	}

	public String getOrderstatuscaption() {
		return orderstatuscaption;
	}

	public void setOrderstatuscaption(String orderstatuscaption) {
		this.orderstatuscaption = orderstatuscaption;
	}

	public String getSelectedmodelcaption() {
		return selectedmodelcaption;
	}

	public void setSelectedmodelcaption(String selectedmodelcaption) {
		this.selectedmodelcaption = selectedmodelcaption;
	}

	public boolean isHasComment() {
		return hasComment;
	}

	public void setHasComment(boolean hasComment) {
		this.hasComment = hasComment;
	}

	public String getStartprice() {
		return startprice;
	}

	public void setStartprice(String startprice) {
		this.startprice = startprice;
	}

	public String getRangeprice() {
		return rangeprice;
	}

	public void setRangeprice(String rangeprice) {
		this.rangeprice = rangeprice;
	}

	public String getTimeprice() {
		return timeprice;
	}

	public void setTimeprice(String timeprice) {
		this.timeprice = timeprice;
	}

	public String getRangecost() {
		return rangecost;
	}

	public void setRangecost(String rangecost) {
		this.rangecost = rangecost;
	}

	public String getTimecost() {
		return timecost;
	}

	public void setTimecost(String timecost) {
		this.timecost = timecost;
	}

	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}

	public String getServicephone() {
		return servicephone;
	}

	public void setServicephone(String servicephone) {
		this.servicephone = servicephone;
	}

	public String getCancelname() {
		return cancelname;
	}

	public void setCancelname(String cancelname) {
		this.cancelname = cancelname;
	}

	public int getSendinterval() {
		return sendinterval;
	}

	public void setSendinterval(int sendinterval) {
		this.sendinterval = sendinterval;
	}

	public String getSelectedmodellogo() {
		return selectedmodellogo;
	}

	public void setSelectedmodellogo(String selectedmodellogo) {
		this.selectedmodellogo = selectedmodellogo;
	}

	public Map<String, Object> getDriverposition() {
		return driverposition;
	}

	public void setDriverposition(Map<String, Object> driverposition) {
		this.driverposition = driverposition;
	}

	public String getTimetype() {
		return timetype;
	}

	public void setTimetype(String timetype) {
		this.timetype = timetype;
	}

	public String getMileagestr() {
		return mileagestr;
	}

	public void setMileagestr(String mileagestr) {
		this.mileagestr = mileagestr;
	}

	public String getRemaintime() {
		return remaintime;
	}

	public void setRemaintime(String remaintime) {
		this.remaintime = remaintime;
	}

	public String getOrderstyle() {
		return orderstyle;
	}

	public void setOrderstyle(String orderstyle) {
		this.orderstyle = orderstyle;
	}

	public JSONObject getCostinfo() {
		return costinfo;
	}

	public void setCostinfo(JSONObject costinfo) {
		this.costinfo = costinfo;
	}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

	public double getCancelfee() {
		return cancelfee;
	}

	public void setCancelfee(double cancelfee) {
		this.cancelfee = cancelfee;
	}

	public boolean isHascancelrule() {
		return hascancelrule;
	}

	public void setHascancelrule(boolean hascancelrule) {
		this.hascancelrule = hascancelrule;
	}
}
