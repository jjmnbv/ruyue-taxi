package com.szyciov.lease.entity;


import java.util.Date;

import com.szyciov.annotation.SzycValid;
import com.szyciov.driver.param.BaseParam;

/**
  * @ClassName LeShiftRule
  * @author Efy
  * @Description 租赁端交接班规则
  * @date 2017年3月1日 14:28:01
  */ 
public class LeShiftRule extends BaseParam{
	/**
	  *id
	  */
	private String id;

	/**
	  *所属租赁公司
	  */
	private String leasescompanyid;

	/**
	  *自主交班时限(分钟)
	  */
	@SzycValid(rules="isSignlessInt")
	private int autoshifttime;

	/**
	  *人工指派时限(分钟)
	  */
	@SzycValid(rules="isSignlessInt")
	private int manualshifttime;

	/**
	  *城市(从字典表中读取)
	  */
	@SzycValid(rules="checkNull")
	private String city;
	
	/**
	 * 城市名称
	 */
	private String cityname;

	/**
	  *创建时间
	  */
	private Date createtime;

	/**
	  *更新时间
	  */
	private Date updatetime;

	/**
	  *创建人
	  */
	private String creater;

	/**
	  *更新人
	  */
	private String updater;

	/**
	  *数据状态
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
	  *设置所属租赁公司
	  */
	public void setLeasescompanyid(String leasescompanyid){
		this.leasescompanyid=leasescompanyid;
	}

	/**
	  *获取所属租赁公司
	  */
	public String getLeasescompanyid(){
		return leasescompanyid;
	}

	/**
	  *设置自主交班时限(分钟)
	  */
	public void setAutoshifttime(int autoshifttime){
		this.autoshifttime=autoshifttime;
	}

	/**
	  *获取自主交班时限(分钟)
	  */
	public int getAutoshifttime(){
		return autoshifttime;
	}

	/**
	  *设置人工指派时限(分钟)
	  */
	public void setManualshifttime(int manualshifttime){
		this.manualshifttime=manualshifttime;
	}

	/**
	  *获取人工指派时限(分钟)
	  */
	public int getManualshifttime(){
		return manualshifttime;
	}

	/**
	  *设置城市(从字典表中读取)
	  */
	public void setCity(String city){
		this.city=city;
	}

	/**
	  *获取城市(从字典表中读取)
	  */
	public String getCity(){
		return city;
	}

	/**  
	 * 获取城市名称  
	 * @return cityname 城市名称  
	 */
	public String getCityname() {
		return cityname;
	}
	
	/**  
	 * 设置城市名称  
	 * @param cityname 城市名称  
	 */
	public void setCityname(String cityname) {
		this.cityname = cityname;
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
	  *设置创建人
	  */
	public void setCreater(String creater){
		this.creater=creater;
	}

	/**
	  *获取创建人
	  */
	public String getCreater(){
		return creater;
	}

	/**
	  *设置更新人
	  */
	public void setUpdater(String updater){
		this.updater=updater;
	}

	/**
	  *获取更新人
	  */
	public String getUpdater(){
		return updater;
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
