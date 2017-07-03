/**
 * 
 */
package com.szyciov.org.param;

import com.szyciov.driver.enums.OrderState;
import com.szyciov.driver.enums.PayState;
import com.szyciov.driver.enums.ReviewState;
import com.szyciov.driver.param.BaseParam;

/**
 * @ClassName OrgUserParam
 * @author Efy Shu
 * @Description TODO(这里用一句话描述这个类的作用)
 * @date 2016年9月27日 下午9:50:23
 */
public class OrgUserParam extends BaseParam {
	/**
	 * 所属机构ID
	 */
	private String orgid;
	/**
	 * 用户ID
	 */
	private String userid;
	
	/**
	 * 订单复核状态
	 */
	private String reviewstatus = ReviewState.NOTREVIEW.state;
	
	/**
	 * 订单支付状态
	 */
	private String paymentstatus = PayState.NOTPAY.state;
	
	/**
	 * 订单行程状态
	 */
	private String orderstatus = OrderState.SERVICEDONE.state;

	/**
	 * 订单状态列表(用于计算未完成订单的数量)
	 */
	private String orderstatuslist;
	
	/**  
	 * 获取订单状态列表  
	 * @return orderstatuslist 订单状态列表  
	 */
	public String getOrderstatuslist() {
		StringBuffer sb = new StringBuffer();
		sb.append("'");
		sb.append(OrderState.WAITTAKE.state).append("','");
		sb.append(OrderState.MANTICSEND.state).append("','");
		sb.append(OrderState.WAITSTART.state).append("','");
		sb.append(OrderState.START.state).append("','");
		sb.append(OrderState.ARRIVAL.state).append("','");
		sb.append(OrderState.PICKUP.state).append("','");
		sb.append(OrderState.WAITMONEY.state).append("','");
		sb.append(OrderState.INSERVICE.state);
		sb.append("'");
		orderstatuslist = sb.toString();
		return orderstatuslist;
	}
	
	/**  
	 * 设置订单状态列表(用于计算未完成订单的数量)  
	 * @param orderstatuslist 订单状态列表(用于计算未完成订单的数量)  
	 */
	public void setOrderstatuslist(String orderstatuslist) {
		this.orderstatuslist = orderstatuslist;
	}

	/**  
	 * 获取订单复核状态  
	 * @return reviewstatus 订单复核状态  
	 */
	public String getReviewstatus() {
		return reviewstatus;
	}
	
	/**  
	 * 设置订单复核状态  
	 * @param reviewstatus 订单复核状态  
	 */
	public void setReviewstatus(String reviewstatus) {
		this.reviewstatus = reviewstatus;
	}
	
	/**  
	 * 获取订单支付状态  
	 * @return paymentstatus 订单支付状态  
	 */
	public String getPaymentstatus() {
		return paymentstatus;
	}
	
	/**  
	 * 设置订单支付状态  
	 * @param paymentstatus 订单支付状态  
	 */
	public void setPaymentstatus(String paymentstatus) {
		this.paymentstatus = paymentstatus;
	}
	
	/**  
	 * 获取订单行程状态  
	 * @return orderstatus 订单行程状态  
	 */
	public String getOrderstatus() {
		return orderstatus;
	}
	

	/**  
	 * 设置订单行程状态  
	 * @param orderstatus 订单行程状态  
	 */
	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}
	

	/**
	 * 获取所属机构ID
	 * 
	 * @return orgid 所属机构ID
	 */
	public String getOrgid() {
		return orgid;
	}

	/**
	 * 设置所属机构ID
	 * 
	 * @param orgid
	 *            所属机构ID
	 */
	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	/**
	 * 获取用户ID
	 * 
	 * @return userid 用户ID
	 */
	public String getUserid() {
		return userid;
	}

	/**
	 * 设置用户ID
	 * 
	 * @param userid
	 *            用户ID
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}

}
