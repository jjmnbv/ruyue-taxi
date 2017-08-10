package com.szyciov.coupon.dto;

import java.time.LocalDate;

import lombok.Data;

/**
 * 抵用券订单信息缓存DTO
 *
 * @author LC
 * @date 2017/8/2
 */
@Data
public class CouponOrderCacheDTO {

    //订单次数
    private Integer count;

    //连续周期天数
    private Integer cycleday;

    //订单总金额
    private Double money;

    //统计时间
    private LocalDate date;

}
 