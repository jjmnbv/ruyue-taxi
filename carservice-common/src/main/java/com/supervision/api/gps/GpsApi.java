package com.supervision.api.gps;


import com.supervision.api.BaseApi;
import lombok.Data;

/**
 * GPS数据
 * Created by 林志伟 on 2017/7/7.
 */
@Data
public class GpsApi extends BaseApi {


    /**
     * 当前位置行政区划编号
     */
    private String address;

}
