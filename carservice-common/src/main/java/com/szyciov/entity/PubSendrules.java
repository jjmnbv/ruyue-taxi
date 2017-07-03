package com.szyciov.entity;


import java.util.Date;
import com.szyciov.driver.param.BaseParam;

/**
  * @ClassName PubSendrules
  * @author Efy Shu
  * @Description 派单规则实体类
  * @date 2017年5月24日 17:21:24
  */ 
public class PubSendrules extends BaseParam{
	/**
	  *主键
	  */
	private String id;

	/**
	  *所属租赁公司
	  */
	private String leasescompanyid;

	/**
	  *城市,从字典表中读取
	  */
	private String city;

	/**
	  *车型升级(0-当前级别，1-升级)
	  */
	private int vehicleupgrade;

	/**
	  *用车类型(0-预约用车,1-即刻用车)
	  */
	private String usetype;

	/**
	  *派单方式(0-强派,1-抢派,2-抢单，3-纯人工)
	  */
	private int sendtype;

	/**
	  *派单模式(0-系统,1-系统+人工)
	  */
	private int sendmodel;

	/**
	  *系统派单时限(分钟)
	  */
	private int systemsendinterval;

	/**
	  *司机抢单时限(分钟)
	  */
	private int driversendinterval;

	/**
	  *特殊派单时限(秒)
	  */
	private int specialinterval;

	/**
	  *人工派单时限(分钟)
	  */
	private int personsendinterval;

	/**
	  *初始派单半径
	  */
	private double initsendradius;

	/**
	  *最大派单半径
	  */
	private double maxsendradius;

	/**
	  *半径递增比
	  */
	private double increratio;

	/**
	  *推送数量(0-不限制,1-限制)
	  */
	private String pushnumlimit;

	/**
	  *推送数量大小
	  */
	private int pushnum;

	/**
	  *推送限制(0-存在抢单弹窗不推单,1-存在抢单弹窗推单)
	  */
	private String pushlimit;

	/**
	  *首字母简称
	  */
	private String shortname;

	/**
	  *约车时限
	  */
	private int carsinterval;

	/**
	  *所属平台(0-运管端，1-租赁端)
	  */
	private String platformtype;

	/**
	  *车型(0-网约车，1-出租车)
	  */
	private int vehicletype;

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
	  *启用状态(0-启用，1-禁用)
	  */
	private String rulesstate;
	
	/**************************************************附加字段*********************************************/
	
	/**
	 * 城市名称
	 */
	private String citycaption;

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
	  *设置城市,从字典表中读取
	  */
	public void setCity(String city){
		this.city=city;
	}

	/**
	  *获取城市,从字典表中读取
	  */
	public String getCity(){
		return city;
	}

	/**
	  *设置车型升级(0-当前级别，1-升级)
	  */
	public void setVehicleupgrade(int vehicleupgrade){
		this.vehicleupgrade=vehicleupgrade;
	}

	/**
	  *获取车型升级(0-当前级别，1-升级)
	  */
	public int getVehicleupgrade(){
		return vehicleupgrade;
	}

	/**
	  *设置用车类型(0-预约用车,1-即刻用车)
	  */
	public void setUsetype(String usetype){
		this.usetype=usetype;
	}

	/**
	  *获取用车类型(0-预约用车,1-即刻用车)
	  */
	public String getUsetype(){
		return usetype;
	}

	/**
	  *设置派单方式(0-强派,1-抢派,2-抢单，3-纯人工)
	  */
	public void setSendtype(int sendtype){
		this.sendtype=sendtype;
	}

	/**
	  *获取派单方式(0-强派,1-抢派,2-抢单，3-纯人工)
	  */
	public int getSendtype(){
		return sendtype;
	}

	/**
	  *设置派单模式(0-系统,1-系统+人工)
	  */
	public void setSendmodel(int sendmodel){
		this.sendmodel=sendmodel;
	}

	/**
	  *获取派单模式(0-系统,1-系统+人工)
	  */
	public int getSendmodel(){
		return sendmodel;
	}

	/**
	  *设置系统派单时限(分钟)
	  */
	public void setSystemsendinterval(int systemsendinterval){
		this.systemsendinterval=systemsendinterval;
	}

	/**
	  *获取系统派单时限(分钟)
	  */
	public int getSystemsendinterval(){
		return systemsendinterval;
	}

