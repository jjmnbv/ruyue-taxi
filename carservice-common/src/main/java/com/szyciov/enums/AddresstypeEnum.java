package com.szyciov.enums;

/**
 * Created by shikang on 2017/6/12.
 */
public enum AddresstypeEnum {

    HONE("0", "家"),

    COMPANY("1", "公司"),

    OTHER("2", "其他");

    public String code;

    public String msg;

    public static AddresstypeEnum getAddresstype(String addresstype) {
        if(HONE.code.equals(addresstype)) {
            return HONE;
        } else if(COMPANY.code.equals(addresstype)) {
            return COMPANY;
        } else {
            return OTHER;
        }
    }

    AddresstypeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
