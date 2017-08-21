package com.szyciov.coupon.service.generate.manual;

import javax.annotation.Resource;

import com.szyciov.coupon.dto.CouponHavedDTO;
import com.szyciov.coupon.dto.GenerateCouponDTO;
import com.szyciov.coupon.service.RedisService;
import com.szyciov.coupon.service.generate.AbstractGenerateCoupon;
import com.szyciov.dto.coupon.PubCouponActivityDto;
import com.szyciov.entity.coupon.PubCouponRule;
import com.szyciov.enums.RedisKeyEnum;
import com.szyciov.param.coupon.GenerateCouponParam;
import com.szyciov.util.GsonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 人工抵用券生成
 *
 * @author LC
 * @date 2017/7/25
 */
@Service("manualGenerateCoupon")
public class ManualGenerateCoupon extends AbstractGenerateCoupon {

    @Resource
    private RedisService redisService;



    @Override
    protected boolean validRule(PubCouponRule rule,GenerateCouponParam param, PubCouponActivityDto activity) {
      return true;
    }

    @Override
    protected boolean afterGenerated(GenerateCouponDTO dto) {
        return false;
    }

    @Override
    protected boolean validHaved(PubCouponActivityDto activity, String userId) {
        String key = RedisKeyEnum.COUPON_HAVE.code+activity.getId();
        String resultStr = redisService.hmGet(key,userId);

        if(StringUtils.isNotEmpty(resultStr)) {
            CouponHavedDTO havedDTO = GsonUtil.fromJson(resultStr,CouponHavedDTO.class);
            //人工模式下，可获得券的最大数量为活动配置的发送数据量
            if(havedDTO.getAllCount()>=activity.getSendcount()){
                return false;
            }
        }
        return true;
    }

    @Override
    protected boolean stayGenerate(PubCouponActivityDto activity, String userId, GenerateCouponParam param) {

        return  false;
    }

    @Override
    protected boolean validCity(PubCouponActivityDto activity, String cityCode) {
        return true;
    }
}
 