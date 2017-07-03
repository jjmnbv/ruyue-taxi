/**
 * 
 */
package com.szyciov.param;

import com.szyciov.driver.enums.OrderState;
import com.szyciov.driver.enums.PayState;
import com.szyciov.driver.enums.ReviewState;
import com.szyciov.driver.param.BaseParam;

/**
 * @ClassName Select2Param
 * @author Efy Shu
 * @Description 页面select2控件参数类
 * @date 2016年9月28日 下午3:45:15
 */
public class Select2Param extends BaseParam {
	/**
	 * 用户ID
	 */
	private String userid;

	/**
	 * 机构ID
	 */
	private String organid;

	/**
	 * 类型(0-姓名为ID 1-手机号为ID)
	 */
	private int type;
	
	/**
	 * 租赁公司ID
	 */
	private String companyid;
	
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
		sb.append(OrderState.INSERVICE.state);
		sb.append("'");
		orderstatuslist = sb.toString();
		return orderstatuslist;
	}

	/**  
	 * 设置订单状态列表  
	 * @param orderstatuslist 订单状态列表  
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
	 * 获取租赁公司ID  
	 * @return companyid 租赁公司ID  
	 */
	public String getCompanyid() {
		return companyid;
	}
	
	/**  
	 * 设置租赁公司ID  
	 * @param companyid 租赁公司ID  
	 */
	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	/**
	 * 获取类型(0-姓名为ID1-手机号为ID)
	 * 
	 * @return type 类型(0-姓名为ID1-手机号为ID)
	 */
	public int getType() {
		return type;
	}

	/**
	 * 设置类型(0-姓名为ID1-手机号为ID)
	 * 
	 * @param type
	 *            类型(0-姓名为ID1-手机号为ID)
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * 获取机构ID
	 * 
	 * @return organid 机构ID
	 */
	public String getOrganid() {
		return organid;
	}

	/**
	 * 设置机构ID
	 * 
	 * @param organid
	 *            机构ID
	 */
	public void setOrganid(String organid) {
		this.organid = organid;
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
