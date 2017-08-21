package com.szyciov.coupon.dto;

import java.time.LocalDate;

import lombok.Data;

/**
 * 抵用券已经获取过参数
 * @author LC
 * @date 2017/8/20
 */
@Data
public class CouponHavedDTO {

    private LocalDate nowDate;

    private Integer nowlCount;

    private Integer allCount;
}
 