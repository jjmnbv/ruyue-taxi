package com.szyciov.entity;


import java.util.Date;

/**
  * @ClassName PubAirportAddr
  * @author Efy
  * @Description 全国机场数据表
  * @date 2016年9月27日 20:29:27
  */ 
public class PubAirPortAddr{
	/**
	  *ID
	  */
	private String id;

	/**
	  *机场所在城市
	  */
	private String city;

	/**
	  *机场名称
	  */
	private String name;

	/**
	  *经度
	  */
	private double lng;

	/**
	  *纬度
	  */
	private double lat;

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
	  *设置机场所在城市
	  */
	public void setCity(String city){
		this.city=city;
	}

	/**
	  *获取机场所在城市
	  */
	public String getCity(){
		return city;
	}

	/**
	  *设置机场名称
	  */
	public void setName(String name){
		this.name=name;
	}

	/**
	  *获取机场名称
	  */
	public String getName(){
		return name;
	}

	/**
	  *设置经度
	  */
	public void setLng(double lng){
		this.lng=lng;
	}

	/**
	  *获取经度
	  */
	public double getLng(){
		return lng;
	}

	/**
	  *设置纬度
	  */
	public void setLat(double lat){
		this.lat=lat;
	}

	/**
	  *获取纬度
	  */
	public double getLat(){
		return lat;
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
