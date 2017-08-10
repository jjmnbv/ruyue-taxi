package com.szyciov.supervision.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by 林志伟 on 2017/7/26.
 */
public class PubSupervisionLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private Integer direct;
    private String interfaceType;
    private String interfaceCommand;
    private String requestHeader;
    private String requestParam;
    private Integer responceCode;
    private String responceContent;
    private Integer status=1;
    private Date createtime=new Date();
    private Date updatetime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getDirect() {
        return direct;
    }

    public void setDirect(Integer direct) {
        this.direct = direct;
    }

    public String getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(String interfaceType) {
        this.interfaceType = interfaceType;
    }

    public String getInterfaceCommand() {
        return interfaceCommand;
    }

    public void setInterfaceCommand(String interfaceCommand) {
        this.interfaceCommand = interfaceCommand;
    }

    public String getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(String requestHeader) {
        this.requestHeader = requestHeader;
    }

    public String getRequestParam() {
        return requestParam;
    }

    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam;
    }

    public Integer getResponceCode() {
        return responceCode;
    }

    public void setResponceCode(Integer responceCode) {
        this.responceCode = responceCode;
    }

    public String getResponceContent() {
        return responceContent;
    }

    public void setResponceContent(String responceContent) {
        this.responceContent = responceContent;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}
