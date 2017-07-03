/**
 * 
 */
package com.szyciov.driver.param;

import com.szyciov.annotation.SzycValid;

/**
 * @ClassName ShiftWorkParam 
 * @author Efy Shu
 * @Description 交接班申请参数类
 * @date 2017年3月20日 上午9:52:38 
 */
public class ShiftWorkParam extends BaseParam{
	/**
	 * 接班司机姓名
	 */
	private String name;
	/**
	 * 接班司机手机号
	 */
	@SzycValid(rules={"checkNull","isMobile"})
	private String mobile;
	/**
	 * 司机ID
	 */
	private String driverid;
	/**
	 * 是否转人工
	 */
	private boolean ismantic = false;
	/**
	 * 是否确认接班
	 */
	private boolean confirm = true;
	/**
	 * 司机在线时长(默认半天)
	 */
	private long onlinetime = 43200 * 1000;
	/**
	 * 司机交班标识(用来标记检测是否可以交班,无实际值)
	 */
	@SzycValid(rules={"checkDriverCanShiftOFF"})
	private boolean shiftOFFSign;
	/**
	 * 司机接班标识(用来标记检测是否可以接班,无实际值)
	 */
	@SzycValid(rules={"checkDriverCanShiftON"})
	private boolean shiftONSign;
	/**
	 * 取消交班标识(用来标记检车是否可以取消交班,无实际值)
	 */
	@SzycValid(rules={"checkDriverCanCancelShift"})
	private boolean cancelShiftSign;
	/**
	 * 是否存在交接班规则标识(用来标记是否存在交接班规则,无实际值)
	 */
	@SzycValid(rules={"checkShiftRule"})
	private boolean hasShiftRuleSign;
	/**
	 * 检查是否有未付结订单(参数检测用,无实际值)
	 */
	@SzycValid(rules={"checkNotPayOrder"})
	private boolean hasNotPayOrder = false;
	/**
	 * 检查是否有未出行的即刻单(参数检测用,无实际值)
	 */
	@SzycValid(rules={"checkWaitStartOrder"})
	private boolean hasWaitStartOrder = false;
	
	/**  
	 * 获取接班司机姓名  
	 * @return name 接班司机姓名  
	 */
	public String getName() {
		return name;
	}
	
	/**  
	 * 设置接班司机姓名  
	 * @param name 接班司机姓名  
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**  
	 * 获取手机号  
	 * @return mobile 手机号  
	 */
	public String getMobile() {
		return mobile;
	}
	
	/**  
	 * 设置手机号  
	 * @param mobile 手机号  
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	/**  
	 * 获取是否转人工  
	 * @return ismantic 是否转人工  
	 */
	public boolean isIsmantic() {
		return ismantic;
	}
	
	/**  
	 * 设置是否转人工  
	 * @param ismantic 是否转人工  
	 */
	public void setIsmantic(boolean ismantic) {
		this.ismantic = ismantic;
	}
	
	/**  
	 * 获取是否确认接班  
	 * @return confirm 是否确认接班  
	 */
	public boolean isConfirm() {
		return confirm;
	}
	
	/**  
	 * 设置是否确认接班  
	 * @param confirm 是否确认接班  
	 */
	public void setConfirm(boolean confirm) {
		this.confirm = confirm;
	}

	/**  
	 * 获取司机在线时长  
	 * @return onlinetime 司机在线时长  
	 */
	public long getOnlinetime() {
		return onlinetime;
	}
	
	/**  
	 * 设置司机在线时长  
	 * @param onlinetime 司机在线时长  
	 */
	public void setOnlinetime(long onlinetime) {
		this.onlinetime = onlinetime;
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
}
