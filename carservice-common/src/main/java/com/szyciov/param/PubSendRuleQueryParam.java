package com.szyciov.param;

/**
 * 派单规则查询对象
 * Created by LC on 2017/5/26.
 */
public class PubSendRuleQueryParam {

    //派单规则 id
    private String id;

    //城市编码
    private String cityCode;

    //车辆类型
    private String vehicleType;

    //系统类型
    private String platformType;

    //下单类型 即刻/预约
    private String useType;

    //派单方式
    private String sendType;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getPlatformType() {
        return platformType;
    }

    public void setPlatformType(String platformType) {
        this.platformType = platformType;
    }

    public String getUseType() {
        return useType;
    }

    public void setUseType(String useType) {
        this.useType = useType;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }
}
 