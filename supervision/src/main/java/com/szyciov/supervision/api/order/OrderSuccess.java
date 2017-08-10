package com.szyciov.supervision.api.order;

import com.szyciov.supervision.enums.CommandEnum;

import lombok.Data;

/**
 * 订单成功
 * Created by 林志伟 on 2017/7/7.
 */
@Data
public class OrderSuccess extends OrderApi {
    public OrderSuccess(){
        super();
        setCommand(CommandEnum.OrderSuccess);
    }

    //    订单编号
    private String orderId;

    //    订单时间	YYYYMMDDHHMMSS
    private String orderTime;

//    车辆经度	单位：1*10-6度
    private String longitude;
//    车辆纬度	单位：1*10-6度
    private String latitude;

    /**
     * 坐标加密标识
     *
     *  1：GCJ-02 测绘局标准
        2：WGS84 GPS标准
        3：BD-09 百度标准
        4：CGCS2000 北斗标准0：其他

     */
    private String encrypt;
//    机动车驾驶证号
    private String licenseId;
//    驾驶员联系电话
    private String driverPhone;
//    车牌号码
    private String vehicleNo;
//    车牌颜色
    private String plateColor;
//    派单成功时间
    private String distributeTime;

//   网络预约出租汽车运输证号
    private String carCertNo;
//    驾驶员姓名
    private String driverName;
//    驾驶员身份证号
    private String idNo;
//    网络预约出租汽车驾驶员证编号
    private String driCertNo;
//    出发城市
    private String departCity;
//  目的城市
    private String destCity;
//    预计出发地点
    private String departLocale;
//    预计出发地点详细地址
    private String departLocaleDetail;
//    预计出发地点经度	单位：1*10-6度
    private String departLon;
//    预计出发地点纬度	单位：1*10-6度
    private String departLat;
//    预计目的地点
    private String desLocale;
//预计目的地点详细地址
    private String desLocaleDetail;
//    预计目的地点经度	单位：1*10-6度
    private String desLon;
//    预计目的地点纬度	单位：1*10-6度
    private String desLat;
//    车辆响应状态
    private String resStatus;
//    车辆响应时长	单位：秒
    private String resTime;


}

