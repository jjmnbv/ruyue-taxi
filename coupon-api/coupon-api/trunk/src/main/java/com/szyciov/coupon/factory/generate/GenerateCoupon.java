package com.szyciov.coupon.factory.generate;

import java.util.List;

import com.szyciov.coupon.dto.GenerateCouponDTO;
import com.szyciov.entity.coupon.PubCoupon;
import com.szyciov.param.coupon.GenerateCouponParam;

/**
 * 生成抵用券接口
 *
 * @author LC
 * @date 2017/7/25
 */
public interface GenerateCoupon {




    /**
     * 生成抵用券
     * @param param
     * @return
     */
    List<GenerateCouponDTO> generate(GenerateCouponParam param);

}
