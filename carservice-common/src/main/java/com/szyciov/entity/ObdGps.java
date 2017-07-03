package com.szyciov.entity;

/**
 * Created by shikang on 2017/5/24.
 */
public class ObdGps {

    /**
     * 设备id
     */
    private String eqpId;

    /**
     * 车辆id
     */
    private String relationId;

    /**
     * 关联类型(1_车辆)
     */
    private Integer relationType;

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 纬度
     */
    private Double latitude;

    /**
     * 上传时间
     */
    private String locTime;

    /**
     * GPS速度
     */
    private Double gpsSpeed;

    /**
     * GPS方向
     */
    private Double gpsDrct;

    /**
     *是否定位(1_有效定位;2_无效定位)
     */
    private Integer isPosition;

    /**
     * 是否是GPS时间(1_GPS时间;2_GSM时间)
     */
    private Integer isGpsTime;

    /**
     * 当前总里程(km)
     */
    private Double locTotalMileage;

    /**
     * 当前位置总油耗(L)
     */
    private Double locTotalFuel;

    /**
     * 创建时间
     */
    private String createTime;

    public String getEqpId() {
        return eqpId;
    }

    public void setEqpId(String eqpId) {
        this.eqpId = eqpId;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public Integer getRelationType() {
        return relationType;
    }

    public void setRelationType(Integer relationType) {
        this.relationType = relationType;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getLocTime() {
        return locTime;
    }

    public void setLocTime(String locTime) {
        this.locTime = locTime;
    }

    public Double getGpsSpeed() {
        return gpsSpeed;
    }

    public void setGpsSpeed(Double gpsSpeed) {
        this.gpsSpeed = gpsSpeed;
    }

    public Double getGpsDrct() {
        return gpsDrct;
    }

    public void setGpsDrct(Double gpsDrct) {
        this.gpsDrct = gpsDrct;
    }

    public Integer getIsPosition() {
        return isPosition;
    }

    public void setIsPosition(Integer isPosition) {
        this.isPosition = isPosition;
    }

    public Integer getIsGpsTime() {
        return isGpsTime;
    }

    public void setIsGpsTime(Integer isGpsTime) {
        this.isGpsTime = isGpsTime;
    }

    public Double getLocTotalMileage() {
        return locTotalMileage;
    }

    public void setLocTotalMileage(Double locTotalMileage) {
        this.locTotalMileage = locTotalMileage;
    }

    public Double getLocTotalFuel() {
        return locTotalFuel;
    }

    public void setLocTotalFuel(Double locTotalFuel) {
        this.locTotalFuel = locTotalFuel;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
