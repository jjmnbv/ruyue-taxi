package com.szyciov.enums;

/**
 * api服务请求状态枚举
 * Created by LC on 2017/3/2.
 */
public enum  ServiceState {
    /**************************************************通用状态*************************************************/
    /**
     * 请求成功
     */
    SUCCESS(0,"请求成功,无异常"),
    /**
     * 请求失败
     */
    FAILED(-1,"请求失败,无异常"),
    /**
     * 请求异常
     */
    EXCEPTION(-2,"服务器异常");



    public int code ;
    public String msg;
    ServiceState(int code,String msg){
        this.code = code;
        this.msg = msg;
    }


}
 