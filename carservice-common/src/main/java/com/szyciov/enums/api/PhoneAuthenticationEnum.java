package com.szyciov.enums.api;

/**
 * 手机实名认证返回enum
 * Created by LC on 2017/4/8.
 */
public enum  PhoneAuthenticationEnum {

    IS_OK(1,"实名通过");


    public int code;
    public String msg;

    PhoneAuthenticationEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
 