package com.szyciov.supervision.api.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.supervision.enums.CommandEnum;
import com.supervision.enums.InterfaceType;


/**
 * 定义api相关参数数据
 * Created by 林志伟 on 2017/7/6.
 */

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


    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public InterfaceType getApiType() {
        return apiType;
    }

    public void setApiType(InterfaceType apiType) {
        this.apiType = apiType;
    }

    public CommandEnum getCommand() {
        return command;
    }

    public void setCommand(CommandEnum command) {
        this.command = command;
    }

    public int getRequestFailNum() {
        return requestFailNum;
    }

    public void setRequestFailNum(int requestFailNum) {
        this.requestFailNum = requestFailNum;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "BaseApi{" +
                "success=" + success +
                ", reason='" + reason + '\'' +
                ", apiType=" + apiType +
                ", command=" + command +
                ", requestFailNum=" + requestFailNum +
                ", companyId='" + companyId + '\'' +
                '}';
    }
}

