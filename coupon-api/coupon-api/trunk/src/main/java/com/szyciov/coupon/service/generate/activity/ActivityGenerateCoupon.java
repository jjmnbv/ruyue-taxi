package com.szyciov.coupon.service.generate.activity;

import java.time.LocalDate;

import com.szyciov.coupon.dto.GenerateCouponDTO;
import com.szyciov.coupon.service.generate.AbstractGenerateCoupon;
import com.szyciov.dto.coupon.PubCouponActivityDto;
import com.szyciov.entity.coupon.PubCouponRule;
import com.szyciov.enums.coupon.CouponActivityEnum;
import com.szyciov.param.coupon.GenerateCouponParam;
import com.szyciov.util.TemplateHelper;
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


    @Override
    protected boolean afterGenerated(GenerateCouponDTO dto) {


        return false;
    }

    @Override
    protected LocalDate getCouponEndDate(PubCouponActivityDto activity) {
        //如果是机构客户，则返回当前日期，机构客户券无使用有效期限制
        if(CouponActivityEnum.TARGET_ORGAN_CLIENT.code.equals(activity.getSendruletarget())){
            return LocalDate.now();
        }
        return super.getCouponEndDate(activity);
    }

    @Override
    protected boolean stayGenerate(PubCouponActivityDto activity, String userId, GenerateCouponParam param) {

        return false;
    }
}
 