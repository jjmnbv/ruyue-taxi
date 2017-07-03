package com.szyciov.entity;

import java.util.Date;

/**
 * Created by shikang on 2017/6/7.
 */
public class PubJpushlog {

    /**
     * 主键
     */
    private String id;

    /**
     * 订单号
     */
    private String orderno;

    /**
     * 极光推送状态(0-推送失败，1-推送成功，默认为0)
     */
    private Integer pushstate;

    /**
     * 手机推送状态(0-推送失败，1-推送成功，默认为0)
     */
    private Integer phonepushstate;

    /**
     * 举手状态(0-未举手，1-已举手，默认为0)
     */
    private Integer handstate;

    /**
     * 接单状态(0-接单失败，1-接单成功，默认为0)
     */
    private Integer takeorderstate;

    /**
     * 司机ID
     */
    private String driverid;

    /**
     * 推送时间
     */
    private Date sendtime;

    /**
     * 举手时间
     */
    private Date handtime;

    /**
     * 设备ID
     */
    private String registrationid;

    /**
     * 安卓messageid
     */
    private Long androidmsgid;

    /**
     * 苹果messageid
     */
    private Long iosmsgid;

    /**
     * 推送类型(0-司机接单推送)
     */
    private Integer pushtype;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 更新时间
     */
    private Date updatetime;

    /**
     * 数据状态
     */
    private Integer status;

    /**
     * 司机手机号
     */
    private String driverphone;

    /**
     * 司机姓名
     * @author xuxxtr
     */
    private String drivername;
    
	/**  
	 * 获取主键  
	 * @return id 主键  
	 */
	public String getId() {
		return id;
	}
	

	/**  
	 * 设置主键  
	 * @param id 主键  
	 */
	public void setId(String id) {
		this.id = id;
	}
	

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
	 * 获取极光推送状态(0-推送失败，1-推送成功，默认为0)  
	 * @return pushstate 极光推送状态(0-推送失败，1-推送成功，默认为0)  
	 */
	public Integer getPushstate() {
		return pushstate;
	}
	

	/**  
	 * 设置极光推送状态(0-推送失败，1-推送成功，默认为0)  
	 * @param pushstate 极光推送状态(0-推送失败，1-推送成功，默认为0)  
	 */
	public void setPushstate(Integer pushstate) {
		this.pushstate = pushstate;
	}
	

	/**  
	 * 获取手机推送状态(0-推送失败，1-推送成功，默认为0)  
	 * @return phonepushstate 手机推送状态(0-推送失败，1-推送成功，默认为0)  
	 */
	public Integer getPhonepushstate() {
		return phonepushstate;
	}
	

	/**  
	 * 设置手机推送状态(0-推送失败，1-推送成功，默认为0)  
	 * @param phonepushstate 手机推送状态(0-推送失败，1-推送成功，默认为0)  
	 */
	public void setPhonepushstate(Integer phonepushstate) {
		this.phonepushstate = phonepushstate;
	}
	

	/**  
	 * 获取举手状态(0-未举手，1-已举手，默认为0)  
	 * @return handstate 举手状态(0-未举手，1-已举手，默认为0)  
	 */
	public Integer getHandstate() {
		return handstate;
	}
	

	/**  
	 * 设置举手状态(0-未举手，1-已举手，默认为0)  
	 * @param handstate 举手状态(0-未举手，1-已举手，默认为0)  
	 */
	public void setHandstate(Integer handstate) {
		this.handstate = handstate;
	}
	

	/**  
	 * 获取接单状态(0-接单失败，1-接单成功，默认为0)  
	 * @return takeorderstate 接单状态(0-接单失败，1-接单成功，默认为0)  
	 */
	public Integer getTakeorderstate() {
		return takeorderstate;
	}
	

	/**  
	 * 设置接单状态(0-接单失败，1-接单成功，默认为0)  
	 * @param takeorderstate 接单状态(0-接单失败，1-接单成功，默认为0)  
	 */
	public void setTakeorderstate(Integer takeorderstate) {
		this.takeorderstate = takeorderstate;
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
	 * 获取推送时间  
	 * @return sendtime 推送时间  
	 */
	public Date getSendtime() {
		return sendtime;
	}
	

	/**  
	 * 设置推送时间  
	 * @param sendtime 推送时间  
	 */
	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}
	

	/**  
	 * 获取举手时间  
	 * @return handtime 举手时间  
	 */
	public Date getHandtime() {
		return handtime;
	}
	

	/**  
	 * 设置举手时间  
	 * @param handtime 举手时间  
	 */
	public void setHandtime(Date handtime) {
		this.handtime = handtime;
	}
	

	/**  
	 * 获取设备ID  
	 * @return registrationid 设备ID  
	 */
	public String getRegistrationid() {
		return registrationid;
	}
	

	/**  
	 * 设置设备ID  
	 * @param registrationid 设备ID  
	 */
	public void setRegistrationid(String registrationid) {
		this.registrationid = registrationid;
	}
	

	/**  
	 * 获取安卓messageid  
	 * @return androidmsgid 安卓messageid  
	 */
	public Long getAndroidmsgid() {
		return androidmsgid;
	}
	

	/**  
	 * 设置安卓messageid  
	 * @param androidmsgid 安卓messageid  
	 */
	public void setAndroidmsgid(Long androidmsgid) {
		this.androidmsgid = androidmsgid;
	}
	

	/**  
	 * 获取苹果messageid  
	 * @return iosmsgid 苹果messageid  
	 */
	public Long getIosmsgid() {
		return iosmsgid;
	}
	

	/**  
	 * 设置苹果messageid  
	 * @param iosmsgid 苹果messageid  
	 */
	public void setIosmsgid(Long iosmsgid) {
		this.iosmsgid = iosmsgid;
	}
	

	/**  
	 * 获取推送类型(0-司机接单推送)  
	 * @return pushtype 推送类型(0-司机接单推送)  
	 */
	public Integer getPushtype() {
		return pushtype;
	}
	

	/**  
	 * 设置推送类型(0-司机接单推送)  
	 * @param pushtype 推送类型(0-司机接单推送)  
	 */
	public void setPushtype(Integer pushtype) {
		this.pushtype = pushtype;
	}
	

	/**  
	 * 获取创建时间  
	 * @return createtime 创建时间  
	 */
	public Date getCreatetime() {
		return createtime;
	}
	

	/**  
	 * 设置创建时间  
	 * @param createtime 创建时间  
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	

	/**  
	 * 获取更新时间  
	 * @return updatetime 更新时间  
	 */
	public Date getUpdatetime() {
		return updatetime;
	}
	

	/**  
	 * 设置更新时间  
	 * @param updatetime 更新时间  
	 */
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	

	/**  
	 * 获取数据状态  
	 * @return status 数据状态  
	 */
	public Integer getStatus() {
		return status;
	}
	

	/**  
	 * 设置数据状态  
	 * @param status 数据状态  
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

    public String getDriverphone() {
        return driverphone;
    }

    public void setDriverphone(String driverphone) {
        this.driverphone = driverphone;
    }


	public String getDrivername() {
		return drivername;
	}


	public void setDrivername(String drivername) {
		this.drivername = drivername;
	}
    
    
}
