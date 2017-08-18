package com.szyciov.op.param;

import com.szyciov.param.QueryParam;

/**
 * Created by Administrator on 2017/8/9 0009.
 */
public class OpPlatformStagnationQueryParam extends QueryParam {

    /**
     * 驻点城市
     */
    private String city;
    /**
     * 负责人姓名
     */
    private String responsible;

    /**
     * 负责人联系方式
     */
    private String responsibleway;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public String getResponsibleway() {
        return responsibleway;
    }

    public void setResponsibleway(String responsibleway) {
        this.responsibleway = responsibleway;
    }
}
