package com.szyciov.supervision.api.operation;

import com.szyciov.supervision.enums.CommandEnum;

import lombok.Data;

/**
 * 3.4.4	营运到达(YYDD) 实时
 * Created by 林志伟 on 2017/7/7.
 */
@Data
public class OperationArrival extends OperationApi {
    public OperationArrival() {
        super();
        setCommand(CommandEnum.OperationArrival);
    }

    /**
     * 订单编号	与发送交通部一致
     */
    private String orderId;
    /**
     * 车辆实际到达经度	单位：1*10-6度
     */
    private String destLongitude;
    /**
     * 车辆实际到达纬度	单位：1*10-6度
     */
    private String destLatitude;
    /**
     * 坐标加密标识	1：GCJ-02 测绘局标准
     2：WGS84 GPS标准
     3：BD-09 百度标准
     4：CGCS2000 北斗标准
     0：其他

     */
    private String encrypt;

    /**
     * 实际下车时间	YYYYMMDDHHMMSS
     */
    private String destTime;
    /**
     * 载客里程	单位：km
     */
    private String driveMile;
    /**
     *载客时间	单位：秒
     */
    private String driveTime;

    /**
     * 行政区划编号	见GB/T 2260
     */
    private String address;

    /**
     * 驾驶员姓名
     */
    private String driverName;
    /**
     * 驾驶员联系电话
     */
    private String driverPhone;

    /**
     * 驾驶员身份证号
     */
    private String driverIDCard;
    /**
     * 网络预约出租汽车驾驶员证编号
     */
    private String driverCertCard;

    /**
     * 车牌号码	省份简称+城市（地区）编号+5位数字或字母
     车牌颜色	见JT/T 697.7—2014中5.6
     */
    private String vehicleNo;

    /**
     * 实际上车时间	YYYYMMDDHHMMSS
     */
    private String depTime;
    /**
     * 车辆实际出发经度	单位：1*10-6度
     */
    private String depLongitude;
    /**
     * 车辆实际出发纬度	单位：1*10-6度
     */
    private String depLatitude;
    /**
     * 等待时间	单位：秒
     */
    private String waitTime;


    /**
     * 乘客称谓
     */
    private String passengerName;


    /**
     * 乘客性别	见JT/T 697.7-2014中，与平台发送交通部一致。
     */
    private String passengerSex;
    /**
     * 乘客手机号
     */
    private String passengerPhone;
    /**
     * 应收金额	单位：元
     */
    private String price;


}
