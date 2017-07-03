package com.szyciov.entity;


import java.util.Date;
import com.szyciov.driver.param.BaseParam;

/**
  * @ClassName PubDrivertrack
  * @author Efy
  * @Description 司机轨迹实体类
  * @date 2017年4月6日 10:48:06
  */ 
public class PubDrivertrack extends BaseParam{
	/**
	  *主键
	  */
	private String id;

	/**
	  *与司机表主键关联
	  */
	private String driverid;

	/**
	  *GPS速度
	  */
	private double gpsspeed;

	/**
	  *GPS方向
	  */
	private double gpsdirection;

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
	  *设置GPS速度
	  */
	public void setGpsspeed(double gpsspeed){
		this.gpsspeed=gpsspeed;
	}

	/**
	  *获取GPS速度
	  */
	public double getGpsspeed(){
		return gpsspeed;
	}

	/**
	  *设置GPS方向
	  */
	public void setGpsdirection(double gpsdirection){
		this.gpsdirection=gpsdirection;
	}

	/**
	  *获取GPS方向
	  */
	public double getGpsdirection(){
		return gpsdirection;
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
