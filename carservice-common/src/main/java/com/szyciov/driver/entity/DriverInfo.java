package com.szyciov.driver.entity;

import com.szyciov.util.StringUtil;

public class DriverInfo {
	/**
	 * 司机姓名
	 */
	private String drivername;
	
	/**
	 * 司机电话
	 */
	private String driverphone;
	/**
	 * 司机头像(小)
	 */
	private String headportraitmin;
	/**
	 * 司机头像(大)
	 */
	private String headportraitmax;
	/**
	 * 评价星级
	 */
	private double driverrate;
	/**
	 * 累计订单
	 */
	private int supportorder;
	/**
	 * 车牌号码
	 */
	private String plateno;
	/**
	 * 汽车品牌
	 */
	private String carbrand;
	/**
	 * 汽车车系
	 */
	private String carvehcline;
	/**
	 * 司机编号
	 */
	private String jobnum;
	
	/**
	 * 车辆颜色
	 */
	private String color;
	
	/**
	 * toB业务车型名称
	 */
	private String cartype;
	
	/**
	 * toC业务车型名称
	 */
	private String opcartype;

	/**
	 * 工作状态(0-空闲 1-服务中 2-下线)
	 */
	private String workstatus;
	
	/**
	 * 交接班状态
	 */
	private String passworkstatus;
	/**
	 * 是否更改过提现密码
	 */
	private boolean wdpchanged;
	/**
	 * 是否有对班司机
	 */
	private boolean hasworkmate;
	/**
	 * 客服电话
	 */
	private String contact;
	/**
	 * 司机类型(0-网约车 1-出租车)
	 */
	private String drivertype;
	
	/**  
	 * 获取toC业务车型名称  
	 * @return opcartype toC业务车型名称  
	 */
	public String getOpcartype() {
		return opcartype;
	}
	


	/**  
	 * 设置toC业务车型名称  
	 * @param opcartype toC业务车型名称  
	 */
	public void setOpcartype(String opcartype) {
		this.opcartype = opcartype;
	}
	


	/**  
	 * 获取司机电话  
	 * @return driverphone 司机电话  
	 */
	public String getDriverphone() {
		return driverphone;
	}
	

	/**  
	 * 设置司机电话  
	 * @param driverphone 司机电话  
	 */
	public void setDriverphone(String driverphone) {
		this.driverphone = driverphone;
	}
	

	/**  
	 * 获取工作状态(0-空闲1-服务中2-下线)  
	 * @return workstatus 工作状态(0-空闲1-服务中2-下线)  
	 */
	public String getWorkstatus() {
		return workstatus;
	}
	
	/**  
	 * 设置工作状态(0-空闲1-服务中2-下线)  
	 * @param workstatus 工作状态(0-空闲1-服务中2-下线)  
	 */
	public void setWorkstatus(String workstatus) {
		this.workstatus = workstatus;
	}

	/**
	 * 获取司机姓名
	 * 
	 * @return drivername 司机姓名
	 */
	public String getDrivername() {
		return drivername;
	}

	/**
	 * 设置司机姓名
	 * 
	 * @param drivername
	 *            司机姓名
	 */
	public void setDrivername(String drivername) {
		this.drivername = drivername;
	}

	/**
	 * 获取司机头像(小)
	 * 
	 * @return headportraitmin 司机头像(小)
	 */
	public String getHeadportraitmin() {
		return headportraitmin;
	}

	/**
	 * 设置司机头像(小)
	 * 
	 * @param headportraitmin
	 *            司机头像(小)
	 */
	public void setHeadportraitmin(String headportraitmin) {
		this.headportraitmin = headportraitmin;
	}

	/**
	 * 获取司机头像(大)
	 * 
	 * @return headportraitmax 司机头像(大)
	 */
	public String getHeadportraitmax() {
		return headportraitmax;
	}

	/**
	 * 设置司机头像(大)
	 * 
	 * @param headportraitmax
	 *            司机头像(大)
	 */
	public void setHeadportraitmax(String headportraitmax) {
		this.headportraitmax = headportraitmax;
	}

	/**
	 * 获取评价星级
	 * 
	 * @return driverrate 评价星级
	 */
	public double getDriverrate() {
		driverrate = StringUtil.formatNum(driverrate, 1);
		return driverrate;
	}

