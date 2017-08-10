package com.szyciov.coupon.mapper;

import java.util.List;

import com.szyciov.coupon.dto.CouponActivityDTO;
import com.szyciov.coupon.param.CouponActivityQueryParam;

/**
 * 抵用券活动发放 城市mapper
 * @author LC
 * @date 2017/7/27
 */
public interface PubCouponActivityUseCityMapper {

    /**
     * 获取抵用券发放城市列表
     * @param activityId
     * @return
     */
    List<String> listUseCityCode(String activityId);

}
 