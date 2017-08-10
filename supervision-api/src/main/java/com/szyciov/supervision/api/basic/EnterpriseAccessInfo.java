package com.szyciov.supervision.api.basic;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.szyciov.supervision.enums.CommandEnum;

import lombok.Data;

/**
 * 3.2.19	普通企业接入信息*(PTQYJR)
 * Created by 林志伟 on 2017/7/7.
 */
@Data
public class EnterpriseAccessInfo extends BasicApi {
    public EnterpriseAccessInfo(){
        super();
        setCommand(CommandEnum.EnterpriseAccessInfo);
    }
//    行政区划编号
    private String address;
//    企业名称
    private String epName;
//    统一社会信用代码
    private String epCode;
//    epStatus
    private String epStatus;
//    注册时间
    private String accessTime;

    @JsonIgnore
    private Integer state;

}
