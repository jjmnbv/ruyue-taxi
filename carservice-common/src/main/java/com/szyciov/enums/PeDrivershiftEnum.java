package com.szyciov.enums;

/**
 * 交接班管理 状态 枚举
 * Created by LC on 2017/2/28.
 */
public enum PeDrivershiftEnum {


    /**
     * 交接班状态 待处理表
     */
    SHIFT_TYPE_PENDING("0","待处理"),

    /**
     * 交接班状态 正在处理
     */
    PROCESS_TYPE_ING("1","正在处理"),
    /**
     * 交接班状态 已处理表
     */
    SHIFT_TYPE_PROCESSED("0","交接班"),
    SHIFT_TYPE_RECYCLE("1","车辆回收"),
    SHIFT_TYPE_TIMEOUT("2","交班超时"),
    SHIFT_TYPE_ASSIGN("3","指派当班"),


    /**
     * 交接班类型
     */
    RELIVED_TYPE_AUTONOMOUSLY("0","自主交班"),
    RELIVED_TYPE_MANPOWER("1","人工指派");


    public String code;
    public String msg;

    PeDrivershiftEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 返回交接班状态对应汉字
     * @param code  状态码
     * @return
     */
    public static String getShiftTypeStr(String code){
        switch (code) {
            case "0":
                return PeDrivershiftEnum.SHIFT_TYPE_PROCESSED.msg;
            case "1":
                return PeDrivershiftEnum.SHIFT_TYPE_RECYCLE.msg;
            case "2":
                return PeDrivershiftEnum.SHIFT_TYPE_TIMEOUT.msg;
            case "3":
                return PeDrivershiftEnum.SHIFT_TYPE_ASSIGN.msg;
            default:
                return "/";
        }
    }

    /**
     * 返回交接班类型对应汉字
     * @param code  状态码
     * @return
     */
    public static String getRelivedTypeStr(String code){
        switch (code) {
            case "0":
                return PeDrivershiftEnum.RELIVED_TYPE_AUTONOMOUSLY.msg;
            case "1":
                return PeDrivershiftEnum.RELIVED_TYPE_MANPOWER.msg;
            default:
                return "/";
        }
    }

}
