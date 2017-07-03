package com.szyciov.dto.driverShiftManagent;

/**
 * 交接班已处理 保存DTO
 * Created by LC on 2017/3/1.
 */
public class ProcessedSaveDto {

    //待处理表ID
    private String pendingId;

    //处理人ID
    private String processperson;

    //处理人姓名
    private String processpersonname;

    //接班司机ID
    private String relieveddriverid;

    //接班司机信息
    private String relieveddriverInfo;

    //租赁公司ID
    private String leaseId;

    //车辆ID
    private String vehicleId;

    //交接状态 1-交班成功，2-车辆回收，3-交班超时，4-指派当班
    private String shifttype;

    //交班类型 0-自主交班,1-人工指派
    private String relievedtype;

    public String getPendingId() {
        return pendingId;
    }

    public void setPendingId(String pendingId) {
        this.pendingId = pendingId;
    }

    public String getProcessperson() {
        return processperson;
    }

    public void setProcessperson(String processperson) {
        this.processperson = processperson;
    }

    public String getRelieveddriverid() {
        return relieveddriverid;
    }

    public void setRelieveddriverid(String relieveddriverid) {
        this.relieveddriverid = relieveddriverid;
    }

    public String getRelieveddriverInfo() {
        return relieveddriverInfo;
    }

    public void setRelieveddriverInfo(String relieveddriverInfo) {
        this.relieveddriverInfo = relieveddriverInfo;
    }

    public String getShifttype() {
        return shifttype;
    }

    public void setShifttype(String shifttype) {
        this.shifttype = shifttype;
    }

    public String getRelievedtype() {
        return relievedtype;
    }

    public void setRelievedtype(String relievedtype) {
        this.relievedtype = relievedtype;
    }

    public String getProcesspersonname() {
        return processpersonname;
    }

    public void setProcesspersonname(String processpersonname) {
        this.processpersonname = processpersonname;
    }

    public String getLeaseId() {
        return leaseId;
    }

    public void setLeaseId(String leaseId) {
        this.leaseId = leaseId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }
}
 