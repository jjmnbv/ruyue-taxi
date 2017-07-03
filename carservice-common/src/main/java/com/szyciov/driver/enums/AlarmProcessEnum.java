/**
 * 
 */
package com.szyciov.driver.enums;

/**
 * @ClassName AlermProcessEnum 
 * @author Efy Shu
 * @Description 报警枚举
 * @date 2017年3月23日 下午3:02:44 
 */
public enum AlarmProcessEnum {
	/**
	 * 报警来源-乘客
	 */
	ALARMSOURCE_PASSENGER("0","乘客"),
	/**
	 * 报警来源-司机
	 */
	ALARMSOURCE_DRIVER("1","司机"),
	/**
	 * 报警类型-候客报警
	 */
	ALARMTYPE_WAITING("0","候客报警"),
	/**
	 * 报警类型-行程中报警
	 */
	ALARMTYPE_INSERVICE("1","行程中报警"),
	/**
	 * 报警状态-待处理
	 */
	PROCESSSTATUS_WAITING("0","待处理"),
	/**
	 * 报警状态-已处理
	 */
	PROCESSSTATUS_PROCESSED("1","已处理"),
	/**
	 * 报警结果-假警
	 */
	PROCESSRESULT_FAKEAPPLY("0","假警"),
	/**
	 * 报警结果-涉嫌遇险
	 */
	PROCESSRESULT_REALAPPLY("1","涉嫌遇险");
	
	
	public String msg;
	public String code;
	
	AlarmProcessEnum(String code,String msg){
		this.code = code;
		this.msg = msg;
	}
}
