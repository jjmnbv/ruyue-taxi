package com.szyciov.lease.param.drivervehiclebind;

import com.szyciov.param.QueryParam;

/**
 * Created by LC on 2017/3/20.
 */
public class CarBindRecordQueryParam extends QueryParam
{

    //租赁公司ID
    private String leaseId;
    //系统类型
    private String platformType;
    //司机ID
    private String driverId;
    //车辆ID
    private String vehicleId;
    //工号
    private String jobNum;
    //绑定状态
    private String bindStatus;
    //操作起始时间
    private String timeStart;
    //操作截止时间
    private String timeEnd;
    //车牌号
    private String platenoStr;
    //车架号
    private String vinStr;
    //查询类型
    private String queryType;
    //归属车企
    private String belongLeasecompany;

    public String getLeaseId() {
        return leaseId;
    }

    public void setLeaseId(String leaseId) {
        this.leaseId = leaseId;
    }

    public String getPlatformType() {
        return platformType;
    }

    public void setPlatformType(String platformType) {
        this.platformType = platformType;
    }

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

    public String getBindStatus() {
        return bindStatus;
    }

    public void setBindStatus(String bindStatus) {
        this.bindStatus = bindStatus;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getPlatenoStr() {
        return platenoStr;
    }

    public void setPlatenoStr(String platenoStr) {
        this.platenoStr = platenoStr;
    }

    public String getVinStr() {
        return vinStr;
    }

    public void setVinStr(String vinStr) {
        this.vinStr = vinStr;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

	public String getBelongLeasecompany() {
		return belongLeasecompany;
	}

	public void setBelongLeasecompany(String belongLeasecompany) {
		this.belongLeasecompany = belongLeasecompany;
	}
}
 