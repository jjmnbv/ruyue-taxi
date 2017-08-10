package com.szyciov.op.param;

import com.szyciov.dto.PagingRequest;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by ZF on 2017/7/24.
 */
public class QueryPubCooresourceParam extends PagingRequest {
    private String coono; // 合作编号
    private String leasecompanyid; // 战略伙伴
    private Integer servicetype; // 加盟业务(0-网约车,1-出租车)
    private String companyid; // 所属租赁,数据归属

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

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }
}
