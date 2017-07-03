package com.szyciov.enums;

/**
 * GPS来源
 * Created by shikang on 2017/5/18.
 */
public enum GpsSourceEnum {

    /**
     *司机APP GPS
     */
    APP_GPS(0, "司机APP GPS"),

    /**
     *OBD GPS
     */
    OBD_GPS(1, "OBD GPS");

    public static GpsSourceEnum getGpsSource(Integer calctype) {
        if(null == calctype) {
            return null;
        }
        if(calctype == CalcTypeEnum.OBD.code || calctype == CalcTypeEnum.OBD_GPS.code) {
            return OBD_GPS;
        } else {
            return APP_GPS;
        }
    }

    /**
     * GPS来源状态码
     */
    public int code;

    /**
     * GPS来源描述
     */
    public String msg;

    GpsSourceEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
