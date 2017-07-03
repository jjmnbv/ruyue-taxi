package com.szyciov.enums;

public enum SendRulesEnum {
	
	/**
	 * 用车类型
	 */
	USETYPE_APPOINTMENT("0", "预约用车"),
	USETYPE_INSTANTLY("1", "即刻用车"),
	
	/**
	 * 是否限制推送人数
	 */
	PUSHNUMLIMIT_OFF("0","不限制"),
	PUSHNUMLIMIT_ON("1","限制"),
	
	/**
	 * 派单方式
	 */
	SENDTYPE_FORCE("0", "强派"),
	SENDTYPE_ROB("1", "抢派"),
	SENDTYPE_ROB_ORDER("2", "抢单"),
	SENDTYPE_SYSTEM_ONLY("3","纯人工"),
	
	SENDMODEL_SYSTEM("0", "系统"),
	SENDMODEL_SYSTEM_LABOUR("1", "系统+人工"),
	
	/**
	 * 0-当前级别，1-升级
	 */
	VEHICLEUPGRADE_CURRENT("0","当前级别"),
	VEHICLEUPGRADE_UPGRADE("1","允许升级");
	
	
	public static SendRulesEnum getSendType(String sendType){
		for(SendRulesEnum e : SendRulesEnum.values()){
			if(e.name().startsWith("SENDTYPE") && e.code.equals(sendType)){
				return e;
			}
		}
		return null;
	}
	
	public static SendRulesEnum getSendModel(String sendType){
		for(SendRulesEnum e : SendRulesEnum.values()){
			if(e.name().startsWith("SENDMODEL") && e.code.equals(sendType)){
				return e;
			}
		}
		return null;
	}
	
	public static SendRulesEnum getVehicleUpgrade(String vehicleUpgrade){
		for(SendRulesEnum e : SendRulesEnum.values()){
			if(e.name().startsWith("VEHICLEUPGRADE") && e.code.equals(vehicleUpgrade)){
				return e;
			}
		}
		return null;
	}
	
	public String code;
    public String msg;
    
    SendRulesEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
	
}
