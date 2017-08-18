package com.szyciov.dto.pubVehicleModelsRef;

import java.util.List;

/**
 * Created by ZF on 2017/8/11.
 */
public class UpdateVehicleModelsRefByVehclineDto {
    private String leaseCompanyId; // 租赁/运管 公司ID
    private int platform; // 平台    1：租赁     0：运管
    private List<String> vehclineId; // 车系ID 集合
    private String vehiclemodels; // 车型Id
    private String updater; // 更改人


    public String getLeaseCompanyId() {
        return leaseCompanyId;
    }

    public void setLeaseCompanyId(String leaseCompanyId) {
        this.leaseCompanyId = leaseCompanyId;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public List<String> getVehclineId() {
        return vehclineId;
    }

    public void setVehclineId(List<String> vehclineId) {
        this.vehclineId = vehclineId;
    }

    public String getVehiclemodels() {
        return vehiclemodels;
    }

    public void setVehiclemodels(String vehiclemodels) {
        this.vehiclemodels = vehiclemodels;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }
}
