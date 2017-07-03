package com.szyciov.dto.api;

/**
 * 寻程数据接口平台 返回dto
 * Created by LC on 2017/4/8.
 */
public class XunChengApiResultDto {
    //返回代码
    private int error_code ;
    //返回结果
    private String result;
    //返回说明
    private String reason;
    //订单号
    private String ordersign;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getOrdersign() {
        return ordersign;
    }

    public void setOrdersign(String ordersign) {
        this.ordersign = ordersign;
    }
}
 