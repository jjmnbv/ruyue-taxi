package com.szyciov.lease.param.pubCoooperateUnion;

/**
 * Created by ZF on 2017/8/3.
 */
public class UpdateResourceParam {
    private String coooId; // 合作ID
    private String vehicleids; // 车辆ID ','分隔
    private String updater;

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public String getVehicleids() {
        return vehicleids;
    }

    public void setVehicleids(String vehicleids) {
        this.vehicleids = vehicleids;
    }

    public String getCoooId() {
        return coooId;
    }

    public void setCoooId(String coooId) {
        this.coooId = coooId;
    }
}
