/**
 * 
 */
package com.szyciov.driver.enums;

/**
 * @ClassName SystemType 
 * @author Efy Shu
 * @Description TODO(这里用一句话描述这个类的作用) 
 * @date 2016年12月12日 下午5:17:33 
 */
public enum SystemType {
	IOS("0","iOS"),
	ANDROID("1","Android");
	
	public static SystemType getByCode(String code){
		return "0".equals(code) ? IOS : 
					"1".equals(code) ? ANDROID  : null;
	}
	public String code;
	public String msg;
	SystemType(String code,String msg){
		this.code = code;
		this.msg = msg;
	}
}
