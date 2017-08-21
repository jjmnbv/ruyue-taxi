package com.szyciov.supervision.api.dto.operation;

import com.supervision.enums.CommandEnum;

/**
 * 3.4.5	营运支付(YYZF) 实时
 * Created by 林志伟 on 2017/7/7.
 */

public class OperationPay extends OperationApi {
    public OperationPay() {
        super();
        setCommand(CommandEnum.OperationPay);
    }

    /**
     * 订单编号	与发送交通部一致
     */
    private String orderId;
    /**
     * 上车位置行政区划编号	见GB/T 2260
     */
    private String onArea;
    /**
     * 机动车驾驶员姓名
     */
    private String driverName;
    /**
     * 机动车驾驶证号
     */
    private String licenseId;
    /**
     * 运价类型编码	由网约车平台公司定义，与计程计价方式信息接口一一对应。
     */
    private String fareType;
    /**
     * 车牌号码	省份简称+城市（地区）编号+5位数字或字母
     */
    private String vehicleNo;
    /**
     * 车牌颜色	见JT/T 697.7—2014中5.6
     */
    private String plateColor;
    /**
     * 预计上车时间	YYYYMMDDHHMMSS
     */
    private String bookDepTime;
    /**
     * 等待时间	单位：秒
     */
    private String waitTime;
    /**
     * 车辆实际出发经度	单位：1*10-6度
     */
    private String depLongitude;
    /**
     * 车辆实际出发纬度	单位：1*10-6度
     */
    private String depLatitude;

    /**
     * 实际上车地点
     */
    private String depArea;


    /**
     * 实际上车时间
     */
    private String depTime;


    /**
     *  车辆实际到达经度
     */
    private String destLongitude;

    /**
     *  车辆实际到达纬度
     */
    private String destLatitude;
    /**
     * 实际下车地点
     */
    private String destArea;
    /**
     * 实际下车时间	YYYYMMDDHHMMSSS
     */
    private String destTime;
    /**
     * 预定车型
     */
    private String bookModel;
    /**
     * 实际使用车型
     */
    private String model;
    /**
     * 载客里程	单位：km
     */
    private String driveMile;
    /**
     * 载客时间	单位：秒
     */
    private String driveTime;
    /**
     * 空驶里程	单位：km
     */
    private String waitMile;
    /**
     * 实收金额	单位：元
     */
    private String factPrice;
    /**
     * 应收金额	单位：元
     */
    private String price;
    /**
     * 现金支付金额	单位：元
     */
    private String cashPrice;
    /**
     * 电子支付机构
     */
    private String lineName;
    /**
     * 电子支付金额	单位：元
     */
    private String linePrice;
    /**
     * POS机支付机构
     */
    private String posName;
    /**
     * POS机支付金额	单位：元
     */
    private String posPrice;
    /**
     * 优惠金额	单位：元
     */
    private String benfitPrice;
    /**
     * 预约服务费	单位：元
     */
    private String bookTip;
    /**
     * 附加费	单位：元
     */
    private String passengerTip;
    /**
     * 高峰时段时间加价金额	单位：元
     */
    private String peakUpPrice;
    /**
     * 夜间时段里程加价金额	单位：元
     */
    private String nightUpPrice;
    /**
     *远途加价金额	单位：元
     */
    private String farUpPrice;
    /**
     * 其他加价金额	单位：元
     */
    private String otherUpPrice;
    /**
     * 结算状态
     */
    private String payState;
    /**
     * 结算时间	YYYYMMDDHHMMSS
     */
    private String payTime;
    /**
     * 订单完成时间	YYYYMMDDHHMMSS
     */
    private String orderMatchTime;

    /**
     * 发票状态	数据取值有效范围：
     0：未开票
     1：已开票
     2：未知

     */
    private String invoiceStatus;
    /**
     * 驾驶员身份证号
     */
    private String driverIDCard;
    /**
     * 网络预约出租汽车驾驶员证编号
     */
    private String driverCertCard;
    /**
     * 电召费	单位：元
     */
    private String callPrice;
    /**
     * 用于补充计费规则
     */
    private String fareRuleUrl;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOnArea() {
        return onArea;
    }

    public void setOnArea(String onArea) {
        this.onArea = onArea;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
    }

    public String getFareType() {
        return fareType;
    }

