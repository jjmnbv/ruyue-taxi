package com.szyciov.coupon.param;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * 订单查询参数
 *
 * @author LC
 * @date 2017/8/1
 */
@Data
public class OrderQueryParam {

    //开始时间
    private LocalDateTime startDt;

    //结束时间
    private LocalDateTime endDt;

    //订单归属公司ID
    private String companyid;

    //上车城市
    private String cityCode;

    //用户ID
    private String userId;

    //用户电话号码
    private String userPhone;

    //数据表名
    private String tbName;

}
 