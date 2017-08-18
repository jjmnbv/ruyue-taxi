package com.supervision.dto;

import com.supervision.enums.CommandEnum;
import com.supervision.enums.InterfaceType;

import java.io.Serializable;
import java.util.Map;

/**
 * 监管传输对象
 * Created by lzw on 2017/8/18.
 */
public class SupervisionDto implements Serializable {
    /**
     * 接口类型
     */
    private InterfaceType interfaceType;


    /**
     * 接口命令
     */
    private CommandEnum commandEnum;


    /**
     * 当前时间戳，毫秒
     */
    private long timestamp;

    /**
     * 传递关键数据
     */
    private Map<String,String> dataMap;

    public CommandEnum getCommandEnum() {
        return commandEnum;
    }

    public void setCommandEnum(CommandEnum commandEnum) {
        this.commandEnum = commandEnum;
    }

    public InterfaceType getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(InterfaceType interfaceType) {
        this.interfaceType = interfaceType;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, String> getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map<String, String> dataMap) {
        this.dataMap = dataMap;
    }

    @Override
    public String toString() {
        return "SupervisionDto{" +
                "interfaceType=" + interfaceType +
                ", commandEnum=" + commandEnum +
                ", timestamp=" + timestamp +
                ", dataMap=" + dataMap +
                '}';
    }
}
