package com.szyciov.carservice.util.sendservice.sendrules.impl.le;

import com.szyciov.carservice.util.sendservice.sendrules.SendRuleHelper;
import com.szyciov.carservice.util.sendservice.sendrules.impl.AbstractSendRule;

import static com.szyciov.carservice.util.sendservice.sendrules.SendRuleHelper.SEND_TYPE_FORCE;

/**
 * 强派规则
 * @author zhu
 *
 */
public class LeCarForceSendRuleImp extends AbstractSendRule {
	
	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public void setSendtype(String sendtype) {
		this.sendtype = sendtype;
	}

	/**
	 * 派单方式
	 */
	private String sendtype = SEND_TYPE_FORCE;
	
	/**
	 * 城市caption
	 */
	private String citycaption;
	
	/**
	 * 城市首字母简称
	 */
	private String shortname;
	
	/**
	 * 城市
	 */
	private String city;
	
	/**
	 * 用车类型0-预约用车,1-即刻用车
	 */
	private String usetype;
	
	/**
	 * 派单模式0-系统,1-系统+人工
	 */
	private String sendmodel;
	
	/**
	 * 是否允许升级
	 */
	private String vehicleupgrade;
	
	/**
	 * 系统派单时限
	 */
	private int systemsendinterval;
	
	/**
	 * 人工派单时限
	 */
	private int personsendinterval;
	
	/**
	 * 初始派单半径
	 */
	private double initsendradius;
	
	/**
	 * 最大派单半径
	 */
	private double maxsendradius;
	
	/**
	 * 半径递增比
	 */
	private double increratio;
	
	/**
	 * 弹窗限制0-存在抢单弹窗不推单,1-存在抢单弹窗推单
	 */
	private String pushlimit;
	
	/**
	 * 约车时限
	 */
	private int carsinterval;
	
	/**
	 * 推送人数限制 0-不限制,1-限制
	 */
	private String pushnumlimit;
	
	/**
	 * 推送人数
	 */
	private int pushnum;

	/**  
	 * 获取城市caption  
	 * @return citycaption 城市caption  
	 */
	public String getCitycaption() {
		return citycaption;
	}
	

	/**  
	 * 设置城市caption  
	 * @param citycaption 城市caption  
	 */
	public void setCitycaption(String citycaption) {
		this.citycaption = citycaption;
	}
	

	/**  
	 * 获取城市  
	 * @return city 城市  
	 */
	public String getCity() {
		return city;
	}
	

	/**  
	 * 设置城市  
	 * @param city 城市  
	 */
	public void setCity(String city) {
		this.city = city;
	}
	

	/**  
	 * 获取用车类型0-预约用车1-即刻用车  
	 * @return usetype 用车类型0-预约用车1-即刻用车  
	 */
	public String getUsetype() {
		return usetype;
	}
	

	/**  
	 * 设置用车类型0-预约用车1-即刻用车  
	 * @param usetype 用车类型0-预约用车1-即刻用车  
	 */
	public void setUsetype(String usetype) {
		this.usetype = usetype;
	}
	

	/**  
	 * 获取派单模式0-系统1-系统+人工  
	 * @return sendmodel 派单模式0-系统1-系统+人工  
	 */
	public String getSendmodel() {
		return sendmodel;
	}
	

	/**  
	 * 设置派单模式0-系统1-系统+人工  
	 * @param sendmodel 派单模式0-系统1-系统+人工  
	 */
	public void setSendmodel(String sendmodel) {
		this.sendmodel = sendmodel;
	}
	

	/**  
	 * 获取是否允许升级  
	 * @return vehicleupgrade 是否允许升级  
	 */
	public String getVehicleupgrade() {
		return vehicleupgrade;
	}
	

	/**  
	 * 设置是否允许升级  
	 * @param vehicleupgrade 是否允许升级  
	 */
	public void setVehicleupgrade(String vehicleupgrade) {
		this.vehicleupgrade = vehicleupgrade;
	}
	

