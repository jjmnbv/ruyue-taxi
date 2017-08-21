package com.szyciov.supervision.api.dto.gps;

import com.supervision.enums.CommandEnum;

/**
 * 3.5.2	乘客定位信息（来自乘客手机app）*(CKDW) 实时
 * Created by 林志伟 on 2017/7/7.
 */

public class PassengerLocationInfo extends GpsApi {
    public PassengerLocationInfo() {
        super();
        setCommand(CommandEnum.PassengerLocationInfo);
    }

    /**
     * 订单编号
     */
    private String orderId;
    /**
     * 驾驶员姓名
     */
    private String driverName;
    /**
     * 驾驶员联系电话
     */
    private String driverPhone;
    /**
     * 驾驶员身份证号
     */
    private String driverIDCard;
    /**
     * 网络预约出租汽车驾驶员证编号
     */
    private String driverCertCard;
    /**
     * 乘客电话
     */
    private String passengerPhone;
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
     * 坐标加密标识
     */
    private String encrypt;
    /**
     * 经度
     */
    private String longitude;
    /**
     * 纬度
     */
    private String latitude;
    /**
     * 瞬时速度
     */
    private String speed;
    /**
     * 报警状态
     */
    private String warnStatus;
    /**
     * 定位时间	YYYYMMDDHHMMSS
     */
    private String positionTime;
    /**
     * 有效性	1:有效
     0:无效

     */
    private Integer validity;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
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

    public String getPassengerPhone() {
        return passengerPhone;
    }

    public void setPassengerPhone(String passengerPhone) {
        this.passengerPhone = passengerPhone;
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

    public String getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(String encrypt) {
        this.encrypt = encrypt;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getWarnStatus() {
        return warnStatus;
    }

    public void setWarnStatus(String warnStatus) {
        this.warnStatus = warnStatus;
    }

    public String getPositionTime() {
        return positionTime;
    }

    public void setPositionTime(String positionTime) {
        this.positionTime = positionTime;
    }

    public Integer getValidity() {
        return validity;
    }

    public void setValidity(Integer validity) {
        this.validity = validity;
    }

    @Override
    public String toString() {
        return "PassengerLocationInfo{" +
                "orderId='" + orderId + '\'' +
                ", driverName='" + driverName + '\'' +
                ", driverPhone='" + driverPhone + '\'' +
                ", driverIDCard='" + driverIDCard + '\'' +
                ", driverCertCard='" + driverCertCard + '\'' +
                ", passengerPhone='" + passengerPhone + '\'' +
                ", vehicleNo='" + vehicleNo + '\'' +
                ", plateColor='" + plateColor + '\'' +
                ", brand='" + brand + '\'' +
                ", encrypt='" + encrypt + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", speed='" + speed + '\'' +
                ", warnStatus='" + warnStatus + '\'' +
                ", positionTime='" + positionTime + '\'' +
                ", validity=" + validity +
                "} " + super.toString();
    }
}
