package com.szyciov.enums;

/**
 * Created by shikang on 2017/8/7.
 */
public enum OrderReviewEnum {

    REVIEWPERSON_DRIVER("1", 1, "司机"),
    REVIEWPERSON_PASSENGER("2", 2, "乘客"),

    REVIEWTYPE_MILEAGE("1", 1, "按里程时长"),
    REVIEWTYPE_COST("2", 2, "按固定金额");


    public String code;

    public int icode;

    public String msg;

    OrderReviewEnum(String code, int icode, String msg) {
        this.code = code;
        this.icode = icode;
        this.msg = msg;
    }

}
