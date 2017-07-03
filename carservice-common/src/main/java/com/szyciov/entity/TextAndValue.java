package com.szyciov.entity;

/**
 * text and value entity
 * Created by liubangwei_lc on 2017/6/7.
 */
public class TextAndValue {

    private String id;

    private String value;

    private Integer status;

    private String text;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
