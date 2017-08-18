package com.szyciov.op.entity;

import java.util.Date;

/**
 * 经营许可信息
 * Created by lzw on 2017/8/15.
 */
public class OpPlatformBusinesslicense {

    /**
     * 主键
     */
    private String id;

    /**
     * 平台ID
     */
    private String platformid;

    /**
     * 经营许可证号
     */
    private String certificate;

    /**
     * 经营许可地
     */
    private String address;


    /**
     *发证机构
     */
    private String organization;

    /**
     * 有效期限开始时间
     */
    private String startdate;


    /**
     * 有效期限截止时间
     */
    private String stopdate;

    /**
     * 初次发证日期
      */
    private String certifydate;

    /**
     * 证照状态
     */
    private int state;
    /**
     * 创建时间
     */
    private Date createtime;


    /**
     * 更新时间
     */
    private Date updatetime;

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

    public String getPlatformid() {
        return platformid;
    }

    public void setPlatformid(String platformid) {
        this.platformid = platformid;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getStopdate() {
        return stopdate;
    }

    public void setStopdate(String stopdate) {
        this.stopdate = stopdate;
    }

    public String getCertifydate() {
        return certifydate;
    }

    public void setCertifydate(String certifydate) {
        this.certifydate = certifydate;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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
        return "OpPlatformBusinesslicense{" +
                "id='" + id + '\'' +
                ", platformid='" + platformid + '\'' +
                ", certificate='" + certificate + '\'' +
                ", address='" + address + '\'' +
                ", organization='" + organization + '\'' +
                ", startdate='" + startdate + '\'' +
                ", stopdate='" + stopdate + '\'' +
                ", certifydate='" + certifydate + '\'' +
                ", state=" + state +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                ", creater='" + creater + '\'' +
                ", updater='" + updater + '\'' +
                ", status=" + status +
                '}';
    }
}
