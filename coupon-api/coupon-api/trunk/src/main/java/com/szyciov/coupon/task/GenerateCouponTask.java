package com.szyciov.coupon.task;

import com.szyciov.coupon.factory.generate.GenerateCoupon;
import com.szyciov.coupon.factory.generate.GenerateCouponFactory;
import com.szyciov.param.coupon.GenerateCouponParam;
import com.szyciov.util.GsonUtil;
import com.szyciov.util.threadpool.AsyncTask;

/**
 * 生成抵用券任务
 * @author LC
 * @date 2017/8/14
 */
public class GenerateCouponTask implements AsyncTask {

    private String jsonStr ;


    public GenerateCouponTask(String jsonStr) {
        this.jsonStr = jsonStr;
    }

    @Override
    public void run() {
        GenerateCouponParam param = GsonUtil.fromJson(jsonStr, GenerateCouponParam.class);

        GenerateCoupon generateCoupon =  GenerateCouponFactory.generateCoupon(param.getType());

        if(generateCoupon!=null) {
            generateCoupon.generate(param);
        }
    }
}
 