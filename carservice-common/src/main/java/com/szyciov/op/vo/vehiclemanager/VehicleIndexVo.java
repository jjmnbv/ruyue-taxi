package com.szyciov.op.vo.vehiclemanager;

import com.szyciov.enums.BindingStateEnum;
import com.szyciov.enums.DriverEnum;
import com.szyciov.enums.VehicleEnum;
import org.apache.commons.lang.StringUtils;

/**
 * 车辆管理首页VO类
 * Created by LC on 2017/3/27.
 */
public class VehicleIndexVo {

    //车辆ID
    private String id;
    //车辆类型
    private String vehicletype;
    //服务状态
    private String workstatus;
    //绑定状态
    private String boundstate;
    //营运状态
    private String vehiclestatus;
    //车牌号码
    private String platenoStr;
    //品牌车系
    private String brandCars;
    //车架号
    private String vin;
    //颜色
    private String color;
    //核载人数
    private Integer loads;
    //登记城市
    private String cityStr;
    //经营范围
    private String scopeStr;
    //服务车型
    private String serviceCars;
    //车型级别
    private String level;

    public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVehicletype() {
        if(StringUtils.isNotEmpty(this.vehicletype)){
            return VehicleEnum.getVehicleTypeMsg(this.vehicletype);
        }
        return vehicletype;
    }

    public void setVehicletype(String vehicletype) {
        this.vehicletype = vehicletype;
    }

    public String getWorkstatus() {
        if(StringUtils.isNotEmpty(this.workstatus)){
            return DriverEnum.getStatusStr(this.workstatus);
        }
        return workstatus;
    }

    public void setWorkstatus(String workstatus) {
        this.workstatus = workstatus;
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
        return vehiclestatus;
    }

    public void setVehiclestatus(String vehiclestatus) {
        this.vehiclestatus = vehiclestatus;
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

    public Integer getLoads() {
        return loads;
    }

    public void setLoads(Integer loads) {
        this.loads = loads;
    }

    public String getCityStr() {
        return cityStr;
    }

    public void setCityStr(String cityStr) {
        this.cityStr = cityStr;
    }

    public String getScopeStr()
    {
        if(StringUtils.isEmpty(scopeStr)){
            return "/";
        }
        return scopeStr;
    }

    public void setScopeStr(String scopeStr) {
        this.scopeStr = scopeStr;
    }

    public String getServiceCars() {
        if(StringUtils.isEmpty(this.serviceCars)||
                VehicleEnum.VEHICLE_TYPE_TAXI.code.equals(this.vehicletype)){
            return "/";
        }
        return serviceCars;
    }

    public void setServiceCars(String serviceCars) {
        this.serviceCars = serviceCars;
    }
}
 