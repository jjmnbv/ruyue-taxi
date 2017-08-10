package com.szyciov.op.param;

import com.szyciov.param.QueryParam;

public class PubCouponRuleQueryParam extends QueryParam {
	public PubCouponRuleQueryParam(){
	}
	
	/**
	 * 派发类别
	 */
	public String ruletype;
	
	/**
	 * 租赁公司
	 */
	public String lecompanyid;
	
	/**
	 * 优惠券规则id
	 */
	public String id;

	public String getRuletype() {
		return ruletype;
	}

	public void setRuletype(String ruletype) {
		this.ruletype = ruletype;
	}

	public String getLecompanyid() {
		return lecompanyid;
	}

	public void setLecompanyid(String lecompanyid) {
		this.lecompanyid = lecompanyid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
