package com.szyciov.supervision.api.dto.operation;

import com.supervision.enums.CommandEnum;

/**
 * 3.4.4	营运到达(YYDD) 实时
 * Created by 林志伟 on 2017/7/7.
 */

public class OperationArrival extends OperationApi {
    public OperationArrival() {
        super();
        setCommand(CommandEnum.OperationArrival);
    }

    /**
     * 订单编号	与发送交通部一致
     */
    private String orderId;
    /**
     * 车辆实际到达经度	单位：1*10-6度
     */
    private String destLongitude;
    /**
     * 车辆实际到达纬度	单位：1*10-6度
     */
    private String destLatitude;
    /**
     * 坐标加密标识	1：GCJ-02 测绘局标准
     2：WGS84 GPS标准
     3：BD-09 百度标准
     4：CGCS2000 北斗标准
     0：其他

     */
    private String encrypt;

    /**
     * 实际下车时间	YYYYMMDDHHMMSS
     */
    private String destTime;
    /**
     * 载客里程	单位：km
     */
    private String driveMile;
    /**
     *载客时间	单位：秒
     */
    private String driveTime;

    /**
     * 行政区划编号	见GB/T 2260
     */
    private String address;

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
     * 车牌号码	省份简称+城市（地区）编号+5位数字或字母
     车牌颜色	见JT/T 697.7—2014中5.6
     */
    private String vehicleNo;

    /**
     * 实际上车时间	YYYYMMDDHHMMSS
     */
    private String depTime;
    /**
     * 车辆实际出发经度	单位：1*10-6度
     */
    private String depLongitude;
    /**
     * 车辆实际出发纬度	单位：1*10-6度
     */
    private String depLatitude;
    /**
     * 等待时间	单位：秒
     */
    private String waitTime;


    /**
     * 乘客称谓
     */
    private String passengerName;


    /**
     * 乘客性别	见JT/T 697.7-2014中，与平台发送交通部一致。
     */
    private String passengerSex;
    /**
     * 乘客手机号
     */
    private String passengerPhone;
    /**
     * 应收金额	单位：元
     */
    private String price;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDestLongitude() {
        return destLongitude;
    }

    public void setDestLongitude(String destLongitude) {
        this.destLongitude = destLongitude;
    }

    public String getDestLatitude() {
        return destLatitude;
    }

    public void setDestLatitude(String destLatitude) {
        this.destLatitude = destLatitude;
    }

    public String getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(String encrypt) {
        this.encrypt = encrypt;
    }

    public String getDestTime() {
        return destTime;
    }

    public void setDestTime(String destTime) {
        this.destTime = destTime;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getDepTime() {
        return depTime;
    }

    public void setDepTime(String depTime) {
        this.depTime = depTime;
    }

    public String getDepLongitude() {
        return depLongitude;
    }

    public void setDepLongitude(String depLongitude) {
        this.depLongitude = depLongitude;
    }

    public String getDepLatitude() {
        return depLatitude;
    }

    public void setDepLatitude(String depLatitude) {
        this.depLatitude = depLatitude;
    }

    public String getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(String waitTime) {
        this.waitTime = waitTime;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getPassengerSex() {
        return passengerSex;
    }

    public void setPassengerSex(String passengerSex) {
        this.passengerSex = passengerSex;
    }

    public String getPassengerPhone() {
        return passengerPhone;
    }

    public void setPassengerPhone(String passengerPhone) {
        this.passengerPhone = passengerPhone;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OperationArrival{" +
                "orderId='" + orderId + '\'' +
                ", destLongitude='" + destLongitude + '\'' +
                ", destLatitude='" + destLatitude + '\'' +
                ", encrypt='" + encrypt + '\'' +
                ", destTime='" + destTime + '\'' +
                ", driveMile='" + driveMile + '\'' +
                ", driveTime='" + driveTime + '\'' +
                ", address='" + address + '\'' +
                ", driverName='" + driverName + '\'' +
                ", driverPhone='" + driverPhone + '\'' +
                ", driverIDCard='" + driverIDCard + '\'' +
                ", driverCertCard='" + driverCertCard + '\'' +
                ", vehicleNo='" + vehicleNo + '\'' +
                ", depTime='" + depTime + '\'' +
                ", depLongitude='" + depLongitude + '\'' +
                ", depLatitude='" + depLatitude + '\'' +
                ", waitTime='" + waitTime + '\'' +
                ", passengerName='" + passengerName + '\'' +
                ", passengerSex='" + passengerSex + '\'' +
                ", passengerPhone='" + passengerPhone + '\'' +
                ", price='" + price + '\'' +
                "} " + super.toString();
    }
}
