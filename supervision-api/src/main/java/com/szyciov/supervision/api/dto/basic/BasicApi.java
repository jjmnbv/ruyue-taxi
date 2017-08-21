package com.szyciov.supervision.api.dto.basic;


import com.supervision.enums.InterfaceType;
import com.szyciov.supervision.api.dto.BaseApi;

/**
 * 基础数据api
 * Created by 林志伟 on 2017/7/6.
 */

public class BasicApi extends BaseApi {

    public BasicApi(){
        this.setApiType(InterfaceType.BASIC);
    }

    //    唯一标识
    private String symbol;

    //    状态
    private Integer state;
//  操作标识
    private Integer flag;
//  更新时间
    private String updateTime;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "BasicApi{" +
                "symbol='" + symbol + '\'' +
                ", state=" + state +
                ", flag=" + flag +
                ", updateTime='" + updateTime + '\'' +
                "} " + super.toString();
    }
}
