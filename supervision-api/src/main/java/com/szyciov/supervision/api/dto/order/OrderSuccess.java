package com.szyciov.supervision.api.dto.order;

import com.supervision.enums.CommandEnum;

/**
 * 订单成功
 * Created by 林志伟 on 2017/7/7.
 */

public class OrderSuccess extends OrderApi {
    public OrderSuccess(){
        super();
        setCommand(CommandEnum.OrderSuccess);
    }

    //    订单编号
    private String orderId;

    //    订单时间	YYYYMMDDHHMMSS
    private String orderTime;

//    车辆经度	单位：1*10-6度
    private String longitude;
//    车辆纬度	单位：1*10-6度
    private String latitude;

    /**
     * 坐标加密标识
     *
     *  1：GCJ-02 测绘局标准
        2：WGS84 GPS标准
        3：BD-09 百度标准
        4：CGCS2000 北斗标准0：其他

     */
    private String encrypt;
//    机动车驾驶证号
    private String licenseId;
//    驾驶员联系电话
    private String driverPhone;
//    车牌号码
    private String vehicleNo;
//    车牌颜色
    private String plateColor;
//    派单成功时间
    private String distributeTime;

//   网络预约出租汽车运输证号
    private String carCertNo;
//    驾驶员姓名
    private String driverName;
//    驾驶员身份证号
    private String idNo;
//    网络预约出租汽车驾驶员证编号
    private String driCertNo;
//    出发城市
    private String departCity;
//  目的城市
    private String destCity;
//    预计出发地点
    private String departLocale;
//    预计出发地点详细地址
    private String departLocaleDetail;
//    预计出发地点经度	单位：1*10-6度
    private String departLon;
//    预计出发地点纬度	单位：1*10-6度
    private String departLat;
//    预计目的地点
    private String desLocale;
//预计目的地点详细地址
    private String desLocaleDetail;
//    预计目的地点经度	单位：1*10-6度
    private String desLon;
//    预计目的地点纬度	单位：1*10-6度
    private String desLat;
//    车辆响应状态
    private String resStatus;
//    车辆响应时长	单位：秒
    private String resTime;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
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

    public String getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(String encrypt) {
        this.encrypt = encrypt;
    }

    public String getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
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

    public String getDistributeTime() {
        return distributeTime;
    }

    public void setDistributeTime(String distributeTime) {
        this.distributeTime = distributeTime;
    }

    public String getCarCertNo() {
        return carCertNo;
    }

    public void setCarCertNo(String carCertNo) {
        this.carCertNo = carCertNo;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getDriCertNo() {
        return driCertNo;
    }

    public void setDriCertNo(String driCertNo) {
        this.driCertNo = driCertNo;
    }

    public String getDepartCity() {
        return departCity;
    }

    public void setDepartCity(String departCity) {
        this.departCity = departCity;
    }

    public String getDestCity() {
        return destCity;
    }

    public void setDestCity(String destCity) {
        this.destCity = destCity;
    }

    public String getDepartLocale() {
        return departLocale;
    }

    public void setDepartLocale(String departLocale) {
        this.departLocale = departLocale;
    }

    public String getDepartLocaleDetail() {
        return departLocaleDetail;
    }

    public void setDepartLocaleDetail(String departLocaleDetail) {
        this.departLocaleDetail = departLocaleDetail;
    }

    public String getDepartLon() {
        return departLon;
    }

    public void setDepartLon(String departLon) {
        this.departLon = departLon;
    }

    public String getDepartLat() {
        return departLat;
    }

    public void setDepartLat(String departLat) {
        this.departLat = departLat;
    }

    public String getDesLocale() {
        return desLocale;
    }

    public void setDesLocale(String desLocale) {
        this.desLocale = desLocale;
    }

    public String getDesLocaleDetail() {
        return desLocaleDetail;
    }

    public void setDesLocaleDetail(String desLocaleDetail) {
        this.desLocaleDetail = desLocaleDetail;
    }

    public String getDesLon() {
        return desLon;
    }

    public void setDesLon(String desLon) {
        this.desLon = desLon;
    }

    public String getDesLat() {
        return desLat;
    }

    public void setDesLat(String desLat) {
        this.desLat = desLat;
    }

    public String getResStatus() {
        return resStatus;
    }

    public void setResStatus(String resStatus) {
        this.resStatus = resStatus;
    }

    public String getResTime() {
        return resTime;
    }

    public void setResTime(String resTime) {
        this.resTime = resTime;
    }

    @Override
    public String toString() {
        return "OrderSuccess{" +
                "orderId='" + orderId + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", encrypt='" + encrypt + '\'' +
                ", licenseId='" + licenseId + '\'' +
                ", driverPhone='" + driverPhone + '\'' +
                ", vehicleNo='" + vehicleNo + '\'' +
                ", plateColor='" + plateColor + '\'' +
                ", distributeTime='" + distributeTime + '\'' +
                ", carCertNo='" + carCertNo + '\'' +
                ", driverName='" + driverName + '\'' +
                ", idNo='" + idNo + '\'' +
                ", driCertNo='" + driCertNo + '\'' +
                ", departCity='" + departCity + '\'' +
                ", destCity='" + destCity + '\'' +
                ", departLocale='" + departLocale + '\'' +
                ", departLocaleDetail='" + departLocaleDetail + '\'' +
                ", departLon='" + departLon + '\'' +
                ", departLat='" + departLat + '\'' +
                ", desLocale='" + desLocale + '\'' +
                ", desLocaleDetail='" + desLocaleDetail + '\'' +
                ", desLon='" + desLon + '\'' +
                ", desLat='" + desLat + '\'' +
                ", resStatus='" + resStatus + '\'' +
                ", resTime='" + resTime + '\'' +
                "} " + super.toString();
    }
}

