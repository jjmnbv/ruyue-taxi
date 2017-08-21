package com.szyciov.supervision.api.dto.evaluate;

import com.supervision.enums.CommandEnum;

/**
 * 3.6.4	驾驶员信誉信息(JSYXY)
 * Created by 林志伟 on 2017/7/7.
 */

public class DriverCreditInfo extends EvaluateApi {
    public DriverCreditInfo() {
        super();
        setCommand(CommandEnum.DriverCreditInfo);
    }

    /**
     * 注册地行政区划编号	驾驶员在平台的注册地，见GB/T 2260
     */
    private String address;
    /**
     * 机动车驾驶证号
     */
    private String licenseId;
    /**
     * 完成订单次数
     */
    private String orderCount;
    /**
     * 处罚次数
     */
    private String punishCount;
    /**
     * 乘客投诉次数
     */
    private String complaintCount;
    /**
     * 服务质量信誉等级	五分制
     */
    private String level;
    /**
     * 服务质量信誉考核日期	YYYYMMDD
     */
    private String testDate;
    /**
     * 服务质量信誉考核机构
     */
    private String testDepartment;
    /**
     * 驾驶员姓名
     */
    private String driverName;
    /**
     * 网络预约出租汽车驾驶员证编号
     */
    private String driverCertCard;
    /**
     * 车牌号码	省份简称+城市（地区）编号+5位数字或字母
     */
    private String vehicleNo;
    /**
     * 网络预约出租汽车运输证号
     */
    private String vehicleCertNo;
    /**
     * 服务质量信誉考核结果
     */
    private String testResult;

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

    public String getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(String orderCount) {
        this.orderCount = orderCount;
    }

    public String getPunishCount() {
        return punishCount;
    }

    public void setPunishCount(String punishCount) {
        this.punishCount = punishCount;
    }

    public String getComplaintCount() {
        return complaintCount;
    }

    public void setComplaintCount(String complaintCount) {
        this.complaintCount = complaintCount;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTestDate() {
        return testDate;
    }

    public void setTestDate(String testDate) {
        this.testDate = testDate;
    }

    public String getTestDepartment() {
        return testDepartment;
    }

    public void setTestDepartment(String testDepartment) {
        this.testDepartment = testDepartment;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverCertCard() {
        return driverCertCard;
    }

    public void setDriverCertCard(String driverCertCard) {
        this.driverCertCard = driverCertCard;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getVehicleCertNo() {
        return vehicleCertNo;
    }

    public void setVehicleCertNo(String vehicleCertNo) {
        this.vehicleCertNo = vehicleCertNo;
    }

    public String getTestResult() {
        return testResult;
    }

    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }

    @Override
    public String toString() {
        return "DriverCreditInfo{" +
                "address='" + address + '\'' +
                ", licenseId='" + licenseId + '\'' +
                ", orderCount='" + orderCount + '\'' +
                ", punishCount='" + punishCount + '\'' +
                ", complaintCount='" + complaintCount + '\'' +
                ", level='" + level + '\'' +
                ", testDate='" + testDate + '\'' +
                ", testDepartment='" + testDepartment + '\'' +
                ", driverName='" + driverName + '\'' +
                ", driverCertCard='" + driverCertCard + '\'' +
                ", vehicleNo='" + vehicleNo + '\'' +
                ", vehicleCertNo='" + vehicleCertNo + '\'' +
                ", testResult='" + testResult + '\'' +
                "} " + super.toString();
    }
}
