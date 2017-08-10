package com.szyciov.lease.vo.pubCoooperateUnion;

/**
 * Created by ZF on 2017/8/1.
 */
public class QueryApplyResourceVo {
    private String fullplateno; // 车牌号
    private String vehclinename; // 品牌车系
    private String jobnum; // 资格证号
    private String vehiclemodelsname; // 服务车型
    private String driverinfo; //   司机信息
    private String cityname; // 登记城市
    private String workstatus; // 服务状态
    private String vehclineid;
    private String vehicleid; // 车辆ID

    public String getVehicleid() {
        return vehicleid;
    }

    public void setVehicleid(String vehicleid) {
        this.vehicleid = vehicleid;
    }

    public String getVehclineid() {
        return vehclineid;
    }

    public void setVehclineid(String vehclineid) {
        this.vehclineid = vehclineid;
    }

    public String getFullplateno() {
        return fullplateno;
    }

    public void setFullplateno(String fullplateno) {
        this.fullplateno = fullplateno;
    }

    public String getVehclinename() {
        return vehclinename;
    }

    public void setVehclinename(String vehclinename) {
        this.vehclinename = vehclinename;
    }

    public String getJobnum() {
        return jobnum;
    }

    public void setJobnum(String jobnum) {
        this.jobnum = jobnum;
    }

    public String getVehiclemodelsname() {
        return vehiclemodelsname;
    }

    public void setVehiclemodelsname(String vehiclemodelsname) {
        this.vehiclemodelsname = vehiclemodelsname;
    }


    public String getDriverinfo() {
        return driverinfo;
    }

    public void setDriverinfo(String driverinfo) {
        this.driverinfo = driverinfo;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getWorkstatus() {
        return workstatus;
    }

    public void setWorkstatus(String workstatus) {
        this.workstatus = workstatus;
    }
}
