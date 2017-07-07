package com.szyciov.supervision.enums;

/**
 * Created by admin on 2017/7/6.
 */
public enum InterfaceType {
    BASIC("BASIC","基础静态信息"),
    OPERATION("OPERATION","经营信息")
    ,GPS("GPS","GPS定位信息")
    ,ORDER("ORDER","订单信息")
    ,EVALUATE("EVALUATE","服务质量信息")
    ,RELATIONSHIP("RELATIONSHIP","人车对应关系信息")
    ,SHARE("SHARE","私人小客车合乘信息")
    ,TOKEN("TOKEN","获取TOKEN");

    private String value;
    private String description;
    private InterfaceType(String v, String desp){
        this.value = v;
        this.description = desp;
    }

    public String value(){
        return this.value;
    }

    public String description(){
        return this.description;
    }

}
