package com.szyciov.dto.pubvehicle;

/**
 * 选择车辆组件 dto
 * Created by LC on 2017/3/7.
 */
public class PubVehicleSelectDto {

    //车辆ID
    private String id;
    //显示内容
    private String text;

    //车架号
    private String vin;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }
}
 