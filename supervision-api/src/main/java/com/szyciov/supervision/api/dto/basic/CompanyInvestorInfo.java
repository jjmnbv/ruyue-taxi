package com.szyciov.supervision.api.dto.basic;

import com.supervision.enums.CommandEnum;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 3.2.16	网约车平台投资人信息*(PTTZR)
 * Created by 林志伟 on 2017/7/7.
 */

public class CompanyInvestorInfo extends BasicApi {
    public CompanyInvestorInfo(){
        super();
        setCommand(CommandEnum.CompanyInvestorInfo);
    }
//    所属平台公司统一社会信用代码
    private String epCode;
//    investerName
    private String investerName;
//    投资人证件类型
    private String invIdType;
//    投资人证件号码
    private String invIdCode;
//   投资人咨信金额
    private String money;
    @JsonIgnore
    private Integer state;

    public String getEpCode() {
        return epCode;
    }

    public void setEpCode(String epCode) {
        this.epCode = epCode;
    }

    public String getInvesterName() {
        return investerName;
    }

    public void setInvesterName(String investerName) {
        this.investerName = investerName;
    }

    public String getInvIdType() {
        return invIdType;
    }

    public void setInvIdType(String invIdType) {
        this.invIdType = invIdType;
    }

    public String getInvIdCode() {
        return invIdCode;
    }

    public void setInvIdCode(String invIdCode) {
        this.invIdCode = invIdCode;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
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
        return "CompanyInvestorInfo{" +
                "epCode='" + epCode + '\'' +
                ", investerName='" + investerName + '\'' +
                ", invIdType='" + invIdType + '\'' +
                ", invIdCode='" + invIdCode + '\'' +
                ", money='" + money + '\'' +
                ", state=" + state +
                "} " + super.toString();
    }
}
