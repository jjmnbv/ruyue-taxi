package com.szyciov.pub.entity;

/**
 * 优惠卷表
 * @author xuxxtr
 *
 */
public class PubCoupon {

	   private String id;
	   private String name;
	   private String couponactivyidref;
	   private Integer servicetype;         //'1-出租车，2-网约车',
	   private Integer target;               //'1-机构客户，2机构用户，3-个人用户',
	   private String useid;                
	   private String money;                 
	   private Integer usetype;              //'1-发放区域有效，2-开通业务城市有效(2即不限制区域)',
	   private Integer lockstate;            //'0-未锁定，1-锁定',
	   private Integer couponstatus;         //'0-未使用，1-已使用，2-已过期',
	   private String outimestart;          
	   private String outtimeend;            
	   private String lecompanyid;          
	   private Integer platformtype;         //'0-运管端，1-租赁端',
	   private String createtime;            
	   private String updatetime;            
	   private String creater;              
	   private String updater;
	
		public PubCoupon() {
			super();
		}
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
		public String getCouponactivyidref() {
			return couponactivyidref;
		}
		public void setCouponactivyidref(String couponactivyidref) {
			this.couponactivyidref = couponactivyidref;
		}
		public Integer getServicetype() {
			return servicetype;
		}
		public void setServicetype(Integer servicetype) {
			this.servicetype = servicetype;
		}
		public Integer getTarget() {
			return target;
		}
		public void setTarget(Integer target) {
			this.target = target;
		}
		public String getUseid() {
			return useid;
		}
		public void setUseid(String useid) {
			this.useid = useid;
		}
		public String getMoney() {
			return money;
		}
		public void setMoney(String money) {
			this.money = money;
		}
		public Integer getUsetype() {
			return usetype;
		}
		public void setUsetype(Integer usetype) {
			this.usetype = usetype;
		}
		public Integer getLockstate() {
			return lockstate;
		}
		public void setLockstate(Integer lockstate) {
			this.lockstate = lockstate;
		}
		public Integer getCouponstatus() {
			return couponstatus;
		}
		public void setCouponstatus(Integer couponstatus) {
			this.couponstatus = couponstatus;
		}
		public String getOutimestart() {
			return outimestart;
		}
		public void setOutimestart(String outimestart) {
			this.outimestart = outimestart;
		}
		public String getOuttimeend() {
			return outtimeend;
		}
		public void setOuttimeend(String outtimeend) {
			this.outtimeend = outtimeend;
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
		public String getCreatetime() {
			return createtime;
		}
		public void setCreatetime(String createtime) {
			this.createtime = createtime;
		}
		public String getUpdatetime() {
			return updatetime;
		}
		public void setUpdatetime(String updatetime) {
			this.updatetime = updatetime;
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
