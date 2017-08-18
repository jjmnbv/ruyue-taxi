package com.szyciov.lease.vo.pubComplaintManage;

/**
 * Created by ZF on 2017/8/14.
 */
public class QueryPubComplaintsVo {
    private String id; // 投诉ID
    private String orderno; // 订单编号
    private String jobnum; // 资格证号
    private String name; // 司机
    private String phone; // 司机手机号
    private String fullplateno; // 车牌号
    private String nickname; // 下单人
    private String account; // 下单人手机号
    private String complainttype; // 投诉类型
    private String complaintcontent; // 投诉内容
    private String createtime; // 投诉时间
    private String creater; // 操作人
    private String processtime; // 处理时间
    private String processresult; // 核实结果
    private String processrecord; // 处理意见
    private String leasename; // 所属机构

    public String getLeasename() {
        return leasename;
    }

    public void setLeasename(String leasename) {
        this.leasename = leasename;
    }

    public String getProcessrecord() {
        return processrecord;
    }

    public void setProcessrecord(String processrecord) {
        this.processrecord = processrecord;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getJobnum() {
        return jobnum;
    }

    public void setJobnum(String jobnum) {
        this.jobnum = jobnum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullplateno() {
        return fullplateno;
    }

    public void setFullplateno(String fullplateno) {
        this.fullplateno = fullplateno;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public String getComplainttype() {
        return complainttype;
    }

    public void setComplainttype(String complainttype) {
        this.complainttype = complainttype;
    }

    public String getComplaintcontent() {
        return complaintcontent;
    }

    public void setComplaintcontent(String complaintcontent) {
        this.complaintcontent = complaintcontent;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getProcesstime() {
        return processtime;
    }

    public void setProcesstime(String processtime) {
        this.processtime = processtime;
    }

    public String getProcessresult() {
        return processresult;
    }

    public void setProcessresult(String processresult) {
        this.processresult = processresult;
    }
}
