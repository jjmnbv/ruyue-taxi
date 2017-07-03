package com.szyciov.enums;

/**
 * 交接班过程状态枚举
 * Created by LC on 2017/3/10.
 */
public enum PendInfoEnum {

    NO_RULE(-1,"无交接班规则"),
    DEFAULT(0,"正常"),
    OUT_TIME(1,"交接失败"),
    NO_PENDING(2,"无交班申请"),
    NO_BIND(3,"无绑定信息");




    public Integer code;
    public String msg;

    PendInfoEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


}
