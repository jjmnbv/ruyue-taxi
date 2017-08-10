package com.szyciov.coupon.dto;

import lombok.Data;

/**
 * @author LC
 * @date 2017/8/9
 */
@Data
public class CouponUseInfoDTO {

    /**
     * 原抵扣金额
     */
    private Double discountamount;

    /**
     * 优惠券抵扣金额
     */
    private Double couponmoney;

    /**
     * 实际抵扣金额
     */
    private Double actualamount;


}
 