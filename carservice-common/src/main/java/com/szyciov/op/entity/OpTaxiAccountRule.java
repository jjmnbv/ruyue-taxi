package com.szyciov.op.entity;


import java.util.Date;

import com.szyciov.annotation.SzycValid;
import com.szyciov.driver.param.BaseParam;

/**
  * @ClassName OpTaxiAccountRule
  * @author Efy
  * @Description 运管端出租车计费规则
  * @date 2017年3月10日 10:01:10
  */ 
public class OpTaxiAccountRule extends BaseParam{
	/**
	  *id
	  */
	private String id;

	/**
	  *城市从字典表中读取
	  */
	@SzycValid(rules={"checkCity"})
	private String city;

	/**
	  *起租价
	  */
	@SzycValid(rules={"checkNumNotZero","checkNumLimit"})
	private double startprice;

	/**
	  *起租里程（米）
	  */
	@SzycValid(rules={"checkNumNotZero","checkNumLimit"})
	private double startrange;

	/**
	  *附加费
	  */
	@SzycValid(rules={"checkNumNotZero","checkNumLimit"})
	private double surcharge;

	/**
	  *空驶费率
	  */
	@SzycValid(rules={"checkNumNotZero","checkNumLimit"})
	private double emptytravelrate;

	/**
	  *标准里程（米）
	  */
	@SzycValid(rules={"checkNumNotZero","checkNumLimit"})
	private double standardrange;

	/**
	  *续租价
	  */
	@SzycValid(rules={"checkNumNotZero","checkNumLimit"})
	private double renewalprice;

	/**
	  *规则状态(0-启用,1-禁用)
	  */
	private String rulesstate;

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
	 * 城市名称
	 */
	private String cityname;

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
	  *设置城市从字典表中读取
	  */
	public void setCity(String city){
		this.city=city;
	}

	/**
	  *获取城市从字典表中读取
	  */
	public String getCity(){
		return city;
	}

	/**
	  *设置起租价
	  */
	public void setStartprice(double startprice){
		this.startprice=startprice;
	}

	/**
	  *获取起租价
	  */
	public double getStartprice(){
		return startprice;
	}

	/**
	  *设置起租里程（米）
	  */
	public void setStartrange(double startrange){
		this.startrange=startrange;
	}

	/**
	  *获取起租里程（米）
	  */
	public double getStartrange(){
		return startrange;
	}

	/**
	  *设置附加费
	  */
	public void setSurcharge(double surcharge){
		this.surcharge=surcharge;
	}

	/**
	  *获取附加费
	  */
	public double getSurcharge(){
		return surcharge;
	}

	/**
	  *设置空驶费率
	  */
	public void setEmptytravelrate(double emptytravelrate){
		this.emptytravelrate=emptytravelrate;
	}

	/**
	  *获取空驶费率
	  */
	public double getEmptytravelrate(){
		return emptytravelrate;
	}

	/**
	  *设置标准里程（米）
	  */
	public void setStandardrange(double standardrange){
		this.standardrange=standardrange;
	}

	/**
	  *获取标准里程（米）
	  */
	public double getStandardrange(){
		return standardrange;
	}

	/**
	  *设置续租价
	  */
	public void setRenewalprice(double renewalprice){
		this.renewalprice=renewalprice;
	}

	/**
	  *获取续租价
	  */
	public double getRenewalprice(){
		return renewalprice;
	}

	/**
	  *设置规则状态(0-启用,1-禁用)
	  */
	public void setRulesstate(String rulesstate){
		this.rulesstate=rulesstate;
	}

	/**
	  *获取规则状态(0-启用,1-禁用)
	  */
	public String getRulesstate(){
		return rulesstate;
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
}
