package com.szyciov.supervision.api.operation;

import com.szyciov.supervision.enums.CommandEnum;

import lombok.Data;

/**
 * 3.4.3	营运出发(YYCF) 实时
 * Created by 林志伟 on 2017/7/7.
 */
@Data
public class OperationDeparture extends OperationApi {
    public OperationDeparture() {
        super();
        setCommand(CommandEnum.OperationDeparture);
    }

    /**
     * 订单编号	与发送交通部一致
     */
    private String orderId;
    /**
     * 机动车驾驶证号
     */
    private String licenseId;
    /**
     * 乘客电话
     */
    private String passengerPhone;
    /**
     * 由网约车平台公司定义，与计程计价方式信息接口一一对应
     */
    private String fareType;
    /**
     * 车牌号码	省份简称+城市（地区）编号+5位数字或字母
     车牌颜色	见JT/T 697.7—2014中5.6
     */
    private String vehicleNo;
    /**
     * 车牌颜色	见JT/T 697.7—2014中5.6
     */
    private String plateColor;
    /**
     * 车辆实际出发经度	单位：1*10-6度
     */
    private String depLongitude;
    /**
     * 车辆实际出发纬度	单位：1*10-6度
     */
    private String depLatitude;
    /**
     * 坐标加密标识	1：GCJ-02 测绘局标准
     2：WGS84 GPS标准
     3：BD-09 百度标准
     4：CGCS2000 北斗标准
     0：其他

     */
    private String encrypt;
    /**
     * 实际上车时间	YYYYMMDDHHMMSS
     */
    private String depTime;
    /**
     * 空驶里程	单位：km
     */
    private String waitMile;
    /**
     * 乘客等待时间	单位：秒
     */
    private String passengerWaitTime;
    /**
     * 乘客等待时间	单位：秒
     */
    private String driveWaitTime;
    /**
     * 行政区划编号	见GB/T 2260
     */
    private String address;

    /**
     * 驾驶员姓名
     */
    private String driverName;

    /**
     * 驾驶员身份证号
     */
    private String driverIDCard;
    /**
     * 网络预约出租汽车驾驶员证编号
     */
    private String driverCertCard;
    /**
     * 乘客称谓
     */
    private String passengerName;

    /**
     * 乘客性别	见JT/T 697.7-2014中，与平台发送交通部一致。
     */
    private String passengerSex;



}
