package com.szyciov.op.param;

import com.szyciov.dto.PagingRequest;

/**
 * Created by ZF on 2017/7/27.
 */
public class QueryPubCooresourceInfoParam extends PagingRequest {
    public String coooId; // 战略伙伴ID
    public String driverinfo; // 司机
    public String jobnum; // 资格证号
    public String fullplateno; // 车牌号

    public String getCoooId() {
        return coooId;
    }

    public void setCoooId(String coooId) {
        this.coooId = coooId;
    }

    public String getDriverinfo() {
        return driverinfo;
    }

    public void setDriverinfo(String driverinfo) {
        this.driverinfo = driverinfo;
    }

    public String getJobnum() {
        return jobnum;
    }

    public void setJobnum(String jobnum) {
        this.jobnum = jobnum;
    }

    public String getFullplateno() {
        return fullplateno;
    }

    public void setFullplateno(String fullplateno) {
        this.fullplateno = fullplateno;
    }
}
