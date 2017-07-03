package com.szyciov.enums;

/**
 * OBD安装状态
 * Created by shikang on 2017/5/18.
 */
public enum  OBDInstallStateEnum {

    /**
     * 未安装
     */
    UNINSTALL("0", "未安装"),

    /**
     * 安装OBD
     */
    INSTALL_OBD("1", "安装OBD"),

    /**
     * 安装GPS
     */
    INSTALL_GPS("2", "安装GPS");

    /**
     * OBD安装状态码
     */
    public String code;

    /**
     * OBD安装状态描述
     */
    public String msg;

    OBDInstallStateEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
