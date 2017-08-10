package com.szyciov.supervision.api.order;

import com.szyciov.supervision.enums.CommandEnum;

import lombok.Data;

/**
 * 3.3.5	订单补传*(DDBC) 实时
 * Created by 林志伟 on 2017/7/7.
 */
@Data
public class OrderSupplements extends OrderApi {
    public OrderSupplements() {
        super();
        setCommand(CommandEnum.OrderSupplements);
    }

    //    订单编号
    private String orderId;

    //    订单时间	YYYYMMDDHHMMSS
    private String orderTime;

    /**
     *
     * 订单状态	DDLR：订单录入（乘客下单）
     DDQX:订单取消（司机应召前乘客取消订单）
     CKQX：司机应召后乘客取消订单
     SJQX：司机应召后司机取消订单
     DDWC：订单完成
     DDSB：订单失败（无司机应召）

     */
    private String orderStatus;

//    用车类型
    private String vehType;
    /**
     * 是否预约订单	0:否，1:是
     */
    private Integer isReserve;
    /**
     * 是否语音订单	0:否，1:是
     */
    private Integer isVoice;
    /**
     * 语音存储地址	是否语音订单为“是”时该字段必填
     */
    private String voiceUrl;
    /**
     * 用车时间	YYYYMMDDHHMMSS
     */
    private String useTime;
    /**
     *用车地点
     *
     */
    private String useLocale;
    /**
     * 坐标加密标识	1：GCJ-02 测绘局标准
     2：WGS84 GPS标准
     3：BD-09 百度标准
     4：CGCS2000 北斗标准
     0：其他

     */
    private String encrypt;
    /**
     * 用车经度	单位：1*10-6度
     */
    private String useLon;
    /**
     *  用车纬度	单位：1*10-6度
     */
    private String useLat;
    /**
     * 预计目的地点详细地址
     */
    private String destLocale;
    /**
     * 预计目的地点经度	单位：1*10-6度
     */
    private String destLon;
    /**
     * 预计目的地点纬度	单位：1*10-6度
     */
    private String destLat;
    /**
     * 驾驶员姓名
     */
    private String driName;
    /**
     * 驾驶员联系电话
     */
    private String driTel;

    /**
     * 网络预约出租汽车驾驶员证编号
     */
    private String qulificationNo;
    /**
     * 车牌号码	省份简称+城市（地区）编号+5位数字或字母
     */
    private String vehicleNo;
    /**
     * 网络预约出租汽车运输证号
     */
    private String carCertNo;
    /**
     * 实际上车时间	YYYYMMDDHHMMSS
     */
    private String onTime;
    /**
     * 实际下车时间	YYYYMMDDHHMMSS
     */
    private String offTime;
    /**
     * 载客里程	单位:km
     */
    private String psgMile;
    /**
     * 载客时间	单位：秒
     */
    private String duration;
    /**
     * 应收金额	单位：元
     */
    private String receivable;
    /**
     * 实收金额	单位：元
     */
    private String paid;
    /**
     * 优惠金额	单位：元
     */
    private String discount;
    /**
     * 现金支付金额	 单位：元
     */
    private String cash;
    /**
     * 电子支付金额	 单位：元
     */
    private String elePay;
    /**
     * 电子支付机构
     */
    private String eleOrg;
    /**
     * Pos机支付金额	单位：元
     */
    private String posPay;
    /**
     * Pos机收单机构
     */
    private String posOrg;
    /**
     * 高峰时段加价金额	 单位：元
     */
    private String callPay;
    /**
     * 附加费	 单位：元
     */
    private String extraPay;
    /**
     * 高峰时段加价金额	 单位：元
     */
    private String peakPay;
    /**
     * 夜间时段加价金额	 单位：元
     */
    private String nightPay;
    /**
     * 发票状态
     */
    private String billStatus;
    /**
     * 乘客称谓
     */
    private String psgName;
    /**
     * 乘客性别
     */
    private String psgGender;
    /**
     * 乘客电话
     */
    private String psgTel;





}
