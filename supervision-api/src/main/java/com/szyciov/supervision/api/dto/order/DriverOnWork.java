package com.szyciov.supervision.api.dto.order;

import com.supervision.enums.CommandEnum;

/**
 * 3.3.7	驾驶员上班*(JSYSB) 实时
 * Created by 林志伟 on 2017/7/7.
 */

public class DriverOnWork extends OrderApi {


    public DriverOnWork(){
        super();
        setCommand(CommandEnum.DriverOnWork);

    }

    /**
     * identifier
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
     * 车牌号码
     */
    private String vehicleNo;
    /**
     * 车牌颜色
     */
    private String plateColor;
    /**
     * 车辆厂牌
     */
    private String brand;

    /**
     * 上班时间	YYYYMMDDHHMMSS
     */
    private String onWorkTime;

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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getOnWorkTime() {
        return onWorkTime;
    }

    public void setOnWorkTime(String onWorkTime) {
        this.onWorkTime = onWorkTime;
    }

    @Override
    public String toString() {
        return "DriverOnWork{" +
                "identifier='" + identifier + '\'' +
                ", driverName='" + driverName + '\'' +
                ", driverIDCard='" + driverIDCard + '\'' +
                ", driverCertCard='" + driverCertCard + '\'' +
                ", driverPhone='" + driverPhone + '\'' +
                ", vehicleNo='" + vehicleNo + '\'' +
                ", plateColor='" + plateColor + '\'' +
                ", brand='" + brand + '\'' +
                ", onWorkTime='" + onWorkTime + '\'' +
                "} " + super.toString();
    }
}
