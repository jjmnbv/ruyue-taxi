package com.szyciov.param;
/**
 * 抵扣券派发管理查询餐厨
 * @author xuxxtr
 *
 */
public class PubCouponActivityQueryParam extends QueryParam{

	  private Integer sendservicetype;      //'发放的业务1-出租车，2网约车',
	  private Integer sendruletype;              //字段冗余，此处用处查找人工发券 1-注册，2充值，3消费，4活动',5邀请，6人工
	  private Integer ruletype;                  //1-注册，2充值，3消费，4活动',
	  private Integer activystate;         //优惠券状态(1-待派发，2-派发中，3-已过期，4-已作废)
	  private String useCity;              //使用区域
	  private String name;                  //优惠券名称
	  private String sendruleidref;        //发放规则'优惠券规则的id',
	  private String sendstarttime;        //'发放的开始时间',
	  private String sendendtime;          //'发放的结束时间',
	public Integer getSendservicetype() {
		return sendservicetype;
	}
	public void setSendservicetype(Integer sendservicetype) {
		this.sendservicetype = sendservicetype;
	}
	public Integer getRuletype() {
		return ruletype;
	}
	public void setRuletype(Integer ruletype) {
		this.ruletype = ruletype;
	}
	public Integer getActivystate() {
		return activystate;
	}
	public void setActivystate(Integer activystate) {
		this.activystate = activystate;
	}
	public String getUseCity() {
		return useCity;
	}
	public void setUseCity(String useCity) {
		this.useCity = useCity;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSendruleidref() {
		return sendruleidref;
	}
	public void setSendruleidref(String sendruleidref) {
		this.sendruleidref = sendruleidref;
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
	public PubCouponActivityQueryParam() {
		super();
	}
	  
}
