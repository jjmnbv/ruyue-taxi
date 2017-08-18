package com.szyciov.dto.pubCooresource;

/**
 * Created by ZF on 2017/8/11.
 */
public class UpdatePubCooresourceStatusDto {
    private String vehicleId; // 车辆ID
    private String leaseCompanyId; // 租赁/运管 公司ID
    private int type; // 0-网约车,1-出租车
    private int status; // 状态   1 解绑    0绑定

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getLeaseCompanyId() {
        return leaseCompanyId;
    }

    public void setLeaseCompanyId(String leaseCompanyId) {
        this.leaseCompanyId = leaseCompanyId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
