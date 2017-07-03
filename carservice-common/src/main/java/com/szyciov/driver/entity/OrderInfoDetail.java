package com.szyciov.driver.entity;

import java.util.Date;

import com.szyciov.entity.OrderCost;
import com.szyciov.util.StringUtil;

/**
 * @ClassName OrderInfoDetail
 * @author Efy
 * @Description 订单详情
 * @date 2016年9月21日 11:46:21
 */
public class OrderInfoDetail extends OrderInfo {
	
	/**
	 * 机构ID(如果是机构则存在机构ID)
	 */
	private String organid;
	
	/**
	 * 下单来源(个人订单该值范围:0-乘客 1-运管  机构订单该值范围:0-乘客 1-租赁 2-机构)
	 */
	private String ordersource;
	
	/**
	 * 下单时间
	 */
	private Date undertime;

	/**
	 * 接单时间
	 */
	private Date ordertime;

	/**
	 * 司机出发时间
	 */
	private Date departuretime;

	/**
	 * 司机抵达时间
	 */
	private Date arrivaltime;

	/**
	 * 服务开始时间
	 */
	private Date starttime;

	/**
	 * 服务结束时间
	 */
	private Date endtime;

	/**
	 * 订单完结时间
	 */
	private Date completetime;

	/**
	 * 订单取消时间
	 */
	private Date canceltime;
	/**
	 * 客服电话
	 */
	private String contact;
	/**
	 * 下单人
	 */
	private String username;
	/**
	 * 下单人电话
	 */
	private String userphone;
	/**
	 * 乘车人
	 */
	private String passengers;

	/**
	 * 乘车人电话
	 */
	private String passengerphone;
	/**
	 * 下单人头像(小)
	 */
	private String passengericonmin;
	/**
	 * 下单人头像(大)
	 */
	private String passengericonmax;

	/**
	 * 所选车型
	 */
	private String cartype;

	/**
	 * 所在城市
	 */
	private String city;

	/**
	 * 所属租赁公司
	 */
	private String companyid;
	
	/**
	 * 所在城市代码
	 */
	private String cityid;
	
	/**
	 * 实际时长(分钟)
	 */
	private int times;

	/**
	 * 实际里程(米)
	 */
	private double mileage;

	/**
	 * 实际金额
	 */
	private double orderamount;
	
	/**
	 * 里程费
	 */
	private double rangecost;
	
	/**
	 * 时长费
	 */
	private double timecost;

	/**
	 * 计价规则副本
	 */
	private String pricecopy;

	/**
	 * 起步价
	 */
	private double startprice;

	/**
	 * 里程单价
	 */
	private double rangeprice;

	/**
	 * 时间单价
	 */
	private double timeprice;

	/**
	 * 航班号
	 */
	private String fltno;

	/**
	 * 降落时间
	 */
	private Date falltime;

	/**
	 * 用户评分 包括：1-1星，2-2星，3-3星，4-4星，5-5星
	 */
	private String userrate;

	/**
	 * 用户评价
	 */
	private String usercomment;
	
	
	/**
	 * 司机ID
	 */
	private String driverid;

	/**
	 * 车辆ID
	 */
	private String vehicleid;

	/**
	 * 取消方
	 */
	private String cancelparty;
	
	/**
	 * 支付状态
	 */
	private String paystatus;
	
	/**
	 * 复议状态
	 */
	private String reviewstatus;
	
	/**
	 * 车型图片
	 */
	private String cartypelogo;

	/**
	 * 时间计费类型(0-总用时 1-低速用时)
	 */
	private String timetype;
	
	/**
	 * 订单结算方式 包括：0-个人支付，1-个人垫付，2-机构支付
	 */
	private String paymethod;
	
	/**
	 * 经度
	 */
	private double lng;
	/**
	 * 纬度
	 */
	private double lat;
	/**
	 * 当前时间节点所在城市
	 */
	private String cityintime;
	/**
	 * 当前时间节点所在地址
	 */
	private String addressintime;
	
	/**
	 * 排序字段
	 */
	private Integer ordersortcolumn;
	
