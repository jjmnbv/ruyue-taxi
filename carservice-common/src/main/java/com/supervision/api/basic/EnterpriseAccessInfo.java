package com.supervision.api.basic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 3.2.19	普通企业接入信息*(PTQYJR)
 * Created by 林志伟 on 2017/7/7.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnterpriseAccessInfo extends BasicApi {

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
