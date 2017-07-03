package com.szyciov.entity;


import java.util.Date;

/**
  * @ClassName PubSmsToken
  * @author Efy
  * @Description 短信记录表
  * @date 2016年9月18日 15:24:18
  */ 
public class PubSmsToken{
	/**
	  *主键
	  */
	private String id;

	/**
	  *短信类型：0-登陆，1-修改密码，2-注册
	  */
	private String smstype;

	/**
	  *0-机构用户，1-个人用户，2-司机，3-租赁，4-机构端
	  */
	private String usertype;

	/**
	  *userid(手机号)
	  */
	private String username;

	/**
	  *短信内容
	  */
	private String smscode;

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
	  *设置主键
	  */
	public void setId(String id){
		this.id=id;
	}

	/**
	  *获取主键
	  */
	public String getId(){
		return id;
	}

	/**
	  *设置短信类型：0-登陆，1-修改密码，2-注册
	  */
	public void setSmstype(String smstype){
		this.smstype=smstype;
	}

	/**
	  *获取短信类型：0-登陆，1-修改密码，2-注册
	  */
	public String getSmstype(){
		return smstype;
	}

	/**
	  *设置0-机构用户，1-个人用户，2-司机，3-租赁，4-机构端
	  */
	public void setUsertype(String usertype){
		this.usertype=usertype;
	}

	/**
	  *获取0-机构用户，1-个人用户，2-司机，3-租赁，4-机构端
	  */
	public String getUsertype(){
		return usertype;
	}

	/**
	  *设置username(存手机号)
	  */
	public void setUsername(String username){
		this.username=username;
	}

	/**
	  *获取username(手机号)
	  */
	public String getUsername(){
		return username;
	}

	/**
	  *设置短信内容
	  */
	public void setSmscode(String smscode){
		this.smscode=smscode;
	}

	/**
	  *获取短信内容
	  */
	public String getSmscode(){
		return smscode;
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

}
