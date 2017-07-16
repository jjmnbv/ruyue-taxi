package com.supervision.api.order;

import com.supervision.enums.CommandEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 3.3.1	订单发起(DDFQ) 实时
 * Created by 林志伟 on 2017/7/7.
 */
@Data
public class OrderInitiation extends OrderApi {
    public OrderInitiation(){
        super();
        setCommand(CommandEnum.OrderInitiation);
    }
    //    订单编号
    private String orderId;

    //    订单时间	YYYYMMDDHHMMSS
    private String orderTime;


//  预计用车时间
    private String departTime;

//    乘客电话
    private String passengerPhone;
//    乘客备注
    private String passengerNote;
//    预计出发地点详细地址
    private String departure;
//    预计出发地点经度
    private String depLongitude;
//    预计出发地点纬度
    private String depLatitude;
//    预计目的地点详细地址
    private String destination;
//  预计目的地点经度
    private String destLongitude;
//    预计目的地点纬度
    private String destLatitude;
//  坐标加密标识
    private String encrypt;

//    运价类型编码
    private String fareType;
//    乘客称谓
    private String orderSource;
//    乘客称谓
    private String psgName;
//    乘客性别
    private String psgGender;
//    乘车人数
    private String psgTotal;
//    vehType
    private String vehType;
//  是否预约订单
    private Integer isReserve;
//    是否语音订单
    private Integer isVoice;
//    语音存储地址
    private String voiceUrl;
//    预计里程
    private String preMile;
//    预计用时
    private String preTime;
//    预计费用
    private String preFare;
//    用车时间
    private String useTime;
//    用车地点
    private String useLocale;
//    用车经度
    private String useLon;
//    用车经度
    private String useLat;
//    服务车型编码
    private String taxiTypeCode;
//    服务类型
    private String serviceTypeCode;
//      出发城市
    private String departCity;
//    目的城市
    private String destCity;



}
