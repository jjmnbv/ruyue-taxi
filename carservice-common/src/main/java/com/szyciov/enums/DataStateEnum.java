package com.szyciov.enums;

/**
 * 数据状态
 * Created by LC on 2017/7/28.
 */
public enum  DataStateEnum {


    SUCCESS(1,"有效"),
    DELETED(0,"已删除");

    public Integer code;
    public String msg;

    DataStateEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
 