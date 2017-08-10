package com.szyciov.supervision.api;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.szyciov.supervision.enums.CommandEnum;
import com.szyciov.supervision.enums.InterfaceType;

import lombok.Data;

/**
 * 定义api相关参数数据
 * Created by 林志伟 on 2017/7/6.
 */
@Data
public class BaseApi implements Serializable {
    private Integer success;
    private String reason;
    //  api 类型,不转换成json
    @JsonIgnore
    private InterfaceType apiType;
    //  api 命令
    @JsonIgnore
    private CommandEnum command;

    /**
     * 失败次数
     */
    @JsonIgnore
    private int requestFailNum=0;

    //    公司标识，与交通部一致,平台标识。
    private String companyId="RY";


}

