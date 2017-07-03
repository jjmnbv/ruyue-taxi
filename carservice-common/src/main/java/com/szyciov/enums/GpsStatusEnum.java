package com.szyciov.enums;

/**
 * GPS坐标点状态
 * Created by shikang on 2017/5/22.
 */
public enum GpsStatusEnum {

    /**
     * 有效
     */
    VALID(1, "有效"),

    /**
     * 无效
     */
    INVALID(2, "无效");

    /**
     * 坐标点状态
     */
    public int code;

    /**
     * 坐标点状态描述
     */
    public String msg;

    GpsStatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
