package com.szyciov.op.vo.pubCooresource;

/**
 * Created by ZF on 2017/7/27.
 */
public class QueryPubCooresourceManageVo {
    private String id; // 资源ID
    private String jobnum; // 资格证号
    private String driverinfo; // 司机信息
    private String vehicleinfo; // 车辆信息
    private String vehicleModel; // 服务车型
    private String updatetime; // 修改时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobnum() {
        return jobnum;
    }

    public void setJobnum(String jobnum) {
        this.jobnum = jobnum;
    }

    public String getDriverinfo() {
        return driverinfo;
    }

    public void setDriverinfo(String driverinfo) {
        this.driverinfo = driverinfo;
    }

    public String getVehicleinfo() {
        return vehicleinfo;
    }

    public void setVehicleinfo(String vehicleinfo) {
        this.vehicleinfo = vehicleinfo;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }
}
