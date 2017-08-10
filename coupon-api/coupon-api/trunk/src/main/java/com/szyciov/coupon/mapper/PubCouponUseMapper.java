package com.szyciov.coupon.mapper;

import java.util.List;

import com.szyciov.entity.coupon.PubCouponUse;
import com.szyciov.param.coupon.CouponExpenseParam;
import org.apache.ibatis.annotations.Param;

/**
 * 抵用券消费mapper
 * @author LC
 * @date 2017/7/27
 */
public interface PubCouponUseMapper {


    void saveCouponUse(PubCouponUse couponUse);

    List<PubCouponUse> listByCouponUseParam(PubCouponUse couponUse);

    void updateCouponUse(PubCouponUse couponUse);

    void removeUseByOrderIdAndCouponId(PubCouponUse couponUse);

    PubCouponUse getCouponUseByOrder(@Param("usestate") String usestate,@Param("billingorderid") String billingorderid);
}
 