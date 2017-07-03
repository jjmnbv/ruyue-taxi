package com.szyciov.param;

/**
 * 待处理交接班 查询条件
 * Created by LC on 2017/2/28.
 */
public class PeDrivershiftPendingParam extends QueryParam{


    private String id;//

    private String leasescompanyid;//租赁公司ID

    private String plateNo;//车牌号码

    private String vehicleid;//车辆信息id

    private String driverid;//当班司机id

    private String applytimeStart;//起始申请时间

    private String applytimeEnd;//截止申请时间

    private String platformtype;//0-运管端，1-租赁端

    private String relievedtype;// 0-自主交班,1-人工指派


    public String getPlatformtype() {
        return platformtype;
    }

    public void setPlatformtype(String platformtype) {
        this.platformtype = platformtype;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLeasescompanyid() {
        return leasescompanyid;
    }

    public void setLeasescompanyid(String leasescompanyid) {
        this.leasescompanyid = leasescompanyid;
    }

    public String getVehicleid() {
        return vehicleid;
    }

    public void setVehicleid(String vehicleid) {
        this.vehicleid = vehicleid;
    }

    public String getDriverid() {
        return driverid;
    }

    public void setDriverid(String driverid) {
        this.driverid = driverid;
    }

    public String getApplytimeStart() {
        return applytimeStart;
    }

    public void setApplytimeStart(String applytimeStart) {
        this.applytimeStart = applytimeStart;
    }

    public String getApplytimeEnd() {
        return applytimeEnd;
    }

    public void setApplytimeEnd(String applytimeEnd) {
        this.applytimeEnd = applytimeEnd;
    }

    public String getRelievedtype() {
        return relievedtype;
    }

    public void setRelievedtype(String relievedtype) {
        this.relievedtype = relievedtype;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }
}
 