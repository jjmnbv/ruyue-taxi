package com.szyciov.enums;

/**
 * 车辆状态枚举
 * Created by LC on 2017/3/9.
 */
public enum VehicleEnum {

    /**
     * 车辆类型
     */
    VEHICLE_TYPE_CAR("0","网约车"),
    VEHICLE_TYPE_TAXI("1","出租车"),



    VEHICLE_STATUS_ONLINE("0","营运中"),
    VEHICLE_STATUS_OFFLINE("1","维修中");



    public String code;
    public String msg;

    VehicleEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 返回车辆状态 msg
     * @param code
     * @return
     */
    public static String getVehicleStatusMsg(String code){
        switch (code) {
            case "0":
                return VehicleEnum.VEHICLE_STATUS_ONLINE.msg;
            case "1":
                return VehicleEnum.VEHICLE_STATUS_OFFLINE.msg;
            default:
                return "/";
        }
    }

    /**
     * 返回车辆类型 msg
     * @param code
     * @return
     */
    public static String getVehicleTypeMsg(String code){
        switch (code) {
            case "0":
                return VehicleEnum.VEHICLE_TYPE_CAR.msg;
            case "1":
                return VehicleEnum.VEHICLE_TYPE_TAXI.msg;
            default:
                return "/";
        }
    }
}
