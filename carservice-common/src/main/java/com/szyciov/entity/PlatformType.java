/**
 * 
 */
package com.szyciov.entity;

/**
 * @ClassName PlatformType 
 * @author Efy Shu
 * @Description 终端类型 包括(0-乘客端,1-司机端,2-租赁端,3-机构端,4-运营端)
 * @date 2016年10月12日 下午4:18:58 
 */
public enum PlatformType {
	PASSENGER("0","乘客端"),
	DRIVER("1","司机端"),
	LEASE("2","租赁端"),
	ORGAN("3","机构端"),
	OPERATING("4","运营端");
	
	public static PlatformType getByCode(String code){
		return "0".equals(code) ? PASSENGER :
					"1".equals(code) ? DRIVER : 
					"2".equals(code) ? LEASE : 
					"3".equals(code) ? ORGAN : 
					"4".equals(code) ? OPERATING : null;
	}
	
	/**
	 * 从数据库订单来源获取推送平台
	 * @param dbcode   数据库的订单来源
	 * @param isOrg       是否机构订单
	 * @return
	 */
	public static PlatformType getByDBCode(String dbcode,boolean isOrg){
		switch (dbcode) {
		case "0":
			return PASSENGER;
		case "1":
			if(isOrg)
				return LEASE;
			else 
				return OPERATING;
		case "2":
			return ORGAN;
		default:
			return null;
		}
	}
	public String code;
	public String msg;
	PlatformType(String code,String msg){
		this.code = code;
		this.msg = msg;
	}
}
