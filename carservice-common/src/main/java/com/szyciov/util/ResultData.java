package com.szyciov.util;


/**
 * 接口返回值
 * @author LC
 * @date 2017/8/9
 */

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}
 