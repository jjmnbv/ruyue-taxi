package com.szyciov.op.param.pubComplaintManage;

import com.szyciov.dto.PagingRequest;

import java.util.Date;

/**
 * Created by ZF on 2017/8/14.
 */
public class QueryPubComplaintsParam extends PagingRequest {
    private String leasecompanyid; // 运管ID
    private String orderno; // 订单号
    private String userid; // 下单人ID
    private String jobnum; // 资格证号
    private String driverid; // 司机信息
    private String fullplateno; // 车牌号
    private String processresult; // 核实结果
    private String type; // 投诉类型
    private Date processtimestart; // 处理时间开始
    private Date processtimeend; // 处理时间结束
    private Date complainttimestart; // 投诉开始时间
    private Date complainttimeend; // 投诉开始时间
    private int processstatus; // 处理状态

    public int getProcessstatus() {
        return processstatus;
    }

    public void setProcessstatus(int processstatus) {
        this.processstatus = processstatus;
    }

    public String getLeasecompanyid() {
        return leasecompanyid;
    }

    public void setLeasecompanyid(String leasecompanyid) {
        this.leasecompanyid = leasecompanyid;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getJobnum() {
        return jobnum;
    }

    public void setJobnum(String jobnum) {
        this.jobnum = jobnum;
    }

    public String getDriverid() {
        return driverid;
    }

    public void setDriverid(String driverid) {
        this.driverid = driverid;
    }

    public String getFullplateno() {
        return fullplateno;
    }

    public void setFullplateno(String fullplateno) {
        this.fullplateno = fullplateno;
    }

    public String getProcessresult() {
        return processresult;
    }

    public void setProcessresult(String processresult) {
        this.processresult = processresult;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getProcesstimestart() {
        return processtimestart;
    }

    public void setProcesstimestart(Date processtimestart) {
        this.processtimestart = processtimestart;
    }

    public Date getProcesstimeend() {
        return processtimeend;
    }

    public void setProcesstimeend(Date processtimeend) {
        this.processtimeend = processtimeend;
    }

    public Date getComplainttimestart() {
        return complainttimestart;
    }

    public void setComplainttimestart(Date complainttimestart) {
        this.complainttimestart = complainttimestart;
    }

    public Date getComplainttimeend() {
        return complainttimeend;
    }

    public void setComplainttimeend(Date complainttimeend) {
        this.complainttimeend = complainttimeend;
    }
}
