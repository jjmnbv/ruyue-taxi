package com.szyciov.op.param;

import com.szyciov.param.QueryParam;

/**
 * Created by Administrator on 2017/8/9 0009.
 */
public class OpPlatformServiceorganQueryParam extends QueryParam {
    /**
     * 服务名称
     */
    private String servicename;
    /**
     * 机构所在地
     */
    private String address;

    /**
     * 机构负责人
     */
    private String responsiblename;

    /**
     * 负责人电话
     */
    private String responsiblephone;

    public String getServicename() {
        return servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getResponsiblename() {
        return responsiblename;
    }

    public void setResponsiblename(String responsiblename) {
        this.responsiblename = responsiblename;
    }

    public String getResponsiblephone() {
        return responsiblephone;
    }

    public void setResponsiblephone(String responsiblephone) {
        this.responsiblephone = responsiblephone;
    }
}
