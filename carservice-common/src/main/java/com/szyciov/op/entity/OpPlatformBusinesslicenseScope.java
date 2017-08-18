package com.szyciov.op.entity;


import java.util.Date;

/**
 * 经营许可经营范围
 * Created by lzw on 2017/8/15.
 */
public class OpPlatformBusinesslicenseScope {


    /**
     * 主键
     */
    private String id;
    /**
     * 所属经营许可信息id
      */
    private String businesslicenseid;

    /***
     * 经营区域
     * */
    private String  operationarea;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 更新时间
     */
    private Date  updatetime;

    /**
     * 创建人
     */
    private String creater;

    /**
     * 更新人
     */
    private String updater;

    /**
     * 数据状态
     */
    private int status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusinesslicenseid() {
        return businesslicenseid;
    }

    public void setBusinesslicenseid(String businesslicenseid) {
        this.businesslicenseid = businesslicenseid;
    }

    public String getOperationarea() {
        return operationarea;
    }

    public void setOperationarea(String operationarea) {
        this.operationarea = operationarea;
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

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OpPlatformBusinesslicenseScope{" +
                "id='" + id + '\'' +
                ", businesslicenseid='" + businesslicenseid + '\'' +
                ", operationarea='" + operationarea + '\'' +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                ", creater='" + creater + '\'' +
                ", updater='" + updater + '\'' +
                ", status=" + status +
                '}';
    }
}
