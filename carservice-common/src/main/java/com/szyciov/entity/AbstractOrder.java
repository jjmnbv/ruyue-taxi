package com.szyciov.entity;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang.StringUtils;

/**
  * @ClassName AbstractOrder
  * @author Efy
  * @Description 订单抽象类
  * @date 2016年9月19日 14:43:19
  */ 
public abstract class AbstractOrder{
	/**
	  *主键(订单编号)
	  */
	private String orderno;

	/**
	  *所属租赁公司 与租赁公司id关联
	  */
	private String companyid;

	/**
	  *订单类型 包括：1-约车、2-接机、3-送机选项
	  */
	private String ordertype;

	/**
	  *下单人（机构用户）
	  */
	private String userid;

	/**
	  *乘车人
	  */
	private String passengers;

	/**
	  *乘车人电话
	  */
	private String passengerphone;

	/**
	  *接单人（司机）
	  */
	private String driverid;

	/**
	  *接单车辆（车辆信息）
	  */
	private String vehicleid;

	/**
	  *所选车型
	  */
	private String selectedmodel;

	/**
	  *上车城市
	  */
	private String oncity;

	/**
	  *上车地址
	  */
	private String onaddress;

	/**
	  *下车城市
	  */
	private String offcity;

	/**
	  *取消方：0-租赁端，1-乘客端，2-司机端
	  */
	private String cancelparty;

	/**
	  *下车地址
	  */
	private String offaddress;

	/**
	  *上车地址经度
	  */
	private double onaddrlng;

	/**
	  *上车地址纬度
	  */
	private double onaddrlat;

	/**
	  *下车地址经度
	  */
	private double offaddrlng;

	/**
	  *下车地址纬度
	  */
	private double offaddrlat;

	/**
	  *用车时间
	  */
//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date usetime;

	/**
	  *行程备注
	  */
	private String tripremark;

	/**
	  *订单状态 包括：未完成（0-待接单，1-待人工派单，2-待出发，3-已出发，4-已抵达，5-接到乘客，6-服务中，7-行程结束，8-已取消 , 9-待确费)
	  */
	private String orderstatus;
	
	/**
	 * 0-未支付，1-已支付，2-结算中，3-已结算
	 */
	private String paymentstatus;
	
	/**
	 * 0-未复核，1-异常待复核，2-已复核
	 */
	private String reviewstatus;

	/**
	  *预估时长（分钟）
	  */
	private int estimatedtime;

	/**
	  *预估里程
	  */
	private double estimatedmileage;

	/**
	  *预估费用
	  */
	private double estimatedcost;

	/**
	  *支付方式
	  */
	private String paytype;

	/**
	  *实际里程
	  */
	private double mileage;

	/**
	  *订单金额
	  */
	private double orderamount;

	/**
	 * 取整的订单金额
	 */
	private int orderamountint;
	
	
	/**
	 * 订单最后推送时间
	 */
	private Date lastsendtime;
	
	/**
	 * 订单推送状态, 0 - 待推送； 1-取消推送
	 */
	private int sendstatus;

	/**
	  *下单时间
	  */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date undertime;

	/**
	  *接单时间
	  */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date ordertime;

	/**
	  *出发时间
	  */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date departuretime;

	/**
	  *抵达时间
	  */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date arrivaltime;

	/**
	  *开始时间
	  */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date starttime;

	/**
	  *结束时间
	  */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date endtime;

	/**
	  *完成时间
	  */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date completetime;

	/**
	 * 取消时间
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date canceltime;
	
	/**
	  *计价条件副本
	  */
	private String pricecopy;

	/**
	  *航班号
	  */
	private String fltno;

	/**
	  *降落时间
	  */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date falltime;

	/**
	  *复核原因
	  */
	private String orderreason;

	/**
	  *下单来源 包括：0-乘客端，1-租赁端，2-机构端
	  */
	private String ordersource;

