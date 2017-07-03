package com.szyciov.carservice.util.sendservice.sendrules.impl.op.taxi;

import com.szyciov.carservice.util.sendservice.sendrules.impl.AbstractSendRule;

/**
 * 抢派规则
 * @author zhu
 *
 */
public class GrabSendRuleImp extends AbstractSendRule {

	/**
	 * 派单方式
	 */
	private String sendtype = SEND_TYPE_GRAB;
	
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
	 * 系统派单时限
	 */
	private int systemsendinterval;
	
	/**
	 * 司机抢单时限(秒)
	 */
	private int driversendinterval;
	
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
	 * 推送人数限制 0-不限制,1-限制
	 */
	private String pushnumlimit;
	
	public String getSendtype() {
		return sendtype;
	}

	public String getCitycaption() {
		return citycaption;
	}

	public void setCitycaption(String citycaption) {
		this.citycaption = citycaption;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getUsetype() {
		return usetype;
	}

	public void setUsetype(String usetype) {
		this.usetype = usetype;
	}

	public String getSendmodel() {
		return sendmodel;
	}

	public void setSendmodel(String sendmodel) {
		this.sendmodel = sendmodel;
	}

	public int getSystemsendinterval() {
		return systemsendinterval;
	}

	public void setSystemsendinterval(int systemsendinterval) {
		this.systemsendinterval = systemsendinterval;
	}

	public int getDriversendinterval() {
		return driversendinterval;
	}

	public void setDriversendinterval(int driversendinterval) {
		this.driversendinterval = driversendinterval;
	}

	public int getPersonsendinterval() {
		return personsendinterval;
	}

	public void setPersonsendinterval(int personsendinterval) {
		this.personsendinterval = personsendinterval;
	}

	public double getInitsendradius() {
		return initsendradius;
	}

	public void setInitsendradius(double initsendradius) {
		this.initsendradius = initsendradius;
	}

	public double getMaxsendradius() {
		return maxsendradius;
	}

	public void setMaxsendradius(double maxsendradius) {
		this.maxsendradius = maxsendradius;
	}

	public double getIncreratio() {
		return increratio;
	}

	public void setIncreratio(double increratio) {
		this.increratio = increratio;
	}

	public String getPushnumlimit() {
		return pushnumlimit;
	}

	public void setPushnumlimit(String pushnumlimit) {
		this.pushnumlimit = pushnumlimit;
	}

	public int getPushnum() {
		return pushnum;
	}

	public void setPushnum(int pushnum) {
		this.pushnum = pushnum;
	}

	public String getPushlimit() {
		return pushlimit;
	}

	public void setPushlimit(String pushlimit) {
		this.pushlimit = pushlimit;
	}

	public int getCarsinterval() {
		return carsinterval;
	}

	public void setCarsinterval(int carsinterval) {
		this.carsinterval = carsinterval;
	}

	/**
	 * 推送人数
	 */
	private int pushnum;
	
	/**
	 * 弹窗限制0-存在抢单弹窗不推单,1-存在抢单弹窗推单
	 */
	private String pushlimit;
	
	/**
	 * 约车时限
	 */
	private int carsinterval;
}
