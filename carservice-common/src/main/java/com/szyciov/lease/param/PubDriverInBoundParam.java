/**
 * 
 */
package com.szyciov.lease.param;

import com.szyciov.driver.enums.DriverState;
import com.szyciov.driver.param.BaseParam;
import com.szyciov.enums.DriverEnum;

/**
 * @ClassName PubDriverInBoundParam 
 * @author Efy Shu
 * @Description 获取范围内司机参数类
 * @date 2016年10月8日 上午10:57:34 
 */
public class PubDriverInBoundParam extends BaseParam{
	/**
	 * 下单人
	 */
	private String userid;
	/**
	 * 订单号,用于更新已推送的司机
	 */
	private String orderno;
	/**
	 * 已通知的司机数
	 */
	private int pushcount;
	/**
	 * 已通知的司机
	 */
	private String alreadySendDrivers;
	/**
	 * 南侧经度
	 */
	private double minLng;
	/**
	 * 北侧经度
	 */
	private double maxLng;
	/**
	 * 西侧纬度
	 */
	private double minLat;
	/**
	 * 东侧纬度
	 */
	private double maxLat;
	
	/**
	 * 订单所属城市
	 */
	private String city;
	/**
	 * 是否即刻用车
	 */
	private boolean usenow;
	
	/**
	 * 是否允许升级
	 */
	private boolean allowgrade = false;
	
	/**
	 * 租赁公司ID(所有派单司机都要限制租赁公司)
	 */
	private String companyid;
	
	/**
	 * 机构ID
	 */
	private String organid;
	
	/**
	 * 所选车型
	 */
	private String cartype;
	
	/**
	 * 简单模式(是否只根据租赁公司和范围获取)
	 */
	private boolean simple;
	
	/**
	 * 工作状态列表
	 */
	private String workstatuslist;
	
	/**
	 * 车型级别列表
	 */
	private String levellist;
	
	/**
	 * 是否是机构用户
	 */
	private boolean orguser;
	
	/**
	 * 强弱调度关联(true -强关联 false -弱关联)
	 */
	private boolean sow;
	/**
	 * 0-网约车 1-出租车
	 */
	private int vehicletype = Integer.valueOf(DriverEnum.DRIVER_TYPE_CAR.code);
	
	public PubDriverInBoundParam() {}
	
	public PubDriverInBoundParam(double range[]){
		if(range == null) return;
		minLng = range[0];
		maxLng = range[1];
		minLat = range[2];
		maxLat = range[3];
	}
	
	
	/**  
	 * 获取工作状态列表  
	 * @return workstatuslist 工作状态列表  
	 */
	public String getWorkstatuslist() {
		//即刻订单或者下单前获取附近司机,指定需要空闲状态
		if(usenow || simple){
			workstatuslist = DriverState.IDLE.code;
		//预约订单获取空闲和服务中
		}else{
			if(sow){ //强调度不推送离线司机
				workstatuslist = DriverState.IDLE.code + "," + DriverState.INSERVICE.code;
			}else{  //弱调度可以推送离线司机
				workstatuslist = DriverState.IDLE.code + "," + DriverState.INSERVICE.code + "," + DriverState.OFFLINE.code;
			}
		}
		return workstatuslist;
	}
	
	/**  
	 * 设置工作状态列表  
	 * @param workstatuslist 工作状态列表  
	 */
	public void setWorkstatuslist(String workstatuslist) {
		this.workstatuslist = workstatuslist;
	}
	
	/**  
	 * 获取车型级别列表  
	 * @return levellist 车型级别列表  
	 */
	public String getLevellist() {
		return levellist;
	}

	/**  
	 * 设置车型级别列表  
	 * @param levellist 车型级别列表  
	 */
	public void setLevellist(String levellist) {
		this.levellist = levellist;
	}

	/**  
	 * 获取下单人  
	 * @return userid 下单人  
	 */
	public String getUserid() {
		return userid;
	}

	/**  
	 * 设置下单人  
	 * @param userid 下单人  
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	/**  
	 * 获取已通知的司机数  
	 * @return pushcount 已通知的司机数  
	 */
	public int getPushcount() {
		return pushcount;
	}

	/**  
	 * 设置已通知的司机数  
	 * @param pushcount 已通知的司机数  
	 */
	public void setPushcount(int pushcount) {
		this.pushcount = pushcount;
	}
	

	/**  
	 * 获取订单号用于更新已推送的司机  
	 * @return orderno 订单号用于更新已推送的司机  
	 */
	public String getOrderno() {
		return orderno;
	}
	

	/**  
	 * 设置订单号用于更新已推送的司机  
	 * @param orderno 订单号用于更新已推送的司机  
	 */
	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
	

	/**  
	 * 获取简单模式(是否只根据租赁公司和范围获取)  
	 * @return simple 简单模式(是否只根据租赁公司和范围获取)  
	 */
	public boolean isSimple() {
		return simple;
	}
	

	/**  
	 * 设置简单模式(是否只根据租赁公司和范围获取)  
	 * @param simple 简单模式(是否只根据租赁公司和范围获取)  
	 */
	public void setSimple(boolean simple) {
		this.simple = simple;
	}
	

