package com.szyciov.supervision.api.dto.basic;

import com.supervision.enums.CommandEnum;

/**
 * 3.2.10	网约车驾驶员基本信息(JSYJB)
 * Created by 林志伟 on 2017/7/6.
 */

public class DriverInfo extends BasicApi {
    public DriverInfo() {
        super();
        setCommand(CommandEnum.DriverInfo);
    }

//    注册地行政区划编号
    private String address;
//    机动车驾驶员姓名
    private String driverName;
//    驾驶员身份证号
    private String driverIDCard;
//  驾驶员联系电话
    private String driverPhone;
//    驾驶员性别
    private String driverGender;
//    出生日期
    private String driverBirthday;
//    国籍
    private String driverNationality;
//    驾驶员民族
    private String driverNation;
//    驾驶员婚姻状况
    private String driverMaritalStatus;
//    驾驶员外语能力
    private String driverLanguageLevel;
//    驾驶员学历
    private String driverEducation;
//    户口登记机关名称
    private String driverCensus;
//    户口地址或常住地址
    private String driverAddress;
//    驾驶员通信地址
    private String driverContactAddress;
//    驾驶员照片文件
    private String photo;
//    驾驶员照片文件编号
    private String photoId;
//    机动车驾驶证号
    private String licenseId;
//    机动车驾驶证扫描件文件
    private String licensePhoto;
//    机动车驾驶证扫描件文件编号
    private String licensePhotoId;
//    准驾车型
    private String driverType;
//    初次领取驾驶证日期
    private String getDriverLicenseDate;
//    驾驶证有效期限起
    private String driverLicenseOn;
//    驾驶证有效期限止
    private String driverLicenseOff;
//    是否巡游出租汽车驾驶员
    private String taxiDriver;
//    网络预约出租汽车驾驶员资格证号
    private String certificateA;
//    巡游出租汽车驾驶员资格证号
    private String certificateB;
//  网络预约出租汽车驾驶员证发证机构
    private String networkCarIssueOrganization;
//    资格证发证日期
    private String networkCarIssueDate;
//    初次领取资格证日期
    private String getNetworkCarProofDate;
//    网络预约出租汽车驾驶员证有效期起
    private String networkCarProofOn;
//    网络预约出租汽车驾驶员证有效期止
    private String networkCarProofOff;
//    注册日期
    private String registerDate;
//    是否专职驾驶员
    private String fullTimeDriver;
//    是否在驾驶员黑名单内
    private String inDriverBlacklist;
//    出租资格类别
    private String commercialType;
//    驾驶员合同（或协议）签署公司标识
    private String contractCompany;
//    合同（或协议）有效期起
    private String contractOn;
//    合同（或协议）有效期止
    private String contractOff;
//    紧急情况联系人
    private String emergencyContact;
//    紧急情况联系人电话
    private String emergencyContactPhone;
//    紧急情况联系人通讯地址
    private String emergencyContactAddress;
//    奖惩情况
    private String rewardPunishment;

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

    public String getDriverIDCard() {
        return driverIDCard;
    }

