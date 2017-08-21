package com.szyciov.supervision.api.dto.gps;


import com.supervision.enums.CommandEnum;

/**
 * 3.5.1	驾驶员定位信息（来自司机手机app）(JSYDW) 实时
 * Created by 林志伟 on 2017/7/7.
 */

public class DriverLocationInfo extends GpsApi {
    public DriverLocationInfo() {
        super();
        setCommand(CommandEnum.DriverLocationInfo);
    }
    /**
     * 行政区划代码
     */
    private String driverRegionCode;
    /**
     * 车牌号码	省份简称+城市（地区）编号+5位数字或字母
     */
    private String vehicleNo;
    /**
     * 车牌颜色	见JT/T 697.7—2014中5.6
     */
    private String plateColor;
    /**
     * 车辆注册地行政区划编号（地市级）	见GB/T2260
     */
    private String vehicleRegionCode;
    /**
     * 定位时间	YYYYMMDDHHMMSS
     */
    private String positionTime;
    /**
     * 经度	单位：1*10-6度


     */
    private String longitude;
    /**
     *    纬度	单位：1*10-6度
     */
    private String latitude;
    /**
     * 瞬时速度	单位：km/h
     */
    private String speed;
    /**
     * 方向角	0-359，顺时针方向
     */
    private String direction;
    /**
     * 海拔高度	单位：米
     */
    private String elevation;
    /**
     * 里程	单位：km
     */
    private String mileage;
    /**
     * 坐标加密标识	1：GCJ-02 测绘局标准
     2：WGS84 GPS标准
     3：BD-09 百度标准
     4：CGCS2000 北斗标准
     0：其他

     */
    private String encrypt;
    /**
     * 报警状态	参考JT／T808
     */
    private String warnStatus;
    /**
     * 车辆状态
     */
    private String vehStatus;
    /**
     * 营运状态	1：载客
     2：接单
     3：空驶
     4：停运

     */
    private String bizStatus;
    /**
     * 营运状态	1：载客
     2：接单
     3：空驶
     4：停运
     */
    private String orderId;
    /**
     * 驾驶员姓名
     */
    private String driverName;
    /**
     * 驾驶员标识号
     */
    private String driverId;
    /**
     * 网络预约出租汽车驾驶员证编号
     */
    private String driverCertCard;
    /**
     * 车辆类型
     */
    private String vehicleType;
    /**
     * SJSB:司机上班、SJXB:司机下班、CKSC:乘客上车、CKXC:乘客下车、AUTO:定时上传、DDPD:派单
     */
    private String positionType;
    /**
     * 1:有效
     0:无效

     */
    private Integer validity;

    public String getDriverRegionCode() {
        return driverRegionCode;
    }

    public void setDriverRegionCode(String driverRegionCode) {
        this.driverRegionCode = driverRegionCode;
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

    public String getVehicleRegionCode() {
        return vehicleRegionCode;
    }

    public void setVehicleRegionCode(String vehicleRegionCode) {
        this.vehicleRegionCode = vehicleRegionCode;
    }

    public String getPositionTime() {
        return positionTime;
    }

    public void setPositionTime(String positionTime) {
        this.positionTime = positionTime;
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

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getElevation() {
        return elevation;
    }

    public void setElevation(String elevation) {
        this.elevation = elevation;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(String encrypt) {
        this.encrypt = encrypt;
    }

    public String getWarnStatus() {
        return warnStatus;
    }

    public void setWarnStatus(String warnStatus) {
        this.warnStatus = warnStatus;
    }

    public String getVehStatus() {
        return vehStatus;
    }

    public void setVehStatus(String vehStatus) {
        this.vehStatus = vehStatus;
    }

    public String getBizStatus() {
        return bizStatus;
    }

    public void setBizStatus(String bizStatus) {
        this.bizStatus = bizStatus;
    }

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

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverCertCard() {
        return driverCertCard;
    }

    public void setDriverCertCard(String driverCertCard) {
        this.driverCertCard = driverCertCard;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getPositionType() {
        return positionType;
    }

    public void setPositionType(String positionType) {
        this.positionType = positionType;
    }

    public Integer getValidity() {
        return validity;
    }

    public void setValidity(Integer validity) {
        this.validity = validity;
    }

    @Override
    public String toString() {
        return "DriverLocationInfo{" +
                "driverRegionCode='" + driverRegionCode + '\'' +
                ", vehicleNo='" + vehicleNo + '\'' +
                ", plateColor='" + plateColor + '\'' +
                ", vehicleRegionCode='" + vehicleRegionCode + '\'' +
                ", positionTime='" + positionTime + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", speed='" + speed + '\'' +
                ", direction='" + direction + '\'' +
                ", elevation='" + elevation + '\'' +
                ", mileage='" + mileage + '\'' +
                ", encrypt='" + encrypt + '\'' +
                ", warnStatus='" + warnStatus + '\'' +
                ", vehStatus='" + vehStatus + '\'' +
                ", bizStatus='" + bizStatus + '\'' +
                ", orderId='" + orderId + '\'' +
                ", driverName='" + driverName + '\'' +
                ", driverId='" + driverId + '\'' +
                ", driverCertCard='" + driverCertCard + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                ", positionType='" + positionType + '\'' +
                ", validity=" + validity +
                "} " + super.toString();
    }
}
