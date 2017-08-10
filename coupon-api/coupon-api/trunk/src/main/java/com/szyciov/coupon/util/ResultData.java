package com.szyciov.coupon.util;

import lombok.Data;

/**
 * 接口返回值
 * @author LC
 * @date 2017/8/9
 */
@Data
public class ResultData {

    public static final String DATA_TYPE_SINGLE = "single";

    public static final String DATA_TYPE_LIST  = "list";


    private Integer status;

    private String message;

    private String data;

    /**
     * 数据类型， obj,还是集合，用于解析data
     */
    private String dataType;

}
 