    public void setDriverIDCard(String driverIDCard) {
        this.driverIDCard = driverIDCard;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public String getDriverGender() {
        return driverGender;
    }

    public void setDriverGender(String driverGender) {
        this.driverGender = driverGender;
    }

    public String getDriverBirthday() {
        return driverBirthday;
    }

    public void setDriverBirthday(String driverBirthday) {
        this.driverBirthday = driverBirthday;
    }

    public String getDriverNationality() {
        return driverNationality;
    }

    public void setDriverNationality(String driverNationality) {
        this.driverNationality = driverNationality;
    }

    public String getDriverNation() {
        return driverNation;
    }

    public void setDriverNation(String driverNation) {
        this.driverNation = driverNation;
    }

    public String getDriverMaritalStatus() {
        return driverMaritalStatus;
    }

    public void setDriverMaritalStatus(String driverMaritalStatus) {
        this.driverMaritalStatus = driverMaritalStatus;
    }

    public String getDriverLanguageLevel() {
        return driverLanguageLevel;
    }

    public void setDriverLanguageLevel(String driverLanguageLevel) {
        this.driverLanguageLevel = driverLanguageLevel;
    }

    public String getDriverEducation() {
        return driverEducation;
    }

    public void setDriverEducation(String driverEducation) {
        this.driverEducation = driverEducation;
    }

    public String getDriverCensus() {
        return driverCensus;
    }

    public void setDriverCensus(String driverCensus) {
        this.driverCensus = driverCensus;
    }

    public String getDriverAddress() {
        return driverAddress;
    }

    public void setDriverAddress(String driverAddress) {
        this.driverAddress = driverAddress;
    }

    public String getDriverContactAddress() {
        return driverContactAddress;
    }

    public void setDriverContactAddress(String driverContactAddress) {
        this.driverContactAddress = driverContactAddress;
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

    public String getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
    }

    public String getLicensePhoto() {
        return licensePhoto;
    }

    public void setLicensePhoto(String licensePhoto) {
        this.licensePhoto = licensePhoto;
    }

    public String getLicensePhotoId() {
        return licensePhotoId;
    }

    public void setLicensePhotoId(String licensePhotoId) {
        this.licensePhotoId = licensePhotoId;
    }

    public String getDriverType() {
        return driverType;
    }

    public void setDriverType(String driverType) {
        this.driverType = driverType;
    }

    public String getGetDriverLicenseDate() {
        return getDriverLicenseDate;
    }

    public void setGetDriverLicenseDate(String getDriverLicenseDate) {
        this.getDriverLicenseDate = getDriverLicenseDate;
    }

    public String getDriverLicenseOn() {
        return driverLicenseOn;
    }

    public void setDriverLicenseOn(String driverLicenseOn) {
        this.driverLicenseOn = driverLicenseOn;
    }

    public String getDriverLicenseOff() {
        return driverLicenseOff;
    }

    public void setDriverLicenseOff(String driverLicenseOff) {
        this.driverLicenseOff = driverLicenseOff;
    }

    public String getTaxiDriver() {
        return taxiDriver;
    }

    public void setTaxiDriver(String taxiDriver) {
        this.taxiDriver = taxiDriver;
    }

    public String getCertificateA() {
        return certificateA;
    }

    public void setCertificateA(String certificateA) {
        this.certificateA = certificateA;
    }

    public String getCertificateB() {
        return certificateB;
    }

    public void setCertificateB(String certificateB) {
        this.certificateB = certificateB;
    }

    public String getNetworkCarIssueOrganization() {
        return networkCarIssueOrganization;
    }

    public void setNetworkCarIssueOrganization(String networkCarIssueOrganization) {
        this.networkCarIssueOrganization = networkCarIssueOrganization;
    }

    public String getNetworkCarIssueDate() {
        return networkCarIssueDate;
    }

    public void setNetworkCarIssueDate(String networkCarIssueDate) {
        this.networkCarIssueDate = networkCarIssueDate;
    }

    public String getGetNetworkCarProofDate() {
        return getNetworkCarProofDate;
    }

    public void setGetNetworkCarProofDate(String getNetworkCarProofDate) {
        this.getNetworkCarProofDate = getNetworkCarProofDate;
    }

    public String getNetworkCarProofOn() {
        return networkCarProofOn;
    }

    public void setNetworkCarProofOn(String networkCarProofOn) {
        this.networkCarProofOn = networkCarProofOn;
    }

    public String getNetworkCarProofOff() {
        return networkCarProofOff;
    }

    public void setNetworkCarProofOff(String networkCarProofOff) {
        this.networkCarProofOff = networkCarProofOff;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getFullTimeDriver() {
        return fullTimeDriver;
    }

    public void setFullTimeDriver(String fullTimeDriver) {
        this.fullTimeDriver = fullTimeDriver;
    }

    public String getInDriverBlacklist() {
        return inDriverBlacklist;
    }

    public void setInDriverBlacklist(String inDriverBlacklist) {
        this.inDriverBlacklist = inDriverBlacklist;
    }

    public String getCommercialType() {
        return commercialType;
    }

    public void setCommercialType(String commercialType) {
        this.commercialType = commercialType;
    }

    public String getContractCompany() {
        return contractCompany;
    }

    public void setContractCompany(String contractCompany) {
        this.contractCompany = contractCompany;
    }

    public String getContractOn() {
        return contractOn;
    }

    public void setContractOn(String contractOn) {
        this.contractOn = contractOn;
    }

    public String getContractOff() {
        return contractOff;
    }

    public void setContractOff(String contractOff) {
        this.contractOff = contractOff;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getEmergencyContactPhone() {
        return emergencyContactPhone;
    }

    public void setEmergencyContactPhone(String emergencyContactPhone) {
        this.emergencyContactPhone = emergencyContactPhone;
    }

    public String getEmergencyContactAddress() {
        return emergencyContactAddress;
    }

    public void setEmergencyContactAddress(String emergencyContactAddress) {
        this.emergencyContactAddress = emergencyContactAddress;
    }

    public String getRewardPunishment() {
        return rewardPunishment;
    }

    public void setRewardPunishment(String rewardPunishment) {
        this.rewardPunishment = rewardPunishment;
    }

    @Override
    public String toString() {
        return "DriverInfo{" +
                "address='" + address + '\'' +
                ", driverName='" + driverName + '\'' +
                ", driverIDCard='" + driverIDCard + '\'' +
                ", driverPhone='" + driverPhone + '\'' +
                ", driverGender='" + driverGender + '\'' +
                ", driverBirthday='" + driverBirthday + '\'' +
                ", driverNationality='" + driverNationality + '\'' +
                ", driverNation='" + driverNation + '\'' +
                ", driverMaritalStatus='" + driverMaritalStatus + '\'' +
                ", driverLanguageLevel='" + driverLanguageLevel + '\'' +
                ", driverEducation='" + driverEducation + '\'' +
                ", driverCensus='" + driverCensus + '\'' +
                ", driverAddress='" + driverAddress + '\'' +
                ", driverContactAddress='" + driverContactAddress + '\'' +
                ", photo='" + photo + '\'' +
                ", photoId='" + photoId + '\'' +
                ", licenseId='" + licenseId + '\'' +
                ", licensePhoto='" + licensePhoto + '\'' +
                ", licensePhotoId='" + licensePhotoId + '\'' +
                ", driverType='" + driverType + '\'' +
                ", getDriverLicenseDate='" + getDriverLicenseDate + '\'' +
                ", driverLicenseOn='" + driverLicenseOn + '\'' +
                ", driverLicenseOff='" + driverLicenseOff + '\'' +
                ", taxiDriver='" + taxiDriver + '\'' +
                ", certificateA='" + certificateA + '\'' +
                ", certificateB='" + certificateB + '\'' +
                ", networkCarIssueOrganization='" + networkCarIssueOrganization + '\'' +
                ", networkCarIssueDate='" + networkCarIssueDate + '\'' +
                ", getNetworkCarProofDate='" + getNetworkCarProofDate + '\'' +
                ", networkCarProofOn='" + networkCarProofOn + '\'' +
                ", networkCarProofOff='" + networkCarProofOff + '\'' +
                ", registerDate='" + registerDate + '\'' +
                ", fullTimeDriver='" + fullTimeDriver + '\'' +
                ", inDriverBlacklist='" + inDriverBlacklist + '\'' +
                ", commercialType='" + commercialType + '\'' +
                ", contractCompany='" + contractCompany + '\'' +
                ", contractOn='" + contractOn + '\'' +
                ", contractOff='" + contractOff + '\'' +
                ", emergencyContact='" + emergencyContact + '\'' +
                ", emergencyContactPhone='" + emergencyContactPhone + '\'' +
                ", emergencyContactAddress='" + emergencyContactAddress + '\'' +
                ", rewardPunishment='" + rewardPunishment + '\'' +
                "} " + super.toString();
    }
}