    public void setFareType(String fareType) {
        this.fareType = fareType;
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

    public String getBookDepTime() {
        return bookDepTime;
    }

    public void setBookDepTime(String bookDepTime) {
        this.bookDepTime = bookDepTime;
    }

    public String getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(String waitTime) {
        this.waitTime = waitTime;
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

    public String getDepArea() {
        return depArea;
    }

    public void setDepArea(String depArea) {
        this.depArea = depArea;
    }

    public String getDepTime() {
        return depTime;
    }

    public void setDepTime(String depTime) {
        this.depTime = depTime;
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

    public String getDestArea() {
        return destArea;
    }

    public void setDestArea(String destArea) {
        this.destArea = destArea;
    }

    public String getDestTime() {
        return destTime;
    }

    public void setDestTime(String destTime) {
        this.destTime = destTime;
    }

    public String getBookModel() {
        return bookModel;
    }

    public void setBookModel(String bookModel) {
        this.bookModel = bookModel;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
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

    public String getWaitMile() {
        return waitMile;
    }

    public void setWaitMile(String waitMile) {
        this.waitMile = waitMile;
    }

    public String getFactPrice() {
        return factPrice;
    }

    public void setFactPrice(String factPrice) {
        this.factPrice = factPrice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCashPrice() {
        return cashPrice;
    }

    public void setCashPrice(String cashPrice) {
        this.cashPrice = cashPrice;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getLinePrice() {
        return linePrice;
    }

    public void setLinePrice(String linePrice) {
        this.linePrice = linePrice;
    }

    public String getPosName() {
        return posName;
    }

    public void setPosName(String posName) {
        this.posName = posName;
    }

    public String getPosPrice() {
        return posPrice;
    }

    public void setPosPrice(String posPrice) {
        this.posPrice = posPrice;
    }

    public String getBenfitPrice() {
        return benfitPrice;
    }

    public void setBenfitPrice(String benfitPrice) {
        this.benfitPrice = benfitPrice;
    }

    public String getBookTip() {
        return bookTip;
    }

    public void setBookTip(String bookTip) {
        this.bookTip = bookTip;
    }

    public String getPassengerTip() {
        return passengerTip;
    }

    public void setPassengerTip(String passengerTip) {
        this.passengerTip = passengerTip;
    }

    public String getPeakUpPrice() {
        return peakUpPrice;
    }

    public void setPeakUpPrice(String peakUpPrice) {
        this.peakUpPrice = peakUpPrice;
    }

    public String getNightUpPrice() {
        return nightUpPrice;
    }

    public void setNightUpPrice(String nightUpPrice) {
        this.nightUpPrice = nightUpPrice;
    }

    public String getFarUpPrice() {
        return farUpPrice;
    }

    public void setFarUpPrice(String farUpPrice) {
        this.farUpPrice = farUpPrice;
    }

    public String getOtherUpPrice() {
        return otherUpPrice;
    }

    public void setOtherUpPrice(String otherUpPrice) {
        this.otherUpPrice = otherUpPrice;
    }

    public String getPayState() {
        return payState;
    }

    public void setPayState(String payState) {
        this.payState = payState;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getOrderMatchTime() {
        return orderMatchTime;
    }

    public void setOrderMatchTime(String orderMatchTime) {
        this.orderMatchTime = orderMatchTime;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
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

    public String getCallPrice() {
        return callPrice;
    }

    public void setCallPrice(String callPrice) {
        this.callPrice = callPrice;
    }

    public String getFareRuleUrl() {
        return fareRuleUrl;
    }

    public void setFareRuleUrl(String fareRuleUrl) {
        this.fareRuleUrl = fareRuleUrl;
    }

    @Override
    public String toString() {
        return "OperationPay{" +
                "orderId='" + orderId + '\'' +
                ", onArea='" + onArea + '\'' +
                ", driverName='" + driverName + '\'' +
                ", licenseId='" + licenseId + '\'' +
                ", fareType='" + fareType + '\'' +
                ", vehicleNo='" + vehicleNo + '\'' +
                ", plateColor='" + plateColor + '\'' +
                ", bookDepTime='" + bookDepTime + '\'' +
                ", waitTime='" + waitTime + '\'' +
                ", depLongitude='" + depLongitude + '\'' +
                ", depLatitude='" + depLatitude + '\'' +
                ", depArea='" + depArea + '\'' +
                ", depTime='" + depTime + '\'' +
                ", destLongitude='" + destLongitude + '\'' +
                ", destLatitude='" + destLatitude + '\'' +
                ", destArea='" + destArea + '\'' +
                ", destTime='" + destTime + '\'' +
                ", bookModel='" + bookModel + '\'' +
                ", model='" + model + '\'' +
                ", driveMile='" + driveMile + '\'' +
                ", driveTime='" + driveTime + '\'' +
                ", waitMile='" + waitMile + '\'' +
                ", factPrice='" + factPrice + '\'' +
                ", price='" + price + '\'' +
                ", cashPrice='" + cashPrice + '\'' +
                ", lineName='" + lineName + '\'' +
                ", linePrice='" + linePrice + '\'' +
                ", posName='" + posName + '\'' +
                ", posPrice='" + posPrice + '\'' +
                ", benfitPrice='" + benfitPrice + '\'' +
                ", bookTip='" + bookTip + '\'' +
                ", passengerTip='" + passengerTip + '\'' +
                ", peakUpPrice='" + peakUpPrice + '\'' +
                ", nightUpPrice='" + nightUpPrice + '\'' +
                ", farUpPrice='" + farUpPrice + '\'' +
                ", otherUpPrice='" + otherUpPrice + '\'' +
                ", payState='" + payState + '\'' +
                ", payTime='" + payTime + '\'' +
                ", orderMatchTime='" + orderMatchTime + '\'' +
                ", invoiceStatus='" + invoiceStatus + '\'' +
                ", driverIDCard='" + driverIDCard + '\'' +
                ", driverCertCard='" + driverCertCard + '\'' +
                ", callPrice='" + callPrice + '\'' +
                ", fareRuleUrl='" + fareRuleUrl + '\'' +
                "} " + super.toString();
    }
}
