package com.szyciov.supervision.api.dto.relationship;

import com.supervision.enums.CommandEnum;

/**
 * 3.7	人车对应关系信息数据*(RCDYGX)
 * Created by 林志伟 on 2017/7/7.
 */

public class HumanVehicleInfo extends RelationShipApi {
    public HumanVehicleInfo() {
        super();
        setCommand(CommandEnum.HumanVehicleInfo);
    }

    /**
     * 注册地行政区划编号	见GB/T 2260
     */
    private String address;
    /**
     * curDriverName
     */
    private String curDriverName;
    /**
     * 当前驾驶员的网络预约出租汽车驾驶员证编号
     */
    private String curDriverCertNo;
    /**
     * 当前车辆车牌号码
     */
    private String curVehicleNo;
    /**
     * 当前车辆的网络预约出租汽车运输证号
     */
    private String curVehicleCertNo;
    /**
     * 当前实际人车对应开始时间
     */
    private String curTimeOn;
    /**
     * 当前实际人车对应结束时间	YYYYMMDDHHMMSS
     */
    private String curTimeOff;
    /**
     * 平台报备驾驶员	驾驶员姓名+身份（主班、替班）
     */
    private String reportDrivers;
    /**
     * 平台报备驾驶员的网络预约出租汽车驾驶员证编号
     */
    private String reportDriverCertNo;
    /**
     * 平台报备车辆车牌号码	省份简称+城市（地区）编号+5位数字或字母
     */
    private String reportVehicleNo;
    /**
     * 平台报备车辆的网络预约出租汽车运输证号
     */
    private String reportVehicleCertNo;
    /**
     * 平台报备开始时间	YYYYMMDDHHMMSS
     */
    private String timeFrom;
    /**
     * 平台报备结束时间	YYYYMMDDHHMMSS
     */
    private String timeTo;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCurDriverName() {
        return curDriverName;
    }

    public void setCurDriverName(String curDriverName) {
        this.curDriverName = curDriverName;
    }

    public String getCurDriverCertNo() {
        return curDriverCertNo;
    }

    public void setCurDriverCertNo(String curDriverCertNo) {
        this.curDriverCertNo = curDriverCertNo;
    }

    public String getCurVehicleNo() {
        return curVehicleNo;
    }

    public void setCurVehicleNo(String curVehicleNo) {
        this.curVehicleNo = curVehicleNo;
    }

    public String getCurVehicleCertNo() {
        return curVehicleCertNo;
    }

    public void setCurVehicleCertNo(String curVehicleCertNo) {
        this.curVehicleCertNo = curVehicleCertNo;
    }

    public String getCurTimeOn() {
        return curTimeOn;
    }

    public void setCurTimeOn(String curTimeOn) {
        this.curTimeOn = curTimeOn;
    }

    public String getCurTimeOff() {
        return curTimeOff;
    }

    public void setCurTimeOff(String curTimeOff) {
        this.curTimeOff = curTimeOff;
    }

    public String getReportDrivers() {
        return reportDrivers;
    }

    public void setReportDrivers(String reportDrivers) {
        this.reportDrivers = reportDrivers;
    }

    public String getReportDriverCertNo() {
        return reportDriverCertNo;
    }

    public void setReportDriverCertNo(String reportDriverCertNo) {
        this.reportDriverCertNo = reportDriverCertNo;
    }

    public String getReportVehicleNo() {
        return reportVehicleNo;
    }

    public void setReportVehicleNo(String reportVehicleNo) {
        this.reportVehicleNo = reportVehicleNo;
    }

    public String getReportVehicleCertNo() {
        return reportVehicleCertNo;
    }

    public void setReportVehicleCertNo(String reportVehicleCertNo) {
        this.reportVehicleCertNo = reportVehicleCertNo;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    @Override
    public String toString() {
        return "HumanVehicleInfo{" +
                "address='" + address + '\'' +
                ", curDriverName='" + curDriverName + '\'' +
                ", curDriverCertNo='" + curDriverCertNo + '\'' +
                ", curVehicleNo='" + curVehicleNo + '\'' +
                ", curVehicleCertNo='" + curVehicleCertNo + '\'' +
                ", curTimeOn='" + curTimeOn + '\'' +
                ", curTimeOff='" + curTimeOff + '\'' +
                ", reportDrivers='" + reportDrivers + '\'' +
                ", reportDriverCertNo='" + reportDriverCertNo + '\'' +
                ", reportVehicleNo='" + reportVehicleNo + '\'' +
                ", reportVehicleCertNo='" + reportVehicleCertNo + '\'' +
                ", timeFrom='" + timeFrom + '\'' +
                ", timeTo='" + timeTo + '\'' +
                "} " + super.toString();
    }
}
