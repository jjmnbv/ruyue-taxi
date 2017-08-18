package com.szyciov.op.vo;

import com.szyciov.op.entity.OpPlatformBusinesslicense;
import com.szyciov.op.entity.OpPlatformBusinesslicenseScope;

import java.util.List;

/**
 * Created by lzw on 2017/8/16.
 */
public class OpPlatformBusinesslicenseVo extends OpPlatformBusinesslicense {

    private List<OpPlatformBusinesslicenseScope> scopes;

    private String addressName;

    public List<OpPlatformBusinesslicenseScope> getScopes() {
        return scopes;
    }

    public void setScopes(List<OpPlatformBusinesslicenseScope> scopes) {
        this.scopes = scopes;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    @Override
    public String toString() {
        return "OpPlatformBusinesslicenseVo{" +
                "scopes=" + scopes +
                ", addressName='" + addressName + '\'' +
                "} " + super.toString();
    }
}
