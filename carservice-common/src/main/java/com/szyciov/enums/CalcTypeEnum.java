package com.szyciov.enums;

/**
 * 里程计算方式
 * Created by shikang on 2017/5/18.
 */
public enum CalcTypeEnum {

    /**
     *OBD里程
     */
    OBD(0, "OBD里程"),

    /**
     * OBD GPS里程
     */
    OBD_GPS(1, "OBD GPS里程"),

    /**
     * APP GPS里程
     */
    APP_GPS(2, "APP GPS里程"),

    /**
     * 鹰眼里程
     */
    LBSYUN(3, "鹰眼里程"),

    /**
     * 预估里程
     */
    ESTIMATE(4, "预估里程");

    /**
     * 里程计算类型状态码
     */
    public int code;

    /**
     * 里程计算类型描述
     */
    public String msg;

    CalcTypeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
