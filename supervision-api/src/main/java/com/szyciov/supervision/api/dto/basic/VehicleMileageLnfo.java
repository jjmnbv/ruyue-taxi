package com.szyciov.supervision.api.dto.basic;

import com.supervision.enums.CommandEnum;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 3.2.9	网约车车辆里程信息(CLLC)
 * Created by 林志伟 on 2017/7/6.
 */

public class VehicleMileageLnfo extends BasicApi {
    public VehicleMileageLnfo() {
        super();
        setCommand(CommandEnum.VehicleMileageLnfo);
    }
//    注册地行政区划编号
    private String address;
//    车牌号码
    private String vehicleNo;
//    车牌颜色
    private String plateColor;
//  车辆行驶总里程
    private String totalMile;
    @JsonIgnore
    private Integer state;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getPlateColor() {
        return plateColor;
    }

    public void setPlateColor(String plateColor) {
        this.plateColor = plateColor;
    }

    public String getTotalMile() {
        return totalMile;
    }

    public void setTotalMile(String totalMile) {
        this.totalMile = totalMile;
    }

    @Override
    public Integer getState() {
        return state;
    }

    @Override
    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "VehicleMileageLnfo{" +
                "address='" + address + '\'' +
                ", vehicleNo='" + vehicleNo + '\'' +
                ", plateColor='" + plateColor + '\'' +
                ", totalMile='" + totalMile + '\'' +
                ", state=" + state +
                "} " + super.toString();
    }
}
