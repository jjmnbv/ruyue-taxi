package com.supervision.api.basic;

import com.supervision.enums.CommandEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 3.2.6	网约车平台公司计程计价方式(PTYJ)
 * Created by 林志伟 on 2017/7/6.
 */
@Data
public class CompanyValuationWay extends BasicApi {
    public CompanyValuationWay(){
        super();
        setCommand(CommandEnum.CompanyValuationWay);
    }
//    行政区划代码
    private String address;
//    运价类型编码
    private String fareType;
//      运价类型说明
    private String fareTypeNote;
//      运价有效期起
    private String fareValidOn;
//    运价有效期止
    private String fareValidOff;
//    起步价
    private String startFare;
//    起步里程
    private String startMile;
//    单价
    private String unitPrice;
//    单程加价单价
    private String upPrice;
//  单程加价公里
    private String upPriceStartMile;
    //营运早高峰时间起
    private String morningPeakTimeOn;
//    营运早高峰时间止
    private String morningPeakTimeOff;
//    营运晚高峰时间起
    private String eveningPeakTimeOn;
//营运晚高峰时间止
    private String eveningPeakTimeOff;
//    其他营运高峰时间起
    private String otherPeakTimeOn;
//其他营运高峰时间止
    private String otherPeakTimeOff;
//    高峰时间单程加价单价
    private String peakUnitPrice;

//    高峰时间单程加价公里
    private String peakPriceStartMile;
//  计程单价（按公里）
    private String unitPricePerMile;
//    计时单价（按分钟）
    private String unitPricePerMinute;
//远途费（按公里）
    private String longDistancePrice;
//远途费起算公里
    private String longDistanceStartMile;
//    低速计时加价（按分钟）
    private String lowSpeedPricePerMinute;
//    夜间费（按公里）1
    private String nightPricePerMile;
//夜间费（按分钟）
    private String nightPricePerMinute;
//   其它加价金额
    private String otherPrice;
//    服务车型编码
    private Integer taxiTypeCode;
//    服务类型
    private String serviceTypeCode;
//    计费规则地址
    private String fareRuleUrl;









}
