package com.szyciov.param;

import java.util.List;

/**
 * 派单司机查询对象
 * Created by LC on 2017/5/25.
 */
public class SendOrderDriverQueryParam {


    /**
     * 租赁公司或者运管公司id
     */
    private String companyid;

    /**
     * 下单人ID
     */
    private String userid;

    /**
     * 订单所属城市
     */
    private String city;

    /**
     * 南侧经度
     */
    private double minLng;

    /**
     * 北侧经度
     */
    private double maxLng;

    /**
     * 西侧纬度
     */
    private double minLat;

    /**
     * 东侧纬度
     */
    private double maxLat;

    /**
     * 下车城市，获取司机的时候，上下车城市只要有一个在经营范围内就可以
     */
    private String offcity;

    /**
     * 司机类型 0-网约车，1-出租车
     */
    private String vehicleType;

    /**
     * 系统类型 0-运管端，1-租赁端
     */
    private String platformType;

    /**
     * 下单车型ID
     */
    private String selectedModelId;

    /**
     * 查询车型ID集合
     */
    private List<String> queryModelIds;

    /**
     * 工作状态
     */
    private List<String> workstatus;

    /**
     * 升级车型次数
     */
    private Integer nextCount;

    /**归属车企*/
    private String belongleasecompany;

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getMinLng() {
        return minLng;
    }

    public void setMinLng(double minLng) {
        this.minLng = minLng;
    }

    public double getMaxLng() {
        return maxLng;
    }

    public void setMaxLng(double maxLng) {
        this.maxLng = maxLng;
    }

    public double getMinLat() {
        return minLat;
    }

    public void setMinLat(double minLat) {
        this.minLat = minLat;
    }

    public double getMaxLat() {
        return maxLat;
    }

    public void setMaxLat(double maxLat) {
        this.maxLat = maxLat;
    }

    public String getOffcity() {
        return offcity;
    }

    public void setOffcity(String offcity) {
        this.offcity = offcity;
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

    public String getSelectedModelId() {
        return selectedModelId;
    }

    public void setSelectedModelId(String selectedModelId) {
        this.selectedModelId = selectedModelId;
    }

    public Integer getNextCount() {
        return nextCount;
    }

    public void setNextCount(Integer nextCount) {
        this.nextCount = nextCount;
    }

    public List<String> getQueryModelIds() {
        return queryModelIds;
    }

    public void setQueryModelIds(List<String> queryModelIds) {
        this.queryModelIds = queryModelIds;
    }

    public List<String> getWorkstatus() {
        return workstatus;
    }

    public void setWorkstatus(List<String> workstatus) {
        this.workstatus = workstatus;
    }

    public String getBelongleasecompany() {
        return belongleasecompany;
    }

    public void setBelongleasecompany(String belongleasecompany) {
        this.belongleasecompany = belongleasecompany;
    }
}
 