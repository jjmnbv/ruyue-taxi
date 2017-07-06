package com.szyciov.supervision.enums;

/**
 * Created by admin on 2017/7/6.
 */
public enum RequestType {

    REQ("REQ","请求"),
    RES("REQ","应答");

    private String value;
    private String description;

    private RequestType(String v, String desp){
        this.value = v;
        this.description = desp;
    }

    public String getValue(){
        return this.value;
    }

    public String getDescription(){
        return this.description;
    }
}
