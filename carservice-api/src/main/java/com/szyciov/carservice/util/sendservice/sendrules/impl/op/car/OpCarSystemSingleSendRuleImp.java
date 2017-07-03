package com.szyciov.carservice.util.sendservice.sendrules.impl.op.car;

import com.szyciov.carservice.util.sendservice.sendrules.SendRuleHelper;
import com.szyciov.carservice.util.sendservice.sendrules.impl.AbstractSendRule;

import static com.szyciov.carservice.util.sendservice.sendrules.SendRuleHelper.SEND_TYPE_SYSTEMSINGLE;

/**
 * 纯人工派单
 * @author zhu
 *
 */
public class OpCarSystemSingleSendRuleImp extends AbstractSendRule {

	/**
	 * 派单方式
	 */
	private String sendtype = SEND_TYPE_SYSTEMSINGLE;

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


	public int getPersonsendinterval() {
		return personsendinterval;
	}

	public void setPersonsendinterval(int personsendinterval) {
		this.personsendinterval = personsendinterval;
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
	 * 用车类型0-预约用车,1-即刻用车
	 */
	private String usetype;


	/**
	 * 人工派单时限
	 */
	private int personsendinterval;


	/**
	 * 弹窗限制0-存在抢单弹窗不推单,1-存在抢单弹窗推单
	 */
	private String pushlimit;

	/**
	 * 约车时限
	 */
	private int carsinterval;

	/**
	 * 是否升级车型
	 */
	private boolean isUpgrade;

	public boolean isUpgrade() {
		return isUpgrade;
	}

	public void setUpgrade(boolean upgrade) {
		isUpgrade = upgrade;
	}
}
