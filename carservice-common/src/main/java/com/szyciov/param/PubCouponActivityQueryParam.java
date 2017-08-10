package com.szyciov.param;
/**
 * 抵扣券派发管理查询餐厨
 * @author xuxxtr
 *
 */
public class PubCouponActivityQueryParam extends QueryParam{

	  private Integer querysendservicetype;      //'发放的业务1-出租车，2网约车',
	  private Integer querysendruletype;         //字段冗余，此处用处查找人工发券 1-注册，2充值，3消费，4活动',5邀请，6人工
	  private Integer querysendruletarget;       //用户类型(1-机构客户，2-机构用户，3个人用户)
	  private Integer queryruletype;             //1-注册，2充值，3消费，4活动',
	  private Integer queryactivystate;          //优惠券状态(1-待派发，2-派发中，3-已过期，4-已作废)
	  private String queryusetype;               //使用区域
	  private String queryname;                  //优惠券名称
	  private String querysendruleidref;         //发放规则'优惠券规则的id',
	  private String querysendstarttime;         //'发放的开始时间',
	  private String querysendendtime;           //'发放的结束时间',
	  private String lecompanyid;                //租赁公司id
	
	  public Integer getQuerysendservicetype() {
		return querysendservicetype;
		}
		public void setQuerysendservicetype(Integer querysendservicetype) {
			this.querysendservicetype = querysendservicetype;
		}
		public Integer getQuerysendruletype() {
			return querysendruletype;
		}
		public void setQuerysendruletype(Integer querysendruletype) {
			this.querysendruletype = querysendruletype;
		}
		public Integer getQueryruletype() {
			return queryruletype;
		}
		public void setQueryruletype(Integer queryruletype) {
			this.queryruletype = queryruletype;
		}
		public Integer getQueryactivystate() {
			return queryactivystate;
		}
		public void setQueryactivystate(Integer queryactivystate) {
			this.queryactivystate = queryactivystate;
		}
		public String getQueryusetype() {
			return queryusetype;
		}
		public void setQueryusetype(String queryusetype) {
			this.queryusetype = queryusetype;
		}
		public String getQueryname() {
			return queryname;
		}
		public void setQueryname(String queryname) {
			this.queryname = queryname;
		}
		public String getQuerysendruleidref() {
			return querysendruleidref;
		}
		public void setQuerysendruleidref(String querysendruleidref) {
			this.querysendruleidref = querysendruleidref;
		}
		public String getQuerysendstarttime() {
			return querysendstarttime;
		}
		public void setQuerysendstarttime(String querysendstarttime) {
			this.querysendstarttime = querysendstarttime;
		}
		public String getQuerysendendtime() {
			return querysendendtime;
		}
		public void setQuerysendendtime(String querysendendtime) {
			this.querysendendtime = querysendendtime;
		}
		public String getLecompanyid() {
			return lecompanyid;
		}
		public void setLecompanyid(String lecompanyid) {
			this.lecompanyid = lecompanyid;
		}
		
		public Integer getQuerysendruletarget() {
			return querysendruletarget;
		}
		public void setQuerysendruletarget(Integer querysendruletarget) {
			this.querysendruletarget = querysendruletarget;
		}
		public PubCouponActivityQueryParam() {
			super();
		}
}
