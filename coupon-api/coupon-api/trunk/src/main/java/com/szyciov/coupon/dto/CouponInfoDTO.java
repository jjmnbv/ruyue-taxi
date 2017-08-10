package com.szyciov.coupon.dto;

import java.time.LocalDate;

import lombok.Data;

/**
 * 抵用券DTO
 * @author LC
 * @date 2017/8/8
 */
@Data
public class CouponInfoDTO {

    private String id;
    private String name;
    private Double money;
    private Integer servicetype;
    private LocalDate outimestart;
    private LocalDate outtimeend;
    private Integer usetype;
    private String cityStr;

}
 