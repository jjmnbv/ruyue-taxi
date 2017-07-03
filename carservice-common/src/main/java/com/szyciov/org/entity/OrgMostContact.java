package com.szyciov.org.entity;


import java.util.Date;

/**
  * @ClassName OrgMostContact
  * @author Efy
  * @Description 机构用户常用联系人
  * @date 2016年10月18日 12:00:18
  */ 
public class OrgMostContact{
	/**
	  *id
	  */
	private String id;

	/**
	  *与用户表主键关联
	  */
	private String userid;

	/**
	  *name
	  */
	private String name;

	/**
	  *phone
	  */
	private String phone;

	/**
	  *createtime
	  */
	private Date createtime;

	/**
	  *updatetime
	  */
	private Date updatetime;

	/**
	  *creater
	  */
	private String creater;

	/**
	  *updater
	  */
	private String updater;

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
	  *设置与用户表主键关联
	  */
	public void setUserid(String userid){
		this.userid=userid;
	}

	/**
	  *获取与用户表主键关联
	  */
	public String getUserid(){
		return userid;
	}

	/**
	  *设置name
	  */
	public void setName(String name){
		this.name=name;
	}

	/**
	  *获取name
	  */
	public String getName(){
		return name;
	}

	/**
	  *设置phone
	  */
	public void setPhone(String phone){
		this.phone=phone;
	}

	/**
	  *获取phone
	  */
	public String getPhone(){
		return phone;
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
	  *设置creater
	  */
	public void setCreater(String creater){
		this.creater=creater;
	}

	/**
	  *获取creater
	  */
	public String getCreater(){
		return creater;
	}

	/**
	  *设置updater
	  */
	public void setUpdater(String updater){
		this.updater=updater;
	}

	/**
	  *获取updater
	  */
	public String getUpdater(){
		return updater;
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
