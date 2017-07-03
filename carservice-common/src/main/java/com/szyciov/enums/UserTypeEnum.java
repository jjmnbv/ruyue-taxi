package com.szyciov.enums;

/**
 * Created by ZF on 2017/4/21.
 */
public enum UserTypeEnum {


    /**
     * 默认普通用户
     */
    DRIVER(1, "司机"),
    /**
     * 超管用户
     */
    PASSENGER(0, "乘客");


    public int value;
    public String name;

    UserTypeEnum(int value, String name) {
        this.name = name;
        this.value = value;
    }
}