	/**
	 * 获取起步价
	 * @return
	 */
	public double getStartprice() {
		if (startprice == 0.0 && pricecopy != null) {
			OrderCost oc = StringUtil.parseJSONToBean(pricecopy, OrderCost.class);
			startprice = oc.getStartprice();
		}
		return startprice;
	}

	/**
	 * 获取里程价
	 * @return
	 */
	public double getRangeprice() {
		if (rangeprice == 0.0 && pricecopy != null) {
			OrderCost oc = StringUtil.parseJSONToBean(pricecopy, OrderCost.class);
			rangeprice = oc.getRangeprice();
		}
		return rangeprice;
	}

	/**
	 * 获取时长价
	 * @return
	 */
	public double getTimeprice() {
		if (timeprice == 0.0 && pricecopy != null) {
			OrderCost oc = StringUtil.parseJSONToBean(pricecopy, OrderCost.class);
			timeprice = oc.getTimeprice();
		}
		return timeprice;
	}
	

	/**  
	 * 获取时间计费类型(0-总用时1-低速用时)  
	 * @return timetype 时间计费类型(0-总用时1-低速用时)  
	 */
	public String getTimetype() {
		if (null == timetype || "".equals(timetype.trim())) {
			OrderCost oc = StringUtil.parseJSONToBean(pricecopy, OrderCost.class);
			timetype = StringUtil.formatTimeType(oc);
		}
		return timetype;
	}
	

	/**  
	 * 获取下单来源(个人订单该值范围:0-乘客1-运管机构订单该值范围:0-乘客1-租赁2-机构)  
	 * @return ordersource 下单来源(个人订单该值范围:0-乘客1-运管机构订单该值范围:0-乘客1-租赁2-机构)  
	 */
	public String getOrdersource() {
		return ordersource;
	}
	

	/**  
	 * 设置下单来源(个人订单该值范围:0-乘客1-运管机构订单该值范围:0-乘客1-租赁2-机构)  
	 * @param ordersource 下单来源(个人订单该值范围:0-乘客1-运管机构订单该值范围:0-乘客1-租赁2-机构)  
	 */
	public void setOrdersource(String ordersource) {
		this.ordersource = ordersource;
	}
	

	/**  
	 * 获取订单结算方式包括：0-个人支付，1-个人垫付，2-机构支付  
	 * @return paymethod 订单结算方式包括：0-个人支付，1-个人垫付，2-机构支付  
	 */
	public String getPaymethod() {
		return paymethod;
	}
	
	/**  
	 * 设置订单结算方式包括：0-个人支付，1-个人垫付，2-机构支付  
	 * @param paymethod 订单结算方式包括：0-个人支付，1-个人垫付，2-机构支付  
	 */
	public void setPaymethod(String paymethod) {
		this.paymethod = paymethod;
	}
	
	/**  
	 * 设置时间计费类型(0-总用时1-低速用时)  
	 * @param timetype 时间计费类型(0-总用时1-低速用时)  
	 */
	public void setTimetype(String timetype) {
		this.timetype = timetype;
	}

	/**  
	 * 获取里程费  
	 * @return rangecost 里程费  
	 */
	public double getRangecost() {
		return rangecost;
	}

	/**  
	 * 设置里程费  
	 * @param rangecost 里程费  
	 */
	public void setRangecost(double rangecost) {
		this.rangecost = rangecost;
	}

	/**  
	 * 获取时长费  
	 * @return timecost 时长费  
	 */
	public double getTimecost() {
		return timecost;
	}
	
