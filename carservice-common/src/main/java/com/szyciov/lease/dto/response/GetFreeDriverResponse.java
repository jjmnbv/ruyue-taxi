package com.szyciov.lease.dto.response;

/**
 * Created by ZF on 2017/5/19.
 */
public class GetFreeDriverResponse {
    private String id; // 司机ID
    private String text; // 司机姓名 for select2
    private String jobNum; // 资格证号
    private String phone; // 手机号
    private String name; // 姓名
    private String workStatus; // 服务状态
    private String plateNo; // 车牌号
    private String vehicleModelName; // 服务车型
    private String belongleasecompany; // 服务车企
    private String belongleasecompanyId; // 服务车企ID

    public String getBelongleasecompany() {
        return belongleasecompany;
    }

    public void setBelongleasecompany(String belongleasecompany) {
        this.belongleasecompany = belongleasecompany;
    }

    public String getBelongleasecompanyId() {
        return belongleasecompanyId;
    }

    public void setBelongleasecompanyId(String belongleasecompanyId) {
        this.belongleasecompanyId = belongleasecompanyId;
    }

    public String getJobNum() {
        return jobNum;
    }

    public void setJobNum(String jobNum) {
        this.jobNum = jobNum;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getVehicleModelName() {
        return vehicleModelName;
    }

    public void setVehicleModelName(String vehicleModelName) {
        this.vehicleModelName = vehicleModelName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
