package com.szyciov.lease.param.pubCoooperateUnion;

import com.szyciov.dto.PagingRequest;

/**
 * Created by ZF on 2017/8/1.
 */
public class QueryApplyResourceParam extends PagingRequest {
    private String leasescompanyid; // 租赁ID
    private String vehicletype; // 车辆类型
    private String vehclineid; // 车系ID
    private String vehiclemodels; // 车型ID
    private String workstatus; // 服务状态
    private String cityaddrid; // 登记城市
    private String jobnum; // 资格证号
    private String fullplateno; // 车牌号

    public String getCityaddrid() {
        return cityaddrid;
    }

    public void setCityaddrid(String cityaddrid) {
        this.cityaddrid = cityaddrid;
    }

    public String getLeasescompanyid() {
        return leasescompanyid;
    }

    public void setLeasescompanyid(String leasescompanyid) {
        this.leasescompanyid = leasescompanyid;
    }

    public String getVehicletype() {
        return vehicletype;
    }

    public void setVehicletype(String vehicletype) {
        this.vehicletype = vehicletype;
    }

    public String getVehclineid() {
        return vehclineid;
    }

    public void setVehclineid(String vehclineid) {
        this.vehclineid = vehclineid;
    }

    public String getVehiclemodels() {
        return vehiclemodels;
    }

    public void setVehiclemodels(String vehiclemodels) {
        this.vehiclemodels = vehiclemodels;
    }

    public String getWorkstatus() {
        return workstatus;
    }

    public void setWorkstatus(String workstatus) {
        this.workstatus = workstatus;
    }


    public String getJobnum() {
        return jobnum;
    }

    public void setJobnum(String jobnum) {
        this.jobnum = jobnum;
    }

    public String getFullplateno() {
        return fullplateno;
    }

    public void setFullplateno(String fullplateno) {
        this.fullplateno = fullplateno;
    }
}
