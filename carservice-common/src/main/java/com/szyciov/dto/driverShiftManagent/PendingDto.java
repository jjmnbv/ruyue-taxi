package com.szyciov.dto.driverShiftManagent;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.szyciov.util.DateUtil;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 待处理DTO
 * Created by LC on 2017/3/1.
 */
public class PendingDto {
    //待处理ID
    private String id;
    //车辆ID
    private String vehicleid;
    //登记城市
    private String cityStr;
    //车牌号码
    private String platenoStr;
    //当班司机ID
    private String driverid;
    //当班司机信息
    private String driverInfo;
    //当前在线时间
    private String onlinetimeStr;

    private Integer onlinetime;
    //
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy/MM/dd HH:mm:ss")
    private Date  applytime;

    private String applytimeStr;

    private String succeedDrivers;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDriverInfo() {
        return driverInfo;
    }

    public void setDriverInfo(String driverInfo) {
        this.driverInfo = driverInfo;
    }


    public String getSucceedDrivers() {
        return succeedDrivers;
    }

    public void setSucceedDrivers(String succeedDrivers) {
        this.succeedDrivers = succeedDrivers;
    }

    public String getVehicleid() {
        return vehicleid;
    }

    public void setVehicleid(String vehicleid) {
        this.vehicleid = vehicleid;
    }

    public String getDriverid() {
        return driverid;
    }

    public void setDriverid(String driverid) {
        this.driverid = driverid;
    }

    public String getOnlinetimeStr() {
        if(this.getOnlinetime()>0){
            return this.getOnlinetime()/60+"小时"+this.getOnlinetime()%60+"分";
        }else{
            return "/";
        }
    }

    public void setOnlinetimeStr(String onlinetimeStr) {
        this.onlinetimeStr = onlinetimeStr;
    }

    public Integer getOnlinetime() {
        return onlinetime;
    }

    public void setOnlinetime(Integer onlinetime) {
        this.onlinetime = onlinetime;
    }

    public String getCityStr() {
        return cityStr;
    }

    public void setCityStr(String cityStr) {
        this.cityStr = cityStr;
    }

    public String getPlatenoStr() {
        return platenoStr;
    }

    public void setPlatenoStr(String platenoStr) {
        this.platenoStr = platenoStr;
    }

    public String getApplytimeStr() {
        if(this.applytime!=null){
            return DateUtil.format(this.applytime,"yyyy-MM-dd HH:mm");
        }
        return applytimeStr;
    }

    public void setApplytimeStr(String applytimeStr) {
        this.applytimeStr = applytimeStr;
    }
}
 