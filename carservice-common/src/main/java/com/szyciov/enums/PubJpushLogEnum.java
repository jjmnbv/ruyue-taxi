package com.szyciov.enums;

/**
 * Created by shikang on 2017/6/7.
 */
public enum PubJpushLogEnum {

    FAIL_PUSHSTATE_PUBJPUSHLOG(0, "0", "推送失败"),

    SUCCESS_PUSHSTATE_PUBJPUSHLOG(1, "1", "推送成功"),

    FAIL_HANDSTATE_PUBJPUSHLOG(0, "0", "未举手"),

    SUCCESS_HANDSTATE_PUBJPUSHLOG(1, "1", "已举手"),

    FAIL_TAKEORDERSTATE_PUBJPUSHLOG(0, "0", "接单失败"),

    SUCCESS_TAKEORDERSTATE_PUBJPUSHLOG(1, "1", "接单成功"),

    DRIVER_TAKEORDER_PUSHTYPE_PUBJPUSHLOG(0, "0", "司机接单推送"),

    DRIVER_USERTYPE_PUBJPUSHREGIDLOG(0, "0", "司机"),

    ORGUSER_USERTYPE_PUBJPUSHREGIDLOG(1, "1", "机构用户"),

    PEUSER_USERTYPE_PUBJPUSHREGIDLOG(2, "2", "个人用户");

    public int icode;

    public String code;

    public String msg;

    PubJpushLogEnum(int icode, String code, String msg) {
        this.icode = icode;
        this.code = code;
        this.msg = msg;
    }

}
