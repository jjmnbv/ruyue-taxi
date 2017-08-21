package com.szyciov.supervision.api.dto.gps;


import com.supervision.enums.InterfaceType;
import com.szyciov.supervision.api.dto.BaseApi;

/**
 * GPS数据
 * Created by 林志伟 on 2017/7/7.
 */

public class GpsApi extends BaseApi {
    public GpsApi() {
        super();
        setApiType(InterfaceType.GPS);
    }

    /**
     * 当前位置行政区划编号
     */
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "GpsApi{" +
                "address='" + address + '\'' +
                "} " + super.toString();
    }
}
