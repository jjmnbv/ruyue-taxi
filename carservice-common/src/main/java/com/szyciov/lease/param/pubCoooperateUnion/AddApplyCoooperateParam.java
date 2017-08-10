package com.szyciov.lease.param.pubCoooperateUnion;

/**
 * Created by ZF on 2017/8/4.
 */
public class AddApplyCoooperateParam {
    private String leasescompanyid; // 租赁ID
    private int vehicletype; // 车辆类型
    private String companyid; // 申请人公司ID
    private int cootype; // 合作类型
    private String vehicleid; // 车辆ID
    private String updater; // 更新人

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public String getVehicleid() {
        return vehicleid;
    }

    public void setVehicleid(String vehicleid) {
        this.vehicleid = vehicleid;
    }

    public String getLeasescompanyid() {
        return leasescompanyid;
    }

    public void setLeasescompanyid(String leasescompanyid) {
        this.leasescompanyid = leasescompanyid;
    }

    public int getVehicletype() {
        return vehicletype;
    }

    public void setVehicletype(int vehicletype) {
        this.vehicletype = vehicletype;
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public int getCootype() {
        return cootype;
    }

    public void setCootype(int cootype) {
        this.cootype = cootype;
    }
}
