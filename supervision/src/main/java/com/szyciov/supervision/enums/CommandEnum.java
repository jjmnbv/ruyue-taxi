package com.szyciov.supervision.enums;

/**
 * Created by admin on 2017/7/6.
 */
public enum CommandEnum {
    ACCESS("ACCESS","");

    private String value;
    private String description;

    private CommandEnum(String v, String desp){
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
