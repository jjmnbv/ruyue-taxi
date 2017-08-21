package com.szyciov.supervision.api.dto.basic;

import com.supervision.enums.CommandEnum;

/**
 * 3.2.4	网约车平台公司服务机构(PTFWJG)
 * Created by 林志伟 on 2017/7/6.
 */

public class CompanyServiceOrgan extends BasicApi {
    public CompanyServiceOrgan(){
        super();
        this.setCommand(CommandEnum.CompanyServiceOrgan);
    }

    /**
     * 行政区划代码
     */
    private String address;

//    服务机构名称
    private String serviceName;
//    服务机构代码
    private String serviceNo;
//    服务机构具体地址
    private String detailAddress;
//  服务机构负责人姓名
    private String responsibleName;

    /**
     * 服务机构负责人联系方式
     */
    private String responsiblePhone;



//  服务机构管理人姓名
    private String managerName;
//    服务机构管理人联系方式
    private String managerPhone;
//    服务机构紧急联系电话
    private String contactPhone;
//    行政文书送达邮寄地址
    private String mailAddress;
//服务机构设立日期
    private String createDate;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceNo() {
        return serviceNo;
    }

    public void setServiceNo(String serviceNo) {
        this.serviceNo = serviceNo;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getResponsibleName() {
        return responsibleName;
    }

    public void setResponsibleName(String responsibleName) {
        this.responsibleName = responsibleName;
    }

    public String getResponsiblePhone() {
        return responsiblePhone;
    }

    public void setResponsiblePhone(String responsiblePhone) {
        this.responsiblePhone = responsiblePhone;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerPhone() {
        return managerPhone;
    }

    public void setManagerPhone(String managerPhone) {
        this.managerPhone = managerPhone;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "CompanyServiceOrgan{" +
                "address='" + address + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", serviceNo='" + serviceNo + '\'' +
                ", detailAddress='" + detailAddress + '\'' +
                ", responsibleName='" + responsibleName + '\'' +
                ", responsiblePhone='" + responsiblePhone + '\'' +
                ", managerName='" + managerName + '\'' +
                ", managerPhone='" + managerPhone + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", mailAddress='" + mailAddress + '\'' +
                ", createDate='" + createDate + '\'' +
                "} " + super.toString();
    }
}
