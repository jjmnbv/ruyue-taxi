package com.szyciov.dto.coupon;

/**
 * 优惠券前台传输实体
 * @author xuxxtr
 *
 */
public class PubCouponActivityDto {
	
	  private String id;                   //'优惠券派发id',
	  private String name;                 //'优惠券活动名称也就是优惠券的名称',
	  private Integer sendservicetype;      //'发放的业务1-出租车，2网约车',
	  private Integer sendruletype;          //发放类型(1-注册，2充值，3消费，4活动，5邀请，6-人工)
	  private Integer sendruletarget;       //发放对象'1-机构客户，2-机构用户，3个人用户',
	  private String sendruleidref;        //发放规则'优惠券规则的id',
	  private String rulename;              //规则名称
	  private Integer activystate;         //优惠券状态(1-待派发，2-派发中，3-已过期，4-已作废)
	  private Integer sendmoneytype;        //'发放金额类型，1-固定，2随机',
	  private Double sendfixedmoney;       //'固定方法的金额',
	  private Double sendlowmoney;         //'随机发放的最新金额',
	  private Double sendhighmoney;        //'随机发放的最高金额',
	  private String sendstarttime;        //'发放的开始时间',格式yyyy-MM-dd
	  private String sendendtime;          //'发放的结束时间',格式yyyy-MM-dd
	  private Integer sendcount;          //每个人优惠卷发放数量
	  private Integer usetype;              //'使用区域 1-发放区域有效，2-开通业务城市有效',
	  private Integer outimetype;           //'有效期类型 1-多少天内有效，2-固定期限',
	  private Integer sendtimeinday;       //'发放多少天内有效',
	  private String fixedstarttime;       //'固定开始时间',格式yyyy-MM-dd
	  private String fixedendtime;         //'固定结束时间',格式yyyy-MM-dd
	  private String lecompanyid;          //'租赁公司或者运管端id',
	  private Integer platformtype;         //'平台类型 0-运管端，1-租赁端',
	  private String updatetime;           //更新时间
	  private String createtime;           //新增时间
	  private String creater;
	  private String updater;
	  private String citys;               //usetype=1时，限定发放区域，城市名以 ，隔开
	  private String users;               //指定用户时，用户account以 ， 隔开
	  private String couponrule;          //优惠券规则
	  
	  
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
	public PubCouponActivityDto() {
		super();
	}
	public String getCouponrule() {
		return couponrule;
	}
	public void setCouponrule(String couponrule) {
		this.couponrule = couponrule;
	}
}
