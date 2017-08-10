package com.szyciov.coupon.service.generate.recharge;

import com.szyciov.coupon.service.generate.AbstractGenerateCoupon;
import com.szyciov.dto.coupon.PubCouponActivityDto;
import com.szyciov.entity.coupon.PubCouponRule;
import com.szyciov.enums.RedisKeyEnum;
import com.szyciov.param.coupon.GenerateCouponParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 充值类型抵用券生成
 *
 * @author LC
 * @date 2017/7/25
 */
@Service("rechargeGenerateCoupon")
public class RechargeGenerateCoupon extends AbstractGenerateCoupon {

    @Override
    protected boolean validRule(PubCouponRule rule,GenerateCouponParam param, PubCouponActivityDto activity) {
        //充值金额 大于= 规则设定金额
       if(param.getMoney()>=rule.getRechargemoney()) {
           return true;
       }
       return false;
    }


}
 