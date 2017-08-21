package com.szyciov.supervision.api.dto.basic;

import com.supervision.enums.CommandEnum;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 3.2.19	普通企业接入信息*(PTQYJR)
 * Created by 林志伟 on 2017/7/7.
 */

public class EnterpriseAccessInfo extends BasicApi {
    public EnterpriseAccessInfo(){
        super();
        setCommand(CommandEnum.EnterpriseAccessInfo);
    }
//    行政区划编号
    private String address;
//    企业名称
    private String epName;
//    统一社会信用代码
    private String epCode;
//    epStatus
    private String epStatus;
//    注册时间
    private String accessTime;

    @JsonIgnore
    private Integer state;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEpName() {
        return epName;
    }

    public void setEpName(String epName) {
        this.epName = epName;
    }

    public String getEpCode() {
        return epCode;
    }

    public void setEpCode(String epCode) {
        this.epCode = epCode;
    }

    public String getEpStatus() {
        return epStatus;
    }

    public void setEpStatus(String epStatus) {
        this.epStatus = epStatus;
    }

    public String getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(String accessTime) {
        this.accessTime = accessTime;
    }

    @Override
    public Integer getState() {
        return state;
    }

    @Override
    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "EnterpriseAccessInfo{" +
                "address='" + address + '\'' +
                ", epName='" + epName + '\'' +
                ", epCode='" + epCode + '\'' +
                ", epStatus='" + epStatus + '\'' +
                ", accessTime='" + accessTime + '\'' +
                ", state=" + state +
                "} " + super.toString();
    }
}
