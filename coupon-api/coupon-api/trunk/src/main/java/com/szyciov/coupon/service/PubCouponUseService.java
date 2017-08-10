package com.szyciov.coupon.service;

import java.time.LocalDateTime;

import javax.annotation.Resource;

import com.szyciov.coupon.dao.PubCouponUseCityDao;
import com.szyciov.coupon.dao.PubCouponUseDao;
import com.szyciov.coupon.dto.CouponInfoDTO;
import com.szyciov.coupon.dto.CouponUseInfoDTO;
import com.szyciov.entity.coupon.PubCouponUse;
import com.szyciov.enums.coupon.CouponEnum;
import com.szyciov.param.coupon.CouponUseParam;
import org.springframework.stereotype.Service;

/**
 * 抵用券使用service
 * @author LC
 * @date 2017/8/8
 */
@Service
public class PubCouponUseService {
    @Resource
    private PubCouponUseDao couponUseDao;

    /**
     * 更新并返回实际抵扣金额
     * @param param
     * @return
     * @throws Exception
     */
    public CouponUseInfoDTO updateActualamount(CouponUseParam param) throws Exception{


        PubCouponUse use = couponUseDao.getCouponUseByOrder(CouponEnum.COUPON_STATUS_USED.code+"", param.getOrderId());

        if(use!=null) {
            //默认抵用券金额
            double actualamount = use.getCouponmoney();
            //订单金额大于抵用券金额
            if (param.getMoney() < use.getCouponmoney()) {
                actualamount = param.getMoney();
            }

            PubCouponUse updateUse = new PubCouponUse();
            updateUse.setId(use.getId());
            updateUse.setActualamount(actualamount);
            updateUse.setUpdatetime(LocalDateTime.now());
            updateUse.setUpdater(param.getUserId());

            couponUseDao.updateCouponUse(updateUse);

            CouponUseInfoDTO infoDTO = new CouponUseInfoDTO();
            infoDTO.setActualamount(actualamount);
            infoDTO.setCouponmoney(use.getCouponmoney());
            infoDTO.setDiscountamount(use.getActualamount());

            return infoDTO;
        }
        return null;
    }

}
 