	/**  
	 * 获取订单所属城市  
	 * @return city 订单所属城市  
	 */
	public String getCity() {
		return city;
	}
	

	/**  
	 * 设置订单所属城市  
	 * @param city 订单所属城市  
	 */
	public void setCity(String city) {
		this.city = city;
	}
	

	/**  
	 * 获取机构ID  
	 * @return organid 机构ID  
	 */
	public String getOrganid() {
		return organid;
	}
	
	/**  
	 * 设置机构ID  
	 * @param organid 机构ID  
	 */
	public void setOrganid(String organid) {
		this.organid = organid;
	}
	
	/**  
	 * 获取租赁公司ID(所有派单司机都要限制租赁公司)  
	 * @return companyid 租赁公司ID(所有派单司机都要限制租赁公司)  
	 */
	public String getCompanyid() {
		return companyid;
	}
	
	/**  
	 * 设置租赁公司ID(所有派单司机都要限制租赁公司)  
	 * @param companyid 租赁公司ID(所有派单司机都要限制租赁公司)  
	 */
	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	/**  
	 * 获取是否允许升级  
	 * @return allowgrade 是否允许升级  
	 */
	public boolean isAllowgrade() {
		return allowgrade;
	}
	
	/**  
	 * 设置是否允许升级  
	 * @param allowgrade 是否允许升级  
	 */
	public void setAllowgrade(boolean allowgrade) {
		this.allowgrade = allowgrade;
	}

	/**  
	 * 获取所选车型  
	 * @return cartype 所选车型  
	 */
	public String getCartype() {
		return cartype;
	}

	/**  
	 * 设置所选车型  
	 * @param cartype 所选车型  
	 */
	public void setCartype(String cartype) {
		this.cartype = cartype;
	}

	/**  
	 * 获取是否即刻用车  
	 * @return usenow 是否即刻用车  
	 */
	public boolean isUsenow() {
		return usenow;
	}
	
	/**  
	 * 设置是否即刻用车  
	 * @param usenow 是否即刻用车  
	 */
	public void setUsenow(boolean usenow) {
		this.usenow = usenow;
	}

	/**  
	 * 获取已通知的司机  
	 * @return alreadySendDrivers 已通知的司机  
	 */
	public String getAlreadySendDrivers() {
		return alreadySendDrivers;
	}
	
	/**  
	 * 设置已通知的司机  
	 * @param alreadySendDrivers 已通知的司机  
	 */
	public void setAlreadySendDrivers(String alreadySendDrivers) {
		this.alreadySendDrivers = alreadySendDrivers;
	}

	/**  
	 * 获取南侧经度  
	 * @return minLng 南侧经度  
	 */
	public double getMinLng() {
		return minLng;
	}
	
	/**  
	 * 设置南侧经度  
	 * @param minLng 南侧经度  
	 */
	public void setMinLng(double minLng) {
		this.minLng = minLng;
	}
	
	/**  
	 * 获取北侧经度  
	 * @return maxLng 北侧经度  
	 */
	public double getMaxLng() {
		return maxLng;
	}
	
	/**  
	 * 设置北侧经度  
	 * @param maxLng 北侧经度  
	 */
	public void setMaxLng(double maxLng) {
		this.maxLng = maxLng;
	}
	
	/**  
	 * 获取西侧纬度  
	 * @return minLat 西侧纬度  
	 */
	public double getMinLat() {
		return minLat;
	}
	
	/**  
	 * 设置西侧纬度  
	 * @param minLat 西侧纬度  
	 */
	public void setMinLat(double minLat) {
		this.minLat = minLat;
	}
	
	/**  
	 * 获取东侧纬度  
	 * @return maxLat 东侧纬度  
	 */
	public double getMaxLat() {
		return maxLat;
	}
	
	/**  
	 * 设置东侧纬度  
	 * @param maxLat 东侧纬度  
	 */
	public void setMaxLat(double maxLat) {
		this.maxLat = maxLat;
	}

	/**  
	 * 获取是否是机构用户  
	 * @return orguser 是否是机构用户  
	 */
	public boolean isOrguser() {
		return orguser;
	}

	/**  
	 * 设置是否是机构用户  
	 * @param orguser 是否是机构用户  
	 */
	public void setOrguser(boolean orguser) {
		this.orguser = orguser;
	}

	/**  
	 * 获取0-网约车1-出租车  
	 * @return vehicletype 0-网约车1-出租车  
	 */
	public int getVehicletype() {
		return vehicletype;
	}
	
	/**  
	 * 设置0-网约车1-出租车  
	 * @param vehicletype 0-网约车1-出租车  
	 */
	public void setVehicletype(int vehicletype) {
		this.vehicletype = vehicletype;
	}

	/**  
	 * 获取强弱调度关联(true-强关联false-弱关联)  
	 * @return sow 强弱调度关联(true-强关联false-弱关联)  
	 */
	public boolean isSow() {
		return sow;
	}
	
	/**  
	 * 设置强弱调度关联(true-强关联false-弱关联)  
	 * @param sow 强弱调度关联(true-强关联false-弱关联)  
	 */
	public void setSow(boolean sow) {
		this.sow = sow;
	}
	
}
