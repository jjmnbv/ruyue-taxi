package com.szyciov.op.param;

/**
 * Created by ZF on 2017/7/28.
 */
public class UpdatePubVehicleModelsParam {

    private String coorId; //  战略伙伴资源ID

    private String vehiclemodelsid; // 车型ID

    private String updater; // 更新人

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public String getVehiclemodelsid() {
        return vehiclemodelsid;
    }

    public void setVehiclemodelsid(String vehiclemodelsid) {
        this.vehiclemodelsid = vehiclemodelsid;
    }

    public String getCoorId() {
        return coorId;
    }

    public void setCoorId(String coorId) {
        this.coorId = coorId;
    }
}