	/**
	  *设置司机抢单时限(分钟)
	  */
	public void setDriversendinterval(int driversendinterval){
		this.driversendinterval=driversendinterval;
	}

	/**
	  *获取司机抢单时限(分钟)
	  */
	public int getDriversendinterval(){
		return driversendinterval;
	}

	/**
	  *设置特殊派单时限(秒)
	  */
	public void setSpecialinterval(int specialinterval){
		this.specialinterval=specialinterval;
	}

	/**
	  *获取特殊派单时限(秒)
	  */
	public int getSpecialinterval(){
		return specialinterval;
	}

	/**
	  *设置人工派单时限(分钟)
	  */
	public void setPersonsendinterval(int personsendinterval){
		this.personsendinterval=personsendinterval;
	}

	/**
	  *获取人工派单时限(分钟)
	  */
	public int getPersonsendinterval(){
		return personsendinterval;
	}

	/**
	  *设置初始派单半径
	  */
	public void setInitsendradius(double initsendradius){
		this.initsendradius=initsendradius;
	}

	/**
	  *获取初始派单半径
	  */
	public double getInitsendradius(){
		return initsendradius;
	}

	/**
	  *设置最大派单半径
	  */
	public void setMaxsendradius(double maxsendradius){
		this.maxsendradius=maxsendradius;
	}

	/**
	  *获取最大派单半径
	  */
	public double getMaxsendradius(){
		return maxsendradius;
	}

	/**
	  *设置半径递增比
	  */
	public void setIncreratio(double increratio){
		this.increratio=increratio;
	}

	/**
	  *获取半径递增比
	  */
	public double getIncreratio(){
		return increratio;
	}

	/**
	  *设置推送数量(0-不限制,1-限制)
	  */
	public void setPushnumlimit(String pushnumlimit){
		this.pushnumlimit=pushnumlimit;
	}

	/**
	  *获取推送数量(0-不限制,1-限制)
	  */
	public String getPushnumlimit(){
		return pushnumlimit;
	}

	/**
	  *设置推送数量大小
	  */
	public void setPushnum(int pushnum){
		this.pushnum=pushnum;
	}

	/**
	  *获取推送数量大小
	  */
	public int getPushnum(){
		return pushnum;
	}

	/**
	  *设置推送限制(0-存在抢单弹窗不推单,1-存在抢单弹窗推单)
	  */
	public void setPushlimit(String pushlimit){
		this.pushlimit=pushlimit;
	}

	/**
	  *获取推送限制(0-存在抢单弹窗不推单,1-存在抢单弹窗推单)
	  */
	public String getPushlimit(){
		return pushlimit;
	}

	/**
	  *设置首字母简称
	  */
	public void setShortname(String shortname){
		this.shortname=shortname;
	}

	/**
	  *获取首字母简称
	  */
	public String getShortname(){
		return shortname;
	}

	/**
	  *设置约车时限
	  */
	public void setCarsinterval(int carsinterval){
		this.carsinterval=carsinterval;
	}

	/**
	  *获取约车时限
	  */
	public int getCarsinterval(){
		return carsinterval;
	}

	/**
	  *设置所属平台(0-运管端，1-租赁端)
	  */
	public void setPlatformtype(String platformtype){
		this.platformtype=platformtype;
	}

	/**
	  *获取所属平台(0-运管端，1-租赁端)
	  */
	public String getPlatformtype(){
		return platformtype;
	}

	/**
	  *设置车型(0-网约车，1-出租车)
	  */
	public void setVehicletype(int vehicletype){
		this.vehicletype=vehicletype;
	}

	/**
	  *获取车型(0-网约车，1-出租车)
	  */
	public int getVehicletype(){
		return vehicletype;
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
	  *设置启用状态(0-启用，1-禁用)
	  */
	public void setRulesstate(String rulesstate){
		this.rulesstate=rulesstate;
	}

	/**
	  *获取启用状态(0-启用，1-禁用)
	  */
	public String getRulesstate(){
		return rulesstate;
	}

	/**  
	 * 获取城市名称
	 * @return citycaption   
	 */
	public String getCitycaption() {
		return citycaption;
	}

	/**  
	 * 设置城市名称
	 * @param citycaption   
	 */
	public void setCitycaption(String citycaption) {
		this.citycaption = citycaption;
	}
}
