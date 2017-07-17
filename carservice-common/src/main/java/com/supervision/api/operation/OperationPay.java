package com.supervision.api.operation;

import com.supervision.enums.CommandEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 3.4.5	营运支付(YYZF) 实时
 * Created by 林志伟 on 2017/7/7.
 */
@Data
public class OperationPay extends OperationApi {
    public OperationPay() {
        super();
        setCommand(CommandEnum.OperationPay);
    }

    /**
     * 订单编号	与发送交通部一致
     */
    private String orderId;
    /**
     * 上车位置行政区划编号	见GB/T 2260
     */
    private String onArea;
    /**
     * 机动车驾驶员姓名
     */
    private String driverName;
    /**
     * 机动车驾驶证号
     */
    private String licenseId;
    /**
     * 运价类型编码	由网约车平台公司定义，与计程计价方式信息接口一一对应。
     */
    private String fareType;
    /**
     * 车牌号码	省份简称+城市（地区）编号+5位数字或字母
     */
    private String vehicleNo;
    /**
     * 车牌颜色	见JT/T 697.7—2014中5.6
     */
    private String plateColor;
    /**
     * 预计上车时间	YYYYMMDDHHMMSS
     */
    private String bookDepTime;
    /**
     * 等待时间	单位：秒
     */
    private String waitTime;
    /**
     * 车辆实际出发经度	单位：1*10-6度
     */
    private String depLongitude;
    /**
     * 车辆实际出发纬度	单位：1*10-6度
     */
    private String depLatitude;

    /**
     * 实际上车地点
     */
    private String depArea;


    /**
     * 实际上车时间
     */
    private String depTime;


    /**
     *  车辆实际到达经度
     */
    private String destLongitude;

    /**
     *  车辆实际到达纬度
     */
    private String destLatitude;
    /**
     * 实际下车地点
     */
    private String destArea;
    /**
     * 实际下车时间	YYYYMMDDHHMMSSS
     */
    private String destTime;
    /**
     * 预定车型
     */
    private String bookModel;
    /**
     * 实际使用车型
     */
    private String model;
    /**
     * 载客里程	单位：km
     */
    private String driveMile;
    /**
     * 载客时间	单位：秒
     */
    private String driveTime;
    /**
     * 空驶里程	单位：km
     */
    private String waitMile;
    /**
     * 实收金额	单位：元
     */
    private String factPrice;
    /**
     * 应收金额	单位：元
     */
    private String price;
    /**
     * 现金支付金额	单位：元
     */
    private String cashPrice;
    /**
     * 电子支付机构
     */
    private String lineName;
    /**
     * 电子支付金额	单位：元
     */
    private String linePrice;
    /**
     * POS机支付机构
     */
    private String posName;
    /**
     * POS机支付金额	单位：元
     */
    private String posPrice;
    /**
     * 优惠金额	单位：元
     */
    private String benfitPrice;
    /**
     * 预约服务费	单位：元
     */
    private String bookTip;
    /**
     * 附加费	单位：元
     */
    private String passengerTip;
    /**
     * 高峰时段时间加价金额	单位：元
     */
    private String peakUpPrice;
    /**
     * 夜间时段里程加价金额	单位：元
     */
    private String nightUpPrice;
    /**
     *远途加价金额	单位：元
     */
    private String farUpPrice;
    /**
     * 其他加价金额	单位：元
     */
    private String otherUpPrice;
    /**
     * 结算状态
     */
    private String payState;
    /**
     * 结算时间	YYYYMMDDHHMMSS
     */
    private String payTime;
    /**
     * 订单完成时间	YYYYMMDDHHMMSS
     */
    private String orderMatchTime;

    /**
     * 发票状态	数据取值有效范围：
     0：未开票
     1：已开票
     2：未知

     */
    private String invoiceStatus;
    /**
     * 驾驶员身份证号
     */
    private String driverIDCard;
    /**
     * 网络预约出租汽车驾驶员证编号
     */
    private String driverCertCard;
    /**
     * 电召费	单位：元
     */
    private String callPrice;
    /**
     * 用于补充计费规则
     */
    private String fareRuleUrl;





}
