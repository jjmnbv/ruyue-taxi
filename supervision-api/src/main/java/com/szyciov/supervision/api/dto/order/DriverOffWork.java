package com.szyciov.supervision.api.dto.order;

import com.supervision.enums.CommandEnum;

/**
 * 3.3.8	驾驶员下班*(JSYXB) 实时
 * Created by 林志伟 on 2017/7/7.
 */

public class DriverOffWork extends OrderApi {
    public DriverOffWork() {
        super();
        setCommand(CommandEnum.DriverOffWork);

    }

    /**
     * 统一社会信用代码
     */
    private String identifier;
    /**
     * 驾驶员姓名
     */
    private String driverName;
    /**
     * 驾驶员身份证号
     */
    private String driverIDCard;
    /**
     * 网络预约出租汽车驾驶员证编号
     */
    private String driverCertCard;
    /**
     * 驾驶员联系电话
     */
    private String driverPhone;
    /**
     * 车牌号码	省份简称+城市（地区）编号+5位数字或字母
     */
    private String vehicleNo;
    /**
     * 车牌颜色	见JT/T 697.7—2014中5.6
     */
    private String plateColor;
    /**
     * 上班/签到时间	YYYYMMDDHHMMSS
     */
    private String onWorkTime;
    /**
     *下班/签退时间	YYYYMMDDHHMMSS
     */
    private String offWorkTime;
    /**
     * 总载客次数
     */
    private String driveCount;
    /**
     * 总载客里程
     */
    private String driveMile;
    /**
     * 总载客时长	单位：分钟
     */
    private String driveTime;
    /**
     * 总应收金额	 单位：元
     */
    private String price;
    /**
     * 总实收金额	 单位：元
     */
    private String factPrice;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverIDCard() {
        return driverIDCard;
    }

    public void setDriverIDCard(String driverIDCard) {
        this.driverIDCard = driverIDCard;
    }

    public String getDriverCertCard() {
        return driverCertCard;
    }

    public void setDriverCertCard(String driverCertCard) {
        this.driverCertCard = driverCertCard;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getPlateColor() {
        return plateColor;
    }

    public void setPlateColor(String plateColor) {
        this.plateColor = plateColor;
    }

    public String getOnWorkTime() {
        return onWorkTime;
    }

    public void setOnWorkTime(String onWorkTime) {
        this.onWorkTime = onWorkTime;
    }

    public String getOffWorkTime() {
        return offWorkTime;
    }

    public void setOffWorkTime(String offWorkTime) {
        this.offWorkTime = offWorkTime;
    }

    public String getDriveCount() {
        return driveCount;
    }

    public void setDriveCount(String driveCount) {
        this.driveCount = driveCount;
    }

    public String getDriveMile() {
        return driveMile;
    }

    public void setDriveMile(String driveMile) {
        this.driveMile = driveMile;
    }

    public String getDriveTime() {
        return driveTime;
    }

    public void setDriveTime(String driveTime) {
        this.driveTime = driveTime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFactPrice() {
        return factPrice;
    }

    public void setFactPrice(String factPrice) {
        this.factPrice = factPrice;
    }

    @Override
    public String toString() {
        return "DriverOffWork{" +
                "identifier='" + identifier + '\'' +
                ", driverName='" + driverName + '\'' +
                ", driverIDCard='" + driverIDCard + '\'' +
                ", driverCertCard='" + driverCertCard + '\'' +
                ", driverPhone='" + driverPhone + '\'' +
                ", vehicleNo='" + vehicleNo + '\'' +
                ", plateColor='" + plateColor + '\'' +
                ", onWorkTime='" + onWorkTime + '\'' +
                ", offWorkTime='" + offWorkTime + '\'' +
                ", driveCount='" + driveCount + '\'' +
                ", driveMile='" + driveMile + '\'' +
                ", driveTime='" + driveTime + '\'' +
                ", price='" + price + '\'' +
                ", factPrice='" + factPrice + '\'' +
                "} " + super.toString();
    }
}
