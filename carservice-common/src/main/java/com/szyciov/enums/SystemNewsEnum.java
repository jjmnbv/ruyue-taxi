package com.szyciov.enums;

/**
 * 系统消息枚举
 * Created by LC on 2017/4/19.
 */
public enum  SystemNewsEnum {

    //消息类型 订单消息
    NEWS_TYPE_ORDER("0","订单消息"),
    //消息类型 系统消息
    NEWS_TYPE_SYSTEM("1","系统消息"),
    //消息类型 推广消息
    NEWS_TYPE_GENERALIZE("2","推广消息"),
    //消息类型 其他消息
    NEWS_TYPE_OTHER("3","其他"),

    //消息是否已读 未读
    NEWS_STATE_UNREAD("0","未读"),
    //消息是否已读 已读
    NEWS_STATE_READED("1","已读");

    public String code;
    public String msg;

    SystemNewsEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }


}
 