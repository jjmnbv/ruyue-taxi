package com.szyciov.op.param;

import com.szyciov.dto.PagingRequest;

/**
 * Created by ZF on 2017/7/27.
 */
public class QueryPubCooresourceManageParam extends PagingRequest {
    private String coooId; // 联盟ID
    private String fullplateno; // 车牌号
    private String driverinfo; // 司机
    private String vehicleModelId; // 服务车型
    private String jobnum; // 资格证号

    public String getJobnum() {
        return jobnum;
    }

    public void setJobnum(String jobnum) {
        this.jobnum = jobnum;
    }

    public String getCoooId() {
        return coooId;
    }

    public void setCoooId(String coooId) {
        this.coooId = coooId;
    }

    public String getFullplateno() {
        return fullplateno;
    }

    public void setFullplateno(String fullplateno) {
        this.fullplateno = fullplateno;
    }

    public String getDriverinfo() {
        return driverinfo;
    }

    public void setDriverinfo(String driverinfo) {
        this.driverinfo = driverinfo;
    }

    public String getVehicleModelId() {
        return vehicleModelId;
    }

    public void setVehicleModelId(String vehicleModelId) {
        this.vehicleModelId = vehicleModelId;
    }
}
