package com.szyciov.entity;

import com.szyciov.param.OrderApiParam;

/**
 * @ClassName OrderReviewParam 
 * @author Efy Shu
 * @Description 订单复议参数类
 * @date 2016年9月22日 下午12:06:46
 */
public class OrderReviewParam extends OrderApiParam {
	/**
	 * 复议方(0-乘客，1-司机)
	 */
	private String reviewperson;
	
	/**
	 * 复议原因
	 */
	private String reason;
	
	/**
	 * 复议类型(0-机构,1-个人)
	 */
	private int reviewtype;

	/**  
	 * 获取复议方(0-乘客，1-司机)  
	 * @return reviewperson 复议方(0-乘客，1-司机)  
	 */
	public String getReviewperson() {
		return reviewperson;
	}
	
	/**  
	 * 设置复议方(0-乘客，1-司机)  
	 * @param reviewperson 复议方(0-乘客，1-司机)  
	 */
	public void setReviewperson(String reviewperson) {
		this.reviewperson = reviewperson;
	}
	
	/**  
	 * 获取复议原因  
	 * @return reason 复议原因  
	 */
	public String getReason() {
		return reason;
	}

	/**  
	 * 设置复议原因  
	 * @param reason 复议原因  
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**  
	 * 获取复议类型(0-机构1-个人)  
	 * @return reviewtype 复议类型(0-机构1-个人)  
	 */
	public int getReviewtype() {
		return reviewtype;
	}
	
	/**  
	 * 设置复议类型(0-机构1-个人)  
	 * @param reviewtype 复议类型(0-机构1-个人)  
	 */
	public void setReviewtype(int reviewtype) {
		this.reviewtype = reviewtype;
	}
}
