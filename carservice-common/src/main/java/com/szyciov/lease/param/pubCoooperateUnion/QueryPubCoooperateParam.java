package com.szyciov.lease.param.pubCoooperateUnion;

import com.szyciov.dto.PagingRequest;

import java.util.Date;

/**
 * Created by ZF on 2017/8/1.
 */
public class QueryPubCoooperateParam extends PagingRequest {
    private String coono; // 合作编号
    private String leasecompanyid; // 战略伙伴
    private Integer servicetype; // 加盟业务(0-网约车,1-出租车)
    private Date applicationtimeStart; // 申请时间开始时间
    private Date applicationtimeEnd; // 申请结束时间
    private String companyid; // 所属租赁,数据归属
    private String coostate; // 合作状态
    private String cootype; // 合作类型

    public String getCoostate() {
        return coostate;
    }

    public void setCoostate(String coostate) {
        this.coostate = coostate;
    }

    public String getCoono() {
        return coono;
    }

    public void setCoono(String coono) {
        this.coono = coono;
    }

    public String getLeasecompanyid() {
        return leasecompanyid;
    }

    public void setLeasecompanyid(String leasecompanyid) {
        this.leasecompanyid = leasecompanyid;
    }

    public Integer getServicetype() {
        return servicetype;
    }

    public void setServicetype(Integer servicetype) {
        this.servicetype = servicetype;
    }

    public Date getApplicationtimeStart() {
        return applicationtimeStart;
    }


    public Date getApplicationtimeEnd() {
        return applicationtimeEnd;
    }

    public void setApplicationtimeStart(Date applicationtimeStart) {
        this.applicationtimeStart = applicationtimeStart;
    }

    public void setApplicationtimeEnd(Date applicationtimeEnd) {
        this.applicationtimeEnd = applicationtimeEnd;
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getCootype() {
        return cootype;
    }

    public void setCootype(String cootype) {
        this.cootype = cootype;
    }
}
