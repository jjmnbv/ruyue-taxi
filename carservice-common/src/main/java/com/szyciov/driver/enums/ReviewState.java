/**
 * 
 */
package com.szyciov.driver.enums;

/**
 * 复核状态 
 * @ClassName ReviewState 
 * @author Efy Shu
 * @Description 包括(0-未复核，1-异常待复核，2-已复核)
 * @date 2016年9月22日 下午9:11:02 
 */
public enum ReviewState {
	/**
	 * 未复核
	 */
	NOTREVIEW("0","未复核"),
	/**
	 * 异常待复核
	 */
	WAITFORREVIEW("1","异常待复核"),
	/**
	 * 已复核
	 */
	REVIEWED("2","已复核");
	
	public String state;
	public String msg;
	ReviewState(String state,String msg){
		this.msg = msg;
		this.state = state;
	}
}
