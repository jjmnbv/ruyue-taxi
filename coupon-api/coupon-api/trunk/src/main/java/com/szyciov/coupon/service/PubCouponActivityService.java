package com.szyciov.coupon.service;

import java.util.List;

import javax.annotation.Resource;

import com.szyciov.coupon.dao.PubCouponActivityDao;
import com.szyciov.entity.coupon.PubCouponActivity;
import org.springframework.stereotype.Service;

/**
 *
 * @author LC
 * @date 2017/7/27
 */
@Service
public class PubCouponActivityService {

    @Resource
    private PubCouponActivityDao pubCouponActivityDao;


    public List<PubCouponActivity> listPubCouponActivity(){
        return  null;
    }

}
 