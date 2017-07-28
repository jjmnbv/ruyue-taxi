package com.szyciov.pub.entity;

import java.util.Date;

/**
 * 优惠券规则表
 */
public class PubCouponRule {
	/**
	 * 规则id
	 */
	public String id;
	
	/**
	 * 规则名称
	 */
	public String name;
	
	/**
	 * 规则类型1-注册，2充值，3消费，4活动，5邀请
	 */
	public String ruletype;
	
	/**
	 * 规则对象1-机构客户，2-机构用户，3个人用户
	 */
	public String ruletarget;
	
	/**
	 * 充值满多少钱
	 */
	public double rechargemoney;
	
	/**
	 * 消费类型1-消费频次，2-消费金额
	 */
	public String consumetype;
	
	/**
	 * 周期天数
	 */
	public int cycleday;
	
	/**
	 * 消费频次类型，1-满，2-满低，3低
	 */
	public String consumefrequencytype;
	
	/**
	 * 消费频次高次
	 */
	public int consumehightimes;
	
	/**
	 * 消费频次低次
	 */
	public int consumelowtimes;
	
	/**
	 * 单次消费可用 0-不可用，1-可用
	 */
	public String consumemoneysingleable;
	
	/**
	 * 单次消费满金额
	 */
	public double consumemoneysingelfull;
	
	/**
	 * 周期消费可用 0-不可用，1-可用
	 */
	public String consumemoneycycleable;
	
	/**
	 * 周期消费金额类型，1-满，2-满低，3低
	 */
	public String consumemoneycycletype;
	
	/**
	 * 周期消费金额满值
	 */
	public double consumemoneycyclefull;
	
	/**
	 * 周期消费金额低值
	 */
	public double consumemoneycyclelow;
	
	/**
	 * 规则内容
	 */
	public String rulecontent;
	
	/**
	 * 租赁公司或者运管id
	 */
	public String lecompanyid;
	
	/**
	 * 平台类型 0-运管端，1-租赁端
	 */
	public String platformtype;
	
	/**
	 * 更新时间
	 */
	public Date updatetime;
	
	/**
	 * 创建时间
	 */
	public Date createtime;
	
	/**
	 * 更新人
	 */
	public String updater;
	
	/**
	 * 创建人
	 */
	public String creater;
	
	/**
	 * 数据状态
	 */
	public String status;
	
	/**
	 * 历史记录数量
	 */
	public int historycount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRuletype() {
		return ruletype;
	}

	public void setRuletype(String ruletype) {
		this.ruletype = ruletype;
	}

	public String getRuletarget() {
		return ruletarget;
	}

	public void setRuletarget(String ruletarget) {
		this.ruletarget = ruletarget;
	}

	public double getRechargemoney() {
		return rechargemoney;
	}

	public void setRechargemoney(double rechargemoney) {
		this.rechargemoney = rechargemoney;
	}

	public String getConsumetype() {
		return consumetype;
	}

	public void setConsumetype(String consumetype) {
		this.consumetype = consumetype;
	}

	public int getCycleday() {
		return cycleday;
	}

	public void setCycleday(int cycleday) {
		this.cycleday = cycleday;
	}

	public String getConsumefrequencytype() {
		return consumefrequencytype;
	}

	public void setConsumefrequencytype(String consumefrequencytype) {
		this.consumefrequencytype = consumefrequencytype;
	}

	public int getConsumehightimes() {
		return consumehightimes;
	}

	public void setConsumehightimes(int consumehightimes) {
		this.consumehightimes = consumehightimes;
	}

	public int getConsumelowtimes() {
		return consumelowtimes;
	}

	public void setConsumelowtimes(int consumelowtimes) {
		this.consumelowtimes = consumelowtimes;
	}

	public String getConsumemoneysingleable() {
		return consumemoneysingleable;
	}

	public void setConsumemoneysingleable(String consumemoneysingleable) {
		this.consumemoneysingleable = consumemoneysingleable;
	}

	public double getConsumemoneysingelfull() {
		return consumemoneysingelfull;
	}

	public void setConsumemoneysingelfull(double consumemoneysingelfull) {
		this.consumemoneysingelfull = consumemoneysingelfull;
	}

	public String getConsumemoneycycleable() {
		return consumemoneycycleable;
	}

	public void setConsumemoneycycleable(String consumemoneycycleable) {
		this.consumemoneycycleable = consumemoneycycleable;
	}

	public String getConsumemoneycycletype() {
		return consumemoneycycletype;
	}

	public void setConsumemoneycycletype(String consumemoneycycletype) {
		this.consumemoneycycletype = consumemoneycycletype;
	}

	public double getConsumemoneycyclefull() {
		return consumemoneycyclefull;
	}

	public void setConsumemoneycyclefull(double consumemoneycyclefull) {
		this.consumemoneycyclefull = consumemoneycyclefull;
	}

	public double getConsumemoneycyclelow() {
		return consumemoneycyclelow;
	}

	public void setConsumemoneycyclelow(double consumemoneycyclelow) {
		this.consumemoneycyclelow = consumemoneycyclelow;
	}

	public String getRulecontent() {
		return rulecontent;
	}

	public void setRulecontent(String rulecontent) {
		this.rulecontent = rulecontent;
	}

	public String getLecompanyid() {
		return lecompanyid;
	}

	public void setLecompanyid(String lecompanyid) {
		this.lecompanyid = lecompanyid;
	}

	public String getPlatformtype() {
		return platformtype;
	}

	public void setPlatformtype(String platformtype) {
		this.platformtype = platformtype;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getHistorycount() {
		return historycount;
	}

	public void setHistorycount(int historycount) {
		this.historycount = historycount;
	}

}
