package com.szyciov.driver.param;

/**
 * @ClassName DriverMessageParam 
 * @author Efy Shu
 * @Description 司机消息类请求参数
 * @date 2017年4月11日 下午2:55:48 
 */
public class DriverMessageParam extends BaseParam{
	/**
	 * 司机ID
	 */
	private String driverid;
	/**
	 * 消息类型 -1- 所有类型 ,2-推广信息
	 */
	private int type = -1;

	/**
	 * 是否包含已读信息
	 */
	private boolean aboveread = true;
	
	/**
	 * 是否已读(0-未读，1-已读)
	 */
	private int state;
	
	/**
	 * 消息ID
	 */
	private String newsid;

	/**
	 * 是否全部标为已读
	 */
	private boolean allread;
	
	/**************************************Getter & Setter************************************************/

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
	 * 获取0-订单消息1-系统消息  
	 * @return type 0-订单消息1-系统消息  
	 */
	public int getType() {
		return type;
	}

	/**  
	 * 设置0-订单消息1-系统消息  
	 * @param type 0-订单消息1-系统消息  
	 */
	public void setType(int type) {
		this.type = type;
	}
	
	/**  
	 * 获取是否包含已读信息  
	 * @return aboveread 是否包含已读信息  
	 */
	public boolean isAboveread() {
		return aboveread;
	}

	/**  
	 * 设置是否包含已读信息  
	 * @param aboveread 是否包含已读信息  
	 */
	public void setAboveread(boolean aboveread) {
		this.aboveread = aboveread;
	}

	/**  
	 * 获取是否已读(0-未读，1-已读)  
	 * @return state 是否已读(0-未读，1-已读)  
	 */
	public int getState() {
		return state;
	}

	/**  
	 * 设置是否已读(0-未读，1-已读)  
	 * @param state 是否已读(0-未读，1-已读)  
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**  
	 * 获取消息ID  
	 * @return newsid 消息ID  
	 */
	public String getNewsid() {
		return newsid;
	}

	/**  
	 * 设置消息ID  
	 * @param newsid 消息ID  
	 */
	public void setNewsid(String newsid) {
		this.newsid = newsid;
	}

	/**  
	 * 获取是否全部标为已读  
	 * @return allread 是否全部标为已读  
	 */
	public boolean isAllread() {
		return allread;
	}
	
	/**  
	 * 设置是否全部标为已读  
	 * @param allread 是否全部标为已读  
	 */
	public void setAllread(boolean allread) {
		this.allread = allread;
	}
}
