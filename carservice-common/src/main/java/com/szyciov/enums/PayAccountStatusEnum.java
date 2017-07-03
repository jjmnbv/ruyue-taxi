package com.szyciov.enums;

/**
 * 支付宝/微信账户状态
 * @author shikang_pc
 *
 */
public enum PayAccountStatusEnum {
	
	FORBIDDEN("0", "未开通"),
	
	DREDGE("1", "已开通");
	
	public String code;
    public String msg;

    PayAccountStatusEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
	
}
