package com.szyciov.param;

/**
 * 已处理交接班 查询条件
 * Created by LC on 2017/2/28.
 */
public class PeDrivershiftProcessedParam extends QueryParam{


    private String id;//

    private String leasescompanyid;//租赁公司ID

    private String vehicleid;//车辆信息id

    private String plateNo;//车牌号码

    private String queryType;

    private String ondutydriverid;//当班司机id

    private String relieveddriverid;//接班司机id

    private String processtimeStart;//处理起始时间

    private String processtimeEnd;//处理截止时间

    private String platformtype;//0-运管端，1-租赁端

    private String relievedtype;// 0-自主交班,1-人工指派

    private String shifttype;// 交接状态 1-交班成功，2-车辆回收，3-交班超时，4-指派当班


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

    public String getOndutydriverid() {
        return ondutydriverid;
    }

    public void setOndutydriverid(String ondutydriverid) {
        this.ondutydriverid = ondutydriverid;
    }

    public String getRelievedtype() {
        return relievedtype;
    }

    public void setRelievedtype(String relievedtype) {
        this.relievedtype = relievedtype;
    }

    public String getRelieveddriverid() {
        return relieveddriverid;
    }

    public void setRelieveddriverid(String relieveddriverid) {
        this.relieveddriverid = relieveddriverid;
    }

    public String getProcesstimeStart() {
        return processtimeStart;
    }

    public void setProcesstimeStart(String processtimeStart) {
        this.processtimeStart = processtimeStart;
    }

    public String getProcesstimeEnd() {
        return processtimeEnd;
    }

    public void setProcesstimeEnd(String processtimeEnd) {
        this.processtimeEnd = processtimeEnd;
    }

    public String getShifttype() {
        return shifttype;
    }

    public void setShifttype(String shifttype) {
        this.shifttype = shifttype;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }
}
 