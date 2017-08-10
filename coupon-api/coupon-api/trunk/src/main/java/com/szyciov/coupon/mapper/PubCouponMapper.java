package com.szyciov.coupon.mapper;

import java.util.List;

import com.szyciov.coupon.dto.CouponInfoDTO;
import com.szyciov.coupon.param.PubCouponQueryParam;
import com.szyciov.entity.coupon.PubCoupon;
import org.apache.ibatis.annotations.Param;

/**
 * 抵用券处理mapper
 * @author LC
 * @date 2017/7/27
 */
public interface PubCouponMapper {

    void savePubCoupon(PubCoupon pubCoupon);


    List<CouponInfoDTO> listAllScopeCoupon(PubCouponQueryParam param);


    List<CouponInfoDTO> listCityScopeCoupon(PubCouponQueryParam param);

    List<CouponInfoDTO> listCoupon(PubCouponQueryParam param);

    void updateState(PubCoupon pubCoupon);

    PubCoupon getCouponById(String id);

    void removeCouponByUserId(PubCoupon pubCoupon);

}
 