	/**
	  *用户评分 包括：1-1星，2-2星，3-3星，4-4星，5-5星
	  */
	private String userrate;

	/**
	  *用户订单评价
	  */
	private String usercomment;

	/**
	  *创建时间
	  */
	private Date createtime;

	/**
	  *更新时间
	  */
	private Date updatetime;

	/**
	  *数据状态
	  */
	private int status;

	/**
	  *1-可见，0不可见
	  */
	private String userhidden;

	/**
	  *订单推送人数
	  */
	private int pushnumber;
	
	/**
	 * 是否是即刻用车
	 */
	private boolean isusenow;
	
	/**
	 * 复核人(1-司机,2-下单人)
	 */
	private String reviewperson;
	
	/**
	 * 应付金额
	 */
	private Double shouldpayamount;
	
	/**
	 * 实付金额
	 */
	private Double actualpayamount;
	
	/**
	 * 出发地址城市
	 */
	private String departurecity;
	
	/**
	 * 出发地址
	 */
	private String departureaddress;
	
	/**
	 * 出发地址经度
	 */
	private Double departurelng;
	
	/**
	 * 出发地址纬度
	 */
	private Double departurelat;
	
	/**
	 * 抵达地址城市
	 */
	private String arrivalcity;
	
	/**
	 * 抵达地址
	 */
	private String arrivaladdress;
	
	/**
	 * 抵达地址经度
	 */
	private Double arrivallng;
	
	/**
	 * 抵达地址纬度
	 */
	private Double arrivallat;
	
	/**
	 * 下单车型名称
	 */
	private String selectedmodelname;
	
	/**
	 * 实际车型名称
	 */
	private String factmodelname;
	
	/**
	 * 计费车型名称
	 */
	private String pricemodelname;
	
	/**
	 * 车品牌名称
	 */
	private String vehcbrandname;
	
	/**
	 * 车系名称
	 */
	private String vehclinename;
	
	/**
	 * 车牌号
	 */
	private String plateno;
	
	/**
	 * 操作人
	 */
	private String operator;
	

	/**
	 * 是否保存乘车人
	 */
	private boolean savepassenger;
	

	/**
	 * 系统派单超时时间
	 */
	private Date systemsendovertime;
	
	/**
	 * 订单超时自动取消时间
	 */
	private Date autocanceltime;
	
	/**
	 * 用车类型 包括：0-因公用车、1-因私用车、2-个人
	 */
	private String usetype;
	
	/**
	 * 自动取消订单剩余的秒数（只有待派单次值才有含义）
	 */
	private int lefttime;
	
	/**
	 * 原始订单金额
	 */
	private Double originalorderamount;
	
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
	private Double startlng;
	
	/**
	 * 开始服务地址纬度
	 */
	private Double startllat;
	
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
	private Double endlng;
	
	/**
	 * 结束服务地址纬度
	 */
	private Double endllat;

	/**归属车企,从字典表中读取*/
	private String belongleasecompany;

    /**
     * 里程计算方式(0-OBD，1-OBDGPS里程，2-司机AppGPS里程，3-鹰眼里程，4-预估里程)
     */
    private Integer costtype;
    
    /**
     * 派单类型(0-系统指派，1-手工指派，默认为0)
     */
    private int sendordertype;

    /**
     * 责任方(1-乘客,2-司机,3-客服,4-平台)
     */
    private Integer dutyparty;

    /**
     * 费用类型(1-行程服务,2-取消处罚)
     */
    private Integer expensetype;
    
    /**
     * 优惠券id
     */
    private String couponid;
    
    /**
     * 优惠券金额
     */
    private Integer couponprice;

    public String getUsetype() {
		return usetype;
	}

	public void setUsetype(String usetype) {
		this.usetype = usetype;
	}

	/**  
	 * 获取复核原因  
	 * @return orderreason 复核原因  
	 */
	public String getOrderreason() {
		return orderreason;
	}
	
