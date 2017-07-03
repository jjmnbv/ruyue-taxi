package com.szyciov.dto.driverVehicleBindManage;

import com.szyciov.enums.DriverEnum;

/**
 * 返回车辆绑定司机信息集合
 * Created by LC on 2017/3/8.
 */
public class VehicleBindInfoDto {

    //绑定信息ID
    private String id;
    //司机姓名
    private String name;
    //司机电话
    private String phone;
    //司机性别
    private String sex;
    //所属城市
    private String cityStr;
    //状态
    private String passworkstatus;
    //司机ID
    private String driverID;

    private String text;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getText() {
        return this.name+" "+this.phone;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String toString(){
        return this.name+" "+this.phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCityStr() {
        return cityStr;
    }

    public void setCityStr(String cityStr) {
        this.cityStr = cityStr;
    }

    public String getPassworkstatus() {
        if(this.passworkstatus!=null){
            return DriverEnum.getPassStatusStr(passworkstatus);
        }
        return "/";
    }

    public void setPassworkstatus(String passworkstatus) {
        this.passworkstatus = passworkstatus;
    }

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }
}
 