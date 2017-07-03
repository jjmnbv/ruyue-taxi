package com.szyciov.org.entity;


import java.util.Date;

/**
  * @ClassName OrgOrderReview
  * @author Efy
  * @Description 订单复议记录
  * @date 2016年9月21日 20:34:21
  */ 
public class OrgOrderReview{
	/**
	  *id
	  */
	private String id;

	/**
	  *所属订单
	  */
	private String orderno;

	/**
	  *差异金额
	  */
	private double price;

	/**
	  *服务里程(米)
	  */
	private double mileage;

	/**
	  *服务时长(分钟)
	  */
	private double times;

	/**
	  *服务开始时间
	  */
	private Date starttime;

	/**
	  *服务结束时间
	  */
	private Date endtime;

	/**
	  *累计时间(分钟)
	  */
	private double counttimes;

	/**
	  *时间补贴(元)
	  */
	private double timesubsidies;

	/**
	  *里程费用(元)
	  */
	private double mileageprices;

	/**
	  *提出复议方(0-乘客，1-司机)
	  */
	private String reviewperson;

	/**
	  *复议原因
	  */
	private String reason;

	/**
	  *复议时间
	  */
	private Date reviewtime;

	/**
	  *复议人
	  */
	private String operator;

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
	 * 复核状态
	 */
	private String reviewstatus;
	
	/**
	 * 原服务开始时间
	 */
	private Date rawstarttime;
	
	/**
	 * 原服务结束时间
	 */
	private Date rawendtime;
	
	/**
	 * 原累计时间
	 */
	private Double rawtimes;
	
	/**
	 * 原服务里程
	 */
	private Double rawmileage;
	
	/**
	 * 复议后金额
	 */
	private Double reviewedprice;
	
	/**
	 * 处理意见
	 */
	private String opinion;
	
	/**
	 * 原订单金额
	 */
	private Double raworderamount;

    /**
     * 复核后计价副本
     */
    private String pricecopy;

    /**
     * 原计价副本
     */
    private String rawpricecopy;

	/**
	  *设置id
	  */
	public void setId(String id){
		this.id=id;
	}

	/**
	  *获取id
	  */
	public String getId(){
		return id;
	}

	/**
	  *设置所属订单
	  */
	public void setOrderno(String orderno){
		this.orderno=orderno;
	}

	/**
	  *获取所属订单
	  */
	public String getOrderno(){
		return orderno;
	}

	/**
	  *设置差异金额
	  */
	public void setPrice(double price){
		this.price=price;
	}

	/**
	  *获取差异金额
	  */
	public double getPrice(){
		return price;
	}

	/**
	  *设置服务里程(公里)
	  */
	public void setMileage(double mileage){
		this.mileage=mileage;
	}

	/**
	  *获取服务里程(公里)
	  */
	public double getMileage(){
		return mileage;
	}

	/**
	  *设置服务时长(分钟)
	  */
	public void setTimes(double times){
		this.times=times;
	}

	/**
	  *获取服务时长(分钟)
	  */
	public double getTimes(){
		return times;
	}

	/**
	  *设置服务开始时间
	  */
	public void setStarttime(Date starttime){
		this.starttime=starttime;
	}

	/**
	  *获取服务开始时间
	  */
	public Date getStarttime(){
		return starttime;
	}

	/**
	  *设置服务结束时间
	  */
	public void setEndtime(Date endtime){
		this.endtime=endtime;
	}

	/**
	  *获取服务结束时间
	  */
	public Date getEndtime(){
		return endtime;
	}

	/**
	  *设置累计时间(分钟)
	  */
	public void setCounttimes(double counttimes){
		this.counttimes=counttimes;
	}

	/**
	  *获取累计时间(分钟)
	  */
	public double getCounttimes(){
		return counttimes;
	}

	/**
	  *设置时间补贴(元)
	  */
	public void setTimesubsidies(double timesubsidies){
		this.timesubsidies=timesubsidies;
	}

	/**
	  *获取时间补贴(元)
	  */
	public double getTimesubsidies(){
		return timesubsidies;
	}

	/**
	  *设置里程费用(元)
	  */
	public void setMileageprices(double mileageprices){
		this.mileageprices=mileageprices;
	}

	/**
	  *获取里程费用(元)
	  */
	public double getMileageprices(){
		return mileageprices;
	}

	/**
	  *设置提出复议方(0-乘客，1-司机)
	  */
	public void setReviewperson(String reviewperson){
		this.reviewperson=reviewperson;
	}

	/**
	  *获取提出复议方(0-乘客，1-司机)
	  */
	public String getReviewperson(){
		return reviewperson;
	}

	/**
	  *设置复议原因
	  */
	public void setReason(String reason){
		this.reason=reason;
	}

	/**
	  *获取复议原因
	  */
	public String getReason(){
		return reason;
	}

	/**
	  *设置复议时间
	  */
	public void setReviewtime(Date reviewtime){
		this.reviewtime=reviewtime;
	}

	/**
	  *获取复议时间
	  */
	public Date getReviewtime(){
		return reviewtime;
	}

	/**
	  *设置复议人
	  */
	public void setOperator(String operator){
		this.operator=operator;
	}

	/**
	  *获取复议人
	  */
	public String getOperator(){
		return operator;
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

	public String getReviewstatus() {
		return reviewstatus;
	}

	public void setReviewstatus(String reviewstatus) {
		this.reviewstatus = reviewstatus;
	}

	public Date getRawstarttime() {
		return rawstarttime;
	}

	public void setRawstarttime(Date rawstarttime) {
		this.rawstarttime = rawstarttime;
	}

	public Date getRawendtime() {
		return rawendtime;
	}

	public void setRawendtime(Date rawendtime) {
		this.rawendtime = rawendtime;
	}

	public Double getRawtimes() {
		return rawtimes;
	}

	public void setRawtimes(Double rawtimes) {
		this.rawtimes = rawtimes;
	}

	public Double getRawmileage() {
		return rawmileage;
	}

	public void setRawmileage(Double rawmileage) {
		this.rawmileage = rawmileage;
	}

	public Double getReviewedprice() {
		return reviewedprice;
	}

	public void setReviewedprice(Double reviewedprice) {
		this.reviewedprice = reviewedprice;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public Double getRaworderamount() {
		return raworderamount;
	}

	public void setRaworderamount(Double raworderamount) {
		this.raworderamount = raworderamount;
	}

    public String getPricecopy() {
        return pricecopy;
    }

    public void setPricecopy(String pricecopy) {
        this.pricecopy = pricecopy;
    }

    public String getRawpricecopy() {
        return rawpricecopy;
    }

    public void setRawpricecopy(String rawpricecopy) {
        this.rawpricecopy = rawpricecopy;
    }
}
