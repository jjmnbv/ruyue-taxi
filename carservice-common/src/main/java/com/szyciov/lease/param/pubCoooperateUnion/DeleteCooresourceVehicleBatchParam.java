package com.szyciov.lease.param.pubCoooperateUnion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZF on 2017/8/3.
 */
public class DeleteCooresourceVehicleBatchParam {
    private String coooId;
    private List<String> vehicleids = new ArrayList<>();

    public String getCoooId() {
        return coooId;
    }

    public void setCoooId(String coooId) {
        this.coooId = coooId;
    }

    public List<String> getVehicleids() {
        return vehicleids;
    }

    public void setVehicleids(List<String> vehicleids) {
        this.vehicleids = vehicleids;
    }

}
