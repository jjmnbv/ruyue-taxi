package com.szyciov.coupon.mapper;

import com.szyciov.entity.coupon.PubCouponUseCity;
import org.apache.ibatis.annotations.Param;

/**
 * 使用城市mapper
 * @author LC
 * @date 2017/7/27
 */
public interface PubCouponUseCityMapper {

    void saveUseCity(PubCouponUseCity useCity);

    PubCouponUseCity getUseCityByCityAndCouponId(@Param("city") String city,@Param("couponidref") String couponidref);
}
 