	/**  
	 * 设置复核原因  
	 * @param orderreason 复核原因  
	 */
	public void setOrderreason(String orderreason) {
		this.orderreason = orderreason;
	}

	/**
	  *设置主键(订单编号)
	  */
	public void setOrderno(String orderno){
		this.orderno=orderno;
	}

	/**
	  *获取主键(订单编号)
	  */
	public String getOrderno(){
		return orderno;
	}

	/**
	  *设置所属租赁公司 与租赁公司id关联
	  */
	public void setCompanyid(String companyid){
		this.companyid=companyid;
	}

	/**
	  *获取所属租赁公司 与租赁公司id关联
	  */
	public String getCompanyid(){
		return companyid;
	}

	/**
	  *设置订单类型 包括：1-约车、2-接机、3-送机选项
	  */
	public void setOrdertype(String ordertype){
		this.ordertype=ordertype;
	}

	/**
	  *获取订单类型 包括：1-约车、2-接机、3-送机选项
	  */
	public String getOrdertype(){
		return ordertype;
	}

	/**
	  *设置下单人（机构用户）
	  */
	public void setUserid(String userid){
		this.userid=userid;
	}

	/**
	  *获取下单人（机构用户）
	  */
	public String getUserid(){
		return userid;
	}

	/**
	  *设置乘车人
	  */
	public void setPassengers(String passengers){
		this.passengers=passengers;
	}

	/**
	  *获取乘车人
	  */
	public String getPassengers(){
		return passengers;
	}

	/**
	  *设置乘车人电话
	  */
	public void setPassengerphone(String passengerphone){
		this.passengerphone=passengerphone;
	}

	/**
	  *获取乘车人电话
	  */
	public String getPassengerphone(){
		return passengerphone;
	}

	/**
	  *设置接单人（司机）
	  */
	public void setDriverid(String driverid){
		this.driverid=driverid;
	}

	/**
	  *获取接单人（司机）
	  */
	public String getDriverid(){
		return driverid;
	}

	/**
	  *设置接单车辆（车辆信息）
	  */
	public void setVehicleid(String vehicleid){
		this.vehicleid=vehicleid;
	}

	/**
	  *获取接单车辆（车辆信息）
	  */
	public String getVehicleid(){
		return vehicleid;
	}

	/**
	  *设置所选车型
	  */
	public void setSelectedmodel(String selectedmodel){
		this.selectedmodel=selectedmodel;
	}

	/**
	  *获取所选车型
	  */
	public String getSelectedmodel(){
		return selectedmodel;
	}

	/**
	  *设置上车城市
	  */
	public void setOncity(String oncity){
		this.oncity=oncity;
	}

	/**
	  *获取上车城市
	  */
	public String getOncity(){
		return oncity;
	}

	/**
	  *设置上车地址
	  */
	public void setOnaddress(String onaddress){
		this.onaddress=onaddress;
	}

	/**
	  *获取上车地址
	  */
	public String getOnaddress(){
		return onaddress;
	}

	/**
	  *设置下车城市
	  */
	public void setOffcity(String offcity){
		this.offcity=offcity;
	}

	/**
	  *获取下车城市
	  */
	public String getOffcity(){
		return offcity;
	}

	/**
	  *设置取消方：0-租赁端，1-乘客端，2-司机端
	  */
	public void setCancelparty(String cancelparty){
		this.cancelparty=cancelparty;
	}

	/**
	  *获取取消方：0-租赁端，1-乘客端，2-司机端
	  */
	public String getCancelparty(){
		return cancelparty;
	}

	/**
	  *设置下车地址
	  */
	public void setOffaddress(String offaddress){
		this.offaddress=offaddress;
	}

	/**
	  *获取下车地址
	  */
	public String getOffaddress(){
		return offaddress;
	}

	/**
	  *设置上车地址经度
	  */
	public void setOnaddrlng(double onaddrlng){
		this.onaddrlng=onaddrlng;
	}

