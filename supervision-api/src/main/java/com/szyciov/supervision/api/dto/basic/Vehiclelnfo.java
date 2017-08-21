package com.szyciov.supervision.api.dto.basic;

import com.supervision.enums.CommandEnum;

/**
 * 3.2.7	网约车车辆基本信息(CLJB)
 * Created by 林志伟 on 2017/7/6.
 */

public class Vehiclelnfo extends BasicApi {
    public Vehiclelnfo(){
        super();
        setCommand(CommandEnum.Vehiclelnfo);
    }
//    车辆所在城市（注册地行政区划编号）
    private String address;
//    车牌号码
    private String vehicleNo;
//    车牌颜色
    private String plateColor;
//    核定载客位
    private String seats;
//车辆厂牌
    private String brand;
//    车辆型号
    private String model;
//    车辆类型
    private String vehicleType;
//    车辆所有人
    private String ownerName;
//    车身颜色
    private String vehicleColor;
//车辆发动机号
    private String engineId;
//    车辆识别VIN码
    private String vin;
//  车辆注册日期
    private String certifyDateA;
//    车辆燃料类型
    private String fuelType;
//    车辆发动机排量
    private String engineDisplace;
//    发动机功率
    private String enginePower;
//    车辆轴距
    private String wheelBase;
//车辆照片文件
    private String photo;
//    车辆照片文件编号
    private String photoId;
//   网络预约出租汽车运输证字号
    private String certificate;
//    网络预约出租汽车运输证发证机构
    private String transAgency;
//    经营区域
    private String transArea;
//    网络预约出租汽车运输证有效期起
    private String transDateStart;
//    网络预约出租汽车运输证有效期止
    private String transDateStop;
//网约车初次登记日期
    private String certifyDateB;
//    车辆检修状态
    private String fixState;
//  车辆下次年检时间
    private String nextFixDate;
//    车辆年度审验状态
    private String checkState;
//  车辆年度审验日期
    private String checkDate;

//网约车发票打印设备序列号
    private String feePrintId;
//    卫星定位装置品牌
    private String gpsBrand;
//    卫星定位装置型号
    private String gpsModel;
//    卫星定位装置IMEI号
    private String gpsImei;
//    卫星定位装置安装日期
    private String gpsInstallDate;
//    注册日期
    private String registerDate;
//  营运类型
    private String commercialType;
//    营运类型
    private String fareType;
//    车辆技术状况
    private String vehicleTec;
//    安全性能情况
    private String vehicleSafe;
//     租赁公司名称
    private String lesseeName;
//      租赁公司统一社会信用代码
    private String lesseeCode;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public String getEngineId() {
        return engineId;
    }

