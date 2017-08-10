package com.szyciov.op.param.pubCoooperatePartner;

/**
 * Created by ZF on 2017/8/7.
 */
public class QueryPubCoooperatePartnerLeaseCompanyParam {
    private String keyword; // 关键字搜索
    private String companyid; // 运管ID

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }
}
