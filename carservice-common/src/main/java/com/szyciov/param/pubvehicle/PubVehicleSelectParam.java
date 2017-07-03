package com.szyciov.param.pubvehicle;

/**
 * 车辆控件查询条件
 * Created by LC on 2017/3/7.
 */
public class PubVehicleSelectParam {


    /**
     *  查询文本信息
     */
    private String queryText;

    /**
     * 租赁公司ID
     */
    private String leasescompanyid;

    /**
     * 系统类型 0-运管端，1-租赁端
     */
    private String platformtype;

    /**
     * 车辆类型  0-网约车，1-出租车
     */
    private String vehicletype;

    /**
     * 锁定状态 0-未锁定，1-已锁定
     */
    private String lockstatus;

    /**
     * 绑定状态 0-未绑定,1-已绑定
     */
    private String boundstate;

    public String getLeasescompanyid() {
        return leasescompanyid;
    }

    public void setLeasescompanyid(String leasescompanyid) {
        this.leasescompanyid = leasescompanyid;
    }

    public String getPlatformtype() {
        return platformtype;
    }

    public void setPlatformtype(String platformtype) {
        this.platformtype = platformtype;
    }

    public String getVehicletype() {
        return vehicletype;
    }

    public void setVehicletype(String vehicletype) {
        this.vehicletype = vehicletype;
    }

    public String getLockstatus() {
        return lockstatus;
    }

    public void setLockstatus(String lockstatus) {
        this.lockstatus = lockstatus;
    }

    public String getBoundstate() {
        return boundstate;
    }

    public void setBoundstate(String boundstate) {
        this.boundstate = boundstate;
    }

    public String getQueryText() {
        return queryText;
    }

    public void setQueryText(String queryText) {
        this.queryText = queryText;
    }
}
 