    public void setEngineId(String engineId) {
        this.engineId = engineId;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getCertifyDateA() {
        return certifyDateA;
    }

    public void setCertifyDateA(String certifyDateA) {
        this.certifyDateA = certifyDateA;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getEngineDisplace() {
        return engineDisplace;
    }

    public void setEngineDisplace(String engineDisplace) {
        this.engineDisplace = engineDisplace;
    }

    public String getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(String enginePower) {
        this.enginePower = enginePower;
    }

    public String getWheelBase() {
        return wheelBase;
    }

    public void setWheelBase(String wheelBase) {
        this.wheelBase = wheelBase;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getTransAgency() {
        return transAgency;
    }

    public void setTransAgency(String transAgency) {
        this.transAgency = transAgency;
    }

    public String getTransArea() {
        return transArea;
    }

    public void setTransArea(String transArea) {
        this.transArea = transArea;
    }

    public String getTransDateStart() {
        return transDateStart;
    }

    public void setTransDateStart(String transDateStart) {
        this.transDateStart = transDateStart;
    }

    public String getTransDateStop() {
        return transDateStop;
    }

    public void setTransDateStop(String transDateStop) {
        this.transDateStop = transDateStop;
    }

    public String getCertifyDateB() {
        return certifyDateB;
    }

    public void setCertifyDateB(String certifyDateB) {
        this.certifyDateB = certifyDateB;
    }

    public String getFixState() {
        return fixState;
    }

    public void setFixState(String fixState) {
        this.fixState = fixState;
    }

    public String getNextFixDate() {
        return nextFixDate;
    }

    public void setNextFixDate(String nextFixDate) {
        this.nextFixDate = nextFixDate;
    }

    public String getCheckState() {
        return checkState;
    }

    public void setCheckState(String checkState) {
        this.checkState = checkState;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public String getFeePrintId() {
        return feePrintId;
    }

    public void setFeePrintId(String feePrintId) {
        this.feePrintId = feePrintId;
    }

    public String getGpsBrand() {
        return gpsBrand;
    }

    public void setGpsBrand(String gpsBrand) {
        this.gpsBrand = gpsBrand;
    }

    public String getGpsModel() {
        return gpsModel;
    }

    public void setGpsModel(String gpsModel) {
        this.gpsModel = gpsModel;
    }

    public String getGpsImei() {
        return gpsImei;
    }

    public void setGpsImei(String gpsImei) {
        this.gpsImei = gpsImei;
    }

    public String getGpsInstallDate() {
        return gpsInstallDate;
    }

    public void setGpsInstallDate(String gpsInstallDate) {
        this.gpsInstallDate = gpsInstallDate;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getCommercialType() {
        return commercialType;
    }

    public void setCommercialType(String commercialType) {
        this.commercialType = commercialType;
    }

    public String getFareType() {
        return fareType;
    }

    public void setFareType(String fareType) {
        this.fareType = fareType;
    }

    public String getVehicleTec() {
        return vehicleTec;
    }

    public void setVehicleTec(String vehicleTec) {
        this.vehicleTec = vehicleTec;
    }

    public String getVehicleSafe() {
        return vehicleSafe;
    }

    public void setVehicleSafe(String vehicleSafe) {
        this.vehicleSafe = vehicleSafe;
    }

    public String getLesseeName() {
        return lesseeName;
    }

    public void setLesseeName(String lesseeName) {
        this.lesseeName = lesseeName;
    }

    public String getLesseeCode() {
        return lesseeCode;
    }

    public void setLesseeCode(String lesseeCode) {
        this.lesseeCode = lesseeCode;
    }

    @Override
    public String toString() {
        return "Vehiclelnfo{" +
                "address='" + address + '\'' +
                ", vehicleNo='" + vehicleNo + '\'' +
                ", plateColor='" + plateColor + '\'' +
                ", seats='" + seats + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", vehicleColor='" + vehicleColor + '\'' +
                ", engineId='" + engineId + '\'' +
                ", vin='" + vin + '\'' +
                ", certifyDateA='" + certifyDateA + '\'' +
                ", fuelType='" + fuelType + '\'' +
                ", engineDisplace='" + engineDisplace + '\'' +
                ", enginePower='" + enginePower + '\'' +
                ", wheelBase='" + wheelBase + '\'' +
                ", photo='" + photo + '\'' +
                ", photoId='" + photoId + '\'' +
                ", certificate='" + certificate + '\'' +
                ", transAgency='" + transAgency + '\'' +
                ", transArea='" + transArea + '\'' +
                ", transDateStart='" + transDateStart + '\'' +
                ", transDateStop='" + transDateStop + '\'' +
                ", certifyDateB='" + certifyDateB + '\'' +
                ", fixState='" + fixState + '\'' +
                ", nextFixDate='" + nextFixDate + '\'' +
                ", checkState='" + checkState + '\'' +
                ", checkDate='" + checkDate + '\'' +
                ", feePrintId='" + feePrintId + '\'' +
                ", gpsBrand='" + gpsBrand + '\'' +
                ", gpsModel='" + gpsModel + '\'' +
                ", gpsImei='" + gpsImei + '\'' +
                ", gpsInstallDate='" + gpsInstallDate + '\'' +
                ", registerDate='" + registerDate + '\'' +
                ", commercialType='" + commercialType + '\'' +
                ", fareType='" + fareType + '\'' +
                ", vehicleTec='" + vehicleTec + '\'' +
                ", vehicleSafe='" + vehicleSafe + '\'' +
                ", lesseeName='" + lesseeName + '\'' +
                ", lesseeCode='" + lesseeCode + '\'' +
                "} " + super.toString();
    }
}
