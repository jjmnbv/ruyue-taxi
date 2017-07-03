package com.szyciov.op.param;

import com.szyciov.param.QueryParam;

/**
 * 司机举手日志查询参数
 * @author xuxxtr
 *
 */
public class JPushLogQueryParam extends QueryParam{

	/**
	 * 订单号
	 */
	private String orderno;
	
	/**
	 * 司机手机号码
	 */
	private String driverphone;

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public String getDriverphone() {
		return driverphone;
	}

	public void setDriverphone(String driverphone) {
		this.driverphone = driverphone;
	}

	public JPushLogQueryParam() {
		super();
	}
	
}
