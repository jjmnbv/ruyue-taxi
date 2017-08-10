package com.szyciov.lease.vo.pubCoooperateUnion;

/**
 * Created by ZF on 2017/8/1.
 */
public class QueryPubCoooperateVo {
    private String id; // 主键
    private String leasecompanytext; // 战略伙伴
    private String coono; // 合作编号
    private Integer cootype; // 合作类型(0-B2B联盟，1-B2C联营)
    private Integer servicetype; // 加盟业务(0-网约车,1-出租车)
    private Integer openresource; // 开放资源个数
    private Integer coostate; // 合作状态(0-审核中,1-合作中,2-未达成,3-已终止,4-已过期)
    private String validatetime; // /** 有效期限 */
    private String applicationtime; // 申请时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLeasecompanytext() {
        return leasecompanytext;
    }

    public void setLeasecompanytext(String leasecompanytext) {
        this.leasecompanytext = leasecompanytext;
    }

    public String getCoono() {
        return coono;
    }

    public void setCoono(String coono) {
        this.coono = coono;
    }

    public Integer getCootype() {
        return cootype;
    }

    public void setCootype(Integer cootype) {
        this.cootype = cootype;
    }

    public Integer getServicetype() {
        return servicetype;
    }

    public void setServicetype(Integer servicetype) {
        this.servicetype = servicetype;
    }

    public Integer getOpenresource() {
        return openresource;
    }

    public void setOpenresource(Integer openresource) {
        this.openresource = openresource;
    }

    public Integer getCoostate() {
        return coostate;
    }

    public void setCoostate(Integer coostate) {
        this.coostate = coostate;
    }

    public String getValidatetime() {
        return validatetime;
    }

    public void setValidatetime(String validatetime) {
        this.validatetime = validatetime;
    }

    public String getApplicationtime() {
        return applicationtime;
    }

    public void setApplicationtime(String applicationtime) {
        this.applicationtime = applicationtime;
    }
}
