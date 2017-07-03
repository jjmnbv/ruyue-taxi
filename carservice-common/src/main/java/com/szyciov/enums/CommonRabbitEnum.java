package com.szyciov.enums;

/**
 * 队列数据类型
 * Created by shikang on 2017/6/11.
 */
public enum CommonRabbitEnum {

    SEND_RULE_PUSH("0", "派单推送");

    public String code;

    public String msg;

    public static CommonRabbitEnum getCommonRabbit(String type) {
        if(SEND_RULE_PUSH.code.equals(type)) {
            return CommonRabbitEnum.SEND_RULE_PUSH;
        } else {
            return null;
        }
    }

    CommonRabbitEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
