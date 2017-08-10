package com.szyciov.lease.param.pubCoooperateUnion;

import com.szyciov.dto.PagingRequest;

/**
 * Created by ZF on 2017/8/1.
 */
public class QueryResourceParam extends PagingRequest {
    private String resource; // 是否被勾选
    private String coooid; // 合作ID
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

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getCoooid() {
        return coooid;
    }

    public void setCoooid(String coooid) {
        this.coooid = coooid;
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
