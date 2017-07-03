package com.szyciov.op.entity;


import java.util.Date;

/**
  * @ClassName PeMostContact
  * @author Efy
  * @Description 个人用户常用联系人
  * @date 2016年10月23日 11:00:23
  */ 
public class PeMostContact{
	/**
	  *id
	  */
	private String id;

	/**
	  *与用户表主键关联
	  */
	private String userid;

	/**
	  *姓名
	  */
	private String name;

	/**
	  *电话
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
	  *设置姓名
	  */
	public void setName(String name){
		this.name=name;
	}

	/**
	  *获取姓名
	  */
	public String getName(){
		return name;
	}

	/**
	  *设置电话
	  */
	public void setPhone(String phone){
		this.phone=phone;
	}

	/**
	  *获取电话
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
