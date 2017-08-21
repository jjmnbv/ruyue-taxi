package com.szyciov.supervision.api.dto.basic;

import com.supervision.enums.CommandEnum;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 3.2.17	网约车平台联系人信息*(PTLXR)
 * Created by 林志伟 on 2017/7/7.
 */

public class CompanyConcactInfo extends BasicApi {
    public CompanyConcactInfo(){
        super();
        setCommand(CommandEnum.CompanyConcactInfo);
    }
//    所属平台公司统一社会信用代码
    private String epCode;
//    联系人姓名
    private String contact;
//  联系人电话
    private String contactWay;

    @JsonIgnore
    private Integer state;

    public String getEpCode() {
        return epCode;
    }

    public void setEpCode(String epCode) {
        this.epCode = epCode;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactWay() {
        return contactWay;
    }

    public void setContactWay(String contactWay) {
        this.contactWay = contactWay;
    }

    @Override
    public Integer getState() {
        return state;
    }

    @Override
    public void setState(Integer state) {
        this.state = state;
    }
}
