package com.szyciov.op.param;

import com.szyciov.param.QueryParam;

/**
 * Created by lzw on 2017/8/16.
 */
public class OpPlatformBusinesslicenseQueryParam extends QueryParam {
    /**
     * 经营许可证号
     */
    private String certificate;

    /**
     * 经营许可地
     */
    private String address;

    /**
     * 证件状态
     */
    private String state;

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "OpPlatformBusinesslicenseQueryParam{" +
                "certificate='" + certificate + '\'' +
                ", address='" + address + '\'' +
                ", state='" + state + '\'' +
                "} " + super.toString();
    }
}
