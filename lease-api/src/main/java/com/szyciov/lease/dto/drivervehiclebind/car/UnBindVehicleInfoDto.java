package com.szyciov.lease.dto.drivervehiclebind.car;

/**
 * 未绑定车辆查询
 * Created by LC on 2017/3/17.
 */
public class UnBindVehicleInfoDto {

    private String id;
    //车牌号
    private String platenoStr;
    //品牌车系
    private String vehclineName;
    //服务车型
    private String vehiclemodelName;
    //城市
    private String cityStr;
    //颜色
    private String color;
    //行数
    private String rownum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getVehiclemodelName() {
        return vehiclemodelName;
    }

    public void setVehiclemodelName(String vehiclemodelName) {
        this.vehiclemodelName = vehiclemodelName;
    }

    public String getCityStr() {
        return cityStr;
    }

    public void setCityStr(String cityStr) {
        this.cityStr = cityStr;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getRownum() {
        return rownum;
    }

    public void setRownum(String rownum) {
        this.rownum = rownum;
    }
}
 