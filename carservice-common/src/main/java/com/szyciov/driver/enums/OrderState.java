package com.szyciov.driver.enums;

/**
 * 订单状态
 * 包括：0-待接单，1-待人工派单，2-待出发，3-已出发，4-已抵达，5-接到乘客，6-服务中，7-行程结束，8-已取消 9-待确费
 * @ClassName OrderState 
 * @author Efy Shu
 * @Description 订单状态枚举类
 * @date 2016年8月23日 下午7:44:57
 */
public enum OrderState {
	/**
	 * 待接单
	 */
	WAITTAKE("0","待接单"),
	/**
	 * 待人工派单
	 */
	MANTICSEND("1","待人工派单"),
	/**
	 * 司机未出发
	 */
	WAITSTART("2","待出发"),
	/**
	 * 司机已出发
	 */
	START("3","已出发"),
	/**
	 * 司机已抵达
	 */
	ARRIVAL("4","已抵达"),
	/**
	 * 已接到乘客
	 */
	PICKUP("5","已接到乘客"),
	/**
	 * 服务中
	 */
	INSERVICE("6","服务中"),
	/**
	 * 行程结束
	 */
	SERVICEDONE("7","服务结束"),
	/**
	 * 订单取消
	 */
	CANCEL("8","已取消"),
	/**
	 * 待确费
	 */
	WAITMONEY("9","待确费");
	

	
	
	public String state;
	public String msg;
	
	public static OrderState getByCode(String code){
		return WAITTAKE.state.equals(code) ? WAITTAKE : 
					MANTICSEND.state.equals(code) ? MANTICSEND : 
					WAITSTART.state.equals(code) ? WAITSTART : 
					START.state.equals(code) ? START : 
					ARRIVAL.state.equals(code) ? ARRIVAL : 
					PICKUP.state.equals(code) ? PICKUP : 
					INSERVICE.state.equals(code) ? INSERVICE : 
					SERVICEDONE.state.equals(code) ? SERVICEDONE : 
					CANCEL.state.equals(code) ? CANCEL : 
					WAITMONEY.state.equals(code) ? WAITMONEY : CANCEL;
	}
	
	OrderState(String state,String msg){
		this.msg = msg;
		this.state = state;
	}
	
}
