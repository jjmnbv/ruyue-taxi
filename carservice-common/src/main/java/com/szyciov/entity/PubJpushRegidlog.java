

package com.szyciov.entity;


import java.util.Date;
import com.szyciov.driver.param.BaseParam;

/**
  * @ClassName PubJpushRegidlog
  * @author Efy Shu
  * @Description 极光推送ID变更历史记录表
  * @date 2017年6月11日 17:17:11
  */ 
public class PubJpushRegidlog extends BaseParam{
	/**
	  *主键
	  */
	private String id;

	/**
	  *设备ID
	  */
	private String registrationid;

	/**
	  *用户ID
	  */
	private String userid;

	/**
	  *用户类型(0-司机，1-机构用户，2-个人用户)
	  */
	private Integer usertype;

	/**
	  *记录时间
	  */
	private Date recordtime;

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
	private Integer status;

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
	  *设置设备ID
	  */
	public void setRegistrationid(String registrationid){
		this.registrationid=registrationid;
	}

	/**
	  *获取设备ID
	  */
	public String getRegistrationid(){
		return registrationid;
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
	  *设置用户类型(0-司机，1-机构用户，2-个人用户)
	  */
	public void setUsertype(Integer usertype){
		this.usertype=usertype;
	}

	/**
	  *获取用户类型(0-司机，1-机构用户，2-个人用户)
	  */
	public Integer getUsertype(){
		return usertype;
	}

	/**
	  *设置记录时间
	  */
	public void setRecordtime(Date recordtime){
		this.recordtime=recordtime;
	}

	/**
	  *获取记录时间
	  */
	public Date getRecordtime(){
		return recordtime;
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
	public void setStatus(Integer status){
		this.status=status;
	}

	/**
	  *获取数据状态
	  */
	public Integer getStatus(){
		return status;
	}

}
