package com.szyciov.driver.entity;


import java.util.Date;

/**
  * @ClassName PubDriverLoginLog
  * @author Efy
  * @Description 司机登录日志
  * @date 2016年10月21日 15:24:21
  */ 
public class PubDriverLoginLog{
	/**
	  *id
	  */
	private String id;

	/**
	  *与司机表主键关联
	  */
	private String driverid;

	/**
	  *ipaddr
	  */
	private String ipaddr;

	/**
	  *1-android，2-ios
	  */
	private String device;

	/**
	  *例：IOS9.0.1
	  */
	private String version;

	/**
	  *App版本
	  */
	private String appversion;

	/**
	  *例：IPHONE，HUAWEI
	  */
	private String phonebrand;

	/**
	  *例：6,6S
	  */
	private String phonemodel;

	/**
	  *0-登陆成功，1-登陆失败，2-登陆异常
	  */
	private String loginstatus;

	/**
	  *logintime
	  */
	private Date logintime;

	/**
	  *createtime
	  */
	private Date createtime;

	/**
	  *updatetime
	  */
	private Date updatetime;

	/**
	  *status
	  */
	private int status;

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
	  *设置与司机表主键关联
	  */
	public void setDriverid(String driverid){
		this.driverid=driverid;
	}

	/**
	  *获取与司机表主键关联
	  */
	public String getDriverid(){
		return driverid;
	}

	/**
	  *设置ipaddr
	  */
	public void setIpaddr(String ipaddr){
		this.ipaddr=ipaddr;
	}

	/**
	  *获取ipaddr
	  */
	public String getIpaddr(){
		return ipaddr;
	}

	/**
	  *设置1-android，2-ios
	  */
	public void setDevice(String device){
		this.device=device;
	}

	/**
	  *获取1-android，2-ios
	  */
	public String getDevice(){
		return device;
	}

	/**
	  *设置例：IOS9.0.1
	  */
	public void setVersion(String version){
		this.version=version;
	}

	/**
	  *获取例：IOS9.0.1
	  */
	public String getVersion(){
		return version;
	}

	/**
	  *设置App版本
	  */
	public void setAppversion(String appversion){
		this.appversion=appversion;
	}

	/**
	  *获取App版本
	  */
	public String getAppversion(){
		return appversion;
	}

	/**
	  *设置例：IPHONE，HUAWEI
	  */
	public void setPhonebrand(String phonebrand){
		this.phonebrand=phonebrand;
	}

	/**
	  *获取例：IPHONE，HUAWEI
	  */
	public String getPhonebrand(){
		return phonebrand;
	}

	/**
	  *设置例：6,6S
	  */
	public void setPhonemodel(String phonemodel){
		this.phonemodel=phonemodel;
	}

	/**
	  *获取例：6,6S
	  */
	public String getPhonemodel(){
		return phonemodel;
	}

	/**
	  *设置0-登陆成功，1-登陆失败，2-登陆异常
	  */
	public void setLoginstatus(String loginstatus){
		this.loginstatus=loginstatus;
	}

	/**
	  *获取0-登陆成功，1-登陆失败，2-登陆异常
	  */
	public String getLoginstatus(){
		return loginstatus;
	}

	/**
	  *设置logintime
	  */
	public void setLogintime(Date logintime){
		this.logintime=logintime;
	}

	/**
	  *获取logintime
	  */
	public Date getLogintime(){
		return logintime;
	}

	/**
	  *设置createtime
	  */
	public void setCreatetime(Date createtime){
		this.createtime=createtime;
	}

	/**
	  *获取createtime
	  */
	public Date getCreatetime(){
		return createtime;
	}

	/**
	  *设置updatetime
	  */
	public void setUpdatetime(Date updatetime){
		this.updatetime=updatetime;
	}

	/**
	  *获取updatetime
	  */
	public Date getUpdatetime(){
		return updatetime;
	}

	/**
	  *设置status
	  */
	public void setStatus(int status){
		this.status=status;
	}

	/**
	  *获取status
	  */
	public int getStatus(){
		return status;
	}

}
