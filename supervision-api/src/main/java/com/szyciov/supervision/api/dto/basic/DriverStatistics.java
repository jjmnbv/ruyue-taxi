package com.szyciov.supervision.api.dto.basic;

import com.supervision.enums.CommandEnum;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 3.2.13	网约车驾驶员统计信息(JSYTJ)
 * Created by 林志伟 on 2017/7/7.
 */

public class DriverStatistics extends BasicApi {
    public DriverStatistics(){
        super();
        setCommand(CommandEnum.DriverStatistics);
    }
//    注册地行政区划代码
    private String address;
//    机动车驾驶证号
    private String licenseId;
//    统计周期
    private String cycle;
//    完成订单次数
    private Integer orderCount;
//    交通违章次数
    private Integer trafficViolationCount;

//
    private Integer trafficAccidentCount;

//    乘客被投诉次数
    private String complainedCount;
//    接单违约次数
    private String violateRecord;

    @JsonIgnore
    private Integer state;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public Integer getTrafficViolationCount() {
        return trafficViolationCount;
    }

    public void setTrafficViolationCount(Integer trafficViolationCount) {
        this.trafficViolationCount = trafficViolationCount;
    }

    public Integer getTrafficAccidentCount() {
        return trafficAccidentCount;
    }

    public void setTrafficAccidentCount(Integer trafficAccidentCount) {
        this.trafficAccidentCount = trafficAccidentCount;
    }

    public String getComplainedCount() {
        return complainedCount;
    }

    public void setComplainedCount(String complainedCount) {
        this.complainedCount = complainedCount;
    }

    public String getViolateRecord() {
        return violateRecord;
    }

    public void setViolateRecord(String violateRecord) {
        this.violateRecord = violateRecord;
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
        return "DriverStatistics{" +
                "address='" + address + '\'' +
                ", licenseId='" + licenseId + '\'' +
                ", cycle='" + cycle + '\'' +
                ", orderCount=" + orderCount +
                ", trafficViolationCount=" + trafficViolationCount +
                ", trafficAccidentCount=" + trafficAccidentCount +
                ", complainedCount='" + complainedCount + '\'' +
                ", violateRecord='" + violateRecord + '\'' +
                ", state=" + state +
                "} " + super.toString();
    }
}
