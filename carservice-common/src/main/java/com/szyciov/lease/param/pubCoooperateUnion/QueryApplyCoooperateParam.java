package com.szyciov.lease.param.pubCoooperateUnion;

/**
 * Created by ZF on 2017/8/3.
 */
public class QueryApplyCoooperateParam {
    private String companyid; // 申请人公司ID
    private String leasecompanyid; // 申请公司ID
    private String servicetype; // 服务类型

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getLeasecompanyid() {
        return leasecompanyid;
    }

    public void setLeasecompanyid(String leasecompanyid) {
        this.leasecompanyid = leasecompanyid;
    }

    public String getServicetype() {
        return servicetype;
    }

    public void setServicetype(String servicetype) {
        this.servicetype = servicetype;
    }
}
