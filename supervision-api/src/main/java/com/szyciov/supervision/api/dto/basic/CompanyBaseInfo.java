package com.szyciov.supervision.api.dto.basic;

import com.supervision.enums.CommandEnum;

/**
 * 3.2.1	网约车平台公司基本信息(PTJB)
 * Created by 林志伟 on 2017/7/6.
 */
public class CompanyBaseInfo extends BasicApi{
    public CompanyBaseInfo() {
        super();
        this.setCommand(CommandEnum.CompanyBaseInfo);
    }

//    企业名称
    private String companyName;
//  统一社会信用代码
    private String identifier;
//    紧急联系电话
    private String contactPhone;
//    注册地行政区划代码
    private String address;
//    经营范围
    private String businessScope;
//    通信地址
    private String contactAddress;
//      经营业户经济类型
    private String economicType;
//    注册资本
    private String regCapital;
//    法定代表人姓名
    private String legalName;
//  法人代表身份证号
    private String legalId;
//  法人电话
    private String legalPhone;
//   法人代表身份证扫描件文件
    private String legalPhoto;
    //法人代表身份证扫描件文件编号
    private String legalPhotoId;


//  营业执照副本扫描件文件
    private String identifierPhoto;
//    广州通信地址
    private String gzAd;
//    负责人姓名(广州)
    private String responsible;
//    负责人电话(广州)
    private String responsibleWay;
//    母公司名称
    private String parentCompany;
//    母公司通信地址
    private String parentAd;
//    邮政编码（广州）
    private String postCode;
//    服务项目
    private String serviceItem;
//    质量标准和承诺
    private String servicePromise;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getEconomicType() {
        return economicType;
    }

    public void setEconomicType(String economicType) {
        this.economicType = economicType;
    }

    public String getRegCapital() {
        return regCapital;
    }

    public void setRegCapital(String regCapital) {
        this.regCapital = regCapital;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getLegalId() {
        return legalId;
    }

    public void setLegalId(String legalId) {
        this.legalId = legalId;
    }

    public String getLegalPhone() {
        return legalPhone;
    }

    public void setLegalPhone(String legalPhone) {
        this.legalPhone = legalPhone;
    }

    public String getLegalPhoto() {
        return legalPhoto;
    }

    public void setLegalPhoto(String legalPhoto) {
        this.legalPhoto = legalPhoto;
    }

    public String getLegalPhotoId() {
        return legalPhotoId;
    }

    public void setLegalPhotoId(String legalPhotoId) {
        this.legalPhotoId = legalPhotoId;
    }

    public String getIdentifierPhoto() {
        return identifierPhoto;
    }

    public void setIdentifierPhoto(String identifierPhoto) {
        this.identifierPhoto = identifierPhoto;
    }

    public String getGzAd() {
        return gzAd;
    }

    public void setGzAd(String gzAd) {
        this.gzAd = gzAd;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public String getResponsibleWay() {
        return responsibleWay;
    }

    public void setResponsibleWay(String responsibleWay) {
        this.responsibleWay = responsibleWay;
    }

    public String getParentCompany() {
        return parentCompany;
    }

    public void setParentCompany(String parentCompany) {
        this.parentCompany = parentCompany;
    }

    public String getParentAd() {
        return parentAd;
    }

    public void setParentAd(String parentAd) {
        this.parentAd = parentAd;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getServiceItem() {
        return serviceItem;
    }

    public void setServiceItem(String serviceItem) {
        this.serviceItem = serviceItem;
    }

    public String getServicePromise() {
        return servicePromise;
    }

    public void setServicePromise(String servicePromise) {
        this.servicePromise = servicePromise;
    }
}
