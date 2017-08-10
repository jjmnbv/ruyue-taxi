package com.szyciov.coupon.mapper;

import java.util.List;

import com.szyciov.coupon.dto.CouponActivityDTO;
import com.szyciov.coupon.param.CouponActivityQueryParam;

/**
 * 抵用券活动mapper
 * @author LC
 * @date 2017/7/27
 */
public interface PubCouponActivityMapper {

    /**
     * 获取有效的抵用券活动集合
     * @param queryParam
     * @return
     */
    List<CouponActivityDTO> listValidActivity(CouponActivityQueryParam queryParam);

}
 