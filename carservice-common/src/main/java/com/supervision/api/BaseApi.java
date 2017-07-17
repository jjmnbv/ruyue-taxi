package com.supervision.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.supervision.enums.CommandEnum;
import com.supervision.enums.InterfaceType;
import lombok.Data;

import java.io.Serializable;

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

