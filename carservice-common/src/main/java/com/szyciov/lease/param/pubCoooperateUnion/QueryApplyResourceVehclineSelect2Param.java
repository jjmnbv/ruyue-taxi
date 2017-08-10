package com.szyciov.lease.param.pubCoooperateUnion;

/**
 * Created by ZF on 2017/8/2.
 */
public class QueryApplyResourceVehclineSelect2Param {
    private String coooId;
    private String keyword;
    private String leasescompanyid; // 租赁ID
    private String vehicletype; // 车辆类型

    public String getCoooId() {
        return coooId;
    }

    public void setCoooId(String coooId) {
        this.coooId = coooId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getLeasescompanyid() {
        return leasescompanyid;
    }

    public void setLeasescompanyid(String leasescompanyid) {
        this.leasescompanyid = leasescompanyid;
    }

    public String getVehicletype() {
        return vehicletype;
    }

    public void setVehicletype(String vehicletype) {
        this.vehicletype = vehicletype;
    }
}
