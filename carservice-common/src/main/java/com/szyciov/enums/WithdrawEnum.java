package com.szyciov.enums;

/**
 * 提现管理状态枚举
 * Created by LC on 2017/3/29.
 */
public enum WithdrawEnum {


    //用户类型
    USER_TYPE_ORGUSER("0","机构用户"),
    USER_TYPE_USER("1","乘客"),
    USER_TYPE_DRIVER("2","司机"),
    USER_TYPE_ORG("3","机构用户"),

    //处理状态
    PROCESS_STATUS_PENDING("0","待处理"),
    PROCESS_STATUS_PROCESSED("1","已处理"),

    //处理结果
    PROCESS_RESULT_SUCCESS("0","已打款"),
    PROCESS_RESULT_REJECT("1","不予提现");


    public String code;
    public String msg;

    WithdrawEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getTypeMsg(String code){
        switch (code) {
            case "0":
                return WithdrawEnum.USER_TYPE_ORGUSER.msg;
            case "1":
                return WithdrawEnum.USER_TYPE_USER.msg;
            case "2":
                return WithdrawEnum.USER_TYPE_DRIVER.msg;
            case "3":
                return WithdrawEnum.USER_TYPE_ORG.msg;
            default:
                return "/";
        }

    }


    public static String getResultMsg(String code){
        switch (code) {
            case "0":
                return WithdrawEnum.PROCESS_RESULT_SUCCESS.msg;
            case "1":
                return WithdrawEnum.PROCESS_RESULT_REJECT.msg;
            default:
                return "/";
        }

    }
}
