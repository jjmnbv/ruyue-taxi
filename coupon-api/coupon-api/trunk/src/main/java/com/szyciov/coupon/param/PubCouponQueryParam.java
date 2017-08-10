package com.szyciov.coupon.param;

import java.time.LocalDate;

import lombok.Data;

/**
 * 抵用券列表查询参数
 * @author LC
 * @date 2017/8/8
 */
@Data
public class PubCouponQueryParam {

    private String userid;

    private String city;

    private String companyid;

    private Integer couponstatus;

    private LocalDate nowDt;

    private Integer servicetype;

    private Integer iDisplayStart;

    private Integer iDisplayLength;

    private Integer target;
}
 