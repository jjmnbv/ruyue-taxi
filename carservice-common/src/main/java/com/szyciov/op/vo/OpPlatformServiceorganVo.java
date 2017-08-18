package com.szyciov.op.vo;

import java.util.Date;

/**
 * Created by Administrator on 2017/8/8 0008.
 */
public class OpPlatformServiceorganVo {
    /**
     * 主键
     */
    private String id;

    /**
     * 平台ID
     */
    private String platformid;
    /**
     * 服务名称
     */
    private String servicename;

    /**
     * 服务机构代码
     */
    private String serviceno;

    /**
     * 机构负责人
     */
    private String responsiblename;

    /**
     * 负责人电话
     */
    private String responsiblephone;

    /**
     * 机构管理人
     */
    private String managername;


    /**
     * 管理人电话
     */
    private String managerphone;

    /**
     * 紧急联系电话
     */
    private String contactphone;

    /**
     * 机构设立日期
     */
    private String createdate;

    /**
     * 机构所在地
     */
    private String address;

    /**
     * 机构所在地名称
     */
    private String addressName;

    /**
     * 机构详细城市
     */
    private String detailaddresscity;

    /**
     * 机构详细地址明细
     */
    private String detailaddress;
    /**
     *文书送达地址城市
     */
    private String mailaddresscity;

    /**
     * 文书送达地址明细
     */
    private String mailaddress;

    /**
     * 创建时间
     */
    private Date  createtime;

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
    public int status;

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

    public String getServicename() {
        return servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }

    public String getServiceno() {
        return serviceno;
    }

    public void setServiceno(String serviceno) {
        this.serviceno = serviceno;
    }

    public String getResponsiblename() {
        return responsiblename;
    }

    public void setResponsiblename(String responsiblename) {
        this.responsiblename = responsiblename;
    }

    public String getResponsiblephone() {
        return responsiblephone;
    }

    public void setResponsiblephone(String responsiblephone) {
        this.responsiblephone = responsiblephone;
    }

    public String getManagername() {
        return managername;
    }

    public void setManagername(String managername) {
        this.managername = managername;
    }

    public String getManagerphone() {
        return managerphone;
    }

    public void setManagerphone(String managerphone) {
        this.managerphone = managerphone;
    }

    public String getContactphone() {
        return contactphone;
    }

    public void setContactphone(String contactphone) {
        this.contactphone = contactphone;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getDetailaddresscity() {
        return detailaddresscity;
    }

    public void setDetailaddresscity(String detailaddresscity) {
        this.detailaddresscity = detailaddresscity;
    }

    public String getDetailaddress() {
        return detailaddress;
    }

    public void setDetailaddress(String detailaddress) {
        this.detailaddress = detailaddress;
    }

    public String getMailaddresscity() {
        return mailaddresscity;
    }

    public void setMailaddresscity(String mailaddresscity) {
        this.mailaddresscity = mailaddresscity;
    }

    public String getMailaddress() {
        return mailaddress;
    }

    public void setMailaddress(String mailaddress) {
        this.mailaddress = mailaddress;
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
        return "OpPlatformServiceorganVo{" +
                "id='" + id + '\'' +
                ", platformid='" + platformid + '\'' +
                ", servicename='" + servicename + '\'' +
                ", serviceno='" + serviceno + '\'' +
                ", responsiblename='" + responsiblename + '\'' +
                ", responsiblephone='" + responsiblephone + '\'' +
                ", managername='" + managername + '\'' +
                ", managerphone='" + managerphone + '\'' +
                ", contactphone='" + contactphone + '\'' +
                ", createdate='" + createdate + '\'' +
                ", address='" + address + '\'' +
                ", addressName='" + addressName + '\'' +
                ", detailaddresscity='" + detailaddresscity + '\'' +
                ", detailaddress='" + detailaddress + '\'' +
                ", mailaddresscity='" + mailaddresscity + '\'' +
                ", mailaddress='" + mailaddress + '\'' +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                ", creater='" + creater + '\'' +
                ", updater='" + updater + '\'' +
                ", status=" + status +
                '}';
    }
}
