package com.szyciov.op.vo.pubdriver;

import com.szyciov.enums.BindingStateEnum;
import com.szyciov.enums.DriverEnum;

/**
 * 用于司机导出VO对象
 * Created by LC on 2017/3/27.
 */
public class ExportDriverVo
{
    //资格证号
    private String jobnum;
    //姓名
    private String name;
    //电话
    private String phone;
    //性别
    private String sex;
    //类型
    private String vehicletype;
    //身份证
    private String idcardnum;
    //驾照类型
    private String driversTypeName;
    //驾龄
    private String driveryears;
    //登记城市
    private String cityName;
    //绑定类型
    private String boundstate;
    //在职状态
    private String jobstatus;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        if(this.sex!=null){
            return DriverEnum.getSexStr(this.sex);
        }
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getVehicletype() {
        if(this.vehicletype!=null){
            return DriverEnum.getDriverTypeStr(this.vehicletype);
        }
        return vehicletype;
    }

    public void setVehicletype(String vehicletype) {
        this.vehicletype = vehicletype;
    }

    public String getDriversTypeName() {
        return driversTypeName;
    }

    public void setDriversTypeName(String driversTypeName) {
        this.driversTypeName = driversTypeName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getJobnum() {
        return jobnum;
    }

    public void setJobnum(String jobnum) {
        this.jobnum = jobnum;
    }

    public String getIdcardnum() {
        return idcardnum;
    }

    public void setIdcardnum(String idcardnum) {
        this.idcardnum = idcardnum;
    }

    public String getDriveryears() {
        return driveryears;
    }

    public void setDriveryears(String driveryears) {
        this.driveryears = driveryears;
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

    public String getJobstatus() {
        if(this.jobstatus!=null){
            return DriverEnum.getJobStatusStr(this.jobstatus);
        }
        return jobstatus;
    }

    public void setJobstatus(String jobstatus) {
        this.jobstatus = jobstatus;
    }

}
 