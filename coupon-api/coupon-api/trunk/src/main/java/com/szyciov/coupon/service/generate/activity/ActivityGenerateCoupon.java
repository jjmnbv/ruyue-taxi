package com.szyciov.coupon.service.generate.activity;

import com.szyciov.coupon.service.generate.AbstractGenerateCoupon;
import com.szyciov.dto.coupon.PubCouponActivityDto;
import com.szyciov.entity.coupon.PubCouponRule;
import com.szyciov.param.coupon.GenerateCouponParam;
import org.springframework.stereotype.Service;

/**
 * 活动类型抵用券生成
 *
 * @author LC
 * @date 2017/7/25
 */
@Service("activityGenerateCoupon")
public class ActivityGenerateCoupon extends AbstractGenerateCoupon {


    @Override
    protected boolean validRule(PubCouponRule rule, GenerateCouponParam param, PubCouponActivityDto activity) {
        return true;
    }
}
 