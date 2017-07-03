package com.szyciov.lease.param.drivervehiclebind;

import com.szyciov.param.QueryParam;

/**
 * 网约车绑定查询条件对象
 * Created by LC on 2017/3/17.
 */
public class CarBindInfoQueryParam extends QueryParam {

    //租赁公司ID
    private String leaseId;
    //司机ID
    private String driverId;
    //工号
    private String jobNum;
    //绑定状态
    private String boundState;
    //城市code
    private String city;
    //车系id
    private String lineId;
    //车牌号
    private String platenoStr;
    //服务状态
    private String workStatus;
    //服务车型ID
    private String modelId;
    //品牌车系名称
    private String lineName;
    //系统类型
    private String platformtype;
    //服务类型-网约车/出租车
    private String vehicletype;

    //车辆状态
    private String vehiclestatus;
    //归属车企
    private String belongLeasecompany;
    
    //查询城市
    private String qCity;
    //查询归属车企
    private String qBelongLeasecompany;

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getJobNum() {
        return jobNum;
    }

    public void setJobNum(String jobNum) {
        this.jobNum = jobNum;
    }

    public String getBoundState() {
        return boundState;
    }

    public void setBoundState(String boundState) {
        this.boundState = boundState;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getPlatenoStr() {
        return platenoStr;
    }

    public void setPlatenoStr(String platenoStr) {
        this.platenoStr = platenoStr;
    }

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

    public String getVehicletype() {
        return vehicletype;
    }

    public void setVehicletype(String vehicletype) {
        this.vehicletype = vehicletype;
    }

    public String getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getVehiclestatus() {
        return vehiclestatus;
    }

    public void setVehiclestatus(String vehiclestatus) {
        this.vehiclestatus = vehiclestatus;
    }

	public String getBelongLeasecompany() {
		return belongLeasecompany;
	}

	public void setBelongLeasecompany(String belongLeasecompany) {
		this.belongLeasecompany = belongLeasecompany;
	}

	public String getqCity() {
		return qCity;
	}

	public void setqCity(String qCity) {
		this.qCity = qCity;
	}

	public String getqBelongLeasecompany() {
		return qBelongLeasecompany;
	}

	public void setqBelongLeasecompany(String qBelongLeasecompany) {
		this.qBelongLeasecompany = qBelongLeasecompany;
	}
}
 