	/**
	  *获取上车地址经度
	  */
	public double getOnaddrlng(){
		return onaddrlng;
	}

	/**
	  *设置上车地址纬度
	  */
	public void setOnaddrlat(double onaddrlat){
		this.onaddrlat=onaddrlat;
	}

	/**
	  *获取上车地址纬度
	  */
	public double getOnaddrlat(){
		return onaddrlat;
	}

	/**
	  *设置下车地址经度
	  */
	public void setOffaddrlng(double offaddrlng){
		this.offaddrlng=offaddrlng;
	}

	/**
	  *获取下车地址经度
	  */
	public double getOffaddrlng(){
		return offaddrlng;
	}

	/**
	  *设置下车地址纬度
	  */
	public void setOffaddrlat(double offaddrlat){
		this.offaddrlat=offaddrlat;
	}

	/**
	  *获取下车地址纬度
	  */
	public double getOffaddrlat(){
		return offaddrlat;
	}

	public void setUsetime(Object usetime) {
		if(usetime instanceof Date){
			this.usetime = (Date) usetime;
		}else if(usetime instanceof String){
			try {
				if(StringUtils.isNotBlank((String) usetime)){
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					this.usetime = format.parse((String)usetime);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else if(usetime instanceof Long){
			this.usetime = new Date((long)usetime);
		}
	}

	/**
	  *获取用车时间
	  */
	public Date getUsetime(){
		return usetime;
	}

	/**
	  *设置行程备注
	  */
	public void setTripremark(String tripremark){
		this.tripremark=tripremark;
	}

	/**
	  *获取行程备注
	  */
	public String getTripremark(){
		return tripremark;
	}


	/**  
	 * 获取订单状态包括：未完成（0-待接单，1-待人工派单，2-待出发，3-已出发，4-已抵达，5-接到乘客，6-服务中，7-行程结束，8-已取消9-待确费)  
	 * @return orderstatus 订单状态包括：未完成（0-待接单，1-待人工派单，2-待出发，3-已出发，4-已抵达，5-接到乘客，6-服务中，7-行程结束，8-已取消9-待确费)  
	 */
	public String getOrderstatus() {
		return orderstatus;
	}

	
	/**  
	 * 设置订单状态包括：未完成（0-待接单，1-待人工派单，2-待出发，3-已出发，4-已抵达，5-接到乘客，6-服务中，7-行程结束，8-已取消9-待确费)  
	 * @param orderstatus 订单状态包括：未完成（0-待接单，1-待人工派单，2-待出发，3-已出发，4-已抵达，5-接到乘客，6-服务中，7-行程结束，8-已取消9-待确费)  
	 */
	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}
	

	/**
	  *设置预估时长（分钟）
	  */
	public void setEstimatedtime(int estimatedtime){
		this.estimatedtime=estimatedtime;
	}

	/**
	  *获取预估时长（分钟）
	  */
	public int getEstimatedtime(){
		return estimatedtime;
	}

	/**
	  *设置预估里程
	  */
	public void setEstimatedmileage(double estimatedmileage){
		this.estimatedmileage=estimatedmileage;
	}

	/**
	  *获取预估里程
	  */
	public double getEstimatedmileage(){
		return estimatedmileage;
	}

	/**
	  *设置预估费用
	  */
	public void setEstimatedcost(double estimatedcost){
		this.estimatedcost=estimatedcost;
	}

	/**
	  *获取预估费用
	  */
	public double getEstimatedcost(){
		return estimatedcost;
	}

	/**
	  *设置支付方式
	  */
	public void setPaytype(String paytype){
		this.paytype=paytype;
	}

	/**
	  *获取支付方式
	  */
	public String getPaytype(){
		return paytype;
	}

	/**
	  *设置实际里程
	  */
	public void setMileage(double mileage){
		this.mileage=mileage;
	}

	/**
	  *获取实际里程
	  */
	public double getMileage(){
		return mileage;
	}

	/**
	  *设置实际金额
	  */
	public void setOrderamount(double orderamount){
		this.orderamount=orderamount;
	}

	/**
	  *获取实际金额
	  */
	public double getOrderamount(){
		return orderamount;
	}

	/**
	  *设置下单时间
	  */
	public void setUndertime(Date undertime){
		this.undertime=undertime;
	}

	/**
	  *获取下单时间
	  */
	public Date getUndertime(){
		return undertime;
	}

	/**
	  *设置接单时间
	  */
	public void setOrdertime(Date ordertime){
		this.ordertime=ordertime;
	}

	/**
	  *获取接单时间
	  */
	public Date getOrdertime(){
		return ordertime;
	}

	/**
	  *设置出发时间
	  */
	public void setDeparturetime(Date departuretime){
		this.departuretime=departuretime;
	}

	/**
	  *获取出发时间
	  */
	public Date getDeparturetime(){
		return departuretime;
	}

	/**
	  *设置抵达时间
	  */
	public void setArrivaltime(Date arrivaltime){
		this.arrivaltime=arrivaltime;
	}

	/**
	  *获取抵达时间
	  */
	public Date getArrivaltime(){
		return arrivaltime;
	}

	/**
	  *设置开始时间
	  */
	public void setStarttime(Date starttime){
		this.starttime=starttime;
	}

	/**
	  *获取开始时间
	  */
	public Date getStarttime(){
		return starttime;
	}

	/**
	  *设置结束时间
	  */
	public void setEndtime(Date endtime){
		this.endtime=endtime;
	}

	/**
	  *获取结束时间
	  */
	public Date getEndtime(){
		return endtime;
	}

	/**
	  *设置完成时间
	  */
	public void setCompletetime(Date completetime){
		this.completetime=completetime;
	}

	/**
	  *获取完成时间
	  */
	public Date getCompletetime(){
		return completetime;
	}

	/**
	 * 获取取消时间
	 * @return
	 */
	public Date getCanceltime() {
		return canceltime;
	}

	/**
	 * 设置取消时间
	 * @param canceltime
	 */
	public void setCanceltime(Date canceltime) {
		this.canceltime = canceltime;
	}

	/**
	  *设置计价条件副本
	  */
	public void setPricecopy(String pricecopy){
		this.pricecopy=pricecopy;
	}

	/**
	  *获取计价条件副本
	  */
	public String getPricecopy(){
		return pricecopy;
	}

	/**
	  *设置航班号
	  */
	public void setFltno(String fltno){
		this.fltno=fltno;
	}

	/**
	  *获取航班号
	  */
	public String getFltno(){
		return fltno;
	}

	/**
	  *设置降落时间
	  */
	public void setFalltime(Date falltime){
		this.falltime=falltime;
	}

	/**
	  *获取降落时间
	  */
	public Date getFalltime(){
		return falltime;
	}


	/**
	  *设置下单来源 包括：0-乘客端，1-租赁端，2-机构端
	  */
	public void setOrdersource(String ordersource){
		this.ordersource=ordersource;
	}

	/**
	  *获取下单来源 包括：0-乘客端，1-租赁端，2-机构端
	  */
	public String getOrdersource(){
		return ordersource;
	}

	/**
	  *设置用户评分 包括：1-1星，2-2星，3-3星，4-4星，5-5星
	  */
	public void setUserrate(String userrate){
		this.userrate=userrate;
	}

	/**
	  *获取用户评分 包括：1-1星，2-2星，3-3星，4-4星，5-5星
	  */
	public String getUserrate(){
		return userrate;
	}

	/**
	  *设置用户订单评价
	  */
	public void setUsercomment(String usercomment){
		this.usercomment=usercomment;
	}

	/**
	  *获取用户订单评价
	  */
	public String getUsercomment(){
		return usercomment;
	}

	/**
	  *设置创建时间
	  */
	public void setCreatetime(Date createtime){
		this.createtime=createtime;
	}

	/**
	  *获取创建时间
	  */
	public Date getCreatetime(){
		return createtime;
	}

	/**
	  *设置更新时间
	  */
	public void setUpdatetime(Date updatetime){
		this.updatetime=updatetime;
	}

	/**
	  *获取更新时间
	  */
	public Date getUpdatetime(){
		return updatetime;
	}

	/**
	  *设置数据状态
	  */
	public void setStatus(int status){
		this.status=status;
	}

	/**
	  *获取数据状态
	  */
	public int getStatus(){
		return status;
	}

	/**
	  *设置1-可见，0不可见
	  */
	public void setUserhidden(String userhidden){
		this.userhidden=userhidden;
	}

	/**
	  *获取1-可见，0不可见
	  */
	public String getUserhidden(){
		return userhidden;
	}

	/**
	  *设置订单推送人数
	  */
	public void setPushnumber(int pushnumber){
		this.pushnumber=pushnumber;
	}

	/**
	  *获取订单推送人数
	  */
	public int getPushnumber(){
		return pushnumber;
	}

	public String getPaymentstatus() {
		return paymentstatus;
	}

	public void setPaymentstatus(String paymentstatus) {
		this.paymentstatus = paymentstatus;
	}

	public String getReviewstatus() {
		return reviewstatus;
	}

	public void setReviewstatus(String reviewstatus) {
		this.reviewstatus = reviewstatus;
	}

	public Date getLastsendtime() {
		return lastsendtime;
	}

	public void setLastsendtime(Date lastsendtime) {
		this.lastsendtime = lastsendtime;
	}

	public int getSendstatus() {
		return sendstatus;
	}

	public void setSendstatus(int sendstatus) {
		this.sendstatus = sendstatus;
	}

	public boolean isIsusenow() {
		return isusenow;
	}

	public void setIsusenow(boolean isusenow) {
		this.isusenow = isusenow;
	}

	public String getReviewperson() {
		return reviewperson;
	}

	public void setReviewperson(String reviewperson) {
		this.reviewperson = reviewperson;
	}

	public Double getShouldpayamount() {
		return shouldpayamount;
	}

	public void setShouldpayamount(Double shouldpayamount) {
		this.shouldpayamount = shouldpayamount;
	}

	public Double getActualpayamount() {
		return actualpayamount;
	}

	public void setActualpayamount(Double actualpayamount) {
		this.actualpayamount = actualpayamount;
	}

	public String getDeparturecity() {
		return departurecity;
	}

	public void setDeparturecity(String departurecity) {
		this.departurecity = departurecity;
	}

	public String getDepartureaddress() {
		return departureaddress;
	}

	public void setDepartureaddress(String departureaddress) {
		this.departureaddress = departureaddress;
	}

	public Double getDeparturelng() {
		return departurelng;
	}

	public void setDeparturelng(Double departurelng) {
		this.departurelng = departurelng;
	}

	public Double getDeparturelat() {
		return departurelat;
	}

	public void setDeparturelat(Double departurelat) {
		this.departurelat = departurelat;
	}

	public String getArrivalcity() {
		return arrivalcity;
	}

	public void setArrivalcity(String arrivalcity) {
		this.arrivalcity = arrivalcity;
	}

	public String getArrivaladdress() {
		return arrivaladdress;
	}

	public void setArrivaladdress(String arrivaladdress) {
		this.arrivaladdress = arrivaladdress;
	}

	public Double getArrivallng() {
		return arrivallng;
	}

	public void setArrivallng(Double arrivallng) {
		this.arrivallng = arrivallng;
	}

	public Double getArrivallat() {
		return arrivallat;
	}

	public void setArrivallat(Double arrivallat) {
		this.arrivallat = arrivallat;
	}

	public String getSelectedmodelname() {
		return selectedmodelname;
	}

	public void setSelectedmodelname(String selectedmodelname) {
		this.selectedmodelname = selectedmodelname;
	}

	public String getFactmodelname() {
		return factmodelname;
	}

	public void setFactmodelname(String factmodelname) {
		this.factmodelname = factmodelname;
	}

	public String getPricemodelname() {
		return pricemodelname;
	}

	public void setPricemodelname(String pricemodelname) {
		this.pricemodelname = pricemodelname;
	}

	public String getVehcbrandname() {
		return vehcbrandname;
	}

	public void setVehcbrandname(String vehcbrandname) {
		this.vehcbrandname = vehcbrandname;
	}

	public String getVehclinename() {
		return vehclinename;
	}

	public void setVehclinename(String vehclinename) {
		this.vehclinename = vehclinename;
	}

	public String getPlateno() {
		return plateno;
	}

	public void setPlateno(String plateno) {
		this.plateno = plateno;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}


	public boolean isSavepassenger() {
		return savepassenger;
	}

	public void setSavepassenger(boolean savepassenger) {
		this.savepassenger = savepassenger;
	}


	public Date getSystemsendovertime() {
		return systemsendovertime;
	}

	public void setSystemsendovertime(Date systemsendovertime) {
		this.systemsendovertime = systemsendovertime;
	}

	public Date getAutocanceltime() {
		return autocanceltime;
	}

	public void setAutocanceltime(Date autocanceltime) {
		this.autocanceltime = autocanceltime;
	}

	public int getLefttime() {
		return lefttime;
	}

	public void setLefttime(int lefttime) {
		this.lefttime = lefttime;
	}

	public Double getOriginalorderamount() {
		return originalorderamount;
	}

	public void setOriginalorderamount(Double originalorderamount) {
		this.originalorderamount = originalorderamount;
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

    public Integer getCosttype() {
        return costtype;
    }

    public void setCosttype(Integer costtype) {
        this.costtype = costtype;
    }
	/**
	 * 获取归属车企从字典表中读取
	 * @return belongleasecompany 归属车企从字典表中读取
	 */
	public String getBelongleasecompany() {
		return belongleasecompany;
	}


	/**
	 * 设置归属车企从字典表中读取
	 * @param belongleasecompany 归属车企从字典表中读取
	 */
	public void setBelongleasecompany(String belongleasecompany) {
		this.belongleasecompany = belongleasecompany;
	}

	/**  
	 * 获取派单类型(0-系统指派，1-手工指派，默认为0)  
	 * @return sendordertype 派单类型(0-系统指派，1-手工指派，默认为0)  
	 */
	public int getSendordertype() {
		return sendordertype;
	}
	
	/**  
	 * 设置派单类型(0-系统指派，1-手工指派，默认为0)  
	 * @param sendordertype 派单类型(0-系统指派，1-手工指派，默认为0)  
	 */
	public void setSendordertype(int sendordertype) {
		this.sendordertype = sendordertype;
	}

	public int getOrderamountint() {
		return orderamountint;
	}

	public void setOrderamountint(int orderamountint) {
		this.orderamountint = orderamountint;
	}

    public Integer getDutyparty() {
        return dutyparty;
    }

    public void setDutyparty(Integer dutyparty) {
        this.dutyparty = dutyparty;
    }

    public Integer getExpensetype() {
        return expensetype;
    }

    public void setExpensetype(Integer expensetype) {
        this.expensetype = expensetype;
    }

	public String getCouponid() {
		return couponid;
	}

	public void setCouponid(String couponid) {
		this.couponid = couponid;
	}

	/**  
	 * 获取优惠券金额  
	 * @return couponprice 优惠券金额  
	 */
	public Integer getCouponprice() {
		return couponprice;
	}

	/**  
	 * 设置优惠券金额  
	 * @param couponprice 优惠券金额  
	 */
	public void setCouponprice(Integer couponprice) {
		this.couponprice = couponprice;
	}
	
}
