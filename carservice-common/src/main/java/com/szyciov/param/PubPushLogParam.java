/**
 * 
 */
package com.szyciov.param;

import java.util.List;

import com.szyciov.driver.param.BaseParam;

/**
 * @ClassName PubPushLogParam 
 * @author Efy Shu
 * @Description TODO(这里用一句话描述这个类的作用) 
 * @date 2017年6月12日 上午10:38:02 
 */
public class PubPushLogParam extends BaseParam {
	/**
	 * 订单号
	 */
	private String orderno;
	/**
	 * 司机id
	 */
	private String driverid;
	/**
	 * 司机手机号
	 */
	private List<String> driverphones;
	/**
	 * 安卓消息id
	 */
	private Long androidmsgid;
	/**
	 * ios消息id
	 */
	private Long iosmsgid;
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
	 * 获取司机id  
	 * @return driverid 司机id  
	 */
	public String getDriverid() {
		return driverid;
	}
	
	/**  
	 * 设置司机id  
	 * @param driverid 司机id  
	 */
	public void setDriverid(String driverid) {
		this.driverid = driverid;
	}

	/**  
	 * 获取司机手机号  
	 * @return driverphones 司机手机号  
	 */
	public List<String> getDriverphones() {
		return driverphones;
	}
	
	/**  
	 * 设置司机手机号  
	 * @param driverphones 司机手机号  
	 */
	public void setDriverphones(List<String> driverphones) {
		this.driverphones = driverphones;
	}

	/**  
	 * 获取安卓消息id  
	 * @return androidmsgid 安卓消息id  
	 */
	public Long getAndroidmsgid() {
		return androidmsgid;
	}

	/**  
	 * 设置安卓消息id  
	 * @param androidmsgid 安卓消息id  
	 */
	public void setAndroidmsgid(Long androidmsgid) {
		this.androidmsgid = androidmsgid;
	}
	
	/**  
	 * 获取ios消息id  
	 * @return iosmsgid ios消息id  
	 */
	public Long getIosmsgid() {
		return iosmsgid;
	}
	
	/**  
	 * 设置ios消息id  
	 * @param iosmsgid ios消息id  
	 */
	public void setIosmsgid(Long iosmsgid) {
		this.iosmsgid = iosmsgid;
	}
	
}