	/**
	 * 设置评价星级
	 * 
	 * @param driverrate
	 *            评价星级
	 */
	public void setDriverrate(double driverrate) {
		this.driverrate = driverrate;
	}

	/**
	 * 获取累计订单
	 * 
	 * @return supportorder 累计订单
	 */
	public int getSupportorder() {
		return supportorder;
	}

	/**
	 * 设置累计订单
	 * 
	 * @param supportorder
	 *            累计订单
	 */
	public void setSupportorder(int supportorder) {
		this.supportorder = supportorder;
	}

	/**
	 * 获取车牌号码
	 * 
	 * @return plateno 车牌号码
	 */
	public String getPlateno() {
		return plateno;
	}

	/**
	 * 设置车牌号码
	 * 
	 * @param plateno
	 *            车牌号码
	 */
	public void setPlateno(String plateno) {
		this.plateno = plateno;
	}

	/**
	 * 获取汽车品牌
	 * 
	 * @return carbrand 汽车品牌
	 */
	public String getCarbrand() {
		return carbrand;
	}

	/**
	 * 设置汽车品牌
	 * 
	 * @param carbrand
	 *            汽车品牌
	 */
	public void setCarbrand(String carbrand) {
		this.carbrand = carbrand;
	}

	/**
	 * 获取汽车车系
	 * 
	 * @return carvehcline 汽车车系
	 */
	public String getCarvehcline() {
		return carvehcline;
	}

	/**
	 * 设置汽车车系
	 * 
	 * @param carvehcline
	 *            汽车车系
	 */
	public void setCarvehcline(String carvehcline) {
		this.carvehcline = carvehcline;
	}

	/**
	 * 获取司机编号
	 * 
	 * @return jobnum 司机编号
	 */
	public String getJobnum() {
		return jobnum;
	}

	/**
	 * 设置司机编号
	 * 
	 * @param jobnum
	 *            司机编号
	 */
	public void setJobnum(String jobnum) {
		this.jobnum = jobnum;
	}

	/**  
	 * 获取车辆颜色  
	 * @return color 车辆颜色  
	 */
	public String getColor() {
		return color;
	}
	

	/**  
	 * 设置车辆颜色  
	 * @param color 车辆颜色  
	 */
	public void setColor(String color) {
		this.color = color;
	}
	

	/**  
	 * 获取车型名称  
	 * @return cartype 车型名称  
	 */
	public String getCartype() {
		return cartype;
	}

	/**  
	 * 设置车型名称  
	 * @param cartype 车型名称  
	 */
	public void setCartype(String cartype) {
		this.cartype = cartype;
	}

	/**  
	 * 获取是否更改过提现密码  
	 * @return wdpchanged 是否更改过提现密码  
	 */
	public boolean isWdpchanged() {
		return wdpchanged;
	}

	/**  
	 * 设置是否更改过提现密码  
	 * @param wdpchanged 是否更改过提现密码  
	 */
	public void setWdpchanged(boolean wdpchanged) {
		this.wdpchanged = wdpchanged;
	}

	/**  
	 * 获取交接班状态  
	 * @return passworkstatus 交接班状态  
	 */
	public String getPassworkstatus() {
		if(!hasworkmate) passworkstatus = "0";
		return passworkstatus;
	}

	/**  
	 * 设置交接班状态  
	 * @param passworkstatus 交接班状态  
	 */
	public void setPassworkstatus(String passworkstatus) {
		this.passworkstatus = passworkstatus;
	}

	/**  
	 * 获取是否有对班司机  
	 * @return hasworkmate 是否有对班司机  
	 */
	public boolean isHasworkmate() {
		return hasworkmate;
	}
	
	/**  
	 * 设置是否有对班司机  
	 * @param hasworkmate 是否有对班司机  
	 */
	public void setHasworkmate(boolean hasworkmate) {
		this.hasworkmate = hasworkmate;
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
	 * 获取司机类型(0-网约车1-出租车)  
	 * @return drivertype 司机类型(0-网约车1-出租车)  
	 */
	public String getDrivertype() {
		return drivertype;
	}
	
	/**  
	 * 设置司机类型(0-网约车1-出租车)  
	 * @param drivertype 司机类型(0-网约车1-出租车)  
	 */
	public void setDrivertype(String drivertype) {
		this.drivertype = drivertype;
	}
}
