package com.szyciov.op.entity;


import java.util.Date;

/**
  * @ClassName PeMostAddress
  * @author Efy
  * @Description 个人用户常用地址
  * @date 2016年10月23日 11:00:23
  */ 
public class PeMostAddress{
	/**
	  *id
	  */
	private String id;

	/**
	  *与用户表主键关联
	  */
	private String userid;

	/**
	  *0-家，1-公司，2-其他
	  */
	private String addresstype;

	/**
	  *城市ID
	  */
	private String city;

	/**
	  *详细地址
	  */
	private String address;
	
	/**
	 * 地址名称
	 */
	private String title;

	/**
	  *lng
	  */
	private double lng;

	/**
	  *lat
	  */
	private double lat;

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
	 * 城市名(中文)
	 */
	private String citycaption;

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
	  *设置0-家，1-公司，2-其他
	  */
	public void setAddresstype(String addresstype){
		this.addresstype=addresstype;
	}

	/**
	  *获取0-家，1-公司，2-其他
	  */
	public String getAddresstype(){
		return addresstype;
	}

	/**
	  *设置城市ID
	  */
	public void setCity(String city){
		this.city=city;
	}

	/**
	  *获取城市ID
	  */
	public String getCity(){
		return city;
	}

	/**
	  *设置详细地址
	  */
	public void setAddress(String address){
		this.address=address;
	}

	/**
	  *获取详细地址
	  */
	public String getAddress(){
		return address;
	}

	/**  
	 * 获取地址名称  
	 * @return title 地址名称  
	 */
	public String getTitle() {
		return title;
	}

	/**  
	 * 设置地址名称  
	 * @param title 地址名称  
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	  *设置lng
	  */
	public void setLng(double lng){
		this.lng=lng;
	}

	/**
	  *获取lng
	  */
	public double getLng(){
		return lng;
	}

	/**
	  *设置lat
	  */
	public void setLat(double lat){
		this.lat=lat;
	}

	/**
	  *获取lat
	  */
	public double getLat(){
		return lat;
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

	/**  
	 * 获取城市名(中文)  
	 * @return citycaption 城市名(中文)  
	 */
	public String getCitycaption() {
		return citycaption;
	}
	

	/**  
	 * 设置城市名(中文)  
	 * @param citycaption 城市名(中文)  
	 */
	public void setCitycaption(String citycaption) {
		this.citycaption = citycaption;
	}
}
