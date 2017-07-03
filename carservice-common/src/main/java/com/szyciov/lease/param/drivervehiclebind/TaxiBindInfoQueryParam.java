package com.szyciov.lease.param.drivervehiclebind;

import com.szyciov.param.QueryParam;

/**
 * 出租车绑定查询参数对象
 * Created by LC on 2017/3/21.
 */
public class TaxiBindInfoQueryParam extends QueryParam {

    //租赁公司ID
    private String leaseId;

    //系统类型
    private String platformtype;

    //车辆ID
    private String vehicleId;

    //品牌车系名称
    private String lineName;

    //车牌号
    private String platenoStr;

    //绑定状态
    private String boundState;

    //服务状态
    private String workStatus;

    //城市code
    private String city;

    //绑定人数
    private Integer bindCount;

    //司机ID
    private String driverId;

    //班次状态 0已分配 1未分配
    private String online;

    //营运状态
    private String vehiclestatus;

    //车辆类型 0网约车 1出租车
    private String vehicletype;
    
    //归属车企
    private String belongLeasecompany;

    public String getLeaseId() {
        return leaseId;
    }

    public void setLeaseId(String leaseId) {
        this.leaseId = leaseId;
    }

    public String getPlatformtype() {
        return platformtype;
    }

    public void setPlatformtype(String platformtype) {
        this.platformtype = platformtype;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getPlatenoStr() {
        return platenoStr;
    }

    public void setPlatenoStr(String platenoStr) {
        this.platenoStr = platenoStr;
    }

    public String getBoundState() {
        return boundState;
    }

    public void setBoundState(String boundState) {
        this.boundState = boundState;
    }

    public String getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getBindCount() {
        return bindCount;
    }

    public void setBindCount(Integer bindCount) {
        this.bindCount = bindCount;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public String getVehiclestatus() {
        return vehiclestatus;
    }

    public void setVehiclestatus(String vehiclestatus) {
        this.vehiclestatus = vehiclestatus;
    }

    public String getVehicletype() {
        return vehicletype;
    }

    public void setVehicletype(String vehicletype) {
        this.vehicletype = vehicletype;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

	public String getBelongLeasecompany() {
		return belongLeasecompany;
	}

	public void setBelongLeasecompany(String belongLeasecompany) {
		this.belongLeasecompany = belongLeasecompany;
	}
}
 