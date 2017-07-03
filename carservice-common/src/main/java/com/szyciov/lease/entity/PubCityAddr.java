package com.szyciov.lease.entity;


import java.util.Date;

/**
  * @ClassName PubCityAddr
  * @author Efy
  * @Description 公共信息城市表
  * @date 2016年12月9日 15:43:09
  */ 
public class PubCityAddr{
	/**
	  *主键
	  */
	private String id;

	/**
	  *城市名称
	  */
	private String city;

	/**
	  *城市首字母
	  */
	private String cityInitials;

	/**
	 * 全名称首字母
	 */
	private String fullNameInitials;
	
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
	  *城市全拼
	  */
	private String fullpinyin;

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
	  *设置城市名称
	  */
	public void setCity(String city){
		this.city=city;
	}

	/**
	  *获取城市名称
	  */
	public String getCity(){
		return city;
	}

	/**
	  *设置城市首字母
	  */
	public void setCityInitials(String cityInitials){
		this.cityInitials=cityInitials;
	}

	/**
	  *获取城市首字母
	  */
	public String getCityInitials(){
		return cityInitials;
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

	/**
	  *设置城市全拼
	  */
	public void setFullpinyin(String fullpinyin){
		this.fullpinyin=fullpinyin;
	}

	/**
	  *获取城市全拼
	  */
	public String getFullpinyin(){
		return fullpinyin;
	}

	public String getFullNameInitials() {
		return fullNameInitials;
	}

	public void setFullNameInitials(String fullNameInitials) {
		this.fullNameInitials = fullNameInitials;
	}

}
