package com.szyciov.coupon.service.generate.register;

import com.szyciov.coupon.service.generate.AbstractGenerateCoupon;
import com.szyciov.dto.coupon.PubCouponActivityDto;
import com.szyciov.entity.coupon.PubCouponRule;
import com.szyciov.param.coupon.GenerateCouponParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 注册类型抵用券生成
 *
 * @author LC
 * @date 2017/7/25
 */
@Service("registerGenerateCoupon")
public class RegisterGenerateCoupon extends AbstractGenerateCoupon {

    private Logger logger = LoggerFactory.getLogger(RegisterGenerateCoupon.class);

    @Override
    protected boolean validRule(PubCouponRule rule, GenerateCouponParam param, PubCouponActivityDto activity) {
        return true;
    }
}
 