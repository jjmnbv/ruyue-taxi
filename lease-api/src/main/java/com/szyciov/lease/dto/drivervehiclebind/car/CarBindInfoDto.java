package com.szyciov.lease.dto.drivervehiclebind.car;

import com.szyciov.enums.BindingStateEnum;
import com.szyciov.enums.DriverEnum;

/**
 * 网约车绑定车辆信息DTO
 * Created by LC on 2017/3/17.
 */
public class CarBindInfoDto {


    //司机ID
    private String id;
    //车辆ID
    private String vehicleId;
    //工号
    private String jobnum;
    //司机姓名
    private String driverName;
    //电话
    private String phone;
    //城市
    private String cityStr;
    //绑定状态
    private String boundstate;
    //服务状态
    private String workstatus;
    //车牌号
    private String platenoStr;
    //品牌车系
    private String vehclineName;
    //服务车型
    private String vehiclemodelName;
    //服务车型ID
    private String modelId;
    //城市code
    private String city;
    //归属车企id
    private String belongleasecompany;
    //归属车企名称
    private String belongLeasecompanyName;

    public String getJobnum() {
        return jobnum;
    }

    public void setJobnum(String jobnum) {
        this.jobnum = jobnum;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCityStr() {
        return cityStr;
    }

    public void setCityStr(String cityStr) {
        this.cityStr = cityStr;
    }

    public String getBoundstate() {
        if(this.boundstate!=null){
            return BindingStateEnum.getBindingText(this.boundstate);
        }
        return boundstate;
    }

    public void setBoundstate(String boundstate) {
        this.boundstate = boundstate;
    }

    public String getWorkstatus() {
        if(this.workstatus!=null) {
             return DriverEnum.getStatusStr(this.workstatus);
        }
        return "/";
    }

    public void setWorkstatus(String workstatus) {
        this.workstatus = workstatus;
    }

    public String getPlatenoStr() {
        if(this.platenoStr!=null) {
            return platenoStr;
        }
        return "/";
    }

    public void setPlatenoStr(String platenoStr) {
        this.platenoStr = platenoStr;
    }

    public String getVehclineName() {
        if(this.vehclineName!=null) {
            return vehclineName;
        }
        return "/";
    }

    public void setVehclineName(String vehclineName) {
        this.vehclineName = vehclineName;
    }

    public String getVehiclemodelName() {
        if(this.vehiclemodelName!=null) {
            return vehiclemodelName;
        }
        return "/";
    }

    public void setVehiclemodelName(String vehiclemodelName) {
        this.vehiclemodelName = vehiclemodelName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

	public String getBelongleasecompany() {
		return belongleasecompany;
	}

	public void setBelongleasecompany(String belongleasecompany) {
		this.belongleasecompany = belongleasecompany;
	}

	public String getBelongLeasecompanyName() {
		return belongLeasecompanyName;
	}

	public void setBelongLeasecompanyName(String belongLeasecompanyName) {
		this.belongLeasecompanyName = belongLeasecompanyName;
	}
}
 