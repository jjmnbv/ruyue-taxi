package com.szyciov.supervision.api.dto.basic;

import com.supervision.enums.CommandEnum;

/**
 * 3.2.5	网约车平台公司经营许可(CPTJYXK)
 * Created by 林志伟 on 2017/7/6.
 */

public class CompanyOperatingPermit extends  BasicApi {
    public CompanyOperatingPermit(){
        super();
        this.setCommand(CommandEnum.CompanyOperatingPermit);
    }
//    许可地行政区划代码
    private String address;
//    网络预约出租汽车经营许可证号
    private String certificate;
//    经营区域
    private String operationArea;
//    公司名称
    private String ownerName;
//    经营许可证发证机构
    private String organization;
//    经营许可证有效期起
    private String startDate;
//    经营许可证有效期止
    private String stopDate;
//  经营许可证初次发证日期
    private String certifyDate;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getOperationArea() {
        return operationArea;
    }

    public void setOperationArea(String operationArea) {
        this.operationArea = operationArea;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStopDate() {
        return stopDate;
    }

    public void setStopDate(String stopDate) {
        this.stopDate = stopDate;
    }

    public String getCertifyDate() {
        return certifyDate;
    }

    public void setCertifyDate(String certifyDate) {
        this.certifyDate = certifyDate;
    }

    @Override
    public String toString() {
        return "CompanyOperatingPermit{" +
                "address='" + address + '\'' +
                ", certificate='" + certificate + '\'' +
                ", operationArea='" + operationArea + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", organization='" + organization + '\'' +
                ", startDate='" + startDate + '\'' +
                ", stopDate='" + stopDate + '\'' +
                ", certifyDate='" + certifyDate + '\'' +
                "} " + super.toString();
    }
}
