package com.szyciov.supervision.api.dto.basic;

import com.supervision.enums.CommandEnum;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 3.2.18	个体驾驶员合同信息*(GTJSYHT)
 * Created by 林志伟 on 2017/7/7.
 */

public class DriverCntractInfo extends BasicApi {
    public DriverCntractInfo(){
        super();
        setCommand(CommandEnum.DriverCntractInfo);
    }
//    行政区划编号
    private String address;
//    驾驶员姓名
    private String driverName;
//    电话
    private String driTel;
//    性别
    private String gender;
//    birthday
    private String birthday;
//    民族
    private String driverNation;
//    身份证类别
    private String idType;
//    证件号码
    private String idNo;
//    网络预约出租汽车驾驶员证编号
    private String driCertNo;
//    车牌号码
    private String vehicleNo;
//    车辆识别VIN码
    private String vin;
//    网络预约出租汽车运输证号
    private String vehCertNo;
//    类型
    private String type;
//    上传扫描件类型
    private String contractType;
//    上传扫描件
    private String contractPhoto;
//    合同状态
    private String contractStatus;
//    合同签订时间
    private String signTime;
//    合同生效时间
    private String validTime;
//    合同失效时间
    private String invalidTime;

    @JsonIgnore
    private Integer state;

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

    public String getDriTel() {
        return driTel;
    }

    public void setDriTel(String driTel) {
        this.driTel = driTel;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDriverNation() {
        return driverNation;
    }

    public void setDriverNation(String driverNation) {
        this.driverNation = driverNation;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
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

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getVehCertNo() {
        return vehCertNo;
    }

    public void setVehCertNo(String vehCertNo) {
        this.vehCertNo = vehCertNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getContractPhoto() {
        return contractPhoto;
    }

    public void setContractPhoto(String contractPhoto) {
        this.contractPhoto = contractPhoto;
    }

    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

    public String getSignTime() {
        return signTime;
    }

    public void setSignTime(String signTime) {
        this.signTime = signTime;
    }

    public String getValidTime() {
        return validTime;
    }

    public void setValidTime(String validTime) {
        this.validTime = validTime;
    }

    public String getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(String invalidTime) {
        this.invalidTime = invalidTime;
    }

    @Override
    public Integer getState() {
        return state;
    }

    @Override
    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "DriverCntractInfo{" +
                "address='" + address + '\'' +
                ", driverName='" + driverName + '\'' +
                ", driTel='" + driTel + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday='" + birthday + '\'' +
                ", driverNation='" + driverNation + '\'' +
                ", idType='" + idType + '\'' +
                ", idNo='" + idNo + '\'' +
                ", driCertNo='" + driCertNo + '\'' +
                ", vehicleNo='" + vehicleNo + '\'' +
                ", vin='" + vin + '\'' +
                ", vehCertNo='" + vehCertNo + '\'' +
                ", type='" + type + '\'' +
                ", contractType='" + contractType + '\'' +
                ", contractPhoto='" + contractPhoto + '\'' +
                ", contractStatus='" + contractStatus + '\'' +
                ", signTime='" + signTime + '\'' +
                ", validTime='" + validTime + '\'' +
                ", invalidTime='" + invalidTime + '\'' +
                ", state=" + state +
                "} " + super.toString();
    }
}
