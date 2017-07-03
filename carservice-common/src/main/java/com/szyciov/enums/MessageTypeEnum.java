package com.szyciov.enums;

/**
 * 消息序列号枚举
 * Created by chenjunfeng on 2017/3/23.
 */
public enum MessageTypeEnum {
	/**
     * 充值成功
     */
	FINANCIALMANAGEMENT_RECHARGE("36", "机构充值成功"),
	/**
     * 提现申请
     */
	FINANCIALMANAGEMENT_WITHDRAW("37", "机构提现申请"),
	
	/**
     * 绑定成功
     */
	BIND_SUCCESS("42", "绑定成功"),
	/**
     * 解绑成功
     */
	UNBIND_SUCCESS("41", "解绑成功");
	
	
	public String code;
    public String msg;

	MessageTypeEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