	/**  
	 * 设置时长费  
	 * @param timecost 时长费  
	 */
	public void setTimecost(double timecost) {
		this.timecost = timecost;
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
	

	/**  
	 * 获取车型图片  
	 * @return cartypelogo 车型图片  
	 */
	public String getCartypelogo() {
		return cartypelogo;
	}
	
	/**  
	 * 设置车型图片  
	 * @param cartypelogo 车型图片  
	 */
	public void setCartypelogo(String cartypelogo) {
		this.cartypelogo = cartypelogo;
	}
	
	/**  
	 * 获取支付状态  
	 * @return paystatus 支付状态  
	 */
	public String getPaystatus() {
		return paystatus;
	}

	/**  
	 * 设置支付状态  
	 * @param paystatus 支付状态  
	 */
	public void setPaystatus(String paystatus) {
		this.paystatus = paystatus;
	}
	
	/**  
	 * 获取复议状态  
	 * @return reviewstatus 复议状态  
	 */
	public String getReviewstatus() {
		return reviewstatus;
	}
	
	/**  
	 * 设置复议状态  
	 * @param reviewstatus 复议状态  
	 */
	public void setReviewstatus(String reviewstatus) {
		this.reviewstatus = reviewstatus;
	}
	
	/**  
	 * 获取所在城市代码  
	 * @return cityid 所在城市代码  
	 */
	public String getCityid() {
		return cityid;
	}

	/**  
	 * 设置所在城市代码  
	 * @param cityid 所在城市代码  
	 */
	public void setCityid(String cityid) {
		this.cityid = cityid;
	}
	
	/**  
	 * 获取所属租赁公司  
	 * @return companyid 所属租赁公司  
	 */
	public String getCompanyid() {
		return companyid;
	}
	

	/**  
	 * 设置所属租赁公司  
	 * @param companyid 所属租赁公司  
	 */
	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}
	

	/**  
	 * 获取机构ID(如果是机构则存在机构ID)  
	 * @return organid 机构ID(如果是机构则存在机构ID)  
	 */
	public String getOrganid() {
		return organid;
	}

	/**  
	 * 设置机构ID(如果是机构则存在机构ID)  
	 * @param organid 机构ID(如果是机构则存在机构ID)  
	 */
	public void setOrganid(String organid) {
		this.organid = organid;
	}
	
	/**  
	 * 获取下单时间  
	 * @return undertime 下单时间  
	 */
	public Date getUndertime() {
		return undertime;
	}
	

	/**  
	 * 设置下单时间  
	 * @param undertime 下单时间  
	 */
	public void setUndertime(Date undertime) {
		this.undertime = undertime;
	}
	

	/**  
	 * 获取接单时间  
	 * @return ordertime 接单时间  
	 */
	public Date getOrdertime() {
		return ordertime;
	}
	

