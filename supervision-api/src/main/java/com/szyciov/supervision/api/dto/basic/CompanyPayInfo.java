package com.szyciov.supervision.api.dto.basic;

import com.supervision.enums.CommandEnum;

/**
 * 3.2.3网约车平台公司支付信息(PTZF)
 * Created by 林志伟 on 2017/7/6.
 */

public class CompanyPayInfo extends BasicApi {
    public CompanyPayInfo(){
        super();
        setCommand(CommandEnum.CompanyPayInfo);
    }

//    银行或者非银行支付机构名称
    private String payName;
//    非银行支付机构支付业务许可证编号
    private String payId;
//  支付业务类型
    private String payType;
//  业务覆盖范围
    private String payScope;
//    备付金存管银行
    private String prepareBank;
//    与银行、非银行支付机构的结算周期
    private Integer countDate;

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayScope() {
        return payScope;
    }

    public void setPayScope(String payScope) {
        this.payScope = payScope;
    }

    public String getPrepareBank() {
        return prepareBank;
    }

    public void setPrepareBank(String prepareBank) {
        this.prepareBank = prepareBank;
    }

    public Integer getCountDate() {
        return countDate;
    }

    public void setCountDate(Integer countDate) {
        this.countDate = countDate;
    }

    @Override
    public String toString() {
        return "CompanyPayInfo{" +
                "payName='" + payName + '\'' +
                ", payId='" + payId + '\'' +
                ", payType='" + payType + '\'' +
                ", payScope='" + payScope + '\'' +
                ", prepareBank='" + prepareBank + '\'' +
                ", countDate=" + countDate +
                "} " + super.toString();
    }
}
