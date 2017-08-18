package com.szyciov.dto.driverVehicleBindManage;

import java.util.List;

/**
 * 人车绑定传输对象
 * Created by LC on 2017/3/8.
 */
public class BindDto {

    //司机ID
    private String driverID;

    //车辆ID
    private String vehicleid;

    //绑定类型 网约车/出租车
    private String drivertype;

    //绑定状态 绑定/解绑
    private String bindStatus;

    //解绑原因
    private String unbindreason;

    //租赁公司ID
    private String leaseCompanyId;


    private String creater;


    private String updater;

    //解绑司机信息
    private String unBindDriverInfo;

    //解绑司机人数
    private Integer unBindNum;

//    /**
//     * 司机ID集合，用于出租车绑定使用
//     */
//    private List<String> driverIds;


    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    public String getVehicleid() {
        return vehicleid;
    }

    public void setVehicleid(String vehicleid) {
        this.vehicleid = vehicleid;
    }

    public String getDrivertype() {
        return drivertype;
    }

    public void setDrivertype(String drivertype) {
        this.drivertype = drivertype;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public String getBindStatus() {
        return bindStatus;
    }

    public void setBindStatus(String bindStatus) {
        this.bindStatus = bindStatus;
    }

    public String getUnbindreason() {
        return unbindreason;
    }

    public void setUnbindreason(String unbindreason) {
        this.unbindreason = unbindreason;
    }

    public String getUnBindDriverInfo() {
        return unBindDriverInfo;
    }

    public void setUnBindDriverInfo(String unBindDriverInfo) {
        this.unBindDriverInfo = unBindDriverInfo;
    }

    public Integer getUnBindNum() {
        return unBindNum;
    }

    public void setUnBindNum(Integer unBindNum) {
        this.unBindNum = unBindNum;
    }

    public String getLeaseCompanyId() {
        return leaseCompanyId;
    }

    public void setLeaseCompanyId(String leaseCompanyId) {
        this.leaseCompanyId = leaseCompanyId;
    }
}
 