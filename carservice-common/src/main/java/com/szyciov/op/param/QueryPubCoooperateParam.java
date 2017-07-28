package com.szyciov.op.param;

import com.szyciov.dto.PagingRequest;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by ZF on 2017/7/24.
 */
public class QueryPubCoooperateParam extends PagingRequest {
    private String coono; // 合作编号
    private String leasecompanyid; // 战略伙伴
    private Integer servicetype; // 加盟业务(0-网约车,1-出租车)
    private Date applicationtimeStart; // 申请时间开始时间
    private Date applicationtimeEnd; // 申请结束时间
    private String companyid; // 所属租赁,数据归属
    private String coostate; // 合作状态

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

    public void setApplicationtimeStart(Date applicationtimeStart) {
        if (applicationtimeStart != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(applicationtimeStart);
            cal.set(Calendar.HOUR, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.MILLISECOND, 0);
            this.applicationtimeStart = cal.getTime();
        } else {
            this.applicationtimeStart = applicationtimeStart;
        }
    }

    public Date getApplicationtimeEnd() {
        return applicationtimeEnd;
    }

    public void setApplicationtimeEnd(Date applicationtimeEnd) {
        if (applicationtimeEnd != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(applicationtimeEnd);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.MILLISECOND, 0);
            cal.add(Calendar.DATE, 1);
            this.applicationtimeEnd = cal.getTime();
        } else {
            this.applicationtimeEnd = applicationtimeEnd;
        }

        this.applicationtimeEnd = applicationtimeEnd;
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }
}
