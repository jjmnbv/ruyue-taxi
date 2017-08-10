package com.szyciov.lease.param.pubCoooperateUnion;

/**
 * Created by ZF on 2017/8/3.
 */
public class QueryApplyLeaseCompanyParam {
    private String name; // 公司全名
    private String type; // 业务类型
    private String companyid; // 申请人公司ID
    private String servicetype; // 服务类型

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getServicetype() {
        return servicetype;
    }

    public void setServicetype(String servicetype) {
        this.servicetype = servicetype;
    }
}
