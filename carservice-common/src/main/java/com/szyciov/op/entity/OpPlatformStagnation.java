package com.szyciov.op.entity;

import java.util.Date;

/**
 * 平台驻点信息表
 * Created by lzw on 2017/8/14.
 */
public class OpPlatformStagnation {
    /*
    主键
     */
    private String id;
    /**
     *平台ID
     */
    private String platformid;
    /**
     * 驻点城市：直接保存名称
     */
    private  String city;
    /**
     * 负责人姓名
     */
    private String  responsible;
    /**
     * 负责人电话
     */
    private String responsibleway;
    /**
     * 邮政编码
     */
    private String postcode;
    /**、
     * 通信地址城市
     */
    private String contactaddresscity;
    /**
     * 通信地址明细
     */
    private String contactaddress;
    /**
     * 母公司名称
     */
    private String parentcompany;
    /**
     * 母公司通信地址城市
     */
    private String parentadcity;
    /**
     * 母公司通信地址明细
     */
    private String parentad;

    private Date createtime;
    private Date updatetime;
    private String creater;
    private String updater;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public String getResponsibleway() {
        return responsibleway;
    }

    public void setResponsibleway(String responsibleway) {
        this.responsibleway = responsibleway;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getContactaddresscity() {
        return contactaddresscity;
    }

    public void setContactaddresscity(String contactaddresscity) {
        this.contactaddresscity = contactaddresscity;
    }

    public String getContactaddress() {
        return contactaddress;
    }

    public void setContactaddress(String contactaddress) {
        this.contactaddress = contactaddress;
    }

    public String getParentcompany() {
        return parentcompany;
    }

    public void setParentcompany(String parentcompany) {
        this.parentcompany = parentcompany;
    }

    public String getParentadcity() {
        return parentadcity;
    }

    public void setParentadcity(String parentadcity) {
        this.parentadcity = parentadcity;
    }

    public String getParentad() {
        return parentad;
    }

    public void setParentad(String parentad) {
        this.parentad = parentad;
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
        return "OpPlatformStagnation{" +
                "id='" + id + '\'' +
                ", platformid='" + platformid + '\'' +
                ", city='" + city + '\'' +
                ", responsible='" + responsible + '\'' +
                ", responsibleway='" + responsibleway + '\'' +
                ", postcode='" + postcode + '\'' +
                ", contactaddresscity='" + contactaddresscity + '\'' +
                ", contactaddress='" + contactaddress + '\'' +
                ", parentcompany='" + parentcompany + '\'' +
                ", parentadcity='" + parentadcity + '\'' +
                ", parentad='" + parentad + '\'' +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                ", creater='" + creater + '\'' +
                ", updater='" + updater + '\'' +
                ", status=" + status +
                '}';
    }
}
