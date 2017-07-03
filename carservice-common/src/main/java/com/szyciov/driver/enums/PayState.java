/**
 * 
 */
package com.szyciov.driver.enums;

/**
 * 支付状态
 * @ClassName PayState 
 * @author Efy Shu
 * @Description  包括(0-未支付，1-已支付，2-结算中，3-已结算)
 * @date 2016年9月22日 下午9:08:44 
 */
public enum PayState {
	/**
	 * 未支付 线上付款
	 */
	NOTPAY("0","未支付"),
	/**
	 * 已支付 线上付款
	 */
	PAYED("1","已支付"),
	/**
	 * 结算中
	 */
	STATEMENTING("2","结算中"),
	/**
	 * 已结算
	 */
	STATEMENTED("3","已结算"),
	/**
	 * 未结算
	 */
	MENTED("4","未结算"),
	/**
	 * 司机和乘客都未付结
	 */
	ALLNOPAY("5","未付结"),
	/**
	 * 司机已结算行程费，乘客未支付调度费
	 */
	PASSENGERNOPAY("6","未付结"),
	/**
	 * 乘客已支付调度费，司机未结算行程费
	 */
	DRIVERNOPAY("7","未付结"),
	/**
	 * 已付结
	 */
	PAYOVER("8","已付结");

	public static PayState getPayStateByDB(String payState){
		for(PayState p : PayState.values()) {
			if(p.state.equals(payState)){
				return p;
			}
		}
		return NOTPAY;
	}
	
	public String state;
	public String msg;
	PayState(String state,String msg){
		this.msg = msg;
		this.state = state;
	}
}