	/**  
	 * 设置接单时间  
	 * @param ordertime 接单时间  
	 */
	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}
	

	/**  
	 * 获取司机出发时间  
	 * @return departuretime 司机出发时间  
	 */
	public Date getDeparturetime() {
		return departuretime;
	}
	

	/**  
	 * 设置司机出发时间  
	 * @param departuretime 司机出发时间  
	 */
	public void setDeparturetime(Date departuretime) {
		this.departuretime = departuretime;
	}
	

	/**  
	 * 获取司机抵达时间  
	 * @return arrivaltime 司机抵达时间  
	 */
	public Date getArrivaltime() {
		return arrivaltime;
	}
	

	/**  
	 * 设置司机抵达时间  
	 * @param arrivaltime 司机抵达时间  
	 */
	public void setArrivaltime(Date arrivaltime) {
		this.arrivaltime = arrivaltime;
	}
	

	/**  
	 * 获取服务开始时间  
	 * @return starttime 服务开始时间  
	 */
	public Date getStarttime() {
		return starttime;
	}
	

	/**  
	 * 设置服务开始时间  
	 * @param starttime 服务开始时间  
	 */
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
	

	/**  
	 * 获取服务结束时间  
	 * @return endtime 服务结束时间  
	 */
	public Date getEndtime() {
		return endtime;
	}
	

	/**  
	 * 设置服务结束时间  
	 * @param endtime 服务结束时间  
	 */
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	

	/**  
	 * 获取订单完结时间  
	 * @return completetime 订单完结时间  
	 */
	public Date getCompletetime() {
		return completetime;
	}
	

	/**  
	 * 设置订单完结时间  
	 * @param completetime 订单完结时间  
	 */
	public void setCompletetime(Date completetime) {
		this.completetime = completetime;
	}
	

	/**  
	 * 获取订单取消时间  
	 * @return canceltime 订单取消时间  
	 */
	public Date getCanceltime() {
		return canceltime;
	}
	

	/**  
	 * 设置订单取消时间  
	 * @param canceltime 订单取消时间  
	 */
	public void setCanceltime(Date canceltime) {
		this.canceltime = canceltime;
	}
	

	/**  
	 * 获取乘车人  
	 * @return passengers 乘车人  
	 */
	public String getPassengers() {
		return passengers;
	}
	

	/**  
	 * 设置乘车人  
	 * @param passengers 乘车人  
	 */
	public void setPassengers(String passengers) {
		this.passengers = passengers;
	}
	

	/**  
	 * 获取乘车人电话  
	 * @return passengerphone 乘车人电话  
	 */
	public String getPassengerphone() {
		return passengerphone;
	}
	

	/**  
	 * 设置乘车人电话  
	 * @param passengerphone 乘车人电话  
	 */
	public void setPassengerphone(String passengerphone) {
		this.passengerphone = passengerphone;
	}
	
	/**  
	 * 获取下单人  
	 * @return username 下单人  
	 */
	public String getUsername() {
		return username;
	}
	
	/**  
	 * 设置下单人  
	 * @param username 下单人  
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**  
	 * 获取下单人电话  
	 * @return userphone 下单人电话  
	 */
	public String getUserphone() {
		return userphone;
	}
	
	/**  
	 * 设置下单人电话  
	 * @param userphone 下单人电话  
	 */
	public void setUserphone(String userphone) {
		this.userphone = userphone;
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
	 * 获取所选车型  
	 * @return cartype 所选车型  
	 */
	public String getCartype() {
		return cartype;
	}
	

	/**  
	 * 设置所选车型  
	 * @param cartype 所选车型  
	 */
	public void setCartype(String cartype) {
		this.cartype = cartype;
	}
	

	/**  
	 * 获取所在城市  
	 * @return city 所在城市  
	 */
	public String getCity() {
		return city;
	}
	

	/**  
	 * 设置所在城市  
	 * @param city 所在城市  
	 */
	public void setCity(String city) {
		this.city = city;
	}
	
	/**  
	 * 获取实际时长(分钟)  
	 * @return times 实际时长(分钟)  
	 */
	public int getTimes() {
		return times % 60 > 0 ? times/60+1 : times/60;
	}
	

	/**  
	 * 设置实际时长(分钟)  
	 * @param times 实际时长(分钟)  
	 */
	public void setTimes(int times) {
		this.times = times;
	}
	

	/**  
	 * 获取实际里程(米)
	 * @return mileage 实际里程  
	 */
	public double getMileage() {
		return mileage;
	}
	

	/**  
	 * 设置实际里程(米)
	 * @param mileage 实际里程  
	 */
	public void setMileage(double mileage) {
		this.mileage = mileage;
	}
	

	/**  
	 * 获取实际金额  
	 * @return orderamount 实际金额  
	 */
	public double getOrderamount() {
		return orderamount;
	}
	

	/**  
	 * 设置实际金额  
	 * @param orderamount 实际金额  
	 */
	public void setOrderamount(double orderamount) {
		this.orderamount = orderamount;
	}
	

	/**  
	 * 获取计价规则副本  
	 * @return pricecopy 计价规则副本  
	 */
	public String getPricecopy() {
		return pricecopy;
	}
	

	/**  
	 * 设置计价规则副本  
	 * @param pricecopy 计价规则副本  
	 */
	public void setPricecopy(String pricecopy) {
		this.pricecopy = pricecopy;
	}
	

	/**  
	 * 获取航班号  
	 * @return fltno 航班号  
	 */
	public String getFltno() {
		return fltno;
	}
	

	/**  
	 * 设置航班号  
	 * @param fltno 航班号  
	 */
	public void setFltno(String fltno) {
		this.fltno = fltno;
	}
	

	/**  
	 * 获取降落时间  
	 * @return falltime 降落时间  
	 */
	public Date getFalltime() {
		return falltime;
	}
	

	/**  
	 * 设置降落时间  
	 * @param falltime 降落时间  
	 */
	public void setFalltime(Date falltime) {
		this.falltime = falltime;
	}
	

	/**  
	 * 获取用户评分包括：1-1星，2-2星，3-3星，4-4星，5-5星  
	 * @return userrate 用户评分包括：1-1星，2-2星，3-3星，4-4星，5-5星  
	 */
	public String getUserrate() {
		return userrate;
	}
	

	/**  
	 * 设置用户评分包括：1-1星，2-2星，3-3星，4-4星，5-5星  
	 * @param userrate 用户评分包括：1-1星，2-2星，3-3星，4-4星，5-5星  
	 */
	public void setUserrate(String userrate) {
		this.userrate = userrate;
	}
	

	/**  
	 * 获取用户评价  
	 * @return usercomment 用户评价  
	 */
	public String getUsercomment() {
		return usercomment;
	}
	

	/**  
	 * 设置用户评价  
	 * @param usercomment 用户评价  
	 */
	public void setUsercomment(String usercomment) {
		this.usercomment = usercomment;
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
	 * 获取车辆ID  
	 * @return vehicleid 车辆ID  
	 */
	public String getVehicleid() {
		return vehicleid;
	}
	

	/**  
	 * 设置车辆ID  
	 * @param vehicleid 车辆ID  
	 */
	public void setVehicleid(String vehicleid) {
		this.vehicleid = vehicleid;
	}
	

	/**  
	 * 获取取消方  
	 * @return cancelparty 取消方  
	 */
	public String getCancelparty() {
		return cancelparty;
	}
	

	/**  
	 * 设置取消方  
	 * @param cancelparty 取消方  
	 */
	public void setCancelparty(String cancelparty) {
		this.cancelparty = cancelparty;
	}
	

	/**  
	 * 设置起步价  
	 * @param startprice 起步价  
	 */
	public void setStartprice(double startprice) {
		this.startprice = startprice;
	}
	

	/**  
	 * 设置里程单价  
	 * @param rangeprice 里程单价  
	 */
	public void setRangeprice(double rangeprice) {
		this.rangeprice = rangeprice;
	}

	/**  
	 * 设置时间单价  
	 * @param timeprice 时间单价  
	 */
	public void setTimeprice(double timeprice) {
		this.timeprice = timeprice;
	}

	/**  
	 * 获取经度  
	 * @return lng 经度  
	 */
	public double getLng() {
		return lng;
	}

	/**  
	 * 设置经度  
	 * @param lng 经度  
	 */
	public void setLng(double lng) {
		this.lng = lng;
	}

	/**  
	 * 获取纬度  
	 * @return lat 纬度  
	 */
	public double getLat() {
		return lat;
	}

	/**  
	 * 设置纬度  
	 * @param lat 纬度  
	 */
	public void setLat(double lat) {
		this.lat = lat;
	}

	/**  
	 * 获取当前时间节点所在城市  
	 * @return cityintime 当前时间节点所在城市  
	 */
	public String getCityintime() {
		return cityintime;
	}
	
	/**  
	 * 设置当前时间节点所在城市  
	 * @param cityintime 当前时间节点所在城市  
	 */
	public void setCityintime(String cityintime) {
		this.cityintime = cityintime;
	}
	
	/**  
	 * 获取当前时间节点所在地址  
	 * @return addressintime 当前时间节点所在地址  
	 */
	public String getAddressintime() {
		return addressintime;
	}
	
	/**  
	 * 设置当前时间节点所在地址  
	 * @param addressintime 当前时间节点所在地址  
	 */
	public void setAddressintime(String addressintime) {
		this.addressintime = addressintime;
	}

	public Integer getOrdersortcolumn() {
		return ordersortcolumn;
	}

	public void setOrdersortcolumn(Integer ordersortcolumn) {
		this.ordersortcolumn = ordersortcolumn;
	}
}
