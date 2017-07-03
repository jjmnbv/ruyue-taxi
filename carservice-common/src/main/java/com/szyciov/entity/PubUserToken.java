package com.szyciov.entity;


import java.util.Date;

/**
  * @ClassName PubUserToken
  * @author Efy
  * @Description 实体类描述
  * @date 2016年9月13日 10:17:13
  */ 
public class PubUserToken{
	/**
	  *ID
	  */
	private String id;

	/**
	  *用户ID
	  */
	private String userid;
	/**
	  *0-机构用户，1-个人用户，2-司机，3-租赁，4-机构端
	  */
	private String usertype;
	/**
	 * 设备唯一标识
	 */
	private String uuid;
	/**
	  *用户令牌
	  */
	private String usertoken;

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
	  *设置ID
	  */
	public void setId(String id){
		this.id=id;
	}

	/**
	  *获取ID
	  */
	public String getId(){
		return id;
	}

	/**
	  *设置用户ID
	  */
	public void setUserid(String userid){
		this.userid=userid;
	}

	/**
	  *获取用户ID
	  */
	public String getUserid(){
		return userid;
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
	 * 获取设备唯一标识  
	 * @return uuid 设备唯一标识  
	 */
	public String getUuid() {
		return uuid;
	}
	
	/**  
	 * 设置设备唯一标识  
	 * @param uuid 设备唯一标识  
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	/**
	  *设置用户令牌
	  */
	public void setUsertoken(String usertoken){
		this.usertoken=usertoken;
	}

	/**
	  *获取用户令牌
	  */
	public String getUsertoken(){
		return usertoken;
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
