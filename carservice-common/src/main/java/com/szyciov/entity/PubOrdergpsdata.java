package com.szyciov.entity;

import java.util.Date;

/**
 * Created by shikang on 2017/5/19.
 */
public class PubOrdergpsdata {

    /**
     * 主键
     */
    private String id;

    /**
     * 订单号
     */
    private String orderno;

    /**
     * 设备ID
     */
    private String deviceid;

    /**
     * 所属车辆
     */
    private String vehicleid;

    /**
     * 经度
     */
    private Double lng;

    /**
     * 纬度
     */
    private Double lat;

    /**
     * GPS方向
     */
    private Double gpsdirection;

    /**
     * GPS速度
     */
    private Double gpsspeed;

    /**
     * OBD速度
     */
    private Double obdspeed;

    /**
     * GPS时间
     */
    private Date gpstime;

    /**
     * 来源(0-司机App，1-OBD)
     */
    private Integer gpssource;

    /**
     * 总里程(单位:米)
     */
    private Double mileage;

    /**
     * 总耗油
     */
    private Double fuel;

    /**
     * 基站码
     */
    private String basestationcode;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 更新时间
     */
    private Date updatetime;

    /**
     * 数据状态
     */
    private Integer status;

    /**
     * GPS状态
     */
    private Integer gpsstatus;

    /**
     * 司机id
     */
    private String driverid;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getVehicleid() {
        return vehicleid;
    }

    public void setVehicleid(String vehicleid) {
        this.vehicleid = vehicleid;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getGpsdirection() {
        return gpsdirection;
    }

    public void setGpsdirection(Double gpsdirection) {
        this.gpsdirection = gpsdirection;
    }

    public Double getGpsspeed() {
        return gpsspeed;
    }

    public void setGpsspeed(Double gpsspeed) {
        this.gpsspeed = gpsspeed;
    }

    public Double getObdspeed() {
        return obdspeed;
    }

    public void setObdspeed(Double obdspeed) {
        this.obdspeed = obdspeed;
    }

    public Date getGpstime() {
        return gpstime;
    }

    public void setGpstime(Date gpstime) {
        this.gpstime = gpstime;
    }

    public Integer getGpssource() {
        return gpssource;
    }

    public void setGpssource(Integer gpssource) {
        this.gpssource = gpssource;
    }

    public Double getMileage() {
        return mileage;
    }

    public void setMileage(Double mileage) {
        this.mileage = mileage;
    }

    public Double getFuel() {
        return fuel;
    }

    public void setFuel(Double fuel) {
        this.fuel = fuel;
    }

    public String getBasestationcode() {
        return basestationcode;
    }

    public void setBasestationcode(String basestationcode) {
        this.basestationcode = basestationcode;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getGpsstatus() {
        return gpsstatus;
    }

    public void setGpsstatus(Integer gpsstatus) {
        this.gpsstatus = gpsstatus;
    }

    public String getDriverid() {
        return driverid;
    }

    public void setDriverid(String driverid) {
        this.driverid = driverid;
    }
}
