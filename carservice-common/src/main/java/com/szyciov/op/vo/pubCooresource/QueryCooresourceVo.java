package com.szyciov.op.vo.pubCooresource;

/**
 * Created by ZF on 2017/7/25.
 */
public class QueryCooresourceVo {
    private String id; // 主键
    private String coono; // 合作编号
    private String leasecompanytext; // 战略伙伴
    private String leasecompanyid; // 战略伙伴ID
    private Integer servicetype; // 加盟业务(0-网约车,1-出租车)
    private Integer openresource; // 开放资源个数
    private Integer workresource; //投运资源数

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCoono() {
        return coono;
    }

    public void setCoono(String coono) {
        this.coono = coono;
    }

    public String getLeasecompanytext() {
        return leasecompanytext;
    }

    public void setLeasecompanytext(String leasecompanytext) {
        this.leasecompanytext = leasecompanytext;
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

    public Integer getWorkresource() {
        return workresource;
    }

    public void setWorkresource(Integer workresource) {
        this.workresource = workresource;
    }

    public String getLeasecompanyid() {
        return leasecompanyid;
    }

    public void setLeasecompanyid(String leasecompanyid) {
        this.leasecompanyid = leasecompanyid;
    }
}
