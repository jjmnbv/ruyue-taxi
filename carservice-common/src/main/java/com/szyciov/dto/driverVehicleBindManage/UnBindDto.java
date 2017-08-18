package com.szyciov.dto.driverVehicleBindManage;

import java.util.List;

/**
 * 人车解绑数据传输对象
 * Created by LC on 2017/3/8.
 */
public class UnBindDto {

    //司机ID
    private String driverId;

    //车辆ID
    private String vehicleId;

    //解绑原因
    private String unBindStr;

    //绑定类型
    private String drivertype;

    //操作人
    private String creater;

    //租赁公司ID
    private String leaseCompanyId;

    //出租车司机Id
    private List<String> taxiDrivers;


    public String getUnBindStr() {
        return unBindStr;
    }

    public void setUnBindStr(String unBindStr) {
        this.unBindStr = unBindStr;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getDrivertype() {
        return drivertype;
    }

    public void setDrivertype(String drivertype) {
        this.drivertype = drivertype;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public List<String> getTaxiDrivers() {
        return taxiDrivers;
    }

    public void setTaxiDrivers(List<String> taxiDrivers) {
        this.taxiDrivers = taxiDrivers;
    }

    public String getLeaseCompanyId() {
        return leaseCompanyId;
    }

    public void setLeaseCompanyId(String leaseCompanyId) {
        this.leaseCompanyId = leaseCompanyId;
    }
}
 