	/**  
	 * 获取系统派单时限  
	 * @return systemsendinterval 系统派单时限  
	 */
	public int getSystemsendinterval() {
		return systemsendinterval;
	}
	

	/**  
	 * 设置系统派单时限  
	 * @param systemsendinterval 系统派单时限  
	 */
	public void setSystemsendinterval(int systemsendinterval) {
		this.systemsendinterval = systemsendinterval;
	}
	

	/**  
	 * 获取人工派单时限  
	 * @return personsendinterval 人工派单时限  
	 */
	public int getPersonsendinterval() {
		return personsendinterval;
	}
	

	/**  
	 * 设置人工派单时限  
	 * @param personsendinterval 人工派单时限  
	 */
	public void setPersonsendinterval(int personsendinterval) {
		this.personsendinterval = personsendinterval;
	}
	

	/**  
	 * 获取初始派单半径  
	 * @return initsendradius 初始派单半径  
	 */
	public double getInitsendradius() {
		return initsendradius;
	}
	

	/**  
	 * 设置初始派单半径  
	 * @param initsendradius 初始派单半径  
	 */
	public void setInitsendradius(double initsendradius) {
		this.initsendradius = initsendradius;
	}
	

	/**  
	 * 获取最大派单半径  
	 * @return maxsendradius 最大派单半径  
	 */
	public double getMaxsendradius() {
		return maxsendradius;
	}
	

	/**  
	 * 设置最大派单半径  
	 * @param maxsendradius 最大派单半径  
	 */
	public void setMaxsendradius(double maxsendradius) {
		this.maxsendradius = maxsendradius;
	}
	

	/**  
	 * 获取半径递增比  
	 * @return increratio 半径递增比  
	 */
	public double getIncreratio() {
		return increratio;
	}
	

	/**  
	 * 设置半径递增比  
	 * @param increratio 半径递增比  
	 */
	public void setIncreratio(double increratio) {
		this.increratio = increratio;
	}
	

	/**  
	 * 获取弹窗限制0-存在抢单弹窗不推单1-存在抢单弹窗推单  
	 * @return pushlimit 弹窗限制0-存在抢单弹窗不推单1-存在抢单弹窗推单  
	 */
	public String getPushlimit() {
		return pushlimit;
	}
	

	/**  
	 * 设置弹窗限制0-存在抢单弹窗不推单1-存在抢单弹窗推单  
	 * @param pushlimit 弹窗限制0-存在抢单弹窗不推单1-存在抢单弹窗推单  
	 */
	public void setPushlimit(String pushlimit) {
		this.pushlimit = pushlimit;
	}
	

	/**  
	 * 获取约车时限  
	 * @return carsinterval 约车时限  
	 */
	public int getCarsinterval() {
		return carsinterval;
	}
	

	/**  
	 * 设置约车时限  
	 * @param carsinterval 约车时限  
	 */
	public void setCarsinterval(int carsinterval) {
		this.carsinterval = carsinterval;
	}
	

	/**  
	 * 获取推送人数限制0-不限制1-限制  
	 * @return pushnumlimit 推送人数限制0-不限制1-限制  
	 */
	public String getPushnumlimit() {
		return pushnumlimit;
	}
	

	/**  
	 * 设置推送人数限制0-不限制1-限制  
	 * @param pushnumlimit 推送人数限制0-不限制1-限制  
	 */
	public void setPushnumlimit(String pushnumlimit) {
		this.pushnumlimit = pushnumlimit;
	}
	

	/**  
	 * 获取推送人数  
	 * @return pushnum 推送人数  
	 */
	public int getPushnum() {
		return pushnum;
	}
	

	/**  
	 * 设置推送人数  
	 * @param pushnum 推送人数  
	 */
	public void setPushnum(int pushnum) {
		this.pushnum = pushnum;
	}
	

	/**  
	 * 获取派单方式  
	 * @return sendtype 派单方式  
	 */
	public String getSendtype() {
		return sendtype;
	}
}
