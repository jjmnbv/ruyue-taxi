package com.szyciov.enums;

/**
 * @ClassName PlatformType 
 * @author Efy Shu
 * @Description 终端类型 以数据库为准 包括(0-运营端,1-租赁端)
 * @date 2016年10月12日 下午4:18:58 
 */
public enum PlatformTypeByDb {

	LEASE("1","租赁端"),
	OPERATING("0","运管端");

	public String code;
	public String msg;
	PlatformTypeByDb(String code, String msg){
		this.code = code;
		this.msg = msg;
	}
}
