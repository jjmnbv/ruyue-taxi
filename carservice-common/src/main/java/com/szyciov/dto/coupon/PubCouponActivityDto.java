package com.szyciov.dto.coupon;

import io.swagger.annotations.ApiModelProperty;

/**
 * 优惠券前台传输实体
 * @author xuxxtr
 *
 */
public class PubCouponActivityDto {

 	@ApiModelProperty("活动id")
  	private String id;

	@ApiModelProperty("活动名称")
	private String name;

	@ApiModelProperty("发放业务 1-出租车，2-网约车")
 	private Integer sendservicetype;

	@ApiModelProperty("发放类型 (1-注册，2充值，3消费，4活动，5邀请，6-人工)")
	private Integer sendruletype;

	@ApiModelProperty("发放对象 (1-机构客户，2-机构用户，3个人用户)")
	private Integer sendruletarget;

	@ApiModelProperty("优惠券规则ID")
	private String sendruleidref;

	@ApiModelProperty("规则名称")
	private String rulename;

	@ApiModelProperty("优惠券状态 (1-待派发，2-派发中，3-已过期，4-已作废)")
	private Integer activystate;

	@ApiModelProperty("发放金额类型 (1-固定，2-随机)")
	private Integer sendmoneytype;

	@ApiModelProperty("固定方法的金额")
	private Double sendfixedmoney;

	@ApiModelProperty("随机发放的最低金额")
	private Double sendlowmoney;

	@ApiModelProperty("随机发放的最高金额")
	private Double sendhighmoney;

	@ApiModelProperty("发放的开始时间,格式yyyy-MM-dd")
	private String sendstarttime;

	@ApiModelProperty("发放的结束时间,格式yyyy-MM-dd")
	private String sendendtime;

	@ApiModelProperty("每个人优惠卷发放数量")
	private Integer sendcount;

	@ApiModelProperty("使用区域 (1-发放区域有效，2-开通业务城市有效)")
	private Integer usetype;

	@ApiModelProperty("有效期类型 (1-多少天内有效，2-固定期限)")
	private Integer outimetype;

	@ApiModelProperty("发放多少天内有效")
	private Integer sendtimeinday;

	@ApiModelProperty("固定开始时间 格式yyyy-MM-dd")
	private String fixedstarttime;

	@ApiModelProperty("固定结束时间 格式yyyy-MM-dd")
	private String fixedendtime;

	@ApiModelProperty("租赁公司或者运管端id")
	private String lecompanyid;

	@ApiModelProperty("平台类型 0-运管端，1-租赁端")
	private Integer platformtype;

	@ApiModelProperty("限定发放区域时的城市code，以 ，隔开")
	private String citys;

	@ApiModelProperty("指定用户时，用户account以 ， 隔开")
	private String users;

	@ApiModelProperty("优惠券规则Json串")
	private String couponrule;

	private String updatetime;
	private String createtime;
	private String creater;
	private String updater;

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

	public Integer getSendservicetype() {
		return sendservicetype;
	}

	public void setSendservicetype(Integer sendservicetype) {
		this.sendservicetype = sendservicetype;
	}

	public Integer getSendruletype() {
		return sendruletype;
	}

	public void setSendruletype(Integer sendruletype) {
		this.sendruletype = sendruletype;
	}

	public Integer getSendruletarget() {
		return sendruletarget;
	}

	public void setSendruletarget(Integer sendruletarget) {
		this.sendruletarget = sendruletarget;
	}

	public String getSendruleidref() {
		return sendruleidref;
	}

	public void setSendruleidref(String sendruleidref) {
		this.sendruleidref = sendruleidref;
	}

	public String getRulename() {
		return rulename;
	}

	public void setRulename(String rulename) {
		this.rulename = rulename;
	}

	public Integer getActivystate() {
		return activystate;
	}

	public void setActivystate(Integer activystate) {
		this.activystate = activystate;
	}

	public Integer getSendmoneytype() {
		return sendmoneytype;
	}

	public void setSendmoneytype(Integer sendmoneytype) {
		this.sendmoneytype = sendmoneytype;
	}

	public Double getSendfixedmoney() {
		return sendfixedmoney;
	}

	public void setSendfixedmoney(Double sendfixedmoney) {
		this.sendfixedmoney = sendfixedmoney;
	}

	public Double getSendlowmoney() {
		return sendlowmoney;
	}

	public void setSendlowmoney(Double sendlowmoney) {
		this.sendlowmoney = sendlowmoney;
	}

	public Double getSendhighmoney() {
		return sendhighmoney;
	}

	public void setSendhighmoney(Double sendhighmoney) {
		this.sendhighmoney = sendhighmoney;
	}

	public String getSendstarttime() {
		return sendstarttime;
	}

	public void setSendstarttime(String sendstarttime) {
		this.sendstarttime = sendstarttime;
	}

	public String getSendendtime() {
		return sendendtime;
	}

	public void setSendendtime(String sendendtime) {
		this.sendendtime = sendendtime;
	}

	public Integer getSendcount() {
		return sendcount;
	}

	public void setSendcount(Integer sendcount) {
		this.sendcount = sendcount;
	}

	public Integer getUsetype() {
		return usetype;
	}

	public void setUsetype(Integer usetype) {
		this.usetype = usetype;
	}

	public Integer getOutimetype() {
		return outimetype;
	}

	public void setOutimetype(Integer outimetype) {
		this.outimetype = outimetype;
	}

	public Integer getSendtimeinday() {
		return sendtimeinday;
	}

	public void setSendtimeinday(Integer sendtimeinday) {
		this.sendtimeinday = sendtimeinday;
	}

	public String getFixedstarttime() {
		return fixedstarttime;
	}

	public void setFixedstarttime(String fixedstarttime) {
		this.fixedstarttime = fixedstarttime;
	}

	public String getFixedendtime() {
		return fixedendtime;
	}

	public void setFixedendtime(String fixedendtime) {
		this.fixedendtime = fixedendtime;
	}

	public String getLecompanyid() {
		return lecompanyid;
	}

	public void setLecompanyid(String lecompanyid) {
		this.lecompanyid = lecompanyid;
	}

	public Integer getPlatformtype() {
		return platformtype;
	}

	public void setPlatformtype(Integer platformtype) {
		this.platformtype = platformtype;
	}

	public String getCitys() {
		return citys;
	}

	public void setCitys(String citys) {
		this.citys = citys;
	}

	public String getUsers() {
		return users;
	}

	public void setUsers(String users) {
		this.users = users;
	}

	public String getCouponrule() {
		return couponrule;
	}

	public void setCouponrule(String couponrule) {
		this.couponrule = couponrule;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}
}
