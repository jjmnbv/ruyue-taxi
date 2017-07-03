/**
 * 
 */
package com.szyciov.driver.param;

/**
 * @ClassName LogoutParam 
 * @author Efy Shu
 * @Description TODO(这里用一句话描述这个类的作用) 
 * @date 2016年11月17日 下午4:40:55 
 */
public class LogoutParam extends BaseParam{
	/**
	 * 是否异常登出
	 */
	private boolean exception = false;

	/**
	 * 司机ID
	 */
	private String driverid;
	
	/**  
	 * 获取是否异常登出  
	 * @return exception 是否异常登出  
	 */
	public boolean isException() {
		return exception;
	}

	/**  
	 * 设置是否异常登出  
	 * @param exception 是否异常登出  
	 */
	public void setException(boolean exception) {
		this.exception = exception;
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
