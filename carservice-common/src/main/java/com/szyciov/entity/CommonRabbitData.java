package com.szyciov.entity;

/**
 * Created by shikang on 2017/6/11.
 */
public class CommonRabbitData {

    /**
     * 消息类型(参考CommonRabbitEnum类)
     */
    private String type;

    /**
     * 数据
     */
    private Object data;

    /**
     * 消息延迟时间(单位:秒)
     */
    private Integer expiration;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getExpiration() {
        return expiration;
    }

    public void setExpiration(Integer expiration) {
        this.expiration = expiration;
    }
}
