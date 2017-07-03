package com.szyciov.driver.enums;

/**
 * 司机状态
 * @ClassName DriverState 
 * @author Efy Shu
 * @Description 司机状态枚举类
 * @date 2016年9月14日 上午11:05:49
 */
public enum DriverState {
	/**
	 * 空闲
	 */
	IDLE("0","空闲"),
	/**
	 * 服务中
	 */
	INSERVICE("1","服务中"),
	/**
	 * 下线
	 */
	OFFLINE("2","下线"),
	/**
	 * 未绑定车辆
	 */
	NOCAR("3","未绑定"),
	
	
	/************************************交接班状态枚举**************************************/
	/**
	 * 无对班司机
	 */
	NOSHIFTDRIVER("0","无对班司机"),
	/**
	 * 当班
	 */
	ONSHIFT("1","当班"),
	/**
	 * 歇班
	 */
	OFFSHIFT("2","歇班"),
	/**
	 * 交班中
	 */
	SHIFTING_SEND("3","交班中"),
	/**
	 * 接班中
	 */
	SHIFTING_TAKE("4","接班中"),
	/**
	 * 未排班
	 */
    NOSHIFTPLAN("5","未排班"),
	
	/************************************绑定车辆状态枚举**************************************/
	/**
	 * 司机未绑定车辆 对应boundstate字段(0-未绑 1-已绑)
	 */
	UNBINDCAR("0","未绑定车辆");
	
	
	public String state;
	public String code;
	
	/**
	 * 司机是否处于上班状态
	 * @param code
	 * @return
	 */
	public static boolean isAtWork(String code){
		switch (code) {
		case "0":
		case "1":
			return true;
		case "2":
		case "3":
			return false;
		default:
			return true;
		}
	}
	DriverState(String code,String state){
		this.code = code;
		this.state = state;
	}
}
