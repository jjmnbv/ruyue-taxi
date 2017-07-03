package com.szyciov.dto.driverShiftManagent;

/**
 * 待接班保存dto
 * Created by LC on 2017/3/16.
 */
public class PendingSaveDto {


    private String id ;

    private String vehicleid;//	车辆ID

    private String driverid;//与司机表主键关联

    private String relieveddriverid;//接班司机ID

    private String driverinfo;//当班司机信息

    private Long onlinetime;//分钟为单位

    private String relievedtype;//0-自主交班,1-人工指派

    private String creater;//创建人

    private String platformtype;//0-运管端，1-租赁端

    private String leasescompanyid;//所属租赁ID

    private String platenoStr;  //车牌号


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

    public String getDriverinfo() {
        return driverinfo;
    }

    public void setDriverinfo(String driverinfo) {
        this.driverinfo = driverinfo;
    }

    public Long getOnlinetime() {
        return onlinetime;
    }

    public void setOnlinetime(Long onlinetime) {
        this.onlinetime = onlinetime;
    }

    public String getRelievedtype() {
        return relievedtype;
    }

    public void setRelievedtype(String relievedtype) {
        this.relievedtype = relievedtype;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getPlatformtype() {
        return platformtype;
    }

    public void setPlatformtype(String platformtype) {
        this.platformtype = platformtype;
    }

    public String getLeasescompanyid() {
        return leasescompanyid;
    }

    public void setLeasescompanyid(String leasescompanyid) {
        this.leasescompanyid = leasescompanyid;
    }

    public String getPlatenoStr() {
        return platenoStr;
    }

    public void setPlatenoStr(String platenoStr) {
        this.platenoStr = platenoStr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRelieveddriverid() {
        return relieveddriverid;
    }

    public void setRelieveddriverid(String relieveddriverid) {
        this.relieveddriverid = relieveddriverid;
    }
}
 