package com.szyciov.op.param.vehicleManager;

import com.szyciov.param.QueryParam;

/**
 * 车辆管理首页查询对象
 * Created by LC on 2017/3/27.
 */
public class VehicleIndexQueryParam extends QueryParam{
    //车辆ID
    private String id;
    //车辆类型
    private String vehicletype;
    //城市code
    private String city;
    //车牌编号
    private String platenoStr;
    //服务状态
    private String workstatus;
    //绑定状态
    private String boundstate;
    //运营状态
    private String vehiclestatus;
    //系统类型
    private String platformtype;
    //品牌车系
    private String brandCars;
    //服务车型
    private String serviceCars;

    public String getVehicletype() {
        return vehicletype;
    }

    public void setVehicletype(String vehicletype) {
        this.vehicletype = vehicletype;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPlatenoStr() {
        return platenoStr;
    }

    public void setPlatenoStr(String platenoStr) {
        this.platenoStr = platenoStr;
    }

    public String getWorkstatus() {
        return workstatus;
    }

    public void setWorkstatus(String workstatus) {
        this.workstatus = workstatus;
    }

    public String getBoundstate() {
        return boundstate;
    }

    public void setBoundstate(String boundstate) {
        this.boundstate = boundstate;
    }

    public String getVehiclestatus() {
        return vehiclestatus;
    }

    public void setVehiclestatus(String vehiclestatus) {
        this.vehiclestatus = vehiclestatus;
    }

    public String getPlatformtype() {
        return platformtype;
    }

    public void setPlatformtype(String platformtype) {
        this.platformtype = platformtype;
    }

    public String getBrandCars() {
        return brandCars;
    }

    public void setBrandCars(String brandCars) {
        this.brandCars = brandCars;
    }

    public String getServiceCars() {
        return serviceCars;
    }

    public void setServiceCars(String serviceCars) {
        this.serviceCars = serviceCars;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
 