package com.szyciov.enums.api;

/**
 * 寻程数据平台返回
 * Created by LC on 2017/4/8.
 */
public enum  XunChengResultEnum {

    ERR_CODE_OK(0,"成功");


    public int code;
    public String msg;

    XunChengResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
 