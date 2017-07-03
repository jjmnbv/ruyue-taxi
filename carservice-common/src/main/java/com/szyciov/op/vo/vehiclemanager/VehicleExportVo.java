package com.szyciov.op.vo.vehiclemanager;

import com.szyciov.enums.BindingStateEnum;
import com.szyciov.enums.VehicleEnum;
import org.apache.commons.lang.StringUtils;

/**
 * 车辆管理导出Excel
 * Created by LC on 2017/3/28.
 */
public class VehicleExportVo {

    //车辆id
    private String id;
    //车辆类型
    private String vehicletype;
    //服务车型
    private String serviceCars;
    //绑定状态
    private String boundstate;
    //营运状态
    private String vehiclestatus;
    //车牌号
    private String platenoStr;
    //品牌车系
    private String brandCars;
    //车架号
    private String vin;
    //颜色
    private String color;
    //核载人数
    private String loads;
    //登记城市
    private String cityStr;
    //经营范围
    private String scopes;

    public String getServiceCars() {
        if(VehicleEnum.VEHICLE_TYPE_CAR.code.equals(this.vehicletype)) {
            return serviceCars;
        }else{
            return "/";
        }
    }

    public void setServiceCars(String serviceCars) {
        this.serviceCars = serviceCars;
    }

    public String getBoundstate() {
        if(StringUtils.isNotEmpty(this.boundstate)){
            return BindingStateEnum.getBindingText(this.boundstate);
        }
        return boundstate;
    }

    public void setBoundstate(String boundstate) {
        this.boundstate = boundstate;
    }

    public String getVehiclestatus() {
        if(StringUtils.isNotEmpty(this.vehiclestatus)){
            return VehicleEnum.getVehicleStatusMsg(this.vehiclestatus);
        }
        return "/";
    }

    public void setVehiclestatus(String vehiclestatus) {
        this.vehiclestatus = vehiclestatus;
    }

    public String getVehicletype() {
        if(StringUtils.isNotEmpty(this.vehicletype)){
            return VehicleEnum.getVehicleTypeMsg(this.vehicletype);
        }
        return "/";
    }

    public void setVehicletype(String vehicletype) {
        this.vehicletype = vehicletype;
    }

    public String getPlatenoStr() {
        return platenoStr;
    }

    public void setPlatenoStr(String platenoStr) {
        this.platenoStr = platenoStr;
    }

    public String getBrandCars() {
        return brandCars;
    }

    public void setBrandCars(String brandCars) {
        this.brandCars = brandCars;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLoads() {
        return loads;
    }

    public void setLoads(String loads) {
        this.loads = loads;
    }

    public String getCityStr() {
        return cityStr;
    }

    public void setCityStr(String cityStr) {
        this.cityStr = cityStr;
    }

    public String getScopes() {
        return scopes;
    }

    public void setScopes(String scopes) {
        this.scopes = scopes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
 