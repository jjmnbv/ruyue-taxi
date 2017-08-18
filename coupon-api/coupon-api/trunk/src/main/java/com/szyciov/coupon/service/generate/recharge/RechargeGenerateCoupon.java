package com.szyciov.coupon.service.generate.recharge;

import java.time.LocalDate;

import com.szyciov.coupon.dto.GenerateCouponDTO;
import com.szyciov.coupon.service.generate.AbstractGenerateCoupon;
import com.szyciov.dto.coupon.PubCouponActivityDto;
import com.szyciov.entity.coupon.PubCouponRule;
import com.szyciov.enums.coupon.CouponActivityEnum;
import com.szyciov.param.coupon.GenerateCouponParam;
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

    @Override
    protected boolean afterGenerated(GenerateCouponDTO dto) {
        return false;
    }

    /**
     * 充值返券，不限定次数
     * @param activity
     * @param userId
     * @return
     */
    @Override
    protected boolean validHaved(PubCouponActivityDto activity, String userId) {
        return true;
    }


    @Override
    protected LocalDate getCouponEndDate(PubCouponActivityDto activity) {
        //如果是机构客户，则返回当前日期，机构客户券无使用有效期限制
        if(CouponActivityEnum.TARGET_ORGAN_CLIENT.code.equals(activity.getSendruletarget())){
            return LocalDate.now();
        }
        return super.getCouponEndDate(activity);
    }
}
 