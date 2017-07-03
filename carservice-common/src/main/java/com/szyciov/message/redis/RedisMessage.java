package com.szyciov.message.redis;

/**
 * redis消息实体类
 * Created by LC on 2016/11/22.
 */
public class RedisMessage {

    /**
     * 操作类型 只读/可操作
     */
    private String operation;

    /**
     * 业务标识
     */
    private String business;

    /**
     * 使用功能
     */
    private String function;

    /**
     * 自定义key
     */
    private String key;

    /**
     * 当前存入时间 单位：毫秒
     */
    private Long nowTime;

    /**
     * 设置超时时间 单位：毫秒
     */
    private Long exTime;

    /**
     * 消息内容主体
     */
    private Object message;


    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getNowTime() {
        return nowTime;
    }

    public void setNowTime(Long nowTime) {
        this.nowTime = nowTime;
    }

    public Long getExTime() {
        return exTime;
    }

    public void setExTime(Long exTime) {
        this.exTime = exTime;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }
}
