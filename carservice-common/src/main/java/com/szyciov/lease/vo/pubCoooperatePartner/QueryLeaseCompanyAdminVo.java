package com.szyciov.lease.vo.pubCoooperatePartner;

/**
 * Created by ZF on 2017/8/7.
 */
public class QueryLeaseCompanyAdminVo {
    private String telphone;
    private String leid;
    private String servicetype;
    private String reviewtext;
    private int cootype;

    public int getCootype() {
        return cootype;
    }

    public void setCootype(int cootype) {
        this.cootype = cootype;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getLeid() {
        return leid;
    }

    public void setLeid(String leid) {
        this.leid = leid;
    }

    public String getServicetype() {
        return servicetype;
    }

    public void setServicetype(String servicetype) {
        this.servicetype = servicetype;
    }

    public String getReviewtext() {
        return reviewtext;
    }

    public void setReviewtext(String reviewtext) {
        this.reviewtext = reviewtext;
    }
}
