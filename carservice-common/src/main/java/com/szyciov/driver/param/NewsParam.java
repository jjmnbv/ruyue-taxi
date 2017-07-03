/**
 * 
 */
package com.szyciov.driver.param;

/**
 * @ClassName ReadNewsParam
 * @author Efy Shu
 * @Description 司机阅读消息参数类
 * @date 2016年10月10日 下午4:11:02
 */
public class NewsParam extends BaseParam {
	/**
	 * 司机ID
	 */
	private String driverid;
	/**
	 * 消息类型 -1- 所有类型 0-订单消息,1-系统消息,2-推广信息,3-其它
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
	 * 获取消息类型0-订单消息1-系统消息2-推广信息3-其它  
	 * @return type 消息类型0-订单消息1-系统消息2-推广信息3-其它  
	 */
	public int getType() {
		return type;
	}
	

	/**  
	 * 设置消息类型0-订单消息1-系统消息2-推广信息3-其它  
	 * @param type 消息类型0-订单消息1-系统消息2-推广信息3-其它  
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
	 * 
	 * @return newsid 消息ID
	 */
	public String getNewsid() {
		return newsid;
	}

	/**
	 * 设置消息ID
	 * 
	 * @param newsid
	 *            消息ID
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
