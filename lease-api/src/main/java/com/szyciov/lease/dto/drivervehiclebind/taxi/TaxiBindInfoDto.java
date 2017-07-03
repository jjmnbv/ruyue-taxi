package com.szyciov.lease.dto.drivervehiclebind.taxi;

import com.szyciov.enums.BindingStateEnum;
import com.szyciov.enums.DriverEnum;
import com.szyciov.enums.VehicleEnum;
import org.apache.commons.lang.StringUtils;

/**
 * 出租车绑定显示dto
 * Created by LC on 2017/3/21.
 */
public class TaxiBindInfoDto {

    //车辆ID
    private String id;
    //车牌号
    private String platenoStr;
    //品牌车系
    private String vehclineName;
    //颜色
    private String color;
    //城市code
    private String city;
    //城市
    private String cityStr;
    //绑定状态
    private String boundstate;
    //营运状态
    private String vehiclestatus;
    //在线司机
    private String driverid;
    //服务状态
    private String workstatus;
    //司机信息
    private String driverInfo;
    //当班状态
    private String passworkstatus;
    //绑定人数
    private Integer bindpersonnum;
    //绑定司机信息
    private String bindDriverInfos;
    //归属车企id
    private String belongleasecompany;
    //归属车企名称
    private String belongLeasecompanyName;

    public String getPlatenoStr() {
        return platenoStr;
    }

    public void setPlatenoStr(String platenoStr) {
        this.platenoStr = platenoStr;
    }

    public String getVehclineName() {
        return vehclineName;
    }

    public void setVehclineName(String vehclineName) {
        this.vehclineName = vehclineName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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
        return "/";
    }

    public void setBoundstate(String boundstate) {
        this.boundstate = boundstate;
    }

    public String getVehiclestatus() {
        if(this.vehiclestatus!=null){
            return VehicleEnum.getVehicleStatusMsg(this.vehiclestatus);
        }
        return "/";
    }

    public void setVehiclestatus(String vehiclestatus) {
        this.vehiclestatus = vehiclestatus;
    }

    public String getDriverid() {
        //如果车辆为维修中，则返回/
//        if(VehicleEnum.VEHICLE_STATUS_OFFLINE.msg.equals(this.getVehiclestatus())){
//            return "/";
//        }
        //未绑定 则返回/
//        if(BindingStateEnum.UN_BINDING.equals(this.getBoundstate())){
//            return "/";
//        }
        if(this.getBindpersonnum()==null){
            return "/";
        }
        //绑定一个司机 则返回/
        if(this.getBindpersonnum()<2||StringUtils.isEmpty(this.driverid)){
            return "/";
        }else
        //如果该司机状态不为当班,或当班状态为空，则显示""
        if(StringUtils.isEmpty(this.passworkstatus)||
                (!DriverEnum.PASS_WORK_STATUS_ON.code.equals(this.passworkstatus)&&
                !DriverEnum.PASS_WORK_STATUS_PENDING.code.equals(this.passworkstatus))){
            return "";
        }
        return driverid;
    }

    public void setDriverid(String driverid) {
        this.driverid = driverid;
    }

    public String getWorkstatus() {
        if(this.workstatus!=null){
            return DriverEnum.getStatusStr(this.workstatus);
        }
        return "/";
    }

    public void setWorkstatus(String workstatus) {
        this.workstatus = workstatus;
    }

    public String getDriverInfo() {

        //如果该司机状态不为空，且状态为当班或交班中，则显示司机信息
        if(StringUtils.isNotEmpty(this.passworkstatus)&&
        (DriverEnum.PASS_WORK_STATUS_ON.code.equals(this.passworkstatus)
        || DriverEnum.PASS_WORK_STATUS_PENDING.code.equals(this.passworkstatus))){
            return driverInfo;
        }
        return "/";
    }

    public void setDriverInfo(String driverInfo) {
        this.driverInfo = driverInfo;
    }

    public Integer getBindpersonnum() {
        return bindpersonnum;
    }

    public void setBindpersonnum(Integer bindpersonnum) {
        this.bindpersonnum = bindpersonnum;
    }

    public String getBindDriverInfos() {
        if(StringUtils.isEmpty(this.bindDriverInfos)){
            return "/";
        }
        return bindDriverInfos;
    }

    public void setBindDriverInfos(String bindDriverInfos) {
        this.bindDriverInfos = bindDriverInfos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPassworkstatus() {
        return passworkstatus;
    }

    public void setPassworkstatus(String passworkstatus) {
        this.passworkstatus = passworkstatus;
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
 